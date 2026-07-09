package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lich_su_goi_chu_tro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichSuGoiChuTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_lich_su_goi")
    private Integer maLichSuGoi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_thue", nullable = false)
    private NguoiDung nguoiThue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_chu_tro", nullable = false)
    private NguoiDung chuTro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_phong", nullable = false)
    private PhongTro phong;

    @Column(name = "thoi_gian_goi")
    private LocalDateTime thoiGianGoi;

    @Column(name = "thoi_luong_goi")
    private Integer thoiLuongGoi; // Giây

    @Column(name = "status_cuoc_goi")
    private Integer statusCuocGoi; // 0: Chưa nghe, 1: Đã nghe, 2: Cuộc gọi nhỡ

    @Column(name = "ghi_chu", columnDefinition = "NVARCHAR(MAX)")
    private String ghiChu;
}
