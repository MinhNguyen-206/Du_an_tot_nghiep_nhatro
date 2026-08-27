package com.nhatro.backend.repository;

import com.nhatro.backend.entity.ThongBao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThongBaoRepository extends JpaRepository<ThongBao, Integer> {
    List<ThongBao> findByNguoiDung_MaNguoiDung(Integer maNguoiDung);
    List<ThongBao> findByNguoiDung_MaNguoiDungAndDaDoc(Integer maNguoiDung, Boolean daDoc);
}
