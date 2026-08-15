package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "CHI_SO_DIEN_NUOC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiSoDienNuoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maChiSo")
    private Integer maChiSo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maPhong", nullable = false)
    private PhongTro phong;

    @Column(name = "thang")
    private Integer thang;

    @Column(name = "nam")
    private Integer nam;

    @Column(name = "chiSoDienCu")
    private Integer chiSoDienCu;

    @Column(name = "chiSoDienMoi")
    private Integer chiSoDienMoi;

    @Column(name = "chiSoNuocCu")
    private Integer chiSoNuocCu;

    @Column(name = "chiSoNuocMoi")
    private Integer chiSoNuocMoi;

    @CreationTimestamp
    @Column(name = "ngayNhap", updatable = false)
    private LocalDateTime ngayNhap;
}