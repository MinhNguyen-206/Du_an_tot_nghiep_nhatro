package com.nhatro.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.YeuCauThue;
import com.nhatro.backend.repository.NguoiDungRepository;
import com.nhatro.backend.service.YeuCauThueService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Yêu cầu thuê", description = "Quản lý yêu cầu thuê phòng")
@RestController
@RequestMapping("/api/yeu-cau-thue")
public class YeuCauThueController {

    private final YeuCauThueService yeuCauThueService;
    private final NguoiDungRepository nguoiDungRepository;

    public YeuCauThueController(YeuCauThueService yeuCauThueService, NguoiDungRepository nguoiDungRepository) {
        this.yeuCauThueService = yeuCauThueService;
        this.nguoiDungRepository = nguoiDungRepository;
    }

    private NguoiDung currentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return nguoiDungRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()) || "ADMIN".equals(a.getAuthority()));
    }

    @GetMapping("/chu-tro/{maChuTro}")
    public ResponseEntity<List<YeuCauThue>> getByChuTro(@PathVariable Integer maChuTro, Authentication authentication) {
        NguoiDung nguoiDungHienTai = currentUser(authentication);
        if (!isAdmin(authentication) && !nguoiDungHienTai.getMaNguoiDung().equals(maChuTro)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(yeuCauThueService.getByChuTro(maChuTro));
    }

    @PutMapping("/{id}/duyet")
    public ResponseEntity<YeuCauThue> duyet(@PathVariable Integer id, Authentication authentication) {
        NguoiDung nguoiDungHienTai = currentUser(authentication);
        YeuCauThue yc = yeuCauThueService.getById(id).orElse(null);
        if (yc == null) return ResponseEntity.notFound().build();
        kiemTraChuPhong(yc, nguoiDungHienTai, authentication);

        return yeuCauThueService.duyet(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/tu-choi")
    public ResponseEntity<YeuCauThue> tuChoi(@PathVariable Integer id, Authentication authentication) {
        NguoiDung nguoiDungHienTai = currentUser(authentication);
        YeuCauThue yc = yeuCauThueService.getById(id).orElse(null);
        if (yc == null) return ResponseEntity.notFound().build();
        kiemTraChuPhong(yc, nguoiDungHienTai, authentication);

        return yeuCauThueService.tuChoi(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private void kiemTraChuPhong(YeuCauThue yc, NguoiDung nguoiDungHienTai, Authentication authentication) {
        Integer maChuPhong = yc.getPhong().getNhaTro().getNguoiDung().getMaNguoiDung();
        if (!isAdmin(authentication) && !maChuPhong.equals(nguoiDungHienTai.getMaNguoiDung())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không phải chủ trọ của phòng này");
        }
    }

    @GetMapping
    public ResponseEntity<List<YeuCauThue>> getAll() {
        return ResponseEntity.ok(yeuCauThueService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<YeuCauThue> getById(@PathVariable Integer id) {
        return yeuCauThueService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nguoi-thue/{maNguoiDung}")
    public ResponseEntity<List<YeuCauThue>> getByNguoiThue(@PathVariable Integer maNguoiDung) {
        return ResponseEntity.ok(yeuCauThueService.getByNguoiThue(maNguoiDung));
    }

    @GetMapping("/phong/{maPhong}")
    public ResponseEntity<List<YeuCauThue>> getByPhong(@PathVariable Integer maPhong) {
        return ResponseEntity.ok(yeuCauThueService.getByPhong(maPhong));
    }

    @PostMapping
    public ResponseEntity<YeuCauThue> create(@RequestBody YeuCauThue yeuCauThue) {
        return ResponseEntity.ok(yeuCauThueService.create(yeuCauThue));
    }

    @PutMapping("/{id}")
    public ResponseEntity<YeuCauThue> update(@PathVariable Integer id, @RequestBody YeuCauThue duLieuMoi) {
        return yeuCauThueService.update(id, duLieuMoi)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return yeuCauThueService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}