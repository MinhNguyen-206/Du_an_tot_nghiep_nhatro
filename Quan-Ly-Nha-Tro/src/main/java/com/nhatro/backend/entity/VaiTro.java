package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "VAI_TRO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaiTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maVaiTro")
    private Integer maVaiTro;

    @Column(name = "tenVaiTro", nullable = false, length = 100)
    private String tenVaiTro;

    @Column(name = "moTa", length = 500)
    private String moTa;

    @Column(name = "trangThai", nullable = false)
    @Builder.Default
    private Boolean trangThai = true;
}
