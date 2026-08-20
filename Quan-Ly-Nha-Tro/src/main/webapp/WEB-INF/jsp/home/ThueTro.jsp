<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thuê Phòng Trọ - ROOM - CONNECT</title>
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
            font-size: 20px;
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
            gap: 20px;
        }

        .filter-sidebar {
            background: #fff;
            padding: 20px;
            border-radius: 12px;
            border: 1px solid #eaeaea;
            height: fit-content;
        }

        .filter-group {
            margin-bottom: 20px;
        }

        .filter-title {
            font-size: 14px;
            font-weight: 700;
            margin-bottom: 10px;
        }

        .filter-select,
        .filter-input {
            width: 100%;
            padding: 9px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 13px;
            outline: none;
        }

        .range-inputs {
            display: flex;
            gap: 8px;
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
            padding: 5px 10px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 12px;
            background: #fff;
            cursor: pointer;
            user-select: none;
        }

        .pill-btn.active {
            background: #ff3345;
            color: #fff;
            border-color: #ff3345;
        }

        .pill-btn.active::after {
            content: " ✔";
        }

        .filter-actions {
            display: flex;
            flex-direction: column;
            gap: 8px;
            margin-top: 15px;
        }

        .btn-submit-filter {
            width: 100%;
            background: #ff3345;
            color: white;
            border: none;
            padding: 10px;
            border-radius: 8px;
            font-weight: bold;
            cursor: pointer;
            transition: 0.2s;
        }

        .btn-submit-filter:hover {
            background: #e02839;
        }

        .btn-reset-filter {
            width: 100%;
            background: #f1f3f5;
            color: #555;
            border: 1px solid #ddd;
            padding: 8px;
            border-radius: 8px;
            font-weight: 600;
            font-size: 12px;
            text-align: center;
            text-decoration: none;
            display: block;
            box-sizing: border-box;
            transition: 0.2s;
        }

        .btn-reset-filter:hover {
            background: #e2e6ea;
            color: #111;
        }

        .cards-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 16px;
            align-items: start;
        }

        .card {
            background: #fff;
            border-radius: 10px;
            overflow: hidden;
            border: 1px solid #eaeaea;
            transition: all 0.2s ease;
            text-decoration: none;
            color: inherit;
            position: relative;
            display: flex;
            flex-direction: column;
        }

        .card:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 16px rgba(0,0,0,0.08);
        }

        .card-img {
            width: 100%;
            height: 145px;
            object-fit: cover;
        }

        .card-tag {
            position: absolute;
            top: 8px;
            left: 8px;
            background: #e67e22;
            color: #fff;
            font-size: 10px;
            font-weight: bold;
            padding: 2px 6px;
            border-radius: 4px;
        }

        .card-heart {
            position: absolute;
            top: 8px;
            right: 8px;
            background: #fff;
            width: 26px;
            height: 26px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 13px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .card-body {
            padding: 10px 12px 12px 12px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            flex-grow: 1;
        }

        .card-category {
            font-size: 11px;
            color: #888;
            margin-bottom: 2px;
        }

        .card-title {
            font-size: 13px;
            font-weight: 700;
            line-height: 1.35;
            margin-bottom: 6px;
            height: 35px;
            overflow: hidden;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
        }

        .card-location {
            font-size: 11px;
            color: #666;
            margin-bottom: 8px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .card-amenities {
            display: flex;
            flex-wrap: wrap;
            gap: 6px;
            font-size: 10px;
            color: #555;
            margin-bottom: 8px;
        }

        .card-amenity {
            background: #f5f5f5;
            padding: 2px 6px;
            border-radius: 4px;
        }

        .card-price {
            font-size: 14px;
            font-weight: 800;
            color: #ff3345;
            padding-top: 4px;
            border-top: 1px dashed #f0f0f0;
        }

        .card-price span {
            font-size: 10px;
            color: #888;
            font-weight: normal;
        }

        .empty-state {
            grid-column: 1 / -1;
            background: #fff;
            border-radius: 12px;
            border: 1px solid #eaeaea;
            padding: 50px 20px;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        .empty-state-icon {
            font-size: 28px;
            width: 56px;
            height: 56px;
            background: #f8f9fa;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 12px;
            border: 1px solid #eee;
        }

        .empty-state-title {
            font-size: 15px;
            font-weight: 700;
            color: #222;
            margin-bottom: 4px;
        }

        .empty-state-desc {
            font-size: 12px;
            color: #777;
            max-width: 360px;
            line-height: 1.4;
            margin-bottom: 16px;
        }

        .btn-reset-empty {
            display: inline-flex;
            align-items: center;
            gap: 6px;
            padding: 8px 16px;
            background: #fff;
            color: #ff3345;
            border: 1px solid #ff3345;
            border-radius: 8px;
            font-size: 12px;
            font-weight: 600;
            text-decoration: none;
            transition: all 0.2s ease;
        }

        .btn-reset-empty:hover {
            background: #ff3345;
            color: #fff;
            box-shadow: 0 4px 10px rgba(255, 51, 69, 0.15);
        }

        .pagination {
            display: flex;
            justify-content: center;
            gap: 6px;
            margin: 25px 0;
        }

        .page-num {
            width: 30px;
            height: 30px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 6px;
            border: 1px solid #ddd;
            text-decoration: none;
            color: #333;
            font-size: 12px;
        }

        .page-num.active {
            background: #555;
            color: #fff;
        }

        .footer {
            background: #4a423d;
            color: #fff;
            padding: 30px 10%;
            margin-top: 40px;
        }

        .footer-logo {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 8px;
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

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/thue-tro" class="logo">
        ROOM - CONNECT
    </a>

    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/thue-tro" class="active">Thuê trọ</a>
        <a href="${pageContext.request.contextPath}/thue-can-ho">Thuê căn hộ</a>
        <a href="#">Về chúng tôi</a>
        <a href="#">Liên hệ</a>
    </div>

    <!-- TÌM KIẾM KEYWORD -->
    <form action="${pageContext.request.contextPath}/thue-tro" method="GET" class="search-bar-top">
        🔍
        <input type="text" name="keyword" value="<c:out value='${param.keyword}'/>" placeholder="Tìm kiếm phòng trọ...">
    </form>

    <div class="user-actions">
        <span>🔔</span>
        <button class="user-btn" type="button">Khách</button>
    </div>
</nav>

<div class="promo-banner">
    Đăng ký & Xác Minh Chủ Trọ: Miễn Phí 30 Ngày Premium Bạc
    <a href="#">Đăng ký ngay</a> | <a href="#">Điều khoản</a>
</div>

<div class="container">

    <!-- FORM FILTER CHÍNH -->
    <form id="filterForm" action="${pageContext.request.contextPath}/thue-tro" method="GET">
        <input type="hidden" name="keyword" value="<c:out value='${param.keyword}'/>">

        <div class="page-header">
            <h1 class="page-title">
                <c:out value="${pageTitle != null ? pageTitle : 'Thuê Phòng Trọ'}"/>
                <span>(Hiển thị <c:out value="${resultCount != null ? resultCount : 0}"/> kết quả)</span>
            </h1>

            <div>
                Sắp xếp theo:
                <select name="sort" onchange="document.getElementById('filterForm').submit();" style="padding:4px 8px;border-radius:6px;font-size:13px;">
                    <option value="" ${empty param.sort ? 'selected' : ''}>Gợi ý</option>
                    <option value="price_asc" ${param.sort == 'price_asc' ? 'selected' : ''}>Giá thấp đến cao</option>
                    <option value="price_desc" ${param.sort == 'price_desc' ? 'selected' : ''}>Giá cao đến thấp</option>
                </select>
            </div>
        </div>

        <div class="layout-grid">
            <!-- FILTER SIDEBAR -->
            <aside class="filter-sidebar">

                <div class="filter-group">
                    <div class="filter-title">Khu vực</div>
                    <select name="location" class="filter-select">
                        <option value="" ${empty param.location ? 'selected' : ''}>-- Tất cả khu vực --</option>
                        <option value="TP.HCM" ${param.location == 'TP.HCM' ? 'selected' : ''}>TP. Hồ Chí Minh</option>
                        <option value="Hà Nội" ${param.location == 'Hà Nội' ? 'selected' : ''}>Hà Nội</option>
                        <option value="Đà Nẵng" ${param.location == 'Đà Nẵng' ? 'selected' : ''}>Đà Nẵng</option>
                    </select>
                </div>

                <div class="filter-group">
                    <div class="filter-title">Khoảng giá</div>
                    <div class="range-inputs">
                        <input type="number" name="minPrice" value="<c:out value='${param.minPrice}'/>" class="filter-input" placeholder="Thấp nhất">
                        <span>-</span>
                        <input type="number" name="maxPrice" value="<c:out value='${param.maxPrice}'/>" class="filter-input" placeholder="Cao nhất">
                    </div>
                </div>

                <div class="filter-group">
                    <div class="filter-title">Loại phòng</div>
                    <div class="checkbox-group">
                        <c:set var="selectedTypes" value="${fn:join(paramValues.type, ',')}" />

                        <label>
                            <input type="checkbox" name="type" value="Chung cư mini" 
                                   ${fn:contains(selectedTypes, 'Chung cư mini') ? 'checked' : ''}> Chung cư mini
                        </label>
                        <label>
                            <input type="checkbox" name="type" value="Kí túc xá" 
                                   ${fn:contains(selectedTypes, 'Kí túc xá') ? 'checked' : ''}> Kí túc xá
                        </label>
                        <label>
                            <input type="checkbox" name="type" value="Phòng trọ" 
                                   ${fn:contains(selectedTypes, 'Phòng trọ') ? 'checked' : ''}> Phòng trọ
                        </label>
                        <label>
                            <input type="checkbox" name="type" value="Studio" 
                                   ${fn:contains(selectedTypes, 'Studio') ? 'checked' : ''}> Studio
                        </label>
                        <label>
                            <input type="checkbox" name="type" value="Nhà nguyên căn" 
                                   ${fn:contains(selectedTypes, 'Nhà nguyên căn') ? 'checked' : ''}> Nhà nguyên căn
                        </label>
                    </div>
                </div>

                <div class="filter-group">
                    <div class="filter-title">Tiện ích</div>
                    <div class="pill-group">
                        <span class="pill-btn ${param.wifi == 'true' ? 'active' : ''}" onclick="togglePill(this, 'wifi')">Wifi</span>
                        <input type="hidden" name="wifi" id="wifi" value="<c:out value='${param.wifi}'/>">

                        <span class="pill-btn ${param.ac == 'true' ? 'active' : ''}" onclick="togglePill(this, 'ac')">Điều hòa</span>
                        <input type="hidden" name="ac" id="ac" value="<c:out value='${param.ac}'/>">

                        <span class="pill-btn ${param.parking == 'true' ? 'active' : ''}" onclick="togglePill(this, 'parking')">Giữ xe</span>
                        <input type="hidden" name="parking" id="parking" value="<c:out value='${param.parking}'/>">

                        <span class="pill-btn ${param.camera == 'true' ? 'active' : ''}" onclick="togglePill(this, 'camera')">Camera</span>
                        <input type="hidden" name="camera" id="camera" value="<c:out value='${param.camera}'/>">

                        <span class="pill-btn ${param.pet == 'true' ? 'active' : ''}" onclick="togglePill(this, 'pet')">Nuôi thú</span>
                        <input type="hidden" name="pet" id="pet" value="<c:out value='${param.pet}'/>">
                    </div>
                </div>

                <!-- CÁC NÚT THAO TÁC LỌC -->
                <div class="filter-actions">
                    <button type="submit" class="btn-submit-filter">Áp dụng bộ lọc</button>
                    <a href="${pageContext.request.contextPath}/thue-tro" class="btn-reset-filter">🔄 Đặt lại bộ lọc</a>
                </div>
            </aside>

            <!-- DANH SÁCH NHÀ TRỌ -->
            <main class="cards-grid">
                <c:choose>
                    <c:when test="${not empty listNhaTro}">
                        <c:forEach var="item" items="${listNhaTro}">
                            <a href="${pageContext.request.contextPath}/chi-tiet-phong?id=${item.maNhaTro}" class="card">
                                <span class="card-tag">Nổi bật</span>
                                <span class="card-heart">♡</span>

                                <img class="card-img" 
                                     src="${not empty item.hinhAnh ? item.hinhAnh : 'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=500'}" 
                                     alt="<c:out value='${item.tenNhaTro}'/>">

                                <div class="card-body">
                                    <div>
                                        <div class="card-category"><c:out value="${item.loaiPhong != null ? item.loaiPhong : 'Phòng trọ'}"/></div>
                                        <h2 class="card-title"><c:out value="${item.tenNhaTro}"/></h2>
                                        <div class="card-location">📍 <c:out value="${item.diaChi}"/></div>

                                        <!-- TIỆN ÍCH ĐỘNG TỪ DATABASE -->
                                        <div class="card-amenities">
                                            <c:choose>
                                                <c:when test="${not empty item.danhSachTienIch}">
                                                    <c:forEach var="ti" items="${item.danhSachTienIch}">
                                                        <span class="card-amenity">
                                                            <c:choose>
                                                                <c:when test="${ti.tenTienIch == 'Wifi'}">📶 Wifi</c:when>
                                                                <c:when test="${ti.tenTienIch == 'Điều hòa'}">❄ Điều hòa</c:when>
                                                                <c:when test="${ti.tenTienIch == 'Giữ xe'}">🛵 Giữ xe</c:when>
                                                                <c:when test="${ti.tenTienIch == 'Camera'}">📹 Camera</c:when>
                                                                <c:when test="${ti.tenTienIch == 'Nuôi thú'}">🐾 Nuôi thú</c:when>
                                                                <c:otherwise>✨ <c:out value="${ti.tenTienIch}"/></c:otherwise>
                                                            </c:choose>
                                                        </span>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="card-amenity" style="color: #999;">Không có tiện ích</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
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
                        <div class="empty-state">
                            <div class="empty-state-icon">🔍</div>
                            <div class="empty-state-title">Không tìm thấy phòng trọ phù hợp</div>
                            <div class="empty-state-desc">
                                Rất tiếc, không tìm thấy kết quả nào khớp với tiêu chí lựa chọn của bạn. Hãy thử thay đổi thông số hoặc đặt lại bộ lọc.
                            </div>
                            <a href="${pageContext.request.contextPath}/thue-tro" class="btn-reset-empty">
                                🔄 Xóa bộ lọc & Xem tất cả
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </main>
        </div>
    </form>

    <!-- PHÂN TRANG -->
    <c:if test="${totalPages > 1}">
        <c:url var="baseFilterUrl" value="/thue-tro">
            <c:param name="keyword" value="${param.keyword}"/>
            <c:param name="location" value="${param.location}"/>
            <c:param name="minPrice" value="${param.minPrice}"/>
            <c:param name="maxPrice" value="${param.maxPrice}"/>
            <c:param name="sort" value="${param.sort}"/>
            <c:forEach var="t" items="${paramValues.type}">
                <c:param name="type" value="${t}"/>
            </c:forEach>
            <c:param name="wifi" value="${param.wifi}"/>
            <c:param name="ac" value="${param.ac}"/>
            <c:param name="parking" value="${param.parking}"/>
            <c:param name="camera" value="${param.camera}"/>
            <c:param name="pet" value="${param.pet}"/>
        </c:url>

        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <a href="${baseFilterUrl}&page=${currentPage - 1}" class="page-num">&lt;</a>
            </c:if>
            
            <c:forEach begin="1" end="${totalPages}" var="i">
                <a href="${baseFilterUrl}&page=${i}" 
                   class="page-num ${i == currentPage ? 'active' : ''}">${i}</a>
            </c:forEach>
            
            <c:if test="${currentPage < totalPages}">
                <a href="${baseFilterUrl}&page=${currentPage + 1}" class="page-num">&gt;</a>
            </c:if>
        </div>
    </c:if>

</div>

<footer class="footer">
    <div class="footer-logo">Room connect</div>
    <p style="font-size:12px;color:#ccc;">
        Nền tảng tìm kiếm phòng trọ thế hệ mới. Hiện đại và tiện dụng cho tất cả mọi người.
    </p>
</footer>

<script>
    function togglePill(element, inputId) {
        // 1. Toggle class active
        element.classList.toggle('active');
        
        // 2. Cập nhật giá trị cho input hidden
        if (inputId) {
            const hiddenInput = document.getElementById(inputId);
            if (hiddenInput) {
                hiddenInput.value = element.classList.contains('active') ? 'true' : '';
            }
        }
        
        // 3. TỰ ĐỘNG SUBMIT FORM
        document.getElementById('filterForm').submit();
    }
</script>

</body>
</html>