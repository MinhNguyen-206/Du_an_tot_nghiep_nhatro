<template>
  <header class="rc-header">
    <router-link to="/" class="rc-stamp" aria-label="Room Connect - Trang chủ">
      <span class="rc-stamp__ring">
        <span class="rc-stamp__text">RC</span>
      </span>
    </router-link>

    <nav class="rc-nav">
      <router-link
        v-for="item in baseNavItems"
        :key="item.to"
        :to="item.to"
        class="rc-nav__tag"
        :class="{ 'is-active': item.to === '/' }"
        :style="{ transform: `rotate(${item.tilt}deg)` }"
      >
        {{ item.label }}
      </router-link>

      <!-- Chưa đăng nhập -->
      <template v-if="!isAuthenticated">
        <router-link to="/login" class="rc-nav__tag" :style="{ transform: 'rotate(1.5deg)' }">
          Đăng nhập
        </router-link>
        <router-link to="/register" class="rc-nav__tag" :style="{ transform: 'rotate(-1deg)' }">
          Đăng ký
        </router-link>
      </template>

      <!-- Đã đăng nhập -->
      <template v-else>
        <router-link :to="accountLink" class="rc-nav__tag rc-nav__tag--user" :style="{ transform: 'rotate(1deg)' }">
          👤 {{ displayName }}
        </router-link>
        <button type="button" class="rc-nav__tag rc-nav__tag--logout" :style="{ transform: 'rotate(-1.5deg)' }" @click="handleLogout">
          Đăng xuất
        </button>
      </template>
    </nav>
  </header>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/authStore'

const router = useRouter()
const authStore = useAuthStore()

const baseNavItems = [
  { to: '/', label: 'Trang chủ', tilt: -1.5 },
  { to: '/about', label: 'Về chúng tôi', tilt: 1 },
  { to: '/contact', label: 'Liên hệ', tilt: -1 }
]

const isAuthenticated = computed(() => authStore.isAuthenticated)
const displayName = computed(() => {
  const name = authStore.user?.hoTen || 'Tài khoản'
  return name.length > 16 ? name.slice(0, 16) + '…' : name
})

// Chủ trọ/Admin bấm vào tên sẽ vào khu quản lý, người thuê vào trang hồ sơ cá nhân
const accountLink = computed(() => {
  const vaiTro = authStore.user?.vaiTro
  if (vaiTro === 3) return '/admin'
  return '/profile'
})

function handleLogout() {
  authStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.rc-header {
  position: absolute;
  top: 22px;
  left: 28px;
  right: 28px;
  z-index: 10;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-family: 'Be Vietnam Pro', 'Segoe UI', Arial, sans-serif;
}

.rc-stamp { text-decoration: none; }
.rc-stamp__ring {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 46px;
  height: 46px;
  border-radius: 50%;
  border: 2.5px dashed rgba(241, 232, 206, 0.9);
  transform: rotate(-6deg);
  background: rgba(194, 59, 43, 0.92);
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.35);
}
.rc-stamp__text {
  font-family: 'Archivo Black', sans-serif;
  font-size: 13px;
  color: #f1e8ce;
  letter-spacing: 1px;
}

.rc-nav { display: flex; align-items: center; gap: 10px; flex-wrap: wrap; }
.rc-nav__tag {
  position: relative;
  padding: 8px 15px;
  font-size: 12.5px;
  font-weight: 600;
  color: #f1e8ce;
  text-decoration: none;
  background: rgba(33, 29, 23, 0.55);
  border: 1px solid rgba(241, 232, 206, 0.35);
  border-radius: 3px;
  backdrop-filter: blur(3px);
  transition: background 0.15s ease, transform 0.15s ease;
  cursor: pointer;
  font-family: inherit;
}
.rc-nav__tag:hover { transform: rotate(0deg) translateY(-2px); }
.rc-nav__tag.is-active {
  background: #e4a63a;
  color: #211d17;
  border-color: #e4a63a;
  font-weight: 700;
}
.rc-nav__tag--user {
  background: rgba(31, 75, 63, 0.75);
  border-color: rgba(228, 166, 58, 0.6);
  max-width: 180px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.rc-nav__tag--logout {
  background: rgba(194, 59, 43, 0.75);
  border-color: rgba(241, 232, 206, 0.35);
}
.rc-nav__tag--logout:hover { background: rgba(163, 46, 32, 0.9); }

@media (max-width: 720px) {
  .rc-header { flex-direction: column; align-items: flex-start; gap: 12px; left: 16px; right: 16px; top: 16px; }
  .rc-nav { gap: 6px; }
  .rc-nav__tag { padding: 6px 10px; font-size: 11px; }
}
</style>