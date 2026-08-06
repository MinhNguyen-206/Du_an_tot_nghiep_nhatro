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
        <p class="rc-user-email">{{ user.email || 'doanquocdat_@gmail.com' }}</p>
        <button class="rc-verify-btn">
          <i class="fas fa-check-circle"></i> Tài khoản đã xác minh
        </button>
      </div>

      <!-- Navigation Menu -->
      <nav class="rc-side-nav">
        <router-link to="/" class="rc-nav-item">
          <i class="fas fa-arrow-left"></i> Trang chủ
        </router-link>
        <a href="#" class="rc-nav-item is-active">
          <i class="far fa-user-circle"></i> Thông tin cá nhân
        </a>
        <a href="#" class="rc-nav-item">
          <i class="far fa-calendar-alt"></i> Lịch hẹn của tôi
        </a>
        <a href="#" class="rc-nav-item">
          <i class="fas fa-file-signature"></i> Hợp đồng của tôi
        </a>
        <a href="#" class="rc-nav-item">
          <i class="far fa-bookmark"></i> Phòng đã lưu
        </a>
        <a href="#" class="rc-nav-item">
          <i class="fas fa-history"></i> Lịch sử xem phòng
        </a>
        <a href="#" class="rc-nav-item rc-nav-settings">
          <i class="fas fa-cog"></i> Cài đặt
        </a>
        <a href="#" class="rc-nav-item rc-nav-logout" @click.prevent="logout">
          <i class="fas fa-sign-out-alt"></i> Đăng xuất
        </a>
      </nav>

      <!-- Support Box -->
      <div class="rc-support-box">
        <p class="rc-support-title">Liên hệ & hỗ trợ</p>
        <p class="rc-support-item">
          <i class="fas fa-phone-alt"></i> 0123456789
        </p>
        <p class="rc-support-item">
          <i class="fas fa-headset"></i> Hỗ trợ 24/7
        </p>
      </div>
    </div>

    <div class="rc-main-content">
      <!-- Top Stats -->
      <div class="rc-stats-row">
        <div class="rc-stat-card rc-stat-card--dark">
          <div class="rc-stat-info">
            <span class="rc-stat-label">Phòng đã lưu</span>
            <span class="rc-stat-value">06</span>
          </div>
          <div class="rc-stat-icon">
            <i class="fas fa-door-closed"></i>
          </div>
        </div>
        <div class="rc-stat-card rc-stat-card--light">
          <div class="rc-stat-info">
            <span class="rc-stat-label">Lịch hẹn sắp tới</span>
            <span class="rc-stat-value">03</span>
          </div>
          <div class="rc-stat-icon">
            <i class="fas fa-users"></i>
          </div>
        </div>
      </div>

      <!-- Section: Phòng đã lưu -->
      <div class="rc-section-header">
        <h3 class="rc-section-title">Phòng đã lưu</h3>
        <a href="#" class="rc-view-all">Xem tất cả</a>
      </div>
      <div class="rc-room-grid">
        <!-- Room Card Mock -->
        <div class="rc-room-card" v-for="i in 2" :key="i">
          <div class="rc-room-img-wrap">
            <span class="rc-room-badge">Mới đăng</span>
            <img src="https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=60" alt="Room" class="rc-room-img" />
            <button class="rc-btn-heart"><i class="fas fa-heart text-danger"></i></button>
          </div>
          <div class="rc-room-info">
            <p class="rc-room-price">7.0Tr/Tháng</p>
            <p class="rc-room-title">Phòng Studio full nội thất</p>
            <p class="rc-room-location"><i class="fas fa-map-marker-alt"></i> Quận 1, TP. HCM</p>
            <div class="rc-room-amenities">
              <span class="rc-amenity"><i class="fas fa-wifi"></i> Wifi</span>
              <span class="rc-amenity"><i class="fas fa-snowflake"></i> Điều hòa</span>
            </div>
          </div>
        </div>
      </div>

      <!-- Section: Lịch sử phòng đã xem -->
      <div class="rc-section-header rc-mt-4">
        <h3 class="rc-section-title">Lịch sử phòng đã xem</h3>
        <a href="#" class="rc-view-all">Xem tất cả</a>
      </div>
      <div class="rc-history-list">
        <!-- Horizontal Card Mock -->
        <div class="rc-history-card">
          <div class="rc-history-img-wrap">
            <span class="rc-room-badge rc-room-badge--sale">Hot sale</span>
            <img src="https://images.unsplash.com/photo-1502672260266-1c1c2c1a89c9?ixlib=rb-4.0.3&auto=format&fit=crop&w=300&q=60" alt="Room" />
            <button class="rc-btn-heart-small"><i class="far fa-heart"></i></button>
          </div>
          <div class="rc-history-info">
            <div class="rc-history-head">
              <h4 class="rc-history-title">Phòng gần ĐH FPT</h4>
              <p class="rc-history-price">2,8 <br>Tr</p>
            </div>
            <p class="rc-history-location"><i class="fas fa-map-marker-alt"></i> Quận 9, TP. HCM</p>
            <div class="rc-room-amenities">
              <span class="rc-amenity"><i class="fas fa-wifi"></i> Wifi</span>
              <span class="rc-amenity"><i class="fas fa-snowflake"></i> Điều hòa</span>
            </div>
          </div>
        </div>
        <div class="rc-history-card">
          <div class="rc-history-img-wrap">
            <img src="https://images.unsplash.com/photo-1493809842364-78817add7ffb?ixlib=rb-4.0.3&auto=format&fit=crop&w=300&q=60" alt="Room" />
            <button class="rc-btn-heart-small"><i class="far fa-heart"></i></button>
          </div>
          <div class="rc-history-info">
            <div class="rc-history-head">
              <h4 class="rc-history-title">Chung cư mini xịn sò</h4>
              <p class="rc-history-price">4,0 <br>Tr</p>
            </div>
            <p class="rc-history-location"><i class="fas fa-map-marker-alt"></i> Quận Phú Nhuận</p>
            <div class="rc-room-amenities">
              <span class="rc-amenity"><i class="fas fa-wifi"></i> Wifi</span>
              <span class="rc-amenity"><i class="fas fa-snowflake"></i> Điều hòa</span>
            </div>
          </div>
        </div>
      </div>

      <!-- Section: Hợp đồng gần đây -->
      <div class="rc-section-header rc-mt-4">
        <h3 class="rc-section-title">Hợp đồng gần đây</h3>
        <a href="#" class="rc-view-all">Xem tất cả</a>
      </div>
      <div class="rc-contract-card">
        <div class="rc-contract-header">
          <div class="rc-contract-icon">
            <i class="fas fa-file-contract"></i>
          </div>
          <div class="rc-contract-title">
            <h4>Hợp đồng thuê phòng #RC-8849</h4>
            <p>Trạng thái: <strong>Đang hiệu lực</strong></p>
          </div>
          <button class="rc-btn-outline">Xem chi tiết</button>
        </div>
        <div class="rc-contract-dates">
          <div class="rc-date-box">
            <span>Ngày bắt đầu</span>
            <strong>21/07/2026</strong>
          </div>
          <div class="rc-date-box">
            <span>Ngày kết thúc</span>
            <strong>21/01/2027</strong>
          </div>
        </div>
      </div>

      <!-- Section: Lịch hẹn sắp tới -->
      <div class="rc-section-header rc-mt-4">
        <h3 class="rc-section-title">Lịch hẹn sắp tới</h3>
        <button class="rc-btn-text"><i class="fas fa-calendar-plus"></i> Đặt hẹn mới</button>
      </div>
      <div class="rc-appointment-card">
        <div class="rc-apt-date">
          <span class="rc-apt-day">Thứ 7</span>
          <span class="rc-apt-num">22</span>
          <span class="rc-apt-month">Th10</span>
        </div>
        <div class="rc-apt-info">
          <h4>Căn hộ Sunrise City - Tháp V4</h4>
          <p>Địa chỉ: 23 Nguyễn Hữu Thọ, Quận 7</p>
        </div>
        <div class="rc-apt-time">
          <p>15:30 - 17:00</p>
          <p>Chủ phòng: Anh Quốc Đạt</p>
        </div>
        <div class="rc-apt-actions">
          <button class="rc-btn-outline-small">Đổi lịch</button>
          <button class="rc-btn-dark-small">Liên hệ</button>
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

function logout() {
  authStore.logout()
  router.push('/login')
}
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

.rc-user-email {
  font-size: 13px;
  color: #6c757d;
  margin: 0 0 15px;
}

.rc-verify-btn {
  background: #fdf6e3;
  color: #b58900;
  border: none;
  padding: 6px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
  cursor: pointer;
}

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
.rc-nav-logout {
  color: #dc3545;
}
.rc-nav-logout:hover {
  background: #fff5f5;
  color: #dc3545;
}

.rc-support-box {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.03);
}
.rc-support-title { font-weight: 700; margin-bottom: 15px; }
.rc-support-item { font-size: 14px; font-weight: 600; margin-bottom: 10px; display: flex; align-items: center; gap: 10px;}

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
  border-radius: 12px;
  padding: 20px 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 4px 15px rgba(0,0,0,0.03);
}

.rc-stat-card--dark {
  background: #5c5c57;
  color: #fff;
}
.rc-stat-card--light {
  background: #8b8a85;
  color: #fff;
}

.rc-stat-label { font-size: 14px; display: block; margin-bottom: 5px; opacity: 0.9; }
.rc-stat-value { font-size: 24px; font-weight: 700; display: block; }
.rc-stat-icon i { font-size: 28px; opacity: 0.8; }

.rc-section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}
.rc-section-title { font-size: 18px; font-weight: 700; margin: 0; }
.rc-view-all { color: #495057; font-size: 13px; font-weight: 600; text-decoration: none; background: #c5c3bc; padding: 4px 12px; border-radius: 20px;}
.rc-btn-text { background: none; border: none; color: #495057; font-weight: 600; cursor: pointer; }
.rc-mt-4 { margin-top: 30px; }

/* Grid */
.rc-room-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}
.rc-room-card {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 15px rgba(0,0,0,0.03);
}
.rc-room-img-wrap { position: relative; height: 180px; }
.rc-room-img { width: 100%; height: 100%; object-fit: cover; }
.rc-room-badge {
  position: absolute; top: 10px; left: 10px;
  background: #e4a63a; color: #fff; font-size: 11px; padding: 4px 8px; border-radius: 4px; font-weight: 700;
}
.rc-btn-heart {
  position: absolute; bottom: -15px; right: 15px;
  width: 34px; height: 34px; border-radius: 50%; border: none; background: #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1); cursor: pointer; display: flex; align-items: center; justify-content: center;
}
.text-danger { color: #dc3545; }

.rc-room-info { padding: 20px 15px 15px; }
.rc-room-price { font-weight: 700; color: #5c5c57; margin: 0 0 5px; font-size: 15px; }
.rc-room-title { font-size: 14px; font-weight: 600; margin: 0 0 5px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;}
.rc-room-location { font-size: 12px; color: #6c757d; margin: 0 0 10px; }
.rc-room-amenities { display: flex; gap: 10px; }
.rc-amenity { background: #f8f9fa; padding: 4px 8px; border-radius: 4px; font-size: 11px; font-weight: 600; color: #495057;}

/* History List */
.rc-history-list { display: flex; flex-direction: column; gap: 15px; }
.rc-history-card {
  display: flex; background: #fff; border-radius: 12px; padding: 10px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.03); gap: 15px; background-color: #fdf5f5;
}
.rc-history-img-wrap { position: relative; width: 140px; height: 100px; border-radius: 8px; overflow: hidden; flex-shrink: 0;}
.rc-history-img-wrap img { width: 100%; height: 100%; object-fit: cover; }
.rc-room-badge--sale { background: #198754; }
.rc-btn-heart-small {
  position: absolute; bottom: 5px; right: 5px;
  background: rgba(255,255,255,0.8); border: none; width: 24px; height: 24px; border-radius: 50%;
  display: flex; align-items: center; justify-content: center; cursor: pointer;
}
.rc-history-info { flex-grow: 1; display: flex; flex-direction: column; justify-content: center; }
.rc-history-head { display: flex; justify-content: space-between; align-items: flex-start; }
.rc-history-title { font-size: 15px; font-weight: 700; margin: 0 0 5px; color: #1a1566;}
.rc-history-price { font-size: 16px; font-weight: 700; color: #dc3545; margin: 0; text-align: right; line-height: 1.1;}
.rc-history-location { font-size: 12px; color: #6c757d; margin: 0 0 10px; }

/* Contracts */
.rc-contract-card {
  background: #fff; border-radius: 12px; padding: 20px; box-shadow: 0 4px 15px rgba(0,0,0,0.03);
}
.rc-contract-header { display: flex; align-items: center; gap: 15px; margin-bottom: 20px; }
.rc-contract-icon {
  width: 40px; height: 40px; background: #e9ecef; border-radius: 50%;
  display: flex; align-items: center; justify-content: center; font-size: 18px; color: #6c757d;
}
.rc-contract-title { flex-grow: 1; }
.rc-contract-title h4 { margin: 0 0 5px; font-size: 14px; font-weight: 700;}
.rc-contract-title p { margin: 0; font-size: 12px; color: #6c757d;}
.rc-btn-outline { border: 1px solid #ced4da; background: #fff; padding: 6px 12px; border-radius: 20px; font-size: 12px; font-weight: 600; cursor: pointer;}
.rc-contract-dates { display: flex; gap: 15px; }
.rc-date-box { flex: 1; background: #e2e1dd; padding: 12px; border-radius: 8px; display: flex; flex-direction: column; gap: 5px; }
.rc-date-box span { font-size: 11px; color: #6c757d; }
.rc-date-box strong { font-size: 14px; font-weight: 700; color: #212529; }

/* Appointments */
.rc-appointment-card {
  background: #fff; border-radius: 12px; padding: 15px 20px; box-shadow: 0 4px 15px rgba(0,0,0,0.03);
  display: flex; align-items: center; gap: 20px;
}
.rc-apt-date {
  background: #5c5c57; color: #fff; border-radius: 8px; width: 60px; height: 70px;
  display: flex; flex-direction: column; align-items: center; justify-content: center;
}
.rc-apt-day { font-size: 10px; opacity: 0.8;}
.rc-apt-num { font-size: 22px; font-weight: 700; line-height: 1; margin: 2px 0;}
.rc-apt-month { font-size: 10px; opacity: 0.8;}

.rc-apt-info { flex-grow: 1; }
.rc-apt-info h4 { margin: 0 0 5px; font-size: 14px; font-weight: 700;}
.rc-apt-info p { margin: 0; font-size: 12px; color: #6c757d;}
.rc-apt-time { font-size: 12px; color: #495057; min-width: 130px;}
.rc-apt-time p { margin: 0 0 5px; font-weight: 600;}
.rc-apt-time p:last-child { margin: 0; font-weight: normal; font-size: 11px;}

.rc-apt-actions { display: flex; flex-direction: column; gap: 8px; }
.rc-btn-outline-small { border: 1px solid #ced4da; background: #fff; padding: 4px 12px; border-radius: 4px; font-size: 11px; font-weight: 600; cursor: pointer;}
.rc-btn-dark-small { background: #5c5c57; color: #fff; border: none; padding: 4px 12px; border-radius: 4px; font-size: 11px; font-weight: 600; cursor: pointer;}

@media (max-width: 768px) {
  .rc-profile-container { flex-direction: column; }
  .rc-sidebar { width: 100%; }
  .rc-room-grid { grid-template-columns: 1fr; }
  .rc-appointment-card { flex-wrap: wrap; }
}
</style>
