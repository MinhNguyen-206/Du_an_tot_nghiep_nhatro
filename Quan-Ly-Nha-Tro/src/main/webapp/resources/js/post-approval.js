(function () {
    'use strict';

    var API = window.ADMIN_DANG_TIN_ENDPOINT;
    var pendingRejectId = null;

    var STATUS_LABEL = {
        CHO_DUYET: { text: 'Chờ duyệt', cls: 'wait' },
        DA_DUYET: { text: 'Đã duyệt', cls: 'ok' },
        TU_CHOI: { text: 'Từ chối', cls: 'bad' }
    };

    document.addEventListener('DOMContentLoaded', init);

    function init() {
        bindStaticEvents();
        loadPosts();
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

    // ===================== LOAD =====================

    function loadPosts() {
        var status = document.getElementById('paStatus');
        var body = document.getElementById('paTableBody');
        status.textContent = 'Đang tải dữ liệu...';
        status.classList.remove('error');

        var q = document.getElementById('paSearch').value.trim();
        var trangThaiDuyet = document.getElementById('paStatusFilter').value;

        var params = new URLSearchParams();
        if (q) params.set('q', q);
        if (trangThaiDuyet) params.set('trangThaiDuyet', trangThaiDuyet);

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
                status.textContent = data.length + ' bài đăng · cập nhật từ hệ thống';
            })
            .catch(function (err) {
                status.textContent = 'Không thể tải dữ liệu. Vui lòng thử lại sau.';
                status.classList.add('error');
                body.innerHTML = '<tr class="table-empty-row"><td colspan="6">Lỗi tải dữ liệu</td></tr>';
                console.error('Load posts error:', err);
            });
    }

    // ===================== RENDER =====================

    function renderTable(posts) {
        var body = document.getElementById('paTableBody');
        if (!posts || !posts.length) {
            body.innerHTML = '<tr class="table-empty-row"><td colspan="6">Không tìm thấy bài đăng nào</td></tr>';
            return;
        }

        body.innerHTML = posts.map(function (p) {
            var st = STATUS_LABEL[p.trangThaiDuyet] || STATUS_LABEL.CHO_DUYET;
            var quickActions = '<button class="btn btn-light" data-action="detail" data-id="' + p.maDangTin + '">Chi tiết</button>';
            if (p.trangThaiDuyet === 'CHO_DUYET') {
                quickActions += ' <button class="btn btn-success" data-action="approve" data-id="' + p.maDangTin + '">Duyệt</button>' +
                    ' <button class="btn btn-danger" data-action="reject" data-id="' + p.maDangTin + '">Từ chối</button>';
            }
            return '' +
                '<tr>' +
                '<td><div class="user-cell-name">' + escapeHtml(p.tieuDe) + '</div><div class="user-cell-sub">#' + p.maDangTin + ' · ' + escapeHtml(p.tenNhaTro || '—') + '</div></td>' +
                '<td>' + escapeHtml(p.tenNguoiDang || '—') + '</td>' +
                '<td>' + formatPrice(p.giaPhong) + '</td>' +
                '<td>' + formatDateTime(p.ngayDang) + '</td>' +
                '<td><span class="pill ' + st.cls + '">' + st.text + '</span></td>' +
                '<td>' + quickActions + '</td>' +
                '</tr>';
        }).join('');
    }

    function formatPrice(value) {
        if (value === null || value === undefined) return '—';
        var n = Number(value);
        if (isNaN(n)) return '—';
        return n.toLocaleString('vi-VN') + ' đ';
    }

    function formatDateTime(value) {
        if (!value) return '—';
        try {
            var d = new Date(value);
            if (isNaN(d.getTime())) return value;
            return d.toLocaleString('vi-VN');
        } catch (e) { return value; }
    }

    function escapeHtml(value) {
        if (value === null || value === undefined) return '';
        return String(value)
            .replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;').replace(/'/g, '&#39;');
    }

    // ===================== EVENTS =====================

    function bindStaticEvents() {
        document.getElementById('paSearchBtn').addEventListener('click', loadPosts);
        document.getElementById('paRefreshBtn').addEventListener('click', loadPosts);
        document.getElementById('paSearch').addEventListener('keydown', function (e) {
            if (e.key === 'Enter') { e.preventDefault(); loadPosts(); }
        });
        document.getElementById('paStatusFilter').addEventListener('change', loadPosts);

        document.querySelectorAll('[data-close]').forEach(function (btn) {
            btn.addEventListener('click', function () { closeModal(btn.getAttribute('data-close')); });
        });
        document.querySelectorAll('.modal-backdrop').forEach(function (backdrop) {
            backdrop.addEventListener('click', function (e) {
                if (e.target === backdrop) closeModal(backdrop.id);
            });
        });

        document.getElementById('paTableBody').addEventListener('click', function (e) {
            var btn = e.target.closest('button[data-action]');
            if (!btn) return;
            var id = btn.getAttribute('data-id');
            var action = btn.getAttribute('data-action');
            if (action === 'detail') openDetail(id);
            else if (action === 'approve') approve(id);
            else if (action === 'reject') openRejectModal(id);
        });

        document.getElementById('paRejectSubmit').addEventListener('click', submitReject);
    }

    function openModal(id) { document.getElementById(id).classList.add('show'); }
    function closeModal(id) { document.getElementById(id).classList.remove('show'); }

    // ===================== DETAIL =====================

    function openDetail(id) {
        var bodyEl = document.getElementById('paDetailBody');
        bodyEl.innerHTML = '<p class="um-status">Đang tải...</p>';
        openModal('paDetailModal');

        fetch(API + '/' + id, { headers: authHeaders() })
            .then(function (res) {
                if (handleAuthError(res)) return null;
                if (!res.ok) throw new Error('HTTP ' + res.status);
                return res.json();
            })
            .then(function (p) {
                if (!p) return;
                renderDetail(p);
            })
            .catch(function (err) {
                bodyEl.innerHTML = '<p class="um-status error">Không tải được chi tiết bài đăng.</p>';
                console.error('Detail error:', err);
            });
    }

    function renderDetail(p) {
        var bodyEl = document.getElementById('paDetailBody');
        var st = STATUS_LABEL[p.trangThaiDuyet] || STATUS_LABEL.CHO_DUYET;

        var gallery = (p.danhSachAnh && p.danhSachAnh.length)
            ? '<div style="display:flex;gap:8px;flex-wrap:wrap;margin-bottom:16px">' +
                p.danhSachAnh.map(function (src) {
                    return '<img src="' + escapeHtml(src) + '" alt="" style="width:110px;height:80px;object-fit:cover;border-radius:8px;border:1px solid #e2e8f0">';
                }).join('') +
              '</div>'
            : '<p class="um-status">Bài đăng chưa có hình ảnh.</p>';

        var rejectReasonBlock = p.trangThaiDuyet === 'TU_CHOI' && p.lyDoTuChoi
            ? '<div class="form-error show" style="margin-top:0">Lý do từ chối: ' + escapeHtml(p.lyDoTuChoi) + '</div>'
            : '';

        bodyEl.innerHTML = '' +
            '<div style="display:flex;justify-content:space-between;align-items:flex-start;gap:10px;margin-bottom:14px">' +
            '<div><h3 style="margin:0 0 4px;font-size:16px">' + escapeHtml(p.tieuDe) + '</h3>' +
            '<div class="user-cell-sub">#' + p.maDangTin + '</div></div>' +
            '<span class="pill ' + st.cls + '">' + st.text + '</span>' +
            '</div>' +
            rejectReasonBlock +
            gallery +
            '<div class="detail-grid">' +
            detailItem('Chủ trọ', escapeHtml(p.tenNguoiDang || '—')) +
            detailItem('Email / SĐT', escapeHtml((p.emailNguoiDang || '—') + ' · ' + (p.soDienThoaiNguoiDang || '—'))) +
            detailItem('Nhà trọ', escapeHtml(p.tenNhaTro || '—')) +
            detailItem('Địa chỉ', escapeHtml(p.diaChiNhaTro || '—')) +
            detailItem('Phòng', escapeHtml((p.tenPhong || '—') + (p.loaiPhong ? ' · ' + p.loaiPhong : ''))) +
            detailItem('Giá phòng', formatPrice(p.giaPhong)) +
            detailItem('Ngày đăng', formatDateTime(p.ngayDang)) +
            detailItem('Ngày hết hạn', formatDateTime(p.ngayHetHan)) +
            '</div>' +
            '<div class="form-row full"><label>Nội dung mô tả</label><div class="value" style="font-weight:400;white-space:pre-wrap">' + escapeHtml(p.noiDung || 'Không có mô tả.') + '</div></div>' +
            '<div style="display:flex;gap:8px;flex-wrap:wrap;margin-top:16px">' +
            (p.trangThaiDuyet !== 'DA_DUYET' ? '<button class="btn btn-success" id="paDetailApprove">Duyệt bài đăng</button>' : '') +
            (p.trangThaiDuyet !== 'TU_CHOI' ? '<button class="btn btn-danger" id="paDetailReject">Từ chối</button>' : '') +
            (p.trangThaiDuyet !== 'CHO_DUYET' ? '<button class="btn btn-light" id="paDetailReset">Đặt lại chờ duyệt</button>' : '') +
            '</div>';

        var approveBtn = document.getElementById('paDetailApprove');
        if (approveBtn) approveBtn.addEventListener('click', function () { approve(p.maDangTin, true); });
        var rejectBtn = document.getElementById('paDetailReject');
        if (rejectBtn) rejectBtn.addEventListener('click', function () { openRejectModal(p.maDangTin); });
        var resetBtn = document.getElementById('paDetailReset');
        if (resetBtn) resetBtn.addEventListener('click', function () { resetToPending(p.maDangTin); });
    }

    function detailItem(label, value) {
        return '<div class="detail-item"><span class="label">' + label + '</span><span class="value">' + value + '</span></div>';
    }

    // ===================== ACTIONS =====================

    function approve(id, fromDetail) {
        if (!window.confirm('Xác nhận duyệt bài đăng #' + id + '?')) return;
        fetch(API + '/' + id + '/duyet', { method: 'PUT', headers: authHeaders() })
            .then(function (res) { return handleWriteResponse(res); })
            .then(function (result) {
                if (result.silent) return;
                if (!result.ok) { window.alert(result.message); return; }
                loadPosts();
                if (fromDetail) openDetail(id); else closeModal('paDetailModal');
            })
            .catch(function (err) {
                window.alert('Không thể duyệt bài đăng. Vui lòng thử lại.');
                console.error(err);
            });
    }

    function resetToPending(id) {
        if (!window.confirm('Đặt bài đăng #' + id + ' về trạng thái chờ duyệt?')) return;
        fetch(API + '/' + id + '/cho-duyet-lai', { method: 'PUT', headers: authHeaders() })
            .then(function (res) { return handleWriteResponse(res); })
            .then(function (result) {
                if (result.silent) return;
                if (!result.ok) { window.alert(result.message); return; }
                loadPosts();
                openDetail(id);
            })
            .catch(function (err) {
                window.alert('Không thể cập nhật trạng thái. Vui lòng thử lại.');
                console.error(err);
            });
    }

    function openRejectModal(id) {
        pendingRejectId = id;
        document.getElementById('paRejectReason').value = '';
        var err = document.getElementById('paRejectError');
        err.textContent = '';
        err.classList.remove('show');
        openModal('paRejectModal');
    }

    function submitReject() {
        var reason = document.getElementById('paRejectReason').value.trim();
        var errEl = document.getElementById('paRejectError');
        if (!reason) {
            errEl.textContent = 'Vui lòng nhập lý do từ chối.';
            errEl.classList.add('show');
            return;
        }
        var submitBtn = document.getElementById('paRejectSubmit');
        submitBtn.disabled = true;

        fetch(API + '/' + pendingRejectId + '/tu-choi', {
            method: 'PUT',
            headers: authHeaders(true),
            body: JSON.stringify({ lyDo: reason })
        })
            .then(function (res) { return handleWriteResponse(res); })
            .then(function (result) {
                submitBtn.disabled = false;
                if (result.silent) return;
                if (!result.ok) { errEl.textContent = result.message; errEl.classList.add('show'); return; }
                closeModal('paRejectModal');
                closeModal('paDetailModal');
                loadPosts();
            })
            .catch(function (err) {
                submitBtn.disabled = false;
                errEl.textContent = 'Không thể từ chối bài đăng. Vui lòng thử lại.';
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