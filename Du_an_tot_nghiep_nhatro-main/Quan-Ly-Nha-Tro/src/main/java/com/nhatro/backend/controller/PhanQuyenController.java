package com.nhatro.backend.controller;

import com.nhatro.backend.entity.PhanQuyen;
import com.nhatro.backend.service.PhanQuyenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Quyền hạn", description = "Quản lý vai trò và quyền hạn")
@RestController
@RequestMapping("/api/phan-quyen")
public class PhanQuyenController {

    private final PhanQuyenService phanQuyenService;

    public PhanQuyenController(PhanQuyenService phanQuyenService) {
        this.phanQuyenService = phanQuyenService;
    }

    @GetMapping
    public List<PhanQuyen> getAll() {
        return phanQuyenService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhanQuyen> getById(@PathVariable Integer id) {
        return phanQuyenService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PhanQuyen> create(@RequestBody PhanQuyen phanQuyen) {
        return ResponseEntity.ok(phanQuyenService.create(phanQuyen));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhanQuyen> update(@PathVariable Integer id, @RequestBody PhanQuyen phanQuyen) {
        return phanQuyenService.update(id, phanQuyen)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return phanQuyenService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
