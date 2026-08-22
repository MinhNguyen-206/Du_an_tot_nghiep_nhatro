package com.nhatro.backend.repository;

import com.nhatro.backend.entity.PhongYeuThich;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhongYeuThichRepository extends JpaRepository<PhongYeuThich, Integer> {
    List<PhongYeuThich> findByNguoiDung_MaNguoiDungOrderByNgayLuuDesc(Integer maNguoiDung);
    Optional<PhongYeuThich> findByNguoiDung_MaNguoiDungAndPhong_MaPhong(Integer maNguoiDung, Integer maPhong);
    boolean existsByNguoiDung_MaNguoiDungAndPhong_MaPhong(Integer maNguoiDung, Integer maPhong);
}
