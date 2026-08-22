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

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
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
                                "/oauth2/**",
                                "/login/oauth2/**",
                                "/oauth2-redirect"
                        ).permitAll()

                        // Trang "Đăng ký chủ trọ": chỉ cần đăng nhập (bất kỳ vai trò nào
                        // chưa phải Chủ trọ), KHÔNG cần đã là Chủ trọ như "/chu-tro/**".
                        .requestMatchers("/dang-ky-chu-tro")
                        .authenticated()

                        .requestMatchers("/admin/**")
                        .permitAll()

                        .requestMatchers("/chu-tro/**")
                        .hasAnyAuthority(CHU_TRO, ADMIN, "CHU_TRO", "ADMIN")

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

                        // Yeu cau dang ky Chu tro:
                        // - Xem 1 yeu cau cu the (theo id) va gui yeu cau moi -> chi can dang nhap
                        //   (kiem tra chinh chu/tu the o tang service/controller).
                        // - Duyet / tu choi -> chi Admin.
                        // - GET danh sach tat ca ("/api/yeu-cau-chu-tro" khong co path con) -> chi Admin.
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/yeu-cau-chu-tro/*/duyet",
                                "/api/yeu-cau-chu-tro/*/tu-choi"
                        ).hasAnyAuthority(ADMIN, "ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/yeu-cau-chu-tro"
                        ).hasAnyAuthority(ADMIN, "ADMIN")

                        // "/**" o day khop ca chinh "/api/yeu-cau-chu-tro" (vd: POST gui yeu cau moi).
                        .requestMatchers(
                                "/api/yeu-cau-chu-tro/**"
                        ).authenticated()

                        .requestMatchers(
                                "/api/profile/**",
                                "/api/phong-yeu-thich/**",
                                "/api/lich-su-xem-phong/**"
                        ).authenticated()

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

                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureUrl("/login?error=google_failed")
                )

                .exceptionHandling(exception -> exception
                        // Chua dang nhap (khong co/ het han cookie "jwt"):
                        // - Goi trang JSP (/chu-tro, /admin,...) -> redirect ve /login that
                        // - Goi API (/api/**) -> tra 401 JSON, KHONG redirect (fetch() se tu xu ly)
                        .authenticationEntryPoint((request, response, authException) -> {
                            String uri = request.getRequestURI();
                            if (uri.startsWith(request.getContextPath() + "/api/")) {
                                response.sendError(
                                        HttpServletResponse.SC_UNAUTHORIZED,
                                        "Chua dang nhap"
                                );
                            } else {
                                response.sendRedirect(
                                        request.getContextPath()
                                                + "/login?redirect="
                                                + java.net.URLEncoder.encode(uri, java.nio.charset.StandardCharsets.UTF_8)
                                );
                            }
                        })
                        // Da dang nhap nhung sai vai tro (vd: nguoi thue vao /chu-tro):
                        // tra 403 that thay vi permitAll ngam nhu truoc, tranh lo du lieu.
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            String uri = request.getRequestURI();
                            if (uri.startsWith(request.getContextPath() + "/api/")) {
                                response.sendError(
                                        HttpServletResponse.SC_FORBIDDEN,
                                        "Khong co quyen truy cap"
                                );
                            } else {
                                response.sendRedirect(
                                        request.getContextPath() + "/login?error=forbidden"
                                );
                            }
                        })
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}