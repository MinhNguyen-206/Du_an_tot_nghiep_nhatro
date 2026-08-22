<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="includes/header.jspf" %>

<div class="owner-page-head">
    <div>
        <div class="eyebrow">CHỦ TRỌ CENTER</div>

        <h1>Xin chào, ${userName}</h1>

        <p>
            Dữ liệu được lấy trực tiếp từ SQL Server của tài khoản
            <strong>${userEmail}</strong>.
        </p>
    </div>

    <a class="owner-btn primary"
       href="${pageContext.request.contextPath}/chu-tro/posts">
        <i class="bi bi-plus-lg"></i>
        Đăng tin mới
    </a>
</div>


<!-- =========================
     THỐNG KÊ TỔNG QUAN
     ========================= -->
<div class="owner-stats">

    <!-- Tổng số phòng -->
    <div class="owner-stat blue">
        <div class="stat-icon">
            <i class="bi bi-door-open"></i>
        </div>

        <span>Tổng số phòng</span>

        <strong>${totalRooms}</strong>

        <small>
            Trong tất cả nhà trọ
        </small>
    </div>


    <!-- Phòng đang có người thuê -->
    <div class="owner-stat green">
        <div class="stat-icon">
            <i class="bi bi-person-check"></i>
        </div>

        <span>Đang có người thuê</span>

        <strong>${occupiedRooms}</strong>

        <small>
            ${occupancyPercent}% công suất
        </small>
    </div>


    <!-- Phòng còn trống -->
    <div class="owner-stat orange">
        <div class="stat-icon">
            <i class="bi bi-door-closed"></i>
        </div>

        <span>Phòng còn trống</span>

        <strong>${availableRooms}</strong>

        <small>
            Có thể cho thuê ngay
        </small>
    </div>


    <!-- Doanh thu -->
    <div class="owner-stat purple">
        <div class="stat-icon">
            <i class="bi bi-wallet2"></i>
        </div>

        <span>Doanh thu tháng này</span>

        <strong>${monthlyRevenue}</strong>

        <small>
            ${revenueGrowth}
        </small>
    </div>

</div>


<!-- =========================
     KHU VỰC BIỂU ĐỒ + CẦN XỬ LÝ
     ========================= -->
<div class="owner-grid-2">

    <!-- =========================
         DOANH THU 7 NGÀY
         ========================= -->
    <section class="owner-card">

        <div class="card-head">

            <div>
                <h3>Doanh thu 7 ngày gần nhất</h3>

                <p>
                    Lấy từ các khoản thanh toán đã thanh toán trong SQL
                </p>
            </div>

            <button class="mini-select" type="button">
                7 ngày
                <i class="bi bi-chevron-down"></i>
            </button>

        </div>


        <div class="owner-chart">

            <c:forEach var="point" items="${revenueChart}">

                <div class="chart-col">

                    <span>
                        ${point.amount}
                    </span>

                    <i style="height:${point.height}%"></i>

                    <small>
                        ${point.label}
                    </small>

                </div>

            </c:forEach>

        </div>

    </section>


    <!-- =========================
         CẦN XỬ LÝ
         ========================= -->
    <section class="owner-card">

        <div class="card-head">

            <div>
                <h3>Cần xử lý</h3>

                <p>
                    Các số liệu đang chờ xử lý từ database
                </p>
            </div>

            <i class="bi bi-lightning-charge-fill accent-icon"></i>

        </div>


        <!-- Yêu cầu thuê -->
        <a class="task"
           href="${pageContext.request.contextPath}/chu-tro/rental-requests">

            <b>
                ${pendingRequests} yêu cầu thuê
            </b>

            <span>
                Người tìm trọ đang chờ phản hồi
            </span>

            <i class="bi bi-chevron-right"></i>

        </a>


        <!-- Hóa đơn chưa thanh toán -->
        <a class="task"
           href="${pageContext.request.contextPath}/chu-tro/invoices">

            <b>
                ${unpaidInvoices} hóa đơn chưa thanh toán
            </b>

            <span>
                Các hóa đơn chưa ở trạng thái đã thanh toán
            </span>

            <i class="bi bi-chevron-right"></i>

        </a>


        <!-- Chưa nhập điện nước -->
        <a class="task"
           href="${pageContext.request.contextPath}/chu-tro/meters">

            <b>
                ${missingMeters} phòng chưa nhập điện nước
            </b>

            <span>
                Chưa có chỉ số của tháng hiện tại
            </span>

            <i class="bi bi-chevron-right"></i>

        </a>


        <!-- Đánh giá mới -->
        <a class="task"
           href="${pageContext.request.contextPath}/chu-tro/reviews">

            <b>
                ${newReviews} đánh giá mới
            </b>

            <span>
                Đánh giá trong 7 ngày gần nhất
            </span>

            <i class="bi bi-chevron-right"></i>

        </a>

    </section>

</div>


<!-- =========================
     TÌNH TRẠNG PHÒNG
     ========================= -->
<section class="owner-card">

    <div class="card-head">

        <div>
            <h3>Phòng & tình trạng</h3>

            <p>
                Tổng quan thật từ hợp đồng điện tử
            </p>
        </div>

        <a class="owner-btn light"
           href="${pageContext.request.contextPath}/chu-tro/rooms">
            Xem tất cả
        </a>

    </div>


    <div class="room-overview">

        <!-- Đang thuê -->
        <div>
            <span class="legend-dot occupied"></span>

            Đang thuê

            <strong>
                ${occupiedRooms}
            </strong>
        </div>


        <!-- Còn trống -->
        <div>
            <span class="legend-dot available"></span>

            Còn trống

            <strong>
                ${availableRooms}
            </strong>
        </div>


        <!-- Đang chờ ký -->
        <div>
            <span class="legend-dot pending"></span>

            Đang chờ ký

            <strong>
                ${pendingRooms}
            </strong>
        </div>


        <!-- Tỷ lệ lấp đầy -->
        <div class="occupancy">

            <div class="progress-label">

                <span>
                    Tỷ lệ lấp đầy
                </span>

                <b>
                    ${occupancyPercent}%
                </b>

            </div>


            <div class="progress-owner">

                <span style="width:${occupancyPercent}%"></span>

            </div>

        </div>

    </div>

</section>


<%@ include file="includes/footer.jspf" %>