package com.nhatro.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhatro.backend.entity.NhaTro;
import com.nhatro.backend.entity.PhongTro;
import com.nhatro.backend.repository.NhaTroRepository;
import com.nhatro.backend.repository.PhongTroRepository;
import com.nhatro.backend.repository.specification.NhaTroSpecification;

import jakarta.transaction.Transactional;

/*
 * Toàn bộ dữ liệu hiển thị trên các trang JSP trong controller này đều lấy
 * từ database thật (NHA_TRO / PHONG_TRO) qua JPA - KHÔNG còn dữ liệu mẫu
 * hard-code trong code Java nữa.
 */
@Controller
public class HomeController {

    private final NhaTroRepository nhaTroRepository;
    private final PhongTroRepository phongTroRepository;

    public HomeController(NhaTroRepository nhaTroRepository, PhongTroRepository phongTroRepository) {
        this.nhaTroRepository = nhaTroRepository;
        this.phongTroRepository = phongTroRepository;
    }

    /*
     * ============================================================
     * TRANG CHỦ
     *
     * Lấy 6 nhà trọ mới nhất (theo maNhaTro giảm dần) từ DB để hiển
     * thị ở khối "Tin đăng mới nhất" trên trang chủ.
     * ============================================================
     */
    @GetMapping({"/home"})
    @Transactional
    public String home(Model model) {

        Pageable pageable = PageRequest.of(0, 6, Sort.by("maNhaTro").descending());
        Page<NhaTro> nhaTroPage = nhaTroRepository.findAll(pageable);

        model.addAttribute("listNhaTro", nhaTroPage.getContent());
        model.addAttribute("repRoomId", buildRepRoomIdMap(nhaTroPage.getContent()));

        return "home/home";
    }


    /*
     * ============================================================
     * TRANG THUÊ PHÒNG TRỌ (đã gộp chức năng bộ lọc: khu vực, khoảng
     * giá, loại phòng, tiện ích wifi/điều hòa/giữ xe/camera/nuôi thú,
     * sắp xếp theo giá, phân trang)
     *
     * URL:
     * /thue-tro
     * ============================================================
     */
    @GetMapping("/thue-tro")
    @Transactional
    public String thueTro(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String sort,
            @RequestParam(name = "type", required = false) String[] types,
            @RequestParam(required = false) Boolean wifi,
            @RequestParam(required = false) Boolean ac,
            @RequestParam(required = false) Boolean parking,
            @RequestParam(required = false) Boolean camera,
            @RequestParam(required = false) Boolean pet,
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        int pageSize = 6;
        int activePage = Math.max(1, page);

        // Sắp xếp theo giá phòng
        Sort sortCriteria = Sort.by("maNhaTro").descending();
        if ("price_asc".equals(sort)) {
            sortCriteria = Sort.by("giaPhong").ascending();
        } else if ("price_desc".equals(sort)) {
            sortCriteria = Sort.by("giaPhong").descending();
        }

        Pageable pageable = PageRequest.of(activePage - 1, pageSize, sortCriteria);

        // Lọc dữ liệu bằng Specification theo các tiêu chí trên
        Specification<NhaTro> spec = NhaTroSpecification.filterNhaTro(
                keyword, location, minPrice, maxPrice, types, wifi, ac, parking, camera, pet);

        Page<NhaTro> nhaTroPage = nhaTroRepository.findAll(spec, pageable);

        // Truyền dữ liệu sang JSP
        model.addAttribute("listNhaTro", nhaTroPage.getContent());
        model.addAttribute("rooms", nhaTroPage.getContent());
        model.addAttribute("repRoomId", buildRepRoomIdMap(nhaTroPage.getContent()));
        model.addAttribute("resultCount", nhaTroPage.getTotalElements());
        model.addAttribute("totalPages", nhaTroPage.getTotalPages());
        model.addAttribute("currentPage", activePage);
        model.addAttribute("pageTitle", "Thuê Phòng Trọ");

        // Giữ lại trạng thái bộ lọc trên form UI
        model.addAttribute("keyword", keyword);
        model.addAttribute("location", location);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("sort", sort);
        model.addAttribute("selectedTypes", types);
        model.addAttribute("wifi", wifi);
        model.addAttribute("ac", ac);
        model.addAttribute("parking", parking);
        model.addAttribute("camera", camera);
        model.addAttribute("pet", pet);

        return "home/ThueTro";
    }


    /*
     * ============================================================
     * TRANG THUÊ CĂN HỘ
     *
     * Cũng lấy từ DB thật (NHA_TRO / PHONG_TRO) như /thue-tro, chỉ khác
     * là chỉ lấy các nhà trọ có loại phòng thuộc nhóm "căn hộ" (Chung cư
     * mini, Studio) thay vì tất cả loại phòng.
     *
     * URL:
     * /thue-can-ho
     * ============================================================
     */
    @GetMapping("/thue-can-ho")
    @Transactional
    public String thueCanHo(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        int pageSize = 6;
        int activePage = Math.max(1, page);

        Pageable pageable = PageRequest.of(activePage - 1, pageSize, Sort.by("maNhaTro").descending());

        // Chỉ lấy các nhà trọ có loại phòng đại diện thuộc nhóm "căn hộ"
        // (khớp với nhóm từ khoá "chung cư"/"căn hộ mini"/"studio" mà
        // NhaTroSpecification.mapLoaiPhongKeywords đã định nghĩa sẵn cho
        // 2 checkbox loại phòng "Chung cư mini" và "Studio" bên /thue-tro).
        Specification<NhaTro> spec = NhaTroSpecification.filterNhaTro(
                keyword, location, minPrice, maxPrice,
                new String[]{"Chung cư mini", "Studio"},
                null, null, null, null, null);

        Page<NhaTro> nhaTroPage = nhaTroRepository.findAll(spec, pageable);

        model.addAttribute("listNhaTro", nhaTroPage.getContent());
        model.addAttribute("rooms", nhaTroPage.getContent());
        model.addAttribute("repRoomId", buildRepRoomIdMap(nhaTroPage.getContent()));
        model.addAttribute("resultCount", nhaTroPage.getTotalElements());
        model.addAttribute("totalPages", nhaTroPage.getTotalPages());
        model.addAttribute("currentPage", activePage);
        model.addAttribute("pageTitle", "Căn hộ tại TP. Hồ Chí Minh");

        return "home/ThueCanHo";
    }


    /*
     * ============================================================
     * CHI TIẾT PHÒNG
     *
     * URL:
     * /chi-tiet-phong?id=1   (id = maPhong, không phải maNhaTro)
     *
     * JSP không nhận dữ liệu qua Model - toàn bộ dữ liệu (thông tin
     * phòng, ảnh, tiện ích, chủ trọ, đánh giá, phòng tương tự...) do
     * JS phía client tự gọi GET /api/phong-tro/{id}/chi-tiet (dữ liệu
     * thật từ DB, xem PhongChiTietService) và render. Controller chỉ
     * forward id sang JSP để JS đọc lại qua URL.
     * ============================================================
     */
    @GetMapping("/chi-tiet-phong")
    public String chiTietPhong(
            @RequestParam(value = "id", defaultValue = "1") int id,
            Model model
    ) {
        model.addAttribute("roomId", id);
        return "home/ChiTietPhong";
    }


    /*
     * ============================================================
     * Với các trang danh sách hiển thị theo mức NHÀ TRỌ (mỗi card gộp
     * nhiều phòng lại thành 1 dòng, dùng giaPhong/loaiPhong/hinhAnh đại
     * diện lưu trực tiếp trên NHA_TRO), link "Xem chi tiết" phải trỏ
     * tới 1 PHÒNG TRỌ cụ thể (API /api/phong-tro/{id}/chi-tiet nhận
     * maPhong). Hàm này build Map<maNhaTro, maPhong đại diện> (phòng có
     * maPhong nhỏ nhất trong nhà trọ đó) để JSP dùng khi build link.
     * ============================================================
     */
    private Map<Integer, Integer> buildRepRoomIdMap(List<NhaTro> danhSachNhaTro) {
        Map<Integer, Integer> repRoomId = new HashMap<>();
        for (NhaTro nhaTro : danhSachNhaTro) {
            Integer maNhaTro = nhaTro.getMaNhaTro();
            Integer maPhong = phongTroRepository.findFirstByNhaTro_MaNhaTroOrderByMaPhongAsc(maNhaTro)
                    .map(PhongTro::getMaPhong)
                    .orElse(0);
            repRoomId.put(maNhaTro, maPhong);
        }
        return repRoomId;
    }
}