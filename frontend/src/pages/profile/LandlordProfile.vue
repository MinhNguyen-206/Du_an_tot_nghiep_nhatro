<template>
  <div class="rc-profile-container">
    <div class="rc-sidebar">
      <!-- User Info Card -->
      <div class="rc-user-card">
        <div class="rc-avatar">
          <img src="https://i.pravatar.cc/150?img=11" alt="Avatar" />
          <span class="rc-status-badge"></span>
        </div>
        <h2 class="rc-user-name">{{ user.hoTen || 'Đoàn Quốc Đạt' }}</h2>
        <div class="rc-user-rating">
          <i class="fas fa-star text-warning"></i> 4.9/5.0 (128 đánh giá)
        </div>
      </div>

      <!-- Navigation Menu -->
      <nav class="rc-side-nav">
        <router-link to="/" class="rc-nav-item">
          <i class="fas fa-arrow-left"></i> Trang chủ
        </router-link>
        <a href="#" class="rc-nav-item is-active">
          <i class="fas fa-bullhorn"></i> Quản lý bài đăng
        </a>
        <a href="#" class="rc-nav-item">
          <i class="far fa-calendar-check"></i> Lịch hẹn xem phòng
        </a>
        <a href="#" class="rc-nav-item">
          <i class="fas fa-file-contract"></i> Quản lý hợp đồng khách thuê
        </a>
        <a href="#" class="rc-nav-item">
          <i class="fas fa-dollar-sign"></i> Quản lý tài chính
        </a>
        <a href="#" class="rc-nav-item rc-nav-settings">
          <i class="far fa-user"></i> Quản lý thông tin cá nhân
        </a>
      </nav>

      <!-- Premium Block -->
      <div class="rc-premium-box">
        <div class="rc-premium-header">
          <i class="fas fa-medal text-warning"></i>
          <span>GÓI THÀNH VIÊN PREMIUM</span>
        </div>
        <div class="rc-premium-progress-bg">
          <div class="rc-premium-progress-info">
            <span>Hạn mức tin đăng</span>
            <span>12/50 tin</span>
          </div>
          <div class="rc-progress-bar">
            <div class="rc-progress-fill" style="width: 24%"></div>
          </div>
        </div>
        <p class="rc-premium-expiry">Hạn sử dụng: đến 21/08/2026 (còn 5 ngày)</p>
        <button class="rc-btn-white">Gia hạn ngay</button>
      </div>
    </div>

    <div class="rc-main-content">
      <!-- Top Stats -->
      <div class="rc-stats-row">
        <div class="rc-stat-card">
          <div class="rc-stat-icon-top">
            <i class="far fa-file-alt"></i>
          </div>
          <div class="rc-stat-info-bottom">
            <span class="rc-stat-label">Tổng số tin đăng</span>
            <span class="rc-stat-value">12</span>
          </div>
        </div>
        <div class="rc-stat-card">
          <div class="rc-stat-icon-top">
            <i class="fas fa-unlock-alt"></i>
          </div>
          <div class="rc-stat-info-bottom">
            <span class="rc-stat-label">Lượt đăng tin còn lại</span>
            <span class="rc-stat-value">38</span>
          </div>
        </div>
        <div class="rc-stat-card">
          <div class="rc-stat-icon-top">
            <i class="fas fa-user-clock"></i>
          </div>
          <div class="rc-stat-info-bottom">
            <span class="rc-stat-label">Lịch hẹn mới</span>
            <span class="rc-stat-value">03</span>
          </div>
        </div>
      </div>

      <!-- Section: Tin đăng đang hoạt động -->
      <div class="rc-section-header">
        <h3 class="rc-section-title">Tin đăng đang hoạt động</h3>
        <button class="rc-btn-dark">Đăng tin mới</button>
      </div>
      <!-- Empty space for posts list in mockup -->
      <div class="rc-empty-space"></div>

      <!-- Section: Lịch hẹn xem phòng gần đây -->
      <div class="rc-section-header rc-mt-4">
        <h3 class="rc-section-title">Lịch hẹn xem phòng gần đây</h3>
      </div>
      <div class="rc-table-card">
        <table class="rc-table">
          <thead>
            <tr>
              <th>Khách hàng</th>
              <th>Phòng quan tâm</th>
              <th>Thời gian</th>
              <th>Trạng thái</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <strong>Trần Huy</strong><br/>
                <span class="text-muted">0123456789</span>
              </td>
              <td><strong>Studio Vinhomes</strong></td>
              <td>
                <strong>20/07/2026</strong><br/>
                <span class="text-muted">14:30 PM</span>
              </td>
              <td><span class="rc-badge rc-badge-primary">Sắp tới</span></td>
              <td><a href="#" class="rc-link rc-link-primary">Xác nhận</a></td>
            </tr>
            <tr>
              <td>
                <strong>Quốc Đạt</strong><br/>
                <span class="text-muted">0123456789</span>
              </td>
              <td><strong>Studio Vinhomes</strong></td>
              <td>
                <strong>19/07/2026</strong><br/>
                <span class="text-muted">17:30 PM</span>
              </td>
              <td><span class="rc-badge rc-badge-success">Hoàn thành</span></td>
              <td><a href="#" class="rc-link rc-link-success">Chi tiết</a></td>
            </tr>
          </tbody>
        </table>
        <div class="rc-table-footer">
          <a href="#">XEM TẤT CẢ LỊCH HẸN</a>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/authStore'

const router = useRouter()
const authStore = useAuthStore()

const user = computed(() => authStore.user || {})

</script>

<style scoped>
.rc-profile-container {
  position: relative;
  z-index: 2;
  max-width: 1100px;
  margin: 0 auto;
  display: flex;
  gap: 30px;
  padding: 0 20px;
  font-family: 'Be Vietnam Pro', sans-serif;
  color: #2c2c2c;
}

/* Sidebar Styles */
.rc-sidebar {
  width: 280px;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.rc-user-card {
  background: #fff;
  border-radius: 12px;
  padding: 24px;
  text-align: center;
  box-shadow: 0 4px 15px rgba(0,0,0,0.03);
}

.rc-avatar {
  position: relative;
  width: 80px;
  height: 80px;
  margin: 0 auto 15px;
}

.rc-avatar img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid #fff;
  box-shadow: 0 4px 10px rgba(0,0,0,0.1);
}

.rc-status-badge {
  position: absolute;
  bottom: 2px;
  right: 2px;
  width: 16px;
  height: 16px;
  background-color: #212529;
  border-radius: 50%;
  border: 2px solid #fff;
  display: flex;
  align-items: center;
  justify-content: center;
}
.rc-status-badge::after {
  content: "✔";
  color: #fff;
  font-size: 8px;
}

.rc-user-name {
  font-size: 18px;
  font-weight: 700;
  margin: 0 0 5px;
}

.rc-user-rating {
  font-size: 12px;
  color: #6c757d;
  font-weight: 600;
}
.text-warning { color: #ffc107; }

.rc-side-nav {
  background: #fff;
  border-radius: 12px;
  padding: 10px 0;
  box-shadow: 0 4px 15px rgba(0,0,0,0.03);
  display: flex;
  flex-direction: column;
}

.rc-nav-item {
  padding: 12px 24px;
  display: flex;
  align-items: center;
  gap: 12px;
  text-decoration: none;
  color: #495057;
  font-weight: 600;
  font-size: 14px;
  transition: all 0.2s;
}

.rc-nav-item:hover, .rc-nav-item.is-active {
  background: rgba(94, 92, 88, 0.9);
  color: #fff;
}
.rc-nav-item i { width: 20px; text-align: center; }

.rc-nav-settings {
  margin-top: 15px;
  border-top: 1px solid #f1f3f5;
}

.rc-premium-box {
  background: #5c5c57;
  border-radius: 12px;
  padding: 20px;
  color: #fff;
  box-shadow: 0 4px 15px rgba(0,0,0,0.03);
}
.rc-premium-header {
  display: flex;
  align-items: center;
  gap: 10px;
  font-weight: 700;
  font-size: 13px;
  margin-bottom: 15px;
}
.rc-premium-progress-bg {
  background: rgba(255,255,255,0.1);
  padding: 10px;
  border-radius: 8px;
  margin-bottom: 10px;
}
.rc-premium-progress-info {
  display: flex;
  justify-content: space-between;
  font-size: 11px;
  font-weight: 600;
  margin-bottom: 5px;
}
.rc-progress-bar {
  background: rgba(255,255,255,0.2);
  height: 4px;
  border-radius: 2px;
  overflow: hidden;
}
.rc-progress-fill {
  background: #ffc107;
  height: 100%;
}
.rc-premium-expiry {
  font-size: 11px;
  opacity: 0.8;
  margin: 0 0 15px;
}
.rc-btn-white {
  background: #fff;
  color: #212529;
  border: none;
  width: 100%;
  padding: 8px;
  border-radius: 4px;
  font-weight: 700;
  font-size: 13px;
  cursor: pointer;
}

/* Main Content Styles */
.rc-main-content {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}

.rc-stats-row {
  display: flex;
  gap: 20px;
  margin-bottom: 30px;
}

.rc-stat-card {
  flex: 1;
  background: #fff;
  border-radius: 12px;
  padding: 15px 20px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.03);
  display: flex;
  flex-direction: column;
}
.rc-stat-icon-top {
  margin-bottom: 10px;
  font-size: 18px;
  color: #495057;
}
.rc-stat-info-bottom {
  display: flex;
  flex-direction: column;
}
.rc-stat-label { font-size: 12px; color: #6c757d; font-weight: 600; margin-bottom: 2px; }
.rc-stat-value { font-size: 20px; font-weight: 700; color: #212529; }

.rc-section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}
.rc-section-title { font-size: 18px; font-weight: 700; margin: 0; color: #5c5c57;}
.rc-btn-dark { background: #5c5c57; color: #fff; border: none; padding: 6px 16px; border-radius: 20px; font-size: 12px; font-weight: 600; cursor: pointer;}
.rc-mt-4 { margin-top: 30px; }

.rc-empty-space { height: 200px; }

/* Table */
.rc-table-card {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 15px rgba(0,0,0,0.03);
}
.rc-table {
  width: 100%;
  border-collapse: collapse;
}
.rc-table th {
  background: #5c5c57;
  color: #fff;
  font-size: 13px;
  font-weight: 600;
  text-align: left;
  padding: 15px 20px;
}
.rc-table td {
  padding: 15px 20px;
  font-size: 13px;
  border-bottom: 1px solid #f1f3f5;
  color: #212529;
}
.text-muted { color: #868e96; font-size: 11px; }

.rc-badge {
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 11px;
  font-weight: 600;
}
.rc-badge-primary { background: #e7f1ff; color: #0d6efd; }
.rc-badge-success { background: #d1e7dd; color: #198754; }

.rc-link { text-decoration: none; font-weight: 700; font-size: 12px; }
.rc-link-primary { color: #0d6efd; }
.rc-link-success { color: #198754; }

.rc-table-footer {
  text-align: center;
  padding: 15px;
}
.rc-table-footer a {
  text-decoration: none;
  font-size: 11px;
  font-weight: 700;
  color: #495057;
}

@media (max-width: 768px) {
  .rc-profile-container { flex-direction: column; }
  .rc-sidebar { width: 100%; }
  .rc-stats-row { flex-direction: column; }
  .rc-table { display: block; overflow-x: auto; white-space: nowrap; }
}
</style>
