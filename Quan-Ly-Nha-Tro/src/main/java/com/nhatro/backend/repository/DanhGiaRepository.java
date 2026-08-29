package com.nhatro.backend.repository;

import com.nhatro.backend.entity.DanhGia;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, Integer> {
    List<DanhGia> findByPhong_MaPhong(Integer maPhong);
    List<DanhGia> findByNguoiDung_MaNguoiDung(Integer maNguoiDung);

    // Dung cho trang "Kiem duyet danh gia" cua Admin: tranh N+1 khi hien
    // thi bang (can ten nguoi danh gia + ten phong/nha tro).
    @EntityGraph(attributePaths = { "nguoiDung", "phong", "phong.nhaTro" })
    List<DanhGia> findAllByOrderByNgayDanhGiaDesc();
}
