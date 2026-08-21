package com.nhatro.backend.controller;

import com.nhatro.backend.entity.*;
import com.nhatro.backend.repository.ThanhToanTienTroRepository;
import com.nhatro.backend.service.*;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/chu-tro")
public class ChuTroController {

    private static final String DEMO_LANDLORD_EMAIL =
            "chutro1@nhatro.vn";

    // =========================================================
    // SERVICE
    // =========================================================

    private final NguoiDungService nguoiDungService;
    private final NhaTroService nhaTroService;
    private final PhongTroService phongTroService;
    private final HopDongDienTuService hopDongService;
    private final HoaDonThangService hoaDonService;
    private final ThanhToanTienTroRepository thanhToanRepository;
    private final YeuCauThueService yeuCauService;
    private final ChiSoDienNuocService chiSoService;
    private final DanhGiaService danhGiaService;
    private final LichHenService lichHenService;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ChuTroController(
            NguoiDungService nguoiDungService,
            NhaTroService nhaTroService,
            PhongTroService phongTroService,
            HopDongDienTuService hopDongService,
            HoaDonThangService hoaDonService,
            ThanhToanTienTroRepository thanhToanRepository,
            YeuCauThueService yeuCauService,
            ChiSoDienNuocService chiSoService,
            DanhGiaService danhGiaService,
            LichHenService lichHenService
    ) {

        this.nguoiDungService = nguoiDungService;
        this.nhaTroService = nhaTroService;
        this.phongTroService = phongTroService;
        this.hopDongService = hopDongService;
        this.hoaDonService = hoaDonService;
        this.thanhToanRepository = thanhToanRepository;
        this.yeuCauService = yeuCauService;
        this.chiSoService = chiSoService;
        this.danhGiaService = danhGiaService;
        this.lichHenService = lichHenService;
    }


    // =========================================================
    // DASHBOARD
    // =========================================================

    @Transactional
    @GetMapping({"", "/", "/dashboard"})
    public String dashboard(
            Model model,
            HttpSession session
    ) {

        NguoiDung landlord =
                currentLandlord(session);

        DashboardData data =
                buildDashboard(landlord);

        model.addAttribute(
                "userName",
                landlord.getHoTen()
        );

        model.addAttribute(
                "userEmail",
                landlord.getEmail()
        );

        model.addAttribute(
                "totalRooms",
                data.totalRooms()
        );

        model.addAttribute(
                "occupiedRooms",
                data.occupiedRooms()
        );

        model.addAttribute(
                "availableRooms",
                data.availableRooms()
        );

        model.addAttribute(
                "pendingRooms",
                data.pendingRooms()
        );

        model.addAttribute(
                "occupancyPercent",
                data.occupancyPercent()
        );

        model.addAttribute(
                "monthlyRevenue",
                moneyShort(
                        data.monthlyRevenue()
                )
        );

        model.addAttribute(
                "revenueGrowth",
                data.revenueGrowth()
        );

        model.addAttribute(
                "revenueChart",
                data.revenueChart()
        );

        model.addAttribute(
                "pendingRequests",
                data.pendingRequests()
        );

        model.addAttribute(
                "unpaidInvoices",
                data.unpaidInvoices()
        );

        model.addAttribute(
                "missingMeters",
                data.missingMeters()
        );

        model.addAttribute(
                "newReviews",
                data.newReviews()
        );

        return "chu-tro/dashboard";
    }


    // =========================================================
    // DANH SÁCH NHÀ TRỌ
    // =========================================================

    @Transactional
    @GetMapping("/properties")
    public String properties(
            Model model,
            HttpSession session
    ) {

        NguoiDung landlord =
                currentLandlord(session);

        List<NhaTro> properties =
                nhaTroService.getByNguoiDung(
                        landlord.getMaNguoiDung()
                );

        List<Map<String, Object>> cards =
                new ArrayList<>();

        int cover = 1;

        int totalRooms = 0;

        int occupied = 0;


        // -----------------------------------------------------
        // Lấy hợp đồng
        // -----------------------------------------------------

        List<HopDongDienTu> contracts =
                hopDongService.getByChuTro(
                        landlord.getMaNguoiDung()
                );


        Set<Integer> occupiedIds =
                contracts.stream()
                        .filter(
                                this::isActiveContract
                        )
                        .filter(
                                h -> h.getPhong() != null
                        )
                        .map(
                                h -> h.getPhong()
                                        .getMaPhong()
                        )
                        .collect(
                                Collectors.toSet()
                        );


        // -----------------------------------------------------
        // Duyệt nhà trọ
        // -----------------------------------------------------

        for (NhaTro property : properties) {

            List<PhongTro> rooms =
                    phongTroService.getByNhaTro(
                            property.getMaNhaTro()
                    );


            long occupiedCount =
                    rooms.stream()
                            .filter(
                                    p -> occupiedIds.contains(
                                            p.getMaPhong()
                                    )
                            )
                            .count();


            totalRooms +=
                    rooms.size();

            occupied +=
                    occupiedCount;


            Map<String, Object> card =
                    new LinkedHashMap<>();


            // -------------------------------------------------
            // Thông tin nhà trọ
            // -------------------------------------------------

            card.put(
                    "id",
                    property.getMaNhaTro()
            );

            card.put(
                    "name",
                    property.getTenNhaTro()
            );

            card.put(
                    "address",
                    property.getDiaChi()
            );

            card.put(
                    "rating",
                    property.getSoSao() == null
                            ? "0.0"
                            : property.getSoSao()
                            .toPlainString()
            );

            card.put(
                    "description",
                    property.getMoTa()
            );


            // -------------------------------------------------
            // GIÁ PHÒNG
            // -------------------------------------------------

            card.put(
                    "giaPhong",
                    property.getGiaPhong()
            );

            // Alias đúng với properties.jsp
            card.put(
                    "price",
                    property.getGiaPhong()
            );


            // -------------------------------------------------
            // LOẠI PHÒNG
            // -------------------------------------------------

            card.put(
                    "loaiPhong",
                    property.getLoaiPhong()
            );

            // Alias đúng với properties.jsp
            card.put(
                    "roomType",
                    property.getLoaiPhong()
            );


            // -------------------------------------------------
            // LINK ẢNH
            // -------------------------------------------------

            card.put(
                    "hinhAnh",
                    property.getHinhAnh()
            );

            // Alias đúng với properties.jsp
            card.put(
                    "image",
                    property.getHinhAnh()
            );


            // -------------------------------------------------
            // PHÒNG
            // -------------------------------------------------

            card.put(
                    "roomCount",
                    rooms.size()
            );

            card.put(
                    "occupiedCount",
                    occupiedCount
            );

            card.put(
                    "availableCount",
                    rooms.size()
                            - occupiedCount
            );


            // -------------------------------------------------
            // CSS cover
            // -------------------------------------------------

            String[] covers = {
                    "one",
                    "two",
                    "three",
                    "four"
            };

            card.put(
                    "coverClass",
                    covers[
                            Math.min(
                                    cover - 1,
                                    covers.length - 1
                            )
                            ]
            );


            card.put(
                    "status",
                    "active"
            );


            cards.add(card);

            cover++;
        }


        // -----------------------------------------------------
        // Model
        // -----------------------------------------------------

        model.addAttribute(
                "userName",
                landlord.getHoTen()
        );

        model.addAttribute(
                "userEmail",
                landlord.getEmail()
        );

        model.addAttribute(
                "propertyCards",
                cards
        );

        model.addAttribute(
                "propertyCount",
                cards.size()
        );

        model.addAttribute(
                "propertyRoomCount",
                totalRooms
        );

        model.addAttribute(
                "propertyOccupiedCount",
                occupied
        );

        model.addAttribute(
                "propertyAvailableCount",
                Math.max(
                        0,
                        totalRooms - occupied
                )
        );


        return "chu-tro/properties";
    }


    // =========================================================
    // FORM THÊM NHÀ TRỌ
    // =========================================================

    @GetMapping("/properties/add")
    public String addProperty(
            Model model,
            HttpSession session
    ) {

        NguoiDung landlord =
                currentLandlord(session);

        model.addAttribute(
                "userName",
                landlord.getHoTen()
        );

        model.addAttribute(
                "userEmail",
                landlord.getEmail()
        );

        model.addAttribute(
                "formTitle",
                "Thêm nhà trọ"
        );

        model.addAttribute(
                "nhaTro",
                new NhaTro()
        );

        return "chu-tro/property-form";
    }


    // =========================================================
    // FORM SỬA NHÀ TRỌ
    // =========================================================

    @GetMapping("/properties/edit/{id}")
    public String editProperty(
            @PathVariable Integer id,
            Model model,
            HttpSession session
    ) {

        NguoiDung landlord =
                currentLandlord(session);


        NhaTro nhaTro =
                nhaTroService.getById(id)
                        .orElse(null);


        if (nhaTro == null) {

            return "redirect:/chu-tro/properties";
        }


        // -----------------------------------------------------
        // Kiểm tra quyền sở hữu
        // -----------------------------------------------------

        if (nhaTro.getNguoiDung() == null
                || nhaTro.getNguoiDung()
                .getMaNguoiDung() == null
                || !landlord.getMaNguoiDung()
                .equals(
                        nhaTro.getNguoiDung()
                                .getMaNguoiDung()
                )) {

            return "redirect:/chu-tro/properties";
        }


        model.addAttribute(
                "userName",
                landlord.getHoTen()
        );

        model.addAttribute(
                "userEmail",
                landlord.getEmail()
        );

        model.addAttribute(
                "formTitle",
                "Sửa nhà trọ"
        );

        model.addAttribute(
                "nhaTro",
                nhaTro
        );


        return "chu-tro/property-form";
    }


    // =========================================================
    // THÊM / SỬA NHÀ TRỌ
    // =========================================================

    @PostMapping("/properties/save")
    public String saveProperty(

            @RequestParam(
                    value = "maNhaTro",
                    required = false
            )
            Integer maNhaTro,

            @RequestParam("tenNhaTro")
            String tenNhaTro,

            @RequestParam("diaChi")
            String diaChi,

            @RequestParam(
                    value = "soSao",
                    required = false
            )
            BigDecimal soSao,

            @RequestParam("giaPhong")
            BigDecimal giaPhong,

            @RequestParam("loaiPhong")
            String loaiPhong,

            @RequestParam(
                    value = "moTa",
                    required = false
            )
            String moTa,

            @RequestParam(
                    value = "hinhAnh",
                    required = false
            )
            String hinhAnh,

            HttpSession session
    ) {

        NguoiDung landlord =
                currentLandlord(session);


        // -----------------------------------------------------
        // Tạo Request
        //
        // CHỈ DÙNG NhaTroService.NhaTroRequest
        // KHÔNG CÓ NhaTroRequestData
        // -----------------------------------------------------

        NhaTroService.NhaTroRequest request =
                new NhaTroService.NhaTroRequest(

                        tenNhaTro,

                        diaChi,

                        soSao,

                        giaPhong,

                        loaiPhong,

                        moTa,

                        hinhAnh
                );


        // -----------------------------------------------------
        // THÊM
        // -----------------------------------------------------

        if (maNhaTro == null) {

            nhaTroService.createForOwner(
                    request,
                    landlord
            );

        }

        // -----------------------------------------------------
        // SỬA
        // -----------------------------------------------------

        else {

            nhaTroService.updateForOwner(
                    maNhaTro,
                    request,
                    landlord
            );
        }


        return "redirect:/chu-tro/properties";
    }


    // =========================================================
    // XÓA NHÀ TRỌ
    // =========================================================

    @GetMapping("/properties/delete/{id}")
    public String deleteProperty(
            @PathVariable Integer id,
            HttpSession session
    ) {

        NguoiDung landlord =
                currentLandlord(session);


        try {

            nhaTroService.deleteForOwner(
                    id,
                    landlord
            );

        } catch (IllegalStateException e) {

            // Có phòng nên không thể xóa.
            // Quay lại danh sách.
            return "redirect:/chu-tro/properties";

        } catch (IllegalArgumentException e) {

            return "redirect:/chu-tro/properties";
        }


        return "redirect:/chu-tro/properties";
    }


    // =========================================================
    // BUILD DASHBOARD
    // =========================================================

    private DashboardData buildDashboard(
            NguoiDung landlord
    ) {

        Integer landlordId =
                landlord.getMaNguoiDung();


        // -----------------------------------------------------
        // Nhà trọ
        // -----------------------------------------------------

        List<NhaTro> properties =
                nhaTroService.getByNguoiDung(
                        landlordId
                );


        // -----------------------------------------------------
        // Phòng
        // -----------------------------------------------------

        List<PhongTro> rooms =
                properties.stream()
                        .flatMap(
                                nt ->
                                        phongTroService
                                                .getByNhaTro(
                                                        nt.getMaNhaTro()
                                                )
                                                .stream()
                        )
                        .toList();


        Set<Integer> roomIds =
                rooms.stream()
                        .map(
                                PhongTro::getMaPhong
                        )
                        .collect(
                                Collectors.toSet()
                        );


        // -----------------------------------------------------
        // Hợp đồng
        // -----------------------------------------------------

        List<HopDongDienTu> contracts =
                hopDongService.getByChuTro(
                        landlordId
                );


        Set<Integer> occupiedIds =
                contracts.stream()
                        .filter(
                                this::isActiveContract
                        )
                        .filter(
                                h -> h.getPhong() != null
                        )
                        .map(
                                h ->
                                        h.getPhong()
                                                .getMaPhong()
                        )
                        .collect(
                                Collectors.toSet()
                        );


        Set<Integer> pendingIds =
                contracts.stream()
                        .filter(
                                this::isPendingContract
                        )
                        .filter(
                                h -> h.getPhong() != null
                        )
                        .map(
                                h ->
                                        h.getPhong()
                                                .getMaPhong()
                        )
                        .collect(
                                Collectors.toSet()
                        );


        int totalRooms =
                rooms.size();


        int occupied =
                occupiedIds.size();


        int available =
                Math.max(
                        0,
                        totalRooms - occupied
                );


        int pending =
                pendingIds.size();


        int occupancy =
                totalRooms == 0
                        ? 0
                        : Math.round(
                        occupied * 100f
                                / totalRooms
                );


        // -----------------------------------------------------
        // Hóa đơn
        // -----------------------------------------------------

        List<HoaDonThang> invoices =
                hoaDonService.getAll()
                        .stream()
                        .filter(
                                hd ->
                                        hd.getHopDong() != null
                        )
                        .filter(
                                hd ->
                                        hd.getHopDong()
                                                .getChuTro() != null
                        )
                        .filter(
                                hd ->
                                        landlordId.equals(
                                                hd.getHopDong()
                                                        .getChuTro()
                                                        .getMaNguoiDung()
                                        )
                        )
                        .toList();


        // -----------------------------------------------------
        // Thanh toán
        // -----------------------------------------------------

        List<ThanhToanTienTro> payments =
                thanhToanRepository.findAll()
                        .stream()
                        .filter(
                                tt ->
                                        tt.getHoaDon() != null
                        )
                        .filter(
                                tt ->
                                        invoices.stream()
                                                .anyMatch(
                                                        hd ->
                                                                hd.getMaHoaDon()
                                                                        .equals(
                                                                                tt.getHoaDon()
                                                                                        .getMaHoaDon()
                                                                        )
                                                )
                        )
                        .toList();


        // -----------------------------------------------------
        // Doanh thu tháng
        // -----------------------------------------------------

        LocalDate today =
                LocalDate.now();


        LocalDate firstDay =
                today.withDayOfMonth(1);


        BigDecimal monthlyRevenue =
                payments.stream()
                        .filter(
                                this::isPaid
                        )
                        .filter(
                                tt ->
                                        tt.getNgayThanhToan() != null
                                                &&
                                                !tt.getNgayThanhToan()
                                                        .toLocalDate()
                                                        .isBefore(
                                                                firstDay
                                                        )
                        )
                        .map(
                                ThanhToanTienTro::getSoTien
                        )
                        .filter(
                                Objects::nonNull
                        )
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );


        // -----------------------------------------------------
        // Chart 7 ngày
        // -----------------------------------------------------

        Map<LocalDate, BigDecimal> dailyRevenue =
                new LinkedHashMap<>();


        for (int i = 6; i >= 0; i--) {

            dailyRevenue.put(
                    today.minusDays(i),
                    BigDecimal.ZERO
            );
        }


        payments.stream()
                .filter(
                        this::isPaid
                )
                .forEach(
                        tt -> {

                            if (tt.getNgayThanhToan() == null
                                    || tt.getSoTien() == null) {

                                return;
                            }


                            LocalDate day =
                                    tt.getNgayThanhToan()
                                            .toLocalDate();


                            if (dailyRevenue.containsKey(day)) {

                                dailyRevenue.put(
                                        day,
                                        dailyRevenue
                                                .get(day)
                                                .add(
                                                        tt.getSoTien()
                                                )
                                );
                            }
                        }
                );


        BigDecimal max =
                dailyRevenue.values()
                        .stream()
                        .max(
                                BigDecimal::compareTo
                        )
                        .orElse(
                                BigDecimal.ONE
                        );


        List<Map<String, String>> chart =
                new ArrayList<>();


        for (
                Map.Entry<LocalDate, BigDecimal> entry :
                dailyRevenue.entrySet()
        ) {

            Map<String, String> point =
                    new LinkedHashMap<>();


            point.put(
                    "label",
                    entry.getKey()
                            .getDayOfWeek()
                            .getDisplayName(
                                    TextStyle.SHORT,
                                    Locale.forLanguageTag(
                                            "vi-VN"
                                    )
                            )
            );


            point.put(
                    "amount",
                    moneyShort(
                            entry.getValue()
                    )
            );


            int height;


            if (
                    entry.getValue()
                            .compareTo(
                                    BigDecimal.ZERO
                            ) == 0
            ) {

                height = 5;

            } else {

                height =
                        entry.getValue()
                                .multiply(
                                        BigDecimal.valueOf(100)
                                )
                                .divide(
                                        max,
                                        0,
                                        java.math.RoundingMode.HALF_UP
                                )
                                .intValue();

                height =
                        Math.max(
                                12,
                                height
                        );
            }


            point.put(
                    "height",
                    String.valueOf(
                            Math.min(
                                    height,
                                    100
                            )
                    )
            );


            chart.add(point);
        }


        // -----------------------------------------------------
        // Yêu cầu thuê
        // -----------------------------------------------------

        long pendingRequests =
                yeuCauService.getAll()
                        .stream()
                        .filter(
                                y ->
                                        y.getPhong() != null
                        )
                        .filter(
                                y ->
                                        roomIds.contains(
                                                y.getPhong()
                                                        .getMaPhong()
                                        )
                        )
                        .filter(
                                y ->
                                        "Chờ duyệt"
                                                .equalsIgnoreCase(
                                                        y.getTrangThai()
                                                )
                        )
                        .count();


        // -----------------------------------------------------
        // Hóa đơn chưa thanh toán
        // -----------------------------------------------------

        long unpaidInvoices =
                invoices.stream()
                        .filter(
                                hd ->
                                        !isInvoicePaid(hd)
                        )
                        .count();


        // -----------------------------------------------------
        // Chỉ số điện nước
        // -----------------------------------------------------

        int currentMonth =
                today.getMonthValue();

        int currentYear =
                today.getYear();


        long missingMeters =
                occupiedIds.stream()
                        .filter(
                                roomId ->
                                        chiSoService
                                                .getByPhong(roomId)
                                                .stream()
                                                .noneMatch(
                                                        cs ->
                                                                currentMonth
                                                                        == cs.getThang()
                                                                        &&
                                                                        currentYear
                                                                                == cs.getNam()
                                                )
                        )
                        .count();


        // -----------------------------------------------------
        // Đánh giá mới
        // -----------------------------------------------------

        LocalDateTime since =
                LocalDateTime.now()
                        .minusDays(7);


        long newReviews =
                danhGiaService.getAll()
                        .stream()
                        .filter(
                                d ->
                                        d.getPhong() != null
                        )
                        .filter(
                                d ->
                                        roomIds.contains(
                                                d.getPhong()
                                                        .getMaPhong()
                                        )
                        )
                        .filter(
                                d ->
                                        d.getNgayDanhGia() != null
                        )
                        .filter(
                                d ->
                                        d.getNgayDanhGia()
                                                .isAfter(
                                                        since
                                                )
                        )
                        .count();


        return new DashboardData(

                totalRooms,

                occupied,

                available,

                pending,

                occupancy,

                monthlyRevenue,

                "+ dữ liệu thật",

                chart,

                pendingRequests,

                unpaidInvoices,

                missingMeters,

                newReviews
        );
    }


    // =========================================================
    // USER HIỆN TẠI
    // =========================================================

    private NguoiDung currentLandlord(
            HttpSession session
    ) {

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        String email =
                auth != null
                        && auth.isAuthenticated()
                        && auth.getName() != null
                        && !"anonymousUser".equals(
                        auth.getName()
                )
                        ? auth.getName()
                        : (String) session.getAttribute(
                        "landlordEmail"
                );


        if (
                email == null
                        || email.isBlank()
        ) {

            email =
                    DEMO_LANDLORD_EMAIL;
        }


        NguoiDung user =
                nguoiDungService
                        .getByEmail(email)
                        .orElse(null);


        if (
                user == null
                        || user.getVaiTro() == null
                        || user.getVaiTro()
                        .getMaVaiTro() != 2
        ) {

            user =
                    nguoiDungService
                            .getByEmail(
                                    DEMO_LANDLORD_EMAIL
                            )
                            .orElseThrow(
                                    () ->
                                            new IllegalStateException(
                                                    "Chưa có tài khoản chủ trọ mẫu: "
                                                            + DEMO_LANDLORD_EMAIL
                                            )
                            );
        }


        session.setAttribute(
                "landlordEmail",
                user.getEmail()
        );


        return user;
    }


    // =========================================================
    // KIỂM TRA HỢP ĐỒNG
    // =========================================================

    private boolean isActiveContract(
            HopDongDienTu h
    ) {

        if (h == null) {
            return false;
        }


        String status =
                h.getTrangThai();


        return
                "Đang hiệu lực"
                        .equalsIgnoreCase(status)

                        ||

                        "Đang thuê"
                                .equalsIgnoreCase(status);
    }


    private boolean isPendingContract(
            HopDongDienTu h
    ) {

        if (h == null) {
            return false;
        }


        String status =
                h.getTrangThai();


        return
                "Chờ ký"
                        .equalsIgnoreCase(status)

                        ||

                        "Đang chờ ký"
                                .equalsIgnoreCase(status);
    }


    // =========================================================
    // KIỂM TRA THANH TOÁN
    // =========================================================

    private boolean isPaid(
            ThanhToanTienTro tt
    ) {

        if (tt == null) {
            return false;
        }


        String status =
                tt.getTrangThai();


        return
                "Đã thanh toán"
                        .equalsIgnoreCase(status)

                        ||

                        "PAID"
                                .equalsIgnoreCase(status);
    }


    private boolean isInvoicePaid(
            HoaDonThang hd
    ) {

        if (hd == null) {
            return false;
        }


        String status =
                hd.getTrangThai();


        return
                "Đã thanh toán"
                        .equalsIgnoreCase(status)

                        ||

                        "PAID"
                                .equalsIgnoreCase(status);
    }


    // =========================================================
    // FORMAT TIỀN
    // =========================================================

    private String moneyShort(
            BigDecimal value
    ) {

        if (value == null) {
            return "0đ";
        }


        long n =
                value.longValue();


        if (n >= 1_000_000) {

            return String.format(
                    Locale.US,
                    "%.1fM",
                    n / 1_000_000.0
            );
        }


        if (n >= 1_000) {

            return String.format(
                    Locale.US,
                    "%.0fK",
                    n / 1_000.0
            );
        }


        return NumberFormat
                .getInstance(
                        new Locale(
                                "vi",
                                "VN"
                        )
                )
                .format(n)
                + "đ";
    }


    // =========================================================
    // CÁC ROUTE KHÁC
    // =========================================================

    @GetMapping("/rooms")
    public String rooms(
            Model model,
            HttpSession session
    ) {

        model.addAttribute(
                "userName",
                currentLandlord(session)
                        .getHoTen()
        );

        return "chu-tro/rooms";
    }


    @GetMapping("/posts")
    public String posts(
            Model model,
            HttpSession session
    ) {

        model.addAttribute(
                "userName",
                currentLandlord(session)
                        .getHoTen()
        );

        return "chu-tro/posts";
    }


    @GetMapping("/rental-requests")
    public String rentalRequests(
            Model model,
            HttpSession session
    ) {

        model.addAttribute(
                "userName",
                currentLandlord(session)
                        .getHoTen()
        );

        return "chu-tro/rentalRequests";
    }


    @GetMapping("/contracts")
    public String contracts(
            Model model,
            HttpSession session
    ) {

        model.addAttribute(
                "userName",
                currentLandlord(session)
                        .getHoTen()
        );

        return "chu-tro/contracts";
    }


    @GetMapping("/invoices")
    public String invoices(
            Model model,
            HttpSession session
    ) {

        model.addAttribute(
                "userName",
                currentLandlord(session)
                        .getHoTen()
        );

        return "chu-tro/invoices";
    }


    @GetMapping("/meters")
    public String meters(
            Model model,
            HttpSession session
    ) {

        model.addAttribute(
                "userName",
                currentLandlord(session)
                        .getHoTen()
        );

        return "chu-tro/meters";
    }


    @GetMapping("/revenue")
    public String revenue(
            Model model,
            HttpSession session
    ) {

        model.addAttribute(
                "userName",
                currentLandlord(session)
                        .getHoTen()
        );

        return "chu-tro/revenue";
    }


    @GetMapping("/appointments")
    public String appointments(
            Model model,
            HttpSession session
    ) {

        model.addAttribute(
                "userName",
                currentLandlord(session)
                        .getHoTen()
        );

        return "chu-tro/appointments";
    }


    @GetMapping("/reviews")
    public String reviews(
            Model model,
            HttpSession session
    ) {

        model.addAttribute(
                "userName",
                currentLandlord(session)
                        .getHoTen()
        );

        return "chu-tro/reviews";
    }


    @GetMapping("/notifications")
    public String notifications(
            Model model,
            HttpSession session
    ) {

        model.addAttribute(
                "userName",
                currentLandlord(session)
                        .getHoTen()
        );

        return "chu-tro/notifications";
    }


    @GetMapping("/profile")
    public String profile(
            Model model,
            HttpSession session
    ) {

        model.addAttribute(
                "userName",
                currentLandlord(session)
                        .getHoTen()
        );

        return "chu-tro/profile";
    }


    // =========================================================
    // DASHBOARD RECORD
    // =========================================================

    private record DashboardData(

            int totalRooms,

            int occupiedRooms,

            int availableRooms,

            int pendingRooms,

            int occupancyPercent,

            BigDecimal monthlyRevenue,

            String revenueGrowth,

            List<Map<String, String>> revenueChart,

            long pendingRequests,

            long unpaidInvoices,

            long missingMeters,

            long newReviews

    ) {
    }
}