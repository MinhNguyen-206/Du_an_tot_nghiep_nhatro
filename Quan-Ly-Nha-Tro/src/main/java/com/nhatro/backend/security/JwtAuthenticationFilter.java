package com.nhatro.backend.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.service.NguoiDungService;

// Doc header "Authorization: Bearer <token>" tren moi request, kiem tra hop le
// bang JwtUtil, roi nap thong tin dang nhap vao SecurityContext.
// vaiTro: 1 = Admin, 2 = Chu tro, 3 = Nguoi thue (theo thu tu INSERT VAI_TRO trong schema SQL)
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final NguoiDungService nguoiDungService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, NguoiDungService nguoiDungService) {
        this.jwtUtil = jwtUtil;
        this.nguoiDungService = nguoiDungService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                     @NonNull HttpServletResponse response,
                                     @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (jwtUtil.isTokenValid(token)) {
                String email = jwtUtil.getEmailFromToken(token);
                String roleName = resolveRoleName(email, jwtUtil.getRoleFromToken(token));

                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(roleName));

                String legacyAuthority = roleName.startsWith("ROLE_") ? roleName.substring(5) : roleName;
                if (!legacyAuthority.equals(roleName)) {
                    authorities.add(new SimpleGrantedAuthority(legacyAuthority));
                }

                var authentication = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveRoleName(String email, Integer roleId) {
        String roleName = switch (roleId != null ? roleId : 0) {
            case 1 -> "ADMIN";
            case 2 -> "CHU_TRO";
            case 3 -> "NGUOI_THUE";
            default -> "";
        };

        if (!roleName.isBlank()) {
            return "ROLE_" + roleName;
        }

        return nguoiDungService.getByEmail(email)
                .map(NguoiDung::getVaiTro)
                .map(vaiTro -> vaiTro.getTenVaiTro() == null ? "" : vaiTro.getTenVaiTro().toUpperCase())
                .map(this::normalizeRoleName)
                .filter(normalized -> !normalized.isBlank())
                .map(normalized -> "ROLE_" + normalized)
                .orElse("ROLE_UNKNOWN");
    }

    private String normalizeRoleName(String roleName) {
        if (roleName.contains("ADMIN") || roleName.contains("QUAN TRI")) {
            return "ADMIN";
        }
        if (roleName.contains("CHU TRO")) {
            return "CHU_TRO";
        }
        if (roleName.contains("NGUOI THUE")) {
            return "NGUOI_THUE";
        }
        return roleName.replace(' ', '_');
    }
}