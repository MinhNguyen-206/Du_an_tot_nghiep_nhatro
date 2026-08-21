package com.nhatro.backend.service;

import com.nhatro.backend.entity.ChiSoDienNuoc;
import com.nhatro.backend.repository.ChiSoDienNuocRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ChiSoDienNuocService {

    private final ChiSoDienNuocRepository chiSoRepository;

    public ChiSoDienNuocService(ChiSoDienNuocRepository chiSoRepository) {
        Objects.requireNonNull(chiSoRepository, "chiSoRepository must not be null");
        this.chiSoRepository = chiSoRepository;
    }

    public List<ChiSoDienNuoc> getAll() {
        return chiSoRepository.findAll();
    }

    public Optional<ChiSoDienNuoc> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return chiSoRepository.findById(id);
    }

    public List<ChiSoDienNuoc> getByPhong(Integer maPhong) {
        Objects.requireNonNull(maPhong, "maPhong must not be null");
        return chiSoRepository.findByPhong_MaPhong(maPhong);
    }

    public ChiSoDienNuoc create(ChiSoDienNuoc chiSo) {
        Objects.requireNonNull(chiSo, "chiSo must not be null");
        return chiSoRepository.save(chiSo);
    }

    public Optional<ChiSoDienNuoc> update(Integer id, ChiSoDienNuoc duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return chiSoRepository.findById(id).map(cs -> {
            cs.setThang(duLieuMoi.getThang());
            cs.setNam(duLieuMoi.getNam());
            cs.setChiSoDienCu(duLieuMoi.getChiSoDienCu());
            cs.setChiSoDienMoi(duLieuMoi.getChiSoDienMoi());
            cs.setChiSoNuocCu(duLieuMoi.getChiSoNuocCu());
            cs.setChiSoNuocMoi(duLieuMoi.getChiSoNuocMoi());
            return chiSoRepository.save(cs);
        });
    }

    public boolean delete(Integer id) {
        if (chiSoRepository.existsById(id)) {
            chiSoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}