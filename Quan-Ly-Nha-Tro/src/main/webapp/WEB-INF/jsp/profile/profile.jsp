<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hồ sơ cá nhân - Room Connect</title>
    <%@ include file="/WEB-INF/jsp/common/head-assets.jspf" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/profile-khach-thue.css">
</head>
<body class="profile-page" data-context-path="${pageContext.request.contextPath}">

<header class="profile-header">
    <div class="profile-header-inner">
        <a href="${pageContext.request.contextPath}/" class="profile-logo">ROOM - CONNECT</a>
        <nav class="profile-nav" aria-label="Điều hướng chính">
            <a href="${pageContext.request.contextPath}/">Trang chủ</a>
            <a href="${pageContext.request.contextPath}/gioi-thieu">Về chúng tôi</a>
            <a href="${pageContext.request.contextPath}/lien-he">Liên hệ</a>
            <a href="${pageContext.request.contextPath}/thue-tro">Tìm phòng</a>
        </nav>
        <div class="profile-header-user">
            <button type="button" id="headerUserBtn" class="header-user-btn" aria-label="Mở menu tài khoản">
                <i class="fa-solid fa-circle-user"></i>
            </button>
            <div id="headerUserMenu" class="header-user-menu hidden">
                <a href="#top">Hồ sơ cá nhân</a>
                <button type="button" id="headerLogoutBtn">Đăng xuất</button>
            </div>
        </div>
    </div>
</header>

<main id="top" class="profile-shell">
    <div class="profile-breadcrumb"><a href="${pageContext.request.contextPath}/">Trang chủ</a><i class="fa-solid fa-chevron-right"></i><strong>Hồ sơ cá nhân</strong></div>

    <div id="profileLoading" class="profile-loading">
        <div class="spinner"></div>
        <span>Đang tải hồ sơ...</span>
    </div>

    <div id="profileError" class="profile-error hidden"></div>

    <div id="profileLayout" class="profile-layout hidden">
        <!-- SIDEBAR -->
        <aside class="profile-sidebar">
            <section class="profile-user-card">
                <div class="avatar-wrap">
                    <img id="profileAvatar" src="https://ui-avatars.com/api/?name=U&background=5e574c&color=fff&size=160" alt="Ảnh đại diện">
                    <span class="verified-badge" title="Tài khoản đã xác minh"><i class="fa-solid fa-check"></i></span>
                </div>
                <h2 id="profileName">Người dùng</h2>
                <p id="profileEmail">—</p>
                <span class="verified-text"><i class="fa-solid fa-shield-halved"></i> Tài khoản đã xác minh</span>
            </section>

            <nav class="profile-menu">
                <button class="profile-menu-item active" data-target="dashboardSection"><i class="fa-regular fa-circle-user"></i><span>Thông tin cá nhân</span></button>
                <button class="profile-menu-item" data-target="appointmentsSection"><i class="fa-regular fa-calendar-check"></i><span>Lịch hẹn của tôi</span></button>
                <button class="profile-menu-item" data-target="contractsSection"><i class="fa-regular fa-file-lines"></i><span>Hợp đồng của tôi</span></button>
                <button class="profile-menu-item" data-target="savedSection"><i class="fa-regular fa-bookmark"></i><span>Phòng đã lưu</span></button>
                <button class="profile-menu-item" data-target="historySection"><i class="fa-solid fa-clock-rotate-left"></i><span>Lịch sử xem phòng</span></button>
                <div class="menu-divider"></div>
                <button class="profile-menu-item" data-action="edit-profile"><i class="fa-solid fa-gear"></i><span>Cài đặt</span></button>
                <button class="profile-menu-item danger" id="sidebarLogoutBtn"><i class="fa-solid fa-right-from-bracket"></i><span>Đăng xuất</span></button>
            </nav>

            <section class="support-card">
                <h3>Liên hệ &amp; hỗ trợ</h3>
                <a href="tel:0123456789"><i class="fa-solid fa-phone"></i><strong>0123456789</strong></a>
                <a href="javascript:void(0)" onclick="alert('Đội ngũ hỗ trợ Room Connect đang trực tuyến 24/7.')"><i class="fa-solid fa-headset"></i><strong>Hỗ trợ 24/7</strong></a>
            </section>
        </aside>

        <!-- CONTENT -->
        <section class="profile-content">
            <div id="dashboardSection">
                <div class="stats-grid">
                    <article class="stat-card saved-stat">
                        <div><span>Phòng đã lưu</span><strong id="savedCount">00</strong></div>
                        <i class="fa-regular fa-bookmark"></i>
                    </article>
                    <article class="stat-card appointment-stat">
                        <div><span>Lịch hẹn sắp tới</span><strong id="appointmentCount">00</strong></div>
                        <i class="fa-regular fa-calendar-check"></i>
                    </article>
                </div>
            </div>

            <section id="savedSection" class="profile-section">
                <div class="section-heading"><h2>Phòng đã lưu</h2><button type="button" class="text-link" data-show="saved">Xem tất cả</button></div>
                <div id="savedRooms" class="saved-grid"></div>
                <div id="savedEmpty" class="empty-card hidden"><i class="fa-regular fa-heart"></i><strong>Chưa có phòng đã lưu</strong><span>Hãy bấm biểu tượng trái tim khi bạn tìm được phòng ưng ý.</span><a href="${pageContext.request.contextPath}/thue-tro">Tìm phòng ngay</a></div>
            </section>

            <section id="historySection" class="profile-section">
                <div class="section-heading"><h2>Lịch sử phòng đã xem</h2><button type="button" class="text-link" data-show="history">Xem tất cả</button></div>
                <div id="historyRooms" class="history-list"></div>
                <div id="historyEmpty" class="empty-card hidden"><i class="fa-solid fa-clock-rotate-left"></i><strong>Chưa có lịch sử xem phòng</strong><span>Những phòng bạn đã mở sẽ xuất hiện tại đây.</span></div>
            </section>

            <section id="contractsSection" class="profile-section">
                <div class="section-heading"><h2>Hợp đồng gần đây</h2><button type="button" class="text-link" data-show="contracts">Xem tất cả</button></div>
                <div id="contractsList" class="contract-list"></div>
                <div id="contractsEmpty" class="empty-card hidden"><i class="fa-regular fa-file-lines"></i><strong>Chưa có hợp đồng</strong><span>Hợp đồng thuê của bạn sẽ được hiển thị tại đây.</span></div>
            </section>

            <section id="appointmentsSection" class="profile-section">
                <div class="section-heading"><h2>Lịch hẹn sắp tới</h2><a class="new-appointment" href="${pageContext.request.contextPath}/thue-tro"><i class="fa-solid fa-plus"></i> Đặt lịch mới</a></div>
                <div id="appointmentsList" class="appointment-list"></div>
                <div id="appointmentsEmpty" class="empty-card hidden"><i class="fa-regular fa-calendar-xmark"></i><strong>Không có lịch hẹn sắp tới</strong><span>Bạn có thể đặt lịch xem phòng từ trang tìm phòng.</span><a href="${pageContext.request.contextPath}/thue-tro">Tìm phòng</a></div>
            </section>

            <section id="profileInfoSection" class="profile-section profile-info-section">
                <div class="section-heading"><div><h2>Thông tin cá nhân</h2><p>Cập nhật thông tin liên hệ và hồ sơ của bạn.</p></div><button type="button" id="editProfileBtn" class="outline-btn"><i class="fa-solid fa-pen"></i> Chỉnh sửa</button></div>
                <div class="profile-info-grid">
                    <div><span>Họ và tên</span><strong id="infoHoTen">—</strong></div>
                    <div><span>Email</span><strong id="infoEmail">—</strong></div>
                    <div><span>Số điện thoại</span><strong id="infoPhone">—</strong></div>
                    <div><span>Ngày sinh</span><strong id="infoBirthday">—</strong></div>
                    <div><span>Giới tính</span><strong id="infoGender">—</strong></div>
                    <div><span>Ngày tham gia</span><strong id="infoJoinDate">—</strong></div>
                    <div class="full"><span>Địa chỉ</span><strong id="infoAddress">—</strong></div>
                </div>
            </section>
        </section>
    </div>
</main>

<!-- EDIT PROFILE MODAL -->
<div id="profileModal" class="profile-modal hidden" role="dialog" aria-modal="true" aria-labelledby="profileModalTitle">
    <div class="modal-backdrop" data-close-modal></div>
    <section class="modal-card">
        <div class="modal-head"><div><h2 id="profileModalTitle">Chỉnh sửa hồ sơ</h2><p>Cập nhật thông tin cá nhân của bạn.</p></div><button type="button" class="modal-close" data-close-modal><i class="fa-solid fa-xmark"></i></button></div>
        <div id="profileAlert" class="profile-alert hidden"></div>
        <form id="profileForm" class="profile-form">
            <label>Họ và tên<input id="editHoTen" required maxlength="255"></label>
            <label>Email<input id="editEmail" type="email" disabled></label>
            <label>Số điện thoại<input id="editPhone" type="tel" maxlength="20"></label>
            <label>Ngày sinh<input id="editBirthday" type="date"></label>
            <fieldset><legend>Giới tính</legend><label class="radio"><input type="radio" name="editGender" value="true"> Nam</label><label class="radio"><input type="radio" name="editGender" value="false"> Nữ</label></fieldset>
            <label class="full">Địa chỉ<input id="editAddress" maxlength="500"></label>
            <div class="form-actions"><button type="button" class="outline-btn" data-close-modal>Hủy</button><button type="submit" id="profileSaveBtn" class="primary-btn">Lưu thay đổi</button></div>
        </form>
    </section>
</div>

<!-- RESCHEDULE MODAL -->
<div id="appointmentModal" class="profile-modal hidden" role="dialog" aria-modal="true">
    <div class="modal-backdrop" data-close-appointment></div>
    <section class="modal-card small-modal">
        <div class="modal-head"><div><h2>Dời lịch hẹn</h2><p>Chọn thời gian mới cho lịch xem phòng.</p></div><button type="button" class="modal-close" data-close-appointment><i class="fa-solid fa-xmark"></i></button></div>
        <form id="appointmentForm" class="profile-form">
            <input type="hidden" id="appointmentId">
            <label>Ngày hẹn<input id="appointmentDate" type="date" required></label>
            <label>Giờ hẹn<input id="appointmentTime" type="time" required></label>
            <label class="full">Địa điểm<input id="appointmentPlace" maxlength="255"></label>
            <div class="form-actions"><button type="button" class="outline-btn" data-close-appointment>Hủy</button><button type="submit" class="primary-btn">Lưu lịch mới</button></div>
        </form>
    </section>
</div>

<script src="${pageContext.request.contextPath}/resources/js/api.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/profile-khach-thue.js"></script>
</body>
</html>
