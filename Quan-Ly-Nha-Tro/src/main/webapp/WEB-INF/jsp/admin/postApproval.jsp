<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>

<div class="page-head">
    <div>
        <h1>Phê duyệt bài đăng trọ</h1>
        <p>Xem chi tiết, duyệt hoặc từ chối (kèm lý do) các bài đăng cho thuê từ Chủ trọ.</p>
    </div>
</div>

<div class="card">
    <div class="toolbar">
        <input class="input" id="paSearch" placeholder="Tiêu đề, chủ trọ, nhà trọ...">
        <select class="select" id="paStatusFilter">
            <option value="">Tất cả trạng thái</option>
            <option value="CHO_DUYET" selected>Chờ duyệt</option>
            <option value="DA_DUYET">Đã duyệt</option>
            <option value="TU_CHOI">Từ chối</option>
        </select>
        <button class="btn btn-primary" id="paSearchBtn"><i class="bi bi-search"></i>&nbsp;Tìm kiếm</button>
        <div class="um-toolbar-right">
            <button class="btn btn-light" id="paRefreshBtn" title="Tải lại"><i class="bi bi-arrow-clockwise"></i></button>
        </div>
    </div>

    <p class="um-status" id="paStatus">Đang tải dữ liệu...</p>

    <div class="table-wrap">
        <table class="table">
            <thead>
            <tr>
                <th>Bài đăng</th>
                <th>Chủ trọ</th>
                <th>Giá phòng</th>
                <th>Ngày gửi</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
            </thead>
            <tbody id="paTableBody">
            <tr class="table-empty-row"><td colspan="6">Đang tải dữ liệu...</td></tr>
            </tbody>
        </table>
    </div>
</div>

<%-- ── Modal: Chi tiết / Duyệt / Từ chối ── --%>
<div class="modal-backdrop" id="paDetailModal">
    <div class="modal-box" style="max-width:680px">
        <div class="modal-head">
            <h3>Chi tiết bài đăng</h3>
            <button class="modal-close" data-close="paDetailModal"><i class="bi bi-x-lg"></i></button>
        </div>
        <div class="modal-body" id="paDetailBody">
            <p class="um-status">Đang tải...</p>
        </div>
    </div>
</div>

<%-- ── Modal: Nhập lý do từ chối ── --%>
<div class="modal-backdrop" id="paRejectModal">
    <div class="modal-box">
        <div class="modal-head">
            <h3>Từ chối bài đăng</h3>
            <button class="modal-close" data-close="paRejectModal"><i class="bi bi-x-lg"></i></button>
        </div>
        <div class="modal-body">
            <div class="form-error" id="paRejectError"></div>
            <div class="form-row">
                <label>Lý do từ chối *</label>
                <textarea class="textarea" id="paRejectReason" rows="4" style="width:100%;box-sizing:border-box"
                          placeholder="VD: Ảnh không rõ ràng, giá không khớp mô tả, thiếu thông tin địa chỉ..."></textarea>
                <small>Lý do này sẽ hiển thị cho Chủ trọ để họ chỉnh sửa lại bài đăng.</small>
            </div>
        </div>
        <div class="modal-foot">
            <button class="btn btn-light" data-close="paRejectModal">Hủy</button>
            <button class="btn btn-danger" id="paRejectSubmit"><i class="bi bi-x-lg"></i>&nbsp;Xác nhận từ chối</button>
        </div>
    </div>
</div>

<script>window.ADMIN_DANG_TIN_ENDPOINT = '${pageContext.request.contextPath}/api/admin/dang-tin';</script>
<script src="${pageContext.request.contextPath}/resources/js/post-approval.js"></script>

<%@ include file="includes/footer.jspf" %>