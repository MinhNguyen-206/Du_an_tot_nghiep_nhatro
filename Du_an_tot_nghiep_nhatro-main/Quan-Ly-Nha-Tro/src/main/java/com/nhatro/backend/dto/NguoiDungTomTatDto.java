package com.nhatro.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDungTomTatDto {
    private Integer maNguoiDung;
    private String hoTen;
    private String avatar;
}