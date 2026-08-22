package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// Yeu cau "Nguoi thue" -> "Chu tro". Nguoi dung dien thong tin xac minh
// (CCCD + bat dong san) tai trang /dang-ky-chu-tro, Admin xem va duyet/tu choi
// tai trang quan tri. Khi duoc duyet, vaiTro cua NguoiDung se duoc doi thanh
// "Chu tro" (xem YeuCauChuTroService.duyet()).
@Entity
@Table(name = "YEU_CAU_CHU_TRO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YeuCauChuTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maYeuCau")
    private Integer maYeuCau;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDung", nullable = false)
    private NguoiDung nguoiDung;

    // ---- Xac minh danh tinh (theo CCCD/CMND) ----
    @Column(name = "hoTenCCCD", length = 255)
    private String hoTenCCCD;

    @Column(name = "soCCCD", length = 20)
    private String soCCCD;

    @Column(name = "anhCCCDMatTruoc", length = 500)
    private String anhCCCDMatTruoc;

    @Column(name = "anhCCCDMatSau", length = 500)
    private String anhCCCDMatSau;

    // ---- Xac minh bat dong san (phong/nha tro se cho thue) ----
    @Column(name = "diaChiBDS", length = 500)
    private String diaChiBDS;

    // Nhieu link anh/video thuc te, ngan cach nhau boi dau phay.
    @Column(name = "anhThucTe", length = 2000)
    private String anhThucTe;

    // Link anh/scan: so do/so hong, hop dong uy quyen quan ly, hoac hop dong
    // thue nha nguyen can (neu la nguoi thue lai roi cho thue tiep).
    @Column(name = "giayToSoHuu", length = 500)
    private String giayToSoHuu;

    // Giay to phap ly bo sung (neu co): PCCC, giay dang ky kinh doanh luu tru...
    @Column(name = "giayToBoSung", length = 500)
    private String giayToBoSung;

    @Column(name = "ghiChuNguoiDung", length = 1000)
    private String ghiChuNguoiDung;

    // CHO_DUYET | DA_DUYET | TU_CHOI
    @Column(name = "trangThai", length = 20, nullable = false)
    @Builder.Default
    private String trangThai = "CHO_DUYET";

    @Column(name = "lyDoTuChoi", length = 500)
    private String lyDoTuChoi;

    @CreationTimestamp
    @Column(name = "ngayGui", updatable = false)
    private LocalDateTime ngayGui;

    @Column(name = "ngayXuLy")
    private LocalDateTime ngayXuLy;
}
