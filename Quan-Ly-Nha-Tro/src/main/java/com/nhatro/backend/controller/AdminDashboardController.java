package com.nhatro.backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    /**
     * Trang chính của Admin Dashboard
     * Chỉ cho phép người có role ADMIN
     */
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("email", "admin@qlnt.vn");
        model.addAttribute("userName", "admin@qlnt.vn");
        return "admin/adminDashboard";
    }

    /**
     * Chuyển hướng /admin -> /admin/dashboard
     */
    @GetMapping
    public String adminRedirect() {
        return "redirect:/admin/dashboard";
    }
}
