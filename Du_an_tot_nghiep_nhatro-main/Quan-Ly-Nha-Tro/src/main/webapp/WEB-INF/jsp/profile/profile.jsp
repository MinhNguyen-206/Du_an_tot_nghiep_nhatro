<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hồ sơ cá nhân - Room Connect</title>

    <%@ include file="/WEB-INF/jsp/common/head-assets.jspf" %>
</head>
<body class="bg-[#f7f7f6]">

<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<main class="max-w-[1000px] mx-auto px-4 py-10">

    <!-- Trạng thái đang tải / chưa đăng nhập -->
    <div id="rpLoading" class="py-24 text-center text-gray-400">
        <i class="fa-solid fa-spinner fa-spin text-2xl"></i>
        <p class="mt-3 text-sm">Đang tải hồ sơ...</p>
    </div>

    <!-- Nội dung hồ sơ (hiện ra sau khi đã xác thực đăng nhập) -->
    <div id="rpContent" class="hidden grid grid-cols-1 md:grid-cols-[280px_1fr] gap-6">

        <!-- CỘT TRÁI: THẺ TÓM TẮT -->
        <aside class="bg-white rounded-2xl shadow-card border border-gray-100 p-6 h-fit text-center">

            <div class="relative w-24 h-24 mx-auto">
                <img id="rpAvatar" src="https://ui-avatars.com/api/?name=U&background=ff641e&color=fff"
                     alt="Avatar" class="w-24 h-24 rounded-full object-cover border-4 border-cream shadow-sm">
            </div>

            <h2 id="rpName" class="mt-4 text-lg font-bold text-navy">—</h2>
            <p id="rpEmail" class="text-xs text-gray-400 mt-1 break-all">—</p>

            <span id="rpRoleBadge"
                  class="inline-block mt-3 px-3 py-1 rounded-full bg-orange-50 text-brand text-xs font-bold">
                —
            </span>

            <hr class="my-5 border-gray-100">

            <div class="text-left text-xs text-gray-500 space-y-2">
                <div class="flex items-center gap-2">
                    <i class="fa-regular fa-calendar w-4 text-gray-400"></i>
                    <span>Tham gia: <span id="rpJoinDate">—</span></span>
                </div>
                <div class="flex items-center gap-2">
                    <i class="fa-solid fa-circle-check w-4 text-green-500"></i>
                    <span id="rpStatus">Tài khoản đang hoạt động</span>
                </div>
            </div>
        </aside>

        <!-- CỘT PHẢI: FORM CHỈNH SỬA -->
        <section class="bg-white rounded-2xl shadow-card border border-gray-100 p-6 md:p-8">

            <h1 class="text-xl font-black text-navy">Thông tin cá nhân</h1>
            <p class="text-sm text-gray-400 mt-1 mb-6">Cập nhật thông tin liên hệ và hồ sơ của bạn.</p>

            <div id="rpAlert" class="hidden mb-5 px-4 py-3 rounded-xl text-sm font-medium"></div>

            <form id="rpForm" class="grid grid-cols-1 sm:grid-cols-2 gap-5">

                <div class="sm:col-span-2">
                    <label class="block text-xs font-bold text-navy mb-1.5">Họ và tên</label>
                    <input id="rpHoTen" name="hoTen" type="text" required
                           class="w-full h-11 rounded-xl border border-gray-200 px-4 text-sm outline-none focus:border-orange-300 focus:ring-2 focus:ring-orange-100 transition">
                </div>

                <div>
                    <label class="block text-xs font-bold text-navy mb-1.5">Email</label>
                    <input id="rpEmailInput" type="email" disabled
                           class="w-full h-11 rounded-xl border border-gray-100 bg-gray-50 px-4 text-sm text-gray-500 outline-none cursor-not-allowed">
                </div>

                <div>
                    <label class="block text-xs font-bold text-navy mb-1.5">Số điện thoại</label>
                    <input id="rpSoDienThoai" name="soDienThoai" type="tel" placeholder="09xxxxxxxx"
                           class="w-full h-11 rounded-xl border border-gray-200 px-4 text-sm outline-none focus:border-orange-300 focus:ring-2 focus:ring-orange-100 transition">
                </div>

                <div>
                    <label class="block text-xs font-bold text-navy mb-1.5">Ngày sinh</label>
                    <input id="rpNgaySinh" name="ngaySinh" type="date"
                           class="w-full h-11 rounded-xl border border-gray-200 px-4 text-sm outline-none focus:border-orange-300 focus:ring-2 focus:ring-orange-100 transition">
                </div>

                <div>
                    <label class="block text-xs font-bold text-navy mb-1.5">Giới tính</label>
                    <div class="flex items-center gap-5 h-11">
                        <label class="flex items-center gap-2 text-sm cursor-pointer">
                            <input type="radio" name="gioiTinh" value="true" id="rpGioiTinhNam" class="accent-brand">
                            Nam
                        </label>
                        <label class="flex items-center gap-2 text-sm cursor-pointer">
                            <input type="radio" name="gioiTinh" value="false" id="rpGioiTinhNu" class="accent-brand">
                            Nữ
                        </label>
                    </div>
                </div>

                <div class="sm:col-span-2">
                    <label class="block text-xs font-bold text-navy mb-1.5">Địa chỉ</label>
                    <input id="rpDiaChi" name="diaChi" type="text" placeholder="Số nhà, đường, phường/xã, tỉnh/thành..."
                           class="w-full h-11 rounded-xl border border-gray-200 px-4 text-sm outline-none focus:border-orange-300 focus:ring-2 focus:ring-orange-100 transition">
                </div>

                <div class="sm:col-span-2 flex items-center gap-3 pt-2">
                    <button type="submit" id="rpSaveBtn"
                            class="px-6 py-2.5 rounded-full bg-brand text-white text-sm font-bold hover:bg-brandDark transition shadow-sm">
                        <i class="fa-solid fa-floppy-disk mr-1.5"></i>Lưu thay đổi
                    </button>
                    <button type="button" id="rpResetBtn"
                            class="px-6 py-2.5 rounded-full border border-gray-200 text-sm font-bold text-navy hover:border-orange-300 hover:text-brand transition">
                        Hủy
                    </button>
                </div>

            </form>
        </section>
    </div>
</main>

<script src="${pageContext.request.contextPath}/resources/js/api.js"></script>
<script>
(function () {
    var loadingEl = document.getElementById('rpLoading');
    var contentEl = document.getElementById('rpContent');
    var alertEl = document.getElementById('rpAlert');

    function showAlert(message, type) {
        alertEl.textContent = message;
        alertEl.className = 'mb-5 px-4 py-3 rounded-xl text-sm font-medium ' +
            (type === 'error'
                ? 'bg-red-50 text-red-600'
                : 'bg-green-50 text-green-600');
        alertEl.classList.remove('hidden');
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    // ---- 1. Bắt buộc đăng nhập ----
    var token = localStorage.getItem('token');
    var userJson = localStorage.getItem('user');
    var user = null;
    try { user = userJson ? JSON.parse(userJson) : null; } catch (e) { user = null; }

    if (!token || !user) {
        window.location.href = '${pageContext.request.contextPath}/login?redirect=/profile';
        return;
    }

    function fillForm(nd) {
        document.getElementById('rpAvatar').src = nd.avatar
            ? nd.avatar
            : 'https://ui-avatars.com/api/?name=' + encodeURIComponent(nd.hoTen || 'U') + '&background=ff641e&color=fff';
        document.getElementById('rpName').textContent = nd.hoTen || 'Người dùng';
        document.getElementById('rpEmail').textContent = nd.email || '';
        document.getElementById('rpEmailInput').value = nd.email || '';

        var tenVaiTro = nd.vaiTro ? nd.vaiTro.tenVaiTro : null;
        document.getElementById('rpRoleBadge').textContent = tenVaiTro || 'Người dùng';

        if (nd.ngayDangKy) {
            var d = new Date(nd.ngayDangKy);
            if (!isNaN(d)) {
                document.getElementById('rpJoinDate').textContent = d.toLocaleDateString('vi-VN');
            }
        }
        document.getElementById('rpStatus').textContent = (nd.trangThai === false)
            ? 'Tài khoản đã bị khóa' : 'Tài khoản đang hoạt động';

        document.getElementById('rpHoTen').value = nd.hoTen || '';
        document.getElementById('rpSoDienThoai').value = nd.soDienThoai || '';
        document.getElementById('rpDiaChi').value = nd.diaChi || '';

        if (nd.ngaySinh) {
            document.getElementById('rpNgaySinh').value = nd.ngaySinh;
        }
        if (nd.gioiTinh === true) {
            document.getElementById('rpGioiTinhNam').checked = true;
        } else if (nd.gioiTinh === false) {
            document.getElementById('rpGioiTinhNu').checked = true;
        }
    }

    // ---- 2. Hiện tạm dữ liệu đã lưu trong localStorage, rồi làm mới từ API ----
    fillForm(user);
    loadingEl.classList.add('hidden');
    contentEl.classList.remove('hidden');
    contentEl.classList.add('grid');

    var userId = user.maNguoiDung;
    if (userId && typeof apiFetch === 'function') {
        apiFetch('/nguoi-dung/' + userId)
            .then(function (fresh) {
                if (fresh) {
                    fillForm(fresh);
                    localStorage.setItem('user', JSON.stringify(fresh));
                }
            })
            .catch(function () { /* Giữ nguyên dữ liệu cũ nếu API lỗi */ });
    }

    // ---- 3. Lưu thay đổi ----
    var form = document.getElementById('rpForm');
    form.addEventListener('submit', function (e) {
        e.preventDefault();
        alertEl.classList.add('hidden');

        var gioiTinhChecked = form.querySelector('input[name="gioiTinh"]:checked');

        var payload = {
            hoTen: document.getElementById('rpHoTen').value.trim(),
            soDienThoai: document.getElementById('rpSoDienThoai').value.trim(),
            diaChi: document.getElementById('rpDiaChi').value.trim(),
            ngaySinh: document.getElementById('rpNgaySinh').value || null,
            gioiTinh: gioiTinhChecked ? (gioiTinhChecked.value === 'true') : null,
            avatar: user.avatar || null
        };

        var saveBtn = document.getElementById('rpSaveBtn');
        saveBtn.disabled = true;
        saveBtn.classList.add('opacity-60');

        apiFetch('/nguoi-dung/' + userId, { method: 'PUT', body: payload })
            .then(function (updated) {
                if (updated) {
                    fillForm(updated);
                    localStorage.setItem('user', JSON.stringify(updated));
                    user = updated;
                }
                showAlert('Cập nhật hồ sơ thành công.', 'success');
            })
            .catch(function (err) {
                var msg = (err && err.status === 403)
                    ? 'Bạn không có quyền cập nhật hồ sơ này.'
                    : (err && err.message) ? err.message : 'Cập nhật thất bại, vui lòng thử lại.';
                showAlert(msg, 'error');
            })
            .finally(function () {
                saveBtn.disabled = false;
                saveBtn.classList.remove('opacity-60');
            });
    });

    document.getElementById('rpResetBtn').addEventListener('click', function () {
        fillForm(user);
        alertEl.classList.add('hidden');
    });
})();
</script>

</body>
</html>
