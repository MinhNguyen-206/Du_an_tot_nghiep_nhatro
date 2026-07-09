package com.nhatro.backend.service;

import com.nhatro.backend.entity.DanhGia;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.PhongTro;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DanhGiaService {

    private final List<DanhGia> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public DanhGiaService(PhongTroService phongTroService, NguoiDungService nguoiDungService) {
        List<PhongTro> dsPhong = phongTroService.getAll();
        Optional<NguoiDung> nguoiThueMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 1)
                .findFirst();

        if (!dsPhong.isEmpty() && nguoiThueMau.isPresent()) {
            danhSach.add(DanhGia.builder()
                    .maDanhGia(idCounter.getAndIncrement())
                    .nguoiThue(nguoiThueMau.get())
                    .phong(dsPhong.get(0))
                    .soSao(5)
                    .noiDungBinhLuan("Phong sach se, chu tro nhiet tinh, se gioi thieu ban be")
                    .trangThaiKiemDuyet(1)
                    .ngayTao(LocalDateTime.now())
                    .build());
        }
    }

    public List<DanhGia> getAll() {
        return danhSach;
    }

    public Optional<DanhGia> getById(Integer id) {
        return danhSach.stream().filter(d -> d.getMaDanhGia().equals(id)).findFirst();
    }

    public DanhGia create(DanhGia danhGia) {
        danhGia.setMaDanhGia(idCounter.getAndIncrement());
        danhGia.setNgayTao(LocalDateTime.now());
        if (danhGia.getTrangThaiKiemDuyet() == null) {
            danhGia.setTrangThaiKiemDuyet(0);
        }
        danhSach.add(danhGia);
        return danhGia;
    }

    public Optional<DanhGia> update(Integer id, DanhGia duLieuMoi) {
        return getById(id).map(d -> {
            d.setSoSao(duLieuMoi.getSoSao());
            d.setNoiDungBinhLuan(duLieuMoi.getNoiDungBinhLuan());
            d.setTrangThaiKiemDuyet(duLieuMoi.getTrangThaiKiemDuyet());
            return d;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(d -> d.getMaDanhGia().equals(id));
    }
}