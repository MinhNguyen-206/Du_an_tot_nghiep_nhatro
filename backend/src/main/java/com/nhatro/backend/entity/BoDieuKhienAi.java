package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bo_dieu_khien_ai")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoDieuKhienAi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_bo_dieu_khien")
    private Integer maBoieuKhien;

    @Column(name = "ten_chuc_nang", nullable = false)
    private String tenChucNang;

    @Column(name = "mo_ta", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;

    @Column(name = "trang_thai_hoat_dong", nullable = false)
    private Integer trangThaiHoatDong; // 0: Tắt, 1: Bật

    @Column(name = "muc_do_tien_tien", nullable = false)
    private Integer mucDoTienTien; // 1-10

    @Column(name = "so_lan_su_dung")
    private Integer soLanSuDung;

    @Column(name = "cau_hinh_tham_so", columnDefinition = "NVARCHAR(MAX)")
    private String cauHinhThamSo; // JSON format

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;
}
