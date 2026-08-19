package com.nhatro.backend.repository;

import com.nhatro.backend.entity.TinNhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TinNhanRepository extends JpaRepository<TinNhan, Integer> {
    List<TinNhan> findByNguoiGui_MaNguoiDung(Integer maNguoiDung);
    List<TinNhan> findByNguoiNhan_MaNguoiDung(Integer maNguoiDung);
}
