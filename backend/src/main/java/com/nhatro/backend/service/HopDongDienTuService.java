package com.nhatro.backend.service;

import com.nhatro.backend.entity.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class HopDongDienTuService {

    private final List<HopDongDienTu> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public HopDongDienTuService(YeuCauThueService yeuCauThueService,
            PhongTroService phongTroService,
            NguoiDungService nguoiDungService) {
        List<PhongTro> dsPhong = phongTroService.getAll();
        Optional<YeuCauThue> yeuCauMau = yeuCauThueService.getAll().stream().findFirst();
        Optional<NguoiDung> chuTroMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 2)
                .findFirst();
        Optional<NguoiDung> nguoiThueMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 1)
                .findFirst();

        if (!dsPhong.isEmpty() && chuTroMau.isPresent() && nguoiThueMau.isPresent()) {
            danhSach.add(HopDongDienTu.builder()
                    .maHopDong(idCounter.getAndIncrement())
                    .yeuCau(yeuCauMau.orElse(null))
                    .phong(dsPhong.get(0))
                    .nguoiThue(nguoiThueMau.get())
                    .chuTro(chuTroMau.get())
                    .ngayBatDau(LocalDateTime.now())
                    .ngayKetThuc(LocalDateTime.now().plusMonths(6))
                    .thoiHanThue(6)
                    .giaThue(new BigDecimal("2500000"))
                    .tienCoc(new BigDecimal("2500000"))
                    .dieuKhoanViPham("Boi thuong 1 thang tien phong neu don ra truoc han khong bao truoc 30 ngay")
                    .trangThaiHopDong(1)
                    .ngayCapNhat(LocalDateTime.now())
                    .build());
        }
    }

    public List<HopDongDienTu> getAll() {
        return danhSach;
    }

    public Optional<HopDongDienTu> getById(Integer id) {
        return danhSach.stream().filter(h -> h.getMaHopDong().equals(id)).findFirst();
    }

    public HopDongDienTu create(HopDongDienTu hopDong) {
        hopDong.setMaHopDong(idCounter.getAndIncrement());
        hopDong.setNgayCapNhat(LocalDateTime.now());
        if (hopDong.getTrangThaiHopDong() == null) {
            hopDong.setTrangThaiHopDong(0);
        }
        danhSach.add(hopDong);
        return hopDong;
    }

    public Optional<HopDongDienTu> update(Integer id, HopDongDienTu duLieuMoi) {
        return getById(id).map(h -> {
            h.setNgayKetThuc(duLieuMoi.getNgayKetThuc());
            h.setNgayChamDut(duLieuMoi.getNgayChamDut());
            h.setLyDoChamDut(duLieuMoi.getLyDoChamDut());
            h.setTrangThaiHopDong(duLieuMoi.getTrangThaiHopDong());
            h.setNgayCapNhat(LocalDateTime.now());
            return h;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(h -> h.getMaHopDong().equals(id));
    }
}