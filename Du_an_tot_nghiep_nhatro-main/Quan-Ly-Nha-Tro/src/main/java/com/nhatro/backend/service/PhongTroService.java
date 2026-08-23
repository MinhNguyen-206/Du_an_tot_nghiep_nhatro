package com.nhatro.backend.service;

import com.nhatro.backend.entity.NhaTro;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.PhongTro;
import com.nhatro.backend.repository.NhaTroRepository;
import com.nhatro.backend.repository.PhongTroRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PhongTroService {

    private final PhongTroRepository phongTroRepository;
    private final NhaTroRepository nhaTroRepository;

    public PhongTroService(
            PhongTroRepository phongTroRepository,
            NhaTroRepository nhaTroRepository
    ) {
        Objects.requireNonNull(phongTroRepository, "phongTroRepository must not be null");
        Objects.requireNonNull(nhaTroRepository, "nhaTroRepository must not be null");
        this.phongTroRepository = phongTroRepository;
        this.nhaTroRepository = nhaTroRepository;
    }

    public List<PhongTro> getAll() {
        return phongTroRepository.findAll();
    }

    public Optional<PhongTro> getById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        return phongTroRepository.findById(id);
    }

    public List<PhongTro> getByNhaTro(Integer maNhaTro) {
        Objects.requireNonNull(maNhaTro, "maNhaTro must not be null");
        return phongTroRepository.findByNhaTro_MaNhaTro(maNhaTro);
    }

    public List<PhongTro> getByTrangThai(Boolean trangThai) {
        Objects.requireNonNull(trangThai, "trangThai must not be null");
        return phongTroRepository.findByTrangThai(trangThai);
    }

    public PhongTro create(PhongTro phongTro) {
        Objects.requireNonNull(phongTro, "phongTro must not be null");
        return phongTroRepository.save(phongTro);
    }


    /**
     * Tạo phòng thuộc một nhà trọ của chính chủ trọ đang đăng nhập.
     * Không cho phép gửi maNhaTro của người khác để tạo phòng.
     */
    @Transactional
    public PhongTro createForOwner(
            Integer maNhaTro,
            String tenPhong,
            java.math.BigDecimal dienTich,
            String loaiPhong,
            Integer soLuongNguoi,
            java.math.BigDecimal giaPhong,
            java.math.BigDecimal giaDien,
            java.math.BigDecimal giaNuoc,
            java.math.BigDecimal giaGuiXe,
            java.math.BigDecimal giaInternet,
            Boolean trangThai,
            NguoiDung owner
    ) {
        if (maNhaTro == null) {
            throw new IllegalArgumentException("Vui lòng chọn nhà trọ.");
        }

        if (owner == null || owner.getMaNguoiDung() == null) {
            throw new IllegalArgumentException("Không xác định được chủ trọ.");
        }

        if (tenPhong == null || tenPhong.isBlank()) {
            throw new IllegalArgumentException("Tên/số phòng không được để trống.");
        }

        if (giaPhong == null || giaPhong.compareTo(java.math.BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Giá phòng phải lớn hơn hoặc bằng 0.");
        }

        if (dienTich != null && dienTich.compareTo(java.math.BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Diện tích không hợp lệ.");
        }

        if (soLuongNguoi != null && soLuongNguoi < 1) {
            throw new IllegalArgumentException("Số người tối đa phải từ 1 trở lên.");
        }

        NhaTro nhaTro = nhaTroRepository.findById(maNhaTro)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhà trọ."));

        if (nhaTro.getNguoiDung() == null
                || nhaTro.getNguoiDung().getMaNguoiDung() == null
                || !owner.getMaNguoiDung().equals(
                nhaTro.getNguoiDung().getMaNguoiDung())) {
            throw new IllegalArgumentException(
                    "Bạn không có quyền thêm phòng vào nhà trọ này."
            );
        }

        String roomName = tenPhong.trim();

        boolean duplicate = phongTroRepository
                .findByNhaTro_MaNhaTro(maNhaTro)
                .stream()
                .anyMatch(p -> p.getTenPhong() != null
                        && p.getTenPhong().trim().equalsIgnoreCase(roomName));

        if (duplicate) {

            throw new IllegalArgumentException(
                    "Phòng \"" + roomName + "\" đã tồn tại trong nhà trọ này."
            );
        }

        PhongTro phong = new PhongTro();
        phong.setNhaTro(nhaTro);
        phong.setTenPhong(roomName);
        phong.setDienTich(dienTich);
        phong.setLoaiPhong(
                loaiPhong == null || loaiPhong.isBlank()
                        ? "Phòng tiêu chuẩn"
                        : loaiPhong.trim()
        );
        phong.setSoLuongNguoi(
                soLuongNguoi == null ? 1 : soLuongNguoi
        );
        phong.setGiaPhong(giaPhong);
        phong.setGiaDien(giaDien);
        phong.setGiaNuoc(giaNuoc);
        phong.setGiaGuiXe(giaGuiXe);
        phong.setGiaInternet(giaInternet);
        // true = còn trống, false = đang thuê theo thiết kế hiện tại.
        phong.setTrangThai(trangThai == null || trangThai);

        return phongTroRepository.saveAndFlush(phong);
    }


    /**
     * Cập nhật phòng nhưng bắt buộc phòng phải thuộc nhà trọ của Chủ trọ đang đăng nhập.
     */
    @Transactional
    public PhongTro updateForOwner(
            Integer id,
            Integer maNhaTro,
            String tenPhong,
            java.math.BigDecimal dienTich,
            String loaiPhong,
            Integer soLuongNguoi,
            java.math.BigDecimal giaPhong,
            java.math.BigDecimal giaDien,
            java.math.BigDecimal giaNuoc,
            java.math.BigDecimal giaGuiXe,
            java.math.BigDecimal giaInternet,
            Boolean trangThai,
            NguoiDung owner
    ) {
        if (id == null) throw new IllegalArgumentException("Không xác định được phòng cần sửa.");
        if (owner == null || owner.getMaNguoiDung() == null) throw new IllegalArgumentException("Không xác định được chủ trọ.");
        if (maNhaTro == null) throw new IllegalArgumentException("Vui lòng chọn nhà trọ.");
        if (tenPhong == null || tenPhong.isBlank()) throw new IllegalArgumentException("Tên/số phòng không được để trống.");
        if (giaPhong == null || giaPhong.compareTo(java.math.BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Giá phòng phải lớn hơn hoặc bằng 0.");
        if (dienTich != null && dienTich.compareTo(java.math.BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Diện tích không hợp lệ.");
        if (soLuongNguoi != null && soLuongNguoi < 1) throw new IllegalArgumentException("Số người tối đa phải từ 1 trở lên.");

        PhongTro phong = phongTroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phòng."));
        NhaTro nhaTro = nhaTroRepository.findById(maNhaTro)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhà trọ."));

        Integer ownerId = nhaTro.getNguoiDung() == null ? null : nhaTro.getNguoiDung().getMaNguoiDung();
        if (!owner.getMaNguoiDung().equals(ownerId)) {
            throw new IllegalArgumentException("Bạn không có quyền sửa phòng này.");
        }

        Integer currentPropertyId = phong.getNhaTro() == null ? null : phong.getNhaTro().getMaNhaTro();
        if (currentPropertyId == null || !maNhaTro.equals(currentPropertyId)) {
            throw new IllegalArgumentException("Không thể chuyển phòng sang nhà trọ khác.");
        }

        String roomName = tenPhong.trim();
        boolean duplicate = phongTroRepository.findByNhaTro_MaNhaTro(maNhaTro)
                .stream()
                .anyMatch(p -> !id.equals(p.getMaPhong())
                        && p.getTenPhong() != null
                        && p.getTenPhong().trim().equalsIgnoreCase(roomName));

        if (duplicate) {
            throw new IllegalArgumentException("Phòng \"" + roomName + "\" đã tồn tại trong nhà trọ này.");
        }

        phong.setNhaTro(nhaTro);
        phong.setTenPhong(roomName);
        phong.setDienTich(dienTich);
        phong.setLoaiPhong(loaiPhong == null || loaiPhong.isBlank() ? "Phòng tiêu chuẩn" : loaiPhong.trim());
        phong.setSoLuongNguoi(soLuongNguoi == null ? 1 : soLuongNguoi);
        phong.setGiaPhong(giaPhong);
        phong.setGiaDien(giaDien);
        phong.setGiaNuoc(giaNuoc);
        phong.setGiaGuiXe(giaGuiXe);
        phong.setGiaInternet(giaInternet);
        phong.setTrangThai(trangThai == null || trangThai);

        return phongTroRepository.saveAndFlush(phong);
    }

    /**
     * Xóa phòng của chính Chủ trọ. Chỉ cho xóa khi phòng đang trống.
     */
    @Transactional
    public void deleteForOwner(Integer id, NguoiDung owner) {
        if (id == null) throw new IllegalArgumentException("Không xác định được phòng cần xóa.");
        if (owner == null || owner.getMaNguoiDung() == null) throw new IllegalArgumentException("Không xác định được chủ trọ.");

        PhongTro phong = phongTroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phòng."));

        NhaTro nhaTro = phong.getNhaTro();
        Integer propertyOwnerId = nhaTro == null || nhaTro.getNguoiDung() == null
                ? null : nhaTro.getNguoiDung().getMaNguoiDung();

        if (!owner.getMaNguoiDung().equals(propertyOwnerId)) {
            throw new IllegalArgumentException("Bạn không có quyền xóa phòng này.");
        }

        if (Boolean.FALSE.equals(phong.getTrangThai())) {
            throw new IllegalStateException("Không thể xóa phòng đang có người thuê. Hãy hoàn tất hợp đồng trước.");
        }

        phongTroRepository.delete(phong);
        phongTroRepository.flush();
    }

    public Optional<PhongTro> update(Integer id, PhongTro duLieuMoi) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(duLieuMoi, "duLieuMoi must not be null");
        return phongTroRepository.findById(id).map(p -> {
            p.setTenPhong(duLieuMoi.getTenPhong());
            p.setDienTich(duLieuMoi.getDienTich());
            p.setLoaiPhong(duLieuMoi.getLoaiPhong());
            p.setSoLuongNguoi(duLieuMoi.getSoLuongNguoi());
            p.setGiaPhong(duLieuMoi.getGiaPhong());
            p.setGiaDien(duLieuMoi.getGiaDien());
            p.setGiaNuoc(duLieuMoi.getGiaNuoc());
            p.setGiaGuiXe(duLieuMoi.getGiaGuiXe());
            p.setGiaInternet(duLieuMoi.getGiaInternet());
            p.setTrangThai(duLieuMoi.getTrangThai());
            return phongTroRepository.save(p);
        });
    }

    public boolean delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        if (phongTroRepository.existsById(id)) {
            phongTroRepository.deleteById(id);
            return true;
        }
        return false;
    }
}