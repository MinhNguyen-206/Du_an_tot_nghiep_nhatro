package com.nhatro.backend.controller;

import com.nhatro.backend.entity.HoaDonPremium;
import com.nhatro.backend.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Tag(name = "Admin - Thong ke", description = "Thong ke tong quan he thong")
@RestController
@RequestMapping("/api/admin/thong-ke")
public class AdminThongKeController {

    private final NguoiDungService nguoiDungService;
    private final DangTinService dangTinService;
    private final PhongTroService phongTroService;
    private final NhaTroService nhaTroService;
    private final HoaDonPremiumService hoaDonPremiumService;
    private final BaoCaoService baoCaoService;
    private final XacThucEkycService ekycService;

    public AdminThongKeController(NguoiDungService nguoiDungService,
                                  DangTinService dangTinService,
                                  PhongTroService phongTroService,
                                  NhaTroService nhaTroService,
                                  HoaDonPremiumService hoaDonPremiumService,
                                  BaoCaoService baoCaoService,
                                  XacThucEkycService ekycService) {
        this.nguoiDungService = nguoiDungService;
        this.dangTinService = dangTinService;
        this.phongTroService = phongTroService;
        this.nhaTroService = nhaTroService;
        this.hoaDonPremiumService = hoaDonPremiumService;
        this.baoCaoService = baoCaoService;
        this.ekycService = ekycService;
    }

    @Operation(summary = "Thong ke tong quan: nguoi dung, bai dang, phong, doanh thu...")
    @Transactional(readOnly = true)
    @GetMapping("/tong-quan")
    public ResponseEntity<Map<String, Object>> tongQuan() {
        List<HoaDonPremium> invoices = hoaDonPremiumService.getAll();
        long tongDoanhThu = invoices.stream()
                .filter(h -> h.getSoTien() != null)
                .mapToLong(h -> h.getSoTien().longValue())
                .sum();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("tongNguoiDung",   nguoiDungService.getAll().size());
        data.put("tongBaiDang",     dangTinService.getAll().size());
        data.put("choDuyetBaiDang", dangTinService.countByTrangThaiDuyet("CHO_DUYET"));
        data.put("tongPhong",       phongTroService.getAll().size());
        data.put("tongNhaTro",      nhaTroService.count());
        data.put("tongBaoCao",      baoCaoService.getAll().size());
        data.put("choDuyetEkyc",    ekycService.countPending());
        data.put("tongDoanhThu",    tongDoanhThu);
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Doanh thu theo thang (6 thang gan nhat)")
    @Transactional(readOnly = true)
    @GetMapping("/doanh-thu")
    public ResponseEntity<List<Map<String, Object>>> doanhThu() {
        List<HoaDonPremium> invoices = hoaDonPremiumService.getAll();
        YearMonth current = YearMonth.now();
        Map<YearMonth, BigDecimal> byMonth = invoices.stream()
                .filter(h -> h.getNgayLap() != null && h.getSoTien() != null)
                .collect(Collectors.groupingBy(
                        h -> YearMonth.from(h.getNgayLap()),
                        Collectors.reducing(BigDecimal.ZERO, HoaDonPremium::getSoTien, BigDecimal::add)));

        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            YearMonth m = current.minusMonths(i);
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("thang", m.format(DateTimeFormatter.ofPattern("MM/yyyy")));
            row.put("doanhThu", byMonth.getOrDefault(m, BigDecimal.ZERO).longValue());
            result.add(row);
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Bai dang theo thang (6 thang gan nhat)")
    @Transactional(readOnly = true)
    @GetMapping("/bai-dang")
    public ResponseEntity<List<Map<String, Object>>> baiDang() {
        Map<YearMonth, Long> byMonth = dangTinService.getAll().stream()
                .filter(d -> d.getNgayDang() != null)
                .collect(Collectors.groupingBy(
                        d -> YearMonth.from(d.getNgayDang()),
                        Collectors.counting()));

        YearMonth current = YearMonth.now();
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            YearMonth m = current.minusMonths(i);
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("thang", m.format(DateTimeFormatter.ofPattern("MM/yyyy")));
            row.put("soBaiDang", byMonth.getOrDefault(m, 0L));
            result.add(row);
        }
        return ResponseEntity.ok(result);
    }
}
