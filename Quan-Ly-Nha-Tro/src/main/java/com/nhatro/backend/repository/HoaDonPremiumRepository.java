package com.nhatro.backend.repository;

import com.nhatro.backend.entity.HoaDonPremium;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonPremiumRepository extends JpaRepository<HoaDonPremium, Integer> {
    List<HoaDonPremium> findByHopDongPremium_MaHopDongPremium(Integer maHopDongPremium);

    @EntityGraph(attributePaths = {"hopDongPremium", "hopDongPremium.dangKy"})
    Page<HoaDonPremium> findAllByOrderByNgayLapDesc(Pageable pageable);

    Page<HoaDonPremium> findByTrangThaiOrderByNgayLapDesc(String trangThai, Pageable pageable);
}
