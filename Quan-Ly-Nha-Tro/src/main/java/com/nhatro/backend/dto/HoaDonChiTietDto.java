package com.nhatro.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Du lieu chi tiet 1 hoa don thang, dung cho man hinh "Xem hoa don" (co
 * the in ra PDF bang chuc nang In cua trinh duyet - xem ghi chu trong
 * AdminGiaoDichService).
 */
public record HoaDonChiTietDto(
        Integer maHoaDon,
        BigDecimal tongTien,
        LocalDateTime ngayLap,
        LocalDate hanThanhToan,
        String trangThai,

        Integer thangChiSo,
        Integer namChiSo,
        Integer chiSoDienCu,
        Integer chiSoDienMoi,
        Integer chiSoNuocCu,
        Integer chiSoNuocMoi,

        Integer maHopDong,
        BigDecimal giaThue,

        String tenChuTro,
        String tenNguoiThue,
        String emailNguoiThue,

        String tenPhong,
        String tenNhaTro,
        String diaChiNhaTro,

        Integer maGiaoDich,
        String maGiaoDichCongThanhToan,
        String nganHang,
        BigDecimal soTienDaThanhToan,
        String phuongThucThanhToan,
        LocalDateTime ngayThanhToan
) {
}
