package com.nhatro.backend.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    // ===== Bổ sung cho form "Yêu cầu thuê phòng" =====

    @Column(name = "hinhThucThue", length = 20)
    @Builder.Default
    private String hinhThucThue = "DON";

    @Column(name = "soNguoiCung")
    private Integer soNguoiCung;

    @Column(name = "thoiHanThue")
    private Integer thoiHanThue;

    @Column(name = "donViThoiHan", length = 20)
    @Builder.Default
    private String donViThoiHan = "Tháng";

    @Column(name = "soDienThoaiLienHe", length = 20)
    private String soDienThoaiLienHe;
}