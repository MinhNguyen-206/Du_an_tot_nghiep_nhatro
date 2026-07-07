package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "phong_tro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhongTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_phong")
    private Integer maPhong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nha_tro")
    private NhaTro nhaTro;

    @Column(name = "so_phong", nullable = false, length = 20)
    private String soPhong;

    @Column(name = "dien_tich")
    private Double dienTich;

    @Column(name = "loai_phong", length = 50)
    private String loaiPhong;

    @Column(name = "ma_trang_thai", nullable = false)
    private Integer maTrangThai;

    @Column(name = "gia_goc", nullable = false)
    private BigDecimal giaGoc;

    @Column(name = "gia_dien")
    private BigDecimal giaDien;

    @Column(name = "gia_nuoc")
    private BigDecimal giaNuoc;

    @Column(name = "gia_xe")
    private BigDecimal giaXe;

    @Column(name = "gia_wifi")
    private BigDecimal giaWifi;
}
