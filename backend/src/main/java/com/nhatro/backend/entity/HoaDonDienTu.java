package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "HOA_DON_DIEN_TU")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonDienTu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maHoaDonDienTu")
    private Integer maHoaDonDienTu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maHoaDon", nullable = false)
    private HoaDonThang hoaDon;

    @Column(name = "maSoThue", length = 50)
    private String maSoThue;

    @Column(name = "soHoaDon", length = 100)
    private String soHoaDon;

    @Column(name = "filePDF", length = 500)
    private String filePDF;

    @Column(name = "ngayPhatHanh")
    private LocalDateTime ngayPhatHanh;
}
