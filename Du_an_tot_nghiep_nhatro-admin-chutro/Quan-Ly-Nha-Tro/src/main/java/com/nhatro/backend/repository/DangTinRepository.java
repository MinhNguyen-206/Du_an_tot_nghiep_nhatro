package com.nhatro.backend.repository;

import com.nhatro.backend.entity.DangTin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DangTinRepository extends JpaRepository<DangTin, Integer> {
    List<DangTin> findByTrangThai(Boolean trangThai);
    List<DangTin> findByNguoiDung_MaNguoiDung(Integer maNguoiDung);
    List<DangTin> findByPhong_MaPhong(Integer maPhong);
}
