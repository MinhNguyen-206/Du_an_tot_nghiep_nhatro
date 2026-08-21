package com.nhatro.backend.service;

import com.nhatro.backend.entity.ThanhToanCoc;
import com.nhatro.backend.repository.ThanhToanCocRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ThanhToanCocService {

    private final ThanhToanCocRepository thanhToanCocRepository;

    public ThanhToanCocService(ThanhToanCocRepository thanhToanCocRepository) {
        Objects.requireNonNull(thanhToanCocRepository, "thanhToanCocRepository must not be null");
        this.thanhToanCocRepository = thanhToanCocRepository;
    }

    public List<ThanhToanCoc> getAll() {
        return thanhToanCocRepository.findAll();
    }

    public Optional<ThanhToanCoc> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return thanhToanCocRepository.findById(id);
    }

    public List<ThanhToanCoc> getByHopDong(Integer maHopDong) {
        Objects.requireNonNull(maHopDong, "maHopDong must not be null");
        return thanhToanCocRepository.findByHopDong_MaHopDong(maHopDong);
    }

    public ThanhToanCoc create(ThanhToanCoc thanhToan) {
        Objects.requireNonNull(thanhToan, "thanhToan must not be null");
        return thanhToanCocRepository.save(thanhToan);
    }

    public Optional<ThanhToanCoc> update(Integer id, ThanhToanCoc duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return thanhToanCocRepository.findById(id).map(tt -> {
            tt.setSoTien(duLieuMoi.getSoTien());
            tt.setNgayThanhToan(duLieuMoi.getNgayThanhToan());
            tt.setPhuongThuc(duLieuMoi.getPhuongThuc());
            tt.setTrangThai(duLieuMoi.getTrangThai());
            return thanhToanCocRepository.save(tt);
        });
    }

    public boolean delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        if (thanhToanCocRepository.existsById(id)) {
            thanhToanCocRepository.deleteById(id);
            return true;
        }
        return false;
    }
}