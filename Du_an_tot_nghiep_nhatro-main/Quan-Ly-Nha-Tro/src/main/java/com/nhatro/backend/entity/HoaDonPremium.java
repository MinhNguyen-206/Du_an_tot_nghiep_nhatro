package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "HOA_DON_PREMIUM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonPremium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maHoaDonPremium")
    private Integer maHoaDonPremium;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maHopDongPremium", nullable = false)
    private HopDongPremium hopDongPremium;

    @Column(name = "soTien", precision = 18, scale = 2)
    private BigDecimal soTien;

    @CreationTimestamp
    @Column(name = "ngayLap", updatable = false)
    private LocalDateTime ngayLap;

    @Column(name = "hanThanhToan")
    private LocalDate hanThanhToan;

    @Column(name = "trangThai", length = 50)
    private String trangThai;
}
