package com.nhatro.backend.repository;

import com.nhatro.backend.entity.LichHen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichHenRepository extends JpaRepository<LichHen, Integer> {
    List<LichHen> findByNguoiDung_MaNguoiDung(Integer maNguoiDung);
    List<LichHen> findByPhong_MaPhong(Integer maPhong);
}
