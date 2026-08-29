package com.nhatro.backend.repository;

import com.nhatro.backend.entity.NhatKyHoatDong;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface NhatKyHoatDongRepository extends JpaRepository<NhatKyHoatDong, Integer> {
    List<NhatKyHoatDong> findByNguoiDung_MaNguoiDung(Integer maNguoiDung);

    // Dung cho trang "Nhat ky hoat dong" cua Admin: moi nhat truoc, kem san
    // thong tin nguoi thuc hien de tranh N+1.
    @EntityGraph(attributePaths = { "nguoiDung" })
    List<NhatKyHoatDong> findAllByOrderByThoiGianDesc();
    @EntityGraph(attributePaths = { "nguoiDung" })
    Page<NhatKyHoatDong> findAllByOrderByThoiGianDesc(Pageable pageable);
}
