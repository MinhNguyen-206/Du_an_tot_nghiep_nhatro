package com.nhatro.backend.service;

import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthServiceTest {

    @Test
    void loginWithValidCredentialsReturnsTokenAndUser() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        NguoiDungService nguoiDungService = new NguoiDungService(passwordEncoder);
        JwtUtil jwtUtil = new JwtUtil();
        AuthService authService = new AuthService(nguoiDungService, jwtUtil, passwordEncoder);

        NguoiDung result = authService.login("admin@nhatro.com", "123456");

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("admin@nhatro.com");
        assertThat(result.getVaiTro()).isEqualTo(3);
    }

    @Test
    void loginWithInvalidPasswordThrowsException() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        NguoiDungService nguoiDungService = new NguoiDungService(passwordEncoder);
        JwtUtil jwtUtil = new JwtUtil();
        AuthService authService = new AuthService(nguoiDungService, jwtUtil, passwordEncoder);

        assertThatThrownBy(() -> authService.login("admin@nhatro.com", "wrong-password"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("không hợp lệ");
    }
}
