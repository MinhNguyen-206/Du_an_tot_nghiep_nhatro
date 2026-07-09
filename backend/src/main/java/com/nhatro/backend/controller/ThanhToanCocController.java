package com.nhatro.backend.controller;

import com.nhatro.backend.entity.ThanhToanCoc;
import com.nhatro.backend.service.ThanhToanCocService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thanh-toan-coc")
public class ThanhToanCocController {

    private final ThanhToanCocService thanhToanCocService;

    public ThanhToanCocController(ThanhToanCocService thanhToanCocService) {
        this.thanhToanCocService = thanhToanCocService;
    }

    @GetMapping
    public List<ThanhToanCoc> getAll() {
        return thanhToanCocService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThanhToanCoc> getById(@PathVariable Integer id) {
        return thanhToanCocService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ThanhToanCoc> create(@RequestBody ThanhToanCoc thanhToanCoc) {
        return ResponseEntity.ok(thanhToanCocService.create(thanhToanCoc));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThanhToanCoc> update(@PathVariable Integer id, @RequestBody ThanhToanCoc thanhToanCoc) {
        return thanhToanCocService.update(id, thanhToanCoc)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return thanhToanCocService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}