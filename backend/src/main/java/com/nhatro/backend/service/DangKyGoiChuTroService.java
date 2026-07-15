package com.nhatro.backend.service;

import com.nhatro.backend.entity.DangKyGoiChuTro;
import com.nhatro.backend.entity.GoiPremium;
import com.nhatro.backend.entity.NguoiDung;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DangKyGoiChuTroService {

    private final List<DangKyGoiChuTro> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public DangKyGoiChuTroService(NguoiDungService nguoiDungService,
            GoiPremiumService goiPremiumService) {
        Optional<NguoiDung> chuTroMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 2)
                .findFirst();
        List<GoiPremium> dsGoi = goiPremiumService.getAll();

        if (chuTroMau.isPresent() && !dsGoi.isEmpty()) {
            GoiPremium goi = dsGoi.get(0);

            danhSach.add(DangKyGoiChuTro.builder()
                    .maDangKy(idCounter.getAndIncrement())
                    .nguoiDung(chuTroMau.get())
                    .goi(goi)
                    .ngayBatDau(LocalDateTime.now().minusDays(5))
                    .ngayKetThuc(LocalDateTime.now().minusDays(5).plusDays(goi.getThoiHanNgay()))
                    .trangThai(1) // dang hoat dong
                    .soTinDaDang(3)
                    .ngayTao(LocalDateTime.now().minusDays(5))
                    .build());
        }
    }

    public List<DangKyGoiChuTro> getAll() {
        return danhSach;
    }

    public Optional<DangKyGoiChuTro> getById(Integer id) {
        return danhSach.stream().filter(d -> d.getMaDangKy().equals(id)).findFirst();
    }

    public DangKyGoiChuTro create(DangKyGoiChuTro dangKy) {
        dangKy.setMaDangKy(idCounter.getAndIncrement());
        dangKy.setNgayTao(LocalDateTime.now());
        if (dangKy.getSoTinDaDang() == null) {
            dangKy.setSoTinDaDang(0);
        }
        danhSach.add(dangKy);
        return dangKy;
    }

    public Optional<DangKyGoiChuTro> update(Integer id, DangKyGoiChuTro duLieuMoi) {
        return getById(id).map(d -> {
            d.setNgayBatDau(duLieuMoi.getNgayBatDau());
            d.setNgayKetThuc(duLieuMoi.getNgayKetThuc());
            d.setTrangThai(duLieuMoi.getTrangThai());
            d.setSoTinDaDang(duLieuMoi.getSoTinDaDang());
            return d;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(d -> d.getMaDangKy().equals(id));
    }
}