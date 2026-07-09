package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cau_hinh_danh_muc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CauHinhDanhMuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_cau_hinh")
    private Integer maCauHinh;

    @Column(name = "ten_danh_muc", nullable = false)
    private String tenDanhMuc; // Ví dụ: LOAI_PHONG, TRANG_THAI_PHONG, VAI_TRO, ...

    @Column(name = "gia_tri_khoa", nullable = false)
    private String giaTriKhoa; // Ví dụ: PHONG_DOI, PHONG_RIENG

    @Column(name = "gia_tri_hien_thi", nullable = false)
    private String giaTriHienThi; // Ví dụ: Phòng Đôi, Phòng Riêng

    @Column(name = "mo_ta", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai; // 0: Không hoạt động, 1: Hoạt động

    @Column(name = "thu_tu_sap_xep")
    private Integer thuTuSapXep;

    @Column(name = "ghi_chu", columnDefinition = "NVARCHAR(MAX)")
    private String ghiChu;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;
}
