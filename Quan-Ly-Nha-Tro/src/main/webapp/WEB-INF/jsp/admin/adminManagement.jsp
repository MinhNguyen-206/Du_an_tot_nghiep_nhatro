<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle} - Room Connect</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/admin-dashboard.css'/>">
</head>
<body class="admin-body admin-page">

    <div class="admin-shell">
        <aside class="admin-sidebar">
            <div class="sidebar-top">
                <div class="admin-brand">
                    <div class="admin-brand-icon"><i class="fa-solid fa-shield-halved"></i></div>
                    <div><strong>Room Connect</strong><span>ADMINISTRATION</span></div>
                </div>
            </div>
            <nav class="sidebar-nav">
                <div class="nav-heading">TỔNG QUAN</div>
                <a href="<c:url value='/admin/dashboard'/>" class="sidebar-link"><i class="fa-solid fa-chart-pie"></i><span>Tổng quan</span></a>
                <div class="nav-heading">QUẢN LÝ HỆ THỐNG</div>
                <a href="<c:url value='/admin/users'/>" class="sidebar-link ${activeMenu == 'users' ? 'active' : ''}"><i class="fa-solid fa-users"></i><span>Người dùng</span></a>
                <a href="<c:url value='/admin/posts'/>" class="sidebar-link ${activeMenu == 'posts' ? 'active' : ''}"><i class="fa-solid fa-house"></i><span>Bài đăng & phòng trọ</span></a>
                <a href="<c:url value='/admin/appointments'/>" class="sidebar-link ${activeMenu == 'appointments' ? 'active' : ''}"><i class="fa-solid fa-calendar-check"></i><span>Lịch hẹn</span></a>
                <a href="<c:url value='/admin/contracts'/>" class="sidebar-link ${activeMenu == 'contracts' ? 'active' : ''}"><i class="fa-solid fa-file-contract"></i><span>Hợp đồng</span></a>
                <a href="<c:url value='/admin/payments'/>" class="sidebar-link ${activeMenu == 'payments' ? 'active' : ''}"><i class="fa-solid fa-wallet"></i><span>Thanh toán</span></a>
                <div class="nav-heading">KIỂM SOÁT</div>
                <a href="<c:url value='/admin/reports'/>" class="sidebar-link ${activeMenu == 'reports' ? 'active' : ''}"><i class="fa-solid fa-flag"></i><span>Báo cáo vi phạm</span></a>
                <a href="<c:url value='/admin/settings'/>" class="sidebar-link ${activeMenu == 'settings' ? 'active' : ''}"><i class="fa-solid fa-gear"></i><span>Cài đặt hệ thống</span></a>
            </nav>
        </aside>
        <main class="admin-main management-main">
            <div class="dashboard-topbar">
                <div class="dashboard-heading">
                    <div class="breadcrumb"><span>Admin</span><i class="fa-solid fa-chevron-right"></i><strong>${pageTitle}</strong></div>
                    <h1>${pageTitle}</h1>
                    <p>${pageDescription}</p>
                </div>
            </div>
            <section class="panel management-panel">
                <div class="management-toolbar">
                    <div><h2>Danh sách ${pageTitle}</h2><span id="recordCount">Đang tải dữ liệu...</span></div>
                    <label class="management-search"><i class="fa-solid fa-magnifying-glass"></i><input id="managementSearch" type="search" placeholder="Tìm kiếm..."></label>
                </div>
                <div class="management-status" id="managementStatus">Đang tải dữ liệu từ hệ thống...</div>
                <div class="management-table-wrap">
                    <table class="management-table"><thead id="managementHead"></thead><tbody id="managementBody"></tbody></table>
                </div>
            </section>
        </main>
    </div>
    <script>window.ADMIN_MANAGEMENT_ENDPOINT = '<c:url value="${apiEndpoint}"/>';</script>
    <script src="<c:url value='/resources/js/admin-management.js'/>"></script>
</body>
</html>
