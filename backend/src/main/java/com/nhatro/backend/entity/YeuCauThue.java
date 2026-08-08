package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "YEU_CAU_THUE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YeuCauThue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maYeuCau")
    private Integer maYeuCau;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maPhong", nullable = false)
    private PhongTro phong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiThue", nullable = false)
    private NguoiDung nguoiThue;

    @CreationTimestamp
    @Column(name = "ngayGui", updatable = false)
    private LocalDateTime ngayGui;

    @Column(name = "ngayMuonNhanPhong")
    private java.time.LocalDate ngayMuonNhanPhong;

    @Column(name = "ghiChu", length = 1000)
    private String ghiChu;

    @Column(name = "trangThai", length = 50)
    @Builder.Default
    private String trangThai = "Chờ duyệt";
}