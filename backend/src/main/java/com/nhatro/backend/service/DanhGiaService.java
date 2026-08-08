package com.nhatro.backend.service;

import com.nhatro.backend.entity.DanhGia;
import com.nhatro.backend.repository.DanhGiaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DanhGiaService {

    private final DanhGiaRepository danhGiaRepository;

    public DanhGiaService(DanhGiaRepository danhGiaRepository) {
        Objects.requireNonNull(danhGiaRepository, "danhGiaRepository must not be null");
        this.danhGiaRepository = danhGiaRepository;
    }

    public List<DanhGia> getAll() {
        return danhGiaRepository.findAll();
    }

    public Optional<DanhGia> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return danhGiaRepository.findById(id);
    }

    public List<DanhGia> getByPhong(Integer maPhong) {
        Objects.requireNonNull(maPhong, "maPhong must not be null");
        return danhGiaRepository.findByPhong_MaPhong(maPhong);
    }

    public List<DanhGia> getByNguoiDung(Integer maNguoiDung) {
        return danhGiaRepository.findByNguoiDung_MaNguoiDung(maNguoiDung);
    }

    public DanhGia create(DanhGia danhGia) {
        Objects.requireNonNull(danhGia, "danhGia must not be null");
        return danhGiaRepository.save(danhGia);
    }

    public Optional<DanhGia> update(Integer id, DanhGia duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return danhGiaRepository.findById(id).map(dg -> {
            dg.setSoSao(duLieuMoi.getSoSao());
            dg.setNoiDung(duLieuMoi.getNoiDung());
            dg.setTrangThai(duLieuMoi.getTrangThai());
            return danhGiaRepository.save(dg);
        });
    }

    public boolean delete(Integer id) {
        if (danhGiaRepository.existsById(id)) {
            danhGiaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}