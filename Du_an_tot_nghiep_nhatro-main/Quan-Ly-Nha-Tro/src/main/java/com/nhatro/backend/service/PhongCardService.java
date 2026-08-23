package com.nhatro.backend.service;

import com.nhatro.backend.dto.PhongCardDto;
import com.nhatro.backend.entity.PhongTro;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhongCardService {

    @Transactional(readOnly = true)
    public PhongCardDto toCard(PhongTro phong, LocalDateTime thoiGian) {
        if (phong == null) return null;
        String tenNhaTro = phong.getNhaTro() != null ? phong.getNhaTro().getTenNhaTro() : "Nhà trọ";
        String diaChi = phong.getNhaTro() != null ? phong.getNhaTro().getDiaChi() : "";
        String hinhAnh = phong.getNhaTro() != null ? phong.getNhaTro().getHinhAnh() : null;
        List<String> tienIch = new ArrayList<>();
        if (phong.getNhaTro() != null && phong.getNhaTro().getDanhSachTienIch() != null) {
            phong.getNhaTro().getDanhSachTienIch().stream()
                    .map(t -> t.getTenTienIch())
                    .filter(s -> s != null && !s.isBlank())
                    .limit(4)
                    .forEach(tienIch::add);
        }
        if (tienIch.isEmpty() && phong.getGiaInternet() != null) tienIch.add("Wifi");
        return new PhongCardDto(
                phong.getMaPhong(),
                value(phong.getTenPhong(), "Phòng trọ"),
                value(tenNhaTro, "Nhà trọ"),
                value(diaChi, ""),
                phong.getGiaPhong(),
                value(phong.getLoaiPhong(), "Phòng trọ"),
                normalizeImage(hinhAnh),
                phong.getTrangThai(),
                tienIch,
                thoiGian
        );
    }

    private String value(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }

    private String normalizeImage(String image) {
        if (image == null || image.isBlank()) return "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=900&q=80";
        String value = image.trim();
        if ((value.startsWith("[") && value.endsWith("]"))) {
            int firstQuote = value.indexOf('"');
            int secondQuote = firstQuote >= 0 ? value.indexOf('"', firstQuote + 1) : -1;
            if (firstQuote >= 0 && secondQuote > firstQuote) value = value.substring(firstQuote + 1, secondQuote);
        }
        return value.replace("\\\"", "\"");
    }
}
