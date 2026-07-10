package com.nhatro.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhanQuyen {
    private Integer maQuyen;
    private String tenQuyen; // Ví dụ: ROLE_USER, ROLE_ADMIN, ROLE_CHUTRO
    private String moTa;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
}
