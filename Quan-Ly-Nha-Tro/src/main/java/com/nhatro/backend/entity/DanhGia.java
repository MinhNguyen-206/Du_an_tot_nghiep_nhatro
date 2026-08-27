package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "DANH_GIA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDanhGia")
    private Integer maDanhGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDung", nullable = false)
    private NguoiDung nguoiDung;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maPhong", nullable = false)
    private PhongTro phong;

    @Column(name = "soSao", nullable = false)
    private Integer soSao;

    @Column(name = "noiDung", columnDefinition = "NVARCHAR(MAX)")
    private String noiDung;

    @CreationTimestamp
    @Column(name = "ngayDanhGia", updatable = false)
    private LocalDateTime ngayDanhGia;

    @Column(name = "trangThai")
    @Builder.Default
    private Boolean trangThai = true;

    // Ly do bi bao cao (nguoi dung khac report, hoac Admin tu ghi nhan khi
    // phat hien spam/vi pham). NULL = danh gia binh thuong, chua bi bao cao.
    // Con trangThai=false nghia la Admin da AN danh gia nay khoi cong khai.
    @Column(name = "lyDoBaoCao", length = 500)
    private String lyDoBaoCao;
}