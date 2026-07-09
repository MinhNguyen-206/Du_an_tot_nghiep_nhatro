package com.nhatro.backend.controller;

import com.nhatro.backend.entity.HopDongDienTu;
import com.nhatro.backend.service.HopDongDienTuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hop-dong")
public class HopDongDienTuController {

    private final HopDongDienTuService hopDongDienTuService;

    public HopDongDienTuController(HopDongDienTuService hopDongDienTuService) {
        this.hopDongDienTuService = hopDongDienTuService;
    }

    @GetMapping
    public List<HopDongDienTu> getAll() {
        return hopDongDienTuService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HopDongDienTu> getById(@PathVariable Integer id) {
        return hopDongDienTuService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HopDongDienTu> create(@RequestBody HopDongDienTu hopDong) {
        return ResponseEntity.ok(hopDongDienTuService.create(hopDong));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HopDongDienTu> update(@PathVariable Integer id, @RequestBody HopDongDienTu hopDong) {
        return hopDongDienTuService.update(id, hopDong)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return hopDongDienTuService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}