<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- CSS riêng cho bảng quản lý (dùng chung với dashboard) --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin-dashboard.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<%@ include file="includes/header.jspf" %>

<%-- ── Page heading ── --%>
<div class="page-head">
    <div>
        <h1>${pageTitle}</h1>
        <p>${pageDescription}</p>
    </div>
</div>

<%-- ── Bảng quản lý ── --%>
<section class="card management-panel">
    <div class="management-toolbar">
        <div>
            <h2>Danh sách ${pageTitle}</h2>
            <span id="recordCount">Đang tải dữ liệu...</span>
        </div>
        <label class="management-search">
            <i class="bi bi-search"></i>
            <input id="managementSearch" type="search" placeholder="Tìm kiếm...">
        </label>
    </div>

    <div class="management-status" id="managementStatus">Đang tải dữ liệu từ hệ thống...</div>

    <div class="management-table-wrap">
        <table class="management-table">
            <thead id="managementHead"></thead>
            <tbody id="managementBody"></tbody>
        </table>
    </div>
</section>

<script>window.ADMIN_MANAGEMENT_ENDPOINT = '<c:url value="${apiEndpoint}"/>';</script>
<script src="${pageContext.request.contextPath}/resources/js/admin-management.js"></script>

<%@ include file="includes/footer.jspf" %>