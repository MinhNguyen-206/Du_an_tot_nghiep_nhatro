package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hoa_don_dien_tu_premium")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonDienTuPremium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hoa_don")
    private Integer maHoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_dung", nullable = false)
    private NguoiDung nguoiDung;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_goi", nullable = false)
    private GoiPremium goi;

    @Column(name = "so_tien", nullable = false)
    private BigDecimal soTien;

    @Column(name = "cong_thanh_toan")
    private String congThanhToan; // VD: MOMO, VNPAY, ZALOPAY, CHUYEN_KHOAN

    @Column(name = "ma_giao_dich_ngoai")
    private String maGiaoDichNgoai; // ma giao dich phia cong thanh toan tra ve

    @Column(name = "ngay_thanh_toan")
    private LocalDateTime ngayThanhToan;

    @Column(name = "trang_thai_thanh_toan", nullable = false)
    private Integer trangThaiThanhToan; // 0: Cho thanh toan, 1: Da thanh toan, 2: That bai, 3: Da hoan tien

    @Column(name = "duong_dan_file_hoa_don")
    private String duongDanFileHoaDon;

    @Column(name = "loai_hoa_don", nullable = false)
    private Integer loaiHoaDon; // 1: Dang ky moi, 2: Gia han
}
