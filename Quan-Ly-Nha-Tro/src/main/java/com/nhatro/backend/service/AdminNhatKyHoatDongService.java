package com.nhatro.backend.service;

import com.nhatro.backend.dto.NhatKyHoatDongDto;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.NhatKyHoatDong;
import com.nhatro.backend.repository.NhatKyHoatDongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Nghiep vu rieng cho man hinh "Nhat ky hoat dong" cua Admin.
 *
 * Chi doc (khong cung cap sua/xoa qua UI): day la nhat ky kiem toan, cho
 * phep sua/xoa se lam mat y nghia "vet lai lich su thao tac" cua no.
 * Du lieu duoc cac service khac (AdminNguoiDungService, AdminDangTinService,
 * AdminDanhGiaService, ...) tu ghi vao moi khi Admin thuc hien hanh dong.
 */
@Service
public class AdminNhatKyHoatDongService {

    private final NhatKyHoatDongRepository nhatKyHoatDongRepository;

    public AdminNhatKyHoatDongService(NhatKyHoatDongRepository nhatKyHoatDongRepository) {
        this.nhatKyHoatDongRepository = Objects.requireNonNull(nhatKyHoatDongRepository, "nhatKyHoatDongRepository must not be null");
    }

    @Transactional(readOnly = true)
    public List<NhatKyHoatDongDto> danhSach(String tuKhoa, LocalDate tuNgay, LocalDate denNgay) {
        String keyword = (tuKhoa == null || tuKhoa.isBlank()) ? null : tuKhoa.trim().toLowerCase();
        LocalDateTime tuThoiDiem = tuNgay == null ? null : tuNgay.atStartOfDay();
        LocalDateTime denThoiDiem = denNgay == null ? null : denNgay.plusDays(1).atStartOfDay(); // het ngay denNgay

        return nhatKyHoatDongRepository.findAllByOrderByThoiGianDesc().stream()
                .filter(nk -> tuThoiDiem == null || (nk.getThoiGian() != null && !nk.getThoiGian().isBefore(tuThoiDiem)))
                .filter(nk -> denThoiDiem == null || (nk.getThoiGian() != null && nk.getThoiGian().isBefore(denThoiDiem)))
                .filter(nk -> keyword == null || khopTuKhoa(nk, keyword))
                .map(this::toDto)
                .toList();
    }

    private boolean khopTuKhoa(NhatKyHoatDong nk, String keyword) {
        NguoiDung nd = nk.getNguoiDung();
        return chua(nk.getHanhDong(), keyword)
                || chua(nk.getDoiTuong(), keyword)
                || chua(nk.getDiaChiIP(), keyword)
                || chua(nd == null ? null : nd.getHoTen(), keyword)
                || chua(nd == null ? null : nd.getEmail(), keyword);
    }

    private boolean chua(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }

    private NhatKyHoatDongDto toDto(NhatKyHoatDong nk) {
        NguoiDung nd = nk.getNguoiDung();
        return new NhatKyHoatDongDto(
                nk.getMaNhatKy(),
                nk.getThoiGian(),
                nd == null ? null : nd.getMaNguoiDung(),
                nd == null ? "Hệ thống" : nd.getHoTen(),
                nd == null ? null : nd.getEmail(),
                nk.getHanhDong(),
                nk.getDoiTuong(),
                nk.getDiaChiIP());
    }
}
