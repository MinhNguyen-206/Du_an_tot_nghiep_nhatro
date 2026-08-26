package com.nhatro.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nhatro.backend.entity.HopDongDienTu;
import com.nhatro.backend.entity.MaOtp;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.repository.HopDongDienTuRepository;
import com.nhatro.backend.repository.NguoiDungRepository;
import com.nhatro.backend.service.OtpService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "OTP", description = "Gui + xac thuc OTP truoc khi ky hop dong dien tu")
@RestController
@RequestMapping("/api/otp")
public class OtpController {

    private final OtpService otpService;
    private final HopDongDienTuRepository hopDongDienTuRepository;
    private final NguoiDungRepository nguoiDungRepository;

    public OtpController(OtpService otpService, HopDongDienTuRepository hopDongDienTuRepository,
                          NguoiDungRepository nguoiDungRepository) {
        this.otpService = otpService;
        this.hopDongDienTuRepository = hopDongDienTuRepository;
        this.nguoiDungRepository = nguoiDungRepository;
    }

    public static class GuiOtpRequest {
        public Integer maHopDong;
        public String vaiTroKy; // "CHU_TRO" hoac "NGUOI_THUE"
        public String kenhGui;  // "SDT" hoac "EMAIL" - nguoi dung chon o modal ky hop dong (mac dinh EMAIL)
    }

    @PostMapping("/gui")
    public ResponseEntity<?> gui(@RequestBody GuiOtpRequest req, Authentication authentication) {
        NguoiDung nguoiDungHienTai = currentUser(authentication);
        HopDongDienTu hopDong = hopDongDienTuRepository.findByIdWithNguoiDung(req.maHopDong)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy hợp đồng"));

        NguoiDung nguoiKy = kiemTraVaLayNguoiKy(hopDong, req.vaiTroKy, nguoiDungHienTai);

        String kenhGui = "SDT".equalsIgnoreCase(req.kenhGui) ? "SDT" : "EMAIL";
        OtpService.KetQuaGuiOtp ketQua = otpService.taoVaGui(hopDong.getMaHopDong(), req.vaiTroKy, nguoiKy, kenhGui);
        MaOtp otp = ketQua.getOtp();
        boolean daGuiThanhCong = ketQua.isDaGuiEmailThanhCong();

        Map<String, Object> res = new HashMap<>();
        res.put("kenhGui", kenhGui);
        if (daGuiThanhCong) {
            if ("SDT".equals(kenhGui)) {
                res.put("message", "Đã gửi mã OTP qua email " + maskEmail(nguoiKy.getEmail()));
                res.put("note", "Hệ thống chưa tích hợp tổng đài SMS thật nên mã OTP được gửi qua email đăng ký của bạn.");
            } else {
                res.put("message", "Đã gửi mã OTP qua email " + maskEmail(nguoiKy.getEmail()));
            }
        } else {
            // Gui email that that bai (VD: chua cau hinh SMTP, sai mat khau ung dung...)
            // -> tra ma OTP ve UI de van test duoc luong ky hop dong.
            res.put("message", "Không gửi được email OTP thật (kiểm tra lại cấu hình SMTP). Dùng tạm mã demo bên dưới.");
            res.put("otpDemo", otp.getMaSo());
        }
        return ResponseEntity.ok(res);
    }

    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) return email;
        String[] parts = email.split("@", 2);
        String ten = parts[0];
        String hienThi = ten.length() <= 2 ? ten.charAt(0) + "*" : ten.substring(0, 2) + "***";
        return hienThi + "@" + parts[1];
    }

    private String maskSdt(String sdt) {
        if (sdt == null || sdt.length() < 4) return sdt;
        return "*****" + sdt.substring(sdt.length() - 3);
    }

    private NguoiDung currentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return nguoiDungRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

    private NguoiDung kiemTraVaLayNguoiKy(HopDongDienTu hopDong, String vaiTroKy, NguoiDung nguoiDungHienTai) {
        if ("CHU_TRO".equals(vaiTroKy)) {
            if (!hopDong.getChuTro().getMaNguoiDung().equals(nguoiDungHienTai.getMaNguoiDung())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            return hopDong.getChuTro();
        } else if ("NGUOI_THUE".equals(vaiTroKy)) {
            if (!hopDong.getNguoiThue().getMaNguoiDung().equals(nguoiDungHienTai.getMaNguoiDung())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            return hopDong.getNguoiThue();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vaiTroKy không hợp lệ");
    }
}