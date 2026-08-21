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

<body class="admin-body">

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

                <!-- ADMIN PROFILE -->
                <div class="admin-profile">
                    <div class="admin-avatar">
                        <i class="fa-solid fa-user-shield"></i>
                    </div>
                    <div class="admin-profile-info">
                        <strong>Quản trị viên</strong>
                        <span>Administrator</span>
                    </div>
                    <span class="online-dot"></span>
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

                    <h1>Chào mừng trở lại, Admin 👋</h1>
                    <p>Đây là tình hình hoạt động của Room Connect hôm nay.</p>
                </div>

                <div class="topbar-actions">
                    <button type="button" class="icon-button">
                        <i class="fa-regular fa-bell"></i>
                        <span class="notification-dot"></span>
                    </button>

                    <button type="button" class="admin-account">
                        <span class="account-avatar">
                            <i class="fa-solid fa-user-shield"></i>
                        </span>
                        <span class="account-text">
                            <strong>Admin</strong>
                            <small>Quản trị viên</small>
                        </span>
                        <i class="fa-solid fa-chevron-down"></i>
                    </button>
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
                </div>

                <!-- POSTS -->
                <div class="kpi-card">
                    <div class="kpi-icon brown">
                        <i class="fa-solid fa-house"></i>
                    </div>
                    <div class="kpi-content">
                        <span class="kpi-label">Bài đăng hoạt động</span>
                        <strong class="kpi-value">856</strong>
                        <span class="kpi-change positive">
                            <i class="fa-solid fa-arrow-up"></i>
                            5.2%
                            <small>so với tháng trước</small>
                        </span>
                    </div>
                </div>

                <!-- APPOINTMENTS -->
                <div class="kpi-card">
                    <div class="kpi-icon gold">
                        <i class="fa-solid fa-calendar-check"></i>
                    </div>
                    <div class="kpi-content">
                        <span class="kpi-label">Lịch hẹn hôm nay</span>
                        <strong class="kpi-value">48</strong>
                        <span class="kpi-change neutral">
                            <i class="fa-solid fa-minus"></i>
                            2.1%
                            <small>so với hôm qua</small>
                        </span>
                    </div>
                </div>

                <!-- REVENUE -->
                <div class="kpi-card">
                    <div class="kpi-icon dark">
                        <i class="fa-solid fa-coins"></i>
                    </div>
                    <div class="kpi-content">
                        <span class="kpi-label">Doanh thu tháng</span>
                        <strong class="kpi-value">128.5M</strong>
                        <span class="kpi-change positive">
                            <i class="fa-solid fa-arrow-up"></i>
                            12.8%
                            <small>so với tháng trước</small>
                        </span>
                    </div>
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
                                <small>24 yêu cầu mới</small>
                            </span>
                            <i class="fa-solid fa-chevron-right"></i>
                        </a>

                        <a href="<c:url value='/admin/posts/pending'/>" class="quick-action">
                            <span class="quick-icon brown">
                                <i class="fa-solid fa-house-circle-check"></i>
                            </span>
                            <span>
                                <strong>Duyệt bài đăng</strong>
                                <small>12 bài đang chờ</small>
                            </span>
                            <i class="fa-solid fa-chevron-right"></i>
                        </a>

                        <a href="<c:url value='/admin/reports/pending'/>" class="quick-action">
                            <span class="quick-icon red">
                                <i class="fa-solid fa-flag"></i>
                            </span>
                            <span>
                                <strong>Xử lý báo cáo</strong>
                                <small>5 báo cáo chưa xử lý</small>
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

</body>
</html>