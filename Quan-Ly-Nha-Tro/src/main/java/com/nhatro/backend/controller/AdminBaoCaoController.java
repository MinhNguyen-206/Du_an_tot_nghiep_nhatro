package com.nhatro.backend.controller;

import com.nhatro.backend.entity.BaoCao;
import com.nhatro.backend.service.BaoCaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Admin - Bao cao", description = "Quan ly khieu nai va bao cao vi pham")
@RestController
@RequestMapping("/api/admin/bao-cao")
public class AdminBaoCaoController {

    private final BaoCaoService baoCaoService;

    public AdminBaoCaoController(BaoCaoService baoCaoService) {
        this.baoCaoService = baoCaoService;
    }

    @Operation(summary = "Danh sach bao cao co phan trang va loc trang thai")
    @GetMapping
    public ResponseEntity<Page<BaoCao>> danhSach(
            @RequestParam(value = "trangThai", required = false) String trangThai,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ngayBaoCao").descending());
        Page<BaoCao> result = (trangThai == null || trangThai.isBlank())
                ? baoCaoService.getAllPaged(pageable)
                : baoCaoService.getByTrangThaiPaged(trangThai, pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Chi tiet bao cao")
    @GetMapping("/{id}")
    public ResponseEntity<BaoCao> chiTiet(@PathVariable Integer id) {
        return baoCaoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cap nhat trang thai xu ly bao cao")
    @PutMapping("/{id}/trang-thai")
    public ResponseEntity<?> capNhatTrangThai(@PathVariable Integer id,
                                              @RequestBody Map<String, String> body) {
        String trangThai = body == null ? null : body.get("trangThai");
        if (trangThai == null || trangThai.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Thieu trang thai"));
        }
        return baoCaoService.capNhatTrangThai(id, trangThai)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Xoa bao cao")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoa(@PathVariable Integer id) {
        boolean deleted = baoCaoService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Dem bao cao theo trang thai")
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> count() {
        return ResponseEntity.ok(Map.of(
                "tatCa",       (long) baoCaoService.getAll().size(),
                "moiGui",      (long) baoCaoService.getByTrangThai("MOI").size(),
                "dangXuLy",    (long) baoCaoService.getByTrangThai("DANG_XU_LY").size(),
                "daGiaiQuyet", (long) baoCaoService.getByTrangThai("DA_GIAI_QUYET").size()
        ));
    }
}
