<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>
<div class="page-head"><div><h1>Thông báo hệ thống</h1><p>Giao diện demo — dữ liệu hiện tại là mock, sẵn sàng nối Controller / Service / JPA / SQL.</p></div></div>

<div class="grid-2"><div class="card"><h5>Gửi thông báo mới</h5><div class="form-grid"><div class="form-group full"><label>Tiêu đề</label><input class="input" style="width:100%" placeholder="Nhập tiêu đề"></div><div class="form-group full"><label>Nội dung</label><textarea class="textarea" placeholder="Nội dung thông báo..."></textarea></div><div class="form-group"><label>Đối tượng</label><select class="select" style="width:100%"><option>Tất cả người dùng</option><option>Chủ trọ</option><option>Người thuê</option></select></div><div class="form-group"><label>Loại</label><select class="select" style="width:100%"><option>Thông tin</option><option>Cảnh báo</option><option>Khẩn cấp</option></select></div><div class="full"><button class="btn btn-primary">Gửi thông báo</button></div></div></div>
<div class="card"><h5>Lịch sử gửi</h5><div class="notice"><strong>Bảo trì hệ thống</strong> Đã gửi 18/08 — 1,284 người.</div><div class="notice"><strong>Khuyến mãi Premium</strong> Đã gửi 15/08 — 624 chủ trọ.</div></div></div>

<%@ include file="includes/footer.jspf" %>
