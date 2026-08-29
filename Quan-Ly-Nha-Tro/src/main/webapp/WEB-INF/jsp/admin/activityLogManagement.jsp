<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>

<div class="page-head">
    <div>
        <h1>Nhật ký hoạt động</h1>
        <p>Theo dõi toàn bộ thao tác quản trị đã thực hiện trên hệ thống.</p>
    </div>
</div>

<div class="card">
    <div class="toolbar">
        <input class="input" id="alSearch" placeholder="Người thực hiện, hành động, đối tượng, IP...">
        <input class="input" id="alFromDate" type="date" style="max-width:160px">
        <input class="input" id="alToDate" type="date" style="max-width:160px">
        <button class="btn btn-primary" id="alSearchBtn"><i class="bi bi-search"></i>&nbsp;Lọc</button>
        <div class="um-toolbar-right">
            <button class="btn btn-light" id="alRefreshBtn" title="Tải lại"><i class="bi bi-arrow-clockwise"></i></button>
        </div>
    </div>

    <p class="um-status" id="alStatus">Đang tải dữ liệu...</p>

    <div class="table-wrap">
        <table class="table">
            <thead>
            <tr>
                <th>Thời gian</th>
                <th>Người thực hiện</th>
                <th>Hành động</th>
                <th>Đối tượng</th>
                <th>Địa chỉ IP</th>
            </tr>
            </thead>
            <tbody id="alTableBody">
            <tr class="table-empty-row"><td colspan="5">Đang tải dữ liệu...</td></tr>
            </tbody>
        </table>
    </div>
</div>

<script>window.ADMIN_NHAT_KY_ENDPOINT = '${pageContext.request.contextPath}/api/admin/nhat-ky';</script>
<script src="${pageContext.request.contextPath}/resources/js/activity-log.js"></script>

<%@ include file="includes/footer.jspf" %>
