package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "XAC_THUC_EKYC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class XacThucEkyc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maEKYC")
    private Integer maEKYC;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maNguoiDung", nullable = false)
    private NguoiDung nguoiDung;

    @Column(name = "soCCCD", length = 20)
    private String soCCCD;

    @Column(name = "anhMatTruoc", length = 500)
    private String anhMatTruoc;

    @Column(name = "anhMatSau", length = 500)
    private String anhMatSau;

    @Column(name = "anhChanDung", length = 500)
    private String anhChanDung;

    @Column(name = "ketQua", length = 100)
    private String ketQua;

    @Column(name = "trangThai")
    @Builder.Default
    private Boolean trangThai = false;

    @CreationTimestamp
    @Column(name = "ngayGui", updatable = false)
    private LocalDateTime ngayGui;

    @Column(name = "ngayDuyet")
    private LocalDateTime ngayDuyet;
}
