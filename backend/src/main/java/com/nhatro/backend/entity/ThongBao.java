package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "THONG_BAO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongBao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maThongBao")
    private Integer maThongBao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDung")
    private NguoiDung nguoiDung;

    @Column(name = "tieuDe", length = 255)
    private String tieuDe;

    @Column(name = "noiDung", columnDefinition = "NVARCHAR(MAX)")
    private String noiDung;

    @Column(name = "daDoc")
    @Builder.Default
    private Boolean daDoc = false;

    @CreationTimestamp
    @Column(name = "ngayGui", updatable = false)
    private LocalDateTime ngayGui;
}