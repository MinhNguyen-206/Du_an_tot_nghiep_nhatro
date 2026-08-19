package com.nhatro.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        // =====================================================
        // PASSWORD ENCODER
        // =====================================================

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // =====================================================
        // ROLES CONSTANTS (Không cần tiền tố ROLE_ khi dùng hasRole)
        // =====================================================

        private static final String ADMIN = "ADMIN";
        private static final String CHU_TRO = "CHU_TRO";
        private static final String NGUOI_THUE = "NGUOI_THUE";

        // =====================================================
        // SECURITY FILTER CHAIN
        // =====================================================

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                // 1. CORS & CSRF & SESSION STATELESS
                                .cors(Customizer.withDefaults())
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                // 2. PHÂN QUYỀN REQUEST
                                .authorizeHttpRequests(auth -> auth

                                                // Cross-Origin Preflight Request
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                                                // Static resources & WEB-INF
                                                .requestMatchers(
                                                                "/resources/**",
                                                                "/static/**",
                                                                "/css/**",
                                                                "/js/**",
                                                                "/images/**",
                                                                "/img/**",
                                                                "/favicon.ico",
                                                                "/WEB-INF/**")
                                                .permitAll()

                                                // Auth API & Swagger Documentation
                                                .requestMatchers(
                                                                "/api/auth/**",
                                                                "/swagger-ui/**",
                                                                "/swagger-ui.html",
                                                                "/v3/api-docs/**")
                                                .permitAll()

                                                // Các trang View JSP công khai
                                                .requestMatchers(
                                                                "/",
                                                                "/home",
                                                                "/login",
                                                                "/register",
                                                                "/forgot-password",
                                                                "/reset-password",
                                                                "/logout",
                                                                "/thue-tro",
                                                                "/thue-can-ho",
                                                                "/chi-tiet-phong",
                                                                "/chi-tiet-phong/**",
                                                                "/rooms",
                                                                "/rooms/**")
                                                .permitAll()

                                                // Admin Dashboard (view JSP): cho phép render sau khi login,
                                                // vì trình duyệt không tự gửi Bearer token khi navigate bằng window.location.
                                                // Quy tắc role được xử lý ở frontend bằng token/localStorage.
                                                .requestMatchers("/admin/**")
                                                .permitAll()

                                                // API Đăng ký tài khoản
                                                .requestMatchers(HttpMethod.POST, "/api/nguoi-dung").permitAll()

                                                // API xem dữ liệu công khai (GET)
                                                .requestMatchers(HttpMethod.GET,
                                                                "/api/phong-tro/**",
                                                                "/api/dang-tin/**",
                                                                "/api/nha-tro/**",
                                                                "/api/danh-gia/**",
                                                                "/api/goi-dich-vu/**",
                                                                "/api/hinh-anh/**")
                                                .permitAll()

                                                // Quản trị viên (ADMIN)
                                                .requestMatchers(
                                                                "/api/phan-quyen/**",
                                                                "/api/vai-tro/**",
                                                                "/api/cau-hinh-danh-muc/**",
                                                                "/api/chi-tiet-danh-muc/**",
                                                                "/api/nhat-ky-hoat-dong/**",
                                                                "/api/bo-dieu-khien-ai/**",
                                                                "/api/bao-cao/**",
                                                                "/api/xac-thuc-ekyc/**")
                                                .hasRole(ADMIN)
                                                .requestMatchers(HttpMethod.PUT, "/api/nguoi-dung/**").hasRole(ADMIN)
                                                .requestMatchers(HttpMethod.DELETE, "/api/nguoi-dung/**").hasRole(ADMIN)
                                                .requestMatchers(HttpMethod.GET, "/api/nguoi-dung").hasRole(ADMIN)

                                                // Chủ trọ & Admin (Tạo / Sửa / Xóa Bài đăng, Nhà trọ, Phòng trọ)
                                                .requestMatchers(HttpMethod.POST,
                                                                "/api/nha-tro/**",
                                                                "/api/phong-tro/**",
                                                                "/api/dang-tin/**")
                                                .hasAnyRole(CHU_TRO, ADMIN)
                                                .requestMatchers(HttpMethod.PUT,
                                                                "/api/nha-tro/**",
                                                                "/api/phong-tro/**",
                                                                "/api/dang-tin/**")
                                                .hasAnyRole(CHU_TRO, ADMIN)
                                                .requestMatchers(HttpMethod.DELETE,
                                                                "/api/nha-tro/**",
                                                                "/api/phong-tro/**",
                                                                "/api/dang-tin/**")
                                                .hasAnyRole(CHU_TRO, ADMIN)

                                                // Chức năng quản lý nghiệp vụ Chủ trọ
                                                .requestMatchers(
                                                                "/api/chi-so-dien-nuoc/**",
                                                                "/api/hoa-don-thang/**",
                                                                "/api/dang-ky-goi-chu-tro/**",
                                                                "/api/hop-dong-premium/**",
                                                                "/api/hoa-don-premium/**",
                                                                "/api/gia-han-hop-dong/**")
                                                .hasAnyRole(CHU_TRO, ADMIN)

                                                // Yêu cầu thuê & Lịch hẹn
                                                .requestMatchers(
                                                                "/api/yeu-cau-thue/**",
                                                                "/api/lich-hen/**")
                                                .hasAnyRole(NGUOI_THUE, CHU_TRO, ADMIN)

                                                // Thông tin người dùng cá nhân
                                                .requestMatchers(HttpMethod.GET, "/api/nguoi-dung/**").authenticated()

                                                // Tất cả các request còn lại yêu cầu đăng nhập
                                                .anyRequest().authenticated())

                                // 3. JWT FILTER
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}