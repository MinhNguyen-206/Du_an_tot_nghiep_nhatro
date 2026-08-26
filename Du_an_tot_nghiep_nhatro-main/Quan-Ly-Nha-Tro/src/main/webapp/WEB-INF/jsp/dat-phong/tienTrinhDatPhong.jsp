<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tiến trình đặt phòng - ROOM CONNECT</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Inter', Arial, sans-serif; }
        body { background: #f5f6f8; color: #222; }

        .topbar { background: #fff; border-bottom: 1px solid #eee; padding: 14px 24px; display: flex; align-items: center; justify-content: space-between; }
        .topbar a { color: #ff3345; font-weight: 800; text-decoration: none; font-size: 18px; }
        .topbar .back-link { color: #666; font-weight: 600; font-size: 13px; text-decoration: none; }

        .wrap { max-width: 780px; margin: 28px auto; padding: 0 16px; }

        .center-state { text-align: center; padding: 80px 16px; color: #777; }
        .hidden { display: none !important; }

        .spinner { width: 32px; height: 32px; border: 3px solid #eee; border-top-color: #ff3345; border-radius: 50%; margin: 0 auto 14px; animation: spin .8s linear infinite; }
        @keyframes spin { to { transform: rotate(360deg); } }

        .retry-btn { margin-top: 14px; background: #ff3345; color: #fff; border: none; padding: 9px 18px; border-radius: 8px; font-weight: 700; cursor: pointer; }

        .room-card { background: #fff; border-radius: 14px; padding: 20px; margin-bottom: 20px; display: flex; justify-content: space-between; gap: 12px; flex-wrap: wrap; }
        .room-card h2 { font-size: 17px; margin-bottom: 4px; }
        .room-card .addr { font-size: 13px; color: #777; }
        .room-card .price { font-size: 16px; font-weight: 800; color: #ff3345; text-align: right; }
        .room-card .contact { font-size: 12px; color: #999; margin-top: 4px; text-align: right; }

        .steps { background: #fff; border-radius: 14px; padding: 24px 20px; margin-bottom: 20px; }
        .step { display: flex; gap: 14px; padding-bottom: 22px; position: relative; }
        .step:last-child { padding-bottom: 0; }
        .step:not(:last-child)::before { content: ''; position: absolute; left: 14px; top: 30px; bottom: 0; width: 2px; background: #eee; }
        .step.done:not(:last-child)::before { background: #2ecc71; }

        .step-dot { width: 30px; height: 30px; min-width: 30px; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-weight: 800; font-size: 13px; background: #eee; color: #999; z-index: 1; }
        .step.done .step-dot { background: #2ecc71; color: #fff; }
        .step.current .step-dot { background: #ff3345; color: #fff; }
        .step.failed .step-dot { background: #c0392b; color: #fff; }

        .step-body b { display: block; font-size: 14px; margin-bottom: 2px; }
        .step-body span { font-size: 12px; color: #888; }

        .action-panel { background: #fff; border-radius: 14px; padding: 20px; }
        .action-panel h3 { font-size: 15px; margin-bottom: 12px; }
        .action-panel label { display: block; font-size: 13px; font-weight: 600; margin: 12px 0 6px; }
        .action-panel input, .action-panel select { width: 100%; border: 1px solid #ddd; border-radius: 8px; padding: 9px 12px; font-size: 13px; font-family: inherit; }
        .action-row { display: flex; gap: 10px; margin-top: 16px; }
        .action-row button { flex: 1; border: none; border-radius: 8px; padding: 11px; font-weight: 800; font-size: 13px; cursor: pointer; }
        .btn-primary { background: #ff3345; color: #fff; }
        .btn-outline { background: #f2f2f2; color: #444; }
        .btn-primary:disabled, .btn-outline:disabled { opacity: .6; cursor: default; }

        .otp-demo-box { background: #fff8e1; border: 1px dashed #e6c200; border-radius: 8px; padding: 10px 12px; font-size: 12px; color: #7a6200; margin-top: 10px; }
        .action-error { background: #fdecea; color: #c0392b; border-radius: 8px; padding: 10px 12px; font-size: 12px; margin-top: 14px; }
        .waiting-note { font-size: 13px; color: #777; }
        .success-note { font-size: 14px; color: #1e8449; font-weight: 700; }
        .grid-2 { display: grid; grid-template-columns: 1fr 1fr; gap: 10px; }
    </style>
</head>
<body data-context-path="${pageContext.request.contextPath}" data-ma-yeu-cau="${maYeuCau}">

<div class="topbar">
    <a href="${pageContext.request.contextPath}/">Room Connect</a>
    <a class="back-link" href="${pageContext.request.contextPath}/profile">Về hồ sơ của tôi</a>
</div>

<div class="wrap">

    <div class="center-state" id="loadingState">
        <div class="spinner"></div>
        <div>Đang tải tiến trình đặt phòng...</div>
    </div>

    <div class="center-state hidden" id="errorState">
        <div id="errorMessage">Không tải được thông tin.</div>
        <button class="retry-btn" id="retryBtn">Thử lại</button>
    </div>

    <div class="hidden" id="pageContent">
        <div class="room-card" id="roomCardBox"></div>
        <div class="steps" id="stepsBox"></div>
        <div class="action-panel" id="actionBox"></div>
    </div>

</div>

<script src="${pageContext.request.contextPath}/resources/js/api.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/hopDongModal.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/hopDongKyModal.js"></script>
<script>
(function () {
    'use strict';

    var ctx = document.body.dataset.contextPath || '';
    var maYeuCau = document.body.dataset.maYeuCau;

    var loadingEl = document.getElementById('loadingState');
    var errorEl = document.getElementById('errorState');
    var errorMessageEl = document.getElementById('errorMessage');
    var contentEl = document.getElementById('pageContent');
    var roomCardBox = document.getElementById('roomCardBox');
    var stepsBox = document.getElementById('stepsBox');
    var actionBox = document.getElementById('actionBox');

    var currentUser = null;
    try { currentUser = JSON.parse(localStorage.getItem('user') || 'null'); } catch (e) { currentUser = null; }
    var token = localStorage.getItem('token');

    if (!currentUser || !currentUser.maNguoiDung || !token) {
        if (confirm('Bạn cần đăng nhập để xem tiến trình đặt phòng. Đăng nhập ngay?')) {
            window.location.href = ctx + '/login';
        }
        return;
    }

    var yeuCau = null;
    var hopDong = null;
    var myRole = null; // 'NGUOI_THUE' | 'CHU_TRO'

    function escapeHtml(v) {
        return String(v == null ? '' : v).replace(/[&<>"']/g, function (c) {
            return ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' })[c];
        });
    }
    function formatMoney(v) {
        var n = Number(v || 0);
        return new Intl.NumberFormat('vi-VN').format(n) + 'đ';
    }
    function formatDate(v) {
        if (!v) return '—';
        var d = new Date(v);
        if (isNaN(d.getTime())) return v;
        return d.toLocaleDateString('vi-VN') + (v.length > 10 ? ' ' + d.toLocaleTimeString('vi-VN', { hour: '2-digit', minute: '2-digit' }) : '');
    }
    function addToDate(dateStr, amount, unit) {
        var d = dateStr ? new Date(dateStr) : new Date();
        if (unit === 'Năm') d.setFullYear(d.getFullYear() + amount);
        else d.setMonth(d.getMonth() + amount);
        return d.toISOString().slice(0, 10);
    }

    function showFatalError(msg) {
        loadingEl.classList.add('hidden');
        contentEl.classList.add('hidden');
        errorMessageEl.textContent = msg;
        errorEl.classList.remove('hidden');
    }

    function loadAll() {
        loadingEl.classList.remove('hidden');
        errorEl.classList.add('hidden');
        contentEl.classList.add('hidden');

        apiFetch('/yeu-cau-thue/' + maYeuCau)
            .then(function (res) {
                if (!res) return;
                yeuCau = res;
                var maChuTro = (yeuCau.phong && yeuCau.phong.nhaTro && yeuCau.phong.nhaTro.nguoiDung)
                    ? yeuCau.phong.nhaTro.nguoiDung.maNguoiDung : null;
                var maNguoiThue = yeuCau.nguoiThue ? yeuCau.nguoiThue.maNguoiDung : null;

                if (currentUser.maNguoiDung === maNguoiThue) {
                    myRole = 'NGUOI_THUE';
                } else if (currentUser.maNguoiDung === maChuTro) {
                    myRole = 'CHU_TRO';
                } else {
                    throw new Error('Bạn không có quyền xem tiến trình của yêu cầu thuê này.');
                }

                return apiFetch('/hop-dong/theo-yeu-cau/' + maYeuCau)
                    .then(function (hd) { hopDong = hd; })
                    .catch(function (err) {
                        if (err && err.status === 404) { hopDong = null; }
                        else { throw err; }
                    });
            })
            .then(function () {
                if (!yeuCau) return;
                renderRoomCard();
                renderSteps();
                renderActionPanel();
                loadingEl.classList.add('hidden');
                contentEl.classList.remove('hidden');
            })
            .catch(function (err) {
                showFatalError((err && err.message) || 'Không tải được thông tin. Vui lòng thử lại.');
            });
    }

    function renderRoomCard() {
        var phong = yeuCau.phong || {};
        var nhaTro = phong.nhaTro || {};
        var chuTro = nhaTro.nguoiDung || {};
        var nguoiThue = yeuCau.nguoiThue || {};
        var doiTac = myRole === 'NGUOI_THUE' ? chuTro : nguoiThue;
        var doiTacLabel = myRole === 'NGUOI_THUE' ? 'Chủ trọ' : 'Người thuê';

        roomCardBox.innerHTML =
            '<div>' +
                '<h2>' + escapeHtml(phong.tenPhong || ('Phòng #' + phong.maPhong)) + '</h2>' +
                '<div class="addr">' + escapeHtml(nhaTro.diaChi || '') + '</div>' +
                '<div class="addr">' + doiTacLabel + ': <b>' + escapeHtml(doiTac.hoTen || '—') + '</b>' +
                    (doiTac.soDienThoai ? ' • ' + escapeHtml(doiTac.soDienThoai) : '') + '</div>' +
            '</div>' +
            '<div>' +
                '<div class="price">' + formatMoney(phong.giaPhong) + '/tháng</div>' +
                '<div class="contact">Yêu cầu #' + yeuCau.maYeuCau + ' • Gửi ' + formatDate(yeuCau.ngayGui) + '</div>' +
            '</div>';
    }

    function computeSteps() {
        var steps = [];
        steps.push({ title: 'Gửi yêu cầu thuê', done: true, sub: 'Đã gửi lúc ' + formatDate(yeuCau.ngayGui) });

        if (yeuCau.trangThai === 'Từ chối') {
            steps.push({ title: 'Chủ trọ xem xét', failed: true, sub: 'Chủ trọ đã từ chối yêu cầu này.' });
            return steps;
        }

        var daDuyet = yeuCau.trangThai === 'Đã duyệt' || !!hopDong;
        steps.push({ title: 'Chủ trọ duyệt yêu cầu', done: daDuyet, current: !daDuyet,
            sub: daDuyet ? 'Đã duyệt' : 'Đang chờ chủ trọ duyệt' });

        var daTaoHopDong = !!hopDong;
        steps.push({ title: 'Tạo hợp đồng điện tử', done: daTaoHopDong, current: daDuyet && !daTaoHopDong,
            sub: daTaoHopDong ? 'Đã tạo hợp đồng #' + hopDong.maHopDong : 'Chờ chủ trọ lập hợp đồng' });

        var chuTroDaKy = !!(hopDong && hopDong.daKyChuTro);
        steps.push({ title: 'Chủ trọ ký hợp đồng', done: chuTroDaKy, current: daTaoHopDong && !chuTroDaKy,
            sub: chuTroDaKy ? 'Đã ký lúc ' + formatDate(hopDong.ngayKyChuTro) : 'Chờ chủ trọ ký (xác thực OTP)' });

        var nguoiThueDaKy = !!(hopDong && hopDong.daKyNguoiThue);
        steps.push({ title: 'Người thuê ký hợp đồng', done: nguoiThueDaKy, current: daTaoHopDong && !nguoiThueDaKy,
            sub: nguoiThueDaKy ? 'Đã ký lúc ' + formatDate(hopDong.ngayKyNguoiThue) : 'Chờ người thuê ký (xác thực OTP)' });

        var hoanTat = !!(hopDong && hopDong.trangThai === 'Hoàn tất');
        steps.push({ title: 'Thanh toán cọc & hoàn tất', done: hoanTat, current: chuTroDaKy && nguoiThueDaKy && !hoanTat,
            sub: hoanTat ? 'Đã hoàn tất, chúc mừng!' : 'Chờ thanh toán tiền cọc' });

        return steps;
    }

    function renderSteps() {
        var steps = computeSteps();
        stepsBox.innerHTML = steps.map(function (s, i) {
            var cls = s.failed ? 'failed' : (s.done ? 'done' : (s.current ? 'current' : ''));
            var icon = s.done ? '✓' : (s.failed ? '✕' : (i + 1));
            return '<div class="step ' + cls + '">' +
                '<div class="step-dot">' + icon + '</div>' +
                '<div class="step-body"><b>' + escapeHtml(s.title) + '</b><span>' + escapeHtml(s.sub) + '</span></div>' +
                '</div>';
        }).join('');
    }

    // ================= ACTION PANEL =================

    function renderActionPanel() {
        actionBox.innerHTML = '';

        if (yeuCau.trangThai === 'Từ chối') {
            actionBox.innerHTML = '<p class="waiting-note">Yêu cầu thuê này đã bị từ chối. Bạn có thể tìm phòng khác phù hợp hơn.</p>';
            return;
        }

        var daDuyet = yeuCau.trangThai === 'Đã duyệt' || !!hopDong;

        if (!daDuyet) {
            if (myRole === 'CHU_TRO') return renderPanelDuyet();
            actionBox.innerHTML = '<p class="waiting-note">Yêu cầu của bạn đang chờ chủ trọ xem xét và duyệt.</p>';
            return;
        }

        if (!hopDong) {
            if (myRole === 'CHU_TRO') return renderPanelTaoHopDong();
            actionBox.innerHTML = '<p class="waiting-note">Chủ trọ đã duyệt yêu cầu. Đang chờ chủ trọ lập hợp đồng điện tử.</p>';
            return;
        }

        if (myRole === 'CHU_TRO' && !hopDong.daKyChuTro) {
            return renderPanelKy('CHU_TRO', 'ky-chu-tro', 'Ký hợp đồng (vai trò Chủ trọ)');
        }
        if (myRole === 'NGUOI_THUE' && !hopDong.daKyNguoiThue) {
            return renderPanelKy('NGUOI_THUE', 'ky-nguoi-thue', 'Ký hợp đồng (vai trò Người thuê)');
        }

        if (!hopDong.daKyChuTro || !hopDong.daKyNguoiThue) {
            actionBox.innerHTML = '<p class="waiting-note">Bạn đã ký. Đang chờ ' +
                (myRole === 'CHU_TRO' ? 'người thuê' : 'chủ trọ') + ' ký hợp đồng.</p>';
            return;
        }

        if (hopDong.trangThai !== 'Hoàn tất') {
            if (myRole === 'NGUOI_THUE') return renderPanelThanhToan();
            actionBox.innerHTML = '<p class="waiting-note">Hợp đồng đã được hai bên ký. Đang chờ người thuê thanh toán tiền cọc.</p>';
            return;
        }

        actionBox.innerHTML = '<p class="success-note">🎉 Giao dịch đã hoàn tất! Hợp đồng #' + hopDong.maHopDong +
            ' đã được ký và thanh toán cọc thành công.</p>';
    }

    function panelError(msg) {
        var el = document.getElementById('panelError');
        if (!el) return;
        el.textContent = msg;
        el.classList.remove('hidden');
    }

    function renderPanelDuyet() {
        actionBox.innerHTML =
            '<h3>Duyệt yêu cầu thuê</h3>' +
            '<p class="waiting-note">Người thuê muốn nhận phòng vào ' + formatDate(yeuCau.ngayMuonNhanPhong) +
            ' • Hình thức: ' + (yeuCau.hinhThucThue === 'NHOM' ? 'Ở ghép (' + (yeuCau.soNguoiCung || '?') + ' người)' : 'Ở một mình') +
            ' • Thời hạn: ' + (yeuCau.thoiHanThue || '?') + ' ' + (yeuCau.donViThoiHan || '') +
            ' • SĐT: ' + escapeHtml(yeuCau.soDienThoaiLienHe || '—') + '</p>' +
            (yeuCau.ghiChu ? '<p class="waiting-note">Ghi chú: ' + escapeHtml(yeuCau.ghiChu) + '</p>' : '') +
            '<div class="action-error hidden" id="panelError"></div>' +
            '<div class="action-row">' +
                '<button class="btn-outline" id="btnTuChoi">Từ chối</button>' +
                '<button class="btn-primary" id="btnDuyet">Duyệt yêu cầu</button>' +
            '</div>';

        document.getElementById('btnDuyet').addEventListener('click', function () {
            var btn = this;
            btn.disabled = true;
            apiFetch('/yeu-cau-thue/' + maYeuCau + '/duyet', { method: 'PUT' })
                .then(function () { loadAll(); })
                .catch(function (err) { panelError((err && err.message) || 'Không duyệt được yêu cầu.'); btn.disabled = false; });
        });

        document.getElementById('btnTuChoi').addEventListener('click', function () {
            if (!confirm('Từ chối yêu cầu thuê này?')) return;
            var btn = this;
            btn.disabled = true;
            apiFetch('/yeu-cau-thue/' + maYeuCau + '/tu-choi', { method: 'PUT' })
                .then(function () { loadAll(); })
                .catch(function (err) { panelError((err && err.message) || 'Không thực hiện được.'); btn.disabled = false; });
        });
    }

    function renderPanelTaoHopDong() {
        var phong = yeuCau.phong || {};

        actionBox.innerHTML =
            '<h3>Lập hợp đồng điện tử</h3>' +
            '<p class="waiting-note">Yêu cầu đã được duyệt. Bấm nút bên dưới để điền đầy đủ thông tin hợp đồng ' +
            '(CCCD, địa chỉ thường trú hai bên, chính sách gia hạn...) và xem trước nội dung hợp đồng trước khi tạo.</p>' +
            '<div class="action-error hidden" id="panelError"></div>' +
            '<div class="action-row"><button class="btn-primary" id="btnMoModalTaoHopDong" style="flex:none;padding-left:24px;padding-right:24px;">' +
                '<i class="bi bi-file-earmark-plus"></i> Tạo hợp đồng</button></div>';

        document.getElementById('btnMoModalTaoHopDong').addEventListener('click', function () {
            openHopDongModal({
                mode: 'fixed',
                currentUser: currentUser,
                yeuCau: yeuCau,
                onSuccess: function () { loadAll(); }
            });
        });
    }

    function renderPanelKy(vaiTroKy, apiSuffix, tieuDe) {
        actionBox.innerHTML =
            '<h3>' + tieuDe + '</h3>' +
            '<p class="waiting-note">Hợp đồng #' + hopDong.maHopDong + ' • ' + formatDate(hopDong.ngayBatDau) +
            ' → ' + formatDate(hopDong.ngayKetThuc) + ' • Giá thuê ' + formatMoney(hopDong.giaThue) +
            '/tháng • Cọc ' + formatMoney(hopDong.tienCoc) + '</p>' +
            '<p class="waiting-note">Bấm nút bên dưới để xem lại toàn bộ nội dung hợp đồng, tạo chữ ký điện tử ' +
            '(vẽ tay hoặc gõ tên) và xác thực bằng mã OTP gửi qua SĐT/Email trước khi hoàn tất ký.</p>' +
            '<div class="action-error hidden" id="panelError"></div>' +
            '<div class="action-row"><button class="btn-primary" id="btnMoModalKy" style="flex:none;padding-left:24px;padding-right:24px;">' +
                '<i class="bi bi-pen"></i> Xem hợp đồng &amp; Ký</button></div>';

        document.getElementById('btnMoModalKy').addEventListener('click', function () {
            openHopDongKyModal({
                hopDong: hopDong,
                yeuCau: yeuCau,
                vaiTroKy: vaiTroKy,
                currentUser: currentUser,
                onSuccess: function () { loadAll(); }
            });
        });
    }

    function renderPanelThanhToan() {
        actionBox.innerHTML =
            '<h3>Thanh toán tiền cọc</h3>' +
            '<p class="waiting-note">Hợp đồng đã được cả hai bên ký. Số tiền cọc cần thanh toán: <b>' +
            formatMoney(hopDong.tienCoc) + '</b>.</p>' +
            '<div class="action-error hidden" id="panelError"></div>' +
            '<div class="action-row">' +
                '<button class="btn-primary" id="btnThanhToan">Thanh toán cọc (mô phỏng chuyển khoản)</button>' +
            '</div>';

        document.getElementById('btnThanhToan').addEventListener('click', function () {
            var btn = this;
            btn.disabled = true;
            btn.textContent = 'Đang xử lý...';
            apiFetch('/hop-dong/' + hopDong.maHopDong + '/thanh-toan', {
                method: 'PUT',
                body: { phuongThuc: 'Chuyển khoản (mô phỏng)' }
            })
                .then(function () { loadAll(); })
                .catch(function (err) {
                    panelError((err && err.message) || 'Thanh toán không thành công.');
                    btn.disabled = false;
                    btn.textContent = 'Thanh toán cọc (mô phỏng chuyển khoản)';
                });
        });
    }

    document.getElementById('retryBtn').addEventListener('click', loadAll);

    loadAll();
})();
</script>

</body>
</html>