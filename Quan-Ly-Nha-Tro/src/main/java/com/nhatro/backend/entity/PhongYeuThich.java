package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "PHONG_YEU_THICH", uniqueConstraints = @UniqueConstraint(name = "UQ_PHONG_YEU_THICH_USER_ROOM", columnNames = {"maNguoiDung", "maPhong"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhongYeuThich {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maPhongYeuThich")
    private Integer maPhongYeuThich;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDung", nullable = false)
    private NguoiDung nguoiDung;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maPhong", nullable = false)
    private PhongTro phong;

    @CreationTimestamp
    @Column(name = "ngayLuu", nullable = false, updatable = false)
    private LocalDateTime ngayLuu;
}
