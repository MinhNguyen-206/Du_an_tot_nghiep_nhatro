<template>
  <header class="site-header">
    <nav class="nav-pill">
      <router-link to="/" class="nav-pill__item is-active">Trang chủ</router-link>
      <router-link to="/about" class="nav-pill__item">Về chúng tôi</router-link>
      <router-link to="/contact" class="nav-pill__item">Liên hệ</router-link>
      <router-link to="/login" class="nav-pill__item">Đăng nhập</router-link>
      <router-link to="/register" class="nav-pill__item">Đăng ký</router-link>
    </nav>
    <router-link to="/" class="logo-badge" aria-label="Room Connect">RC</router-link>
  </header>
</template>

<script setup>
</script>

<style scoped>
.site-header {
  position: absolute;
  top: 24px;
  right: 32px;
  left: 32px;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 12px;
  z-index: 10;
}
.nav-pill {
  display: flex;
  align-items: center;
  gap: 4px;
  background: rgba(255, 255, 255, 0.14);
  backdrop-filter: blur(6px);
  border-radius: 999px;
  padding: 6px;
}
.nav-pill__item {
  padding: 9px 16px;
  border-radius: 999px;
  font-size: 13px;
  font-weight: 600;
  color: #fff;
  text-decoration: none;
  white-space: nowrap;
  transition: background 0.15s ease, color 0.15s ease;
}
.nav-pill__item:hover { background: rgba(255, 255, 255, 0.18); }
.nav-pill__item.is-active { background: #fff; color: #1a1a1a; }

.logo-badge {
  width: 40px;
  height: 40px;
  border-radius: 12px;
  background: #ee6383;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 800;
  font-size: 13px;
  text-decoration: none;
}

@media (max-width: 720px) {
  .site-header { left: 16px; right: 16px; top: 16px; flex-wrap: wrap; justify-content: center; }
  .nav-pill { flex-wrap: wrap; justify-content: center; }
}
</style>