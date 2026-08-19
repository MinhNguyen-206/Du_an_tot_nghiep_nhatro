package com.nhatro.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    // =====================================================
    // DASHBOARD
    // =====================================================

    @GetMapping("")
    public String adminHome() {
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/")
    public String adminHomeSlash() {
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {

        // Demo data
        model.addAttribute("email", "admin@qlnt.vn");
        model.addAttribute("userName", "Administrator");

        model.addAttribute("totalUsers", 1284);
        model.addAttribute("totalPosts", 856);
        model.addAttribute("pendingEkyc", 7);
        model.addAttribute("monthlyRevenue", "86.4M");

        return "admin/adminDashboard";
    }


    // =====================================================
    // KIỂM DUYỆT
    // =====================================================

    @GetMapping("/posts")
    public String postApproval() {

        return "admin/postApproval";
    }


    @GetMapping("/ekyc")
    public String ekycApproval() {

        return "admin/ekycApproval";
    }


    @GetMapping("/reviews")
    public String reviewModeration() {

        return "admin/reviewModeration";
    }


    // =====================================================
    // QUẢN LÝ
    // =====================================================

    @GetMapping("/users")
    public String userManagement() {

        return "admin/userManagement";
    }


    @GetMapping("/complaints")
    public String complaintManagement() {

        return "admin/complaintManagement";
    }


    @GetMapping("/categories")
    public String categoryManagement() {

        return "admin/categoryManagement";
    }


    @GetMapping("/blog")
    public String blogManagement() {

        return "admin/blogManagement";
    }


    // =====================================================
    // THỐNG KÊ
    // =====================================================

    @GetMapping("/statistics")
    public String statistics() {

        return "admin/statistics";
    }


    @GetMapping("/revenue")
    public String revenue() {

        return "admin/revenue";
    }


    @GetMapping("/transactions")
    public String transactions() {

        return "admin/transactions";
    }


    @GetMapping("/premium")
    public String premiumManagement() {

        return "admin/premiumManagement";
    }


    // =====================================================
    // HỆ THỐNG
    // =====================================================

    @GetMapping("/notifications")
    public String notifications() {

        return "admin/notifications";
    }


    @GetMapping("/activity-log")
    public String activityLogManagement() {

        return "admin/activityLogManagement";
    }


    // =====================================================
    // AI ADMIN
    // =====================================================

    @GetMapping("/ai")
    public String aiAdmin() {

        return "admin/aiAdmin";
    }
}