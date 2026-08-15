package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "HOP_DONG_PREMIUM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HopDongPremium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maHopDongPremium")
    private Integer maHopDongPremium;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maDangKy", nullable = false)
    private DangKyGoiChuTro dangKy;

    @Column(name = "soHopDong", length = 100)
    private String soHopDong;

    @Column(name = "ngayKy")
    private LocalDateTime ngayKy;

    @Column(name = "ngayBatDau")
    private LocalDateTime ngayBatDau;

    @Column(name = "ngayKetThuc")
    private LocalDateTime ngayKetThuc;

    @Column(name = "fileHopDong", length = 500)
    private String fileHopDong;

    @Column(name = "trangThai", length = 50)
    private String trangThai;
}
