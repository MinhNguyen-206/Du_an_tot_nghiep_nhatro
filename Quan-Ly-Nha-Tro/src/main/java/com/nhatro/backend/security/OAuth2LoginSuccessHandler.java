package com.nhatro.backend.security;

import com.nhatro.backend.dto.AuthResponse;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.service.AuthService;
import com.nhatro.backend.service.NguoiDungService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

// Xu ly sau khi Google xac thuc THANH CONG (chua chac da co tai khoan trong
// DB cua minh). Neu email da ton tai -> dang nhap luon, phat JWT. Neu chua
// -> luu tam thong tin vao session, chuyen sang trang "chon vai tro".
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final NguoiDungService nguoiDungService;
    private final AuthService authService;

    public OAuth2LoginSuccessHandler(NguoiDungService nguoiDungService, AuthService authService) {
        this.nguoiDungService = nguoiDungService;
        this.authService = authService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String hoTen = oAuth2User.getAttribute("name");
        String avatar = oAuth2User.getAttribute("picture");

        if (email == null || email.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/login?error=google_no_email");
            return;
        }

        Optional<NguoiDung> existing = nguoiDungService.getByEmail(email);

        if (existing.isPresent()) {
            NguoiDung nguoiDung = existing.get();
            if (nguoiDung.getTrangThai() == null || !nguoiDung.getTrangThai()) {
                response.sendRedirect(request.getContextPath() + "/login?error=locked");
                return;
            }
            AuthResponse authResponse = authService.buildAuthResponse(nguoiDung);
            redirectWithToken(request, response, authResponse.getToken());
            return;
        }

        // Chua co tai khoan -> luu tam vao session, chuyen sang trang chon vai tro
        request.getSession().setAttribute("google_email", email);
        request.getSession().setAttribute("google_hoTen", hoTen != null ? hoTen : email);
        request.getSession().setAttribute("google_avatar", avatar);

        response.sendRedirect(request.getContextPath() + "/chon-vai-tro");
    }

    // Redirect ve trang trung gian oauth2-redirect.jsp kem token tren query
    // string - vi server-side redirect khong the ghi thang vao localStorage
    // cua trinh duyet, phai nho 1 trang JS nho lam viec do.
    private void redirectWithToken(HttpServletRequest request, HttpServletResponse response, String token)
            throws IOException {
        String url = UriComponentsBuilder.fromPath(request.getContextPath() + "/oauth2-redirect")
                .queryParam("token", token)
                .build()
                .toUriString();
        response.sendRedirect(url);
    }
}