package com.nhatro.backend.repository;

import com.nhatro.backend.entity.HinhAnh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HinhAnhRepository extends JpaRepository<HinhAnh, Integer> {
    List<HinhAnh> findByDangTin_MaDangTinOrderByThuTuHienThi(Integer maDangTin);
}
