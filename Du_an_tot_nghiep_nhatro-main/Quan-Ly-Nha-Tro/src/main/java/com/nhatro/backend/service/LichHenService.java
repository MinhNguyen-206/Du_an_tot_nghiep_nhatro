package com.nhatro.backend.service;

import com.nhatro.backend.dto.DatLichXemPhongRequest;
import com.nhatro.backend.dto.DatLichXemPhongResponse;
import com.nhatro.backend.dto.LichHenChuTroDto;
import com.nhatro.backend.entity.LichHen;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.PhongTro;
import com.nhatro.backend.repository.LichHenRepository;
import com.nhatro.backend.repository.NguoiDungRepository;
import com.nhatro.backend.repository.PhongTroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LichHenService {

    private final LichHenRepository lichHenRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final PhongTroRepository phongTroRepository;

    public LichHenService(
            LichHenRepository lichHenRepository,
            NguoiDungRepository nguoiDungRepository,
            PhongTroRepository phongTroRepository) {
        this.lichHenRepository = Objects.requireNonNull(lichHenRepository, "lichHenRepository must not be null");
        this.nguoiDungRepository = Objects.requireNonNull(nguoiDungRepository, "nguoiDungRepository must not be null");
        this.phongTroRepository = Objects.requireNonNull(phongTroRepository, "phongTroRepository must not be null");
    }

    public List<LichHen> getAll() {
        return lichHenRepository.findAll();
    }

    public Optional<LichHen> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return lichHenRepository.findById(id);
    }

    public List<LichHen> getByNguoiDung(Integer maNguoiDung) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        return lichHenRepository.findByNguoiDung_MaNguoiDung(maNguoiDung);
    }

    public List<LichHen> getByPhong(Integer maPhong) {
        Objects.requireNonNull(maPhong, "maPhong must not be null");
        return lichHenRepository.findByPhong_MaPhong(maPhong);
    }

    @Transactional(readOnly = true)
    public List<LichHenChuTroDto> getByChuTro(Integer maChuTro) {
        return lichHenRepository.findByChuTro(maChuTro).stream()
                .map(this::toChuTroDto)
                .toList();
    }

    @Transactional
    public DatLichXemPhongResponse datLichXemPhong(
            DatLichXemPhongRequest request,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vui lòng đăng nhập để đặt lịch xem phòng.");
        }

        NguoiDung user = nguoiDungRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Không xác định được tài khoản."));

        if (request == null || request.getMaPhong() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chưa chọn phòng cần xem.");
        }
        if (request.getNgayHen() == null || request.getGioHen() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vui lòng chọn ngày và giờ xem phòng.");
        }

        LocalDateTime thoiDiemHen = LocalDateTime.of(request.getNgayHen(), request.getGioHen());
        if (thoiDiemHen.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ngày và giờ xem phòng phải ở thời điểm tương lai.");
        }

        PhongTro phong = phongTroRepository.findById(request.getMaPhong())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy phòng.") );

        if (!Boolean.TRUE.equals(phong.getTrangThai())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phòng này hiện không còn trống để đặt lịch xem.");
        }

        String hoTen = clean(request.getHoTen());
        if (hoTen == null) hoTen = clean(user.getHoTen());
        if (hoTen == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vui lòng nhập họ và tên.");
        }

        String soDienThoai = clean(request.getSoDienThoai());
        if (soDienThoai == null) soDienThoai = clean(user.getSoDienThoai());
        if (soDienThoai == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vui lòng nhập số điện thoại.");
        }

        String nuoiThuCung = clean(request.getNuoiThuCung());
        if (nuoiThuCung == null) nuoiThuCung = "Không";

        String duKienDonVao = clean(request.getThoiGianDuKienDonVao());
        if (duKienDonVao == null) duKienDonVao = "Cần phòng ở ngay";

        int soNguoi = request.getSoNguoiDiCung() == null ? 1 : request.getSoNguoiDiCung();
        if (soNguoi < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Số người đi cùng không hợp lệ.");
        }

        String ghiChu = buildGhiChu(hoTen, soDienThoai, nuoiThuCung, duKienDonVao, soNguoi, request.getGhiChu());
        String diaDiem = phong.getNhaTro() != null ? phong.getNhaTro().getDiaChi() : null;

        LichHen lichHen = LichHen.builder()
                .nguoiDung(user)
                .phong(phong)
                .ngayHen(request.getNgayHen())
                .gioHen(request.getGioHen())
                .diaDiem(diaDiem)
                .ghiChu(ghiChu)
                // false = đang xử lý/chờ chủ trọ xác nhận theo schema hiện tại.
                .trangThai(false)
                .build();

        LichHen saved = lichHenRepository.saveAndFlush(lichHen);

        return toResponse(saved, hoTen, soDienThoai, nuoiThuCung, duKienDonVao, request.getGhiChu());
    }

    public LichHen create(LichHen lichHen) {
        Objects.requireNonNull(lichHen, "lichHen must not be null");
        return lichHenRepository.save(lichHen);
    }

    public Optional<LichHen> update(Integer id, LichHen duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return lichHenRepository.findById(id).map(lh -> {
            lh.setNgayHen(duLieuMoi.getNgayHen());
            lh.setGioHen(duLieuMoi.getGioHen());
            lh.setDiaDiem(duLieuMoi.getDiaDiem());
            lh.setGhiChu(duLieuMoi.getGhiChu());
            lh.setTrangThai(duLieuMoi.getTrangThai());
            return lichHenRepository.save(lh);
        });
    }

    public boolean delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        if (lichHenRepository.existsById(id)) {
            lichHenRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private LichHenChuTroDto toChuTroDto(LichHen lh) {
        PhongTro phong = lh.getPhong();
        NguoiDung user = lh.getNguoiDung();
        String[] extra = parseExtra(lh.getGhiChu());

        return new LichHenChuTroDto(
                lh.getMaLichHen(),
                phong == null ? null : phong.getMaPhong(),
                phong == null ? "Phòng" : phong.getTenPhong(),
                phong != null && phong.getNhaTro() != null ? phong.getNhaTro().getTenNhaTro() : "Nhà trọ",
                extra[0] != null ? extra[0] : (user == null ? "Khách" : user.getHoTen()),
                extra[1] != null ? extra[1] : (user == null ? null : user.getSoDienThoai()),
                lh.getNgayHen(),
                lh.getGioHen(),
                extractSoNguoi(lh.getGhiChu()),
                extra[2] != null ? extra[2] : "Không",
                extra[3] != null ? extra[3] : "Cần phòng ở ngay",
                extra[4],
                Boolean.TRUE.equals(lh.getTrangThai()) ? "Đã xác nhận" : "Đang xử lý",
                lh.getDiaDiem()
        );
    }

    private DatLichXemPhongResponse toResponse(
            LichHen lh,
            String hoTen,
            String soDienThoai,
            String nuoiThuCung,
            String duKienDonVao,
            String ghiChuGoc) {
        return new DatLichXemPhongResponse(
                lh.getMaLichHen(),
                lh.getPhong().getMaPhong(),
                lh.getPhong().getTenPhong(),
                hoTen,
                soDienThoai,
                lh.getNgayHen(),
                lh.getGioHen(),
                extractSoNguoi(lh.getGhiChu()),
                nuoiThuCung,
                duKienDonVao,
                ghiChuGoc == null ? "" : ghiChuGoc,
                "Đang xử lý",
                lh.getDiaDiem()
        );
    }

    private String buildGhiChu(String hoTen, String soDienThoai, String nuoiThuCung,
                               String duKienDonVao, Integer soNguoi, String ghiChuGoc) {
        StringBuilder sb = new StringBuilder();
        sb.append("[HO_TEN]").append(hoTen).append("\n");
        sb.append("[SO_DIEN_THOAI]").append(soDienThoai).append("\n");
        sb.append("[NUOI_THU_CUNG]").append(nuoiThuCung).append("\n");
        sb.append("[DU_KIEN_DON_VAO]").append(duKienDonVao).append("\n");
        sb.append("[SO_NGUOI]").append(soNguoi == null ? 1 : soNguoi).append("\n");
        sb.append("[GHI_CHU]").append(clean(ghiChuGoc) == null ? "" : clean(ghiChuGoc));
        return sb.toString();
    }

    private String[] parseExtra(String raw) {
        String[] result = new String[5];
        if (raw == null || raw.isBlank()) return result;
        for (String line : raw.split("\\R")) {
            if (line.startsWith("[HO_TEN]")) result[0] = line.substring(8).trim();
            else if (line.startsWith("[SO_DIEN_THOAI]")) result[1] = line.substring(15).trim();
            else if (line.startsWith("[NUOI_THU_CUNG]")) result[2] = line.substring(15).trim();
            else if (line.startsWith("[DU_KIEN_DON_VAO]")) result[3] = line.substring(17).trim();
            else if (line.startsWith("[GHI_CHU]")) result[4] = line.substring(9).trim();
        }
        return result;
    }

    private Integer parseSoNguoi(String value) {
        if (value == null) return 1;
        return extractSoNguoi(value);
    }

    private Integer extractSoNguoi(String raw) {
        if (raw == null) return 1;
        for (String line : raw.split("\\R")) {
            if (line.startsWith("[SO_NGUOI]")) {
                try { return Integer.valueOf(line.substring(10).trim()); }
                catch (NumberFormatException ignored) { return 1; }
            }
        }
        return 1;
    }

    private String clean(String value) {
        if (value == null) return null;
        String v = value.trim();
        return v.isBlank() ? null : v;
    }
}
