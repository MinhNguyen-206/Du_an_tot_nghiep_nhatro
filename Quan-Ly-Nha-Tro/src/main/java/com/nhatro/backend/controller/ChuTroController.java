package com.nhatro.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller giao diện demo dành cho Chủ Trọ.
 * Dữ liệu hiện tại là mock, sau này có thể nối Service/JPA/SQL.
 */
@Controller
@RequestMapping("/chu-tro")
public class ChuTroController {

    @GetMapping({"", "/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("userName", "Nguyễn Văn Chủ Trọ");
        model.addAttribute("totalRooms", 24);
        model.addAttribute("occupiedRooms", 18);
        model.addAttribute("availableRooms", 6);
        model.addAttribute("monthlyRevenue", "32.5M");
        return "chu-tro/dashboard";
    }

    @GetMapping("/properties")
    public String properties() { return "chu-tro/properties"; }

    @GetMapping("/rooms")
    public String rooms() { return "chu-tro/rooms"; }

    @GetMapping("/posts")
    public String posts() { return "chu-tro/posts"; }

    @GetMapping("/rental-requests")
    public String rentalRequests() { return "chu-tro/rentalRequests"; }

    @GetMapping("/contracts")
    public String contracts() { return "chu-tro/contracts"; }

    @GetMapping("/invoices")
    public String invoices() { return "chu-tro/invoices"; }

    @GetMapping("/meters")
    public String meters() { return "chu-tro/meters"; }

    @GetMapping("/revenue")
    public String revenue() { return "chu-tro/revenue"; }

    @GetMapping("/appointments")
    public String appointments() { return "chu-tro/appointments"; }

    @GetMapping("/reviews")
    public String reviews() { return "chu-tro/reviews"; }

    @GetMapping("/notifications")
    public String notifications() { return "chu-tro/notifications"; }

    @GetMapping("/profile")
    public String profile() { return "chu-tro/profile"; }
}
