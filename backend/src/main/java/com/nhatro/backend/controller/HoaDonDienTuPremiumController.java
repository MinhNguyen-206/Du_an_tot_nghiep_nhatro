package com.nhatro.backend.controller;

import com.nhatro.backend.entity.HoaDonDienTuPremium;
import com.nhatro.backend.service.HoaDonDienTuPremiumService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Hóa đơn điện tử Premium", description = "Quản lý hóa đơn thanh toán gói Premium của chủ trọ")
@RestController
@RequestMapping("/api/hoa-don-dien-tu-premium")
public class HoaDonDienTuPremiumController {

    private final HoaDonDienTuPremiumService hoaDonDienTuPremiumService;

    public HoaDonDienTuPremiumController(HoaDonDienTuPremiumService hoaDonDienTuPremiumService) {
        this.hoaDonDienTuPremiumService = hoaDonDienTuPremiumService;
    }

    @GetMapping
    public List<HoaDonDienTuPremium> getAll() {
        return hoaDonDienTuPremiumService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HoaDonDienTuPremium> getById(@PathVariable Integer id) {
        return hoaDonDienTuPremiumService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HoaDonDienTuPremium> create(@RequestBody HoaDonDienTuPremium hoaDon) {
        return ResponseEntity.ok(hoaDonDienTuPremiumService.create(hoaDon));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HoaDonDienTuPremium> update(@PathVariable Integer id, @RequestBody HoaDonDienTuPremium hoaDon) {
        return hoaDonDienTuPremiumService.update(id, hoaDon)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return hoaDonDienTuPremiumService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
