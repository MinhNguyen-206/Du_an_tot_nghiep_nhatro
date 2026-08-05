<template>
  <div class="rc-profile-wrapper">
    <Header />
    <LandlordProfile v-if="isLandlord" />
    <TenantProfile v-else />
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useAuthStore } from '../../stores/authStore'
import Header from '../../components/layout/Header.vue'
import TenantProfile from './TenantProfile.vue'
import LandlordProfile from './LandlordProfile.vue'

const authStore = useAuthStore()

const isLandlord = computed(() => {
  return authStore.user?.vaiTro === 2
})
</script>

<style scoped>
.rc-profile-wrapper {
  position: relative;
  min-height: 100vh;
  background-color: #f4f6f9;
  padding-top: 110px; /* Space for header */
  padding-bottom: 50px;
}

.rc-profile-wrapper::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 90px;
  background: #ffffff;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
  z-index: 0;
}
</style>
