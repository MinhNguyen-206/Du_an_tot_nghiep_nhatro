package com.nhatro.backend.service;

import com.nhatro.backend.entity.XacThucOtp;
import com.nhatro.backend.entity.NguoiDung;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class XacThucOtpService {

    private final List<XacThucOtp> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);
    private final Random random = new Random();

    public XacThucOtpService(NguoiDungService nguoiDungService) {
        Optional<NguoiDung> nguoiThueMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 1)
                .findFirst();

        if (nguoiThueMau.isPresent()) {
            danhSach.add(XacThucOtp.builder()
                    .maOtp(idCounter.getAndIncrement())
                    .nguoiDung(nguoiThueMau.get())
                    .maOtpCode("123456")
                    .loaiXacThuc("EMAIL")
                    .dichVuXacThuc("DANG_NHAP")
                    .email(nguoiThueMau.get().getEmail())
                    .soLanThu(1)
                    .maxThu(5)
                    .trangThai(1) // Đã xác thực
                    .thoiGianTao(LocalDateTime.now().minusHours(2))
                    .thoiGianHetHan(LocalDateTime.now().minusHours(1).plusMinutes(58))
                    .thoiGianXacThuc(LocalDateTime.now().minusHours(1).plusMinutes(45))
                    .build());

            danhSach.add(XacThucOtp.builder()
                    .maOtp(idCounter.getAndIncrement())
                    .nguoiDung(nguoiThueMau.get())
                    .maOtpCode("654321")
                    .loaiXacThuc("SMS")
                    .dichVuXacThuc("THANH_TOAN")
                    .soDienThoai(nguoiThueMau.get().getSoDienThoai())
                    .soLanThu(0)
                    .maxThu(3)
                    .trangThai(0) // Chưa xác thực
                    .thoiGianTao(LocalDateTime.now().minusMinutes(5))
                    .thoiGianHetHan(LocalDateTime.now().plusMinutes(10))
                    .build());
        }
    }

    public List<XacThucOtp> getAll() {
        return danhSach;
    }

    public Optional<XacThucOtp> getById(Integer id) {
        return danhSach.stream().filter(x -> x.getMaOtp().equals(id)).findFirst();
    }

    public XacThucOtp create(XacThucOtp xacThucOtp) {
        xacThucOtp.setMaOtp(idCounter.getAndIncrement());
        xacThucOtp.setThoiGianTao(LocalDateTime.now());
        xacThucOtp.setThoiGianHetHan(LocalDateTime.now().plusMinutes(15));
        if (xacThucOtp.getMaxThu() == null) {
            xacThucOtp.setMaxThu(5);
        }
        if (xacThucOtp.getSoLanThu() == null) {
            xacThucOtp.setSoLanThu(0);
        }
        if (xacThucOtp.getTrangThai() == null) {
            xacThucOtp.setTrangThai(0);
        }
        danhSach.add(xacThucOtp);
        return xacThucOtp;
    }

    public Optional<XacThucOtp> update(Integer id, XacThucOtp duLieuMoi) {
        return getById(id).map(x -> {
            x.setTrangThai(duLieuMoi.getTrangThai());
            x.setSoLanThu(duLieuMoi.getSoLanThu());
            if (duLieuMoi.getTrangThai() == 1) {
                x.setThoiGianXacThuc(LocalDateTime.now());
            }
            return x;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(x -> x.getMaOtp().equals(id));
    }

    // Hàm giúp tạo OTP ngẫu nhiên
    public String generateOtpCode() {
        return String.format("%06d", random.nextInt(999999));
    }
}
