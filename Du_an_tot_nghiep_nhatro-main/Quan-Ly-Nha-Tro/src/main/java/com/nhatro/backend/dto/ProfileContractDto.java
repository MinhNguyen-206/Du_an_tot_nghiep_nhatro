package com.nhatro.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProfileContractDto(
        Integer maHopDong,
        Integer maPhong,
        String tenPhong,
        String tenNhaTro,
        LocalDate ngayBatDau,
        LocalDate ngayKetThuc,
        BigDecimal giaThue,
        String trangThai,
        String fileHopDong
) {}
