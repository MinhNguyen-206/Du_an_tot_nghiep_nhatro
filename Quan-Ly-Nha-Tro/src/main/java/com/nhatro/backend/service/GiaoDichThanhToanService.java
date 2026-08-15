package com.nhatro.backend.service;

import com.nhatro.backend.entity.GiaoDichThanhToan;
import com.nhatro.backend.repository.GiaoDichThanhToanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GiaoDichThanhToanService {

    private final GiaoDichThanhToanRepository giaoDichRepository;

    public GiaoDichThanhToanService(GiaoDichThanhToanRepository giaoDichRepository) {
        Objects.requireNonNull(giaoDichRepository, "giaoDichRepository must not be null");
        this.giaoDichRepository = giaoDichRepository;
    }

    public List<GiaoDichThanhToan> getAll() {
        return giaoDichRepository.findAll();
    }

    public Optional<GiaoDichThanhToan> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return giaoDichRepository.findById(id);
    }

    public List<GiaoDichThanhToan> getByThanhToan(Integer maThanhToan) {
        Objects.requireNonNull(maThanhToan, "maThanhToan must not be null");
        return giaoDichRepository.findByThanhToan_MaThanhToan(maThanhToan);
    }

    public GiaoDichThanhToan create(GiaoDichThanhToan giaoDich) {
        Objects.requireNonNull(giaoDich, "giaoDich must not be null");
        return giaoDichRepository.save(giaoDich);
    }

    public Optional<GiaoDichThanhToan> update(Integer id, GiaoDichThanhToan duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return giaoDichRepository.findById(id).map(gd -> {
            gd.setMaGiaoDichCongThanhToan(duLieuMoi.getMaGiaoDichCongThanhToan());
            gd.setNganHang(duLieuMoi.getNganHang());
            gd.setNoiDung(duLieuMoi.getNoiDung());
            gd.setNgayGiaoDich(duLieuMoi.getNgayGiaoDich());
            gd.setTrangThai(duLieuMoi.getTrangThai());
            return giaoDichRepository.save(gd);
        });
    }

    public boolean delete(Integer id) {
        if (giaoDichRepository.existsById(id)) {
            giaoDichRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
