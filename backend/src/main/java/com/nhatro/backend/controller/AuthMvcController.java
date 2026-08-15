package com.nhatro.backend.controller;

import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.service.AuthService;
import com.nhatro.backend.service.NguoiDungService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * MVC Controller xử lý các trang xác thực (JSP): đăng nhập, đăng ký, quên mật khẩu.
 * Sử dụng session-based authentication cho JSP pages.
 */
@Controller
public class AuthMvcController {

    private final AuthService authService;
    private final NguoiDungService nguoiDungService;

    public AuthMvcController(AuthService authService,
                             NguoiDungService nguoiDungService) {
        this.authService = authService;
        this.nguoiDungService = nguoiDungService;
    }

    // ======================== ĐĂNG NHẬP ========================

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model,
                                HttpSession session) {
        // Nếu đã đăng nhập rồi thì redirect về trang chủ
        if (session.getAttribute("currentUser") != null) {
            return "redirect:/";
        }
        if (error != null) {
            model.addAttribute("errorMessage", "Email/số điện thoại hoặc mật khẩu không đúng.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Bạn đã đăng xuất thành công.");
        }
        return "auth/login";
    }

    @PostMapping("/do-login")
    public String handleLogin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              HttpSession session,
                              Model model) {
        try {
            NguoiDung user = authService.login(email, password);
            session.setAttribute("currentUser", user);
            session.setAttribute("userId", user.getMaNguoiDung());
            session.setAttribute("userRole", user.getVaiTro() != null ? user.getVaiTro().getMaVaiTro() : 3);
            session.setAttribute("userName", user.getHoTen());

            // Redirect theo vai trò: 1=Admin, 2=ChuTro, 3=NguoiThue
            Integer role = user.getVaiTro() != null ? user.getVaiTro().getMaVaiTro() : 3;
            if (role == 1) {
                return "redirect:/admin";
            }
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/login";
        }
    }

    // ======================== ĐĂNG XUẤT ========================

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }

    // ======================== ĐĂNG KÝ ========================

    @GetMapping("/register")
    public String showRegisterPage(HttpSession session) {
        if (session.getAttribute("currentUser") != null) {
            return "redirect:/";
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@RequestParam("hoTen") String hoTen,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam("soDienThoai") String soDienThoai,
                                 Model model) {
        try {
            // Kiểm tra email đã tồn tại chưa
            if (nguoiDungService.getByEmail(email).isPresent()) {
                model.addAttribute("errorMessage", "Email này đã được đăng ký.");
                return "auth/register";
            }

            NguoiDung newUser = NguoiDung.builder()
                    .hoTen(hoTen)
                    .email(email)
                    .soDienThoai(soDienThoai)
                    .matKhau(password)   // create() sẽ tự encode password
                    .trangThai(true)
                    .build();

            nguoiDungService.create(newUser);
            model.addAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "auth/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Đăng ký thất bại: " + e.getMessage());
            return "auth/register";
        }
    }

    // ======================== QUÊN MẬT KHẨU ========================

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "auth/forgot-password";
    }

    // ======================== XÁC THỰC OTP ========================

    @GetMapping("/otp-verify")
    public String showOtpVerifyPage() {
        return "auth/otp-verify";
    }
}
