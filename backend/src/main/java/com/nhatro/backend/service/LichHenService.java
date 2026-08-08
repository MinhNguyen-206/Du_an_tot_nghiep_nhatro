package com.nhatro.backend.service;

import com.nhatro.backend.entity.LichHen;
import com.nhatro.backend.repository.LichHenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LichHenService {

    private final LichHenRepository lichHenRepository;

    public LichHenService(LichHenRepository lichHenRepository) {
        Objects.requireNonNull(lichHenRepository, "lichHenRepository must not be null");
        this.lichHenRepository = lichHenRepository;
    }

    public List<LichHen> getAll() {
        return lichHenRepository.findAll();
    }

    public Optional<LichHen> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return lichHenRepository.findById(id);
    }

    public List<LichHen> getByNguoiDung(Integer maNguoiDung) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        return lichHenRepository.findByNguoiDung_MaNguoiDung(maNguoiDung);
    }

    public List<LichHen> getByPhong(Integer maPhong) {
        Objects.requireNonNull(maPhong, "maPhong must not be null");
        return lichHenRepository.findByPhong_MaPhong(maPhong);
    }

    public LichHen create(LichHen lichHen) {
        Objects.requireNonNull(lichHen, "lichHen must not be null");
        return lichHenRepository.save(lichHen);
    }

    public Optional<LichHen> update(Integer id, LichHen duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return lichHenRepository.findById(id).map(lh -> {
            lh.setNgayHen(duLieuMoi.getNgayHen());
            lh.setGioHen(duLieuMoi.getGioHen());
            lh.setDiaDiem(duLieuMoi.getDiaDiem());
            lh.setGhiChu(duLieuMoi.getGhiChu());
            lh.setTrangThai(duLieuMoi.getTrangThai());
            return lichHenRepository.save(lh);
        });
    }

    public boolean delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        if (lichHenRepository.existsById(id)) {
            lichHenRepository.deleteById(id);
            return true;
        }
        return false;
    }
}