package com.nhatro.backend.service;

import com.nhatro.backend.entity.TinNhan;
import com.nhatro.backend.repository.TinNhanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TinNhanService {

    private final TinNhanRepository tinNhanRepository;

    public TinNhanService(TinNhanRepository tinNhanRepository) {
        Objects.requireNonNull(tinNhanRepository, "tinNhanRepository must not be null");
        this.tinNhanRepository = tinNhanRepository;
    }

    public List<TinNhan> getAll() {
        return tinNhanRepository.findAll();
    }

    public Optional<TinNhan> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return tinNhanRepository.findById(id);
    }

    public List<TinNhan> getByNguoiGui(Integer maNguoiDung) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        return tinNhanRepository.findByNguoiGui_MaNguoiDung(maNguoiDung);
    }

    public List<TinNhan> getByNguoiNhan(Integer maNguoiDung) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        return tinNhanRepository.findByNguoiNhan_MaNguoiDung(maNguoiDung);
    }

    public TinNhan create(TinNhan tinNhan) {
        Objects.requireNonNull(tinNhan, "tinNhan must not be null");
        tinNhan.setDaDoc(false);
        return tinNhanRepository.save(tinNhan);
    }

    public Optional<TinNhan> markAsRead(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return tinNhanRepository.findById(id).map(tn -> {
            tn.setDaDoc(true);
            return tinNhanRepository.save(tn);
        });
    }

    public boolean delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        if (tinNhanRepository.existsById(id)) {
            tinNhanRepository.deleteById(id);
            return true;
        }
        return false;
    }
}