package com.nhatro.backend.controller;

import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.service.NguoiDungService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Người dùng", description = "Quản lý tài khoản người dùng")
@RestController
@RequestMapping("/api/nguoi-dung")
public class NguoiDungController {

    private final NguoiDungService nguoiDungService;

    public NguoiDungController(NguoiDungService nguoiDungService) {
        this.nguoiDungService = nguoiDungService;
    }

    @GetMapping
    public ResponseEntity<List<NguoiDung>> getAll() {
        return ResponseEntity.ok(nguoiDungService.getAll());
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

    // Cho phep nguoi dung da dang nhap tu cap nhat ho so cua CHINH MINH
    // (trang /profile), hoac ADMIN cap nhat ho so cua bat ky ai.
    // Xem SecurityConfig: PUT /api/nguoi-dung/** chi yeu cau authenticated(),
    // nen phai tu kiem tra quyen so huu o day de tranh 1 user sua du lieu cua user khac.
    @PutMapping("/{id}")
    public ResponseEntity<NguoiDung> update(@PathVariable Integer id,
                                             @RequestBody NguoiDung duLieuMoi,
                                             Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);

        if (!isAdmin) {
            String currentEmail = authentication.getName();
            boolean isSelf = nguoiDungService.getById(id)
                    .map(nd -> nd.getEmail() != null && nd.getEmail().equalsIgnoreCase(currentEmail))
                    .orElse(false);
            if (!isSelf) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        return nguoiDungService.update(id, duLieuMoi)
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