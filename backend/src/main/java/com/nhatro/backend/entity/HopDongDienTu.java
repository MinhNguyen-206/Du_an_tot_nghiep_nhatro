package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "HOP_DONG_DIEN_TU")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HopDongDienTu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maHopDong")
    private Integer maHopDong;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maPhong", nullable = false)
    private PhongTro phong;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maChuTro", nullable = false)
    private NguoiDung chuTro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maNguoiThue", nullable = false)
    private NguoiDung nguoiThue;

    @Column(name = "ngayBatDau")
    private LocalDate ngayBatDau;

    @Column(name = "ngayKetThuc")
    private LocalDate ngayKetThuc;

    @Column(name = "tienCoc", precision = 18, scale = 2)
    private BigDecimal tienCoc;

    @Column(name = "giaThue", precision = 18, scale = 2)
    private BigDecimal giaThue;

    @Column(name = "fileHopDong", length = 500)
    private String fileHopDong;

    @Column(name = "trangThai", length = 50)
    private String trangThai;

    @Column(name = "ngayKy")
    private LocalDateTime ngayKy;
}