package com.nhatro.backend.service;

import com.nhatro.backend.entity.MaOtp;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.repository.MaOtpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class OtpService {

    private static final Logger log = LoggerFactory.getLogger(OtpService.class);
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int TTL_PHUT = 5;

    private final MaOtpRepository maOtpRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String fromAddress;

    public OtpService(MaOtpRepository maOtpRepository, JavaMailSender mailSender) {
        this.maOtpRepository = maOtpRepository;
        this.mailSender = mailSender;
    }

    public MaOtp taoVaGui(Integer maHopDong, String vaiTroKy, NguoiDung nguoiKy) {
        String maSo = String.format("%06d", RANDOM.nextInt(1_000_000));

        MaOtp otp = MaOtp.builder()
                .maHopDong(maHopDong)
                .vaiTroKy(vaiTroKy)
                .maNguoiDung(nguoiKy.getMaNguoiDung())
                .maSo(maSo)
                .hetHan(LocalDateTime.now().plusMinutes(TTL_PHUT))
                .daSuDung(false)
                .build();
        otp = maOtpRepository.save(otp);

        guiEmail(nguoiKy.getEmail(), maSo);
        return otp;
    }

    private void guiEmail(String toEmail, String maSo) {
        try {
            if (fromAddress == null || fromAddress.isBlank() || toEmail == null || toEmail.isBlank()) {
                throw new IllegalStateException("Chua cau hinh MAIL_USERNAME/MAIL_PASSWORD hoac thieu email nguoi nhan");
            }
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromAddress);
            message.setTo(toEmail);
            message.setSubject("Room Connect - Ma OTP xac thuc ky hop dong");
            message.setText("Ma OTP cua ban la: " + maSo + "\nMa co hieu luc trong " + TTL_PHUT + " phut.\n\n" +
                    "Neu ban khong yeu cau, vui long bo qua email nay.\n\n- Room Connect -");
            mailSender.send(message);
            log.info("Da gui OTP toi {}", toEmail);
        } catch (Exception e) {
            log.warn("Khong gui duoc email OTP that ({}). Ma OTP (DEV) cho {}: {}", e.getMessage(), toEmail, maSo);
        }
    }

    public boolean xacThuc(Integer maHopDong, String vaiTroKy, Integer maNguoiDung, String maSo) {
        return maOtpRepository
                .findFirstByMaHopDongAndVaiTroKyAndMaNguoiDungAndDaSuDungFalseOrderByNgayTaoDesc(maHopDong, vaiTroKy, maNguoiDung)
                .filter(otp -> otp.getMaSo().equals(maSo))
                .filter(otp -> otp.getHetHan().isAfter(LocalDateTime.now()))
                .map(otp -> {
                    otp.setDaSuDung(true);
                    maOtpRepository.save(otp);
                    return true;
                })
                .orElse(false);
    }
}