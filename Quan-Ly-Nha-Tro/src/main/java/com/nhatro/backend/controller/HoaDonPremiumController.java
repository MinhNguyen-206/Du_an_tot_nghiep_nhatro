package com.nhatro.backend.controller;

import com.nhatro.backend.entity.HoaDonPremium;
import com.nhatro.backend.service.HoaDonPremiumService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Hóa đơn Premium", description = "Quản lý hóa đơn gói dịch vụ Premium")
@RestController
@RequestMapping("/api/hoa-don-premium")
public class HoaDonPremiumController {

    private final HoaDonPremiumService hoaDonPremiumService;

    public HoaDonPremiumController(HoaDonPremiumService hoaDonPremiumService) {
        this.hoaDonPremiumService = hoaDonPremiumService;
    }

    @GetMapping
    public ResponseEntity<List<HoaDonPremium>> getAll() {
        return ResponseEntity.ok(hoaDonPremiumService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HoaDonPremium> getById(@PathVariable Integer id) {
        return hoaDonPremiumService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/hop-dong/{maHopDong}")
    public ResponseEntity<List<HoaDonPremium>> getByHopDong(@PathVariable Integer maHopDong) {
        return ResponseEntity.ok(hoaDonPremiumService.getByHopDong(maHopDong));
    }

    @PostMapping
    public ResponseEntity<HoaDonPremium> create(@RequestBody HoaDonPremium hoaDon) {
        return ResponseEntity.ok(hoaDonPremiumService.create(hoaDon));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HoaDonPremium> update(@PathVariable Integer id, @RequestBody HoaDonPremium duLieuMoi) {
        return hoaDonPremiumService.update(id, duLieuMoi)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return hoaDonPremiumService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
