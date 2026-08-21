package com.nhatro.backend.controller;

import com.nhatro.backend.entity.HoaDonThang;
import com.nhatro.backend.service.HoaDonThangService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Hoá đơn hàng tháng", description = "Quản lý hoá đơn hàng tháng")
@RestController
@RequestMapping("/api/hoa-don-thang")
public class HoaDonThangController {

    private final HoaDonThangService hoaDonThangService;

    public HoaDonThangController(HoaDonThangService hoaDonThangService) {
        this.hoaDonThangService = hoaDonThangService;
    }

    @GetMapping
    public List<HoaDonThang> getAll() {
        return hoaDonThangService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HoaDonThang> getById(@PathVariable Integer id) {
        return hoaDonThangService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HoaDonThang> create(@RequestBody HoaDonThang hoaDonThang) {
        return ResponseEntity.ok(hoaDonThangService.create(hoaDonThang));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HoaDonThang> update(@PathVariable Integer id, @RequestBody HoaDonThang hoaDonThang) {
        return hoaDonThangService.update(id, hoaDonThang)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return hoaDonThangService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}