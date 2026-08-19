package com.nhatro.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Gui email don gian bang JavaMailSender (Spring Boot tu cau hinh bean nay
 * dua vao cac property spring.mail.* trong application.properties).
 *
 * Neu chua dien MAIL_USERNAME/MAIL_PASSWORD (bien moi truong) thi viec gui
 * mail se that bai (loi xac thuc SMTP) - luc do thay vi lam sap ung dung,
 * ta bat loi va IN LINK RA CONSOLE, de van co the test toan bo luong
 * "quen mat khau" ma khong can tai khoan email that.
 */
@Service
public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String fromAddress;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendResetPasswordEmail(String toEmail, String resetLink) {
        try {
            if (fromAddress == null || fromAddress.isBlank()) {
                throw new IllegalStateException("Chua cau hinh MAIL_USERNAME/MAIL_PASSWORD");
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromAddress);
            message.setTo(toEmail);
            message.setSubject("Room Connect - Yeu cau khoi phuc mat khau");
            message.setText(
                    "Xin chao,\n\n" +
                    "Ban (hoac ai do) vua yeu cau khoi phuc mat khau cho tai khoan Room Connect gan voi email nay.\n" +
                    "Vui long bam vao lien ket sau de dat lai mat khau moi (lien ket co hieu luc trong 15 phut):\n\n" +
                    resetLink + "\n\n" +
                    "Neu ban khong yeu cau, vui long bo qua email nay.\n\n" +
                    "- Room Connect -");
            mailSender.send(message);
            log.info("Da gui email khoi phuc mat khau toi {}", toEmail);
        } catch (Exception e) {
            log.warn("Khong the gui email that (chua cau hinh SMTP hoac gui loi): {}", e.getMessage());
            log.warn("===================================================================");
            log.warn("[DEV] Link khoi phuc mat khau cho {}:", toEmail);
            log.warn(resetLink);
            log.warn("===================================================================");
        }
    }

    /**
     * Gui email khi co khach dien form "Lien he" tren trang /lien-he.
     * Email se duoc gui TOI CHINH hop mail admin (fromAddress, tuc la
     * MAIL_USERNAME da cau hinh), kem Reply-To la email cua khach de
     * admin bam "Tra loi" trong Gmail la nhan thang cho khach, khong
     * can copy/paste email thu cong.
     */
    public boolean sendContactMessage(String hoTen, String emailKhach, String noiDung) {
        try {
            if (fromAddress == null || fromAddress.isBlank()) {
                throw new IllegalStateException("Chua cau hinh MAIL_USERNAME/MAIL_PASSWORD");
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromAddress);
            message.setTo(fromAddress); // gui ve chinh hop mail admin
            message.setReplyTo(emailKhach); // bam Tra loi la nhan cho khach
            message.setSubject("[Lien he Room Connect] Tin nhan moi tu " + hoTen);
            message.setText(
                    "Co mot khach vua gui lien he tu website Room Connect:\n\n" +
                    "Ho ten: " + hoTen + "\n" +
                    "Email: " + emailKhach + "\n\n" +
                    "Noi dung:\n" + noiDung + "\n\n" +
                    "------\n" +
                    "Ban co the bam \"Tra loi\" (Reply) de phan hoi truc tiep cho khach.");
            mailSender.send(message);
            log.info("Da gui email lien he tu {} ({}) ve hop mail admin", hoTen, emailKhach);
            return true;
        } catch (Exception e) {
            log.warn("Khong the gui email lien he (chua cau hinh SMTP hoac gui loi): {}", e.getMessage());
            log.warn("===================================================================");
            log.warn("[DEV] Tin nhan lien he tu {} <{}>:", hoTen, emailKhach);
            log.warn(noiDung);
            log.warn("===================================================================");
            return false;
        }
    }
}
