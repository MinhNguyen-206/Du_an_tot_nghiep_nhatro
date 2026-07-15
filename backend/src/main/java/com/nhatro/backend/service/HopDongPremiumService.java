package com.nhatro.backend.service;

import com.nhatro.backend.entity.DangKyGoiChuTro;
import com.nhatro.backend.entity.HopDongPremium;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class HopDongPremiumService {

    private final List<HopDongPremium> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public HopDongPremiumService(DangKyGoiChuTroService dangKyGoiChuTroService) {
        List<DangKyGoiChuTro> dsDangKy = dangKyGoiChuTroService.getAll();

        if (!dsDangKy.isEmpty()) {
            DangKyGoiChuTro dangKy = dsDangKy.get(0);

            danhSach.add(HopDongPremium.builder()
                    .maHopDongPremium(idCounter.getAndIncrement())
                    .dangKy(dangKy)
                    .nguoiDung(dangKy.getNguoiDung())
                    .ngayKy(dangKy.getNgayTao())
                    .noiDungDieuKhoan("Chủ trọ đồng ý sử dụng gói " + dangKy.getGoi().getTenGoi()
                            + " trong " + dangKy.getGoi().getThoiHanNgay() + " ngày, tuân thủ điều khoản dịch vụ Premium của hệ thống.")
                    .chuKyNguoiDung("da-xac-nhan-dien-tu")
                    .duongDanFilePDF("/files/hop-dong-premium/hd-premium-1.pdf")
                    .trangThaiHopDong(1) // da ky - hieu luc
                    .build());
        }
    }

    public List<HopDongPremium> getAll() {
        return danhSach;
    }

    public Optional<HopDongPremium> getById(Integer id) {
        return danhSach.stream().filter(h -> h.getMaHopDongPremium().equals(id)).findFirst();
    }

    public HopDongPremium create(HopDongPremium hopDong) {
        hopDong.setMaHopDongPremium(idCounter.getAndIncrement());
        if (hopDong.getNgayKy() == null) {
            hopDong.setNgayKy(LocalDateTime.now());
        }
        if (hopDong.getTrangThaiHopDong() == null) {
            hopDong.setTrangThaiHopDong(0); // cho ky
        }
        danhSach.add(hopDong);
        return hopDong;
    }

    public Optional<HopDongPremium> update(Integer id, HopDongPremium duLieuMoi) {
        return getById(id).map(h -> {
            h.setNoiDungDieuKhoan(duLieuMoi.getNoiDungDieuKhoan());
            h.setChuKyNguoiDung(duLieuMoi.getChuKyNguoiDung());
            h.setDuongDanFilePDF(duLieuMoi.getDuongDanFilePDF());
            h.setTrangThaiHopDong(duLieuMoi.getTrangThaiHopDong());
            h.setNgayChamDut(duLieuMoi.getNgayChamDut());
            h.setLyDoChamDut(duLieuMoi.getLyDoChamDut());
            return h;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(h -> h.getMaHopDongPremium().equals(id));
    }
}
