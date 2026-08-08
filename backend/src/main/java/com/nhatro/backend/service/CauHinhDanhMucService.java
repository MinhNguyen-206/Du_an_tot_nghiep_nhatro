package com.nhatro.backend.service;

import com.nhatro.backend.entity.CauHinhDanhMuc;
import com.nhatro.backend.repository.CauHinhDanhMucRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CauHinhDanhMucService {

    private final CauHinhDanhMucRepository cauHinhRepository;

    public CauHinhDanhMucService(CauHinhDanhMucRepository cauHinhRepository) {
        Objects.requireNonNull(cauHinhRepository, "cauHinhRepository must not be null");
        this.cauHinhRepository = cauHinhRepository;
    }

    public List<CauHinhDanhMuc> getAll() {
        return cauHinhRepository.findAll();
    }

    public Optional<CauHinhDanhMuc> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return cauHinhRepository.findById(id);
    }

    public List<CauHinhDanhMuc> getActive() {
        return cauHinhRepository.findByTrangThai(true);
    }

    public CauHinhDanhMuc create(CauHinhDanhMuc cauHinh) {
        Objects.requireNonNull(cauHinh, "cauHinh must not be null");
        return cauHinhRepository.save(cauHinh);
    }

    public Optional<CauHinhDanhMuc> update(Integer id, CauHinhDanhMuc duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return cauHinhRepository.findById(id).map(ch -> {
            ch.setTenDanhMuc(duLieuMoi.getTenDanhMuc());
            ch.setMoTa(duLieuMoi.getMoTa());
            ch.setTrangThai(duLieuMoi.getTrangThai());
            return cauHinhRepository.save(ch);
        });
    }

    public boolean delete(Integer id) {
        if (cauHinhRepository.existsById(id)) {
            cauHinhRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
