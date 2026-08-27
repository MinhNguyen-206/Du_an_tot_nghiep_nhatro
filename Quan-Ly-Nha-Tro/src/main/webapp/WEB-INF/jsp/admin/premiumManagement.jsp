<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>
<div class="page-head"><div><h1>Quản lý gói Premium</h1><p>Giao diện demo — dữ liệu hiện tại là mock, sẵn sàng nối Controller / Service / JPA / SQL.</p></div></div>

<div class="cards"><div class="stat"><div class="icon"><i class="bi bi-gem"></i></div><h3>248</h3><p>Tài khoản Premium</p></div><div class="stat"><div class="icon"><i class="bi bi-calendar-check"></i></div><h3>39</h3><p>Sắp hết hạn</p></div><div class="stat"><div class="icon"><i class="bi bi-cash"></i></div><h3>68%</h3><p>Tỷ trọng doanh thu</p></div><div class="stat"><div class="icon"><i class="bi bi-arrow-up"></i></div><h3>14.2%</h3><p>Tăng trưởng</p></div></div>
<div class="card"><div class="page-head"><div><h1 style="font-size:18px">Các gói dịch vụ</h1></div><button class="btn btn-primary">+ Thêm gói</button></div><table class="table"><tr><th>Gói</th><th>Thời hạn</th><th>Giá</th><th>Quyền lợi</th><th>Trạng thái</th><th></th></tr>
<tr><td>Premium Tháng</td><td>30 ngày</td><td>99,000đ</td><td>20 tin + ưu tiên hiển thị</td><td><span class="pill ok">Đang bán</span></td><td><button class="btn btn-light">Sửa</button></td></tr>
<tr><td>Premium Năm</td><td>365 ngày</td><td>899,000đ</td><td>Không giới hạn tin + ưu tiên</td><td><span class="pill ok">Đang bán</span></td><td><button class="btn btn-light">Sửa</button></td></tr>
</table></div>

<%@ include file="includes/footer.jspf" %>
