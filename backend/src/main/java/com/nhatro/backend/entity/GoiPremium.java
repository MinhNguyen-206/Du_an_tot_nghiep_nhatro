package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "goi_premium")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoiPremium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_goi")
    private Integer maGoi;

    @Column(name = "ten_goi", nullable = false)
    private String tenGoi;

    @Column(name = "gia_tien", nullable = false)
    private BigDecimal giaTien;

    @Column(name = "thoi_han_ngay", nullable = false)
    private Integer thoiHanNgay;

    @Column(name = "gioi_han_tin_dang")
    private Integer gioiHanTinDang; // so tin dang toi da duoc dang trong thoi han goi

    @Column(name = "noi_dung_dieu_khoan", columnDefinition = "NVARCHAR(MAX)")
    private String noiDungDieuKhoan;
}
