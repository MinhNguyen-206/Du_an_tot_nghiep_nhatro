package com.nhatro.backend.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

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

    @GetMapping("/forgot-password")
    public String quenMatKhau() {
        return "auth/forgotPassword";
    }

    @GetMapping("/reset-password")
    public String datLaiMatKhau() {
        return "auth/resetPassword";
    }

    // "/rooms" la route cu (stub roomList.jsp chua noi voi du lieu that).
    // Chuc nang tim kiem + loc phong hien da hoat dong day du o "/thue-tro"
    // (xem HomeController + NhaTroSpecification), nen redirect sang do va
    // giu nguyen query string (keyword, type, minPrice...) de cac form
    // tim kiem cu (neu con tro ve "/rooms") van hoat dong dung.
    @GetMapping("/rooms")
    public String danhSachPhong(HttpServletRequest request) {
        String queryString = request.getQueryString();
        return "redirect:/thue-tro" + (queryString != null ? "?" + queryString : "");
    }

    @GetMapping("/rooms/{id}")
    public String chiTietPhong(@org.springframework.web.bind.annotation.PathVariable Integer id, Model model) {
        model.addAttribute("maPhong", id);
        return "room/roomDetail";
    }

    // Da bo trang "/chon-vai-tro": tai khoan Google moi gio tu dong dang ky
    // voi vai tro "Người thuê" ngay trong OAuth2LoginSuccessHandler, khong
    // con man hinh trung gian de chon vai tro nua.

    @GetMapping("/oauth2-redirect")
    public String oauth2Redirect() {
        return "auth/oauth2-redirect";
    }

    // =====================================================
    // VE CHUNG TOI + LIEN HE
    // =====================================================

    @GetMapping("/gioi-thieu")
    public String gioiThieu() {
        return "about/about";
    }

    @GetMapping("/lien-he")
    public String lienHe() {
        return "contact/contact";
    }

    // =====================================================
    // HO SO CA NHAN (trang tinh, tu kiem tra dang nhap bang JS
    // + localStorage giong header.jsp; xem note trong SecurityConfig)
    // =====================================================

    @GetMapping("/profile")
    public String hoSoCaNhan() {
        return "profile/profile";
    }

    // =====================================================
    // DANG KY CHU TRO (Nguoi thue gui yeu cau + upload xac minh,
    // Admin duyet tai /admin/duyet-chu-tro - xem AdminPageController hoac
    // admin/ekycApproval.jsp)
    // =====================================================

    @GetMapping("/dang-ky-chu-tro")
    public String dangKyChuTro() {
        return "chu-tro/dangKyChuTro";
    }
}
