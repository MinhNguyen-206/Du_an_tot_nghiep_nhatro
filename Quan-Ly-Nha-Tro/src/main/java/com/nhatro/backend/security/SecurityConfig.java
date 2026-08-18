package com.nhatro.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

<<<<<<< HEAD
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
    // ROLE
    // =====================================================

    private static final String ADMIN = "ROLE_ADMIN";
    private static final String CHU_TRO = "ROLE_CHU_TRO";
    private static final String NGUOI_THUE = "ROLE_NGUOI_THUE";

    // =====================================================
    // SECURITY FILTER CHAIN
    // =====================================================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

                // =================================================
                // CORS
                // =================================================

                .cors(Customizer.withDefaults())

                // =================================================
                // JWT -> TẮT CSRF
                // =================================================

                .csrf(csrf -> csrf.disable())

                // =================================================
                // PHÂN QUYỀN REQUEST
                // =================================================

                .authorizeHttpRequests(auth -> auth

                        // =================================================
                        // 1. OPTIONS
                        // =================================================

                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        ).permitAll()

                        // =================================================
                        // 2. API AUTH + SWAGGER
                        // =================================================

                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // =================================================
                        // 3. TRANG JSP CÔNG KHAI
                        // =================================================

                        .requestMatchers(
                                HttpMethod.GET,

                                // -----------------------------
                                // Trang chủ
                                // -----------------------------

                                "/",
                                "/home",

                                // -----------------------------
                                // Login / Register
                                // -----------------------------

                                "/login",
                                "/register",
                                "/forgot-password",
                                "/reset-password",

                                // -----------------------------
                                // Danh mục
                                // -----------------------------

                                "/thue-tro",
                                "/thue-can-ho",

                                // -----------------------------
                                // Chi tiết phòng
                                // -----------------------------

                                "/chi-tiet-phong",
                                "/chi-tiet-phong/**",

                                // -----------------------------
                                // Danh sách phòng
                                // -----------------------------

                                "/rooms",
                                "/rooms/**",

                                // -----------------------------
                                // Static
                                // -----------------------------

                                "/resources/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/img/**",
                                "/static/**",

                                // -----------------------------
                                // JSP
                                // -----------------------------

                                "/WEB-INF/**"

                        ).permitAll()

                        // =================================================
                        // 4. API ĐĂNG KÝ
                        // =================================================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/nguoi-dung"
                        ).permitAll()

                        // =================================================
                        // 5. API XEM DỮ LIỆU CÔNG KHAI
                        // =================================================

                        .requestMatchers(
                                HttpMethod.GET,

                                "/api/phong-tro/**",
                                "/api/dang-tin/**",
                                "/api/nha-tro/**",
                                "/api/danh-gia/**",
                                "/api/goi-dich-vu/**",
                                "/api/hinh-anh/**"

                        ).permitAll()

                        // =================================================
                        // 6. CHỈ ADMIN
                        // =================================================

                        .requestMatchers(
                                "/api/phan-quyen/**",
                                "/api/vai-tro/**",
                                "/api/cau-hinh-danh-muc/**",
                                "/api/chi-tiet-danh-muc/**",
                                "/api/nhat-ky-hoat-dong/**",
                                "/api/bo-dieu-khien-ai/**",
                                "/api/bao-cao/**",
                                "/api/xac-thuc-ekyc/**"

                        ).hasAuthority(ADMIN)

                        // =================================================
                        // ADMIN SỬA NGƯỜI DÙNG
                        // =================================================

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/nguoi-dung/**"

                        ).hasAuthority(ADMIN)

                        // =================================================
                        // ADMIN XÓA NGƯỜI DÙNG
                        // =================================================

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/nguoi-dung/**"

                        ).hasAuthority(ADMIN)

                        // =================================================
                        // ADMIN XEM DANH SÁCH NGƯỜI DÙNG
                        // =================================================

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/nguoi-dung"

                        ).hasAuthority(ADMIN)

                        // =================================================
                        // 7. CHỦ TRỌ + ADMIN
                        // =================================================

                        .requestMatchers(
                                HttpMethod.POST,

                                "/api/nha-tro/**",
                                "/api/phong-tro/**",
                                "/api/dang-tin/**"

                        ).hasAnyAuthority(
                                CHU_TRO,
                                ADMIN
                        )

                        // =================================================
                        // SỬA
                        // =================================================

                        .requestMatchers(
                                HttpMethod.PUT,

                                "/api/nha-tro/**",
                                "/api/phong-tro/**",
                                "/api/dang-tin/**"

                        ).hasAnyAuthority(
                                CHU_TRO,
                                ADMIN
                        )

                        // =================================================
                        // XÓA
                        // =================================================

                        .requestMatchers(
                                HttpMethod.DELETE,

                                "/api/nha-tro/**",
                                "/api/phong-tro/**",
                                "/api/dang-tin/**"

                        ).hasAnyAuthority(
                                CHU_TRO,
                                ADMIN
                        )

                        // =================================================
                        // 8. CHỨC NĂNG CHỦ TRỌ
                        // =================================================

                        .requestMatchers(
                                "/api/chi-so-dien-nuoc/**",
                                "/api/hoa-don-thang/**",
                                "/api/dang-ky-goi-chu-tro/**",
                                "/api/hop-dong-premium/**",
                                "/api/hoa-don-premium/**",
                                "/api/gia-han-hop-dong/**"

                        ).hasAnyAuthority(
                                CHU_TRO,
                                ADMIN
                        )

                        // =================================================
                        // 9. NGƯỜI THUÊ + CHỦ TRỌ + ADMIN
                        // =================================================

                        .requestMatchers(
                                "/api/yeu-cau-thue/**",
                                "/api/lich-hen/**"

                        ).hasAnyAuthority(
                                NGUOI_THUE,
                                CHU_TRO,
                                ADMIN
                        )

                        // =================================================
                        // 10. THÔNG TIN NGƯỜI DÙNG
                        // =================================================

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/nguoi-dung/**"

                        ).authenticated()

                        // =================================================
                        // 11. CÒN LẠI
                        // =================================================

                        .anyRequest().authenticated()
                )

                // =====================================================
                // JWT FILTER
                // =====================================================

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
=======
        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // maVaiTro: 1=Admin, 2=ChuTro, 3=NguoiThue (khop voi INSERT VAI_TRO trong
        // schema SQL)
        private static final String ADMIN = "ROLE_ADMIN";
        private static final String CHU_TRO = "ROLE_CHU_TRO";
        private static final String NGUOI_THUE = "ROLE_NGUOI_THUE";

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .cors(Customizer.withDefaults())
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth

                                                // ===================== 1) CONG KHAI (khong can dang nhap)
                                                // =====================
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                .requestMatchers(
                                                                "/api/auth/**",
                                                                "/swagger-ui/**",
                                                                "/v3/api-docs/**")
                                                .permitAll()
                                                // ===================== CAC TRANG JSP (giao dien web,
                                                // khong can dang nhap de xem) =====================
                                                .requestMatchers(HttpMethod.GET,
                                                                "/",
                                                                "/login",
                                                                "/register",
                                                                "/forgot-password",
                                                                "/reset-password",
                                                                "/rooms",
                                                                "/rooms/**",
                                                                "/resources/**",
                                                                // Spring MVC forward noi bo toi day de render JSP
                                                                // (vi du: return "home/home" -> forward toi
                                                                // /WEB-INF/jsp/home/home.jsp). Spring Security kiem
                                                                // tra lai tu dau khi forward, nen phai permitAll
                                                                // luon duong dan JSP thuc te nay.
                                                                "/WEB-INF/**")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/nguoi-dung").permitAll()
                                                .requestMatchers(HttpMethod.GET,
                                                                "/api/phong-tro/**",
                                                                "/api/dang-tin/**",
                                                                "/api/nha-tro/**",
                                                                "/api/danh-gia/**",
                                                                "/api/goi-dich-vu/**",
                                                                "/api/hinh-anh/**")
                                                .permitAll()

                                                // ===================== 2) CHI ADMIN =====================
                                                .requestMatchers(
                                                                "/api/phan-quyen/**",
                                                                "/api/vai-tro/**",
                                                                "/api/cau-hinh-danh-muc/**",
                                                                "/api/chi-tiet-danh-muc/**",
                                                                "/api/nhat-ky-hoat-dong/**",
                                                                "/api/bo-dieu-khien-ai/**",
                                                                "/api/bao-cao/**",
                                                                "/api/xac-thuc-ekyc/**")
                                                .hasAuthority(ADMIN)
                                                .requestMatchers(HttpMethod.PUT, "/api/nguoi-dung/**")
                                                .hasAuthority(ADMIN)
                                                .requestMatchers(HttpMethod.DELETE, "/api/nguoi-dung/**")
                                                .hasAuthority(ADMIN)
                                                .requestMatchers(HttpMethod.GET, "/api/nguoi-dung").hasAuthority(ADMIN)

                                                // ===================== 3) CHU TRO (+ ADMIN) =====================
                                                .requestMatchers(HttpMethod.POST, "/api/nha-tro/**",
                                                                "/api/phong-tro/**", "/api/dang-tin/**")
                                                .hasAnyAuthority(CHU_TRO, ADMIN)
                                                .requestMatchers(HttpMethod.PUT, "/api/nha-tro/**", "/api/phong-tro/**",
                                                                "/api/dang-tin/**")
                                                .hasAnyAuthority(CHU_TRO, ADMIN)
                                                .requestMatchers(HttpMethod.DELETE, "/api/nha-tro/**",
                                                                "/api/phong-tro/**", "/api/dang-tin/**")
                                                .hasAnyAuthority(CHU_TRO, ADMIN)
                                                .requestMatchers(
                                                                "/api/chi-so-dien-nuoc/**",
                                                                "/api/hoa-don-thang/**",
                                                                "/api/dang-ky-goi-chu-tro/**",
                                                                "/api/hop-dong-premium/**",
                                                                "/api/hoa-don-premium/**",
                                                                "/api/gia-han-hop-dong/**")
                                                .hasAnyAuthority(CHU_TRO, ADMIN)

                                                // ===================== 4) NGUOI THUE (+ ADMIN) =====================
                                                .requestMatchers(
                                                                "/api/yeu-cau-thue/**",
                                                                "/api/lich-hen/**")
                                                .hasAnyAuthority(NGUOI_THUE, CHU_TRO, ADMIN)

                                                // ===================== 5) DUNG CHUNG (da dang nhap)
                                                // =====================
                                                .requestMatchers(HttpMethod.GET, "/api/nguoi-dung/**").authenticated()

                                                // Tat ca nhung gi con lai: bat buoc phai dang nhap
                                                .anyRequest().authenticated())
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
>>>>>>> 1d4d66bcf64b749d3a09fd66a4d5bf639a706a85
}