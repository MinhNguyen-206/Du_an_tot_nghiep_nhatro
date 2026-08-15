package com.nhatro.backend.service;

import com.nhatro.backend.dto.AuthResponse;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class AuthService {

    private final NguoiDungService nguoiDungService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(NguoiDungService nguoiDungService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        Objects.requireNonNull(nguoiDungService, "nguoiDungService must not be null");
        Objects.requireNonNull(jwtUtil, "jwtUtil must not be null");
        Objects.requireNonNull(passwordEncoder, "passwordEncoder must not be null");
        this.nguoiDungService = nguoiDungService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public NguoiDung login(String email, String password) {
        Objects.requireNonNull(email, "email must not be null");
        Objects.requireNonNull(password, "password must not be null");
        NguoiDung nguoiDung = nguoiDungService.getByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email không tồn tại"));

        if (!passwordEncoder.matches(password, nguoiDung.getMatKhau())) {
            throw new IllegalArgumentException("Mật khẩu không hợp lệ");
        }

        if (nguoiDung.getTrangThai() == null || !nguoiDung.getTrangThai()) {
            throw new IllegalArgumentException("Tài khoản đã bị khóa");
        }

        return nguoiDung;
    }

    public AuthResponse buildAuthResponse(NguoiDung nguoiDung) {
        Integer maVaiTro = nguoiDung.getVaiTro() != null ? nguoiDung.getVaiTro().getMaVaiTro() : null;
        String token = jwtUtil.generateToken(nguoiDung.getEmail(), maVaiTro);
        return new AuthResponse(token, nguoiDung);
    }
}
