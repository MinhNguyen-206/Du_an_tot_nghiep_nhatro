package com.nhatro.backend.controller;

import com.nhatro.backend.entity.TinNhan;
import com.nhatro.backend.service.TinNhanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tin nhắn", description = "Quản lý tin nhắn giữa người dùng")
@RestController
@RequestMapping("/api/tin-nhan")
public class TinNhanController {

    private final TinNhanService tinNhanService;

    public TinNhanController(TinNhanService tinNhanService) {
        this.tinNhanService = tinNhanService;
    }

    @GetMapping
    public List<TinNhan> getAll() {
        return tinNhanService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TinNhan> getById(@PathVariable Integer id) {
        return tinNhanService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/gui/{maNguoiDung}")
    public ResponseEntity<List<TinNhan>> getByNguoiGui(@PathVariable Integer maNguoiDung) {
        return ResponseEntity.ok(tinNhanService.getByNguoiGui(maNguoiDung));
    }

    @GetMapping("/nhan/{maNguoiDung}")
    public ResponseEntity<List<TinNhan>> getByNguoiNhan(@PathVariable Integer maNguoiDung) {
        return ResponseEntity.ok(tinNhanService.getByNguoiNhan(maNguoiDung));
    }

    @PostMapping
    public ResponseEntity<TinNhan> create(@RequestBody TinNhan tinNhan) {
        return ResponseEntity.ok(tinNhanService.create(tinNhan));
    }

    @PutMapping("/{id}/doc")
    public ResponseEntity<TinNhan> markAsRead(@PathVariable Integer id) {
        return tinNhanService.markAsRead(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return tinNhanService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}