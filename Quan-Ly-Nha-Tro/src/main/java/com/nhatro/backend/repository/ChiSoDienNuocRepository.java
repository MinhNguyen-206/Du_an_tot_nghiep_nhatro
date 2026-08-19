package com.nhatro.backend.repository;

import com.nhatro.backend.entity.ChiSoDienNuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChiSoDienNuocRepository extends JpaRepository<ChiSoDienNuoc, Integer> {
    List<ChiSoDienNuoc> findByPhong_MaPhong(Integer maPhong);
    Optional<ChiSoDienNuoc> findByPhong_MaPhongAndThangAndNam(Integer maPhong, Integer thang, Integer nam);
}
