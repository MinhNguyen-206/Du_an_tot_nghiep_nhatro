<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>
<div class="page-head"><div><h1>Audit Log — Nhật ký hoạt động</h1><p>Giao diện demo — dữ liệu hiện tại là mock, sẵn sàng nối Controller / Service / JPA / SQL.</p></div></div>

<div class="card"><div class="toolbar"><input class="input" placeholder="User / IP / hành động"><select class="select"><option>Tất cả hành động</option><option>LOGIN</option><option>APPROVE_POST</option><option>BAN_USER</option><option>CREATE_INVOICE</option><option>PAYMENT</option></select><button class="btn btn-primary">Lọc</button></div>
<table class="table"><tr><th>Thời gian</th><th>Người thực hiện</th><th>Hành động</th><th>Bảng</th><th>Mô tả</th><th>IP</th></tr>
<tr><td>19/08 18:10</td><td>admin@roomconnect.vn</td><td><span class="pill info">APPROVE_POST</span></td><td>posts</td><td>Duyệt bài #1024</td><td>192.168.1.20</td></tr>
<tr><td>19/08 17:52</td><td>admin@roomconnect.vn</td><td><span class="pill bad">BAN_USER</span></td><td>users</td><td>Khóa tài khoản #381</td><td>192.168.1.20</td></tr>
<tr><td>19/08 17:30</td><td>user@demo.vn</td><td><span class="pill ok">LOGIN</span></td><td>users</td><td>Đăng nhập thành công</td><td>113.22.xx.xx</td></tr>
</table></div>

<%@ include file="includes/footer.jspf" %>
