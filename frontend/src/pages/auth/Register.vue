<template>
  <div class="auth-page">
    <!-- Panel form bên trái -->
    <div class="auth-form-panel">
      <div class="auth-form">
        <h2 class="auth-title">Đăng Ký</h2>
        <p class="auth-subtitle">
          Vui lòng tạo tài khoản để có thể bắt đầu tìm kiếm phòng trên Room Connect.
        </p>

        <form @submit.prevent="handleRegister">
          <div class="field">
            <label class="field__label">Họ và tên *</label>
            <input
              v-model="form.hoTen"
              type="text"
              class="field__input"
              placeholder="Nguyễn Văn A"
              required
            />
          </div>

          <div class="field-row">
            <div class="field">
              <label class="field__label">Email *</label>
              <input
                v-model="form.email"
                type="email"
                class="field__input"
                placeholder="ban@email.com"
                required
              />
            </div>
            <div class="field">
              <label class="field__label">Số điện thoại *</label>
              <input
                v-model="form.soDienThoai"
                type="tel"
                class="field__input"
                placeholder="0123456789"
                required
              />
            </div>
          </div>

          <div class="field">
            <label class="field__label">Mật khẩu *</label>
            <div class="field__password">
              <input
                v-model="form.matKhau"
                :type="showPassword ? 'text' : 'password'"
                class="field__input"
                placeholder="••••••••••"
                required
                minlength="6"
              />
              <button
                type="button"
                class="eye-btn"
                @click="showPassword = !showPassword"
                :aria-label="showPassword ? 'Ẩn mật khẩu' : 'Hiện mật khẩu'"
              >
                <EyeIcon :open="showPassword" />
              </button>
            </div>
          </div>

          <div class="field">
            <label class="field__label">Xác nhận mật khẩu *</label>
            <div class="field__password">
              <input
                v-model="form.xacNhanMatKhau"
                :type="showConfirmPassword ? 'text' : 'password'"
                class="field__input"
                placeholder="••••••••••"
                required
              />
              <button
                type="button"
                class="eye-btn"
                @click="showConfirmPassword = !showConfirmPassword"
                :aria-label="showConfirmPassword ? 'Ẩn mật khẩu' : 'Hiện mật khẩu'"
              >
                <EyeIcon :open="showConfirmPassword" />
              </button>
            </div>
          </div>

          <label class="checkbox">
            <input type="checkbox" v-model="form.dongY" required />
            <span class="checkbox__box"></span>
            Tôi đồng ý với Điều khoản sử dụng và Chính sách bảo mật.
          </label>

          <button type="submit" class="btn-primary" :disabled="loading">
            {{ loading ? 'Đang đăng ký...' : 'Đăng ký' }}
          </button>

          <p v-if="errorMessage" class="form-error">{{ errorMessage }}</p>
        </form>

        <hr class="divider-line" />

        <p class="footer-link">
          Bạn đã có tài khoản?
          <router-link to="/login">Đăng nhập ngay</router-link>
        </p>
      </div>
    </div>

    <!-- Panel ảnh bên phải -->
    <div class="auth-visual">
      <div class="auth-visual__overlay"></div>
      <div class="auth-visual__content">
        <div class="progress-bar">
          <span class="progress-bar__seg"></span>
          <span class="progress-bar__seg is-active"></span>
          <span class="progress-bar__seg"></span>
        </div>
        <h1 class="brand">ROOM CONNECT</h1>
        <p class="tagline">Kết nối không gian, sẻ chia cuộc sống.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, h } from 'vue'
import { useRouter } from 'vue-router'
import { registerApi } from '../../api/authApi'

const router = useRouter()

const form = reactive({
  hoTen: '',
  email: '',
  soDienThoai: '',
  matKhau: '',
  xacNhanMatKhau: '',
  dongY: false
})
const showPassword = ref(false)
const showConfirmPassword = ref(false)
const loading = ref(false)
const errorMessage = ref('')

async function handleRegister() {
  errorMessage.value = ''

  if (form.matKhau !== form.xacNhanMatKhau) {
    errorMessage.value = 'Mật khẩu xác nhận không khớp'
    return
  }
  if (!form.dongY) {
    errorMessage.value = 'Bạn cần đồng ý với Điều khoản sử dụng và Chính sách bảo mật'
    return
  }

  loading.value = true
  try {
    // Field khớp với entity NguoiDung ở backend.
    // vaiTro: 1 = người thuê (mặc định khi tự đăng ký)
    await registerApi({
      hoTen: form.hoTen,
      email: form.email,
      soDienThoai: form.soDienThoai,
      matKhauMaHoa: form.matKhau,
      vaiTro: 1,
      trangThaiTaiKhoan: 1,
      daXacMinhEkyc: false
    })
    router.push('/login')
  } catch (error) {
    errorMessage.value = 'Đăng ký thất bại, vui lòng kiểm tra lại thông tin'
  } finally {
    loading.value = false
  }
}

const EyeIcon = (props) =>
  h(
    'svg',
    { width: 20, height: 20, viewBox: '0 0 24 24', fill: 'none', stroke: 'currentColor', 'stroke-width': 2 },
    props.open
      ? [
          h('path', { d: 'M1 12s4-7 11-7 11 7 11 7-4 7-11 7-11-7-11-7Z' }),
          h('circle', { cx: 12, cy: 12, r: 3 })
        ]
      : [
          h('path', { d: 'M17.94 17.94A10.94 10.94 0 0 1 12 20c-7 0-11-8-11-8a20.7 20.7 0 0 1 5.06-6.06M9.9 4.24A10.4 10.4 0 0 1 12 4c7 0 11 8 11 8a20.6 20.6 0 0 1-2.16 3.19M14.12 14.12a3 3 0 1 1-4.24-4.24' }),
          h('line', { x1: 1, y1: 1, x2: 23, y2: 23 })
        ]
  )
</script>

<style scoped>
* { box-sizing: border-box; }

.auth-page {
  min-height: 100vh;
  display: grid;
  grid-template-columns: 1fr 1.1fr;
  font-family: 'Segoe UI', Arial, sans-serif;
  background: #f5f5f4;
}

/* ---- Panel form ---- */
.auth-form-panel {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 48px 40px;
}
.auth-form { width: 100%; max-width: 460px; }
.auth-title { margin: 0 0 12px; font-size: 34px; font-weight: 800; color: #111; }
.auth-subtitle { margin: 0 0 28px; color: #555; font-size: 15px; line-height: 1.5; }

.field { margin-bottom: 18px; }
.field-row { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
.field__label { display: block; margin-bottom: 8px; font-size: 14px; color: #222; }
.field__input {
  width: 100%;
  padding: 14px 20px;
  border: none;
  border-radius: 999px;
  background: #ece1df;
  font-size: 15px;
  color: #1a1a1a;
  outline: none;
  transition: box-shadow 0.15s ease;
}
.field__input:focus { box-shadow: 0 0 0 2px #b9a6a3; }
.field__input::placeholder { color: #9c9c9c; }

.field__password { position: relative; }
.field__password .field__input { padding-right: 52px; }
.eye-btn {
  position: absolute;
  right: 14px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: #333;
  cursor: pointer;
  display: flex;
}

.checkbox {
  display: flex;
  align-items: flex-start;
  gap: 10px;
  margin: 8px 0 24px;
  font-size: 14px;
  color: #333;
  cursor: pointer;
  user-select: none;
  line-height: 1.4;
}
.checkbox input { display: none; }
.checkbox__box {
  flex: none;
  margin-top: 2px;
  width: 18px;
  height: 18px;
  border-radius: 5px;
  border: 2px solid #e3b6c9;
  background: #fff;
  display: inline-block;
  position: relative;
}
.checkbox input:checked + .checkbox__box { background: #111; border-color: #111; }
.checkbox input:checked + .checkbox__box::after {
  content: '';
  position: absolute;
  left: 5px; top: 1px;
  width: 4px; height: 9px;
  border: solid #fff;
  border-width: 0 2px 2px 0;
  transform: rotate(45deg);
}

.btn-primary {
  width: 100%;
  padding: 16px;
  border: none;
  border-radius: 999px;
  background: #111;
  color: #fff;
  font-size: 16px;
  font-weight: 700;
  cursor: pointer;
  transition: opacity 0.15s ease;
}
.btn-primary:hover { opacity: 0.9; }
.btn-primary:disabled { opacity: 0.6; cursor: not-allowed; }

.form-error { margin-top: 12px; color: #d64545; font-size: 14px; text-align: center; }

.divider-line { border: none; border-top: 1px solid #ddd; margin: 28px 0 20px; }

.footer-link { text-align: center; font-size: 14px; color: #7a6df0; }
.footer-link a { color: #111; font-weight: 700; text-decoration: none; margin-left: 4px; }
.footer-link a:hover { text-decoration: underline; }

/* ---- Panel ảnh ---- */
.auth-visual {
  position: relative;
  min-height: 100vh;
  background: linear-gradient(160deg, #2b2015 0%, #4a3520 45%, #3a2a18 100%);
  overflow: hidden;
}
.auth-visual::after {
  content: '';
  position: absolute;
  inset: 0;
  background:
    radial-gradient(circle at 70% 25%, rgba(255,200,120,0.12), transparent 45%),
    radial-gradient(circle at 20% 80%, rgba(0,0,0,0.4), transparent 60%);
}
.auth-visual__overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(0deg, rgba(10,7,4,0.7) 0%, rgba(10,7,4,0.1) 55%, rgba(10,7,4,0.45) 100%);
}
.auth-visual__content {
  position: absolute;
  left: 48px;
  bottom: 48px;
  right: 48px;
  color: #fff;
  z-index: 2;
}
.progress-bar { display: flex; gap: 8px; margin-bottom: 20px; }
.progress-bar__seg { height: 4px; width: 64px; border-radius: 999px; background: rgba(255,255,255,0.35); }
.progress-bar__seg.is-active { background: #ffffff; }
.brand { margin: 0 0 6px; font-size: 32px; font-weight: 800; letter-spacing: 0.5px; }
.tagline { margin: 0; font-size: 15px; color: rgba(255,255,255,0.85); }

@media (max-width: 900px) {
  .auth-page { grid-template-columns: 1fr; }
  .auth-form-panel { padding: 32px 20px; order: 1; }
  .auth-visual { min-height: 220px; order: 0; }
}
</style>