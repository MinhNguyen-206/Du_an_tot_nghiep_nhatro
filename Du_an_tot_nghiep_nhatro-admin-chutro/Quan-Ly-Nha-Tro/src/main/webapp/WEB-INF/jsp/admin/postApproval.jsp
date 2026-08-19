<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>
<div class="page-head"><div><h1>Phê duyệt bài đăng trọ</h1><p>Giao diện demo — dữ liệu hiện tại là mock, sẵn sàng nối Controller / Service / JPA / SQL.</p></div></div>

<div class="card"><div class="toolbar"><input class="input" placeholder="Tìm tiêu đề, chủ trọ..."><select class="select"><option>Tất cả trạng thái</option><option>Chờ duyệt</option><option>Đã duyệt</option><option>Từ chối</option></select><button class="btn btn-primary">Tìm kiếm</button></div>
<div class="table-wrap"><table class="table"><tr><th>ID</th><th>Bài đăng</th><th>Chủ trọ</th><th>Giá</th><th>Ngày gửi</th><th>Trạng thái</th><th>Thao tác</th></tr>
<tr><td>#POST1024</td><td>Phòng full nội thất gần FPT</td><td>Nguyễn Văn A</td><td>3.2M</td><td>19/08/2026</td><td><span class="pill wait">Chờ duyệt</span></td><td><button class="btn btn-success">Duyệt</button> <button class="btn btn-danger">Từ chối</button></td></tr>
<tr><td>#POST1025</td><td>Studio ban công Quận 9</td><td>Trần Minh B</td><td>4.5M</td><td>19/08/2026</td><td><span class="pill wait">Chờ duyệt</span></td><td><button class="btn btn-success">Duyệt</button> <button class="btn btn-danger">Từ chối</button></td></tr>
<tr><td>#POST1026</td><td>KTX giá sinh viên</td><td>Lê C</td><td>1.5M</td><td>18/08/2026</td><td><span class="pill ok">Đã duyệt</span></td><td><button class="btn btn-light">Xem</button></td></tr>
</table></div></div>

<%@ include file="includes/footer.jspf" %>
