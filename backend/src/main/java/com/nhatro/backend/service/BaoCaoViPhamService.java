package com.nhatro.backend.service;

import com.nhatro.backend.entity.BaoCaoViPham;
import com.nhatro.backend.entity.DangTin;
import com.nhatro.backend.entity.NguoiDung;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BaoCaoViPhamService {

    private final List<BaoCaoViPham> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public BaoCaoViPhamService(DangTinService dangTinService, NguoiDungService nguoiDungService) {
        List<DangTin> dsDangTin = dangTinService.getAll();
        Optional<NguoiDung> nguoiToCaoMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 1)
                .findFirst();

        if (!dsDangTin.isEmpty() && nguoiToCaoMau.isPresent()) {
            danhSach.add(BaoCaoViPham.builder()
                    .maBaoCao(idCounter.getAndIncrement())
                    .nguoiToCao(nguoiToCaoMau.get())
                    .dangTin(dsDangTin.get(0))
                    .loaiViPham("TIN_GIA")
                    .moTa("Tin dang khong dung voi hinh anh thuc te")
                    .trangThaiXuLy(0)
                    .ngayTao(LocalDateTime.now())
                    .build());
        }
    }

    public List<BaoCaoViPham> getAll() {
        return danhSach;
    }

    public Optional<BaoCaoViPham> getById(Integer id) {
        return danhSach.stream().filter(b -> b.getMaBaoCao().equals(id)).findFirst();
    }

    public BaoCaoViPham create(BaoCaoViPham baoCao) {
        baoCao.setMaBaoCao(idCounter.getAndIncrement());
        baoCao.setNgayTao(LocalDateTime.now());
        if (baoCao.getTrangThaiXuLy() == null) {
            baoCao.setTrangThaiXuLy(0);
        }
        danhSach.add(baoCao);
        return baoCao;
    }

    public Optional<BaoCaoViPham> update(Integer id, BaoCaoViPham duLieuMoi) {
        return getById(id).map(b -> {
            b.setTrangThaiXuLy(duLieuMoi.getTrangThaiXuLy());
            b.setMoTa(duLieuMoi.getMoTa());
            return b;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(b -> b.getMaBaoCao().equals(id));
    }
}