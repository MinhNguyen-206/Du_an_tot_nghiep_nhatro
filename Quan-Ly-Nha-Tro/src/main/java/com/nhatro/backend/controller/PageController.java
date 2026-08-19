package com.nhatro.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // Trỏ tới file login.jsp
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    // Trỏ tới file register.jsp
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    // Trỏ tới file forgotPassword.jsp
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "auth/forgotPassword";
    }

    // Trỏ tới file resetPassword.jsp
    @GetMapping("/reset-password")
    public String resetPasswordPage() {
        return "auth/resetPassword";
    }

    // Logout - xóa token và redirect tới trang chủ
    @GetMapping("/logout")
    public String logout() {
        // Token được lưu ở client (localStorage/sessionStorage)
        // Nên server chỉ cần redirect về trang chủ
        // Client sẽ tự động xóa token
        return "redirect:/";
    }
}