package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hoa_don_thang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonThang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hoa_don")
    private Integer maHoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_hop_dong", nullable = false)
    private HopDongDienTu hopDong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_phong", nullable = false)
    private PhongTro phong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_chu_tro", nullable = false)
    private NguoiDung chuTro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_thue", nullable = false)
    private NguoiDung nguoiThue;

    @Column(name = "thang_nam", nullable = false, length = 7)
    private String thangNam;

    @Column(name = "tien_xe")
    private BigDecimal tienXe;

    @Column(name = "tien_wifi")
    private BigDecimal tienWifi;

    @Column(name = "tien_phong")
    private BigDecimal tienPhong;

    @Column(name = "tong_tien", nullable = false)
    private BigDecimal tongTien;

    @Column(name = "trang_thai_thanh_toan", nullable = false)
    private Integer trangThaiThanhToan;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_thanh_toan")
    private LocalDateTime ngayThanhToan;

    @UpdateTimestamp
    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;
}