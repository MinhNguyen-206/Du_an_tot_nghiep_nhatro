package com.nhatro.backend.controller;

import com.nhatro.backend.entity.CauHinhDanhMuc;
import com.nhatro.backend.service.CauHinhDanhMucService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cấu hình danh mục", description = "Quản lý cấu hình và danh mục hệ thống")
@RestController
@RequestMapping("/api/cau-hinh-danh-muc")
public class CauHinhDanhMucController {

    private final CauHinhDanhMucService cauHinhDanhMucService;

    public CauHinhDanhMucController(CauHinhDanhMucService cauHinhDanhMucService) {
        this.cauHinhDanhMucService = cauHinhDanhMucService;
    }

    @GetMapping
    public List<CauHinhDanhMuc> getAll() {
        return cauHinhDanhMucService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CauHinhDanhMuc> getById(@PathVariable Integer id) {
        return cauHinhDanhMucService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CauHinhDanhMuc> create(@RequestBody CauHinhDanhMuc cauHinhDanhMuc) {
        return ResponseEntity.ok(cauHinhDanhMucService.create(cauHinhDanhMuc));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CauHinhDanhMuc> update(@PathVariable Integer id, @RequestBody CauHinhDanhMuc cauHinhDanhMuc) {
        return cauHinhDanhMucService.update(id, cauHinhDanhMuc)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return cauHinhDanhMucService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
