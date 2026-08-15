package com.nhatro.backend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMvcController {

    private void addCommonAttrs(Model model, HttpSession session) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("userRole", session.getAttribute("userRole"));
    }

    private boolean isAdmin(HttpSession session) {
        Integer role = (Integer) session.getAttribute("userRole");
        return role != null && role == 1;
    }

    @GetMapping({"", "/"})
    public String dashboard(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        addCommonAttrs(model, session);
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String userManagement(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        addCommonAttrs(model, session);
        return "admin/user-management";
    }

    @GetMapping("/posts")
    public String postManagement(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        addCommonAttrs(model, session);
        return "admin/post-management";
    }

    @GetMapping("/payments")
    public String paymentManagement(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        addCommonAttrs(model, session);
        return "admin/payment-management";
    }

    @GetMapping("/contracts")
    public String contractManagement(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        addCommonAttrs(model, session);
        return "admin/contract-management";
    }

    @GetMapping("/roles")
    public String roleManagement(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        addCommonAttrs(model, session);
        return "admin/role-management";
    }

    @GetMapping("/admins")
    public String adminAccountManagement(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        addCommonAttrs(model, session);
        return "admin/admin-accounts";
    }

    @GetMapping("/categories")
    public String categoryConfigManagement(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        addCommonAttrs(model, session);
        return "admin/category-config";
    }

    @GetMapping("/activity-logs")
    public String activityLogManagement(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        addCommonAttrs(model, session);
        return "admin/activity-logs";
    }

    @GetMapping("/violation-reports")
    public String violationReportManagement(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        addCommonAttrs(model, session);
        return "admin/violation-reports";
    }

    @GetMapping("/ai-control")
    public String aiControlManagement(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        addCommonAttrs(model, session);
        return "admin/ai-control";
    }

    @GetMapping("/houses")
    public String houseManagement(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        addCommonAttrs(model, session);
        return "admin/house-management";
    }
}
