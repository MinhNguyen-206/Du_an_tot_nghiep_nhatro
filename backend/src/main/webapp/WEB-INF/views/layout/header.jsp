<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<header class="site-header">
  <div class="container site-header__inner">
    <a href="/" class="site-header__logo">
      <span class="logo-text">ROOM CONNECT</span>
    </a>

    <nav class="site-header__nav">
      <a href="/" class="nav-link">Trang chủ</a>
      <a href="/rooms" class="nav-link">Tìm phòng</a>
      <c:if test="${not empty sessionScope.currentUser}">
        <a href="/notifications" class="nav-link">Thông báo</a>
        <a href="/messages" class="nav-link">Tin nhắn</a>
      </c:if>
    </nav>

    <div class="site-header__actions">
      <c:choose>
        <c:when test="${not empty sessionScope.currentUser}">
          <div class="user-menu" id="userMenuToggle">
            <button class="user-menu__btn" onclick="toggleUserMenu()">
              <div class="user-avatar">${sessionScope.userName != null ? sessionScope.userName.substring(0,1).toUpperCase() : 'U'}</div>
              <span class="user-name">${sessionScope.userName}</span>
              <span class="user-menu__arrow">▾</span>
            </button>
            <div class="user-menu__dropdown" id="userDropdown">
              <a href="/profile" class="user-menu__item">👤 Hồ sơ</a>
              <a href="/appointments" class="user-menu__item">📅 Lịch hẹn</a>
              <a href="/rental-requests" class="user-menu__item">📋 Yêu cầu thuê</a>
              <a href="/invoices/monthly" class="user-menu__item">🧾 Hóa đơn</a>
              <a href="/premium/packages" class="user-menu__item">⭐ Gói Premium</a>
              <c:if test="${sessionScope.userRole == 1}">
                <div class="user-menu__divider"></div>
                <a href="/admin" class="user-menu__item user-menu__item--admin">⚙️ Quản trị</a>
              </c:if>
              <div class="user-menu__divider"></div>
              <a href="/logout" class="user-menu__item user-menu__item--danger">🚪 Đăng xuất</a>
            </div>
          </div>
        </c:when>
        <c:otherwise>
          <a href="/login" class="btn btn--outline btn--sm">Đăng nhập</a>
          <a href="/register" class="btn btn--primary btn--sm">Đăng ký</a>
        </c:otherwise>
      </c:choose>
    </div>

    <button class="mobile-menu-btn" id="mobileMenuBtn" onclick="toggleMobileMenu()">☰</button>
  </div>

  <!-- Mobile nav -->
  <div class="mobile-nav" id="mobileNav">
    <a href="/" class="mobile-nav__link">Trang chủ</a>
    <a href="/rooms" class="mobile-nav__link">Tìm phòng</a>
    <c:if test="${not empty sessionScope.currentUser}">
      <a href="/profile" class="mobile-nav__link">Hồ sơ</a>
      <a href="/notifications" class="mobile-nav__link">Thông báo</a>
      <a href="/logout" class="mobile-nav__link">Đăng xuất</a>
    </c:if>
    <c:if test="${empty sessionScope.currentUser}">
      <a href="/login" class="mobile-nav__link">Đăng nhập</a>
      <a href="/register" class="mobile-nav__link">Đăng ký</a>
    </c:if>
  </div>
</header>

<style>
.site-header {
  position: sticky; top: 0; z-index: 1000;
  background: rgba(241,232,206,.95); backdrop-filter: blur(12px);
  border-bottom: 1px solid rgba(33,29,23,.08);
  box-shadow: 0 2px 16px rgba(33,29,23,.06);
}
.site-header__inner { display: flex; align-items: center; gap: 24px; height: 68px; }
.site-header__logo { display: flex; align-items: center; gap: 10px; text-decoration: none; }
.logo-icon { font-size: 24px; }
.logo-text { font-family: 'Archivo Black', sans-serif; font-size: 18px; color: var(--pine); letter-spacing: .5px; }
.site-header__nav { display: flex; align-items: center; gap: 4px; flex: 1; margin-left: 16px; }
.nav-link { padding: 8px 14px; border-radius: var(--radius-full); font-size: 14px; font-weight: 600; color: var(--ink); text-decoration: none; transition: all .2s; }
.nav-link:hover { background: rgba(33,29,23,.06); }
.site-header__actions { display: flex; align-items: center; gap: 10px; margin-left: auto; }

/* User menu */
.user-menu { position: relative; }
.user-menu__btn { display: flex; align-items: center; gap: 10px; padding: 8px 14px; border: none; background: rgba(31,75,63,.08); border-radius: var(--radius-full); cursor: pointer; transition: .2s; }
.user-menu__btn:hover { background: rgba(31,75,63,.14); }
.user-avatar { width: 32px; height: 32px; border-radius: 50%; background: var(--pine); color: var(--paper); display: flex; align-items: center; justify-content: center; font-weight: 800; font-size: 14px; }
.user-name { font-size: 14px; font-weight: 600; color: var(--ink); max-width: 120px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.user-menu__arrow { font-size: 11px; color: var(--gray-600); }
.user-menu__dropdown { position: absolute; top: calc(100% + 8px); right: 0; width: 220px; background: #fff; border-radius: var(--radius); box-shadow: var(--shadow-lg); border: 1px solid var(--gray-200); overflow: hidden; display: none; animation: slideUp .15s ease; z-index: 1001; }
.user-menu__dropdown.open { display: block; }
.user-menu__item { display: block; padding: 12px 16px; font-size: 14px; font-weight: 500; color: var(--ink); text-decoration: none; transition: .2s; }
.user-menu__item:hover { background: var(--gray-100); }
.user-menu__item--admin { color: var(--pine); font-weight: 700; }
.user-menu__item--danger { color: var(--brick); }
.user-menu__divider { height: 1px; background: var(--gray-200); margin: 4px 0; }

/* Mobile */
.mobile-menu-btn { display: none; width: 40px; height: 40px; border: none; background: none; font-size: 20px; cursor: pointer; color: var(--ink); margin-left: auto; }
.mobile-nav { display: none; background: #fff; border-top: 1px solid var(--gray-200); }
.mobile-nav.open { display: block; }
.mobile-nav__link { display: block; padding: 14px 24px; font-size: 15px; font-weight: 500; color: var(--ink); text-decoration: none; border-bottom: 1px solid var(--gray-200); }
.mobile-nav__link:hover { background: var(--gray-100); }

@media (max-width: 768px) {
  .site-header__nav, .site-header__actions { display: none; }
  .mobile-menu-btn { display: flex; align-items: center; justify-content: center; }
}
</style>
