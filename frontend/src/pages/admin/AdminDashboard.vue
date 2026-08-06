<template>
  <div class="rc-admin-layout">
    <Header class="admin-global-header" />
    <div class="rc-admin-container">
      <!-- Sidebar -->
      <aside class="rc-admin-sidebar">
        <div class="rc-admin-brand">
          <h2>Admin Panel</h2>
        </div>
        <nav class="rc-admin-nav">
          <router-link to="/" class="rc-nav-item">
            <i class="fas fa-arrow-left"></i> Quay lại Trang chủ
          </router-link>
          <router-link to="/admin" class="rc-nav-item is-active">
            <i class="fas fa-chart-line"></i> Dashboard
          </router-link>
          <router-link to="/admin/users" class="rc-nav-item">
            <i class="fas fa-users"></i> Quản lý Người dùng
          </router-link>
          <router-link to="/admin/posts" class="rc-nav-item">
            <i class="fas fa-file-alt"></i> Quản lý Tin đăng
          </router-link>
          <router-link to="/admin/houses" class="rc-nav-item">
            <i class="fas fa-home"></i> Quản lý Nhà trọ
          </router-link>
          <router-link to="/admin/payments" class="rc-nav-item">
            <i class="fas fa-money-bill-wave"></i> Quản lý Thanh toán
          </router-link>
          <router-link to="/admin/contracts" class="rc-nav-item">
            <i class="fas fa-file-signature"></i> Quản lý Hợp đồng
          </router-link>
          <router-link to="/admin/roles" class="rc-nav-item">
            <i class="fas fa-user-tag"></i> Phân quyền
          </router-link>
          <div class="rc-nav-divider"></div>
          <a href="#" class="rc-nav-item text-danger" @click.prevent="logout">
            <i class="fas fa-sign-out-alt"></i> Đăng xuất
          </a>
        </nav>
      </aside>

      <!-- Main Content -->
      <main class="rc-admin-main">
        <header class="rc-admin-header">
          <h1>Tổng quan Hệ thống</h1>
          <div class="rc-admin-user">
            <span>Xin chào, Admin</span>
            <div class="rc-admin-avatar">
              <i class="fas fa-user-shield"></i>
            </div>
          </div>
        </header>

        <div class="rc-admin-content">

        <!-- Stats Row -->
        <div class="rc-stats-grid">
          <div class="rc-stat-card">
            <div class="rc-stat-icon text-primary"><i class="fas fa-users"></i></div>
            <div class="rc-stat-info">
              <h3>Tổng người dùng</h3>
              <p>1,250</p>
            </div>
          </div>
          <div class="rc-stat-card">
            <div class="rc-stat-icon text-success"><i class="fas fa-home"></i></div>
            <div class="rc-stat-info">
              <h3>Tin đăng đang hoạt động</h3>
              <p>458</p>
            </div>
          </div>
          <div class="rc-stat-card">
            <div class="rc-stat-icon text-warning"><i class="fas fa-money-check-alt"></i></div>
            <div class="rc-stat-info">
              <h3>Giao dịch trong tháng</h3>
              <p>235</p>
            </div>
          </div>
          <div class="rc-stat-card">
            <div class="rc-stat-icon text-danger"><i class="fas fa-exclamation-triangle"></i></div>
            <div class="rc-stat-info">
              <h3>Báo cáo vi phạm</h3>
              <p>12</p>
            </div>
          </div>
        </div>

        <!-- Charts/Tables Placeholder -->
        <div class="rc-dashboard-sections">
          <div class="rc-section-card">
            <h3>Hoạt động gần đây</h3>
            <ul class="rc-activity-list">
              <li>
                <span class="rc-activity-time">10 phút trước</span>
                <span class="rc-activity-text">Người dùng <strong>Trần Huy</strong> vừa đăng ký mới.</span>
              </li>
              <li>
                <span class="rc-activity-time">30 phút trước</span>
                <span class="rc-activity-text">Tin đăng <strong>Studio Vinhomes</strong> đã được duyệt.</span>
              </li>
              <li>
                <span class="rc-activity-time">1 giờ trước</span>
                <span class="rc-activity-text">Giao dịch mua gói Premium #TRX-998 thành công.</span>
              </li>
              <li>
                <span class="rc-activity-time">2 giờ trước</span>
                <span class="rc-activity-text">Báo cáo vi phạm từ người dùng về tin đăng #1234.</span>
              </li>
            </ul>
          </div>
          <div class="rc-section-card">
            <h3>Tin đăng chờ duyệt</h3>
            <div class="rc-empty-state">
              <i class="far fa-check-circle"></i>
              <p>Không có tin đăng nào cần duyệt lúc này.</p>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/authStore'
import Header from '../../components/layout/Header.vue'

const router = useRouter()
const authStore = useAuthStore()

function logout() {
  authStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.rc-admin-layout {
  position: relative;
  min-height: 100vh;
  background-color: #ffffff;
  padding-top: 90px; /* Space for Header */
}

.rc-admin-layout::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 90px;
  background: #ffffff;
  border-bottom: 1px solid #e2e1dd;
  z-index: 0;
}

.rc-admin-container {
  position: relative;
  z-index: 2;
  display: flex;
  min-height: calc(100vh - 90px);
  background-color: #ffffff;
  border-top: 1px solid #e2e1dd;
  font-family: 'Be Vietnam Pro', sans-serif;
  color: #211d17;
}

/* Sidebar */
.rc-admin-sidebar {
  width: 260px;
  background-color: #ffffff;
  color: #211d17;
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  border-right: 1px solid #e2e1dd;
}
.rc-admin-brand {
  padding: 20px;
  background-color: #ffffff;
  text-align: center;
}
.rc-admin-brand h2 {
  margin: 0;
  font-size: 20px;
  font-weight: 800;
  letter-spacing: 1px;
  color: #c23b2b;
}
.rc-admin-nav {
  padding: 20px 0;
  display: flex;
  flex-direction: column;
}
.rc-nav-item {
  padding: 15px 25px;
  color: #5c5c57;
  text-decoration: none;
  display: flex;
  align-items: center;
  gap: 15px;
  transition: 0.2s;
  font-size: 14px;
}
.rc-nav-item i {
  width: 20px;
  text-align: center;
  font-size: 16px;
}
.rc-nav-item:hover, .rc-nav-item.is-active {
  background-color: #f4f6f9;
  color: #c23b2b;
  border-left: 4px solid #c23b2b;
  font-weight: 600;
}
.rc-nav-divider {
  height: 1px;
  background-color: #e2e1dd;
  margin: 15px 0;
}
.text-danger {
  color: #e74c3c !important;
}
.text-danger:hover {
  background-color: rgba(231, 76, 60, 0.1) !important;
  border-left-color: #e74c3c !important;
}

/* Main Content */
.rc-admin-main {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  background-color: #f4f6f9;
  border-top-left-radius: 32px;
  padding-top: 10px;
}
.rc-admin-header {
  background-color: transparent;
  padding: 20px 30px 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: none;
  z-index: 10;
}
.rc-admin-header h1 {
  margin: 0;
  font-size: 24px;
  font-weight: 600;
}
.rc-admin-user {
  display: flex;
  align-items: center;
  gap: 15px;
  font-weight: 600;
}
.rc-admin-avatar {
  width: 40px;
  height: 40px;
  background-color: #c23b2b;
  color: #f1e8ce;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
}

.rc-admin-content {
  padding: 30px;
  flex-grow: 1;
}

/* Stats Grid */
.rc-stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 30px;
}
.rc-stat-card {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.03);
  display: flex;
  align-items: center;
  gap: 20px;
}
.rc-stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  background-color: #f8f9fa;
}
.rc-stat-icon.text-primary { color: #4a90e2; background-color: rgba(74, 144, 226, 0.15); }
.rc-stat-icon.text-success { color: #50c878; background-color: rgba(80, 200, 120, 0.15); }
.rc-stat-icon.text-warning { color: #f1c40f; background-color: rgba(241, 196, 15, 0.15); }
.rc-stat-icon.text-danger { color: #e74c3c; background-color: rgba(231, 76, 60, 0.15); }

.rc-stat-info h3 {
  margin: 0 0 5px;
  font-size: 13px;
  color: #7f8c8d;
  font-weight: 600;
}
.rc-stat-info p {
  margin: 0;
  font-size: 24px;
  font-weight: 700;
  color: #2c3e50;
}

/* Sections */
.rc-dashboard-sections {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 20px;
}
.rc-section-card {
  background-color: #fff;
  border-radius: 8px;
  padding: 25px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.03);
}
.rc-section-card h3 {
  margin: 0 0 20px;
  font-size: 16px;
  color: #211d17;
  font-weight: 700;
  border-bottom: 2px solid #e2e1dd;
  padding-bottom: 10px;
}

/* Activity List */
.rc-activity-list {
  list-style: none;
  padding: 0;
  margin: 0;
}
.rc-activity-list li {
  padding: 15px 0;
  border-bottom: 1px solid #e2e1dd;
  display: flex;
  flex-direction: column;
  gap: 5px;
}
.rc-activity-list li:last-child {
  border-bottom: none;
}
.rc-activity-time {
  font-size: 12px;
  color: #95a5a6;
}
.rc-activity-text {
  font-size: 14px;
  color: #5c5c57;
}

/* Empty State */
.rc-empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px 0;
  color: #bdc3c7;
  text-align: center;
}
.rc-empty-state i {
  font-size: 48px;
  margin-bottom: 15px;
  color: #ecf0f1;
}
.rc-empty-state p {
  margin: 0;
  font-size: 14px;
}

@media (max-width: 1024px) {
  .rc-stats-grid { grid-template-columns: repeat(2, 1fr); }
  .rc-dashboard-sections { grid-template-columns: 1fr; }
}
@media (max-width: 768px) {
  .rc-admin-container { flex-direction: column; }
  .rc-admin-sidebar { width: 100%; }
}
</style>
