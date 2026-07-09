package com.nhatro.backend.controller;

import com.nhatro.backend.entity.ThongBao;
import com.nhatro.backend.service.ThongBaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thong-bao")
public class ThongBaoController {

    private final ThongBaoService thongBaoService;

    public ThongBaoController(ThongBaoService thongBaoService) {
        this.thongBaoService = thongBaoService;
    }

    @GetMapping
    public List<ThongBao> getAll() {
        return thongBaoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThongBao> getById(@PathVariable Long id) {
        return thongBaoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ThongBao> create(@RequestBody ThongBao thongBao) {
        return ResponseEntity.ok(thongBaoService.create(thongBao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThongBao> update(@PathVariable Long id, @RequestBody ThongBao thongBao) {
        return thongBaoService.update(id, thongBao)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return thongBaoService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}