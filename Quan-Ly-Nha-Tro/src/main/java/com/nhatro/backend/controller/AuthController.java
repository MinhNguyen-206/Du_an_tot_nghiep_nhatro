package com.nhatro.backend.controller;

import com.nhatro.backend.dto.AuthResponse;
import com.nhatro.backend.dto.LoginRequest;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
