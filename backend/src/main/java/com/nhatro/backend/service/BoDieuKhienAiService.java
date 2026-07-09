package com.nhatro.backend.service;

import com.nhatro.backend.entity.BoDieuKhienAi;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BoDieuKhienAiService {

    private final List<BoDieuKhienAi> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public BoDieuKhienAiService() {
        themDuLieuMau();
    }

    private void themDuLieuMau() {
        danhSach.add(BoDieuKhienAi.builder()
                .maBoieuKhien(idCounter.getAndIncrement())
                .tenChucNang("Khuyến nghị phòng")
                .moTa("AI gợi ý các phòng trọ phù hợp dựa trên sở thích của người dùng")
                .trangThaiHoatDong(1)
                .mucDoTienTien(8)
                .soLanSuDung(1245)
                .cauHinhThamSo("{\"max_distance\": 5, \"price_range\": \"1-3M\", \"min_rating\": 4}")
                .ngayTao(LocalDateTime.now().minusDays(90))
                .ngayCapNhat(LocalDateTime.now())
                .build());

        danhSach.add(BoDieuKhienAi.builder()
                .maBoieuKhien(idCounter.getAndIncrement())
                .tenChucNang("Phát hiện gian lận")
                .moTa("AI phát hiện các tin đăng hoặc tài khoản nghi vấn gian lận")
                .trangThaiHoatDong(1)
                .mucDoTienTien(9)
                .soLanSuDung(856)
                .cauHinhThamSo("{\"threshold\": 0.75, \"check_frequency\": \"hourly\"}")
                .ngayTao(LocalDateTime.now().minusDays(60))
                .ngayCapNhat(LocalDateTime.now())
                .build());

        danhSach.add(BoDieuKhienAi.builder()
                .maBoieuKhien(idCounter.getAndIncrement())
                .tenChucNang("Duyệt nội dung tự động")
                .moTa("AI tự động duyệt hoặc từ chối các bài đăng dựa trên quy tắc")
                .trangThaiHoatDong(1)
                .mucDoTienTien(7)
                .soLanSuDung(3421)
                .cauHinhThamSo("{\"auto_approve\": true, \"review_level\": \"moderate\"}")
                .ngayTao(LocalDateTime.now().minusDays(30))
                .ngayCapNhat(LocalDateTime.now())
                .build());

        danhSach.add(BoDieuKhienAi.builder()
                .maBoieuKhien(idCounter.getAndIncrement())
                .tenChucNang("Phân tích xu hướng thị trường")
                .moTa("AI phân tích giá cả và xu hướng thị trường nhà trọ")
                .trangThaiHoatDong(0) // Tắt
                .mucDoTienTien(6)
                .soLanSuDung(234)
                .cauHinhThamSo("{\"update_frequency\": \"daily\", \"regions\": [\"HCM\", \"HN\"]}")
                .ngayTao(LocalDateTime.now().minusDays(15))
                .ngayCapNhat(LocalDateTime.now())
                .build());
    }

    public List<BoDieuKhienAi> getAll() {
        return danhSach;
    }

    public Optional<BoDieuKhienAi> getById(Integer id) {
        return danhSach.stream().filter(b -> b.getMaBoieuKhien().equals(id)).findFirst();
    }

    public BoDieuKhienAi create(BoDieuKhienAi boDieuKhienAi) {
        boDieuKhienAi.setMaBoieuKhien(idCounter.getAndIncrement());
        boDieuKhienAi.setNgayTao(LocalDateTime.now());
        boDieuKhienAi.setNgayCapNhat(LocalDateTime.now());
        boDieuKhienAi.setSoLanSuDung(0);
        danhSach.add(boDieuKhienAi);
        return boDieuKhienAi;
    }

    public Optional<BoDieuKhienAi> update(Integer id, BoDieuKhienAi duLieuMoi) {
        return getById(id).map(b -> {
            b.setTenChucNang(duLieuMoi.getTenChucNang());
            b.setTrangThaiHoatDong(duLieuMoi.getTrangThaiHoatDong());
            b.setMucDoTienTien(duLieuMoi.getMucDoTienTien());
            b.setCauHinhThamSo(duLieuMoi.getCauHinhThamSo());
            b.setNgayCapNhat(LocalDateTime.now());
            return b;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(b -> b.getMaBoieuKhien().equals(id));
    }
}
