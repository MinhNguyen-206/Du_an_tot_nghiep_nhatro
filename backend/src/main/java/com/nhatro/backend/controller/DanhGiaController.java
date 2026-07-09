package com.nhatro.backend.controller;

import com.nhatro.backend.entity.DanhGia;
import com.nhatro.backend.service.DanhGiaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danh-gia")
public class DanhGiaController {

    private final DanhGiaService danhGiaService;

    public DanhGiaController(DanhGiaService danhGiaService) {
        this.danhGiaService = danhGiaService;
    }

    @GetMapping
    public List<DanhGia> getAll() {
        return danhGiaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DanhGia> getById(@PathVariable Integer id) {
        return danhGiaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DanhGia> create(@RequestBody DanhGia danhGia) {
        return ResponseEntity.ok(danhGiaService.create(danhGia));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DanhGia> update(@PathVariable Integer id, @RequestBody DanhGia danhGia) {
        return danhGiaService.update(id, danhGia)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return danhGiaService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}