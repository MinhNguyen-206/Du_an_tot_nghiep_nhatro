package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "dang_ky_goi_chu_tro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DangKyGoiChuTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_dang_ky")
    private Integer maDangKy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_dung", nullable = false)
    private NguoiDung nguoiDung; // chu tro dang ky goi

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_goi", nullable = false)
    private GoiPremium goi;

    // KHONG lien ket toi GiaoDichThanhToan (bang danh cho thanh toan thue phong/coc).
    // Thanh toan Premium duoc ghi nhan rieng qua HoaDonDienTuPremium de tach biet
    // hoan toan 2 luong tien: thue phong (nguoi thue <-> chu tro) va Premium (chu tro <-> he thong).

    @Column(name = "ngay_bat_dau")
    private LocalDateTime ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private LocalDateTime ngayKetThuc;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai; // 0: Cho thanh toan, 1: Dang hoat dong, 2: Da het han, 3: Da huy

    @Column(name = "so_tin_da_dang")
    private Integer soTinDaDang;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;
}