package com.nhatro.backend.service;

import com.nhatro.backend.entity.HopDongDienTu;
import com.nhatro.backend.entity.HoaDonThang;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class HoaDonThangService {

    private final List<HoaDonThang> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public HoaDonThangService(HopDongDienTuService hopDongDienTuService) {
        Optional<HopDongDienTu> hopDongMau = hopDongDienTuService.getAll().stream().findFirst();

        hopDongMau.ifPresent(hopDong -> {
            BigDecimal tienPhong = hopDong.getGiaThue();
            BigDecimal tienXe = new BigDecimal("100000");
            BigDecimal tienWifi = new BigDecimal("50000");
            BigDecimal tongTien = tienPhong.add(tienXe).add(tienWifi);

            danhSach.add(HoaDonThang.builder()
                    .maHoaDon(idCounter.getAndIncrement())
                    .hopDong(hopDong)
                    .phong(hopDong.getPhong())
                    .chuTro(hopDong.getChuTro())
                    .nguoiThue(hopDong.getNguoiThue())
                    .thangNam(LocalDateTime.now().getYear() + "-" +
                            String.format("%02d", LocalDateTime.now().getMonthValue()))
                    .tienXe(tienXe)
                    .tienWifi(tienWifi)
                    .tienPhong(tienPhong)
                    .tongTien(tongTien)
                    .trangThaiThanhToan(0)
                    .ngayTao(LocalDateTime.now())
                    .ngayCapNhat(LocalDateTime.now())
                    .build());
        });
    }

    public List<HoaDonThang> getAll() {
        return danhSach;
    }

    public Optional<HoaDonThang> getById(Integer id) {
        return danhSach.stream().filter(h -> h.getMaHoaDon().equals(id)).findFirst();
    }

    public HoaDonThang create(HoaDonThang hoaDonThang) {
        hoaDonThang.setMaHoaDon(idCounter.getAndIncrement());
        hoaDonThang.setNgayTao(LocalDateTime.now());
        hoaDonThang.setNgayCapNhat(LocalDateTime.now());
        if (hoaDonThang.getTrangThaiThanhToan() == null) {
            hoaDonThang.setTrangThaiThanhToan(0);
        }
        danhSach.add(hoaDonThang);
        return hoaDonThang;
    }

    public Optional<HoaDonThang> update(Integer id, HoaDonThang duLieuMoi) {
        return getById(id).map(h -> {
            h.setTrangThaiThanhToan(duLieuMoi.getTrangThaiThanhToan());
            h.setNgayThanhToan(duLieuMoi.getNgayThanhToan());
            h.setNgayCapNhat(LocalDateTime.now());
            return h;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(h -> h.getMaHoaDon().equals(id));
    }
}