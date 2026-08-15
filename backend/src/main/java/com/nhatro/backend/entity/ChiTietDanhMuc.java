package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CHI_TIET_DANH_MUC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietDanhMuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maChiTiet")
    private Integer maChiTiet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maDanhMuc", nullable = false)
    private CauHinhDanhMuc danhMuc;

    @Column(name = "tenChiTiet", length = 255)
    private String tenChiTiet;

    @Column(name = "giaTri", length = 500)
    private String giaTri;

    @Column(name = "thuTu")
    private Integer thuTu;
}
