package com.nhatro.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Du lieu 1 bai dang hien thi cho Admin trong man hinh "Duyet bai dang"
 * (WEB-INF/jsp/admin/postApproval.jsp + /api/admin/dang-tin/**).
 * Gop thong tin DANG_TIN + nguoi dang (Chu tro) + phong/nha tro + anh dai dien,
 * de FE khong phai goi nhieu API rieng le cho 1 dong bang.
 */
public record DangTinDuyetDto(
        Integer maDangTin,
        String tieuDe,
        String noiDung,
        LocalDateTime ngayDang,
        LocalDateTime ngayHetHan,
        Boolean trangThai,
        // CHO_DUYET | DA_DUYET | TU_CHOI
        String trangThaiDuyet,
        String lyDoTuChoi,
        LocalDateTime ngayDuyet,
        Integer maNguoiDuyet,
        String tenNguoiDuyet,

        Integer maNguoiDang,
        String tenNguoiDang,
        String emailNguoiDang,
        String soDienThoaiNguoiDang,
        String avatarNguoiDang,

        Integer maPhong,
        String tenPhong,
        BigDecimal dienTich,
        String loaiPhong,
        BigDecimal giaPhong,

        Integer maNhaTro,
        String tenNhaTro,
        String diaChiNhaTro,

        String anhDaiDien,
        List<String> danhSachAnh
) {
}
