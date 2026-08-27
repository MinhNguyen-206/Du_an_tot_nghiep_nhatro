package com.nhatro.backend.controller;

import com.nhatro.backend.dto.NguoiDungQuanLyDto;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.VaiTro;
import com.nhatro.backend.service.AdminNguoiDungService;
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
 * API rieng cho man hinh "Quan ly nguoi dung" cua Admin
 * (khac voi /api/nguoi-dung dung chung cho dang ky/profile va
 * /api/admin/management/users chi tra ve bang tom tat cho dashboard).
 *
 * Quyen truy cap: chi ADMIN (xem SecurityConfig - matcher /api/admin/nguoi-dung/**).
 */
@Tag(name = "Admin - Người dùng", description = "Quản trị tài khoản người dùng: danh sách, tạo mới, khóa/mở khóa, đổi vai trò, xóa")
@RestController
@RequestMapping("/api/admin/nguoi-dung")
public class AdminNguoiDungController {

    private final AdminNguoiDungService adminNguoiDungService;
    private final NguoiDungService nguoiDungService;

    public AdminNguoiDungController(AdminNguoiDungService adminNguoiDungService, NguoiDungService nguoiDungService) {
        this.adminNguoiDungService = adminNguoiDungService;
        this.nguoiDungService = nguoiDungService;
    }

    @Operation(summary = "Danh sách người dùng có lọc theo từ khóa / vai trò / trạng thái")
    @GetMapping
    public ResponseEntity<List<NguoiDungQuanLyDto>> danhSach(
            @RequestParam(value = "q", required = false) String tuKhoa,
            @RequestParam(value = "maVaiTro", required = false) Integer maVaiTro,
            @RequestParam(value = "trangThai", required = false) Boolean trangThai) {
        return ResponseEntity.ok(adminNguoiDungService.danhSach(tuKhoa, maVaiTro, trangThai));
    }

    @Operation(summary = "Danh sách vai trò để hiển thị bộ lọc / form thêm-sửa")
    @GetMapping("/vai-tro")
    public ResponseEntity<List<VaiTro>> danhSachVaiTro() {
        return ResponseEntity.ok(adminNguoiDungService.danhSachVaiTro());
    }

    @Operation(summary = "Xem chi tiết 1 người dùng (kèm trạng thái eKYC, số lượt vi phạm)")
    @GetMapping("/{id}")
    public ResponseEntity<NguoiDungQuanLyDto> chiTiet(@PathVariable Integer id) {
        return adminNguoiDungService.chiTiet(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Admin tạo tài khoản mới với vai trò tùy chọn")
    @PostMapping
    public ResponseEntity<?> taoTaiKhoan(@RequestBody NguoiDung duLieu, Authentication authentication) {
        try {
            NguoiDung daTao = adminNguoiDungService.taoTaiKhoan(duLieu, layMaNguoiDungHienTai(authentication));
            return ResponseEntity.status(HttpStatus.CREATED).body(daTao);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }

    @Operation(summary = "Khóa hoặc mở khóa tài khoản")
    @PutMapping("/{id}/trang-thai")
    public ResponseEntity<?> capNhatTrangThai(@PathVariable Integer id,
                                               @RequestBody Map<String, Boolean> body,
                                               Authentication authentication) {
        Boolean trangThai = body == null ? null : body.get("trangThai");
        if (trangThai == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Thiếu trường trangThai"));
        }
        try {
            NguoiDung daLuu = adminNguoiDungService.capNhatTrangThai(id, trangThai, layMaNguoiDungHienTai(authentication));
            return ResponseEntity.ok(daLuu);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", ex.getMessage()));
        }
    }

    @Operation(summary = "Đổi vai trò của một người dùng")
    @PutMapping("/{id}/vai-tro")
    public ResponseEntity<?> capNhatVaiTro(@PathVariable Integer id,
                                            @RequestBody Map<String, Integer> body,
                                            Authentication authentication) {
        Integer maVaiTro = body == null ? null : body.get("maVaiTro");
        if (maVaiTro == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Thiếu trường maVaiTro"));
        }
        try {
            NguoiDung daLuu = adminNguoiDungService.capNhatVaiTro(id, maVaiTro, layMaNguoiDungHienTai(authentication));
            return ResponseEntity.ok(daLuu);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", ex.getMessage()));
        }
    }

    @Operation(summary = "Xóa tài khoản người dùng")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoa(@PathVariable Integer id, Authentication authentication) {
        try {
            adminNguoiDungService.xoa(id, layMaNguoiDungHienTai(authentication));
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", ex.getMessage()));
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
