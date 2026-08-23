package com.nhatro.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhatro.backend.entity.HopDongDienTu;

@Repository
public interface HopDongDienTuRepository extends JpaRepository<HopDongDienTu, Integer> {
    List<HopDongDienTu> findByNguoiThue_MaNguoiDung(Integer maNguoiDung);
    List<HopDongDienTu> findByChuTro_MaNguoiDung(Integer maNguoiDung);
    List<HopDongDienTu> findByPhong_MaPhong(Integer maPhong);
    List<HopDongDienTu> findByTrangThai(String trangThai);
    java.util.Optional<HopDongDienTu> findByYeuCauThue_MaYeuCau(Integer maYeuCau);
}