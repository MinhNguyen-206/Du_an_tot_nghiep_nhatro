package com.nhatro.backend.service;

import com.nhatro.backend.entity.DangTin;
import com.nhatro.backend.repository.DangTinRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DangTinService {

    private final DangTinRepository dangTinRepository;

    public DangTinService(DangTinRepository dangTinRepository) {
        Objects.requireNonNull(dangTinRepository, "dangTinRepository must not be null");
        this.dangTinRepository = dangTinRepository;
    }

    public List<DangTin> getAll() {
        return dangTinRepository.findAll();
    }

    public Optional<DangTin> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return dangTinRepository.findById(id);
    }

    public List<DangTin> getByNguoiDung(Integer maNguoiDung) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        return dangTinRepository.findByNguoiDung_MaNguoiDung(maNguoiDung);
    }

    public List<DangTin> getByPhong(Integer maPhong) {
        Objects.requireNonNull(maPhong, "maPhong must not be null");
        return dangTinRepository.findByPhong_MaPhong(maPhong);
    }

    public List<DangTin> getDangHoatDong() {
        return dangTinRepository.findByTrangThai(true);
    }

    public DangTin create(DangTin dangTin) {
        Objects.requireNonNull(dangTin, "dangTin must not be null");
        return dangTinRepository.save(dangTin);
    }

    public Optional<DangTin> update(Integer id, DangTin duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return dangTinRepository.findById(id).map(dt -> {
            dt.setTieuDe(duLieuMoi.getTieuDe());
            dt.setNoiDung(duLieuMoi.getNoiDung());
            dt.setNgayHetHan(duLieuMoi.getNgayHetHan());
            dt.setTrangThai(duLieuMoi.getTrangThai());
            return dangTinRepository.save(dt);
        });
    }

    public boolean delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        if (dangTinRepository.existsById(id)) {
            dangTinRepository.deleteById(id);
            return true;
        }
        return false;
    }
}