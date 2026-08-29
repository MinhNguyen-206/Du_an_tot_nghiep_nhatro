package com.nhatro.backend.controller;

import com.nhatro.backend.dto.GiaoDichThanhToanDto;
import com.nhatro.backend.dto.HoaDonChiTietDto;
import com.nhatro.backend.service.AdminGiaoDichService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * API rieng cho man hinh "Giao dich & hoa don" cua Admin. Chi doc.
 * Quyen truy cap: chi ADMIN (xem SecurityConfig - matcher /api/admin/giao-dich/**).
 */
@Tag(name = "Admin - Giao dịch & hóa đơn", description = "Theo dõi lịch sử giao dịch thanh toán và xem chi tiết hóa đơn")
@RestController
@RequestMapping("/api/admin/giao-dich")
public class AdminGiaoDichController {

    private final AdminGiaoDichService adminGiaoDichService;

    public AdminGiaoDichController(AdminGiaoDichService adminGiaoDichService) {
        this.adminGiaoDichService = adminGiaoDichService;
    }

    @Operation(summary = "Danh sách giao dịch, lọc theo trạng thái / từ khóa")
    @GetMapping
    public ResponseEntity<List<GiaoDichThanhToanDto>> danhSach(
            @RequestParam(value = "trangThai", required = false) String trangThai,
            @RequestParam(value = "q", required = false) String tuKhoa) {
        return ResponseEntity.ok(adminGiaoDichService.danhSach(trangThai, tuKhoa));
    }

    @Operation(summary = "Xem chi tiết hóa đơn gắn với 1 giao dịch (dùng để in / lưu PDF từ trình duyệt)")
    @GetMapping("/{maGiaoDich}/hoa-don")
    public ResponseEntity<?> chiTietHoaDon(@PathVariable Integer maGiaoDich) {
        try {
            return ResponseEntity.ok(adminGiaoDichService.chiTietHoaDon(maGiaoDich));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
