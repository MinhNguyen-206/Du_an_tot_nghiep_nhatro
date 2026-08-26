package com.nhatro.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HOP_DONG_DIEN_TU")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HopDongDienTu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maHopDong")
    private Integer maHopDong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maPhong", nullable = false)
    private PhongTro phong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maChuTro", nullable = false)
    private NguoiDung chuTro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiThue", nullable = false)
    private NguoiDung nguoiThue;

    @Column(name = "ngayBatDau")
    private LocalDate ngayBatDau;

    @Column(name = "ngayKetThuc")
    private LocalDate ngayKetThuc;

    @Column(name = "tienCoc", precision = 18, scale = 2)
    private BigDecimal tienCoc;

    @Column(name = "giaThue", precision = 18, scale = 2)
    private BigDecimal giaThue;

    @Column(name = "fileHopDong", length = 500)
    private String fileHopDong;

    @Column(name = "trangThai", length = 50)
    private String trangThai;

    @Column(name = "ngayKy")
    private LocalDateTime ngayKy;

    // ===== Bổ sung cho luồng đặt phòng -> ký hợp đồng online -> thanh toán =====

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maYeuCau")
    private YeuCauThue yeuCauThue;

    @Lob
    @Column(name = "chuKyChuTro")
    private String chuKyChuTro;

    @Lob
    @Column(name = "chuKyNguoiThue")
    private String chuKyNguoiThue;

    @Column(name = "daKyChuTro", nullable = false)
    @Builder.Default
    private Boolean daKyChuTro = false;

    @Column(name = "daKyNguoiThue", nullable = false)
    @Builder.Default
    private Boolean daKyNguoiThue = false;

    @Column(name = "ngayKyChuTro")
    private LocalDateTime ngayKyChuTro;

    @Column(name = "ngayKyNguoiThue")
    private LocalDateTime ngayKyNguoiThue;

    @Column(name = "cccdChuTro", length = 50)
    private String cccdChuTro;

    @Column(name = "ngayCapCccdChuTro")
    private LocalDate ngayCapCccdChuTro;

    @Column(name = "noiCapCccdChuTro", length = 255)
    private String noiCapCccdChuTro;

    @Column(name = "diaChiThuongTruChuTro", length = 500)
    private String diaChiThuongTruChuTro;

    @Column(name = "cccdNguoiThue", length = 50)
    private String cccdNguoiThue;

    @Column(name = "ngayCapCccdNguoiThue")
    private LocalDate ngayCapCccdNguoiThue;

    @Column(name = "noiCapCccdNguoiThue", length = 255)
    private String noiCapCccdNguoiThue;

    @Column(name = "diaChiThuongTruNguoiThue", length = 500)
    private String diaChiThuongTruNguoiThue;

    @Column(name = "diaDiemKy", length = 255)
    private String diaDiemKy;

    // ===== Bổ sung cho Điều 13 - Chính sách gia hạn sau khi hết hạn hợp đồng =====

    @Column(name = "choPhepGiaHan", nullable = false)
    @Builder.Default
    private Boolean choPhepGiaHan = true;

    @Column(name = "soNgayBaoTruocGiaHan")
    private Integer soNgayBaoTruocGiaHan;

    @Column(name = "soLanGiaHanToiDa")
    private Integer soLanGiaHanToiDa;

    @Column(name = "mucTangGiaToiDaPhanTram")
    private Integer mucTangGiaToiDaPhanTram;
}