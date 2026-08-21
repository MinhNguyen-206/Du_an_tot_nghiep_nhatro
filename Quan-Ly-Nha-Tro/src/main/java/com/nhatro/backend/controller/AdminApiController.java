package com.nhatro.backend.controller;

import com.nhatro.backend.dto.AdminDashboardResponse;
import com.nhatro.backend.entity.HoaDonPremium;
import com.nhatro.backend.entity.NhatKyHoatDong;
import com.nhatro.backend.service.BaoCaoService;
import com.nhatro.backend.service.DangTinService;
import com.nhatro.backend.service.GiaoDichThanhToanService;
import com.nhatro.backend.service.HoaDonPremiumService;
import com.nhatro.backend.service.HopDongDienTuService;
import com.nhatro.backend.service.LichHenService;
import com.nhatro.backend.service.NguoiDungService;
import com.nhatro.backend.service.NhaTroService;
import com.nhatro.backend.service.NhatKyHoatDongService;
import com.nhatro.backend.service.PhongTroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Tag(name = "Admin", description = "API quản trị hệ thống")
@RestController
@RequestMapping("/api/admin")
public class AdminApiController {

    private final NguoiDungService nguoiDungService;
    private final NhaTroService nhaTroService;
    private final PhongTroService phongTroService;
    private final DangTinService dangTinService;
    private final LichHenService lichHenService;
    private final HopDongDienTuService hopDongDienTuService;
    private final GiaoDichThanhToanService giaoDichThanhToanService;
    private final BaoCaoService baoCaoService;
    private final HoaDonPremiumService hoaDonPremiumService;
    private final NhatKyHoatDongService nhatKyHoatDongService;

    public AdminApiController(NguoiDungService nguoiDungService,
                              NhaTroService nhaTroService,
                              PhongTroService phongTroService,
                              DangTinService dangTinService,
                              LichHenService lichHenService,
                              HopDongDienTuService hopDongDienTuService,
                              GiaoDichThanhToanService giaoDichThanhToanService,
                              BaoCaoService baoCaoService,
                              HoaDonPremiumService hoaDonPremiumService,
                              NhatKyHoatDongService nhatKyHoatDongService) {
        this.nguoiDungService = nguoiDungService;
        this.nhaTroService = nhaTroService;
        this.phongTroService = phongTroService;
        this.dangTinService = dangTinService;
        this.lichHenService = lichHenService;
        this.hopDongDienTuService = hopDongDienTuService;
        this.giaoDichThanhToanService = giaoDichThanhToanService;
        this.baoCaoService = baoCaoService;
        this.hoaDonPremiumService = hoaDonPremiumService;
        this.nhatKyHoatDongService = nhatKyHoatDongService;
    }

    @Operation(summary = "Lấy thống kê tổng quan cho admin dashboard")
    @Transactional(readOnly = true)
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> getDashboard() {
        List<HoaDonPremium> invoices = hoaDonPremiumService.getAll();
        List<NhatKyHoatDong> logs = nhatKyHoatDongService.getAll();
        List<AdminDashboardResponse.MonthlyRevenue> monthlyRevenue = buildMonthlyRevenue(invoices);

        return ResponseEntity.ok(new AdminDashboardResponse(
                nguoiDungService.getAll().size(),
                nhaTroService.getAll().size(),
                phongTroService.getAll().size(),
                dangTinService.getAll().size(),
                lichHenService.getAll().size(),
                hopDongDienTuService.getAll().size(),
                giaoDichThanhToanService.getAll().size(),
                baoCaoService.getAll().size(),
                invoices.stream()
                    .map(HoaDonPremium::getSoTien)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .longValue(),
                nguoiDungService.getAll().size(),
                dangTinService.getAll().size(),
                baoCaoService.getAll().size(),
                monthlyRevenue,
                buildActivities(logs)));
    }

            private List<AdminDashboardResponse.MonthlyRevenue> buildMonthlyRevenue(List<HoaDonPremium> invoices) {
            YearMonth currentMonth = YearMonth.now();
            Map<YearMonth, BigDecimal> revenueByMonth = invoices.stream()
                .filter(invoice -> invoice.getNgayLap() != null)
                .collect(Collectors.groupingBy(
                    invoice -> YearMonth.from(invoice.getNgayLap()),
                    Collectors.reducing(BigDecimal.ZERO,
                        invoice -> invoice.getSoTien() == null ? BigDecimal.ZERO : invoice.getSoTien(),
                        BigDecimal::add)));

            return java.util.stream.IntStream.rangeClosed(0, 5)
                .mapToObj(offset -> currentMonth.minusMonths(5L - offset))
                .map(month -> new AdminDashboardResponse.MonthlyRevenue(
                    month.format(DateTimeFormatter.ofPattern("MM/yyyy")),
                    revenueByMonth.getOrDefault(month, BigDecimal.ZERO).longValue()))
                .toList();
            }

            private List<AdminDashboardResponse.DashboardActivity> buildActivities(List<NhatKyHoatDong> logs) {
            LocalDateTime now = LocalDateTime.now();
            return logs.stream()
                .filter(log -> log.getThoiGian() != null)
                .sorted(Comparator.comparing(NhatKyHoatDong::getThoiGian).reversed())
                .limit(4)
                .map(log -> new AdminDashboardResponse.DashboardActivity(
                    "fa-clock-rotate-left",
                    "green-bg",
                    log.getNguoiDung() == null || log.getNguoiDung().getHoTen() == null
                        ? "Hệ thống" : log.getNguoiDung().getHoTen(),
                    log.getHanhDong() == null ? "đã thực hiện một thao tác" : log.getHanhDong(),
                    formatRelativeTime(log.getThoiGian(), now),
                    log.getDoiTuong() == null ? "Hoạt động" : log.getDoiTuong(),
                    "success"))
                .toList();
            }

            private String formatRelativeTime(LocalDateTime time, LocalDateTime now) {
            long minutes = Math.max(0, Duration.between(time, now).toMinutes());
            if (minutes < 60) {
                return minutes + " phút trước";
            }
            long hours = minutes / 60;
            if (hours < 24) {
                return hours + " giờ trước";
            }
            return (hours / 24) + " ngày trước";
            }
}