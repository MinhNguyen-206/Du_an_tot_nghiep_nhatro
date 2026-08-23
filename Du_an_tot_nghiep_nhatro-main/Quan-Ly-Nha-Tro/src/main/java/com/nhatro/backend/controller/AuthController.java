package com.nhatro.backend.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhatro.backend.dto.AuthResponse;
import com.nhatro.backend.dto.ForgotPasswordRequest;
import com.nhatro.backend.dto.LoginRequest;
import com.nhatro.backend.dto.ResetPasswordRequest;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Xác thực & Đăng nhập", description = "Quản lý đăng nhập, đăng xuất, quản lý token")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            NguoiDung user = authService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(authService.buildAuthResponse(user));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Gui email chua link dat lai mat khau. LUON tra ve 200 (khong tiet lo
    // email co ton tai trong he thong hay khong), tranh bi loi dung de do
    // xem email nao da dang ky tai khoan.
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok(Map.of(
                "message", "Nếu email tồn tại trong hệ thống, một liên kết khôi phục mật khẩu đã được gửi."));
    }

    // Dat lai mat khau bang token nhan duoc tu email (hoac tu console neu
    // chua cau hinh SMTP that - xem MailService).
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            authService.resetPassword(request.getToken(), request.getMatKhauMoi());
            return ResponseEntity.ok(Map.of("message", "Đặt lại mật khẩu thành công."));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }
    // Da bo man "chon vai tro" khi dang ky Google (xem OAuth2LoginSuccessHandler):
    // tai khoan Google moi gio tu dong tao voi vai tro "Người thuê" va dang nhap
    // thang, nen khong con can 2 endpoint pending-info / complete-registration nua.
}
