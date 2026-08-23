<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>
<div class="page-head"><div><h1>Giao dịch & hóa đơn</h1><p>Giao diện demo — dữ liệu hiện tại là mock, sẵn sàng nối Controller / Service / JPA / SQL.</p></div></div>

<div class="card"><div class="toolbar"><input class="input" placeholder="Mã giao dịch / email"><select class="select"><option>Tất cả</option><option>Thành công</option><option>Thất bại</option><option>Đang xử lý</option></select><button class="btn btn-primary">Tìm</button></div>
<table class="table"><tr><th>Mã GD</th><th>Người mua</th><th>Gói</th><th>Số tiền</th><th>Thời gian</th><th>Trạng thái</th><th>Hóa đơn</th></tr>
<tr><td>TXN00192</td><td>Nguyễn Văn A</td><td>Premium 1 tháng</td><td>99,000đ</td><td>19/08/2026 15:20</td><td><span class="pill ok">Thành công</span></td><td><button class="btn btn-light">Xem</button></td></tr>
<tr><td>TXN00191</td><td>Trần Minh B</td><td>Premium 1 năm</td><td>899,000đ</td><td>19/08/2026 14:02</td><td><span class="pill ok">Thành công</span></td><td><button class="btn btn-light">Xem</button></td></tr>
</table></div>

<%@ include file="includes/footer.jspf" %>
