<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %><%@ include file="includes/header.jspf" %>

<div class="owner-page-head">
    <div>
        <div class="eyebrow">HỢP ĐỒNG</div>
        <h1>Quản lý hợp đồng</h1>
        <p>Theo dõi hợp đồng đang hiệu lực, sắp hết hạn và đã kết thúc.</p>
    </div>
    <button type="button" class="owner-btn primary" id="openHdModalBtn"><i class="bi bi-file-earmark-plus"></i> Tạo hợp đồng</button>
</div>

<div class="owner-stats compact" id="hdStats">
    <div class="owner-stat green"><span>Đang hiệu lực</span><strong id="hdStatHieuLuc">0</strong><small>Hợp đồng</small></div>
    <div class="owner-stat orange"><span>Sắp hết hạn</span><strong id="hdStatSapHetHan">0</strong><small>Trong 30 ngày</small></div>
    <div class="owner-stat blue"><span>Chờ ký</span><strong id="hdStatChoKy">0</strong><small>Người thuê</small></div>
    <div class="owner-stat purple"><span>Đã kết thúc</span><strong id="hdStatKetThuc">0</strong><small>Hợp đồng</small></div>
</div>

<section class="owner-card">
    <div class="toolbar-owner">
        <input class="owner-input" id="hdSearch" placeholder="Tìm tên người thuê / số phòng...">
        <select class="owner-input" id="hdStatusFilter">
            <option value="ALL">Tất cả trạng thái</option>
            <option value="HIEU_LUC">Đang hiệu lực</option>
            <option value="SAP_HET_HAN">Sắp hết hạn</option>
            <option value="CHO_KY">Chờ ký</option>
            <option value="KET_THUC">Đã kết thúc</option>
        </select>
    </div>

    <div id="hdLoading" style="padding:40px 0;text-align:center;color:#888;">
        <i class="bi bi-arrow-repeat"></i> Đang tải danh sách hợp đồng...
    </div>

    <div id="hdErrorBox" class="owner-form-note" style="display:none;background:#fdecea;color:#c0392b;">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <span id="hdErrorMsg">Có lỗi xảy ra.</span>
    </div>

    <table class="owner-table" id="hdTable" style="display:none;">
        <thead><tr><th>Người thuê</th><th>Phòng</th><th>Thời hạn</th><th>Tiền thuê</th><th>Trạng thái</th><th>Thao tác</th></tr></thead>
        <tbody id="hdTableBody"></tbody>
    </table>

    <div id="hdEmpty" class="owner-empty" hidden>
        <i class="bi bi-file-earmark-text"></i>
        <strong>Chưa có hợp đồng nào</strong>
        <span>Bấm "Tạo hợp đồng" để lập hợp đồng từ một yêu cầu thuê đã duyệt.</span>
    </div>
</section>

<%--
  Modal "Tạo hợp đồng" (form 2 cột + preview + Điều 1-13) giờ được nạp dùng chung
  từ resources/js/hopDongModal.js - cùng 1 component này cũng được dùng ở trang
  Tiến trình đặt phòng (dat-phong/tienTrinhDatPhong.jsp) khi chủ trọ bấm "Tạo hợp đồng"
  ở đúng bước đó, để không phải duy trì 2 bản form khác nhau.
--%>
<script src="${pageContext.request.contextPath}/resources/js/api.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/hopDongModal.js"></script>
<script>
(function () {
    'use strict';

    var currentUser = null;
    try { currentUser = JSON.parse(localStorage.getItem('user') || 'null'); } catch (e) { currentUser = null; }
    var token = localStorage.getItem('token');

    if (!currentUser || !currentUser.maNguoiDung || !token) {
        window.location.href = '${pageContext.request.contextPath}/login';
        return;
    }

    /* ---------- helpers ---------- */
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
    function daysBetween(a, b) {
        var MS = 24 * 60 * 60 * 1000;
        return Math.round((new Date(b) - new Date(a)) / MS);
    }

    /* =====================================================
       DANH SÁCH HỢP ĐỒNG (bảng)
       ===================================================== */
    var loadingEl = document.getElementById('hdLoading');
    var errorBox = document.getElementById('hdErrorBox');
    var errorMsg = document.getElementById('hdErrorMsg');
    var tableEl = document.getElementById('hdTable');
    var tbody = document.getElementById('hdTableBody');
    var emptyEl = document.getElementById('hdEmpty');
    var searchEl = document.getElementById('hdSearch');
    var statusFilterEl = document.getElementById('hdStatusFilter');

    var allContracts = [];

    function trangThaiNhom(hd) {
        var today = new Date().toISOString().slice(0, 10);
        if (['Chờ ký', 'Chờ người thuê ký', 'Chờ chủ trọ ký', 'Đã ký, chờ thanh toán'].indexOf(hd.trangThai) !== -1) return 'CHO_KY';
        if (hd.ngayKetThuc && hd.ngayKetThuc < today) return 'KET_THUC';
        if (hd.ngayKetThuc && daysBetween(today, hd.ngayKetThuc) <= 30 && daysBetween(today, hd.ngayKetThuc) >= 0) return 'SAP_HET_HAN';
        return 'HIEU_LUC';
    }
    function trangThaiPillInfo(nhom) {
        switch (nhom) {
            case 'HIEU_LUC': return { cls: 'green', label: 'Đang hiệu lực' };
            case 'SAP_HET_HAN': return { cls: 'orange', label: 'Sắp hết hạn' };
            case 'CHO_KY': return { cls: 'purple', label: 'Chờ ký' };
            default: return { cls: 'blue', label: 'Đã kết thúc' };
        }
    }

    function updateStats(list) {
        var c = { HIEU_LUC: 0, SAP_HET_HAN: 0, CHO_KY: 0, KET_THUC: 0 };
        list.forEach(function (hd) { c[trangThaiNhom(hd)]++; });
        document.getElementById('hdStatHieuLuc').textContent = c.HIEU_LUC;
        document.getElementById('hdStatSapHetHan').textContent = c.SAP_HET_HAN;
        document.getElementById('hdStatChoKy').textContent = c.CHO_KY;
        document.getElementById('hdStatKetThuc').textContent = c.KET_THUC;
    }

    function renderTable() {
        var kw = (searchEl.value || '').trim().toLowerCase();
        var statusVal = statusFilterEl.value;

        var list = allContracts.filter(function (hd) {
            var nhom = trangThaiNhom(hd);
            if (statusVal !== 'ALL' && nhom !== statusVal) return false;
            if (!kw) return true;
            var ten = hd.nguoiThue ? (hd.nguoiThue.hoTen || '') : '';
            var phong = hd.phong ? (hd.phong.tenPhong || '') : '';
            return (ten + ' ' + phong).toLowerCase().indexOf(kw) !== -1;
        });

        if (list.length === 0) {
            tableEl.style.display = 'none';
            emptyEl.hidden = false;
            return;
        }
        emptyEl.hidden = true;
        tableEl.style.display = '';

        tbody.innerHTML = list.map(function (hd) {
            var pill = trangThaiPillInfo(trangThaiNhom(hd));
            var tenKhach = hd.nguoiThue ? (hd.nguoiThue.hoTen || hd.nguoiThue.email) : '—';
            var tenPhong = hd.phong ? hd.phong.tenPhong : '—';
            var maYeuCau = hd.yeuCauThue ? hd.yeuCauThue.maYeuCau : null;
            var link = maYeuCau ? ('${pageContext.request.contextPath}/tien-trinh-dat-phong?id=' + maYeuCau) : '#';
            // Trang doc noi dung day du hop dong + tai PDF (xem
            // resources/js/hopDongChiTiet.js) - luon co san vi hang nao
            // trong bang nay cung la 1 HopDongDienTu that su (co maHopDong).
            var linkChiTietHopDong = '${pageContext.request.contextPath}/hop-dong/' + hd.maHopDong;

            return '<tr>'
                + '<td><b>' + escapeHtml(tenKhach) + '</b><small style="display:block;color:#64748b;">' + (hd.daKyNguoiThue ? 'Đã ký' : 'Chưa ký') + '</small></td>'
                + '<td>' + escapeHtml(tenPhong) + '</td>'
                + '<td>' + formatDate(hd.ngayBatDau) + ' → ' + formatDate(hd.ngayKetThuc) + '</td>'
                + '<td>' + formatMoney(hd.giaThue) + '</td>'
                + '<td><span class="status-pill ' + pill.cls + '">' + pill.label + '</span></td>'
                + '<td style="white-space:nowrap;">'
                    + '<a class="owner-btn light small" href="' + link + '">Tiến trình</a> '
                    + '<a class="owner-btn light small" href="' + linkChiTietHopDong + '"><i class="bi bi-file-earmark-pdf"></i> Xem/Tải PDF</a>'
                + '</td>'
                + '</tr>';
        }).join('');
    }

    function loadContracts() {
        loadingEl.style.display = '';
        errorBox.style.display = 'none';
        tableEl.style.display = 'none';
        emptyEl.hidden = true;

        apiFetch('/hop-dong/chu-tro/' + currentUser.maNguoiDung)
            .then(function (res) {
                if (!res) return;
                allContracts = res.sort(function (a, b) { return (b.maHopDong || 0) - (a.maHopDong || 0); });
                updateStats(allContracts);
                renderTable();
            })
            .catch(function (err) {
                errorMsg.textContent = (err && err.message) ? err.message : 'Không tải được danh sách hợp đồng.';
                errorBox.style.display = '';
            })
            .finally(function () { loadingEl.style.display = 'none'; });
    }

    searchEl.addEventListener('input', renderTable);
    statusFilterEl.addEventListener('change', renderTable);
    loadContracts();

    /* =====================================================
       MỞ MODAL TẠO HỢP ĐỒNG (chọn từ danh sách yêu cầu đã duyệt)
       ===================================================== */
    document.getElementById('openHdModalBtn').addEventListener('click', function () {
        openHopDongModal({
            mode: 'select',
            currentUser: currentUser,
            onSuccess: function () { loadContracts(); }
        });
    });
})();
</script>

<%@ include file="includes/footer.jspf" %>
