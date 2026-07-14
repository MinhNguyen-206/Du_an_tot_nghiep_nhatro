<template>
  <div class="login-page">
    <div class="login-card">
      <h2>Đăng nhập</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label>Email</label>
          <input v-model="form.email" type="email" required />
        </div>
        <div class="form-group">
          <label>Mật khẩu</label>
          <input v-model="form.password" type="password" required />
        </div>
        <button type="submit">Đăng nhập</button>
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/authStore'
import { loginApi } from '../../api/authApi'

const router = useRouter()
const authStore = useAuthStore()
const form = reactive({ email: '', password: '' })
const errorMessage = ref('')

async function handleLogin() {
  errorMessage.value = ''
  try {
    const { data } = await loginApi(form)
    authStore.login(data.user, data.token)

    if (data.user?.vaiTro === 3) {
      router.push('/admin')
    } else {
      router.push('/')
    }
  } catch (error) {
    errorMessage.value = 'Email hoặc mật khẩu không đúng'
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #f4f6f8;
}
.login-card {
  background: white;
  padding: 24px;
  border-radius: 12px;
  width: 100%;
  max-width: 360px;
  box-shadow: 0 8px 24px rgba(0,0,0,0.1);
}
.form-group { display: flex; flex-direction: column; margin-bottom: 12px; }
input { padding: 10px; border: 1px solid #ccc; border-radius: 8px; }
button { width: 100%; padding: 10px; background: #2563eb; color: white; border: none; border-radius: 8px; cursor: pointer; }
.error { color: red; margin-top: 8px; }
</style>
