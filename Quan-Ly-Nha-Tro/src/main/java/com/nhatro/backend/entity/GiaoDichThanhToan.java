package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "GIAO_DICH_THANH_TOAN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiaoDichThanhToan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maGiaoDich")
    private Integer maGiaoDich;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maThanhToan", nullable = false)
    private ThanhToanTienTro thanhToan;

    @Column(name = "maGiaoDichCongThanhToan", length = 255)
    private String maGiaoDichCongThanhToan;

    @Column(name = "nganHang", length = 100)
    private String nganHang;

    @Column(name = "noiDung", length = 500)
    private String noiDung;

    @Column(name = "ngayGiaoDich")
    private LocalDateTime ngayGiaoDich;

    @Column(name = "trangThai", length = 50)
    private String trangThai;
}
