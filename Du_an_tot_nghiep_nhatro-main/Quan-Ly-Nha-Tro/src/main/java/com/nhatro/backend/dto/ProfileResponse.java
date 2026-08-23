package com.nhatro.backend.dto;

import com.nhatro.backend.entity.NguoiDung;

import java.util.List;

public record ProfileResponse(
        NguoiDung nguoiDung,
        int soPhongDaLuu,
        int soLichHenSapToi,
        List<PhongCardDto> phongDaLuu,
        List<PhongCardDto> lichSuXemPhong,
        List<ProfileContractDto> hopDongGanDay,
        List<ProfileAppointmentDto> lichHenSapToi
) {}
