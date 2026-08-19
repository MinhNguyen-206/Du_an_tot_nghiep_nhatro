package com.nhatro.backend.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhatro.backend.dto.AuthResponse;
import com.nhatro.backend.dto.CompleteGoogleRegistrationRequest;
import com.nhatro.backend.dto.ForgotPasswordRequest;
import com.nhatro.backend.dto.LoginRequest;
import com.nhatro.backend.dto.ResetPasswordRequest;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

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
     @GetMapping("/google/pending-info")
    public ResponseEntity<?> pendingGoogleInfo(HttpSession session) {
        String email = (String) session.getAttribute("google_email");
        if (email == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Không có phiên đăng ký Google nào đang chờ."));
        }
        Object avatar = session.getAttribute("google_avatar");
        return ResponseEntity.ok(Map.of(
                "email", email,
                "hoTen", session.getAttribute("google_hoTen"),
                "avatar", avatar == null ? "" : avatar));
    }

    @PostMapping("/google/complete-registration")
    public ResponseEntity<?> completeGoogleRegistration(HttpSession session,
            @RequestBody CompleteGoogleRegistrationRequest request) {
        String email = (String) session.getAttribute("google_email");
        String hoTen = (String) session.getAttribute("google_hoTen");
        String avatar = (String) session.getAttribute("google_avatar");

        if (email == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Phiên đăng nhập Google đã hết hạn, vui lòng thử lại."));
        }

        try {
            AuthResponse authResponse = authService.completeGoogleRegistration(email, hoTen, avatar,
                    request.getMaVaiTro());
            session.removeAttribute("google_email");
            session.removeAttribute("google_hoTen");
            session.removeAttribute("google_avatar");
            return ResponseEntity.ok(authResponse);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }
}
