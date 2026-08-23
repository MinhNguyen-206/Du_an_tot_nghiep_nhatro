package com.nhatro.backend.repository;

import com.nhatro.backend.entity.YeuCauChuTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YeuCauChuTroRepository extends JpaRepository<YeuCauChuTro, Integer> {

    List<YeuCauChuTro> findByNguoiDung_MaNguoiDungOrderByNgayGuiDesc(Integer maNguoiDung);

    Optional<YeuCauChuTro> findTopByNguoiDung_MaNguoiDungOrderByNgayGuiDesc(Integer maNguoiDung);

    List<YeuCauChuTro> findByTrangThaiOrderByNgayGuiAsc(String trangThai);

    boolean existsByNguoiDung_MaNguoiDungAndTrangThai(Integer maNguoiDung, String trangThai);
}
