package com.nhatro.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller cho cac trang JSP trong khu vuc "Kenh chu tro" (/chu-tro/**).
 *
 * TRUOC KHI CO FILE NAY: khong he co @GetMapping nao xu ly "/chu-tro" hay
 * "/chu-tro/rental-requests" ca (cac file .jsp trong webapp/WEB-INF/jsp/chu-tro/
 * da duoc ban cua ban thiet ke san, nhung chua noi voi controller nao).
 * Vi vay khi bam nut "Kenh chu tro" tren header, Spring khong tim thay
 * @Controller nao khop -> roi xuong ResourceHttpRequestHandler (tim file
 * static ten "chu-tro") -> khong thay -> 404 "No static resource chu-tro".
 *
 * File nay chi lam nhiem vu dieu huong toi dung view JSP (khong truyen
 * model), vi phan lon cac trang nay hien la giao dien tinh (rooms, dashboard,
 * invoices...) hoac tu load du lieu bang JS + apiFetch() (rental-requests) -
 * dung quy uoc chung cua du an (xem resources/js/api.js).
 *
 * Quyen truy cap "/chu-tro/**" (chi CHU_TRO hoac ADMIN) da duoc chan san o
 * SecurityConfig, khong can kiem tra lai o day.
 */
@Controller
public class ChuTroController {

    // "/chu-tro" (link "Kenh chu tro" tren header) va "/chu-tro/dashboard"
    // (link "Tong quan" tren sidebar) cung tro ve 1 trang tong quan.
    @GetMapping({ "/chu-tro", "/chu-tro/dashboard" })
    public String dashboard() {
        return "chu-tro/dashboard";
    }

    // Trang "Yeu cau thue" - DA duoc noi du lieu that qua JS goi
    // GET /api/yeu-cau-thue/chu-tro/{maChuTro} (xem rentalRequests.jsp).
    @GetMapping("/chu-tro/rental-requests")
    public String rentalRequests() {
        return "chu-tro/rentalRequests";
    }

    @GetMapping("/chu-tro/properties")
    public String properties() {
        return "chu-tro/properties";
    }

    @GetMapping("/chu-tro/rooms")
    public String rooms() {
        return "chu-tro/rooms";
    }

    @GetMapping("/chu-tro/posts")
    public String posts() {
        return "chu-tro/posts";
    }

    @GetMapping("/chu-tro/appointments")
    public String appointments() {
        return "chu-tro/appointments";
    }

    @GetMapping("/chu-tro/contracts")
    public String contracts() {
        return "chu-tro/contracts";
    }

    @GetMapping("/chu-tro/invoices")
    public String invoices() {
        return "chu-tro/invoices";
    }

    @GetMapping("/chu-tro/meters")
    public String meters() {
        return "chu-tro/meters";
    }

    @GetMapping("/chu-tro/revenue")
    public String revenue() {
        return "chu-tro/revenue";
    }

    @GetMapping("/chu-tro/reviews")
    public String reviews() {
        return "chu-tro/reviews";
    }

    @GetMapping("/chu-tro/notifications")
    public String notifications() {
        return "chu-tro/notifications";
    }

    @GetMapping("/chu-tro/profile")
    public String profile() {
        return "chu-tro/profile";
    }
}
