<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>
<div class="page-head"><div><h1>Quản lý danh mục & tiện ích</h1><p>Giao diện demo — dữ liệu hiện tại là mock, sẵn sàng nối Controller / Service / JPA / SQL.</p></div></div>

<div class="grid-2"><div class="card"><h5>Tiện ích phòng</h5><div class="toolbar"><input class="input" placeholder="Tên tiện ích"><button class="btn btn-primary">+ Thêm</button></div><table class="table"><tr><th>Tên</th><th>Trạng thái</th><th></th></tr><tr><td>Wifi</td><td><span class="pill ok">Hiển thị</span></td><td><button class="btn btn-light">Sửa</button></td></tr><tr><td>Điều hòa</td><td><span class="pill ok">Hiển thị</span></td><td><button class="btn btn-light">Sửa</button></td></tr><tr><td>Thang máy</td><td><span class="pill ok">Hiển thị</span></td><td><button class="btn btn-light">Sửa</button></td></tr></table></div>
<div class="card"><h5>Địa bàn</h5><div class="notice"><strong>TP. Hồ Chí Minh</strong> 22 quận/huyện/phường đang hoạt động.</div><button class="btn btn-primary">+ Thêm khu vực</button></div></div>

<%@ include file="includes/footer.jspf" %>
