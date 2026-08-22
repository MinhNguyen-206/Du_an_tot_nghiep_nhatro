package com.nhatro.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
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
import com.nhatro.backend.model.Room;
import com.nhatro.backend.repository.NhaTroRepository;
import com.nhatro.backend.repository.specification.NhaTroSpecification;

import jakarta.transaction.Transactional;

@Controller
public class HomeController {

    private final NhaTroRepository nhaTroRepository;

    public HomeController(NhaTroRepository nhaTroRepository) {
        this.nhaTroRepository = nhaTroRepository;
    }

    /*
     * ============================================================
     * TRANG CHỦ
     * ============================================================
     */
    @GetMapping({"/home"})
    public String home() {
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

        // spring.jpa.open-in-view=false -> session Hibernate đóng ngay khi
        // hàm này return, TRƯỚC KHI JSP render. Nếu không ép load
        // danhSachTienIch (LAZY) ở đây, JSP sẽ dính LazyInitializationException
        // giữa chừng khi render -> response bị cắt cụt (ERR_INCOMPLETE_CHUNKED_ENCODING).
        nhaTroPage.getContent()
                .forEach(nt -> Hibernate.initialize(nt.getDanhSachTienIch()));

        // Truyền dữ liệu sang JSP
        model.addAttribute("listNhaTro", nhaTroPage.getContent());
        model.addAttribute("rooms", nhaTroPage.getContent());
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
     * URL:
     * /thue-can-ho
     * ============================================================
     */
    @GetMapping("/thue-can-ho")
    public String thueCanHo(Model model) {

        List<Room> apartments = getDemoApartments();

        model.addAttribute("rooms", apartments);
        model.addAttribute("pageTitle", "Căn hộ tại TP. Hồ Chí Minh");
        model.addAttribute("resultCount", apartments.size());

        return "home/ThueCanHo";
    }


    /*
     * ============================================================
     * CHI TIẾT PHÒNG
     *
     * URL:
     * /chi-tiet-phong?id=1
     *
     * Không có .jsp
     * ============================================================
     */
    @GetMapping("/chi-tiet-phong")
    public String chiTietPhong(
            @RequestParam(value = "id", defaultValue = "1") int id,
            Model model
    ) {

        List<Room> rooms = getDemoRooms();

        Room room = null;

        for (Room r : rooms) {
            if (r.getId() == id) {
                room = r;
                break;
            }
        }

        /*
         * Nếu không tìm thấy ID
         */
        if (room == null) {
            room = rooms.get(0);
        }

        model.addAttribute("room", room);
        model.addAttribute("similarRooms", rooms);

        return "home/ChiTietPhong";
    }


    /*
     * ============================================================
     * DỮ LIỆU DEMO PHÒNG TRỌ
     *
     * SAU NÀY:
     *
     * getDemoRooms()
     *
     * sẽ được thay bằng:
     *
     * roomDAO.findAll()
     *
     * ============================================================
     */
    private List<Room> getDemoRooms() {

        List<Room> rooms = new ArrayList<>();

        rooms.add(new Room(
                1,
                "Phòng Studio full nội thất đẹp thoáng mát gần trung tâm",
                "Chung cư mini",
                "Quận 1, TP. HCM",
                "7.000.000",
                "35 m²",
                "1 Tháng",
                "Sẵn sàng",
                "https://images.unsplash.com/photo-1616486338812-3dadae4b4ace?auto=format&fit=crop&w=1000&q=80",
                "Đoàn Quốc Đạt",
                "https://randomuser.me/api/portraits/men/32.jpg",
                "0939 *** ***",
                "Căn hộ studio có ban công thoáng mát, đón ánh sáng tự nhiên cực tốt. Phòng được trang bị sẵn giường nệm cao cấp, tủ quần áo âm tường và bếp từ hiện đại. Khu dân cư an ninh, có camera giám sát 24/7. Thích hợp cho nhân viên văn phòng hoặc sinh viên.",
                "3.500đ / kWh",
                "100.000đ / người",
                "150.000đ / phòng",
                "50.000đ / phòng / tháng",
                "Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh"
        ));

        rooms.add(new Room(
                2,
                "Phòng trọ cao cấp full nội thất gần Đại học FPT",
                "Phòng trọ",
                "Quận 12, TP. HCM",
                "4.500.000",
                "28 m²",
                "1 Tháng",
                "Sẵn sàng",
                "https://images.unsplash.com/photo-1600607687939-ce8a6c25118c?auto=format&fit=crop&w=1000&q=80",
                "Nguyễn Văn An",
                "https://randomuser.me/api/portraits/men/44.jpg",
                "0901 *** ***",
                "Phòng trọ rộng rãi, đầy đủ nội thất, giờ giấc tự do. Khu vực an ninh, gần trường học và nhiều cửa hàng tiện lợi.",
                "3.500đ / kWh",
                "100.000đ / người",
                "100.000đ / phòng",
                "50.000đ / phòng / tháng",
                "Quận 12, TP. Hồ Chí Minh"
        ));

        rooms.add(new Room(
                3,
                "Studio hiện đại có ban công, nội thất đầy đủ",
                "Studio",
                "Quận 3, TP. HCM",
                "6.500.000",
                "32 m²",
                "2 Tháng",
                "Sẵn sàng",
                "https://images.unsplash.com/photo-1600566753190-17f0baa2a6c3?auto=format&fit=crop&w=1000&q=80",
                "Trần Minh Đức",
                "https://randomuser.me/api/portraits/men/55.jpg",
                "0912 *** ***",
                "Studio thiết kế hiện đại, có ban công riêng, đầy đủ máy lạnh, máy giặt và bếp.",
                "3.500đ / kWh",
                "100.000đ / người",
                "150.000đ / phòng",
                "50.000đ / phòng / tháng",
                "Quận 3, TP. Hồ Chí Minh"
        ));

        rooms.add(new Room(
                4,
                "Phòng trọ gần chợ Bến Thành, an ninh 24/7",
                "Phòng trọ",
                "Quận 1, TP. HCM",
                "5.000.000",
                "30 m²",
                "1 Tháng",
                "Sẵn sàng",
                "https://images.unsplash.com/photo-1586023492125-27b2c045efd7?auto=format&fit=crop&w=1000&q=80",
                "Lê Quốc Huy",
                "https://randomuser.me/api/portraits/men/61.jpg",
                "0988 *** ***",
                "Phòng nằm ngay trung tâm Quận 1, thuận tiện đi làm và học tập.",
                "3.500đ / kWh",
                "100.000đ / người",
                "120.000đ / phòng",
                "50.000đ / phòng / tháng",
                "Quận 1, TP. Hồ Chí Minh"
        ));

        rooms.add(new Room(
                5,
                "Phòng full nội thất gần Đại học Quốc Gia",
                "Chung cư mini",
                "Thủ Đức, TP. HCM",
                "4.800.000",
                "30 m²",
                "1 Tháng",
                "Sẵn sàng",
                "https://images.unsplash.com/photo-1600210492486-724fe5c67fb0?auto=format&fit=crop&w=1000&q=80",
                "Phạm Hoàng Nam",
                "https://randomuser.me/api/portraits/men/72.jpg",
                "0977 *** ***",
                "Phòng sạch đẹp, gần Đại học Quốc Gia, phù hợp sinh viên và người đi làm.",
                "3.500đ / kWh",
                "80.000đ / người",
                "100.000đ / phòng",
                "50.000đ / phòng / tháng",
                "Thủ Đức, TP. Hồ Chí Minh"
        ));

        rooms.add(new Room(
                6,
                "Phòng cao cấp view đẹp, có thang máy và camera",
                "Chung cư mini",
                "Bình Thạnh, TP. HCM",
                "6.000.000",
                "38 m²",
                "1 Tháng",
                "Sẵn sàng",
                "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=1000&q=80",
                "Đặng Thành Công",
                "https://randomuser.me/api/portraits/men/80.jpg",
                "0966 *** ***",
                "Phòng cao cấp, có thang máy, camera và bãi xe. Khu vực dân cư yên tĩnh.",
                "3.500đ / kWh",
                "100.000đ / người",
                "150.000đ / phòng",
                "50.000đ / phòng / tháng",
                "Bình Thạnh, TP. Hồ Chí Minh"
        ));

        return rooms;
    }


    /*
     * ============================================================
     * DỮ LIỆU DEMO CĂN HỘ
     *
     * Sau này cũng lấy từ SQL.
     * ============================================================
     */
    private List<Room> getDemoApartments() {

        List<Room> apartments = new ArrayList<>();

        apartments.add(new Room(
                1,
                "Căn hộ Studio full nội thất cao cấp - View thành phố",
                "Chung cư cao cấp",
                "Quận 1, TP. HCM",
                "7.500.000",
                "35 m²",
                "2 Tháng",
                "Sẵn sàng",
                "https://images.unsplash.com/photo-1600607687939-ce8a6c25118c?auto=format&fit=crop&w=1000&q=80",
                "Đoàn Quốc Đạt",
                "https://randomuser.me/api/portraits/men/32.jpg",
                "0939 *** ***",
                "Căn hộ Studio cao cấp đầy đủ nội thất, view thành phố đẹp, có ban công và an ninh 24/7.",
                "3.500đ / kWh",
                "100.000đ / người",
                "200.000đ / phòng",
                "50.000đ / phòng / tháng",
                "Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh"
        ));

        apartments.add(new Room(
                2,
                "Căn hộ 1 phòng ngủ hiện đại gần trung tâm",
                "Căn hộ 1 PN",
                "Bình Thạnh, TP. HCM",
                "8.000.000",
                "45 m²",
                "2 Tháng",
                "Sẵn sàng",
                "https://images.unsplash.com/photo-1600210492486-724fe5c67fb0?auto=format&fit=crop&w=1000&q=80",
                "Nguyễn Văn Minh",
                "https://randomuser.me/api/portraits/men/33.jpg",
                "0908 *** ***",
                "Căn hộ 1 phòng ngủ, nội thất hiện đại, gần trung tâm.",
                "3.500đ / kWh",
                "100.000đ / người",
                "200.000đ / phòng",
                "50.000đ / phòng / tháng",
                "Bình Thạnh, TP. Hồ Chí Minh"
        ));

        apartments.add(new Room(
                3,
                "Căn hộ 2 phòng ngủ rộng rãi cho gia đình",
                "Căn hộ 2 PN",
                "Quận 7, TP. HCM",
                "11.000.000",
                "65 m²",
                "2 Tháng",
                "Sẵn sàng",
                "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=1000&q=80",
                "Trần Quốc Bảo",
                "https://randomuser.me/api/portraits/men/40.jpg",
                "0918 *** ***",
                "Căn hộ 2 phòng ngủ rộng rãi, phù hợp gia đình.",
                "3.500đ / kWh",
                "100.000đ / người",
                "250.000đ / phòng",
                "70.000đ / phòng / tháng",
                "Quận 7, TP. Hồ Chí Minh"
        ));

        return apartments;
    }
}