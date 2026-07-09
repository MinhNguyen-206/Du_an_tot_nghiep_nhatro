package com.nhatro.backend.service;

import com.nhatro.backend.entity.LichSuXemPhong;
import com.nhatro.backend.entity.DangTin;
import com.nhatro.backend.entity.PhongTro;
import com.nhatro.backend.entity.NguoiDung;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LichSuXemPhongService {

    private final List<LichSuXemPhong> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public LichSuXemPhongService(DangTinService dangTinService, NguoiDungService nguoiDungService) {
        List<DangTin> dsDangTin = dangTinService.getAll();
        Optional<NguoiDung> nguoiThueMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 1)
                .findFirst();

        if (!dsDangTin.isEmpty() && nguoiThueMau.isPresent()) {
            DangTin dangTin = dsDangTin.get(0);

            danhSach.add(LichSuXemPhong.builder()
                    .maLichSuXem(idCounter.getAndIncrement())
                    .nguoiDung(nguoiThueMau.get())
                    .phong(dangTin.getPhong())
                    .dangTin(dangTin)
                    .thoiGianXem(LocalDateTime.now().minusHours(2))
                    .thoiLuongXem(180) // 3 phút
                    .ghiChu("Xem phong chi tiet, hoi chu tro ve gia ca")
                    .build());

            danhSach.add(LichSuXemPhong.builder()
                    .maLichSuXem(idCounter.getAndIncrement())
                    .nguoiDung(nguoiThueMau.get())
                    .phong(dangTin.getPhong())
                    .dangTin(dangTin)
                    .thoiGianXem(LocalDateTime.now().minusHours(1))
                    .thoiLuongXem(240) // 4 phút
                    .ghiChu("Xem lai phong, co y dinh thue")
                    .build());
        }
    }

    public List<LichSuXemPhong> getAll() {
        return danhSach;
    }

    public Optional<LichSuXemPhong> getById(Integer id) {
        return danhSach.stream().filter(l -> l.getMaLichSuXem().equals(id)).findFirst();
    }

    public LichSuXemPhong create(LichSuXemPhong lichSuXemPhong) {
        lichSuXemPhong.setMaLichSuXem(idCounter.getAndIncrement());
        lichSuXemPhong.setThoiGianXem(LocalDateTime.now());
        danhSach.add(lichSuXemPhong);
        return lichSuXemPhong;
    }

    public Optional<LichSuXemPhong> update(Integer id, LichSuXemPhong duLieuMoi) {
        return getById(id).map(l -> {
            l.setThoiLuongXem(duLieuMoi.getThoiLuongXem());
            l.setGhiChu(duLieuMoi.getGhiChu());
            return l;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(l -> l.getMaLichSuXem().equals(id));
    }
}
