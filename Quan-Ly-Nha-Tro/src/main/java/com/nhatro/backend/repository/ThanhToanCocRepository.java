package com.nhatro.backend.repository;

import com.nhatro.backend.entity.ThanhToanCoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThanhToanCocRepository extends JpaRepository<ThanhToanCoc, Integer> {
    List<ThanhToanCoc> findByHopDong_MaHopDong(Integer maHopDong);
}
