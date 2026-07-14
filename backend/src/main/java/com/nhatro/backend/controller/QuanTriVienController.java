package com.nhatro.backend.controller;

import com.nhatro.backend.entity.QuanTriVien;
import com.nhatro.backend.service.QuanTriVienService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Quản trị viên", description = "Quản lý quản trị viên hệ thống")
@RestController
@RequestMapping("/api/quan-tri-vien")
public class QuanTriVienController {

    private final QuanTriVienService quanTriVienService;

    public QuanTriVienController(QuanTriVienService quanTriVienService) {
        this.quanTriVienService = quanTriVienService;
    }

    @GetMapping
    public List<QuanTriVien> getAll() {
        return quanTriVienService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuanTriVien> getById(@PathVariable Integer id) {
        return quanTriVienService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<QuanTriVien> create(@RequestBody QuanTriVien quanTriVien) {
        return ResponseEntity.ok(quanTriVienService.create(quanTriVien));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuanTriVien> update(@PathVariable Integer id, @RequestBody QuanTriVien quanTriVien) {
        return quanTriVienService.update(id, quanTriVien)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return quanTriVienService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
