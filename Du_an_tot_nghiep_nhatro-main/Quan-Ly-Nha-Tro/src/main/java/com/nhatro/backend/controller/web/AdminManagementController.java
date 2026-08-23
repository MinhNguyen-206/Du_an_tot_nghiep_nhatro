package com.nhatro.backend.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminManagementController {

    @GetMapping("/users")
    public String users(Model model) {
        return management(model, "Người dùng", "Quản lý tài khoản và trạng thái người dùng", "/api/admin/management/users", "users");
    }

    @GetMapping("/posts")
    public String posts(Model model) {
        return management(model, "Bài đăng & phòng trọ", "Theo dõi bài đăng và phòng trọ trên hệ thống", "/api/admin/management/posts", "posts");
    }

    @GetMapping("/appointments")
    public String appointments(Model model) {
        return management(model, "Lịch hẹn", "Theo dõi các lịch hẹn xem phòng", "/api/admin/management/appointments", "appointments");
    }

    @GetMapping("/contracts")
    public String contracts(Model model) {
        return management(model, "Hợp đồng", "Quản lý hợp đồng điện tử", "/api/admin/management/contracts", "contracts");
    }

    @GetMapping("/payments")
    public String payments(Model model) {
        return management(model, "Thanh toán", "Theo dõi giao dịch thanh toán", "/api/admin/management/payments", "payments");
    }

    @GetMapping("/reports")
    public String reports(Model model) {
        return management(model, "Báo cáo vi phạm", "Kiểm tra và xử lý báo cáo từ người dùng", "/api/admin/management/reports", "reports");
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        return management(model, "Cài đặt hệ thống", "Các cấu hình quản trị hiện có", "/api/admin/management/settings", "settings");
    }

    private String management(Model model, String title, String description, String apiEndpoint, String activeMenu) {
        model.addAttribute("pageTitle", title);
        model.addAttribute("pageDescription", description);
        model.addAttribute("apiEndpoint", apiEndpoint);
        model.addAttribute("activeMenu", activeMenu);
        return "admin/adminManagement";
    }
}
