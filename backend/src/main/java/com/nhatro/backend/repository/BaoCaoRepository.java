package com.nhatro.backend.repository;

import com.nhatro.backend.entity.BaoCao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaoCaoRepository extends JpaRepository<BaoCao, Integer> {
    // Legacy methods (kept for backward compatibility)
    List<BaoCao> findByNguoiGui_MaNguoiDung(Integer maNguoiDung);

    List<BaoCao> findByTrangThai(String trangThai);

    // Pagination + EntityGraph to avoid N+1 and allow efficient paging
    @EntityGraph(attributePaths = { "nguoiGui" })
    Page<BaoCao> findByNguoiGui_MaNguoiDung(Integer maNguoiDung, Pageable pageable);

    @EntityGraph(attributePaths = { "nguoiGui" })
    Page<BaoCao> findByTrangThai(String trangThai, Pageable pageable);
}
