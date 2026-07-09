package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "giao_dich_thanh_toan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiaoDichThanhToan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_giao_dich")
    private Integer maGiaoDich;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_hoa_don")
    private HoaDonThang hoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_thanh_toan_coc")
    private ThanhToanCoc thanhToanCoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_thanh_toan", nullable = false)
    private NguoiDung nguoiThanhToan;

    @Column(name = "so_tien", nullable = false)
    private BigDecimal soTien;

    @Column(name = "phuong_thuc_thanh_toan", nullable = false)
    private String phuongThucThanhToan; // Ví dụ: CHUYEN_KHOAN, TIEN_MAT, MOMO, ZALOPAY

    @Column(name = "ma_tham_chieu", length = 100)
    private String maThiamChieu; // Transaction ID từ payment gateway

    @Column(name = "trang_thai_thanh_toan", nullable = false)
    private Integer trangThaiThanhToan; // 0: Chưa xác nhận, 1: Đã xác nhận, 2: Thất bại

    @Column(name = "ghi_chu", columnDefinition = "NVARCHAR(MAX)")
    private String ghiChu;

    @Column(name = "thoi_gian_tao")
    private LocalDateTime thoiGianTao;

    @Column(name = "thoi_gian_xac_nhan")
    private LocalDateTime thoiGianXacNhan;
}
