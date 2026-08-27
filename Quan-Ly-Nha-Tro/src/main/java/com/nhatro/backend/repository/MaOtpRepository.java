package com.nhatro.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhatro.backend.entity.MaOtp;

@Repository
public interface MaOtpRepository extends JpaRepository<MaOtp, Integer> {
    Optional<MaOtp> findFirstByMaHopDongAndVaiTroKyAndMaNguoiDungAndDaSuDungFalseOrderByNgayTaoDesc(
            Integer maHopDong, String vaiTroKy, Integer maNguoiDung);
}