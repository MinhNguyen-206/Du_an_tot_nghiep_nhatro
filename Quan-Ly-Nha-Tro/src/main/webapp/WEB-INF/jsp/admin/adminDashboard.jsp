<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>

<div class="page-head">
    <div>
        <h1>Dashboard Admin</h1>
        <p>Quản lý và theo dõi toàn bộ hoạt động của hệ thống Room Connect.</p>
    </div>
</div>

<div class="cards">

    <div class="stat">
        <div class="icon"><i class="bi bi-people"></i></div>
        <h3>${totalUsers}</h3>
        <p>Tổng người dùng</p>
    </div>

    <div class="stat">
        <div class="icon"><i class="bi bi-house"></i></div>
        <h3>${totalPosts}</h3>
        <p>Tin đăng đang hiển thị</p>
    </div>

    <div class="stat">
        <div class="icon"><i class="bi bi-person-vcard"></i></div>
        <h3>${pendingEkyc}</h3>
        <p>Hồ sơ eKYC chờ duyệt</p>
    </div>

    <div class="stat">
        <div class="icon"><i class="bi bi-cash-stack"></i></div>
        <h3>${monthlyRevenue}</h3>
        <p>Doanh thu tháng này</p>
    </div>

</div>

<div class="grid-2">

    <div class="card">

        <h5>Doanh thu 7 ngày gần nhất</h5>

        <div class="chart">

            <div class="bar" style="height:45%">
                <span>8M</span>
            </div>

            <div class="bar" style="height:58%">
                <span>10M</span>
            </div>

            <div class="bar" style="height:40%">
                <span>7M</span>
            </div>

            <div class="bar" style="height:76%">
                <span>13M</span>
            </div>

            <div class="bar" style="height:62%">
                <span>11M</span>
            </div>

            <div class="bar" style="height:88%">
                <span>16M</span>
            </div>

            <div class="bar" style="height:70%">
                <span>12M</span>
            </div>

        </div>

    </div>


    <div class="card">

        <h5>Cần xử lý</h5>

        <div class="notice">
            <strong>12 bài đăng</strong>
            đang chờ Admin duyệt.
        </div>

        <div class="notice">
            <strong>7 hồ sơ eKYC</strong>
            cần kiểm tra.
        </div>

        <div class="notice">
            <strong>5 khiếu nại</strong>
            chưa được xử lý.
        </div>

        <div class="notice">
            <strong>9 đánh giá</strong>
            bị báo cáo.
        </div>

    </div>

</div>

<%@ include file="includes/footer.jspf" %>
