<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer class="site-footer">
  <div class="container site-footer__inner">
    <div class="site-footer__brand">
      <span class="footer-logo">ROOM CONNECT</span>
      <p>Kết nối không gian, sẻ chia cuộc sống.</p>
    </div>
    <div class="site-footer__links">
      <h4>Dịch vụ</h4>
      <a href="/rooms">Tìm phòng</a>
      <a href="/premium/packages">Gói Premium</a>
      <a href="/register">Đăng ký</a>
    </div>
    <div class="site-footer__links">
      <h4>Hỗ trợ</h4>
      <a href="#">Câu hỏi thường gặp</a>
      <a href="#">Điều khoản sử dụng</a>
      <a href="#">Chính sách bảo mật</a>
    </div>
    <div class="site-footer__links">
      <h4>Liên hệ</h4>
      <a href="mailto:support@roomconnect.vn">support@roomconnect.vn</a>
      <a href="tel:19001234">1900 1234</a>
    </div>
  </div>
  <div class="site-footer__bottom">
    <p>© 2025 Room Connect. Mọi quyền được bảo lưu.</p>
  </div>
</footer>
<style>
.site-footer{background:#1f4b3f;color:rgba(241,232,206,.85);margin-top:64px;}
.site-footer__inner{display:grid;grid-template-columns:2fr 1fr 1fr 1fr;gap:40px;padding:48px 24px 32px;max-width:1200px;margin:0 auto;}
.footer-logo{font-family:'Archivo Black',sans-serif;font-size:18px;color:#e4a63a;}
.site-footer__brand p{margin:12px 0 0;font-size:13px;opacity:.75;line-height:1.6;}
.site-footer__links h4{color:#e4a63a;font-size:12px;font-weight:700;text-transform:uppercase;letter-spacing:.6px;margin:0 0 16px;}
.site-footer__links a{display:block;font-size:13px;color:rgba(241,232,206,.75);text-decoration:none;margin-bottom:8px;transition:.2s;}
.site-footer__links a:hover{color:#f1e8ce;}
.site-footer__bottom{border-top:1px solid rgba(241,232,206,.1);padding:20px 24px;text-align:center;font-size:12px;opacity:.55;}
@media(max-width:768px){.site-footer__inner{grid-template-columns:1fr 1fr;}}
@media(max-width:480px){.site-footer__inner{grid-template-columns:1fr;}}
</style>
