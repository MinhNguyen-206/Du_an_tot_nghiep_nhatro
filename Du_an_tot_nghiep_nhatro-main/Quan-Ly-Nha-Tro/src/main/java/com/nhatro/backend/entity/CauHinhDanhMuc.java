package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CAU_HINH_DANH_MUC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CauHinhDanhMuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDanhMuc")
    private Integer maDanhMuc;

    @Column(name = "tenDanhMuc", length = 255)
    private String tenDanhMuc;

    @Column(name = "moTa", length = 500)
    private String moTa;

    @Column(name = "trangThai")
    @Builder.Default
    private Boolean trangThai = true;
}
