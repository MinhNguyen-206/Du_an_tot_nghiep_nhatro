<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Room Connect</title>

    <!-- Google Fonts & Icons -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Stylesheet -->
    <link rel="stylesheet" href="<c:url value='/resources/css/admin-dashboard.css'/>">
</head>

<body class="admin-body admin-page">

    <!-- HEADER -->
    <jsp:include page="/WEB-INF/jsp/common/header.jsp" />

    <!-- ADMIN LAYOUT -->
    <div class="admin-shell">

        <!-- SIDEBAR -->
        <aside class="admin-sidebar">

            <!-- BRAND + ADMIN -->
            <div class="sidebar-top">

                <!-- BRAND -->
                <div class="admin-brand">
                    <div class="admin-brand-icon">
                        <i class="fa-solid fa-shield-halved"></i>
                    </div>
                    <div>
                        <strong>Room Connect</strong>
                        <span>ADMINISTRATION</span>
                    </div>
                </div>

            </div>

            <!-- MENU -->
            <nav class="sidebar-nav">

                <!-- TỔNG QUAN -->
                <div class="nav-heading">TỔNG QUAN</div>

                <a href="<c:url value='/admin/dashboard'/>" class="sidebar-link active">
                    <i class="fa-solid fa-chart-pie"></i>
                    <span>Tổng quan</span>
                </a>

                <!-- QUẢN LÝ HỆ THỐNG -->
                <div class="nav-heading">QUẢN LÝ HỆ THỐNG</div>

                <a href="<c:url value='/admin/users'/>" class="sidebar-link">
                    <i class="fa-solid fa-users"></i>
                    <span>Người dùng</span>
                    <span class="nav-count">24</span>
                </a>

                <a href="<c:url value='/admin/posts'/>" class="sidebar-link">
                    <i class="fa-solid fa-house"></i>
                    <span>Bài đăng & phòng trọ</span>
                    <span class="nav-count warning">12</span>
                </a>

                <a href="<c:url value='/admin/appointments'/>" class="sidebar-link">
                    <i class="fa-solid fa-calendar-check"></i>
                    <span>Lịch hẹn</span>
                </a>

                <a href="<c:url value='/admin/contracts'/>" class="sidebar-link">
                    <i class="fa-solid fa-file-contract"></i>
                    <span>Hợp đồng</span>
                </a>

                <a href="<c:url value='/admin/payments'/>" class="sidebar-link">
                    <i class="fa-solid fa-wallet"></i>
                    <span>Thanh toán</span>
                </a>

                <!-- KIỂM SOÁT -->
                <div class="nav-heading">KIỂM SOÁT</div>

                <a href="<c:url value='/admin/reports'/>" class="sidebar-link">
                    <i class="fa-solid fa-flag"></i>
                    <span>Báo cáo vi phạm</span>
                    <span class="nav-count danger">5</span>
                </a>

                <a href="<c:url value='/admin/settings'/>" class="sidebar-link">
                    <i class="fa-solid fa-gear"></i>
                    <span>Cài đặt hệ thống</span>
                </a>

            </nav>

        </aside>

        <!-- MAIN CONTENT -->
        <main class="admin-main">

            <!-- TOP BAR -->
            <div class="dashboard-topbar">

                <div class="dashboard-heading">
                    <div class="breadcrumb">
                        <span>Admin</span>
                        <i class="fa-solid fa-chevron-right"></i>
                        <strong>Tổng quan</strong>
                    </div>

                    <h1>Dashboard thống kê</h1>
                    <p>Ban Quản trị · Tổng quan toàn hệ thống</p>
                </div>

                <div class="period-switcher" role="group" aria-label="Khoảng thời gian thống kê">
                    <button type="button">Ngày</button>
                    <button type="button" class="active">Tháng</button>
                    <button type="button">Quý</button>
                    <button type="button">Năm</button>
                </div>

            </div>

            <!-- KPI CARDS -->
            <section class="kpi-grid">

                <!-- USERS -->
                <div class="kpi-card">
                    <div class="kpi-icon green">
                        <i class="fa-solid fa-users"></i>
                    </div>
                    <div class="kpi-content">
                        <span class="kpi-label">Tổng người dùng</span>
                        <strong class="kpi-value">1,284</strong>
                        <span class="kpi-change positive">
                            <i class="fa-solid fa-arrow-up"></i>
                            8.4%
                            <small>so với tháng trước</small>
                        </span>
                    </div>
                    <span class="kpi-sparkline green-line"></span>
                </div>

                <!-- POSTS -->
                <div class="kpi-card">
                    <div class="kpi-icon brown">
                        <i class="fa-solid fa-house"></i>
                    </div>
                    <div class="kpi-content">
                        <span class="kpi-label">Tổng bài đăng</span>
                        <strong class="kpi-value">856</strong>
                        <span class="kpi-change positive">
                            <i class="fa-solid fa-arrow-up"></i>
                            5.2%
                            <small>so với tháng trước</small>
                        </span>
                    </div>
                    <span class="kpi-sparkline teal-line"></span>
                </div>

                <!-- APPOINTMENTS -->
                <div class="kpi-card">
                    <div class="kpi-icon gold">
                        <i class="fa-solid fa-calendar-check"></i>
                    </div>
                    <div class="kpi-content">
                        <span class="kpi-label">Tổng lịch hẹn</span>
                        <strong class="kpi-value">48</strong>
                        <span class="kpi-change neutral">
                            <i class="fa-solid fa-minus"></i>
                            2.1%
                            <small>so với hôm qua</small>
                        </span>
                    </div>
                    <span class="kpi-sparkline orange-line"></span>
                </div>

                <!-- REVENUE -->
                <div class="kpi-card">
                    <div class="kpi-icon dark">
                        <i class="fa-solid fa-coins"></i>
                    </div>
                    <div class="kpi-content">
                        <span class="kpi-label">Tổng giao dịch</span>
                        <strong class="kpi-value">128.5M</strong>
                        <span class="kpi-change positive">
                            <i class="fa-solid fa-arrow-up"></i>
                            12.8%
                            <small>so với tháng trước</small>
                        </span>
                    </div>
                    <span class="kpi-sparkline blue-line"></span>
                </div>

            </section>

            <div class="analytics-grid">
                <section class="panel analytics-panel">
                    <div class="panel-header">
                        <div>
                            <h2>Phân bổ dữ liệu hệ thống</h2>
                            <p>Tỷ trọng các nhóm dữ liệu chính</p>
                        </div>
                    </div>
                    <div class="analytics-period-label">Tháng 08/2026</div>
                    <div class="pie-chart-content">
                        <div class="pie-chart" id="adminPieChart">
                            <div class="pie-chart-center">
                                <strong id="pieChartTotal">0</strong>
                                <span>Tổng mục</span>
                            </div>
                        </div>
                        <div class="pie-chart-legend" id="pieChartLegend"></div>
                    </div>
                </section>

                <section class="panel analytics-panel">
                    <div class="panel-header">
                        <div>
                            <h2>Chi tiết mốc thống kê</h2>
                            <p>Thông tin tổng hợp của kỳ đang xem</p>
                        </div>
                    </div>
                    <div class="period-detail">
                        <span class="detail-eyebrow">MỐC ĐANG XEM</span>
                        <strong>Tháng 08/2026</strong>
                        <small>Tổng quan tháng hiện tại</small>
                    </div>
                    <div class="detail-stat-grid">
                        <div><strong id="detailCompleted">0</strong><small>Người dùng</small></div>
                        <div><strong id="detailPosts">0</strong><small>Bài đăng</small></div>
                        <div><strong id="detailRooms">0</strong><small>Phòng trọ</small></div>
                        <div><strong id="detailRevenue">0</strong><small>Thu nhập</small></div>
                    </div>
                    <div class="detail-note">
                        <strong>Nhận định nhanh</strong>
                        <p>Hệ thống đang duy trì hoạt động ổn định trong kỳ thống kê hiện tại.</p>
                    </div>
                </section>
            </div>

            <section class="panel income-panel">
                <div class="panel-header">
                    <div>
                        <h2>Thu nhập theo thời gian</h2>
                        <p>Diễn biến thu nhập trong 6 tháng gần nhất</p>
                    </div>
                    <span class="chart-period-badge">03/2026 - 08/2026</span>
                </div>
                <div class="income-chart" id="incomeChart" aria-label="Biểu đồ thu nhập theo tháng"></div>
                <div class="income-chart-caption">
                    <span><i class="income-dot"></i>Thu nhập (VNĐ)</span>
                    <span>Hover từng cột để xem tháng và số tiền</span>
                </div>
            </section>

            <!-- ACTIVITY + QUICK ACTION GRID -->
            <div class="dashboard-grid">

                <!-- RECENT ACTIVITY -->
                <section class="panel">
                    <div class="panel-header">
                        <div>
                            <h2>Hoạt động gần đây</h2>
                            <p>Các hoạt động mới nhất trên hệ thống</p>
                        </div>
                        <button type="button" class="text-button">
                            Xem tất cả
                            <i class="fa-solid fa-arrow-right"></i>
                        </button>
                    </div>

                    <div class="activity-list">

                        <!-- ITEM 1 -->
                        <div class="activity-item">
                            <div class="activity-avatar green-bg">
                                <i class="fa-solid fa-user-plus"></i>
                            </div>
                            <div class="activity-info">
                                <strong>Nguyễn Minh Anh</strong>
                                <span>đã đăng ký tài khoản mới</span>
                                <small>5 phút trước</small>
                            </div>
                            <span class="activity-tag success">Người dùng</span>
                        </div>

                        <!-- ITEM 2 -->
                        <div class="activity-item">
                            <div class="activity-avatar brown-bg">
                                <i class="fa-solid fa-house"></i>
                            </div>
                            <div class="activity-info">
                                <strong>Phòng Studio Vinhomes</strong>
                                <span>đã được gửi để duyệt bài đăng</span>
                                <small>18 phút trước</small>
                            </div>
                            <span class="activity-tag warning">Chờ duyệt</span>
                        </div>

                        <!-- ITEM 3 -->
                        <div class="activity-item">
                            <div class="activity-avatar gold-bg">
                                <i class="fa-solid fa-credit-card"></i>
                            </div>
                            <div class="activity-info">
                                <strong>Gói Premium Standard</strong>
                                <span>đã được thanh toán thành công</span>
                                <small>32 phút trước</small>
                            </div>
                            <span class="activity-tag success">Thanh toán</span>
                        </div>

                        <!-- ITEM 4 -->
                        <div class="activity-item">
                            <div class="activity-avatar red-bg">
                                <i class="fa-solid fa-flag"></i>
                            </div>
                            <div class="activity-info">
                                <strong>Bài đăng #RC-1082</strong>
                                <span>được người dùng báo cáo</span>
                                <small>1 giờ trước</small>
                            </div>
                            <span class="activity-tag danger">Cần xử lý</span>
                        </div>

                    </div>
                </section>

                <!-- QUICK ACTIONS -->
                <section class="panel">
                    <div class="panel-header">
                        <div>
                            <h2>Thao tác nhanh</h2>
                            <p>Quản lý các tác vụ thường dùng</p>
                        </div>
                    </div>

                    <div class="quick-actions">

                        <a href="<c:url value='/admin/users/approve'/>" class="quick-action">
                            <span class="quick-icon green">
                                <i class="fa-solid fa-user-check"></i>
                            </span>
                            <span>
                                <strong>Duyệt người dùng</strong>
                                <small><span id="pendingUsers">0</span> yêu cầu mới</small>
                            </span>
                            <i class="fa-solid fa-chevron-right"></i>
                        </a>

                        <a href="<c:url value='/admin/posts/pending'/>" class="quick-action">
                            <span class="quick-icon brown">
                                <i class="fa-solid fa-house-circle-check"></i>
                            </span>
                            <span>
                                <strong>Duyệt bài đăng</strong>
                                <small><span id="pendingPosts">0</span> bài đang chờ</small>
                            </span>
                            <i class="fa-solid fa-chevron-right"></i>
                        </a>

                        <a href="<c:url value='/admin/reports/pending'/>" class="quick-action">
                            <span class="quick-icon red">
                                <i class="fa-solid fa-flag"></i>
                            </span>
                            <span>
                                <strong>Xử lý báo cáo</strong>
                                <small><span id="pendingReports">0</span> báo cáo chưa xử lý</small>
                            </span>
                            <i class="fa-solid fa-chevron-right"></i>
                        </a>

                        <a href="<c:url value='/admin/reports/analytics'/>" class="quick-action">
                            <span class="quick-icon gold">
                                <i class="fa-solid fa-chart-line"></i>
                            </span>
                            <span>
                                <strong>Xem báo cáo</strong>
                                <small>Thống kê hệ thống</small>
                            </span>
                            <i class="fa-solid fa-chevron-right"></i>
                        </a>

                    </div>
                </section>

            </div>

        </main>

    </div>

    <script src="<c:url value='/resources/js/admin-dashboard.js'/>"></script>
</body>
</html>