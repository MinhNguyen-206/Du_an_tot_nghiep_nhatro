package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "chi_so_dien_nuoc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiSoDienNuoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_chi_so")
    private Integer maChiSo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_hoa_don", nullable = false)
    private HoaDonThang hoaDon;

    @Column(name = "so_dien_cu")
    private Double soDienCu;

    @Column(name = "so_dien_moi")
    private Double soDienMoi;

    @Column(name = "so_nuoc_cu")
    private Double soNuocCu;

    @Column(name = "so_nuoc_moi")
    private Double soNuocMoi;

    @Column(name = "tien_dien")
    private BigDecimal tienDien;

    @Column(name = "tien_nuoc")
    private BigDecimal tienNuoc;

    @Column(name = "anh_chi_so_dien")
    private String anhChiSoDien;

    @Column(name = "anh_chi_so_nuoc")
    private String anhChiSoNuoc;

    @Column(name = "ngay_ghi_chi_so")
    private LocalDateTime ngayGhiChiSo;
}