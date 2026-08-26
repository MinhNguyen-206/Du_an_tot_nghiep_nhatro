package com.nhatro.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nhatro.backend.entity.HopDongDienTu;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.YeuCauThue;
import com.nhatro.backend.repository.NguoiDungRepository;
import com.nhatro.backend.service.HopDongDienTuService;
import com.nhatro.backend.service.OtpService;
import com.nhatro.backend.service.YeuCauThueService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Hợp đồng điện tử", description = "Quản lý hợp đồng thuê nhà")
@RestController
@RequestMapping("/api/hop-dong")
public class HopDongDienTuController {

    private final HopDongDienTuService hopDongDienTuService;
    private final NguoiDungRepository nguoiDungRepository;
    private final YeuCauThueService yeuCauThueService;
    private final OtpService otpService;

    public HopDongDienTuController(HopDongDienTuService hopDongDienTuService, NguoiDungRepository nguoiDungRepository,
                                    YeuCauThueService yeuCauThueService, OtpService otpService) {
        this.hopDongDienTuService = hopDongDienTuService;
        this.nguoiDungRepository = nguoiDungRepository;
        this.yeuCauThueService = yeuCauThueService;
        this.otpService = otpService;
    }

    private NguoiDung currentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return nguoiDungRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

    @GetMapping("/theo-yeu-cau/{maYeuCau}")
    public ResponseEntity<HopDongDienTu> theoYeuCau(@PathVariable Integer maYeuCau) {
        return hopDongDienTuService.getByYeuCauThue(maYeuCau)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/tu-yeu-cau/{maYeuCau}")
    public ResponseEntity<HopDongDienTu> taoTuYeuCau(@PathVariable Integer maYeuCau,
                                                       @RequestBody HopDongDienTu duLieuHopDong,
                                                       Authentication authentication) {
        NguoiDung nguoiDungHienTai = currentUser(authentication);
        YeuCauThue yeuCau = yeuCauThueService.getById(maYeuCau)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy yêu cầu thuê"));

        Integer maChuPhong = yeuCau.getPhong().getNhaTro().getNguoiDung().getMaNguoiDung();
        if (!maChuPhong.equals(nguoiDungHienTai.getMaNguoiDung())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không phải chủ trọ của phòng này");
        }
        if (!"Đã duyệt".equals(yeuCau.getTrangThai())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Yêu cầu thuê chưa được duyệt");
        }
        if (hopDongDienTuService.getByYeuCauThue(maYeuCau).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hợp đồng đã được tạo cho yêu cầu này");
        }

        HopDongDienTu hopDong = hopDongDienTuService.taoTuYeuCau(yeuCau, duLieuHopDong);
        return ResponseEntity.ok(hopDong);
    }

    public static class KyHopDongRequest {
        public String chuKy;
        public String maOtp;
    }

    @PutMapping("/{id}/ky-chu-tro")
    public ResponseEntity<HopDongDienTu> kyChuTro(@PathVariable Integer id, @RequestBody KyHopDongRequest req,
                                                    Authentication authentication) {
        NguoiDung nguoiDungHienTai = currentUser(authentication);
        HopDongDienTu hopDong = hopDongDienTuService.getByIdWithNguoiDung(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!hopDong.getChuTro().getMaNguoiDung().equals(nguoiDungHienTai.getMaNguoiDung())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!otpService.xacThuc(id, "CHU_TRO", nguoiDungHienTai.getMaNguoiDung(), req.maOtp)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mã OTP không đúng hoặc đã hết hạn");
        }
        return ResponseEntity.ok(hopDongDienTuService.kyChuTro(hopDong, req.chuKy));
    }

    @PutMapping("/{id}/ky-nguoi-thue")
    public ResponseEntity<HopDongDienTu> kyNguoiThue(@PathVariable Integer id, @RequestBody KyHopDongRequest req,
                                                       Authentication authentication) {
        NguoiDung nguoiDungHienTai = currentUser(authentication);
        HopDongDienTu hopDong = hopDongDienTuService.getByIdWithNguoiDung(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!hopDong.getNguoiThue().getMaNguoiDung().equals(nguoiDungHienTai.getMaNguoiDung())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!otpService.xacThuc(id, "NGUOI_THUE", nguoiDungHienTai.getMaNguoiDung(), req.maOtp)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mã OTP không đúng hoặc đã hết hạn");
        }
        return ResponseEntity.ok(hopDongDienTuService.kyNguoiThue(hopDong, req.chuKy));
    }

    @PutMapping("/{id}/thanh-toan")
    public ResponseEntity<HopDongDienTu> thanhToan(@PathVariable Integer id, @RequestBody(required = false) Map<String, String> body,
                                                     Authentication authentication) {
        NguoiDung nguoiDungHienTai = currentUser(authentication);
        HopDongDienTu hopDong = hopDongDienTuService.getByIdWithNguoiDung(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!hopDong.getNguoiThue().getMaNguoiDung().equals(nguoiDungHienTai.getMaNguoiDung())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!Boolean.TRUE.equals(hopDong.getDaKyChuTro()) || !Boolean.TRUE.equals(hopDong.getDaKyNguoiThue())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hợp đồng chưa được ký đầy đủ");
        }

        String phuongThuc = body != null ? body.get("phuongThuc") : null;
        return ResponseEntity.ok(hopDongDienTuService.thanhToanCoc(hopDong, phuongThuc));
    }

    @GetMapping
    public List<HopDongDienTu> getAll() {
        return hopDongDienTuService.getAll();
    }

    // Dung cho trang "Xem chi tiet hop dong" (xem + xuat PDF) - tra ve kem day du
    // phong/nhaTro/chuTro/nguoiThue (JOIN FETCH) va CHI cho phep chu tro, nguoi
    // thue cua chinh hop dong nay hoac ADMIN xem, tranh lo thong tin CCCD/dia
    // chi cho nguoi dung bat ky nhu truoc day (getById() cu khong kiem tra quyen).
    @GetMapping("/{id}")
    public ResponseEntity<HopDongDienTu> getById(@PathVariable Integer id, Authentication authentication) {
        NguoiDung nguoiDungHienTai = currentUser(authentication);
        HopDongDienTu hopDong = hopDongDienTuService.getByIdChiTietDayDu(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean laChuTro = hopDong.getChuTro().getMaNguoiDung().equals(nguoiDungHienTai.getMaNguoiDung());
        boolean laNguoiThue = hopDong.getNguoiThue().getMaNguoiDung().equals(nguoiDungHienTai.getMaNguoiDung());
        boolean laAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()) || "ADMIN".equals(a.getAuthority()));
        if (!laChuTro && !laNguoiThue && !laAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không có quyền xem hợp đồng này");
        }
        return ResponseEntity.ok(hopDong);
    }

    @GetMapping("/nguoi-thue/{userId}")
    public ResponseEntity<List<HopDongDienTu>> getByNguoiThue(@PathVariable Integer userId, Authentication authentication) {
        checkSelf(userId, authentication);
        return ResponseEntity.ok(hopDongDienTuService.getByNguoiThue(userId));
    }

    @GetMapping("/chu-tro/{userId}")
    public ResponseEntity<List<HopDongDienTu>> getByChuTro(@PathVariable Integer userId, Authentication authentication) {
        checkSelf(userId, authentication);
        return ResponseEntity.ok(hopDongDienTuService.getByChuTro(userId));
    }

    private void checkSelf(Integer userId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.UNAUTHORIZED);
        }
        boolean admin = authentication.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()) || "ADMIN".equals(a.getAuthority()));
        if (!admin && nguoiDungRepository.findById(userId).map(u -> u.getEmail() == null || !u.getEmail().equalsIgnoreCase(authentication.getName())).orElse(true)) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping
    public ResponseEntity<HopDongDienTu> create(@RequestBody HopDongDienTu hopDong) {
        return ResponseEntity.ok(hopDongDienTuService.create(hopDong));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HopDongDienTu> update(@PathVariable Integer id, @RequestBody HopDongDienTu hopDong) {
        return hopDongDienTuService.update(id, hopDong)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return hopDongDienTuService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}