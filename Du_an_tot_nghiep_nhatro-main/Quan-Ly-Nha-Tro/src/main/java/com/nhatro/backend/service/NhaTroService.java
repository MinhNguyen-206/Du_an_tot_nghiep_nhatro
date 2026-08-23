package com.nhatro.backend.service;

import com.nhatro.backend.entity.NhaTro;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.repository.NhaTroRepository;
import com.nhatro.backend.repository.PhongTroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class NhaTroService {

    private final NhaTroRepository nhaTroRepository;
    private final PhongTroRepository phongTroRepository;

    public NhaTroService(
            NhaTroRepository nhaTroRepository,
            PhongTroRepository phongTroRepository
    ) {
        this.nhaTroRepository = nhaTroRepository;
        this.phongTroRepository = phongTroRepository;
    }

    // =========================================================
    // LẤY TẤT CẢ
    // =========================================================

    public List<NhaTro> getAll() {
        return nhaTroRepository.findAll();
    }

    // =========================================================
    // ĐẾM TỔNG SỐ NHÀ TRỌ
    // =========================================================

    public long count() {
        return nhaTroRepository.count();
    }

    // =========================================================
    // LẤY THEO ID
    // =========================================================

    public Optional<NhaTro> getById(Integer id) {
        return nhaTroRepository.findById(id);
    }

    // =========================================================
    // LẤY NHÀ TRỌ CỦA CHỦ TRỌ
    // =========================================================

    public List<NhaTro> getByNguoiDung(Integer maNguoiDung) {
        return nhaTroRepository
                .findByNguoiDung_MaNguoiDung(maNguoiDung);
    }

    // =========================================================
    // THÊM
    // =========================================================

    @Transactional
    public NhaTro createForOwner(
            NhaTroRequest request,
            NguoiDung owner
    ) {

        validate(request);

        if (owner == null
                || owner.getMaNguoiDung() == null) {

            throw new IllegalArgumentException(
                    "Không xác định được chủ trọ."
            );
        }

        NhaTro nhaTro = new NhaTro();

        nhaTro.setNguoiDung(owner);

        nhaTro.setTenNhaTro(
                request.tenNhaTro().trim()
        );

        nhaTro.setDiaChi(
                request.diaChi().trim()
        );

        nhaTro.setSoSao(
                request.soSao()
        );

        nhaTro.setGiaPhong(
                request.giaPhong()
        );

        nhaTro.setLoaiPhong(
                clean(request.loaiPhong())
        );

        nhaTro.setMoTa(
                clean(request.moTa())
        );

        nhaTro.setHinhAnh(
                clean(request.hinhAnh())
        );

        return nhaTroRepository.saveAndFlush(nhaTro);
    }

    // =========================================================
    // SỬA
    // =========================================================

    @Transactional
    public NhaTro updateForOwner(
            Integer id,
            NhaTroRequest request,
            NguoiDung owner
    ) {

        validate(request);

        if (id == null) {
            throw new IllegalArgumentException(
                    "Mã nhà trọ không hợp lệ."
            );
        }

        if (owner == null
                || owner.getMaNguoiDung() == null) {

            throw new IllegalArgumentException(
                    "Không xác định được chủ trọ."
            );
        }

        NhaTro nhaTro =
                nhaTroRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Không tìm thấy nhà trọ."
                                )
                        );

        checkOwner(nhaTro, owner);

        nhaTro.setTenNhaTro(
                request.tenNhaTro().trim()
        );

        nhaTro.setDiaChi(
                request.diaChi().trim()
        );

        nhaTro.setSoSao(
                request.soSao()
        );

        nhaTro.setGiaPhong(
                request.giaPhong()
        );

        nhaTro.setLoaiPhong(
                clean(request.loaiPhong())
        );

        nhaTro.setMoTa(
                clean(request.moTa())
        );

        nhaTro.setHinhAnh(
                clean(request.hinhAnh())
        );

        return nhaTroRepository.saveAndFlush(nhaTro);
    }

    // =========================================================
    // XÓA
    // =========================================================

    @Transactional
    public void deleteForOwner(
            Integer id,
            NguoiDung owner
    ) {

        if (id == null) {
            throw new IllegalArgumentException(
                    "Mã nhà trọ không hợp lệ."
            );
        }

        if (owner == null
                || owner.getMaNguoiDung() == null) {

            throw new IllegalArgumentException(
                    "Không xác định được chủ trọ."
            );
        }

        NhaTro nhaTro =
                nhaTroRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Không tìm thấy nhà trọ."
                                )
                        );

        checkOwner(nhaTro, owner);

        // Không cho xóa nếu còn phòng
        boolean hasRooms =
                !phongTroRepository
                        .findByNhaTro_MaNhaTro(id)
                        .isEmpty();

        if (hasRooms) {

            throw new IllegalStateException(
                    "Không thể xóa nhà trọ vì nhà trọ vẫn còn phòng. "
                            + "Hãy xóa hoặc chuyển các phòng trước."
            );
        }

        nhaTroRepository.delete(nhaTro);
    }

    // =========================================================
    // KIỂM TRA CHỦ SỞ HỮU
    // =========================================================

    private void checkOwner(
            NhaTro nhaTro,
            NguoiDung owner
    ) {

        if (nhaTro == null
                || nhaTro.getNguoiDung() == null
                || nhaTro.getNguoiDung()
                .getMaNguoiDung() == null
                || owner == null
                || owner.getMaNguoiDung() == null
                || !nhaTro.getNguoiDung()
                .getMaNguoiDung()
                .equals(
                        owner.getMaNguoiDung()
                )) {

            throw new IllegalArgumentException(
                    "Bạn không có quyền thao tác nhà trọ này."
            );
        }
    }

    // =========================================================
    // VALIDATE
    // =========================================================

    private void validate(
            NhaTroRequest request
    ) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Dữ liệu nhà trọ không hợp lệ."
            );
        }

        if (request.tenNhaTro() == null
                || request.tenNhaTro().isBlank()) {

            throw new IllegalArgumentException(
                    "Tên nhà trọ không được để trống."
            );
        }

        if (request.diaChi() == null
                || request.diaChi().isBlank()) {

            throw new IllegalArgumentException(
                    "Địa chỉ không được để trống."
            );
        }

        if (request.giaPhong() == null
                || request.giaPhong()
                .compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Giá phòng phải lớn hơn hoặc bằng 0."
            );
        }

        if (request.loaiPhong() == null
                || request.loaiPhong().isBlank()) {

            throw new IllegalArgumentException(
                    "Vui lòng chọn loại phòng."
            );
        }

        if (request.soSao() != null) {

            if (request.soSao()
                    .compareTo(BigDecimal.ZERO) < 0
                    || request.soSao()
                    .compareTo(
                            BigDecimal.valueOf(5)
                    ) > 0) {

                throw new IllegalArgumentException(
                        "Số sao phải từ 0 đến 5."
                );
            }
        }

        // Link ảnh là không bắt buộc.
        // Nếu có thì chỉ cần trim, không bắt buộc
        // phải upload file lên server.
    }

    // =========================================================
    // CLEAN STRING
    // =========================================================

    private String clean(String value) {

        if (value == null) {
            return null;
        }

        String result = value.trim();

        return result.isEmpty()
                ? null
                : result;
    }

    // =========================================================
    // REQUEST
    // =========================================================

    public record NhaTroRequest(

            String tenNhaTro,

            String diaChi,

            BigDecimal soSao,

            BigDecimal giaPhong,

            String loaiPhong,

            String moTa,

            String hinhAnh

    ) {
    }
}