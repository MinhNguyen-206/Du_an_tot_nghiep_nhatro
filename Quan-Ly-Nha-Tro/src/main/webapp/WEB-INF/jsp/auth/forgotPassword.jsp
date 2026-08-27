<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên mật khẩu - Room Connect</title>
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
                <h2>Khôi phục mật khẩu</h2>
                <p>Nhập địa chỉ email đã đăng ký. Chúng tôi sẽ gửi cho bạn một liên kết để tạo lại mật khẩu mới.</p>
            </div>

            <div class="auth-alert error" id="forgotError"></div>
            <div class="auth-alert success" id="forgotSuccess"></div>

            <form id="forgotForm">
                <div class="form-group">
                    <div class="label-group"><label for="forgotEmail">Địa chỉ email *</label></div>
                    <div class="input-wrapper">
                        <i class="fa-regular fa-envelope left-icon"></i>
                        <input type="email" id="forgotEmail" placeholder="example@gmail.com" required>
                    </div>
                </div>

                <button type="submit" class="btn-submit" id="forgotSubmitBtn" style="margin-top: 10px;">
                    Gửi yêu cầu khôi phục
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
        const forgotForm = document.getElementById("forgotForm");
        const forgotError = document.getElementById("forgotError");
        const forgotSuccess = document.getElementById("forgotSuccess");
        const forgotSubmitBtn = document.getElementById("forgotSubmitBtn");

        forgotForm.addEventListener("submit", async function (e) {
            e.preventDefault();
            forgotError.classList.remove("show");
            forgotSuccess.classList.remove("show");

            const email = document.getElementById("forgotEmail").value.trim();

            forgotSubmitBtn.disabled = true;
            forgotSubmitBtn.textContent = "Đang gửi...";

            try {
                await AuthApi.forgotPassword(email);

                forgotSuccess.textContent =
                    "Nếu email tồn tại trong hệ thống, một liên kết khôi phục mật khẩu đã được gửi tới hộp thư của bạn (có hiệu lực trong 15 phút).";
                forgotSuccess.classList.add("show");
                forgotForm.reset();
            } catch (err) {
                // Loi mang / server that su gap su co (khong phai truong hop email
                // khong ton tai - truong hop do BE van tra ve 200 nhu binh thuong).
                forgotError.textContent = "Có lỗi xảy ra, vui lòng thử lại sau.";
                forgotError.classList.add("show");
                console.error(err);
            } finally {
                forgotSubmitBtn.disabled = false;
                forgotSubmitBtn.textContent = "Gửi yêu cầu khôi phục";
            }
        });
    </script>

</body>
</html>
