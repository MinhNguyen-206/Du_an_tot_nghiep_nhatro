package com.nhatro.backend.service;

import com.nhatro.backend.dto.NguoiDungQuanLyDto;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.NhatKyHoatDong;
import com.nhatro.backend.entity.VaiTro;
import com.nhatro.backend.entity.XacThucEkyc;
import com.nhatro.backend.repository.BaoCaoRepository;
import com.nhatro.backend.repository.NguoiDungRepository;
import com.nhatro.backend.repository.VaiTroRepository;
import com.nhatro.backend.repository.XacThucEkycRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * Nghiep vu rieng cho man hinh "Quan ly nguoi dung" cua Admin
 * (WEB-INF/jsp/admin/userManagement.jsp + /api/admin/nguoi-dung/**).
 *
 * Tach rieng khoi NguoiDungService (dung chung cho dang ky/profile...) vi
 * o day can gop them du lieu tu nhieu bang (eKYC, bao cao vi pham) va co
 * cac hanh dong dac thu cua Admin (khoa/mo khoa, doi vai tro, admin tu tao
 * tai khoan voi vai tro tuy chon) - khac voi luong dang ky cong khai.
 */
@Service
public class AdminNguoiDungService {

    private static final String TRANG_THAI_CHUA_GUI = "CHUA_GUI";
    private static final String TRANG_THAI_CHO_DUYET = "CHO_DUYET";
    private static final String TRANG_THAI_DA_XAC_MINH = "DA_XAC_MINH";

    private final NguoiDungRepository nguoiDungRepository;
    private final VaiTroRepository vaiTroRepository;
    private final XacThucEkycRepository xacThucEkycRepository;
    private final BaoCaoRepository baoCaoRepository;
    private final PasswordEncoder passwordEncoder;
    private final NhatKyHoatDongService nhatKyHoatDongService;

    public AdminNguoiDungService(NguoiDungRepository nguoiDungRepository,
                                  VaiTroRepository vaiTroRepository,
                                  XacThucEkycRepository xacThucEkycRepository,
                                  BaoCaoRepository baoCaoRepository,
                                  PasswordEncoder passwordEncoder,
                                  NhatKyHoatDongService nhatKyHoatDongService) {
        this.nguoiDungRepository = Objects.requireNonNull(nguoiDungRepository, "nguoiDungRepository must not be null");
        this.vaiTroRepository = Objects.requireNonNull(vaiTroRepository, "vaiTroRepository must not be null");
        this.xacThucEkycRepository = Objects.requireNonNull(xacThucEkycRepository, "xacThucEkycRepository must not be null");
        this.baoCaoRepository = Objects.requireNonNull(baoCaoRepository, "baoCaoRepository must not be null");
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder, "passwordEncoder must not be null");
        this.nhatKyHoatDongService = Objects.requireNonNull(nhatKyHoatDongService, "nhatKyHoatDongService must not be null");
    }

    @Transactional(readOnly = true)
    public List<NguoiDungQuanLyDto> danhSach(String tuKhoa, Integer maVaiTro, Boolean trangThai) {
        Map<Integer, String> ekycTheoNguoiDung = layTrangThaiEkycMoiNhat();
        Map<Integer, Long> viPhamTheoNguoiDung = laySoLuotViPham();
        String keyword = (tuKhoa == null || tuKhoa.isBlank()) ? null : tuKhoa.trim().toLowerCase();

        return nguoiDungRepository.findAll().stream()
                .filter(nd -> maVaiTro == null || (nd.getVaiTro() != null && maVaiTro.equals(nd.getVaiTro().getMaVaiTro())))
                .filter(nd -> trangThai == null || trangThai.equals(nd.getTrangThai()))
                .filter(nd -> keyword == null || khopTuKhoa(nd, keyword))
                .sorted(Comparator.comparing(NguoiDung::getMaNguoiDung, Comparator.nullsLast(Comparator.reverseOrder())))
                .map(nd -> toDto(nd, ekycTheoNguoiDung, viPhamTheoNguoiDung))
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<NguoiDungQuanLyDto> chiTiet(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        Map<Integer, String> ekycTheoNguoiDung = layTrangThaiEkycMoiNhat();
        Map<Integer, Long> viPhamTheoNguoiDung = laySoLuotViPham();
        return nguoiDungRepository.findById(id).map(nd -> toDto(nd, ekycTheoNguoiDung, viPhamTheoNguoiDung));
    }

    @Transactional(readOnly = true)
    public List<VaiTro> danhSachVaiTro() {
        return vaiTroRepository.findAll();
    }

    // Admin tu tao 1 tai khoan MOI voi vai tro tuy chon (khac voi
    // POST /api/nguoi-dung cong khai luon ep vai tro "Nguoi thue").
    @Transactional
    public NguoiDung taoTaiKhoan(NguoiDung duLieu, Integer maAdminThucHien) {
        Objects.requireNonNull(duLieu, "duLieu must not be null");
        if (duLieu.getHoTen() == null || duLieu.getHoTen().isBlank()) {
            throw new IllegalArgumentException("Họ tên không được để trống");
        }
        if (duLieu.getEmail() == null || duLieu.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        if (nguoiDungRepository.existsByEmail(duLieu.getEmail())) {
            throw new IllegalArgumentException("Email đã được sử dụng");
        }
        if (duLieu.getSoDienThoai() != null && !duLieu.getSoDienThoai().isBlank()
                && nguoiDungRepository.existsBySoDienThoai(duLieu.getSoDienThoai())) {
            throw new IllegalArgumentException("Số điện thoại đã được sử dụng");
        }
        if (duLieu.getMatKhau() == null || duLieu.getMatKhau().isBlank()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống");
        }

        Integer maVaiTroMongMuon = duLieu.getVaiTro() != null ? duLieu.getVaiTro().getMaVaiTro() : null;
        VaiTro vaiTro = maVaiTroMongMuon != null
                ? vaiTroRepository.findById(maVaiTroMongMuon)
                        .orElseThrow(() -> new IllegalArgumentException("Vai trò không tồn tại"))
                : vaiTroRepository.findByTenVaiTro("Người thuê")
                        .orElseThrow(() -> new IllegalStateException("Không tìm thấy vai trò mặc định 'Người thuê'"));

        duLieu.setMaNguoiDung(null);
        duLieu.setVaiTro(vaiTro);
        duLieu.setMatKhau(passwordEncoder.encode(duLieu.getMatKhau()));
        duLieu.setTrangThai(duLieu.getTrangThai() == null ? Boolean.TRUE : duLieu.getTrangThai());

        NguoiDung daTao = nguoiDungRepository.save(duLieu);
        ghiNhat(maAdminThucHien, "Tạo tài khoản người dùng", "Người dùng #" + daTao.getMaNguoiDung());
        return daTao;
    }

    // Khoa / mo khoa tai khoan (cot "Trang thai" trong bang).
    @Transactional
    public NguoiDung capNhatTrangThai(Integer id, boolean trangThaiMoi, Integer maAdminThucHien) {
        Objects.requireNonNull(id, "id must not be null");
        if (id.equals(maAdminThucHien) && !trangThaiMoi) {
            throw new IllegalStateException("Không thể tự khóa tài khoản đang đăng nhập");
        }
        NguoiDung nd = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng"));
        nd.setTrangThai(trangThaiMoi);
        nd.setNgayCapNhat(LocalDateTime.now());
        NguoiDung daLuu = nguoiDungRepository.save(nd);
        ghiNhat(maAdminThucHien, trangThaiMoi ? "Mở khóa tài khoản" : "Khóa tài khoản", "Người dùng #" + id);
        return daLuu;
    }

    // Doi vai tro (VD: nang/ha quyen giua Nguoi thue - Chu tro - Admin).
    @Transactional
    public NguoiDung capNhatVaiTro(Integer id, Integer maVaiTroMoi, Integer maAdminThucHien) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(maVaiTroMoi, "maVaiTroMoi must not be null");
        if (id.equals(maAdminThucHien)) {
            throw new IllegalStateException("Không thể tự đổi vai trò của chính mình");
        }
        NguoiDung nd = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng"));
        VaiTro vaiTro = vaiTroRepository.findById(maVaiTroMoi)
                .orElseThrow(() -> new IllegalArgumentException("Vai trò không tồn tại"));
        nd.setVaiTro(vaiTro);
        nd.setNgayCapNhat(LocalDateTime.now());
        NguoiDung daLuu = nguoiDungRepository.save(nd);
        ghiNhat(maAdminThucHien, "Đổi vai trò thành " + vaiTro.getTenVaiTro(), "Người dùng #" + id);
        return daLuu;
    }

    @Transactional
    public void xoa(Integer id, Integer maAdminThucHien) {
        Objects.requireNonNull(id, "id must not be null");
        if (id.equals(maAdminThucHien)) {
            throw new IllegalStateException("Không thể tự xóa tài khoản đang đăng nhập");
        }
        if (!nguoiDungRepository.existsById(id)) {
            throw new NoSuchElementException("Không tìm thấy người dùng");
        }
        nguoiDungRepository.deleteById(id);
        ghiNhat(maAdminThucHien, "Xóa tài khoản người dùng", "Người dùng #" + id);
    }

    // ===================== HELPER =====================

    private void ghiNhat(Integer maAdminThucHien, String hanhDong, String doiTuong) {
        try {
            NguoiDung admin = maAdminThucHien == null ? null : nguoiDungRepository.findById(maAdminThucHien).orElse(null);
            nhatKyHoatDongService.create(NhatKyHoatDong.builder()
                    .nguoiDung(admin)
                    .hanhDong(hanhDong)
                    .doiTuong(doiTuong)
                    .build());
        } catch (RuntimeException ignored) {
            // Ghi nhat ky la thao tac phu, khong duoc phep lam hong thao tac chinh.
        }
    }

    private boolean khopTuKhoa(NguoiDung nd, String keyword) {
        return chua(nd.getHoTen(), keyword) || chua(nd.getEmail(), keyword) || chua(nd.getSoDienThoai(), keyword);
    }

    private boolean chua(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }

    private Map<Integer, String> layTrangThaiEkycMoiNhat() {
        Map<Integer, String> result = new HashMap<>();
        for (XacThucEkyc ekyc : xacThucEkycRepository.findAllByOrderByNgayGuiDesc()) {
            if (ekyc.getNguoiDung() == null || ekyc.getNguoiDung().getMaNguoiDung() == null) {
                continue;
            }
            Integer maNguoiDung = ekyc.getNguoiDung().getMaNguoiDung();
            // Da sap xep giam dan theo ngayGui -> ban ghi dau tien gap cho
            // moi maNguoiDung chinh la ban ghi moi nhat, cac ban ghi cu hon bo qua.
            result.putIfAbsent(maNguoiDung, Boolean.TRUE.equals(ekyc.getTrangThai()) ? TRANG_THAI_DA_XAC_MINH : TRANG_THAI_CHO_DUYET);
        }
        return result;
    }

    private Map<Integer, Long> laySoLuotViPham() {
        Map<Integer, Long> result = new HashMap<>();
        for (Object[] row : baoCaoRepository.demSoBaoCaoTheoNguoiBiBaoCao()) {
            result.put((Integer) row[0], (Long) row[1]);
        }
        return result;
    }

    private NguoiDungQuanLyDto toDto(NguoiDung nd, Map<Integer, String> ekycMap, Map<Integer, Long> viPhamMap) {
        return new NguoiDungQuanLyDto(
                nd.getMaNguoiDung(),
                nd.getHoTen(),
                nd.getEmail(),
                nd.getSoDienThoai(),
                nd.getAvatar(),
                nd.getVaiTro() == null ? null : nd.getVaiTro().getMaVaiTro(),
                nd.getVaiTro() == null ? null : nd.getVaiTro().getTenVaiTro(),
                nd.getGioiTinh(),
                nd.getNgaySinh(),
                nd.getDiaChi(),
                nd.getTrangThai(),
                nd.getNgayDangKy(),
                nd.getNgayCapNhat(),
                ekycMap.getOrDefault(nd.getMaNguoiDung(), TRANG_THAI_CHUA_GUI),
                viPhamMap.getOrDefault(nd.getMaNguoiDung(), 0L));
    }
}
