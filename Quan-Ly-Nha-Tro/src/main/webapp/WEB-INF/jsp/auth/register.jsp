<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký - Room Connect</title>
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
                <h2>Tạo tài khoản</h2>
                <p>Vui lòng điền thông tin để đăng ký thành viên tại <strong>Room Connect.</strong></p>
                <p style="font-size:13px;color:#6b7280;margin-top:4px;">
                    Tài khoản mới sẽ được tạo với vai trò <strong>Người thuê</strong>.
                    Muốn đăng cho thuê phòng, sau khi đăng ký bạn có thể gửi yêu cầu
                    trở thành Chủ trọ để quản trị viên xét duyệt.
                </p>
            </div>

            <div class="auth-alert error" id="registerError"></div>
            <div class="auth-alert success" id="registerSuccess"></div>

            <form id="registerForm">
                <div class="form-group">
                    <div class="label-group"><label for="hoTen">Họ và tên *</label></div>
                    <div class="input-wrapper">
                        <i class="fa-regular fa-id-badge left-icon"></i>
                        <input type="text" id="hoTen" placeholder="Nhập họ và tên của bạn" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label-group"><label for="regEmail">Email *</label></div>
                    <div class="input-wrapper">
                        <i class="fa-regular fa-envelope left-icon"></i>
                        <input type="email" id="regEmail" placeholder="Nhập email của bạn" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label-group"><label for="soDienThoai">Số điện thoại</label></div>
                    <div class="input-wrapper">
                        <i class="fa-solid fa-phone left-icon"></i>
                        <input type="tel" id="soDienThoai" placeholder="Nhập số điện thoại">
                    </div>
                </div>
                <div class="form-group">
                    <div class="label-group"><label for="regPassword">Mật khẩu *</label></div>
                    <div class="input-wrapper">
                        <i class="fa-solid fa-lock left-icon"></i>
                        <input type="password" id="regPassword" placeholder="Tạo mật khẩu" required minlength="6">
                        <i class="fa-regular fa-eye right-icon toggle-password"></i>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label-group"><label for="regConfirmPassword">Xác nhận mật khẩu *</label></div>
                    <div class="input-wrapper">
                        <i class="fa-solid fa-shield left-icon"></i>
                        <input type="password" id="regConfirmPassword" placeholder="Nhập lại mật khẩu" required minlength="6">
                        <i class="fa-regular fa-eye right-icon toggle-password"></i>
                    </div>
                </div>

                <div class="terms-checkbox">
                    <input type="checkbox" id="terms" required>
                    <label for="terms">Tôi đồng ý với các <a href="#">Điều khoản &amp; Dịch vụ</a></label>
                </div>

                <button type="submit" class="btn-submit" id="registerSubmitBtn">Đăng ký ngay</button>
            </form>

            <div class="switch-link">
                Bạn đã có tài khoản?
                <a href="${pageContext.request.contextPath}/login">Đăng nhập ngay</a>
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

        const registerForm = document.getElementById("registerForm");
        const registerError = document.getElementById("registerError");
        const registerSuccess = document.getElementById("registerSuccess");
        const registerSubmitBtn = document.getElementById("registerSubmitBtn");

        registerForm.addEventListener("submit", async function (e) {
            e.preventDefault();
            registerError.classList.remove("show");
            registerSuccess.classList.remove("show");

            const hoTen = document.getElementById("hoTen").value.trim();
            const email = document.getElementById("regEmail").value.trim();
            const soDienThoai = document.getElementById("soDienThoai").value.trim();
            const matKhau = document.getElementById("regPassword").value;
            const confirmPassword = document.getElementById("regConfirmPassword").value;

            if (matKhau !== confirmPassword) {
                registerError.textContent = "Mật khẩu xác nhận không khớp.";
                registerError.classList.add("show");
                return;
            }

            registerSubmitBtn.disabled = true;
            registerSubmitBtn.textContent = "Đang đăng ký...";

            try {
                // TODO (BE): tam thoi dung POST /api/nguoi-dung (xem ghi chu trong api.js).
                // Khong con gui maVaiTro nua - BE luon ep vai tro moi tao ve "Nguoi thue".
                await AuthApi.register({ hoTen, email, soDienThoai, matKhau });

                registerSuccess.textContent = "Đăng ký thành công! Đang chuyển sang trang đăng nhập...";
                registerSuccess.classList.add("show");
                setTimeout(function () {
                    window.location.href = "${pageContext.request.contextPath}/login";
                }, 1200);
            } catch (err) {
                registerError.textContent =
                    (err && err.data && err.data.message) || "Đăng ký thất bại. Email có thể đã được sử dụng.";
                registerError.classList.add("show");
                console.error(err);
            } finally {
                registerSubmitBtn.disabled = false;
                registerSubmitBtn.textContent = "Đăng ký ngay";
            }
        });
    </script>

</body>
</html>
