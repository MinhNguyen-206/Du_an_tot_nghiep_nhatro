<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %><%@ include file="includes/header.jspf" %>
<div class="owner-page-head">
    <div>
        <div class="eyebrow"><i class="bi bi-person-check"></i> QUẢN LÝ YÊU CẦU</div>
        <h1>Yêu cầu thuê</h1>
        <p>Duyệt yêu cầu thuê phòng / đặt cọc giữ phòng của khách. Sau khi duyệt, hai bên sẽ chuyển sang trang tiến trình đặt phòng (tạo hợp đồng, ký OTP, thanh toán cọc).</p>
    </div>
</div>

<div class="owner-stats compact" id="rrStats">
    <div class="owner-stat orange"><span>Chờ duyệt</span><strong id="rrCountCho">0</strong></div>
    <div class="owner-stat green"><span>Đã duyệt</span><strong id="rrCountDuyet">0</strong></div>
    <div class="owner-stat red"><span>Từ chối</span><strong id="rrCountTuChoi">0</strong></div>
</div>

<section class="owner-card">
    <div class="toolbar-owner">
        <select class="owner-input" id="rrFilter">
            <option value="ALL">Tất cả trạng thái</option>
            <option value="Chờ duyệt">Chờ duyệt</option>
            <option value="Đã duyệt">Đã duyệt</option>
            <option value="Từ chối">Từ chối</option>
        </select>
    </div>

    <div id="rrLoading" style="padding:40px 0;text-align:center;color:#888;">
        <i class="bi bi-arrow-repeat"></i> Đang tải danh sách yêu cầu...
    </div>

    <div id="rrErrorBox" class="owner-form-note" style="display:none;background:#fdecea;color:#c0392b;">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <span id="rrErrorMsg">Có lỗi xảy ra.</span>
    </div>

    <table class="owner-table" id="rrTable" style="display:none;">
        <thead>
        <tr>
            <th>Phòng / Nhà trọ</th>
            <th>Khách thuê</th>
            <th>SĐT liên hệ</th>
            <th>Hình thức</th>
            <th>Thời hạn thuê</th>
            <th>Ghi chú</th>
            <th>Ngày gửi</th>
            <th>Trạng thái</th>
            <th></th>
        </tr>
        </thead>
        <tbody id="rrTableBody"></tbody>
    </table>

    <div id="rrEmpty" class="owner-empty" hidden>
        <i class="bi bi-person-check"></i>
        <strong>Chưa có yêu cầu thuê nào</strong>
        <span>Khi khách hàng bấm "Thuê phòng ngay" hoặc "Đặt cọc giữ phòng", yêu cầu sẽ hiện ở đây.</span>
    </div>
</section>

<script src="${pageContext.request.contextPath}/resources/js/api.js"></script>
<script>
(function () {
    'use strict';

    var ctx = '${pageContext.request.contextPath}';

    var currentUser = null;
    try { currentUser = JSON.parse(localStorage.getItem('user') || 'null'); } catch (e) { currentUser = null; }
    var token = localStorage.getItem('token');

    if (!currentUser || !currentUser.maNguoiDung || !token) {
        window.location.href = ctx + '/login';
        return;
    }

    var loadingEl = document.getElementById('rrLoading');
    var errorBox = document.getElementById('rrErrorBox');
    var errorMsg = document.getElementById('rrErrorMsg');
    var tableEl = document.getElementById('rrTable');
    var tbody = document.getElementById('rrTableBody');
    var emptyEl = document.getElementById('rrEmpty');
    var filterEl = document.getElementById('rrFilter');

    var allRequests = [];

    function escapeHtml(v) {
        return String(v == null ? '' : v).replace(/[&<>"']/g, function (c) {
            return ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' })[c];
        });
    }

    function formatDate(v) {
        if (!v) return '—';
        var d = new Date(v);
        if (isNaN(d.getTime())) return v;
        return d.toLocaleDateString('vi-VN');
    }

    function statusPillClass(trangThai) {
        if (trangThai === 'Đã duyệt') return 'green';
        if (trangThai === 'Từ chối') return 'red';
        return 'orange'; // Chờ duyệt
    }

    function hinhThucLabel(yc) {
        if (yc.hinhThucThue === 'NHOM') {
            return 'Thuê nhóm' + (yc.soNguoiCung ? ' (' + escapeHtml(yc.soNguoiCung) + ' người)' : '');
        }
        return 'Thuê đơn';
    }

    function thoiHanLabel(yc) {
        if (!yc.thoiHanThue) return '—';
        return escapeHtml(yc.thoiHanThue) + ' ' + escapeHtml(yc.donViThoiHan || 'Tháng');
    }

    function updateCounts(list) {
        var cho = 0, duyet = 0, tuChoi = 0;
        list.forEach(function (yc) {
            if (yc.trangThai === 'Đã duyệt') duyet++;
            else if (yc.trangThai === 'Từ chối') tuChoi++;
            else cho++;
        });
        document.getElementById('rrCountCho').textContent = cho;
        document.getElementById('rrCountDuyet').textContent = duyet;
        document.getElementById('rrCountTuChoi').textContent = tuChoi;
    }

    function render() {
        var filterVal = filterEl.value;
        var list = allRequests.filter(function (yc) {
            return filterVal === 'ALL' || yc.trangThai === filterVal;
        });

        if (list.length === 0) {
            tableEl.style.display = 'none';
            emptyEl.hidden = false;
            return;
        }
        emptyEl.hidden = true;
        tableEl.style.display = '';

        tbody.innerHTML = list.map(function (yc) {
            var tenPhong = yc.phong ? yc.phong.tenPhong : '—';
            var tenNhaTro = (yc.phong && yc.phong.nhaTro) ? yc.phong.nhaTro.tenNhaTro : '';
            var tenKhach = yc.nguoiThue ? (yc.nguoiThue.hoTen || yc.nguoiThue.email) : '—';
            var actionBtn = (yc.trangThai === 'Chờ duyệt')
                ? '<a class="owner-btn primary small" href="' + ctx + '/tien-trinh-dat-phong?id=' + yc.maYeuCau + '">Xử lý yêu cầu</a>'
                : '<a class="owner-btn light small" href="' + ctx + '/tien-trinh-dat-phong?id=' + yc.maYeuCau + '">Xem tiến trình</a>';

            return '<tr>'
                + '<td><b>' + escapeHtml(tenPhong) + '</b><br><small>' + escapeHtml(tenNhaTro) + '</small></td>'
                + '<td>' + escapeHtml(tenKhach) + '</td>'
                + '<td>' + escapeHtml(yc.soDienThoaiLienHe) + '</td>'
                + '<td>' + hinhThucLabel(yc) + '</td>'
                + '<td>' + thoiHanLabel(yc) + '</td>'
                + '<td>' + (yc.ghiChu ? escapeHtml(yc.ghiChu) : '<span style="color:#aaa;">—</span>') + '</td>'
                + '<td>' + formatDate(yc.ngayGui) + '</td>'
                + '<td><span class="status-pill ' + statusPillClass(yc.trangThai) + '">' + escapeHtml(yc.trangThai) + '</span></td>'
                + '<td>' + actionBtn + '</td>'
                + '</tr>';
        }).join('');
    }

    function load() {
        loadingEl.style.display = '';
        errorBox.style.display = 'none';
        tableEl.style.display = 'none';
        emptyEl.hidden = true;

        apiFetch('/yeu-cau-thue/chu-tro/' + currentUser.maNguoiDung)
            .then(function (res) {
                if (!res) return;
                allRequests = res.sort(function (a, b) {
                    return new Date(b.ngayGui) - new Date(a.ngayGui);
                });
                updateCounts(allRequests);
                render();
            })
            .catch(function (err) {
                errorMsg.textContent = (err && err.message) ? err.message : 'Không tải được danh sách yêu cầu thuê.';
                errorBox.style.display = '';
            })
            .finally(function () {
                loadingEl.style.display = 'none';
            });
    }

    filterEl.addEventListener('change', render);
    load();
})();
</script>
<%@ include file="includes/footer.jspf" %>
