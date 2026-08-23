package com.nhatro.backend.controller;

import java.util.List;

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

import com.nhatro.backend.dto.PhongCardDto;
import com.nhatro.backend.dto.PhongChiTietDto;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.PhongTro;
import com.nhatro.backend.service.NguoiDungService;
import com.nhatro.backend.service.PhongCardService;
import com.nhatro.backend.service.PhongChiTietService;
import com.nhatro.backend.service.PhongTroService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Phòng trọ", description = "Quản lý phòng trọ")
@RestController
@RequestMapping("/api/phong-tro")
public class PhongTroController {

    private final PhongTroService phongTroService;
    private final PhongCardService phongCardService;
    private final PhongChiTietService phongChiTietService;
    private final NguoiDungService nguoiDungService;

    public PhongTroController(PhongTroService phongTroService,
                               PhongCardService phongCardService,
                               PhongChiTietService phongChiTietService,
                               NguoiDungService nguoiDungService) {
        this.phongTroService = phongTroService;
        this.phongCardService = phongCardService;
        this.phongChiTietService = phongChiTietService;
        this.nguoiDungService = nguoiDungService;
    }

    /*
     * ============================================================
     * CHI TIẾT PHÒNG (public - không cần login)
     *
     * URL: GET /api/phong-tro/{id}/chi-tiet
     *
     * Nếu request kèm JWT hợp lệ (Authorization header hoặc cookie "jwt",
     * xem JwtAuthenticationFilter) thì trả kèm daYeuThich của người dùng
     * đang đăng nhập. Việc GHI lịch sử xem phòng KHÔNG làm ở đây - JS
     * phía JSP tự gọi POST /api/profile/{userId}/viewed-rooms/{roomId}
     * để tránh insert 2 dòng lịch sử mỗi lần mở trang.
     * ============================================================
     */
    @GetMapping("/{id}/chi-tiet")
    public ResponseEntity<PhongChiTietDto> getChiTiet(@PathVariable Integer id, Authentication authentication) {
        Integer maNguoiDungDangXem = null;
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof String email) {
            maNguoiDungDangXem = nguoiDungService.getByEmail(email)
                    .map(NguoiDung::getMaNguoiDung)
                    .orElse(null);
        }
        return ResponseEntity.ok(phongChiTietService.layChiTiet(id, maNguoiDungDangXem));
    }

    @GetMapping
    public ResponseEntity<List<PhongTro>> getAll() {
        return ResponseEntity.ok(phongTroService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhongTro> getById(@PathVariable Integer id) {
        return phongTroService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/card")
    public ResponseEntity<PhongCardDto> getCard(@PathVariable Integer id) {
        return phongTroService.getById(id)
                .map(room -> ResponseEntity.ok(phongCardService.toCard(room, null)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nha-tro/{maNhaTro}")
    public ResponseEntity<List<PhongTro>> getByNhaTro(@PathVariable Integer maNhaTro) {
        return ResponseEntity.ok(phongTroService.getByNhaTro(maNhaTro));
    }

    @GetMapping("/trong")
    public ResponseEntity<List<PhongTro>> getPhongTrong() {
        return ResponseEntity.ok(phongTroService.getByTrangThai(true));
    }

    @PostMapping
    public ResponseEntity<PhongTro> create(@RequestBody PhongTro phongTro) {
        return ResponseEntity.ok(phongTroService.create(phongTro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhongTro> update(@PathVariable Integer id, @RequestBody PhongTro duLieuMoi) {
        return phongTroService.update(id, duLieuMoi)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return phongTroService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}