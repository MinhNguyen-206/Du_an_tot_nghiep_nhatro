/**
 * Admin Dashboard JavaScript
 * Xử lý các tương tác và hành động trên trang admin
 */

document.addEventListener('DOMContentLoaded', function() {
    initializeAdminDashboard();
});

/**
 * Khởi tạo trang admin dashboard
 */
function initializeAdminDashboard() {
    setupMenuHandlers();
    setupButtonHandlers();
    setupTableInteractions();
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
            if (!event.target.closest('.header-profile')) {
                profileMenu.style.display = 'none';
            }
        });

        // Handle logout
        const logoutLink = profileMenu.querySelector('.logout-item');
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
    const token = localStorage.getItem('authToken') || sessionStorage.getItem('authToken');
    
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
