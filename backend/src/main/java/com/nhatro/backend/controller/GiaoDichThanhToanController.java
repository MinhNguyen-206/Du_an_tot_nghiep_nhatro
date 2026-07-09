package com.nhatro.backend.controller;

import com.nhatro.backend.entity.GiaoDichThanhToan;
import com.nhatro.backend.service.GiaoDichThanhToanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/giao-dich-thanh-toan")
public class GiaoDichThanhToanController {

    private final GiaoDichThanhToanService giaoDichThanhToanService;

    public GiaoDichThanhToanController(GiaoDichThanhToanService giaoDichThanhToanService) {
        this.giaoDichThanhToanService = giaoDichThanhToanService;
    }

    @GetMapping
    public List<GiaoDichThanhToan> getAll() {
        return giaoDichThanhToanService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiaoDichThanhToan> getById(@PathVariable Integer id) {
        return giaoDichThanhToanService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GiaoDichThanhToan> create(@RequestBody GiaoDichThanhToan giaoDichThanhToan) {
        return ResponseEntity.ok(giaoDichThanhToanService.create(giaoDichThanhToan));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiaoDichThanhToan> update(@PathVariable Integer id,
            @RequestBody GiaoDichThanhToan giaoDichThanhToan) {
        return giaoDichThanhToanService.update(id, giaoDichThanhToan)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return giaoDichThanhToanService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
