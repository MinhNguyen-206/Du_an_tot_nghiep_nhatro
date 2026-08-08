package com.nhatro.backend.service;

import com.nhatro.backend.entity.HoaDonThang;
import com.nhatro.backend.repository.HoaDonThangRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HoaDonThangService {

    private final HoaDonThangRepository hoaDonThangRepository;

    public HoaDonThangService(HoaDonThangRepository hoaDonThangRepository) {
        Objects.requireNonNull(hoaDonThangRepository, "hoaDonThangRepository must not be null");
        this.hoaDonThangRepository = hoaDonThangRepository;
    }

    public List<HoaDonThang> getAll() {
        return hoaDonThangRepository.findAll();
    }

    public Optional<HoaDonThang> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return hoaDonThangRepository.findById(id);
    }

    public List<HoaDonThang> getByHopDong(Integer maHopDong) {
        Objects.requireNonNull(maHopDong, "maHopDong must not be null");
        return hoaDonThangRepository.findByHopDong_MaHopDong(maHopDong);
    }

    public List<HoaDonThang> getByTrangThai(String trangThai) {
        Objects.requireNonNull(trangThai, "trangThai must not be null");
        return hoaDonThangRepository.findByTrangThai(trangThai);
    }

    public HoaDonThang create(HoaDonThang hoaDon) {
        Objects.requireNonNull(hoaDon, "hoaDon must not be null");
        return hoaDonThangRepository.save(hoaDon);
    }

    public Optional<HoaDonThang> update(Integer id, HoaDonThang duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return hoaDonThangRepository.findById(id).map(hd -> {
            hd.setTongTien(duLieuMoi.getTongTien());
            hd.setHanThanhToan(duLieuMoi.getHanThanhToan());
            hd.setTrangThai(duLieuMoi.getTrangThai());
            return hoaDonThangRepository.save(hd);
        });
    }

    public boolean delete(Integer id) {
        if (hoaDonThangRepository.existsById(id)) {
            hoaDonThangRepository.deleteById(id);
            return true;
        }
        return false;
    }
}