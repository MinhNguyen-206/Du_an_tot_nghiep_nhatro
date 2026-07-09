package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hop_dong_dien_tu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HopDongDienTu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hop_dong")
    private Integer maHopDong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_yeu_cau")
    private YeuCauThue yeuCau;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_phong", nullable = false)
    private PhongTro phong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_thue", nullable = false)
    private NguoiDung nguoiThue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_chu_tro", nullable = false)
    private NguoiDung chuTro;

    @Column(name = "ngay_bat_dau", nullable = false)
    private LocalDateTime ngayBatDau;

    @Column(name = "ngay_ket_thuc", nullable = false)
    private LocalDateTime ngayKetThuc;

    @Column(name = "ngay_cham_dut")
    private LocalDateTime ngayChamDut;

    @Column(name = "ly_do_cham_dut", columnDefinition = "NVARCHAR(MAX)")
    private String lyDoChamDut;

    @Column(name = "thoi_han_thue")
    private Integer thoiHanThue;

    @Column(name = "gia_thue", nullable = false)
    private BigDecimal giaThue;

    @Column(name = "tien_coc", nullable = false)
    private BigDecimal tienCoc;

    @Column(name = "dieu_khoan_vi_pham", columnDefinition = "NVARCHAR(MAX)")
    private String dieuKhoanViPham;

    @Column(name = "chu_ky_chu_tro")
    private String chuKyChuTro;

    @Column(name = "chu_ky_nguoi_thue")
    private String chuKyNguoiThue;

    @Column(name = "duong_dan_file_pdf")
    private String duongDanFilePdf;

    @Column(name = "trang_thai_hop_dong", nullable = false)
    private Integer trangThaiHopDong;

    @UpdateTimestamp
    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;
}