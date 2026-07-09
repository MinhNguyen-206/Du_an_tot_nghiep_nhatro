package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quan_tri_vien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuanTriVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_quan_tri")
    private Integer maQuanTri;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_dung", nullable = false)
    private NguoiDung nguoiDung;

    @Column(name = "quyen_han", columnDefinition = "NVARCHAR(MAX)")
    private String quyenHan; // JSON hoặc comma-separated

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai; // 0: Không hoạt động, 1: Hoạt động

    @Column(name = "ghi_chu", columnDefinition = "NVARCHAR(MAX)")
    private String ghiChu;

    @Column(name = "ngay_cap_quyen")
    private LocalDateTime ngayCapQuyen;

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;
}
