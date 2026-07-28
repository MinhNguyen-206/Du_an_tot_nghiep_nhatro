<template>
  <footer class="site-footer">
    <div class="site-footer__inner">
      <div class="footer-col footer-col--brand">
        <div class="footer-logo">
          <span class="footer-logo__badge">RC</span>
          <span class="footer-logo__text">Room connect</span>
        </div>
        <p class="footer-desc">
          Nền tảng tìm kiếm phòng trọ thế hệ mới. Hiện đại và dễ dàng cho tất cả mọi người.
        </p>
      </div>

      <div class="footer-col">
        <h4>Thông tin liên hệ</h4>
        <p>Email: lienhe@roomconnect.vn</p>
        <p>Địa chỉ: TP. Hồ Chí Minh</p>
      </div>

      <div class="footer-col">
        <h4>Hotline</h4>
        <p>1900 0000</p>
        <p>Hỗ trợ 8:00 - 22:00 mỗi ngày</p>
      </div>
    </div>

    <div class="site-footer__bottom">
      © {{ new Date().getFullYear() }} Room Connect. Bảo lưu mọi quyền.
    </div>
  </footer>
</template>

<script setup>
</script>

<style scoped>
.site-footer {
  background: #4b3f37;
  color: #efe7e2;
  padding: 44px 32px 0;
}
.site-footer__inner {
  max-width: 1200px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: 1.4fr 1fr 1fr;
  gap: 32px;
}
.footer-logo { display: flex; align-items: center; gap: 10px; margin-bottom: 12px; }
.footer-logo__badge {
  width: 28px; height: 28px;
  border-radius: 8px;
  background: #ee6383;
  display: flex; align-items: center; justify-content: center;
  font-size: 11px; font-weight: 800; color: #fff;
}
.footer-logo__text { font-size: 18px; font-weight: 800; color: #fff; }
.footer-desc { font-size: 13px; line-height: 1.6; color: #cfc3bc; max-width: 320px; }

.footer-col h4 { margin: 0 0 12px; font-size: 14px; color: #fff; }
.footer-col p { margin: 0 0 6px; font-size: 13px; color: #cfc3bc; }

.site-footer__bottom {
  margin-top: 32px;
  border-top: 1px solid rgba(255,255,255,0.12);
  padding: 16px 0;
  text-align: center;
  font-size: 12px;
  color: #b3a69e;
}

@media (max-width: 720px) {
  .site-footer__inner { grid-template-columns: 1fr; }
}
</style>