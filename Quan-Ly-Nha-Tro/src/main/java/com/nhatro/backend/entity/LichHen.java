package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "LICH_HEN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichHen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maLichHen")
    private Integer maLichHen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDung", nullable = false)
    private NguoiDung nguoiDung;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maPhong", nullable = false)
    private PhongTro phong;

    @Column(name = "ngayHen")
    private LocalDate ngayHen;

    @Column(name = "gioHen")
    private LocalTime gioHen;

    @Column(name = "diaDiem", length = 255)
    private String diaDiem;

    @Column(name = "ghiChu", length = 1000)
    private String ghiChu;

    @Column(name = "trangThai")
    @Builder.Default
    private Boolean trangThai = false;
}