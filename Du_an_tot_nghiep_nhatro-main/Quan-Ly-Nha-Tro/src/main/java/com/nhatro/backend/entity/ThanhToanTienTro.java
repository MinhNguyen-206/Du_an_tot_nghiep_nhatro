package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "THANH_TOAN_TIEN_TRO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThanhToanTienTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maThanhToan")
    private Integer maThanhToan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maHoaDon", nullable = false)
    private HoaDonThang hoaDon;

    @Column(name = "soTien", precision = 18, scale = 2)
    private BigDecimal soTien;

    @Column(name = "ngayThanhToan")
    private LocalDateTime ngayThanhToan;

    @Column(name = "phuongThuc", length = 100)
    private String phuongThuc;

    @Column(name = "trangThai", length = 50)
    private String trangThai;
}
