package com.nhatro.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// Doc header "Authorization: Bearer <token>" tren moi request, kiem tra hop le
// bang JwtUtil, roi nap thong tin dang nhap vao SecurityContext de
// anyRequest().authenticated() trong SecurityConfig nhan dien duoc.
// Neu khong co token hoac token khong hop le -> bo qua, de request di tiep
// nhu "chua dang nhap" (cac rule permitAll van hoat dong binh thuong,
// cac rule authenticated() se bi tu choi o buoc sau boi Spring Security).
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
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
                Integer role = jwtUtil.getRoleFromToken(token);

                // vaiTro: 1 = nguoi thue, 2 = chu tro, 3 = quan tri vien
                String roleName = "ROLE_" + switch (role != null ? role : 0) {
                    case 1 -> "NGUOI_THUE";
                    case 2 -> "CHU_TRO";
                    case 3 -> "ADMIN";
                    default -> "UNKNOWN";
                };

                var authentication = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        List.of(new SimpleGrantedAuthority(roleName)));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            // token khong hop le (het han/sai chu ky) -> khong set Authentication,
            // request coi nhu chua dang nhap, khong throw loi o day.
        }

        filterChain.doFilter(request, response);
    }
}