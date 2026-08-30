package com.nhatro.backend.controller;

import com.nhatro.backend.entity.DangKyGoiChuTro;
import com.nhatro.backend.entity.GoiDichVu;
import com.nhatro.backend.entity.HoaDonPremium;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.repository.DangKyGoiChuTroRepository;
import com.nhatro.backend.service.GoiDichVuService;
import com.nhatro.backend.service.HoaDonPremiumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Tag(name = "Admin - Premium", description = "Quan ly goi dich vu va hoa don premium")
@RestController
@RequestMapping("/api/admin/premium")
public class AdminPremiumController {

    private final GoiDichVuService goiDichVuService;
    private final HoaDonPremiumService hoaDonPremiumService;
    private final DangKyGoiChuTroRepository dangKyRepo;

    public AdminPremiumController(GoiDichVuService goiDichVuService,
                                  HoaDonPremiumService hoaDonPremiumService,
                                  DangKyGoiChuTroRepository dangKyRepo) {
        this.goiDichVuService = goiDichVuService;
        this.hoaDonPremiumService = hoaDonPremiumService;
        this.dangKyRepo = dangKyRepo;
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

    @Operation(summary = "Bat / tat goi dich vu")
    @PatchMapping("/goi/{id}/toggle")
    public ResponseEntity<?> toggleGoi(@PathVariable Integer id) {
        return goiDichVuService.getById(id).map(g -> {
            g.setTrangThai(!Boolean.TRUE.equals(g.getTrangThai()));
            return ResponseEntity.ok(goiDichVuService.save(g));
        }).<ResponseEntity<?>>map(r -> r).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Xoa goi dich vu")
    @DeleteMapping("/goi/{id}")
    public ResponseEntity<?> xoaGoi(@PathVariable Integer id) {
        boolean deleted = goiDichVuService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // ── DANG KY PREMIUM ──

    @Operation(summary = "Danh sach dang ky Premium (chi Chu tro) co phan trang va filter")
    @Transactional(readOnly = true)
    @GetMapping("/dang-ky")
    public ResponseEntity<Page<DangKyGoiChuTro>> danhSachDangKy(
            @RequestParam(value = "trangThai", required = false) String trangThai,
            @RequestParam(value = "q", required = false) String tuKhoa,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<DangKyGoiChuTro> all = dangKyRepo.findAll().stream()
                .sorted((a, b) -> {
                    if (a.getNgayDangKy() == null) return 1;
                    if (b.getNgayDangKy() == null) return -1;
                    return b.getNgayDangKy().compareTo(a.getNgayDangKy());
                })
                .filter(d -> trangThai == null || trangThai.isBlank() || trangThai.equals(d.getTrangThai()))
                .filter(d -> {
                    if (tuKhoa == null || tuKhoa.isBlank()) return true;
                    String kw = tuKhoa.toLowerCase();
                    String hoTen = d.getChuTro() != null && d.getChuTro().getHoTen() != null
                            ? d.getChuTro().getHoTen().toLowerCase() : "";
                    String tenGoi = d.getGoi() != null && d.getGoi().getTenGoi() != null
                            ? d.getGoi().getTenGoi().toLowerCase() : "";
                    return hoTen.contains(kw) || tenGoi.contains(kw);
                })
                .toList();
        int start = (int) pageable.getOffset();
        int end = Math.min(start + size, all.size());
        List<DangKyGoiChuTro> slice = start > all.size() ? List.of() : all.subList(start, end);
        return ResponseEntity.ok(new PageImpl<>(slice, pageable, all.size()));
    }

    @Operation(summary = "Gia han dang ky premium them 30 ngay")
    @PatchMapping("/dang-ky/{id}/gia-han")
    public ResponseEntity<?> giaHan(@PathVariable Integer id) {
        return dangKyRepo.findById(id).map(d -> {
            LocalDateTime hienTai = d.getNgayHetHan() != null && d.getNgayHetHan().isAfter(LocalDateTime.now())
                    ? d.getNgayHetHan() : LocalDateTime.now();
            d.setNgayHetHan(hienTai.plusDays(30));
            d.setTrangThai("HOAT_DONG");
            return ResponseEntity.ok(dangKyRepo.save(d));
        }).<ResponseEntity<?>>map(r -> r).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Huy dang ky premium")
    @PatchMapping("/dang-ky/{id}/huy")
    public ResponseEntity<?> huyDangKy(@PathVariable Integer id) {
        return dangKyRepo.findById(id).map(d -> {
            d.setTrangThai("DA_HUY");
            return ResponseEntity.ok(dangKyRepo.save(d));
        }).<ResponseEntity<?>>map(r -> r).orElse(ResponseEntity.notFound().build());
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

    // ── THONG KE ──

    @Operation(summary = "Thong ke premium tong hop")
    @Transactional(readOnly = true)
    @GetMapping("/thong-ke")
    public ResponseEntity<Map<String, Object>> thongKe() {
        List<GoiDichVu> gois = goiDichVuService.getAll();
        List<HoaDonPremium> hoaDons = hoaDonPremiumService.getAll();
        // Tat ca ban ghi trong DANG_KY_GOI_CHU_TRO deu la cua chu tro
        List<DangKyGoiChuTro> dangKys = dangKyRepo.findAll();
        long tongDoanhThu = hoaDons.stream()
                .filter(h -> h.getSoTien() != null)
                .mapToLong(h -> h.getSoTien().longValue())
                .sum();
        long dangHoatDong = dangKys.stream()
                .filter(d -> "HOAT_DONG".equals(d.getTrangThai())).count();
        long sinhHetHan = dangKys.stream()
                .filter(d -> d.getNgayHetHan() != null
                        && d.getNgayHetHan().isAfter(LocalDateTime.now())
                        && d.getNgayHetHan().isBefore(LocalDateTime.now().plusDays(7))).count();
        return ResponseEntity.ok(Map.of(
                "soGoi",        gois.size(),
                "tongDangKy",   dangKys.size(),
                "dangHoatDong", dangHoatDong,
                "sapHetHan",    sinhHetHan,
                "tongDoanhThu", tongDoanhThu
        ));
    }

    // ── HELPER ──

    /** Kiem tra nguoi dung co vai tro "Chu tro" hay khong */
    private boolean laChuTro(NguoiDung nd) {
        if (nd == null || nd.getVaiTro() == null) return false;
        return "Chủ trọ".equals(nd.getVaiTro().getTenVaiTro());
    }
}
