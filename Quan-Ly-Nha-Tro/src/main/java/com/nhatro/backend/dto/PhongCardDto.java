package com.nhatro.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PhongCardDto(
        Integer maPhong,
        String tenPhong,
        String tenNhaTro,
        String diaChi,
        BigDecimal giaPhong,
        String loaiPhong,
        String hinhAnh,
        Boolean trangThai,
        List<String> tienIch,
        LocalDateTime thoiGian
) {}
