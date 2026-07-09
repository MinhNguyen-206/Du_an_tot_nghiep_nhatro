package com.nhatro.backend.service;

import com.nhatro.backend.entity.GiaoDichThanhToan;
import com.nhatro.backend.entity.HoaDonThang;
import com.nhatro.backend.entity.NguoiDung;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GiaoDichThanhToanService {

    private final List<GiaoDichThanhToan> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public GiaoDichThanhToanService(HoaDonThangService hoaDonThangService, NguoiDungService nguoiDungService) {
        List<HoaDonThang> dsHoaDon = hoaDonThangService.getAll();
        Optional<NguoiDung> nguoiThueMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 1)
                .findFirst();

        if (!dsHoaDon.isEmpty() && nguoiThueMau.isPresent()) {
            HoaDonThang hoaDon = dsHoaDon.get(0);

            danhSach.add(GiaoDichThanhToan.builder()
                    .maGiaoDich(idCounter.getAndIncrement())
                    .hoaDon(hoaDon)
                    .nguoiThanhToan(nguoiThueMau.get())
                    .soTien(hoaDon.getTongTien())
                    .phuongThucThanhToan("CHUYEN_KHOAN")
                    .maThiamChieu("TXN20260710001")
                    .trangThaiThanhToan(1) // Đã xác nhận
                    .ghiChu("Thanh toán hóa đơn tháng 7/2026 qua chuyển khoản")
                    .thoiGianTao(LocalDateTime.now().minusDays(5))
                    .thoiGianXacNhan(LocalDateTime.now().minusDays(5).plusHours(2))
                    .build());

            danhSach.add(GiaoDichThanhToan.builder()
                    .maGiaoDich(idCounter.getAndIncrement())
                    .hoaDon(hoaDon)
                    .nguoiThanhToan(nguoiThueMau.get())
                    .soTien(new BigDecimal("500000")) // Thanh toán một phần
                    .phuongThucThanhToan("MOMO")
                    .maThiamChieu("MOMO20260710002")
                    .trangThaiThanhToan(1)
                    .ghiChu("Thanh toán một phần qua Momo")
                    .thoiGianTao(LocalDateTime.now().minusHours(12))
                    .thoiGianXacNhan(LocalDateTime.now().minusHours(11))
                    .build());
        }
    }

    public List<GiaoDichThanhToan> getAll() {
        return danhSach;
    }

    public Optional<GiaoDichThanhToan> getById(Integer id) {
        return danhSach.stream().filter(g -> g.getMaGiaoDich().equals(id)).findFirst();
    }

    public GiaoDichThanhToan create(GiaoDichThanhToan giaoDichThanhToan) {
        giaoDichThanhToan.setMaGiaoDich(idCounter.getAndIncrement());
        giaoDichThanhToan.setThoiGianTao(LocalDateTime.now());
        if (giaoDichThanhToan.getTrangThaiThanhToan() == null) {
            giaoDichThanhToan.setTrangThaiThanhToan(0); // Chưa xác nhận
        }
        danhSach.add(giaoDichThanhToan);
        return giaoDichThanhToan;
    }

    public Optional<GiaoDichThanhToan> update(Integer id, GiaoDichThanhToan duLieuMoi) {
        return getById(id).map(g -> {
            g.setTrangThaiThanhToan(duLieuMoi.getTrangThaiThanhToan());
            g.setGhiChu(duLieuMoi.getGhiChu());
            if (duLieuMoi.getTrangThaiThanhToan() == 1) {
                g.setThoiGianXacNhan(LocalDateTime.now());
            }
            return g;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(g -> g.getMaGiaoDich().equals(id));
    }
}
