<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>

<div class="page-head">
    <div>
        <h1>Giao dịch &amp; hóa đơn</h1>
        <p>Theo dõi lịch sử giao dịch thanh toán và xem/in hóa đơn liên quan.</p>
    </div>
</div>

<div class="card">
    <div class="toolbar">
        <input class="input" id="gdSearch" placeholder="Mã giao dịch, người thuê, nhà trọ...">
        <select class="select" id="gdStatusFilter">
            <option value="">Tất cả trạng thái</option>
        </select>
        <button class="btn btn-primary" id="gdSearchBtn"><i class="bi bi-search"></i>&nbsp;Tìm kiếm</button>
        <div class="um-toolbar-right">
            <button class="btn btn-light" id="gdRefreshBtn" title="Tải lại"><i class="bi bi-arrow-clockwise"></i></button>
        </div>
    </div>

    <p class="um-status" id="gdStatus">Đang tải dữ liệu...</p>

    <div class="table-wrap">
        <table class="table">
            <thead>
            <tr>
                <th>Mã GD</th>
                <th>Người thuê</th>
                <th>Phòng / Nhà trọ</th>
                <th>Số tiền</th>
                <th>Thời gian</th>
                <th>Trạng thái</th>
                <th>Hóa đơn</th>
            </tr>
            </thead>
            <tbody id="gdTableBody">
            <tr class="table-empty-row"><td colspan="7">Đang tải dữ liệu...</td></tr>
            </tbody>
        </table>
    </div>
</div>

<%-- ── Modal: Xem / in hóa đơn ── --%>
<div class="modal-backdrop" id="gdInvoiceModal">
    <div class="modal-box" style="max-width:620px">
        <div class="modal-head no-print">
            <h3>Hóa đơn</h3>
            <button class="modal-close" data-close="gdInvoiceModal"><i class="bi bi-x-lg"></i></button>
        </div>
        <div class="modal-body" id="gdInvoiceBody">
            <p class="um-status">Đang tải...</p>
        </div>
        <div class="modal-foot no-print">
            <button class="btn btn-light" data-close="gdInvoiceModal">Đóng</button>
            <button class="btn btn-primary" id="gdInvoicePrint"><i class="bi bi-printer"></i>&nbsp;In / Lưu PDF</button>
        </div>
    </div>
</div>

<style>
    .invoice-head { text-align: center; margin-bottom: 18px; }
    .invoice-head h2 { margin: 0 0 4px; font-size: 18px; }
    .invoice-head p { margin: 0; color: #64748b; font-size: 12px; }
    .invoice-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 10px 20px; margin-bottom: 16px; font-size: 13px; }
    .invoice-grid .full { grid-column: 1 / -1; }
    .invoice-grid .label { color: #94a3b8; font-size: 11px; display: block; }
    .invoice-table { width: 100%; border-collapse: collapse; margin-bottom: 16px; font-size: 13px; }
    .invoice-table th, .invoice-table td { border: 1px solid #e2e8f0; padding: 8px 10px; text-align: left; }
    .invoice-table th { background: #f8fafc; }
    .invoice-total { text-align: right; font-size: 15px; font-weight: 700; margin-top: 10px; }
    @media print {
        body * { visibility: hidden; }
        #gdInvoiceModal, #gdInvoiceModal * { visibility: visible; }
        #gdInvoiceModal { position: absolute; inset: 0; background: #fff; }
        .no-print { display: none !important; }
        .modal-box { box-shadow: none; max-width: 100% !important; }
    }
</style>

<script>window.ADMIN_GIAO_DICH_ENDPOINT = '${pageContext.request.contextPath}/api/admin/giao-dich';</script>
<script src="${pageContext.request.contextPath}/resources/js/transactions.js"></script>

<%@ include file="includes/footer.jspf" %>
