package com.nhatro.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Dữ liệu tổng hợp cho trang "Xem chi tiết phòng" (chi-tiet-phong.jsp / trang FE tương ứng).
 * Gom mọi thứ FE cần trong 1 lần gọi API để tránh gọi nhiều request lẻ:
 * ảnh, tiện ích, thông tin chủ trọ, đánh giá, giá điện/nước và phòng tương tự.
 */
public record PhongChiTietDto(
        Integer maPhong,
        String tenPhong,
        String tieuDe,
        String moTa,
        String diaChi,
        String tenNhaTro,
        Integer maNhaTro,
        BigDecimal dienTich,
        String loaiPhong,
        Integer soLuongNguoi,
        BigDecimal giaPhong,
        BigDecimal giaDien,
        BigDecimal giaNuoc,
        BigDecimal giaGuiXe,
        BigDecimal giaInternet,
        Boolean conPhong,
        Double soSaoTrungBinh,
        Integer soLuongDanhGia,
        Boolean daYeuThich,
        List<HinhAnhDto> hinhAnh,
        List<TienIchDto> tienIch,
        ChuTroDto chuTro,
        List<DanhGiaDto> danhGia,
        List<PhongCardDto> phongTuongTu
) {

    public record HinhAnhDto(
            Integer maHinhAnh,
            String duongDan,
            String moTa,
            Integer thuTuHienThi
    ) {}

    public record TienIchDto(
            Integer maTienIch,
            String tenTienIch
    ) {}

    public record ChuTroDto(
            Integer maNguoiDung,
            String hoTen,
            String avatar,
            String soDienThoai,
            String email,
            Boolean daXacThuc
    ) {}

    public record DanhGiaDto(
            Integer maDanhGia,
            Integer soSao,
            String noiDung,
            LocalDateTime ngayDanhGia,
            String tenNguoiDanhGia,
            String avatarNguoiDanhGia
    ) {}
}
