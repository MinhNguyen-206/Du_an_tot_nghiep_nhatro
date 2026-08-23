<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>
<div class="page-head"><div><h1>Khiếu nại & báo cáo vi phạm</h1><p>Giao diện demo — dữ liệu hiện tại là mock, sẵn sàng nối Controller / Service / JPA / SQL.</p></div></div>

<div class="cards"><div class="stat"><div class="icon"><i class="bi bi-flag"></i></div><h3>5</h3><p>Ticket mới</p></div><div class="stat"><div class="icon"><i class="bi bi-hourglass"></i></div><h3>8</h3><p>Đang xử lý</p></div><div class="stat"><div class="icon"><i class="bi bi-check-circle"></i></div><h3>42</h3><p>Đã giải quyết</p></div><div class="stat"><div class="icon"><i class="bi bi-exclamation-triangle"></i></div><h3>3</h3><p>Mức độ nghiêm trọng cao</p></div></div>
<div class="card"><table class="table"><tr><th>Ticket</th><th>Người báo cáo</th><th>Nội dung</th><th>Mức độ</th><th>Trạng thái</th><th>Thao tác</th></tr>
<tr><td>#TK001</td><td>Nguyễn A</td><td>Tin đăng có dấu hiệu lừa đảo</td><td><span class="pill bad">Cao</span></td><td><span class="pill wait">Mới</span></td><td><button class="btn btn-primary">Xử lý</button></td></tr>
<tr><td>#TK002</td><td>Trần B</td><td>Thông tin phòng không đúng</td><td><span class="pill wait">Trung bình</span></td><td><span class="pill info">Đang xử lý</span></td><td><button class="btn btn-light">Xem</button></td></tr>
</table></div>

<%@ include file="includes/footer.jspf" %>
