package com.nhatro.backend.security;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    private static final String ADMIN = "ROLE_ADMIN";
    private static final String CHU_TRO = "ROLE_CHU_TRO";
    private static final String NGUOI_THUE = "ROLE_NGUOI_THUE";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ===================== CORS =====================
                .cors(Customizer.withDefaults())

                // ===================== CSRF =====================
                .csrf(csrf -> csrf.disable())

                // ===================== SESSION =====================
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // ===================== AUTHORIZATION =====================
                .authorizeHttpRequests(auth -> auth

                        // OPTIONS / CORS preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()

                        // ===================== STATIC =====================
                        .requestMatchers(
                                "/resources/**",
                                "/static/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/img/**",
                                "/favicon.ico",
                                "/WEB-INF/**"
                        )
                        .permitAll()

                        // ===================== PUBLIC API =====================
                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        )
                        .permitAll()

                        // ===================== PUBLIC PAGE =====================
                        .requestMatchers(
                                "/",
                                "/home",
                                "/login",
                                "/register",
                                "/forgot-password",
                                "/reset-password",
                                "/logout",
                                "/gioi-thieu",
                                "/lien-he",
                                "/thue-tro",
                                "/thue-can-ho",
                                "/chi-tiet-phong",
                                "/chi-tiet-phong/**",
                                "/rooms",
                                "/rooms/**",
                                "/profile",
                                "/error"
                        )
                        .permitAll()

                        // ===================== ĐĂNG KÝ CHỦ TRỌ =====================
                        .requestMatchers("/dang-ky-chu-tro")
                        .authenticated()

                        // ===================== ADMIN PAGE =====================
                        .requestMatchers("/admin/**")
                        .permitAll()

                        // ===================== CHỦ TRỌ PAGE =====================
                        .requestMatchers("/chu-tro/**")
                        .hasAnyAuthority(
                                CHU_TRO,
                                ADMIN,
                                "CHU_TRO",
                                "ADMIN"
                        )

                        // ===================== USER =====================
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/nguoi-dung"
                        )
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/lien-he"
                        )
                        .permitAll()

                        // ===================== ADMIN API =====================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/admin/dashboard"
                        )
                        .hasAnyAuthority(
                                ADMIN,
                                "ADMIN"
                        )

                        .requestMatchers(
                                "/api/admin/management/**"
                        )
                        .hasAnyAuthority(
                                ADMIN,
                                "ADMIN"
                        )

                        // ===================== PUBLIC GET API =====================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/phong-tro/**",
                                "/api/dang-tin/**",
                                "/api/nha-tro/**",
                                "/api/danh-gia/**",
                                "/api/goi-dich-vu/**",
                                "/api/hinh-anh/**"
                        )
                        .permitAll()

                        // ===================== ADMIN ONLY =====================
                        .requestMatchers(
                                "/api/phan-quyen/**",
                                "/api/vai-tro/**",
                                "/api/cau-hinh-danh-muc/**",
                                "/api/chi-tiet-danh-muc/**",
                                "/api/nhat-ky-hoat-dong/**",
                                "/api/bo-dieu-khien-ai/**",
                                "/api/bao-cao/**",
                                "/api/xac-thuc-ekyc/**"
                        )
                        .hasAuthority(ADMIN)

                        // ===================== YÊU CẦU CHỦ TRỌ =====================

                        // Duyệt / từ chối -> ADMIN
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/yeu-cau-chu-tro/*/duyet",
                                "/api/yeu-cau-chu-tro/*/tu-choi"
                        )
                        .hasAnyAuthority(
                                ADMIN,
                                "ADMIN"
                        )

                        // Danh sách tất cả -> ADMIN
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/yeu-cau-chu-tro"
                        )
                        .hasAnyAuthority(
                                ADMIN,
                                "ADMIN"
                        )

                        // Các API yêu cầu chủ trọ còn lại
                        .requestMatchers(
                                "/api/yeu-cau-chu-tro/**"
                        )
                        .authenticated()

                        // ===================== PROFILE =====================
                        .requestMatchers(
                                "/api/profile/**",
                                "/api/phong-yeu-thich/**",
                                "/api/lich-su-xem-phong/**"
                        )
                        .authenticated()

                        // ===================== USER UPDATE =====================
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/nguoi-dung/**"
                        )
                        .authenticated()

                        // ===================== USER DELETE =====================
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/nguoi-dung/**"
                        )
                        .hasAuthority(ADMIN)

                        // ===================== USER LIST =====================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/nguoi-dung"
                        )
                        .hasAuthority(ADMIN)

                        // ===================== CHỦ TRỌ CRUD =====================
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/nha-tro/**",
                                "/api/phong-tro/**",
                                "/api/dang-tin/**"
                        )
                        .hasAnyAuthority(
                                CHU_TRO,
                                ADMIN
                        )

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/nha-tro/**",
                                "/api/phong-tro/**",
                                "/api/dang-tin/**"
                        )
                        .hasAnyAuthority(
                                CHU_TRO,
                                ADMIN
                        )

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/nha-tro/**",
                                "/api/phong-tro/**",
                                "/api/dang-tin/**"
                        )
                        .hasAnyAuthority(
                                CHU_TRO,
                                ADMIN
                        )

                        // ===================== CHỦ TRỌ SERVICES =====================
                        .requestMatchers(
                                "/api/chi-so-dien-nuoc/**",
                                "/api/hoa-don-thang/**",
                                "/api/dang-ky-goi-chu-tro/**",
                                "/api/hop-dong-premium/**",
                                "/api/hoa-don-premium/**",
                                "/api/gia-han-hop-dong/**"
                        )
                        .hasAnyAuthority(
                                CHU_TRO,
                                ADMIN
                        )

                        // ===================== THUÊ PHÒNG =====================
                        .requestMatchers(
                                "/api/yeu-cau-thue/**",
                                "/api/lich-hen/**"
                        )
                        .hasAnyAuthority(
                                NGUOI_THUE,
                                CHU_TRO,
                                ADMIN
                        )

                        // ===================== USER GET =====================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/nguoi-dung/**"
                        )
                        .authenticated()

                        // ===================== EVERYTHING ELSE =====================
                        .anyRequest()
                        .authenticated()
                )

                // =========================================================
                // EXCEPTION HANDLING
                // =========================================================
                .exceptionHandling(exception -> exception

                        // ===================== 401 =====================
                        .authenticationEntryPoint((request, response, authException) -> {

                            String uri = request.getRequestURI();

                            if (uri.startsWith(
                                    request.getContextPath() + "/api/"
                            )) {

                                response.sendError(
                                        HttpServletResponse.SC_UNAUTHORIZED,
                                        "Chua dang nhap"
                                );

                            } else {

                                String redirect = URLEncoder.encode(
                                        uri,
                                        StandardCharsets.UTF_8
                                );

                                response.sendRedirect(
                                        request.getContextPath()
                                                + "/login?redirect="
                                                + redirect
                                );
                            }
                        })

                        // ===================== 403 =====================
                        .accessDeniedHandler(
                                (request, response, accessDeniedException) -> {

                                    String uri = request.getRequestURI();

                                    if (uri.startsWith(
                                            request.getContextPath() + "/api/"
                                    )) {

                                        response.sendError(
                                                HttpServletResponse.SC_FORBIDDEN,
                                                "Khong co quyen truy cap"
                                        );

                                    } else {

                                        response.sendRedirect(
                                                request.getContextPath()
                                                        + "/login?error=forbidden"
                                        );
                                    }
                                }
                        )
                )

                // =========================================================
                // JWT FILTER
                // =========================================================
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
