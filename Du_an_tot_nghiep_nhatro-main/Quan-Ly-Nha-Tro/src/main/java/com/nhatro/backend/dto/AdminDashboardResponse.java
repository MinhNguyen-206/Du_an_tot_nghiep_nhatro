package com.nhatro.backend.dto;

import java.util.List;

public record AdminDashboardResponse(
        long totalUsers,
        long totalProperties,
        long totalRooms,
        long totalPosts,
        long totalAppointments,
        long totalContracts,
        long totalTransactions,
        long totalReports,
        long totalRevenue,
        long pendingUsers,
        long pendingPosts,
        long pendingReports,
        List<MonthlyRevenue> monthlyRevenue,
        List<DashboardActivity> activities
) {
    public record MonthlyRevenue(String month, long value) {
    }

    public record DashboardActivity(
            String icon,
            String color,
            String title,
            String detail,
            String time,
            String tag,
            String tagColor
    ) {
    }
}