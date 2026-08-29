package com.nhatro.backend.service;

import com.nhatro.backend.entity.BaoCao;
import com.nhatro.backend.repository.BaoCaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BaoCaoService {

    private final BaoCaoRepository baoCaoRepository;

    public BaoCaoService(BaoCaoRepository baoCaoRepository) {
        Objects.requireNonNull(baoCaoRepository, "baoCaoRepository must not be null");
        this.baoCaoRepository = baoCaoRepository;
    }

    public List<BaoCao> getAll() {
        return baoCaoRepository.findAll();
    }

    public Optional<BaoCao> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return baoCaoRepository.findById(id);
    }

    public List<BaoCao> getByNguoiGui(Integer maNguoiDung) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        return baoCaoRepository.findByNguoiGui_MaNguoiDung(maNguoiDung);
    }

    public List<BaoCao> getByTrangThai(String trangThai) {
        Objects.requireNonNull(trangThai, "trangThai must not be null");
        return baoCaoRepository.findByTrangThai(trangThai);
    }

    /** Lấy tất cả báo cáo, sắp xếp mới nhất trước */
    public Page<BaoCao> getAllPaged(Pageable pageable) {
        return baoCaoRepository.findAll(pageable);
    }

    public Page<BaoCao> getByTrangThaiPaged(String trangThai, Pageable pageable) {
        Objects.requireNonNull(trangThai, "trangThai must not be null");
        return baoCaoRepository.findByTrangThai(trangThai, pageable);
    }

    /** Admin cập nhật trạng thái xử lý báo cáo */
    public Optional<BaoCao> capNhatTrangThai(Integer id, String trangThai) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(trangThai, "trangThai must not be null");
        return baoCaoRepository.findById(id).map(bc -> {
            bc.setTrangThai(trangThai);
            return baoCaoRepository.save(bc);
        });
    }

    public BaoCao create(BaoCao baoCao) {
        Objects.requireNonNull(baoCao, "baoCao must not be null");
        return baoCaoRepository.save(baoCao);
    }

    public Optional<BaoCao> update(Integer id, BaoCao duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return baoCaoRepository.findById(id).map(bc -> {
            bc.setTrangThai(duLieuMoi.getTrangThai());
            return baoCaoRepository.save(bc);
        });
    }

    public boolean delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        if (baoCaoRepository.existsById(id)) {
            baoCaoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
