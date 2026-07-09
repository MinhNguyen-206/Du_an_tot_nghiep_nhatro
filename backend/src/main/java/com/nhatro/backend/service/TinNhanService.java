package com.nhatro.backend.service;

import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.TinNhan;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TinNhanService {

    private final List<TinNhan> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public TinNhanService(NguoiDungService nguoiDungService) {
        Optional<NguoiDung> chuTroMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 2)
                .findFirst();
        Optional<NguoiDung> nguoiThueMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 1)
                .findFirst();

        if (chuTroMau.isPresent() && nguoiThueMau.isPresent()) {
            danhSach.add(TinNhan.builder()
                    .maTinNhan(idCounter.getAndIncrement())
                    .nguoiGui(nguoiThueMau.get())
                    .nguoiNhan(chuTroMau.get())
                    .noiDung("Chao anh/chi, phong con trong khong a?")
                    .thoiGianGui(LocalDateTime.now().minusHours(2))
                    .daDoc(true)
                    .build());

            danhSach.add(TinNhan.builder()
                    .maTinNhan(idCounter.getAndIncrement())
                    .nguoiGui(chuTroMau.get())
                    .nguoiNhan(nguoiThueMau.get())
                    .noiDung("Con trong ban nhe, em qua xem phong luc nao cung duoc")
                    .thoiGianGui(LocalDateTime.now().minusHours(1))
                    .daDoc(false)
                    .build());
        }
    }

    public List<TinNhan> getAll() {
        return danhSach;
    }

    public Optional<TinNhan> getById(Long id) {
        return danhSach.stream().filter(t -> t.getMaTinNhan().equals(id)).findFirst();
    }

    public TinNhan create(TinNhan tinNhan) {
        tinNhan.setMaTinNhan(idCounter.getAndIncrement());
        tinNhan.setThoiGianGui(LocalDateTime.now());
        if (tinNhan.getDaDoc() == null) {
            tinNhan.setDaDoc(false);
        }
        danhSach.add(tinNhan);
        return tinNhan;
    }

    public Optional<TinNhan> update(Long id, TinNhan duLieuMoi) {
        return getById(id).map(t -> {
            t.setDaDoc(duLieuMoi.getDaDoc());
            return t;
        });
    }

    public boolean delete(Long id) {
        return danhSach.removeIf(t -> t.getMaTinNhan().equals(id));
    }
}