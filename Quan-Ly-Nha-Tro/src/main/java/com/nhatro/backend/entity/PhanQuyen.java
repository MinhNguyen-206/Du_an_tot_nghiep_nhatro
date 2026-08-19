package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PHAN_QUYEN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhanQuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maQuyen")
    private Integer maQuyen;

    @Column(name = "tenQuyen", nullable = false, length = 200)
    private String tenQuyen;

    @Column(name = "maQuyenCode", unique = true, length = 100)
    private String maQuyenCode;

    @Column(name = "moTa", length = 500)
    private String moTa;
}
