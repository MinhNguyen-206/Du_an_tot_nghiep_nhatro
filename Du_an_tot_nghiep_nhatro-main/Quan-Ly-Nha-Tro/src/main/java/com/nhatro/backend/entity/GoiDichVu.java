package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "GOI_DICH_VU")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoiDichVu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maGoi")
    private Integer maGoi;

    @Column(name = "tenGoi", length = 255)
    private String tenGoi;

    @Column(name = "gia", precision = 18, scale = 2)
    private BigDecimal gia;

    @Column(name = "thoiHan")
    private Integer thoiHan;

    @Column(name = "soLuongTin")
    private Integer soLuongTin;

    @Column(name = "uuTien")
    @Builder.Default
    private Boolean uuTien = false;

    @Column(name = "moTa", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;

    @Column(name = "trangThai")
    @Builder.Default
    private Boolean trangThai = true;
}
