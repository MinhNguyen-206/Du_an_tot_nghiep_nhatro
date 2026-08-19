package com.nhatro.backend.repository;

import com.nhatro.backend.entity.DangKyGoiChuTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DangKyGoiChuTroRepository extends JpaRepository<DangKyGoiChuTro, Integer> {
    List<DangKyGoiChuTro> findByChuTro_MaNguoiDung(Integer maNguoiDung);
    List<DangKyGoiChuTro> findByTrangThai(String trangThai);
}
