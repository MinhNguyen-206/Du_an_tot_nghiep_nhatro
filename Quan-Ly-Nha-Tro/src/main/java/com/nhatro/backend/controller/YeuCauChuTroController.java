package com.nhatro.backend.controller;

import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.YeuCauChuTro;
import com.nhatro.backend.service.NguoiDungService;
import com.nhatro.backend.service.YeuCauChuTroService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "Đăng ký Chủ trọ", description = "Nguoi thue gui yeu cau tro thanh Chu tro, Admin xet duyet")
@RestController
@RequestMapping("/api/yeu-cau-chu-tro")
public class YeuCauChuTroController {

    private final YeuCauChuTroService yeuCauChuTroService;
    private final NguoiDungService nguoiDungService;

    public YeuCauChuTroController(YeuCauChuTroService yeuCauChuTroService, NguoiDungService nguoiDungService) {
        this.yeuCauChuTroService = yeuCauChuTroService;
        this.nguoiDungService = nguoiDungService;
    }

    // Admin: xem tat ca yeu cau (hoac loc theo trangThai=CHO_DUYET)
    @GetMapping
    public ResponseEntity<List<YeuCauChuTro>> getAll(
            @RequestParam(value = "trangThai", required = false) String trangThai) {
        if ("CHO_DUYET".equalsIgnoreCase(trangThai)) {
            return ResponseEntity.ok(yeuCauChuTroService.getChoDuyet());
        }
        return ResponseEntity.ok(yeuCauChuTroService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<YeuCauChuTro> getById(@PathVariable Integer id) {
        return yeuCauChuTroService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Nguoi dung dang nhap xem lich su + trang thai yeu cau cua chinh minh.
    @GetMapping("/nguoi-dung/{maNguoiDung}")
    public ResponseEntity<List<YeuCauChuTro>> getByNguoiDung(@PathVariable Integer maNguoiDung,
                                                              Authentication authentication) {
        if (!isSelfOrAdmin(maNguoiDung, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(yeuCauChuTroService.getByNguoiDung(maNguoiDung));
    }

    // Yeu cau moi nhat cua chinh minh - dung de trang /dang-ky-chu-tro biet
    // nen hien form gui yeu cau hay hien trang thai "dang cho duyet/da bi tu choi".
    @GetMapping("/nguoi-dung/{maNguoiDung}/moi-nhat")
    public ResponseEntity<YeuCauChuTro> getMoiNhat(@PathVariable Integer maNguoiDung,
                                                    Authentication authentication) {
        if (!isSelfOrAdmin(maNguoiDung, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return yeuCauChuTroService.getMoiNhatByNguoiDung(maNguoiDung)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // Gui yeu cau dang ky Chu tro - nguoi dung dang nhap (bat ky vai tro nao
    // chua phai Chu tro) co the goi. maNguoiDung lay tu chinh JWT dang nhap,
    // KHONG tin tuong body de tranh gia mao gui ho nguoi khac.
    @PostMapping
    public ResponseEntity<?> guiYeuCau(@RequestBody YeuCauChuTro duLieu, Authentication authentication) {
        Optional<NguoiDung> nguoiDungOpt = nguoiDungService.getByEmail(authentication.getName());
        if (nguoiDungOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Không xác định được tài khoản đang đăng nhập."));
        }
        try {
            YeuCauChuTro daTao = yeuCauChuTroService.guiYeuCau(nguoiDungOpt.get().getMaNguoiDung(), duLieu);
            return ResponseEntity.ok(daTao);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }

    // Admin duyet yeu cau -> nang vai tro nguoi dung len Chu tro.
    @PutMapping("/{id}/duyet")
    public ResponseEntity<?> duyet(@PathVariable Integer id) {
        try {
            return yeuCauChuTroService.duyet(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }

    // Admin tu choi yeu cau, kem ly do (body: { "lyDo": "..." }).
    @PutMapping("/{id}/tu-choi")
    public ResponseEntity<?> tuChoi(@PathVariable Integer id, @RequestBody(required = false) Map<String, String> body) {
        String lyDo = body != null ? body.get("lyDo") : null;
        try {
            return yeuCauChuTroService.tuChoi(id, lyDo)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }

    private boolean isSelfOrAdmin(Integer maNguoiDung, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ADMIN"));
        if (isAdmin) {
            return true;
        }
        return nguoiDungService.getById(maNguoiDung)
                .map(nd -> nd.getEmail() != null && nd.getEmail().equalsIgnoreCase(authentication.getName()))
                .orElse(false);
    }
}
