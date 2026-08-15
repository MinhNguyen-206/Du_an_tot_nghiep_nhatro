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
// bang JwtUtil, roi nap thong tin dang nhap vao SecurityContext.
// vaiTro: 1 = Admin, 2 = Chu tro, 3 = Nguoi thue (theo thu tu INSERT VAI_TRO trong schema SQL)
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

                // maVaiTro: 1 = Admin, 2 = Chu tro, 3 = Nguoi thue
                String roleName = "ROLE_" + switch (role != null ? role : 0) {
                    case 1 -> "ADMIN";
                    case 2 -> "CHU_TRO";
                    case 3 -> "NGUOI_THUE";
                    default -> "UNKNOWN";
                };

                var authentication = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        List.of(new SimpleGrantedAuthority(roleName)));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}