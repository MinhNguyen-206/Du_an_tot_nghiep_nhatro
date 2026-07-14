package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "nguoi_dung")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NguoiDung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_nguoi_dung")
    private Integer maNguoiDung;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "so_dien_thoai", length = 20)
    private String soDienThoai;

    @com.fasterxml.jackson.annotation.JsonProperty(access = com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY)
    @Column(name = "mat_khau_ma_hoa", nullable = false)
    private String matKhauMaHoa;

    @Column(name = "ho_ten", nullable = false, length = 150)
    private String hoTen;

    @Column(name = "anh_dai_dien")
    private String anhDaiDien;

    @Column(name = "dia_chi_thuong_tru")
    private String diaChiThuongTru;

    @Column(name = "vai_tro", nullable = false)
    private Integer vaiTro;

    @Column(name = "trang_thai_tai_khoan", nullable = false)
    private Integer trangThaiTaiKhoan;

    @Column(name = "da_xac_minh_ekyc", nullable = false)
    private Boolean daXacMinhEkyc;

    @Column(name = "nguon_dang_nhap", length = 30)
    private String nguonDangNhap;

    @CreationTimestamp
    @Column(name = "ngay_tao", updatable = false)
    private LocalDateTime ngayTao;

    @UpdateTimestamp
    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;
}
