<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>
<style>
.cm-head{display:flex;align-items:center;justify-content:space-between;margin-bottom:24px;flex-wrap:wrap;gap:12px}
.cm-head h1{font-size:22px;font-weight:700;color:#0f172a;margin:0}
.cm-head p{font-size:13px;color:#64748b;margin:2px 0 0}
.cm-grid{display:grid;grid-template-columns:1fr 1fr;gap:20px}
.cm-card{background:#fff;border-radius:16px;border:1px solid #e8edf3;box-shadow:0 1px 6px rgba(0,0,0,.05);overflow:hidden}
.cm-ch{display:flex;align-items:center;justify-content:space-between;padding:16px 20px;border-bottom:1px solid #f1f5f9}
.cm-ch h2{font-size:15px;font-weight:700;color:#0f172a;margin:0;display:flex;align-items:center;gap:8px}
.cm-ch-badge{background:#f1f5f9;color:#475569;font-size:11px;font-weight:700;padding:2px 8px;border-radius:20px}
.cm-add-btn{height:32px;padding:0 14px;border-radius:8px;border:none;background:#7c3aed;color:#fff;font-size:12px;font-weight:600;cursor:pointer;display:flex;align-items:center;gap:5px;transition:all .15s}
.cm-add-btn:hover{background:#6d28d9}
/* Input bar */
.cm-bar{display:flex;gap:8px;padding:12px 16px;border-bottom:1px solid #f1f5f9}
.cm-input{flex:1;height:34px;padding:0 10px;border:1px solid #e2e8f0;border-radius:8px;font-size:13px;outline:none;background:#f8fafc;color:#0f172a;transition:border .15s}
.cm-input:focus{border-color:#7c3aed;background:#fff}
/* Table */
.cm-tbl{width:100%;border-collapse:collapse;font-size:13px}
.cm-tbl th{padding:10px 16px;text-align:left;font-weight:600;color:#475569;font-size:11px;text-transform:uppercase;letter-spacing:.04em;background:#f8fafc;border-bottom:1px solid #e8edf3}
.cm-tbl td{padding:12px 16px;border-bottom:1px solid #f1f5f9;vertical-align:middle}
.cm-tbl tbody tr:last-child td{border-bottom:none}
.cm-tbl tbody tr:hover td{background:#fafbff}
/* Badges */
.cm-bdg{display:inline-flex;align-items:center;gap:4px;padding:3px 9px;border-radius:20px;font-size:11px;font-weight:600}
.cm-bdg.on{background:#f0fdf4;color:#15803d}.cm-bdg.off{background:#fef2f2;color:#b91c1c}
/* Buttons */
.cm-btn{height:28px;padding:0 10px;border-radius:7px;border:1px solid transparent;font-size:12px;font-weight:500;cursor:pointer;display:inline-flex;align-items:center;gap:3px;transition:all .15s}
.cm-be{background:#eff6ff;color:#2563eb;border-color:#bfdbfe}.cm-be:hover{background:#3b82f6;color:#fff}
.cm-bd{background:#fef2f2;color:#b91c1c;border-color:#fecaca}.cm-bd:hover{background:#ef4444;color:#fff}
.cm-bt{background:#f5f3ff;color:#7c3aed;border-color:#ddd6fe}.cm-bt:hover{background:#7c3aed;color:#fff}
/* Empty */
.cm-empty{padding:36px 16px;text-align:center;color:#94a3b8;font-size:13px}
.cm-empty i{font-size:36px;display:block;margin-bottom:8px;opacity:.35}
/* Modal */
.cm-mbg{position:fixed;inset:0;background:rgba(15,23,42,.55);z-index:2000;display:flex;align-items:center;justify-content:center;padding:20px;opacity:0;visibility:hidden;transition:opacity .2s,visibility .2s}
.cm-mbg.show{opacity:1;visibility:visible}
.cm-modal{background:#fff;border-radius:16px;width:420px;max-width:100%;box-shadow:0 20px 50px rgba(0,0,0,.2);transform:translateY(20px);transition:transform .2s}
.cm-mbg.show .cm-modal{transform:translateY(0)}
.cm-mh{display:flex;align-items:center;justify-content:space-between;padding:16px 20px 12px;border-bottom:1px solid #f1f5f9}
.cm-mh h3{font-size:15px;font-weight:700;color:#0f172a;margin:0}
.cm-mcls{width:28px;height:28px;border-radius:7px;border:none;background:#f1f5f9;cursor:pointer;font-size:14px;color:#475569;display:flex;align-items:center;justify-content:center}
.cm-mcls:hover{background:#e2e8f0}
.cm-mb{padding:16px 20px}
.cm-frow{margin-bottom:12px}
.cm-frow label{display:block;font-size:11px;font-weight:700;color:#64748b;text-transform:uppercase;letter-spacing:.04em;margin-bottom:4px}
.cm-frow input,.cm-frow textarea{width:100%;height:36px;padding:0 10px;border:1px solid #e2e8f0;border-radius:8px;font-size:13px;outline:none;font-family:inherit;box-sizing:border-box;background:#f8fafc;color:#0f172a;transition:border .15s}
.cm-frow input:focus,.cm-frow textarea:focus{border-color:#7c3aed;background:#fff}
.cm-frow textarea{height:64px;padding-top:8px;resize:vertical}
.cm-mf{display:flex;gap:8px;justify-content:flex-end;padding:12px 20px 16px;border-top:1px solid #f1f5f9}
.cm-msv{height:34px;padding:0 16px;border-radius:8px;border:none;background:#7c3aed;color:#fff;font-size:13px;font-weight:600;cursor:pointer;display:flex;align-items:center;gap:4px;transition:all .15s}
.cm-msv:hover{background:#6d28d9}
.cm-mcl{height:34px;padding:0 14px;border-radius:8px;border:1px solid #e2e8f0;background:#f1f5f9;color:#374151;font-size:13px;font-weight:500;cursor:pointer;transition:all .15s}
.cm-mcl:hover{background:#e2e8f0}
/* Toast */
.cm-toast{position:fixed;bottom:24px;right:24px;z-index:9999;padding:11px 16px;border-radius:10px;font-size:13px;font-weight:500;box-shadow:0 6px 20px rgba(0,0,0,.15);display:flex;align-items:center;gap:7px;transform:translateY(80px);opacity:0;transition:all .3s;pointer-events:none}
.cm-toast.show{transform:translateY(0);opacity:1}
.cm-toast.ok{background:#22c55e;color:#fff}.cm-toast.er{background:#ef4444;color:#fff}
@keyframes cmspin{from{transform:rotate(0)}to{transform:rotate(360deg)}}
@media(max-width:768px){.cm-grid{grid-template-columns:1fr}}
</style>

<div class="cm-head">
  <div>
    <h1><i class="bi bi-grid-3x3-gap-fill" style="color:#7c3aed;margin-right:8px"></i>Quản lý danh mục</h1>
    <p>Quản lý tiện ích phòng trọ và danh mục hệ thống</p>
  </div>
</div>

<div class="cm-grid">
  <!-- Panel: Tien ich -->
  <div class="cm-card">
    <div class="cm-ch">
      <h2><i class="bi bi-lightning-charge-fill" style="color:#f97316"></i> Tiện ích phòng <span class="cm-ch-badge" id="tiBadge">0</span></h2>
      <button class="cm-add-btn" onclick="tiOpenAdd()"><i class="bi bi-plus-lg"></i> Thêm</button>
    </div>
    <div class="cm-bar">
      <input class="cm-input" id="tiSrch" placeholder="Tìm tiện ích..." oninput="tiFilter()">
    </div>
    <div style="overflow-x:auto">
      <table class="cm-tbl">
        <thead><tr><th>Tên tiện ích</th><th>Mô tả</th><th style="text-align:right">Thao tác</th></tr></thead>
        <tbody id="tiBody"><tr><td colspan="3"><div class="cm-empty"><i class="bi bi-arrow-clockwise" style="animation:cmspin 1s linear infinite"></i>Đang tải...</div></td></tr></tbody>
      </table>
    </div>
  </div>

  <!-- Panel: Cau hinh danh muc -->
  <div class="cm-card">
    <div class="cm-ch">
      <h2><i class="bi bi-tags-fill" style="color:#7c3aed"></i> Danh mục hệ thống <span class="cm-ch-badge" id="dmBadge">0</span></h2>
      <button class="cm-add-btn" onclick="dmOpenAdd()"><i class="bi bi-plus-lg"></i> Thêm</button>
    </div>
    <div class="cm-bar">
      <input class="cm-input" id="dmSrch" placeholder="Tìm danh mục..." oninput="dmFilter()">
    </div>
    <div style="overflow-x:auto">
      <table class="cm-tbl">
        <thead><tr><th>Tên danh mục</th><th>Mô tả</th><th>Trạng thái</th><th style="text-align:right">Thao tác</th></tr></thead>
        <tbody id="dmBody"><tr><td colspan="4"><div class="cm-empty"><i class="bi bi-arrow-clockwise" style="animation:cmspin 1s linear infinite"></i>Đang tải...</div></td></tr></tbody>
      </table>
    </div>
  </div>
</div>

<!-- Modal Tien Ich -->
<div class="cm-mbg" id="tiModal" onclick="if(event.target===this)tiClose()">
  <div class="cm-modal">
    <div class="cm-mh">
      <h3 id="tiModalTitle"><i class="bi bi-lightning-charge" style="color:#f97316;margin-right:5px"></i>Thêm tiện ích</h3>
      <button class="cm-mcls" onclick="tiClose()"><i class="bi bi-x-lg"></i></button>
    </div>
    <div class="cm-mb">
      <div class="cm-frow"><label>Tên tiện ích *</label><input type="text" id="fTiTen" placeholder="VD: Wifi, Điều hòa..."></div>
      <div class="cm-frow"><label>Mô tả</label><textarea id="fTiMoTa" placeholder="Mô tả ngắn..."></textarea></div>
    </div>
    <div class="cm-mf">
      <button class="cm-mcl" onclick="tiClose()">Huỷ</button>
      <button class="cm-msv" onclick="tiSave()"><i class="bi bi-check-lg"></i> Lưu</button>
    </div>
  </div>
</div>

<!-- Modal Danh Muc -->
<div class="cm-mbg" id="dmModal" onclick="if(event.target===this)dmClose()">
  <div class="cm-modal">
    <div class="cm-mh">
      <h3 id="dmModalTitle"><i class="bi bi-tag" style="color:#7c3aed;margin-right:5px"></i>Thêm danh mục</h3>
      <button class="cm-mcls" onclick="dmClose()"><i class="bi bi-x-lg"></i></button>
    </div>
    <div class="cm-mb">
      <div class="cm-frow"><label>Tên danh mục *</label><input type="text" id="fDmTen" placeholder="VD: Loại phòng, Khu vực..."></div>
      <div class="cm-frow"><label>Mô tả</label><textarea id="fDmMoTa" placeholder="Mô tả ngắn..."></textarea></div>
    </div>
    <div class="cm-mf">
      <button class="cm-mcl" onclick="dmClose()">Huỷ</button>
      <button class="cm-msv" onclick="dmSave()"><i class="bi bi-check-lg"></i> Lưu</button>
    </div>
  </div>
</div>

<div class="cm-toast" id="cmToast"></div>

<script>
(function(){
  var CP='${pageContext.request.contextPath}', tk=localStorage.getItem('token');
  var tiAll=[], dmAll=[], tiEditId=null, dmEditId=null;

  loadTi(); loadDm();

  // ── TIEN ICH ──
  function loadTi(){
    api('/api/admin/danh-muc/tien-ich').then(function(list){
      tiAll=list; g('tiBadge').textContent=list.length; drawTi(list);
    }).catch(function(e){ g('tiBody').innerHTML='<tr><td colspan="3"><div class="cm-empty"><i class="bi bi-exclamation-circle"></i>Lỗi tải ('+e+')</div></td></tr>'; });
  }
  function drawTi(list){
    var tb=g('tiBody');
    if(!list.length){ tb.innerHTML='<tr><td colspan="3"><div class="cm-empty"><i class="bi bi-inbox"></i>Chưa có tiện ích nào</div></td></tr>'; return; }
    tb.innerHTML=list.map(function(r){
      return '<tr>'
        +'<td><strong>'+x(r.tenTienIch||'—')+'</strong></td>'
        +'<td style="color:#64748b;font-size:12px;max-width:180px;word-break:break-word">'+x(r.moTa||'—')+'</td>'
        +'<td><div style="display:flex;gap:5px;justify-content:flex-end">'
        +'<button class="cm-btn cm-be" onclick="tiEdit('+JSON.stringify(r)+')"><i class="bi bi-pencil"></i> Sửa</button>'
        +'<button class="cm-btn cm-bd" onclick="tiDel('+r.maTienIch+')"><i class="bi bi-trash3"></i></button>'
        +'</div></td></tr>';
    }).join('');
  }
  window.tiFilter=function(){ var kw=g('tiSrch').value.toLowerCase(); drawTi(tiAll.filter(function(r){ return (r.tenTienIch||'').toLowerCase().includes(kw)||(r.moTa||'').toLowerCase().includes(kw); })); };
  window.tiOpenAdd=function(){ tiEditId=null; g('tiModalTitle').innerHTML='<i class="bi bi-plus-circle" style="color:#f97316;margin-right:5px"></i>Thêm tiện ích'; g('fTiTen').value=''; g('fTiMoTa').value=''; g('tiModal').classList.add('show'); document.body.style.overflow='hidden'; };
  window.tiEdit=function(r){ tiEditId=r.maTienIch; g('tiModalTitle').innerHTML='<i class="bi bi-pencil" style="color:#f97316;margin-right:5px"></i>Sửa tiện ích'; g('fTiTen').value=r.tenTienIch||''; g('fTiMoTa').value=r.moTa||''; g('tiModal').classList.add('show'); document.body.style.overflow='hidden'; };
  window.tiClose=function(){ g('tiModal').classList.remove('show'); document.body.style.overflow=''; };
  window.tiSave=function(){
    var ten=g('fTiTen').value.trim(); if(!ten){ g('fTiTen').focus(); return; }
    var body={tenTienIch:ten, moTa:g('fTiMoTa').value.trim()};
    var m=tiEditId?'PUT':'POST', p=tiEditId?'/api/admin/danh-muc/tien-ich/'+tiEditId:'/api/admin/danh-muc/tien-ich';
    api(p,m,body).then(function(){ toast(tiEditId?'Đã cập nhật':'Đã thêm tiện ích','ok'); tiClose(); loadTi(); }).catch(function(){ toast('Lỗi lưu','er'); });
  };
  window.tiDel=function(id){ if(!confirm('Xoá tiện ích này?')) return; api('/api/admin/danh-muc/tien-ich/'+id,'DELETE').then(function(){ toast('Đã xoá','ok'); loadTi(); }).catch(function(){ toast('Lỗi xoá — có thể tiện ích đang được sử dụng','er'); }); };

  // ── DANH MUC ──
  function loadDm(){
    api('/api/admin/danh-muc/cau-hinh').then(function(list){
      dmAll=list; g('dmBadge').textContent=list.length; drawDm(list);
    }).catch(function(e){ g('dmBody').innerHTML='<tr><td colspan="4"><div class="cm-empty"><i class="bi bi-exclamation-circle"></i>Lỗi tải ('+e+')</div></td></tr>'; });
  }
  function drawDm(list){
    var tb=g('dmBody');
    if(!list.length){ tb.innerHTML='<tr><td colspan="4"><div class="cm-empty"><i class="bi bi-inbox"></i>Chưa có danh mục nào</div></td></tr>'; return; }
    tb.innerHTML=list.map(function(r){
      var st=r.trangThai?'<span class="cm-bdg on"><i class="bi bi-circle-fill" style="font-size:6px"></i> Hoạt động</span>'
                        :'<span class="cm-bdg off"><i class="bi bi-circle-fill" style="font-size:6px"></i> Ẩn</span>';
      return '<tr>'
        +'<td><strong>'+x(r.tenDanhMuc||'—')+'</strong></td>'
        +'<td style="color:#64748b;font-size:12px;max-width:160px;word-break:break-word">'+x(r.moTa||'—')+'</td>'
        +'<td>'+st+'</td>'
        +'<td><div style="display:flex;gap:5px;justify-content:flex-end">'
        +'<button class="cm-btn cm-be" onclick="dmEdit('+JSON.stringify(r)+')"><i class="bi bi-pencil"></i> Sửa</button>'
        +'<button class="cm-btn cm-bt" onclick="dmToggle('+r.maDanhMuc+')">'+(r.trangThai?'<i class="bi bi-eye-slash"></i>':'<i class="bi bi-eye"></i>')+'</button>'
        +'<button class="cm-btn cm-bd" onclick="dmDel('+r.maDanhMuc+')"><i class="bi bi-trash3"></i></button>'
        +'</div></td></tr>';
    }).join('');
  }
  window.dmFilter=function(){ var kw=g('dmSrch').value.toLowerCase(); drawDm(dmAll.filter(function(r){ return (r.tenDanhMuc||'').toLowerCase().includes(kw)||(r.moTa||'').toLowerCase().includes(kw); })); };
  window.dmOpenAdd=function(){ dmEditId=null; g('dmModalTitle').innerHTML='<i class="bi bi-plus-circle" style="color:#7c3aed;margin-right:5px"></i>Thêm danh mục'; g('fDmTen').value=''; g('fDmMoTa').value=''; g('dmModal').classList.add('show'); document.body.style.overflow='hidden'; };
  window.dmEdit=function(r){ dmEditId=r.maDanhMuc; g('dmModalTitle').innerHTML='<i class="bi bi-pencil" style="color:#7c3aed;margin-right:5px"></i>Sửa danh mục'; g('fDmTen').value=r.tenDanhMuc||''; g('fDmMoTa').value=r.moTa||''; g('dmModal').classList.add('show'); document.body.style.overflow='hidden'; };
  window.dmClose=function(){ g('dmModal').classList.remove('show'); document.body.style.overflow=''; };
  window.dmSave=function(){
    var ten=g('fDmTen').value.trim(); if(!ten){ g('fDmTen').focus(); return; }
    var body={tenDanhMuc:ten, moTa:g('fDmMoTa').value.trim(), trangThai:true};
    var m=dmEditId?'PUT':'POST', p=dmEditId?'/api/admin/danh-muc/cau-hinh/'+dmEditId:'/api/admin/danh-muc/cau-hinh';
    api(p,m,body).then(function(){ toast(dmEditId?'Đã cập nhật':'Đã thêm danh mục','ok'); dmClose(); loadDm(); }).catch(function(){ toast('Lỗi lưu','er'); });
  };
  window.dmToggle=function(id){ api('/api/admin/danh-muc/cau-hinh/'+id+'/toggle','PATCH').then(function(){ toast('Đã cập nhật trạng thái','ok'); loadDm(); }).catch(function(){ toast('Lỗi','er'); }); };
  window.dmDel=function(id){ if(!confirm('Xoá danh mục này?')) return; api('/api/admin/danh-muc/cau-hinh/'+id,'DELETE').then(function(){ toast('Đã xoá','ok'); loadDm(); }).catch(function(){ toast('Lỗi xoá','er'); }); };

  // ── HELPERS ──
  function x(s){ return String(s).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;'); }
  function g(id){ return document.getElementById(id); }
  function toast(msg,type){ var t=g('cmToast'); t.textContent=msg; t.className='cm-toast '+(type||'ok'); t.classList.add('show'); setTimeout(function(){ t.classList.remove('show'); },3000); }
  function api(path,method,body){
    var opts={method:method||'GET',headers:{'Content-Type':'application/json'}};
    if(tk) opts.headers['Authorization']='Bearer '+tk;
    if(body) opts.body=JSON.stringify(body);
    return fetch(CP+path,opts).then(function(r){ if(!r.ok) throw r.status; return r.json(); });
  }
})();
</script>
<%@ include file="includes/footer.jspf" %>
