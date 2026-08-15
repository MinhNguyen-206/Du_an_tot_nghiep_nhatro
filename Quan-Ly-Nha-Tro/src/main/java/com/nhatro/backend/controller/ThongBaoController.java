package com.nhatro.backend.controller;

import com.nhatro.backend.entity.ThongBao;
import com.nhatro.backend.service.ThongBaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Thông báo", description = "Quản lý thông báo hệ thống")
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
    public ResponseEntity<ThongBao> getById(@PathVariable Integer id) {
        return thongBaoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nguoi-dung/{maNguoiDung}")
    public ResponseEntity<List<ThongBao>> getByNguoiDung(@PathVariable Integer maNguoiDung) {
        return ResponseEntity.ok(thongBaoService.getByNguoiDung(maNguoiDung));
    }

    @GetMapping("/nguoi-dung/{maNguoiDung}/chua-doc")
    public ResponseEntity<List<ThongBao>> getChuaDoc(@PathVariable Integer maNguoiDung) {
        return ResponseEntity.ok(thongBaoService.getChuaDoc(maNguoiDung));
    }

    @PostMapping
    public ResponseEntity<ThongBao> create(@RequestBody ThongBao thongBao) {
        return ResponseEntity.ok(thongBaoService.create(thongBao));
    }

    @PutMapping("/{id}/doc")
    public ResponseEntity<ThongBao> markAsRead(@PathVariable Integer id) {
        return thongBaoService.markAsRead(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return thongBaoService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}