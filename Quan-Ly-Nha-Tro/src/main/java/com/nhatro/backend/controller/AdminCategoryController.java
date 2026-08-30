package com.nhatro.backend.controller;

import com.nhatro.backend.entity.CauHinhDanhMuc;
import com.nhatro.backend.entity.TienIch;
import com.nhatro.backend.repository.CauHinhDanhMucRepository;
import com.nhatro.backend.repository.TienIchRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Tag(name = "Admin - Danh mục", description = "Quản lý tiện ích phòng và danh mục hệ thống")
@RestController
@RequestMapping("/api/admin/danh-muc")
public class AdminCategoryController {

    private final TienIchRepository tienIchRepo;
    private final CauHinhDanhMucRepository cauHinhRepo;

    public AdminCategoryController(TienIchRepository tienIchRepo,
                                   CauHinhDanhMucRepository cauHinhRepo) {
        this.tienIchRepo = tienIchRepo;
        this.cauHinhRepo = cauHinhRepo;
    }

    // ── TIEN ICH ──

    @Operation(summary = "Danh sach toan bo tien ich phong")
    @GetMapping("/tien-ich")
    public ResponseEntity<List<TienIch>> danhSachTienIch() {
        return ResponseEntity.ok(tienIchRepo.findAll());
    }

    @Operation(summary = "Them tien ich moi")
    @PostMapping("/tien-ich")
    public ResponseEntity<TienIch> themTienIch(@RequestBody TienIch tienIch) {
        if (tienIch.getTenTienIch() == null || tienIch.getTenTienIch().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        tienIch.setMaTienIch(null);
        return ResponseEntity.ok(tienIchRepo.save(tienIch));
    }

    @Operation(summary = "Cap nhat tien ich")
    @PutMapping("/tien-ich/{id}")
    public ResponseEntity<TienIch> suaTienIch(@PathVariable Integer id, @RequestBody TienIch body) {
        return tienIchRepo.findById(id).map(ti -> {
            ti.setTenTienIch(body.getTenTienIch());
            ti.setMoTa(body.getMoTa());
            return ResponseEntity.ok(tienIchRepo.save(ti));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Xoa tien ich")
    @DeleteMapping("/tien-ich/{id}")
    public ResponseEntity<Map<String, String>> xoaTienIch(@PathVariable Integer id) {
        if (!tienIchRepo.existsById(id)) {
            throw new NoSuchElementException("Không tìm thấy tiện ích #" + id);
        }
        tienIchRepo.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Đã xoá"));
    }

    // ── CAU HINH DANH MUC ──

    @Operation(summary = "Danh sach danh muc he thong")
    @GetMapping("/cau-hinh")
    public ResponseEntity<List<CauHinhDanhMuc>> danhSachDanhMuc() {
        return ResponseEntity.ok(cauHinhRepo.findAll());
    }

    @Operation(summary = "Them danh muc moi")
    @PostMapping("/cau-hinh")
    public ResponseEntity<CauHinhDanhMuc> themDanhMuc(@RequestBody CauHinhDanhMuc body) {
        if (body.getTenDanhMuc() == null || body.getTenDanhMuc().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        body.setMaDanhMuc(null);
        return ResponseEntity.ok(cauHinhRepo.save(body));
    }

    @Operation(summary = "Cap nhat danh muc")
    @PutMapping("/cau-hinh/{id}")
    public ResponseEntity<CauHinhDanhMuc> suaDanhMuc(@PathVariable Integer id, @RequestBody CauHinhDanhMuc body) {
        return cauHinhRepo.findById(id).map(dm -> {
            dm.setTenDanhMuc(body.getTenDanhMuc());
            dm.setMoTa(body.getMoTa());
            dm.setTrangThai(body.getTrangThai());
            return ResponseEntity.ok(cauHinhRepo.save(dm));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Toggle trang thai danh muc")
    @Transactional
    @PatchMapping("/cau-hinh/{id}/toggle")
    public ResponseEntity<CauHinhDanhMuc> toggleDanhMuc(@PathVariable Integer id) {
        return cauHinhRepo.findById(id).map(dm -> {
            dm.setTrangThai(!Boolean.TRUE.equals(dm.getTrangThai()));
            return ResponseEntity.ok(cauHinhRepo.save(dm));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Xoa danh muc")
    @DeleteMapping("/cau-hinh/{id}")
    public ResponseEntity<Map<String, String>> xoaDanhMuc(@PathVariable Integer id) {
        if (!cauHinhRepo.existsById(id)) {
            throw new NoSuchElementException("Không tìm thấy danh mục #" + id);
        }
        cauHinhRepo.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Đã xoá"));
    }
}
