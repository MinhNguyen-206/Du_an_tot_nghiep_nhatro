package com.nhatro.backend.controller;

import com.nhatro.backend.entity.GoiPremium;
import com.nhatro.backend.service.GoiPremiumService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Gói Premium", description = "Quản lý các gói Premium dành cho chủ trọ")
@RestController
@RequestMapping("/api/goi-premium")
public class GoiPremiumController {

    private final GoiPremiumService goiPremiumService;

    public GoiPremiumController(GoiPremiumService goiPremiumService) {
        this.goiPremiumService = goiPremiumService;
    }

    @GetMapping
    public List<GoiPremium> getAll() {
        return goiPremiumService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoiPremium> getById(@PathVariable Integer id) {
        return goiPremiumService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GoiPremium> create(@RequestBody GoiPremium goiPremium) {
        return ResponseEntity.ok(goiPremiumService.create(goiPremium));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoiPremium> update(@PathVariable Integer id, @RequestBody GoiPremium goiPremium) {
        return goiPremiumService.update(id, goiPremium)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return goiPremiumService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
