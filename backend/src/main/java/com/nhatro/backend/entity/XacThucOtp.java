package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "xac_thuc_otp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class XacThucOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_otp")
    private Integer maOtp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_dung", nullable = false)
    private NguoiDung nguoiDung;

    @Column(name = "ma_otp_code", nullable = false, length = 10)
    private String maOtpCode;

    @Column(name = "loai_xac_thuc", nullable = false)
    private String loaiXacThuc; // EMAIL, SMS, APP

    @Column(name = "dich_vu_xac_thuc", nullable = false)
    private String dichVuXacThuc; // DANG_KY, DANG_NHAP, THAY_DOI_MAT_KHAU, THANH_TOAN

    @Column(name = "so_dien_thoai", length = 20)
    private String soDienThoai;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "so_lan_thu")
    private Integer soLanThu;

    @Column(name = "max_thu")
    private Integer maxThu; // Số lần thử tối đa

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai; // 0: Chưa xác thực, 1: Đã xác thực, 2: Hết hạn

    @Column(name = "thoi_gian_tao")
    private LocalDateTime thoiGianTao;

    @Column(name = "thoi_gian_het_han")
    private LocalDateTime thoiGianHetHan;

    @Column(name = "thoi_gian_xac_thuc")
    private LocalDateTime thoiGianXacThuc;
}
