(function () {
    'use strict';

    var API = window.ADMIN_NGUOI_DUNG_ENDPOINT;

    var roles = [];
    var currentUserId = null;

    var EKYC_LABEL = {
        DA_XAC_MINH: { text: 'Đã xác minh', cls: 'ok' },
        CHO_DUYET: { text: 'Chờ duyệt', cls: 'wait' },
        CHUA_GUI: { text: 'Chưa gửi', cls: 'muted' }
    };

    document.addEventListener('DOMContentLoaded', init);

    function init() {
        try {
            var user = JSON.parse(localStorage.getItem('user') || 'null');
            currentUserId = user ? (user.maNguoiDung || null) : null;
        } catch (e) { currentUserId = null; }

        bindStaticEvents();
        loadRoles().then(function () {
            loadUsers();
        });
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

    // ===================== LOAD DATA =====================

    function loadRoles() {
        return fetch(API + '/vai-tro', { headers: authHeaders() })
            .then(function (res) {
                if (handleAuthError(res)) return [];
                if (!res.ok) throw new Error('HTTP ' + res.status);
                return res.json();
            })
            .then(function (data) {
                roles = Array.isArray(data) ? data : [];
                fillRoleSelect(document.getElementById('umRoleFilter'), true);
                fillRoleSelect(document.getElementById('umAddRoleSelect'), false);
            })
            .catch(function (err) {
                console.error('Không tải được danh sách vai trò:', err);
            });
    }

    function fillRoleSelect(select, withAllOption) {
        if (!select) return;
        var current = select.value;
        select.innerHTML = '';
        if (withAllOption) {
            var optAll = document.createElement('option');
            optAll.value = '';
            optAll.textContent = 'Tất cả vai trò';
            select.appendChild(optAll);
        }
        roles.forEach(function (role) {
            var opt = document.createElement('option');
            opt.value = role.maVaiTro;
            opt.textContent = role.tenVaiTro;
            select.appendChild(opt);
        });
        if (current) select.value = current;
    }

    function loadUsers() {
        var status = document.getElementById('umStatus');
        var body = document.getElementById('umTableBody');
        status.textContent = 'Đang tải dữ liệu...';
        status.classList.remove('error');

        var q = document.getElementById('umSearch').value.trim();
        var maVaiTro = document.getElementById('umRoleFilter').value;
        var trangThai = document.getElementById('umStatusFilter').value;

        var params = new URLSearchParams();
        if (q) params.set('q', q);
        if (maVaiTro) params.set('maVaiTro', maVaiTro);
        if (trangThai !== '') params.set('trangThai', trangThai);

        fetch(API + (params.toString() ? '?' + params.toString() : ''), { headers: authHeaders() })
            .then(function (res) {
                if (handleAuthError(res)) return null;
                if (res.status === 403) {
                    status.textContent = 'Tài khoản không có quyền xem dữ liệu này.';
                    status.classList.add('error');
                    body.innerHTML = '<tr class="table-empty-row"><td colspan="8">Không có quyền truy cập</td></tr>';
                    return null;
                }
                if (!res.ok) throw new Error('HTTP ' + res.status);
                return res.json();
            })
            .then(function (data) {
                if (data === null) return;
                renderTable(data);
                status.textContent = data.length + ' người dùng được tìm thấy · cập nhật từ hệ thống';
            })
            .catch(function (err) {
                status.textContent = 'Không thể tải dữ liệu. Vui lòng thử lại sau.';
                status.classList.add('error');
                body.innerHTML = '<tr class="table-empty-row"><td colspan="8">Lỗi tải dữ liệu</td></tr>';
                console.error('Load users error:', err);
            });
    }

    // ===================== RENDER =====================

    function renderTable(users) {
        var body = document.getElementById('umTableBody');
        if (!users || !users.length) {
            body.innerHTML = '<tr class="table-empty-row"><td colspan="8">Không tìm thấy người dùng nào</td></tr>';
            return;
        }

        body.innerHTML = users.map(function (u) {
            var ekyc = EKYC_LABEL[u.trangThaiEkyc] || EKYC_LABEL.CHUA_GUI;
            var violationCls = u.soLuotViPham === 0 ? 'zero' : (u.soLuotViPham < 3 ? 'some' : 'many');
            var statusPill = u.trangThai
                ? '<span class="pill ok">Hoạt động</span>'
                : '<span class="pill bad">Bị khóa</span>';
            var avatar = u.avatar
                ? escapeAttr(u.avatar)
                : 'https://ui-avatars.com/api/?name=' + encodeURIComponent(u.hoTen || '?') + '&background=6366f1&color=fff&size=64';
            var isSelf = currentUserId && String(currentUserId) === String(u.maNguoiDung);

            var lockBtn = u.trangThai
                ? '<button class="btn btn-danger" data-action="lock" data-id="' + u.maNguoiDung + '"' + (isSelf ? ' disabled title="Không thể tự khóa"' : '') + '>Khóa</button>'
                : '<button class="btn btn-success" data-action="unlock" data-id="' + u.maNguoiDung + '">Mở khóa</button>';

            return '' +
                '<tr>' +
                '<td><div class="user-cell"><img src="' + avatar + '" alt=""><div><div class="user-cell-name">' + escapeHtml(u.hoTen) + '</div><div class="user-cell-sub">#' + u.maNguoiDung + (isSelf ? ' · bạn' : '') + '</div></div></div></td>' +
                '<td>' + escapeHtml(u.email) + '</td>' +
                '<td>' + escapeHtml(u.soDienThoai || '—') + '</td>' +
                '<td><span class="pill info">' + escapeHtml(u.tenVaiTro || '—') + '</span></td>' +
                '<td><span class="pill ' + ekyc.cls + '">' + ekyc.text + '</span></td>' +
                '<td><span class="violation-count ' + violationCls + '">' + u.soLuotViPham + '</span></td>' +
                '<td>' + statusPill + '</td>' +
                '<td>' +
                '<button class="btn btn-light" data-action="detail" data-id="' + u.maNguoiDung + '">Chi tiết</button> ' +
                lockBtn +
                '</td>' +
                '</tr>';
        }).join('');
    }

    function escapeHtml(value) {
        if (value === null || value === undefined) return '';
        return String(value)
            .replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;').replace(/'/g, '&#39;');
    }

    function escapeAttr(value) { return escapeHtml(value); }

    // ===================== EVENTS =====================

    function bindStaticEvents() {
        document.getElementById('umSearchBtn').addEventListener('click', loadUsers);
        document.getElementById('umRefreshBtn').addEventListener('click', loadUsers);
        document.getElementById('umSearch').addEventListener('keydown', function (e) {
            if (e.key === 'Enter') { e.preventDefault(); loadUsers(); }
        });
        document.getElementById('umRoleFilter').addEventListener('change', loadUsers);
        document.getElementById('umStatusFilter').addEventListener('change', loadUsers);

        document.getElementById('umAddBtn').addEventListener('click', openAddModal);
        document.getElementById('umAddSubmit').addEventListener('click', submitAdd);

        document.querySelectorAll('[data-close]').forEach(function (btn) {
            btn.addEventListener('click', function () { closeModal(btn.getAttribute('data-close')); });
        });
        document.querySelectorAll('.modal-backdrop').forEach(function (backdrop) {
            backdrop.addEventListener('click', function (e) {
                if (e.target === backdrop) closeModal(backdrop.id);
            });
        });

        document.getElementById('umTableBody').addEventListener('click', function (e) {
            var btn = e.target.closest('button[data-action]');
            if (!btn) return;
            var id = btn.getAttribute('data-id');
            var action = btn.getAttribute('data-action');
            if (action === 'detail') openDetail(id);
            else if (action === 'lock') setTrangThai(id, false);
            else if (action === 'unlock') setTrangThai(id, true);
        });
    }

    function openModal(id) { document.getElementById(id).classList.add('show'); }
    function closeModal(id) { document.getElementById(id).classList.remove('show'); }

    // ===================== DETAIL / ROLE / LOCK / DELETE =====================

    function openDetail(id) {
        var bodyEl = document.getElementById('umDetailBody');
        bodyEl.innerHTML = '<p class="um-status">Đang tải...</p>';
        openModal('umDetailModal');

        fetch(API + '/' + id, { headers: authHeaders() })
            .then(function (res) {
                if (handleAuthError(res)) return null;
                if (!res.ok) throw new Error('HTTP ' + res.status);
                return res.json();
            })
            .then(function (u) {
                if (!u) return;
                renderDetail(u);
            })
            .catch(function (err) {
                bodyEl.innerHTML = '<p class="um-status error">Không tải được chi tiết người dùng.</p>';
                console.error('Detail error:', err);
            });
    }

    function renderDetail(u) {
        var bodyEl = document.getElementById('umDetailBody');
        var ekyc = EKYC_LABEL[u.trangThaiEkyc] || EKYC_LABEL.CHUA_GUI;
        var isSelf = currentUserId && String(currentUserId) === String(u.maNguoiDung);
        var avatar = u.avatar
            ? escapeAttr(u.avatar)
            : 'https://ui-avatars.com/api/?name=' + encodeURIComponent(u.hoTen || '?') + '&background=6366f1&color=fff&size=96';

        var roleOptions = roles.map(function (r) {
            return '<option value="' + r.maVaiTro + '"' + (String(r.maVaiTro) === String(u.maVaiTro) ? ' selected' : '') + '>' + escapeHtml(r.tenVaiTro) + '</option>';
        }).join('');

        bodyEl.innerHTML = '' +
            '<div class="user-cell" style="margin-bottom:18px">' +
            '<img src="' + avatar + '" style="width:56px;height:56px" alt="">' +
            '<div><div class="user-cell-name" style="font-size:15px">' + escapeHtml(u.hoTen) + '</div>' +
            '<div class="user-cell-sub">#' + u.maNguoiDung + (isSelf ? ' · Tài khoản đang đăng nhập' : '') + '</div></div>' +
            '</div>' +
            '<div class="form-error" id="umDetailError"></div>' +
            '<div class="detail-grid">' +
            detailItem('Email', u.email) +
            detailItem('Số điện thoại', u.soDienThoai || '—') +
            detailItem('Địa chỉ', u.diaChi || '—') +
            detailItem('Giới tính', u.gioiTinh === true ? 'Nam' : (u.gioiTinh === false ? 'Nữ' : '—')) +
            detailItem('Ngày sinh', u.ngaySinh || '—') +
            detailItem('Ngày đăng ký', formatDateTime(u.ngayDangKy)) +
            detailItem('eKYC', '<span class="pill ' + ekyc.cls + '">' + ekyc.text + '</span>', true) +
            detailItem('Số lượt bị báo cáo / vi phạm', String(u.soLuotViPham), true) +
            '</div>' +
            '<div class="form-row">' +
            '<label>Vai trò</label>' +
            '<select class="select" id="umDetailRoleSelect"' + (isSelf ? ' disabled' : '') + '>' + roleOptions + '</select>' +
            (isSelf ? '<small>Không thể tự đổi vai trò của chính mình.</small>' : '') +
            '</div>' +
            '<div style="display:flex;gap:8px;flex-wrap:wrap;margin-top:16px">' +
            '<button class="btn btn-primary" id="umDetailSaveRole">Lưu vai trò</button>' +
            (u.trangThai
                ? '<button class="btn btn-danger" id="umDetailToggleStatus"' + (isSelf ? ' disabled title="Không thể tự khóa"' : '') + '>Khóa tài khoản</button>'
                : '<button class="btn btn-success" id="umDetailToggleStatus">Mở khóa tài khoản</button>') +
            '<button class="btn btn-danger" id="umDetailDelete"' + (isSelf ? ' disabled title="Không thể tự xóa"' : '') + '>Xóa tài khoản</button>' +
            '</div>';

        document.getElementById('umDetailSaveRole').addEventListener('click', function () {
            var maVaiTro = document.getElementById('umDetailRoleSelect').value;
            changeRole(u.maNguoiDung, maVaiTro);
        });
        var toggleBtn = document.getElementById('umDetailToggleStatus');
        if (toggleBtn) {
            toggleBtn.addEventListener('click', function () {
                setTrangThai(u.maNguoiDung, !u.trangThai, true);
            });
        }
        document.getElementById('umDetailDelete').addEventListener('click', function () {
            deleteUser(u.maNguoiDung);
        });
    }

    function detailItem(label, value, full) {
        return '<div class="detail-item' + (full ? ' full' : '') + '"><span class="label">' + label + '</span><span class="value">' + value + '</span></div>';
    }

    function formatDateTime(value) {
        if (!value) return '—';
        try {
            var d = new Date(value);
            if (isNaN(d.getTime())) return value;
            return d.toLocaleString('vi-VN');
        } catch (e) { return value; }
    }

    function setTrangThai(id, trangThai, fromDetail) {
        var actionLabel = trangThai ? 'mở khóa' : 'khóa';
        if (!window.confirm('Xác nhận ' + actionLabel + ' tài khoản #' + id + '?')) return;

        fetch(API + '/' + id + '/trang-thai', {
            method: 'PUT',
            headers: authHeaders(true),
            body: JSON.stringify({ trangThai: trangThai })
        })
            .then(function (res) { return handleWriteResponse(res); })
            .then(function (result) {
                if (!result.ok) { showError(result.message, fromDetail); return; }
                loadUsers();
                if (fromDetail) openDetail(id); else closeModal('umDetailModal');
            })
            .catch(function (err) {
                showError('Không thể cập nhật trạng thái. Vui lòng thử lại.', fromDetail);
                console.error(err);
            });
    }

    function changeRole(id, maVaiTro) {
        if (!maVaiTro) return;
        fetch(API + '/' + id + '/vai-tro', {
            method: 'PUT',
            headers: authHeaders(true),
            body: JSON.stringify({ maVaiTro: Number(maVaiTro) })
        })
            .then(function (res) { return handleWriteResponse(res); })
            .then(function (result) {
                if (!result.ok) { showError(result.message, true); return; }
                loadUsers();
                openDetail(id);
            })
            .catch(function (err) {
                showError('Không thể đổi vai trò. Vui lòng thử lại.', true);
                console.error(err);
            });
    }

    function deleteUser(id) {
        if (!window.confirm('Xóa vĩnh viễn tài khoản #' + id + '? Hành động này không thể hoàn tác.')) return;

        fetch(API + '/' + id, { method: 'DELETE', headers: authHeaders() })
            .then(function (res) {
                if (handleAuthError(res)) return { ok: false, silent: true };
                if (res.status === 204) return { ok: true };
                return res.json().catch(function () { return {}; }).then(function (body) {
                    return { ok: false, message: body.message || ('HTTP ' + res.status) };
                });
            })
            .then(function (result) {
                if (result.silent) return;
                if (!result.ok) { showError(result.message, true); return; }
                closeModal('umDetailModal');
                loadUsers();
            })
            .catch(function (err) {
                showError('Không thể xóa tài khoản. Vui lòng thử lại.', true);
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

    function showError(message, inDetail) {
        var el = document.getElementById(inDetail ? 'umDetailError' : 'umAddError');
        if (!el) { window.alert(message || 'Đã xảy ra lỗi.'); return; }
        el.textContent = message || 'Đã xảy ra lỗi.';
        el.classList.add('show');
    }

    // ===================== ADD USER =====================

    function openAddModal() {
        document.getElementById('umAddForm').reset();
        var err = document.getElementById('umAddError');
        err.textContent = '';
        err.classList.remove('show');
        openModal('umAddModal');
    }

    function submitAdd() {
        var form = document.getElementById('umAddForm');
        if (!form.reportValidity()) return;

        var formData = new FormData(form);
        var payload = {
            hoTen: (formData.get('hoTen') || '').trim(),
            email: (formData.get('email') || '').trim(),
            soDienThoai: (formData.get('soDienThoai') || '').trim() || null,
            matKhau: formData.get('matKhau'),
            diaChi: (formData.get('diaChi') || '').trim() || null,
            vaiTro: { maVaiTro: Number(formData.get('maVaiTro')) }
        };

        var submitBtn = document.getElementById('umAddSubmit');
        submitBtn.disabled = true;

        fetch(API, { method: 'POST', headers: authHeaders(true), body: JSON.stringify(payload) })
            .then(function (res) {
                if (handleAuthError(res)) return { ok: false, silent: true };
                if (res.status === 201) return { ok: true };
                return res.json().catch(function () { return {}; }).then(function (body) {
                    return { ok: false, message: body.message || ('Tạo tài khoản thất bại (HTTP ' + res.status + ')') };
                });
            })
            .then(function (result) {
                submitBtn.disabled = false;
                if (result.silent) return;
                if (!result.ok) { showError(result.message, false); return; }
                closeModal('umAddModal');
                loadUsers();
            })
            .catch(function (err) {
                submitBtn.disabled = false;
                showError('Không thể tạo tài khoản. Vui lòng thử lại.', false);
                console.error(err);
            });
    }
})();
