/**
 * Admin Dashboard JavaScript
 * Xử lý các tương tác và hành động trên trang admin
 */

document.addEventListener('DOMContentLoaded', function() {
    initializeAdminDashboard();
});

const ADMIN_MOCK_DATA = {
    totalUsers: 1284,
    totalPosts: 856,
    totalAppointments: 48,
    totalTransactions: 327,
    pendingUsers: 24,
    pendingPosts: 12,
    pendingReports: 5,
    monthlyRevenue: [
        { month: '03/2026', value: 82000000 },
        { month: '04/2026', value: 97000000 },
        { month: '05/2026', value: 106000000 },
        { month: '06/2026', value: 115000000 },
        { month: '07/2026', value: 121000000 },
        { month: '08/2026', value: 128500000 }
    ],
    activities: [
        { icon: 'fa-user-plus', color: 'green-bg', title: 'Nguyễn Minh Anh', detail: 'đã đăng ký tài khoản mới', time: '5 phút trước', tag: 'Người dùng', tagColor: 'success' },
        { icon: 'fa-house', color: 'brown-bg', title: 'Phòng Studio Vinhomes', detail: 'đã được gửi để duyệt bài đăng', time: '18 phút trước', tag: 'Chờ duyệt', tagColor: 'warning' },
        { icon: 'fa-credit-card', color: 'gold-bg', title: 'Gói Premium Standard', detail: 'đã được thanh toán thành công', time: '32 phút trước', tag: 'Thanh toán', tagColor: 'success' },
        { icon: 'fa-flag', color: 'red-bg', title: 'Bài đăng #RC-1082', detail: 'được người dùng báo cáo', time: '1 giờ trước', tag: 'Cần xử lý', tagColor: 'danger' }
    ]
};

const ADMIN_USE_MOCK = new URLSearchParams(window.location.search).get('mock') === 'true';
let dashboardSummary = null;
let selectedPeriod = 'month';

/**
 * Khởi tạo trang admin dashboard
 */
function initializeAdminDashboard() {
    setupMenuHandlers();
    setupButtonHandlers();
    setupTableInteractions();
    setupPeriodSwitcher();
    loadDashboardSummary();
}

/**
 * Load số liệu tổng quan từ API admin.
 */
async function loadDashboardSummary() {
    const summary = ADMIN_USE_MOCK
        ? ADMIN_MOCK_DATA
        : await apiCall('/api/admin/dashboard');
    if (!summary) {
        return;
    }

    dashboardSummary = summary;
    applySelectedPeriod();
}

function applySelectedPeriod() {
    const summary = dashboardSummary || ADMIN_MOCK_DATA;
    const period = getPeriodDefinition(selectedPeriod, summary.monthlyRevenue || []);

    const values = document.querySelectorAll('.kpi-value');
    const dashboardValues = [
        summary.totalUsers,
        summary.totalPosts,
        summary.totalAppointments,
        summary.totalTransactions
    ];

    dashboardValues.forEach((value, index) => {
        if (values[index]) {
            values[index].textContent = Number(value).toLocaleString('vi-VN');
        }
    });

    updatePendingCounts(summary);
    updateDetailStats(summary, period);
    renderPieChart(summary);
    renderIncomeChart(summary, period.revenue);
    updatePeriodLabels(period);
    if (summary.activities) {
        renderActivities(summary.activities);
    }
}

function setupPeriodSwitcher() {
    const periodButtons = document.querySelectorAll('.period-switcher button');
    periodButtons.forEach((button, index) => {
        button.dataset.period = ['day', 'month', 'quarter', 'year'][index];
        button.addEventListener('click', function() {
            periodButtons.forEach(item => item.classList.remove('active'));
            this.classList.add('active');
            selectedPeriod = this.dataset.period;
            applySelectedPeriod();
        });
    });
}

function getPeriodDefinition(period, revenue) {
    const latest = revenue[revenue.length - 1] || { month: '08/2026', value: 0 };
    const latestParts = latest.month.split('/');
    const month = Number(latestParts[0]);
    const year = latestParts[1];
    const labels = {
        day: { title: `Ngày 21/${latest.month}`, description: 'Tổng quan trong ngày hiện tại' },
        month: { title: `Tháng ${latest.month}`, description: 'Tổng quan tháng hiện tại' },
        quarter: { title: `Quý ${Math.ceil(month / 3)}/${year}`, description: 'Tổng quan trong quý hiện tại' },
        year: { title: `Năm ${year}`, description: 'Tổng quan trong năm hiện tại' }
    };
    const windows = { day: 1, month: 1, quarter: 3, year: revenue.length };
    const periodRevenue = revenue.slice(-windows[period]);
    return { ...labels[period], revenue: periodRevenue };
}

function updatePeriodLabels(period) {
    const analyticsLabel = document.getElementById('analyticsPeriodLabel');
    const detailLabel = document.getElementById('periodDetailLabel');
    const detailDescription = document.getElementById('periodDetailDescription');
    const chartBadge = document.getElementById('chartPeriodBadge');
    if (analyticsLabel) analyticsLabel.textContent = period.title;
    if (detailLabel) detailLabel.textContent = period.title;
    if (detailDescription) detailDescription.textContent = period.description;
    if (chartBadge && period.revenue.length) {
        chartBadge.textContent = period.revenue.length === 1
            ? period.revenue[0].month
            : `${period.revenue[0].month} - ${period.revenue[period.revenue.length - 1].month}`;
    }
}

function updateDetailStats(summary, period) {
    const detailValues = {
        detailCompleted: summary.totalUsers,
        detailPosts: summary.totalPosts,
        detailRooms: summary.totalRooms || 0,
        detailRevenue: period.revenue.reduce((total, item) => total + (Number(item.value) || 0), 0)
    };

    Object.entries(detailValues).forEach(([id, value]) => {
        const element = document.getElementById(id);
        if (element) {
            element.textContent = Number(value).toLocaleString('vi-VN');
        }
    });
}

function renderPieChart(summary) {
    const chart = document.getElementById('adminPieChart');
    const legend = document.getElementById('pieChartLegend');
    const totalElement = document.getElementById('pieChartTotal');
    if (!chart || !legend || !totalElement) {
        return;
    }

    const items = [
        { label: 'Người dùng', value: Number(summary.totalUsers) || 0, color: '#16a085' },
        { label: 'Bài đăng', value: Number(summary.totalPosts) || 0, color: '#f29a62' },
        { label: 'Nhà trọ', value: Number(summary.totalProperties) || 0, color: '#f2c14e' },
        { label: 'Phòng trọ', value: Number(summary.totalRooms) || 0, color: '#ef6f73' }
    ];
    const total = items.reduce((sum, item) => sum + item.value, 0);
    let currentPercentage = 0;
    const stops = items.map(item => {
        const nextPercentage = total ? currentPercentage + (item.value / total * 100) : 0;
        const stop = `${item.color} ${currentPercentage}% ${nextPercentage}%`;
        currentPercentage = nextPercentage;
        return stop;
    });

    chart.style.background = 'transparent';
    totalElement.textContent = total.toLocaleString('vi-VN');
    const segments = [];
    let startAngle = -90;
    items.forEach((item, index) => {
        const angle = total ? item.value / total * 360 : 0;
        const endAngle = startAngle + angle;
        segments.push(`<path class="pie-segment" data-index="${index}" d="${describeArc(95, 95, 95, startAngle, endAngle)}" fill="${item.color}"><title>${item.label}: ${item.value.toLocaleString('vi-VN')} mục</title></path>`);
        startAngle = endAngle;
    });
    chart.insertAdjacentHTML('afterbegin', `<svg class="pie-chart-svg" viewBox="0 0 190 190" aria-label="Biểu đồ phân bổ dữ liệu">${segments.join('')}</svg>`);
    legend.innerHTML = items.map((item, index) => {
        const percentage = total ? Math.round(item.value / total * 100) : 0;
        return `<div class="pie-legend-item" data-index="${index}" tabindex="0">
            <span class="pie-legend-dot" style="background:${item.color}"></span>
            <span>${item.label}</span>
            <strong>${percentage}%</strong>
        </div>`;
    }).join('');

    chart.querySelectorAll('.pie-segment').forEach(segment => {
        segment.addEventListener('mouseenter', () => highlightPieItem(segment.dataset.index, true));
        segment.addEventListener('mouseleave', () => highlightPieItem(segment.dataset.index, false));
    });
    legend.querySelectorAll('.pie-legend-item').forEach(item => {
        item.addEventListener('mouseenter', () => highlightPieItem(item.dataset.index, true));
        item.addEventListener('mouseleave', () => highlightPieItem(item.dataset.index, false));
        item.addEventListener('focus', () => highlightPieItem(item.dataset.index, true));
        item.addEventListener('blur', () => highlightPieItem(item.dataset.index, false));
    });
}

function describeArc(centerX, centerY, radius, startAngle, endAngle) {
    const start = polarToCartesian(centerX, centerY, radius, endAngle);
    const end = polarToCartesian(centerX, centerY, radius, startAngle);
    const largeArcFlag = endAngle - startAngle <= 180 ? '0' : '1';
    return `M ${centerX} ${centerY} L ${start.x} ${start.y} A ${radius} ${radius} 0 ${largeArcFlag} 0 ${end.x} ${end.y} Z`;
}

function polarToCartesian(centerX, centerY, radius, angleInDegrees) {
    const angleInRadians = (angleInDegrees - 90) * Math.PI / 180;
    return {
        x: centerX + radius * Math.cos(angleInRadians),
        y: centerY + radius * Math.sin(angleInRadians)
    };
}

function highlightPieItem(index, active) {
    document.querySelectorAll(`.pie-segment[data-index="${index}"], .pie-legend-item[data-index="${index}"]`)
        .forEach(element => element.classList.toggle('is-active', active));
}

function renderIncomeChart(summary, revenue) {
    const chart = document.getElementById('incomeChart');
    if (!chart) {
        return;
    }

    const chartRevenue = revenue.length ? revenue : (summary.monthlyRevenue || ADMIN_MOCK_DATA.monthlyRevenue);
    const maxValue = Math.max(...chartRevenue.map(item => item.value), 1);
    chart.innerHTML = chartRevenue.map(item => {
        const height = Math.max((item.value / maxValue) * 100, 8);
        const amount = `${(item.value / 1000000).toFixed(1)}M VNĐ`;
        return `<div class="income-bar-column">
            <div class="income-bar-wrap">
                <div class="income-bar" style="height:${height}%" data-tooltip="${item.month}: ${amount}" tabindex="0"></div>
            </div>
            <span>${item.month.slice(0, 2)}/${item.month.slice(3)}</span>
        </div>`;
    }).join('');
}

function updatePendingCounts(summary) {
    const counts = document.querySelectorAll('.nav-count');
    if (counts[0] && summary.pendingUsers !== undefined) {
        counts[0].textContent = summary.pendingUsers;
    }
    if (counts[1] && summary.pendingPosts !== undefined) {
        counts[1].textContent = summary.pendingPosts;
    }
    if (counts[2] && summary.pendingReports !== undefined) {
        counts[2].textContent = summary.pendingReports;
    }

    const pendingElements = {
        pendingUsers: summary.pendingUsers,
        pendingPosts: summary.pendingPosts,
        pendingReports: summary.pendingReports
    };
    Object.entries(pendingElements).forEach(([id, value]) => {
        const element = document.getElementById(id);
        if (element && value !== undefined) {
            element.textContent = Number(value).toLocaleString('vi-VN');
        }
    });
}

function renderActivities(activities) {
    const activityList = document.querySelector('.activity-list');
    if (!activityList || !activities) {
        return;
    }

    activityList.innerHTML = activities.map(activity => `
        <div class="activity-item">
            <div class="activity-avatar ${activity.color}">
                <i class="fa-solid ${activity.icon}"></i>
            </div>
            <div class="activity-info">
                <strong>${activity.title}</strong>
                <span>${activity.detail}</span>
                <small>${activity.time}</small>
            </div>
            <span class="activity-tag ${activity.tagColor}">${activity.tag}</span>
        </div>
    `).join('');
}

/**
 * Setup Menu Handlers
 */
function setupMenuHandlers() {
    const profileBtn = document.getElementById('profileBtn');
    const profileMenu = document.getElementById('profileMenu');

    if (profileBtn && profileMenu) {
        profileBtn.addEventListener('click', function(e) {
            e.stopPropagation();
            profileMenu.style.display = profileMenu.style.display === 'none' ? 'block' : 'none';
        });

        // Close menu when clicking outside
        document.addEventListener('click', function(event) {
            if (!event.target.closest('.admin-account-menu')) {
                profileMenu.style.display = 'none';
            }
        });

        // Handle logout
        const logoutLink = profileMenu.querySelector('.logout-item') || profileMenu.querySelector('a:last-child');
        if (logoutLink) {
            logoutLink.addEventListener('click', function(e) {
                e.preventDefault();
                logout();
            });
        }
    }
}

/**
 * Setup Button Handlers
 */
function setupButtonHandlers() {
    // Handle "Đăng tin mới" button
    const newPostBtn = document.querySelector('.btn-new-post');
    if (newPostBtn) {
        newPostBtn.addEventListener('click', function() {
            handleNewPost();
        });
    }

    // Handle "Gia hạn ngay" button
    const renewBtn = document.querySelector('.btn-renew');
    if (renewBtn) {
        renewBtn.addEventListener('click', function() {
            handleRenewPremium();
        });
    }

    // Handle sidebar menu links
    setupSidebarMenu();
}

/**
 * Setup Sidebar Menu
 */
function setupSidebarMenu() {
    const menuLinks = document.querySelectorAll('.sidebar-menu .menu-link');

    menuLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            
            // Remove active class from all items
            document.querySelectorAll('.sidebar-menu .menu-item').forEach(item => {
                item.classList.remove('active');
            });
            
            // Add active class to clicked item
            this.closest('.menu-item').classList.add('active');
            
            // Handle section change
            const section = this.getAttribute('data-section');
            handleSectionChange(section);
        });
    });
}

/**
 * Handle Section Change
 */
function handleSectionChange(section) {
    console.log('Switching to section:', section);
    
    switch(section) {
        case 'posts':
            loadPostsSection();
            break;
        case 'appointments':
            loadAppointmentsSection();
            break;
        case 'contracts':
            loadContractsSection();
            break;
        case 'finance':
            loadFinanceSection();
            break;
        case 'profile':
            loadProfileSection();
            break;
        default:
            console.log('Unknown section:', section);
    }
}

/**
 * Load Posts Section
 */
function loadPostsSection() {
    console.log('Loading posts section...');
    // TODO: Tải dữ liệu từ API khi sẵn sàng
}

/**
 * Load Appointments Section
 */
function loadAppointmentsSection() {
    console.log('Loading appointments section...');
    // TODO: Tải dữ liệu từ API khi sẵn sàng
}

/**
 * Load Contracts Section
 */
function loadContractsSection() {
    console.log('Loading contracts section...');
    // TODO: Tải dữ liệu từ API khi sẵn sàng
}

/**
 * Load Finance Section
 */
function loadFinanceSection() {
    console.log('Loading finance section...');
    // TODO: Tải dữ liệu từ API khi sẵn sàng
}

/**
 * Load Profile Section
 */
function loadProfileSection() {
    console.log('Loading profile section...');
    // TODO: Tải dữ liệu từ API khi sẵn sàng
}

/**
 * Setup Table Interactions
 */
function setupTableInteractions() {
    const actionButtons = document.querySelectorAll('.btn-action');
    
    actionButtons.forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            const buttonText = this.textContent.trim();
            const row = this.closest('tr');
            
            if (buttonText === 'Chi tiết') {
                handleAppointmentDetail(row);
            } else if (buttonText === 'Xóc nhân') {
                handleAppointmentEdit(row);
            }
        });
    });
}

/**
 * Handle New Post
 */
function handleNewPost() {
    console.log('Creating new post...');
    alert('Tính năng đăng tin mới sẽ sớm được cập nhật!');
    // TODO: Redirect to create post page hoặc mở modal
}

/**
 * Handle Renew Premium
 */
function handleRenewPremium() {
    console.log('Renewing premium membership...');
    alert('Tính năng gia hạn premium sẽ sớm được cập nhật!');
    // TODO: Redirect to premium renewal page hoặc mở modal
}

/**
 * Handle Appointment Detail
 */
function handleAppointmentDetail(row) {
    const customerName = row.querySelector('.customer-info strong').textContent;
    const room = row.querySelector('td:nth-child(2)').textContent;
    const appointmentTime = row.querySelector('.appointment-time').textContent;
    
    console.log('Viewing appointment details:', {
        customer: customerName,
        room: room,
        time: appointmentTime
    });
    
    alert(`Xem chi tiết lịch hẹn của ${customerName} tại phòng ${room}`);
    // TODO: Mở modal hoặc redirect to detail page
}

/**
 * Handle Appointment Edit
 */
function handleAppointmentEdit(row) {
    const customerName = row.querySelector('.customer-info strong').textContent;
    console.log('Editing appointment for:', customerName);
    alert(`Chỉnh sửa lịch hẹn cho ${customerName}`);
    // TODO: Mở modal để chỉnh sửa
}

/**
 * Logout Function
 */
function logout() {
    console.log('Logging out...');
    
    // Có thể xóa token từ localStorage/sessionStorage
    localStorage.removeItem('authToken');
    sessionStorage.removeItem('authToken');
    localStorage.removeItem('token');
    sessionStorage.removeItem('token');
    
    // Redirect to login page
    window.location.href = '/login';
}

/**
 * Utility function to show notifications
 */
function showNotification(message, type = 'info') {
    console.log(`[${type.toUpperCase()}]`, message);
    // TODO: Implement a proper notification system (toast, etc.)
}

/**
 * Utility function to make API calls with JWT token
 */
async function apiCall(url, options = {}) {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token')
        || localStorage.getItem('authToken') || sessionStorage.getItem('authToken');
    
    const headers = {
        'Content-Type': 'application/json',
        ...options.headers
    };
    
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }
    
    try {
        const response = await fetch(url, {
            ...options,
            headers
        });
        
        if (!response.ok) {
            if (response.status === 401) {
                logout();
                return null;
            }
            if (response.status === 403) {
                showNotification('Tài khoản không có quyền tải dữ liệu tổng quan.', 'error');
                return null;
            }
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('API call error:', error);
        showNotification('Đã xảy ra lỗi khi tải dữ liệu', 'error');
        return null;
    }
}

// Export functions for use in other scripts if needed
window.adminDashboard = {
    handleNewPost,
    handleRenewPremium,
    handleAppointmentDetail,
    handleAppointmentEdit,
    logout,
    showNotification,
    apiCall
};
