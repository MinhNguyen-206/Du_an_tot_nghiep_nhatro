package com.nhatro.backend.service;

import com.nhatro.backend.dto.admin.AdminAiDtos;
import com.nhatro.backend.entity.LichSuXemPhong;
import com.nhatro.backend.entity.NhaTro;
import com.nhatro.backend.entity.PhongTro;
import com.nhatro.backend.entity.PhongYeuThich;
import com.nhatro.backend.repository.LichSuXemPhongRepository;
import com.nhatro.backend.repository.NhaTroRepository;
import com.nhatro.backend.repository.PhongTroRepository;
import com.nhatro.backend.repository.PhongYeuThichRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AI13Service {

    private final NhaTroRepository nhaTroRepository;
    private final PhongTroRepository phongTroRepository;
    private final LichSuXemPhongRepository lichSuXemPhongRepository;
    private final PhongYeuThichRepository phongYeuThichRepository;

    public AI13Service(
            NhaTroRepository nhaTroRepository,
            PhongTroRepository phongTroRepository,
            LichSuXemPhongRepository lichSuXemPhongRepository,
            PhongYeuThichRepository phongYeuThichRepository
    ) {
        this.nhaTroRepository = nhaTroRepository;
        this.phongTroRepository = phongTroRepository;
        this.lichSuXemPhongRepository = lichSuXemPhongRepository;
        this.phongYeuThichRepository = phongYeuThichRepository;
    }

    /**
     * UC-ADMIN-AI-13
     *
     * AI gợi ý các khu vực có nhu cầu thuê phòng cao.
     *
     * Dữ liệu sử dụng:
     * - Địa chỉ nhà trọ
     * - Số lượng phòng
     * - Lượt xem phòng
     * - Lượt yêu thích phòng
     *
     * Công thức mật độ nhu cầu:
     *
     * (lượt xem + lượt yêu thích * 3) / số phòng
     *
     * Sau đó chuẩn hóa khu vực cao nhất về 100 điểm.
     */
    @Transactional(readOnly = true)
    public List<AdminAiDtos.HotArea> goiYKhuVucHot() {

        List<NhaTro> danhSachNhaTro =
                nhaTroRepository.findAll();

        List<PhongTro> danhSachPhong =
                phongTroRepository.findAll();

        /*
         * Không có nhà trọ thì không có khu vực để phân tích.
         */
        if (danhSachNhaTro.isEmpty()) {
            return List.of();
        }

        /*
         * =====================================================
         * 1. XÁC ĐỊNH KHU VỰC CỦA TỪNG NHÀ TRỌ
         * =====================================================
         *
         * maNhaTro -> khuVuc
         */
        Map<Integer, String> khuVucTheoNhaTro =
                new LinkedHashMap<>();

        for (NhaTro nhaTro : danhSachNhaTro) {

            if (nhaTro == null
                    || nhaTro.getMaNhaTro() == null) {
                continue;
            }

            String khuVuc =
                    xacDinhKhuVuc(
                            nhaTro.getDiaChi()
                    );

            khuVucTheoNhaTro.put(
                    nhaTro.getMaNhaTro(),
                    khuVuc
            );
        }

        /*
         * =====================================================
         * 2. XÁC ĐỊNH NHÀ TRỌ CỦA TỪNG PHÒNG
         * =====================================================
         *
         * maPhong -> maNhaTro
         *
         * Dùng map này để biết lượt xem / yêu thích
         * của phòng thuộc khu vực nào.
         */
        Map<Integer, Integer> nhaTroTheoPhong =
                new LinkedHashMap<>();

        /*
         * =====================================================
         * 3. TẠO THỐNG KÊ KHU VỰC
         * =====================================================
         */
        Map<String, AreaData> areaMap =
                new LinkedHashMap<>();

        /*
         * =====================================================
         * 4. ĐẾM SỐ PHÒNG THEO KHU VỰC
         * =====================================================
         */
        for (PhongTro phong : danhSachPhong) {

            if (phong == null
                    || phong.getMaPhong() == null
                    || phong.getNhaTro() == null
                    || phong.getNhaTro().getMaNhaTro() == null) {

                continue;
            }

            Integer maNhaTro =
                    phong.getNhaTro()
                            .getMaNhaTro();

            String khuVuc =
                    khuVucTheoNhaTro.get(
                            maNhaTro
                    );

            if (khuVuc == null
                    || khuVuc.isBlank()) {

                khuVuc = "Không xác định";
            }

            /*
             * Lưu quan hệ phòng -> nhà trọ.
             */
            nhaTroTheoPhong.put(
                    phong.getMaPhong(),
                    maNhaTro
            );

            /*
             * Tạo hoặc lấy vùng thống kê.
             */
            AreaData area =
                    areaMap.computeIfAbsent(
                            khuVuc,
                            AreaData::new
                    );

            area.soPhong++;
        }

        /*
         * =====================================================
         * 5. CỘNG LƯỢT XEM THEO KHU VỰC
         * =====================================================
         */
        List<LichSuXemPhong> danhSachLichSuXem =
                lichSuXemPhongRepository.findAll();

        for (LichSuXemPhong lichSu :
                danhSachLichSuXem) {

            if (lichSu == null
                    || lichSu.getPhong() == null
                    || lichSu.getPhong().getMaPhong() == null) {

                continue;
            }

            Integer maPhong =
                    lichSu.getPhong()
                            .getMaPhong();

            Integer maNhaTro =
                    nhaTroTheoPhong.get(
                            maPhong
                    );

            if (maNhaTro == null) {
                continue;
            }

            String khuVuc =
                    khuVucTheoNhaTro.get(
                            maNhaTro
                    );

            if (khuVuc == null
                    || khuVuc.isBlank()) {

                khuVuc = "Không xác định";
            }

            AreaData area =
                    areaMap.computeIfAbsent(
                            khuVuc,
                            AreaData::new
                    );

            area.luotXem++;
        }

        /*
         * =====================================================
         * 6. CỘNG LƯỢT YÊU THÍCH THEO KHU VỰC
         * =====================================================
         */
        List<PhongYeuThich> danhSachYeuThich =
                phongYeuThichRepository.findAll();

        for (PhongYeuThich yeuThich :
                danhSachYeuThich) {

            if (yeuThich == null
                    || yeuThich.getPhong() == null
                    || yeuThich.getPhong().getMaPhong() == null) {

                continue;
            }

            Integer maPhong =
                    yeuThich.getPhong()
                            .getMaPhong();

            Integer maNhaTro =
                    nhaTroTheoPhong.get(
                            maPhong
                    );

            if (maNhaTro == null) {
                continue;
            }

            String khuVuc =
                    khuVucTheoNhaTro.get(
                            maNhaTro
                    );

            if (khuVuc == null
                    || khuVuc.isBlank()) {

                khuVuc = "Không xác định";
            }

            AreaData area =
                    areaMap.computeIfAbsent(
                            khuVuc,
                            AreaData::new
                    );

            area.luotLuu++;
        }

        /*
         * =====================================================
         * 7. CHỈ GIỮ KHU VỰC CÓ PHÒNG
         * =====================================================
         */
        List<AreaData> areas =
                new ArrayList<>(
                        areaMap.values()
                );

        areas.removeIf(
                area -> area.soPhong <= 0
        );

        if (areas.isEmpty()) {
            return List.of();
        }

        /*
         * =====================================================
         * 8. TÌM MẬT ĐỘ NHU CẦU CAO NHẤT
         * =====================================================
         */
        double maxDemand =
                areas.stream()
                        .mapToDouble(
                                AreaData::tinhMatDoNhuCau
                        )
                        .max()
                        .orElse(0.0);

        /*
         * =====================================================
         * 9. TRƯỜNG HỢP CHƯA CÓ DỮ LIỆU TƯƠNG TÁC
         * =====================================================
         *
         * Nếu tất cả khu vực đều:
         *
         * lượt xem = 0
         * lượt thích = 0
         *
         * thì chưa thể nói khu vực nào hot.
         */
        if (maxDemand <= 0) {

            return areas.stream()
                    .map(
                            area ->
                                    taoHotArea(
                                            area,
                                            0.0
                                    )
                    )
                    .sorted(
                            Comparator.comparing(
                                    AdminAiDtos.HotArea::khuVuc
                            )
                    )
                    .toList();
        }

        /*
         * =====================================================
         * 10. CHUYỂN SANG DTO + XẾP HẠNG
         * =====================================================
         */
        return areas.stream()
                .map(
                        area ->
                                taoHotArea(
                                        area,
                                        maxDemand
                                )
                )
                .sorted(
                        Comparator
                                .comparingInt(
                                        AdminAiDtos.HotArea::diemNhuCau
                                )
                                .reversed()
                                .thenComparing(
                                        AdminAiDtos.HotArea::khuVuc
                                )
                )
                .toList();
    }

    /**
     * Chuyển thống kê khu vực thành DTO trả về frontend.
     */
    private AdminAiDtos.HotArea taoHotArea(
            AreaData area,
            double maxDemand
    ) {

        int diemNhuCau;

        if (maxDemand <= 0) {

            diemNhuCau = 0;

        } else {

            double normalized =
                    area.tinhMatDoNhuCau()
                            / maxDemand
                            * 100.0;

            diemNhuCau =
                    (int) Math.round(
                            Math.max(
                                    0,
                                    Math.min(
                                            100,
                                            normalized
                                    )
                            )
                    );
        }

        String xuHuong =
                xacDinhXuHuong(
                        diemNhuCau
                );

        return new AdminAiDtos.HotArea(
                area.khuVuc,
                area.luotXem,
                area.soPhong,
                diemNhuCau,
                xuHuong
        );
    }

    /**
     * Xác định nhãn xu hướng dựa trên điểm nhu cầu.
     */
    private String xacDinhXuHuong(
            int diemNhuCau
    ) {

        if (diemNhuCau >= 80) {
            return "Rất hot";
        }

        if (diemNhuCau >= 60) {
            return "Hot";
        }

        if (diemNhuCau >= 40) {
            return "Đang tăng";
        }

        if (diemNhuCau >= 20) {
            return "Ổn định";
        }

        return "Thấp";
    }

    /**
     * =====================================================
     * XÁC ĐỊNH KHU VỰC TỪ ĐỊA CHỈ
     * =====================================================
     *
     * Ví dụ:
     *
     * 125 Nguyễn Văn Quá, Quận 12, TP. Hồ Chí Minh
     * -> Quận 12
     *
     * 88 Phạm Văn Đồng, Gò Vấp, TP. Hồ Chí Minh
     * -> Gò Vấp
     *
     * 45 Lê Văn Việt, TP. Thủ Đức, TP. Hồ Chí Minh
     * -> TP. Thủ Đức
     *
     * Ưu tiên quận/huyện/thị xã trước thành phố Hồ Chí Minh.
     */
    private String xacDinhKhuVuc(
            String diaChi
    ) {

        if (diaChi == null
                || diaChi.isBlank()) {

            return "Không xác định";
        }

        String normalized =
                diaChi.trim();

        /*
         * =====================================================
         * 1. QUẬN + SỐ
         *
         * Ví dụ:
         * Quận 12
         * Quận 1
         * Quận 7
         * =====================================================
         */
        Pattern quanPattern =
                Pattern.compile(
                        "(quận\\s+\\d+)",
                        Pattern.CASE_INSENSITIVE
                                | Pattern.UNICODE_CASE
                );

        Matcher quanMatcher =
                quanPattern.matcher(
                        normalized
                );

        if (quanMatcher.find()) {

            return chuanHoaKhuVuc(
                    quanMatcher.group(1)
            );
        }

        /*
         * =====================================================
         * 2. GÒ VẤP
         * =====================================================
         */
        String lower =
                normalized.toLowerCase(
                        Locale.ROOT
                );

        if (lower.contains("gò vấp")
                || lower.contains("go vap")) {

            return "Gò Vấp";
        }

        /*
         * =====================================================
         * 3. THỦ ĐỨC
         * =====================================================
         */
        if (lower.contains("thủ đức")
                || lower.contains("thu duc")) {

            return "TP. Thủ Đức";
        }

        /*
         * =====================================================
         * 4. HUYỆN
         * =====================================================
         */
        Pattern huyenPattern =
                Pattern.compile(
                        "(huyện\\s+[^,]+)",
                        Pattern.CASE_INSENSITIVE
                                | Pattern.UNICODE_CASE
                );

        Matcher huyenMatcher =
                huyenPattern.matcher(
                        normalized
                );

        if (huyenMatcher.find()) {

            return chuanHoaKhuVuc(
                    huyenMatcher.group(1)
            );
        }

        /*
         * =====================================================
         * 5. THỊ XÃ
         * =====================================================
         */
        Pattern thiXaPattern =
                Pattern.compile(
                        "(thị xã\\s+[^,]+)",
                        Pattern.CASE_INSENSITIVE
                                | Pattern.UNICODE_CASE
                );

        Matcher thiXaMatcher =
                thiXaPattern.matcher(
                        normalized
                );

        if (thiXaMatcher.find()) {

            return chuanHoaKhuVuc(
                    thiXaMatcher.group(1)
            );
        }

        /*
         * =====================================================
         * 6. THÀNH PHỐ KHÁC
         * =====================================================
         */
        Pattern thanhPhoPattern =
                Pattern.compile(
                        "(?:thành phố|tp\\.?)\\s+([^,]+)",
                        Pattern.CASE_INSENSITIVE
                                | Pattern.UNICODE_CASE
                );

        Matcher thanhPhoMatcher =
                thanhPhoPattern.matcher(
                        normalized
                );

        if (thanhPhoMatcher.find()) {

            String ten =
                    thanhPhoMatcher
                            .group(1)
                            .trim();

            /*
             * Nếu đây là TP. Hồ Chí Minh
             * thì bỏ qua để tránh gom toàn bộ
             * khu vực về thành phố cấp tỉnh.
             */
            String tenLower =
                    ten.toLowerCase(
                            Locale.ROOT
                    );

            if (!tenLower.contains(
                    "hồ chí minh"
            )
                    && !tenLower.contains(
                    "ho chi minh"
            )) {

                return chuanHoaKhuVuc(
                        "TP. " + ten
                );
            }
        }

        /*
         * =====================================================
         * 7. FALLBACK
         * =====================================================
         *
         * Lấy thành phần gần cuối địa chỉ,
         * nhưng không chọn TP. Hồ Chí Minh.
         * =====================================================
         */
        String[] parts =
                normalized.split(",");

        if (parts.length >= 2) {

            for (int i = parts.length - 2;
                 i >= 0;
                 i--) {

                String part =
                        parts[i].trim();

                if (part.isBlank()) {
                    continue;
                }

                String partLower =
                        part.toLowerCase(
                                Locale.ROOT
                        );

                if (partLower.contains(
                        "hồ chí minh"
                )
                        || partLower.contains(
                        "ho chi minh"
                )) {

                    continue;
                }

                return chuanHoaKhuVuc(
                        part
                );
            }
        }

        /*
         * Không có đủ thông tin.
         */
        return "Không xác định";
    }

    /**
     * Chuẩn hóa tên khu vực.
     */
    private String chuanHoaKhuVuc(
            String khuVuc
    ) {

        if (khuVuc == null
                || khuVuc.isBlank()) {

            return "Không xác định";
        }

        return khuVuc
                .replaceAll(
                        "\\s+",
                        " "
                )
                .replaceAll(
                        "[.,;]+$",
                        ""
                )
                .trim();
    }

    /**
     * =====================================================
     * CLASS DỮ LIỆU TRUNG GIAN
     * =====================================================
     */
    private static class AreaData {

        private final String khuVuc;

        private long luotXem;

        private long luotLuu;

        private long soPhong;

        private AreaData(
                String khuVuc
        ) {

            this.khuVuc = khuVuc;
        }

        /**
         * Mật độ nhu cầu:
         *
         * (lượt xem + lượt thích * 3)
         * / số phòng
         */
        private double tinhMatDoNhuCau() {

            if (soPhong <= 0) {
                return 0.0;
            }

            return (
                    luotXem
                            + luotLuu * 3.0
            ) / soPhong;
        }
    }
}