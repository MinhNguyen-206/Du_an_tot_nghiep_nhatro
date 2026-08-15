<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập - Room Connect</title>
</head>
<body>

<%-- TODO: khung giao dien dang nhap (form email/password) --%>
<form id="loginForm">
    <input type="email" id="email" placeholder="Email" required>
    <input type="password" id="password" placeholder="Mật khẩu" required>
    <button type="submit">Đăng nhập</button>
</form>
<p id="loginError" style="color:red;"></p>

<script src="${pageContext.request.contextPath}/resources/js/api.js"></script>
<script>
  // Vi du goi API dang nhap that (khop dung field "email"/"password" ma
  // AuthController/LoginRequest.java yeu cau)
  document.getElementById("loginForm").addEventListener("submit", async function (e) {
    e.preventDefault();
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
      const data = await AuthApi.login(email, password);
      localStorage.setItem("token", data.token);
      localStorage.setItem("user", JSON.stringify(data.user));

      // dieu huong theo vai tro, khop quy uoc: 1=nguoi thue, 2=chu tro, 3=admin
      if (data.user.vaiTro === 3) {
        window.location.href = "/admin";
      } else {
        window.location.href = "/";
      }
    } catch (err) {
      document.getElementById("loginError").innerText =
        "Email/số điện thoại hoặc mật khẩu không đúng";
      console.error(err);
    }
  });
</script>

</body>
</html>
