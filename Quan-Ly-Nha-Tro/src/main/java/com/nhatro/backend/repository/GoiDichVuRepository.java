package com.nhatro.backend.repository;

import com.nhatro.backend.entity.GoiDichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoiDichVuRepository extends JpaRepository<GoiDichVu, Integer> {
    List<GoiDichVu> findByTrangThai(Boolean trangThai);
}
