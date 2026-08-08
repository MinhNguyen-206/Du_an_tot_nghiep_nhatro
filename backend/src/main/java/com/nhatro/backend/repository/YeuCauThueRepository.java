package com.nhatro.backend.repository;

import com.nhatro.backend.entity.YeuCauThue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YeuCauThueRepository extends JpaRepository<YeuCauThue, Integer> {
    List<YeuCauThue> findByNguoiThue_MaNguoiDung(Integer maNguoiDung);
    List<YeuCauThue> findByPhong_MaPhong(Integer maPhong);
    List<YeuCauThue> findByTrangThai(String trangThai);
}
