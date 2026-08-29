package com.nhatro.backend.repository;

import com.nhatro.backend.entity.GiaoDichThanhToan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiaoDichThanhToanRepository extends JpaRepository<GiaoDichThanhToan, Integer> {
    List<GiaoDichThanhToan> findByThanhToan_MaThanhToan(Integer maThanhToan);

    // Dung cho trang "Giao dich & hoa don" cua Admin: tranh N+1 khi hien
    // thi bang (can ten nguoi thue, ten phong/nha tro, thong tin hoa don).
    @EntityGraph(attributePaths = {
            "thanhToan",
            "thanhToan.hoaDon",
            "thanhToan.hoaDon.hopDong",
            "thanhToan.hoaDon.hopDong.nguoiThue",
            "thanhToan.hoaDon.hopDong.phong",
            "thanhToan.hoaDon.hopDong.phong.nhaTro"
    })
    List<GiaoDichThanhToan> findAllByOrderByNgayGiaoDichDesc();

    @EntityGraph(attributePaths = {
            "thanhToan",
            "thanhToan.hoaDon",
            "thanhToan.hoaDon.hopDong",
            "thanhToan.hoaDon.hopDong.nguoiThue",
            "thanhToan.hoaDon.hopDong.chuTro",
            "thanhToan.hoaDon.hopDong.phong",
            "thanhToan.hoaDon.hopDong.phong.nhaTro",
            "thanhToan.hoaDon.chiSo"
    })
    java.util.Optional<GiaoDichThanhToan> findByMaGiaoDich(Integer maGiaoDich);
}
