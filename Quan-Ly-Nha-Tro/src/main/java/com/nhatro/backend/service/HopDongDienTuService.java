package com.nhatro.backend.service;

import com.nhatro.backend.entity.HopDongDienTu;
import com.nhatro.backend.repository.HopDongDienTuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HopDongDienTuService {

    private final HopDongDienTuRepository hopDongRepository;

    public HopDongDienTuService(HopDongDienTuRepository hopDongRepository) {
        Objects.requireNonNull(hopDongRepository, "hopDongRepository must not be null");
        this.hopDongRepository = hopDongRepository;
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