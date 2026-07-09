package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "nhat_ky_hoat_dong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhatKyHoatDong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_nhat_ky")
    private Integer maNhatKy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_dung", nullable = false)
    private NguoiDung nguoiDung;

    @Column(name = "loai_hoat_dong", nullable = false)
    private String loaiHoatDong; // Ví dụ: DANG_PHONG, TIM_PHONG, THANH_TOAN, ...

    @Column(name = "chi_tiet", columnDefinition = "NVARCHAR(MAX)")
    private String chiTiet;

    @Column(name = "dia_chi_ip")
    private String diaChiIp;

    @Column(name = "thoi_gian_tao")
    private LocalDateTime thoiGianTao;

    @Column(name = "trang_thai")
    private Integer trangThai; // 0: Bình thường, 1: Cảnh báo, 2: Lỗi
}
