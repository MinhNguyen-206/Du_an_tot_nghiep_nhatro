<template>
  <div class="fp">
    <section class="fp-hero">
      <Header />

      <div class="fp-hero__content">
        <span class="fp-eyebrow"
          >Sổ tay tìm trọ &middot; báo mất chìa khóa</span
        >

        <div class="slip" :class="{ 'is-stamped': submitted }">
          <span class="slip__pin"></span>

          <template v-if="!submitted">
            <div class="slip__stamp-ring">
              <KeyIcon />
            </div>

            <h1 class="slip__title">QUÊN MẬT KHẨU?</h1>
            <p class="slip__subtitle">
              Không sao cả — ai cũng có lúc làm thất lạc "chìa khóa" của mình.
              Điền thông tin bên dưới, chúng tôi sẽ gửi liên kết để bạn làm
              chiếc chìa mới.
            </p>

            <form class="slip__form" @submit.prevent="handleSubmit">
              <label class="slip__label">Email hoặc số điện thoại *</label>
              <input
                v-model="email"
                type="text"
                class="slip__input"
                placeholder="VD: ban@email.com"
                required
              />

              <button
                type="submit"
                class="btn-stamp-submit"
                :disabled="loading"
              >
                {{ loading ? "Đang gửi..." : "GỬI LIÊN KẾT KHÔI PHỤC" }} →
              </button>
            </form>
          </template>

          <template v-else>
            <span class="approved-stamp">ĐÃ GỬI</span>
            <div class="slip__stamp-ring slip__stamp-ring--done">
              <CheckIcon />
            </div>
            <h1 class="slip__title">KIỂM TRA HÒM THƯ</h1>
            <p class="slip__subtitle">
              Nếu <strong>{{ email }}</strong> có trong sổ đăng ký của chúng
              tôi, một liên kết khôi phục đã được gửi tới đó. Đừng quên xem cả
              mục thư rác nhé.
            </p>
            <button
              type="button"
              class="btn-outline-slip"
              @click="submitted = false"
            >
              Gửi lại liên kết
            </button>
          </template>

          <hr class="slip__divider" />
          <router-link to="/login" class="slip__back"
            >← Quay lại đăng nhập</router-link
          >
        </div>
      </div>
    </section>

    <Footer />
  </div>
</template>

<script setup>
import { h, ref } from "vue";
import Header from "../../components/layout/Header.vue";
import Footer from "../../components/layout/Footer.vue";

const email = ref("");
const loading = ref(false);
const submitted = ref(false);

async function handleSubmit() {
  loading.value = true;
  try {
    // NOTE: nối API thật khi backend có endpoint quên mật khẩu, ví dụ:
    // await axiosClient.post('/auth/forgot-password', { email: email.value })
    // Hiện AuthController mới chỉ có /auth/login, chưa có endpoint này.
    await new Promise((resolve) => setTimeout(resolve, 700));
    submitted.value = true;
  } finally {
    loading.value = false;
  }
}

const KeyIcon = () =>
  h(
    "svg",
    {
      width: 30,
      height: 30,
      viewBox: "0 0 24 24",
      fill: "none",
      stroke: "currentColor",
      "stroke-width": 1.8,
    },
    [
      h("circle", { cx: 8, cy: 15, r: 4 }),
      h("path", { d: "M11 12 20 3" }),
      h("path", { d: "M16 4l3 3" }),
      h("path", { d: "M13.5 6.5l2 2" }),
    ],
  );

const CheckIcon = () =>
  h(
    "svg",
    {
      width: 26,
      height: 26,
      viewBox: "0 0 24 24",
      fill: "none",
      stroke: "currentColor",
      "stroke-width": 2.2,
    },
    [h("path", { d: "M20 6 9 17l-5-5" })],
  );
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Archivo+Black&family=Be+Vietnam+Pro:wght@400;500;600;700;800&display=swap");
* {
  box-sizing: border-box;
}

.fp {
  --paper: #f1e8ce;
  --paper-dark: #e5d8ae;
  --ink: #211d17;
  --pine: #1f4b3f;
  --pine-dark: #163a30;
  --brick: #c23b2b;
  --brick-dark: #a32e20;
  --mustard: #e4a63a;
  font-family: "Be Vietnam Pro", "Segoe UI", Arial, sans-serif;
  color: var(--ink);
  background: var(--paper);
}

.fp-hero {
  position: relative;
  min-height: 100vh;
  padding: 120px 24px 80px;
  display: flex;
  justify-content: center;
  background:
    radial-gradient(rgba(0, 0, 0, 0.05) 1px, transparent 1.4px) 0 0/14px 14px,
    linear-gradient(160deg, var(--pine) 0%, var(--pine-dark) 100%);
  overflow: hidden;
}
.fp-hero::before {
  content: "";
  position: absolute;
  inset: 0;
  background: repeating-linear-gradient(
    0deg,
    rgba(0, 0, 0, 0.06) 0 1px,
    transparent 1px 90px
  );
  pointer-events: none;
}

.fp-hero__content {
  position: relative;
  z-index: 2;
  width: 100%;
  max-width: 440px;
  text-align: center;
}
.fp-eyebrow {
  display: inline-block;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 1.5px;
  text-transform: uppercase;
  color: var(--mustard);
  margin-bottom: 22px;
}

/* ---------- Tờ đơn báo mất chìa khóa ---------- */
.slip {
  position: relative;
  background: var(--paper);
  border-radius: 4px 12px 6px 14px;
  padding: 40px 34px 30px;
  box-shadow: 0 26px 50px rgba(0, 0, 0, 0.3);
  transform: rotate(-0.6deg);
}
.slip__pin {
  position: absolute;
  top: -13px;
  left: 50%;
  margin-left: -13px;
  width: 26px;
  height: 26px;
  border-radius: 50%;
  background: radial-gradient(circle at 35% 30%, #e9765c, var(--brick-dark));
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.35);
}

.slip__stamp-ring {
  width: 66px;
  height: 66px;
  margin: 0 auto 18px;
  border: 3px dashed var(--brick);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--brick);
  transform: rotate(-4deg);
}
.slip__stamp-ring--done {
  border-color: var(--pine);
  color: var(--pine);
}

.slip__title {
  margin: 0 0 12px;
  font-family: "Archivo Black", sans-serif;
  font-size: 24px;
  letter-spacing: 0.5px;
}
.slip__subtitle {
  margin: 0 0 26px;
  font-size: 14px;
  line-height: 1.65;
  color: #4a4335;
}
.slip__subtitle strong {
  color: var(--ink);
}

.slip__form {
  text-align: left;
}
.slip__label {
  display: block;
  margin-bottom: 8px;
  font-size: 13px;
  font-weight: 600;
}
.slip__input {
  width: 100%;
  padding: 13px 16px;
  border: none;
  border-radius: 6px;
  background: var(--paper-dark);
  font-size: 14px;
  font-family: inherit;
  color: var(--ink);
  outline: none;
  margin-bottom: 20px;
}
.slip__input::placeholder {
  color: #8a7f65;
}
.slip__input:focus {
  box-shadow: 0 0 0 2px var(--pine);
}

.btn-stamp-submit {
  width: 100%;
  border: none;
  border-radius: 6px;
  background: var(--brick);
  color: var(--paper);
  font-family: "Archivo Black", sans-serif;
  font-size: 13.5px;
  letter-spacing: 0.4px;
  padding: 15px;
  cursor: pointer;
}
.btn-stamp-submit:hover {
  background: var(--brick-dark);
}
.btn-stamp-submit:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-outline-slip {
  width: 100%;
  border: 1.5px dashed var(--pine);
  border-radius: 6px;
  background: transparent;
  color: var(--pine);
  font-weight: 700;
  font-size: 13.5px;
  padding: 13px;
  cursor: pointer;
}
.btn-outline-slip:hover {
  background: rgba(31, 75, 63, 0.06);
}

.approved-stamp {
  position: absolute;
  top: 26px;
  right: 10px;
  font-family: "Archivo Black", sans-serif;
  font-size: 13px;
  color: var(--pine);
  border: 3px double var(--pine);
  padding: 6px 12px;
  border-radius: 4px;
  transform: rotate(10deg);
  opacity: 0.85;
}

.slip__divider {
  border: none;
  border-top: 1px dashed rgba(33, 29, 23, 0.2);
  margin: 26px 0 18px;
}
.slip__back {
  font-size: 13.5px;
  color: var(--pine);
  text-decoration: underline;
  text-underline-offset: 3px;
  font-weight: 600;
}
.slip__back:hover {
  color: var(--pine-dark);
}

@media (max-width: 520px) {
  .fp-hero {
    padding: 140px 16px 60px;
  }
  .slip {
    padding: 32px 24px 24px;
  }
}
</style>
