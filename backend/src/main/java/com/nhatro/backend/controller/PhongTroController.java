package com.nhatro.backend.controller;

import com.nhatro.backend.entity.PhongTro;
import com.nhatro.backend.service.PhongTroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phong-tro")
public class PhongTroController {

    private final PhongTroService phongTroService;

    public PhongTroController(PhongTroService phongTroService) {
        this.phongTroService = phongTroService;
    }

    @GetMapping
    public List<PhongTro> getAll() {
        return phongTroService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhongTro> getById(@PathVariable Integer id) {
        return phongTroService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PhongTro> create(@RequestBody PhongTro phongTro) {
        return ResponseEntity.ok(phongTroService.create(phongTro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhongTro> update(@PathVariable Integer id, @RequestBody PhongTro phongTro) {
        return phongTroService.update(id, phongTro)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return phongTroService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}