/**
 * Modal "Tạo hợp đồng" dùng chung cho nhiều trang (chu-tro/contracts.jsp,
 * dat-phong/tienTrinhDatPhong.jsp, ...).
 *
 * Cách dùng:
 *   <script src="${pageContext.request.contextPath}/resources/js/api.js"></script>
 *   <script src="${pageContext.request.contextPath}/resources/js/hopDongModal.js"></script>
 *
 *   // Chế độ 1: cho chọn yêu cầu thuê từ danh sách (trang Hợp đồng)
 *   openHopDongModal({
 *       mode: 'select',
 *       currentUser: currentUser,           // object lấy từ localStorage('user')
 *       onSuccess: function (hopDong) { ... } // sau khi tạo thành công
 *   });
 *
 *   // Chế độ 2: yêu cầu thuê đã biết sẵn (trang Tiến trình đặt phòng)
 *   openHopDongModal({
 *       mode: 'fixed',
 *       currentUser: currentUser,
 *       yeuCau: yeuCauObject,               // object yêu cầu thuê đầy đủ (có phong, nguoiThue...)
 *       onSuccess: function (hopDong) { ... }
 *   });
 */
(function (global) {
    'use strict';

    if (global.openHopDongModal) return; // đã nạp rồi, khỏi nạp lại

    var STYLE_ID = 'hopDongModalStyles';
    var injected = false;
    var f = null;
    var modal = null;
    var formError = null;
    var previewEl = null;
    var eligibleRequests = [];
    var selectedYeuCau = null;
    var currentMode = 'select';
    var currentUser = null;
    var onSuccessCb = null;

    /* ================= CSS (tự chèn 1 lần) ================= */
    var CSS = ''
        + 'body.modal-open{overflow:hidden;}'
        + '.owner-modal{position:fixed;inset:0;z-index:1000;display:none;align-items:center;justify-content:center;padding:20px;font-family:"Inter",Arial,sans-serif;}'
        + '.owner-modal.show{display:flex;}'
        + '.owner-modal-backdrop{position:absolute;inset:0;background:rgba(2,6,23,.76);backdrop-filter:blur(5px);}'
        + '.owner-modal-dialog{position:relative;z-index:1;width:min(680px,100%);max-height:calc(100vh - 40px);overflow-y:auto;background:#101827;border:1px solid #2b3850;border-radius:18px;box-shadow:0 28px 80px rgba(0,0,0,.5);animation:hdModalIn .18s ease-out;}'
        + '@keyframes hdModalIn{from{opacity:0;transform:translateY(10px) scale(.985);}to{opacity:1;transform:translateY(0) scale(1);}}'
        + '.owner-modal-head{display:flex;align-items:flex-start;justify-content:space-between;gap:18px;padding:20px 22px 16px;border-bottom:1px solid #1f2b40;}'
        + '.owner-modal-head h2{margin:4px 0 0;color:#f8fafc;font-size:20px;}'
        + '.owner-modal-head p{margin:5px 0 0;color:#64748b;font-size:11px;}'
        + '.modal-kicker{color:#818cf8;font-size:9px;font-weight:800;letter-spacing:.09em;}'
        + '.modal-close{width:34px;height:34px;border:1px solid #2b3850;border-radius:9px;background:#0f172a;color:#94a3b8;cursor:pointer;display:grid;place-items:center;}'
        + '.modal-close:hover{color:#fff;border-color:#6366f1;}'
        + '.owner-form{padding:20px 22px 22px;}'
        + '.owner-btn{border:0;border-radius:9px;padding:10px 14px;font-size:12px;font-weight:800;cursor:pointer;display:inline-flex;align-items:center;justify-content:center;gap:7px;white-space:nowrap;}'
        + '.owner-btn.primary{background:linear-gradient(135deg,#6366f1,#8b5cf6);color:#fff;box-shadow:0 8px 18px rgba(99,102,241,.25);}'
        + '.owner-btn.primary:hover{filter:brightness(1.08);}'
        + '.owner-btn.light{background:#1e293b !important;color:#cbd5e1 !important;border:1px solid #334155;}'
        + '.owner-input{border:1px solid #2b3850;background:#0f172a !important;color:#e2e8f0 !important;border-radius:9px;padding:10px 11px;font:inherit;font-size:11px;outline:none;min-width:0;width:100%;}'
        + '.owner-input::placeholder{color:#64748b;}'
        + '.owner-input:focus{border-color:#6366f1;box-shadow:0 0 0 3px rgba(99,102,241,.12);}'
        + '.owner-form-note{margin-top:15px;padding:10px 12px;border:1px solid rgba(99,102,241,.16);border-radius:9px;background:rgba(99,102,241,.06);color:#94a3b8;font-size:10px;line-height:1.5;}'
        + '.owner-form-note i{color:#818cf8;margin-right:5px;}'
        + '.owner-modal-actions{display:flex;justify-content:flex-end;gap:9px;margin-top:18px;padding:0 22px 22px;}'
        + '.form-grid{display:grid;grid-template-columns:1fr 1fr;gap:14px;}'
        + '.form-grid label{display:flex;flex-direction:column;gap:7px;}'
        + '.form-grid label.full{grid-column:1 / -1;}'
        + '.form-grid label>span{color:#cbd5e1;font-size:10px;font-weight:700;}'
        + '.form-grid label>span b{color:#f87171;}'
        + '.hd-dialog{width:min(1180px,96vw);}'
        + '.hd-head{flex-wrap:wrap;}'
        + '.hd-head-right{display:flex;align-items:flex-start;gap:10px;}'
        + '.hd-tabs{display:flex;gap:6px;background:#0b1220;border:1px solid #2b3850;border-radius:10px;padding:4px;}'
        + '.hd-tab{border:0;background:transparent;color:#94a3b8;font-size:11px;font-weight:700;padding:7px 12px;border-radius:7px;cursor:pointer;display:inline-flex;align-items:center;gap:6px;white-space:nowrap;}'
        + '.hd-tab.active{background:linear-gradient(135deg,#6366f1,#8b5cf6);color:#fff;}'
        + '.hd-body{display:grid;grid-template-columns:1fr 1fr;gap:0;max-height:62vh;}'
        + '.hd-pane{overflow-y:auto;}'
        + '.hd-pane-form{padding:18px 20px;border-right:1px solid #1f2b40;}'
        + '.hd-pane-preview{padding:24px 26px;background:#0b1220;}'
        + '.hd-form.owner-form{padding:0;}'
        + '.hd-section{margin-bottom:20px;}'
        + '.hd-section-title{color:#a5b4fc;font-size:11px;font-weight:800;letter-spacing:.03em;margin-bottom:10px;display:flex;align-items:center;gap:7px;}'
        + '.hd-input{width:100%;padding:10px 11px;border:1px solid #2b3850;border-radius:9px;background:#0b1220;color:#e2e8f0;font:inherit;font-size:11px;outline:none;}'
        + '.hd-input:focus{border-color:#6366f1;box-shadow:0 0 0 3px rgba(99,102,241,.12);}'
        + '.hd-input:disabled{color:#64748b;cursor:not-allowed;}'
        + '.hd-checkline{display:flex;align-items:center;gap:9px;color:#cbd5e1;font-size:11px;font-weight:700;}'
        + '.hd-checkline input{width:16px;height:16px;accent-color:#6366f1;}'
        + '.hd-preview{background:#fff;color:#1e293b;border-radius:10px;padding:30px 34px;font-size:12.5px;line-height:1.75;box-shadow:0 10px 30px rgba(0,0,0,.35);}'
        + '.hd-preview h1{text-align:center;font-size:16px;margin:0 0 4px;}'
        + '.hd-preview .hd-quochieu{text-align:center;font-weight:700;font-size:12.5px;}'
        + '.hd-preview .hd-tieungu{text-align:center;font-size:11.5px;margin-bottom:18px;}'
        + '.hd-preview .hd-tieungu::after{content:"";display:block;width:90px;border-bottom:1px solid #1e293b;margin:3px auto 0;}'
        + '.hd-preview h3{font-size:13px;margin:16px 0 6px;}'
        + '.hd-preview p{margin:0 0 8px;text-align:justify;}'
        + '.hd-preview ul{margin:0 0 8px 18px;padding:0;}'
        + '.hd-preview li{margin-bottom:4px;text-align:justify;}'
        + '.hd-sign-table{width:100%;border-collapse:collapse;margin-top:22px;}'
        + '.hd-sign-table td{width:50%;text-align:center;vertical-align:top;padding:14px 10px;border:1px solid #cbd5e1;font-weight:700;}'
        + '.hd-sign-table small{display:block;font-weight:400;color:#64748b;margin-top:60px;}'
        + '.hd-coming-soon{display:flex;flex-direction:column;align-items:center;justify-content:center;gap:8px;text-align:center;padding:70px 30px;color:#94a3b8;}'
        + '.hd-coming-soon i{font-size:32px;color:#6366f1;}'
        + '.hd-coming-soon strong{color:#f8fafc;font-size:15px;}'
        + '@media (max-width:860px){.hd-body{grid-template-columns:1fr;max-height:none;}.hd-pane-form{border-right:0;border-bottom:1px solid #1f2b40;}}';

    /* ================= HTML ================= */
    var HTML = ''
        + '<div class="owner-modal" id="hdModal" aria-hidden="true">'
        + '  <div class="owner-modal-backdrop" data-close-hd></div>'
        + '  <div class="owner-modal-dialog hd-dialog" role="dialog" aria-modal="true">'
        + '    <div class="owner-modal-head hd-head">'
        + '      <div>'
        + '        <span class="modal-kicker"><i class="bi bi-file-earmark-plus"></i> TẠO HỢP ĐỒNG</span>'
        + '        <h2>Hợp đồng thuê nhà - Mẫu cơ bản (nhà trọ/phòng)</h2>'
        + '        <p>Điền thông tin vào hợp đồng. Hệ thống đã tự động điền một số thông tin từ yêu cầu thuê.</p>'
        + '      </div>'
        + '      <div class="hd-head-right">'
        + '        <div class="hd-tabs">'
        + '          <button type="button" class="hd-tab active" data-tab="ketHop"><i class="bi bi-layout-split"></i> Kết hợp</button>'
        + '          <button type="button" class="hd-tab" data-tab="hopDong"><i class="bi bi-file-earmark-text"></i> Hợp đồng</button>'
        + '          <button type="button" class="hd-tab" data-tab="bieuMau"><i class="bi bi-list-ul"></i> Biểu mẫu</button>'
        + '        </div>'
        + '        <button type="button" class="modal-close" data-close-hd aria-label="Đóng"><i class="bi bi-x-lg"></i></button>'
        + '      </div>'
        + '    </div>'
        + '    <div class="hd-body" id="hdBody">'
        + '      <div class="hd-pane hd-pane-form" id="hdPaneForm">'
        + '        <form id="hdForm" class="owner-form hd-form" onsubmit="return false;">'
        + '          <div class="hd-section" id="hdYeuCauSection">'
        + '            <div class="hd-section-title"><i class="bi bi-person-check"></i> Chọn yêu cầu thuê</div>'
        + '            <label class="full">'
        + '              <span>Yêu cầu thuê đã duyệt <b>*</b></span>'
        + '              <select class="owner-input" id="hdYeuCau" required><option value="">-- Chọn phòng / khách thuê --</option></select>'
        + '            </label>'
        + '            <div class="owner-form-note" id="hdYeuCauNote" style="margin-top:10px;"><i class="bi bi-info-circle"></i> Đang tải danh sách yêu cầu đã duyệt...</div>'
        + '          </div>'
        + '          <div class="hd-section">'
        + '            <div class="hd-section-title"><i class="bi bi-pencil-square"></i> Thông tin cơ bản hợp đồng</div>'
        + '            <div class="form-grid">'
        + '              <label><span>Địa điểm ký <b>*</b></span><input class="hd-input" id="hdDiaDiemKy" required placeholder="VD: Đà Nẵng"></label>'
        + '              <label><span>Ngày ký <b>*</b></span><input class="hd-input" id="hdNgayKyNgay" type="number" min="1" max="31" required></label>'
        + '              <label><span>Tháng ký <b>*</b></span><input class="hd-input" id="hdNgayKyThang" type="number" min="1" max="12" required></label>'
        + '              <label><span>Năm ký <b>*</b></span><input class="hd-input" id="hdNgayKyNam" type="number" min="2020" required></label>'
        + '            </div>'
        + '          </div>'
        + '          <div class="hd-section">'
        + '            <div class="hd-section-title"><i class="bi bi-building"></i> Thông tin Bên cho thuê (Bên A)</div>'
        + '            <div class="form-grid">'
        + '              <label><span>Tên chủ nhà <b>*</b></span><input class="hd-input" id="hdTenChuNha" required placeholder="Nhập tên chủ nhà"></label>'
        + '              <label><span>Số điện thoại chủ nhà <b>*</b></span><input class="hd-input" id="hdSdtChuNha" required placeholder="Nhập số điện thoại chủ nhà"></label>'
        + '              <label><span>CMND/CCCD chủ nhà <b>*</b></span><input class="hd-input" id="hdCccdChuNha" required placeholder="Nhập CMND/CCCD chủ nhà"></label>'
        + '              <label><span>Ngày cấp CMND/CCCD chủ nhà <b>*</b></span><input class="hd-input" id="hdNgayCapChuNha" type="date" required></label>'
        + '              <label><span>Nơi cấp CMND/CCCD chủ nhà <b>*</b></span><input class="hd-input" id="hdNoiCapChuNha" required placeholder="Nhập nơi cấp CMND/CCCD chủ nhà"></label>'
        + '              <label><span>Địa chỉ thường trú chủ nhà <b>*</b></span><input class="hd-input" id="hdDiaChiChuNha" required placeholder="Nhập địa chỉ thường trú chủ nhà"></label>'
        + '            </div>'
        + '          </div>'
        + '          <div class="hd-section">'
        + '            <div class="hd-section-title"><i class="bi bi-person-badge"></i> Thông tin Bên thuê (Bên B)</div>'
        + '            <div class="form-grid">'
        + '              <label><span>Tên người thuê <b>*</b></span><input class="hd-input" id="hdTenNguoiThue" required placeholder="Nhập tên người thuê"></label>'
        + '              <label><span>Số điện thoại người thuê <b>*</b></span><input class="hd-input" id="hdSdtNguoiThue" required placeholder="Nhập số điện thoại người thuê"></label>'
        + '              <label><span>CMND/CCCD người thuê <b>*</b></span><input class="hd-input" id="hdCccdNguoiThue" required placeholder="Nhập CMND/CCCD người thuê"></label>'
        + '              <label><span>Ngày cấp CMND/CCCD người thuê <b>*</b></span><input class="hd-input" id="hdNgayCapNguoiThue" type="date" required></label>'
        + '              <label><span>Nơi cấp CMND/CCCD người thuê <b>*</b></span><input class="hd-input" id="hdNoiCapNguoiThue" required placeholder="Nhập nơi cấp CMND/CCCD người thuê"></label>'
        + '              <label><span>Địa chỉ thường trú người thuê <b>*</b></span><input class="hd-input" id="hdDiaChiNguoiThue" required placeholder="Nhập địa chỉ thường trú người thuê"></label>'
        + '            </div>'
        + '          </div>'
        + '          <div class="hd-section">'
        + '            <div class="hd-section-title"><i class="bi bi-door-open"></i> Thông tin phòng thuê</div>'
        + '            <div class="form-grid">'
        + '              <label><span>Nhà trọ</span><input class="hd-input" id="hdTenNhaTro" disabled></label>'
        + '              <label><span>Phòng</span><input class="hd-input" id="hdTenPhong" disabled></label>'
        + '              <label><span>Ngày bắt đầu <b>*</b></span><input class="hd-input" id="hdBatDau" type="date" required></label>'
        + '              <label><span>Ngày kết thúc <b>*</b></span><input class="hd-input" id="hdKetThuc" type="date" required></label>'
        + '              <label><span>Giá thuê / tháng (đ) <b>*</b></span><input class="hd-input" id="hdGiaThue" type="number" min="0" step="10000" required></label>'
        + '              <label><span>Tiền cọc (đ) <b>*</b></span><input class="hd-input" id="hdTienCoc" type="number" min="0" step="10000" required></label>'
        + '            </div>'
        + '          </div>'
        + '          <div class="hd-section">'
        + '            <div class="hd-section-title"><i class="bi bi-arrow-repeat"></i> Chính sách gia hạn (Điều 13)</div>'
        + '            <label class="hd-checkline"><input type="checkbox" id="hdChoGiaHan" checked><span>Cho phép gia hạn hợp đồng sau khi hết hạn</span></label>'
        + '            <div class="form-grid" style="margin-top:12px;">'
        + '              <label><span>Báo trước gia hạn (ngày)</span><input class="hd-input" id="hdSoNgayBaoTruoc" type="number" min="0" value="30"></label>'
        + '              <label><span>Số lần gia hạn tối đa</span><input class="hd-input" id="hdSoLanGiaHan" type="number" min="0" value="3"></label>'
        + '              <label><span>Mức tăng giá tối đa / lần (%)</span><input class="hd-input" id="hdMucTangGia" type="number" min="0" value="10"></label>'
        + '            </div>'
        + '          </div>'
        + '          <div class="owner-form-note" id="hdFormError" style="display:none;background:rgba(239,68,68,.08);border-color:rgba(239,68,68,.25);color:#fca5a5;"></div>'
        + '        </form>'
        + '      </div>'
        + '      <div class="hd-pane hd-pane-preview" id="hdPanePreview"><div class="hd-preview" id="hdPreview"></div></div>'
        + '      <div class="hd-pane hd-pane-templates" id="hdPaneTemplates" hidden>'
        + '        <div class="hd-coming-soon"><i class="bi bi-list-ul"></i><strong>Thư viện biểu mẫu</strong><span>Tính năng chọn nhiều mẫu hợp đồng sẽ sớm ra mắt. Hiện tại hệ thống dùng mẫu cơ bản (nhà trọ/phòng).</span></div>'
        + '      </div>'
        + '    </div>'
        + '    <div class="owner-modal-actions">'
        + '      <button type="button" class="owner-btn light" data-close-hd>Hủy</button>'
        + '      <button type="button" class="owner-btn light" id="hdBtnXemTruoc"><i class="bi bi-eye"></i> Xem trước</button>'
        + '      <button type="button" class="owner-btn primary" id="hdBtnSubmit"><i class="bi bi-check-lg"></i> Tạo hợp đồng</button>'
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
    function addToDate(dateStr, amount, unit) {
        var d = dateStr ? new Date(dateStr) : new Date();
        if (unit === 'Năm') d.setFullYear(d.getFullYear() + amount);
        else d.setMonth(d.getMonth() + (amount || 6));
        return d.toISOString().slice(0, 10);
    }

    /* ================= inject (1 lần) ================= */
    function injectOnce() {
        if (injected) return;
        injected = true;

        var styleEl = document.createElement('style');
        styleEl.id = STYLE_ID;
        styleEl.textContent = CSS;
        document.head.appendChild(styleEl);

        var wrap = document.createElement('div');
        wrap.innerHTML = HTML;
        document.body.appendChild(wrap.firstElementChild);

        modal = document.getElementById('hdModal');
        formError = document.getElementById('hdFormError');
        previewEl = document.getElementById('hdPreview');

        f = {
            yeuCau: document.getElementById('hdYeuCau'),
            yeuCauNote: document.getElementById('hdYeuCauNote'),
            diaDiemKy: document.getElementById('hdDiaDiemKy'),
            ngayKyNgay: document.getElementById('hdNgayKyNgay'),
            ngayKyThang: document.getElementById('hdNgayKyThang'),
            ngayKyNam: document.getElementById('hdNgayKyNam'),
            tenChuNha: document.getElementById('hdTenChuNha'),
            sdtChuNha: document.getElementById('hdSdtChuNha'),
            cccdChuNha: document.getElementById('hdCccdChuNha'),
            ngayCapChuNha: document.getElementById('hdNgayCapChuNha'),
            noiCapChuNha: document.getElementById('hdNoiCapChuNha'),
            diaChiChuNha: document.getElementById('hdDiaChiChuNha'),
            tenNguoiThue: document.getElementById('hdTenNguoiThue'),
            sdtNguoiThue: document.getElementById('hdSdtNguoiThue'),
            cccdNguoiThue: document.getElementById('hdCccdNguoiThue'),
            ngayCapNguoiThue: document.getElementById('hdNgayCapNguoiThue'),
            noiCapNguoiThue: document.getElementById('hdNoiCapNguoiThue'),
            diaChiNguoiThue: document.getElementById('hdDiaChiNguoiThue'),
            tenNhaTro: document.getElementById('hdTenNhaTro'),
            tenPhong: document.getElementById('hdTenPhong'),
            batDau: document.getElementById('hdBatDau'),
            ketThuc: document.getElementById('hdKetThuc'),
            giaThue: document.getElementById('hdGiaThue'),
            tienCoc: document.getElementById('hdTienCoc'),
            choGiaHan: document.getElementById('hdChoGiaHan'),
            soNgayBaoTruoc: document.getElementById('hdSoNgayBaoTruoc'),
            soLanGiaHan: document.getElementById('hdSoLanGiaHan'),
            mucTangGia: document.getElementById('hdMucTangGia')
        };

        Object.keys(f).forEach(function (key) {
            var el = f[key];
            if (!el || el === f.yeuCau) return;
            el.addEventListener('input', renderPreview);
            el.addEventListener('change', renderPreview);
        });

        document.querySelectorAll('[data-close-hd]').forEach(function (el) { el.addEventListener('click', closeModal); });
        document.querySelectorAll('.hd-tab').forEach(function (btn) {
            btn.addEventListener('click', function () { setTab(btn.getAttribute('data-tab')); });
        });
        document.getElementById('hdBtnXemTruoc').addEventListener('click', function () { setTab('hopDong'); });
        document.getElementById('hdBtnSubmit').addEventListener('click', onSubmit);

        f.yeuCau.addEventListener('change', function () {
            var maYeuCau = Number(f.yeuCau.value);
            selectedYeuCau = eligibleRequests.find(function (yc) { return yc.maYeuCau === maYeuCau; }) || null;
            if (!selectedYeuCau) { renderPreview(); return; }
            fillFromYeuCau(selectedYeuCau);
        });
    }

    /* ================= tabs ================= */
    function setTab(tab) {
        document.querySelectorAll('.hd-tab').forEach(function (btn) {
            btn.classList.toggle('active', btn.getAttribute('data-tab') === tab);
        });
        var paneForm = document.getElementById('hdPaneForm');
        var panePreview = document.getElementById('hdPanePreview');
        var paneTemplates = document.getElementById('hdPaneTemplates');

        if (tab === 'bieuMau') {
            paneForm.hidden = true; panePreview.hidden = true; paneTemplates.hidden = false;
            return;
        }
        paneTemplates.hidden = true;
        if (tab === 'hopDong') {
            paneForm.hidden = true; panePreview.hidden = false;
            panePreview.style.gridColumn = '1 / -1';
        } else {
            paneForm.hidden = false; panePreview.hidden = false;
            panePreview.style.gridColumn = '';
        }
    }

    /* ================= mở / đóng modal ================= */
    function openModal() {
        modal.classList.add('show');
        modal.setAttribute('aria-hidden', 'false');
        document.body.classList.add('modal-open');
        resetForm();

        if (currentMode === 'fixed') {
            document.getElementById('hdYeuCauSection').style.display = 'none';
            f.yeuCau.required = false;
        } else {
            document.getElementById('hdYeuCauSection').style.display = '';
            f.yeuCau.required = true;
            loadEligibleRequests();
        }
        setTab('ketHop');
    }
    function closeModal() {
        modal.classList.remove('show');
        modal.setAttribute('aria-hidden', 'true');
        document.body.classList.remove('modal-open');
    }

    /* ================= form ================= */
    function resetForm() {
        var today = new Date();
        f.diaDiemKy.value = '';
        f.ngayKyNgay.value = today.getDate();
        f.ngayKyThang.value = today.getMonth() + 1;
        f.ngayKyNam.value = today.getFullYear();
        f.tenChuNha.value = (currentUser && currentUser.hoTen) || '';
        f.sdtChuNha.value = (currentUser && currentUser.soDienThoai) || '';
        f.diaChiChuNha.value = (currentUser && currentUser.diaChi) || '';
        f.cccdChuNha.value = '';
        f.ngayCapChuNha.value = '';
        f.noiCapChuNha.value = '';
        f.tenNguoiThue.value = '';
        f.sdtNguoiThue.value = '';
        f.cccdNguoiThue.value = '';
        f.ngayCapNguoiThue.value = '';
        f.noiCapNguoiThue.value = '';
        f.diaChiNguoiThue.value = '';
        f.tenNhaTro.value = '';
        f.tenPhong.value = '';
        f.batDau.value = '';
        f.ketThuc.value = '';
        f.giaThue.value = '';
        f.tienCoc.value = '';
        f.choGiaHan.checked = true;
        f.soNgayBaoTruoc.value = 30;
        f.soLanGiaHan.value = 3;
        f.mucTangGia.value = 10;
        formError.style.display = 'none';
        renderPreview();
    }

    function fillFromYeuCau(yc) {
        var phong = yc.phong || {};
        var nhaTro = phong.nhaTro || {};
        var nguoiThue = yc.nguoiThue || {};

        f.tenNhaTro.value = nhaTro.tenNhaTro || '';
        f.tenPhong.value = phong.tenPhong || '';
        f.tenNguoiThue.value = nguoiThue.hoTen || '';
        f.sdtNguoiThue.value = yc.soDienThoaiLienHe || nguoiThue.soDienThoai || '';
        f.diaChiNguoiThue.value = nguoiThue.diaChi || '';

        var ngayBatDau = yc.ngayMuonNhanPhong || new Date().toISOString().slice(0, 10);
        f.batDau.value = ngayBatDau;
        f.ketThuc.value = addToDate(ngayBatDau, yc.thoiHanThue || 6, yc.donViThoiHan || 'Tháng');
        f.giaThue.value = phong.giaPhong || 0;
        f.tienCoc.value = phong.giaPhong || 0;

        renderPreview();
    }

    function loadEligibleRequests() {
        f.yeuCau.innerHTML = '<option value="">-- Chọn phòng / khách thuê --</option>';
        f.yeuCauNote.style.display = '';
        f.yeuCauNote.innerHTML = '<i class="bi bi-info-circle"></i> Đang tải danh sách yêu cầu đã duyệt...';

        Promise.all([
            apiFetch('/yeu-cau-thue/chu-tro/' + currentUser.maNguoiDung),
            apiFetch('/hop-dong/chu-tro/' + currentUser.maNguoiDung)
        ]).then(function (results) {
            var yeuCauList = results[0] || [];
            var hopDongList = results[1] || [];
            var daCoHopDong = {};
            hopDongList.forEach(function (hd) {
                if (hd.yeuCauThue && hd.yeuCauThue.maYeuCau) daCoHopDong[hd.yeuCauThue.maYeuCau] = true;
            });

            eligibleRequests = yeuCauList.filter(function (yc) {
                return yc.trangThai === 'Đã duyệt' && !daCoHopDong[yc.maYeuCau];
            });

            if (eligibleRequests.length === 0) {
                f.yeuCauNote.innerHTML = '<i class="bi bi-info-circle"></i> Không có yêu cầu thuê nào đã duyệt và chưa có hợp đồng. Hãy duyệt một yêu cầu thuê ở mục "Yêu cầu thuê" trước.';
                return;
            }
            f.yeuCauNote.style.display = 'none';

            f.yeuCau.innerHTML = '<option value="">-- Chọn phòng / khách thuê --</option>' +
                eligibleRequests.map(function (yc) {
                    var tenPhong = yc.phong ? yc.phong.tenPhong : '?';
                    var tenNhaTro = (yc.phong && yc.phong.nhaTro) ? yc.phong.nhaTro.tenNhaTro : '';
                    var tenKhach = yc.nguoiThue ? (yc.nguoiThue.hoTen || yc.nguoiThue.email) : '?';
                    return '<option value="' + yc.maYeuCau + '">' + escapeHtml(tenPhong) + ' - ' + escapeHtml(tenNhaTro) + ' • ' + escapeHtml(tenKhach) + '</option>';
                }).join('');
        }).catch(function (err) {
            f.yeuCauNote.innerHTML = '<i class="bi bi-exclamation-triangle-fill"></i> ' + escapeHtml((err && err.message) || 'Không tải được danh sách yêu cầu thuê.');
        });
    }

    /* ================= preview trực tiếp ================= */
    function currentData() {
        return {
            diaDiemKy: f.diaDiemKy.value || '..........',
            ngay: f.ngayKyNgay.value || '..', thang: f.ngayKyThang.value || '..', nam: f.ngayKyNam.value || '........',
            tenChuNha: f.tenChuNha.value || '..........', sdtChuNha: f.sdtChuNha.value || '..........',
            cccdChuNha: f.cccdChuNha.value || '..........', ngayCapChuNha: f.ngayCapChuNha.value,
            noiCapChuNha: f.noiCapChuNha.value || '..........', diaChiChuNha: f.diaChiChuNha.value || '..........',
            tenNguoiThue: f.tenNguoiThue.value || '..........', sdtNguoiThue: f.sdtNguoiThue.value || '..........',
            cccdNguoiThue: f.cccdNguoiThue.value || '..........', ngayCapNguoiThue: f.ngayCapNguoiThue.value,
            noiCapNguoiThue: f.noiCapNguoiThue.value || '..........', diaChiNguoiThue: f.diaChiNguoiThue.value || '..........',
            tenNhaTro: f.tenNhaTro.value || '..........', tenPhong: f.tenPhong.value || '..........',
            diaChiNhaTro: (selectedYeuCau && selectedYeuCau.phong && selectedYeuCau.phong.nhaTro) ? (selectedYeuCau.phong.nhaTro.diaChi || '') : '',
            batDau: f.batDau.value, ketThuc: f.ketThuc.value,
            giaThue: f.giaThue.value || 0, tienCoc: f.tienCoc.value || 0,
            choGiaHan: f.choGiaHan.checked,
            soNgayBaoTruoc: f.soNgayBaoTruoc.value || 30, soLanGiaHan: f.soLanGiaHan.value || 3, mucTangGia: f.mucTangGia.value || 10
        };
    }

    function renderPreview() {
        if (!previewEl) return;
        var d = currentData();
        previewEl.innerHTML =
            '<div class="hd-quochieu">CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</div>' +
            '<div class="hd-tieungu">Độc lập - Tự do - Hạnh phúc</div>' +
            '<h1>HỢP ĐỒNG THUÊ NHÀ</h1>' +
            '<p style="text-align:center;">(Mẫu cơ bản áp dụng cho nhà trọ / phòng trọ)</p>' +
            '<p>Hôm nay, ngày <b>' + escapeHtml(d.ngay) + '</b> tháng <b>' + escapeHtml(d.thang) + '</b> năm <b>' + escapeHtml(d.nam) + '</b>, tại <b>' + escapeHtml(d.diaDiemKy) + '</b>, chúng tôi gồm có:</p>' +

            '<h3>BÊN CHO THUÊ (BÊN A)</h3>' +
            '<p>Ông/Bà: <b>' + escapeHtml(d.tenChuNha) + '</b> — Điện thoại: ' + escapeHtml(d.sdtChuNha) + '<br>' +
            'CMND/CCCD số: ' + escapeHtml(d.cccdChuNha) + (d.ngayCapChuNha ? ', cấp ngày ' + formatDate(d.ngayCapChuNha) : '') + (d.noiCapChuNha ? ', tại ' + escapeHtml(d.noiCapChuNha) : '') + '<br>' +
            'Địa chỉ thường trú: ' + escapeHtml(d.diaChiChuNha) + '</p>' +

            '<h3>BÊN THUÊ (BÊN B)</h3>' +
            '<p>Ông/Bà: <b>' + escapeHtml(d.tenNguoiThue) + '</b> — Điện thoại: ' + escapeHtml(d.sdtNguoiThue) + '<br>' +
            'CMND/CCCD số: ' + escapeHtml(d.cccdNguoiThue) + (d.ngayCapNguoiThue ? ', cấp ngày ' + formatDate(d.ngayCapNguoiThue) : '') + (d.noiCapNguoiThue ? ', tại ' + escapeHtml(d.noiCapNguoiThue) : '') + '<br>' +
            'Địa chỉ thường trú: ' + escapeHtml(d.diaChiNguoiThue) + '</p>' +

            '<p>Sau khi bàn bạc, hai bên thống nhất ký kết hợp đồng thuê nhà với các điều khoản sau:</p>' +

            '<h3>Điều 1. Đối tượng và mục đích thuê</h3>' +
            '<p>Bên A đồng ý cho Bên B thuê phòng <b>' + escapeHtml(d.tenPhong) + '</b> thuộc nhà trọ <b>' + escapeHtml(d.tenNhaTro) + '</b>' +
            (d.diaChiNhaTro ? (', địa chỉ: ' + escapeHtml(d.diaChiNhaTro)) : '') + '. Bên B sử dụng phòng thuê đúng mục đích để ở, không được sử dụng vào mục đích khác nếu chưa có sự đồng ý bằng văn bản của Bên A.</p>' +

            '<h3>Điều 2. Thời hạn thuê</h3>' +
            '<p>Thời hạn thuê từ ngày <b>' + formatDate(d.batDau) + '</b> đến ngày <b>' + formatDate(d.ketThuc) + '</b>. Hết thời hạn này, nếu Bên B có nhu cầu tiếp tục thuê thì thực hiện theo chính sách gia hạn tại Điều 13.</p>' +

            '<h3>Điều 3. Giá thuê và phương thức thanh toán</h3>' +
            '<p>Giá thuê phòng là <b>' + formatMoney(d.giaThue) + '</b>/tháng, chưa bao gồm tiền điện, nước, gửi xe, internet (tính theo thực tế sử dụng hàng tháng). Bên B thanh toán tiền thuê hàng tháng cho Bên A trước ngày 05 của tháng, bằng tiền mặt hoặc chuyển khoản.</p>' +

            '<h3>Điều 4. Tiền đặt cọc</h3>' +
            '<p>Bên B đặt cọc cho Bên A số tiền <b>' + formatMoney(d.tienCoc) + '</b> để đảm bảo thực hiện hợp đồng. Tiền cọc được hoàn trả lại cho Bên B khi kết thúc hợp đồng, sau khi đã trừ các khoản chi phí phát sinh (nếu có) do lỗi của Bên B.</p>' +

            '<h3>Điều 5. Quyền và nghĩa vụ của Bên A</h3>' +
            '<ul><li>Giao phòng đúng tình trạng, đúng thời hạn đã thỏa thuận.</li>' +
            '<li>Đảm bảo quyền sử dụng phòng ổn định cho Bên B trong thời hạn thuê.</li>' +
            '<li>Thông báo trước cho Bên B khi cần sửa chữa lớn hoặc kiểm tra phòng.</li>' +
            '<li>Không tự ý tăng giá thuê, thay đổi điều khoản hợp đồng khi chưa có sự đồng ý của Bên B.</li></ul>' +

            '<h3>Điều 6. Quyền và nghĩa vụ của Bên B</h3>' +
            '<ul><li>Sử dụng phòng đúng mục đích, giữ gìn vệ sinh, an ninh trật tự chung.</li>' +
            '<li>Thanh toán tiền thuê và các chi phí phát sinh đúng hạn theo Điều 3.</li>' +
            '<li>Không tự ý cải tạo, sửa chữa kết cấu phòng khi chưa được Bên A đồng ý.</li>' +
            '<li>Không cho người khác thuê lại, ở ghép ngoài số người đã đăng ký khi chưa có sự đồng ý của Bên A.</li></ul>' +

            '<h3>Điều 7. Sửa chữa, cải tạo phòng thuê</h3>' +
            '<p>Mọi hư hỏng do lỗi kết cấu, hao mòn tự nhiên sẽ do Bên A chịu trách nhiệm sửa chữa. Hư hỏng do lỗi của Bên B gây ra thì Bên B có trách nhiệm khắc phục hoặc bồi thường.</p>' +

            '<h3>Điều 8. Đơn phương chấm dứt hợp đồng</h3>' +
            '<p>Bên nào muốn chấm dứt hợp đồng trước hạn phải thông báo cho bên còn lại trước ít nhất 30 ngày. Nếu Bên B đơn phương chấm dứt hợp đồng không đúng quy định thì không được hoàn lại tiền cọc. Nếu Bên A đơn phương chấm dứt hợp đồng không đúng quy định thì phải hoàn cọc và bồi thường cho Bên B một khoản tương đương tiền cọc.</p>' +

            '<h3>Điều 9. Bồi thường thiệt hại</h3>' +
            '<p>Bên nào vi phạm nghĩa vụ gây thiệt hại cho bên còn lại thì phải bồi thường theo thiệt hại thực tế phát sinh, trừ trường hợp bất khả kháng quy định tại Điều 10.</p>' +

            '<h3>Điều 10. Trường hợp bất khả kháng</h3>' +
            '<p>Trường hợp thiên tai, hỏa hoạn, dịch bệnh hoặc các sự kiện bất khả kháng khác nằm ngoài khả năng kiểm soát của hai bên, các bên sẽ cùng bàn bạc, thỏa thuận hướng giải quyết phù hợp, không bên nào phải chịu trách nhiệm bồi thường.</p>' +

            '<h3>Điều 11. Giải quyết tranh chấp</h3>' +
            '<p>Mọi tranh chấp phát sinh trong quá trình thực hiện hợp đồng sẽ được hai bên ưu tiên giải quyết thông qua thương lượng, hòa giải. Trường hợp không thỏa thuận được, tranh chấp sẽ được đưa ra Tòa án nhân dân có thẩm quyền giải quyết theo quy định pháp luật.</p>' +

            '<h3>Điều 12. Điều khoản chung</h3>' +
            '<p>Hợp đồng này có hiệu lực kể từ ngày ký. Hợp đồng được lập thành 02 bản có giá trị pháp lý như nhau, mỗi bên giữ 01 bản. Các thỏa thuận khác mà hai bên thống nhất nhưng chưa được ghi tại các điều khoản trên đây và/hoặc phụ lục kèm theo, là một phần không tách rời của Hợp đồng này.</p>' +

            '<h3>Điều 13. Chính sách gia hạn sau khi hết hạn hợp đồng</h3>' +
            '<ul>' +
            '<li>13.1. Việc gia hạn sau khi hết thời hạn hợp đồng được áp dụng nếu: <b>' + (d.choGiaHan ? '☑' : '☐') + '</b> .</li>' +
            '<li>13.2. Trường hợp được phép gia hạn, Bên B phải thông báo bằng văn bản cho Bên A về nhu cầu gia hạn trước ít nhất <u>' + escapeHtml(d.soNgayBaoTruoc) + '</u> ngày so với ngày hết hạn hợp đồng.</li>' +
            '<li>13.3. Số lần gia hạn tối đa là <u>' + escapeHtml(d.soLanGiaHan) + '</u> lần, trừ khi Hai Bên có thỏa thuận khác bằng văn bản.</li>' +
            '<li>13.4. Mức tăng giá thuê tối đa cho mỗi lần gia hạn không vượt quá <u>' + escapeHtml(d.mucTangGia) + '</u>% so với giá thuê liền kề trước đó, trừ khi Hai Bên có thỏa thuận khác bằng văn bản hoặc pháp luật có quy định khác.</li>' +
            '</ul>' +

            '<table class="hd-sign-table"><tr>' +
            '<td>BÊN CHO THUÊ (BÊN A)<small>(Ký và ghi rõ họ tên)</small></td>' +
            '<td>BÊN THUÊ (BÊN B)<small>(Ký và ghi rõ họ tên)</small></td>' +
            '</tr></table>';
    }

    /* ================= submit ================= */
    function showFormError(msg) {
        formError.textContent = msg;
        formError.style.display = '';
    }

    function onSubmit() {
        if (currentMode === 'select' && !selectedYeuCau) { showFormError('Vui lòng chọn yêu cầu thuê trước.'); setTab('ketHop'); return; }
        if (!document.getElementById('hdForm').reportValidity()) { setTab('ketHop'); return; }
        if (new Date(f.ketThuc.value) <= new Date(f.batDau.value)) {
            showFormError('Ngày kết thúc phải sau ngày bắt đầu.'); return;
        }
        formError.style.display = 'none';

        var maYeuCau = selectedYeuCau.maYeuCau;

        var body = {
            ngayBatDau: f.batDau.value,
            ngayKetThuc: f.ketThuc.value,
            giaThue: Number(f.giaThue.value),
            tienCoc: Number(f.tienCoc.value),
            diaDiemKy: f.diaDiemKy.value,
            cccdChuTro: f.cccdChuNha.value,
            ngayCapCccdChuTro: f.ngayCapChuNha.value,
            noiCapCccdChuTro: f.noiCapChuNha.value,
            diaChiThuongTruChuTro: f.diaChiChuNha.value,
            cccdNguoiThue: f.cccdNguoiThue.value,
            ngayCapCccdNguoiThue: f.ngayCapNguoiThue.value,
            noiCapCccdNguoiThue: f.noiCapNguoiThue.value,
            diaChiThuongTruNguoiThue: f.diaChiNguoiThue.value,
            choPhepGiaHan: f.choGiaHan.checked,
            soNgayBaoTruocGiaHan: Number(f.soNgayBaoTruoc.value || 0),
            soLanGiaHanToiDa: Number(f.soLanGiaHan.value || 0),
            mucTangGiaToiDaPhanTram: Number(f.mucTangGia.value || 0)
        };

        var btn = document.getElementById('hdBtnSubmit');
        btn.disabled = true;
        btn.innerHTML = '<i class="bi bi-arrow-repeat"></i> Đang tạo...';

        apiFetch('/hop-dong/tu-yeu-cau/' + maYeuCau, { method: 'POST', body: body })
            .then(function (hopDong) {
                closeModal();
                if (typeof onSuccessCb === 'function') onSuccessCb(hopDong);
            })
            .catch(function (err) {
                showFormError((err && err.message) || 'Không tạo được hợp đồng.');
            })
            .finally(function () {
                btn.disabled = false;
                btn.innerHTML = '<i class="bi bi-check-lg"></i> Tạo hợp đồng';
            });
    }

    /* ================= API công khai ================= */
    global.openHopDongModal = function (opts) {
        opts = opts || {};
        injectOnce();

        currentMode = opts.mode === 'fixed' ? 'fixed' : 'select';
        currentUser = opts.currentUser || null;
        onSuccessCb = opts.onSuccess || null;
        eligibleRequests = [];
        selectedYeuCau = null;

        if (currentMode === 'fixed') {
            selectedYeuCau = opts.yeuCau || null;
        }

        openModal();

        if (currentMode === 'fixed' && selectedYeuCau) {
            fillFromYeuCau(selectedYeuCau);
        }
    };
})(window);
