package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "GIA_HAN_HOP_DONG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiaHanHopDong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maGiaHan")
    private Integer maGiaHan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maHopDong", nullable = false)
    private HopDongDienTu hopDong;

    @Column(name = "ngayBatDauMoi")
    private LocalDate ngayBatDauMoi;

    @Column(name = "ngayKetThucMoi")
    private LocalDate ngayKetThucMoi;

    @Column(name = "soLanGiaHan")
    @Builder.Default
    private Integer soLanGiaHan = 1;

    @Column(name = "ghiChu", length = 1000)
    private String ghiChu;
}
