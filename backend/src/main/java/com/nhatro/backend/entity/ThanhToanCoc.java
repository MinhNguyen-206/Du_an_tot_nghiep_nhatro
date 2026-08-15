package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "THANH_TOAN_COC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThanhToanCoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maThanhToanCoc")
    private Integer maThanhToanCoc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maHopDong", nullable = false)
    private HopDongDienTu hopDong;

    @Column(name = "soTien", precision = 18, scale = 2)
    private BigDecimal soTien;

    @Column(name = "ngayThanhToan")
    private LocalDateTime ngayThanhToan;

    @Column(name = "phuongThuc", length = 100)
    private String phuongThuc;

    @Column(name = "trangThai", length = 50)
    private String trangThai;
}