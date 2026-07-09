package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "goi_dich_vu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoiDichVu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_goi_dv")
    private Integer maGoiDv;

    @Column(name = "ten_goi_dv", nullable = false)
    private String tenGoiDv;

    @Column(name = "mo_ta", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;

    @Column(name = "loai_goi_dv", nullable = false)
    private String loaiGoiDv; // VIP, PREMIUM, STANDARD

    @Column(name = "gia_goi_dv", nullable = false)
    private BigDecimal giaGoiDv;

    @Column(name = "thoi_han_days", nullable = false)
    private Integer thoiHanDays; // Số ngày sử dụng

    @Column(name = "tinh_nang", columnDefinition = "NVARCHAR(MAX)")
    private String tinhNang; // JSON hoặc comma-separated

    @Column(name = "muc_uu_tien", nullable = false)
    private Integer mucUuTien; // 1-5, 5 là cao nhất

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai; // 0: Không hoạt động, 1: Hoạt động

    @Column(name = "so_lan_su_dung")
    private Integer soLanSuDung;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;
}
