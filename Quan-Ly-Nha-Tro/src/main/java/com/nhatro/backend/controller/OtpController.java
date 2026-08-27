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
    }

    @PostMapping("/gui")
    public ResponseEntity<?> gui(@RequestBody GuiOtpRequest req, Authentication authentication) {
        NguoiDung nguoiDungHienTai = currentUser(authentication);
        HopDongDienTu hopDong = hopDongDienTuRepository.findById(req.maHopDong)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy hợp đồng"));

        NguoiDung nguoiKy = kiemTraVaLayNguoiKy(hopDong, req.vaiTroKy, nguoiDungHienTai);

        MaOtp otp = otpService.taoVaGui(hopDong.getMaHopDong(), req.vaiTroKy, nguoiKy);

        Map<String, Object> res = new HashMap<>();
        res.put("message", "Đã gửi mã OTP");
        res.put("otpDemo", otp.getMaSo()); // TODO: xóa khi có SMS/SMTP thật
        return ResponseEntity.ok(res);
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