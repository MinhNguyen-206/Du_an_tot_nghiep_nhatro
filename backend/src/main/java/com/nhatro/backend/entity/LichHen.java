package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lich_hen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichHen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_lich_hen")
    private Integer maLichHen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_thue", nullable = false)
    private NguoiDung nguoiThue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_dang_tin", nullable = false)
    private DangTin dangTin;

    @Column(name = "ngay_gio_hen", nullable = false)
    private LocalDateTime ngayGioHen;

    @Column(name = "so_nguoi_di_cung")
    private Integer soNguoiDiCung;

    @Column(name = "loi_nhan", columnDefinition = "NVARCHAR(MAX)")
    private String loiNhan;

    @Column(name = "ly_do_tu_choi", columnDefinition = "NVARCHAR(MAX)")
    private String lyDoTuChoi;

    @Column(name = "trang_thai_lich_hen", nullable = false)
    private Integer trangThaiLichHen;
}