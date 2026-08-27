package com.nhatro.backend.service;

import com.nhatro.backend.dto.DangTinDuyetDto;
import com.nhatro.backend.entity.DangTin;
import com.nhatro.backend.entity.HinhAnh;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.NhaTro;
import com.nhatro.backend.entity.NhatKyHoatDong;
import com.nhatro.backend.entity.PhongTro;
import com.nhatro.backend.repository.DangTinRepository;
import com.nhatro.backend.repository.HinhAnhRepository;
import com.nhatro.backend.repository.NguoiDungRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * Nghiep vu rieng cho man hinh "Duyet bai dang" cua Admin.
 * Chu tro tao bai dang -> mac dinh CHO_DUYET (xem DangTin#trangThaiDuyet) ->
 * Admin xem, DUYET hoac TU_CHOI (kem ly do) tai day. Chi bai DA_DUYET moi
 * duoc hien thi cong khai (xem PhongChiTietService).
 */
@Service
public class AdminDangTinService {

    public static final String CHO_DUYET = "CHO_DUYET";
    public static final String DA_DUYET = "DA_DUYET";
    public static final String TU_CHOI = "TU_CHOI";

    private final DangTinRepository dangTinRepository;
    private final HinhAnhRepository hinhAnhRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final NhatKyHoatDongService nhatKyHoatDongService;

    public AdminDangTinService(DangTinRepository dangTinRepository,
                                HinhAnhRepository hinhAnhRepository,
                                NguoiDungRepository nguoiDungRepository,
                                NhatKyHoatDongService nhatKyHoatDongService) {
        this.dangTinRepository = Objects.requireNonNull(dangTinRepository, "dangTinRepository must not be null");
        this.hinhAnhRepository = Objects.requireNonNull(hinhAnhRepository, "hinhAnhRepository must not be null");
        this.nguoiDungRepository = Objects.requireNonNull(nguoiDungRepository, "nguoiDungRepository must not be null");
        this.nhatKyHoatDongService = Objects.requireNonNull(nhatKyHoatDongService, "nhatKyHoatDongService must not be null");
    }

    @Transactional(readOnly = true)
    public List<DangTinDuyetDto> danhSach(String trangThaiDuyet, String tuKhoa) {
        List<DangTin> nguon = (trangThaiDuyet == null || trangThaiDuyet.isBlank())
                ? dangTinRepository.findAllByOrderByNgayDangDesc()
                : dangTinRepository.findByTrangThaiDuyetOrderByNgayDangDesc(trangThaiDuyet.trim().toUpperCase());

        String keyword = (tuKhoa == null || tuKhoa.isBlank()) ? null : tuKhoa.trim().toLowerCase();

        return nguon.stream()
                .filter(dt -> keyword == null || khopTuKhoa(dt, keyword))
                .map(dt -> toDto(dt, false))
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<DangTinDuyetDto> chiTiet(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return dangTinRepository.findByMaDangTin(id).map(dt -> toDto(dt, true));
    }

    @Transactional
    public DangTinDuyetDto duyet(Integer id, Integer maAdminThucHien) {
        DangTin dt = layDeCapNhat(id);
        dt.setTrangThaiDuyet(DA_DUYET);
        dt.setLyDoTuChoi(null);
        dt.setNgayDuyet(LocalDateTime.now());
        dt.setNguoiDuyet(layAdmin(maAdminThucHien));
        DangTin daLuu = dangTinRepository.save(dt);
        ghiNhat(maAdminThucHien, "Duyệt bài đăng", "Bài đăng #" + id + " - " + safe(dt.getTieuDe()));
        return toDto(daLuu, true);
    }

    @Transactional
    public DangTinDuyetDto tuChoi(Integer id, String lyDo, Integer maAdminThucHien) {
        if (lyDo == null || lyDo.isBlank()) {
            throw new IllegalArgumentException("Vui lòng nhập lý do từ chối để chủ trọ biết cách chỉnh sửa");
        }
        DangTin dt = layDeCapNhat(id);
        dt.setTrangThaiDuyet(TU_CHOI);
        dt.setLyDoTuChoi(lyDo.trim());
        dt.setNgayDuyet(LocalDateTime.now());
        dt.setNguoiDuyet(layAdmin(maAdminThucHien));
        DangTin daLuu = dangTinRepository.save(dt);
        ghiNhat(maAdminThucHien, "Từ chối bài đăng", "Bài đăng #" + id + " - " + safe(dt.getTieuDe()));
        return toDto(daLuu, true);
    }

    // Dua bai dang tu DA_DUYET/TU_CHOI ve lai CHO_DUYET de xet duyet lai
    // (VD: Admin bam nham, hoac muon xem xet lai truong hop bi khieu nai).
    @Transactional
    public DangTinDuyetDto datLaiChoDuyet(Integer id, Integer maAdminThucHien) {
        DangTin dt = layDeCapNhat(id);
        dt.setTrangThaiDuyet(CHO_DUYET);
        dt.setLyDoTuChoi(null);
        dt.setNgayDuyet(null);
        dt.setNguoiDuyet(null);
        DangTin daLuu = dangTinRepository.save(dt);
        ghiNhat(maAdminThucHien, "Đặt lại chờ duyệt", "Bài đăng #" + id + " - " + safe(dt.getTieuDe()));
        return toDto(daLuu, true);
    }

    // ===================== HELPER =====================

    private DangTin layDeCapNhat(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return dangTinRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy bài đăng"));
    }

    private NguoiDung layAdmin(Integer maAdminThucHien) {
        return maAdminThucHien == null ? null : nguoiDungRepository.findById(maAdminThucHien).orElse(null);
    }

    private void ghiNhat(Integer maAdminThucHien, String hanhDong, String doiTuong) {
        try {
            nhatKyHoatDongService.create(NhatKyHoatDong.builder()
                    .nguoiDung(layAdmin(maAdminThucHien))
                    .hanhDong(hanhDong)
                    .doiTuong(doiTuong)
                    .build());
        } catch (RuntimeException ignored) {
            // Ghi nhat ky la thao tac phu, khong duoc phep lam hong thao tac chinh.
        }
    }

    private boolean khopTuKhoa(DangTin dt, String keyword) {
        NguoiDung nguoiDung = dt.getNguoiDung();
        PhongTro phong = dt.getPhong();
        NhaTro nhaTro = phong == null ? null : phong.getNhaTro();
        return chua(dt.getTieuDe(), keyword)
                || chua(nguoiDung == null ? null : nguoiDung.getHoTen(), keyword)
                || chua(nguoiDung == null ? null : nguoiDung.getEmail(), keyword)
                || chua(nhaTro == null ? null : nhaTro.getTenNhaTro(), keyword)
                || chua(phong == null ? null : phong.getTenPhong(), keyword);
    }

    private boolean chua(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private DangTinDuyetDto toDto(DangTin dt, boolean kemAnh) {
        NguoiDung nguoiDung = dt.getNguoiDung();
        PhongTro phong = dt.getPhong();
        NhaTro nhaTro = phong == null ? null : phong.getNhaTro();
        NguoiDung nguoiDuyet = dt.getNguoiDuyet();

        List<HinhAnh> anh = kemAnh
                ? hinhAnhRepository.findByDangTin_MaDangTinOrderByThuTuHienThi(dt.getMaDangTin())
                : List.of();
        List<String> duongDanAnh = anh.stream()
                .sorted(Comparator.comparing(HinhAnh::getThuTuHienThi, Comparator.nullsLast(Comparator.naturalOrder())))
                .map(HinhAnh::getDuongDan)
                .filter(Objects::nonNull)
                .toList();
        String anhDaiDien = duongDanAnh.isEmpty() ? (nhaTro == null ? null : nhaTro.getHinhAnh()) : duongDanAnh.get(0);

        return new DangTinDuyetDto(
                dt.getMaDangTin(),
                dt.getTieuDe(),
                dt.getNoiDung(),
                dt.getNgayDang(),
                dt.getNgayHetHan(),
                dt.getTrangThai(),
                dt.getTrangThaiDuyet(),
                dt.getLyDoTuChoi(),
                dt.getNgayDuyet(),
                nguoiDuyet == null ? null : nguoiDuyet.getMaNguoiDung(),
                nguoiDuyet == null ? null : nguoiDuyet.getHoTen(),

                nguoiDung == null ? null : nguoiDung.getMaNguoiDung(),
                nguoiDung == null ? null : nguoiDung.getHoTen(),
                nguoiDung == null ? null : nguoiDung.getEmail(),
                nguoiDung == null ? null : nguoiDung.getSoDienThoai(),
                nguoiDung == null ? null : nguoiDung.getAvatar(),

                phong == null ? null : phong.getMaPhong(),
                phong == null ? null : phong.getTenPhong(),
                phong == null ? null : phong.getDienTich(),
                phong == null ? null : phong.getLoaiPhong(),
                phong == null ? null : phong.getGiaPhong(),

                nhaTro == null ? null : nhaTro.getMaNhaTro(),
                nhaTro == null ? null : nhaTro.getTenNhaTro(),
                nhaTro == null ? null : nhaTro.getDiaChi(),

                anhDaiDien,
                duongDanAnh);
    }
}
