package com.nhatro.backend.repository;

import com.nhatro.backend.entity.BoDieuKhienAi;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BoDieuKhienAiRepository extends JpaRepository<BoDieuKhienAi, Integer> {
}
