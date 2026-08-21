package com.nhatro.backend.repository;

import com.nhatro.backend.entity.PhongTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhongTroRepository extends JpaRepository<PhongTro, Integer> {
    List<PhongTro> findByNhaTro_MaNhaTro(Integer maNhaTro);
    List<PhongTro> findByTrangThai(Boolean trangThai);
}
