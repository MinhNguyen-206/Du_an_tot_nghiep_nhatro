(function () {
    'use strict';

    var ctx = document.body.dataset.contextPath || '';
    var token = localStorage.getItem('token');
    var user = null;
    try { user = JSON.parse(localStorage.getItem('user') || 'null'); } catch (e) { user = null; }

    var loading = document.getElementById('profileLoading');
    var layout = document.getElementById('profileLayout');
    var errorEl = document.getElementById('profileError');
    var currentUser = user;
    var profileData = null;

    if (!token || !user || !user.maNguoiDung) {
        window.location.href = ctx + '/login?redirect=/profile';
        return;
    }

    function qs(id) { return document.getElementById(id); }
    function escapeHtml(value) {
        return String(value == null ? '' : value).replace(/[&<>"']/g, function (c) { return ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]); });
    }
    function formatMoney(value) {
        var n = Number(value || 0);
        if (n >= 1000000) return (n / 1000000).toFixed(1).replace('.0', '') + 'Tr';
        return new Intl.NumberFormat('vi-VN').format(n) + 'đ';
    }
    function formatDate(value) {
        if (!value) return '—';
        var parts = String(value).split('-');
        if (parts.length === 3) return parts[2] + '/' + parts[1] + '/' + parts[0];
        var d = new Date(value);
        return isNaN(d) ? String(value) : d.toLocaleDateString('vi-VN');
    }
    function shortLocation(value) {
        if (!value) return 'Địa chỉ đang cập nhật';
        return value.length > 62 ? value.slice(0, 59) + '...' : value;
    }
    function imageUrl(value) {
        return value || 'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=900&q=80';
    }
    function amenityIcon(name) {
        var n = String(name || '').toLowerCase();
        if (n.includes('wifi') || n.includes('internet')) return 'fa-wifi';
        if (n.includes('điều hòa') || n.includes('may lanh') || n.includes('máy lạnh')) return 'fa-snowflake';
        if (n.includes('bếp')) return 'fa-kitchen-set';
        if (n.includes('giặt')) return 'fa-shirt';
        if (n.includes('thang máy')) return 'fa-elevator';
        return 'fa-circle-check';
    }
    function roomCard(room, saved) {
        var amenities = (room.tienIch || []).slice(0, 3).map(function (x) {
            return '<span class="amenity"><i class="fa-solid ' + amenityIcon(x) + '"></i>' + escapeHtml(x) + '</span>';
        }).join('');
        if (!amenities) amenities = '<span class="amenity"><i class="fa-solid fa-house"></i>' + escapeHtml(room.loaiPhong || 'Phòng trọ') + '</span>';
        return '<article class="room-card" data-room-id="' + escapeHtml(room.maPhong) + '">' +
            '<div class="room-image-wrap"><img loading="lazy" src="' + escapeHtml(imageUrl(room.hinhAnh)) + '" alt="' + escapeHtml(room.tenPhong) + '" onerror="this.src=\'https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=900&q=80\'">' +
            '<span class="room-badge">Mới đăng</span>' +
            (saved ? '<button type="button" class="heart-btn unsave-room" title="Bỏ lưu"><i class="fa-solid fa-heart"></i></button>' : '') +
            '</div><div class="room-body"><div class="room-price">' + formatMoney(room.giaPhong) + '<span style="font-weight:600;font-size:9px">/Tháng</span></div>' +
            '<div class="room-title" title="' + escapeHtml(room.tenPhong) + '">' + escapeHtml(room.tenPhong) + '</div>' +
            '<div class="room-location"><i class="fa-solid fa-location-dot"></i><span>' + escapeHtml(shortLocation(room.diaChi)) + '</span></div>' +
            '<div class="amenities">' + amenities + '</div></div></article>';
    }
    function historyCard(room) {
        return '<article class="history-card" data-room-id="' + escapeHtml(room.maPhong) + '">' +
            '<div class="history-image"><img loading="lazy" src="' + escapeHtml(imageUrl(room.hinhAnh)) + '" alt="' + escapeHtml(room.tenPhong) + '" onerror="this.src=\'https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=900&q=80\'"><span class="heart-small"><i class="fa-regular fa-heart"></i></span></div>' +
            '<div class="history-main"><h3>' + escapeHtml(room.tenPhong || room.tenNhaTro) + '</h3><div class="location"><i class="fa-solid fa-location-dot"></i> ' + escapeHtml(shortLocation(room.diaChi)) + '</div><div class="history-amenities">' +
            ((room.tienIch || []).slice(0, 2).map(function (x) { return '<span><i class="fa-solid ' + amenityIcon(x) + '"></i> ' + escapeHtml(x) + '</span>'; }).join('') || '<span><i class="fa-solid fa-house"></i> ' + escapeHtml(room.loaiPhong || 'Phòng trọ') + '</span>') +
            '</div></div><div class="history-price">' + formatMoney(room.giaPhong) + '<small>/Tháng</small></div></article>';
    }
    function dayName(date) { return ['CN','Thứ 2','Thứ 3','Thứ 4','Thứ 5','Thứ 6','Thứ 7'][new Date(date + 'T00:00:00').getDay()]; }
    function monthName(date) { return 'Tháng ' + String(new Date(date + 'T00:00:00').getMonth() + 1); }
    function appointmentCard(item) {
        return '<article class="appointment-card"><div class="date-box"><div class="dow">' + escapeHtml(dayName(item.ngayHen)) + '</div><div class="day">' + escapeHtml(String(item.ngayHen || '').split('-')[2] || '—') + '</div><div class="month">' + escapeHtml(monthName(item.ngayHen)) + '</div></div>' +
            '<div class="appointment-main"><h3>' + escapeHtml(item.tenNhaTro || item.tenPhong || 'Lịch xem phòng') + '</h3><p>Địa chỉ: ' + escapeHtml(item.diaDiem || 'Đang cập nhật') + '</p></div>' +
            '<div class="appointment-side"><div><div class="appointment-time">' + escapeHtml((item.gioHen || '').slice(0,5)) + ' ' + (item.gioHen ? '' : '') + '</div><div class="appointment-owner">Phòng: ' + escapeHtml(item.tenPhong || '—') + '</div><div class="appointment-owner">' + (item.trangThai === true ? 'Đã xác nhận' : 'Chờ xác nhận') + '</div></div>' +
            '<div class="appointment-actions"><button type="button" class="reschedule-btn" data-id="' + escapeHtml(item.maLichHen) + '">Dời lịch</button><button type="button" class="cancel-appointment-btn" data-id="' + escapeHtml(item.maLichHen) + '">Hủy lịch</button></div></div></article>';
    }
    function contractCard(item) {
        var status = item.trangThai || 'Đang hiệu lực';
        return '<article class="contract-card"><div class="contract-icon"><i class="fa-regular fa-file-lines"></i></div><div class="contract-main"><strong>Hợp đồng thuê phòng #' + escapeHtml(item.maHopDong) + '</strong><span>Trạng thái: ' + escapeHtml(status) + '</span></div>' +
            (item.fileHopDong ? '<a class="contract-btn" href="' + escapeHtml(item.fileHopDong) + '" target="_blank" rel="noopener">Xem chi tiết</a>' : '<button type="button" class="contract-btn" onclick="alert(\'Hợp đồng chưa có file đính kèm.\')">Xem chi tiết</button>') +
            '<div class="contract-dates"><div class="contract-date"><span>Ngày bắt đầu</span><strong>' + formatDate(item.ngayBatDau) + '</strong></div><div class="contract-date"><span>Ngày kết thúc</span><strong>' + formatDate(item.ngayKetThuc) + '</strong></div></div></article>';
    }

    function fillUser(nd) {
        currentUser = nd || currentUser;
        var avatar = currentUser.avatar || ('https://ui-avatars.com/api/?name=' + encodeURIComponent(currentUser.hoTen || 'U') + '&background=5e574c&color=fff&size=160');
        qs('profileAvatar').src = avatar;
        qs('profileName').textContent = currentUser.hoTen || 'Người dùng';
        qs('profileEmail').textContent = currentUser.email || '—';
        qs('infoHoTen').textContent = currentUser.hoTen || '—';
        qs('infoEmail').textContent = currentUser.email || '—';
        qs('infoPhone').textContent = currentUser.soDienThoai || 'Chưa cập nhật';
        qs('infoBirthday').textContent = formatDate(currentUser.ngaySinh);
        qs('infoGender').textContent = currentUser.gioiTinh === true ? 'Nam' : (currentUser.gioiTinh === false ? 'Nữ' : 'Chưa cập nhật');
        qs('infoJoinDate').textContent = formatDate(String(currentUser.ngayDangKy || '').slice(0,10));
        qs('infoAddress').textContent = currentUser.diaChi || 'Chưa cập nhật';
    }

    function render(data) {
        profileData = data;
        fillUser(data.nguoiDung || currentUser);
        qs('savedCount').textContent = String(data.soPhongDaLuu || 0).padStart(2, '0');
        qs('appointmentCount').textContent = String(data.soLichHenSapToi || 0).padStart(2, '0');

        var saved = data.phongDaLuu || [];
        qs('savedRooms').innerHTML = saved.map(function (r) { return roomCard(r, true); }).join('');
        qs('savedEmpty').classList.toggle('hidden', saved.length !== 0);

        var history = data.lichSuXemPhong || [];
        qs('historyRooms').innerHTML = history.slice(0, 4).map(historyCard).join('');
        qs('historyEmpty').classList.toggle('hidden', history.length !== 0);

        var contracts = data.hopDongGanDay || [];
        qs('contractsList').innerHTML = contracts.length ? '<div class="contract-card-list">' + contracts.slice(0, 3).map(contractCard).join('</div><div class="contract-separator"></div><div class="contract-card-list">') + '</div>' : '';
        qs('contractsEmpty').classList.toggle('hidden', contracts.length !== 0);

        var appointments = data.lichHenSapToi || [];
        qs('appointmentsList').innerHTML = appointments.map(appointmentCard).join('');
        qs('appointmentsEmpty').classList.toggle('hidden', appointments.length !== 0);

        bindDynamicButtons();
    }

    function showError(message) {
        loading.classList.add('hidden');
        layout.classList.add('hidden');
        errorEl.textContent = message;
        errorEl.classList.remove('hidden');
    }

    function loadProfile() {
        apiFetch('/profile/' + encodeURIComponent(user.maNguoiDung))
            .then(function (data) {
                if (!data) return;
                render(data);
                loading.classList.add('hidden');
                errorEl.classList.add('hidden');
                layout.classList.remove('hidden');
                localStorage.setItem('user', JSON.stringify(data.nguoiDung || currentUser));
            })
            .catch(function (err) {
                showError((err && err.message) || 'Không thể tải hồ sơ. Vui lòng thử lại.');
            });
    }

    function logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        document.cookie = 'jwt=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT';
        window.location.href = ctx + '/login';
    }

    function openProfileModal() {
        qs('editHoTen').value = currentUser.hoTen || '';
        qs('editEmail').value = currentUser.email || '';
        qs('editPhone').value = currentUser.soDienThoai || '';
        qs('editBirthday').value = currentUser.ngaySinh || '';
        qs('editAddress').value = currentUser.diaChi || '';
        document.querySelectorAll('input[name="editGender"]').forEach(function (r) { r.checked = (currentUser.gioiTinh != null && String(currentUser.gioiTinh) === r.value); });
        qs('profileAlert').classList.add('hidden');
        qs('profileModal').classList.remove('hidden');
    }
    function closeProfileModal() { qs('profileModal').classList.add('hidden'); }

    function bindDynamicButtons() {
        document.querySelectorAll('.unsave-room').forEach(function (btn) {
            btn.onclick = function () {
                var roomId = btn.closest('[data-room-id]').getAttribute('data-room-id');
                btn.disabled = true;
                apiFetch('/profile/' + user.maNguoiDung + '/saved-rooms/' + roomId, { method: 'DELETE' })
                    .then(function () { loadProfile(); })
                    .catch(function () { btn.disabled = false; alert('Không thể bỏ lưu phòng lúc này.'); });
            };
        });
        document.querySelectorAll('.reschedule-btn').forEach(function (btn) {
            btn.onclick = function () {
                var item = (profileData.lichHenSapToi || []).find(function (x) { return String(x.maLichHen) === String(btn.dataset.id); });
                if (!item) return;
                qs('appointmentId').value = item.maLichHen;
                qs('appointmentDate').value = item.ngayHen || '';
                qs('appointmentTime').value = (item.gioHen || '').slice(0,5);
                qs('appointmentPlace').value = item.diaDiem || '';
                qs('appointmentModal').classList.remove('hidden');
            };
        });
        document.querySelectorAll('.cancel-appointment-btn').forEach(function (btn) {
            btn.onclick = function () {
                if (!confirm('Bạn có chắc muốn hủy lịch hẹn này không?')) return;
                apiFetch('/profile/' + user.maNguoiDung + '/appointments/' + btn.dataset.id, { method: 'DELETE' })
                    .then(function () { loadProfile(); })
                    .catch(function (err) { alert((err && err.message) || 'Không thể hủy lịch hẹn.'); });
            };
        });
    }

    qs('profileForm').addEventListener('submit', function (e) {
        e.preventDefault();
        var checked = document.querySelector('input[name="editGender"]:checked');
        var btn = qs('profileSaveBtn');
        btn.disabled = true;
        apiFetch('/nguoi-dung/' + user.maNguoiDung, {
            method: 'PUT',
            body: {
                hoTen: qs('editHoTen').value.trim(),
                soDienThoai: qs('editPhone').value.trim(),
                diaChi: qs('editAddress').value.trim(),
                ngaySinh: qs('editBirthday').value || null,
                gioiTinh: checked ? checked.value === 'true' : null,
                avatar: currentUser.avatar || null
            }
        }).then(function (updated) {
            if (updated) {
                currentUser = updated;
                localStorage.setItem('user', JSON.stringify(updated));
                fillUser(updated);
            }
            closeProfileModal();
            loadProfile();
        }).catch(function (err) {
            var alert = qs('profileAlert');
            alert.textContent = (err && err.message) || 'Không thể cập nhật hồ sơ.';
            alert.style.background = '#fff0f0'; alert.style.color = '#b42318'; alert.classList.remove('hidden');
        }).finally(function () { btn.disabled = false; });
    });

    qs('appointmentForm').addEventListener('submit', function (e) {
        e.preventDefault();
        apiFetch('/profile/' + user.maNguoiDung + '/appointments/' + qs('appointmentId').value, {
            method: 'PUT',
            body: { ngayHen: qs('appointmentDate').value, gioHen: qs('appointmentTime').value + ':00', diaDiem: qs('appointmentPlace').value }
        }).then(function () {
            qs('appointmentModal').classList.add('hidden');
            loadProfile();
        }).catch(function (err) { alert((err && err.message) || 'Không thể dời lịch hẹn.'); });
    });

    document.querySelectorAll('[data-close-modal]').forEach(function (el) { el.addEventListener('click', closeProfileModal); });
    document.querySelectorAll('[data-close-appointment]').forEach(function (el) { el.addEventListener('click', function () { qs('appointmentModal').classList.add('hidden'); }); });
    qs('editProfileBtn').addEventListener('click', openProfileModal);
    document.querySelectorAll('[data-action="edit-profile"]').forEach(function (el) { el.addEventListener('click', openProfileModal); });
    qs('sidebarLogoutBtn').addEventListener('click', logout);
    qs('headerLogoutBtn').addEventListener('click', logout);
    qs('headerUserBtn').addEventListener('click', function () { qs('headerUserMenu').classList.toggle('hidden'); });
    document.addEventListener('click', function (e) {
        if (!e.target.closest('.profile-header-user')) qs('headerUserMenu').classList.add('hidden');
    });
    document.querySelectorAll('.profile-menu-item[data-target]').forEach(function (btn) {
        btn.addEventListener('click', function () {
            document.querySelectorAll('.profile-menu-item').forEach(function (x) { x.classList.remove('active'); });
            btn.classList.add('active');
            var target = qs(btn.dataset.target);
            if (target) target.scrollIntoView({ behavior: 'smooth', block: 'start' });
        });
    });

    document.querySelectorAll('[data-show]').forEach(function (btn) {
        btn.addEventListener('click', function () {
            var target = btn.dataset.show === 'saved' ? qs('savedSection') : btn.dataset.show === 'history' ? qs('historySection') : qs('contractsSection');
            target.scrollIntoView({ behavior: 'smooth', block: 'start' });
        });
    });

    loadProfile();
})();
