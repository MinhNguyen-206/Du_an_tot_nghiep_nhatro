package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TIEN_ICH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TienIch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maTienIch")
    private Integer maTienIch;

    @Column(name = "tenTienIch", nullable = false, length = 255)
    private String tenTienIch;

    @Column(name = "moTa", length = 500)
    private String moTa;
}
