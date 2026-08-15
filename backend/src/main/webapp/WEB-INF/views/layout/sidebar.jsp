<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<aside class="admin-sidebar">
  <div class="admin-sidebar__header">
    <a href="/" class="admin-sidebar__logo">RC Admin</a>
  </div>
  <nav class="admin-sidebar__nav">
    <a href="/" class="admin-nav-item">Trang chủ</a>
    <div class="admin-nav-divider">QUẢN LÝ</div>
    <a href="/admin" class="admin-nav-item">Dashboard</a>
    <a href="/admin/users" class="admin-nav-item">Người dùng</a>
    <a href="/admin/houses" class="admin-nav-item">Nhà trọ</a>
    <a href="/admin/posts" class="admin-nav-item">Tin đăng</a>
    <a href="/admin/contracts" class="admin-nav-item">Hợp đồng</a>
    <a href="/admin/payments" class="admin-nav-item">Thanh toán</a>
    <div class="admin-nav-divider">CẤU HÌNH</div>
    <a href="/admin/roles" class="admin-nav-item">Phân quyền</a>
    <a href="/admin/admins" class="admin-nav-item">Tài khoản Admin</a>
    <a href="/admin/categories" class="admin-nav-item">Danh mục</a>
    <a href="/admin/activity-logs" class="admin-nav-item">Nhật ký HĐ</a>
    <div class="admin-nav-divider">KIỂM SOÁT</div>
    <a href="/admin/violation-reports" class="admin-nav-item">Báo cáo VP</a>
    <a href="/admin/ai-control" class="admin-nav-item">Điều khiển AI</a>
    <div class="admin-nav-divider"></div>
    <a href="/logout" class="admin-nav-item admin-nav-item--danger">Đăng xuất</a>
  </nav>
</aside>
<style>
.admin-sidebar{width:240px;min-height:100vh;background:#1a1f2e;display:flex;flex-direction:column;flex-shrink:0;position:sticky;top:0;height:100vh;overflow-y:auto;}
.admin-sidebar__header{padding:20px;border-bottom:1px solid rgba(255,255,255,.08);}
.admin-sidebar__logo{font-family:'Archivo Black',sans-serif;font-size:16px;color:#e4a63a;text-decoration:none;}
.admin-sidebar__nav{padding:12px 0;flex:1;}
.admin-nav-item{display:flex;align-items:center;gap:10px;padding:11px 20px;font-size:13.5px;font-weight:500;color:rgba(255,255,255,.65);text-decoration:none;transition:.2s;}
.admin-nav-item:hover,.admin-nav-item.active{background:rgba(255,255,255,.07);color:#fff;border-left:3px solid #e4a63a;}
.admin-nav-item span{font-size:15px;width:20px;text-align:center;}
.admin-nav-item--danger{color:rgba(231,76,60,.8)!important;}
.admin-nav-item--danger:hover{background:rgba(231,76,60,.1)!important;color:#e74c3c!important;}
.admin-nav-divider{padding:12px 20px 6px;font-size:10px;font-weight:700;letter-spacing:1px;text-transform:uppercase;color:rgba(255,255,255,.25);}
</style>
<script>
// Highlight menu item hiện tại
document.querySelectorAll('.admin-nav-item').forEach(a => {
  if(a.href && window.location.pathname === new URL(a.href, location.origin).pathname) {
    a.classList.add('active');
  }
});
</script>
