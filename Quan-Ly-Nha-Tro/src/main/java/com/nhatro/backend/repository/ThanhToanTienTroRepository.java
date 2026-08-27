package com.nhatro.backend.repository;

import com.nhatro.backend.entity.ThanhToanTienTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThanhToanTienTroRepository extends JpaRepository<ThanhToanTienTro, Integer> {
    List<ThanhToanTienTro> findByHoaDon_MaHoaDon(Integer maHoaDon);
}
