package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "BAO_CAO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaoCao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maBaoCao")
    private Integer maBaoCao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiGui", nullable = false)
    private NguoiDung nguoiGui;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiBiBaoCao")
    private NguoiDung nguoiBiBaoCao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maDangTin")
    private DangTin dangTin;

    @Column(name = "lyDo", length = 1000)
    private String lyDo;

    @Column(name = "noiDung", columnDefinition = "NVARCHAR(MAX)")
    private String noiDung;

    @CreationTimestamp
    @Column(name = "ngayBaoCao", updatable = false)
    private LocalDateTime ngayBaoCao;

    @Column(name = "trangThai", length = 50)
    private String trangThai;


    // =========================================================
    // CÁC TRƯỜNG AI PHÂN LOẠI BÁO CÁO
    // =========================================================

    /**
     * Nhóm báo cáo do AI phân loại
     * Ví dụ: LỪA ĐẢO, THÔNG TIN SAI, NỘI DUNG KHÔNG PHÙ HỢP...
     */
    @Column(name = "aiNhom", length = 100)
    private String aiNhom;

    /**
     * Mức độ nghiêm trọng do AI đánh giá
     * Ví dụ: THẤP, TRUNG_BÌNH, CAO, KHẨN_CẤP
     */
    @Column(name = "aiMucDo", length = 50)
    private String aiMucDo;

    /**
     * Điểm rủi ro do AI chấm
     * Có thể dùng thang điểm 0 - 100
     */
    @Column(name = "aiDiem")
    private Integer aiDiem;

    /**
     * Phân tích chi tiết của AI
     */
    @Column(name = "aiPhanTich", columnDefinition = "NVARCHAR(MAX)")
    private String aiPhanTich;
}