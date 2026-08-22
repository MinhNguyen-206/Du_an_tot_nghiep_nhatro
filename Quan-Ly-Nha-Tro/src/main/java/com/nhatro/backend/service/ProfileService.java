package com.nhatro.backend.service;

import com.nhatro.backend.dto.*;
import com.nhatro.backend.entity.*;
import com.nhatro.backend.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class ProfileService {
    private final NguoiDungRepository nguoiDungRepository;
    private final PhongYeuThichRepository phongYeuThichRepository;
    private final LichSuXemPhongRepository lichSuXemPhongRepository;
    private final PhongTroRepository phongTroRepository;
    private final LichHenRepository lichHenRepository;
    private final HopDongDienTuRepository hopDongRepository;
    private final PhongCardService phongCardService;

    public ProfileService(
            NguoiDungRepository nguoiDungRepository,
            PhongYeuThichRepository phongYeuThichRepository,
            LichSuXemPhongRepository lichSuXemPhongRepository,
            PhongTroRepository phongTroRepository,
            LichHenRepository lichHenRepository,
            HopDongDienTuRepository hopDongRepository,
            PhongCardService phongCardService) {
        this.nguoiDungRepository = nguoiDungRepository;
        this.phongYeuThichRepository = phongYeuThichRepository;
        this.lichSuXemPhongRepository = lichSuXemPhongRepository;
        this.phongTroRepository = phongTroRepository;
        this.lichHenRepository = lichHenRepository;
        this.hopDongRepository = hopDongRepository;
        this.phongCardService = phongCardService;
    }

    @Transactional(readOnly = true)
    public ProfileResponse getProfile(Integer userId, Authentication authentication) {
        NguoiDung user = requireSelf(userId, authentication);
        List<PhongYeuThich> saved = phongYeuThichRepository.findByNguoiDung_MaNguoiDungOrderByNgayLuuDesc(userId);
        List<LichSuXemPhong> history = lichSuXemPhongRepository.findByNguoiDung_MaNguoiDungOrderByThoiGianXemDesc(userId);
        LocalDate today = LocalDate.now();
        List<LichHen> appointments = lichHenRepository.findByNguoiDung_MaNguoiDung(userId).stream()
                .filter(lh -> lh.getNgayHen() != null && !lh.getNgayHen().isBefore(today))
                .sorted(Comparator.comparing(LichHen::getNgayHen).thenComparing(lh -> lh.getGioHen() == null ? java.time.LocalTime.MAX : lh.getGioHen()))
                .limit(6)
                .toList();
        List<HopDongDienTu> contracts = hopDongRepository.findByNguoiThue_MaNguoiDung(userId).stream()
                .sorted(Comparator.comparing(HopDongDienTu::getNgayBatDau, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(4)
                .toList();

        List<PhongCardDto> savedCards = saved.stream().limit(6)
                .map(x -> phongCardService.toCard(x.getPhong(), x.getNgayLuu())).toList();
        List<PhongCardDto> historyCards = history.stream().limit(6)
                .map(x -> phongCardService.toCard(x.getPhong(), x.getThoiGianXem())).toList();
        List<ProfileContractDto> contractDtos = contracts.stream().map(this::toContract).toList();
        List<ProfileAppointmentDto> appointmentDtos = appointments.stream().map(this::toAppointment).toList();

        return new ProfileResponse(
                user,
                saved.size(),
                appointmentDtos.size(),
                savedCards,
                historyCards,
                contractDtos,
                appointmentDtos
        );
    }

    @Transactional
    public void saveRoom(Integer userId, Integer roomId, Authentication authentication) {
        NguoiDung user = requireSelf(userId, authentication);
        if (!phongTroRepository.existsById(roomId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Phòng không tồn tại");
        }
        if (!phongYeuThichRepository.existsByNguoiDung_MaNguoiDungAndPhong_MaPhong(userId, roomId)) {
            phongYeuThichRepository.save(PhongYeuThich.builder()
                    .nguoiDung(user)
                    .phong(phongTroRepository.getReferenceById(roomId))
                    .build());
        }
    }

    @Transactional
    public void unsaveRoom(Integer userId, Integer roomId, Authentication authentication) {
        requireSelf(userId, authentication);
        phongYeuThichRepository.findByNguoiDung_MaNguoiDungAndPhong_MaPhong(userId, roomId)
                .ifPresent(phongYeuThichRepository::delete);
    }

    @Transactional
    public void recordView(Integer userId, Integer roomId, Authentication authentication) {
        NguoiDung user = requireSelf(userId, authentication);
        PhongTro room = phongTroRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Phòng không tồn tại"));
        lichSuXemPhongRepository.save(LichSuXemPhong.builder()
                .nguoiDung(user)
                .phong(room)
                .build());
    }

    @Transactional
    public ProfileAppointmentDto reschedule(Integer userId, Integer appointmentId, LichHen input, Authentication authentication) {
        requireSelf(userId, authentication);
        LichHen lh = lichHenRepository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy lịch hẹn"));
        if (lh.getNguoiDung() == null || !Objects.equals(lh.getNguoiDung().getMaNguoiDung(), userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không có quyền sửa lịch hẹn này");
        }
        if (input.getNgayHen() == null || input.getGioHen() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vui lòng chọn ngày và giờ hẹn");
        }
        lh.setNgayHen(input.getNgayHen());
        lh.setGioHen(input.getGioHen());
        if (input.getDiaDiem() != null) lh.setDiaDiem(input.getDiaDiem());
        if (input.getGhiChu() != null) lh.setGhiChu(input.getGhiChu());
        return toAppointment(lichHenRepository.save(lh));
    }

    @Transactional
    public void cancelAppointment(Integer userId, Integer appointmentId, Authentication authentication) {
        requireSelf(userId, authentication);
        LichHen lh = lichHenRepository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy lịch hẹn"));
        if (lh.getNguoiDung() == null || !Objects.equals(lh.getNguoiDung().getMaNguoiDung(), userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không có quyền hủy lịch hẹn này");
        }
        lichHenRepository.delete(lh);
    }

    private NguoiDung requireSelf(Integer userId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Chưa đăng nhập");
        }
        NguoiDung user = nguoiDungRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));
        boolean admin = authentication.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()) || "ADMIN".equals(a.getAuthority()));
        if (!admin && (user.getEmail() == null || !user.getEmail().equalsIgnoreCase(authentication.getName()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không có quyền truy cập hồ sơ này");
        }
        return user;
    }

    private ProfileContractDto toContract(HopDongDienTu hd) {
        PhongTro room = hd.getPhong();
        String tenPhong = room != null ? room.getTenPhong() : "Phòng thuê";
        String tenNhaTro = room != null && room.getNhaTro() != null ? room.getNhaTro().getTenNhaTro() : "Nhà trọ";
        return new ProfileContractDto(hd.getMaHopDong(), room == null ? null : room.getMaPhong(), tenPhong, tenNhaTro,
                hd.getNgayBatDau(), hd.getNgayKetThuc(), hd.getGiaThue(), hd.getTrangThai(), hd.getFileHopDong());
    }

    private ProfileAppointmentDto toAppointment(LichHen lh) {
        PhongTro room = lh.getPhong();
        String tenPhong = room != null ? room.getTenPhong() : "Phòng thuê";
        String tenNhaTro = room != null && room.getNhaTro() != null ? room.getNhaTro().getTenNhaTro() : "Nhà trọ";
        return new ProfileAppointmentDto(lh.getMaLichHen(), room == null ? null : room.getMaPhong(), tenPhong, tenNhaTro,
                lh.getDiaDiem(), lh.getNgayHen(), lh.getGioHen(), lh.getTrangThai(), lh.getGhiChu());
    }
}
