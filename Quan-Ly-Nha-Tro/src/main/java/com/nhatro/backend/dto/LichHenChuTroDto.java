package com.nhatro.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO dùng cho trang JSP chủ trọ.
 * Dùng JavaBean getter để JSP/EL đọc được chắc chắn trên Tomcat/Jakarta EL.
 */
public class LichHenChuTroDto {
    private final Integer maLichHen;
    private final Integer maPhong;
    private final String tenPhong;
    private final String tenNhaTro;
    private final String hoTen;
    private final String soDienThoai;
    private final LocalDate ngayHen;
    private final LocalTime gioHen;
    private final Integer soNguoiDiCung;
    private final String nuoiThuCung;
    private final String thoiGianDuKienDonVao;
    private final String ghiChu;
    private final String trangThai;
    private final String diaDiem;

    public LichHenChuTroDto(Integer maLichHen, Integer maPhong, String tenPhong, String tenNhaTro,
                            String hoTen, String soDienThoai, LocalDate ngayHen, LocalTime gioHen,
                            Integer soNguoiDiCung, String nuoiThuCung, String thoiGianDuKienDonVao,
                            String ghiChu, String trangThai, String diaDiem) {
        this.maLichHen = maLichHen;
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.tenNhaTro = tenNhaTro;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.ngayHen = ngayHen;
        this.gioHen = gioHen;
        this.soNguoiDiCung = soNguoiDiCung;
        this.nuoiThuCung = nuoiThuCung;
        this.thoiGianDuKienDonVao = thoiGianDuKienDonVao;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
        this.diaDiem = diaDiem;
    }

    public Integer getMaLichHen() { return maLichHen; }
    public Integer getMaPhong() { return maPhong; }
    public String getTenPhong() { return tenPhong; }
    public String getTenNhaTro() { return tenNhaTro; }
    public String getHoTen() { return hoTen; }
    public String getSoDienThoai() { return soDienThoai; }
    public LocalDate getNgayHen() { return ngayHen; }
    public LocalTime getGioHen() { return gioHen; }
    public Integer getSoNguoiDiCung() { return soNguoiDiCung; }
    public String getNuoiThuCung() { return nuoiThuCung; }
    public String getThoiGianDuKienDonVao() { return thoiGianDuKienDonVao; }
    public String getGhiChu() { return ghiChu; }
    public String getTrangThai() { return trangThai; }
    public String getDiaDiem() { return diaDiem; }
}
