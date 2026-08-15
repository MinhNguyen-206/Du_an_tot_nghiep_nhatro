<template>
  <div class="auth-page">
    <!-- Panel ảnh bên trái -->
    <div class="auth-visual">
      <div class="auth-visual__overlay"></div>
      <div class="auth-visual__content">
        <div class="progress-bar">
          <span class="progress-bar__seg is-active"></span>
          <span class="progress-bar__seg"></span>
          <span class="progress-bar__seg"></span>
        </div>
        <h1 class="brand">ROOM CONNECT</h1>
        <p class="tagline">Kết nối không gian, sẻ chia cuộc sống.</p>
      </div>
    </div>

    <!-- Panel form bên phải -->
    <div class="auth-form-panel">
      <div class="auth-form">
        <h2 class="auth-title">Chào mừng trở lại</h2>
        <p class="auth-subtitle">
          Vui lòng đăng nhập vào tài khoản của bạn tại <em>Room Connect</em>.
        </p>

        <form @submit.prevent="handleLogin">
          <div class="field">
            <label class="field__label">Email hoặc số điện thoại *</label>
            <input
              v-model="form.email"
              type="text"
              class="field__input"
              placeholder="ban@email.com"
              required
            />
          </div>

          <div class="field">
            <div class="field__row">
              <label class="field__label">Mật khẩu *</label>
              <router-link to="/forgot-password" class="link-muted"
                >Quên mật khẩu ?</router-link
              >
            </div>
            <div class="field__password">
              <input
                v-model="form.password"
                :type="showPassword ? 'text' : 'password'"
                class="field__input"
                placeholder="••••••••••"
                required
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

          <label class="checkbox">
            <input type="checkbox" v-model="form.remember" />
            <span class="checkbox__box"></span>
            Nhớ mật khẩu
          </label>

          <button type="submit" class="btn-primary" :disabled="loading">
            {{ loading ? "Đang đăng nhập..." : "Đăng nhập" }}
          </button>

          <p v-if="errorMessage" class="form-error">{{ errorMessage }}</p>
        </form>

        <div class="divider"><span>Hoặc đăng nhập với</span></div>

        <div class="social-row">
          <button type="button" class="btn-social" @click="loginWithGoogle">
            <GoogleIcon /> Google
          </button>
          <button type="button" class="btn-social" @click="loginWithFacebook">
            <FacebookIcon /> Facebook
          </button>
        </div>

        <p class="footer-link">
          Bạn chưa có tài khoản?
          <router-link to="/register">Đăng ký ngay</router-link>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, h } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "../../stores/authStore";
import { loginApi } from "../../api/authApi";

const router = useRouter();
const authStore = useAuthStore();

const form = reactive({ email: "", password: "", remember: false });
const showPassword = ref(false);
const loading = ref(false);
const errorMessage = ref("");

async function handleLogin() {
  errorMessage.value = "";
  loading.value = true;
  try {
    const { data } = await loginApi({
      email: form.email,
      password: form.password,
    });
    authStore.login(data.user, data.token);

    if (data.user?.vaiTro === 3) {
      router.push("/admin");
    } else {
      router.push("/");
    }
  } catch (error) {
    errorMessage.value = "Email/số điện thoại hoặc mật khẩu không đúng";
  } finally {
    loading.value = false;
  }
}

// NOTE: nối OAuth Google/Facebook khi backend có endpoint tương ứng
function loginWithGoogle() {
  console.log("Đăng nhập với Google - chưa triển khai");
}
function loginWithFacebook() {
  console.log("Đăng nhập với Facebook - chưa triển khai");
}

// Icon inline, không phụ thuộc thư viện ngoài
const EyeIcon = (props) =>
  h(
    "svg",
    {
      width: 20,
      height: 20,
      viewBox: "0 0 24 24",
      fill: "none",
      stroke: "currentColor",
      "stroke-width": 2,
    },
    props.open
      ? [
          h("path", { d: "M1 12s4-7 11-7 11 7 11 7-4 7-11 7-11-7-11-7Z" }),
          h("circle", { cx: 12, cy: 12, r: 3 }),
        ]
      : [
          h("path", {
            d: "M17.94 17.94A10.94 10.94 0 0 1 12 20c-7 0-11-8-11-8a20.7 20.7 0 0 1 5.06-6.06M9.9 4.24A10.4 10.4 0 0 1 12 4c7 0 11 8 11 8a20.6 20.6 0 0 1-2.16 3.19M14.12 14.12a3 3 0 1 1-4.24-4.24",
          }),
          h("line", { x1: 1, y1: 1, x2: 23, y2: 23 }),
        ],
  );

const GoogleIcon = () =>
  h("svg", { width: 18, height: 18, viewBox: "0 0 24 24" }, [
    h("path", {
      fill: "#4285F4",
      d: "M23.52 12.27c0-.82-.07-1.6-.2-2.36H12v4.47h6.47a5.53 5.53 0 0 1-2.4 3.63v3h3.88c2.27-2.09 3.57-5.17 3.57-8.74Z",
    }),
    h("path", {
      fill: "#34A853",
      d: "M12 24c3.24 0 5.96-1.07 7.95-2.9l-3.88-3a7.4 7.4 0 0 1-4.07 1.14c-3.13 0-5.78-2.11-6.73-4.96H1.26v3.1A12 12 0 0 0 12 24Z",
    }),
    h("path", {
      fill: "#FBBC05",
      d: "M5.27 14.28A7.2 7.2 0 0 1 4.89 12c0-.79.14-1.56.38-2.28v-3.1H1.26A12 12 0 0 0 0 12c0 1.94.47 3.77 1.26 5.38l4.01-3.1Z",
    }),
    h("path", {
      fill: "#EA4335",
      d: "M12 4.77c1.76 0 3.34.6 4.58 1.79l3.44-3.44C17.95 1.19 15.24 0 12 0A12 12 0 0 0 1.26 6.62l4.01 3.1C6.22 6.88 8.87 4.77 12 4.77Z",
    }),
  ]);

const FacebookIcon = () =>
  h("svg", { width: 18, height: 18, viewBox: "0 0 24 24", fill: "#1877F2" }, [
    h("path", {
      d: "M24 12.07C24 5.4 18.63 0 12 0S0 5.4 0 12.07C0 18.1 4.39 23.1 10.13 24v-8.44H7.08v-3.49h3.05V9.41c0-3.02 1.79-4.7 4.53-4.7 1.31 0 2.68.24 2.68.24v2.97h-1.51c-1.49 0-1.96.93-1.96 1.89v2.26h3.33l-.53 3.49h-2.8V24C19.61 23.1 24 18.1 24 12.07Z",
    }),
  ]);
</script>

<style scoped>
* {
  box-sizing: border-box;
}

.auth-page {
  min-height: 100vh;
  display: grid;
  grid-template-columns: 1.1fr 1fr;
  font-family: "Segoe UI", Arial, sans-serif;
  background: #f5f5f4;
}

/* ---- Panel ảnh ---- */
.auth-visual {
  position: relative;
  min-height: 100vh;
  background: linear-gradient(160deg, #12261f 0%, #1c3a30 45%, #274e40 100%);
  overflow: hidden;
}
.auth-visual::after {
  content: "";
  position: absolute;
  inset: 0;
  background:
    radial-gradient(
      circle at 30% 20%,
      rgba(255, 255, 255, 0.06),
      transparent 40%
    ),
    radial-gradient(circle at 80% 70%, rgba(0, 0, 0, 0.35), transparent 60%);
}
.auth-visual__overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(
    0deg,
    rgba(6, 20, 15, 0.75) 0%,
    rgba(6, 20, 15, 0.15) 55%,
    rgba(6, 20, 15, 0.55) 100%
  );
}
.auth-visual__content {
  position: absolute;
  left: 48px;
  bottom: 48px;
  right: 48px;
  color: #fff;
  z-index: 2;
}
.progress-bar {
  display: flex;
  gap: 8px;
  margin-bottom: 20px;
}
.progress-bar__seg {
  height: 4px;
  width: 64px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.35);
}
.progress-bar__seg.is-active {
  background: #ffffff;
}
.brand {
  margin: 0 0 6px;
  font-size: 32px;
  font-weight: 800;
  letter-spacing: 0.5px;
}
.tagline {
  margin: 0;
  font-size: 15px;
  color: rgba(255, 255, 255, 0.85);
}

/* ---- Panel form ---- */
.auth-form-panel {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 48px 40px;
}
.auth-form {
  width: 100%;
  max-width: 460px;
}
.auth-title {
  margin: 0 0 12px;
  font-size: 34px;
  font-weight: 800;
  color: #111;
}
.auth-subtitle {
  margin: 0 0 32px;
  color: #555;
  font-size: 15px;
  line-height: 1.5;
}
.auth-subtitle em {
  font-style: italic;
}

.field {
  margin-bottom: 20px;
}
.field__row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.field__label {
  display: block;
  margin-bottom: 8px;
  font-size: 14px;
  color: #222;
}
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
.field__input:focus {
  box-shadow: 0 0 0 2px #b9a6a3;
}
.field__input::placeholder {
  color: #9c9c9c;
}

.field__password {
  position: relative;
}
.field__password .field__input {
  padding-right: 52px;
}
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

.link-muted {
  font-size: 13px;
  font-weight: 700;
  color: #111;
  text-decoration: none;
}
.link-muted:hover {
  text-decoration: underline;
}

.checkbox {
  display: flex;
  align-items: center;
  gap: 10px;
  margin: 4px 0 24px;
  font-size: 14px;
  color: #333;
  cursor: pointer;
  user-select: none;
}
.checkbox input {
  display: none;
}
.checkbox__box {
  width: 18px;
  height: 18px;
  border-radius: 5px;
  border: 2px solid #e3b6c9;
  background: #fff;
  display: inline-block;
  position: relative;
}
.checkbox input:checked + .checkbox__box {
  background: #111;
  border-color: #111;
}
.checkbox input:checked + .checkbox__box::after {
  content: "";
  position: absolute;
  left: 5px;
  top: 1px;
  width: 4px;
  height: 9px;
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
.btn-primary:hover {
  opacity: 0.9;
}
.btn-primary:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.form-error {
  margin-top: 12px;
  color: #d64545;
  font-size: 14px;
  text-align: center;
}

.divider {
  display: flex;
  align-items: center;
  text-align: center;
  color: #8a8a8a;
  font-size: 14px;
  font-style: italic;
  margin: 28px 0 20px;
}
.divider::before,
.divider::after {
  content: "";
  flex: 1;
  height: 1px;
  background: #ddd;
}
.divider span {
  padding: 0 14px;
  white-space: nowrap;
}

.social-row {
  display: flex;
  gap: 16px;
  margin-bottom: 28px;
}
.btn-social {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  padding: 13px;
  border-radius: 999px;
  border: 1px solid #ddd;
  background: #fff;
  font-size: 15px;
  font-style: italic;
  cursor: pointer;
}
.btn-social:hover {
  background: #f8f8f8;
}

.footer-link {
  text-align: center;
  font-size: 14px;
  color: #7a6df0;
}
.footer-link a {
  color: #111;
  font-weight: 700;
  text-decoration: none;
  margin-left: 4px;
}
.footer-link a:hover {
  text-decoration: underline;
}

@media (max-width: 900px) {
  .auth-page {
    grid-template-columns: 1fr;
  }
  .auth-visual {
    min-height: 260px;
  }
  .auth-form-panel {
    padding: 32px 20px;
  }
}
</style>
