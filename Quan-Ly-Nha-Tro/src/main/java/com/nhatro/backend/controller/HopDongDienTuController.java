package com.nhatro.backend.controller;

import com.nhatro.backend.entity.HopDongDienTu;
import com.nhatro.backend.service.HopDongDienTuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.nhatro.backend.repository.NguoiDungRepository;

import java.util.List;

@Tag(name = "Hợp đồng điện tử", description = "Quản lý hợp đồng thuê nhà")
@RestController
@RequestMapping("/api/hop-dong")
public class HopDongDienTuController {

    private final HopDongDienTuService hopDongDienTuService;
    private final NguoiDungRepository nguoiDungRepository;

    public HopDongDienTuController(HopDongDienTuService hopDongDienTuService, NguoiDungRepository nguoiDungRepository) {
        this.hopDongDienTuService = hopDongDienTuService;
        this.nguoiDungRepository = nguoiDungRepository;
    }

    @GetMapping
    public List<HopDongDienTu> getAll() {
        return hopDongDienTuService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HopDongDienTu> getById(@PathVariable Integer id) {
        return hopDongDienTuService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nguoi-thue/{userId}")
    public ResponseEntity<List<HopDongDienTu>> getByNguoiThue(@PathVariable Integer userId, Authentication authentication) {
        checkSelf(userId, authentication);
        return ResponseEntity.ok(hopDongDienTuService.getByNguoiThue(userId));
    }

    @GetMapping("/chu-tro/{userId}")
    public ResponseEntity<List<HopDongDienTu>> getByChuTro(@PathVariable Integer userId, Authentication authentication) {
        checkSelf(userId, authentication);
        return ResponseEntity.ok(hopDongDienTuService.getByChuTro(userId));
    }

    private void checkSelf(Integer userId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.UNAUTHORIZED);
        }
        boolean admin = authentication.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()) || "ADMIN".equals(a.getAuthority()));
        if (!admin && nguoiDungRepository.findById(userId).map(u -> u.getEmail() == null || !u.getEmail().equalsIgnoreCase(authentication.getName())).orElse(true)) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping
    public ResponseEntity<HopDongDienTu> create(@RequestBody HopDongDienTu hopDong) {
        return ResponseEntity.ok(hopDongDienTuService.create(hopDong));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HopDongDienTu> update(@PathVariable Integer id, @RequestBody HopDongDienTu hopDong) {
        return hopDongDienTuService.update(id, hopDong)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return hopDongDienTuService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}