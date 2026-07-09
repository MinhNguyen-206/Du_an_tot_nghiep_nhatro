package com.nhatro.backend.service;

import com.nhatro.backend.entity.DangTin;
import com.nhatro.backend.entity.LichHen;
import com.nhatro.backend.entity.NguoiDung;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LichHenService {

    private final List<LichHen> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public LichHenService(DangTinService dangTinService, NguoiDungService nguoiDungService) {
        List<DangTin> dsDangTin = dangTinService.getAll();
        Optional<NguoiDung> nguoiThueMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 1)
                .findFirst();

        if (!dsDangTin.isEmpty() && nguoiThueMau.isPresent()) {
            danhSach.add(LichHen.builder()
                    .maLichHen(idCounter.getAndIncrement())
                    .nguoiThue(nguoiThueMau.get())
                    .dangTin(dsDangTin.get(0))
                    .ngayGioHen(LocalDateTime.now().plusDays(2))
                    .soNguoiDiCung(1)
                    .loiNhan("Xin xem phong vao buoi chieu")
                    .trangThaiLichHen(0)
                    .build());
        }
    }

    public List<LichHen> getAll() {
        return danhSach;
    }

    public Optional<LichHen> getById(Integer id) {
        return danhSach.stream().filter(l -> l.getMaLichHen().equals(id)).findFirst();
    }

    public LichHen create(LichHen lichHen) {
        lichHen.setMaLichHen(idCounter.getAndIncrement());
        if (lichHen.getTrangThaiLichHen() == null) {
            lichHen.setTrangThaiLichHen(0);
        }
        danhSach.add(lichHen);
        return lichHen;
    }

    public Optional<LichHen> update(Integer id, LichHen duLieuMoi) {
        return getById(id).map(l -> {
            l.setNgayGioHen(duLieuMoi.getNgayGioHen());
            l.setSoNguoiDiCung(duLieuMoi.getSoNguoiDiCung());
            l.setLoiNhan(duLieuMoi.getLoiNhan());
            l.setLyDoTuChoi(duLieuMoi.getLyDoTuChoi());
            l.setTrangThaiLichHen(duLieuMoi.getTrangThaiLichHen());
            return l;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(l -> l.getMaLichHen().equals(id));
    }
}