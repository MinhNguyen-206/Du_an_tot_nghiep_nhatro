package com.nhatro.backend.service;

import com.nhatro.backend.entity.HopDongPremium;
import com.nhatro.backend.repository.HopDongPremiumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HopDongPremiumService {

    private final HopDongPremiumRepository hopDongPremiumRepository;

    public HopDongPremiumService(HopDongPremiumRepository hopDongPremiumRepository) {
        Objects.requireNonNull(hopDongPremiumRepository, "hopDongPremiumRepository must not be null");
        this.hopDongPremiumRepository = hopDongPremiumRepository;
    }

    public List<HopDongPremium> getAll() {
        return hopDongPremiumRepository.findAll();
    }

    public Optional<HopDongPremium> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return hopDongPremiumRepository.findById(id);
    }

    public List<HopDongPremium> getByDangKy(Integer maDangKy) {
        Objects.requireNonNull(maDangKy, "maDangKy must not be null");
        return hopDongPremiumRepository.findByDangKy_MaDangKy(maDangKy);
    }

    public HopDongPremium create(HopDongPremium hopDong) {
        Objects.requireNonNull(hopDong, "hopDong must not be null");
        return hopDongPremiumRepository.save(hopDong);
    }

    public Optional<HopDongPremium> update(Integer id, HopDongPremium duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return hopDongPremiumRepository.findById(id).map(hd -> {
            hd.setSoHopDong(duLieuMoi.getSoHopDong());
            hd.setNgayKy(duLieuMoi.getNgayKy());
            hd.setNgayBatDau(duLieuMoi.getNgayBatDau());
            hd.setNgayKetThuc(duLieuMoi.getNgayKetThuc());
            hd.setFileHopDong(duLieuMoi.getFileHopDong());
            hd.setTrangThai(duLieuMoi.getTrangThai());
            return hopDongPremiumRepository.save(hd);
        });
    }

    public boolean delete(Integer id) {
        if (hopDongPremiumRepository.existsById(id)) {
            hopDongPremiumRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
