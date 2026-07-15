package com.nhatro.backend.service;

import com.nhatro.backend.entity.DangTin;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.PhongTro;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DangTinService {

    private final List<DangTin> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);
    private final PhongTroService phongTroService;
    private final NguoiDungService nguoiDungService;

    public DangTinService(PhongTroService phongTroService, NguoiDungService nguoiDungService) {
        this.phongTroService = phongTroService;
        this.nguoiDungService = nguoiDungService;
        List<PhongTro> dsPhong = phongTroService.getAll();
        Optional<NguoiDung> chuTroMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 2)
                .findFirst();

        if (!dsPhong.isEmpty() && chuTroMau.isPresent()) {
            NguoiDung chuTro = chuTroMau.get();

            danhSach.add(DangTin.builder()
                    .maDangTin(idCounter.getAndIncrement())
                    .phong(dsPhong.get(0))
                    .nguoiDung(chuTro)
                    .tieuDe("Phong tro gia re gan truong, day du tien nghi")
                    .moTaChiTiet("Phong sach se, an ninh, gio giac tu do, gan truong hoc va cho.")
                    .toaDoBanDo("10.8412,106.7897")
                    .trangThaiKiemDuyet(1)
                    .trangThaiHienThi(1)
                    .soLuotXem(0)
                    .isVip(false)
                    .ngayTao(LocalDateTime.now())
                    .ngayCapNhat(LocalDateTime.now())
                    .build());

            if (dsPhong.size() > 1) {
                danhSach.add(DangTin.builder()
                        .maDangTin(idCounter.getAndIncrement())
                        .phong(dsPhong.get(1))
                        .nguoiDung(chuTro)
                        .tieuDe("Phong doi rong rai, co gac lung")
                        .moTaChiTiet("Phong moi xay, co may lanh, tu lanh, ban ghe hoc.")
                        .toaDoBanDo("10.8500,106.7700")
                        .trangThaiKiemDuyet(0)
                        .trangThaiHienThi(1)
                        .soLuotXem(0)
                        .isVip(true)
                        .ngayTao(LocalDateTime.now())
                        .ngayCapNhat(LocalDateTime.now())
                        .build());
            }
        }
    }

    public List<DangTin> getAll() {
        return danhSach;
    }

    public Optional<DangTin> getById(Integer id) {
        return danhSach.stream()
                .filter(dt -> dt.getMaDangTin().equals(id))
                .findFirst();
    }

    public DangTin create(DangTin dangTin) {
        dangTin.setMaDangTin(idCounter.getAndIncrement());
        dangTin.setNgayTao(LocalDateTime.now());
        dangTin.setNgayCapNhat(LocalDateTime.now());
        if (dangTin.getSoLuotXem() == null) {
            dangTin.setSoLuotXem(0);
        }

        if (dangTin.getPhong() != null && dangTin.getPhong().getMaPhong() != null) {
            phongTroService.getById(dangTin.getPhong().getMaPhong())
                    .ifPresent(dangTin::setPhong);
        }

        if (dangTin.getNguoiDung() != null && dangTin.getNguoiDung().getMaNguoiDung() != null) {
            nguoiDungService.getById(dangTin.getNguoiDung().getMaNguoiDung())
                    .ifPresent(dangTin::setNguoiDung);
        }

        danhSach.add(dangTin);
        return dangTin;
    }

    public Optional<DangTin> update(Integer id, DangTin duLieuMoi) {
        return getById(id).map(dt -> {
            dt.setTieuDe(duLieuMoi.getTieuDe());
            dt.setMoTaChiTiet(duLieuMoi.getMoTaChiTiet());
            dt.setTrangThaiHienThi(duLieuMoi.getTrangThaiHienThi());
            dt.setNgayCapNhat(LocalDateTime.now());
            return dt;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(dt -> dt.getMaDangTin().equals(id));
    }
}