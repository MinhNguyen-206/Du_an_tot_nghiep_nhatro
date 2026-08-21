<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>ROOM - CONNECT | Liên hệ</title>

    <!-- Font -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:ital,wght@0,400;0,500;0,600;1,500&family=Inter:wght@400;500;600;700;800&display=swap"
          rel="stylesheet">

    <%@ include file="/WEB-INF/jsp/common/head-assets.jspf" %>

    <style>

        /* =====================================================
           RESET
        ===================================================== */

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        html {
            width: 100%;
            min-height: 100%;
        }

        body {
            width: 100%;
            min-height: 100vh;

            font-family: "Inter", Arial, sans-serif;

            background: #e9e9e9;
            color: #222;

            overflow-x: hidden;
        }

        button,
        input,
        textarea {
            font-family: inherit;
        }


        /* =====================================================
           HEADER
        ===================================================== */

        .header {
            width: 100%;
            height: 62px;

            background: #ffffff;

            display: flex;
            align-items: center;

            padding: 0 28px;

            border-top: 1px solid #555;
            border-bottom: 1px solid #ddd;

            position: relative;
            z-index: 20;
        }

        .logo {
            font-size: 15px;
            font-weight: 800;

            color: #111;

            white-space: nowrap;
        }


        /* NAV */

        .nav {
            margin-left: 50px;

            display: flex;
            align-items: center;

            border: 1px solid #c9c9c9;

            border-radius: 30px;

            overflow: hidden;

            background: #fff;
        }

        .nav a {
            text-decoration: none;

            color: #333;

            font-size: 10px;

            padding: 8px 20px;

            transition: .2s;
        }

        .nav a:hover,
        .nav a.active {
            background: #f0f0f0;
        }


        /* SEARCH */

        .header-search {
            margin-left: auto;

            width: 220px;
            height: 32px;

            border: 1px solid #ddd;
            border-radius: 20px;

            background: #fafafa;

            padding: 0 15px;

            font-size: 10px;

            outline: none;
        }

        .header-search:focus {
            border-color: #999;
        }


        /* RIGHT */

        .header-right {
            margin-left: 15px;

            display: flex;
            align-items: center;

            gap: 14px;
        }

        .notification {
            font-size: 15px;

            color: #222;

            cursor: pointer;
        }

        .user {
            height: 34px;

            padding: 3px 12px 3px 5px;

            display: flex;
            align-items: center;

            gap: 7px;

            background: #f8eeee;

            border: 1px solid #d6caca;

            border-radius: 30px;

            font-size: 10px;
        }

        .avatar {
            width: 26px;
            height: 26px;

            border-radius: 50%;

            display: flex;
            align-items: center;
            justify-content: center;

            background: #e2ddd7;

            color: #777;
        }


        /* =====================================================
           MAIN
        ===================================================== */

        .main {
            width: 100%;

            min-height: calc(100vh - 62px);

            padding: 60px 5vw 55px;
        }

        .container {
            width: 100%;

            max-width: 1400px;

            margin: 0 auto;
        }


        /* =====================================================
           HERO
        ===================================================== */

        .hero {
            width: 100%;

            display: grid;

            grid-template-columns:
                minmax(340px, 1fr)
                minmax(420px, .92fr);

            column-gap: clamp(30px, 5vw, 80px);

            align-items: start;
        }


        /* =====================================================
           LEFT
        ===================================================== */

        .hero-left {
            width: 100%;

            /* Đệm phải để nét chữ thảo (script) và dấu "&" phóng to
               không bị dính sát/đè lên khung form bên cạnh */
            padding-right: 18px;

            position: relative;
            z-index: 1;
        }


        /* BIG TITLE */

        .main-title {
            font-family: "Cormorant Garamond", serif;

            /* Dùng vw của cột trái (ước lượng ~46% container) thay vì vw
               toàn viewport, để chữ không phình to hơn cột chứa nó và
               tràn đè sang form bên phải ở các khung hình vừa (~1000-1500px) */
            font-size: clamp(56px, 5.6vw, 112px);

            font-weight: 500;

            line-height: .82;

            letter-spacing: -2px;

            color: #111;

            overflow-wrap: break-word;
        }

        .main-title .italic {
            display: block;

            font-style: italic;

            margin-top: 28px;
            margin-bottom: 25px;
        }

        .main-title .normal {
            display: block;
        }


        /* =====================================================
           MAIN IMAGE
        ===================================================== */

        .main-image {
            width: 100%;

            height: 500px;

            margin-top: 40px;

            object-fit: cover;

            display: block;
        }


        /* =====================================================
           FORM RIGHT
        ===================================================== */

        .hero-right {
            width: 100%;

            padding-top: 90px;
        }

        .contact-form {
            width: 100%;

            max-width: 560px;

            margin-left: auto;

            padding: 28px 30px 22px;

            background: #eeeeee;

            border: 1px solid #bdbdbd;

            position: relative;
            z-index: 2;
        }

        .form-small-title {
            text-align: center;

            font-family: "Cormorant Garamond", serif;

            font-size: 14px;

            color: #777;

            margin-bottom: 16px;
        }

        .form-title {
            font-size: 25px;

            line-height: 1.2;

            font-weight: 700;

            color: #222;

            margin-bottom: 22px;
        }


        /* INPUT */

        .field {
            width: 100%;

            margin-bottom: 12px;
        }

        .field input,
        .field textarea {
            width: 100%;

            border: 1px solid #bdbdbd;

            background: #f8f8f8;

            outline: none;

            color: #222;

            font-size: 11px;

            padding: 0 12px;
        }

        .field input {
            height: 40px;
        }

        .field textarea {
            height: 120px;

            resize: none;

            padding-top: 12px;
        }

        .field input:focus,
        .field textarea:focus {
            border-color: #555;
        }

        .field input::placeholder,
        .field textarea::placeholder {
            color: #777;
        }


        /* SUBMIT */

        .submit-btn {
            width: 100%;

            height: 42px;

            margin-top: 2px;

            border: 1px solid #aaa;

            background: #fff;

            color: #222;

            font-size: 10px;

            font-weight: 700;

            cursor: pointer;

            transition: .2s;
        }

        .submit-btn:hover {
            background: #222;

            color: white;
        }

        .submit-btn:disabled {
            opacity: .6;
            cursor: not-allowed;
        }

        .form-message {
            display: none;
            margin-top: 12px;
            padding: 10px 12px;
            font-size: 11px;
            border-radius: 4px;
        }

        .form-message.show {
            display: block;
        }

        .form-message.success {
            background: #e6f4ea;
            color: #1e7e34;
            border: 1px solid #b7e1c0;
        }

        .form-message.error {
            background: #fdecea;
            color: #b02a37;
            border: 1px solid #f5c2c7;
        }


        /* =====================================================
           CONTACT INFORMATION
        ===================================================== */

        .contact-info {
            width: 100%;

            margin-top: 30px;

            border-top: 1px solid #aaa;

            padding-top: 15px;
        }

        .contact-grid {
            display: grid;

            grid-template-columns:
                1.5fr
                1fr
                1fr;

            gap: 35px;
        }

        .contact-item {
            min-height: 70px;
        }

        .contact-label {
            font-family: "Cormorant Garamond", serif;

            font-size: 12px;

            color: #777;

            margin-bottom: 10px;
        }

        .contact-value {
            font-family: "Cormorant Garamond", serif;

            font-size: 25px;

            color: #555;
        }

        .contact-item:not(:first-child) .contact-value {
            font-family: "Inter", sans-serif;

            font-size: 12px;
        }


        /* =====================================================
           OFFICE
        ===================================================== */

        .office {
            width: 100%;

            margin-top: 35px;

            padding: 24px;

            background: #f0f0f0;

            border: 1px solid #c4c4c4;

            display: grid;

            grid-template-columns: .9fr 1.1fr;

            gap: 40px;

            min-height: 290px;
        }

        .office-content {
            display: flex;

            flex-direction: column;

            justify-content: center;
        }

        .office-label {
            width: 95px;

            padding-top: 9px;

            border-top: 1px solid #999;

            font-size: 10px;

            color: #666;
        }

        .office-title {
            font-family: "Cormorant Garamond", serif;

            font-size: 42px;

            font-weight: 500;

            margin-top: 17px;

            margin-bottom: 15px;
        }

        .office-text {
            max-width: 360px;

            color: #666;

            font-size: 11px;

            line-height: 1.6;
        }

        .direction {
            display: inline-flex;

            align-items: center;

            gap: 7px;

            width: max-content;

            margin-top: 20px;

            padding-top: 8px;

            border-top: 1px solid #999;

            text-decoration: none;

            color: #333;

            font-size: 10px;
        }

        .office-image {
            width: 100%;

            height: 270px;

            object-fit: cover;
        }


        /* =====================================================
           FOOTER
        ===================================================== */

        .footer {
            width: 100%;

            background: #625d54;

            color: #fff;

            margin-top: 50px;

            padding: 42px 5vw 25px;

            border-radius: 28px 28px 0 0;
        }

        .footer-inner {
            width: 100%;

            max-width: 1400px;

            margin: auto;
        }

        .footer-grid {
            display: grid;

            grid-template-columns:
                1.5fr
                1fr
                1fr;

            gap: 80px;
        }

        .footer-logo {
            display: flex;

            align-items: center;

            gap: 9px;

            font-size: 14px;

            font-weight: 700;

            margin-bottom: 16px;
        }

        .footer-logo-box {
            width: 25px;
            height: 25px;

            background: red;

            display: flex;

            align-items: center;
            justify-content: center;

            color: white;

            font-size: 6px;
        }

        .footer-description {
            max-width: 300px;

            color: #eee;

            font-size: 9px;

            line-height: 1.7;
        }

        .footer-title {
            font-size: 10px;

            font-weight: 700;

            margin-bottom: 14px;
        }

        .footer-link {
            display: block;

            color: #eee;

            text-decoration: none;

            font-size: 9px;

            margin-bottom: 8px;
        }

        .footer-link:hover {
            text-decoration: underline;
        }

        .footer-bottom {
            border-top: 1px solid rgba(255,255,255,.3);

            margin-top: 32px;

            padding-top: 16px;

            text-align: center;

            font-size: 8px;

            color: #eee;
        }


        /* =====================================================
           LARGE DESKTOP
        ===================================================== */

        @media (min-width: 1600px) {

            .header {
                height: 70px;

                padding: 0 45px;
            }

            .logo {
                font-size: 17px;
            }

            .nav {
                margin-left: 60px;
            }

            .nav a {
                font-size: 11px;

                padding: 10px 24px;
            }

            .main {
                padding: 75px 7vw 60px;
            }

            .container {
                max-width: 1500px;
            }

            .hero {
                grid-template-columns: 1.08fr .92fr;

                column-gap: 75px;
            }

            .main-title {
                font-size: 130px;
            }

            .main-image {
                height: 580px;
            }

            .hero-right {
                padding-top: 145px;
            }

            .contact-form {
                max-width: 620px;

                padding: 35px;
            }

            .form-title {
                font-size: 29px;
            }

            .field input {
                height: 44px;
            }

            .field textarea {
                height: 135px;
            }

            .office {
                min-height: 330px;
            }

            .office-image {
                height: 310px;
            }

            .footer {
                padding-left: 7vw;
                padding-right: 7vw;
            }
        }


        /* =====================================================
           TABLET
        ===================================================== */

        @media (max-width: 1050px) {

            .header {
                padding: 0 20px;
            }

            .nav {
                margin-left: 25px;
            }

            .nav a {
                padding: 8px 12px;
            }

            .header-search {
                display: none;
            }

            .main {
                padding: 45px 30px;
            }

            .hero {
                grid-template-columns: 1fr;

                gap: 35px;
            }

            .hero-right {
                padding-top: 0;
            }

            .contact-form {
                max-width: 100%;

                margin: 0;
            }

            .main-image {
                height: 500px;
            }
        }


        /* =====================================================
           MOBILE
        ===================================================== */

        @media (max-width: 650px) {

            .header {
                height: 58px;

                padding: 0 15px;
            }

            .logo {
                font-size: 13px;
            }

            .nav,
            .header-search {
                display: none;
            }

            .header-right {
                margin-left: auto;
            }

            .notification {
                display: none;
            }

            .main {
                padding: 35px 16px;
            }

            .main-title {
                font-size: 65px;

                line-height: .78;

                letter-spacing: -2px;
            }

            .main-title .italic {
                margin-top: 20px;

                margin-bottom: 20px;
            }

            .main-image {
                height: 380px;

                margin-top: 30px;
            }

            .hero-right {
                padding-top: 20px;
            }

            .contact-form {
                padding: 22px;
            }

            .form-title {
                font-size: 23px;
            }

            .contact-grid {
                grid-template-columns: 1fr;

                gap: 18px;
            }

            .office {
                grid-template-columns: 1fr;

                padding: 18px;

                gap: 25px;
            }

            .office-title {
                font-size: 37px;
            }

            .office-image {
                height: 220px;
            }

            .footer {
                margin-top: 30px;

                padding: 35px 22px 20px;

                border-radius: 22px 22px 0 0;
            }

            .footer-grid {
                grid-template-columns: 1fr;

                gap: 25px;
            }
        }

    </style>
</head>


<body>


<!-- =====================================================
     HEADER
===================================================== -->

<%@ include file="/WEB-INF/jsp/common/header.jsp" %>



<!-- =====================================================
     MAIN
===================================================== -->

<main class="main">

    <div class="container">


        <!-- HERO -->

        <section class="hero">


            <!-- LEFT -->

            <div class="hero-left">

                <h1 class="main-title">

                    Kết nối

                    <span class="italic">
                        Kiến tạo
                    </span>

                    <span class="normal">
                        không gian.
                    </span>

                </h1>


                <img
                    class="main-image"
                    src="https://images.unsplash.com/photo-1600210492486-724fe5c67fb0?auto=format&fit=crop&w=1400&q=90"
                    alt="Không gian phòng"
                >

            </div>



            <!-- RIGHT -->

            <div class="hero-right">

                <form class="contact-form" id="contactForm">


                    <div class="form-small-title">
                        Gửi lời nhắn
                    </div>


                    <h2 class="form-title">
                        Chúng tôi ở đây để
                        lắng nghe.
                    </h2>


                    <div class="field">

                        <input
                            type="text"
                            id="contactName"
                            placeholder="Tên của bạn *"
                            required
                        >

                    </div>


                    <div class="field">

                        <input
                            type="email"
                            id="contactEmail"
                            placeholder="Địa chỉ Email *"
                            required
                        >

                    </div>


                    <div class="field">

                        <textarea
                            id="contactMessage"
                            placeholder="Nội dung tin nhắn *"
                            required
                        ></textarea>

                    </div>


                    <button
                        type="submit"
                        class="submit-btn"
                        id="contactSubmitBtn"
                    >
                        GỬI THÔNG ĐIỆP →
                    </button>


                    <div class="form-message" id="contactFormMessage"></div>

                </form>

            </div>

        </section>



        <!-- =================================================
             CONTACT INFO
        ================================================== -->

        <section class="contact-info">

            <div class="contact-grid">


                <div class="contact-item">

                    <div class="contact-label">
                        Kết nối trực tiếp
                    </div>

                    <div class="contact-value">
                        Thông tin liên hệ
                    </div>

                </div>


                <div class="contact-item">

                    <div class="contact-label">
                        Hotline
                    </div>

                    <div class="contact-value">
                        +8412345678
                    </div>

                </div>


                <div class="contact-item">

                    <div class="contact-label">
                        Email
                    </div>

                    <div class="contact-value">
                        roomconnect@gmail.com
                    </div>

                </div>

            </div>

        </section>



        <!-- =================================================
             OFFICE
        ================================================== -->

        <section class="office">


            <div class="office-content">

                <div class="office-label">
                    Ghé thăm chúng tôi
                </div>


                <h2 class="office-title">
                    Trụ sở chính.
                </h2>


                <p class="office-text">

                    Tòa nhà QTSC9, đường Tô Ký,
                    Phường Trung Mỹ Tây,
                    Thành phố Hồ Chí Minh.

                </p>


                <a href="https://www.google.com/maps/search/?api=1&query=QTSC9+To+Ky+Ho+Chi+Minh" target="_blank" rel="noopener" class="direction">

                    <i class="fa-solid fa-location-dot"></i>

                    Chỉ đường →

                </a>

            </div>


            <img
                class="office-image"
                src="https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?auto=format&fit=crop&w=1400&q=90"
                alt="Tòa nhà văn phòng"
            >

        </section>


    </div>

</main>



<!-- =====================================================
     FOOTER
===================================================== -->

<footer class="footer">

    <div class="footer-inner">

        <div class="footer-grid">


            <div>

                <div class="footer-logo">

                    <div class="footer-logo-box">
                        logo
                    </div>

                    Room connect

                </div>


                <p class="footer-description">

                    Nền tảng tìm kiếm phòng trọ dễ dàng,
                    giúp người thuê và chủ trọ kết nối với nhau
                    một cách nhanh chóng.

                </p>

            </div>



            <div>

                <div class="footer-title">
                    Thông tin liên hệ
                </div>

                <a class="footer-link" href="${pageContext.request.contextPath}/gioi-thieu">
                    Về chúng tôi
                </a>

                <a class="footer-link" href="${pageContext.request.contextPath}/lien-he">
                    Liên hệ
                </a>

            </div>



            <div>

                <div class="footer-title">
                    Hotline
                </div>

                <a
                    href="tel:0123456789"
                    class="footer-link"
                >
                    0123456789
                </a>

                <a
                    href="mailto:roomconnect@gmail.com"
                    class="footer-link"
                >
                    roomconnect@gmail.com
                </a>

            </div>


        </div>


        <div class="footer-bottom">

            © 2026 ROOM - CONNECT. All rights reserved.

        </div>

    </div>

</footer>



<!-- =====================================================
     JAVASCRIPT
===================================================== -->

<script>

    // Highlight menu
    const navLinksContact = document.querySelectorAll(".nav a");

    navLinksContact.forEach(link => {
        link.addEventListener("click", function () {
            navLinksContact.forEach(item => item.classList.remove("active"));
            this.classList.add("active");
        });
    });


    // Form gửi thật lên backend: POST /api/lien-he -> server sẽ gửi email
    // về hộp mail admin (MAIL_USERNAME đã cấu hình), kèm Reply-To là email
    // của khách để admin bấm "Trả lời" là nhắn thẳng cho khách.
    const contactForm = document.getElementById("contactForm");
    const contactFormMessage = document.getElementById("contactFormMessage");
    const contactSubmitBtn = document.getElementById("contactSubmitBtn");

    contactForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const name = document.getElementById("contactName").value.trim();
        const email = document.getElementById("contactEmail").value.trim();
        const message = document.getElementById("contactMessage").value.trim();

        contactFormMessage.classList.remove("show", "success", "error");

        if (!name || !email || !message) {
            contactFormMessage.textContent = "Vui lòng nhập đầy đủ thông tin.";
            contactFormMessage.classList.add("show", "error");
            return;
        }

        contactSubmitBtn.disabled = true;
        contactSubmitBtn.textContent = "Đang gửi...";

        fetch("${pageContext.request.contextPath}/api/lien-he", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                hoTen: name,
                email: email,
                noiDung: message
            })
        })
            .then(function (res) {
                return res.json().then(function (data) {
                    return { ok: res.ok, data: data };
                });
            })
            .then(function (result) {
                if (result.ok && result.data && result.data.success) {
                    contactFormMessage.textContent = result.data.message ||
                        ("Cảm ơn " + name + " đã liên hệ ROOM - CONNECT. Chúng tôi sẽ phản hồi qua email " + email + " sớm nhất.");
                    contactFormMessage.classList.add("show", "success");
                    contactForm.reset();
                } else {
                    contactFormMessage.textContent = (result.data && result.data.message) ||
                        "Có lỗi xảy ra, vui lòng thử lại sau.";
                    contactFormMessage.classList.add("show", "error");
                }
            })
            .catch(function () {
                contactFormMessage.textContent =
                    "Không thể kết nối tới máy chủ. Vui lòng kiểm tra mạng và thử lại.";
                contactFormMessage.classList.add("show", "error");
            })
            .finally(function () {
                contactSubmitBtn.disabled = false;
                contactSubmitBtn.textContent = "GỬI THÔNG ĐIỆP →";
            });
    });

</script>

</body>
</html>
