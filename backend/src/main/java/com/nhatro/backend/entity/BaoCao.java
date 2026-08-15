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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maNguoiGui", nullable = false)
    private NguoiDung nguoiGui;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maNguoiBiBaoCao")
    private NguoiDung nguoiBiBaoCao;

    @ManyToOne(fetch = FetchType.EAGER)
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
}
