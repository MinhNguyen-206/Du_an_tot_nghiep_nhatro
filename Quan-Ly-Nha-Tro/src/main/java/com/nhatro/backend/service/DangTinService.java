package com.nhatro.backend.service;

import com.nhatro.backend.entity.DangTin;
import com.nhatro.backend.repository.DangTinRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Nghiep vu DangTin dung chung (CRUD co ban + truy van cong khai).
 *
 * LUU Y VE THIET KE: "trangThai" (Boolean) va "trangThaiDuyet" (String) la
 * 2 truong KHAC NHAU tren entity DangTin:
 *  - trangThai       : bai dang con hieu luc / con hien thi hay khong (on/off)
 *  - trangThaiDuyet  : ket qua kiem duyet cua Admin - CHO_DUYET | DA_DUYET | TU_CHOI
 *    (xem AdminDangTinService cho nghiep vu duyet/tu choi day du, dung cho
 *    man hinh Admin; cac ham duyet()/tuChoi() o day la ban rut gon, KHONG
 *    ghi nhat ky hoat dong va khong luu nguoiDuyet).
 */
@Service
public class DangTinService {

    public static final String CHO_DUYET = "CHO_DUYET";
    public static final String DA_DUYET = "DA_DUYET";
    public static final String TU_CHOI = "TU_CHOI";

    private final DangTinRepository dangTinRepository;

    public DangTinService(DangTinRepository dangTinRepository) {
        this.dangTinRepository = Objects.requireNonNull(dangTinRepository, "dangTinRepository must not be null");
    }

    @Transactional(readOnly = true)
    public List<DangTin> getAll() {
        return dangTinRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<DangTin> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return dangTinRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<DangTin> getByNguoiDung(Integer maNguoiDung) {
        Objects.requireNonNull(maNguoiDung, "maNguoiDung must not be null");
        return dangTinRepository.findByNguoiDung_MaNguoiDung(maNguoiDung);
    }

    @Transactional(readOnly = true)
    public List<DangTin> getByPhong(Integer maPhong) {
        Objects.requireNonNull(maPhong, "maPhong must not be null");
        return dangTinRepository.findByPhong_MaPhong(maPhong);
    }

    /** Bai dang dang hoat dong cong khai: con hieu luc VA da duoc Admin duyet. */
    @Transactional(readOnly = true)
    public List<DangTin> getDangHoatDong() {
        return dangTinRepository.findByTrangThai(Boolean.TRUE).stream()
                .filter(dt -> dt.getTrangThaiDuyet() == null || DA_DUYET.equals(dt.getTrangThaiDuyet()))
                .toList();
    }

    /** Lấy tất cả có phân trang, sắp xếp theo ngày đăng mới nhất */
    @Transactional(readOnly = true)
    public Page<DangTin> getAllPaged(Pageable pageable) {
        return dangTinRepository.findAllByOrderByNgayDangDesc(pageable);
    }

    /** Lấy theo trạng thái duyệt (CHO_DUYET/DA_DUYET/TU_CHOI), có phân trang */
    @Transactional(readOnly = true)
    public Page<DangTin> getByTrangThaiDuyetPaged(String trangThaiDuyet, Pageable pageable) {
        Objects.requireNonNull(trangThaiDuyet, "trangThaiDuyet must not be null");
        return dangTinRepository.findByTrangThaiDuyetOrderByNgayDangDesc(trangThaiDuyet, pageable);
    }

    @Transactional(readOnly = true)
    public long countByTrangThaiDuyet(String trangThaiDuyet) {
        Objects.requireNonNull(trangThaiDuyet, "trangThaiDuyet must not be null");
        return dangTinRepository.countByTrangThaiDuyet(trangThaiDuyet);
    }

    /**
     * Admin duyệt bài đăng (bản rút gọn - không ghi nhật ký/người duyệt).
     * Nếu cần đầy đủ nghiệp vụ Admin (ghi log, lưu người duyệt), dùng
     * {@code AdminDangTinService#duyet(Integer, Integer)} thay cho hàm này.
     */
    @Transactional
    public Optional<DangTin> duyet(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return dangTinRepository.findById(id).map(dt -> {
            dt.setTrangThaiDuyet(DA_DUYET);
            dt.setLyDoTuChoi(null);
            dt.setNgayDuyet(LocalDateTime.now());
            return dangTinRepository.save(dt);
        });
    }

    /** Admin từ chối bài đăng (bản rút gọn - xem ghi chú ở duyet()). */
    @Transactional
    public Optional<DangTin> tuChoi(Integer id, String lyDo) {
        Objects.requireNonNull(id, "id must not be null");
        return dangTinRepository.findById(id).map(dt -> {
            dt.setTrangThaiDuyet(TU_CHOI);
            dt.setLyDoTuChoi(lyDo);
            dt.setNgayDuyet(LocalDateTime.now());
            return dangTinRepository.save(dt);
        });
    }

    @Transactional
    public DangTin create(DangTin dangTin) {
        Objects.requireNonNull(dangTin, "dangTin must not be null");
        return dangTinRepository.save(dangTin);
    }

    @Transactional
    public Optional<DangTin> update(Integer id, DangTin duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return dangTinRepository.findById(id).map(dt -> {
            dt.setTieuDe(duLieuMoi.getTieuDe());
            dt.setNoiDung(duLieuMoi.getNoiDung());
            dt.setNgayHetHan(duLieuMoi.getNgayHetHan());
            dt.setTrangThai(duLieuMoi.getTrangThai());
            return dangTinRepository.save(dt);
        });
    }

    @Transactional
    public boolean delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        if (dangTinRepository.existsById(id)) {
            dangTinRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
