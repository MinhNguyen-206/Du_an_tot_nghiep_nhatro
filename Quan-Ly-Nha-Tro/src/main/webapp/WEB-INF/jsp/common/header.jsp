<%@ page pageEncoding="UTF-8" %>
<%-- =========================================================
     HEADER DÙNG CHUNG CHO TOÀN BỘ TRANG (KHÁCH + ĐÃ ĐĂNG NHẬP)
     - Style Tailwind đồng bộ với home.jsp / ThueTro.jsp.
     - Yêu cầu trang cha đã include common/head-assets.jspf trong <head>
       (Tailwind CDN + FontAwesome + font) trước khi include file này.
     - Đọc localStorage "user" (lưu lúc login, xem auth/login.jsp) để
       tự hiện avatar + tên + menu theo vai trò (Admin / Chủ trọ / Người thuê).
     Cách dùng trong 1 trang .jsp:
       <%@ include file="/WEB-INF/jsp/common/head-assets.jspf" %>  (trong <head>)
       ...
       <%@ include file="/WEB-INF/jsp/common/header.jsp" %>        (đầu <body>)
========================================================= --%>

<header class="sticky top-0 z-50 bg-white/95 backdrop-blur-md border-b border-gray-200">

    <div class="max-w-[1180px] mx-auto px-4">

        <div class="h-[64px] flex items-center gap-3">

            <!-- MOBILE MENU TOGGLE -->
            <button
                    id="rcMobileMenuBtn"
                    class="md:hidden w-10 h-10 rounded-full flex items-center justify-center hover:bg-orange-50 transition"
            >
                <i class="fa-solid fa-bars text-lg"></i>
            </button>

            <!-- LOGO -->
            <a href="${pageContext.request.contextPath}/" class="shrink-0 flex items-center gap-1">
                <span class="text-lg">🏠</span>
                <span class="text-[20px] font-black italic tracking-[-1px] text-brand">ROOM - CONNECT</span>
            </a>

            <!-- NAV (desktop) -->
            <nav class="hidden md:flex items-center gap-3 ml-6">
                <a href="${pageContext.request.contextPath}/" class="px-4 py-2 rounded-full text-sm font-semibold text-navy hover:bg-orange-50 hover:text-brand transition whitespace-nowrap">Trang chủ</a>
                <a href="${pageContext.request.contextPath}/thue-tro" class="px-4 py-2 rounded-full text-sm font-semibold text-navy hover:bg-orange-50 hover:text-brand transition whitespace-nowrap">Tìm phòng</a>
                <a href="${pageContext.request.contextPath}/gioi-thieu" class="px-4 py-2 rounded-full text-sm font-semibold text-navy hover:bg-orange-50 hover:text-brand transition whitespace-nowrap">Về chúng tôi</a>
                <a href="${pageContext.request.contextPath}/lien-he" class="px-4 py-2 rounded-full text-sm font-semibold text-navy hover:bg-orange-50 hover:text-brand transition whitespace-nowrap">Liên hệ</a>
            </nav>

            <!-- SEARCH -->
            <form
                    action="${pageContext.request.contextPath}/thue-tro"
                    method="GET"
                    class="flex-1 hidden sm:flex ml-2"
            >
                <div class="relative w-full">
                    <i class="fa-solid fa-magnifying-glass absolute left-4 top-1/2 -translate-y-1/2 text-gray-400"></i>
                    <input
                            type="text"
                            name="keyword"
                            placeholder="Tìm phòng trọ, căn hộ..."
                            class="w-full h-11 rounded-full bg-gray-100 pl-11 pr-14 text-sm outline-none border border-transparent focus:border-orange-200 focus:bg-white transition"
                    >
                    <button
                            type="submit"
                            class="absolute right-1 top-1/2 -translate-y-1/2 w-9 h-9 rounded-full bg-brand text-white hover:bg-brandDark transition"
                    >
                        <i class="fa-solid fa-arrow-right"></i>
                    </button>
                </div>
            </form>

            <!-- RIGHT -->
            <div class="flex items-center gap-2 ml-auto">

                <!-- Trạng thái CHƯA đăng nhập -->
                <div id="rcGuestActions" class="hidden items-center gap-1">
                    <a
                            href="${pageContext.request.contextPath}/login"
                            class="hidden sm:flex px-4 py-2.5 rounded-full border border-gray-200 text-sm font-bold hover:border-orange-300 hover:text-brand transition"
                    >Đăng nhập</a>
                    <a
                            href="${pageContext.request.contextPath}/register"
                            class="flex items-center gap-2 bg-brand text-white px-4 py-2.5 rounded-full text-sm font-bold hover:bg-brandDark transition shadow-sm"
                    >
                        <i class="fa-solid fa-user-plus"></i>
                        <span>Đăng ký</span>
                    </a>
                </div>

                <!-- Trạng thái ĐÃ đăng nhập -->
                <div id="rcUserActions" class="hidden items-center gap-2">

                    <button class="hidden md:flex w-10 h-10 items-center justify-center rounded-full hover:bg-orange-50">
                        <i class="fa-regular fa-heart text-lg"></i>
                    </button>

                    <button class="hidden md:flex w-10 h-10 items-center justify-center rounded-full hover:bg-orange-50">
                        <i class="fa-regular fa-bell text-lg"></i>
                    </button>

                    <a
                            id="rcLandlordLink"
                            href="${pageContext.request.contextPath}/chu-tro"
                            class="hidden lg:flex items-center gap-2 px-4 py-2.5 rounded-full border border-gray-200 text-sm font-bold hover:border-orange-300 hover:text-brand transition"
                    >
                        <i class="fa-solid fa-house-chimney-user"></i>
                        <span id="rcLandlordLinkText">Kênh chủ trọ</span>
                    </a>

                    <div class="relative">
                        <button id="rcUserMenuBtn" class="flex items-center gap-2 pl-1 pr-3 py-1 rounded-full hover:bg-orange-50 transition">
                            <img id="rcUserAvatar" src="https://ui-avatars.com/api/?name=U&background=ff641e&color=fff" alt="Avatar" class="w-9 h-9 rounded-full object-cover border border-gray-200">
                            <span id="rcUserName" class="hidden lg:inline text-sm font-semibold max-w-[120px] truncate">Người dùng</span>
                            <i class="fa-solid fa-chevron-down text-[10px] text-gray-400"></i>
                        </button>

                        <div id="rcUserDropdown" class="hidden absolute right-0 mt-2 w-56 bg-white rounded-2xl shadow-soft border border-gray-100 py-2 overflow-hidden">
                            <a href="${pageContext.request.contextPath}/profile" class="flex items-center gap-2 px-4 py-2.5 text-sm font-medium hover:bg-orange-50 hover:text-brand transition">
                                <i class="fa-regular fa-user w-4"></i> Hồ sơ cá nhân
                            </a>
                            <a id="rcAdminLink" href="${pageContext.request.contextPath}/admin" class="hidden items-center gap-2 px-4 py-2.5 text-sm font-medium hover:bg-orange-50 hover:text-brand transition">
                                <i class="fa-solid fa-shield-halved w-4"></i> Trang quản trị
                            </a>
                            <a href="${pageContext.request.contextPath}/chu-tro/notifications" class="flex items-center gap-2 px-4 py-2.5 text-sm font-medium hover:bg-orange-50 hover:text-brand transition">
                                <i class="fa-regular fa-bell w-4"></i> Thông báo
                            </a>
                            <hr class="my-1 border-gray-100">
                            <button id="rcLogoutBtn" class="w-full flex items-center gap-2 px-4 py-2.5 text-sm font-semibold text-red-500 hover:bg-red-50 transition text-left">
                                <i class="fa-solid fa-right-from-bracket w-4"></i> Đăng xuất
                            </button>
                        </div>
                    </div>
                </div>

            </div>

        </div>

        <!-- MOBILE NAV -->
        <div id="rcMobileNav" class="hidden md:hidden pb-3 flex flex-col gap-1 border-t border-gray-100 pt-2">
            <a href="${pageContext.request.contextPath}/" class="px-3 py-2 rounded-lg text-sm font-semibold hover:bg-orange-50">Trang chủ</a>
            <a href="${pageContext.request.contextPath}/thue-tro" class="px-3 py-2 rounded-lg text-sm font-semibold hover:bg-orange-50">Tìm phòng</a>
            <a href="${pageContext.request.contextPath}/gioi-thieu" class="px-3 py-2 rounded-lg text-sm font-semibold hover:bg-orange-50">Về chúng tôi</a>
            <a href="${pageContext.request.contextPath}/lien-he" class="px-3 py-2 rounded-lg text-sm font-semibold hover:bg-orange-50">Liên hệ</a>
            <form action="${pageContext.request.contextPath}/thue-tro" method="GET" class="px-3 pt-2 sm:hidden">
                <div class="relative w-full">
                    <i class="fa-solid fa-magnifying-glass absolute left-4 top-1/2 -translate-y-1/2 text-gray-400"></i>
                    <input type="text" name="keyword" placeholder="Tìm phòng trọ, căn hộ..."
                           class="w-full h-11 rounded-full bg-gray-100 pl-11 pr-4 text-sm outline-none border border-transparent focus:border-orange-200 focus:bg-white transition">
                </div>
            </form>
        </div>

    </div>

</header>

<script>
(function () {
    // ---- Toggle menu mobile ----
    var mobileBtn = document.getElementById('rcMobileMenuBtn');
    var mobileNav = document.getElementById('rcMobileNav');
    if (mobileBtn && mobileNav) {
        mobileBtn.addEventListener('click', function () {
            mobileNav.classList.toggle('hidden');
        });
    }

    // ---- Toggle dropdown user ----
    var userBtn = document.getElementById('rcUserMenuBtn');
    var userDropdown = document.getElementById('rcUserDropdown');
    if (userBtn && userDropdown) {
        userBtn.addEventListener('click', function (e) {
            e.stopPropagation();
            userDropdown.classList.toggle('hidden');
        });
        document.addEventListener('click', function (e) {
            if (!e.target.closest('#rcUserMenuBtn') && !e.target.closest('#rcUserDropdown')) {
                userDropdown.classList.add('hidden');
            }
        });
    }

    // ---- Đăng xuất ----
    var logoutBtn = document.getElementById('rcLogoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function () {
            localStorage.removeItem('token');
            localStorage.removeItem('user');
            window.location.href = '${pageContext.request.contextPath}/login';
        });
    }

    // ---- Hiện đúng trạng thái đăng nhập / vai trò ----
    function setDisplay(el, show, mode) {
        if (!el) return;
        el.classList.toggle('hidden', !show);
        if (show && mode) el.classList.add(mode);
    }

    var guestBox = document.getElementById('rcGuestActions');
    var userBox = document.getElementById('rcUserActions');

    try {
        var token = localStorage.getItem('token');
        var userJson = localStorage.getItem('user');
        var user = userJson ? JSON.parse(userJson) : null;

        if (token && user) {
            setDisplay(guestBox, false);
            setDisplay(userBox, true, 'flex');

            var nameEl = document.getElementById('rcUserName');
            var avatarEl = document.getElementById('rcUserAvatar');
            if (nameEl) nameEl.textContent = user.hoTen || user.email || 'Người dùng';
            if (avatarEl) {
                avatarEl.src = user.avatar
                    ? user.avatar
                    : 'https://ui-avatars.com/api/?name=' + encodeURIComponent(user.hoTen || 'U') + '&background=ff641e&color=fff';
            }

            // Xác định vai trò: maVaiTro 1=Admin, 2=Chủ trọ, 3=Người thuê
            var maVaiTro = user.vaiTro ? user.vaiTro.maVaiTro : user.maVaiTro;
            var tenVaiTro = user.vaiTro ? user.vaiTro.tenVaiTro : user.tenVaiTro;
            var isAdmin = maVaiTro === 1 || maVaiTro === '1' || (tenVaiTro && tenVaiTro.toLowerCase().indexOf('admin') > -1);
            var isChuTro = maVaiTro === 2 || maVaiTro === '2' || (tenVaiTro && tenVaiTro.toLowerCase().indexOf('chủ trọ') > -1);

            var landlordLink = document.getElementById('rcLandlordLink');
            var landlordLinkText = document.getElementById('rcLandlordLinkText');
            var adminLink = document.getElementById('rcAdminLink');

            if (isAdmin) {
                setDisplay(landlordLink, true, 'flex');
                if (landlordLinkText) landlordLinkText.textContent = 'Kênh chủ trọ';
                setDisplay(adminLink, true, 'flex');
            } else if (isChuTro) {
                setDisplay(landlordLink, true, 'flex');
                if (landlordLinkText) landlordLinkText.textContent = 'Kênh chủ trọ';
                setDisplay(adminLink, false);
            } else {
                // Người thuê: chưa phải chủ trọ -> mời đăng ký làm chủ trọ
                setDisplay(landlordLink, true, 'flex');
                if (landlordLink) landlordLink.href = '${pageContext.request.contextPath}/dang-ky-chu-tro';
                if (landlordLinkText) landlordLinkText.textContent = 'Đăng ký chủ trọ';
                setDisplay(adminLink, false);
            }
        } else {
            setDisplay(guestBox, true, 'flex');
            setDisplay(userBox, false);
        }
    } catch (e) {
        // localStorage hỏng / user JSON lỗi -> coi như khách
        setDisplay(guestBox, true, 'flex');
        setDisplay(userBox, false);
    }
})();
</script>
