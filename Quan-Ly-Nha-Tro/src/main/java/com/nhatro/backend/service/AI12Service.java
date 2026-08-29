package com.nhatro.backend.service;

import com.nhatro.backend.dto.AI12Response;
import com.nhatro.backend.entity.DangTin;
import com.nhatro.backend.entity.PhongTro;
import com.nhatro.backend.repository.DangTinRepository;
import com.nhatro.backend.repository.LichSuXemPhongRepository;
import com.nhatro.backend.repository.PhongTroTienIchRepository;
import com.nhatro.backend.repository.PhongYeuThichRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class AI12Service {

    private final DangTinRepository dangTinRepository;
    private final LichSuXemPhongRepository lichSuXemPhongRepository;
    private final PhongYeuThichRepository phongYeuThichRepository;
    private final PhongTroTienIchRepository phongTroTienIchRepository;

    public AI12Service(
            DangTinRepository dangTinRepository,
            LichSuXemPhongRepository lichSuXemPhongRepository,
            PhongYeuThichRepository phongYeuThichRepository,
            PhongTroTienIchRepository phongTroTienIchRepository
    ) {
        this.dangTinRepository = dangTinRepository;
        this.lichSuXemPhongRepository = lichSuXemPhongRepository;
        this.phongYeuThichRepository = phongYeuThichRepository;
        this.phongTroTienIchRepository = phongTroTienIchRepository;
    }

    /**
     * UC-ADMIN-AI-12
     *
     * Phân tích toàn bộ bài đăng và xếp hạng
     * theo điểm AI từ cao xuống thấp.
     */
    @Transactional(readOnly = true)
    public List<AI12Response> duDoanBaiDang() {

        List<DangTin> danhSach = dangTinRepository.findAll();

        if (danhSach.isEmpty()) {
            return List.of();
        }

        return danhSach.stream()
                .map(this::phanTichBaiDang)
                .filter(java.util.Objects::nonNull)
                .sorted(
                        Comparator.comparing(
                                AI12Response::diemAI,
                                Comparator.nullsLast(
                                        Comparator.reverseOrder()
                                )
                        )
                )
                .toList();
    }

    /**
     * Phân tích một bài đăng.
     */
    private AI12Response phanTichBaiDang(DangTin dangTin) {

        if (dangTin == null) {
            return null;
        }

        PhongTro phong = dangTin.getPhong();

        /*
         * Nếu bài đăng chưa liên kết phòng
         * thì không thể phân tích đầy đủ.
         */
        if (phong == null) {
            return new AI12Response(
                    dangTin.getMaDangTin(),
                    dangTin.getTieuDe(),
                    null,
                    null,
                    null,
                    null,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    0L,
                    0L,
                    0,
                    0.0,
                    "Không xác định",
                    "Bài đăng chưa liên kết với phòng."
            );
        }

        /*
         * =====================================================
         * 1. LƯỢT XEM
         * =====================================================
         */
        long luotXem =
                lichSuXemPhongRepository.countByPhong_MaPhong(
                        phong.getMaPhong()
                );

        /*
         * =====================================================
         * 2. LƯỢT YÊU THÍCH
         * =====================================================
         */
        long luotYeuThich =
                phongYeuThichRepository.countByPhong_MaPhong(
                        phong.getMaPhong()
                );

        /*
         * =====================================================
         * 3. TIỆN ÍCH THỰC TẾ
         * =====================================================
         */
        int soTienIch =
                demTienIch(phong);

        /*
         * =====================================================
         * 4. TÍNH ĐIỂM TỪNG YẾU TỐ
         * =====================================================
         */
        double diemLuotXem =
                tinhDiemLuotXem(luotXem);

        double diemYeuThich =
                tinhDiemYeuThich(luotYeuThich);

        double diemGia =
                tinhDiemGia(phong.getGiaPhong());

        double diemDienTich =
                tinhDiemDienTich(phong.getDienTich());

        double diemTienIch =
                tinhDiemTienIch(soTienIch);

        double diemSoNguoi =
                tinhDiemSoNguoi(phong.getSoLuongNguoi());

        double diemLoaiPhong =
                tinhDiemLoaiPhong(phong.getLoaiPhong());

        double diemTrangThai =
                Boolean.TRUE.equals(dangTin.getTrangThai())
                        ? 100.0
                        : 0.0;

        /*
         * =====================================================
         * CÔNG THỨC AI-12
         *
         * Lượt xem       : 25%
         * Yêu thích      : 20%
         * Giá phòng      : 15%
         * Diện tích      : 15%
         * Tiện ích       : 10%
         * Số người       : 5%
         * Loại phòng     : 5%
         * Trạng thái     : 5%
         *
         * Tổng            : 100%
         * =====================================================
         */
        double diem =
                diemLuotXem * 0.25
                        + diemYeuThich * 0.20
                        + diemGia * 0.15
                        + diemDienTich * 0.15
                        + diemTienIch * 0.10
                        + diemSoNguoi * 0.05
                        + diemLoaiPhong * 0.05
                        + diemTrangThai * 0.05;

        /*
         * Làm tròn 1 chữ số thập phân.
         */
        diem = lamTron(diem);

        /*
         * Xác định mức độ tiềm năng.
         */
        String mucDo =
                xacDinhMucDo(diem);

        /*
         * Tạo nhận định giải thích kết quả.
         */
        String nhanDinh =
                taoNhanDinh(
                        diem,
                        luotXem,
                        luotYeuThich,
                        soTienIch,
                        phong.getGiaPhong(),
                        phong.getDienTich(),
                        phong.getLoaiPhong(),
                        phong.getSoLuongNguoi(),
                        dangTin.getTrangThai()
                );

        /*
         * Thông tin nhà trọ.
         */
        String tenNhaTro = null;
        String diaChi = null;

        if (phong.getNhaTro() != null) {

            tenNhaTro =
                    phong.getNhaTro().getTenNhaTro();

            diaChi =
                    phong.getNhaTro().getDiaChi();
        }

        /*
         * Trả kết quả AI-12.
         */
        return new AI12Response(
                dangTin.getMaDangTin(),
                dangTin.getTieuDe(),
                phong.getMaPhong(),
                phong.getTenPhong(),
                tenNhaTro,
                diaChi,
                phong.getGiaPhong(),
                phong.getDienTich(),
                luotXem,
                luotYeuThich,
                soTienIch,
                diem,
                mucDo,
                nhanDinh
        );
    }

    /**
     * =========================================================
     * ĐIỂM LƯỢT XEM
     *
     * 0 lượt   -> 0
     * 50 lượt  -> 50
     * 100 lượt -> 100
     * Trên 100 -> vẫn tối đa 100
     * =========================================================
     */
    private double tinhDiemLuotXem(long luotXem) {

        if (luotXem <= 0) {
            return 0.0;
        }

        return Math.min(luotXem, 100) / 100.0 * 100.0;
    }

    /**
     * =========================================================
     * ĐIỂM YÊU THÍCH
     *
     * 0 lượt  -> 0
     * 25 lượt -> 50
     * 50 lượt -> 100
     * =========================================================
     */
    private double tinhDiemYeuThich(long luotYeuThich) {

        if (luotYeuThich <= 0) {
            return 0.0;
        }

        return Math.min(luotYeuThich, 50) / 50.0 * 100.0;
    }

    /**
     * =========================================================
     * ĐIỂM GIÁ
     *
     * <= 3 triệu  -> 100
     * <= 5 triệu  -> 80
     * <= 7 triệu  -> 60
     * <= 10 triệu -> 40
     * <= 15 triệu -> 20
     * > 15 triệu  -> 0
     * =========================================================
     */
    private double tinhDiemGia(BigDecimal giaPhong) {

        if (giaPhong == null) {
            return 50.0;
        }

        double gia =
                giaPhong.doubleValue();

        if (gia <= 3_000_000) {
            return 100.0;
        }

        if (gia <= 5_000_000) {
            return 80.0;
        }

        if (gia <= 7_000_000) {
            return 60.0;
        }

        if (gia <= 10_000_000) {
            return 40.0;
        }

        if (gia <= 15_000_000) {
            return 20.0;
        }

        return 0.0;
    }

    /**
     * =========================================================
     * ĐIỂM DIỆN TÍCH
     *
     * 20 - 40 m²  -> 100
     * 15 - <20 m² -> 80
     * >40 - 60 m² -> 80
     * 10 - <15 m² -> 60
     * >60 m²      -> 60
     * còn lại     -> 30
     * =========================================================
     */
    private double tinhDiemDienTich(
            BigDecimal dienTich
    ) {

        if (dienTich == null) {
            return 50.0;
        }

        double dt =
                dienTich.doubleValue();

        if (dt >= 20 && dt <= 40) {
            return 100.0;
        }

        if (dt >= 15 && dt < 20) {
            return 80.0;
        }

        if (dt > 40 && dt <= 60) {
            return 80.0;
        }

        if (dt >= 10 && dt < 15) {
            return 60.0;
        }

        if (dt > 60) {
            return 60.0;
        }

        return 30.0;
    }

    /**
     * =========================================================
     * ĐIỂM TIỆN ÍCH
     *
     * 0 tiện ích -> 0
     * 1 tiện ích -> 20
     * 2 tiện ích -> 40
     * 3 tiện ích -> 60
     * 4 tiện ích -> 80
     * 5+ tiện ích -> 100
     * =========================================================
     */
    private double tinhDiemTienIch(
            int soTienIch
    ) {

        if (soTienIch <= 0) {
            return 0.0;
        }

        return Math.min(soTienIch, 5) / 5.0 * 100.0;
    }

    /**
     * =========================================================
     * ĐIỂM SỐ NGƯỜI
     *
     * Không có dữ liệu -> 50
     * 1 - 4 người  -> 100
     * 5 - 6 người  -> 80
     * 7 - 8 người  -> 60
     * > 8 người    -> 40
     *
     * Đây là điểm hỗ trợ, trọng số chỉ 5%.
     * =========================================================
     */
    private double tinhDiemSoNguoi(
            Integer soLuongNguoi
    ) {

        if (soLuongNguoi == null
                || soLuongNguoi <= 0) {
            return 50.0;
        }

        if (soLuongNguoi <= 4) {
            return 100.0;
        }

        if (soLuongNguoi <= 6) {
            return 80.0;
        }

        if (soLuongNguoi <= 8) {
            return 60.0;
        }

        return 40.0;
    }

    /**
     * =========================================================
     * ĐIỂM LOẠI PHÒNG
     *
     * Có thông tin loại phòng -> điểm cao hơn.
     *
     * Các loại phòng phổ biến:
     * - phòng trọ
     * - studio
     * - căn hộ
     * - chung cư
     *
     * Loại khác nhưng có dữ liệu -> 80
     * Không có dữ liệu -> 50
     * =========================================================
     */
    private double tinhDiemLoaiPhong(
            String loaiPhong
    ) {

        if (loaiPhong == null
                || loaiPhong.isBlank()) {
            return 50.0;
        }

        String loai =
                loaiPhong
                        .trim()
                        .toLowerCase();

        if (loai.contains("phòng trọ")
                || loai.contains("phong tro")
                || loai.contains("studio")
                || loai.contains("căn hộ")
                || loai.contains("can ho")
                || loai.contains("chung cư")
                || loai.contains("chung cu")) {

            return 100.0;
        }

        return 80.0;
    }

    /**
     * =========================================================
     * ĐẾM TIỆN ÍCH THỰC TẾ
     *
     * PHONG_TRO
     *      ↓
     * PHONG_TRO_TIEN_ICH
     *      ↓
     * TIEN_ICH
     *
     * Không hard-code.
     * =========================================================
     */
    private int demTienIch(
            PhongTro phong
    ) {

        if (phong == null
                || phong.getMaPhong() == null) {
            return 0;
        }

        return phongTroTienIchRepository
                .findByMaPhong(
                        phong.getMaPhong()
                )
                .size();
    }

    /**
     * =========================================================
     * XÁC ĐỊNH MỨC ĐỘ
     * =========================================================
     */
    private String xacDinhMucDo(
            double diem
    ) {

        if (diem >= 80) {
            return "Rất tiềm năng";
        }

        if (diem >= 60) {
            return "Tiềm năng";
        }

        if (diem >= 40) {
            return "Khá";
        }

        return "Thấp";
    }

    /**
     * =========================================================
     * NHẬN ĐỊNH AI
     *
     * Không chỉ đưa ra điểm,
     * mà còn giải thích vì sao.
     * =========================================================
     */
    private String taoNhanDinh(
            double diem,
            long luotXem,
            long luotYeuThich,
            int soTienIch,
            BigDecimal giaPhong,
            BigDecimal dienTich,
            String loaiPhong,
            Integer soLuongNguoi,
            Boolean trangThai
    ) {

        /*
         * Bài đăng không hoạt động.
         */
        if (!Boolean.TRUE.equals(trangThai)) {

            return "Bài đăng hiện không hoạt động nên khả năng tiếp cận người thuê thấp.";
        }

        /*
         * Đã có lượng quan tâm rất cao.
         */
        if (luotXem >= 100
                && luotYeuThich >= 20) {

            return "Bài đăng có lượng xem và yêu thích cao, khả năng thu hút người thuê rất tốt.";
        }

        /*
         * Có lượng quan tâm tốt.
         */
        if (luotXem >= 50
                && luotYeuThich >= 10) {

            return "Bài đăng đang nhận được sự quan tâm tốt từ người tìm phòng.";
        }

        /*
         * Bài đăng mới nhưng chất lượng cơ bản tốt.
         */
        if (diem >= 60
                && luotXem < 10
                && luotYeuThich < 5) {

            return "Bài đăng có chất lượng cơ bản tốt nhưng chưa có nhiều dữ liệu tương tác. Có tiềm năng tăng trưởng nếu được tiếp cận thêm người thuê.";
        }

        /*
         * Giá + diện tích + tiện ích tốt.
         */
        if (giaPhong != null
                && giaPhong.compareTo(
                BigDecimal.valueOf(5_000_000)
        ) <= 0
                && dienTich != null
                && dienTich.compareTo(
                BigDecimal.valueOf(20)
        ) >= 0
                && soTienIch >= 3) {

            return "Mức giá hợp lý, diện tích phù hợp và có nhiều tiện ích, có tiềm năng thu hút người thuê.";
        }

        /*
         * Giá tốt.
         */
        if (giaPhong != null
                && giaPhong.compareTo(
                BigDecimal.valueOf(5_000_000)
        ) <= 0) {

            return "Mức giá tương đối hợp lý và có khả năng thu hút nhóm người thuê phổ thông.";
        }

        /*
         * Có nhiều tiện ích.
         */
        if (soTienIch >= 3) {

            return "Phòng có nhiều tiện ích, đây là yếu tố có thể giúp tăng khả năng thu hút người thuê.";
        }

        /*
         * Điểm tổng thể tốt.
         */
        if (diem >= 60) {

            return "Bài đăng có nhiều yếu tố thuận lợi và có khả năng thu hút người thuê.";
        }

        /*
         * Điểm trung bình.
         */
        if (diem >= 40) {

            return "Bài đăng có mức tiềm năng trung bình và có thể cải thiện thêm để tăng khả năng thu hút người thuê.";
        }

        /*
         * Điểm thấp.
         */
        return "Bài đăng hiện chưa có đủ yếu tố nổi bật hoặc dữ liệu tương tác để được đánh giá là tiềm năng cao.";
    }

    /**
     * Làm tròn điểm AI đến 1 chữ số thập phân.
     */
    private double lamTron(
            double value
    ) {

        return Math.round(
                value * 10.0
        ) / 10.0;
    }
}