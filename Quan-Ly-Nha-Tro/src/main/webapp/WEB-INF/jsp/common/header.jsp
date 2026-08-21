<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Header Component - Include this in any page -->
<header class="header">
    <nav class="navbar">
        <div class="navbar-container">
            <!-- Logo -->
            <div class="navbar-logo">
                <a href="/" class="logo-link">
                    <span class="logo-icon">🏠</span>
                    <span class="logo-text">ROOM - CONNECT</span>
                </a>
            </div>

            <!-- Navigation Menu -->
            <ul class="nav-menu">
                <li class="nav-item">
                    <a href="/" class="nav-link">Trang chủ</a>
                </li>
                <li class="nav-item">
                    <a href="<c:url value='/rooms'/>" class="nav-link">Danh sách phòng</a>
                </li>
                <li class="nav-item">
                    <a href="<c:url value='/lien-he'/>" class="nav-link">Liên hệ</a>
                </li>
            </ul>

            <!-- User Profile & Actions -->
            <div class="navbar-actions">
                <div class="notification-menu">
                <button class="notification-btn" id="notificationBtn" type="button" aria-expanded="false">
                    <span class="icon">🔔</span>
                    <span class="badge" id="notificationBadge" style="display:none;">0</span>
                </button>
                <div class="notification-dropdown" id="notificationDropdown" style="display:none;">
                    <strong>Thông báo</strong>
                    <div id="notificationList">Đang tải thông báo...</div>
                </div>
                </div>

                <div class="admin-header-account" id="adminHeaderAccount">
                    <div class="admin-account-menu">
                        <button type="button" class="admin-account" id="profileBtn" aria-expanded="false">
                            <span class="account-avatar">
                                <i class="fa-solid fa-user-shield"></i>
                            </span>
                            <span class="account-text">
                                <strong>Quản trị viên</strong>
                                <small>Administrator</small>
                            </span>
                            <i class="fa-solid fa-chevron-down"></i>
                        </button>
                        <div class="admin-profile-menu" id="profileMenu" style="display: none;">
                            <a href="<c:url value='/profile'/>">Hồ sơ cá nhân</a>
                            <a href="<c:url value='/admin/settings'/>">Cài đặt</a>
                            <a href="<c:url value='/logout'/>" class="logout-item">Đăng xuất</a>
                        </div>
                    </div>
                </div>

                <div class="user-menu">
                    <button class="user-btn" id="userMenuBtn">
                        <img src="https://via.placeholder.com/40" alt="Avatar" class="user-avatar">
                        <span class="user-name" id="userName">Người dùng</span>
                        <span class="dropdown-icon">▼</span>
                    </button>

                    <div class="dropdown-menu" id="userDropdown" style="display:none;">
                        <a href="/profile" class="dropdown-item">Hồ sơ cá nhân</a>
                        <a href="/settings" class="dropdown-item">Cài đặt</a>
                        <a href="#" class="dropdown-item" id="logoutBtn">Đăng xuất</a>
                    </div>
                </div>
            </div>

            <!-- Mobile Menu Toggle -->
            <div class="mobile-menu-toggle">
                <button id="mobileMenuBtn" class="menu-icon">☰</button>
            </div>
        </div>
    </nav>
</header>

<style>
    .header {
        background-color: #f2f1ef;
        border-bottom: 1px solid #d6d3cf;
        position: sticky;
        top: 0;
        z-index: 100;
    }

    .navbar {
        padding: 0;
    }

    .navbar-container {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0.8rem 2rem;
        max-width: 100%;
        min-height: 56px;
        gap: 1rem;
    }

    .navbar-logo {
        display: flex;
        align-items: center;
        min-width: 180px;
    }

    .logo-link {
        text-decoration: none;
        display: flex;
        align-items: center;
        gap: 0.5rem;
        color: #1e1e1e;
    }

    .logo-icon {
        font-size: 1.2rem;
    }

    .logo-text {
        font-size: 1.1rem;
        font-weight: 700;
        letter-spacing: 0.04em;
        color: #1e1e1e;
    }

    .nav-menu {
        display: flex;
        align-items: center;
        justify-content: center;
        flex: 1;
        list-style: none;
        gap: 0.5rem;
        margin: 0;
        padding: 0;
        background: rgba(255,255,255,0.35);
        border: 1px solid #d2d2d2;
        border-radius: 999px;
        max-width: 560px;
        padding: 0.2rem 0.6rem;
    }

    .nav-item {
        display: flex;
        align-items: center;
    }

    .nav-link {
        text-decoration: none;
        color: #4a4a4a;
        font-weight: 500;
        padding: 0.55rem 0.9rem;
        border-radius: 999px;
        transition: all 0.2s ease;
        font-size: 0.92rem;
    }

    .nav-link:hover {
        background: rgba(0,0,0,0.03);
        color: #1d1d1d;
    }

    .navbar-actions {
        display: flex;
        align-items: center;
        gap: 1rem;
    }

    .admin-header-account {
        display: none;
        align-items: center;
    }

    .notification-btn {
        background: none;
        border: none;
        font-size: 1.25rem;
        cursor: pointer;
        position: relative;
    }

    .notification-menu {
        position: relative;
    }

    .notification-dropdown {
        position: absolute;
        top: calc(100% + 0.65rem);
        right: 0;
        z-index: 210;
        width: 280px;
        padding: 0.85rem;
        background: #fff;
        border: 1px solid #ddd;
        border-radius: 8px;
        box-shadow: 0 5px 16px rgba(0,0,0,0.14);
        color: #333;
        font-size: 0.85rem;
    }

    .notification-dropdown > strong {
        display: block;
        padding-bottom: 0.65rem;
        border-bottom: 1px solid #eee;
    }

    .notification-item {
        display: block;
        padding: 0.65rem 0;
        border-bottom: 1px solid #f1f1f1;
    }

    .notification-item:last-child {
        border-bottom: 0;
    }

    .notification-item strong,
    .notification-item small {
        display: block;
    }

    .notification-item small {
        margin-top: 0.25rem;
        color: #888;
    }

    .badge {
        position: absolute;
        top: -5px;
        right: -5px;
        background-color: #dc3545;
        color: white;
        border-radius: 50%;
        width: 20px;
        height: 20px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 0.75rem;
        font-weight: bold;
    }

    .user-menu {
        position: relative;
    }

    .user-btn {
        background: transparent;
        border: none;
        padding: 0.25rem;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        transition: all 0.3s;
    }

    .user-btn:hover {
        background: rgba(0,0,0,0.04);
    }

    .user-avatar {
        width: 28px;
        height: 28px;
        border-radius: 50%;
        object-fit: cover;
        border: 1px solid #d9d9d9;
    }

    .user-name {
        font-weight: 500;
        color: #333;
    }

    .dropdown-icon {
        font-size: 0.75rem;
        color: #999;
    }

    .dropdown-menu {
        position: absolute;
        top: 100%;
        right: 0;
        background: white;
        border: 1px solid #ddd;
        border-radius: 6px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        min-width: 180px;
        margin-top: 0.5rem;
        z-index: 200;
    }

    .dropdown-item {
        display: block;
        padding: 0.75rem 1.5rem;
        color: #333;
        text-decoration: none;
        transition: background-color 0.3s;
    }

    .dropdown-item:hover {
        background-color: #f5f5f5;
    }

    .dropdown-item:last-child {
        border-top: 1px solid #eee;
        color: #dc3545;
    }

    .mobile-menu-toggle {
        display: none;
    }

    .menu-icon {
        background: none;
        border: none;
        font-size: 1.5rem;
        cursor: pointer;
    }

    @media (max-width: 768px) {
        .nav-menu {
            display: none;
        }

        .navbar-container {
            padding: 1rem 1.5rem;
        }

        .mobile-menu-toggle {
            display: block;
        }
    }
</style>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const userMenuBtn = document.getElementById('userMenuBtn');
        const userDropdown = document.getElementById('userDropdown');
        const logoutBtn = document.getElementById('logoutBtn');
        const notificationBtn = document.getElementById('notificationBtn');
        const notificationDropdown = document.getElementById('notificationDropdown');
        const notificationList = document.getElementById('notificationList');

        // Toggle user dropdown
        if (userMenuBtn && userDropdown) {
            userMenuBtn.addEventListener('click', function(e) {
                e.stopPropagation();
                userDropdown.style.display = userDropdown.style.display === 'none' ? 'block' : 'none';
            });

            // Close dropdown when clicking outside
            document.addEventListener('click', function(e) {
                if (!e.target.closest('.user-menu')) {
                    userDropdown.style.display = 'none';
                }
            });
        }

        if (notificationBtn && notificationDropdown) {
            notificationBtn.addEventListener('click', function (e) {
                e.stopPropagation();
                const isHidden = notificationDropdown.style.display === 'none';
                notificationDropdown.style.display = isHidden ? 'block' : 'none';
                notificationBtn.setAttribute('aria-expanded', String(isHidden));
                if (isHidden) loadNotifications();
            });
        }

        document.addEventListener('click', function (e) {
            if (!e.target.closest('.notification-menu')) {
                if (notificationDropdown) notificationDropdown.style.display = 'none';
            }
        });

        // Logout functionality
        if (logoutBtn) {
            logoutBtn.addEventListener('click', function(e) {
                e.preventDefault();
                localStorage.removeItem('token');
                localStorage.removeItem('userRole');
                window.location.href = '/login';
            });
        }

        // Load user info from token
        const token = localStorage.getItem('token');
        if (token) {
            try {
                const payload = JSON.parse(atob(token.split('.')[1]));
                const userName = document.getElementById('userName');
                if (userName && payload.sub) {
                    userName.textContent = payload.sub;
                }
            } catch (e) {
                console.log('Could not parse token');
            }
        }
    });

    async function loadNotifications() {
        const list = document.getElementById('notificationList');
        if (!list) return;
        const token = localStorage.getItem('token');
        try {
            const response = await fetch('/api/thong-bao', {
                headers: token ? { Authorization: 'Bearer ' + token } : {}
            });
            if (!response.ok) throw new Error('Notification API unavailable');
            const notifications = await response.json();
            if (!notifications.length) {
                list.textContent = 'Không có thông báo mới.';
                return;
            }
            list.innerHTML = notifications.slice(0, 5).map(item => `
                <div class="notification-item">
                    <strong>${item.tieuDe || item.noiDung || 'Thông báo hệ thống'}</strong>
                    <small>${item.ngayGui || 'Mới cập nhật'}</small>
                </div>
            `).join('');
        } catch (error) {
            list.textContent = 'Không thể tải thông báo lúc này.';
        }
    }
</script>
