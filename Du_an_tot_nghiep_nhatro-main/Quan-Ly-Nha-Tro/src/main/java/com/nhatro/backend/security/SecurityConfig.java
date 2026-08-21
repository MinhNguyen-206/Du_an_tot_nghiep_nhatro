package com.nhatro.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    private static final String ADMIN = "ROLE_ADMIN";
    private static final String CHU_TRO = "ROLE_CHU_TRO";
    private static final String NGUOI_THUE = "ROLE_NGUOI_THUE";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()

                        .requestMatchers(
                                "/resources/**",
                                "/static/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/img/**",
                                "/favicon.ico",
                                "/WEB-INF/**"
                        ).permitAll()

                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        .requestMatchers(
                                "/",
                                "/home",
                                "/login",
                                "/register",
                                "/forgot-password",
                                "/reset-password",
                                "/chon-vai-tro",
                                "/logout",
                                "/gioi-thieu",
                                "/lien-he",
                                "/thue-tro",
                                "/thue-can-ho",
                                "/chi-tiet-phong",
                                "/chi-tiet-phong/**",
                                "/rooms",
                                "/rooms/**",
                                "/profile"
                        ).permitAll()

                        .requestMatchers("/admin/**")
                        .permitAll()

                        .requestMatchers("/chu-tro/**")
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/nguoi-dung"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/lien-he"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/admin/dashboard"
                        ).hasAnyAuthority(ADMIN, "ADMIN")

                        .requestMatchers(
                                "/api/admin/management/**"
                        ).hasAnyAuthority(ADMIN, "ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/phong-tro/**",
                                "/api/dang-tin/**",
                                "/api/nha-tro/**",
                                "/api/danh-gia/**",
                                "/api/goi-dich-vu/**",
                                "/api/hinh-anh/**"
                        ).permitAll()

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

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/nguoi-dung/**"
                        ).authenticated()

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/nguoi-dung/**"
                        ).hasAuthority(ADMIN)

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/nguoi-dung"
                        ).hasAuthority(ADMIN)

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/nha-tro/**",
                                "/api/phong-tro/**",
                                "/api/dang-tin/**"
                        ).hasAnyAuthority(CHU_TRO, ADMIN)

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/nha-tro/**",
                                "/api/phong-tro/**",
                                "/api/dang-tin/**"
                        ).hasAnyAuthority(CHU_TRO, ADMIN)

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/nha-tro/**",
                                "/api/phong-tro/**",
                                "/api/dang-tin/**"
                        ).hasAnyAuthority(CHU_TRO, ADMIN)

                        .requestMatchers(
                                "/api/chi-so-dien-nuoc/**",
                                "/api/hoa-don-thang/**",
                                "/api/dang-ky-goi-chu-tro/**",
                                "/api/hop-dong-premium/**",
                                "/api/hoa-don-premium/**",
                                "/api/gia-han-hop-dong/**"
                        ).hasAnyAuthority(CHU_TRO, ADMIN)

                        .requestMatchers(
                                "/api/yeu-cau-thue/**",
                                "/api/lich-hen/**"
                        ).hasAnyAuthority(
                                NGUOI_THUE,
                                CHU_TRO,
                                ADMIN
                        )

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/nguoi-dung/**"
                        ).authenticated()

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}