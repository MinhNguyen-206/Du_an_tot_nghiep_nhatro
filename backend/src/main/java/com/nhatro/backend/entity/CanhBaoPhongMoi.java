package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "canh_bao_phong_moi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CanhBaoPhongMoi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_canh_bao")
    private Integer maCanhBao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_dung", nullable = false)
    private NguoiDung nguoiDung; // nguoi thue thiet lap canh bao

    @Column(name = "khu_vuc")
    private String khuVuc;

    @Column(name = "khoang_gia")
    private String khoangGia; // VD: "1000000-3000000"

    @Column(name = "loai_phong")
    private String loaiPhong;

    @Column(name = "tien_ich", columnDefinition = "NVARCHAR(MAX)")
    private String tienIch; // danh sach tien ich mong muon, cach nhau boi dau phay

    @Column(name = "trang_thai_hoat_dong", nullable = false)
    private Boolean trangThaiHoatDong;
}
