package com.nhatro.backend.controller;

import com.nhatro.backend.entity.DangKyGoiChuTro;
import com.nhatro.backend.service.DangKyGoiChuTroService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Đăng ký gói chủ trọ", description = "Quản lý việc chủ trọ đăng ký/gia hạn/hủy gói Premium")
@RestController
@RequestMapping("/api/dang-ky-goi-chu-tro")
public class DangKyGoiChuTroController {

    private final DangKyGoiChuTroService dangKyGoiChuTroService;

    public DangKyGoiChuTroController(DangKyGoiChuTroService dangKyGoiChuTroService) {
        this.dangKyGoiChuTroService = dangKyGoiChuTroService;
    }

    @GetMapping
    public List<DangKyGoiChuTro> getAll() {
        return dangKyGoiChuTroService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DangKyGoiChuTro> getById(@PathVariable Integer id) {
        return dangKyGoiChuTroService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DangKyGoiChuTro> create(@RequestBody DangKyGoiChuTro dangKy) {
        return ResponseEntity.ok(dangKyGoiChuTroService.create(dangKy));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DangKyGoiChuTro> update(@PathVariable Integer id, @RequestBody DangKyGoiChuTro dangKy) {
        return dangKyGoiChuTroService.update(id, dangKy)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return dangKyGoiChuTroService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
