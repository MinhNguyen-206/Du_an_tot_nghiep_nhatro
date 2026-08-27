package com.nhatro.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanhGiaResponse {
    private Integer maDanhGia;
    private Integer soSao;
    private String noiDung;
    private LocalDateTime ngayDanhGia;
    private Boolean trangThai;
    private NguoiDungTomTatDto nguoiDung;
    private PhongTomTatDto phong;
}