package com.nhatro.backend.service;

import com.nhatro.backend.entity.QuanTriVien;
import com.nhatro.backend.entity.NguoiDung;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuanTriVienService {

    private final List<QuanTriVien> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public QuanTriVienService(NguoiDungService nguoiDungService) {
        Optional<NguoiDung> adminMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 3)
                .findFirst();

        if (adminMau.isPresent()) {
            danhSach.add(QuanTriVien.builder()
                    .maQuanTri(idCounter.getAndIncrement())
                    .nguoiDung(adminMau.get())
                    .quyenHan("QLND,QLPT,QLDT,QLHD,QLBC,DUYET_DT") // Quản lý người dùng, phòng trọ, đăng tin, hóa đơn,
                                                                   // báo cáo, duyệt đăng tin
                    .trangThai(1)
                    .ghiChu("Admin chính - Có quyền truy cập toàn bộ hệ thống")
                    .ngayCapQuyen(LocalDateTime.now().minusDays(365))
                    .ngayCapNhat(LocalDateTime.now())
                    .build());
        }
    }

    public List<QuanTriVien> getAll() {
        return danhSach;
    }

    public Optional<QuanTriVien> getById(Integer id) {
        return danhSach.stream().filter(q -> q.getMaQuanTri().equals(id)).findFirst();
    }

    public QuanTriVien create(QuanTriVien quanTriVien) {
        quanTriVien.setMaQuanTri(idCounter.getAndIncrement());
        quanTriVien.setNgayCapQuyen(LocalDateTime.now());
        quanTriVien.setNgayCapNhat(LocalDateTime.now());
        danhSach.add(quanTriVien);
        return quanTriVien;
    }

    public Optional<QuanTriVien> update(Integer id, QuanTriVien duLieuMoi) {
        return getById(id).map(q -> {
            q.setQuyenHan(duLieuMoi.getQuyenHan());
            q.setTrangThai(duLieuMoi.getTrangThai());
            q.setGhiChu(duLieuMoi.getGhiChu());
            q.setNgayCapNhat(LocalDateTime.now());
            return q;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(q -> q.getMaQuanTri().equals(id));
    }
}
