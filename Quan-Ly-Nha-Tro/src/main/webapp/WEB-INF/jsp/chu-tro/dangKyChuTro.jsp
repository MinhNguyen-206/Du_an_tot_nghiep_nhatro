<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký Chủ trọ - Room Connect</title>

    <%@ include file="/WEB-INF/jsp/common/head-assets.jspf" %>
</head>
<body class="bg-[#f7f7f6]">

<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<main class="max-w-[820px] mx-auto px-4 py-10">

    <!-- Trạng thái đang tải / chưa đăng nhập -->
    <div id="dkLoading" class="py-24 text-center text-gray-400">
        <i class="fa-solid fa-spinner fa-spin text-2xl"></i>
        <p class="mt-3 text-sm">Đang tải...</p>
    </div>

    <!-- Đã là Chủ trọ -->
    <div id="dkAlreadyLandlord" class="hidden bg-white rounded-2xl shadow-card border border-gray-100 p-10 text-center">
        <i class="fa-solid fa-house-chimney-user text-4xl text-brand"></i>
        <h1 class="text-xl font-black text-navy mt-4">Bạn đã là Chủ trọ</h1>
        <p class="text-sm text-gray-500 mt-2">Truy cập kênh quản lý chủ trọ để đăng tin và quản lý phòng trọ.</p>
        <a href="${pageContext.request.contextPath}/chu-tro"
           class="inline-block mt-5 px-6 py-2.5 rounded-full bg-brand text-white text-sm font-bold hover:bg-brandDark transition">
            Vào kênh Chủ trọ
        </a>
    </div>

    <!-- Có yêu cầu đang chờ duyệt -->
    <div id="dkPending" class="hidden bg-white rounded-2xl shadow-card border border-gray-100 p-10 text-center">
        <i class="fa-solid fa-hourglass-half text-4xl text-amber-500"></i>
        <h1 class="text-xl font-black text-navy mt-4">Yêu cầu của bạn đang chờ duyệt</h1>
        <p class="text-sm text-gray-500 mt-2">
            Chúng tôi đã nhận được thông tin xác minh của bạn vào lúc
            <span id="dkPendingDate" class="font-semibold text-navy">—</span>.
            Quản trị viên sẽ xét duyệt trong thời gian sớm nhất, kết quả sẽ được gửi qua email.
        </p>
    </div>

    <!-- Yêu cầu vừa bị từ chối -->
    <div id="dkRejected" class="hidden bg-white rounded-2xl shadow-card border border-gray-100 p-6 mb-6">
        <div class="flex items-start gap-3">
            <i class="fa-solid fa-circle-exclamation text-red-500 mt-0.5"></i>
            <div>
                <h2 class="text-sm font-bold text-navy">Yêu cầu trước đó đã bị từ chối</h2>
                <p id="dkRejectedReason" class="text-sm text-gray-500 mt-1">—</p>
                <p class="text-xs text-gray-400 mt-1">Bạn có thể chỉnh sửa thông tin bên dưới và gửi lại yêu cầu mới.</p>
            </div>
        </div>
    </div>

    <!-- Form đăng ký -->
    <div id="dkFormWrap" class="hidden">

        <div class="bg-white rounded-2xl shadow-card border border-gray-100 p-6 md:p-8 mb-6">
            <h1 class="text-xl font-black text-navy">Đăng ký trở thành Chủ trọ</h1>
            <p class="text-sm text-gray-500 mt-1">
                Vui lòng cung cấp thông tin xác minh danh tính và bất động sản để quản trị viên
                xét duyệt. Tài khoản của bạn sẽ được nâng cấp lên <strong>Chủ trọ</strong> sau khi
                yêu cầu được duyệt.
            </p>
        </div>

        <div id="dkAlert" class="hidden mb-5 px-4 py-3 rounded-xl text-sm font-medium"></div>

        <form id="dkForm" class="space-y-6">

            <!-- 1. Thông tin cá nhân / Chủ sở hữu -->
            <section class="bg-white rounded-2xl shadow-card border border-gray-100 p-6 md:p-8">
                <h2 class="text-base font-black text-navy flex items-center gap-2">
                    <span class="w-6 h-6 rounded-full bg-brand text-white text-xs flex items-center justify-center">1</span>
                    Thông tin cá nhân / Chủ sở hữu
                </h2>
                <p class="text-xs text-gray-400 mt-1 mb-5">Dùng để xác minh danh tính thật, chống lừa đảo.</p>

                <div class="grid grid-cols-1 sm:grid-cols-2 gap-5">
                    <div class="sm:col-span-2">
                        <label class="block text-xs font-bold text-navy mb-1.5">Họ và tên (theo CCCD/CMND) *</label>
                        <input id="dkHoTenCCCD" type="text" required placeholder="Nguyễn Văn A"
                               class="w-full h-11 rounded-xl border border-gray-200 px-4 text-sm outline-none focus:border-orange-300 focus:ring-2 focus:ring-orange-100 transition">
                    </div>

                    <div>
                        <label class="block text-xs font-bold text-navy mb-1.5">Số CCCD/CMND *</label>
                        <input id="dkSoCCCD" type="text" required placeholder="012345678901"
                               class="w-full h-11 rounded-xl border border-gray-200 px-4 text-sm outline-none focus:border-orange-300 focus:ring-2 focus:ring-orange-100 transition">
                    </div>

                    <div>
                        <label class="block text-xs font-bold text-navy mb-1.5">Số điện thoại chính chủ</label>
                        <input id="dkSoDienThoai" type="tel" placeholder="09xxxxxxxx"
                               class="w-full h-11 rounded-xl border border-gray-100 bg-gray-50 px-4 text-sm text-gray-500 outline-none cursor-not-allowed" disabled>
                        <p class="text-[11px] text-gray-400 mt-1">Lấy từ hồ sơ tài khoản. Cập nhật tại trang Hồ sơ cá nhân nếu cần đổi.</p>
                    </div>

                    <div>
                        <label class="block text-xs font-bold text-navy mb-1.5">Ảnh CCCD/CMND - mặt trước *</label>
                        <input id="dkAnhCCCDTruoc" type="url" required placeholder="Dán link ảnh (vd: đã upload lên Google Drive/Imgur...)"
                               class="w-full h-11 rounded-xl border border-gray-200 px-4 text-sm outline-none focus:border-orange-300 focus:ring-2 focus:ring-orange-100 transition">
                    </div>

                    <div>
                        <label class="block text-xs font-bold text-navy mb-1.5">Ảnh CCCD/CMND - mặt sau *</label>
                        <input id="dkAnhCCCDSau" type="url" required placeholder="Dán link ảnh mặt sau"
                               class="w-full h-11 rounded-xl border border-gray-200 px-4 text-sm outline-none focus:border-orange-300 focus:ring-2 focus:ring-orange-100 transition">
                    </div>
                </div>
            </section>

            <!-- 2. Xác thực bất động sản -->
            <section class="bg-white rounded-2xl shadow-card border border-gray-100 p-6 md:p-8">
                <h2 class="text-base font-black text-navy flex items-center gap-2">
                    <span class="w-6 h-6 rounded-full bg-brand text-white text-xs flex items-center justify-center">2</span>
                    Xác thực bất động sản (Phòng / Nhà trọ)
                </h2>
                <p class="text-xs text-gray-400 mt-1 mb-5">Thông tin phòng/nhà trọ đầu tiên bạn dự định cho thuê.</p>

                <div class="grid grid-cols-1 gap-5">
                    <div>
                        <label class="block text-xs font-bold text-navy mb-1.5">Địa chỉ chính xác *</label>
                        <input id="dkDiaChiBDS" type="text" required
                               placeholder="Số nhà, tên đường, phường/xã, quận/huyện, tỉnh/thành phố"
                               class="w-full h-11 rounded-xl border border-gray-200 px-4 text-sm outline-none focus:border-orange-300 focus:ring-2 focus:ring-orange-100 transition">
                    </div>

                    <div>
                        <label class="block text-xs font-bold text-navy mb-1.5">Ảnh/Video thực tế</label>
                        <input id="dkAnhThucTe" type="text"
                               placeholder="Dán 1 hoặc nhiều link ảnh (mặt tiền, phòng, nhà vệ sinh, chỗ để xe...), cách nhau bởi dấu phẩy"
                               class="w-full h-11 rounded-xl border border-gray-200 px-4 text-sm outline-none focus:border-orange-300 focus:ring-2 focus:ring-orange-100 transition">
                    </div>

                    <div>
                        <label class="block text-xs font-bold text-navy mb-1.5">Giấy tờ chứng minh quyền sở hữu/quản lý</label>
                        <input id="dkGiayToSoHuu" type="url"
                               placeholder="Link ảnh Sổ đỏ/sổ hồng, hợp đồng ủy quyền quản lý, hoặc hợp đồng thuê nhà nguyên căn"
                               class="w-full h-11 rounded-xl border border-gray-200 px-4 text-sm outline-none focus:border-orange-300 focus:ring-2 focus:ring-orange-100 transition">
                    </div>

                    <div>
                        <label class="block text-xs font-bold text-navy mb-1.5">Giấy tờ pháp lý bổ sung (nếu có)</label>
                        <input id="dkGiayToBoSung" type="url"
                               placeholder="Link giấy chứng nhận PCCC / đăng ký kinh doanh dịch vụ lưu trú..."
                               class="w-full h-11 rounded-xl border border-gray-200 px-4 text-sm outline-none focus:border-orange-300 focus:ring-2 focus:ring-orange-100 transition">
                    </div>

                    <div>
                        <label class="block text-xs font-bold text-navy mb-1.5">Ghi chú thêm</label>
                        <textarea id="dkGhiChu" rows="3" placeholder="Thông tin khác bạn muốn gửi kèm cho quản trị viên..."
                                  class="w-full rounded-xl border border-gray-200 px-4 py-2.5 text-sm outline-none focus:border-orange-300 focus:ring-2 focus:ring-orange-100 transition resize-none"></textarea>
                    </div>
                </div>
            </section>

            <div class="flex items-start gap-2.5 px-1">
                <input type="checkbox" id="dkCamKet" required class="mt-1 accent-brand">
                <label for="dkCamKet" class="text-xs text-gray-500 leading-relaxed">
                    Tôi cam kết các thông tin trên là chính xác, thuộc quyền sở hữu/quản lý hợp pháp của tôi,
                    và đồng ý để Room Connect sử dụng thông tin này cho mục đích xác minh tài khoản Chủ trọ.
                </label>
            </div>

            <div class="flex items-center gap-3">
                <button type="submit" id="dkSubmitBtn"
                        class="px-6 py-2.5 rounded-full bg-brand text-white text-sm font-bold hover:bg-brandDark transition shadow-sm">
                    <i class="fa-solid fa-paper-plane mr-1.5"></i>Gửi yêu cầu xét duyệt
                </button>
                <a href="${pageContext.request.contextPath}/"
                   class="px-6 py-2.5 rounded-full border border-gray-200 text-sm font-bold text-navy hover:border-orange-300 hover:text-brand transition">
                    Hủy
                </a>
            </div>
        </form>
    </div>
</main>

<script src="${pageContext.request.contextPath}/resources/js/api.js"></script>
<script>
(function () {
    var loadingEl = document.getElementById('dkLoading');
    var alreadyEl = document.getElementById('dkAlreadyLandlord');
    var pendingEl = document.getElementById('dkPending');
    var rejectedEl = document.getElementById('dkRejected');
    var formWrapEl = document.getElementById('dkFormWrap');
    var alertEl = document.getElementById('dkAlert');

    function showAlert(message, type) {
        alertEl.textContent = message;
        alertEl.className = 'mb-5 px-4 py-3 rounded-xl text-sm font-medium ' +
            (type === 'error' ? 'bg-red-50 text-red-600' : 'bg-green-50 text-green-600');
        alertEl.classList.remove('hidden');
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    // ---- Bắt buộc đăng nhập ----
    var token = localStorage.getItem('token');
    var userJson = localStorage.getItem('user');
    var user = null;
    try { user = userJson ? JSON.parse(userJson) : null; } catch (e) { user = null; }

    if (!token || !user) {
        window.location.href = '${pageContext.request.contextPath}/login?redirect=/dang-ky-chu-tro';
        return;
    }

    var userId = user.maNguoiDung;

    function hideAllStates() {
        loadingEl.classList.add('hidden');
        alreadyEl.classList.add('hidden');
        pendingEl.classList.add('hidden');
        formWrapEl.classList.add('hidden');
    }

    function tenVaiTro(nd) {
        return nd && nd.vaiTro ? (nd.vaiTro.tenVaiTro || '') : '';
    }

    function khoiTao(freshUser) {
        var currentRole = tenVaiTro(freshUser).toLowerCase();
        if (currentRole.indexOf('chủ trọ') > -1) {
            hideAllStates();
            alreadyEl.classList.remove('hidden');
            return;
        }

        if (typeof apiFetch !== 'function') {
            hideAllStates();
            formWrapEl.classList.remove('hidden');
            return;
        }

        apiFetch('/yeu-cau-chu-tro/nguoi-dung/' + userId + '/moi-nhat')
            .then(function (yeuCau) {
                hideAllStates();
                if (yeuCau && yeuCau.trangThai === 'CHO_DUYET') {
                    pendingEl.classList.remove('hidden');
                    if (yeuCau.ngayGui) {
                        var d = new Date(yeuCau.ngayGui);
                        if (!isNaN(d)) {
                            document.getElementById('dkPendingDate').textContent = d.toLocaleString('vi-VN');
                        }
                    }
                    return;
                }

                if (yeuCau && yeuCau.trangThai === 'TU_CHOI') {
                    rejectedEl.classList.remove('hidden');
                    document.getElementById('dkRejectedReason').textContent =
                        yeuCau.lyDoTuChoi || 'Không có lý do cụ thể được ghi lại.';
                }

                formWrapEl.classList.remove('hidden');
                document.getElementById('dkSoDienThoai').value = freshUser.soDienThoai || '';
                document.getElementById('dkHoTenCCCD').value = freshUser.hoTen || '';
            })
            .catch(function () {
                hideAllStates();
                formWrapEl.classList.remove('hidden');
                document.getElementById('dkSoDienThoai').value = freshUser.soDienThoai || '';
                document.getElementById('dkHoTenCCCD').value = freshUser.hoTen || '';
            });
    }

    khoiTao(user);
    if (userId && typeof apiFetch === 'function') {
        apiFetch('/nguoi-dung/' + userId)
            .then(function (fresh) {
                if (fresh) {
                    localStorage.setItem('user', JSON.stringify(fresh));
                    user = fresh;
                }
            })
            .catch(function () { /* giữ nguyên dữ liệu cũ nếu API lỗi */ });
    }

    // ---- Gửi form ----
    var form = document.getElementById('dkForm');
    form.addEventListener('submit', function (e) {
        e.preventDefault();
        alertEl.classList.add('hidden');

        var payload = {
            hoTenCCCD: document.getElementById('dkHoTenCCCD').value.trim(),
            soCCCD: document.getElementById('dkSoCCCD').value.trim(),
            anhCCCDMatTruoc: document.getElementById('dkAnhCCCDTruoc').value.trim(),
            anhCCCDMatSau: document.getElementById('dkAnhCCCDSau').value.trim(),
            diaChiBDS: document.getElementById('dkDiaChiBDS').value.trim(),
            anhThucTe: document.getElementById('dkAnhThucTe').value.trim(),
            giayToSoHuu: document.getElementById('dkGiayToSoHuu').value.trim(),
            giayToBoSung: document.getElementById('dkGiayToBoSung').value.trim(),
            ghiChuNguoiDung: document.getElementById('dkGhiChu').value.trim()
        };

        var submitBtn = document.getElementById('dkSubmitBtn');
        submitBtn.disabled = true;
        submitBtn.classList.add('opacity-60');

        apiFetch('/yeu-cau-chu-tro', { method: 'POST', body: payload })
            .then(function () {
                showAlert('Gửi yêu cầu thành công! Quản trị viên sẽ xét duyệt trong thời gian sớm nhất.', 'success');
                hideAllStates();
                pendingEl.classList.remove('hidden');
                document.getElementById('dkPendingDate').textContent = new Date().toLocaleString('vi-VN');
            })
            .catch(function (err) {
                var msg = (err && err.data && err.data.message) || (err && err.message) || 'Gửi yêu cầu thất bại, vui lòng thử lại.';
                showAlert(msg, 'error');
            })
            .finally(function () {
                submitBtn.disabled = false;
                submitBtn.classList.remove('opacity-60');
            });
    });
})();
</script>

</body>
</html>
