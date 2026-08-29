package com.nhatro.backend.repository;

import com.nhatro.backend.entity.LichSuXemPhong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichSuXemPhongRepository extends JpaRepository<LichSuXemPhong, Integer> {

    List<LichSuXemPhong> findByNguoiDung_MaNguoiDungOrderByThoiGianXemDesc(
            Integer maNguoiDung
    );

    long countByPhong_MaPhong(Integer maPhong);
}