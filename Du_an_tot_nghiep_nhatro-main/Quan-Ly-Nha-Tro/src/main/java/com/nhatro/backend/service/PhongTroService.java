package com.nhatro.backend.service;

import com.nhatro.backend.entity.PhongTro;
import com.nhatro.backend.repository.PhongTroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PhongTroService {

    private final PhongTroRepository phongTroRepository;

    public PhongTroService(PhongTroRepository phongTroRepository) {
        Objects.requireNonNull(phongTroRepository, "phongTroRepository must not be null");
        this.phongTroRepository = phongTroRepository;
    }

    public List<PhongTro> getAll() {
        return phongTroRepository.findAll();
    }

    public Optional<PhongTro> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return phongTroRepository.findById(id);
    }

    public List<PhongTro> getByNhaTro(Integer maNhaTro) {
        Objects.requireNonNull(maNhaTro, "maNhaTro must not be null");
        return phongTroRepository.findByNhaTro_MaNhaTro(maNhaTro);
    }

    public List<PhongTro> getByTrangThai(Boolean trangThai) {
        Objects.requireNonNull(trangThai, "trangThai must not be null");
        return phongTroRepository.findByTrangThai(trangThai);
    }

    public PhongTro create(PhongTro phongTro) {
        Objects.requireNonNull(phongTro, "phongTro must not be null");
        return phongTroRepository.save(phongTro);
    }

    public Optional<PhongTro> update(Integer id, PhongTro duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return phongTroRepository.findById(id).map(p -> {
            p.setTenPhong(duLieuMoi.getTenPhong());
            p.setDienTich(duLieuMoi.getDienTich());
            p.setLoaiPhong(duLieuMoi.getLoaiPhong());
            p.setSoLuongNguoi(duLieuMoi.getSoLuongNguoi());
            p.setGiaPhong(duLieuMoi.getGiaPhong());
            p.setGiaDien(duLieuMoi.getGiaDien());
            p.setGiaNuoc(duLieuMoi.getGiaNuoc());
            p.setGiaGuiXe(duLieuMoi.getGiaGuiXe());
            p.setGiaInternet(duLieuMoi.getGiaInternet());
            p.setTrangThai(duLieuMoi.getTrangThai());
            return phongTroRepository.save(p);
        });
    }

    public boolean delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        if (phongTroRepository.existsById(id)) {
            phongTroRepository.deleteById(id);
            return true;
        }
        return false;
    }
}