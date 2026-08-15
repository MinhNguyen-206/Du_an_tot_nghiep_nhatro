package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "NHAT_KY_HOAT_DONG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhatKyHoatDong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maNhatKy")
    private Integer maNhatKy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maNguoiDung")
    private NguoiDung nguoiDung;

    @Column(name = "hanhDong", length = 255)
    private String hanhDong;

    @Column(name = "doiTuong", length = 255)
    private String doiTuong;

    @Column(name = "diaChiIP", length = 50)
    private String diaChiIP;

    @CreationTimestamp
    @Column(name = "thoiGian", updatable = false)
    private LocalDateTime thoiGian;
}
