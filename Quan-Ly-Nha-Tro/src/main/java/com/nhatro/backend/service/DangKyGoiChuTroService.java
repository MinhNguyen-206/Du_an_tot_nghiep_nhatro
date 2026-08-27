package com.nhatro.backend.service;

import com.nhatro.backend.entity.DangKyGoiChuTro;
import com.nhatro.backend.repository.DangKyGoiChuTroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DangKyGoiChuTroService {

    private final DangKyGoiChuTroRepository dangKyRepository;

    public DangKyGoiChuTroService(DangKyGoiChuTroRepository dangKyRepository) {
        Objects.requireNonNull(dangKyRepository, "dangKyRepository must not be null");
        this.dangKyRepository = dangKyRepository;
    }

    public List<DangKyGoiChuTro> getAll() {
        return dangKyRepository.findAll();
    }

    public Optional<DangKyGoiChuTro> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return dangKyRepository.findById(id);
    }

    public List<DangKyGoiChuTro> getByChuTro(Integer maNguoiDung) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        return dangKyRepository.findByChuTro_MaNguoiDung(maNguoiDung);
    }

    public DangKyGoiChuTro create(DangKyGoiChuTro dangKy) {
        Objects.requireNonNull(dangKy, "dangKy must not be null");
        return dangKyRepository.save(dangKy);
    }

    public Optional<DangKyGoiChuTro> update(Integer id, DangKyGoiChuTro duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return dangKyRepository.findById(id).map(dk -> {
            dk.setNgayHetHan(duLieuMoi.getNgayHetHan());
            dk.setTrangThai(duLieuMoi.getTrangThai());
            return dangKyRepository.save(dk);
        });
    }

    public boolean delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        if (dangKyRepository.existsById(id)) {
            dangKyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}