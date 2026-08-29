<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>
<<<<<<< HEAD

<div class="page-head">
    <div>
        <h1>Quản lý người dùng</h1>
        <p>Danh sách, tìm kiếm/lọc, phân quyền, khóa &amp; mở khóa, tạo và xóa tài khoản người dùng.</p>
    </div>
</div>

<div class="card">
    <div class="toolbar">
        <input class="input" id="umSearch" placeholder="Tên / Email / SĐT">
        <select class="select" id="umRoleFilter">
            <option value="">Tất cả vai trò</option>
        </select>
        <select class="select" id="umStatusFilter">
            <option value="">Tất cả trạng thái</option>
            <option value="true">Hoạt động</option>
            <option value="false">Bị khóa</option>
        </select>
        <button class="btn btn-primary" id="umSearchBtn"><i class="bi bi-search"></i>&nbsp;Tìm kiếm</button>
        <div class="um-toolbar-right">
            <button class="btn btn-light" id="umRefreshBtn" title="Tải lại"><i class="bi bi-arrow-clockwise"></i></button>
            <button class="btn btn-success" id="umAddBtn"><i class="bi bi-person-plus"></i>&nbsp;Thêm người dùng</button>
        </div>
    </div>

    <p class="um-status" id="umStatus">Đang tải dữ liệu...</p>

    <div class="table-wrap">
        <table class="table">
            <thead>
            <tr>
                <th>User</th>
                <th>Email</th>
                <th>SĐT</th>
                <th>Vai trò</th>
                <th>eKYC</th>
                <th>Vi phạm</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
            </thead>
            <tbody id="umTableBody">
            <tr class="table-empty-row"><td colspan="8">Đang tải dữ liệu...</td></tr>
            </tbody>
        </table>
    </div>
</div>

<%-- ── Modal: Chi tiết / phân quyền / khóa-mở khóa ── --%>
<div class="modal-backdrop" id="umDetailModal">
    <div class="modal-box">
        <div class="modal-head">
            <h3>Chi tiết người dùng</h3>
            <button class="modal-close" data-close="umDetailModal"><i class="bi bi-x-lg"></i></button>
        </div>
        <div class="modal-body" id="umDetailBody">
            <p class="um-status">Đang tải...</p>
        </div>
    </div>
</div>

<%-- ── Modal: Thêm người dùng mới ── --%>
<div class="modal-backdrop" id="umAddModal">
    <div class="modal-box">
        <div class="modal-head">
            <h3>Thêm người dùng mới</h3>
            <button class="modal-close" data-close="umAddModal"><i class="bi bi-x-lg"></i></button>
        </div>
        <div class="modal-body">
            <div class="form-error" id="umAddError"></div>
            <form id="umAddForm" autocomplete="off">
                <div class="form-row">
                    <label>Họ tên *</label>
                    <input class="input" name="hoTen" required>
                </div>
                <div class="form-row">
                    <label>Email *</label>
                    <input class="input" type="email" name="email" required>
                </div>
                <div class="form-row">
                    <label>Số điện thoại</label>
                    <input class="input" name="soDienThoai">
                </div>
                <div class="form-row">
                    <label>Mật khẩu *</label>
                    <input class="input" type="password" name="matKhau" required minlength="6">
                    <small>Tối thiểu 6 ký tự. Admin cấp mật khẩu ban đầu, người dùng có thể đổi lại sau.</small>
                </div>
                <div class="form-row">
                    <label>Vai trò *</label>
                    <select class="select" name="maVaiTro" id="umAddRoleSelect" required></select>
                </div>
                <div class="form-row">
                    <label>Địa chỉ</label>
                    <input class="input" name="diaChi">
                </div>
            </form>
        </div>
        <div class="modal-foot">
            <button class="btn btn-light" data-close="umAddModal">Hủy</button>
            <button class="btn btn-primary" id="umAddSubmit"><i class="bi bi-check-lg"></i>&nbsp;Tạo tài khoản</button>
        </div>
    </div>
</div>

<script>window.ADMIN_NGUOI_DUNG_ENDPOINT = '${pageContext.request.contextPath}/api/admin/nguoi-dung';</script>
<script src="${pageContext.request.contextPath}/resources/js/user-management.js"></script>
=======
<div class="page-head"><div><h1>Quản lý người dùng</h1><p>Giao diện demo — dữ liệu hiện tại là mock, sẵn sàng nối Controller / Service / JPA / SQL.</p></div></div>

<div class="card"><div class="toolbar"><input class="input" placeholder="Tên / Email / SĐT"><select class="select"><option>Tất cả vai trò</option><option>Người thuê</option><option>Chủ trọ</option><option>Admin</option></select><select class="select"><option>Tất cả trạng thái</option><option>Hoạt động</option><option>Bị khóa</option></select><button class="btn btn-primary">Tìm kiếm</button></div>
<table class="table"><tr><th>User</th><th>Email</th><th>Vai trò</th><th>eKYC</th><th>Vi phạm</th><th>Trạng thái</th><th>Thao tác</th></tr>
<tr><td>Nguyễn Văn A</td><td>vana@gmail.com</td><td>Chủ trọ</td><td><span class="pill ok">Đã xác minh</span></td><td>0</td><td><span class="pill ok">Hoạt động</span></td><td><button class="btn btn-light">Chi tiết</button> <button class="btn btn-danger">Khóa</button></td></tr>
<tr><td>Trần Thị B</td><td>thib@gmail.com</td><td>Người thuê</td><td><span class="pill wait">Chờ duyệt</span></td><td>1</td><td><span class="pill ok">Hoạt động</span></td><td><button class="btn btn-light">Chi tiết</button> <button class="btn btn-danger">Khóa</button></td></tr>
</table></div>
>>>>>>> origin/main

<%@ include file="includes/footer.jspf" %>
