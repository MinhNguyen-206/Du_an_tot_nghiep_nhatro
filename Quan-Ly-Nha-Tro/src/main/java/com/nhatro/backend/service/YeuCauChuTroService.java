package com.nhatro.backend.service;

import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.VaiTro;
import com.nhatro.backend.entity.YeuCauChuTro;
import com.nhatro.backend.repository.NguoiDungRepository;
import com.nhatro.backend.repository.VaiTroRepository;
import com.nhatro.backend.repository.YeuCauChuTroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class YeuCauChuTroService {

    private static final String CHO_DUYET = "CHO_DUYET";
    private static final String DA_DUYET = "DA_DUYET";
    private static final String TU_CHOI = "TU_CHOI";
    private static final String TEN_VAI_TRO_CHU_TRO = "Chủ trọ";
    private static final Integer MA_VAI_TRO_CHU_TRO_DU_PHONG = 2;

    private final YeuCauChuTroRepository yeuCauRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final VaiTroRepository vaiTroRepository;

    public YeuCauChuTroService(YeuCauChuTroRepository yeuCauRepository,
                                NguoiDungRepository nguoiDungRepository,
                                VaiTroRepository vaiTroRepository) {
        this.yeuCauRepository = yeuCauRepository;
        this.nguoiDungRepository = nguoiDungRepository;
        this.vaiTroRepository = vaiTroRepository;
    }

    public List<YeuCauChuTro> getAll() {
        return yeuCauRepository.findAll();
    }

    public List<YeuCauChuTro> getChoDuyet() {
        return yeuCauRepository.findByTrangThaiOrderByNgayGuiAsc(CHO_DUYET);
    }

    public Optional<YeuCauChuTro> getById(Integer id) {
        return yeuCauRepository.findById(id);
    }

    public List<YeuCauChuTro> getByNguoiDung(Integer maNguoiDung) {
        return yeuCauRepository.findByNguoiDung_MaNguoiDungOrderByNgayGuiDesc(maNguoiDung);
    }

    public Optional<YeuCauChuTro> getMoiNhatByNguoiDung(Integer maNguoiDung) {
        return yeuCauRepository.findTopByNguoiDung_MaNguoiDungOrderByNgayGuiDesc(maNguoiDung);
    }

    // Nguoi dung (dang la "Nguoi thue") gui yeu cau tro thanh Chu tro.
    // - Khong cho gui neu tai khoan da la Chu tro/Admin.
    // - Khong cho gui trung khi con 1 yeu cau CHO_DUYET chua duoc xu ly.
    @Transactional
    public YeuCauChuTro guiYeuCau(Integer maNguoiDung, YeuCauChuTro duLieu) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        Objects.requireNonNull(duLieu, "duLieu must not be null");

        NguoiDung nguoiDung = nguoiDungRepository.findById(maNguoiDung)
                .orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));

        String tenVaiTroHienTai = nguoiDung.getVaiTro() != null ? nguoiDung.getVaiTro().getTenVaiTro() : "";
        if (tenVaiTroHienTai != null && tenVaiTroHienTai.equalsIgnoreCase(TEN_VAI_TRO_CHU_TRO)) {
            throw new IllegalStateException("Tài khoản của bạn đã là Chủ trọ.");
        }

        if (yeuCauRepository.existsByNguoiDung_MaNguoiDungAndTrangThai(maNguoiDung, CHO_DUYET)) {
            throw new IllegalStateException(
                    "Bạn đã gửi một yêu cầu đăng ký Chủ trọ và đang chờ quản trị viên xét duyệt.");
        }

        if (duLieu.getSoCCCD() == null || duLieu.getSoCCCD().isBlank()) {
            throw new IllegalArgumentException("Vui lòng nhập số CCCD/CMND.");
        }
        if (duLieu.getAnhCCCDMatTruoc() == null || duLieu.getAnhCCCDMatTruoc().isBlank()
                || duLieu.getAnhCCCDMatSau() == null || duLieu.getAnhCCCDMatSau().isBlank()) {
            throw new IllegalArgumentException("Vui lòng cung cấp ảnh CCCD/CMND mặt trước và mặt sau.");
        }
        if (duLieu.getDiaChiBDS() == null || duLieu.getDiaChiBDS().isBlank()) {
            throw new IllegalArgumentException("Vui lòng nhập địa chỉ phòng/nhà trọ cần xác minh.");
        }

        YeuCauChuTro yeuCau = YeuCauChuTro.builder()
                .nguoiDung(nguoiDung)
                .hoTenCCCD(duLieu.getHoTenCCCD())
                .soCCCD(duLieu.getSoCCCD())
                .anhCCCDMatTruoc(duLieu.getAnhCCCDMatTruoc())
                .anhCCCDMatSau(duLieu.getAnhCCCDMatSau())
                .diaChiBDS(duLieu.getDiaChiBDS())
                .anhThucTe(duLieu.getAnhThucTe())
                .giayToSoHuu(duLieu.getGiayToSoHuu())
                .giayToBoSung(duLieu.getGiayToBoSung())
                .ghiChuNguoiDung(duLieu.getGhiChuNguoiDung())
                .trangThai(CHO_DUYET)
                .build();

        return yeuCauRepository.save(yeuCau);
    }

    // Admin duyet: danh dau yeu cau DA_DUYET va nang vai tro NguoiDung len Chu tro.
    @Transactional
    public Optional<YeuCauChuTro> duyet(Integer id) {
        return yeuCauRepository.findById(id).map(yeuCau -> {
            if (!CHO_DUYET.equals(yeuCau.getTrangThai())) {
                throw new IllegalStateException("Yêu cầu này đã được xử lý trước đó.");
            }

            VaiTro vaiTroChuTro = vaiTroRepository.findByTenVaiTro(TEN_VAI_TRO_CHU_TRO)
                    .or(() -> vaiTroRepository.findById(MA_VAI_TRO_CHU_TRO_DU_PHONG))
                    .orElseThrow(() -> new IllegalStateException(
                            "Không tìm thấy vai trò 'Chủ trọ' trong bảng VAI_TRO"));

            NguoiDung nguoiDung = yeuCau.getNguoiDung();
            nguoiDung.setVaiTro(vaiTroChuTro);
            nguoiDung.setNgayCapNhat(LocalDateTime.now());
            nguoiDungRepository.save(nguoiDung);

            yeuCau.setTrangThai(DA_DUYET);
            yeuCau.setNgayXuLy(LocalDateTime.now());
            yeuCau.setLyDoTuChoi(null);
            return yeuCauRepository.save(yeuCau);
        });
    }

    @Transactional
    public Optional<YeuCauChuTro> tuChoi(Integer id, String lyDo) {
        return yeuCauRepository.findById(id).map(yeuCau -> {
            if (!CHO_DUYET.equals(yeuCau.getTrangThai())) {
                throw new IllegalStateException("Yêu cầu này đã được xử lý trước đó.");
            }
            yeuCau.setTrangThai(TU_CHOI);
            yeuCau.setLyDoTuChoi(lyDo);
            yeuCau.setNgayXuLy(LocalDateTime.now());
            return yeuCauRepository.save(yeuCau);
        });
    }
}
