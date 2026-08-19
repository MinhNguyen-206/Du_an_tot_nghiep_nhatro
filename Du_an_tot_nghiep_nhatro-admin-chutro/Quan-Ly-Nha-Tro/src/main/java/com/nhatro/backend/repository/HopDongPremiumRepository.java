package com.nhatro.backend.repository;

import com.nhatro.backend.entity.HopDongPremium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HopDongPremiumRepository extends JpaRepository<HopDongPremium, Integer> {
    List<HopDongPremium> findByDangKy_MaDangKy(Integer maDangKy);
}
