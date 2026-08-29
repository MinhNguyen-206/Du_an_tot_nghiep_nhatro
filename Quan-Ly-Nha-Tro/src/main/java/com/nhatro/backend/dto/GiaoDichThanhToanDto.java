package com.nhatro.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Du lieu 1 dong trong bang "Giao dich & hoa don" cua Admin
 * (WEB-INF/jsp/admin/transactions.jsp + /api/admin/giao-dich/**).
 * Gop thong tin GiaoDichThanhToan (giao dich cong thanh toan) +
 * ThanhToanTienTro (phieu thanh toan) + HoaDonThang (hoa don thang) +
 * HopDongDienTu (hop dong, de biet nguoi thue/phong/nha tro).
 */
public record GiaoDichThanhToanDto(
        Integer maGiaoDich,
        String maGiaoDichCongThanhToan,
        String nganHang,
        String noiDung,
        LocalDateTime ngayGiaoDich,
        String trangThaiGiaoDich,

        Integer maThanhToan,
        BigDecimal soTien,
        String phuongThuc,
        LocalDateTime ngayThanhToan,

        Integer maHoaDon,
        BigDecimal tongTienHoaDon,
        LocalDateTime ngayLapHoaDon,
        LocalDate hanThanhToan,
        String trangThaiHoaDon,

        Integer maNguoiThue,
        String tenNguoiThue,
        String emailNguoiThue,

        Integer maPhong,
        String tenPhong,
        String tenNhaTro
) {
}
