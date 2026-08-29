package com.nhatro.backend.repository;

import com.nhatro.backend.entity.BaoCao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaoCaoRepository extends JpaRepository<BaoCao, Integer> {
    // Legacy methods (kept for backward compatibility)
    List<BaoCao> findByNguoiGui_MaNguoiDung(Integer maNguoiDung);

    List<BaoCao> findByTrangThai(String trangThai);

    // Dung cho trang Quan ly nguoi dung (Admin): dem so lan 1 nguoi dung
    // bi nguoi khac bao cao/khieu nai, de hien cot "Vi pham".
    long countByNguoiBiBaoCao_MaNguoiDung(Integer maNguoiDung);

    // Gom nhom (tranh N+1) de tinh so vi pham cho CA danh sach nguoi dung
    // cung luc: tra ve mang [maNguoiDung, soLuotBaoCao].
    @Query("SELECT b.nguoiBiBaoCao.maNguoiDung, COUNT(b) FROM BaoCao b " +
            "WHERE b.nguoiBiBaoCao IS NOT NULL GROUP BY b.nguoiBiBaoCao.maNguoiDung")
    List<Object[]> demSoBaoCaoTheoNguoiBiBaoCao();

    // Pagination + EntityGraph to avoid N+1 and allow efficient paging
    @EntityGraph(attributePaths = { "nguoiGui" })
    Page<BaoCao> findByNguoiGui_MaNguoiDung(Integer maNguoiDung, Pageable pageable);

    @EntityGraph(attributePaths = { "nguoiGui" })
    Page<BaoCao> findByTrangThai(String trangThai, Pageable pageable);
}
