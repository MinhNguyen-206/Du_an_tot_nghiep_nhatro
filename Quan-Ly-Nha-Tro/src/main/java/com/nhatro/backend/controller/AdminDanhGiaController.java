package com.nhatro.backend.controller;

import com.nhatro.backend.dto.DanhGiaKiemDuyetDto;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.service.AdminDanhGiaService;
import com.nhatro.backend.service.NguoiDungService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * API rieng cho man hinh "Kiem duyet danh gia & binh luan" cua Admin.
 * Quyen truy cap: chi ADMIN (xem SecurityConfig - matcher /api/admin/danh-gia/**).
 */
@Tag(name = "Admin - Kiểm duyệt đánh giá", description = "Xem, ẩn, khôi phục, ẩn & cảnh cáo đánh giá của người dùng")
@RestController
@RequestMapping("/api/admin/danh-gia")
public class AdminDanhGiaController {

    private final AdminDanhGiaService adminDanhGiaService;
    private final NguoiDungService nguoiDungService;

    public AdminDanhGiaController(AdminDanhGiaService adminDanhGiaService, NguoiDungService nguoiDungService) {
        this.adminDanhGiaService = adminDanhGiaService;
        this.nguoiDungService = nguoiDungService;
    }

    @Operation(summary = "Danh sách đánh giá, lọc theo trạng thái kiểm duyệt / từ khóa")
    @GetMapping
    public ResponseEntity<List<DanhGiaKiemDuyetDto>> danhSach(
            @RequestParam(value = "trangThai", required = false) String trangThai,
            @RequestParam(value = "q", required = false) String tuKhoa) {
        return ResponseEntity.ok(adminDanhGiaService.danhSach(trangThai, tuKhoa));
    }

    @Operation(summary = "Khôi phục đánh giá về bình thường")
    @PutMapping("/{id}/khoi-phuc")
    public ResponseEntity<?> khoiPhuc(@PathVariable Integer id, Authentication authentication) {
        try {
            return ResponseEntity.ok(adminDanhGiaService.khoiPhuc(id, layMaNguoiDungHienTai(authentication)));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Ẩn đánh giá khỏi hiển thị công khai")
    @PutMapping("/{id}/an")
    public ResponseEntity<?> an(@PathVariable Integer id, Authentication authentication) {
        try {
            return ResponseEntity.ok(adminDanhGiaService.an(id, layMaNguoiDungHienTai(authentication)));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Ẩn đánh giá và ghi nhận 1 lượt vi phạm cho người viết")
    @PutMapping("/{id}/an-va-canh-cao")
    public ResponseEntity<?> anVaCanhCao(@PathVariable Integer id,
                                          @RequestBody(required = false) Map<String, String> body,
                                          Authentication authentication) {
        String lyDo = body != null ? body.get("lyDo") : null;
        try {
            return ResponseEntity.ok(adminDanhGiaService.anVaCanhCao(id, lyDo, layMaNguoiDungHienTai(authentication)));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    private Integer layMaNguoiDungHienTai(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        return nguoiDungService.getByEmail(authentication.getName())
                .map(NguoiDung::getMaNguoiDung)
                .orElse(null);
    }
}
