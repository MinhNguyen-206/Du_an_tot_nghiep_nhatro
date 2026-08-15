package com.nhatro.backend.repository;

import com.nhatro.backend.entity.NhatKyHoatDong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NhatKyHoatDongRepository extends JpaRepository<NhatKyHoatDong, Integer> {
    List<NhatKyHoatDong> findByNguoiDung_MaNguoiDung(Integer maNguoiDung);
}
