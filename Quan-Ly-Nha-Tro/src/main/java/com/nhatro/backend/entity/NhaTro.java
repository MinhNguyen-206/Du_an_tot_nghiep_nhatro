package com.nhatro.backend.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "tenNhaTro", length = 200, nullable = false)
    private String tenNhaTro;

    @Column(name = "diaChi", length = 500)
    private String diaChi;

    @Column(name = "soSao", precision = 2, scale = 1)
    private BigDecimal soSao;

    @Column(name = "moTa", columnDefinition = "nvarchar(max)")
    private String moTa;

    @Column(name = "giaPhong", precision = 18, scale = 2)
    private BigDecimal giaPhong;

    @Column(name = "loaiPhong", length = 100)
    private String loaiPhong;

    @Column(name = "hinhAnh", length = 2000)
    private String hinhAnh;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "NHA_TRO_TIEN_ICH",
            joinColumns = @JoinColumn(name = "maNhaTro"),
            inverseJoinColumns = @JoinColumn(name = "maTienIch")
    )
    @Builder.Default
    private Set<TienIch> danhSachTienIch = new HashSet<>();
}