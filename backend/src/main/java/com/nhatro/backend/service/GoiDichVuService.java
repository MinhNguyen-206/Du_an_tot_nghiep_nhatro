package com.nhatro.backend.service;

import com.nhatro.backend.entity.GoiDichVu;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GoiDichVuService {

    private final List<GoiDichVu> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public GoiDichVuService() {
        themDuLieuMau();
    }

    private void themDuLieuMau() {
        danhSach.add(GoiDichVu.builder()
                .maGoiDv(idCounter.getAndIncrement())
                .tenGoiDv("Gói Tiêu Chuẩn")
                .moTa("Gói dịch vụ cơ bản cho người thuê nhà trọ")
                .loaiGoiDv("STANDARD")
                .giaGoiDv(new BigDecimal("0")) // Miễn phí
                .thoiHanDays(0)
                .tinhNang("Xem phòng,Liên hệ chủ trọ,Gửi yêu cầu thuê")
                .mucUuTien(1)
                .trangThai(1)
                .soLanSuDung(5432)
                .ngayTao(LocalDateTime.now().minusDays(365))
                .ngayCapNhat(LocalDateTime.now())
                .build());

        danhSach.add(GoiDichVu.builder()
                .maGoiDv(idCounter.getAndIncrement())
                .tenGoiDv("Gói Premium")
                .moTa("Gói dịch vụ cao cấp - Ưu tiên liên hệ, xem thêm thông tin")
                .loaiGoiDv("PREMIUM")
                .giaGoiDv(new BigDecimal("49000"))
                .thoiHanDays(30)
                .tinhNang("Tất cả gói tiêu chuẩn + Ưu tiên liên hệ + Xem thêm ảnh + Lưu phòng yêu thích")
                .mucUuTien(3)
                .trangThai(1)
                .soLanSuDung(1234)
                .ngayTao(LocalDateTime.now().minusDays(180))
                .ngayCapNhat(LocalDateTime.now())
                .build());

        danhSach.add(GoiDichVu.builder()
                .maGoiDv(idCounter.getAndIncrement())
                .tenGoiDv("Gói VIP")
                .moTa("Gói dịch vụ VIP - Truy cập đầy đủ tất cả tính năng")
                .loaiGoiDv("VIP")
                .giaGoiDv(new BigDecimal("99000"))
                .thoiHanDays(30)
                .tinhNang("Tất cả gói Premium + Hỗ trợ 24/7 + Lịch sử gọi riêng + Xem lượt xem phòng")
                .mucUuTien(5)
                .trangThai(1)
                .soLanSuDung(423)
                .ngayTao(LocalDateTime.now().minusDays(90))
                .ngayCapNhat(LocalDateTime.now())
                .build());
    }

    public List<GoiDichVu> getAll() {
        return danhSach;
    }

    public Optional<GoiDichVu> getById(Integer id) {
        return danhSach.stream().filter(g -> g.getMaGoiDv().equals(id)).findFirst();
    }

    public GoiDichVu create(GoiDichVu goiDichVu) {
        goiDichVu.setMaGoiDv(idCounter.getAndIncrement());
        goiDichVu.setNgayTao(LocalDateTime.now());
        goiDichVu.setNgayCapNhat(LocalDateTime.now());
        goiDichVu.setSoLanSuDung(0);
        danhSach.add(goiDichVu);
        return goiDichVu;
    }

    public Optional<GoiDichVu> update(Integer id, GoiDichVu duLieuMoi) {
        return getById(id).map(g -> {
            g.setTenGoiDv(duLieuMoi.getTenGoiDv());
            g.setGiaGoiDv(duLieuMoi.getGiaGoiDv());
            g.setThoiHanDays(duLieuMoi.getThoiHanDays());
            g.setTinhNang(duLieuMoi.getTinhNang());
            g.setTrangThai(duLieuMoi.getTrangThai());
            g.setNgayCapNhat(LocalDateTime.now());
            return g;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(g -> g.getMaGoiDv().equals(id));
    }
}
