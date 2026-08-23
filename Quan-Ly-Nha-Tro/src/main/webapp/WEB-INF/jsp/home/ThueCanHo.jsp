<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Thuê Căn Hộ - ROOM - CONNECT</title>

    <%@ include file="/WEB-INF/jsp/common/head-assets.jspf" %>

    <style>

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background-color: #f8f9fa;
            color: #333;
        }

        .navbar {
            background: #fff;
            padding: 12px 5%;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            position: sticky;
            top: 0;
            z-index: 100;
        }

        .logo {
            font-weight: 800;
            font-size: 20px;
            color: #111;
            text-decoration: none;
        }

        .nav-links {
            display: flex;
            gap: 20px;
            font-size: 14px;
        }

        .nav-links a {
            text-decoration: none;
            color: #555;
            font-weight: 500;
        }

        .nav-links a:hover,
        .nav-links a.active {
            color: #ff3345;
            font-weight: bold;
        }

        .search-bar-top {
            flex: 0 1 400px;
            display: flex;
            background: #f1f3f5;
            border-radius: 20px;
            padding: 6px 15px;
            align-items: center;
        }

        .search-bar-top input {
            border: none;
            background: transparent;
            outline: none;
            width: 100%;
            padding-left: 8px;
            font-size: 13px;
        }

        .user-actions {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .user-btn {
            background: #eef2f5;
            padding: 6px 16px;
            border-radius: 20px;
            font-size: 13px;
            font-weight: 600;
            cursor: pointer;
            border: none;
        }

        .promo-banner {
            background: #f8f9fa;
            text-align: center;
            padding: 8px;
            font-size: 12px;
            color: #666;
            border-bottom: 1px solid #eee;
        }

        .promo-banner a {
            color: #111;
            font-weight: bold;
        }

        .container {
            max-width: 1350px;
            margin: 20px auto;
            padding: 0 20px;
        }

        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .page-title {
            font-size: 22px;
            font-weight: 700;
        }

        .page-title span {
            font-size: 13px;
            color: #777;
            font-weight: normal;
            margin-left: 8px;
        }

        .layout-grid {
            display: grid;
            grid-template-columns: 260px 1fr;
            gap: 25px;
        }

        .filter-sidebar {
            background: #fff;
            padding: 20px;
            border-radius: 12px;
            border: 1px solid #eaeaea;
            height: fit-content;
        }

        .filter-group {
            margin-bottom: 22px;
        }

        .filter-title {
            font-size: 14px;
            font-weight: 700;
            margin-bottom: 12px;
        }

        .filter-select,
        .filter-input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 13px;
            outline: none;
        }

        .range-inputs {
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .range-inputs input {
            width: 50%;
        }

        .checkbox-group {
            display: flex;
            flex-direction: column;
            gap: 8px;
            font-size: 13px;
            color: #555;
        }

        .checkbox-group label {
            display: flex;
            align-items: center;
            gap: 8px;
            cursor: pointer;
        }

        .pill-group {
            display: flex;
            flex-wrap: wrap;
            gap: 6px;
        }

        .pill-btn {
            padding: 6px 12px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 12px;
            background: #fff;
            cursor: pointer;
        }

        .pill-btn.active,
        .pill-btn:hover {
            background: #ff3345;
            color: #fff;
            border-color: #ff3345;
        }

        .cards-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
        }

        .card {
            background: #fff;
            border-radius: 12px;
            overflow: hidden;
            border: 1px solid #eaeaea;
            transition: 0.2s;
            text-decoration: none;
            color: inherit;
            position: relative;
        }

        .card:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 20px rgba(0,0,0,0.08);
        }

        .card-img {
            width: 100%;
            height: 180px;
            object-fit: cover;
        }

        .card-tag {
            position: absolute;
            top: 10px;
            left: 10px;
            background: #f39c12;
            color: #fff;
            font-size: 10px;
            font-weight: bold;
            padding: 3px 8px;
            border-radius: 4px;
        }

        .card-heart {
            position: absolute;
            top: 10px;
            right: 10px;
            background: #fff;
            width: 28px;
            height: 28px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .card-body {
            padding: 14px;
        }

        .card-category {
            font-size: 11px;
            color: #888;
            margin-bottom: 4px;
        }

        .card-title {
            font-size: 14px;
            font-weight: 700;
            line-height: 1.3;
            margin-bottom: 8px;
            height: 36px;
            overflow: hidden;
        }

        .card-location {
            font-size: 12px;
            color: #666;
            margin-bottom: 10px;
        }

        .card-amenities {
            display: flex;
            gap: 8px;
            font-size: 11px;
            color: #555;
            margin-bottom: 12px;
        }

        .card-amenity {
            background: #f5f5f5;
            padding: 3px 8px;
            border-radius: 4px;
        }

        .card-price {
            font-size: 16px;
            font-weight: 800;
            color: #ff3345;
            border-top: 1px solid #f0f0f0;
            padding-top: 10px;
        }

        .card-price span {
            font-size: 11px;
            color: #777;
            font-weight: normal;
        }

        .pagination {
            display: flex;
            justify-content: center;
            gap: 8px;
            margin: 30px 0;
        }

        .page-num {
            width: 32px;
            height: 32px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 6px;
            border: 1px solid #ddd;
            text-decoration: none;
            color: #333;
            font-size: 13px;
        }

        .page-num.active {
            background: #555;
            color: #fff;
        }

        .footer {
            background: #4a423d;
            color: #fff;
            padding: 40px 10%;
            margin-top: 50px;
        }

        .footer-logo {
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 10px;
        }

        @media (max-width: 992px) {

            .cards-grid {
                grid-template-columns: repeat(2, 1fr);
            }

            .layout-grid {
                grid-template-columns: 1fr;
            }

        }

    </style>

</head>

<body>


<%@ include file="/WEB-INF/jsp/common/header.jsp" %>


<div class="promo-banner">

    Đăng ký & Xác Minh Chủ Căn Hộ:
    Miễn Phí 30 Ngày Premium Bạc

    <a href="#">
        Đăng ký ngay
    </a>

    |

    <a href="#">
        Điều khoản
    </a>

</div>


<div class="container">

    <div class="page-header">

        <h1 class="page-title">

            ${pageTitle}

            <span>
                (Hiển thị ${resultCount} kết quả)
            </span>

        </h1>

        <div>

            Sắp xếp theo:

            <select style="padding:5px;border-radius:6px;">

                <option>Gợi ý</option>
                <option>Giá thấp đến cao</option>
                <option>Giá cao đến thấp</option>

            </select>

        </div>

    </div>


    <div class="layout-grid">


        <!-- FILTER -->

        <aside class="filter-sidebar">

            <div class="filter-group">

                <div class="filter-title">
                    Khu vực
                </div>

                <select class="filter-select">

                    <option>TP. Hồ Chí Minh</option>
                    <option>Hà Nội</option>
                    <option>Đà Nẵng</option>

                </select>

            </div>


            <div class="filter-group">

                <div class="filter-title">
                    Khoảng giá
                </div>

                <div class="range-inputs">

                    <input type="text"
                           class="filter-input"
                           placeholder="Thấp nhất">

                    <span>-</span>

                    <input type="text"
                           class="filter-input"
                           placeholder="Cao nhất">

                </div>

            </div>


            <div class="filter-group">

                <div class="filter-title">
                    Loại căn hộ
                </div>

                <div class="checkbox-group">

                    <label>
                        <input type="checkbox">
                        Chung cư mini
                    </label>

                    <label>
                        <input type="checkbox">
                        Căn hộ Studio
                    </label>

                    <label>
                        <input type="checkbox">
                        Căn hộ 1 PN
                    </label>

                    <label>
                        <input type="checkbox">
                        Căn hộ 2 PN
                    </label>

                    <label>
                        <input type="checkbox">
                        Penthouse
                    </label>

                </div>

            </div>


            <div class="filter-group">

                <div class="filter-title">
                    Tiện ích
                </div>

                <div class="pill-group">

                    <span class="pill-btn active">
                        Wifi ✔
                    </span>

                    <span class="pill-btn active">
                        Điều hòa ✔
                    </span>

                    <span class="pill-btn">
                        Hồ bơi
                    </span>

                    <span class="pill-btn">
                        Thang máy
                    </span>

                    <span class="pill-btn">
                        Bãi xe
                    </span>

                </div>

            </div>

        </aside>


        <!-- CARDS -->

        <main class="cards-grid">

            <c:choose>
                <c:when test="${not empty listNhaTro}">
                    <c:forEach var="item" items="${listNhaTro}">

                        <a href="${pageContext.request.contextPath}/chi-tiet-phong?id=${repRoomId[item.maNhaTro]}"
                           class="card">

                            <span class="card-tag">
                                Nổi bật
                            </span>

                            <span class="card-heart">
                                ♡
                            </span>

                            <img class="card-img"
                                 src="${not empty item.hinhAnh ? item.hinhAnh : 'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=500'}"
                                 alt="<c:out value='${item.tenNhaTro}'/>">

                            <div class="card-body">

                                <div class="card-category">
                                    <c:out value="${item.loaiPhong != null ? item.loaiPhong : 'Căn hộ'}"/>
                                </div>

                                <h2 class="card-title">
                                    <c:out value="${item.tenNhaTro}"/>
                                </h2>

                                <div class="card-location">
                                    📍 <c:out value="${item.diaChi}"/>
                                </div>

                                <div class="card-amenities">
                                    <c:choose>
                                        <c:when test="${not empty item.danhSachTienIch}">
                                            <c:forEach var="ti" items="${item.danhSachTienIch}" varStatus="tiStatus" end="2">
                                                <span class="card-amenity">
                                                    <c:choose>
                                                        <c:when test="${ti.tenTienIch == 'Wifi'}">📶 Wifi</c:when>
                                                        <c:when test="${ti.tenTienIch == 'Điều hòa'}">❄ Điều hòa</c:when>
                                                        <c:when test="${ti.tenTienIch == 'Máy giặt'}">🧺 Máy giặt</c:when>
                                                        <c:when test="${ti.tenTienIch == 'Camera an ninh'}">📹 Camera</c:when>
                                                        <c:otherwise>✨ <c:out value="${ti.tenTienIch}"/></c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="card-amenity" style="color:#999;">Không có tiện ích</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <div class="card-price">
                                    <c:choose>
                                        <c:when test="${item.giaPhong != null && item.giaPhong > 0}">
                                            <fmt:formatNumber value="${item.giaPhong}" type="number" groupingUsed="true"/> VNĐ<span>/Tháng</span>
                                        </c:when>
                                        <c:otherwise>
                                            Liên hệ chủ trọ
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                            </div>

                        </a>

                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="empty-note" style="grid-column:1/-1;text-align:center;color:#888;padding:40px 0;">
                        Chưa có căn hộ nào phù hợp.
                    </div>
                </c:otherwise>
            </c:choose>

        </main>

    </div>


    <div class="pagination">

        <a href="#" class="page-num">
            &lt;
        </a>

        <a href="#" class="page-num active">
            1
        </a>

        <a href="#" class="page-num">
            2
        </a>

        <a href="#" class="page-num">
            3
        </a>

        <a href="#" class="page-num">
            ...
        </a>

        <a href="#" class="page-num">
            8
        </a>

        <a href="#" class="page-num">
            &gt;
        </a>

    </div>

</div>


<footer class="footer">

    <div class="footer-logo">
        Room connect
    </div>

    <p style="font-size:12px;color:#ccc;">
        Nền tảng tìm kiếm phòng trọ & căn hộ thế hệ mới.
    </p>

</footer>


</body>

</html>