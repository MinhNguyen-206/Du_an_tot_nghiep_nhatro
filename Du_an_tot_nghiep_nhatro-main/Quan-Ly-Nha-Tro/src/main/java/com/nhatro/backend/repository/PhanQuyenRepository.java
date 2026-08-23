package com.nhatro.backend.repository;

import com.nhatro.backend.entity.PhanQuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhanQuyenRepository extends JpaRepository<PhanQuyen, Integer> {
    Optional<PhanQuyen> findByMaQuyenCode(String maQuyenCode);
}
