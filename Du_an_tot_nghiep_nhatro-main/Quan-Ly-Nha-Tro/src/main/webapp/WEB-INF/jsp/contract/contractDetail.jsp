<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết hợp đồng - ROOM CONNECT</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Inter', Arial, sans-serif; }
        body { background: #f5f6f8; color: #222; }

        .topbar { background: #fff; border-bottom: 1px solid #eee; padding: 14px 24px; display: flex; align-items: center; justify-content: space-between; }
        .topbar a.brand { color: #ff3345; font-weight: 800; text-decoration: none; font-size: 18px; }
        .topbar .back-link { color: #666; font-weight: 600; font-size: 13px; text-decoration: none; }

        .wrap { max-width: 860px; margin: 28px auto 60px; padding: 0 16px; }

        .center-state { text-align: center; padding: 80px 16px; color: #777; }
        .hidden { display: none !important; }

        .spinner { width: 32px; height: 32px; border: 3px solid #eee; border-top-color: #ff3345; border-radius: 50%; margin: 0 auto 14px; animation: spin .8s linear infinite; }
        @keyframes spin { to { transform: rotate(360deg); } }
        .retry-btn { margin-top: 14px; background: #ff3345; color: #fff; border: none; padding: 9px 18px; border-radius: 8px; font-weight: 700; cursor: pointer; }

        .toolbar { display: flex; align-items: center; justify-content: space-between; gap: 12px; flex-wrap: wrap; margin-bottom: 18px; }
        .toolbar h1 { font-size: 18px; }
        .toolbar .sub { font-size: 12px; color: #888; margin-top: 2px; }
        .toolbar-actions { display: flex; gap: 10px; flex-wrap: wrap; }
        .cd-btn { border: none; border-radius: 8px; padding: 10px 16px; font-size: 13px; font-weight: 700; cursor: pointer; display: inline-flex; align-items: center; gap: 7px; }
        .cd-btn.primary { background: #ff3345; color: #fff; }
        .cd-btn.outline { background: #fff; color: #444; border: 1px solid #ddd; }
        .cd-btn:disabled { opacity: .6; cursor: default; }

        .status-strip { display: flex; gap: 10px; flex-wrap: wrap; margin-bottom: 18px; }
        .status-pill { padding: 5px 12px; border-radius: 999px; font-size: 11.5px; font-weight: 700; }
        .status-pill.green { background: #e7f8ee; color: #1e8449; }
        .status-pill.orange { background: #fff4e5; color: #b9770e; }
        .status-pill.purple { background: #f1e9ff; color: #6d28d9; }
        .status-pill.blue { background: #e8f1ff; color: #1d4ed8; }

        #cdPrintArea { background: #fff; border-radius: 14px; padding: 30px 34px; font-size: 13px; line-height: 1.75; box-shadow: 0 6px 20px rgba(0,0,0,.05); }
        #cdPrintArea h1 { text-align: center; font-size: 17px; margin: 0 0 4px; }
        #cdPrintArea .quochieu { text-align: center; font-weight: 700; font-size: 13px; }
        #cdPrintArea .tieungu { text-align: center; font-size: 12px; margin-bottom: 18px; }
        #cdPrintArea .tieungu::after { content: ""; display: block; width: 100px; border-bottom: 1px solid #1e293b; margin: 4px auto 0; }
        #cdPrintArea h3 { font-size: 13.5px; margin: 16px 0 6px; }
        #cdPrintArea p { margin: 0 0 9px; text-align: justify; }
        #cdPrintArea ul { margin: 0 0 9px 18px; padding: 0; }
        #cdPrintArea li { margin-bottom: 4px; text-align: justify; }
        .cd-sign-table { width: 100%; border-collapse: collapse; margin-top: 22px; }
        .cd-sign-table td { width: 50%; text-align: center; vertical-align: top; padding: 14px 12px; border: 1px solid #cbd5e1; font-weight: 700; font-size: 12px; }
        .cd-sign-table .cd-sign-status { font-weight: 400; color: #64748b; margin-top: 10px; font-size: 11px; }
        .cd-sign-table img { max-height: 60px; max-width: 100%; margin-top: 10px; }
        .cd-sign-table .cd-cursive { font-family: "Segoe Script","Brush Script MT",cursive; font-size: 24px; color: #1e293b; margin-top: 12px; }
        .cd-sign-table .cd-pending { color: #c2410c; font-weight: 400; font-size: 11px; }

        @media print {
            .topbar, .toolbar, .status-strip { display: none !important; }
            body { background: #fff; }
            .wrap { max-width: 100%; margin: 0; padding: 0; }
            #cdPrintArea { box-shadow: none; border-radius: 0; padding: 0; }
        }
    </style>
</head>
<body data-context-path="${pageContext.request.contextPath}" data-ma-hop-dong="${maHopDong}">

<div class="topbar">
    <a class="brand" href="${pageContext.request.contextPath}/">Room Connect</a>
    <a class="back-link" id="backLink" href="${pageContext.request.contextPath}/profile">Quay lại</a>
</div>

<div class="wrap">

    <div class="center-state" id="loadingState">
        <div class="spinner"></div>
        <div>Đang tải hợp đồng...</div>
    </div>

    <div class="center-state hidden" id="errorState">
        <div id="errorMessage">Không tải được hợp đồng.</div>
        <button class="retry-btn" id="retryBtn">Thử lại</button>
    </div>

    <div class="hidden" id="pageContent">
        <div class="toolbar">
            <div>
                <h1 id="cdTitle">Hợp đồng thuê phòng</h1>
                <div class="sub" id="cdSub"></div>
            </div>
            <div class="toolbar-actions">
                <button type="button" class="cd-btn outline" id="btnPrint"><i class="bi bi-printer"></i> In hợp đồng</button>
                <button type="button" class="cd-btn primary" id="btnDownloadPdf"><i class="bi bi-file-earmark-arrow-down"></i> Tải PDF</button>
            </div>
        </div>

        <div class="status-strip" id="statusStrip"></div>

        <div id="cdPrintArea"></div>
    </div>

</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/api.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/hopDongChiTiet.js"></script>

</body>
</html>
