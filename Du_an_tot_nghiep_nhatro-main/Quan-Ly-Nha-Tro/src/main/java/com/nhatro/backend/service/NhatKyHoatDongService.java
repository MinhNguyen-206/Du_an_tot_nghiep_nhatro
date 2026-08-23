package com.nhatro.backend.service;

import com.nhatro.backend.entity.NhatKyHoatDong;
import com.nhatro.backend.repository.NhatKyHoatDongRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NhatKyHoatDongService {

    private final NhatKyHoatDongRepository nhatKyRepository;

    public NhatKyHoatDongService(NhatKyHoatDongRepository nhatKyRepository) {
        Objects.requireNonNull(nhatKyRepository, "nhatKyRepository must not be null");
        this.nhatKyRepository = nhatKyRepository;
    }

    public List<NhatKyHoatDong> getAll() {
        return nhatKyRepository.findAll();
    }

    public Optional<NhatKyHoatDong> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return nhatKyRepository.findById(id);
    }

    public List<NhatKyHoatDong> getByNguoiDung(Integer maNguoiDung) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        return nhatKyRepository.findByNguoiDung_MaNguoiDung(maNguoiDung);
    }

    public NhatKyHoatDong create(NhatKyHoatDong nhatKy) {
        Objects.requireNonNull(nhatKy, "nhatKy must not be null");
        return nhatKyRepository.save(nhatKy);
    }

    public Optional<NhatKyHoatDong> update(Integer id, NhatKyHoatDong duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return nhatKyRepository.findById(id).map(nk -> {
            nk.setHanhDong(duLieuMoi.getHanhDong());
            nk.setDoiTuong(duLieuMoi.getDoiTuong());
            nk.setDiaChiIP(duLieuMoi.getDiaChiIP());
            nk.setNguoiDung(duLieuMoi.getNguoiDung());
            return nhatKyRepository.save(nk);
        });
    }

    public boolean delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        if (nhatKyRepository.existsById(id)) {
            nhatKyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
