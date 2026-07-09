package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "yeu_cau_thue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YeuCauThue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_yeu_cau")
    private Integer maYeuCau;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_thue", nullable = false)
    private NguoiDung nguoiThue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_dang_tin", nullable = false)
    private DangTin dangTin;

    @Column(name = "thoi_han_thue_mong_muon")
    private Integer thoiHanThueMongMuon;

    @Column(name = "ngay_du_kien_don_vao")
    private LocalDate ngayDuKienDonVao;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai;

    @Column(name = "trang_thai_coc", nullable = false)
    private Integer trangThaiCoc;

    @Column(name = "ghi_chu", columnDefinition = "NVARCHAR(MAX)")
    private String ghiChu;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;
}