<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đang đăng nhập...</title>
</head>
<body>
    <p>Đang đăng nhập, vui lòng chờ...</p>
    <script src="${pageContext.request.contextPath}/resources/js/api.js"></script>
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
            // saveAuthToken() luu ca localStorage LAN cookie "jwt" de cac trang
            // JSP dieu huong binh thuong (khong phai fetch) van xac thuc duoc.
            saveAuthToken(token);
            window.location.href = "${pageContext.request.contextPath}/";
        })();
    </script>
</body>
</html>