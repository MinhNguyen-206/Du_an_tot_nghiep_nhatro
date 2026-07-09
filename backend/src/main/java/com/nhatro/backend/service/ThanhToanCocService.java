package com.nhatro.backend.service;

import com.nhatro.backend.entity.HopDongDienTu;
import com.nhatro.backend.entity.ThanhToanCoc;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ThanhToanCocService {

    private final List<ThanhToanCoc> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public ThanhToanCocService(HopDongDienTuService hopDongDienTuService) {
        Optional<HopDongDienTu> hopDongMau = hopDongDienTuService.getAll().stream().findFirst();

        hopDongMau.ifPresent(hopDong -> danhSach.add(ThanhToanCoc.builder()
                .maThanhToan(idCounter.getAndIncrement())
                .hopDong(hopDong)
                .nguoiThue(hopDong.getNguoiThue())
                .chuTro(hopDong.getChuTro())
                .soTienCoc(new BigDecimal("2500000"))
                .phuongThucThanhToan("Chuyen khoan")
                .trangThaiCoc(1)
                .ngayThanhToan(LocalDateTime.now())
                .ghiChu("Da chuyen khoan qua Momo")
                .build()));
    }

    public List<ThanhToanCoc> getAll() {
        return danhSach;
    }

    public Optional<ThanhToanCoc> getById(Integer id) {
        return danhSach.stream().filter(t -> t.getMaThanhToan().equals(id)).findFirst();
    }

    public ThanhToanCoc create(ThanhToanCoc thanhToanCoc) {
        thanhToanCoc.setMaThanhToan(idCounter.getAndIncrement());
        if (thanhToanCoc.getTrangThaiCoc() == null) {
            thanhToanCoc.setTrangThaiCoc(0);
        }
        danhSach.add(thanhToanCoc);
        return thanhToanCoc;
    }

    public Optional<ThanhToanCoc> update(Integer id, ThanhToanCoc duLieuMoi) {
        return getById(id).map(t -> {
            t.setTrangThaiCoc(duLieuMoi.getTrangThaiCoc());
            t.setNgayThanhToan(duLieuMoi.getNgayThanhToan());
            t.setGhiChu(duLieuMoi.getGhiChu());
            return t;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(t -> t.getMaThanhToan().equals(id));
    }
}