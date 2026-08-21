package com.nhatro.backend.controller;

import com.nhatro.backend.service.BaoCaoService;
import com.nhatro.backend.service.BoDieuKhienAiService;
import com.nhatro.backend.service.DangTinService;
import com.nhatro.backend.service.GiaoDichThanhToanService;
import com.nhatro.backend.service.HopDongDienTuService;
import com.nhatro.backend.service.LichHenService;
import com.nhatro.backend.service.NguoiDungService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/management")
public class AdminManagementApiController {

    private final NguoiDungService nguoiDungService;
    private final DangTinService dangTinService;
    private final LichHenService lichHenService;
    private final HopDongDienTuService hopDongDienTuService;
    private final GiaoDichThanhToanService giaoDichThanhToanService;
    private final BaoCaoService baoCaoService;
    private final BoDieuKhienAiService boDieuKhienAiService;

    public AdminManagementApiController(NguoiDungService nguoiDungService,
                                        DangTinService dangTinService,
                                        LichHenService lichHenService,
                                        HopDongDienTuService hopDongDienTuService,
                                        GiaoDichThanhToanService giaoDichThanhToanService,
                                        BaoCaoService baoCaoService,
                                        BoDieuKhienAiService boDieuKhienAiService) {
        this.nguoiDungService = nguoiDungService;
        this.dangTinService = dangTinService;
        this.lichHenService = lichHenService;
        this.hopDongDienTuService = hopDongDienTuService;
        this.giaoDichThanhToanService = giaoDichThanhToanService;
        this.baoCaoService = baoCaoService;
        this.boDieuKhienAiService = boDieuKhienAiService;
    }

    @GetMapping("/users")
    @Transactional(readOnly = true)
    public List<Map<String, Object>> users() {
        return nguoiDungService.getAll().stream().map(user -> row(
                "maNguoiDung", user.getMaNguoiDung(), "email", user.getEmail(),
                "hoTen", user.getHoTen(), "soDienThoai", user.getSoDienThoai(),
                "trangThai", user.getTrangThai())).toList();
    }

    @GetMapping("/posts")
    @Transactional(readOnly = true)
    public List<Map<String, Object>> posts() {
        return dangTinService.getAll().stream().map(post -> row(
                "maDangTin", post.getMaDangTin(), "tieuDe", post.getTieuDe(),
                "ngayDang", post.getNgayDang(), "trangThai", post.getTrangThai())).toList();
    }

    @GetMapping("/appointments")
    @Transactional(readOnly = true)
    public List<Map<String, Object>> appointments() {
        return lichHenService.getAll().stream().map(appointment -> row(
                "maLichHen", appointment.getMaLichHen(), "ngayHen", appointment.getNgayHen(),
                "gioHen", appointment.getGioHen(), "diaDiem", appointment.getDiaDiem(),
                "trangThai", appointment.getTrangThai())).toList();
    }

    @GetMapping("/contracts")
    @Transactional(readOnly = true)
    public List<Map<String, Object>> contracts() {
        return hopDongDienTuService.getAll().stream().map(contract -> row(
                "maHopDong", contract.getMaHopDong(), "ngayBatDau", contract.getNgayBatDau(),
                "ngayKetThuc", contract.getNgayKetThuc(), "giaThue", contract.getGiaThue(),
                "trangThai", contract.getTrangThai())).toList();
    }

    @GetMapping("/payments")
    @Transactional(readOnly = true)
    public List<Map<String, Object>> payments() {
        return giaoDichThanhToanService.getAll().stream().map(payment -> row(
                "maGiaoDich", payment.getMaGiaoDich(), "nganHang", payment.getNganHang(),
                "ngayGiaoDich", payment.getNgayGiaoDich(), "trangThai", payment.getTrangThai())).toList();
    }

    @GetMapping("/reports")
    @Transactional(readOnly = true)
    public List<Map<String, Object>> reports() {
        return baoCaoService.getAll().stream().map(report -> row(
                "maBaoCao", report.getMaBaoCao(), "lyDo", report.getLyDo(),
                "ngayBaoCao", report.getNgayBaoCao(), "trangThai", report.getTrangThai())).toList();
    }

    @GetMapping("/settings")
    @Transactional(readOnly = true)
    public List<Map<String, Object>> settings() {
        return boDieuKhienAiService.getAll().stream().map(setting -> row(
                "maAI", setting.getMaAI(), "tenMoHinh", setting.getTenMoHinh(),
                "phienBan", setting.getPhienBan(), "trangThai", setting.getTrangThai(),
                "ngayCapNhat", setting.getNgayCapNhat())).toList();
    }

    private Map<String, Object> row(Object... values) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (int index = 0; index < values.length; index += 2) {
            result.put((String) values[index], values[index + 1]);
        }
        return result;
    }
}
