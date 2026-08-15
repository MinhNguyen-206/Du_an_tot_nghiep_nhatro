package com.nhatro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "VAI_TRO_PHAN_QUYEN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(VaiTroPhanQuyen.VaiTroPhanQuyenId.class)
public class VaiTroPhanQuyen {

    @Id
    @Column(name = "maVaiTro")
    private Integer maVaiTro;

    @Id
    @Column(name = "maQuyen")
    private Integer maQuyen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maVaiTro", insertable = false, updatable = false)
    private VaiTro vaiTro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maQuyen", insertable = false, updatable = false)
    private PhanQuyen phanQuyen;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VaiTroPhanQuyenId implements Serializable {
        private Integer maVaiTro;
        private Integer maQuyen;
    }
}
