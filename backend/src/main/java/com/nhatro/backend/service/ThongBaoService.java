package com.nhatro.backend.service;

import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.entity.ThongBao;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ThongBaoService {

    private final List<ThongBao> danhSach = new CopyOnWriteArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public ThongBaoService(NguoiDungService nguoiDungService) {
        Optional<NguoiDung> nguoiThueMau = nguoiDungService.getAll().stream()
                .filter(nd -> nd.getVaiTro() != null && nd.getVaiTro() == 1)
                .findFirst();

        nguoiThueMau.ifPresent(nguoiDung -> {
            danhSach.add(ThongBao.builder()
                    .maThongBao(idCounter.getAndIncrement())
                    .nguoiNhan(nguoiDung)
                    .loaiSuKien("YEU_CAU_THUE_DUOC_CHAP_NHAN")
                    .noiDung("Yeu cau thue phong cua ban da duoc chu tro chap nhan")
                    .daDoc(false)
                    .thoiGian(LocalDateTime.now())
                    .build());

            danhSach.add(ThongBao.builder()
                    .maThongBao(idCounter.getAndIncrement())
                    .nguoiNhan(nguoiDung)
                    .loaiSuKien("HOA_DON_MOI")
                    .noiDung("Hoa don thang nay da duoc tao, vui long thanh toan truoc ngay 5")
                    .daDoc(false)
                    .thoiGian(LocalDateTime.now().minusDays(1))
                    .build());
        });
    }

    public List<ThongBao> getAll() {
        return danhSach;
    }

    public Optional<ThongBao> getById(Long id) {
        return danhSach.stream().filter(t -> t.getMaThongBao().equals(id)).findFirst();
    }

    public ThongBao create(ThongBao thongBao) {
        thongBao.setMaThongBao(idCounter.getAndIncrement());
        thongBao.setThoiGian(LocalDateTime.now());
        if (thongBao.getDaDoc() == null) {
            thongBao.setDaDoc(false);
        }
        danhSach.add(thongBao);
        return thongBao;
    }

    public Optional<ThongBao> update(Long id, ThongBao duLieuMoi) {
        return getById(id).map(t -> {
            t.setDaDoc(duLieuMoi.getDaDoc());
            return t;
        });
    }

    public boolean delete(Long id) {
        return danhSach.removeIf(t -> t.getMaThongBao().equals(id));
    }
}