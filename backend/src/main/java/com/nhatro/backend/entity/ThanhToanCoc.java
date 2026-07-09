package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "thanh_toan_coc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThanhToanCoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_thanh_toan")
    private Integer maThanhToan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_hop_dong", nullable = false)
    private HopDongDienTu hopDong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_thue", nullable = false)
    private NguoiDung nguoiThue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_chu_tro", nullable = false)
    private NguoiDung chuTro;

    @Column(name = "so_tien_coc", nullable = false)
    private BigDecimal soTienCoc;

    @Column(name = "phuong_thuc_thanh_toan")
    private String phuongThucThanhToan;

    @Column(name = "trang_thai_coc", nullable = false)
    private Integer trangThaiCoc;

    @Column(name = "ngay_thanh_toan")
    private LocalDateTime ngayThanhToan;

    @Column(name = "ghi_chu", columnDefinition = "NVARCHAR(MAX)")
    private String ghiChu;
}