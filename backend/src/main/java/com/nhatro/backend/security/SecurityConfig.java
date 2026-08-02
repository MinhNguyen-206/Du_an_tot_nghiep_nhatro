package com.nhatro.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    // Cac hang so role, khop voi ROLE_* duoc gan trong JwtAuthenticationFilter
    // dua tren field vaiTro cua NguoiDung (1 = nguoi thue, 2 = chu tro, 3 = admin).
    private static final String ADMIN = "ROLE_ADMIN";
    private static final String CHU_TRO = "ROLE_CHU_TRO";
    private static final String NGUOI_THUE = "ROLE_NGUOI_THUE";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // ===================== 1) CONG KHAI (khong can dang nhap)
                        // =====================
                        // Dang nhap, dang ky tai khoan moi, tai lieu Swagger,
                        // va duyet phong/tin dang cong khai (khach chua dang nhap van xem duoc,
                        // khop voi FE: trang RoomList/RoomDetail khong yeu cau dang nhap).
                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/nguoi-dung").permitAll() // dang ky tai khoan
                        .requestMatchers(HttpMethod.GET,
                                "/api/phong-tro/**",
                                "/api/dang-tin/**",
                                "/api/nha-tro/**")
                        .permitAll()

                        // ===================== 2) CHI ADMIN =====================
                        // Quan tri he thong: phan quyen, tai khoan quan tri vien, cau hinh danh muc,
                        // nhat ky hoat dong, quan ly goi Premium (catalog), bo dieu khien AI,
                        // xu ly bao cao vi pham. Truoc day bat ky ai dang nhap (ke ca chu tro)
                        // cung goi duoc do chi kiem tra authenticated() ma khong kiem tra vai tro.
                        .requestMatchers(
                                "/api/phan-quyen/**",
                                "/api/quan-tri-vien/**",
                                "/api/cau-hinh-danh-muc/**",
                                "/api/nhat-ky-hoat-dong/**",
                                "/api/goi-premium/**",
                                "/api/bo-dieu-khien-ai/**",
                                "/api/bao-cao-vi-pham/**")
                        .hasAuthority(ADMIN)
                        // Sua/xoa tai khoan nguoi dung khac: chi admin (chua co kiem tra "tu sua
                        // ho so cua chinh minh" o muc method-level nen tam thoi khoa chat o day,
                        // tranh truong hop 1 user tu sua/xoa duoc tai khoan bat ky qua id tren URL).
                        .requestMatchers(HttpMethod.PUT, "/api/nguoi-dung/**").hasAuthority(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/nguoi-dung/**").hasAuthority(ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/nguoi-dung").hasAuthority(ADMIN) // xem toan bo danh sach

                        // ===================== 3) CHU TRO (+ ADMIN) =====================
                        // Nghiep vu thuoc pham vi chu tro: tao/sua/xoa nha tro, phong tro, dang tin,
                        // ghi chi so dien nuoc, xuat hoa don thang, goi dich vu, va cac API lien quan
                        // toi viec chu tro dang ky/quan ly goi Premium ho dang dung.
                        .requestMatchers(HttpMethod.POST, "/api/nha-tro/**", "/api/phong-tro/**", "/api/dang-tin/**")
                        .hasAnyAuthority(CHU_TRO, ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/nha-tro/**", "/api/phong-tro/**", "/api/dang-tin/**")
                        .hasAnyAuthority(CHU_TRO, ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/nha-tro/**", "/api/phong-tro/**", "/api/dang-tin/**")
                        .hasAnyAuthority(CHU_TRO, ADMIN)
                        .requestMatchers(
                                "/api/chi-so-dien-nuoc/**",
                                "/api/hoa-don-thang/**",
                                "/api/goi-dich-vu/**",
                                "/api/dang-ky-goi-chu-tro/**",
                                "/api/lich-su-goi-chu-tro/**",
                                "/api/hop-dong-premium/**",
                                "/api/hoa-don-dien-tu-premium/**")
                        .hasAnyAuthority(CHU_TRO, ADMIN)

                        // ===================== 4) NGUOI THUE (+ ADMIN) =====================
                        // Nghiep vu thuoc pham vi nguoi thue: gui yeu cau thue, xem lai lich su
                        // xem phong cua minh, dang ky nhan canh bao phong moi.
                        .requestMatchers(
                                "/api/yeu-cau-thue/**",
                                "/api/lich-su-xem-phong/**",
                                "/api/canh-bao-phong-moi/**")
                        .hasAnyAuthority(NGUOI_THUE, ADMIN)

                        // ===================== 5) DUNG CHUNG (da dang nhap, khong phan biet vai tro)
                        // =====================
                        // Hop dong, thanh toan coc, giao dich thanh toan, danh gia, lich hen,
                        // tin nhan, thong bao, xac thuc OTP: ca chu tro lan nguoi thue deu can dung,
                        // va xem ho so nguoi dung theo id (VD chu tro xem ho so nguoi thue truoc khi
                        // duyet).
                        .requestMatchers(HttpMethod.GET, "/api/nguoi-dung/**").authenticated()

                        // Tat ca nhung gi con lai: bat buoc phai dang nhap (JWT hop le).
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}