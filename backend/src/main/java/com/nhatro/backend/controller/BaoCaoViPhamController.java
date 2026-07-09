package com.nhatro.backend.controller;

import com.nhatro.backend.entity.BaoCaoViPham;
import com.nhatro.backend.service.BaoCaoViPhamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bao-cao-vi-pham")
public class BaoCaoViPhamController {

    private final BaoCaoViPhamService baoCaoViPhamService;

    public BaoCaoViPhamController(BaoCaoViPhamService baoCaoViPhamService) {
        this.baoCaoViPhamService = baoCaoViPhamService;
    }

    @GetMapping
    public List<BaoCaoViPham> getAll() {
        return baoCaoViPhamService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaoCaoViPham> getById(@PathVariable Integer id) {
        return baoCaoViPhamService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BaoCaoViPham> create(@RequestBody BaoCaoViPham baoCao) {
        return ResponseEntity.ok(baoCaoViPhamService.create(baoCao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaoCaoViPham> update(@PathVariable Integer id, @RequestBody BaoCaoViPham baoCao) {
        return baoCaoViPhamService.update(id, baoCao)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return baoCaoViPhamService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}