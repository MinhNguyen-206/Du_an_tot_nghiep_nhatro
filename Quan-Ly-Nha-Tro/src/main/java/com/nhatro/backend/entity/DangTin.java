package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "DANG_TIN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DangTin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDangTin")
    private Integer maDangTin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDung", nullable = false)
    private NguoiDung nguoiDung;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maPhong", nullable = false)
    private PhongTro phong;

    @Column(name = "tieuDe", nullable = false, length = 255)
    private String tieuDe;

    @Column(name = "noiDung", columnDefinition = "NVARCHAR(MAX)")
    private String noiDung;

    @CreationTimestamp
    @Column(name = "ngayDang", updatable = false)
    private LocalDateTime ngayDang;

    @Column(name = "ngayHetHan")
    private LocalDateTime ngayHetHan;

    @Column(name = "trangThai")
    @Builder.Default
    private Boolean trangThai = true;

    // ===== Kiem duyet bai dang (Admin) =====
    // CHO_DUYET | DA_DUYET | TU_CHOI. Mac dinh CHO_DUYET: bai dang moi tao
    // (tu Chu tro) phai cho Admin xet duyet truoc khi hien thi cong khai
    // (xem PhongChiTietService - chi lay DangTin da DA_DUYET).
    @Column(name = "trangThaiDuyet", length = 20)
    @Builder.Default
    private String trangThaiDuyet = "CHO_DUYET";

    // Ly do Admin tu choi bai dang (hien thi cho Chu tro biet de sua lai).
    @Column(name = "lyDoTuChoi", length = 500)
    private String lyDoTuChoi;

    // Admin da xu ly duyet/tu choi bai dang nay.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDuyet")
    private NguoiDung nguoiDuyet;

    @Column(name = "ngayDuyet")
    private LocalDateTime ngayDuyet;
}
