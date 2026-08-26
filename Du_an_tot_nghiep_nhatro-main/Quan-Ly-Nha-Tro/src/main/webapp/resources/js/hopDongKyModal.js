/**
 * Modal "Ký hợp đồng" dùng chung (trang Tiến trình đặt phòng, cả vai trò
 * Chủ trọ lẫn Người thuê).
 *
 * Bố cục 2 cột giống ứng dụng ký số thật:
 *   - Cột trái: xem lại toàn bộ nội dung hợp đồng (kèm trạng thái chữ ký 2 bên).
 *   - Cột phải: tạo chữ ký điện tử (vẽ tay hoặc gõ tên) rồi xác thực bằng mã OTP
 *     (gửi qua SĐT hoặc Email) trước khi hoàn tất bước ký.
 *
 * Cách dùng:
 *   <script src=".../resources/js/api.js"></script>
 *   <script src=".../resources/js/hopDongKyModal.js"></script>
 *
 *   openHopDongKyModal({
 *       hopDong: hopDong,        // object hợp đồng lấy từ GET /hop-dong/theo-yeu-cau/{id}
 *       yeuCau: yeuCau,          // object yêu cầu thuê đầy đủ (có phong.nhaTro.nguoiDung, nguoiThue...)
 *       vaiTroKy: 'CHU_TRO' | 'NGUOI_THUE',
 *       currentUser: currentUser,
 *       onSuccess: function () { ... }   // gọi sau khi ký (đã xác thực OTP) thành công
 *   });
 *
 * Sau khi MỘT bên ký xong, trang gọi lại onSuccess() để tải lại tiến trình;
 * chỉ khi CẢ HAI bên (daKyChuTro && daKyNguoiThue) đều đã ký thì trang mới
 * chuyển sang bước "Thanh toán cọc" (logic này nằm ở tienTrinhDatPhong.jsp).
 */
(function (global) {
    'use strict';

    if (global.openHopDongKyModal) return; // đã nạp rồi

    var STYLE_ID = 'hopDongKyModalStyles';
    var injected = false;

    var modal, previewEl, signPane, panelErrorEl;
    var opts = null;
    var step = 'sign'; // 'sign' | 'otp'
    var signMode = 'draw'; // 'draw' | 'type'
    var hasSignature = false;
    var otpSent = false;
    var kenhGui = 'EMAIL';

    var canvas, ctx, drawing = false, lastX = 0, lastY = 0;

    /* ================= CSS (tự chèn 1 lần) ================= */
    var CSS = ''
        + 'body.hky-modal-open{overflow:hidden;}'
        + '.hky-modal{position:fixed;inset:0;z-index:1100;display:none;align-items:center;justify-content:center;padding:18px;font-family:"Inter",Arial,sans-serif;}'
        + '.hky-modal.show{display:flex;}'
        + '.hky-backdrop{position:absolute;inset:0;background:rgba(2,6,23,.76);backdrop-filter:blur(5px);}'
        + '.hky-dialog{position:relative;z-index:1;width:min(1180px,97vw);max-height:calc(100vh - 36px);overflow:hidden;display:flex;flex-direction:column;background:#101827;border:1px solid #2b3850;border-radius:18px;box-shadow:0 28px 80px rgba(0,0,0,.5);animation:hkyIn .18s ease-out;}'
        + '@keyframes hkyIn{from{opacity:0;transform:translateY(10px) scale(.985);}to{opacity:1;transform:translateY(0) scale(1);}}'
        + '.hky-head{display:flex;align-items:flex-start;justify-content:space-between;gap:18px;padding:18px 22px 14px;border-bottom:1px solid #1f2b40;}'
        + '.hky-head h2{margin:4px 0 0;color:#f8fafc;font-size:19px;}'
        + '.hky-head p{margin:5px 0 0;color:#64748b;font-size:11px;}'
        + '.hky-kicker{color:#818cf8;font-size:9px;font-weight:800;letter-spacing:.09em;}'
        + '.hky-close{width:34px;height:34px;border:1px solid #2b3850;border-radius:9px;background:#0f172a;color:#94a3b8;cursor:pointer;display:grid;place-items:center;flex:none;}'
        + '.hky-close:hover{color:#fff;border-color:#6366f1;}'
        + '.hky-body{display:grid;grid-template-columns:1.15fr 1fr;gap:0;overflow:hidden;flex:1;min-height:0;}'
        + '.hky-pane{overflow-y:auto;min-height:0;}'
        + '.hky-pane-preview{padding:22px 24px;background:#0b1220;border-right:1px solid #1f2b40;}'
        + '.hky-preview{background:#fff;color:#1e293b;border-radius:10px;padding:26px 28px;font-size:12px;line-height:1.7;box-shadow:0 10px 30px rgba(0,0,0,.35);}'
        + '.hky-preview h1{text-align:center;font-size:15px;margin:0 0 4px;}'
        + '.hky-preview .hky-quochieu{text-align:center;font-weight:700;font-size:12px;}'
        + '.hky-preview .hky-tieungu{text-align:center;font-size:11px;margin-bottom:16px;}'
        + '.hky-preview .hky-tieungu::after{content:"";display:block;width:90px;border-bottom:1px solid #1e293b;margin:3px auto 0;}'
        + '.hky-preview h3{font-size:12.5px;margin:14px 0 6px;}'
        + '.hky-preview p{margin:0 0 8px;text-align:justify;}'
        + '.hky-preview ul{margin:0 0 8px 18px;padding:0;}'
        + '.hky-preview li{margin-bottom:4px;text-align:justify;}'
        + '.hky-sign-table{width:100%;border-collapse:collapse;margin-top:20px;}'
        + '.hky-sign-table td{width:50%;text-align:center;vertical-align:top;padding:12px 10px;border:1px solid #cbd5e1;font-weight:700;font-size:11.5px;}'
        + '.hky-sign-table .hky-sign-status{font-weight:400;color:#64748b;margin-top:8px;font-size:10.5px;}'
        + '.hky-sign-table img{max-height:56px;max-width:100%;margin-top:8px;}'
        + '.hky-sign-table .hky-cursive{font-family:"Segoe Script","Brush Script MT",cursive;font-size:22px;color:#1e293b;margin-top:10px;}'
        + '.hky-sign-table .hky-pending{color:#c2410c;font-weight:400;font-size:10.5px;}'
        + '.hky-pane-sign{padding:20px 22px;display:flex;flex-direction:column;}'
        + '.hky-sign-title{color:#f8fafc;font-size:13px;font-weight:800;margin-bottom:2px;}'
        + '.hky-sign-sub{color:#64748b;font-size:11px;margin-bottom:14px;}'
        + '.hky-summary{background:rgba(99,102,241,.08);border:1px solid rgba(99,102,241,.18);border-radius:9px;padding:10px 12px;color:#a5b4fc;font-size:10.5px;line-height:1.6;margin-bottom:16px;}'
        + '.hky-tabs{display:flex;gap:6px;background:#0b1220;border:1px solid #2b3850;border-radius:10px;padding:4px;margin-bottom:14px;}'
        + '.hky-tab{flex:1;border:0;background:transparent;color:#94a3b8;font-size:11px;font-weight:700;padding:8px 10px;border-radius:7px;cursor:pointer;display:inline-flex;align-items:center;justify-content:center;gap:6px;}'
        + '.hky-tab.active{background:linear-gradient(135deg,#6366f1,#8b5cf6);color:#fff;}'
        + '.hky-label{display:block;color:#cbd5e1;font-size:10.5px;font-weight:700;margin:0 0 6px;}'
        + '.hky-input{width:100%;padding:10px 11px;border:1px solid #2b3850;border-radius:9px;background:#0b1220;color:#e2e8f0;font:inherit;font-size:12px;outline:none;margin-bottom:12px;}'
        + '.hky-input:focus{border-color:#6366f1;box-shadow:0 0 0 3px rgba(99,102,241,.12);}'
        + '.hky-canvas-row{display:flex;justify-content:space-between;align-items:center;margin-bottom:8px;}'
        + '.hky-mini-btn{border:1px solid #2b3850;background:#1e293b;color:#cbd5e1;font-size:10.5px;font-weight:700;padding:6px 10px;border-radius:7px;cursor:pointer;display:inline-flex;align-items:center;gap:5px;}'
        + '.hky-mini-btn:hover{border-color:#6366f1;color:#fff;}'
        + '.hky-canvas-wrap{border:1px dashed #334155;border-radius:10px;background:#fff;overflow:hidden;touch-action:none;}'
        + '.hky-canvas-wrap.disabled{opacity:.55;}'
        + '#hkyCanvas{width:100%;height:190px;display:block;cursor:crosshair;}'
        + '.hky-hint{color:#64748b;font-size:9.5px;margin-top:6px;}'
        + '.hky-checkline{display:flex;align-items:flex-start;gap:9px;color:#cbd5e1;font-size:11px;font-weight:600;margin-top:16px;line-height:1.5;}'
        + '.hky-checkline input{width:16px;height:16px;accent-color:#6366f1;margin-top:2px;flex:none;}'
        + '.hky-otp-methods{display:flex;flex-direction:column;gap:8px;margin-bottom:14px;}'
        + '.hky-otp-method{display:flex;align-items:center;gap:9px;border:1px solid #2b3850;border-radius:9px;padding:10px 12px;cursor:pointer;color:#cbd5e1;font-size:11.5px;font-weight:600;}'
        + '.hky-otp-method.active{border-color:#6366f1;background:rgba(99,102,241,.08);color:#fff;}'
        + '.hky-otp-method input{accent-color:#6366f1;}'
        + '.hky-otp-method small{display:block;color:#64748b;font-weight:400;font-size:10px;margin-top:2px;}'
        + '.hky-otp-method.disabled{opacity:.45;pointer-events:none;}'
        + '.hky-demo-box{background:#3a2f06;border:1px dashed #eab308;border-radius:8px;padding:9px 11px;font-size:10.5px;color:#fde68a;margin-bottom:12px;}'
        + '.hky-note-box{background:#0b1220;border:1px solid #334155;border-radius:8px;padding:9px 11px;font-size:10px;color:#94a3b8;margin-bottom:12px;}'
        + '.hky-error{background:rgba(239,68,68,.1);border:1px solid rgba(239,68,68,.25);color:#fca5a5;border-radius:8px;padding:9px 11px;font-size:11px;margin-top:12px;}'
        + '.hky-otp-input{letter-spacing:6px;font-weight:800;font-size:16px;text-align:center;}'
        + '.hky-actions{margin-top:auto;display:flex;justify-content:flex-end;gap:9px;padding-top:16px;}'
        + '.hky-btn{border:0;border-radius:9px;padding:10px 16px;font-size:12px;font-weight:800;cursor:pointer;display:inline-flex;align-items:center;gap:7px;}'
        + '.hky-btn.light{background:#1e293b;color:#cbd5e1;border:1px solid #334155;}'
        + '.hky-btn.primary{background:linear-gradient(135deg,#6366f1,#8b5cf6);color:#fff;box-shadow:0 8px 18px rgba(99,102,241,.25);}'
        + '.hky-btn:disabled{opacity:.5;cursor:not-allowed;}'
        + '.hky-back-link{background:none;border:0;color:#818cf8;font-size:10.5px;font-weight:700;cursor:pointer;padding:0;margin-bottom:12px;display:inline-flex;align-items:center;gap:5px;}'
        + '@media (max-width:860px){.hky-body{grid-template-columns:1fr;overflow-y:auto;}.hky-pane-preview{border-right:0;border-bottom:1px solid #1f2b40;}}';

    /* ================= HTML ================= */
    var HTML = ''
        + '<div class="hky-modal" id="hkyModal" aria-hidden="true">'
        + '  <div class="hky-backdrop" data-hky-close></div>'
        + '  <div class="hky-dialog" role="dialog" aria-modal="true">'
        + '    <div class="hky-head">'
        + '      <div>'
        + '        <span class="hky-kicker"><i class="bi bi-pen"></i> KÝ HỢP ĐỒNG</span>'
        + '        <h2 id="hkyTitle">Ký hợp đồng</h2>'
        + '        <p id="hkySub">Kiểm tra nội dung, tạo chữ ký rồi xác nhận ký.</p>'
        + '      </div>'
        + '      <button type="button" class="hky-close" data-hky-close aria-label="Đóng"><i class="bi bi-x-lg"></i></button>'
        + '    </div>'
        + '    <div class="hky-body">'
        + '      <div class="hky-pane hky-pane-preview"><div class="hky-preview" id="hkyPreview"></div></div>'
        + '      <div class="hky-pane hky-pane-sign" id="hkySignPane"></div>'
        + '    </div>'
        + '  </div>'
        + '</div>';

    /* ================= helpers ================= */
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
        return d.toLocaleDateString('vi-VN');
    }
    function formatDateTime(v) {
        if (!v) return '—';
        var d = new Date(v);
        if (isNaN(d.getTime())) return v;
        return d.toLocaleDateString('vi-VN') + ' ' + d.toLocaleTimeString('vi-VN', { hour: '2-digit', minute: '2-digit' });
    }
    function maskEmail(email) {
        if (!email || email.indexOf('@') === -1) return email || '—';
        var parts = email.split('@');
        var ten = parts[0];
        var hien = ten.length <= 2 ? ten.charAt(0) + '*' : ten.substring(0, 2) + '***';
        return hien + '@' + parts[1];
    }
    function maskSdt(sdt) {
        if (!sdt || sdt.length < 4) return sdt || '—';
        return '*****' + sdt.substring(sdt.length - 3);
    }

    /* ================= inject (1 lần) ================= */
    function injectOnce() {
        if (injected) return;
        injected = true;

        var style = document.createElement('style');
        style.id = STYLE_ID;
        style.textContent = CSS;
        document.head.appendChild(style);

        var wrap = document.createElement('div');
        wrap.innerHTML = HTML;
        document.body.appendChild(wrap.firstElementChild);

        modal = document.getElementById('hkyModal');
        previewEl = document.getElementById('hkyPreview');
        signPane = document.getElementById('hkySignPane');

        modal.addEventListener('click', function (e) {
            if (e.target && e.target.hasAttribute('data-hky-close')) closeModal();
        });
        document.addEventListener('keydown', function (e) {
            if (e.key === 'Escape' && modal.classList.contains('show')) closeModal();
        });
    }

    function openModal() {
        modal.classList.add('show');
        modal.setAttribute('aria-hidden', 'false');
        document.body.classList.add('hky-modal-open');
    }
    function closeModal() {
        modal.classList.remove('show');
        modal.setAttribute('aria-hidden', 'true');
        document.body.classList.remove('hky-modal-open');
    }

    /* ================= xây nội dung hợp đồng (xem lại) ================= */
    function currentData() {
        var hd = opts.hopDong || {};
        var yc = opts.yeuCau || {};
        var phong = yc.phong || {};
        var nhaTro = phong.nhaTro || {};
        var chuTro = nhaTro.nguoiDung || {};
        var nguoiThue = yc.nguoiThue || {};

        return {
            diaDiemKy: hd.diaDiemKy || '..........',
            ngayKy: hd.ngayKy || hd.ngayBatDau,
            tenChuNha: chuTro.hoTen || '..........', sdtChuNha: chuTro.soDienThoai || '..........',
            cccdChuNha: hd.cccdChuTro || '..........', ngayCapChuNha: hd.ngayCapCccdChuTro,
            noiCapChuNha: hd.noiCapCccdChuTro || '..........', diaChiChuNha: hd.diaChiThuongTruChuTro || '..........',
            tenNguoiThue: nguoiThue.hoTen || '..........', sdtNguoiThue: nguoiThue.soDienThoai || '..........',
            cccdNguoiThue: hd.cccdNguoiThue || '..........', ngayCapNguoiThue: hd.ngayCapCccdNguoiThue,
            noiCapNguoiThue: hd.noiCapCccdNguoiThue || '..........', diaChiNguoiThue: hd.diaChiThuongTruNguoiThue || '..........',
            tenNhaTro: nhaTro.tenNhaTro || '..........', tenPhong: phong.tenPhong || '..........',
            diaChiNhaTro: nhaTro.diaChi || '',
            batDau: hd.ngayBatDau, ketThuc: hd.ngayKetThuc,
            giaThue: hd.giaThue || 0, tienCoc: hd.tienCoc || 0,
            choGiaHan: hd.choPhepGiaHan,
            soNgayBaoTruoc: hd.soNgayBaoTruocGiaHan || 30, soLanGiaHan: hd.soLanGiaHanToiDa || 3,
            mucTangGia: hd.mucTangGiaToiDaPhanTram || 10
        };
    }

    function signCellHtml(daKy, ten, chuKy, ngayKy) {
        if (!daKy) {
            return '<div class="hky-sign-status hky-pending"><i class="bi bi-hourglass-split"></i> Chưa ký</div>';
        }
        var chuKyHtml;
        if (chuKy && chuKy.indexOf('data:image') === 0) {
            chuKyHtml = '<img src="' + chuKy + '" alt="Chữ ký">';
        } else {
            chuKyHtml = '<div class="hky-cursive">' + escapeHtml(chuKy || ten || '') + '</div>';
        }
        return chuKyHtml +
            '<div class="hky-sign-status"><i class="bi bi-check-circle-fill" style="color:#16a34a;"></i> Đã ký lúc ' + formatDateTime(ngayKy) + '</div>';
    }

    function renderPreview() {
        var d = currentData();
        var hd = opts.hopDong || {};

        previewEl.innerHTML =
            '<div class="hky-quochieu">CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</div>' +
            '<div class="hky-tieungu">Độc lập - Tự do - Hạnh phúc</div>' +
            '<h1>HỢP ĐỒNG THUÊ NHÀ</h1>' +
            '<p style="text-align:center;">' + escapeHtml(d.diaDiemKy) + ', ngày ' + formatDate(d.ngayKy) + '</p>' +

            '<h3>BÊN CHO THUÊ (BÊN A)</h3>' +
            '<p>Ông/Bà: <b>' + escapeHtml(d.tenChuNha) + '</b> — Điện thoại: ' + escapeHtml(d.sdtChuNha) + '<br>' +
            'CMND/CCCD số: ' + escapeHtml(d.cccdChuNha) + (d.ngayCapChuNha ? ', cấp ngày ' + formatDate(d.ngayCapChuNha) : '') + (d.noiCapChuNha ? ', tại ' + escapeHtml(d.noiCapChuNha) : '') + '<br>' +
            'Địa chỉ thường trú: ' + escapeHtml(d.diaChiChuNha) + '</p>' +

            '<h3>BÊN THUÊ (BÊN B)</h3>' +
            '<p>Ông/Bà: <b>' + escapeHtml(d.tenNguoiThue) + '</b> — Điện thoại: ' + escapeHtml(d.sdtNguoiThue) + '<br>' +
            'CMND/CCCD số: ' + escapeHtml(d.cccdNguoiThue) + (d.ngayCapNguoiThue ? ', cấp ngày ' + formatDate(d.ngayCapNguoiThue) : '') + (d.noiCapNguoiThue ? ', tại ' + escapeHtml(d.noiCapNguoiThue) : '') + '<br>' +
            'Địa chỉ thường trú: ' + escapeHtml(d.diaChiNguoiThue) + '</p>' +

            '<h3>Điều 1. Đối tượng và mục đích thuê</h3>' +
            '<p>Bên A đồng ý cho Bên B thuê phòng <b>' + escapeHtml(d.tenPhong) + '</b> thuộc nhà trọ <b>' + escapeHtml(d.tenNhaTro) + '</b>' +
            (d.diaChiNhaTro ? (', địa chỉ: ' + escapeHtml(d.diaChiNhaTro)) : '') + '. Bên B sử dụng phòng thuê đúng mục đích để ở, không được sử dụng vào mục đích khác nếu chưa có sự đồng ý bằng văn bản của Bên A.</p>' +

            '<h3>Điều 2. Thời hạn thuê</h3>' +
            '<p>Thời hạn thuê từ ngày <b>' + formatDate(d.batDau) + '</b> đến ngày <b>' + formatDate(d.ketThuc) + '</b>. Hết thời hạn này, nếu Bên B có nhu cầu tiếp tục thuê thì thực hiện theo chính sách gia hạn tại Điều 5.</p>' +

            '<h3>Điều 3. Giá thuê và phương thức thanh toán</h3>' +
            '<p>Giá thuê phòng là <b>' + formatMoney(d.giaThue) + '</b>/tháng, chưa bao gồm tiền điện, nước, gửi xe, internet (tính theo thực tế sử dụng hàng tháng). Bên B thanh toán tiền thuê hàng tháng cho Bên A trước ngày 05 của tháng, bằng tiền mặt hoặc chuyển khoản.</p>' +

            '<h3>Điều 4. Tiền đặt cọc</h3>' +
            '<p>Bên B đặt cọc cho Bên A số tiền <b>' + formatMoney(d.tienCoc) + '</b> để đảm bảo thực hiện hợp đồng. Tiền cọc được hoàn trả lại cho Bên B khi kết thúc hợp đồng, sau khi đã trừ các khoản chi phí phát sinh (nếu có) do lỗi của Bên B.</p>' +

            '<h3>Điều 5. Chính sách gia hạn sau khi hết hạn hợp đồng</h3>' +
            '<ul>' +
            '<li>5.1. Việc gia hạn sau khi hết thời hạn hợp đồng được áp dụng nếu: <b>' + (d.choGiaHan ? '☑' : '☐') + '</b> .</li>' +
            '<li>5.2. Bên B phải thông báo bằng văn bản cho Bên A về nhu cầu gia hạn trước ít nhất <u>' + escapeHtml(d.soNgayBaoTruoc) + '</u> ngày so với ngày hết hạn hợp đồng.</li>' +
            '<li>5.3. Số lần gia hạn tối đa là <u>' + escapeHtml(d.soLanGiaHan) + '</u> lần, trừ khi Hai Bên có thỏa thuận khác bằng văn bản.</li>' +
            '<li>5.4. Mức tăng giá thuê tối đa cho mỗi lần gia hạn không vượt quá <u>' + escapeHtml(d.mucTangGia) + '</u>% so với giá thuê liền kề trước đó.</li>' +
            '</ul>' +

            '<h3>Điều 6. Điều khoản chung</h3>' +
            '<p>Hợp đồng này có hiệu lực kể từ thời điểm cả hai bên hoàn tất ký điện tử (có xác thực OTP) và được lập thành bản điện tử duy nhất, lưu trữ trên hệ thống Room Connect, có giá trị pháp lý như bản giấy đối với hai bên.</p>' +

            '<table class="hky-sign-table"><tr>' +
            '<td>BÊN CHO THUÊ (BÊN A)' + signCellHtml(hd.daKyChuTro, d.tenChuNha, hd.chuKyChuTro, hd.ngayKyChuTro) + '</td>' +
            '<td>BÊN THUÊ (BÊN B)' + signCellHtml(hd.daKyNguoiThue, d.tenNguoiThue, hd.chuKyNguoiThue, hd.ngayKyNguoiThue) + '</td>' +
            '</tr></table>';
    }

    /* ================= chữ ký (canvas) ================= */
    function setupCanvas() {
        canvas = document.getElementById('hkyCanvas');
        if (!canvas) return;
        var wrapEl = canvas.parentElement;
        var ratio = window.devicePixelRatio || 1;
        var w = wrapEl.clientWidth || 420;
        var h = 190;
        canvas.width = w * ratio;
        canvas.height = h * ratio;
        ctx = canvas.getContext('2d');
        ctx.scale(ratio, ratio);
        ctx.lineWidth = 2.4;
        ctx.lineCap = 'round';
        ctx.lineJoin = 'round';
        ctx.strokeStyle = '#1e293b';

        function pos(e) {
            var rect = canvas.getBoundingClientRect();
            var cx = (e.touches ? e.touches[0].clientX : e.clientX) - rect.left;
            var cy = (e.touches ? e.touches[0].clientY : e.clientY) - rect.top;
            return { x: cx, y: cy };
        }
        function start(e) {
            if (signMode !== 'draw') return;
            e.preventDefault();
            drawing = true;
            var p = pos(e);
            lastX = p.x; lastY = p.y;
        }
        function move(e) {
            if (!drawing || signMode !== 'draw') return;
            e.preventDefault();
            var p = pos(e);
            ctx.beginPath();
            ctx.moveTo(lastX, lastY);
            ctx.lineTo(p.x, p.y);
            ctx.stroke();
            lastX = p.x; lastY = p.y;
            hasSignature = true;
            updateSignButtonState();
        }
        function end() { drawing = false; }

        canvas.addEventListener('pointerdown', start);
        canvas.addEventListener('pointermove', move);
        window.addEventListener('pointerup', end);
        canvas.addEventListener('touchstart', start, { passive: false });
        canvas.addEventListener('touchmove', move, { passive: false });
        canvas.addEventListener('touchend', end);
    }

    function clearCanvas() {
        if (!ctx || !canvas) return;
        var ratio = window.devicePixelRatio || 1;
        ctx.clearRect(0, 0, canvas.width / ratio, canvas.height / ratio);
        hasSignature = false;
        updateSignButtonState();
    }

    function drawTextSignature(text) {
        if (!ctx || !canvas) return;
        var ratio = window.devicePixelRatio || 1;
        var w = canvas.width / ratio, h = canvas.height / ratio;
        ctx.clearRect(0, 0, w, h);
        if (!text) { hasSignature = false; updateSignButtonState(); return; }
        ctx.save();
        ctx.font = '600 40px "Segoe Script","Brush Script MT",cursive';
        ctx.fillStyle = '#1e293b';
        ctx.textBaseline = 'middle';
        var tw = ctx.measureText(text).width;
        var scale = Math.min(1, (w - 40) / Math.max(tw, 1));
        ctx.translate(w / 2, h / 2);
        ctx.scale(scale, scale);
        ctx.textAlign = 'center';
        ctx.fillText(text, 0, 0);
        ctx.restore();
        hasSignature = true;
        updateSignButtonState();
    }

    function updateSignButtonState() {
        var btn = document.getElementById('hkyBtnContinue');
        if (!btn) return;
        var name = (document.getElementById('hkyHoTen') || {}).value || '';
        var agree = (document.getElementById('hkyAgree') || {}).checked;
        btn.disabled = !(hasSignature && name.trim() && agree);
    }

    /* ================= vẽ panel chữ ký (step 'sign') ================= */
    function renderSignStep() {
        var hd = opts.hopDong || {};
        signPane.innerHTML =
            '<div class="hky-sign-title">Tạo chữ ký điện tử</div>' +
            '<div class="hky-sign-sub">Vẽ hoặc gõ chữ ký, sau đó xác nhận qua mã OTP để hoàn tất bước ký.</div>' +
            '<div class="hky-summary">Hợp đồng #' + escapeHtml(hd.maHopDong) + ' • ' + formatDate(hd.ngayBatDau) +
            ' → ' + formatDate(hd.ngayKetThuc) + ' • Giá thuê ' + formatMoney(hd.giaThue) + '/tháng • Cọc ' + formatMoney(hd.tienCoc) + '</div>' +

            '<div class="hky-tabs">' +
            '  <button type="button" class="hky-tab' + (signMode === 'draw' ? ' active' : '') + '" data-mode="draw"><i class="bi bi-pen"></i> Vẽ chữ ký</button>' +
            '  <button type="button" class="hky-tab' + (signMode === 'type' ? ' active' : '') + '" data-mode="type"><i class="bi bi-keyboard"></i> Gõ chữ ký</button>' +
            '</div>' +

            '<label class="hky-label" for="hkyHoTen">Họ và tên (hiển thị dưới chữ ký)</label>' +
            '<input class="hky-input" id="hkyHoTen" placeholder="Nhập họ và tên của bạn" value="' + escapeHtml((opts.currentUser && opts.currentUser.hoTen) || '') + '">' +

            (signMode === 'type'
                ? '<label class="hky-label" for="hkyTypedSign">Nội dung chữ ký</label>' +
                  '<input class="hky-input" id="hkyTypedSign" placeholder="Nhập tên bạn muốn hiển thị làm chữ ký">'
                : '') +

            '<div class="hky-canvas-row">' +
            '  <span class="hky-label" style="margin:0;">Chữ ký</span>' +
            '  <span style="display:flex;gap:8px;">' +
            (signMode === 'draw' ? '<button type="button" class="hky-mini-btn" id="hkyBtnFromName"><i class="bi bi-magic"></i> Tạo chữ ký từ tên</button>' : '') +
            '    <button type="button" class="hky-mini-btn" id="hkyBtnClear"><i class="bi bi-arrow-counterclockwise"></i> Xoá</button>' +
            '  </span>' +
            '</div>' +
            '<div class="hky-canvas-wrap' + (signMode === 'type' ? ' disabled' : '') + '"><canvas id="hkyCanvas"></canvas></div>' +
            '<div class="hky-hint">Ký trực tiếp bằng chuột/trackpad/touch. Trên mobile có thể dùng ngón tay.</div>' +

            '<label class="hky-checkline"><input type="checkbox" id="hkyAgree"><span>Tôi xác nhận đồng ý ký hợp đồng và chịu trách nhiệm với chữ ký điện tử này.</span></label>' +

            '<div class="hky-error" id="hkyPanelError" style="display:none;"></div>' +

            '<div class="hky-actions">' +
            '  <button type="button" class="hky-btn light" data-hky-close>Đóng</button>' +
            '  <button type="button" class="hky-btn primary" id="hkyBtnContinue" disabled><i class="bi bi-arrow-right-circle"></i> Tiếp tục xác thực OTP</button>' +
            '</div>';

        setupCanvas();
        hasSignature = false;

        signPane.querySelectorAll('.hky-tab').forEach(function (tab) {
            tab.addEventListener('click', function () {
                signMode = tab.getAttribute('data-mode');
                renderSignStep();
            });
        });

        var hoTenInput = document.getElementById('hkyHoTen');
        hoTenInput.addEventListener('input', function () {
            updateSignButtonState();
            if (signMode === 'type') {
                var typedInput = document.getElementById('hkyTypedSign');
                if (typedInput && !typedInput.value) drawTextSignature(hoTenInput.value.trim());
            }
        });

        if (signMode === 'type') {
            var typedInput = document.getElementById('hkyTypedSign');
            typedInput.value = hoTenInput.value;
            drawTextSignature(typedInput.value.trim());
            typedInput.addEventListener('input', function () {
                drawTextSignature(typedInput.value.trim());
            });
        }

        var btnFromName = document.getElementById('hkyBtnFromName');
        if (btnFromName) {
            btnFromName.addEventListener('click', function () {
                drawTextSignature(hoTenInput.value.trim());
            });
        }
        document.getElementById('hkyBtnClear').addEventListener('click', clearCanvas);
        document.getElementById('hkyAgree').addEventListener('change', updateSignButtonState);

        document.getElementById('hkyBtnContinue').addEventListener('click', function () {
            if (!hasSignature) { showPanelError('Vui lòng tạo chữ ký (vẽ tay hoặc gõ tên) trước khi tiếp tục.'); return; }
            if (!hoTenInput.value.trim()) { showPanelError('Vui lòng nhập họ tên hiển thị dưới chữ ký.'); return; }
            if (!document.getElementById('hkyAgree').checked) { showPanelError('Vui lòng xác nhận đồng ý ký hợp đồng.'); return; }
            step = 'otp';
            otpSent = false;
            renderOtpStep();
        });
    }

    function showPanelError(msg) {
        var el = document.getElementById('hkyPanelError');
        if (!el) return;
        el.textContent = msg;
        el.style.display = '';
    }

    /* ================= panel OTP (step 'otp') ================= */
    function renderOtpStep() {
        var hd = opts.hopDong || {};
        var yc = opts.yeuCau || {};
        var chuTro = (yc.phong && yc.phong.nhaTro) ? yc.phong.nhaTro.nguoiDung : null;
        var nguoiKy = opts.vaiTroKy === 'CHU_TRO' ? chuTro : yc.nguoiThue;
        var email = (nguoiKy && nguoiKy.email) || (opts.currentUser && opts.currentUser.email) || '';
        var sdt = (nguoiKy && nguoiKy.soDienThoai) || (opts.currentUser && opts.currentUser.soDienThoai) || '';

        signPane.innerHTML =
            '<button type="button" class="hky-back-link" id="hkyBackToSign"><i class="bi bi-arrow-left"></i> Quay lại chỉnh chữ ký</button>' +
            '<div class="hky-sign-title">Xác thực mã OTP</div>' +
            '<div class="hky-sign-sub">Chọn kênh nhận mã, sau đó nhập mã OTP để hoàn tất ký hợp đồng.</div>' +

            '<div class="hky-otp-methods" id="hkyOtpMethods">' +
            '  <label class="hky-otp-method' + (kenhGui === 'SDT' ? ' active' : '') + (sdt ? '' : ' disabled') + '" data-method-label="SDT">' +
            '    <input type="radio" name="hkyKenh" value="SDT"' + (kenhGui === 'SDT' ? ' checked' : '') + (sdt ? '' : ' disabled') + '>' +
            '    <span><i class="bi bi-phone"></i> Xác thực qua SĐT<small>' + escapeHtml(maskSdt(sdt)) + '</small></span>' +
            '  </label>' +
            '  <label class="hky-otp-method' + (kenhGui === 'EMAIL' ? ' active' : '') + '" data-method-label="EMAIL">' +
            '    <input type="radio" name="hkyKenh" value="EMAIL"' + (kenhGui === 'EMAIL' ? ' checked' : '') + '>' +
            '    <span><i class="bi bi-envelope"></i> Xác thực qua Email<small>' + escapeHtml(maskEmail(email)) + '</small></span>' +
            '  </label>' +
            '</div>' +

            '<div id="hkyOtpSendArea">' +
            '  <div class="hky-actions" style="padding-top:0;justify-content:flex-start;">' +
            '    <button type="button" class="hky-btn primary" id="hkyBtnGuiOtp"><i class="bi bi-send"></i> Gửi mã OTP</button>' +
            '  </div>' +
            '</div>' +

            '<div id="hkyOtpVerifyArea" style="display:none;">' +
            '  <div class="hky-note-box" id="hkyNoteBox" style="display:none;"></div>' +
            '  <div class="hky-demo-box" id="hkyDemoBox" style="display:none;"></div>' +
            '  <label class="hky-label" for="hkyOtpCode">Mã OTP (6 số)</label>' +
            '  <input class="hky-input hky-otp-input" id="hkyOtpCode" maxlength="6" inputmode="numeric" placeholder="------">' +
            '  <button type="button" class="hky-mini-btn" id="hkyBtnResend"><i class="bi bi-arrow-counterclockwise"></i> Gửi lại mã</button>' +
            '</div>' +

            '<div class="hky-error" id="hkyPanelError" style="display:none;"></div>' +

            '<div class="hky-actions">' +
            '  <button type="button" class="hky-btn light" data-hky-close>Đóng</button>' +
            '  <button type="button" class="hky-btn primary" id="hkyBtnXacNhanKy" disabled><i class="bi bi-check-circle"></i> Xác nhận ký</button>' +
            '</div>';

        document.getElementById('hkyBackToSign').addEventListener('click', function () {
            step = 'sign';
            renderSignStep();
        });

        signPane.querySelectorAll('input[name="hkyKenh"]').forEach(function (radio) {
            radio.addEventListener('change', function () {
                kenhGui = radio.value;
                signPane.querySelectorAll('.hky-otp-method').forEach(function (lbl) {
                    lbl.classList.toggle('active', lbl.getAttribute('data-method-label') === kenhGui);
                });
            });
        });

        function guiOtp() {
            var btn = document.getElementById('hkyBtnGuiOtp') || document.getElementById('hkyBtnResend');
            if (btn) btn.disabled = true;
            apiFetch('/otp/gui', { method: 'POST', body: { maHopDong: hd.maHopDong, vaiTroKy: opts.vaiTroKy, kenhGui: kenhGui } })
                .then(function (res) {
                    otpSent = true;
                    document.getElementById('hkyOtpSendArea').style.display = 'none';
                    document.getElementById('hkyOtpVerifyArea').style.display = '';
                    document.getElementById('hkyBtnXacNhanKy').disabled = false;

                    var noteBox = document.getElementById('hkyNoteBox');
                    if (res && res.note) {
                        noteBox.textContent = res.note;
                        noteBox.style.display = '';
                    }
                    var demoBox = document.getElementById('hkyDemoBox');
                    if (res && res.otpDemo) {
                        demoBox.textContent = 'Chưa cấu hình gửi email/SMS thật — mã OTP demo của bạn: ' + res.otpDemo;
                        demoBox.style.display = '';
                    }
                })
                .catch(function (err) {
                    showPanelError((err && err.message) || 'Không gửi được mã OTP.');
                })
                .finally(function () {
                    if (btn) btn.disabled = false;
                });
        }

        document.getElementById('hkyBtnGuiOtp').addEventListener('click', guiOtp);
        document.getElementById('hkyBtnResend').addEventListener('click', guiOtp);

        var otpCodeInput = document.getElementById('hkyOtpCode');
        otpCodeInput.addEventListener('input', function () {
            document.getElementById('hkyBtnXacNhanKy').disabled = !(/^\d{6}$/.test(otpCodeInput.value.trim()) && otpSent);
        });

        document.getElementById('hkyBtnXacNhanKy').addEventListener('click', function () {
            var otp = otpCodeInput.value.trim();
            if (!/^\d{6}$/.test(otp)) { showPanelError('Mã OTP gồm 6 chữ số.'); return; }

            var apiSuffix = opts.vaiTroKy === 'CHU_TRO' ? 'ky-chu-tro' : 'ky-nguoi-thue';
            var chuKyDataUrl = canvas ? canvas.toDataURL('image/png') : '';

            var btn = this;
            btn.disabled = true;
            apiFetch('/hop-dong/' + hd.maHopDong + '/' + apiSuffix, {
                method: 'PUT',
                body: { chuKy: chuKyDataUrl, maOtp: otp }
            })
                .then(function () {
                    closeModal();
                    if (typeof opts.onSuccess === 'function') opts.onSuccess();
                })
                .catch(function (err) {
                    showPanelError((err && err.message) || 'Ký hợp đồng không thành công. Kiểm tra lại mã OTP.');
                    btn.disabled = false;
                });
        });
    }

    /* ================= API công khai ================= */
    global.openHopDongKyModal = function (o) {
        opts = o || {};
        injectOnce();

        step = 'sign';
        signMode = 'draw';
        hasSignature = false;
        otpSent = false;
        kenhGui = 'EMAIL';

        var tieuDe = opts.vaiTroKy === 'CHU_TRO' ? 'Ký hợp đồng (Chủ trọ)' : 'Ký hợp đồng (Người thuê)';
        document.getElementById('hkyTitle').textContent = tieuDe;

        renderPreview();
        renderSignStep();
        openModal();
    };
})(window);
