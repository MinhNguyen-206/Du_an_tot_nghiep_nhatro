package com.nhatro.backend.repository;

import com.nhatro.backend.entity.HoaDonThang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonThangRepository extends JpaRepository<HoaDonThang, Integer> {
    List<HoaDonThang> findByHopDong_MaHopDong(Integer maHopDong);
    List<HoaDonThang> findByTrangThai(String trangThai);
}
