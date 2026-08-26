package com.nhatro.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nhatro.backend.entity.HopDongDienTu;

@Repository
public interface HopDongDienTuRepository extends JpaRepository<HopDongDienTu, Integer> {
    List<HopDongDienTu> findByNguoiThue_MaNguoiDung(Integer maNguoiDung);
    List<HopDongDienTu> findByChuTro_MaNguoiDung(Integer maNguoiDung);
    List<HopDongDienTu> findByPhong_MaPhong(Integer maPhong);
    List<HopDongDienTu> findByTrangThai(String trangThai);
    Optional<HopDongDienTu> findByYeuCauThue_MaYeuCau(Integer maYeuCau);

    /**
     * Du an dung spring.jpa.open-in-view=false: session Hibernate dong ngay sau khi
     * repository/service tra ve, nen findById() thuong khong the truy cap them cac
     * quan he LAZY (chuTro, nguoiThue) o tang controller nua - se nem loi
     * "Could not initialize proxy [...] - no session".
     *
     * Cac endpoint can kiem tra/doc chuTro hoac nguoiThue ngay sau khi lay hop dong
     * (gui OTP, ky hop dong, thanh toan coc...) PHAI dung ham nay (JOIN FETCH san
     * 2 quan he do) thay vi findById() thuong.
     */
    @Query("SELECT h FROM HopDongDienTu h " +
           "JOIN FETCH h.chuTro " +
           "JOIN FETCH h.nguoiThue " +
           "WHERE h.maHopDong = :maHopDong")
    Optional<HopDongDienTu> findByIdWithNguoiDung(@Param("maHopDong") Integer maHopDong);

    /**
     * Dung cho trang "Xem chi tiet hop dong" / xuat PDF va cho danh sach hop dong
     * (ho so khach hang, quan ly hop dong ben chu tro): JOIN FETCH day du
     * chuTro, nguoiThue, phong VA phong.nhaTro de JS co the doc thang
     * hd.phong.tenPhong, hd.phong.nhaTro.diaChi, hd.chuTro.hoTen,
     * hd.nguoiThue.hoTen... ma khong bi Hibernate6Module tra ve null (xem
     * JacksonConfig - open-in-view=false, proxy chua initialize -> null).
     */
    @Query("SELECT h FROM HopDongDienTu h " +
           "JOIN FETCH h.chuTro " +
           "JOIN FETCH h.nguoiThue " +
           "JOIN FETCH h.phong p " +
           "JOIN FETCH p.nhaTro " +
           "WHERE h.maHopDong = :maHopDong")
    Optional<HopDongDienTu> findByIdWithChiTietDayDu(@Param("maHopDong") Integer maHopDong);

    @Query("SELECT h FROM HopDongDienTu h " +
           "JOIN FETCH h.chuTro " +
           "JOIN FETCH h.nguoiThue " +
           "JOIN FETCH h.phong p " +
           "JOIN FETCH p.nhaTro " +
           "WHERE h.nguoiThue.maNguoiDung = :maNguoiDung " +
           "ORDER BY h.maHopDong DESC")
    List<HopDongDienTu> findByNguoiThueWithChiTietDayDu(@Param("maNguoiDung") Integer maNguoiDung);

    @Query("SELECT h FROM HopDongDienTu h " +
           "JOIN FETCH h.chuTro " +
           "JOIN FETCH h.nguoiThue " +
           "JOIN FETCH h.phong p " +
           "JOIN FETCH p.nhaTro " +
           "WHERE h.chuTro.maNguoiDung = :maNguoiDung " +
           "ORDER BY h.maHopDong DESC")
    List<HopDongDienTu> findByChuTroWithChiTietDayDu(@Param("maNguoiDung") Integer maNguoiDung);
}