package com.nhatro.backend.service;

import com.nhatro.backend.entity.NhaTro;
import com.nhatro.backend.repository.NhaTroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NhaTroService {

    private final NhaTroRepository nhaTroRepository;

    public NhaTroService(NhaTroRepository nhaTroRepository) {
        Objects.requireNonNull(nhaTroRepository, "nhaTroRepository must not be null");
        this.nhaTroRepository = nhaTroRepository;
    }

    public List<NhaTro> getAll() {
        return nhaTroRepository.findAll();
    }

    public Optional<NhaTro> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return nhaTroRepository.findById(id);
    }

    public List<NhaTro> getByNguoiDung(Integer maNguoiDung) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        return nhaTroRepository.findByNguoiDung_MaNguoiDung(maNguoiDung);
    }

    public NhaTro create(NhaTro nhaTro) {
        Objects.requireNonNull(nhaTro, "nhaTro must not be null");
        return nhaTroRepository.save(nhaTro);
    }

    public Optional<NhaTro> update(Integer id, NhaTro duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return nhaTroRepository.findById(id).map(nt -> {
            nt.setTenNhaTro(duLieuMoi.getTenNhaTro());
            nt.setDiaChi(duLieuMoi.getDiaChi());
            nt.setSoSao(duLieuMoi.getSoSao());
            nt.setMoTa(duLieuMoi.getMoTa());
            return nhaTroRepository.save(nt);
        });
    }

    public boolean delete(Integer id) {
        if (nhaTroRepository.existsById(id)) {
            nhaTroRepository.deleteById(id);
            return true;
        }
        return false;
    }
}