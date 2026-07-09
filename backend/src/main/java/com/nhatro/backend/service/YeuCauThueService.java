package com.nhatro.backend.service;

import com.nhatro.backend.entity.DangTin;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.YeuCauThue;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class YeuCauThueService {

    private final List<YeuCauThue> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public YeuCauThueService(DangTinService dangTinService, NguoiDungService nguoiDungService) {
        List<DangTin> dsDangTin = dangTinService.getAll();
        Optional<NguoiDung> nguoiThueMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 1)
                .findFirst();

        if (!dsDangTin.isEmpty() && nguoiThueMau.isPresent()) {
            danhSach.add(YeuCauThue.builder()
                    .maYeuCau(idCounter.getAndIncrement())
                    .nguoiThue(nguoiThueMau.get())
                    .dangTin(dsDangTin.get(0))
                    .thoiHanThueMongMuon(6)
                    .ngayDuKienDonVao(LocalDate.now().plusDays(7))
                    .trangThai(0)
                    .trangThaiCoc(0)
                    .ghiChu("Muon don vao som, mong chu tro phan hoi som")
                    .ngayTao(LocalDateTime.now())
                    .build());
        }
    }

    public List<YeuCauThue> getAll() {
        return danhSach;
    }

    public Optional<YeuCauThue> getById(Integer id) {
        return danhSach.stream().filter(y -> y.getMaYeuCau().equals(id)).findFirst();
    }

    public YeuCauThue create(YeuCauThue yeuCauThue) {
        yeuCauThue.setMaYeuCau(idCounter.getAndIncrement());
        yeuCauThue.setNgayTao(LocalDateTime.now());
        if (yeuCauThue.getTrangThai() == null) {
            yeuCauThue.setTrangThai(0);
        }
        danhSach.add(yeuCauThue);
        return yeuCauThue;
    }

    public Optional<YeuCauThue> update(Integer id, YeuCauThue duLieuMoi) {
        return getById(id).map(y -> {
            y.setTrangThai(duLieuMoi.getTrangThai());
            y.setTrangThaiCoc(duLieuMoi.getTrangThaiCoc());
            y.setGhiChu(duLieuMoi.getGhiChu());
            return y;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(y -> y.getMaYeuCau().equals(id));
    }
}