<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách phòng - Room Connect</title>
</head>
<body>

<%-- TODO: khung giao dien danh sach phong --%>
<div id="roomList">Đang tải danh sách phòng...</div>

<script src="${pageContext.request.contextPath}/resources/js/api.js"></script>
<script>
  // Vi du goi API GET /api/phong-tro that, khong can dang nhap
  // (endpoint nay dang permitAll trong SecurityConfig.java)
  (async function () {
    try {
      const rooms = await PhongTroApi.getAll();
      const container = document.getElementById("roomList");
      container.innerHTML = rooms
        .map((r) => `<div>${r.tenPhong || r.tieuDe || "Phòng #" + r.maPhongTro}</div>`)
        .join("");
    } catch (err) {
      document.getElementById("roomList").innerText = "Không tải được danh sách phòng.";
      console.error(err);
    }
  })();
</script>

</body>
</html>
