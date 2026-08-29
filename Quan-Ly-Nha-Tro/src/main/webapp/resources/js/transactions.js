(function () {
    'use strict';

    var API = window.ADMIN_GIAO_DICH_ENDPOINT;
    var allTransactions = [];
    var statusOptionsPopulated = false;

    document.addEventListener('DOMContentLoaded', init);

    function init() {
        bindStaticEvents();
        loadTransactions();
    }

    function authHeaders() {
        var token = localStorage.getItem('token');
        return token ? { Authorization: 'Bearer ' + token } : {};
    }

    function handleAuthError(response) {
        if (response.status === 401) {
            window.location.href = (window.CONTEXT_PATH || '') + '/login';
            return true;
        }
        return false;
    }

    // ===================== LOAD =====================

    function loadTransactions() {
        var status = document.getElementById('gdStatus');
        var body = document.getElementById('gdTableBody');
        status.textContent = 'Đang tải dữ liệu...';
        status.classList.remove('error');

        var q = document.getElementById('gdSearch').value.trim();
        var trangThai = document.getElementById('gdStatusFilter').value;

        var params = new URLSearchParams();
        if (q) params.set('q', q);
        if (trangThai) params.set('trangThai', trangThai);

        fetch(API + (params.toString() ? '?' + params.toString() : ''), { headers: authHeaders() })
            .then(function (res) {
                if (handleAuthError(res)) return null;
                if (res.status === 403) {
                    status.textContent = 'Tài khoản không có quyền xem dữ liệu này.';
                    status.classList.add('error');
                    body.innerHTML = '<tr class="table-empty-row"><td colspan="7">Không có quyền truy cập</td></tr>';
                    return null;
                }
                if (!res.ok) throw new Error('HTTP ' + res.status);
                return res.json();
            })
            .then(function (data) {
                if (data === null) return;
                allTransactions = data;
                if (!statusOptionsPopulated) {
                    populateStatusOptions(data);
                    statusOptionsPopulated = true;
                }
                renderTable(data);
                status.textContent = data.length + ' giao dịch · cập nhật từ hệ thống';
            })
            .catch(function (err) {
                status.textContent = 'Không thể tải dữ liệu. Vui lòng thử lại sau.';
                status.classList.add('error');
                body.innerHTML = '<tr class="table-empty-row"><td colspan="7">Lỗi tải dữ liệu</td></tr>';
                console.error('Load transactions error:', err);
            });
    }

    function populateStatusOptions(data) {
        var select = document.getElementById('gdStatusFilter');
        var current = select.value;
        var values = Array.from(new Set(data.map(function (gd) { return gd.trangThaiGiaoDich; }).filter(Boolean)));
        values.sort();
        values.forEach(function (v) {
            var opt = document.createElement('option');
            opt.value = v;
            opt.textContent = v;
            select.appendChild(opt);
        });
        if (current) select.value = current;
    }

    // ===================== RENDER =====================

    function renderTable(list) {
        var body = document.getElementById('gdTableBody');
        if (!list || !list.length) {
            body.innerHTML = '<tr class="table-empty-row"><td colspan="7">Không tìm thấy giao dịch nào</td></tr>';
            return;
        }

        body.innerHTML = list.map(function (gd) {
            var pillCls = statusPillClass(gd.trangThaiGiaoDich);
            var soTien = gd.soTien != null ? gd.soTien : gd.tongTienHoaDon;
            return '' +
                '<tr>' +
                '<td><div class="user-cell-name">#' + gd.maGiaoDich + '</div>' +
                (gd.maGiaoDichCongThanhToan ? '<div class="user-cell-sub">' + escapeHtml(gd.maGiaoDichCongThanhToan) + '</div>' : '') + '</td>' +
                '<td>' + escapeHtml(gd.tenNguoiThue || '—') + '</td>' +
                '<td>' + escapeHtml((gd.tenPhong || '') + (gd.tenNhaTro ? ' · ' + gd.tenNhaTro : '') || '—') + '</td>' +
                '<td>' + formatPrice(soTien) + '</td>' +
                '<td>' + formatDateTime(gd.ngayGiaoDich || gd.ngayThanhToan) + '</td>' +
                '<td><span class="pill ' + pillCls + '">' + escapeHtml(gd.trangThaiGiaoDich || '—') + '</span></td>' +
                '<td>' + (gd.maHoaDon ? '<button class="btn btn-light" data-action="invoice" data-id="' + gd.maGiaoDich + '">Xem</button>' : '—') + '</td>' +
                '</tr>';
        }).join('');
    }

    function statusPillClass(trangThai) {
        if (!trangThai) return 'muted';
        var t = trangThai.toLowerCase();
        if (t.indexOf('thanh_cong') > -1 || t.indexOf('thành công') > -1 || t.indexOf('success') > -1 || t.indexOf('paid') > -1) return 'ok';
        if (t.indexOf('that_bai') > -1 || t.indexOf('thất bại') > -1 || t.indexOf('fail') > -1 || t.indexOf('huy') > -1 || t.indexOf('hủy') > -1) return 'bad';
        return 'wait';
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
        document.getElementById('gdSearchBtn').addEventListener('click', loadTransactions);
        document.getElementById('gdRefreshBtn').addEventListener('click', loadTransactions);
        document.getElementById('gdSearch').addEventListener('keydown', function (e) {
            if (e.key === 'Enter') { e.preventDefault(); loadTransactions(); }
        });
        document.getElementById('gdStatusFilter').addEventListener('change', loadTransactions);

        document.querySelectorAll('[data-close]').forEach(function (btn) {
            btn.addEventListener('click', function () { closeModal(btn.getAttribute('data-close')); });
        });
        document.querySelectorAll('.modal-backdrop').forEach(function (backdrop) {
            backdrop.addEventListener('click', function (e) {
                if (e.target === backdrop) closeModal(backdrop.id);
            });
        });

        document.getElementById('gdTableBody').addEventListener('click', function (e) {
            var btn = e.target.closest('button[data-action="invoice"]');
            if (!btn) return;
            openInvoice(btn.getAttribute('data-id'));
        });

        document.getElementById('gdInvoicePrint').addEventListener('click', function () {
            window.print();
        });
    }

    function openModal(id) { document.getElementById(id).classList.add('show'); }
    function closeModal(id) { document.getElementById(id).classList.remove('show'); }

    // ===================== INVOICE =====================

    function openInvoice(maGiaoDich) {
        var bodyEl = document.getElementById('gdInvoiceBody');
        bodyEl.innerHTML = '<p class="um-status">Đang tải...</p>';
        openModal('gdInvoiceModal');

        fetch(API + '/' + maGiaoDich + '/hoa-don', { headers: authHeaders() })
            .then(function (res) {
                if (handleAuthError(res)) return null;
                if (!res.ok) throw new Error('HTTP ' + res.status);
                return res.json();
            })
            .then(function (hd) {
                if (!hd) return;
                renderInvoice(hd);
            })
            .catch(function (err) {
                bodyEl.innerHTML = '<p class="um-status error">Không tải được hóa đơn.</p>';
                console.error('Invoice error:', err);
            });
    }

    function renderInvoice(hd) {
        var bodyEl = document.getElementById('gdInvoiceBody');
        var tieuThuDien = (hd.chiSoDienMoi != null && hd.chiSoDienCu != null) ? (hd.chiSoDienMoi - hd.chiSoDienCu) : null;
        var tieuThuNuoc = (hd.chiSoNuocMoi != null && hd.chiSoNuocCu != null) ? (hd.chiSoNuocMoi - hd.chiSoNuocCu) : null;

        bodyEl.innerHTML = '' +
            '<div class="invoice-head">' +
            '<h2>HÓA ĐƠN THUÊ TRỌ #' + hd.maHoaDon + '</h2>' +
            '<p>Ngày lập: ' + formatDateTime(hd.ngayLap) + (hd.hanThanhToan ? ' · Hạn thanh toán: ' + hd.hanThanhToan : '') + '</p>' +
            '</div>' +
            '<div class="invoice-grid">' +
            invoiceItem('Chủ trọ', hd.tenChuTro) +
            invoiceItem('Người thuê', hd.tenNguoiThue) +
            invoiceItem('Nhà trọ', hd.tenNhaTro) +
            invoiceItem('Phòng', hd.tenPhong) +
            invoiceItem('Địa chỉ', hd.diaChiNhaTro, true) +
            '</div>' +
            '<table class="invoice-table">' +
            '<tr><th>Khoản mục</th><th>Chỉ số cũ</th><th>Chỉ số mới</th><th>Tiêu thụ</th></tr>' +
            '<tr><td>Tiền phòng' + (hd.thangChiSo ? ' (tháng ' + hd.thangChiSo + '/' + hd.namChiSo + ')' : '') + '</td><td colspan="3">' + formatPrice(hd.giaThue) + '</td></tr>' +
            '<tr><td>Điện</td><td>' + (hd.chiSoDienCu != null ? hd.chiSoDienCu : '—') + '</td><td>' + (hd.chiSoDienMoi != null ? hd.chiSoDienMoi : '—') + '</td><td>' + (tieuThuDien != null ? tieuThuDien : '—') + '</td></tr>' +
            '<tr><td>Nước</td><td>' + (hd.chiSoNuocCu != null ? hd.chiSoNuocCu : '—') + '</td><td>' + (hd.chiSoNuocMoi != null ? hd.chiSoNuocMoi : '—') + '</td><td>' + (tieuThuNuoc != null ? tieuThuNuoc : '—') + '</td></tr>' +
            '</table>' +
            '<div class="invoice-total">Tổng cộng: ' + formatPrice(hd.tongTien) + '</div>' +
            '<div class="invoice-grid" style="margin-top:16px">' +
            invoiceItem('Trạng thái hóa đơn', hd.trangThai) +
            invoiceItem('Đã thanh toán', hd.soTienDaThanhToan != null ? formatPrice(hd.soTienDaThanhToan) : 'Chưa thanh toán') +
            invoiceItem('Phương thức', hd.phuongThucThanhToan) +
            invoiceItem('Ngày thanh toán', formatDateTime(hd.ngayThanhToan)) +
            '</div>';
    }

    function invoiceItem(label, value, full) {
        return '<div class="' + (full ? 'full' : '') + '"><span class="label">' + label + '</span>' + escapeHtml(value || '—') + '</div>';
    }
})();
