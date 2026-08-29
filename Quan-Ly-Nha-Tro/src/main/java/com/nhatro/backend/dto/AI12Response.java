package com.nhatro.backend.dto;

import java.math.BigDecimal;

public record AI12Response(
        Integer maDangTin,
        String tieuDe,
        Integer maPhong,
        String tenPhong,
        String tenNhaTro,
        String diaChi,

        BigDecimal giaPhong,
        BigDecimal dienTich,

        Long luotXem,
        Long luotYeuThich,

        Integer soTienIch,

        Double diemAI,
        String mucDo,
        String nhanDinh
) {
}