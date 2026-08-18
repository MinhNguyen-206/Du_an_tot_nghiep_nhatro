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
        (async function () {
            const params = new URLSearchParams(window.location.search);
            const token = params.get("token");

            if (!token) {
                window.location.href = "${pageContext.request.contextPath}/login?error=google";
                return;
            }

            try {
                const user = await AuthApi.getMe(token); // xem ghi chu ben duoi neu chua co ham nay
                localStorage.setItem("token", token);
                localStorage.setItem("user", JSON.stringify(user));

                const maVaiTro = user && user.vaiTro ? user.vaiTro.maVaiTro : null;
                window.location.href = maVaiTro === 1
                    ? "${pageContext.request.contextPath}/admin"
                    : "${pageContext.request.contextPath}/";
            } catch (err) {
                console.error(err);
                window.location.href = "${pageContext.request.contextPath}/login?error=google";
            }
        })();
    </script>
</body>
</html>