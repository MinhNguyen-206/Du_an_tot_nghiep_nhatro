package com.nhatro.backend.service;

import com.nhatro.backend.entity.YeuCauThue;
import com.nhatro.backend.repository.YeuCauThueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class YeuCauThueService {

    private final YeuCauThueRepository yeuCauThueRepository;

    public YeuCauThueService(YeuCauThueRepository yeuCauThueRepository) {
        Objects.requireNonNull(yeuCauThueRepository, "yeuCauThueRepository must not be null");
        this.yeuCauThueRepository = yeuCauThueRepository;
    }

    public List<YeuCauThue> getAll() {
        return yeuCauThueRepository.findAll();
    }

    public Optional<YeuCauThue> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return yeuCauThueRepository.findById(id);
    }

    public List<YeuCauThue> getByNguoiThue(Integer maNguoiDung) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        return yeuCauThueRepository.findByNguoiThue_MaNguoiDung(maNguoiDung);
    }

    public List<YeuCauThue> getByPhong(Integer maPhong) {
        Objects.requireNonNull(maPhong, "maPhong must not be null");
        return yeuCauThueRepository.findByPhong_MaPhong(maPhong);
    }

    public List<YeuCauThue> getByTrangThai(String trangThai) {
        Objects.requireNonNull(trangThai, "trangThai must not be null");
        return yeuCauThueRepository.findByTrangThai(trangThai);
    }

    public YeuCauThue create(YeuCauThue yeuCauThue) {
        Objects.requireNonNull(yeuCauThue, "yeuCauThue must not be null");
        return yeuCauThueRepository.save(yeuCauThue);
    }

    public Optional<YeuCauThue> update(Integer id, YeuCauThue duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return yeuCauThueRepository.findById(id).map(yc -> {
            yc.setNgayMuonNhanPhong(duLieuMoi.getNgayMuonNhanPhong());
            yc.setGhiChu(duLieuMoi.getGhiChu());
            yc.setTrangThai(duLieuMoi.getTrangThai());
            return yeuCauThueRepository.save(yc);
        });
    }

    public boolean delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        if (yeuCauThueRepository.existsById(id)) {
            yeuCauThueRepository.deleteById(id);
            return true;
        }
        return false;
    }
}