package com.nhatro.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhatro.backend.entity.VaiTro;

@Repository
public interface VaiTroRepository extends JpaRepository<VaiTro, Integer> {
    
}
