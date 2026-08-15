package com.nhatro.backend.service;

import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.repository.NguoiDungRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NguoiDungService {

    private final NguoiDungRepository nguoiDungRepository;
    private final PasswordEncoder passwordEncoder;

    public NguoiDungService(NguoiDungRepository nguoiDungRepository, PasswordEncoder passwordEncoder) {
        Objects.requireNonNull(nguoiDungRepository, "nguoiDungRepository must not be null");
        Objects.requireNonNull(passwordEncoder, "passwordEncoder must not be null");
        this.nguoiDungRepository = nguoiDungRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<NguoiDung> getAll() {
        return nguoiDungRepository.findAll();
    }

    public Optional<NguoiDung> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return nguoiDungRepository.findById(id);
    }

    public Optional<NguoiDung> getByEmail(String email) {
        Objects.requireNonNull(email, "email must not be null");
        return nguoiDungRepository.findByEmail(email);
    }

    public NguoiDung create(NguoiDung nguoiDung) {
        Objects.requireNonNull(nguoiDung, "nguoiDung must not be null");
        if (nguoiDung.getMatKhau() != null && !nguoiDung.getMatKhau().isEmpty()) {
            nguoiDung.setMatKhau(passwordEncoder.encode(nguoiDung.getMatKhau()));
        }
        return nguoiDungRepository.save(nguoiDung);
    }

    public Optional<NguoiDung> update(Integer id, NguoiDung duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return nguoiDungRepository.findById(id).map(nd -> {
            nd.setHoTen(duLieuMoi.getHoTen());
            nd.setSoDienThoai(duLieuMoi.getSoDienThoai());
            nd.setDiaChi(duLieuMoi.getDiaChi());
            nd.setAvatar(duLieuMoi.getAvatar());
            nd.setGioiTinh(duLieuMoi.getGioiTinh());
            nd.setNgaySinh(duLieuMoi.getNgaySinh());
            nd.setNgayCapNhat(LocalDateTime.now());
            return nguoiDungRepository.save(nd);
        });
    }

    public boolean delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        if (nguoiDungRepository.existsById(id)) {
            nguoiDungRepository.deleteById(id);
            return true;
        }
        return false;
    }
}