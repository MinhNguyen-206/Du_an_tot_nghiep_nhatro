package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "PHONG_TRO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhongTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maPhong")
    private Integer maPhong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNhaTro", nullable = false)
    private NhaTro nhaTro;

    @Column(name = "tenPhong", nullable = false, length = 255)
    private String tenPhong;

    @Column(name = "dienTich", precision = 10, scale = 2)
    private BigDecimal dienTich;

    @Column(name = "loaiPhong", length = 100)
    private String loaiPhong;

    @Column(name = "soLuongNguoi")
    private Integer soLuongNguoi;

    @Column(name = "giaPhong", precision = 18, scale = 2)
    private BigDecimal giaPhong;

    @Column(name = "giaDien", precision = 18, scale = 2)
    private BigDecimal giaDien;

    @Column(name = "giaNuoc", precision = 18, scale = 2)
    private BigDecimal giaNuoc;

    @Column(name = "giaGuiXe", precision = 18, scale = 2)
    private BigDecimal giaGuiXe;

    @Column(name = "giaInternet", precision = 18, scale = 2)
    private BigDecimal giaInternet;

    @Column(name = "trangThai")
    @Builder.Default
    private Boolean trangThai = true;
}
