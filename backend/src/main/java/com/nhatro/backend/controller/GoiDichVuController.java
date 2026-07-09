package com.nhatro.backend.controller;

import com.nhatro.backend.entity.GoiDichVu;
import com.nhatro.backend.service.GoiDichVuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goi-dich-vu")
public class GoiDichVuController {

    private final GoiDichVuService goiDichVuService;

    public GoiDichVuController(GoiDichVuService goiDichVuService) {
        this.goiDichVuService = goiDichVuService;
    }

    @GetMapping
    public List<GoiDichVu> getAll() {
        return goiDichVuService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoiDichVu> getById(@PathVariable Integer id) {
        return goiDichVuService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GoiDichVu> create(@RequestBody GoiDichVu goiDichVu) {
        return ResponseEntity.ok(goiDichVuService.create(goiDichVu));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoiDichVu> update(@PathVariable Integer id, @RequestBody GoiDichVu goiDichVu) {
        return goiDichVuService.update(id, goiDichVu)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return goiDichVuService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
