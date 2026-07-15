package com.nhatro.backend.service;

import com.nhatro.backend.entity.CanhBaoPhongMoi;
import com.nhatro.backend.entity.NguoiDung;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CanhBaoPhongMoiService {

    private final List<CanhBaoPhongMoi> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public CanhBaoPhongMoiService(NguoiDungService nguoiDungService) {
        Optional<NguoiDung> nguoiThueMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 1)
                .findFirst();

        nguoiThueMau.ifPresent(nguoiDung -> danhSach.add(CanhBaoPhongMoi.builder()
                .maCanhBao(idCounter.getAndIncrement())
                .nguoiDung(nguoiDung)
                .khuVuc("Quận 1, TP.HCM")
                .khoangGia("1000000-3000000")
                .loaiPhong("Phòng trọ")
                .tienIch("Wifi,Máy lạnh,Chỗ để xe")
                .trangThaiHoatDong(true)
                .build()));
    }

    public List<CanhBaoPhongMoi> getAll() {
        return danhSach;
    }

    public Optional<CanhBaoPhongMoi> getById(Integer id) {
        return danhSach.stream().filter(c -> c.getMaCanhBao().equals(id)).findFirst();
    }

    public CanhBaoPhongMoi create(CanhBaoPhongMoi canhBao) {
        canhBao.setMaCanhBao(idCounter.getAndIncrement());
        if (canhBao.getTrangThaiHoatDong() == null) {
            canhBao.setTrangThaiHoatDong(true);
        }
        danhSach.add(canhBao);
        return canhBao;
    }

    public Optional<CanhBaoPhongMoi> update(Integer id, CanhBaoPhongMoi duLieuMoi) {
        return getById(id).map(c -> {
            c.setKhuVuc(duLieuMoi.getKhuVuc());
            c.setKhoangGia(duLieuMoi.getKhoangGia());
            c.setLoaiPhong(duLieuMoi.getLoaiPhong());
            c.setTienIch(duLieuMoi.getTienIch());
            c.setTrangThaiHoatDong(duLieuMoi.getTrangThaiHoatDong());
            return c;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(c -> c.getMaCanhBao().equals(id));
    }
}
