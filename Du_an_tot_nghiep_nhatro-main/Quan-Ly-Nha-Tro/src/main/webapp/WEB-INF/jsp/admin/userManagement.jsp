<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>
<div class="page-head"><div><h1>Quản lý người dùng</h1><p>Giao diện demo — dữ liệu hiện tại là mock, sẵn sàng nối Controller / Service / JPA / SQL.</p></div></div>

<div class="card"><div class="toolbar"><input class="input" placeholder="Tên / Email / SĐT"><select class="select"><option>Tất cả vai trò</option><option>Người thuê</option><option>Chủ trọ</option><option>Admin</option></select><select class="select"><option>Tất cả trạng thái</option><option>Hoạt động</option><option>Bị khóa</option></select><button class="btn btn-primary">Tìm kiếm</button></div>
<table class="table"><tr><th>User</th><th>Email</th><th>Vai trò</th><th>eKYC</th><th>Vi phạm</th><th>Trạng thái</th><th>Thao tác</th></tr>
<tr><td>Nguyễn Văn A</td><td>vana@gmail.com</td><td>Chủ trọ</td><td><span class="pill ok">Đã xác minh</span></td><td>0</td><td><span class="pill ok">Hoạt động</span></td><td><button class="btn btn-light">Chi tiết</button> <button class="btn btn-danger">Khóa</button></td></tr>
<tr><td>Trần Thị B</td><td>thib@gmail.com</td><td>Người thuê</td><td><span class="pill wait">Chờ duyệt</span></td><td>1</td><td><span class="pill ok">Hoạt động</span></td><td><button class="btn btn-light">Chi tiết</button> <button class="btn btn-danger">Khóa</button></td></tr>
</table></div>

<%@ include file="includes/footer.jspf" %>
