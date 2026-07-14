package com.nhatro.backend.controller;

import com.nhatro.backend.entity.ChiSoDienNuoc;
import com.nhatro.backend.service.ChiSoDienNuocService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Chỉ số", description = "Quản lý chỉ số điện nước")
@RestController
@RequestMapping("/api/chi-so-dien-nuoc")
public class ChiSoDienNuocController {

    private final ChiSoDienNuocService chiSoDienNuocService;

    public ChiSoDienNuocController(ChiSoDienNuocService chiSoDienNuocService) {
        this.chiSoDienNuocService = chiSoDienNuocService;
    }

    @GetMapping
    public List<ChiSoDienNuoc> getAll() {
        return chiSoDienNuocService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiSoDienNuoc> getById(@PathVariable Integer id) {
        return chiSoDienNuocService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ChiSoDienNuoc> create(@RequestBody ChiSoDienNuoc chiSo) {
        return ResponseEntity.ok(chiSoDienNuocService.create(chiSo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChiSoDienNuoc> update(@PathVariable Integer id, @RequestBody ChiSoDienNuoc chiSo) {
        return chiSoDienNuocService.update(id, chiSo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return chiSoDienNuocService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}