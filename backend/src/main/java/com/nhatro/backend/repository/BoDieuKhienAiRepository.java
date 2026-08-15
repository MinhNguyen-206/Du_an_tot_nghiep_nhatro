package com.nhatro.backend.repository;

import com.nhatro.backend.entity.BoDieuKhienAi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoDieuKhienAiRepository extends JpaRepository<BoDieuKhienAi, Integer> {
}
