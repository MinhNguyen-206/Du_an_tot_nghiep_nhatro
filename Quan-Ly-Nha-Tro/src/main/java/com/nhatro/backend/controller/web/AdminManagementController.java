package com.nhatro.backend.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminManagementController {

    // Da chuyen "/admin/users" sang mot trang JSP rieng, day du chuc nang
    // (khong con dung template bang generic nay nua) - xem
    // AdminDashboardController#userManagement() + userManagement.jsp +
    // resources/js/user-management.js + AdminNguoiDungController.

    // Da chuyen "/admin/posts" sang mot trang JSP rieng, day du chuc nang
    // duyet/tu choi bai dang - xem AdminDashboardController#postApproval()
    // + postApproval.jsp + resources/js/post-approval.js + AdminDangTinController.

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
