package com.nhatro.backend.service;

import com.nhatro.backend.entity.XacThucEkyc;
import com.nhatro.backend.repository.XacThucEkycRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class XacThucEkycService {

    private final XacThucEkycRepository ekycRepository;

    public XacThucEkycService(XacThucEkycRepository ekycRepository) {
        Objects.requireNonNull(ekycRepository, "ekycRepository must not be null");
        this.ekycRepository = ekycRepository;
    }

    public List<XacThucEkyc> getAll() {
        return ekycRepository.findAll();
    }

    public Optional<XacThucEkyc> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return ekycRepository.findById(id);
    }

    public List<XacThucEkyc> getByNguoiDung(Integer maNguoiDung) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        return ekycRepository.findByNguoiDung_MaNguoiDung(maNguoiDung);
    }

    /** Lấy danh sách hồ sơ chờ duyệt */
    public List<XacThucEkyc> getPending() {
        return ekycRepository.findAllByOrderByNgayGuiDesc()
                .stream()
                .filter(e -> "CHO_DUYET".equals(e.getTrangThai()))
                .toList();
    }

    public long countPending() {
        return ekycRepository.findAllByOrderByNgayGuiDesc()
                .stream()
                .filter(e -> "CHO_DUYET".equals(e.getTrangThai()))
                .count();
    }

    public XacThucEkyc create(XacThucEkyc ekyc) {
        Objects.requireNonNull(ekyc, "ekyc must not be null");
        return ekycRepository.save(ekyc);
    }

    /** Admin xác minh eKYC */
    public Optional<XacThucEkyc> duyet(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return ekycRepository.findById(id).map(e -> {
            e.setTrangThai("DA_DUYET");
            e.setLyDoTuChoi(null);
            e.setNgayDuyet(java.time.LocalDateTime.now());
            return ekycRepository.save(e);
        });
    }

    /** Admin từ chối eKYC với lý do */
    public Optional<XacThucEkyc> tuChoi(Integer id, String lyDo) {
        Objects.requireNonNull(id, "id must not be null");
        return ekycRepository.findById(id).map(e -> {
            e.setTrangThai("TU_CHOI");
            e.setLyDoTuChoi(lyDo);
            e.setNgayDuyet(java.time.LocalDateTime.now());
            return ekycRepository.save(e);
        });
    }

    public boolean delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        if (ekycRepository.existsById(id)) {
            ekycRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
