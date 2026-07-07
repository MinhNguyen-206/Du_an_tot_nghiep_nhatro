package com.nhatro.backend.service;

import com.nhatro.backend.entity.NguoiDung;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NguoiDungService {

    private final List<NguoiDung> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public NguoiDungService() {
        themDuLieuMau();
    }

    private void themDuLieuMau() {
        danhSach.add(NguoiDung.builder()
                .maNguoiDung(idCounter.getAndIncrement())
                .email("chutro1@gmail.com")
                .matKhauMaHoa("123456")
                .hoTen("Nguyen Van A")
                .soDienThoai("0901111111")
                .vaiTro(2)
                .trangThaiTaiKhoan(1)
                .daXacMinhEkyc(true)
                .nguonDangNhap("local")
                .ngayTao(LocalDateTime.now())
                .ngayCapNhat(LocalDateTime.now())
                .build());

        danhSach.add(NguoiDung.builder()
                .maNguoiDung(idCounter.getAndIncrement())
                .email("nguoithue1@gmail.com")
                .matKhauMaHoa("123456")
                .hoTen("Tran Thi B")
                .soDienThoai("0902222222")
                .vaiTro(1)
                .trangThaiTaiKhoan(1)
                .daXacMinhEkyc(false)
                .nguonDangNhap("local")
                .ngayTao(LocalDateTime.now())
                .ngayCapNhat(LocalDateTime.now())
                .build());

        danhSach.add(NguoiDung.builder()
                .maNguoiDung(idCounter.getAndIncrement())
                .email("admin@nhatro.com")
                .matKhauMaHoa("123456")
                .hoTen("Quan Tri Vien")
                .vaiTro(3)
                .trangThaiTaiKhoan(1)
                .daXacMinhEkyc(true)
                .nguonDangNhap("local")
                .ngayTao(LocalDateTime.now())
                .ngayCapNhat(LocalDateTime.now())
                .build());
    }

    public List<NguoiDung> getAll() {
        return danhSach;
    }

    public Optional<NguoiDung> getById(Integer id) {
        return danhSach.stream()
                .filter(nd -> nd.getMaNguoiDung().equals(id))
                .findFirst();
    }

    public NguoiDung create(NguoiDung nguoiDung) {
        nguoiDung.setMaNguoiDung(idCounter.getAndIncrement());
        nguoiDung.setNgayTao(LocalDateTime.now());
        nguoiDung.setNgayCapNhat(LocalDateTime.now());
        danhSach.add(nguoiDung);
        return nguoiDung;
    }

    public Optional<NguoiDung> update(Integer id, NguoiDung duLieuMoi) {
        return getById(id).map(nd -> {
            nd.setHoTen(duLieuMoi.getHoTen());
            nd.setSoDienThoai(duLieuMoi.getSoDienThoai());
            nd.setDiaChiThuongTru(duLieuMoi.getDiaChiThuongTru());
            nd.setAnhDaiDien(duLieuMoi.getAnhDaiDien());
            nd.setNgayCapNhat(LocalDateTime.now());
            return nd;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(nd -> nd.getMaNguoiDung().equals(id));
    }
}