package com.nhatro.backend.repository;

import com.nhatro.backend.entity.PhongTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhongTroRepository extends JpaRepository<PhongTro, Integer> {
    List<PhongTro> findByNhaTro_MaNhaTro(Integer maNhaTro);
    List<PhongTro> findByTrangThai(Boolean trangThai);

    // Phòng "đại diện" của 1 nhà trọ (phòng đầu tiên theo maPhong) - dùng để
    // suy ra maPhong thật khi trang danh sách chỉ hiển thị ở mức NHA_TRO
    // (card gộp), nhưng link "Xem chi tiết" phải trỏ tới 1 PHONG_TRO cụ thể
    // (API /api/phong-tro/{id}/chi-tiet nhận maPhong, không nhận maNhaTro).
    Optional<PhongTro> findFirstByNhaTro_MaNhaTroOrderByMaPhongAsc(Integer maNhaTro);
}