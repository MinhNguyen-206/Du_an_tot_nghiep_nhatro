<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>
<div class="page-head"><div><h1>Quản lý Blog</h1><p>Giao diện demo — dữ liệu hiện tại là mock, sẵn sàng nối Controller / Service / JPA / SQL.</p></div></div>

<div class="card"><div class="page-head"><div><h1 style="font-size:18px">Bài viết</h1><p>Quản lý nội dung chia sẻ kinh nghiệm thuê trọ.</p></div><button class="btn btn-primary">+ Viết bài mới</button></div>
<table class="table"><tr><th>Tiêu đề</th><th>Tác giả</th><th>Ngày</th><th>Trạng thái</th><th></th></tr>
<tr><td>5 lưu ý khi thuê phòng trọ</td><td>Admin</td><td>18/08/2026</td><td><span class="pill ok">Đã xuất bản</span></td><td><button class="btn btn-light">Sửa</button></td></tr>
<tr><td>Cách kiểm tra phòng trước khi thuê</td><td>Admin</td><td>15/08/2026</td><td><span class="pill wait">Bản nháp</span></td><td><button class="btn btn-light">Chỉnh sửa</button></td></tr>
</table></div>

<%@ include file="includes/footer.jspf" %>
