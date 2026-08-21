document.addEventListener('DOMContentLoaded', () => {
    loadManagementData();
});

let managementRecords = [];

async function loadManagementData() {
    const status = document.getElementById('managementStatus');
    try {
        const token = localStorage.getItem('token');
        const response = await fetch(window.ADMIN_MANAGEMENT_ENDPOINT, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
        if (response.status === 401) {
            window.location.href = '/login';
            return;
        }
        if (response.status === 403) {
            status.textContent = 'Tài khoản không có quyền xem dữ liệu này.';
            status.classList.add('error');
            return;
        }
        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }
        managementRecords = await response.json();
        renderManagementTable(managementRecords);
        status.textContent = 'Dữ liệu được cập nhật từ hệ thống';
    } catch (error) {
        status.textContent = 'Không thể tải dữ liệu. Vui lòng thử lại sau.';
        status.classList.add('error');
        console.error('Admin management API error:', error);
    }
}

function renderManagementTable(records) {
    const head = document.getElementById('managementHead');
    const body = document.getElementById('managementBody');
    const count = document.getElementById('recordCount');
    if (!head || !body) return;

    count.textContent = `${records.length.toLocaleString('vi-VN')} bản ghi`;
    if (!records.length) {
        head.innerHTML = '';
        body.innerHTML = '<tr><td class="management-empty" colspan="6">Chưa có dữ liệu</td></tr>';
        return;
    }

    const keys = Object.keys(records[0]).slice(0, 5);
    head.innerHTML = `<tr>${keys.map(key => `<th>${formatLabel(key)}</th>`).join('')}<th>Thao tác</th></tr>`;
    body.innerHTML = records.map(record => `<tr>${keys.map(key => `<td>${formatValue(record[key])}</td>`).join('')}<td><button class="management-detail" type="button">Chi tiết</button></td></tr>`).join('');
}

function formatLabel(key) {
    return key.replace(/([A-Z])/g, ' $1').replace(/^./, value => value.toUpperCase());
}

function formatValue(value) {
    if (value === null || value === undefined) return '-';
    if (typeof value === 'object') return value.tenVaiTro || value.hoTen || value.email || '[Dữ liệu liên kết]';
    if (typeof value === 'boolean') return value ? 'Đang hoạt động' : 'Không hoạt động';
    return String(value);
}

document.addEventListener('input', event => {
    if (event.target.id !== 'managementSearch') return;
    const query = event.target.value.trim().toLowerCase();
    const filtered = managementRecords.filter(record => JSON.stringify(record).toLowerCase().includes(query));
    renderManagementTable(filtered);
});
