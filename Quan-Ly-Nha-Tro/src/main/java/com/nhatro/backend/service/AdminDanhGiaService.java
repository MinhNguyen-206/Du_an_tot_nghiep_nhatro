package com.nhatro.backend.service;

import com.nhatro.backend.dto.DanhGiaKiemDuyetDto;
import com.nhatro.backend.entity.BaoCao;
import com.nhatro.backend.entity.DanhGia;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.NhaTro;
import com.nhatro.backend.entity.NhatKyHoatDong;
import com.nhatro.backend.entity.PhongTro;
import com.nhatro.backend.repository.BaoCaoRepository;
import com.nhatro.backend.repository.DanhGiaRepository;
import com.nhatro.backend.repository.NguoiDungRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Nghiep vu rieng cho man hinh "Kiem duyet danh gia & binh luan" cua Admin.
 *
 * Quy uoc trang thai (suy ra tu 2 truong tren entity DanhGia, khong luu
 * rieng 1 cot trang thai kiem duyet vi chi co 3 truong hop don gian):
 *  - trangThai=true,  lyDoBaoCao=null  -> "Binh thuong"
 *  - trangThai=true,  lyDoBaoCao!=null -> "Bi bao cao" (van hien thi cong
 *    khai, cho Admin xu ly)
 *  - trangThai=false                   -> "Da an" (Admin da an khoi cong khai)
 *
 * "An & canh cao": ngoai viec an danh gia, con tao 1 ban ghi BAO_CAO nham
 * vao nguoi viet danh gia -> tu dong cong don vao so "Vi pham" cua nguoi
 * do (hien thi o trang Quan ly nguoi dung).
 */
@Service
public class AdminDanhGiaService {

    public static final String BINH_THUONG = "BINH_THUONG";
    public static final String BI_BAO_CAO = "BI_BAO_CAO";
    public static final String DA_AN = "DA_AN";

    private final DanhGiaRepository danhGiaRepository;
    private final BaoCaoRepository baoCaoRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final NhatKyHoatDongService nhatKyHoatDongService;

    public AdminDanhGiaService(DanhGiaRepository danhGiaRepository,
                                BaoCaoRepository baoCaoRepository,
                                NguoiDungRepository nguoiDungRepository,
                                NhatKyHoatDongService nhatKyHoatDongService) {
        this.danhGiaRepository = Objects.requireNonNull(danhGiaRepository, "danhGiaRepository must not be null");
        this.baoCaoRepository = Objects.requireNonNull(baoCaoRepository, "baoCaoRepository must not be null");
        this.nguoiDungRepository = Objects.requireNonNull(nguoiDungRepository, "nguoiDungRepository must not be null");
        this.nhatKyHoatDongService = Objects.requireNonNull(nhatKyHoatDongService, "nhatKyHoatDongService must not be null");
    }

    @Transactional(readOnly = true)
    public List<DanhGiaKiemDuyetDto> danhSach(String trangThaiLoc, String tuKhoa) {
        String keyword = (tuKhoa == null || tuKhoa.isBlank()) ? null : tuKhoa.trim().toLowerCase();
        String loc = (trangThaiLoc == null || trangThaiLoc.isBlank()) ? null : trangThaiLoc.trim().toUpperCase();

        return danhGiaRepository.findAllByOrderByNgayDanhGiaDesc().stream()
                .filter(dg -> loc == null || loc.equals(suyRaTrangThai(dg)))
                .filter(dg -> keyword == null || khopTuKhoa(dg, keyword))
                .map(this::toDto)
                .toList();
    }

    /** Khôi phục: đánh giá trở lại bình thường, xóa cờ báo cáo, hiện công khai lại. */
    @Transactional
    public DanhGiaKiemDuyetDto khoiPhuc(Integer id, Integer maAdminThucHien) {
        DanhGia dg = layDeCapNhat(id);
        dg.setTrangThai(true);
        dg.setLyDoBaoCao(null);
        DanhGia daLuu = danhGiaRepository.save(dg);
        ghiNhat(maAdminThucHien, "Khôi phục đánh giá", "Đánh giá #" + id);
        return toDto(daLuu);
    }

    /** Ẩn: gỡ khỏi hiển thị công khai nhưng không ghi nhận vi phạm cho người viết. */
    @Transactional
    public DanhGiaKiemDuyetDto an(Integer id, Integer maAdminThucHien) {
        DanhGia dg = layDeCapNhat(id);
        dg.setTrangThai(false);
        DanhGia daLuu = danhGiaRepository.save(dg);
        ghiNhat(maAdminThucHien, "Ẩn đánh giá", "Đánh giá #" + id);
        return toDto(daLuu);
    }

    /** Ẩn & cảnh cáo: ẩn đánh giá + ghi nhận 1 lượt vi phạm cho người viết. */
    @Transactional
    public DanhGiaKiemDuyetDto anVaCanhCao(Integer id, String lyDo, Integer maAdminThucHien) {
        DanhGia dg = layDeCapNhat(id);
        dg.setTrangThai(false);
        if (lyDo != null && !lyDo.isBlank()) {
            dg.setLyDoBaoCao(lyDo.trim());
        }
        DanhGia daLuu = danhGiaRepository.save(dg);

        NguoiDung tacGia = dg.getNguoiDung();
        if (tacGia != null) {
            NguoiDung admin = layAdmin(maAdminThucHien);
            baoCaoRepository.save(BaoCao.builder()
                    .nguoiGui(admin != null ? admin : tacGia) // he thong tu tao, gan tam nguoi gui la Admin xu ly
                    .nguoiBiBaoCao(tacGia)
                    .lyDo("Đánh giá vi phạm: " + (dg.getLyDoBaoCao() != null ? dg.getLyDoBaoCao() : "Nội dung không phù hợp"))
                    .noiDung("Tự động ghi nhận khi Admin ẩn & cảnh cáo đánh giá #" + id)
                    .trangThai("DA_XU_LY")
                    .build());
        }

        ghiNhat(maAdminThucHien, "Ẩn & cảnh cáo đánh giá", "Đánh giá #" + id
                + (tacGia != null ? " - người viết: " + tacGia.getHoTen() : ""));
        return toDto(daLuu);
    }

    // ===================== HELPER =====================

    private DanhGia layDeCapNhat(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return danhGiaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy đánh giá"));
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

    private String suyRaTrangThai(DanhGia dg) {
        if (!Boolean.TRUE.equals(dg.getTrangThai())) {
            return DA_AN;
        }
        return (dg.getLyDoBaoCao() != null && !dg.getLyDoBaoCao().isBlank()) ? BI_BAO_CAO : BINH_THUONG;
    }

    private boolean khopTuKhoa(DanhGia dg, String keyword) {
        NguoiDung nd = dg.getNguoiDung();
        PhongTro phong = dg.getPhong();
        NhaTro nhaTro = phong == null ? null : phong.getNhaTro();
        return chua(dg.getNoiDung(), keyword)
                || chua(nd == null ? null : nd.getHoTen(), keyword)
                || chua(nhaTro == null ? null : nhaTro.getTenNhaTro(), keyword)
                || chua(phong == null ? null : phong.getTenPhong(), keyword);
    }

    private boolean chua(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }

    private DanhGiaKiemDuyetDto toDto(DanhGia dg) {
        NguoiDung nd = dg.getNguoiDung();
        PhongTro phong = dg.getPhong();
        NhaTro nhaTro = phong == null ? null : phong.getNhaTro();

        return new DanhGiaKiemDuyetDto(
                dg.getMaDanhGia(),
                dg.getNoiDung(),
                dg.getSoSao(),
                dg.getNgayDanhGia(),
                dg.getTrangThai(),
                dg.getLyDoBaoCao(),

                nd == null ? null : nd.getMaNguoiDung(),
                nd == null ? null : nd.getHoTen(),
                nd == null ? null : nd.getAvatar(),

                phong == null ? null : phong.getMaPhong(),
                phong == null ? null : phong.getTenPhong(),
                nhaTro == null ? null : nhaTro.getMaNhaTro(),
                nhaTro == null ? null : nhaTro.getTenNhaTro());
    }
}
