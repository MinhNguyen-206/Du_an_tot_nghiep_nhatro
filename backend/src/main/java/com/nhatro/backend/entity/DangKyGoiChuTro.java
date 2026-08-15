package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "DANG_KY_GOI_CHU_TRO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DangKyGoiChuTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDangKy")
    private Integer maDangKy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maChuTro", nullable = false)
    private NguoiDung chuTro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maGoi", nullable = false)
    private GoiDichVu goi;

    @CreationTimestamp
    @Column(name = "ngayDangKy", updatable = false)
    private LocalDateTime ngayDangKy;

    @Column(name = "ngayHetHan")
    private LocalDateTime ngayHetHan;

    @Column(name = "trangThai", length = 50)
    private String trangThai;
}