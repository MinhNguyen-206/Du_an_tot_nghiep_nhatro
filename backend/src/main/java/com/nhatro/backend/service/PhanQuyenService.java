package com.nhatro.backend.service;

import com.nhatro.backend.entity.PhanQuyen;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PhanQuyenService {

    private final List<PhanQuyen> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public PhanQuyenService() {
        themDuLieuMau();
    }

    private void themDuLieuMau() {
        // Vai trò tương ứng với NguoiDung.vaiTro: 1=Người thuê, 2=Chủ trọ, 3=Admin
        danhSach.add(PhanQuyen.builder()
                .maQuyen(idCounter.getAndIncrement())
                .tenQuyen("ROLE_NGUOI_THUE")
                .moTa("Người thuê trọ, có quyền tìm kiếm và xem phòng")
                .ngayTao(LocalDateTime.now())
                .ngayCapNhat(LocalDateTime.now())
                .build());

        danhSach.add(PhanQuyen.builder()
                .maQuyen(idCounter.getAndIncrement())
                .tenQuyen("ROLE_CHU_TRO")
                .moTa("Chủ trọ, có quyền đăng tin và quản lý phòng trọ")
                .ngayTao(LocalDateTime.now())
                .ngayCapNhat(LocalDateTime.now())
                .build());

        danhSach.add(PhanQuyen.builder()
                .maQuyen(idCounter.getAndIncrement())
                .tenQuyen("ROLE_ADMIN")
                .moTa("Quản trị viên, có toàn quyền trên hệ thống")
                .ngayTao(LocalDateTime.now())
                .ngayCapNhat(LocalDateTime.now())
                .build());
    }

    public List<PhanQuyen> getAll() {
        return danhSach;
    }

    public Optional<PhanQuyen> getById(Integer id) {
        return danhSach.stream()
                .filter(pq -> pq.getMaQuyen().equals(id))
                .findFirst();
    }

    public PhanQuyen create(PhanQuyen phanQuyen) {
        phanQuyen.setMaQuyen(idCounter.getAndIncrement());
        phanQuyen.setNgayTao(LocalDateTime.now());
        phanQuyen.setNgayCapNhat(LocalDateTime.now());
        danhSach.add(phanQuyen);
        return phanQuyen;
    }

    public Optional<PhanQuyen> update(Integer id, PhanQuyen duLieuMoi) {
        return getById(id).map(pq -> {
            pq.setTenQuyen(duLieuMoi.getTenQuyen());
            pq.setMoTa(duLieuMoi.getMoTa());
            pq.setNgayCapNhat(LocalDateTime.now());
            return pq;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(pq -> pq.getMaQuyen().equals(id));
    }
}
