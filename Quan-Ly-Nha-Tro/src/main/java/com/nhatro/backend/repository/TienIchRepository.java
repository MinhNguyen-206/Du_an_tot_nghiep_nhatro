package com.nhatro.backend.repository;

import com.nhatro.backend.entity.TienIch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TienIchRepository extends JpaRepository<TienIch, Integer> {
}
