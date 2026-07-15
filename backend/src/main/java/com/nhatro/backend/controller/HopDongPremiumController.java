package com.nhatro.backend.controller;

import com.nhatro.backend.entity.HopDongPremium;
import com.nhatro.backend.service.HopDongPremiumService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Hợp đồng Premium", description = "Quản lý hợp đồng điện tử của gói chủ trọ Premium")
@RestController
@RequestMapping("/api/hop-dong-premium")
public class HopDongPremiumController {

    private final HopDongPremiumService hopDongPremiumService;

    public HopDongPremiumController(HopDongPremiumService hopDongPremiumService) {
        this.hopDongPremiumService = hopDongPremiumService;
    }

    @GetMapping
    public List<HopDongPremium> getAll() {
        return hopDongPremiumService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HopDongPremium> getById(@PathVariable Integer id) {
        return hopDongPremiumService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HopDongPremium> create(@RequestBody HopDongPremium hopDong) {
        return ResponseEntity.ok(hopDongPremiumService.create(hopDong));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HopDongPremium> update(@PathVariable Integer id, @RequestBody HopDongPremium hopDong) {
        return hopDongPremiumService.update(id, hopDong)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return hopDongPremiumService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
