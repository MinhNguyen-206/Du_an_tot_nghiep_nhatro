package com.nhatro.backend.controller;

import com.nhatro.backend.dto.NhatKyHoatDongDto;
import com.nhatro.backend.service.AdminNhatKyHoatDongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * API rieng cho man hinh "Nhat ky hoat dong" cua Admin. Chi doc.
 * Quyen truy cap: chi ADMIN (xem SecurityConfig - matcher /api/admin/nhat-ky/**).
 */
@Tag(name = "Admin - Nhật ký hoạt động", description = "Xem lại lịch sử thao tác của Admin trên hệ thống")
@RestController
@RequestMapping("/api/admin/nhat-ky")
public class AdminNhatKyHoatDongController {

    private final AdminNhatKyHoatDongService adminNhatKyHoatDongService;

    public AdminNhatKyHoatDongController(AdminNhatKyHoatDongService adminNhatKyHoatDongService) {
        this.adminNhatKyHoatDongService = adminNhatKyHoatDongService;
    }

    @Operation(summary = "Danh sách nhật ký hoạt động, lọc theo từ khóa / khoảng ngày")
    @GetMapping
    public ResponseEntity<List<NhatKyHoatDongDto>> danhSach(
            @RequestParam(value = "q", required = false) String tuKhoa,
            @RequestParam(value = "tuNgay", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tuNgay,
            @RequestParam(value = "denNgay", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate denNgay) {
        return ResponseEntity.ok(adminNhatKyHoatDongService.danhSach(tuKhoa, tuNgay, denNgay));
    }
}
