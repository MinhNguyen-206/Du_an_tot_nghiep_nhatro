package com.nhatro.backend.service;

import com.nhatro.backend.entity.PhanQuyen;
import com.nhatro.backend.repository.PhanQuyenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PhanQuyenService {

    private final PhanQuyenRepository phanQuyenRepository;

    public PhanQuyenService(PhanQuyenRepository phanQuyenRepository) {
        this.phanQuyenRepository = phanQuyenRepository;
    }

    public List<PhanQuyen> getAll() {
        return phanQuyenRepository.findAll();
    }

    public Optional<PhanQuyen> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return phanQuyenRepository.findById(id);
    }

    public PhanQuyen create(PhanQuyen phanQuyen) {
        Objects.requireNonNull(phanQuyen, "phanQuyen must not be null");
        return phanQuyenRepository.save(phanQuyen);
    }

    public Optional<PhanQuyen> update(Integer id, PhanQuyen duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return phanQuyenRepository.findById(id).map(pq -> {
            pq.setTenQuyen(duLieuMoi.getTenQuyen());
            pq.setMaQuyenCode(duLieuMoi.getMaQuyenCode());
            pq.setMoTa(duLieuMoi.getMoTa());
            return phanQuyenRepository.save(pq);
        });
    }

    public boolean delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        if (phanQuyenRepository.existsById(id)) {
            phanQuyenRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
