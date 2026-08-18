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
}