package com.nhatro.backend.service;

import com.nhatro.backend.entity.HoaDonPremium;
import com.nhatro.backend.repository.HoaDonPremiumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HoaDonPremiumService {

    private final HoaDonPremiumRepository hoaDonPremiumRepository;

    public HoaDonPremiumService(HoaDonPremiumRepository hoaDonPremiumRepository) {
        Objects.requireNonNull(hoaDonPremiumRepository, "hoaDonPremiumRepository must not be null");
        this.hoaDonPremiumRepository = hoaDonPremiumRepository;
    }

    public List<HoaDonPremium> getAll() {
        return hoaDonPremiumRepository.findAll();
    }

    public Optional<HoaDonPremium> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return hoaDonPremiumRepository.findById(id);
    }

    public List<HoaDonPremium> getByHopDong(Integer maHopDongPremium) {
        return hoaDonPremiumRepository.findByHopDongPremium_MaHopDongPremium(maHopDongPremium);
    }

    public HoaDonPremium create(HoaDonPremium hoaDon) {
        Objects.requireNonNull(hoaDon, "hoaDon must not be null");
        return hoaDonPremiumRepository.save(hoaDon);
    }

    public Optional<HoaDonPremium> update(Integer id, HoaDonPremium duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return hoaDonPremiumRepository.findById(id).map(hd -> {
            hd.setSoTien(duLieuMoi.getSoTien());
            hd.setHanThanhToan(duLieuMoi.getHanThanhToan());
            hd.setTrangThai(duLieuMoi.getTrangThai());
            return hoaDonPremiumRepository.save(hd);
        });
    }

    public boolean delete(Integer id) {
        if (hoaDonPremiumRepository.existsById(id)) {
            hoaDonPremiumRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
