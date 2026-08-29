(function () {
    'use strict';

    var API = window.ADMIN_DANH_GIA_ENDPOINT;
    var pendingWarnId = null;

    var STATUS_LABEL = {
        BINH_THUONG: { text: 'Bình thường', cls: 'ok' },
        BI_BAO_CAO: { text: 'Bị báo cáo', cls: 'wait' },
        DA_AN: { text: 'Đã ẩn', cls: 'bad' }
    };

    document.addEventListener('DOMContentLoaded', init);

    function init() {
        bindStaticEvents();
        loadReviews();
    }

    function authHeaders(json) {
        var token = localStorage.getItem('token');
        var headers = token ? { Authorization: 'Bearer ' + token } : {};
        if (json) headers['Content-Type'] = 'application/json';
        return headers;
    }

    function handleAuthError(response) {
        if (response.status === 401) {
            window.location.href = (window.CONTEXT_PATH || '') + '/login';
            return true;
        }
        return false;
    }

    function trangThai(dg) {
        if (dg.trangThai === false) return 'DA_AN';
        return dg.lyDoBaoCao ? 'BI_BAO_CAO' : 'BINH_THUONG';
    }

    // ===================== LOAD =====================

    function loadReviews() {
        var status = document.getElementById('rmStatus');
        var body = document.getElementById('rmTableBody');
        status.textContent = 'Đang tải dữ liệu...';
        status.classList.remove('error');

        var q = document.getElementById('rmSearch').value.trim();
        var trangThaiLoc = document.getElementById('rmStatusFilter').value;

        var params = new URLSearchParams();
        if (q) params.set('q', q);
        if (trangThaiLoc) params.set('trangThai', trangThaiLoc);

        fetch(API + (params.toString() ? '?' + params.toString() : ''), { headers: authHeaders() })
            .then(function (res) {
                if (handleAuthError(res)) return null;
                if (res.status === 403) {
                    status.textContent = 'Tài khoản không có quyền xem dữ liệu này.';
                    status.classList.add('error');
                    body.innerHTML = '<tr class="table-empty-row"><td colspan="6">Không có quyền truy cập</td></tr>';
                    return null;
                }
                if (!res.ok) throw new Error('HTTP ' + res.status);
                return res.json();
            })
            .then(function (data) {
                if (data === null) return;
                renderTable(data);
                status.textContent = data.length + ' đánh giá · cập nhật từ hệ thống';
            })
            .catch(function (err) {
                status.textContent = 'Không thể tải dữ liệu. Vui lòng thử lại sau.';
                status.classList.add('error');
                body.innerHTML = '<tr class="table-empty-row"><td colspan="6">Lỗi tải dữ liệu</td></tr>';
                console.error('Load reviews error:', err);
            });
    }

    // ===================== RENDER =====================

    function renderTable(reviews) {
        var body = document.getElementById('rmTableBody');
        if (!reviews || !reviews.length) {
            body.innerHTML = '<tr class="table-empty-row"><td colspan="6">Không tìm thấy đánh giá nào</td></tr>';
            return;
        }

        body.innerHTML = reviews.map(function (dg) {
            var st = STATUS_LABEL[trangThai(dg)];
            var actions;
            if (trangThai(dg) === 'DA_AN') {
                actions = '<button class="btn btn-success" data-action="restore" data-id="' + dg.maDanhGia + '">Khôi phục</button>';
            } else {
                actions = '<button class="btn btn-success" data-action="restore" data-id="' + dg.maDanhGia + '">Khôi phục</button>' +
                    ' <button class="btn btn-danger" data-action="hide" data-id="' + dg.maDanhGia + '">Ẩn</button>' +
                    ' <button class="btn btn-danger" data-action="warn" data-id="' + dg.maDanhGia + '">Ẩn &amp; cảnh cáo</button>';
            }

            return '' +
                '<tr>' +
                '<td>' + escapeHtml(dg.tenNguoiDung || '—') + '</td>' +
                '<td><div class="user-cell-name" style="font-weight:400;max-width:320px;white-space:normal">' + escapeHtml(truncate(dg.noiDung, 90)) + '</div>' +
                '<div class="user-cell-sub">' + escapeHtml(dg.tenNhaTro || '') + (dg.tenPhong ? ' · ' + escapeHtml(dg.tenPhong) : '') + '</div></td>' +
                '<td>' + (dg.soSao != null ? dg.soSao + '/5' : '—') + '</td>' +
                '<td>' + escapeHtml(dg.lyDoBaoCao || 'Không có') + '</td>' +
                '<td><span class="pill ' + st.cls + '">' + st.text + '</span></td>' +
                '<td>' + actions + '</td>' +
                '</tr>';
        }).join('');
    }

    function truncate(text, maxLen) {
        if (!text) return '';
        return text.length > maxLen ? text.slice(0, maxLen) + '…' : text;
    }

    function escapeHtml(value) {
        if (value === null || value === undefined) return '';
        return String(value)
            .replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;').replace(/'/g, '&#39;');
    }

    // ===================== EVENTS =====================

    function bindStaticEvents() {
        document.getElementById('rmSearchBtn').addEventListener('click', loadReviews);
        document.getElementById('rmRefreshBtn').addEventListener('click', loadReviews);
        document.getElementById('rmSearch').addEventListener('keydown', function (e) {
            if (e.key === 'Enter') { e.preventDefault(); loadReviews(); }
        });
        document.getElementById('rmStatusFilter').addEventListener('change', loadReviews);

        document.querySelectorAll('[data-close]').forEach(function (btn) {
            btn.addEventListener('click', function () { closeModal(btn.getAttribute('data-close')); });
        });
        document.querySelectorAll('.modal-backdrop').forEach(function (backdrop) {
            backdrop.addEventListener('click', function (e) {
                if (e.target === backdrop) closeModal(backdrop.id);
            });
        });

        document.getElementById('rmTableBody').addEventListener('click', function (e) {
            var btn = e.target.closest('button[data-action]');
            if (!btn) return;
            var id = btn.getAttribute('data-id');
            var action = btn.getAttribute('data-action');
            if (action === 'restore') restore(id);
            else if (action === 'hide') hide(id);
            else if (action === 'warn') openWarnModal(id);
        });

        document.getElementById('rmWarnSubmit').addEventListener('click', submitWarn);
    }

    function openModal(id) { document.getElementById(id).classList.add('show'); }
    function closeModal(id) { document.getElementById(id).classList.remove('show'); }

    // ===================== ACTIONS =====================

    function restore(id) {
        if (!window.confirm('Khôi phục đánh giá #' + id + ' về bình thường?')) return;
        fetch(API + '/' + id + '/khoi-phuc', { method: 'PUT', headers: authHeaders() })
            .then(handleWriteResponse)
            .then(function (result) {
                if (result.silent) return;
                if (!result.ok) { window.alert(result.message); return; }
                loadReviews();
            })
            .catch(function (err) {
                window.alert('Không thể khôi phục đánh giá. Vui lòng thử lại.');
                console.error(err);
            });
    }

    function hide(id) {
        if (!window.confirm('Ẩn đánh giá #' + id + ' khỏi hiển thị công khai?')) return;
        fetch(API + '/' + id + '/an', { method: 'PUT', headers: authHeaders() })
            .then(handleWriteResponse)
            .then(function (result) {
                if (result.silent) return;
                if (!result.ok) { window.alert(result.message); return; }
                loadReviews();
            })
            .catch(function (err) {
                window.alert('Không thể ẩn đánh giá. Vui lòng thử lại.');
                console.error(err);
            });
    }

    function openWarnModal(id) {
        pendingWarnId = id;
        document.getElementById('rmWarnReason').value = '';
        var err = document.getElementById('rmWarnError');
        err.textContent = '';
        err.classList.remove('show');
        openModal('rmWarnModal');
    }

    function submitWarn() {
        var reason = document.getElementById('rmWarnReason').value.trim();
        var errEl = document.getElementById('rmWarnError');
        if (!reason) {
            errEl.textContent = 'Vui lòng nhập lý do vi phạm.';
            errEl.classList.add('show');
            return;
        }
        var submitBtn = document.getElementById('rmWarnSubmit');
        submitBtn.disabled = true;

        fetch(API + '/' + pendingWarnId + '/an-va-canh-cao', {
            method: 'PUT',
            headers: authHeaders(true),
            body: JSON.stringify({ lyDo: reason })
        })
            .then(handleWriteResponse)
            .then(function (result) {
                submitBtn.disabled = false;
                if (result.silent) return;
                if (!result.ok) { errEl.textContent = result.message; errEl.classList.add('show'); return; }
                closeModal('rmWarnModal');
                loadReviews();
            })
            .catch(function (err) {
                submitBtn.disabled = false;
                errEl.textContent = 'Không thể xử lý. Vui lòng thử lại.';
                errEl.classList.add('show');
                console.error(err);
            });
    }

    function handleWriteResponse(res) {
        if (handleAuthError(res)) return { ok: false, silent: true };
        if (res.ok) return res.json().then(function () { return { ok: true }; }).catch(function () { return { ok: true }; });
        return res.json().catch(function () { return {}; }).then(function (body) {
            return { ok: false, message: body.message || ('Thao tác thất bại (HTTP ' + res.status + ')') };
        });
    }
})();
