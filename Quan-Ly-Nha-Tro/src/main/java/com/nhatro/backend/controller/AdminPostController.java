package com.nhatro.backend.controller;

import com.nhatro.backend.entity.DangTin;
import com.nhatro.backend.service.DangTinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST API duyet/tu choi bai dang - chi danh cho ADMIN.
 */
@Tag(name = "Admin - Bai dang", description = "Quan ly phe duyet bai dang tro")
@RestController
@RequestMapping("/api/admin/posts")
public class AdminPostController {

    private final DangTinService dangTinService;

    public AdminPostController(DangTinService dangTinService) {
        this.dangTinService = dangTinService;
    }

    @Operation(summary = "Danh sach bai dang co phan trang va loc trang thai")
    @GetMapping
    public ResponseEntity<Page<DangTin>> danhSach(
            @RequestParam(value = "trangThai", required = false) String trangThai,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DangTin> result = (trangThai == null || trangThai.isBlank())
                ? dangTinService.getAllPaged(pageable)
                : dangTinService.getByTrangThaiDuyetPaged(trangThai, pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Duyet bai dang")
    @PutMapping("/{id}/duyet")
    public ResponseEntity<?> duyet(@PathVariable Integer id) {
        return dangTinService.duyet(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Tu choi bai dang")
    @PutMapping("/{id}/tu-choi")
    public ResponseEntity<?> tuChoi(@PathVariable Integer id,
                                    @RequestBody(required = false) Map<String, String> body) {
        String lyDo = body != null ? body.get("lyDo") : null;
        return dangTinService.tuChoi(id, lyDo)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Xoa bai dang")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoa(@PathVariable Integer id) {
        boolean deleted = dangTinService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Dem bai dang theo trang thai")
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> count() {
        return ResponseEntity.ok(Map.of(
                "choDuyet", dangTinService.countByTrangThaiDuyet("CHO_DUYET"),
                "daDuyet",  dangTinService.countByTrangThaiDuyet("DA_DUYET"),
                "tuChoi",   dangTinService.countByTrangThaiDuyet("TU_CHOI"),
                "tatCa",    (long) dangTinService.getAll().size()
        ));
    }
}
