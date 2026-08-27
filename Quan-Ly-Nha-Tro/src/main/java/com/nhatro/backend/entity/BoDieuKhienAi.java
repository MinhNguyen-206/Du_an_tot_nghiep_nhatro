package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "BO_DIEU_KHIEN_AI")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoDieuKhienAi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maAI")
    private Integer maAI;

    @Column(name = "tenMoHinh", length = 255)
    private String tenMoHinh;

    @Column(name = "phienBan", length = 100)
    private String phienBan;

    @Column(name = "trangThai")
    @Builder.Default
    private Boolean trangThai = true;

    @UpdateTimestamp
    @Column(name = "ngayCapNhat")
    private LocalDateTime ngayCapNhat;
}
