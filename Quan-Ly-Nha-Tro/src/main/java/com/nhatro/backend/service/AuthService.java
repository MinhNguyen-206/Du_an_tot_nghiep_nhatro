package com.nhatro.backend.service;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nhatro.backend.dto.AuthResponse;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.VaiTro;
import com.nhatro.backend.repository.VaiTroRepository;
import com.nhatro.backend.security.JwtUtil;

import io.jsonwebtoken.JwtException;

@Service
public class AuthService {

    private final NguoiDungService nguoiDungService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final VaiTroRepository vaiTroRepository;

    @Value("${app.reset-password.base-url}")
    private String resetPasswordBaseUrl;

    public AuthService(NguoiDungService nguoiDungService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder,
            MailService mailService, VaiTroRepository vaiTroRepository) {
        Objects.requireNonNull(nguoiDungService, "nguoiDungService must not be null");
        Objects.requireNonNull(jwtUtil, "jwtUtil must not be null");
        Objects.requireNonNull(passwordEncoder, "passwordEncoder must not be null");
        Objects.requireNonNull(mailService, "mailService must not be null");
        Objects.requireNonNull(vaiTroRepository, "vaiTroRepository must not be null");
        this.nguoiDungService = nguoiDungService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.vaiTroRepository = vaiTroRepository;
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

    // Sinh token khoi phuc mat khau (han 15 phut) va gui email chua link dat lai
    // mat khau. Neu email khong ton tai trong he thong thi ÂM THẦM bo qua
    // (khong nem loi) de tranh lo thong tin "email nao da dang ky" cho ke xau do.
    public void forgotPassword(String email) {
        Objects.requireNonNull(email, "email must not be null");
        nguoiDungService.getByEmail(email).ifPresent(nguoiDung -> {
            String token = jwtUtil.generateResetToken(nguoiDung.getEmail());
            String link = resetPasswordBaseUrl + "/reset-password?token=" + token;
            mailService.sendResetPasswordEmail(nguoiDung.getEmail(), link);
        });
    }

    // Kiem tra token hop le (dung muc dich, con han) roi ghi de mat khau moi.
    public void resetPassword(String token, String matKhauMoi) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Thiếu token khôi phục mật khẩu");
        }
        if (matKhauMoi == null || matKhauMoi.length() < 6) {
            throw new IllegalArgumentException("Mật khẩu mới phải có ít nhất 6 ký tự");
        }

        String email;
        try {
            email = jwtUtil.getResetEmailFromToken(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Liên kết khôi phục mật khẩu không hợp lệ hoặc đã hết hạn");
        }

        NguoiDung nguoiDung = nguoiDungService.getByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Tài khoản không tồn tại"));

        nguoiDungService.updatePassword(nguoiDung.getMaNguoiDung(), matKhauMoi);
    }
        // Tao tai khoan cho user dang nhap Google LAN DAU: tao NguoiDung moi voi
    // mat khau ngau nhien (an, khong tra ve cho user). Khong con man "chon vai
    // tro" nua - MOI tai khoan dang ky moi (kem ca qua Google) deu mac dinh la
    // "Người thuê" (khach hang). Muon thanh Chu tro phai gui yeu cau rieng de
    // Admin duyet (xem YeuCauChuTroController), khong duoc chon thang khi dang ky.
    public AuthResponse registerFromGoogle(String email, String hoTen, String avatar) {
        Objects.requireNonNull(email, "email must not be null");

        if (nguoiDungService.getByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email đã được đăng ký");
        }

        VaiTro vaiTro = vaiTroRepository.findByTenVaiTro("Người thuê")
                .or(() -> vaiTroRepository.findById(3))
                .orElseThrow(() -> new IllegalStateException(
                        "Khong tim thay vai tro mac dinh 'Người thuê' trong bang VAI_TRO"));

        String matKhauNgauNhien = UUID.randomUUID().toString();

        NguoiDung nguoiDung = NguoiDung.builder()
                .email(email)
                .hoTen(hoTen != null ? hoTen : email)
                .avatar(avatar)
                .matKhau(matKhauNgauNhien) // duoc ma hoa ben trong nguoiDungService.create()
                .vaiTro(vaiTro)
                .trangThai(true)
                .build();

        NguoiDung daLuu = nguoiDungService.create(nguoiDung);
        return buildAuthResponse(daLuu);
    }
}
