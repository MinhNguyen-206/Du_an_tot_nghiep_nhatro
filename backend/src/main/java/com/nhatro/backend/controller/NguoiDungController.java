package com.nhatro.backend.controller;

import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.service.NguoiDungService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nguoi-dung")
public class NguoiDungController {

    private final NguoiDungService nguoiDungService;

    public NguoiDungController(NguoiDungService nguoiDungService) {
        this.nguoiDungService = nguoiDungService;
    }

    @GetMapping
    public List<NguoiDung> getAll() {
        return nguoiDungService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NguoiDung> getById(@PathVariable Integer id) {
        return nguoiDungService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NguoiDung> create(@RequestBody NguoiDung nguoiDung) {
        return ResponseEntity.ok(nguoiDungService.create(nguoiDung));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NguoiDung> update(@PathVariable Integer id, @RequestBody NguoiDung nguoiDung) {
        return nguoiDungService.update(id, nguoiDung)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return nguoiDungService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}