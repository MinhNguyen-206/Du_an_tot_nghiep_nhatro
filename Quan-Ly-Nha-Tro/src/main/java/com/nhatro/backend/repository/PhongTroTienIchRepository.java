package com.nhatro.backend.repository;

import com.nhatro.backend.entity.PhongTroTienIch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhongTroTienIchRepository extends JpaRepository<PhongTroTienIch, PhongTroTienIch.PhongTroTienIchId> {
    List<PhongTroTienIch> findByMaPhong(Integer maPhong);
}
