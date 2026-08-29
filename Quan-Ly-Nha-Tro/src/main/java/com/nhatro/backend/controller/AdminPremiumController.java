package com.nhatro.backend.controller;

import com.nhatro.backend.entity.GoiDichVu;
import com.nhatro.backend.entity.HoaDonPremium;
import com.nhatro.backend.service.GoiDichVuService;
import com.nhatro.backend.service.HoaDonPremiumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Admin - Premium", description = "Quan ly goi dich vu va hoa don premium")
@RestController
@RequestMapping("/api/admin/premium")
public class AdminPremiumController {

    private final GoiDichVuService goiDichVuService;
    private final HoaDonPremiumService hoaDonPremiumService;

    public AdminPremiumController(GoiDichVuService goiDichVuService,
                                  HoaDonPremiumService hoaDonPremiumService) {
        this.goiDichVuService = goiDichVuService;
        this.hoaDonPremiumService = hoaDonPremiumService;
    }

    // ── GOI DICH VU ──

    @Operation(summary = "Danh sach goi dich vu")
    @GetMapping("/goi")
    public ResponseEntity<List<GoiDichVu>> danhSachGoi() {
        return ResponseEntity.ok(goiDichVuService.getAll());
    }

    @Operation(summary = "Tao goi dich vu moi")
    @PostMapping("/goi")
    public ResponseEntity<?> taoGoi(@RequestBody GoiDichVu goi) {
        GoiDichVu daTao = goiDichVuService.create(goi);
        return ResponseEntity.status(HttpStatus.CREATED).body(daTao);
    }

    @Operation(summary = "Cap nhat goi dich vu")
    @PutMapping("/goi/{id}")
    public ResponseEntity<?> capNhatGoi(@PathVariable Integer id,
                                        @RequestBody GoiDichVu duLieuMoi) {
        return goiDichVuService.update(id, duLieuMoi)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Xoa goi dich vu")
    @DeleteMapping("/goi/{id}")
    public ResponseEntity<?> xoaGoi(@PathVariable Integer id) {
        boolean deleted = goiDichVuService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // ── HOA DON PREMIUM ──

    @Operation(summary = "Danh sach hoa don premium co phan trang")
    @GetMapping("/hoa-don")
    public ResponseEntity<Page<HoaDonPremium>> danhSachHoaDon(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(hoaDonPremiumService.getAllSorted(pageable));
    }

    @Operation(summary = "Thong ke premium: so tai khoan, doanh thu")
    @GetMapping("/thong-ke")
    public ResponseEntity<Map<String, Object>> thongKe() {
        List<GoiDichVu> gois = goiDichVuService.getAll();
        List<HoaDonPremium> hoaDons = hoaDonPremiumService.getAll();
        long tongDoanhThu = hoaDons.stream()
                .filter(h -> h.getSoTien() != null)
                .mapToLong(h -> h.getSoTien().longValue())
                .sum();
        return ResponseEntity.ok(Map.of(
                "soGoi",       gois.size(),
                "tongHoaDon",  hoaDons.size(),
                "tongDoanhThu", tongDoanhThu
        ));
    }
}
