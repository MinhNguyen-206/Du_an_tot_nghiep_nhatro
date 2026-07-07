package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "nha_tro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhaTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_nha_tro")
    private Integer maNhaTro;

    @Column(name = "ten_nha_tro", nullable = false, length = 150)
    private String tenNhaTro;

    @Column(name = "dia_chi", nullable = false)
    private String diaChi;

    @Column(name = "so_tang")
    private Integer soTang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_chu_tro", nullable = false)
    private NguoiDung chuTro;

    @Column(name = "ngay_tao", insertable = false, updatable = false)
    private LocalDateTime ngayTao;
}
