<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>
<div class="page-head"><div><h1>Kiểm duyệt đánh giá & bình luận</h1><p>Giao diện demo — dữ liệu hiện tại là mock, sẵn sàng nối Controller / Service / JPA / SQL.</p></div></div>

<div class="card"><table class="table"><tr><th>User</th><th>Đánh giá</th><th>Điểm</th><th>Lý do báo cáo</th><th>Trạng thái</th><th>Thao tác</th></tr>
<tr><td>Nguyễn A</td><td>Phòng rất ổn, chủ trọ nhiệt tình...</td><td>5/5</td><td>Không có</td><td><span class="pill wait">Bị báo cáo</span></td><td><button class="btn btn-success">Khôi phục</button> <button class="btn btn-danger">Ẩn</button></td></tr>
<tr><td>Trần B</td><td>Spam liên tục, nội dung quảng cáo...</td><td>1/5</td><td>Spam</td><td><span class="pill bad">Vi phạm</span></td><td><button class="btn btn-danger">Ẩn & cảnh cáo</button></td></tr>
</table></div>

<%@ include file="includes/footer.jspf" %>
