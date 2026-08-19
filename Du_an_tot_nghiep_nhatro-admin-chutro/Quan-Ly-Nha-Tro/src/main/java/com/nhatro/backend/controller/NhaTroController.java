package com.nhatro.backend.controller;

import com.nhatro.backend.entity.NhaTro;
import com.nhatro.backend.service.NhaTroService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Nhà trọ", description = "Quản lý thông tin nhà trọ")
@RestController
@RequestMapping("/api/nha-tro")
public class NhaTroController {

    private final NhaTroService nhaTroService;

    public NhaTroController(NhaTroService nhaTroService) {
        this.nhaTroService = nhaTroService;
    }

    @GetMapping
    public List<NhaTro> getAll() {
        return nhaTroService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhaTro> getById(@PathVariable Integer id) {
        return nhaTroService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NhaTro> create(@RequestBody NhaTro nhaTro) {
        return ResponseEntity.ok(nhaTroService.create(nhaTro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NhaTro> update(@PathVariable Integer id, @RequestBody NhaTro nhaTro) {
        return nhaTroService.update(id, nhaTro)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return nhaTroService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}