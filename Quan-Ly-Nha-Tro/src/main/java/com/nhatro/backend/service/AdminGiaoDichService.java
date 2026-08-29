package com.nhatro.backend.service;

import com.nhatro.backend.dto.GiaoDichThanhToanDto;
import com.nhatro.backend.dto.HoaDonChiTietDto;
import com.nhatro.backend.entity.ChiSoDienNuoc;
import com.nhatro.backend.entity.GiaoDichThanhToan;
import com.nhatro.backend.entity.HoaDonThang;
import com.nhatro.backend.entity.HopDongDienTu;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.NhaTro;
import com.nhatro.backend.entity.PhongTro;
import com.nhatro.backend.entity.ThanhToanTienTro;
import com.nhatro.backend.repository.GiaoDichThanhToanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Nghiep vu rieng cho man hinh "Giao dich & hoa don" cua Admin.
 * Chi doc (Admin chi theo doi, khong sua/xoa giao dich thanh toan).
 *
 * VE "Xuat hoa don PDF" trong use case: thay vi sinh PDF phia server (can
 * them thu vien nhu iText/OpenPDF vao pom.xml - rui ro xung dot dependency
 * voi cac thanh vien khac trong nhom dang lam song song), man hinh chi
 * tiet hoa don o day duoc thiet ke de IN TRUC TIEP tu trinh duyet (Ctrl+P
 * -> Luu thanh PDF), dat duoc cung muc dich ma khong doi them dependency.
 */
@Service
public class AdminGiaoDichService {

    private final GiaoDichThanhToanRepository giaoDichThanhToanRepository;

    public AdminGiaoDichService(GiaoDichThanhToanRepository giaoDichThanhToanRepository) {
        this.giaoDichThanhToanRepository = Objects.requireNonNull(giaoDichThanhToanRepository, "giaoDichThanhToanRepository must not be null");
    }

    @Transactional(readOnly = true)
    public List<GiaoDichThanhToanDto> danhSach(String trangThai, String tuKhoa) {
        String keyword = (tuKhoa == null || tuKhoa.isBlank()) ? null : tuKhoa.trim().toLowerCase();
        String loc = (trangThai == null || trangThai.isBlank()) ? null : trangThai.trim();

        return giaoDichThanhToanRepository.findAllByOrderByNgayGiaoDichDesc().stream()
                .filter(gd -> loc == null || loc.equalsIgnoreCase(gd.getTrangThai()))
                .filter(gd -> keyword == null || khopTuKhoa(gd, keyword))
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public HoaDonChiTietDto chiTietHoaDon(Integer maGiaoDich) {
        Objects.requireNonNull(maGiaoDich, "maGiaoDich must not be null");
        GiaoDichThanhToan gd = giaoDichThanhToanRepository.findByMaGiaoDich(maGiaoDich)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy giao dịch"));
        return toHoaDonChiTietDto(gd);
    }

    // ===================== HELPER =====================

    private boolean khopTuKhoa(GiaoDichThanhToan gd, String keyword) {
        HopDongDienTu hopDong = layHopDong(gd);
        NguoiDung nguoiThue = hopDong == null ? null : hopDong.getNguoiThue();
        PhongTro phong = hopDong == null ? null : hopDong.getPhong();
        NhaTro nhaTro = phong == null ? null : phong.getNhaTro();

        return chua(gd.getMaGiaoDichCongThanhToan(), keyword)
                || chua(gd.getNganHang(), keyword)
                || chua(gd.getNoiDung(), keyword)
                || chua(nguoiThue == null ? null : nguoiThue.getHoTen(), keyword)
                || chua(nguoiThue == null ? null : nguoiThue.getEmail(), keyword)
                || chua(nhaTro == null ? null : nhaTro.getTenNhaTro(), keyword)
                || chua(phong == null ? null : phong.getTenPhong(), keyword);
    }

    private boolean chua(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }

    private HopDongDienTu layHopDong(GiaoDichThanhToan gd) {
        ThanhToanTienTro tt = gd.getThanhToan();
        HoaDonThang hd = tt == null ? null : tt.getHoaDon();
        return hd == null ? null : hd.getHopDong();
    }

    private GiaoDichThanhToanDto toDto(GiaoDichThanhToan gd) {
        ThanhToanTienTro tt = gd.getThanhToan();
        HoaDonThang hd = tt == null ? null : tt.getHoaDon();
        HopDongDienTu hopDong = hd == null ? null : hd.getHopDong();
        NguoiDung nguoiThue = hopDong == null ? null : hopDong.getNguoiThue();
        PhongTro phong = hopDong == null ? null : hopDong.getPhong();
        NhaTro nhaTro = phong == null ? null : phong.getNhaTro();

        return new GiaoDichThanhToanDto(
                gd.getMaGiaoDich(),
                gd.getMaGiaoDichCongThanhToan(),
                gd.getNganHang(),
                gd.getNoiDung(),
                gd.getNgayGiaoDich(),
                gd.getTrangThai(),

                tt == null ? null : tt.getMaThanhToan(),
                tt == null ? null : tt.getSoTien(),
                tt == null ? null : tt.getPhuongThuc(),
                tt == null ? null : tt.getNgayThanhToan(),

                hd == null ? null : hd.getMaHoaDon(),
                hd == null ? null : hd.getTongTien(),
                hd == null ? null : hd.getNgayLap(),
                hd == null ? null : hd.getHanThanhToan(),
                hd == null ? null : hd.getTrangThai(),

                nguoiThue == null ? null : nguoiThue.getMaNguoiDung(),
                nguoiThue == null ? null : nguoiThue.getHoTen(),
                nguoiThue == null ? null : nguoiThue.getEmail(),

                phong == null ? null : phong.getMaPhong(),
                phong == null ? null : phong.getTenPhong(),
                nhaTro == null ? null : nhaTro.getTenNhaTro());
    }

    private HoaDonChiTietDto toHoaDonChiTietDto(GiaoDichThanhToan gd) {
        ThanhToanTienTro tt = gd.getThanhToan();
        HoaDonThang hd = tt == null ? null : tt.getHoaDon();
        ChiSoDienNuoc chiSo = hd == null ? null : hd.getChiSo();
        HopDongDienTu hopDong = hd == null ? null : hd.getHopDong();
        NguoiDung chuTro = hopDong == null ? null : hopDong.getChuTro();
        NguoiDung nguoiThue = hopDong == null ? null : hopDong.getNguoiThue();
        PhongTro phong = hopDong == null ? null : hopDong.getPhong();
        NhaTro nhaTro = phong == null ? null : phong.getNhaTro();

        return new HoaDonChiTietDto(
                hd == null ? null : hd.getMaHoaDon(),
                hd == null ? null : hd.getTongTien(),
                hd == null ? null : hd.getNgayLap(),
                hd == null ? null : hd.getHanThanhToan(),
                hd == null ? null : hd.getTrangThai(),

                chiSo == null ? null : chiSo.getThang(),
                chiSo == null ? null : chiSo.getNam(),
                chiSo == null ? null : chiSo.getChiSoDienCu(),
                chiSo == null ? null : chiSo.getChiSoDienMoi(),
                chiSo == null ? null : chiSo.getChiSoNuocCu(),
                chiSo == null ? null : chiSo.getChiSoNuocMoi(),

                hopDong == null ? null : hopDong.getMaHopDong(),
                hopDong == null ? null : hopDong.getGiaThue(),

                chuTro == null ? null : chuTro.getHoTen(),
                nguoiThue == null ? null : nguoiThue.getHoTen(),
                nguoiThue == null ? null : nguoiThue.getEmail(),

                phong == null ? null : phong.getTenPhong(),
                nhaTro == null ? null : nhaTro.getTenNhaTro(),
                nhaTro == null ? null : nhaTro.getDiaChi(),

                gd.getMaGiaoDich(),
                gd.getMaGiaoDichCongThanhToan(),
                gd.getNganHang(),
                tt == null ? null : tt.getSoTien(),
                tt == null ? null : tt.getPhuongThuc(),
                tt == null ? null : tt.getNgayThanhToan());
    }
}
