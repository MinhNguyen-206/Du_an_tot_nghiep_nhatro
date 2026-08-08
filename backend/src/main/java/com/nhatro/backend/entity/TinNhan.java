package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "TIN_NHAN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TinNhan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maTinNhan")
    private Integer maTinNhan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoiGui", nullable = false)
    private NguoiDung nguoiGui;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoiNhan", nullable = false)
    private NguoiDung nguoiNhan;

    @Column(name = "noiDung", columnDefinition = "NVARCHAR(MAX)")
    private String noiDung;

    @CreationTimestamp
    @Column(name = "thoiGian", updatable = false)
    private LocalDateTime thoiGian;

    @Column(name = "daDoc")
    @Builder.Default
    private Boolean daDoc = false;
}