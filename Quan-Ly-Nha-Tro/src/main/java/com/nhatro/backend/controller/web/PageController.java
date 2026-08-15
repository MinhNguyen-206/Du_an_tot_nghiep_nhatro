package com.nhatro.backend.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller cho cac trang JSP (khac voi cac @RestController tra ve JSON
 * cho FE Vue truoc day). Moi @GetMapping o day tra ve TEN VIEW (String),
 * Spring se tu ghep voi spring.mvc.view.prefix/suffix trong
 * application.properties de tim ra dung file .jsp tuong ung.
 *
 * VD: return "home/home" -> render file
 *     src/main/webapp/WEB-INF/jsp/home/home.jsp
 *
 * Day chi la 1 vi du mau cho trang chu. Nhan rong pattern nay cho tung
 * trang con lai (auth/login, room/roomList, admin/adminDashboard...).
 */
@Controller
public class PageController {

    @GetMapping("/")
    public String trangChu(Model model) {
        // Vi du truyen du lieu sang JSP, lay bang ${tenBien} trong file .jsp
        model.addAttribute("tieuDe", "Room Connect - Trang chủ");
        return "home/home";
    }

    @GetMapping("/login")
    public String dangNhap() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String dangKy() {
        return "auth/register";
    }

    @GetMapping("/rooms")
    public String danhSachPhong() {
        return "room/roomList";
    }

    @GetMapping("/rooms/{id}")
    public String chiTietPhong(@org.springframework.web.bind.annotation.PathVariable Integer id, Model model) {
        model.addAttribute("maPhong", id);
        return "room/roomDetail";
    }
}
