<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập - Room Connect</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/auth.css">
</head>
<body class="auth-body">

    <div class="auth-container">

        <div class="auth-brand">
            <div class="brand-top">
                <i class="fa-solid fa-house-chimney"></i>
                <span>Room Connect</span>
            </div>
            <div class="brand-bottom">
                <div class="line"></div>
                <h1>ROOM CONNECT</h1>
                <p>Kết nối không gian, sẻ chia cuộc sống.</p>
            </div>
        </div>

        <div class="auth-form-panel">
            <div class="auth-header">
                <h2>Chào mừng trở lại</h2>
                <p>Vui lòng đăng nhập vào tài khoản của bạn tại <strong>Room Connect.</strong></p>
            </div>

            <div class="auth-alert error" id="loginError"></div>

            <form id="loginForm">
                <div class="form-group">
                    <div class="label-group"><label for="email">Email *</label></div>
                    <div class="input-wrapper">
                        <i class="fa-regular fa-user left-icon"></i>
                        <input type="email" id="email" placeholder="Nhập email của bạn" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label-group">
                        <label for="password">Mật khẩu *</label>
                        <a href="${pageContext.request.contextPath}/forgot-password" class="forgot-link">Quên mật khẩu ?</a>
                    </div>
                    <div class="input-wrapper">
                        <i class="fa-solid fa-lock left-icon"></i>
                        <input type="password" id="password" placeholder="Nhập mật khẩu" required>
                        <i class="fa-regular fa-eye right-icon toggle-password"></i>
                    </div>
                </div>
                <button type="submit" class="btn-submit" id="loginSubmitBtn">Đăng nhập</button>
            </form>

            <div class="switch-link">
                Bạn chưa có tài khoản?
                <a href="${pageContext.request.contextPath}/register">Đăng ký ngay</a>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/resources/js/api.js"></script>
    <script>
        // Bat/tat hien mat khau
        document.querySelectorAll(".toggle-password").forEach(function (icon) {
            icon.addEventListener("click", function () {
                const input = this.previousElementSibling;
                const isHidden = input.type === "password";
                input.type = isHidden ? "text" : "password";
                this.classList.toggle("fa-eye", !isHidden);
                this.classList.toggle("fa-eye-slash", isHidden);
            });
        });

        // Goi API dang nhap that (khop dung field "email"/"password" ma
        // AuthController/LoginRequest.java yeu cau)
        const loginForm = document.getElementById("loginForm");
        const loginError = document.getElementById("loginError");
        const loginSubmitBtn = document.getElementById("loginSubmitBtn");

        loginForm.addEventListener("submit", async function (e) {
            e.preventDefault();
            loginError.classList.remove("show");
            loginSubmitBtn.disabled = true;
            loginSubmitBtn.textContent = "Đang đăng nhập...";

            const email = document.getElementById("email").value.trim();
            const password = document.getElementById("password").value;

            try {
                const data = await AuthApi.login(email, password);
                localStorage.setItem("token", data.token);
                localStorage.setItem("user", JSON.stringify(data.user));

                // dieu huong theo vai tro (maVaiTro: 1 = Admin, 2 = Chu tro, 3 = Nguoi thue)
                const maVaiTro = data.user && data.user.vaiTro ? data.user.vaiTro.maVaiTro : null;
                if (maVaiTro === 1) {
                    window.location.href = "${pageContext.request.contextPath}/admin";
                } else {
                    window.location.href = "${pageContext.request.contextPath}/";
                }
            } catch (err) {
                loginError.textContent = "Email hoặc mật khẩu không đúng. Vui lòng thử lại.";
                loginError.classList.add("show");
                console.error(err);
            } finally {
                loginSubmitBtn.disabled = false;
                loginSubmitBtn.textContent = "Đăng nhập";
            }
        });
    </script>

</body>
</html>
