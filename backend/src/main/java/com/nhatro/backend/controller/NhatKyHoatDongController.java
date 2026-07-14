package com.nhatro.backend.controller;

import com.nhatro.backend.entity.NhatKyHoatDong;
import com.nhatro.backend.service.NhatKyHoatDongService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Nhật ký hoạt động", description = "Quản lý nhật ký hoạt động của người dùng")
@RestController
@RequestMapping("/api/nhat-ky-hoat-dong")
public class NhatKyHoatDongController {

    private final NhatKyHoatDongService nhatKyHoatDongService;

    public NhatKyHoatDongController(NhatKyHoatDongService nhatKyHoatDongService) {
        this.nhatKyHoatDongService = nhatKyHoatDongService;
    }

    @GetMapping
    public List<NhatKyHoatDong> getAll() {
        return nhatKyHoatDongService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhatKyHoatDong> getById(@PathVariable Integer id) {
        return nhatKyHoatDongService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NhatKyHoatDong> create(@RequestBody NhatKyHoatDong nhatKyHoatDong) {
        return ResponseEntity.ok(nhatKyHoatDongService.create(nhatKyHoatDong));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NhatKyHoatDong> update(@PathVariable Integer id, @RequestBody NhatKyHoatDong nhatKyHoatDong) {
        return nhatKyHoatDongService.update(id, nhatKyHoatDong)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return nhatKyHoatDongService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
