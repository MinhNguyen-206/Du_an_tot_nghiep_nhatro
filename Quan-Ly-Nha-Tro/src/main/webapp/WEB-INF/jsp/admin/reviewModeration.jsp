<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>
<<<<<<< HEAD

<div class="page-head">
    <div>
        <h1>Kiểm duyệt đánh giá &amp; bình luận</h1>
        <p>Xem, ẩn, khôi phục hoặc ẩn &amp; cảnh cáo các đánh giá vi phạm từ người dùng.</p>
    </div>
</div>

<div class="card">
    <div class="toolbar">
        <input class="input" id="rmSearch" placeholder="Nội dung, người viết, nhà trọ...">
        <select class="select" id="rmStatusFilter">
            <option value="">Tất cả trạng thái</option>
            <option value="BINH_THUONG">Bình thường</option>
            <option value="BI_BAO_CAO">Bị báo cáo</option>
            <option value="DA_AN">Đã ẩn</option>
        </select>
        <button class="btn btn-primary" id="rmSearchBtn"><i class="bi bi-search"></i>&nbsp;Tìm kiếm</button>
        <div class="um-toolbar-right">
            <button class="btn btn-light" id="rmRefreshBtn" title="Tải lại"><i class="bi bi-arrow-clockwise"></i></button>
        </div>
    </div>

    <p class="um-status" id="rmStatus">Đang tải dữ liệu...</p>

    <div class="table-wrap">
        <table class="table">
            <thead>
            <tr>
                <th>User</th>
                <th>Đánh giá</th>
                <th>Điểm</th>
                <th>Lý do báo cáo</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
            </thead>
            <tbody id="rmTableBody">
            <tr class="table-empty-row"><td colspan="6">Đang tải dữ liệu...</td></tr>
            </tbody>
        </table>
    </div>
</div>

<%-- ── Modal: Ẩn & cảnh cáo (nhập lý do) ── --%>
<div class="modal-backdrop" id="rmWarnModal">
    <div class="modal-box">
        <div class="modal-head">
            <h3>Ẩn &amp; cảnh cáo</h3>
            <button class="modal-close" data-close="rmWarnModal"><i class="bi bi-x-lg"></i></button>
        </div>
        <div class="modal-body">
            <div class="form-error" id="rmWarnError"></div>
            <div class="form-row">
                <label>Lý do vi phạm *</label>
                <textarea class="textarea" id="rmWarnReason" rows="4" style="width:100%;box-sizing:border-box"
                          placeholder="VD: Spam, ngôn từ phản cảm, nội dung sai sự thật..."></textarea>
                <small>Đánh giá sẽ bị ẩn khỏi trang công khai và người viết sẽ bị ghi nhận 1 lượt vi phạm.</small>
            </div>
        </div>
        <div class="modal-foot">
            <button class="btn btn-light" data-close="rmWarnModal">Hủy</button>
            <button class="btn btn-danger" id="rmWarnSubmit"><i class="bi bi-exclamation-triangle"></i>&nbsp;Xác nhận ẩn &amp; cảnh cáo</button>
        </div>
    </div>
</div>

<script>window.ADMIN_DANH_GIA_ENDPOINT = '${pageContext.request.contextPath}/api/admin/danh-gia';</script>
<script src="${pageContext.request.contextPath}/resources/js/review-moderation.js"></script>
=======
<div class="page-head"><div><h1>Kiểm duyệt đánh giá & bình luận</h1><p>Giao diện demo — dữ liệu hiện tại là mock, sẵn sàng nối Controller / Service / JPA / SQL.</p></div></div>

<div class="card"><table class="table"><tr><th>User</th><th>Đánh giá</th><th>Điểm</th><th>Lý do báo cáo</th><th>Trạng thái</th><th>Thao tác</th></tr>
<tr><td>Nguyễn A</td><td>Phòng rất ổn, chủ trọ nhiệt tình...</td><td>5/5</td><td>Không có</td><td><span class="pill wait">Bị báo cáo</span></td><td><button class="btn btn-success">Khôi phục</button> <button class="btn btn-danger">Ẩn</button></td></tr>
<tr><td>Trần B</td><td>Spam liên tục, nội dung quảng cáo...</td><td>1/5</td><td>Spam</td><td><span class="pill bad">Vi phạm</span></td><td><button class="btn btn-danger">Ẩn & cảnh cáo</button></td></tr>
</table></div>
>>>>>>> origin/main

<%@ include file="includes/footer.jspf" %>
