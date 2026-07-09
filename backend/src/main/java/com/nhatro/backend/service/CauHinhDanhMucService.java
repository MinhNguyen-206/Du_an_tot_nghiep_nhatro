package com.nhatro.backend.service;

import com.nhatro.backend.entity.CauHinhDanhMuc;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CauHinhDanhMucService {

    private final List<CauHinhDanhMuc> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public CauHinhDanhMucService() {
        themDuLieuMau();
    }

    private void themDuLieuMau() {
        // Loại phòng
        danhSach.add(CauHinhDanhMuc.builder()
                .maCauHinh(idCounter.getAndIncrement())
                .tenDanhMuc("LOAI_PHONG")
                .giaTriKhoa("PHONG_DOI")
                .giaTriHienThi("Phòng Đôi")
                .moTa("Phòng có 2 giường")
                .trangThai(1)
                .thuTuSapXep(1)
                .ghiChu("Loại phòng tiêu chuẩn")
                .ngayTao(LocalDateTime.now().minusDays(365))
                .ngayCapNhat(LocalDateTime.now())
                .build());

        danhSach.add(CauHinhDanhMuc.builder()
                .maCauHinh(idCounter.getAndIncrement())
                .tenDanhMuc("LOAI_PHONG")
                .giaTriKhoa("PHONG_RIENG")
                .giaTriHienThi("Phòng Riêng")
                .moTa("Phòng có 1 giường")
                .trangThai(1)
                .thuTuSapXep(2)
                .ghiChu("Phòng cá nhân")
                .ngayTao(LocalDateTime.now().minusDays(365))
                .ngayCapNhat(LocalDateTime.now())
                .build());

        // Trạng thái phòng
        danhSach.add(CauHinhDanhMuc.builder()
                .maCauHinh(idCounter.getAndIncrement())
                .tenDanhMuc("TRANG_THAI_PHONG")
                .giaTriKhoa("AVAILABLE")
                .giaTriHienThi("Có thể cho thuê")
                .moTa("Phòng sẵn sàng cho thuê")
                .trangThai(1)
                .thuTuSapXep(1)
                .ngayTao(LocalDateTime.now().minusDays(365))
                .ngayCapNhat(LocalDateTime.now())
                .build());

        danhSach.add(CauHinhDanhMuc.builder()
                .maCauHinh(idCounter.getAndIncrement())
                .tenDanhMuc("TRANG_THAI_PHONG")
                .giaTriKhoa("RENTED")
                .giaTriHienThi("Đã cho thuê")
                .moTa("Phòng đã có người thuê")
                .trangThai(1)
                .thuTuSapXep(2)
                .ngayTao(LocalDateTime.now().minusDays(365))
                .ngayCapNhat(LocalDateTime.now())
                .build());

        danhSach.add(CauHinhDanhMuc.builder()
                .maCauHinh(idCounter.getAndIncrement())
                .tenDanhMuc("TRANG_THAI_PHONG")
                .giaTriKhoa("MAINTENANCE")
                .giaTriHienThi("Đang bảo trì")
                .moTa("Phòng đang được sửa chữa")
                .trangThai(1)
                .thuTuSapXep(3)
                .ngayTao(LocalDateTime.now().minusDays(365))
                .ngayCapNhat(LocalDateTime.now())
                .build());

        // Vai trò
        danhSach.add(CauHinhDanhMuc.builder()
                .maCauHinh(idCounter.getAndIncrement())
                .tenDanhMuc("VAI_TRO")
                .giaTriKhoa("NGUOI_THUE")
                .giaTriHienThi("Người Thuê")
                .moTa("Người dùng là người thuê phòng")
                .trangThai(1)
                .thuTuSapXep(1)
                .ngayTao(LocalDateTime.now().minusDays(365))
                .ngayCapNhat(LocalDateTime.now())
                .build());

        danhSach.add(CauHinhDanhMuc.builder()
                .maCauHinh(idCounter.getAndIncrement())
                .tenDanhMuc("VAI_TRO")
                .giaTriKhoa("CHU_TRO")
                .giaTriHienThi("Chủ Trọ")
                .moTa("Người dùng là chủ trọ")
                .trangThai(1)
                .thuTuSapXep(2)
                .ngayTao(LocalDateTime.now().minusDays(365))
                .ngayCapNhat(LocalDateTime.now())
                .build());

        danhSach.add(CauHinhDanhMuc.builder()
                .maCauHinh(idCounter.getAndIncrement())
                .tenDanhMuc("VAI_TRO")
                .giaTriKhoa("ADMIN")
                .giaTriHienThi("Admin")
                .moTa("Người dùng là admin hệ thống")
                .trangThai(1)
                .thuTuSapXep(3)
                .ngayTao(LocalDateTime.now().minusDays(365))
                .ngayCapNhat(LocalDateTime.now())
                .build());

        // Trạng thái thanh toán
        danhSach.add(CauHinhDanhMuc.builder()
                .maCauHinh(idCounter.getAndIncrement())
                .tenDanhMuc("TRANG_THAI_THANH_TOAN")
                .giaTriKhoa("CHUA_THANH_TOAN")
                .giaTriHienThi("Chưa Thanh Toán")
                .moTa("Hóa đơn chưa được thanh toán")
                .trangThai(1)
                .thuTuSapXep(1)
                .ngayTao(LocalDateTime.now().minusDays(365))
                .ngayCapNhat(LocalDateTime.now())
                .build());

        danhSach.add(CauHinhDanhMuc.builder()
                .maCauHinh(idCounter.getAndIncrement())
                .tenDanhMuc("TRANG_THAI_THANH_TOAN")
                .giaTriKhoa("DA_THANH_TOAN")
                .giaTriHienThi("Đã Thanh Toán")
                .moTa("Hóa đơn đã được thanh toán")
                .trangThai(1)
                .thuTuSapXep(2)
                .ngayTao(LocalDateTime.now().minusDays(365))
                .ngayCapNhat(LocalDateTime.now())
                .build());
    }

    public List<CauHinhDanhMuc> getAll() {
        return danhSach;
    }

    public Optional<CauHinhDanhMuc> getById(Integer id) {
        return danhSach.stream().filter(c -> c.getMaCauHinh().equals(id)).findFirst();
    }

    public CauHinhDanhMuc create(CauHinhDanhMuc cauHinhDanhMuc) {
        cauHinhDanhMuc.setMaCauHinh(idCounter.getAndIncrement());
        cauHinhDanhMuc.setNgayTao(LocalDateTime.now());
        cauHinhDanhMuc.setNgayCapNhat(LocalDateTime.now());
        danhSach.add(cauHinhDanhMuc);
        return cauHinhDanhMuc;
    }

    public Optional<CauHinhDanhMuc> update(Integer id, CauHinhDanhMuc duLieuMoi) {
        return getById(id).map(c -> {
            c.setGiaTriHienThi(duLieuMoi.getGiaTriHienThi());
            c.setMoTa(duLieuMoi.getMoTa());
            c.setTrangThai(duLieuMoi.getTrangThai());
            c.setThuTuSapXep(duLieuMoi.getThuTuSapXep());
            c.setGhiChu(duLieuMoi.getGhiChu());
            c.setNgayCapNhat(LocalDateTime.now());
            return c;
        });
    }

    public boolean delete(Integer id) {
        return danhSach.removeIf(c -> c.getMaCauHinh().equals(id));
    }
}
