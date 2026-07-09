package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "danh_gia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_danh_gia")
    private Integer maDanhGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_thue", nullable = false)
    private NguoiDung nguoiThue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_phong", nullable = false)
    private PhongTro phong;

    @Column(name = "so_sao", nullable = false)
    private Integer soSao;

    @Column(name = "noi_dung_binh_luan", columnDefinition = "NVARCHAR(MAX)")
    private String noiDungBinhLuan;

    @Column(name = "anh_thuc_te", columnDefinition = "NVARCHAR(MAX)")
    private String anhThucTe;

    @Column(name = "trang_thai_kiem_duyet", nullable = false)
    private Integer trangThaiKiemDuyet;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;
}