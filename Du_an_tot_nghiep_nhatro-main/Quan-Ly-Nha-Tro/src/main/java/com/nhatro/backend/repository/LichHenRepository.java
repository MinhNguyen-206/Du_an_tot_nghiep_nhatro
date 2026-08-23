package com.nhatro.backend.repository;

import com.nhatro.backend.entity.LichHen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichHenRepository extends JpaRepository<LichHen, Integer> {
    List<LichHen> findByNguoiDung_MaNguoiDung(Integer maNguoiDung);
    List<LichHen> findByPhong_MaPhong(Integer maPhong);

    @Query("""
            select lh
            from LichHen lh
            join fetch lh.nguoiDung nd
            join fetch lh.phong p
            join fetch p.nhaTro nt
            where nt.nguoiDung.maNguoiDung = :maChuTro
            order by lh.ngayHen asc, lh.gioHen asc, lh.maLichHen desc
            """)
    List<LichHen> findByChuTro(@Param("maChuTro") Integer maChuTro);
}
