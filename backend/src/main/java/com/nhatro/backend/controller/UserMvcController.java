package com.nhatro.backend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserMvcController {

    private void addCommonAttrs(Model model, HttpSession session) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("userRole", session.getAttribute("userRole"));
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        addCommonAttrs(model, session);
        return "profile/profile";
    }

    @GetMapping("/notifications")
    public String notifications(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        addCommonAttrs(model, session);
        return "notification/notifications";
    }

    @GetMapping("/messages")
    public String messages(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        addCommonAttrs(model, session);
        return "message/message-box";
    }

    @GetMapping("/appointments")
    public String appointments(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        addCommonAttrs(model, session);
        return "appointment/appointments";
    }

    @GetMapping("/rental-requests")
    public String rentalRequests(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        addCommonAttrs(model, session);
        return "rental-request/rental-requests";
    }

    @GetMapping("/reviews")
    public String reviews(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        addCommonAttrs(model, session);
        return "review/reviews";
    }

    @GetMapping("/invoices/monthly")
    public String monthlyInvoices(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        addCommonAttrs(model, session);
        return "invoice/monthly-invoices";
    }

    @GetMapping("/deposits")
    public String deposits(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        addCommonAttrs(model, session);
        return "deposit/deposits";
    }

    @GetMapping("/service-packages")
    public String servicePackages(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        addCommonAttrs(model, session);
        return "service-package/service-packages";
    }

    @GetMapping("/utility-index")
    public String utilityIndex(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        addCommonAttrs(model, session);
        return "utility/utility-index";
    }
}
