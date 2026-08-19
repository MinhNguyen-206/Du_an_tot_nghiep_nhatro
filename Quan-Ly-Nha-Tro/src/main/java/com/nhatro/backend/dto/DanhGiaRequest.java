package com.nhatro.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanhGiaRequest {

    @NotNull(message = "maNguoiDung khong duoc de trong")
    private Integer maNguoiDung;

    @NotNull(message = "maPhong khong duoc de trong")
    private Integer maPhong;

    @NotNull(message = "soSao khong duoc de trong")
    @Min(value = 1, message = "soSao phai tu 1 den 5")
    @Max(value = 5, message = "soSao phai tu 1 den 5")
    private Integer soSao;

    private String noiDung;

    private Boolean trangThai;
}