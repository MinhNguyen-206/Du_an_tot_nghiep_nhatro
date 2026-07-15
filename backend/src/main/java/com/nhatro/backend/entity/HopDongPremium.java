package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "hop_dong_premium")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HopDongPremium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hop_dong_premium")
    private Integer maHopDongPremium;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_dang_ky", nullable = false)
    private DangKyGoiChuTro dangKy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_dung", nullable = false)
    private NguoiDung nguoiDung;

    @Column(name = "ngay_ky")
    private LocalDateTime ngayKy;

    @Column(name = "noi_dung_dieu_khoan", columnDefinition = "NVARCHAR(MAX)")
    private String noiDungDieuKhoan;

    @Column(name = "chu_ky_nguoi_dung")
    private String chuKyNguoiDung; // duong dan anh chu ky hoac chuoi ma hoa xac nhan

    @Column(name = "duong_dan_file_pdf")
    private String duongDanFilePDF;

    @Column(name = "trang_thai_hop_dong", nullable = false)
    private Integer trangThaiHopDong; // 0: Cho ky, 1: Da ky - hieu luc, 2: Da cham dut

    @Column(name = "ngay_cham_dut")
    private LocalDateTime ngayChamDut;

    @Column(name = "ly_do_cham_dut", columnDefinition = "NVARCHAR(MAX)")
    private String lyDoChamDut;
}
