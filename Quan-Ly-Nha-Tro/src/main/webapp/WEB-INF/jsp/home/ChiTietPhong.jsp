<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>${room.title} - ROOM - CONNECT</title>

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
            cursor: pointer;
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

        }

    </style>

</head>

<body>


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

        <a href="#">
            ${room.location}
        </a>

        ›

        <b>
            ${room.title}
        </b>

    </div>


    <!-- GALLERY -->

    <div class="gallery">

        <div class="gallery-main">

            <img src="${room.image}"
                 alt="${room.title}">

        </div>


        <div class="gallery-sub">

            <img src="https://images.unsplash.com/photo-1556911220-e15b29be8c8f?auto=format&fit=crop&w=500&q=80"
                 alt="Bếp">

            <img src="https://images.unsplash.com/photo-1584622650111-993a426fbf0a?auto=format&fit=crop&w=500&q=80"
                 alt="Nhà vệ sinh">

            <img src="https://images.unsplash.com/photo-1600566753190-17f0baa2a6c3?auto=format&fit=crop&w=500&q=80"
                 alt="Phòng khách">

            <div class="gallery-more">

                <img src="https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=500&q=80"
                     alt="Góc khác">

                <div class="gallery-more-overlay">

                    <span>▦</span>

                    <span>
                        Xem tất cả 12 ảnh
                    </span>

                </div>

            </div>

        </div>

    </div>


    <!-- DETAIL -->

    <div class="detail-layout">


        <div class="main-info">


            <div class="tags">

                <span class="tag">
                    CHO THUÊ
                </span>

                <span class="tag red">
                    ${room.category}
                </span>

            </div>


            <h1 class="title">

                ${room.title}

            </h1>


            <div class="location">

                📍 ${room.address}

            </div>


            <!-- SPECS -->

            <div class="specs-bar">


                <div class="spec-item">

                    <div class="spec-label">
                        Giá thuê
                    </div>

                    <div class="spec-value">
                        ${room.price}
                    </div>

                </div>


                <div class="spec-item">

                    <div class="spec-label">
                        Diện tích
                    </div>

                    <div class="spec-value"
                         style="color:#333;">

                        ${room.area}

                    </div>

                </div>


                <div class="spec-item">

                    <div class="spec-label">
                        Tiền cọc
                    </div>

                    <div class="spec-value"
                         style="color:#333;">

                        ${room.deposit}

                    </div>

                </div>


                <div class="spec-item">

                    <div class="spec-label">
                        Trạng thái
                    </div>

                    <div class="spec-value"
                         style="color:#333;">

                        ${room.status}

                    </div>

                </div>


            </div>


            <!-- TIỆN ÍCH -->

            <div class="section-title">
                Tiện ích căn hộ
            </div>


            <div class="amenities-grid">

                <div class="amenity-chip">
                    📶 Wifi
                </div>

                <div class="amenity-chip">
                    ❄ Điều hòa
                </div>

                <div class="amenity-chip">
                    🧺 Máy giặt
                </div>

                <div class="amenity-chip">
                    🛡 An ninh 24/7
                </div>

                <div class="amenity-chip">
                    📹 Camera
                </div>

                <div class="amenity-chip">
                    🌿 Balcony
                </div>

                <div class="amenity-chip">
                    🛵 Bãi xe
                </div>

                <div class="amenity-chip">
                    🧹 Tự do
                </div>

            </div>


            <!-- DESCRIPTION -->

            <div class="section-title">

                Thông tin chi tiết

            </div>


            <div class="description-text">

                ${room.description}

                <br>
                <br>

                • <b>Giờ giấc:</b>
                Tự do (khóa vân tay)

                <br>

                • <b>Số người ở tối đa:</b>
                Tối đa 2 người.

                <br>

                • <b>Hợp đồng tối thiểu:</b>
                Cam kết ở tối thiểu 1 năm.

                <br>

                • <b>Nuôi thú cưng:</b>
                Cho phép nuôi thú cưng nhỏ.

            </div>


            <!-- MAP -->

            <div class="section-title">

                Vị trí trên bản đồ

            </div>


            <div class="map-box">

                <iframe
                        src="https://www.openstreetmap.org/export/embed.html?bbox=106.690%2C10.760%2C106.705%2C10.775&layer=mapnik&marker=10.768%2C106.697">
                </iframe>

            </div>


        </div>


        <!-- SIDEBAR -->

        <aside class="sidebar">


            <div class="sidebar-card">


                <div class="owner-profile">

                    <img class="owner-avatar"
                         src="${room.ownerAvatar}"
                         alt="${room.ownerName}">

                    <div>

                        <div class="owner-name">

                            ${room.ownerName}

                        </div>

                        <div style="font-size:11px;color:#28a745;">

                            ✓ Đã xác thực

                        </div>

                    </div>

                </div>


                <button class="btn-action btn-call">

                    📞 ${room.ownerPhone}

                </button>


                <button class="btn-action btn-chat">

                    💬 Nhắn tin ngay

                </button>


                <button class="btn-action btn-book">

                    📅 Đặt lịch xem phòng

                </button>


                <div style="display:flex;gap:10px;margin-top:10px;">

                    <button
                            style="flex:1;padding:10px;border-radius:6px;border:1px solid #ccc;background:#6c757d;color:#fff;font-size:12px;font-weight:bold;cursor:pointer;">

                        Đặt cọc

                    </button>


                    <button
                            style="flex:1;padding:10px;border-radius:6px;border:none;background:#ff3345;color:white;font-size:12px;font-weight:bold;cursor:pointer;">

                        Thuê phòng

                    </button>

                </div>

            </div>


            <!-- COST -->

            <div class="cost-table">


                <div class="cost-row">

                    <span>
                        ⚡ Tiền điện
                    </span>

                    <span class="cost-price">
                        ${room.electricity}
                    </span>

                </div>


                <div class="cost-row">

                    <span>
                        💧 Tiền nước
                    </span>

                    <span class="cost-price">
                        ${room.water}
                    </span>

                </div>


                <div class="cost-row">

                    <span>
                        🛡 Phí quản lý / dịch vụ
                    </span>

                    <span class="cost-price">
                        ${room.serviceFee}
                    </span>

                </div>


                <div class="cost-row">

                    <span>
                        📶 Tiền Internet / Wifi
                    </span>

                    <span class="cost-price">
                        ${room.internet}
                    </span>

                </div>


            </div>


        </aside>


    </div>


    <!-- PHÒNG TƯƠNG TỰ -->

    <div style="margin-top:40px;">

        <div class="section-title">

            Phòng tương tự

        </div>


        <div class="similar-grid">


            <c:forEach var="similar"
                       items="${similarRooms}">


                <a class="similar-card"
                   href="${pageContext.request.contextPath}/chi-tiet-phong?id=${similar.id}">


                    <img src="${similar.image}"
                         alt="${similar.title}">


                    <div class="similar-body">

                        <div style="font-size:13px;font-weight:bold;">

                                ${similar.title}

                        </div>


                        <div style="font-size:11px;color:#777;margin:4px 0;">

                            📍 ${similar.location}

                        </div>


                        <div style="font-size:14px;font-weight:bold;color:#ff3345;">

                                ${similar.price}
                            VND/Tháng

                        </div>

                    </div>

                </a>


            </c:forEach>


        </div>

    </div>


</div>


</body>

</html>