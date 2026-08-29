package com.nhatro.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DatLichXemPhongRequest {
    private Integer maPhong;
    private LocalDate ngayHen;
    private LocalTime gioHen;
    private Integer soNguoiDiCung;
    private String nuoiThuCung;
    private String thoiGianDuKienDonVao;
    private String hoTen;
    private String soDienThoai;
    private String ghiChu;
}
