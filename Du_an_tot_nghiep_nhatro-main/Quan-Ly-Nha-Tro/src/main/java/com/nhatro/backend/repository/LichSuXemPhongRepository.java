package com.nhatro.backend.repository;

import com.nhatro.backend.entity.LichSuXemPhong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LichSuXemPhongRepository extends JpaRepository<LichSuXemPhong, Integer> {
    List<LichSuXemPhong> findByNguoiDung_MaNguoiDungOrderByThoiGianXemDesc(Integer maNguoiDung);
}
