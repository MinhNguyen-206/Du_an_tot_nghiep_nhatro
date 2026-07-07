package com.nhatro.backend.service;

import com.nhatro.backend.entity.NhaTro;
import com.nhatro.backend.entity.PhongTro;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PhongTroService {

    private final List<PhongTro> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public PhongTroService(NhaTroService nhaTroService) {
        List<NhaTro> dsNhaTro = nhaTroService.getAll();
        if (!dsNhaTro.isEmpty()) {
            NhaTro nhaTro1 = dsNhaTro.get(0);

            danhSach.add(PhongTro.builder()
                    .maPhong(idCounter.getAndIncrement())
                    .nhaTro(nhaTro1)
                    .soPhong("P101")
                    .dienTich(20.0)
                    .loaiPhong("PHONG_DON")
                    .maTrangThai(1)
                    .giaGoc(new BigDecimal("2500000"))
                    .giaDien(new BigDecimal("3500"))
                    .giaNuoc(new BigDecimal("15000"))
                    .giaXe(new BigDecimal("100000"))
                    .giaWifi(new BigDecimal("50000"))
                    .build());

            danhSach.add(PhongTro.builder()
                    .maPhong(idCounter.getAndIncrement())
                    .nhaTro(nhaTro1)
                    .soPhong("P102")
                    .dienTich(25.0)
                    .loaiPhong("PHONG_DOI")
                    .maTrangThai(2)
                    .giaGoc(new BigDecimal("3200000"))
                    .giaDien(new BigDecimal("3500"))
                    .giaNuoc(new BigDecimal("15000"))
                    .giaXe(new BigDecimal("100000"))
                    .giaWifi(new BigDecimal("50000"))
                    .build());
        }
    }

    public List<PhongTro> getAll() {
        return danhSach;
    }

    public Optional<PhongTro> getById(Integer id) {
        return danhSach.stream()
                .filter(p -> p.getMaPhong().equals(id))
                .findFirst();
    }

    public PhongTro create(PhongTro phongTro) {
        phongTro.setMaPhong(idCounter.getAndIncrement());
        danhSach.add(phongTro);
        return phongTro;
    }

    public Optional<PhongTro> update(Integer id, PhongTro duLieuMoi) {
        return getById(id).map(p -> {
            p.setSoPhong(duLieuMoi.getSoPhong());
            p.setDienTich(duLieuMoi.getDienTich());
            p.setLoaiPhong(duLieuMoi.getLoaiPhong());
            p.setMaTrangThai(duLieuMoi.getMaTrangThai());
            p.setGiaGoc(duLieuMoi.getGiaGoc());
            p.setGiaDien(duLieuMoi.getGiaDien());
            p.setGiaNuoc(duLieuMoi.getGiaNuoc());
            p.setGiaXe(duLieuMoi.getGiaXe());
            p.setGiaWifi(duLieuMoi.getGiaWifi());
            return p;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(p -> p.getMaPhong().equals(id));
    }
}