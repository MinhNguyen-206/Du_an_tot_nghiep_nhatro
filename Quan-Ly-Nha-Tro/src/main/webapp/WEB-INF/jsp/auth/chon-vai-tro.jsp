<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chọn vai trò - Room Connect</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/auth.css">
</head>
<body class="auth-body">
    <div class="auth-container">
        <div class="auth-form-panel" style="margin: auto;">
            <div class="auth-header">
                <h2>Bạn muốn sử dụng Room Connect với vai trò nào?</h2>
                <p id="welcomeText">Chọn vai trò phù hợp để tiếp tục.</p>
            </div>

            <div class="auth-alert error" id="roleError"></div>

            <div style="display:flex; gap:16px; margin: 24px 0;">
                <button type="button" class="btn-submit role-option" data-role="2">
                    <i class="fa-solid fa-house-chimney-user"></i> Tôi là Chủ trọ
                </button>
                <button type="button" class="btn-submit role-option" data-role="3">
                    <i class="fa-solid fa-user"></i> Tôi là Người thuê
                </button>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/resources/js/api.js"></script>
    <script>
        const roleError = document.getElementById("roleError");
        const welcomeText = document.getElementById("welcomeText");

        // Hien thi email/ten tam thoi de user biet minh dang dang ky bang tai khoan nao
        fetch("${pageContext.request.contextPath}/api/auth/google/pending-info")
            .then(r => r.json())
            .then(data => {
                if (data.email) {
                    welcomeText.textContent = "Xin chào " + (data.hoTen || data.email) + " — chọn vai trò để hoàn tất đăng ký.";
                }
            })
            .catch(() => {});

        document.querySelectorAll(".role-option").forEach(function (btn) {
            btn.addEventListener("click", async function () {
                roleError.classList.remove("show");
                const maVaiTro = parseInt(this.dataset.role, 10);

                try {
                    const res = await fetch("${pageContext.request.contextPath}/api/auth/google/complete-registration", {
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify({ maVaiTro })
                    });
                    const data = await res.json();

                    if (!res.ok) {
                        throw new Error(data.message || "Có lỗi xảy ra");
                    }

                    localStorage.setItem("token", data.token);
                    localStorage.setItem("user", JSON.stringify(data.user));

                    window.location.href = "${pageContext.request.contextPath}/";
                } catch (err) {
                    roleError.textContent = err.message;
                    roleError.classList.add("show");
                }
            });
        });
    </script>
</body>
</html>