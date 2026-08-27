package com.nhatro.backend.repository;

import com.nhatro.backend.entity.GiaoDichThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiaoDichThanhToanRepository extends JpaRepository<GiaoDichThanhToan, Integer> {
    List<GiaoDichThanhToan> findByThanhToan_MaThanhToan(Integer maThanhToan);
}
