<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Về chúng tôi - ROOM CONNECT</title>

    <!-- Google Font -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;600;700&family=Playfair+Display:ital,wght@0,400;0,500;0,600;1,400;1,500&display=swap" rel="stylesheet">

    <%@ include file="/WEB-INF/jsp/common/head-assets.jspf" %>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        html {
            scroll-behavior: smooth;
        }

        body {
            font-family: "DM Sans", sans-serif;
            background: #fff;
            color: #605b52;
            overflow-x: hidden;
        }

        img {
            display: block;
            max-width: 100%;
        }

        button,
        input {
            font-family: inherit;
        }

        /* =========================
           HEADER
        ========================= */

        .header {
            width: 100%;
            height: 64px;
            background: #fff;
            border-bottom: 1px solid #ddd;
            display: flex;
            align-items: center;
            padding: 0 32px;
            position: sticky;
            top: 0;
            z-index: 1000;
        }

        .header-inner {
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .logo {
            font-size: 18px;
            font-weight: 700;
            color: #111;
            letter-spacing: -0.5px;
            white-space: nowrap;
        }

        .nav {
            display: flex;
            align-items: center;
            gap: 4px;
            border: 1px solid #d5d2ce;
            border-radius: 30px;
            padding: 3px;
            margin-left: 20px;
        }

        .nav a {
            text-decoration: none;
            color: #333;
            font-size: 12px;
            padding: 8px 18px;
            border-radius: 20px;
            transition: .2s;
        }

        .nav a:hover,
        .nav a.active {
            background: #625c53;
            color: white;
        }

        .header-right {
            display: flex;
            align-items: center;
            gap: 18px;
        }

        .search {
            width: 180px;
            height: 34px;
            border: 1px solid #ddd;
            background: #f7f7f7;
            border-radius: 20px;
            padding: 0 14px;
            font-size: 11px;
            outline: none;
        }

        .notification {
            font-size: 18px;
            color: #222;
            cursor: pointer;
        }

        .user {
            height: 36px;
            border: 1px solid #d8d0ca;
            background: #f8eeee;
            border-radius: 22px;
            padding: 0 12px 0 7px;
            display: flex;
            align-items: center;
            gap: 7px;
            font-size: 11px;
            color: #555;
        }

        .user-avatar {
            width: 25px;
            height: 25px;
            border-radius: 50%;
            background: #ddd;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 12px;
        }

        /* =========================
           MAIN
        ========================= */

        .main {
            width: 100%;
            background: #fff;
        }

        .container {
            width: min(1180px, calc(100% - 70px));
            margin: auto;
        }

        /* =========================
           HERO
        ========================= */

        .hero {
            padding: 65px 0 85px;
        }

        .hero-grid {
            display: grid;
            grid-template-columns: 35% 65%;
            align-items: start;
            gap: 35px;
        }

        .hero-left {
            padding-top: 5px;
        }

        .eyebrow {
            font-size: 15px;
            font-weight: 600;
            color: #686158;
            margin-bottom: 20px;
        }

        .hero-title {
            font-family: "Playfair Display", serif;
            font-size: clamp(58px, 7vw, 104px);
            font-weight: 400;
            line-height: .89;
            color: #080808;
            letter-spacing: -4px;
        }

        .hero-title .italic {
            font-style: italic;
            font-size: .78em;
            display: block;
            margin: 12px 0;
        }

        .hero-title .last {
            display: block;
        }

        .hero-description {
            border-left: 4px solid #6a645b;
            margin-top: 35px;
            padding-left: 18px;
            max-width: 350px;
            color: #716b63;
            font-size: 15px;
            line-height: 1.45;
        }

        .hero-image {
            width: 100%;
            height: 560px;
            object-fit: cover;
        }

        /* =========================
           STORY
        ========================= */

        .story {
            padding: 50px 0 120px;
        }

        .story-grid {
            display: grid;
            grid-template-columns: 43% 57%;
            align-items: center;
            gap: 70px;
        }

        .story-image {
            width: 100%;
            height: 500px;
            object-fit: cover;
        }

        .story-content {
            padding-right: 50px;
        }

        .section-label {
            font-size: 15px;
            font-weight: 600;
            margin-bottom: 15px;
        }

        .story-title {
            font-family: "Playfair Display", serif;
            font-size: clamp(36px, 4vw, 56px);
            line-height: 1.08;
            font-weight: 500;
            color: #655f56;
            margin-bottom: 28px;
        }

        .story-content p {
            font-size: 15px;
            line-height: 1.55;
            margin-bottom: 24px;
            color: #706b63;
        }

        .quote {
            font-weight: 700;
            font-style: italic;
            color: #5d574f !important;
        }

        /* =========================
           VALUES
        ========================= */

        .values {
            padding: 30px 0 120px;
        }

        .values-heading {
            margin-bottom: 70px;
        }

        .values-script {
            font-family: "Playfair Display", serif;
            font-size: clamp(45px, 6vw, 80px);
            font-style: italic;
            font-weight: 400;
            color: #111;
            line-height: 1;
        }

        .values-main {
            font-family: "Playfair Display", serif;
            font-size: clamp(55px, 7vw, 90px);
            font-weight: 400;
            color: #6a645c;
            line-height: .95;
        }

        .values-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 80px 120px;
        }

        .value {
            position: relative;
        }

        .value:nth-child(2) {
            margin-top: 90px;
        }

        .value:nth-child(3) {
            margin-top: -20px;
        }

        .value-number {
            font-family: "Playfair Display", serif;
            font-size: 90px;
            color: #c9c7c4;
            font-style: italic;
            line-height: .6;
            position: absolute;
            left: -25px;
            top: 10px;
            z-index: 0;
        }

        .value-content {
            position: relative;
            z-index: 1;
            padding-left: 25px;
        }

        .value-title {
            font-family: "Playfair Display", serif;
            font-size: 48px;
            font-weight: 500;
            color: #6b655d;
            margin-bottom: 15px;
        }

        .value-text {
            max-width: 400px;
            line-height: 1.5;
            font-size: 15px;
            color: #716c65;
        }

        .value-image {
            width: 100%;
            height: 260px;
            object-fit: cover;
            margin-top: 30px;
        }

        .value:nth-child(2) .value-image {
            margin-top: 30px;
        }

        /* =========================
           TEAM
        ========================= */

        .team {
            background: #625c52;
            color: white;
            padding: 75px 0 110px;
        }

        .team-top {
            margin-bottom: 65px;
        }

        .team-small {
            font-size: 13px;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 15px;
            color: #d9d5ce;
        }

        .team-title {
            font-family: "Playfair Display", serif;
            font-size: clamp(48px, 6vw, 76px);
            line-height: .95;
            font-weight: 500;
        }

        .team-title em {
            display: block;
            font-size: .7em;
            margin-top: 12px;
        }

        .team-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 90px 130px;
            max-width: 1000px;
            margin: auto;
        }

        .member {
            width: 100%;
        }

        .member-image {
            width: 100%;
            aspect-ratio: 1 / 1.15;
            object-fit: cover;
            background: #d9d9d9;
            filter: grayscale(100%);
        }

        .member-info {
            border-top: 1px solid rgba(255,255,255,.65);
            margin-top: 18px;
            padding-top: 15px;
        }

        .member-name {
            font-family: "Playfair Display", serif;
            font-size: 23px;
            color: white;
        }

        .member-role {
            font-size: 13px;
            color: #d1cdc6;
            margin-top: 8px;
        }

        .member:last-child {
            grid-column: 1 / -1;
            width: 50%;
            margin: 0 auto;
        }

        /* =========================
           FOOTER
        ========================= */

        .footer {
            background: #625c52;
            color: white;
            padding: 0 0 35px;
        }

        .footer-inner {
            border-top: 1px solid rgba(255,255,255,.3);
            padding-top: 28px;
        }

        .footer-grid {
            display: grid;
            grid-template-columns: 2fr 1fr 1fr;
            gap: 80px;
            padding-bottom: 35px;
        }

        .footer-logo {
            display: flex;
            align-items: center;
            gap: 9px;
            font-size: 17px;
            font-weight: 600;
        }

        .logo-box {
            width: 24px;
            height: 24px;
            background: red;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 7px;
            font-weight: bold;
        }

        .footer-description {
            font-size: 11px;
            line-height: 1.5;
            color: #ddd8d1;
            max-width: 310px;
            margin-top: 16px;
        }

        .footer-heading {
            font-size: 12px;
            font-weight: 600;
            margin-bottom: 15px;
        }

        .footer-list {
            list-style: none;
        }

        .footer-list li {
            font-size: 11px;
            color: #d8d4ce;
            margin-bottom: 9px;
        }

        .copyright {
            border-top: 1px solid rgba(255,255,255,.3);
            padding-top: 20px;
            text-align: center;
            font-size: 10px;
            color: #d0cbc4;
        }

        /* =========================
           RESPONSIVE TABLET
        ========================= */

        @media (max-width: 900px) {

            .header {
                padding: 0 20px;
            }

            .nav {
                display: none;
            }

            .search {
                width: 130px;
            }

            .container {
                width: min(100% - 40px, 700px);
            }

            .hero {
                padding: 45px 0 70px;
            }

            .hero-grid {
                grid-template-columns: 1fr;
                gap: 40px;
            }

            .hero-title {
                font-size: 78px;
            }

            .hero-image {
                height: 500px;
            }

            .story-grid {
                grid-template-columns: 1fr;
                gap: 50px;
            }

            .story-content {
                padding-right: 0;
            }

            .story-image {
                height: 450px;
            }

            .values-grid {
                gap: 60px;
            }

            .team-grid {
                gap: 70px 50px;
            }

            .footer-grid {
                gap: 40px;
            }
        }

        /* =========================
           MOBILE
        ========================= */

        @media (max-width: 600px) {

            .header {
                height: 58px;
                padding: 0 15px;
            }

            .logo {
                font-size: 15px;
            }

            .header-right {
                gap: 8px;
            }

            .search {
                display: none;
            }

            .notification {
                font-size: 16px;
            }

            .user {
                height: 31px;
                padding-right: 9px;
                font-size: 9px;
            }

            .user-avatar {
                width: 21px;
                height: 21px;
            }

            .container {
                width: calc(100% - 30px);
            }

            .hero {
                padding: 45px 0 60px;
            }

            .eyebrow {
                font-size: 13px;
                margin-bottom: 15px;
            }

            .hero-title {
                font-size: clamp(55px, 18vw, 82px);
                letter-spacing: -3px;
            }

            .hero-description {
                font-size: 13px;
                margin-top: 28px;
            }

            .hero-image {
                height: 400px;
            }

            .story {
                padding: 30px 0 80px;
            }

            .story-grid {
                gap: 40px;
            }

            .story-image {
                height: 360px;
            }

            .story-title {
                font-size: 38px;
            }

            .story-content p {
                font-size: 13px;
            }

            .values {
                padding: 20px 0 80px;
            }

            .values-heading {
                margin-bottom: 50px;
            }

            .values-grid {
                grid-template-columns: 1fr;
                gap: 65px;
            }

            .value:nth-child(2),
            .value:nth-child(3) {
                margin-top: 0;
            }

            .value-title {
                font-size: 42px;
            }

            .value-image {
                height: 220px;
            }

            .team {
                padding: 55px 0 70px;
            }

            .team-top {
                margin-bottom: 50px;
            }

            .team-title {
                font-size: 50px;
            }

            .team-grid {
                grid-template-columns: 1fr;
                gap: 55px;
            }

            .member:last-child {
                grid-column: auto;
                width: 100%;
            }

            .member-image {
                aspect-ratio: 1 / 1.1;
            }

            .footer {
                padding-bottom: 25px;
            }

            .footer-grid {
                grid-template-columns: 1fr;
                gap: 35px;
            }

            .footer-description {
                max-width: 100%;
            }
        }
    </style>
</head>

<body>

<!-- =========================
     HEADER
========================= -->

<%@ include file="/WEB-INF/jsp/common/header.jsp" %>


<!-- =========================
     MAIN
========================= -->

<main class="main">

    <!-- HERO -->
    <section class="hero">
        <div class="container">

            <div class="hero-grid">

                <div class="hero-left">

                    <div class="eyebrow">
                        Về chúng tôi
                    </div>

                    <h1 class="hero-title">
                        Kết nối

                        <span class="italic">
                            kiến tạo
                        </span>

                        <span class="last">
                            không gian.
                        </span>
                    </h1>

                    <div class="hero-description">
                        Room Connect không chỉ là nền tảng tìm kiếm,
                        chúng tôi là cầu nối giữa những tâm hồn đồng điệu
                        và những không gian đầy cảm hứng.
                    </div>

                </div>

                <img
                    class="hero-image"
                    src="https://images.unsplash.com/photo-1600210492486-724fe5c67fb0?auto=format&fit=crop&w=1400&q=85"
                    alt="Không gian sống"
                >

            </div>

        </div>
    </section>


    <!-- STORY -->
    <section class="story">

        <div class="container">

            <div class="story-grid">

                <img
                    class="story-image"
                    src="https://images.unsplash.com/photo-1600607687920-4e2a09cf159d?auto=format&fit=crop&w=1000&q=85"
                    alt="Không gian phòng"
                >

                <div class="story-content">

                    <div class="section-label">
                        Câu chuyện
                    </div>

                    <h2 class="story-title">
                        Hành trình từ một
                        nhu cầu thiết yếu đến
                        một triết lý sống
                    </h2>

                    <p>
                        Khởi nguồn từ những khó khăn chân thực trong việc
                        tìm kiếm một không gian sống phù hợp giữa lòng đô
                        thị phồn hoa, Room Connect được tạo ra với một khát
                        vọng đơn giản nhưng mạnh mẽ: mang đến một không gian
                        sống và sự an tâm lý tưởng.
                    </p>

                    <p>
                        Chúng tôi tin rằng, một căn phòng không chỉ là bốn
                        bức tường che mưa nắng. Nó là nơi chốn vỗ về tâm hồn
                        sau những xô bồ, là nơi ươm mầm những giấc mơ và là
                        tấm gương phản chiếu phong cách sống của mỗi cá nhân.
                    </p>

                    <p class="quote">
                        "Bằng sự kết hợp giữa công nghệ hiện đại và sự tinh
                        tế trong thẩm mỹ, Room Connect tuyển chọn và mang đến
                        những không gian sống chất lượng, an toàn và đáng tin cậy."
                    </p>

                </div>

            </div>

        </div>

    </section>


    <!-- VALUES -->
    <section class="values">

        <div class="container">

            <div class="values-heading">

                <div class="values-script">
                    Giá trị
                </div>

                <div class="values-main">
                    Cốt lõi.
                </div>

            </div>


            <div class="values-grid">

                <!-- VALUE 1 -->
                <article class="value">

                    <div class="value-number">
                        1
                    </div>

                    <div class="value-content">

                        <h3 class="value-title">
                            Tin cậy
                        </h3>

                        <p class="value-text">
                            Mọi thông tin, hình ảnh đều được kiểm chứng
                            nghiêm ngặt. Chúng tôi xây dựng một nền tảng
                            dựa trên sự thật, nơi niềm tin là thước đo cao nhất.
                        </p>

                        <img
                            class="value-image"
                            src="https://images.unsplash.com/photo-1497366754035-f200968a6e72?auto=format&fit=crop&w=1000&q=85"
                            alt="Không gian làm việc"
                        >

                    </div>

                </article>


                <!-- VALUE 2 -->
                <article class="value">

                    <div class="value-number">
                        2
                    </div>

                    <div class="value-content">

                        <h3 class="value-title">
                            Minh bạch
                        </h3>

                        <p class="value-text">
                            Không góc khuất, không chi phí ẩn. Mọi quy trình
                            từ tìm kiếm đến giao dịch đều được hiển thị rõ ràng,
                            giúp bạn đưa ra quyết định tự tin nhất.
                        </p>

                        <img
                            class="value-image"
                            src="https://images.unsplash.com/photo-1511818966892-d7d671e672a2?auto=format&fit=crop&w=1000&q=85"
                            alt="Không gian xanh"
                        >

                    </div>

                </article>


                <!-- VALUE 3 -->
                <article class="value">

                    <div class="value-number">
                        3
                    </div>

                    <div class="value-content">

                        <h3 class="value-title">
                            Hiệu quả
                        </h3>

                        <p class="value-text">
                            Tối ưu hóa thời gian và công sức nhờ thuật toán
                            thông minh và giao diện trực quan. Việc tìm kiếm
                            không gian sống trở nên tinh tế và mượt mà.
                        </p>

                    </div>

                </article>

            </div>

        </div>

    </section>


    <!-- TEAM -->
    <section class="team">

        <div class="container">

            <div class="team-top">

                <div class="team-small">
                    Đội ngũ sáng lập
                </div>

                <h2 class="team-title">
                    Những người kiến tạo
                    <em>Tĩnh lặng.</em>
                </h2>

            </div>


            <div class="team-grid">

                <!-- MEMBER 1 -->
                <article class="member">

                    <img
                        class="member-image"
                        src="https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?auto=format&fit=crop&w=800&q=85"
                        alt="Đoàn Quốc Đạt"
                    >

                    <div class="member-info">

                        <div class="member-name">
                            Đoàn Quốc Đạt
                        </div>

                        <div class="member-role">
                            Member
                        </div>

                    </div>

                </article>


                <!-- MEMBER 2 -->
                <article class="member">

                    <img
                        class="member-image"
                        src="https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&w=800&q=85"
                        alt="Nguyễn Văn B"
                    >

                    <div class="member-info">

                        <div class="member-name">
                            Nguyễn Văn B
                        </div>

                        <div class="member-role">
                            Member
                        </div>

                    </div>

                </article>


                <!-- MEMBER 3 -->
                <article class="member">

                    <img
                        class="member-image"
                        src="https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?auto=format&fit=crop&w=800&q=85"
                        alt="Nguyễn Văn C"
                    >

                    <div class="member-info">

                        <div class="member-name">
                            Nguyễn Văn C
                        </div>

                        <div class="member-role">
                            Member
                        </div>

                    </div>

                </article>


                <!-- MEMBER 4 -->
                <article class="member">

                    <img
                        class="member-image"
                        src="https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=800&q=85"
                        alt="Nguyễn Văn D"
                    >

                    <div class="member-info">

                        <div class="member-name">
                            Nguyễn Văn D
                        </div>

                        <div class="member-role">
                            Member
                        </div>

                    </div>

                </article>


                <!-- MEMBER 5 -->
                <article class="member">

                    <img
                        class="member-image"
                        src="https://images.unsplash.com/photo-1507591064344-4c6ce005b128?auto=format&fit=crop&w=800&q=85"
                        alt="Nguyễn Văn E"
                    >

                    <div class="member-info">

                        <div class="member-name">
                            Nguyễn Văn E
                        </div>

                        <div class="member-role">
                            Leader
                        </div>

                    </div>

                </article>

            </div>

        </div>

    </section>

</main>


<!-- =========================
     FOOTER
========================= -->

<footer class="footer">

    <div class="container">

        <div class="footer-inner">

            <div class="footer-grid">

                <div>

                    <div class="footer-logo">

                        <div class="logo-box">
                            logo
                        </div>

                        <span>
                            Room connect
                        </span>

                    </div>

                    <p class="footer-description">
                        Nền tảng tìm kiếm phòng trọ dễ dàng,
                        giúp người thuê và chủ trọ kết nối với
                        nhau một cách nhanh chóng.
                    </p>

                </div>


                <div>

                    <div class="footer-heading">
                        Thông tin liên hệ
                    </div>

                    <ul class="footer-list">
                        <li>Chính sách</li>
                        <li>Điều khoản sử dụng</li>
                        <li>Chính sách bảo mật</li>
                    </ul>

                </div>


                <div>

                    <div class="footer-heading">
                        Hotline
                    </div>

                    <ul class="footer-list">
                        <li>0123456789</li>
                        <li>roomconnect@gmail.com</li>
                    </ul>

                </div>

            </div>


            <div class="copyright">
                © 2026 ROOM - CONNECT. All rights reserved.
            </div>

        </div>

    </div>

</footer>


<script>

    // Highlight menu
    const navLinks = document.querySelectorAll(".nav a");

    navLinks.forEach(link => {

        link.addEventListener("click", function(e) {

            navLinks.forEach(item => {
                item.classList.remove("active");
            });

            this.classList.add("active");

        });

    });


    // Search demo (UI tạm thời, chưa nối API tìm kiếm thật)
    const search = document.querySelector(".search");

    if (search) {

        search.addEventListener("keydown", function(e) {

            if (e.key === "Enter") {

                const value = this.value.trim();

                if (value !== "") {
                    window.location.href = "${pageContext.request.contextPath}/thue-tro?keyword=" + encodeURIComponent(value);
                }

            }

        });

    }

</script>

</body>
</html>
