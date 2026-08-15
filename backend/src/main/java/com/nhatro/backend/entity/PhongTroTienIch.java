package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "PHONG_TRO_TIEN_ICH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(PhongTroTienIch.PhongTroTienIchId.class)
public class PhongTroTienIch {

    @Id
    @Column(name = "maPhong")
    private Integer maPhong;

    @Id
    @Column(name = "maTienIch")
    private Integer maTienIch;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maPhong", insertable = false, updatable = false)
    private PhongTro phong;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maTienIch", insertable = false, updatable = false)
    private TienIch tienIch;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhongTroTienIchId implements Serializable {
        private Integer maPhong;
        private Integer maTienIch;
    }
}
