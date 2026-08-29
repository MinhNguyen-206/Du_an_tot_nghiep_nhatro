package com.nhatro.backend.repository;

import com.nhatro.backend.entity.DangTin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DangTinRepository extends JpaRepository<DangTin, Integer> {
    List<DangTin> findByTrangThai(Boolean trangThai);
    List<DangTin> findByNguoiDung_MaNguoiDung(Integer maNguoiDung);
    List<DangTin> findByPhong_MaPhong(Integer maPhong);

    // Dung cho trang "Duyet bai dang" cua Admin: lay ca danh sach kem san
    // thong tin nguoi dang + phong + nha tro, tranh N+1 khi hien thi bang.
    @EntityGraph(attributePaths = { "nguoiDung", "phong", "phong.nhaTro" })
    List<DangTin> findAllByOrderByNgayDangDesc();

    @EntityGraph(attributePaths = { "nguoiDung", "phong", "phong.nhaTro" })
    List<DangTin> findByTrangThaiDuyetOrderByNgayDangDesc(String trangThaiDuyet);

    @EntityGraph(attributePaths = { "nguoiDung", "phong", "phong.nhaTro", "nguoiDuyet" })
    Optional<DangTin> findByMaDangTin(Integer maDangTin);

    // ===== Phien ban co phan trang (dung cho danh sach cong khai / API list lon) =====
    @EntityGraph(attributePaths = { "nguoiDung", "phong", "phong.nhaTro" })
    Page<DangTin> findAllByOrderByNgayDangDesc(Pageable pageable);

    @EntityGraph(attributePaths = { "nguoiDung", "phong", "phong.nhaTro" })
    Page<DangTin> findByTrangThaiDuyetOrderByNgayDangDesc(String trangThaiDuyet, Pageable pageable);

    long countByTrangThaiDuyet(String trangThaiDuyet);
}
