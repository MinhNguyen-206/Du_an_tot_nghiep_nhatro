package com.nhatro.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Du lieu hien thi 1 dong trong bang "Quan ly nguoi dung" cua Admin.
 * Gom thong tin co ban tu NGUOI_DUNG + trang thai eKYC moi nhat +
 * so luot bi bao cao (vi pham), de FE khong phai goi nhieu API rieng le.
 */
public record NguoiDungQuanLyDto(
        Integer maNguoiDung,
        String hoTen,
        String email,
        String soDienThoai,
        String avatar,
        Integer maVaiTro,
        String tenVaiTro,
        Boolean gioiTinh,
        LocalDate ngaySinh,
        String diaChi,
        Boolean trangThai,
        LocalDateTime ngayDangKy,
        LocalDateTime ngayCapNhat,
        // CHUA_GUI | CHO_DUYET | DA_XAC_MINH
        String trangThaiEkyc,
        long soLuotViPham
) {
}
