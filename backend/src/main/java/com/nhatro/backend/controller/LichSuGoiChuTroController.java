package com.nhatro.backend.controller;

import com.nhatro.backend.entity.LichSuGoiChuTro;
import com.nhatro.backend.service.LichSuGoiChuTroService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Lịch sử gói chủ trọ", description = "Quản lý lịch sử đăng ký gói dịch vụ chủ trọ")
@RestController
@RequestMapping("/api/lich-su-goi-chu-tro")
public class LichSuGoiChuTroController {

    private final LichSuGoiChuTroService lichSuGoiChuTroService;

    public LichSuGoiChuTroController(LichSuGoiChuTroService lichSuGoiChuTroService) {
        this.lichSuGoiChuTroService = lichSuGoiChuTroService;
    }

    @GetMapping
    public List<LichSuGoiChuTro> getAll() {
        return lichSuGoiChuTroService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LichSuGoiChuTro> getById(@PathVariable Integer id) {
        return lichSuGoiChuTroService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LichSuGoiChuTro> create(@RequestBody LichSuGoiChuTro lichSuGoiChuTro) {
        return ResponseEntity.ok(lichSuGoiChuTroService.create(lichSuGoiChuTro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LichSuGoiChuTro> update(@PathVariable Integer id,
            @RequestBody LichSuGoiChuTro lichSuGoiChuTro) {
        return lichSuGoiChuTroService.update(id, lichSuGoiChuTro)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return lichSuGoiChuTroService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
