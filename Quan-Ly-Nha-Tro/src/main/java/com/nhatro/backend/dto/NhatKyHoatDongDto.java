package com.nhatro.backend.dto;

import java.time.LocalDateTime;

/**
 * Du lieu 1 dong trong bang "Nhat ky hoat dong" cua Admin
 * (WEB-INF/jsp/admin/activityLogManagement.jsp + /api/admin/nhat-ky/**).
 */
public record NhatKyHoatDongDto(
        Integer maNhatKy,
        LocalDateTime thoiGian,
        Integer maNguoiThucHien,
        String tenNguoiThucHien,
        String emailNguoiThucHien,
        String hanhDong,
        String doiTuong,
        String diaChiIP
) {
}
