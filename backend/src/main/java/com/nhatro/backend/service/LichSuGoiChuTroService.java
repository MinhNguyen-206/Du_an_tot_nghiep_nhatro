package com.nhatro.backend.service;

import com.nhatro.backend.entity.LichSuGoiChuTro;
import com.nhatro.backend.entity.DangTin;
import com.nhatro.backend.entity.NguoiDung;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LichSuGoiChuTroService {

    private final List<LichSuGoiChuTro> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public LichSuGoiChuTroService(DangTinService dangTinService, NguoiDungService nguoiDungService) {
        List<DangTin> dsDangTin = dangTinService.getAll();
        Optional<NguoiDung> chuTroMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 2)
                .findFirst();
        Optional<NguoiDung> nguoiThueMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 1)
                .findFirst();

        if (!dsDangTin.isEmpty() && chuTroMau.isPresent() && nguoiThueMau.isPresent()) {
            DangTin dangTin = dsDangTin.get(0);

            danhSach.add(LichSuGoiChuTro.builder()
                    .maLichSuGoi(idCounter.getAndIncrement())
                    .nguoiThue(nguoiThueMau.get())
                    .chuTro(chuTroMau.get())
                    .phong(dangTin.getPhong())
                    .thoiGianGoi(LocalDateTime.now().minusHours(3))
                    .thoiLuongGoi(540) // 9 phút
                    .statusCuocGoi(1) // Đã nghe
                    .ghiChu("Thảo luận chi tiết về phòng và thủ tục thuê")
                    .build());

            danhSach.add(LichSuGoiChuTro.builder()
                    .maLichSuGoi(idCounter.getAndIncrement())
                    .nguoiThue(nguoiThueMau.get())
                    .chuTro(chuTroMau.get())
                    .phong(dangTin.getPhong())
                    .thoiGianGoi(LocalDateTime.now().minusHours(1))
                    .thoiLuongGoi(120) // 2 phút
                    .statusCuocGoi(1) // Đã nghe
                    .ghiChu("Xác nhận thời gian xem phòng")
                    .build());

            danhSach.add(LichSuGoiChuTro.builder()
                    .maLichSuGoi(idCounter.getAndIncrement())
                    .nguoiThue(nguoiThueMau.get())
                    .chuTro(chuTroMau.get())
                    .phong(dangTin.getPhong())
                    .thoiGianGoi(LocalDateTime.now().minusMinutes(30))
                    .thoiLuongGoi(0) // 0 giây
                    .statusCuocGoi(2) // Cuộc gọi nhỡ
                    .ghiChu("Cuộc gọi nhỡ")
                    .build());
        }
    }

    public List<LichSuGoiChuTro> getAll() {
        return danhSach;
    }

    public Optional<LichSuGoiChuTro> getById(Integer id) {
        return danhSach.stream().filter(l -> l.getMaLichSuGoi().equals(id)).findFirst();
    }

    public LichSuGoiChuTro create(LichSuGoiChuTro lichSuGoiChuTro) {
        lichSuGoiChuTro.setMaLichSuGoi(idCounter.getAndIncrement());
        lichSuGoiChuTro.setThoiGianGoi(LocalDateTime.now());
        if (lichSuGoiChuTro.getStatusCuocGoi() == null) {
            lichSuGoiChuTro.setStatusCuocGoi(0);
        }
        danhSach.add(lichSuGoiChuTro);
        return lichSuGoiChuTro;
    }

    public Optional<LichSuGoiChuTro> update(Integer id, LichSuGoiChuTro duLieuMoi) {
        return getById(id).map(l -> {
            l.setThoiLuongGoi(duLieuMoi.getThoiLuongGoi());
            l.setStatusCuocGoi(duLieuMoi.getStatusCuocGoi());
            l.setGhiChu(duLieuMoi.getGhiChu());
            return l;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(l -> l.getMaLichSuGoi().equals(id));
    }
}
