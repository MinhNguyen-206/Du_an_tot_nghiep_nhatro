package com.nhatro.backend.service;

import com.nhatro.backend.dto.PhongCardDto;
import com.nhatro.backend.dto.PhongChiTietDto;
import com.nhatro.backend.entity.DangTin;
import com.nhatro.backend.entity.DanhGia;
import com.nhatro.backend.entity.HinhAnh;
import com.nhatro.backend.entity.NhaTro;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.PhongTro;
import com.nhatro.backend.entity.PhongTroTienIch;
import com.nhatro.backend.entity.XacThucEkyc;
import com.nhatro.backend.exception.ResourceNotFoundException;
import com.nhatro.backend.repository.DangTinRepository;
import com.nhatro.backend.repository.DanhGiaRepository;
import com.nhatro.backend.repository.HinhAnhRepository;
import com.nhatro.backend.repository.NhaTroRepository;
import com.nhatro.backend.repository.PhongTroRepository;
import com.nhatro.backend.repository.PhongTroTienIchRepository;
import com.nhatro.backend.repository.PhongYeuThichRepository;
import com.nhatro.backend.repository.XacThucEkycRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class PhongChiTietService {

    private static final int SO_LUONG_PHONG_TUONG_TU = 6;

    // Nhận diện đoạn "khu vực" trong địa chỉ dạng "..., Quận 7, TP. Hồ Chí Minh"
    // hoặc "..., TP. Thủ Đức, TP. Hồ Chí Minh" để tìm phòng tương tự cùng khu vực.
    private static final Pattern DISTRICT_PATTERN = Pattern.compile(
            "(Qu[aậ]n\\s+\\S+|Huy[eệ]n\\s+[^,]+|TP\\.?\\s*Th[uủ]\\s*[ĐD][uứ]c|Th[àa]nh\\s*ph[oố]\\s*Th[uủ]\\s*[ĐD][uứ]c)",
            Pattern.CASE_INSENSITIVE
    );

    private final PhongTroRepository phongTroRepository;
    private final DangTinRepository dangTinRepository;
    private final HinhAnhRepository hinhAnhRepository;
    private final PhongTroTienIchRepository phongTroTienIchRepository;
    private final DanhGiaRepository danhGiaRepository;
    private final XacThucEkycRepository xacThucEkycRepository;
    private final NhaTroRepository nhaTroRepository;
    private final PhongYeuThichRepository phongYeuThichRepository;
    private final PhongCardService phongCardService;

    public PhongChiTietService(PhongTroRepository phongTroRepository,
                                DangTinRepository dangTinRepository,
                                HinhAnhRepository hinhAnhRepository,
                                PhongTroTienIchRepository phongTroTienIchRepository,
                                DanhGiaRepository danhGiaRepository,
                                XacThucEkycRepository xacThucEkycRepository,
                                NhaTroRepository nhaTroRepository,
                                PhongYeuThichRepository phongYeuThichRepository,
                                PhongCardService phongCardService) {
        this.phongTroRepository = phongTroRepository;
        this.dangTinRepository = dangTinRepository;
        this.hinhAnhRepository = hinhAnhRepository;
        this.phongTroTienIchRepository = phongTroTienIchRepository;
        this.danhGiaRepository = danhGiaRepository;
        this.xacThucEkycRepository = xacThucEkycRepository;
        this.nhaTroRepository = nhaTroRepository;
        this.phongYeuThichRepository = phongYeuThichRepository;
        this.phongCardService = phongCardService;
    }

    @Transactional(readOnly = true)
    public PhongChiTietDto layChiTiet(Integer maPhong, Integer maNguoiDungDangXem) {
        Objects.requireNonNull(maPhong, "maPhong must not be null");

        PhongTro phong = phongTroRepository.findById(maPhong)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng trọ id=" + maPhong));

        NhaTro nhaTro = phong.getNhaTro();
        NguoiDung chuTro = nhaTro != null ? nhaTro.getNguoiDung() : null;

        // Tin đăng còn hiệu lực mới nhất của phòng này (để lấy tiêu đề/nội dung/ảnh).
        // Chỉ lấy bài đã được Admin DUYỆT; bài trangThaiDuyet=null (tạo trước khi
        // có tính năng kiểm duyệt) vẫn coi là hợp lệ để không ẩn mất dữ liệu cũ.
        DangTin dangTin = dangTinRepository.findByPhong_MaPhong(maPhong).stream()
                .filter(dt -> Boolean.TRUE.equals(dt.getTrangThai()))
                .filter(dt -> dt.getTrangThaiDuyet() == null || "DA_DUYET".equals(dt.getTrangThaiDuyet()))
                .max(Comparator.comparing(DangTin::getNgayDang, Comparator.nullsFirst(Comparator.naturalOrder())))
                .orElse(null);

        List<PhongChiTietDto.HinhAnhDto> hinhAnh = layHinhAnh(dangTin, nhaTro);
        List<PhongChiTietDto.TienIchDto> tienIch = layTienIch(maPhong);

        List<DanhGia> danhSachDanhGia = danhGiaRepository.findByPhong_MaPhong(maPhong).stream()
                .filter(dg -> Boolean.TRUE.equals(dg.getTrangThai()))
                .sorted(Comparator.comparing(DanhGia::getNgayDanhGia, Comparator.nullsFirst(Comparator.naturalOrder())).reversed())
                .toList();
        double soSaoTrungBinh = danhSachDanhGia.stream()
                .mapToInt(DanhGia::getSoSao)
                .average()
                .orElse(0.0);
        List<PhongChiTietDto.DanhGiaDto> danhGiaDto = danhSachDanhGia.stream()
                .map(this::toDanhGiaDto)
                .toList();

        boolean daYeuThich = maNguoiDungDangXem != null &&
                phongYeuThichRepository.existsByNguoiDung_MaNguoiDungAndPhong_MaPhong(maNguoiDungDangXem, maPhong);

        List<PhongCardDto> phongTuongTu = layPhongTuongTu(phong, nhaTro);

        return new PhongChiTietDto(
                phong.getMaPhong(),
                phong.getTenPhong(),
                dangTin != null ? dangTin.getTieuDe() : phong.getTenPhong(),
                dangTin != null ? dangTin.getNoiDung() : (nhaTro != null ? nhaTro.getMoTa() : null),
                nhaTro != null ? nhaTro.getDiaChi() : null,
                nhaTro != null ? nhaTro.getTenNhaTro() : null,
                nhaTro != null ? nhaTro.getMaNhaTro() : null,
                phong.getDienTich(),
                phong.getLoaiPhong(),
                phong.getSoLuongNguoi(),
                phong.getGiaPhong(),
                phong.getGiaDien(),
                phong.getGiaNuoc(),
                phong.getGiaGuiXe(),
                phong.getGiaInternet(),
                phong.getTrangThai(),
                Math.round(soSaoTrungBinh * 10.0) / 10.0,
                danhSachDanhGia.size(),
                daYeuThich,
                hinhAnh,
                tienIch,
                toChuTroDto(chuTro),
                danhGiaDto,
                phongTuongTu
        );
    }

    private List<PhongChiTietDto.HinhAnhDto> layHinhAnh(DangTin dangTin, NhaTro nhaTro) {
        if (dangTin != null) {
            List<HinhAnh> anh = hinhAnhRepository.findByDangTin_MaDangTinOrderByThuTuHienThi(dangTin.getMaDangTin());
            if (!anh.isEmpty()) {
                return anh.stream()
                        .map(a -> new PhongChiTietDto.HinhAnhDto(a.getMaHinhAnh(), a.getDuongDan(), a.getMoTa(), a.getThuTuHienThi()))
                        .toList();
            }
        }
        // Không có ảnh riêng cho tin đăng -> dùng ảnh đại diện của nhà trọ (nếu có)
        if (nhaTro != null && nhaTro.getHinhAnh() != null && !nhaTro.getHinhAnh().isBlank()) {
            return List.of(new PhongChiTietDto.HinhAnhDto(null, nhaTro.getHinhAnh(), nhaTro.getTenNhaTro(), 1));
        }
        return List.of();
    }

    private List<PhongChiTietDto.TienIchDto> layTienIch(Integer maPhong) {
        return phongTroTienIchRepository.findByMaPhong(maPhong).stream()
                .map(PhongTroTienIch::getTienIch)
                .filter(Objects::nonNull)
                .map(t -> new PhongChiTietDto.TienIchDto(t.getMaTienIch(), t.getTenTienIch()))
                .toList();
    }

    private PhongChiTietDto.ChuTroDto toChuTroDto(NguoiDung chuTro) {
        if (chuTro == null) return null;
        boolean daXacThuc = xacThucEkycRepository.findTopByNguoiDung_MaNguoiDungOrderByNgayGuiDesc(chuTro.getMaNguoiDung())
                .map(this::laHopLe)
                .orElse(false);
        return new PhongChiTietDto.ChuTroDto(
                chuTro.getMaNguoiDung(),
                chuTro.getHoTen(),
                chuTro.getAvatar(),
                chuTro.getSoDienThoai(),
                chuTro.getEmail(),
                daXacThuc
        );
    }

    private boolean laHopLe(XacThucEkyc ekyc) {
        return Boolean.TRUE.equals(ekyc.getTrangThai())
                && ekyc.getKetQua() != null
                && ekyc.getKetQua().toLowerCase(Locale.ROOT).contains("hợp lệ");
    }

    private PhongChiTietDto.DanhGiaDto toDanhGiaDto(DanhGia dg) {
        NguoiDung nd = dg.getNguoiDung();
        return new PhongChiTietDto.DanhGiaDto(
                dg.getMaDanhGia(),
                dg.getSoSao(),
                dg.getNoiDung(),
                dg.getNgayDanhGia(),
                nd != null ? nd.getHoTen() : "Người dùng",
                nd != null ? nd.getAvatar() : null
        );
    }

    /**
     * Lấy tối đa {@link #SO_LUONG_PHONG_TUONG_TU} phòng còn trống, thuộc các nhà trọ khác
     * nằm cùng khu vực (quận/huyện/TP trực thuộc) suy ra từ địa chỉ nhà trọ hiện tại.
     * Nếu không tách được khu vực từ địa chỉ, coi như không có phòng tương tự.
     */
    private List<PhongCardDto> layPhongTuongTu(PhongTro phongHienTai, NhaTro nhaTroHienTai) {
        if (nhaTroHienTai == null || nhaTroHienTai.getDiaChi() == null) return List.of();
        String khuVuc = trichKhuVuc(nhaTroHienTai.getDiaChi());
        if (khuVuc == null) return List.of();

        return nhaTroRepository.findAll().stream()
                .filter(n -> !n.getMaNhaTro().equals(nhaTroHienTai.getMaNhaTro()))
                .filter(n -> n.getDiaChi() != null && n.getDiaChi().toLowerCase(Locale.ROOT).contains(khuVuc.toLowerCase(Locale.ROOT)))
                .flatMap(n -> phongTroRepository.findByNhaTro_MaNhaTro(n.getMaNhaTro()).stream())
                .filter(p -> Boolean.TRUE.equals(p.getTrangThai()))
                .filter(p -> !p.getMaPhong().equals(phongHienTai.getMaPhong()))
                .limit(SO_LUONG_PHONG_TUONG_TU)
                .map(p -> phongCardService.toCard(p, null))
                .collect(Collectors.toList());
    }

    private String trichKhuVuc(String diaChi) {
        Matcher m = DISTRICT_PATTERN.matcher(diaChi);
        if (m.find()) return m.group(1).trim();
        return null;
    }
}
