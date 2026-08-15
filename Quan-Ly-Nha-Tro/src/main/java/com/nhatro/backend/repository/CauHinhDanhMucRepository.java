package com.nhatro.backend.repository;

import com.nhatro.backend.entity.CauHinhDanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CauHinhDanhMucRepository extends JpaRepository<CauHinhDanhMuc, Integer> {
    List<CauHinhDanhMuc> findByTrangThai(Boolean trangThai);
}
