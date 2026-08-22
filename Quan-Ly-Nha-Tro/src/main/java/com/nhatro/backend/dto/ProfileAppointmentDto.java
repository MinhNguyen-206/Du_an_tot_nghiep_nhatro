package com.nhatro.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ProfileAppointmentDto(
        Integer maLichHen,
        Integer maPhong,
        String tenPhong,
        String tenNhaTro,
        String diaDiem,
        LocalDate ngayHen,
        LocalTime gioHen,
        Boolean trangThai,
        String ghiChu
) {}
