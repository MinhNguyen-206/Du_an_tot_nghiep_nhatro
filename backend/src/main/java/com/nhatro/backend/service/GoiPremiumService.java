package com.nhatro.backend.service;

import com.nhatro.backend.entity.GoiPremium;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GoiPremiumService {

    private final List<GoiPremium> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public GoiPremiumService() {
        themDuLieuMau();
    }

    private void themDuLieuMau() {
        danhSach.add(GoiPremium.builder()
                .maGoi(idCounter.getAndIncrement())
                .tenGoi("Premium Tháng")
                .giaTien(new BigDecimal("99000"))
                .thoiHanNgay(30)
                .gioiHanTinDang(20)
                .noiDungDieuKhoan("Chủ trọ được đăng tối đa 20 tin trong 30 ngày, tin được ưu tiên hiển thị đầu danh sách tìm kiếm.")
                .build());

        danhSach.add(GoiPremium.builder()
                .maGoi(idCounter.getAndIncrement())
                .tenGoi("Premium Quý")
                .giaTien(new BigDecimal("249000"))
                .thoiHanNgay(90)
                .gioiHanTinDang(70)
                .noiDungDieuKhoan("Chủ trọ được đăng tối đa 70 tin trong 90 ngày, tin được ưu tiên hiển thị và gắn nhãn Premium.")
                .build());
    }

    public List<GoiPremium> getAll() {
        return danhSach;
    }

    public Optional<GoiPremium> getById(Integer id) {
        return danhSach.stream().filter(g -> g.getMaGoi().equals(id)).findFirst();
    }

    public GoiPremium create(GoiPremium goiPremium) {
        goiPremium.setMaGoi(idCounter.getAndIncrement());
        danhSach.add(goiPremium);
        return goiPremium;
    }

    public Optional<GoiPremium> update(Integer id, GoiPremium duLieuMoi) {
        return getById(id).map(g -> {
            g.setTenGoi(duLieuMoi.getTenGoi());
            g.setGiaTien(duLieuMoi.getGiaTien());
            g.setThoiHanNgay(duLieuMoi.getThoiHanNgay());
            g.setGioiHanTinDang(duLieuMoi.getGioiHanTinDang());
            g.setNoiDungDieuKhoan(duLieuMoi.getNoiDungDieuKhoan());
            return g;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(g -> g.getMaGoi().equals(id));
    }
}
