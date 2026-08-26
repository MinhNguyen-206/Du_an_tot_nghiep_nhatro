package com.nhatro.backend.controller;

import com.nhatro.backend.dto.LichHenChuTroDto;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.NhaTro;
import com.nhatro.backend.entity.PhongTro;
import com.nhatro.backend.service.LichHenService;
import com.nhatro.backend.service.NguoiDungService;
import com.nhatro.backend.service.NhaTroService;
import com.nhatro.backend.service.PhongTroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    // Tai khoan demo dung khi chua dang nhap (giu dong bo voi
    // NhaTroApiController de 2 trang "Nha tro" va "Phong tro" cung xem
    // du lieu cua 1 chu tro trong luc test).
    private static final String DEMO_EMAIL = "chutro1@nhatro.vn";

    private final NguoiDungService nguoiDungService;
    private final NhaTroService nhaTroService;
    private final PhongTroService phongTroService;
    private final LichHenService lichHenService;

    public ChuTroController(
            NguoiDungService nguoiDungService,
            NhaTroService nhaTroService,
            PhongTroService phongTroService,
            LichHenService lichHenService
    ) {
        this.nguoiDungService = nguoiDungService;
        this.nhaTroService = nhaTroService;
        this.phongTroService = phongTroService;
        this.lichHenService = lichHenService;
    }

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

    // Trang "Nha tro" - load danh sach nha tro that tu SQL Server (bang
    // NHA_TRO), chi lay nha tro cua chu tro dang dang nhap, kem so lieu tong
    // phong / dang thue / trong cua tung nha tro.
    @GetMapping("/chu-tro/properties")
    public String properties(Model model, HttpSession session) {
        NguoiDung owner = currentOwner(session);

        List<NhaTro> properties = nhaTroService.getByNguoiDung(owner.getMaNguoiDung());

        List<Map<String, Object>> propertyCards = new ArrayList<>();
        int totalRooms = 0;
        int totalAvailable = 0;

        for (NhaTro property : properties) {
            List<PhongTro> rooms = phongTroService.getByNhaTro(property.getMaNhaTro());

            long roomCount = rooms.size();
            long availableCount = rooms.stream()
                    .filter(r -> r.getTrangThai() == null || r.getTrangThai())
                    .count();
            long occupiedCount = roomCount - availableCount;

            Map<String, Object> card = new LinkedHashMap<>();
            card.put("id", property.getMaNhaTro());
            card.put("name", property.getTenNhaTro());
            card.put("address", property.getDiaChi());
            card.put("price", property.getGiaPhong());
            card.put("roomType", property.getLoaiPhong());
            card.put("description", property.getMoTa());
            card.put("image", property.getHinhAnh());
            card.put("rating", property.getSoSao());
            card.put("roomCount", roomCount);
            card.put("occupiedCount", occupiedCount);
            card.put("availableCount", availableCount);
            propertyCards.add(card);

            totalRooms += roomCount;
            totalAvailable += availableCount;
        }

        model.addAttribute("propertyCards", propertyCards);
        model.addAttribute("propertyCount", properties.size());
        model.addAttribute("propertyRoomCount", totalRooms);
        model.addAttribute("propertyAvailableCount", totalAvailable);

        return "chu-tro/properties";
    }

    // Trang "Phong tro" - load danh sach phong that tu SQL Server (bang
    // PHONG_TRO), chi lay phong thuoc cac nha tro cua chu tro dang dang nhap.
    @GetMapping("/chu-tro/rooms")
    public String rooms(Model model, HttpSession session) {
        NguoiDung owner = currentOwner(session);

        List<NhaTro> properties = nhaTroService.getByNguoiDung(owner.getMaNguoiDung());

        List<Map<String, Object>> propertyOptions = new ArrayList<>();
        List<Map<String, Object>> roomCards = new ArrayList<>();
        int total = 0;
        int occupied = 0;
        int available = 0;

        for (NhaTro property : properties) {
            Map<String, Object> option = new LinkedHashMap<>();
            option.put("maNhaTro", property.getMaNhaTro());
            option.put("tenNhaTro", property.getTenNhaTro());
            propertyOptions.add(option);

            List<PhongTro> rooms = phongTroService.getByNhaTro(property.getMaNhaTro());

            for (PhongTro room : rooms) {
                boolean isAvailable = room.getTrangThai() == null || room.getTrangThai();

                Map<String, Object> card = new LinkedHashMap<>();
                card.put("id", room.getMaPhong());
                card.put("propertyId", property.getMaNhaTro());
                card.put("propertyName", property.getTenNhaTro());
                card.put("name", room.getTenPhong());
                card.put("price", room.getGiaPhong());
                card.put("area", room.getDienTich());
                card.put("roomType", room.getLoaiPhong());
                card.put("maxPeople", room.getSoLuongNguoi());
                card.put("electricPrice", room.getGiaDien());
                card.put("waterPrice", room.getGiaNuoc());
                card.put("parkingPrice", room.getGiaGuiXe());
                card.put("internetPrice", room.getGiaInternet());
                card.put("available", isAvailable);
                card.put("status", isAvailable ? "available" : "occupied");
                roomCards.add(card);

                total++;
                if (isAvailable) {
                    available++;
                } else {
                    occupied++;
                }
            }
        }

        model.addAttribute("roomProperties", propertyOptions);
        model.addAttribute("roomCards", roomCards);
        model.addAttribute("roomTotal", total);
        model.addAttribute("roomOccupied", occupied);
        model.addAttribute("roomAvailable", available);

        return "chu-tro/rooms";
    }

    // Xu ly nut "Them phong" tren trang Phong tro.
    @PostMapping("/chu-tro/rooms/save")
    public String saveRoom(
            @ModelAttribute RoomForm form,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        try {
            NguoiDung owner = currentOwner(session);
            phongTroService.createForOwner(
                    form.getMaNhaTro(),
                    form.getTenPhong(),
                    form.getDienTich(),
                    form.getLoaiPhong(),
                    form.getSoLuongNguoi(),
                    form.getGiaPhong(),
                    form.getGiaDien(),
                    form.getGiaNuoc(),
                    form.getGiaGuiXe(),
                    form.getGiaInternet(),
                    form.getTrangThai(),
                    owner
            );
            return "redirect:/chu-tro/rooms?success=1";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/chu-tro/rooms";
        }
    }

    // Xu ly nut "Luu thay doi" (sua phong) tren trang Phong tro.
    @PostMapping("/chu-tro/rooms/update")
    public String updateRoom(
            @ModelAttribute RoomForm form,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        try {
            NguoiDung owner = currentOwner(session);
            phongTroService.updateForOwner(
                    form.getMaPhong(),
                    form.getMaNhaTro(),
                    form.getTenPhong(),
                    form.getDienTich(),
                    form.getLoaiPhong(),
                    form.getSoLuongNguoi(),
                    form.getGiaPhong(),
                    form.getGiaDien(),
                    form.getGiaNuoc(),
                    form.getGiaGuiXe(),
                    form.getGiaInternet(),
                    form.getTrangThai(),
                    owner
            );
            return "redirect:/chu-tro/rooms?success=2";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/chu-tro/rooms";
        }
    }

    // Xu ly nut "Xoa" phong tren trang Phong tro.
    @PostMapping("/chu-tro/rooms/{id}/delete")
    public String deleteRoom(
            @PathVariable Integer id,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        try {
            NguoiDung owner = currentOwner(session);
            phongTroService.deleteForOwner(id, owner);
            return "redirect:/chu-tro/rooms?success=3";
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/chu-tro/rooms";
        }
    }

    // =========================================================
    // LAY CHU TRO DANG DANG NHAP (dong bo logic voi NhaTroApiController)
    // =========================================================
    private NguoiDung currentOwner(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = null;
        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getName() != null
                && !"anonymousUser".equals(authentication.getName())) {
            email = authentication.getName();
        }

        if (email == null || email.isBlank()) {
            Object sessionEmail = session.getAttribute("landlordEmail");
            if (sessionEmail != null) {
                email = sessionEmail.toString();
            }
        }

        if (email == null || email.isBlank()) {
            email = DEMO_EMAIL;
        }

        NguoiDung user = nguoiDungService.getByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản chủ trọ."));

        if (user.getVaiTro() == null
                || user.getVaiTro().getMaVaiTro() == null
                || user.getVaiTro().getMaVaiTro() != 2) {
            throw new IllegalArgumentException("Tài khoản hiện tại không phải chủ trọ.");
        }

        session.setAttribute("landlordEmail", user.getEmail());
        return user;
    }

    // Object nhan du lieu tu form "Them/Sua phong" (rooms.jsp) qua POST.
    public static class RoomForm {
        private Integer maPhong;
        private Integer maNhaTro;
        private String tenPhong;
        private BigDecimal dienTich;
        private String loaiPhong;
        private Integer soLuongNguoi;
        private BigDecimal giaPhong;
        private BigDecimal giaDien;
        private BigDecimal giaNuoc;
        private BigDecimal giaGuiXe;
        private BigDecimal giaInternet;
        private Boolean trangThai;

        public Integer getMaPhong() { return maPhong; }
        public void setMaPhong(Integer maPhong) { this.maPhong = maPhong; }

        public Integer getMaNhaTro() { return maNhaTro; }
        public void setMaNhaTro(Integer maNhaTro) { this.maNhaTro = maNhaTro; }

        public String getTenPhong() { return tenPhong; }
        public void setTenPhong(String tenPhong) { this.tenPhong = tenPhong; }

        public BigDecimal getDienTich() { return dienTich; }
        public void setDienTich(BigDecimal dienTich) { this.dienTich = dienTich; }

        public String getLoaiPhong() { return loaiPhong; }
        public void setLoaiPhong(String loaiPhong) { this.loaiPhong = loaiPhong; }

        public Integer getSoLuongNguoi() { return soLuongNguoi; }
        public void setSoLuongNguoi(Integer soLuongNguoi) { this.soLuongNguoi = soLuongNguoi; }

        public BigDecimal getGiaPhong() { return giaPhong; }
        public void setGiaPhong(BigDecimal giaPhong) { this.giaPhong = giaPhong; }

        public BigDecimal getGiaDien() { return giaDien; }
        public void setGiaDien(BigDecimal giaDien) { this.giaDien = giaDien; }

        public BigDecimal getGiaNuoc() { return giaNuoc; }
        public void setGiaNuoc(BigDecimal giaNuoc) { this.giaNuoc = giaNuoc; }

        public BigDecimal getGiaGuiXe() { return giaGuiXe; }
        public void setGiaGuiXe(BigDecimal giaGuiXe) { this.giaGuiXe = giaGuiXe; }

        public BigDecimal getGiaInternet() { return giaInternet; }
        public void setGiaInternet(BigDecimal giaInternet) { this.giaInternet = giaInternet; }

        public Boolean getTrangThai() { return trangThai; }
        public void setTrangThai(Boolean trangThai) { this.trangThai = trangThai; }
    }

    @GetMapping("/chu-tro/posts")
    public String posts() {
        return "chu-tro/posts";
    }

    // Trang "Lich hen" - load danh sach lich hen xem phong that tu SQL Server,
    // chi lay lich hen thuoc cac nha tro cua chu tro dang dang nhap.
    @GetMapping("/chu-tro/appointments")
    public String appointments(Model model, HttpSession session) {
        NguoiDung owner = currentOwner(session);

        List<LichHenChuTroDto> appointments = lichHenService.getByChuTro(owner.getMaNguoiDung());

        long pending = appointments.stream()
                .filter(a -> "Đang xử lý".equals(a.getTrangThai()))
                .count();
        long confirmed = appointments.stream()
                .filter(a -> "Đã xác nhận".equals(a.getTrangThai()))
                .count();

        model.addAttribute("appointments", appointments);
        model.addAttribute("pendingAppointments", pending);
        model.addAttribute("confirmedAppointments", confirmed);

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