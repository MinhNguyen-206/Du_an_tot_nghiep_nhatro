package com.nhatro.backend.service;

import com.nhatro.backend.entity.DangKyGoiChuTro;
import com.nhatro.backend.entity.HoaDonDienTuPremium;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class HoaDonDienTuPremiumService {

    private final List<HoaDonDienTuPremium> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public HoaDonDienTuPremiumService(DangKyGoiChuTroService dangKyGoiChuTroService) {
        List<DangKyGoiChuTro> dsDangKy = dangKyGoiChuTroService.getAll();

        if (!dsDangKy.isEmpty()) {
            DangKyGoiChuTro dangKy = dsDangKy.get(0);

            danhSach.add(HoaDonDienTuPremium.builder()
                    .maHoaDon(idCounter.getAndIncrement())
                    .nguoiDung(dangKy.getNguoiDung())
                    .goi(dangKy.getGoi())
                    .soTien(dangKy.getGoi().getGiaTien())
                    .congThanhToan("MOMO")
                    .maGiaoDichNgoai("MOMO20260710PREM01")
                    .ngayThanhToan(dangKy.getNgayTao())
                    .trangThaiThanhToan(1) // da thanh toan
                    .duongDanFileHoaDon("/files/hoa-don-premium/hd-premium-1.pdf")
                    .loaiHoaDon(1) // dang ky moi
                    .build());
        }
    }

    public List<HoaDonDienTuPremium> getAll() {
        return danhSach;
    }

    public Optional<HoaDonDienTuPremium> getById(Integer id) {
        return danhSach.stream().filter(h -> h.getMaHoaDon().equals(id)).findFirst();
    }

    public HoaDonDienTuPremium create(HoaDonDienTuPremium hoaDon) {
        hoaDon.setMaHoaDon(idCounter.getAndIncrement());
        if (hoaDon.getTrangThaiThanhToan() == null) {
            hoaDon.setTrangThaiThanhToan(0); // cho thanh toan
        }
        danhSach.add(hoaDon);
        return hoaDon;
    }

    public Optional<HoaDonDienTuPremium> update(Integer id, HoaDonDienTuPremium duLieuMoi) {
        return getById(id).map(h -> {
            h.setCongThanhToan(duLieuMoi.getCongThanhToan());
            h.setMaGiaoDichNgoai(duLieuMoi.getMaGiaoDichNgoai());
            h.setNgayThanhToan(duLieuMoi.getNgayThanhToan());
            h.setTrangThaiThanhToan(duLieuMoi.getTrangThaiThanhToan());
            h.setDuongDanFileHoaDon(duLieuMoi.getDuongDanFileHoaDon());
            h.setLoaiHoaDon(duLieuMoi.getLoaiHoaDon());
            return h;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(h -> h.getMaHoaDon().equals(id));
    }
}
