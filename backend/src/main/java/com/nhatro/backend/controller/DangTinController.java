package com.nhatro.backend.controller;

import com.nhatro.backend.entity.DangTin;
import com.nhatro.backend.service.DangTinService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bài đăng cho thuê", description = "Quản lý bài đăng tin cho thuê")
@RestController
@RequestMapping("/api/dang-tin")
public class DangTinController {

    private final DangTinService dangTinService;

    public DangTinController(DangTinService dangTinService) {
        this.dangTinService = dangTinService;
    }

    @GetMapping
    public List<DangTin> getAll() {
        return dangTinService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DangTin> getById(@PathVariable Integer id) {
        return dangTinService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DangTin> create(@RequestBody DangTin dangTin) {
        return ResponseEntity.ok(dangTinService.create(dangTin));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DangTin> update(@PathVariable Integer id, @RequestBody DangTin dangTin) {
        return dangTinService.update(id, dangTin)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return dangTinService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}