package com.nhatro.backend.service;

import com.nhatro.backend.entity.ThongBao;
import com.nhatro.backend.repository.ThongBaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ThongBaoService {

    private final ThongBaoRepository thongBaoRepository;

    public ThongBaoService(ThongBaoRepository thongBaoRepository) {
        Objects.requireNonNull(thongBaoRepository, "thongBaoRepository must not be null");
        this.thongBaoRepository = thongBaoRepository;
    }

    public List<ThongBao> getAll() {
        return thongBaoRepository.findAll();
    }

    public Optional<ThongBao> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return thongBaoRepository.findById(id);
    }

    public List<ThongBao> getByNguoiDung(Integer maNguoiDung) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        return thongBaoRepository.findByNguoiDung_MaNguoiDung(maNguoiDung);
    }

    public List<ThongBao> getChuaDoc(Integer maNguoiDung) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        return thongBaoRepository.findByNguoiDung_MaNguoiDungAndDaDoc(maNguoiDung, false);
    }

    public ThongBao create(ThongBao thongBao) {
        Objects.requireNonNull(thongBao, "thongBao must not be null");
        thongBao.setDaDoc(false);
        return thongBaoRepository.save(thongBao);
    }

    public Optional<ThongBao> markAsRead(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return thongBaoRepository.findById(id).map(tb -> {
            tb.setDaDoc(true);
            return thongBaoRepository.save(tb);
        });
    }

    public boolean delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        if (thongBaoRepository.existsById(id)) {
            thongBaoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}