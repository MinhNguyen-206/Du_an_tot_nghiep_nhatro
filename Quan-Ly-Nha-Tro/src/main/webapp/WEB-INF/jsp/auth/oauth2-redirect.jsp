<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đang đăng nhập...</title>
</head>
<body>
    <p>Đang đăng nhập, vui lòng chờ...</p>
    <script>
        (function () {
            const params = new URLSearchParams(window.location.search);
            const token = params.get("token");

            if (!token) {
                window.location.href = "${pageContext.request.contextPath}/login?error=google";
                return;
            }

            // Luu token truoc, KHONG goi /api/nguoi-dung/me (endpoint nay
            // chua ton tai) - de trang chu tu doc token va lay thong tin
            // user sau, giong cach cac trang khac trong app dang lam.
            localStorage.setItem("token", token);
            window.location.href = "${pageContext.request.contextPath}/";
        })();
    </script>
</body>
</html>