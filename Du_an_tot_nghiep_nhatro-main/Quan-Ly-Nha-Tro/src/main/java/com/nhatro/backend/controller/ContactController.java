package com.nhatro.backend.controller;

import com.nhatro.backend.dto.ContactRequest;
import com.nhatro.backend.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Nhan du lieu tu form "Lien he" (trang /lien-he) va gui email
 * thong bao ve hop mail admin (MAIL_USERNAME da cau hinh trong
 * application.properties / bien moi truong).
 */
@RestController
public class ContactController {

    private final MailService mailService;

    public ContactController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/api/lien-he")
    public ResponseEntity<Map<String, Object>> guiLienHe(@RequestBody ContactRequest request) {

        if (request.getHoTen() == null || request.getHoTen().isBlank()
                || request.getEmail() == null || request.getEmail().isBlank()
                || request.getNoiDung() == null || request.getNoiDung().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Vui long nhap day du Ten, Email va Noi dung."
            ));
        }

        boolean daGui = mailService.sendContactMessage(
                request.getHoTen().trim(),
                request.getEmail().trim(),
                request.getNoiDung().trim()
        );

        if (daGui) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Cam on ban da lien he. Chung toi se phan hoi qua email som nhat."
            ));
        }

        // Chua cau hinh SMTP that (MAIL_USERNAME/MAIL_PASSWORD) - van tra ve
        // 200 de UI khong bao loi voi khach, nhung log da in ra console de
        // admin xem noi dung (xem MailService.sendContactMessage).
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Da ghi nhan tin nhan cua ban. (Luu y: server chua cau hinh gui email that, xem console log.)"
        ));
    }
}
