package com.nhatro.backend.dto;

import java.time.LocalDateTime;

/**
 * Du lieu 1 dong trong bang "Kiem duyet danh gia & binh luan" cua Admin
 * (WEB-INF/jsp/admin/reviewModeration.jsp + /api/admin/danh-gia/**).
 */
public record DanhGiaKiemDuyetDto(
        Integer maDanhGia,
        String noiDung,
        Integer soSao,
        LocalDateTime ngayDanhGia,
        Boolean trangThai,
        String lyDoBaoCao,

        Integer maNguoiDung,
        String tenNguoiDung,
        String avatarNguoiDung,

        Integer maPhong,
        String tenPhong,
        Integer maNhaTro,
        String tenNhaTro
) {
}
