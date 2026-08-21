package com.nhatro.backend.repository;

import com.nhatro.backend.entity.NhaTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NhaTroRepository
        extends JpaRepository<NhaTro, Integer>,
        JpaSpecificationExecutor<NhaTro> {

    List<NhaTro> findByNguoiDung_MaNguoiDung(Integer maNguoiDung);
}