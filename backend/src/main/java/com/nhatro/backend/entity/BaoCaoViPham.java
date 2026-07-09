package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bao_cao_vi_pham")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaoCaoViPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_bao_cao")
    private Integer maBaoCao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_to_cao", nullable = false)
    private NguoiDung nguoiToCao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_bi_to_cao")
    private NguoiDung biToCao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_dang_tin")
    private DangTin dangTin;

    @Column(name = "loai_vi_pham")
    private String loaiViPham;

    @Column(name = "mo_ta", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;

    @Column(name = "trang_thai_xu_ly", nullable = false)
    private Integer trangThaiXuLy;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;
}