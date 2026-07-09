package com.nhatro.backend.service;

import com.nhatro.backend.entity.ChiSoDienNuoc;
import com.nhatro.backend.entity.HoaDonThang;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ChiSoDienNuocService {

    private final List<ChiSoDienNuoc> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public ChiSoDienNuocService(HoaDonThangService hoaDonThangService) {
        Optional<HoaDonThang> hoaDonMau = hoaDonThangService.getAll().stream().findFirst();

        hoaDonMau.ifPresent(hoaDon -> danhSach.add(ChiSoDienNuoc.builder()
                .maChiSo(idCounter.getAndIncrement())
                .hoaDon(hoaDon)
                .soDienCu(100.0)
                .soDienMoi(150.0)
                .soNuocCu(20.0)
                .soNuocMoi(25.0)
                .tienDien(new BigDecimal("175000"))
                .tienNuoc(new BigDecimal("75000"))
                .ngayGhiChiSo(LocalDateTime.now())
                .build()));
    }

    public List<ChiSoDienNuoc> getAll() {
        return danhSach;
    }

    public Optional<ChiSoDienNuoc> getById(Integer id) {
        return danhSach.stream().filter(c -> c.getMaChiSo().equals(id)).findFirst();
    }

    public ChiSoDienNuoc create(ChiSoDienNuoc chiSo) {
        chiSo.setMaChiSo(idCounter.getAndIncrement());
        chiSo.setNgayGhiChiSo(LocalDateTime.now());
        danhSach.add(chiSo);
        return chiSo;
    }

    public Optional<ChiSoDienNuoc> update(Integer id, ChiSoDienNuoc duLieuMoi) {
        return getById(id).map(c -> {
            c.setSoDienMoi(duLieuMoi.getSoDienMoi());
            c.setSoNuocMoi(duLieuMoi.getSoNuocMoi());
            c.setTienDien(duLieuMoi.getTienDien());
            c.setTienNuoc(duLieuMoi.getTienNuoc());
            return c;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(c -> c.getMaChiSo().equals(id));
    }
}