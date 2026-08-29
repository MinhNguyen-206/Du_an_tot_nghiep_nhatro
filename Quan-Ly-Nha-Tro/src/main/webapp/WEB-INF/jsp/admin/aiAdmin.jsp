<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">

    <title>AI Admin - Phân tích thông minh</title>

    <style>

        * {
            box-sizing: border-box;
        }

        :root {
            --primary: #2563eb;
            --primary-dark: #1d4ed8;
            --orange: #f97316;
            --orange-dark: #ea580c;

            --text: #172033;
            --muted: #64748b;

            --background: #f8fafc;
            --card: #ffffff;

            --border: #e2e8f0;

            --success-bg: #ecfdf5;
            --success-text: #047857;

            --warning-bg: #fff7ed;
            --warning-text: #c2410c;

            --danger-bg: #fef2f2;
            --danger-text: #b91c1c;

            --shadow:
                    0 10px 30px rgba(15, 23, 42, 0.08);

            --radius: 18px;
        }


        body {

            margin: 0;

            min-height: 100vh;

            font-family:
                    Inter,
                    "Segoe UI",
                    Arial,
                    sans-serif;

            background:
                    linear-gradient(
                            135deg,
                            #f8fafc 0%,
                            #eff6ff 100%
                    );

            color: var(--text);
        }


        .page {

            max-width: 1400px;

            margin: 0 auto;

            padding: 36px 28px 60px;
        }


        /* =====================================================
           HEADER
           ===================================================== */

        .hero {

            position: relative;

            overflow: hidden;

            padding: 34px;

            margin-bottom: 30px;

            border-radius: 24px;

            background:
                    linear-gradient(
                            135deg,
                            #1d4ed8,
                            #2563eb,
                            #3b82f6
                    );

            color: white;

            box-shadow:
                    0 20px 45px rgba(37, 99, 235, 0.22);
        }


        .hero::before {

            content: "";

            position: absolute;

            width: 250px;
            height: 250px;

            border-radius: 50%;

            background: rgba(255, 255, 255, 0.08);

            right: -80px;

            top: -90px;
        }


        .hero::after {

            content: "";

            position: absolute;

            width: 160px;
            height: 160px;

            border-radius: 50%;

            background: rgba(255, 255, 255, 0.06);

            right: 140px;

            bottom: -100px;
        }


        .hero-content {

            position: relative;

            z-index: 1;
        }


        .hero-title {

            margin: 0 0 8px;

            font-size: 34px;

            font-weight: 800;

            letter-spacing: -0.5px;
        }


        .hero-subtitle {

            margin: 0;

            max-width: 780px;

            color: rgba(255, 255, 255, 0.86);

            font-size: 16px;

            line-height: 1.7;
        }


        .hero-badge {

            display: inline-flex;

            align-items: center;

            gap: 8px;

            margin-bottom: 16px;

            padding: 7px 12px;

            border-radius: 999px;

            background: rgba(255, 255, 255, 0.15);

            border: 1px solid rgba(255, 255, 255, 0.2);

            font-size: 13px;

            font-weight: 700;
        }


        /* =====================================================
           SUMMARY
           ===================================================== */

        .summary-grid {

            display: grid;

            grid-template-columns:
                    repeat(
                            2,
                            minmax(0, 1fr)
                    );

            gap: 22px;

            margin-bottom: 34px;
        }


        .summary-card {

            background: var(--card);

            border: 1px solid var(--border);

            border-radius: var(--radius);

            padding: 24px;

            box-shadow: var(--shadow);
        }


        .summary-card.ai12 {

            border-top: 4px solid var(--primary);
        }


        .summary-card.ai13 {

            border-top: 4px solid var(--orange);
        }


        .summary-top {

            display: flex;

            align-items: flex-start;

            justify-content: space-between;

            gap: 18px;

            margin-bottom: 18px;
        }


        .summary-icon {

            display: flex;

            align-items: center;

            justify-content: center;

            width: 52px;

            height: 52px;

            flex: 0 0 52px;

            border-radius: 15px;

            font-size: 25px;
        }


        .summary-icon.blue {

            background: #eff6ff;
        }


        .summary-icon.orange {

            background: #fff7ed;
        }


        .summary-title {

            margin: 0 0 6px;

            font-size: 19px;

            font-weight: 800;
        }


        .summary-description {

            margin: 0;

            color: var(--muted);

            font-size: 14px;

            line-height: 1.55;
        }


        .endpoint {

            display: block;

            margin-top: 16px;

            padding: 10px 12px;

            border-radius: 10px;

            background: #f8fafc;

            border: 1px dashed var(--border);

            color: #475569;

            font-family:
                    Consolas,
                    monospace;

            font-size: 12px;

            word-break: break-all;
        }


        /* =====================================================
           SECTION
           ===================================================== */

        .section {

            margin-top: 36px;
        }


        .section-heading {

            display: flex;

            align-items: center;

            justify-content: space-between;

            gap: 20px;

            margin-bottom: 18px;
        }


        .section-title-wrap {

            display: flex;

            align-items: center;

            gap: 12px;
        }


        .section-number {

            display: flex;

            align-items: center;

            justify-content: center;

            width: 38px;

            height: 38px;

            border-radius: 12px;

            font-weight: 800;

            font-size: 14px;

            color: white;
        }


        .section-number.blue {

            background: var(--primary);
        }


        .section-number.orange {

            background: var(--orange);
        }


        .section-title {

            margin: 0;

            font-size: 23px;

            font-weight: 800;
        }


        .section-subtitle {

            margin: 5px 0 0;

            color: var(--muted);

            font-size: 14px;
        }


        /* =====================================================
           BUTTON
           ===================================================== */

        .btn {

            display: inline-flex;

            align-items: center;

            justify-content: center;

            gap: 8px;

            padding: 11px 17px;

            border: none;

            border-radius: 11px;

            color: white;

            font-size: 14px;

            font-weight: 700;

            cursor: pointer;

            transition:
                    transform 0.15s ease,
                    box-shadow 0.15s ease,
                    opacity 0.15s ease;
        }


        .btn:hover {

            transform: translateY(-1px);
        }


        .btn:disabled {

            opacity: 0.6;

            cursor: not-allowed;

            transform: none;
        }


        .btn-blue {

            background: var(--primary);

            box-shadow:
                    0 6px 15px rgba(37, 99, 235, 0.2);
        }


        .btn-blue:hover {

            background: var(--primary-dark);
        }


        .btn-orange {

            background: var(--orange);

            box-shadow:
                    0 6px 15px rgba(249, 115, 22, 0.2);
        }


        .btn-orange:hover {

            background: var(--orange-dark);
        }


        /* =====================================================
           STATUS
           ===================================================== */

        .status {

            display: flex;

            align-items: center;

            gap: 10px;

            padding: 14px 16px;

            margin-bottom: 18px;

            background: var(--success-bg);

            border: 1px solid #a7f3d0;

            border-radius: 12px;

            color: var(--success-text);

            font-size: 14px;
        }


        .status-dot {

            width: 9px;

            height: 9px;

            border-radius: 50%;

            background: #10b981;

            box-shadow:
                    0 0 0 4px rgba(16, 185, 129, 0.12);
        }


        /* =====================================================
           LOADING / ERROR
           ===================================================== */

        .loading {

            display: none;

            margin: 18px 0;

            padding: 16px;

            border-radius: 12px;

            background: var(--warning-bg);

            color: var(--warning-text);

            border: 1px solid #fed7aa;

            font-size: 14px;
        }


        .error {

            display: none;

            margin: 18px 0;

            padding: 16px;

            border-radius: 12px;

            background: var(--danger-bg);

            color: var(--danger-text);

            border: 1px solid #fecaca;

            font-size: 14px;

            line-height: 1.6;
        }


        /* =====================================================
           AI-12 CARDS
           ===================================================== */

        .grid {

            display: grid;

            grid-template-columns:
                    repeat(
                            auto-fill,
                            minmax(330px, 1fr)
                    );

            gap: 20px;

            margin-top: 22px;
        }


        .post-card {

            position: relative;

            background: var(--card);

            border: 1px solid var(--border);

            border-radius: var(--radius);

            padding: 22px;

            box-shadow: var(--shadow);

            transition:
                    transform 0.18s ease,
                    box-shadow 0.18s ease;
        }


        .post-card:hover {

            transform: translateY(-4px);

            box-shadow:
                    0 18px 38px rgba(
                            15,
                            23,
                            42,
                            0.12
                    );
        }


        .post-card h3 {

            margin: 0 0 12px;

            font-size: 18px;

            line-height: 1.4;
        }


        .score {

            display: inline-flex;

            align-items: center;

            gap: 6px;

            font-size: 31px;

            line-height: 1;

            font-weight: 900;

            color: var(--primary);

            margin-bottom: 14px;
        }


        .level-row {

            display: flex;

            align-items: center;

            gap: 8px;

            margin-bottom: 16px;

            font-size: 14px;
        }


        .level-pill {

            display: inline-flex;

            align-items: center;

            padding: 5px 10px;

            border-radius: 999px;

            font-size: 12px;

            font-weight: 800;
        }


        .level-high {

            background: #dcfce7;

            color: #166534;
        }


        .level-medium {

            background: #ffedd5;

            color: #9a3412;
        }


        .level-low {

            background: #fee2e2;

            color: #991b1b;
        }


        .info-list {

            display: grid;

            gap: 4px;
        }


        .info {

            display: flex;

            align-items: flex-start;

            justify-content: space-between;

            gap: 15px;

            padding: 8px 0;

            border-bottom: 1px solid #f1f5f9;

            font-size: 13px;

            line-height: 1.5;
        }


        .info:last-child {

            border-bottom: none;
        }


        .info-label {

            color: var(--muted);

            flex: 0 0 auto;
        }


        .info-value {

            text-align: right;

            font-weight: 600;

            word-break: break-word;
        }


        .ai-box {

            margin-top: 16px;

            padding: 14px;

            background: #eff6ff;

            border-left: 4px solid var(--primary);

            border-radius: 10px;
        }


        .ai-box-title {

            display: block;

            margin-bottom: 5px;

            font-size: 13px;

            color: #1e40af;

            font-weight: 800;
        }


        .ai-box p {

            margin: 0;

            color: #334155;

            font-size: 13px;

            line-height: 1.65;
        }


        /* =====================================================
           AI-13
           ===================================================== */

        .hot-grid {

            display: grid;

            grid-template-columns:
                    repeat(
                            auto-fill,
                            minmax(280px, 1fr)
                    );

            gap: 20px;

            margin-top: 22px;
        }


        .area-card {

            background: var(--card);

            border: 1px solid var(--border);

            border-radius: var(--radius);

            padding: 22px;

            box-shadow: var(--shadow);

            transition:
                    transform 0.18s ease,
                    box-shadow 0.18s ease;
        }


        .area-card:hover {

            transform: translateY(-4px);

            box-shadow:
                    0 18px 38px rgba(
                            15,
                            23,
                            42,
                            0.12
                    );
        }


        .rank {

            display: inline-flex;

            align-items: center;

            justify-content: center;

            min-width: 40px;

            height: 30px;

            padding: 0 10px;

            border-radius: 999px;

            background: #f1f5f9;

            color: #475569;

            font-size: 12px;

            font-weight: 900;

            margin-bottom: 12px;
        }


        .rank-first {

            background: #ffedd5;

            color: #c2410c;
        }


        .area-card h3 {

            margin: 0 0 14px;

            font-size: 20px;
        }


        .hot-score {

            font-size: 32px;

            font-weight: 900;

            color: var(--orange);

            margin-bottom: 13px;
        }


        .trend {

            margin-bottom: 15px;

            font-size: 14px;
        }


        .trend-pill {

            display: inline-flex;

            padding: 5px 10px;

            border-radius: 999px;

            margin-left: 5px;

            font-size: 12px;

            font-weight: 800;
        }


        .trend-hot {

            background: #ffedd5;

            color: #c2410c;
        }


        .trend-stable {

            background: #dbeafe;

            color: #1d4ed8;
        }


        .trend-low {

            background: #f1f5f9;

            color: #475569;
        }


        .hot-box {

            margin-top: 16px;

            padding: 14px;

            background: #fff7ed;

            border-left: 4px solid var(--orange);

            border-radius: 10px;
        }


        .hot-box-title {

            display: block;

            margin-bottom: 5px;

            color: #c2410c;

            font-size: 13px;

            font-weight: 800;
        }


        .hot-box p {

            margin: 0;

            color: #475569;

            font-size: 13px;

            line-height: 1.65;
        }


        /* =====================================================
           EMPTY
           ===================================================== */

        .empty {

            grid-column: 1 / -1;

            padding: 45px 25px;

            text-align: center;

            background: white;

            border: 1px dashed var(--border);

            border-radius: var(--radius);

            color: var(--muted);
        }


        /* =====================================================
           FOOTNOTE
           ===================================================== */

        .footnote {

            margin-top: 42px;

            padding: 18px 20px;

            border-radius: 14px;

            background: #f8fafc;

            border: 1px solid var(--border);

            color: var(--muted);

            font-size: 13px;

            line-height: 1.7;
        }


        .footnote strong {

            color: #334155;
        }


        /* =====================================================
           RESPONSIVE
           ===================================================== */

        @media (max-width: 900px) {

            .summary-grid {

                grid-template-columns: 1fr;
            }


            .section-heading {

                align-items: flex-start;

                flex-direction: column;
            }


            .hero-title {

                font-size: 28px;
            }
        }


        @media (max-width: 600px) {

            .page {

                padding: 20px 14px 40px;
            }


            .hero {

                padding: 26px 22px;

                border-radius: 18px;
            }


            .grid {

                grid-template-columns: 1fr;
            }


            .hot-grid {

                grid-template-columns: 1fr;
            }


            .summary-card {

                padding: 19px;
            }
        }

    </style>
</head>


<body>

<div class="page">


    <!-- =====================================================
         HERO
         ===================================================== -->

    <section class="hero">

        <div class="hero-content">

            <div class="hero-badge">
                🤖 AI ADMIN DASHBOARD
            </div>

            <h1 class="hero-title">
                Phân tích thông minh
            </h1>

            <p class="hero-subtitle">
                Trung tâm hỗ trợ quản trị nhà trọ bằng AI.
                Phân tích bài đăng tiềm năng và nhận diện
                các khu vực đang có mức độ quan tâm cao.
            </p>

        </div>

    </section>


    <!-- =====================================================
         SUMMARY
         ===================================================== -->

    <section class="summary-grid">


        <div class="summary-card ai12">

            <div class="summary-top">

                <div>

                    <div class="summary-icon blue">
                        📊
                    </div>

                </div>

                <div style="flex: 1;">

                    <h2 class="summary-title">
                        AI-12
                    </h2>

                    <p class="summary-description">
                        Dự đoán bài đăng có tiềm năng
                        thu hút người thuê.
                    </p>

                </div>

            </div>

            <span class="endpoint">
                GET /api/admin/ai/potential-posts
            </span>

        </div>


        <div class="summary-card ai13">

            <div class="summary-top">

                <div>

                    <div class="summary-icon orange">
                        🔥
                    </div>

                </div>

                <div style="flex: 1;">

                    <h2 class="summary-title">
                        AI-13
                    </h2>

                    <p class="summary-description">
                        Gợi ý các khu vực có nhu cầu
                        thuê phòng cao.
                    </p>

                </div>

            </div>

            <span class="endpoint">
                GET /api/admin/ai/hot-areas
            </span>

        </div>


    </section>


    <!-- =====================================================
         AI-12
         ===================================================== -->

    <section class="section">


        <div class="section-heading">

            <div>

                <div class="section-title-wrap">

                    <div class="section-number blue">
                        12
                    </div>

                    <div>

                        <h2 class="section-title">
                            Bài đăng tiềm năng
                        </h2>

                        <p class="section-subtitle">
                            Phân tích nhiều yếu tố để xếp hạng
                            mức độ tiềm năng của từng bài đăng.
                        </p>

                    </div>

                </div>

            </div>


            <button
                    type="button"
                    class="btn btn-blue"
                    id="btnLoadAI">

                🔄
                Phân tích bài đăng

            </button>

        </div>


        <div class="status">

            <span class="status-dot"></span>

            <div>
                <strong>AI-12 đang hoạt động</strong>
                — dữ liệu được lấy trực tiếp từ hệ thống.
            </div>

        </div>


        <div
                id="loading"
                class="loading">

            ⏳
            Đang phân tích các bài đăng...

        </div>


        <div
                id="error"
                class="error">
        </div>


        <div
                id="result"
                class="grid">
        </div>


    </section>


    <!-- =====================================================
         AI-13
         ===================================================== -->

    <section class="section">


        <div class="section-heading">

            <div>

                <div class="section-title-wrap">

                    <div class="section-number orange">
                        13
                    </div>

                    <div>

                        <h2 class="section-title">
                            Khu vực hot
                        </h2>

                        <p class="section-subtitle">
                            Phân tích lượt xem, lượt yêu thích
                            và số phòng để xếp hạng khu vực.
                        </p>

                    </div>

                </div>

            </div>


            <button
                    type="button"
                    class="btn btn-orange"
                    id="btnLoadHotAreas">

                🔥
                Phân tích khu vực

            </button>

        </div>


        <div class="status">

            <span class="status-dot"></span>

            <div>
                <strong>AI-13 đang hoạt động</strong>
                — dữ liệu được lấy trực tiếp từ hệ thống.
            </div>

        </div>


        <div
                id="hotAreaLoading"
                class="loading">

            ⏳
            Đang phân tích các khu vực...

        </div>


        <div
                id="hotAreaError"
                class="error">
        </div>


        <div
                id="hotAreaResult"
                class="hot-grid">
        </div>


    </section>


    <!-- =====================================================
         FOOTNOTE
         ===================================================== -->

    <div class="footnote">

        <strong>💡 Cách hoạt động:</strong>

        AI-12 sử dụng dữ liệu bài đăng, phòng,
        lượt xem, lượt yêu thích, giá, diện tích,
        tiện ích và các thuộc tính phòng để tính
        điểm tiềm năng.

        AI-13 tổng hợp dữ liệu tương tác của các phòng
        theo khu vực để xác định nơi đang có mức độ
        quan tâm cao hơn.

    </div>


</div>


<script>

    /*
     * =========================================================
     * API
     * =========================================================
     *
     * Không sử dụng JavaScript template literal
     * để tránh xung đột với JSP Expression Language.
     */

    const AI12_API_URL =
        '${pageContext.request.contextPath}/api/admin/ai/potential-posts';

    const AI13_API_URL =
        '${pageContext.request.contextPath}/api/admin/ai/hot-areas';


    /*
     * =========================================================
     * DOM
     * =========================================================
     */

    const btnLoadAI =
        document.getElementById("btnLoadAI");

    const loading =
        document.getElementById("loading");

    const error =
        document.getElementById("error");

    const result =
        document.getElementById("result");


    const btnLoadHotAreas =
        document.getElementById("btnLoadHotAreas");

    const hotAreaLoading =
        document.getElementById("hotAreaLoading");

    const hotAreaError =
        document.getElementById("hotAreaError");

    const hotAreaResult =
        document.getElementById("hotAreaResult");


    /*
     * =========================================================
     * HELPER
     * =========================================================
     */

    function safeValue(
            value,
            defaultValue
    ) {

        if (
            value === null ||
            value === undefined ||
            value === ""
        ) {

            return defaultValue;
        }

        return value;
    }


    function formatMoney(value) {

        if (
            value === null ||
            value === undefined ||
            value === ""
        ) {

            return "0 VNĐ";
        }

        const number =
            Number(value);

        if (Number.isNaN(number)) {

            return "0 VNĐ";
        }

        return number.toLocaleString(
            "vi-VN"
        ) + " VNĐ";
    }


    function createInfo(
            label,
            value
    ) {

        const div =
            document.createElement("div");

        div.className =
            "info";


        const labelElement =
            document.createElement("span");

        labelElement.className =
            "info-label";

        labelElement.textContent =
            label;


        const valueElement =
            document.createElement("span");

        valueElement.className =
            "info-value";

        valueElement.textContent =
            safeValue(
                value,
                ""
            );


        div.appendChild(
            labelElement
        );

        div.appendChild(
            valueElement
        );


        return div;
    }


    /*
     * =========================================================
     * AI-12
     * =========================================================
     */

    function getLevelClass(level) {

        if (!level) {
            return "level-low";
        }

        const text =
            String(level)
                .toLowerCase()
                .trim();


        if (
            text.includes("rất tiềm năng") ||
            text === "tiềm năng"
        ) {

            return "level-high";
        }


        if (text === "khá") {

            return "level-medium";
        }


        return "level-low";
    }


    function createPostCard(item) {

        const card =
            document.createElement("div");

        card.className =
            "post-card";


        /*
         * Tiêu đề
         */
        const title =
            document.createElement("h3");

        title.textContent =
            safeValue(
                item.tieuDe,
                "Không có tiêu đề"
            );


        /*
         * Điểm
         */
        const score =
            document.createElement("div");

        score.className =
            "score";

        score.textContent =
            safeValue(
                item.diemAI,
                0
            ) + " điểm";


        /*
         * Mức độ
         */
        const levelRow =
            document.createElement("div");

        levelRow.className =
            "level-row";


        const levelLabel =
            document.createElement("strong");

        levelLabel.textContent =
            "Mức độ:";


        const levelPill =
            document.createElement("span");

        levelPill.className =
            "level-pill "
            + getLevelClass(
                item.mucDo
            );

        levelPill.textContent =
            safeValue(
                item.mucDo,
                "Không xác định"
            );


        levelRow.appendChild(
            levelLabel
        );

        levelRow.appendChild(
            levelPill
        );


        /*
         * Thông tin
         */
        const infoList =
            document.createElement("div");

        infoList.className =
            "info-list";


        infoList.appendChild(
            createInfo(
                "Phòng",
                item.tenPhong
            )
        );


        infoList.appendChild(
            createInfo(
                "Nhà trọ",
                item.tenNhaTro
            )
        );


        infoList.appendChild(
            createInfo(
                "Địa chỉ",
                item.diaChi
            )
        );


        infoList.appendChild(
            createInfo(
                "Giá",
                formatMoney(
                    item.giaPhong
                )
            )
        );


        infoList.appendChild(
            createInfo(
                "Diện tích",
                safeValue(
                    item.dienTich,
                    0
                ) + " m²"
            )
        );


        infoList.appendChild(
            createInfo(
                "Số tiện ích",
                safeValue(
                    item.soTienIch,
                    0
                )
            )
        );


        infoList.appendChild(
            createInfo(
                "Lượt xem",
                safeValue(
                    item.luotXem,
                    0
                )
            )
        );


        infoList.appendChild(
            createInfo(
                "Lượt yêu thích",
                safeValue(
                    item.luotYeuThich,
                    0
                )
            )
        );


        /*
         * Nhận định
         */
        const aiBox =
            document.createElement("div");

        aiBox.className =
            "ai-box";


        const aiTitle =
            document.createElement("span");

        aiTitle.className =
            "ai-box-title";

        aiTitle.textContent =
            "🤖 Nhận định AI";


        const aiText =
            document.createElement("p");

        aiText.textContent =
            safeValue(
                item.nhanDinh,
                "Chưa có nhận định."
            );


        aiBox.appendChild(
            aiTitle
        );

        aiBox.appendChild(
            aiText
        );


        /*
         * Ghép
         */
        card.appendChild(title);

        card.appendChild(score);

        card.appendChild(levelRow);

        card.appendChild(infoList);

        card.appendChild(aiBox);


        return card;
    }


    function renderData(data) {

        result.innerHTML =
            "";


        if (
            !Array.isArray(data) ||
            data.length === 0
        ) {

            const empty =
                document.createElement("div");

            empty.className =
                "empty";

            empty.textContent =
                "Không có bài đăng nào.";

            result.appendChild(
                empty
            );

            return;
        }


        data.forEach(
            function(item) {

                result.appendChild(
                    createPostCard(item)
                );

            }
        );
    }


    async function loadAI() {

        loading.style.display =
            "block";

        error.style.display =
            "none";

        result.innerHTML =
            "";

        btnLoadAI.disabled =
            true;


        try {

            const response =
                await fetch(
                    AI12_API_URL,
                    {
                        method: "GET",

                        headers: {
                            "Accept":
                                "application/json"
                        }
                    }
                );


            if (!response.ok) {

                throw new Error(
                    "API AI-12 trả về HTTP "
                    + response.status
                );
            }


            const data =
                await response.json();


            console.log(
                "AI-12 DATA:",
                data
            );


            renderData(data);


        } catch (e) {

            console.error(
                "Lỗi AI-12:",
                e
            );


            error.style.display =
                "block";

            error.textContent =
                "Lỗi khi gọi AI-12: "
                + e.message;


        } finally {

            loading.style.display =
                "none";

            btnLoadAI.disabled =
                false;
        }
    }


    /*
     * =========================================================
     * AI-13
     * =========================================================
     */

    function getTrendClass(trend) {

        if (!trend) {
            return "trend-low";
        }

        const text =
            String(trend)
                .toLowerCase()
                .trim();


        if (
            text === "rất hot" ||
            text === "hot"
        ) {

            return "trend-hot";
        }


        if (
            text === "đang tăng" ||
            text === "ổn định"
        ) {

            return "trend-stable";
        }


        return "trend-low";
    }


    function createHotAreaCard(
            item,
            index
    ) {

        const card =
            document.createElement("div");

        card.className =
            "area-card";


        /*
         * Hạng
         */
        const rank =
            document.createElement("div");

        rank.className =
            "rank";


        if (index === 0) {

            rank.classList.add(
                "rank-first"
            );
        }


        rank.textContent =
            "#" + (index + 1);


        /*
         * Khu vực
         */
        const title =
            document.createElement("h3");

        title.textContent =
            safeValue(
                item.khuVuc,
                "Không xác định"
            );


        /*
         * Điểm
         */
        const score =
            document.createElement("div");

        score.className =
            "hot-score";

        score.textContent =
            safeValue(
                item.diemNhuCau,
                0
            ) + " điểm";


        /*
         * Xu hướng
         */
        const trend =
            document.createElement("div");

        trend.className =
            "trend";


        const trendLabel =
            document.createElement("strong");

        trendLabel.textContent =
            "Xu hướng:";


        const trendPill =
            document.createElement("span");

        trendPill.className =
            "trend-pill "
            + getTrendClass(
                item.xuHuong
            );

        trendPill.textContent =
            safeValue(
                item.xuHuong,
                "Chưa đủ dữ liệu"
            );


        trend.appendChild(
            trendLabel
        );

        trend.appendChild(
            trendPill
        );


        /*
         * Thống kê
         */
        const infoList =
            document.createElement("div");

        infoList.className =
            "info-list";


        infoList.appendChild(
            createInfo(
                "Số phòng",
                safeValue(
                    item.soPhong,
                    0
                )
            )
        );


        infoList.appendChild(
            createInfo(
                "Lượt xem",
                safeValue(
                    item.luotXem,
                    0
                )
            )
        );


        /*
         * Nhận định
         */
        const hotBox =
            document.createElement("div");

        hotBox.className =
            "hot-box";


        const hotTitle =
            document.createElement("span");

        hotTitle.className =
            "hot-box-title";

        hotTitle.textContent =
            "🔥 Phân tích khu vực";


        const hotText =
            document.createElement("p");


        const scoreValue =
            Number(
                safeValue(
                    item.diemNhuCau,
                    0
                )
            );


        if (scoreValue === 0) {

            hotText.textContent =
                "Chưa có đủ dữ liệu tương tác để đánh giá mức độ hot của khu vực.";

        } else if (scoreValue >= 80) {

            hotText.textContent =
                "Khu vực đang có mức độ quan tâm rất cao so với các khu vực còn lại.";

        } else if (scoreValue >= 60) {

            hotText.textContent =
                "Khu vực đang có nhu cầu tốt và có dấu hiệu thu hút người tìm phòng.";

        } else if (scoreValue >= 40) {

            hotText.textContent =
                "Khu vực có mức độ quan tâm tương đối và có tiềm năng tăng trưởng.";

        } else {

            hotText.textContent =
                "Khu vực đang có dữ liệu quan tâm nhưng chưa nổi bật.";
        }


        hotBox.appendChild(
            hotTitle
        );

        hotBox.appendChild(
            hotText
        );


        /*
         * Ghép card
         */
        card.appendChild(rank);

        card.appendChild(title);

        card.appendChild(score);

        card.appendChild(trend);

        card.appendChild(infoList);

        card.appendChild(hotBox);


        return card;
    }


    function renderHotAreas(data) {

        hotAreaResult.innerHTML =
            "";


        if (
            !Array.isArray(data) ||
            data.length === 0
        ) {

            const empty =
                document.createElement("div");

            empty.className =
                "empty";

            empty.textContent =
                "Không có dữ liệu khu vực.";

            hotAreaResult.appendChild(
                empty
            );

            return;
        }


        data.forEach(
            function(item, index) {

                hotAreaResult.appendChild(
                    createHotAreaCard(
                        item,
                        index
                    )
                );

            }
        );
    }


    async function loadHotAreas() {

        hotAreaLoading.style.display =
            "block";

        hotAreaError.style.display =
            "none";

        hotAreaResult.innerHTML =
            "";

        btnLoadHotAreas.disabled =
            true;


        try {

            const response =
                await fetch(
                    AI13_API_URL,
                    {
                        method: "GET",

                        headers: {
                            "Accept":
                                "application/json"
                        }
                    }
                );


            if (!response.ok) {

                throw new Error(
                    "API AI-13 trả về HTTP "
                    + response.status
                );
            }


            const data =
                await response.json();


            console.log(
                "AI-13 DATA:",
                data
            );


            renderHotAreas(data);


        } catch (e) {

            console.error(
                "Lỗi AI-13:",
                e
            );


            hotAreaError.style.display =
                "block";

            hotAreaError.textContent =
                "Lỗi khi gọi AI-13: "
                + e.message;


        } finally {

            hotAreaLoading.style.display =
                "none";

            btnLoadHotAreas.disabled =
                false;
        }
    }


    /*
     * =========================================================
     * EVENTS
     * =========================================================
     */

    btnLoadAI.addEventListener(
        "click",
        loadAI
    );


    btnLoadHotAreas.addEventListener(
        "click",
        loadHotAreas
    );


    /*
     * =========================================================
     * AUTO LOAD
     * =========================================================
     */

    document.addEventListener(
        "DOMContentLoaded",
        function() {

            loadAI();

            loadHotAreas();

        }
    );

</script>

</body>
</html>