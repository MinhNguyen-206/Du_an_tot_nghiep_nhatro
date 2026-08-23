package com.nhatro.backend.service;

import com.nhatro.backend.entity.BoDieuKhienAi;
import com.nhatro.backend.repository.BoDieuKhienAiRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BoDieuKhienAiService {

    private final BoDieuKhienAiRepository boDieuKhienAiRepository;

    public BoDieuKhienAiService(BoDieuKhienAiRepository boDieuKhienAiRepository) {
        Objects.requireNonNull(boDieuKhienAiRepository, "boDieuKhienAiRepository must not be null");
        this.boDieuKhienAiRepository = boDieuKhienAiRepository;
    }

    public List<BoDieuKhienAi> getAll() {
        return boDieuKhienAiRepository.findAll();
    }

    public Optional<BoDieuKhienAi> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return boDieuKhienAiRepository.findById(id);
    }

    public BoDieuKhienAi create(BoDieuKhienAi ai) {
        Objects.requireNonNull(ai, "ai must not be null");
        return boDieuKhienAiRepository.save(ai);
    }

    public Optional<BoDieuKhienAi> update(Integer id, BoDieuKhienAi duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return boDieuKhienAiRepository.findById(id).map(ai -> {
            ai.setTenMoHinh(duLieuMoi.getTenMoHinh());
            ai.setPhienBan(duLieuMoi.getPhienBan());
            ai.setTrangThai(duLieuMoi.getTrangThai());
            return boDieuKhienAiRepository.save(ai);
        });
    }

    public boolean delete(Integer id) {
        if (boDieuKhienAiRepository.existsById(id)) {
            boDieuKhienAiRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
