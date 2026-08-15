package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "DANG_TIN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DangTin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDangTin")
    private Integer maDangTin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maNguoiDung", nullable = false)
    private NguoiDung nguoiDung;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maPhong", nullable = false)
    private PhongTro phong;

    @Column(name = "tieuDe", nullable = false, length = 255)
    private String tieuDe;

    @Column(name = "noiDung", columnDefinition = "NVARCHAR(MAX)")
    private String noiDung;

    @CreationTimestamp
    @Column(name = "ngayDang", updatable = false)
    private LocalDateTime ngayDang;

    @Column(name = "ngayHetHan")
    private LocalDateTime ngayHetHan;

    @Column(name = "trangThai")
    @Builder.Default
    private Boolean trangThai = true;
}
