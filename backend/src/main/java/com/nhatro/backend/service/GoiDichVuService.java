package com.nhatro.backend.service;

import com.nhatro.backend.entity.GoiDichVu;
import com.nhatro.backend.repository.GoiDichVuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GoiDichVuService {

    private final GoiDichVuRepository goiDichVuRepository;

    public GoiDichVuService(GoiDichVuRepository goiDichVuRepository) {
        Objects.requireNonNull(goiDichVuRepository, "goiDichVuRepository must not be null");
        this.goiDichVuRepository = goiDichVuRepository;
    }

    public List<GoiDichVu> getAll() {
        return goiDichVuRepository.findAll();
    }

    public Optional<GoiDichVu> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return goiDichVuRepository.findById(id);
    }

    public List<GoiDichVu> getActive() {
        return goiDichVuRepository.findByTrangThai(true);
    }

    public GoiDichVu create(GoiDichVu goi) {
        Objects.requireNonNull(goi, "goi must not be null");
        return goiDichVuRepository.save(goi);
    }

    public Optional<GoiDichVu> update(Integer id, GoiDichVu duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return goiDichVuRepository.findById(id).map(g -> {
            g.setTenGoi(duLieuMoi.getTenGoi());
            g.setGia(duLieuMoi.getGia());
            g.setThoiHan(duLieuMoi.getThoiHan());
            g.setSoLuongTin(duLieuMoi.getSoLuongTin());
            g.setUuTien(duLieuMoi.getUuTien());
            g.setMoTa(duLieuMoi.getMoTa());
            g.setTrangThai(duLieuMoi.getTrangThai());
            return goiDichVuRepository.save(g);
        });
    }

    public boolean delete(Integer id) {
        if (goiDichVuRepository.existsById(id)) {
            goiDichVuRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
