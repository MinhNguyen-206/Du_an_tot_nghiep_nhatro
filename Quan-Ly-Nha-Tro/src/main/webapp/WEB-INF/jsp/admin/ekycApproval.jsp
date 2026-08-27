<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>
<div class="page-head"><div><h1>Phê duyệt hồ sơ eKYC</h1><p>Giao diện demo — dữ liệu hiện tại là mock, sẵn sàng nối Controller / Service / JPA / SQL.</p></div></div>

<div class="grid-2"><div class="card"><h5>Hồ sơ chờ xác minh</h5><div class="table-wrap"><table class="table"><tr><th>User</th><th>Vai trò</th><th>Ngày gửi</th><th>Trạng thái</th></tr>
<tr><td>Nguyễn Văn A</td><td>Chủ trọ</td><td>19/08</td><td><span class="pill wait">Chờ duyệt</span></td></tr>
<tr><td>Phạm Thị B</td><td>Người thuê</td><td>19/08</td><td><span class="pill wait">Chờ duyệt</span></td></tr>
<tr><td>Trần Minh C</td><td>Chủ trọ</td><td>18/08</td><td><span class="pill wait">Chờ duyệt</span></td></tr></table></div></div>
<div class="card"><h5>Chi tiết hồ sơ</h5><div class="notice"><strong>Nguyễn Văn A</strong> CCCD đã tải đủ 2 mặt + ảnh chân dung.</div><div style="display:flex;gap:8px"><button class="btn btn-success">Xác minh</button><button class="btn btn-danger">Từ chối</button></div></div></div>

<%@ include file="includes/footer.jspf" %>
