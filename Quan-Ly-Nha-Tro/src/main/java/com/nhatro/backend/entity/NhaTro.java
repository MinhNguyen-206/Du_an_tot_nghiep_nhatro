package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "NHA_TRO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhaTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maNhaTro")
    private Integer maNhaTro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDung", nullable = false)
    private NguoiDung nguoiDung;

    @Column(name = "tenNhaTro", nullable = false, length = 255)
    private String tenNhaTro;

    @Column(name = "diaChi", nullable = false, length = 500)
    private String diaChi;

    @Column(name = "soSao", precision = 2, scale = 1)
    private BigDecimal soSao;

    @Column(name = "moTa", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;
}
