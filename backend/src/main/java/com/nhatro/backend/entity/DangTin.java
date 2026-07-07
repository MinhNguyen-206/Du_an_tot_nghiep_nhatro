package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "dang_tin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DangTin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_dang_tin")
    private Integer maDangTin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_phong", nullable = false)
    private PhongTro phong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nguoi_dung", nullable = false)
    private NguoiDung nguoiDung;

    @Column(name = "tieu_de", nullable = false, length = 200)
    private String tieuDe;

    @Column(name = "mo_ta_chi_tiet", columnDefinition = "NVARCHAR(MAX)")
    private String moTaChiTiet;

    @Column(name = "danh_sach_anh", columnDefinition = "NVARCHAR(MAX)")
    private String danhSachAnh;

    @Column(name = "video")
    private String video;

    @Column(name = "tien_ich", columnDefinition = "NVARCHAR(MAX)")
    private String tienIch;

    @Column(name = "toa_do_ban_do", length = 100)
    private String toaDoBanDo;

    @Column(name = "trang_thai_kiem_duyet", nullable = false)
    private Integer trangThaiKiemDuyet;

    @Column(name = "trang_thai_hien_thi", nullable = false)
    private Integer trangThaiHienThi;

    @Column(name = "so_luot_xem", nullable = false)
    private Integer soLuotXem;

    @Column(name = "is_vip", nullable = false)
    private Boolean isVip;

    @CreationTimestamp
    @Column(name = "ngay_tao", updatable = false)
    private LocalDateTime ngayTao;

    @UpdateTimestamp
    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;
}
