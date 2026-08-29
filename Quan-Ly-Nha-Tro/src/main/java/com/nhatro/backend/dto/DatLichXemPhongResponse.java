package com.nhatro.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record DatLichXemPhongResponse(
        Integer maLichHen,
        Integer maPhong,
        String tenPhong,
        String hoTen,
        String soDienThoai,
        LocalDate ngayHen,
        LocalTime gioHen,
        Integer soNguoiDiCung,
        String nuoiThuCung,
        String thoiGianDuKienDonVao,
        String ghiChu,
        String trangThai,
        String diaDiem
) {}
