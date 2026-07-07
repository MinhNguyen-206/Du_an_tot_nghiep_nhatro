package com.nhatro.backend.service;

import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.NhaTro;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NhaTroService {

    private final List<NhaTro> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public NhaTroService(NguoiDungService nguoiDungService) {
        Optional<NguoiDung> chuTroMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 2)
                .findFirst();

        chuTroMau.ifPresent(chuTro -> {
            danhSach.add(NhaTro.builder()
                    .maNhaTro(idCounter.getAndIncrement())
                    .tenNhaTro("Nha Tro Binh An")
                    .diaChi("123 Le Van Viet, Quan 9, TP.HCM")
                    .soTang(3)
                    .chuTro(chuTro)
                    .ngayTao(LocalDateTime.now())
                    .build());

            danhSach.add(NhaTro.builder()
                    .maNhaTro(idCounter.getAndIncrement())
                    .tenNhaTro("Nha Tro Sinh Vien")
                    .diaChi("45 Vo Van Ngan, Thu Duc, TP.HCM")
                    .soTang(2)
                    .chuTro(chuTro)
                    .ngayTao(LocalDateTime.now())
                    .build());
        });
    }

    public List<NhaTro> getAll() {
        return danhSach;
    }

    public Optional<NhaTro> getById(Integer id) {
        return danhSach.stream()
                .filter(nt -> nt.getMaNhaTro().equals(id))
                .findFirst();
    }

    public NhaTro create(NhaTro nhaTro) {
        nhaTro.setMaNhaTro(idCounter.getAndIncrement());
        nhaTro.setNgayTao(LocalDateTime.now());
        danhSach.add(nhaTro);
        return nhaTro;
    }

    public Optional<NhaTro> update(Integer id, NhaTro duLieuMoi) {
        return getById(id).map(nt -> {
            nt.setTenNhaTro(duLieuMoi.getTenNhaTro());
            nt.setDiaChi(duLieuMoi.getDiaChi());
            nt.setSoTang(duLieuMoi.getSoTang());
            return nt;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(nt -> nt.getMaNhaTro().equals(id));
    }
}