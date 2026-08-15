package com.nhatro.backend.controller;

import com.nhatro.backend.service.DangTinService;
import com.nhatro.backend.service.PhongTroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeMvcController {

    private final DangTinService dangTinService;
    private final PhongTroService phongTroService;

    public HomeMvcController(DangTinService dangTinService, PhongTroService phongTroService) {
        this.dangTinService = dangTinService;
        this.phongTroService = phongTroService;
    }

    @GetMapping("/")
    public String showHome(Model model, HttpSession session) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("userRole", session.getAttribute("userRole"));
        try {
            model.addAttribute("dangTinList", dangTinService.getAll());
        } catch (Exception e) {
            model.addAttribute("dangTinList", java.util.Collections.emptyList());
        }
        return "home/index";
    }

    @GetMapping("/rooms")
    public String showRoomList(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "category", required = false) String category,
                               @RequestParam(value = "province", required = false) String province,
                               Model model, HttpSession session) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("userRole", session.getAttribute("userRole"));
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("province", province);
        try {
            model.addAttribute("phongTroList", phongTroService.getAll());
        } catch (Exception e) {
            model.addAttribute("phongTroList", java.util.Collections.emptyList());
        }
        return "room/room-list";
    }

    @GetMapping("/rooms/{id}")
    public String showRoomDetail(@PathVariable Integer id, Model model, HttpSession session) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("userRole", session.getAttribute("userRole"));
        model.addAttribute("roomId", id);
        try {
            phongTroService.getById(id).ifPresent(p -> model.addAttribute("phongTro", p));
        } catch (Exception e) {
            // Handle not found
        }
        return "room/room-detail";
    }

    @GetMapping("/rooms/view-history")
    public String showRoomViewHistory(Model model, HttpSession session) {
        if (session.getAttribute("currentUser") == null) return "redirect:/login";
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("userName", session.getAttribute("userName"));
        return "room/view-history";
    }

    @GetMapping("/rooms/new-alerts")
    public String showNewRoomAlerts(Model model, HttpSession session) {
        if (session.getAttribute("currentUser") == null) return "redirect:/login";
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("userName", session.getAttribute("userName"));
        return "room/new-alerts";
    }
}
