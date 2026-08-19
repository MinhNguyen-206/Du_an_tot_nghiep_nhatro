package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "HOA_DON_THANG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonThang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maHoaDon")
    private Integer maHoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maHopDong", nullable = false)
    private HopDongDienTu hopDong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maChiSo", nullable = false)
    private ChiSoDienNuoc chiSo;

    @Column(name = "tongTien", precision = 18, scale = 2)
    private BigDecimal tongTien;

    @CreationTimestamp
    @Column(name = "ngayLap", updatable = false)
    private LocalDateTime ngayLap;

    @Column(name = "hanThanhToan")
    private LocalDate hanThanhToan;

    @Column(name = "trangThai", length = 50)
    private String trangThai;
}