package com.nhatro.backend.repository;

import com.nhatro.backend.entity.HoaDonPremium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonPremiumRepository extends JpaRepository<HoaDonPremium, Integer> {
    List<HoaDonPremium> findByHopDongPremium_MaHopDongPremium(Integer maHopDongPremium);
}
