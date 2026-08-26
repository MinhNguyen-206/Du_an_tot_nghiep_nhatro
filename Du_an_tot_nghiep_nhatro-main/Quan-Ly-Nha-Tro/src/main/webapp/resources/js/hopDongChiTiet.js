/**
 * Trang "Xem chi tiết hợp đồng" (/hop-dong/{id}) - dùng chung cho cả Người thuê
 * (link "Xem chi tiết" trong /profile) và Chủ trọ (nút "Tải PDF" trong
 * /chu-tro/contracts).
 *
 * Nội dung hợp đồng render giống hệt bản xem trước trong modal ký hợp đồng
 * (resources/js/hopDongKyModal.js) để 2 nơi luôn khớp nhau, nhưng nguồn dữ
 * liệu ở đây lấy thẳng từ GET /api/hop-dong/{id} (đã JOIN FETCH sẵn
 * phong/nhaTro/chuTro/nguoiThue - xem HopDongDienTuController#getById) thay
 * vì phải gọi thêm API yêu cầu thuê như modal ký.
 *
 * Nút "Tải PDF" dùng thư viện html2pdf.js (tải qua CDN, không cần backend
 * sinh PDF) để xuất đúng nội dung đang hiển thị thành file .pdf tải về máy.
 */
(function () {
    'use strict';

    var ctx = document.body.dataset.contextPath || '';
    var maHopDong = document.body.dataset.maHopDong;

    var loadingEl = document.getElementById('loadingState');
    var errorEl = document.getElementById('errorState');
    var errorMessageEl = document.getElementById('errorMessage');
    var contentEl = document.getElementById('pageContent');
    var printArea = document.getElementById('cdPrintArea');
    var statusStrip = document.getElementById('statusStrip');
    var cdTitle = document.getElementById('cdTitle');
    var cdSub = document.getElementById('cdSub');
    var backLink = document.getElementById('backLink');

    var currentUser = null;
    try { currentUser = JSON.parse(localStorage.getItem('user') || 'null'); } catch (e) { currentUser = null; }
    var token = localStorage.getItem('token');

    if (!currentUser || !currentUser.maNguoiDung || !token) {
        if (confirm('Bạn cần đăng nhập để xem hợp đồng. Đăng nhập ngay?')) {
            window.location.href = ctx + '/login?redirect=' + encodeURIComponent(window.location.pathname);
        }
        return;
    }

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

    function showFatalError(msg) {
        loadingEl.classList.add('hidden');
        contentEl.classList.add('hidden');
        errorMessageEl.textContent = msg;
        errorEl.classList.remove('hidden');
    }

    function trangThaiPill(hd) {
        var today = new Date().toISOString().slice(0, 10);
        if (['Chờ ký', 'Chờ người thuê ký', 'Chờ chủ trọ ký', 'Đã ký, chờ thanh toán'].indexOf(hd.trangThai) !== -1) {
            return { cls: 'purple', label: 'Chờ ký / chờ thanh toán' };
        }
        if (hd.ngayKetThuc && hd.ngayKetThuc < today) return { cls: 'blue', label: 'Đã kết thúc' };
        var soNgayConLai = hd.ngayKetThuc ? Math.round((new Date(hd.ngayKetThuc) - new Date(today)) / 86400000) : null;
        if (soNgayConLai !== null && soNgayConLai >= 0 && soNgayConLai <= 30) return { cls: 'orange', label: 'Sắp hết hạn' };
        return { cls: 'green', label: 'Đang hiệu lực' };
    }

    function signCellHtml(daKy, ten, chuKy, ngayKy) {
        if (!daKy) {
            return '<div class="cd-sign-status cd-pending"><i class="bi bi-hourglass-split"></i> Chưa ký</div>';
        }
        var chuKyHtml;
        if (chuKy && chuKy.indexOf('data:image') === 0) {
            chuKyHtml = '<img src="' + chuKy + '" alt="Chữ ký">';
        } else {
            chuKyHtml = '<div class="cd-cursive">' + escapeHtml(chuKy || ten || '') + '</div>';
        }
        return chuKyHtml +
            '<div class="cd-sign-status"><i class="bi bi-check-circle-fill" style="color:#16a34a;"></i> Đã ký lúc ' + formatDateTime(ngayKy) + '</div>';
    }

    function renderContract(hd) {
        var phong = hd.phong || {};
        var nhaTro = phong.nhaTro || {};
        var chuTro = hd.chuTro || {};
        var nguoiThue = hd.nguoiThue || {};

        cdTitle.textContent = 'Hợp đồng thuê phòng #' + hd.maHopDong;
        cdSub.textContent = (phong.tenPhong || 'Phòng') + ' — ' + (nhaTro.tenNhaTro || '');

        var pill = trangThaiPill(hd);
        statusStrip.innerHTML = '<span class="status-pill ' + pill.cls + '">' + pill.label + '</span>' +
            '<span class="status-pill blue">' + formatDate(hd.ngayBatDau) + ' → ' + formatDate(hd.ngayKetThuc) + '</span>';

        printArea.innerHTML =
            '<div class="quochieu">CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</div>' +
            '<div class="tieungu">Độc lập - Tự do - Hạnh phúc</div>' +
            '<h1>HỢP ĐỒNG THUÊ NHÀ</h1>' +
            '<p style="text-align:center;">' + escapeHtml(hd.diaDiemKy || '..........') + ', ngày ' + formatDate(hd.ngayKy || hd.ngayBatDau) + '</p>' +

            '<h3>BÊN CHO THUÊ (BÊN A)</h3>' +
            '<p>Ông/Bà: <b>' + escapeHtml(chuTro.hoTen || '..........') + '</b> — Điện thoại: ' + escapeHtml(chuTro.soDienThoai || '..........') + '<br>' +
            'CMND/CCCD số: ' + escapeHtml(hd.cccdChuTro || '..........') + (hd.ngayCapCccdChuTro ? ', cấp ngày ' + formatDate(hd.ngayCapCccdChuTro) : '') + (hd.noiCapCccdChuTro ? ', tại ' + escapeHtml(hd.noiCapCccdChuTro) : '') + '<br>' +
            'Địa chỉ thường trú: ' + escapeHtml(hd.diaChiThuongTruChuTro || '..........') + '</p>' +

            '<h3>BÊN THUÊ (BÊN B)</h3>' +
            '<p>Ông/Bà: <b>' + escapeHtml(nguoiThue.hoTen || '..........') + '</b> — Điện thoại: ' + escapeHtml(nguoiThue.soDienThoai || '..........') + '<br>' +
            'CMND/CCCD số: ' + escapeHtml(hd.cccdNguoiThue || '..........') + (hd.ngayCapCccdNguoiThue ? ', cấp ngày ' + formatDate(hd.ngayCapCccdNguoiThue) : '') + (hd.noiCapCccdNguoiThue ? ', tại ' + escapeHtml(hd.noiCapCccdNguoiThue) : '') + '<br>' +
            'Địa chỉ thường trú: ' + escapeHtml(hd.diaChiThuongTruNguoiThue || '..........') + '</p>' +

            '<h3>Điều 1. Đối tượng và mục đích thuê</h3>' +
            '<p>Bên A đồng ý cho Bên B thuê phòng <b>' + escapeHtml(phong.tenPhong || '..........') + '</b> thuộc nhà trọ <b>' + escapeHtml(nhaTro.tenNhaTro || '..........') + '</b>' +
            (nhaTro.diaChi ? (', địa chỉ: ' + escapeHtml(nhaTro.diaChi)) : '') + '. Bên B sử dụng phòng thuê đúng mục đích để ở, không được sử dụng vào mục đích khác nếu chưa có sự đồng ý bằng văn bản của Bên A.</p>' +

            '<h3>Điều 2. Thời hạn thuê</h3>' +
            '<p>Thời hạn thuê từ ngày <b>' + formatDate(hd.ngayBatDau) + '</b> đến ngày <b>' + formatDate(hd.ngayKetThuc) + '</b>. Hết thời hạn này, nếu Bên B có nhu cầu tiếp tục thuê thì thực hiện theo chính sách gia hạn tại Điều 5.</p>' +

            '<h3>Điều 3. Giá thuê và phương thức thanh toán</h3>' +
            '<p>Giá thuê phòng là <b>' + formatMoney(hd.giaThue) + '</b>/tháng, chưa bao gồm tiền điện, nước, gửi xe, internet (tính theo thực tế sử dụng hàng tháng). Bên B thanh toán tiền thuê hàng tháng cho Bên A trước ngày 05 của tháng, bằng tiền mặt hoặc chuyển khoản.</p>' +

            '<h3>Điều 4. Tiền đặt cọc</h3>' +
            '<p>Bên B đặt cọc cho Bên A số tiền <b>' + formatMoney(hd.tienCoc) + '</b> để đảm bảo thực hiện hợp đồng. Tiền cọc được hoàn trả lại cho Bên B khi kết thúc hợp đồng, sau khi đã trừ các khoản chi phí phát sinh (nếu có) do lỗi của Bên B.</p>' +

            '<h3>Điều 5. Chính sách gia hạn sau khi hết hạn hợp đồng</h3>' +
            '<ul>' +
            '<li>5.1. Việc gia hạn sau khi hết thời hạn hợp đồng được áp dụng nếu: <b>' + (hd.choPhepGiaHan ? '☑' : '☐') + '</b> .</li>' +
            '<li>5.2. Bên B phải thông báo bằng văn bản cho Bên A về nhu cầu gia hạn trước ít nhất <u>' + escapeHtml(hd.soNgayBaoTruocGiaHan || 30) + '</u> ngày so với ngày hết hạn hợp đồng.</li>' +
            '<li>5.3. Số lần gia hạn tối đa là <u>' + escapeHtml(hd.soLanGiaHanToiDa || 3) + '</u> lần, trừ khi Hai Bên có thỏa thuận khác bằng văn bản.</li>' +
            '<li>5.4. Mức tăng giá thuê tối đa cho mỗi lần gia hạn không vượt quá <u>' + escapeHtml(hd.mucTangGiaToiDaPhanTram || 10) + '</u>% so với giá thuê liền kề trước đó.</li>' +
            '</ul>' +

            '<h3>Điều 6. Điều khoản chung</h3>' +
            '<p>Hợp đồng này có hiệu lực kể từ thời điểm cả hai bên hoàn tất ký điện tử (có xác thực OTP) và được lập thành bản điện tử duy nhất, lưu trữ trên hệ thống Room Connect, có giá trị pháp lý như bản giấy đối với hai bên.</p>' +

            '<table class="cd-sign-table"><tr>' +
            '<td>BÊN CHO THUÊ (BÊN A)' + signCellHtml(hd.daKyChuTro, chuTro.hoTen, hd.chuKyChuTro, hd.ngayKyChuTro) + '</td>' +
            '<td>BÊN THUÊ (BÊN B)' + signCellHtml(hd.daKyNguoiThue, nguoiThue.hoTen, hd.chuKyNguoiThue, hd.ngayKyNguoiThue) + '</td>' +
            '</tr></table>';

        // Nút "Quay lại" điều hướng đúng theo vai trò của người đang xem.
        if (currentUser.maNguoiDung === (chuTro && chuTro.maNguoiDung)) {
            backLink.href = ctx + '/chu-tro/contracts';
            backLink.textContent = 'Về Quản lý hợp đồng';
        } else {
            backLink.href = ctx + '/profile';
            backLink.textContent = 'Về hồ sơ của tôi';
        }
    }

    function loadContract() {
        loadingEl.classList.remove('hidden');
        errorEl.classList.add('hidden');
        contentEl.classList.add('hidden');

        apiFetch('/hop-dong/' + encodeURIComponent(maHopDong))
            .then(function (hd) {
                if (!hd) return;
                renderContract(hd);
                loadingEl.classList.add('hidden');
                contentEl.classList.remove('hidden');
            })
            .catch(function (err) {
                if (err && err.status === 403) {
                    showFatalError('Bạn không có quyền xem hợp đồng này.');
                } else if (err && err.status === 404) {
                    showFatalError('Không tìm thấy hợp đồng.');
                } else {
                    showFatalError((err && err.message) || 'Không tải được hợp đồng. Vui lòng thử lại.');
                }
            });
    }

    document.getElementById('retryBtn').addEventListener('click', loadContract);

    document.getElementById('btnPrint').addEventListener('click', function () {
        window.print();
    });

    document.getElementById('btnDownloadPdf').addEventListener('click', function () {
        var btn = this;
        if (typeof html2pdf === 'undefined') {
            alert('Không tải được công cụ xuất PDF. Vui lòng kiểm tra kết nối mạng và thử lại.');
            return;
        }
        var oldHtml = btn.innerHTML;
        btn.disabled = true;
        btn.innerHTML = '<i class="bi bi-hourglass-split"></i> Đang tạo PDF...';

        html2pdf().set({
            margin: 10,
            filename: 'HopDongThuePhong_' + maHopDong + '.pdf',
            image: { type: 'jpeg', quality: 0.98 },
            html2canvas: { scale: 2, useCORS: true },
            jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' },
            pagebreak: { mode: ['avoid-all', 'css', 'legacy'] }
        }).from(printArea).save().finally(function () {
            btn.disabled = false;
            btn.innerHTML = oldHtml;
        });
    });

    loadContract();
})();
