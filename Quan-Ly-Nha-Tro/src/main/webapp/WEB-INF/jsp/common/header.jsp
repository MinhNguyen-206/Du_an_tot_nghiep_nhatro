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
                    <a href="#" class="nav-link">Danh sách phòng</a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link">Liên hệ</a>
                </li>
            </ul>

            <!-- User Profile & Actions -->
            <div class="navbar-actions">
                <button class="notification-btn" id="notificationBtn">
                    <span class="icon">🔔</span>
                    <span class="badge" id="notificationBadge" style="display:none;">0</span>
                </button>

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

    .notification-btn {
        background: none;
        border: none;
        font-size: 1.25rem;
        cursor: pointer;
        position: relative;
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
</script>
