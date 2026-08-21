package com.nhatro.backend.controller;

import com.nhatro.backend.entity.BaoCao;
import com.nhatro.backend.service.BaoCaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Báo cáo", description = "Quản lý báo cáo người dùng/tin đăng")
@RestController
@RequestMapping("/api/bao-cao")
public class BaoCaoController {

    private final BaoCaoService baoCaoService;

    public BaoCaoController(BaoCaoService baoCaoService) {
        this.baoCaoService = baoCaoService;
    }

    @GetMapping
    public ResponseEntity<List<BaoCao>> getAll() {
        return ResponseEntity.ok(baoCaoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaoCao> getById(@PathVariable Integer id) {
        return baoCaoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nguoi-gui/{maNguoiDung}")
    public ResponseEntity<List<BaoCao>> getByNguoiGui(@PathVariable Integer maNguoiDung) {
        return ResponseEntity.ok(baoCaoService.getByNguoiGui(maNguoiDung));
    }

    @PostMapping
    public ResponseEntity<BaoCao> create(@RequestBody BaoCao baoCao) {
        return ResponseEntity.ok(baoCaoService.create(baoCao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaoCao> update(@PathVariable Integer id, @RequestBody BaoCao duLieuMoi) {
        return baoCaoService.update(id, duLieuMoi)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return baoCaoService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
