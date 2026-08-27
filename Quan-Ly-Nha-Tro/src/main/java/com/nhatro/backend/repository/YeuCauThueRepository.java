package com.nhatro.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nhatro.backend.entity.YeuCauThue;

@Repository
public interface YeuCauThueRepository extends JpaRepository<YeuCauThue, Integer> {
    List<YeuCauThue> findByNguoiThue_MaNguoiDung(Integer maNguoiDung);
    List<YeuCauThue> findByPhong_MaPhong(Integer maPhong);
    List<YeuCauThue> findByTrangThai(String trangThai);
    @Query("SELECT y FROM YeuCauThue y " +
            "JOIN FETCH y.phong p " +
            "JOIN FETCH p.nhaTro n " +
            "JOIN FETCH n.nguoiDung chuTro " +
            "JOIN FETCH y.nguoiThue " +
            "WHERE chuTro.maNguoiDung = :maChuTro " +
            "ORDER BY y.ngayGui DESC")
    List<YeuCauThue> findByChuTroWithChiTiet(@Param("maChuTro") Integer maChuTro);


    @Query("SELECT y FROM YeuCauThue y " +
            "JOIN FETCH y.phong p " +
            "JOIN FETCH p.nhaTro n " +
            "JOIN FETCH n.nguoiDung " +
            "JOIN FETCH y.nguoiThue " +
            "WHERE y.maYeuCau = :id")
    Optional<YeuCauThue> findByIdWithChiTiet(@Param("id") Integer id);
}
