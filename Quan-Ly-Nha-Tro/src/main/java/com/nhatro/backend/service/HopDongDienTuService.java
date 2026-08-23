package com.nhatro.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nhatro.backend.entity.HopDongDienTu;
import com.nhatro.backend.entity.ThanhToanCoc;
import com.nhatro.backend.entity.YeuCauThue;
import com.nhatro.backend.repository.HopDongDienTuRepository;
import com.nhatro.backend.repository.ThanhToanCocRepository;

@Service
public class HopDongDienTuService {

    private final HopDongDienTuRepository hopDongRepository;
    private final ThanhToanCocRepository thanhToanCocRepository;

    public HopDongDienTuService(HopDongDienTuRepository hopDongRepository,
                                 ThanhToanCocRepository thanhToanCocRepository) {
        Objects.requireNonNull(hopDongRepository, "hopDongRepository must not be null");
        this.hopDongRepository = hopDongRepository;
        this.thanhToanCocRepository = thanhToanCocRepository;
    }

    public Optional<HopDongDienTu> getByYeuCauThue(Integer maYeuCau) {
        Objects.requireNonNull(maYeuCau, "maYeuCau must not be null");
        return hopDongRepository.findByYeuCauThue_MaYeuCau(maYeuCau);
    }

    public HopDongDienTu taoTuYeuCau(YeuCauThue yeuCau, HopDongDienTu duLieuHopDong) {
        Objects.requireNonNull(yeuCau, "yeuCau must not be null");
        Objects.requireNonNull(duLieuHopDong, "duLieuHopDong must not be null");

        duLieuHopDong.setYeuCauThue(yeuCau);
        duLieuHopDong.setPhong(yeuCau.getPhong());
        duLieuHopDong.setChuTro(yeuCau.getPhong().getNhaTro().getNguoiDung());
        duLieuHopDong.setNguoiThue(yeuCau.getNguoiThue());
        duLieuHopDong.setDaKyChuTro(false);
        duLieuHopDong.setDaKyNguoiThue(false);
        duLieuHopDong.setTrangThai("Chờ ký");
        return hopDongRepository.save(duLieuHopDong);
    }

    public HopDongDienTu kyChuTro(HopDongDienTu hopDong, String chuKy) {
        hopDong.setChuKyChuTro(chuKy);
        hopDong.setDaKyChuTro(true);
        hopDong.setNgayKyChuTro(LocalDateTime.now());
        capNhatTrangThaiSauKhiKy(hopDong);
        return hopDongRepository.save(hopDong);
    }

    public HopDongDienTu kyNguoiThue(HopDongDienTu hopDong, String chuKy) {
        hopDong.setChuKyNguoiThue(chuKy);
        hopDong.setDaKyNguoiThue(true);
        hopDong.setNgayKyNguoiThue(LocalDateTime.now());
        capNhatTrangThaiSauKhiKy(hopDong);
        return hopDongRepository.save(hopDong);
    }

    private void capNhatTrangThaiSauKhiKy(HopDongDienTu hopDong) {
        if (Boolean.TRUE.equals(hopDong.getDaKyChuTro()) && Boolean.TRUE.equals(hopDong.getDaKyNguoiThue())) {
            hopDong.setTrangThai("Đã ký, chờ thanh toán");
            hopDong.setNgayKy(LocalDateTime.now());
        } else if (Boolean.TRUE.equals(hopDong.getDaKyChuTro())) {
            hopDong.setTrangThai("Chờ người thuê ký");
        } else if (Boolean.TRUE.equals(hopDong.getDaKyNguoiThue())) {
            hopDong.setTrangThai("Chờ chủ trọ ký");
        }
    }

    public HopDongDienTu thanhToanCoc(HopDongDienTu hopDong, String phuongThuc) {
        BigDecimal soTien = hopDong.getTienCoc() != null ? hopDong.getTienCoc() : BigDecimal.ZERO;

        ThanhToanCoc thanhToan = ThanhToanCoc.builder()
                .hopDong(hopDong)
                .soTien(soTien)
                .ngayThanhToan(LocalDateTime.now())
                .phuongThuc(phuongThuc != null ? phuongThuc : "Chuyển khoản (mô phỏng)")
                .trangThai("Thành công")
                .build();
        thanhToanCocRepository.save(thanhToan);

        hopDong.setTrangThai("Hoàn tất");
        return hopDongRepository.save(hopDong);
    }

    public List<HopDongDienTu> getAll() {
        return hopDongRepository.findAll();
    }

    public Optional<HopDongDienTu> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return hopDongRepository.findById(id);
    }

    public List<HopDongDienTu> getByNguoiThue(Integer maNguoiDung) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        return hopDongRepository.findByNguoiThue_MaNguoiDung(maNguoiDung);
    }

    public List<HopDongDienTu> getByChuTro(Integer maNguoiDung) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        return hopDongRepository.findByChuTro_MaNguoiDung(maNguoiDung);
    }

    public List<HopDongDienTu> getByPhong(Integer maPhong) {
        return hopDongRepository.findByPhong_MaPhong(maPhong);
    }

    public List<HopDongDienTu> getByTrangThai(String trangThai) {
        return hopDongRepository.findByTrangThai(trangThai);
    }

    public HopDongDienTu create(HopDongDienTu hopDong) {
        Objects.requireNonNull(hopDong, "hopDong must not be null");
        return hopDongRepository.save(hopDong);
    }

    public Optional<HopDongDienTu> update(Integer id, HopDongDienTu duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return hopDongRepository.findById(id).map(hd -> {
            hd.setNgayBatDau(duLieuMoi.getNgayBatDau());
            hd.setNgayKetThuc(duLieuMoi.getNgayKetThuc());
            hd.setTienCoc(duLieuMoi.getTienCoc());
            hd.setGiaThue(duLieuMoi.getGiaThue());
            hd.setFileHopDong(duLieuMoi.getFileHopDong());
            hd.setTrangThai(duLieuMoi.getTrangThai());
            hd.setNgayKy(duLieuMoi.getNgayKy());
            return hopDongRepository.save(hd);
        });
    }

    public boolean delete(Integer id) {
        if (hopDongRepository.existsById(id)) {
            hopDongRepository.deleteById(id);
            return true;
        }
        return false;
    }
}