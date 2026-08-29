package com.nhatro.backend.repository;

import com.nhatro.backend.entity.XacThucEkyc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface XacThucEkycRepository extends JpaRepository<XacThucEkyc, Integer> {
    List<XacThucEkyc> findByNguoiDung_MaNguoiDung(Integer maNguoiDung);
    Optional<XacThucEkyc> findTopByNguoiDung_MaNguoiDungOrderByNgayGuiDesc(Integer maNguoiDung);

    // Dung de tinh trang thai eKYC moi nhat cho CA danh sach nguoi dung cung
    // luc (trang Quan ly nguoi dung): sap xep giam dan theo ngay gui, ban ghi
    // dau tien gap duoc cho moi maNguoiDung chinh la ban ghi moi nhat.
    List<XacThucEkyc> findAllByOrderByNgayGuiDesc();
}
