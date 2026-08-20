package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

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

    @Column(name = "giaPhong")
    private BigDecimal giaPhong;

    @Column(name = "loaiPhong", length = 100)
    private String loaiPhong;

    @Column(name = "hinhAnh", columnDefinition = "VARCHAR(MAX)")
    private String hinhAnh;

    // Quan hệ nhiều-nhiều với tiện ích, dùng cho chức năng lọc theo tiện ích
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "NHA_TRO_TIEN_ICH",
        joinColumns = @JoinColumn(name = "maNhaTro"),
        inverseJoinColumns = @JoinColumn(name = "maTienIch")
    )
    private Set<TienIch> danhSachTienIch;
}
