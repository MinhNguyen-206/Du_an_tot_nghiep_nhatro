<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Chi tiết phòng - ROOM - CONNECT</title>

    <style>

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background: #eef1f5;
            color: #111;
        }

        .navbar {
            background: #fff;
            padding: 12px 6%;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid #eee;
            position: sticky;
            top: 0;
            z-index: 100;
        }

        .logo {
            font-weight: 800;
            font-size: 20px;
            text-decoration: none;
            color: #111;
        }

        .nav-links {
            display: flex;
            gap: 20px;
        }

        .nav-links a {
            text-decoration: none;
            color: #555;
            font-size: 14px;
        }

        .nav-links a:hover {
            color: #ff3345;
        }

        .container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 0 20px;
        }

        /* ---------- LOADING / ERROR STATE ---------- */

        .state-box {
            background: #fff;
            border: 1px solid #eaeaea;
            border-radius: 12px;
            padding: 60px 20px;
            text-align: center;
            color: #666;
            font-size: 14px;
        }

        .state-box.hidden {
            display: none;
        }

        .spinner {
            width: 34px;
            height: 34px;
            border: 3px solid #eee;
            border-top-color: #ff3345;
            border-radius: 50%;
            margin: 0 auto 14px;
            animation: spin 0.8s linear infinite;
        }

        @keyframes spin {
            to { transform: rotate(360deg); }
        }

        .state-box .retry-btn {
            margin-top: 14px;
            border: 1px solid #ff3345;
            background: #fff;
            color: #ff3345;
            padding: 8px 18px;
            border-radius: 8px;
            font-weight: 700;
            cursor: pointer;
        }

        #pageContent.hidden {
            display: none;
        }

        .breadcrumb {
            font-size: 13px;
            color: #777;
            margin-bottom: 15px;
        }

        .breadcrumb a {
            color: #555;
            text-decoration: none;
        }

        .gallery {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 8px;
            height: 420px;
            border-radius: 16px;
            overflow: hidden;
            margin-bottom: 25px;
            background: #ddd;
        }

        .gallery-main img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .gallery-sub {
            display: grid;
            grid-template-columns: 1fr 1fr;
            grid-template-rows: 1fr 1fr;
            gap: 8px;
        }

        .gallery-sub img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .gallery-more {
            position: relative;
        }

        .gallery-more-overlay {
            position: absolute;
            inset: 0;
            background: rgba(0,0,0,0.5);
            color: #fff;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            font-size: 14px;
        }

        .detail-layout {
            display: grid;
            grid-template-columns: 1fr 340px;
            gap: 30px;
        }

        .tags {
            display: flex;
            gap: 8px;
            margin-bottom: 10px;
        }

        .tag {
            background: #555;
            color: white;
            padding: 4px 12px;
            border-radius: 12px;
            font-size: 11px;
            font-weight: bold;
        }

        .tag.red {
            background: #ff3345;
        }

        .tag.gray {
            background: #888;
        }

        .title {
            font-size: 26px;
            font-weight: 800;
            margin-bottom: 8px;
            line-height: 1.3;
        }

        .location {
            font-size: 14px;
            color: #666;
            margin-bottom: 20px;
        }

        .specs-bar {
            background: #fff;
            border-radius: 12px;
            padding: 15px;
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            text-align: center;
            border: 1px solid #eaeaea;
            margin-bottom: 25px;
        }

        .spec-item {
            border-right: 1px solid #eee;
        }

        .spec-item:last-child {
            border-right: none;
        }

        .spec-label {
            font-size: 11px;
            color: #888;
            text-transform: uppercase;
            margin-bottom: 4px;
        }

        .spec-value {
            font-size: 18px;
            font-weight: 800;
            color: #ff3345;
        }

        .section-title {
            font-size: 16px;
            font-weight: 700;
            margin: 25px 0 12px;
            border-bottom: 2px solid #ddd;
            padding-bottom: 6px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .amenities-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 10px;
        }

        .amenity-chip {
            background: #fff;
            border: 1px solid #e0e0e0;
            padding: 8px 12px;
            border-radius: 8px;
            font-size: 12px;
            text-align: center;
        }

        .description-text {
            background: #fff;
            padding: 20px;
            border-radius: 12px;
            border: 1px solid #eaeaea;
            font-size: 13.5px;
            line-height: 1.7;
            color: #444;
            white-space: pre-line;
        }

        .map-box {
            height: 250px;
            border-radius: 12px;
            overflow: hidden;
            margin-top: 10px;
        }

        .map-box iframe {
            width: 100%;
            height: 100%;
            border: 0;
        }

        .sidebar-card {
            background: #fbf8f8;
            border: 1px solid #f0e6e6;
            border-radius: 16px;
            padding: 20px;
            margin-bottom: 20px;
        }

        .owner-profile {
            display: flex;
            align-items: center;
            gap: 12px;
            margin-bottom: 15px;
        }

        .owner-avatar {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            object-fit: cover;
            background: #ddd;
        }

        .owner-name {
            font-weight: bold;
            font-size: 15px;
        }

        .btn-action {
            width: 100%;
            border: none;
            padding: 12px;
            border-radius: 8px;
            font-weight: bold;
            color: white;
            cursor: pointer;
            margin-bottom: 8px;
            font-size: 13px;
            text-align: center;
            text-decoration: none;
            display: block;
        }

        .btn-call {
            background: #7036ff;
        }

        .btn-chat {
            background: #08c5ed;
        }

        .btn-book {
            background: #05c99b;
        }

        .btn-rent {
            background: #ff3345;
            font-size: 14px;
            padding: 13px;
        }

        .btn-deposit {
            background: #fff;
            color: #ff3345;
            border: 1.5px solid #ff3345;
            font-size: 14px;
            padding: 13px;
        }

        .cta-row {
            display: flex;
            gap: 8px;
        }

        .cta-row .btn-action {
            margin-bottom: 12px;
        }

        /* ---------- MODAL YEU CAU THUE / DAT COC ---------- */

        .rent-modal-overlay {
            position: fixed;
            inset: 0;
            background: rgba(0, 0, 0, .5);
            display: flex;
            align-items: center;
            justify-content: center;
            z-index: 1000;
            padding: 16px;
        }

        .rent-modal-overlay.hidden {
            display: none;
        }

        .rent-modal {
            background: #fff;
            border-radius: 14px;
            width: 100%;
            max-width: 420px;
            padding: 24px;
            max-height: 90vh;
            overflow-y: auto;
        }

        .rent-modal h3 {
            margin: 0 0 4px;
            font-size: 18px;
        }

        .rent-modal .rent-modal-sub {
            font-size: 13px;
            color: #777;
            margin: 0 0 18px;
        }

        .rent-modal label {
            display: block;
            font-size: 13px;
            font-weight: 600;
            margin-bottom: 6px;
            margin-top: 14px;
        }

        .rent-modal input[type="date"],
        .rent-modal textarea {
            width: 100%;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 10px 12px;
            font-size: 13px;
            font-family: inherit;
            box-sizing: border-box;
        }

        .rent-modal textarea {
            resize: vertical;
            min-height: 80px;
        }

        .rent-modal-note {
            font-size: 12px;
            color: #999;
            margin-top: 6px;
        }

        .rent-modal-actions {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }

        .rent-modal-actions button {
            flex: 1;
            border-radius: 8px;
            padding: 11px;
            font-weight: bold;
            font-size: 13px;
            cursor: pointer;
            border: none;
        }

        #rentModalCancel {
            background: #f2f2f2;
            color: #444;
        }

        #rentModalSubmit {
            background: #ff3345;
            color: #fff;
        }

        #rentModalSubmit:disabled {
            opacity: .6;
            cursor: default;
        }

        .rent-modal-error {
            background: #fdecea;
            color: #c0392b;
            border-radius: 8px;
            padding: 10px 12px;
            font-size: 12px;
            margin-top: 14px;
        }

        .rent-modal-error.hidden {
            display: none;
        }

        #saveRoomBtn {
            width: 100%;
            border: 1px solid #ddd;
            padding: 9px 14px;
            border-radius: 8px;
            background: #fff;
            color: #444;
            font-weight: 700;
            cursor: pointer;
            margin-bottom: 12px;
        }

        #saveRoomBtn.active {
            color: #e44b3c;
            border-color: #f3c9c5;
        }

        .cost-table {
            background: #fff;
            border-radius: 12px;
            border: 1px solid #eee;
            overflow: hidden;
            font-size: 12px;
        }

        .cost-row {
            display: flex;
            justify-content: space-between;
            padding: 10px 15px;
            border-bottom: 1px solid #eee;
        }

        .cost-row:last-child {
            border-bottom: none;
        }

        .cost-price {
            font-weight: bold;
            color: #ff3345;
        }

        /* ---------- REVIEWS ---------- */

        .review-summary {
            display: flex;
            align-items: baseline;
            gap: 10px;
            font-size: 13px;
            color: #666;
            font-weight: normal;
        }

        .review-summary b {
            color: #ff9900;
            font-size: 18px;
        }

        .review-card {
            background: #fff;
            border: 1px solid #eaeaea;
            border-radius: 12px;
            padding: 15px;
            margin-bottom: 10px;
            display: flex;
            gap: 12px;
        }

        .review-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            object-fit: cover;
            flex-shrink: 0;
            background: #ddd;
        }

        .review-name {
            font-weight: bold;
            font-size: 13px;
        }

        .review-stars {
            color: #ff9900;
            font-size: 12px;
            margin: 2px 0 6px;
        }

        .review-content {
            font-size: 13px;
            color: #444;
            line-height: 1.5;
        }

        .review-date {
            font-size: 11px;
            color: #999;
        }

        .empty-note {
            font-size: 13px;
            color: #888;
            padding: 10px 2px;
        }

        .similar-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            margin-top: 15px;
        }

        .similar-card {
            background: #fff;
            border-radius: 12px;
            overflow: hidden;
            border: 1px solid #eee;
            text-decoration: none;
            color: #111;
        }

        .similar-card img {
            width: 100%;
            height: 140px;
            object-fit: cover;
            background: #ddd;
        }

        .similar-body {
            padding: 12px;
        }

        @media (max-width: 992px) {

            .detail-layout {
                grid-template-columns: 1fr;
            }

            .similar-grid {
                grid-template-columns: repeat(2, 1fr);
            }
            .rent-modal select {
    width: 100%;
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 10px 12px;
    font-size: 13px;
    font-family: inherit;
    box-sizing: border-box;
    background: #fff;
}

.rent-modal-row {
    display: flex;
    gap: 10px;
}

.rent-modal-row > div {
    flex: 1;
}

#rentModalSoNguoiWrap.hidden {
    display: none;
}

        }

    </style>

</head>

<body data-context-path="${pageContext.request.contextPath}" data-room-id="${roomId}">


<!-- NAVBAR -->

<nav class="navbar">

    <a href="${pageContext.request.contextPath}/thue-tro"
       class="logo">

        ROOM - CONNECT

    </a>


    <div class="nav-links">

        <a href="${pageContext.request.contextPath}/thue-tro">

            Thuê trọ

        </a>

        <a href="${pageContext.request.contextPath}/thue-can-ho">

            Thuê căn hộ

        </a>

        <a href="#">

            Về chúng tôi

        </a>

        <a href="#">

            Liên hệ

        </a>

    </div>

</nav>


<div class="container">

    <!-- LOADING -->
    <div id="loadingState" class="state-box">
        <div class="spinner"></div>
        Đang tải thông tin phòng...
    </div>

    <!-- ERROR -->
    <div id="errorState" class="state-box hidden">
        <div id="errorMessage">Không thể tải thông tin phòng. Vui lòng thử lại.</div>
        <button class="retry-btn" id="retryBtn" type="button">Thử lại</button>
    </div>

    <!-- NỘI DUNG CHÍNH (ẩn cho tới khi tải xong) -->
    <div id="pageContent" class="hidden">

        <!-- BREADCRUMB -->

        <div class="breadcrumb">

            <a href="${pageContext.request.contextPath}/thue-tro">
                Trang chủ
            </a>

            ›

            <a href="#">
                Hồ Chí Minh
            </a>

            ›

            <a href="#" id="breadcrumbNhaTro">
                Nhà trọ
            </a>

            ›

            <b id="breadcrumbTitle"></b>

        </div>


        <!-- GALLERY -->

        <div class="gallery">

            <div class="gallery-main">

                <img id="galleryMainImg" src="" alt="Ảnh phòng">

            </div>


            <div class="gallery-sub" id="gallerySub">
                <!-- render bằng JS: tối đa 3 ảnh phụ + overlay "xem tất cả" -->
            </div>

        </div>


        <!-- DETAIL -->

        <div class="detail-layout">


            <div class="main-info">


                <div class="tags">
                    <span class="tag">CHO THUÊ</span>
                    <span class="tag red" id="typeTag"></span>
                    <span class="tag gray hidden" id="statusTag"></span>
                </div>


                <h1 class="title" id="roomTitle"></h1>

                <button id="saveRoomBtn" type="button">
                    ♡ Lưu phòng
                </button>


                <div class="location">
                    📍 <span id="roomAddress"></span>
                </div>


                <!-- SPECS -->

                <div class="specs-bar">

                    <div class="spec-item">
                        <div class="spec-label">Giá thuê</div>
                        <div class="spec-value" id="specPrice"></div>
                    </div>

                    <div class="spec-item">
                        <div class="spec-label">Diện tích</div>
                        <div class="spec-value" style="color:#333;" id="specArea"></div>
                    </div>

                    <div class="spec-item">
                        <div class="spec-label">Sức chứa</div>
                        <div class="spec-value" style="color:#333;" id="specCapacity"></div>
                    </div>

                    <div class="spec-item">
                        <div class="spec-label">Trạng thái</div>
                        <div class="spec-value" style="color:#333;" id="specStatus"></div>
                    </div>

                </div>


                <!-- TIỆN ÍCH -->

                <div class="section-title">
                    Tiện ích phòng
                </div>

                <div class="amenities-grid" id="amenitiesGrid">
                    <!-- render bằng JS -->
                </div>


                <!-- DESCRIPTION -->

                <div class="section-title">
                    Thông tin chi tiết
                </div>

                <div class="description-text" id="descriptionText"></div>


                <!-- ĐÁNH GIÁ -->

                <div class="section-title">
                    <span>Đánh giá</span>
                    <span class="review-summary" id="reviewSummary"></span>
                </div>

                <div id="reviewsList">
                    <!-- render bằng JS -->
                </div>


                <!-- MAP -->

                <div class="section-title">
                    Vị trí trên bản đồ
                </div>

                <div class="map-box">
                    <iframe id="mapFrame" src="" loading="lazy"></iframe>
                </div>


            </div>


            <!-- SIDEBAR -->
            <aside class="sidebar">


                <div class="sidebar-card">


                    <div class="owner-profile">

                        <img class="owner-avatar" id="ownerAvatar" src="" alt="Chủ trọ">

                        <div>
                            <div class="owner-name" id="ownerName"></div>
                            <div style="font-size:11px;color:#28a745;" id="ownerVerified"></div>
                        </div>

                    </div>


                    <button class="btn-action btn-rent" id="rentNowBtn" type="button">
                        🏠 Thuê phòng ngay
                    </button>

                    <button class="btn-action btn-deposit" id="depositBtn" type="button">
                        🔒 Đặt cọc giữ phòng
                    </button>


                    <a class="btn-action btn-call" id="ownerCallBtn" href="#">
                        📞 <span id="ownerPhoneText"></span>
                    </a>


                    <button class="btn-action btn-chat" id="chatBtn" type="button">
                        💬 Nhắn tin ngay
                    </button>


                    <button class="btn-action btn-book" id="bookBtn" type="button">
                        📅 Đặt lịch xem phòng
                    </button>

                </div>


                <!-- COST -->
                <div class="cost-table">

                    <div class="cost-row">
                        <span>⚡ Tiền điện</span>
                        <span class="cost-price" id="costElectricity"></span>
                    </div>

                    <div class="cost-row">
                        <span>💧 Tiền nước</span>
                        <span class="cost-price" id="costWater"></span>
                    </div>

                    <div class="cost-row">
                        <span>🛵 Phí gửi xe</span>
                        <span class="cost-price" id="costParking"></span>
                    </div>

                    <div class="cost-row">
                        <span>📶 Tiền Internet / Wifi</span>
                        <span class="cost-price" id="costInternet"></span>
                    </div>

                </div>


            </aside>


        </div>


        <!-- PHÒNG TƯƠNG TỰ -->

        <div style="margin-top:40px;" id="similarSection">

            <div class="section-title">
                Phòng tương tự
            </div>

            <div class="similar-grid" id="similarGrid">
                <!-- render bằng JS -->
            </div>

            <div class="empty-note hidden" id="similarEmpty">
                Chưa tìm thấy phòng tương tự cùng khu vực.
            </div>

        </div>


    </div>

</div>


<!-- MODAL: GUI YEU CAU THUE / DAT COC -->
<div class="rent-modal-overlay hidden" id="rentModalOverlay">
    <div class="rent-modal">

        <h3 id="rentModalTitle">Thuê phòng ngay</h3>
        <p class="rent-modal-sub" id="rentModalSub">Gửi yêu cầu tới chủ trọ, chủ trọ sẽ liên hệ lại để xác nhận.</p>

        <div class="rent-modal-error hidden" id="rentModalError"></div>

        <label for="rentModalHinhThuc">Hình thức thuê</label>
        <select id="rentModalHinhThuc">
            <option value="DON">Ở một mình</option>
            <option value="NHOM">Ở ghép (nhiều người)</option>
        </select>

        <div id="rentModalSoNguoiWrap" class="hidden">
            <label for="rentModalSoNguoi">Số người ở cùng (kể cả bạn)</label>
            <input type="number" id="rentModalSoNguoi" min="2" max="20" placeholder="VD: 2">
        </div>

        <div class="rent-modal-row">
            <div>
                <label for="rentModalThoiHan">Thời hạn thuê</label>
                <input type="number" id="rentModalThoiHan" min="1" placeholder="VD: 6">
            </div>
            <div>
                <label for="rentModalDonVi">Đơn vị</label>
                <select id="rentModalDonVi">
                    <option value="Tháng">Tháng</option>
                    <option value="Năm">Năm</option>
                </select>
            </div>
        </div>

        <label for="rentModalDate">Ngày muốn nhận phòng</label>
        <input type="date" id="rentModalDate">

        <label for="rentModalPhone">Số điện thoại liên hệ</label>
        <input type="tel" id="rentModalPhone" placeholder="VD: 0987654321">

        <label for="rentModalNote">Ghi chú cho chủ trọ</label>
        <textarea id="rentModalNote" placeholder="VD: Mình muốn xem phòng trước, thời gian thuê dự kiến..."></textarea>

        <p class="rent-modal-note" id="rentModalFootnote"></p>

        <div class="rent-modal-actions">
            <button type="button" id="rentModalCancel">Để sau</button>
            <button type="button" id="rentModalSubmit">Gửi yêu cầu</button>
        </div>

    </div>
</div>


<script src="${pageContext.request.contextPath}/resources/js/api.js"></script>
<script>
(function () {
    'use strict';

    var ctx = document.body.dataset.contextPath || '';
    var params = new URLSearchParams(window.location.search);
    var roomId = params.get('id') || document.body.dataset.roomId;

    var loadingEl = document.getElementById('loadingState');
    var errorEl = document.getElementById('errorState');
    var errorMessageEl = document.getElementById('errorMessage');
    var contentEl = document.getElementById('pageContent');
    var retryBtn = document.getElementById('retryBtn');

    var currentUser = null;
    try { currentUser = JSON.parse(localStorage.getItem('user') || 'null'); } catch (e) { currentUser = null; }
    var token = localStorage.getItem('token');

    var currentRoom = null;

    function escapeHtml(value) {
        return String(value == null ? '' : value).replace(/[&<>"']/g, function (c) {
            return ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' })[c];
        });
    }

    function formatMoney(value) {
        var n = Number(value || 0);
        return new Intl.NumberFormat('vi-VN').format(n) + 'đ';
    }

    function formatDate(value) {
        if (!value) return '';
        var d = new Date(value);
        return isNaN(d) ? '' : d.toLocaleDateString('vi-VN');
    }

    function fallbackImage() {
        return 'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=900&q=80';
    }

    function amenityIcon(name) {
        var n = String(name || '').toLowerCase();
        if (n.indexOf('wifi') >= 0 || n.indexOf('internet') >= 0) return '📶';
        if (n.indexOf('điều hòa') >= 0 || n.indexOf('máy lạnh') >= 0) return '❄';
        if (n.indexOf('giặt') >= 0) return '🧺';
        if (n.indexOf('camera') >= 0 || n.indexOf('an ninh') >= 0) return '📹';
        if (n.indexOf('ban công') >= 0) return '🌿';
        if (n.indexOf('gác') >= 0) return '🏠';
        if (n.indexOf('xe') >= 0) return '🛵';
        if (n.indexOf('vân tay') >= 0 || n.indexOf('khóa') >= 0) return '🔐';
        return '✅';
    }

    function stars(soSao) {
        var n = Math.round(Number(soSao || 0));
        return '★★★★★☆☆☆☆☆'.slice(5 - n, 10 - n);
    }

    function showError(message) {
        loadingEl.classList.add('hidden');
        contentEl.classList.add('hidden');
        errorEl.classList.remove('hidden');
        errorMessageEl.textContent = message || 'Không thể tải thông tin phòng. Vui lòng thử lại.';
    }

    function render(room) {
        currentRoom = room;

        document.title = (room.tieuDe || room.tenPhong || 'Chi tiết phòng') + ' - ROOM - CONNECT';

        document.getElementById('breadcrumbNhaTro').textContent = room.tenNhaTro || 'Nhà trọ';
        document.getElementById('breadcrumbTitle').textContent = room.tieuDe || room.tenPhong || '';
        document.getElementById('roomTitle').textContent = room.tieuDe || room.tenPhong || '';
        document.getElementById('roomAddress').textContent = room.diaChi || 'Đang cập nhật';
        document.getElementById('typeTag').textContent = room.loaiPhong || 'Phòng trọ';

        var statusTag = document.getElementById('statusTag');
        if (room.conPhong === false) {
            statusTag.textContent = 'ĐÃ CHO THUÊ';
            statusTag.classList.remove('hidden');
        } else {
            statusTag.classList.add('hidden');
        }

        // Gallery
        var images = (room.hinhAnh || []).slice().sort(function (a, b) {
            return (a.thuTuHienThi || 0) - (b.thuTuHienThi || 0);
        });
        var mainImg = document.getElementById('galleryMainImg');
        mainImg.src = images.length ? images[0].duongDan : fallbackImage();
        mainImg.onerror = function () { mainImg.src = fallbackImage(); };

        var subWrap = document.getElementById('gallerySub');
        subWrap.innerHTML = '';
        var subImages = images.slice(1, 4);
        if (subImages.length === 0 && images.length <= 1) {
            // Không có ảnh phụ riêng -> vẫn hiển thị 3 ô placeholder cho đủ bố cục
            subImages = [];
        }
        subImages.forEach(function (img, idx) {
            var el = document.createElement('img');
            el.src = img.duongDan || fallbackImage();
            el.alt = img.moTa || room.tenPhong || '';
            el.onerror = function () { el.src = fallbackImage(); };
            if (idx === subImages.length - 1 && images.length > 4) {
                var wrap = document.createElement('div');
                wrap.className = 'gallery-more';
                var overlay = document.createElement('div');
                overlay.className = 'gallery-more-overlay';
                overlay.innerHTML = '<span>▦</span><span>Xem tất cả ' + images.length + ' ảnh</span>';
                wrap.appendChild(el);
                wrap.appendChild(overlay);
                subWrap.appendChild(wrap);
            } else {
                subWrap.appendChild(el);
            }
        });

        // Specs
        document.getElementById('specPrice').textContent = formatMoney(room.giaPhong) + '/tháng';
        document.getElementById('specArea').textContent = (room.dienTich != null ? room.dienTich : '—') + ' m²';
        document.getElementById('specCapacity').textContent = (room.soLuongNguoi != null ? room.soLuongNguoi : '—') + ' người';
        document.getElementById('specStatus').textContent = room.conPhong === false ? 'Đã cho thuê' : 'Còn trống';

        // Tiện ích
        var amenitiesGrid = document.getElementById('amenitiesGrid');
        var tienIch = room.tienIch || [];
        if (tienIch.length) {
            amenitiesGrid.innerHTML = tienIch.map(function (t) {
                return '<div class="amenity-chip">' + amenityIcon(t.tenTienIch) + ' ' + escapeHtml(t.tenTienIch) + '</div>';
            }).join('');
        } else {
            amenitiesGrid.innerHTML = '<div class="empty-note">Chưa có thông tin tiện ích cho phòng này.</div>';
        }

        // Mô tả
        document.getElementById('descriptionText').textContent = room.moTa || 'Chủ trọ chưa cập nhật mô tả chi tiết cho phòng này.';

        // Đánh giá
        var reviewSummary = document.getElementById('reviewSummary');
        if (room.soLuongDanhGia > 0) {
            reviewSummary.innerHTML = '<b>' + (room.soSaoTrungBinh || 0).toFixed(1) + '★</b> (' + room.soLuongDanhGia + ' đánh giá)';
        } else {
            reviewSummary.textContent = 'Chưa có đánh giá';
        }

        var reviewsList = document.getElementById('reviewsList');
        var danhGia = room.danhGia || [];
        if (danhGia.length) {
            reviewsList.innerHTML = danhGia.map(function (dg) {
                return '<div class="review-card">' +
                    '<img class="review-avatar" src="' + escapeHtml(dg.avatarNguoiDanhGia || fallbackImage()) + '" ' +
                    'onerror="this.src=\'' + fallbackImage() + '\'" alt="">' +
                    '<div>' +
                    '<div class="review-name">' + escapeHtml(dg.tenNguoiDanhGia || 'Người dùng') + '</div>' +
                    '<div class="review-stars">' + stars(dg.soSao) + '</div>' +
                    '<div class="review-content">' + escapeHtml(dg.noiDung || '') + '</div>' +
                    '<div class="review-date">' + formatDate(dg.ngayDanhGia) + '</div>' +
                    '</div>' +
                    '</div>';
            }).join('');
        } else {
            reviewsList.innerHTML = '<div class="empty-note">Chưa có đánh giá nào cho phòng này.</div>';
        }

        // Map (dùng địa chỉ để search trên Google Maps, không cần toạ độ chính xác)
        var mapFrame = document.getElementById('mapFrame');
        var query = encodeURIComponent(room.diaChi || room.tenNhaTro || 'Hồ Chí Minh');
        mapFrame.src = 'https://www.google.com/maps?q=' + query + '&output=embed';

        // Chủ trọ
        var chuTro = room.chuTro || {};
        document.getElementById('ownerAvatar').src = chuTro.avatar || fallbackImage();
        document.getElementById('ownerAvatar').onerror = function () { this.src = fallbackImage(); };
        document.getElementById('ownerName').textContent = chuTro.hoTen || 'Chủ trọ';
        document.getElementById('ownerVerified').innerHTML = chuTro.daXacThuc ? '✓ Đã xác thực' : '';
        document.getElementById('ownerPhoneText').textContent = chuTro.soDienThoai || 'Liên hệ';
        var callBtn = document.getElementById('ownerCallBtn');
        callBtn.href = chuTro.soDienThoai ? ('tel:' + chuTro.soDienThoai) : '#';

        // Chi phí
        document.getElementById('costElectricity').textContent = formatMoney(room.giaDien) + '/kWh';
        document.getElementById('costWater').textContent = formatMoney(room.giaNuoc) + '/khối';
        document.getElementById('costParking').textContent = formatMoney(room.giaGuiXe) + '/tháng';
        document.getElementById('costInternet').textContent = formatMoney(room.giaInternet) + '/tháng';

        // Phòng tương tự
        var similarGrid = document.getElementById('similarGrid');
        var similarEmpty = document.getElementById('similarEmpty');
        var phongTuongTu = room.phongTuongTu || [];
        if (phongTuongTu.length) {
            similarGrid.classList.remove('hidden');
            similarEmpty.classList.add('hidden');
            similarGrid.innerHTML = phongTuongTu.map(function (p) {
                return '<a class="similar-card" href="' + ctx + '/chi-tiet-phong?id=' + p.maPhong + '">' +
                    '<img src="' + escapeHtml(p.hinhAnh || fallbackImage()) + '" ' +
                    'onerror="this.src=\'' + fallbackImage() + '\'" alt="' + escapeHtml(p.tenPhong || '') + '">' +
                    '<div class="similar-body">' +
                    '<div style="font-size:13px;font-weight:bold;">' + escapeHtml(p.tenNhaTro || p.tenPhong || '') + '</div>' +
                    '<div style="font-size:11px;color:#777;margin:4px 0;">📍 ' + escapeHtml(p.diaChi || '') + '</div>' +
                    '<div style="font-size:14px;font-weight:bold;color:#ff3345;">' + formatMoney(p.giaPhong) + '/tháng</div>' +
                    '</div>' +
                    '</a>';
            }).join('');
        } else {
            similarGrid.classList.add('hidden');
            similarEmpty.classList.remove('hidden');
        }

        // Nút Lưu phòng
        var saveBtn = document.getElementById('saveRoomBtn');
        setSaveButtonState(room.daYeuThich === true);

        loadingEl.classList.add('hidden');
        errorEl.classList.add('hidden');
        contentEl.classList.remove('hidden');
    }

    function setSaveButtonState(saved) {
        var btn = document.getElementById('saveRoomBtn');
        if (saved) {
            btn.textContent = '♥ Đã lưu phòng';
            btn.classList.add('active');
        } else {
            btn.textContent = '♡ Lưu phòng';
            btn.classList.remove('active');
        }
        btn.dataset.saved = saved ? '1' : '0';
    }

    function loadRoom() {
        if (!roomId) {
            showError('Không tìm thấy phòng bạn muốn xem.');
            return;
        }
        loadingEl.classList.remove('hidden');
        errorEl.classList.add('hidden');
        contentEl.classList.add('hidden');

        apiFetch('/phong-tro/' + roomId + '/chi-tiet')
            .then(function (data) {
                if (!data) return; // apiFetch tự điều hướng /login khi 401
                render(data);

                // Ghi nhận lượt xem (chỉ khi đã đăng nhập) - gọi riêng, không chặn render trang
                if (currentUser && currentUser.maNguoiDung && token) {
                    apiFetch('/profile/' + currentUser.maNguoiDung + '/viewed-rooms/' + roomId, { method: 'POST' }).catch(function () {});
                }
            })
            .catch(function (err) {
                if (err && err.status === 404) {
                    showError('Không tìm thấy phòng trọ này. Có thể phòng đã bị gỡ hoặc không còn tồn tại.');
                } else {
                    showError((err && err.message) || 'Không thể tải thông tin phòng. Vui lòng thử lại.');
                }
            });
    }

    retryBtn.addEventListener('click', loadRoom);

    document.getElementById('saveRoomBtn').addEventListener('click', function () {
        if (!currentUser || !currentUser.maNguoiDung || !token) {
            if (confirm('Bạn cần đăng nhập để lưu phòng. Đăng nhập ngay?')) {
                window.location.href = ctx + '/login';
            }
            return;
        }
        var btn = document.getElementById('saveRoomBtn');
        var alreadySaved = btn.dataset.saved === '1';
        btn.disabled = true;

        var request = alreadySaved
            ? apiFetch('/profile/' + currentUser.maNguoiDung + '/saved-rooms/' + roomId, { method: 'DELETE' })
            : apiFetch('/profile/' + currentUser.maNguoiDung + '/saved-rooms/' + roomId, { method: 'POST' });

        request
            .then(function () { setSaveButtonState(!alreadySaved); })
            .catch(function (err) { alert((err && err.message) || 'Không thể cập nhật trạng thái lưu phòng.'); })
            .finally(function () { btn.disabled = false; });
    });

    document.getElementById('chatBtn').addEventListener('click', function () {
        if (!currentUser || !currentUser.maNguoiDung || !token) {
            if (confirm('Bạn cần đăng nhập để nhắn tin với chủ trọ. Đăng nhập ngay?')) {
                window.location.href = ctx + '/login';
            }
            return;
        }
        // Trang nhắn tin (message/messageBox.jsp) chưa được nối route MVC -
        // tạm hướng dẫn liên hệ qua số điện thoại cho tới khi có route thật.
        alert('Tính năng nhắn tin đang được hoàn thiện. Vui lòng gọi trực tiếp số điện thoại chủ trọ ở trên.');
    });

    document.getElementById('bookBtn').addEventListener('click', function () {
        if (!currentUser || !currentUser.maNguoiDung || !token) {
            if (confirm('Bạn cần đăng nhập để đặt lịch xem phòng. Đăng nhập ngay?')) {
                window.location.href = ctx + '/login';
            }
            return;
        }
        window.location.href = ctx + '/profile#appointments';
    });

        var rentModalOverlay = document.getElementById('rentModalOverlay');
    var rentModalTitle = document.getElementById('rentModalTitle');
    var rentModalSub = document.getElementById('rentModalSub');
    var rentModalHinhThuc = document.getElementById('rentModalHinhThuc');
    var rentModalSoNguoiWrap = document.getElementById('rentModalSoNguoiWrap');
    var rentModalSoNguoi = document.getElementById('rentModalSoNguoi');
    var rentModalThoiHan = document.getElementById('rentModalThoiHan');
    var rentModalDonVi = document.getElementById('rentModalDonVi');
    var rentModalDate = document.getElementById('rentModalDate');
    var rentModalPhone = document.getElementById('rentModalPhone');
    var rentModalNote = document.getElementById('rentModalNote');
    var rentModalFootnote = document.getElementById('rentModalFootnote');
    var rentModalError = document.getElementById('rentModalError');
    var rentModalSubmit = document.getElementById('rentModalSubmit');
    var rentModalMode = 'thue';

    function openRentModal(mode) {
        if (!currentUser || !currentUser.maNguoiDung || !token) {
            if (confirm('Bạn cần đăng nhập để gửi yêu cầu thuê phòng. Đăng nhập ngay?')) {
                window.location.href = ctx + '/login';
            }
            return;
        }
        rentModalMode = mode;
        rentModalError.classList.add('hidden');
        rentModalError.textContent = '';
        rentModalHinhThuc.value = 'DON';
        rentModalSoNguoiWrap.classList.add('hidden');
        rentModalSoNguoi.value = '';
        rentModalThoiHan.value = '6';
        rentModalDonVi.value = 'Tháng';
        rentModalNote.value = '';
        rentModalPhone.value = (currentUser && currentUser.soDienThoai) || '';
        var today = new Date().toISOString().slice(0, 10);
        rentModalDate.value = today;
        rentModalDate.min = today;

        if (mode === 'coc') {
            rentModalTitle.textContent = 'Đặt cọc giữ phòng';
            rentModalSub.textContent = 'Gửi yêu cầu đặt cọc giữ chỗ tới chủ trọ, chủ trọ sẽ liên hệ để xác nhận và hướng dẫn thanh toán cọc.';
            rentModalFootnote.textContent = 'Sau khi chủ trọ duyệt yêu cầu, hệ thống sẽ lập hợp đồng điện tử để hai bên ký online và thanh toán cọc ngay trên trang tiến trình đặt phòng.';
        } else {
            rentModalTitle.textContent = 'Thuê phòng ngay';
            rentModalSub.textContent = 'Gửi yêu cầu thuê tới chủ trọ, chủ trọ sẽ liên hệ lại để xác nhận.';
            rentModalFootnote.textContent = 'Sau khi chủ trọ duyệt yêu cầu, hệ thống sẽ lập hợp đồng điện tử để hai bên ký online.';
        }

        rentModalOverlay.classList.remove('hidden');
    }

    function closeRentModal() {
        rentModalOverlay.classList.add('hidden');
    }

    rentModalHinhThuc.addEventListener('change', function () {
        if (rentModalHinhThuc.value === 'NHOM') {
            rentModalSoNguoiWrap.classList.remove('hidden');
        } else {
            rentModalSoNguoiWrap.classList.add('hidden');
            rentModalSoNguoi.value = '';
        }
    });

    document.getElementById('rentNowBtn').addEventListener('click', function () {
        openRentModal('thue');
    });

    document.getElementById('depositBtn').addEventListener('click', function () {
        openRentModal('coc');
    });

    document.getElementById('rentModalCancel').addEventListener('click', closeRentModal);
    rentModalOverlay.addEventListener('click', function (e) {
        if (e.target === rentModalOverlay) closeRentModal();
    });

    rentModalSubmit.addEventListener('click', function () {
        rentModalError.classList.add('hidden');

        function showError(msg) {
            rentModalError.textContent = msg;
            rentModalError.classList.remove('hidden');
        }

        if (!rentModalDate.value) {
            showError('Vui lòng chọn ngày muốn nhận phòng.');
            return;
        }

        var phone = rentModalPhone.value.trim();
        if (!/^(0|\+84)(3|5|7|8|9)[0-9]{8}$/.test(phone)) {
            showError('Số điện thoại liên hệ không hợp lệ.');
            return;
        }

        var thoiHan = Number(rentModalThoiHan.value);
        if (!thoiHan || thoiHan < 1) {
            showError('Vui lòng nhập thời hạn thuê hợp lệ.');
            return;
        }

        var hinhThuc = rentModalHinhThuc.value;
        var soNguoiCung = null;
        if (hinhThuc === 'NHOM') {
            soNguoiCung = Number(rentModalSoNguoi.value);
            if (!soNguoiCung || soNguoiCung < 2) {
                showError('Ở ghép cần từ 2 người trở lên. Vui lòng nhập lại số người ở cùng.');
                return;
            }
        }

        var ghiChu = rentModalNote.value.trim();
        if (rentModalMode === 'coc') {
            ghiChu = '[Muốn đặt cọc giữ phòng ngay] ' + (ghiChu || 'Mình muốn đặt cọc giữ phòng này càng sớm càng tốt.');
        }

        rentModalSubmit.disabled = true;
        rentModalSubmit.textContent = 'Đang gửi...';

        apiFetch('/yeu-cau-thue', {
            method: 'POST',
            body: {
                phong: { maPhong: Number(roomId) },
                nguoiThue: { maNguoiDung: currentUser.maNguoiDung },
                ngayMuonNhanPhong: rentModalDate.value,
                ghiChu: ghiChu,
                hinhThucThue: hinhThuc,
                soNguoiCung: soNguoiCung,
                thoiHanThue: thoiHan,
                donViThoiHan: rentModalDonVi.value,
                soDienThoaiLienHe: phone
            }
        })
            .then(function (res) {
                if (!res) return; // apiFetch tự điều hướng /login khi 401
                closeRentModal();
                if (res.maYeuCau) {
                    window.location.href = ctx + '/tien-trinh-dat-phong?id=' + res.maYeuCau;
                } else {
                    alert('Đã gửi yêu cầu tới chủ trọ. Chủ trọ sẽ liên hệ với bạn qua số điện thoại/tài khoản đã đăng ký.');
                }
            })
            .catch(function (err) {
                showError((err && err.message) || 'Không thể gửi yêu cầu. Vui lòng thử lại.');
            })
            .finally(function () {
                rentModalSubmit.disabled = false;
                rentModalSubmit.textContent = 'Gửi yêu cầu';
            });
    });

    loadRoom();
})();
</script>

</body>

</html>
