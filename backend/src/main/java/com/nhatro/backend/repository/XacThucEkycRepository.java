package com.nhatro.backend.repository;

import com.nhatro.backend.entity.XacThucEkyc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface XacThucEkycRepository extends JpaRepository<XacThucEkyc, Integer> {
    List<XacThucEkyc> findByNguoiDung_MaNguoiDung(Integer maNguoiDung);
    Optional<XacThucEkyc> findTopByNguoiDung_MaNguoiDungOrderByNgayGuiDesc(Integer maNguoiDung);
}
