<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt lại mật khẩu - Room Connect</title>
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
                <h2>Đặt mật khẩu mới</h2>
                <p>Nhập mật khẩu mới cho tài khoản của bạn. Liên kết này chỉ có hiệu lực trong <strong>15 phút</strong>.</p>
            </div>

            <div class="auth-alert error" id="resetError"></div>
            <div class="auth-alert success" id="resetSuccess"></div>

            <form id="resetForm">
                <div class="form-group">
                    <div class="label-group"><label for="newPassword">Mật khẩu mới *</label></div>
                    <div class="input-wrapper">
                        <i class="fa-solid fa-lock left-icon"></i>
                        <input type="password" id="newPassword" placeholder="Nhập mật khẩu mới" required minlength="6">
                        <i class="fa-regular fa-eye right-icon toggle-password"></i>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label-group"><label for="confirmNewPassword">Xác nhận mật khẩu mới *</label></div>
                    <div class="input-wrapper">
                        <i class="fa-solid fa-shield left-icon"></i>
                        <input type="password" id="confirmNewPassword" placeholder="Nhập lại mật khẩu mới" required minlength="6">
                        <i class="fa-regular fa-eye right-icon toggle-password"></i>
                    </div>
                </div>

                <button type="submit" class="btn-submit" id="resetSubmitBtn" style="margin-top: 10px;">
                    Xác nhận đặt lại mật khẩu
                </button>
            </form>

            <div class="switch-link" style="margin-top: 30px;">
                <a href="${pageContext.request.contextPath}/login" class="back-to-login">
                    <i class="fa-solid fa-arrow-left"></i> Quay lại Đăng nhập
                </a>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/resources/js/api.js"></script>
    <script>
        document.querySelectorAll(".toggle-password").forEach(function (icon) {
            icon.addEventListener("click", function () {
                const input = this.previousElementSibling;
                const isHidden = input.type === "password";
                input.type = isHidden ? "text" : "password";
                this.classList.toggle("fa-eye", !isHidden);
                this.classList.toggle("fa-eye-slash", isHidden);
            });
        });

        // Token duoc BE gan vao link trong email, vi du:
        // /reset-password?token=eyJhbGciOi...
        const params = new URLSearchParams(window.location.search);
        const token = params.get("token");

        const resetForm = document.getElementById("resetForm");
        const resetError = document.getElementById("resetError");
        const resetSuccess = document.getElementById("resetSuccess");
        const resetSubmitBtn = document.getElementById("resetSubmitBtn");

        if (!token) {
            resetError.textContent = "Thiếu hoặc sai liên kết đặt lại mật khẩu. Vui lòng yêu cầu lại từ trang Quên mật khẩu.";
            resetError.classList.add("show");
            resetSubmitBtn.disabled = true;
        }

        resetForm.addEventListener("submit", async function (e) {
            e.preventDefault();
            resetError.classList.remove("show");
            resetSuccess.classList.remove("show");

            const newPassword = document.getElementById("newPassword").value;
            const confirmNewPassword = document.getElementById("confirmNewPassword").value;

            if (newPassword !== confirmNewPassword) {
                resetError.textContent = "Mật khẩu xác nhận không khớp.";
                resetError.classList.add("show");
                return;
            }

            resetSubmitBtn.disabled = true;
            resetSubmitBtn.textContent = "Đang xử lý...";

            try {
                await AuthApi.resetPassword(token, newPassword);
                resetSuccess.textContent = "Đặt lại mật khẩu thành công! Đang chuyển sang trang đăng nhập...";
                resetSuccess.classList.add("show");
                resetForm.reset();
                setTimeout(function () {
                    window.location.href = "${pageContext.request.contextPath}/login";
                }, 1500);
            } catch (err) {
                resetError.textContent =
                    (err && err.data && err.data.message) ||
                    "Liên kết đã hết hạn hoặc không hợp lệ. Vui lòng yêu cầu lại từ trang Quên mật khẩu.";
                resetError.classList.add("show");
                console.error(err);
                resetSubmitBtn.disabled = false;
                resetSubmitBtn.textContent = "Xác nhận đặt lại mật khẩu";
                return;
            }
        });
    </script>

</body>
</html>
