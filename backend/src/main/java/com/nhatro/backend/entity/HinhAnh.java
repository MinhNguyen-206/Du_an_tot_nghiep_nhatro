package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "HINH_ANH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HinhAnh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maHinhAnh")
    private Integer maHinhAnh;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maDangTin", nullable = false)
    private DangTin dangTin;

    @Column(name = "duongDan", length = 500)
    private String duongDan;

    @Column(name = "moTa", length = 500)
    private String moTa;

    @Column(name = "thuTuHienThi")
    private Integer thuTuHienThi;

    @CreationTimestamp
    @Column(name = "ngayTaiLen", updatable = false)
    private LocalDateTime ngayTaiLen;
}
