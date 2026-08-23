package com.nhatro.backend.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MA_OTP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maOtp")
    private Integer maOtp;

    @Column(name = "maHopDong", nullable = false)
    private Integer maHopDong;

    @Column(name = "vaiTroKy", length = 20, nullable = false)
    private String vaiTroKy; // "CHU_TRO" hoac "NGUOI_THUE"

    @Column(name = "maNguoiDung", nullable = false)
    private Integer maNguoiDung;

    @Column(name = "maSo", length = 6, nullable = false)
    private String maSo;

    @CreationTimestamp
    @Column(name = "ngayTao", updatable = false)
    private LocalDateTime ngayTao;

    @Column(name = "hetHan", nullable = false)
    private LocalDateTime hetHan;

    @Column(name = "daSuDung", nullable = false)
    @Builder.Default
    private Boolean daSuDung = false;
}