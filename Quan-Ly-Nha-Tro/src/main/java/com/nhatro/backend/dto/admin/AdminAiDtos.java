package com.nhatro.backend.dto.admin;

import java.time.LocalDateTime;
import java.util.List;

public final class AdminAiDtos {

    private AdminAiDtos() {
    }

    // ==========================================
    // UC-ADMIN-AI-12
    // AI DỰ ĐOÁN BÀI ĐĂNG TIỀM NĂNG
    // ==========================================

    public record PotentialPost(
            Integer maDangTin,
            String tieuDe,
            String diaChi,
            long luotXem,
            long luotLuu,
            int diemTiemNang,
            String mucDo,
            String lyDo
    ) {
    }


    // ==========================================
    // UC-ADMIN-AI-13
    // AI GỢI Ý KHU VỰC HOT
    // ==========================================

    public record HotArea(
            String khuVuc,
            long luotXem,
            long soPhong,
            int diemNhuCau,
            String xuHuong
    ) {
    }


    // ==========================================
    // UC-ADMIN-AI-14
    // AI PHÂN LOẠI BÁO CÁO VI PHẠM
    // ==========================================

    public record ReportClassification(
            Integer maBaoCao,
            String lyDo,
            String noiDung,
            String nhom,
            String mucDo,
            int diem,
            String phanTich,
            LocalDateTime ngayBaoCao
    ) {
    }


    // ==========================================
    // UC-ADMIN-AI-15
    // AI THỐNG KÊ HÀNH VI NGƯỜI DÙNG
    // ==========================================

    public record BehaviorStats(
            long luotXemPhong,
            long luotChat,
            long nguoiDungXemPhong,
            long nguoiDungChat,
            long danhGia,
            String xuHuong,
            List<BehaviorItem> theoNhom
    ) {
    }


    public record BehaviorItem(
            String nhom,
            long luotXem,
            long luotChat,
            long danhGia
    ) {
    }


    // ==========================================
    // UC-ADMIN-AI-16
    // AI GỢI Ý XỬ LÝ KHIẾU NẠI
    // ==========================================

    public record ComplaintSuggestion(
            Integer maBaoCao,
            String nhom,
            String mucDo,
            String deXuat,
            int doTuongDong,
            String canCu
    ) {
    }


    // ==========================================
    // CÁC DTO AI ADMIN KHÁC
    // ==========================================

    public record ReviewModeration(
            Integer maDanhGia,
            Integer maNguoiDung,
            String nguoiDung,
            Integer maPhong,
            Integer soSao,
            String noiDung,
            LocalDateTime ngayDanhGia,
            Boolean trangThai,
            long soBaoCao
    ) {
    }


    public record PremiumTransaction(
            Integer maHoaDon,
            Integer maDangKy,
            Integer maHopDong,
            String soHopDong,
            String nguoiMua,
            String email,
            String goi,
            java.math.BigDecimal soTien,
            LocalDateTime ngayLap,
            String trangThai,
            String fileHopDong
    ) {
    }


    public record SystemConfig(
            String khoa,
            String giaTri
    ) {
    }
}

