package com.nhatro.backend.service;

import com.nhatro.backend.dto.AuthResponse;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final NguoiDungService nguoiDungService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(NguoiDungService nguoiDungService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.nguoiDungService = nguoiDungService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public NguoiDung login(String email, String password) {
        Optional<NguoiDung> nguoiDungOpt = nguoiDungService.getAll().stream()
                .filter(user -> user.getEmail() != null && user.getEmail().equalsIgnoreCase(email))
                .findFirst();

        if (nguoiDungOpt.isEmpty()) {
            throw new IllegalArgumentException("Email không tồn tại");
        }

        NguoiDung nguoiDung = nguoiDungOpt.get();
        if (!passwordEncoder.matches(password, nguoiDung.getMatKhauMaHoa())) {
            throw new IllegalArgumentException("Mật khẩu không hợp lệ");
        }

        return nguoiDung;
    }

    public AuthResponse buildAuthResponse(NguoiDung nguoiDung) {
        String token = jwtUtil.generateToken(nguoiDung.getEmail(), nguoiDung.getVaiTro());
        return new AuthResponse(token, nguoiDung);
    }
}
