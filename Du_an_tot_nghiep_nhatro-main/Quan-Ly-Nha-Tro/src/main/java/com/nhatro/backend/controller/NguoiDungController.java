package com.nhatro.backend.controller;

import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.VaiTro;
import com.nhatro.backend.repository.VaiTroRepository;
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
    private final VaiTroRepository vaiTroRepository;

    // Ten vai tro mac dinh cho MOI tai khoan tu dang ky cong khai (xem create()
    // ben duoi). Phai khop voi du lieu trong bang VAI_TRO (qlnt_co_du_lieu_mau.sql).
    private static final String VAI_TRO_MAC_DINH = "Người thuê";
    private static final Integer VAI_TRO_MAC_DINH_ID_DU_PHONG = 3;

    public NguoiDungController(NguoiDungService nguoiDungService, VaiTroRepository vaiTroRepository) {
        this.nguoiDungService = nguoiDungService;
        this.vaiTroRepository = vaiTroRepository;
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

    // QUAN TRONG: endpoint nay la permitAll (xem SecurityConfig) vi dang duoc
    // FE dung tam lam "POST /api/nguoi-dung" cho man dang ky cong khai (xem
    // ghi chu trong api.js). Vi la endpoint public, KHONG duoc tin tuong
    // maVaiTro ma client gui len (truoc day cho phep client tu chon vai tro,
    // ke ca Admin - lo bao mat leo thang quyen). Tu nay MOI tai khoan tao qua
    // day deu bi ep cung ve vai tro "Người thuê" (khach hang), bat ke body
    // gui gi. Muon thanh Chu tro se phai di qua luong "dang ky chu tro" rieng
    // de Admin duyet - luong nay CHUA duoc trien khai (xem /dang-ky-chu-tro).
    @PostMapping
    public ResponseEntity<NguoiDung> create(@RequestBody NguoiDung nguoiDung) {
        nguoiDung.setVaiTro(layVaiTroMacDinh());
        return ResponseEntity.ok(nguoiDungService.create(nguoiDung));
    }

    private VaiTro layVaiTroMacDinh() {
        return vaiTroRepository.findByTenVaiTro(VAI_TRO_MAC_DINH)
                .orElseGet(() -> vaiTroRepository.findById(VAI_TRO_MAC_DINH_ID_DU_PHONG)
                        .orElseThrow(() -> new IllegalStateException(
                                "Khong tim thay vai tro mac dinh '" + VAI_TRO_MAC_DINH + "' trong bang VAI_TRO")));
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