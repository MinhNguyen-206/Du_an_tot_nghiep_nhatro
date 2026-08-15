package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "HINH_ANH_DANH_GIA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HinhAnhDanhGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maHinhAnh")
    private Integer maHinhAnh;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maDanhGia", nullable = false)
    private DanhGia danhGia;

    @Column(name = "duongDan", length = 500)
    private String duongDan;

    @Column(name = "moTa", length = 500)
    private String moTa;
}
