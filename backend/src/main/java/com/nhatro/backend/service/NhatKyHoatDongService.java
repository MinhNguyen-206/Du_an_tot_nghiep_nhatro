package com.nhatro.backend.service;

import com.nhatro.backend.entity.NhatKyHoatDong;
import com.nhatro.backend.entity.NguoiDung;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NhatKyHoatDongService {

    private final List<NhatKyHoatDong> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public NhatKyHoatDongService(NguoiDungService nguoiDungService) {
        Optional<NguoiDung> nguoiThueMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 1)
                .findFirst();
        Optional<NguoiDung> chuTroMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 2)
                .findFirst();

        if (nguoiThueMau.isPresent()) {
            // Nhật ký hoạt động người thuê
            danhSach.add(NhatKyHoatDong.builder()
                    .maNhatKy(idCounter.getAndIncrement())
                    .nguoiDung(nguoiThueMau.get())
                    .loaiHoatDong("TIM_PHONG")
                    .chiTiet("Người dùng đã tìm kiếm các phòng trọ")
                    .diaChiIp("192.168.1.100")
                    .thoiGianTao(LocalDateTime.now().minusHours(3))
                    .trangThai(0) // Bình thường
                    .build());

            danhSach.add(NhatKyHoatDong.builder()
                    .maNhatKy(idCounter.getAndIncrement())
                    .nguoiDung(nguoiThueMau.get())
                    .loaiHoatDong("XEM_PHONG")
                    .chiTiet("Xem chi tiết bài đăng phòng tro")
                    .diaChiIp("192.168.1.100")
                    .thoiGianTao(LocalDateTime.now().minusHours(2))
                    .trangThai(0)
                    .build());

            danhSach.add(NhatKyHoatDong.builder()
                    .maNhatKy(idCounter.getAndIncrement())
                    .nguoiDung(nguoiThueMau.get())
                    .loaiHoatDong("THANH_TOAN")
                    .chiTiet("Thanh toán tiền tạm ứng phòng")
                    .diaChiIp("192.168.1.100")
                    .thoiGianTao(LocalDateTime.now().minusHours(1))
                    .trangThai(0)
                    .build());

            danhSach.add(NhatKyHoatDong.builder()
                    .maNhatKy(idCounter.getAndIncrement())
                    .nguoiDung(nguoiThueMau.get())
                    .loaiHoatDong("CAP_NHAT_THONG_TIN")
                    .chiTiet("Cập nhật hồ sơ cá nhân")
                    .diaChiIp("192.168.1.100")
                    .thoiGianTao(LocalDateTime.now().minusMinutes(30))
                    .trangThai(0)
                    .build());
        }

        if (chuTroMau.isPresent()) {
            // Nhật ký hoạt động chủ trọ
            danhSach.add(NhatKyHoatDong.builder()
                    .maNhatKy(idCounter.getAndIncrement())
                    .nguoiDung(chuTroMau.get())
                    .loaiHoatDong("DANG_PHONG")
                    .chiTiet("Đăng bài cho thuê phòng trọ")
                    .diaChiIp("192.168.1.101")
                    .thoiGianTao(LocalDateTime.now().minusHours(24))
                    .trangThai(0)
                    .build());

            danhSach.add(NhatKyHoatDong.builder()
                    .maNhatKy(idCounter.getAndIncrement())
                    .nguoiDung(chuTroMau.get())
                    .loaiHoatDong("DUYET_DANG_TIN")
                    .chiTiet("Duyệt bài đăng phòng trọ")
                    .diaChiIp("192.168.1.101")
                    .thoiGianTao(LocalDateTime.now().minusHours(20))
                    .trangThai(0)
                    .build());

            danhSach.add(NhatKyHoatDong.builder()
                    .maNhatKy(idCounter.getAndIncrement())
                    .nguoiDung(chuTroMau.get())
                    .loaiHoatDong("LAP_HOA_DON")
                    .chiTiet("Lập hóa đơn tháng cho người thuê")
                    .diaChiIp("192.168.1.101")
                    .thoiGianTao(LocalDateTime.now().minusHours(5))
                    .trangThai(0)
                    .build());

            danhSach.add(NhatKyHoatDong.builder()
                    .maNhatKy(idCounter.getAndIncrement())
                    .nguoiDung(chuTroMau.get())
                    .loaiHoatDong("THEO_DOI_THANH_TOAN")
                    .chiTiet("Kiểm tra thanh toán từ người thuê")
                    .diaChiIp("192.168.1.101")
                    .thoiGianTao(LocalDateTime.now().minusMinutes(10))
                    .trangThai(0)
                    .build());
        }
    }

    public List<NhatKyHoatDong> getAll() {
        return danhSach;
    }

    public Optional<NhatKyHoatDong> getById(Integer id) {
        return danhSach.stream().filter(n -> n.getMaNhatKy().equals(id)).findFirst();
    }

    public NhatKyHoatDong create(NhatKyHoatDong nhatKyHoatDong) {
        nhatKyHoatDong.setMaNhatKy(idCounter.getAndIncrement());
        nhatKyHoatDong.setThoiGianTao(LocalDateTime.now());
        if (nhatKyHoatDong.getTrangThai() == null) {
            nhatKyHoatDong.setTrangThai(0);
        }
        danhSach.add(nhatKyHoatDong);
        return nhatKyHoatDong;
    }

    public Optional<NhatKyHoatDong> update(Integer id, NhatKyHoatDong duLieuMoi) {
        return getById(id).map(n -> {
            n.setTrangThai(duLieuMoi.getTrangThai());
            n.setChiTiet(duLieuMoi.getChiTiet());
            return n;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(n -> n.getMaNhatKy().equals(id));
    }
}
