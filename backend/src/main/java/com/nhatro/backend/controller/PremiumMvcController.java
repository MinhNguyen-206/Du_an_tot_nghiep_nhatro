package com.nhatro.backend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/premium")
public class PremiumMvcController {

    private void addCommonAttrs(Model model, HttpSession session) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("userRole", session.getAttribute("userRole"));
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping("/packages")
    public String packageList(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        addCommonAttrs(model, session);
        return "premium/package-list";
    }

    @GetMapping("/subscribe")
    public String subscribe(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        addCommonAttrs(model, session);
        return "premium/subscribe";
    }

    @GetMapping("/subscription-history")
    public String subscriptionHistory(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        addCommonAttrs(model, session);
        return "premium/subscription-history";
    }

    @GetMapping("/contracts")
    public String contracts(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        addCommonAttrs(model, session);
        return "premium/contracts";
    }

    @GetMapping("/invoices")
    public String invoices(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        addCommonAttrs(model, session);
        return "premium/invoices";
    }
}
