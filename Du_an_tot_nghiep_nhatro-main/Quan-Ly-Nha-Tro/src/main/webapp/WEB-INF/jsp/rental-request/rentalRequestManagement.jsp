<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %><%@ include file="includes/header.jspf" %>
<div class="owner-page-head"><div><div class="eyebrow">NGƯỜI THUÊ</div><h1>Yêu cầu thuê</h1><p>Kiểm tra hồ sơ và phản hồi yêu cầu thuê phòng.</p></div></div>
<section class="owner-card">
    <div class="center-state" id="loadingState" style="text-align:center;padding:40px;color:#777;">Đang tải danh sách yêu cầu...</div>
    <div class="center-state hidden" id="errorState" style="text-align:center;padding:40px;color:#c0392b;">
        <div id="errorMessage">Không tải được danh sách.</div>
        <button id="retryBtn" style="margin-top:10px;background:#ff3345;color:#fff;border:none;padding:8px 16px;border-radius:8px;font-weight:700;cursor:pointer;">Thử lại</button>
    </div>
    <div class="hidden" id="emptyState" style="text-align:center;padding:40px;color:#999;">Chưa có yêu cầu thuê nào.</div>
    <div class="request-list hidden" id="requestList"></div>
</section>
<script src="${pageContext.request.contextPath}/resources/js/api.js"></script>
<script>
(function () {
    'use strict';

    var loadingEl = document.getElementById('loadingState');
    var errorEl = document.getElementById('errorState');
    var errorMessageEl = document.getElementById('errorMessage');
    var emptyEl = document.getElementById('emptyState');
    var listEl = document.getElementById('requestList');

    var currentUser = null;
    try { currentUser = JSON.parse(localStorage.getItem('user') || 'null'); } catch (e) { currentUser = null; }
    var token = localStorage.getItem('token');
    var ctx = '${pageContext.request.contextPath}';

    if (!currentUser || !currentUser.maNguoiDung || !token) {
        window.location.href = ctx + '/login';
        return;
    }

    function escapeHtml(v) {
        return String(v == null ? '' : v).replace(/[&<>"']/g, function (c) {
            return ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' })[c];
        });
    }
    function formatDate(v) {
        if (!v) return '—';
        var d = new Date(v);
        return isNaN(d.getTime()) ? v : d.toLocaleDateString('vi-VN');
    }
    function initials(name) {
        var parts = (name || '').trim().split(/\s+/);
        return ((parts[0] || '')[0] || '') + ((parts[parts.length - 1] || '')[0] || '');
    }
    function statusPill(trangThai) {
        if (trangThai === 'Đã duyệt') return '<span class="status-pill green">Đã chấp nhận</span>';
        if (trangThai === 'Từ chối') return '<span class="status-pill red">Đã từ chối</span>';
        return '<span class="status-pill orange">Chờ xử lý</span>';
    }

    function load() {
        loadingEl.classList.remove('hidden');
        errorEl.classList.add('hidden');
        emptyEl.classList.add('hidden');
        listEl.classList.add('hidden');

        apiFetch('/yeu-cau-thue/chu-tro/' + currentUser.maNguoiDung)
            .then(function (list) {
                loadingEl.classList.add('hidden');
                if (!list || list.length === 0) {
                    emptyEl.classList.remove('hidden');
                    return;
                }
                render(list);
                listEl.classList.remove('hidden');
            })
            .catch(function (err) {
                loadingEl.classList.add('hidden');
                errorMessageEl.textContent = (err && err.message) || 'Không tải được danh sách yêu cầu.';
                errorEl.classList.remove('hidden');
            });
    }

    function render(list) {
        listEl.innerHTML = list.map(function (yc) {
            var nguoiThue = yc.nguoiThue || {};
            var phong = yc.phong || {};
            var showActions = yc.trangThai === 'Chờ duyệt' || !yc.trangThai;

            return '<div class="request-item" data-id="' + yc.maYeuCau + '">' +
                '<div class="request-avatar">' + escapeHtml(initials(nguoiThue.hoTen)) + '</div>' +
                '<div class="request-main">' +
                    '<b>' + escapeHtml(nguoiThue.hoTen || 'Người thuê') + '</b>' +
                    '<span>Muốn thuê ' + escapeHtml(phong.tenPhong || ('phòng #' + phong.maPhong)) +
                        ' • Dự kiến vào ' + formatDate(yc.ngayMuonNhanPhong) + '</span>' +
                    '<small>Đã gửi ' + formatDate(yc.ngayGui) +
                        (yc.soDienThoaiLienHe ? ' • SĐT: ' + escapeHtml(yc.soDienThoaiLienHe) : '') + '</small>' +
                '</div>' +
                statusPill(yc.trangThai) +
                (showActions
                    ? '<button class="owner-btn light small btn-tu-choi">Từ chối</button>' +
                      '<button class="owner-btn primary small btn-duyet">Duyệt</button>'
                    : '<button class="owner-btn light small btn-xem-tien-trinh">Xem tiến trình</button>') +
            '</div>';
        }).join('');
    }

    listEl.addEventListener('click', function (e) {
        var item = e.target.closest('.request-item');
        if (!item) return;
        var id = item.dataset.id;

        if (e.target.classList.contains('btn-duyet')) {
            e.target.disabled = true;
            apiFetch('/yeu-cau-thue/' + id + '/duyet', { method: 'PUT' })
                .then(load)
                .catch(function (err) { alert((err && err.message) || 'Không duyệt được yêu cầu.'); e.target.disabled = false; });
        } else if (e.target.classList.contains('btn-tu-choi')) {
            if (!confirm('Từ chối yêu cầu thuê này?')) return;
            e.target.disabled = true;
            apiFetch('/yeu-cau-thue/' + id + '/tu-choi', { method: 'PUT' })
                .then(load)
                .catch(function (err) { alert((err && err.message) || 'Không thực hiện được.'); e.target.disabled = false; });
        } else if (e.target.classList.contains('btn-xem-tien-trinh')) {
            window.location.href = ctx + '/tien-trinh-dat-phong?id=' + id;
        }
    });

    document.getElementById('retryBtn').addEventListener('click', load);

    load();
})();
</script>
<%@ include file="includes/footer.jspf" %>