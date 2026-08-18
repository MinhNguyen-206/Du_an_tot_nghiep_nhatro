package com.nhatro.backend.service;

import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.VaiTro;
import com.nhatro.backend.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    // NOTE: AuthService + NguoiDungService gio dung JPA (NguoiDungRepository).
    // Unit tests nay can duoc viet lai voi Mockito de mock repository.
    // Hien tai skip de khong block compilation.
    // NOTE: Unit tests can be rewritten with @ExtendWith(MockitoExtension.class)

    @Test
    void loginWithInvalidPasswordThrowsException() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        JwtUtil jwtUtil = new JwtUtil();

        // Mock NguoiDungService
        NguoiDungService nguoiDungService = mock(NguoiDungService.class);
        MailService mailService = mock(MailService.class);
        AuthService authService = new AuthService(nguoiDungService, jwtUtil, passwordEncoder, mailService);

        // Tao NguoiDung mau co mat khau ma hoa sai
        VaiTro vaiTro = VaiTro.builder().maVaiTro(3).tenVaiTro("Người thuê").trangThai(true).build();
        NguoiDung nguoiDung = NguoiDung.builder()
                .maNguoiDung(1)
                .email("test@nhatro.com")
                .matKhau(passwordEncoder.encode("correct-password"))
                .trangThai(true)
                .vaiTro(vaiTro)
                .build();
        when(nguoiDungService.getByEmail("test@nhatro.com")).thenReturn(Optional.of(nguoiDung));

        assertThatThrownBy(() -> authService.login("test@nhatro.com", "wrong-password"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("không hợp lệ");
    }
}
