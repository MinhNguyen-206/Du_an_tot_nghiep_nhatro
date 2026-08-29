package com.nhatro.backend.controller;

import com.nhatro.backend.dto.DangTinDuyetDto;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.service.AdminDangTinService;
import com.nhatro.backend.service.NguoiDungService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * API rieng cho man hinh "Duyet bai dang" cua Admin.
 * Quyen truy cap: chi ADMIN (xem SecurityConfig - matcher
 * /api/admin/dang-tin/**).
 */
@Tag(name = "Admin - Duyệt bài đăng", description = "Xem, duyệt, từ chối bài đăng cho thuê")
@RestController
@RequestMapping("/api/admin/dang-tin")
public class AdminDangTinController {

    private final AdminDangTinService adminDangTinService;
    private final NguoiDungService nguoiDungService;

    public AdminDangTinController(AdminDangTinService adminDangTinService, NguoiDungService nguoiDungService) {
        this.adminDangTinService = adminDangTinService;
        this.nguoiDungService = nguoiDungService;
    }

    @Operation(summary = "Danh sách bài đăng, lọc theo trạng thái duyệt / từ khóa")
    @GetMapping
    public ResponseEntity<List<DangTinDuyetDto>> danhSach(
            @RequestParam(value = "trangThaiDuyet", required = false) String trangThaiDuyet,
            @RequestParam(value = "q", required = false) String tuKhoa) {
        return ResponseEntity.ok(adminDangTinService.danhSach(trangThaiDuyet, tuKhoa));
    }

    @Operation(summary = "Xem chi tiết 1 bài đăng (kèm toàn bộ hình ảnh)")
    @GetMapping("/{id}")
    public ResponseEntity<DangTinDuyetDto> chiTiet(@PathVariable Integer id) {
        return adminDangTinService.chiTiet(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Duyệt bài đăng")
    @PutMapping("/{id}/duyet")
    public ResponseEntity<?> duyet(@PathVariable Integer id, Authentication authentication) {
        try {
            return ResponseEntity.ok(adminDangTinService.duyet(id, layMaNguoiDungHienTai(authentication)));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Từ chối bài đăng kèm lý do")
    @PutMapping("/{id}/tu-choi")
    public ResponseEntity<?> tuChoi(@PathVariable Integer id,
            @RequestBody(required = false) Map<String, String> body,
            Authentication authentication) {
        String lyDo = body != null ? body.get("lyDo") : null;
        try {
            return ResponseEntity.ok(adminDangTinService.tuChoi(id, lyDo, layMaNguoiDungHienTai(authentication)));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }

    @Operation(summary = "Đặt lại trạng thái chờ duyệt (xét duyệt lại)")
    @PutMapping("/{id}/cho-duyet-lai")
    public ResponseEntity<?> datLaiChoDuyet(@PathVariable Integer id, Authentication authentication) {
        try {
            return ResponseEntity.ok(adminDangTinService.datLaiChoDuyet(id, layMaNguoiDungHienTai(authentication)));
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
