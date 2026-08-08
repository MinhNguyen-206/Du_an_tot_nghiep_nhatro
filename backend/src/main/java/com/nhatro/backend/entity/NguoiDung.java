package com.nhatro.backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "NGUOI_DUNG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NguoiDung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maNguoiDung")
    private Integer maNguoiDung;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maVaiTro", nullable = false)
    private VaiTro vaiTro;

    @Column(name = "hoTen", nullable = false, length = 255)
    private String hoTen;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "soDienThoai", unique = true, length = 20)
    private String soDienThoai;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "matKhau", nullable = false, length = 255)
    private String matKhau;

    @Column(name = "avatar", length = 500)
    private String avatar;

    @Column(name = "gioiTinh")
    private Boolean gioiTinh;

    @Column(name = "ngaySinh")
    private LocalDate ngaySinh;

    @Column(name = "diaChi", length = 500)
    private String diaChi;

    @Column(name = "trangThai", nullable = false)
    @Builder.Default
    private Boolean trangThai = true;

    @CreationTimestamp
    @Column(name = "ngayDangKy", nullable = false, updatable = false)
    private LocalDateTime ngayDangKy;

    @Column(name = "ngayCapNhat")
    private LocalDateTime ngayCapNhat;
}
