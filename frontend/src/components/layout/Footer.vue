<template>
  <footer class="rc-footer">
    <div class="rc-footer__inner">
      <div class="rc-footer__brand">
        <span class="rc-footer__stamp">RC</span>
        <div>
          <p class="rc-footer__title">Room Connect</p>
          <p class="rc-footer__desc">
            Sổ tay tìm trọ thế hệ mới — dán một tin, tìm một nhà, không cần dán giấy lên cột điện nữa.
          </p>
        </div>
      </div>

      <div class="rc-footer__page">
        <h4>Thông tin liên hệ</h4>
        <p>Email: lienhe@roomconnect.vn</p>
        <p>Địa chỉ: TP. Hồ Chí Minh</p>
      </div>

      <div class="rc-footer__page">
        <h4>Hotline</h4>
        <p>1900 0000</p>
        <p>Hỗ trợ 8:00 – 22:00 mỗi ngày</p>
      </div>
    </div>

    <div class="rc-footer__bottom">
      © {{ new Date().getFullYear() }} Room Connect · Bản in thử, không dùng để dán cột điện.
    </div>
  </footer>
</template>

<script setup>
</script>

<style scoped>
.rc-footer {
  background: #211d17;
  color: #e9dfc4;
  padding: 48px 28px 0;
  font-family: 'Be Vietnam Pro', 'Segoe UI', Arial, sans-serif;
}
.rc-footer__inner {
  max-width: 1180px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: 1.5fr 1fr 1fr;
  gap: 28px;
}
.rc-footer__brand { display: flex; gap: 14px; align-items: flex-start; }
.rc-footer__stamp {
  flex: none;
  width: 40px; height: 40px;
  border-radius: 50%;
  border: 2px dashed rgba(233, 223, 196, 0.6);
  display: flex; align-items: center; justify-content: center;
  background: #c23b2b;
  color: #f1e8ce;
  font-family: 'Archivo Black', sans-serif;
  font-size: 12px;
  transform: rotate(-6deg);
}
.rc-footer__title { margin: 2px 0 6px; font-family: 'Archivo Black', sans-serif; font-size: 16px; color: #fff; letter-spacing: 0.5px; }
.rc-footer__desc { margin: 0; font-size: 13px; line-height: 1.6; color: #b7ab8c; max-width: 320px; }

.rc-footer__page {
  border-left: 1px dashed rgba(233, 223, 196, 0.25);
  padding-left: 20px;
}
.rc-footer__page h4 { margin: 0 0 12px; font-size: 13px; letter-spacing: 1px; text-transform: uppercase; color: #e4a63a; }
.rc-footer__page p { margin: 0 0 6px; font-size: 13px; color: #b7ab8c; }

.rc-footer__bottom {
  margin-top: 34px;
  border-top: 1px dashed rgba(233, 223, 196, 0.2);
  padding: 16px 0;
  text-align: center;
  font-size: 11.5px;
  color: #8a7f65;
}

@media (max-width: 720px) {
  .rc-footer__inner { grid-template-columns: 1fr; }
  .rc-footer__page { border-left: none; padding-left: 0; }
}
</style>