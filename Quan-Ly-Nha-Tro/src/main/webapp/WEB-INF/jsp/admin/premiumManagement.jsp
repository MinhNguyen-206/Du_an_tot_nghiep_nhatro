<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>
<style>
.pm-head{display:flex;align-items:center;justify-content:space-between;margin-bottom:24px;flex-wrap:wrap;gap:12px}
.pm-head h1{font-size:22px;font-weight:700;color:#0f172a;margin:0}
.pm-head p{font-size:13px;color:#64748b;margin:2px 0 0}
/* Stats — dong nhat voi eKYC */
.pm-stats{display:grid;grid-template-columns:repeat(4,1fr);gap:16px;margin-bottom:24px}
.pm-sc{background:#fff;border-radius:14px;padding:18px 20px;border:1px solid #e8edf3;display:flex;align-items:center;gap:14px;box-shadow:0 1px 4px rgba(0,0,0,.04);transition:transform .15s}
.pm-sc:hover{transform:translateY(-2px);box-shadow:0 4px 12px rgba(0,0,0,.08)}
.pm-si2{width:46px;height:46px;border-radius:12px;display:flex;align-items:center;justify-content:center;font-size:20px;flex-shrink:0}
.c-pu .pm-si2{background:#f5f3ff;color:#7c3aed}
.c-gr .pm-si2{background:#f0fdf4;color:#22c55e}
.c-or .pm-si2{background:#fff7ed;color:#f97316}
.c-bl .pm-si2{background:#eff6ff;color:#3b82f6}
.pm-sv{font-size:26px;font-weight:700;color:#0f172a;line-height:1}
.pm-sl{font-size:12px;color:#64748b;margin-top:3px}
.pm-sc-deco{display:none}
/* Tabs redesign */
.pm-tabs{display:flex;gap:8px;margin-bottom:20px}
.pm-tab{
  display:flex;align-items:center;gap:8px;padding:0 20px;height:42px;
  border-radius:12px;border:1.5px solid #e2e8f0;background:#fff;
  font-size:13px;font-weight:600;cursor:pointer;color:#64748b;
  transition:all .2s;white-space:nowrap;
  box-shadow:0 1px 3px rgba(0,0,0,.05);
}
.pm-tab .pm-tab-badge{
  background:#e2e8f0;color:#475569;font-size:11px;font-weight:700;
  padding:1px 7px;border-radius:20px;transition:all .2s;
}
.pm-tab.on{
  background:linear-gradient(135deg,#7c3aed,#6d28d9);
  border-color:transparent;color:#fff;
  box-shadow:0 4px 12px rgba(124,58,237,.35);
}
.pm-tab.on .pm-tab-badge{background:rgba(255,255,255,.25);color:#fff}
.pm-tab:hover:not(.on){border-color:#c4b5fd;color:#7c3aed;background:#faf5ff}
/* Card */
.pm-card{background:#fff;border-radius:16px;border:1px solid #e8edf3;box-shadow:0 1px 6px rgba(0,0,0,.05);overflow:hidden}
/* Goi table */
.pm-tbl{width:100%;border-collapse:collapse;font-size:13px}
.pm-tbl thead{background:#f8fafc}
.pm-tbl th{padding:12px 16px;text-align:left;font-weight:600;color:#475569;font-size:12px;text-transform:uppercase;letter-spacing:.04em;border-bottom:1px solid #e8edf3;white-space:nowrap}
.pm-tbl td{padding:13px 16px;border-bottom:1px solid #f1f5f9;vertical-align:middle}
.pm-tbl tbody tr:last-child td{border-bottom:none}
.pm-tbl tbody tr:hover td{background:#fafbff}
/* Badges */
.pm-bdg{display:inline-flex;align-items:center;gap:4px;padding:4px 10px;border-radius:20px;font-size:12px;font-weight:600}
.pm-bdg.on{background:#f0fdf4;color:#15803d}.pm-bdg.off{background:#f1f5f9;color:#64748b}
.pm-bdg.hd{background:#f5f3ff;color:#7c3aed}.pm-bdg.ht{background:#fef2f2;color:#b91c1c}.pm-bdg.hh{background:#fff7ed;color:#c2410c}
/* Buttons */
.pm-btn{height:30px;padding:0 12px;border-radius:8px;border:1px solid transparent;font-size:12px;font-weight:500;cursor:pointer;display:inline-flex;align-items:center;gap:4px;transition:all .15s;white-space:nowrap}
.pm-be{background:#eff6ff;color:#2563eb;border-color:#bfdbfe}.pm-be:hover{background:#3b82f6;color:#fff}
.pm-bd{background:#fef2f2;color:#b91c1c;border-color:#fecaca}.pm-bd:hover{background:#ef4444;color:#fff}
.pm-bt{background:#f5f3ff;color:#7c3aed;border-color:#ddd6fe}.pm-bt:hover{background:#7c3aed;color:#fff}
.pm-bg{background:#f0fdf4;color:#15803d;border-color:#bbf7d0}.pm-bg:hover{background:#22c55e;color:#fff}
.pm-bh{background:#fef2f2;color:#b91c1c;border-color:#fecaca}.pm-bh:hover{background:#ef4444;color:#fff}
/* Toolbar */
.pm-bar{display:flex;align-items:center;gap:10px;padding:14px 16px;border-bottom:1px solid #f1f5f9;flex-wrap:wrap}
.pm-srch{position:relative;flex:1;min-width:180px}
.pm-srch i{position:absolute;left:10px;top:50%;transform:translateY(-50%);color:#94a3b8;font-size:13px}
.pm-srch input{width:100%;height:36px;padding:0 10px 0 32px;border:1px solid #e2e8f0;border-radius:9px;font-size:13px;outline:none;background:#f8fafc;transition:border .15s}
.pm-srch input:focus{border-color:#7c3aed;background:#fff}
.pm-fb{height:36px;padding:0 14px;border-radius:9px;border:1px solid #e2e8f0;background:#f8fafc;font-size:12px;font-weight:600;cursor:pointer;transition:all .15s;white-space:nowrap;color:#374151}
.pm-fb:hover,.pm-fb.on{background:#7c3aed;color:#fff;border-color:#7c3aed}
.pm-fb.fh.on{background:#f97316;border-color:#f97316}
.pm-fb.fhuy.on{background:#ef4444;border-color:#ef4444}
/* Add btn */
.pm-add{height:36px;padding:0 16px;border-radius:9px;border:none;background:#7c3aed;color:#fff;font-size:13px;font-weight:600;cursor:pointer;display:flex;align-items:center;gap:6px;transition:all .15s;white-space:nowrap}
.pm-add:hover{background:#6d28d9}
/* Empty */
.pm-empty{padding:50px 20px;text-align:center;color:#94a3b8}
.pm-empty i{font-size:44px;display:block;margin-bottom:10px;opacity:.4}
/* Pag */
.pm-pag{display:flex;align-items:center;justify-content:space-between;padding:12px 16px;border-top:1px solid #f1f5f9;font-size:13px}
.pm-pag-info{color:#64748b}.pm-pag-btns{display:flex;gap:5px}
.pm-pb{width:30px;height:30px;border-radius:7px;border:1px solid #e2e8f0;background:#fff;font-size:12px;cursor:pointer;display:flex;align-items:center;justify-content:center;color:#374151;transition:all .15s}
.pm-pb:hover:not(:disabled){background:#7c3aed;color:#fff;border-color:#7c3aed}
.pm-pb.on{background:#7c3aed;color:#fff;border-color:#7c3aed;font-weight:600}
.pm-pb:disabled{opacity:.4;cursor:not-allowed}
/* Modal */
.pm-mbg{position:fixed;inset:0;background:rgba(15,23,42,.55);z-index:2000;display:flex;align-items:center;justify-content:center;padding:20px;opacity:0;visibility:hidden;transition:opacity .2s,visibility .2s}
.pm-mbg.show{opacity:1;visibility:visible}
.pm-modal{background:#fff;border-radius:18px;width:480px;max-width:100%;box-shadow:0 24px 60px rgba(0,0,0,.2);transform:translateY(20px);transition:transform .2s}
.pm-mbg.show .pm-modal{transform:translateY(0)}
.pm-mh{display:flex;align-items:center;justify-content:space-between;padding:18px 22px 14px;border-bottom:1px solid #f1f5f9}
.pm-mh h3{font-size:16px;font-weight:700;color:#0f172a;margin:0}
.pm-mcls{width:30px;height:30px;border-radius:7px;border:none;background:#f1f5f9;cursor:pointer;font-size:15px;color:#475569;display:flex;align-items:center;justify-content:center}
.pm-mcls:hover{background:#e2e8f0}
.pm-mb{padding:18px 22px}
.pm-frow{margin-bottom:14px}
.pm-frow label{display:block;font-size:12px;font-weight:600;color:#64748b;text-transform:uppercase;letter-spacing:.04em;margin-bottom:5px}
.pm-frow input,.pm-frow textarea,.pm-frow select{width:100%;height:38px;padding:0 12px;border:1px solid #e2e8f0;border-radius:9px;font-size:13px;outline:none;font-family:inherit;transition:border .15s;box-sizing:border-box;color:#0f172a;background:#f8fafc}
.pm-frow input:focus,.pm-frow textarea:focus,.pm-frow select:focus{border-color:#7c3aed;background:#fff}
.pm-frow textarea{height:72px;padding-top:8px;resize:vertical}
.pm-fgrid{display:grid;grid-template-columns:1fr 1fr;gap:12px}
.pm-mf{display:flex;gap:8px;justify-content:flex-end;padding:14px 22px 18px;border-top:1px solid #f1f5f9}
.eb2{height:36px;padding:0 18px;border-radius:9px;border:none;font-size:13px;font-weight:600;cursor:pointer;display:flex;align-items:center;gap:5px;transition:all .15s}
.eb2.sv{background:#7c3aed;color:#fff}.eb2.sv:hover{background:#6d28d9}
.eb2.cl{background:#f1f5f9;color:#374151;border:1px solid #e2e8f0}.eb2.cl:hover{background:#e2e8f0}
/* Toast */
.pm-toast{position:fixed;bottom:24px;right:24px;z-index:9999;padding:12px 18px;border-radius:12px;font-size:13px;font-weight:500;box-shadow:0 8px 24px rgba(0,0,0,.15);display:flex;align-items:center;gap:8px;transform:translateY(80px);opacity:0;transition:all .3s;pointer-events:none}
.pm-toast.show{transform:translateY(0);opacity:1}
.pm-toast.ok{background:#22c55e;color:#fff}.pm-toast.er{background:#ef4444;color:#fff}
@keyframes spin{from{transform:rotate(0)}to{transform:rotate(360deg)}}
@media(max-width:768px){.pm-stats{grid-template-columns:repeat(2,1fr)}.pm-fgrid{grid-template-columns:1fr}}
</style>

<div class="pm-head">
  <div>
    <h1><i class="bi bi-gem" style="color:#7c3aed;margin-right:8px"></i>Quản lý Premium</h1>
    <p>Quản lý gói dịch vụ và theo dõi đăng ký Premium của chủ trọ</p>
  </div>
  <button class="pm-add" id="btnThemGoi" onclick="pmOpenAdd()"><i class="bi bi-plus-lg"></i> Thêm gói mới</button>
</div>

<!-- Stats -->
<div class="pm-stats">
  <div class="pm-sc c-pu">
    <div class="pm-si2"><i class="bi bi-gem"></i></div>
    <div><div class="pm-sv" id="pS0">—</div><div class="pm-sl">Số gói dịch vụ</div></div>
  </div>
  <div class="pm-sc c-gr">
    <div class="pm-si2"><i class="bi bi-people-fill"></i></div>
    <div><div class="pm-sv" id="pS1">—</div><div class="pm-sl">Đang hoạt động</div></div>
  </div>
  <div class="pm-sc c-or">
    <div class="pm-si2"><i class="bi bi-calendar-x"></i></div>
    <div><div class="pm-sv" id="pS2">—</div><div class="pm-sl">Sắp hết hạn (7 ngày)</div></div>
  </div>
  <div class="pm-sc c-bl">
    <div class="pm-si2"><i class="bi bi-cash-stack"></i></div>
    <div><div class="pm-sv" id="pS3">—</div><div class="pm-sl">Tổng doanh thu</div></div>
  </div>
</div>

<!-- Tabs -->
<div class="pm-tabs">
  <button class="pm-tab on" id="tabGoi" onclick="pmSwitchTab('goi')">
    <i class="bi bi-box-seam"></i> Gói dịch vụ
    <span class="pm-tab-badge" id="tbGoi">—</span>
  </button>
  <button class="pm-tab" id="tabDangKy" onclick="pmSwitchTab('dangky')">
    <i class="bi bi-person-check"></i> Đăng ký Premium
    <span class="pm-tab-badge" id="tbDk">—</span>
  </button>
</div>

<!-- Tab: Goi -->
<div id="panelGoi">
  <div class="pm-card">
    <div style="overflow-x:auto">
      <table class="pm-tbl">
        <thead><tr><th>#</th><th>Tên gói</th><th>Giá</th><th>Thời hạn</th><th>Số tin</th><th>Ưu tiên</th><th>Mô tả</th><th>Trạng thái</th><th style="text-align:right">Thao tác</th></tr></thead>
        <tbody id="goiBody"><tr><td colspan="9"><div class="pm-empty"><i class="bi bi-arrow-clockwise" style="animation:spin 1s linear infinite"></i><p>Đang tải...</p></div></td></tr></tbody>
      </table>
    </div>
  </div>
</div>

<!-- Tab: DangKy -->
<div id="panelDangKy" style="display:none">
  <div class="pm-card">
    <div class="pm-bar">
      <div class="pm-srch"><i class="bi bi-search"></i><input type="text" id="dkSrch" placeholder="Tìm tên chủ trọ hoặc tên gói..." oninput="dkDbc()"></div>
      <button class="pm-fb on"   id="dkAll" onclick="dkFlt('')">Tất cả</button>
      <button class="pm-fb fh"   id="dkHd"  onclick="dkFlt('HOAT_DONG')"><i class="bi bi-check-circle"></i> Hoạt động</button>
      <button class="pm-fb fhuy" id="dkHuy" onclick="dkFlt('DA_HUY')"><i class="bi bi-x-circle"></i> Đã huỷ</button>
    </div>
    <div style="overflow-x:auto">
      <table class="pm-tbl">
        <thead><tr><th>#</th><th>Chủ trọ</th><th>Gói đăng ký</th><th>Ngày đăng ký</th><th>Hết hạn</th><th>Trạng thái</th><th style="text-align:right">Thao tác</th></tr></thead>
        <tbody id="dkBody"><tr><td colspan="7"><div class="pm-empty"><i class="bi bi-arrow-clockwise" style="animation:spin 1s linear infinite"></i><p>Đang tải...</p></div></td></tr></tbody>
      </table>
    </div>
    <div class="pm-pag" id="dkPag" style="display:none">
      <span class="pm-pag-info" id="dkPagI"></span>
      <div class="pm-pag-btns" id="dkPagB"></div>
    </div>
  </div>
</div>

<!-- Modal thêm/sửa gói -->
<div class="pm-mbg" id="goiModal" onclick="if(event.target===this)pmCloseModal()">
  <div class="pm-modal">
    <div class="pm-mh">
      <h3 id="modalTitle"><i class="bi bi-box-seam" style="color:#7c3aed;margin-right:6px"></i>Thêm gói dịch vụ</h3>
      <button class="pm-mcls" onclick="pmCloseModal()"><i class="bi bi-x-lg"></i></button>
    </div>
    <div class="pm-mb">
      <div class="pm-frow"><label>Tên gói</label><input type="text" id="fTenGoi" placeholder="VD: Premium Tháng"></div>
      <div class="pm-fgrid">
        <div class="pm-frow"><label>Giá (VNĐ)</label><input type="number" id="fGia" placeholder="99000"></div>
        <div class="pm-frow"><label>Thời hạn (ngày)</label><input type="number" id="fThoiHan" placeholder="30"></div>
      </div>
      <div class="pm-fgrid">
        <div class="pm-frow"><label>Số lượng tin</label><input type="number" id="fSoTin" placeholder="20"></div>
        <div class="pm-frow"><label>Ưu tiên hiển thị</label>
          <select id="fUuTien"><option value="true">Có</option><option value="false">Không</option></select>
        </div>
      </div>
      <div class="pm-frow"><label>Mô tả</label><textarea id="fMoTa" placeholder="Mô tả ngắn về gói..."></textarea></div>
    </div>
    <div class="pm-mf">
      <button class="eb2 cl" onclick="pmCloseModal()"><i class="bi bi-x"></i> Huỷ</button>
      <button class="eb2 sv" onclick="pmSaveGoi()"><i class="bi bi-check-lg"></i> Lưu gói</button>
    </div>
  </div>
</div>

<div class="pm-toast" id="pmToast"></div>

<script>
(function(){
  var CP='${pageContext.request.contextPath}',tk=localStorage.getItem('token');
  var editId=null, dkPg=0, dkSz=10, dkFlt_='', dkSrch_='', dkStmr=null;
  var curTab='goi';

  loadStats(); loadGoi();

  // ── Stats ──
  function loadStats(){
    api('/api/admin/premium/thong-ke').then(function(d){
      g('pS0').textContent = d.soGoi||0;
      g('pS1').textContent = d.dangHoatDong||0;
      g('pS2').textContent = d.sapHetHan||0;
      g('pS3').textContent = fmtVnd(d.tongDoanhThu||0);
      // Cap nhat badge tren tab
      g('tbGoi').textContent = d.soGoi||0;
      g('tbDk').textContent  = d.tongDangKy||0;
    }).catch(function(){});
  }

  // ── Tab switch ──
  window.pmSwitchTab = function(tab){
    curTab = tab;
    g('tabGoi').classList.toggle('on', tab==='goi');
    g('tabDangKy').classList.toggle('on', tab==='dangky');
    g('panelGoi').style.display   = tab==='goi'    ? '' : 'none';
    g('panelDangKy').style.display= tab==='dangky' ? '' : 'none';
    g('btnThemGoi').style.display = tab==='goi'    ? '' : 'none';
    if(tab==='dangky') loadDangKy();
  };

  // ── GOI DICH VU ──
  function loadGoi(){
    api('/api/admin/premium/goi').then(function(list){
      var tb=g('goiBody');
      if(!list.length){ tb.innerHTML='<tr><td colspan="9"><div class="pm-empty"><i class="bi bi-inbox"></i><p>Chưa có gói dịch vụ nào</p></div></td></tr>'; return; }
      tb.innerHTML = list.map(function(r,i){
        var gia = r.gia ? Number(r.gia).toLocaleString('vi-VN')+'đ' : '—';
        var tg  = r.thoiHan ? r.thoiHan+' ngày' : '—';
        var tin = r.soLuongTin!=null ? (r.soLuongTin===0?'Không giới hạn':r.soLuongTin+' tin') : '—';
        var ut  = r.uuTien ? '<i class="bi bi-check-circle-fill" style="color:#22c55e"></i>' : '<i class="bi bi-dash-circle" style="color:#cbd5e1"></i>';
        var st  = r.trangThai ? '<span class="pm-bdg on"><i class="bi bi-circle-fill" style="font-size:7px"></i> Đang bán</span>'
                              : '<span class="pm-bdg off"><i class="bi bi-circle-fill" style="font-size:7px"></i> Ẩn</span>';
        return '<tr><td style="color:#94a3b8;font-size:12px">'+(i+1)+'</td>'
          +'<td><strong>'+x(r.tenGoi||'—')+'</strong></td>'
          +'<td style="font-weight:600;color:#7c3aed">'+gia+'</td>'
          +'<td>'+tg+'</td><td>'+tin+'</td><td style="text-align:center">'+ut+'</td>'
          +'<td style="color:#64748b;font-size:12px;max-width:220px;word-break:break-word;white-space:normal;line-height:1.5" title="'+x(r.moTa||'')+'">'+x(r.moTa||'—')+'</td>'
          +'<td>'+st+'</td>'
          +'<td><div style="display:flex;gap:5px;justify-content:flex-end">'
          +'<button class="pm-btn pm-be" onclick="pmEditGoi('+JSON.stringify(r)+')"><i class="bi bi-pencil"></i> Sửa</button>'
          +'<button class="pm-btn pm-bt" onclick="pmToggleGoi('+r.maGoi+','+r.trangThai+')">'
          +(r.trangThai?'<i class="bi bi-eye-slash"></i> Ẩn':'<i class="bi bi-eye"></i> Hiện')
          +'</button>'
          +'</div></td></tr>';
      }).join('');
    }).catch(function(e){ g('goiBody').innerHTML='<tr><td colspan="9"><div class="pm-empty"><i class="bi bi-exclamation-circle"></i><p>Lỗi tải ('+e+')</p></div></td></tr>'; });
  }

  window.pmOpenAdd = function(){
    editId=null;
    g('modalTitle').innerHTML='<i class="bi bi-plus-circle" style="color:#7c3aed;margin-right:6px"></i>Thêm gói dịch vụ';
    g('fTenGoi').value=''; g('fGia').value=''; g('fThoiHan').value=''; g('fSoTin').value=''; g('fMoTa').value=''; g('fUuTien').value='true';
    g('goiModal').classList.add('show'); document.body.style.overflow='hidden';
  };
  window.pmEditGoi = function(r){
    editId = r.maGoi;
    g('modalTitle').innerHTML='<i class="bi bi-pencil" style="color:#7c3aed;margin-right:6px"></i>Sửa gói dịch vụ';
    g('fTenGoi').value  = r.tenGoi||'';
    g('fGia').value     = r.gia||'';
    g('fThoiHan').value = r.thoiHan||'';
    g('fSoTin').value   = r.soLuongTin!=null?r.soLuongTin:'';
    g('fMoTa').value    = r.moTa||'';
    g('fUuTien').value  = r.uuTien?'true':'false';
    g('goiModal').classList.add('show'); document.body.style.overflow='hidden';
  };
  window.pmCloseModal = function(){
    g('goiModal').classList.remove('show'); document.body.style.overflow='';
  };
  window.pmSaveGoi = function(){
    var body={
      tenGoi:   g('fTenGoi').value.trim(),
      gia:      g('fGia').value||null,
      thoiHan:  g('fThoiHan').value||null,
      soLuongTin: g('fSoTin').value||null,
      moTa:     g('fMoTa').value.trim(),
      uuTien:   g('fUuTien').value==='true',
      trangThai: true
    };
    if(!body.tenGoi){ g('fTenGoi').focus(); return; }
    var method = editId ? 'PUT' : 'POST';
    var path   = editId ? '/api/admin/premium/goi/'+editId : '/api/admin/premium/goi';
    api(path, method, body).then(function(){
      toast(editId?'Đã cập nhật gói':'Đã thêm gói mới','ok');
      pmCloseModal(); loadGoi(); loadStats();
    }).catch(function(){ toast('Lỗi khi lưu gói','er'); });
  };
  window.pmToggleGoi = function(id, cur){
    api('/api/admin/premium/goi/'+id+'/toggle','PATCH').then(function(){
      toast(cur?'Đã ẩn gói':'Đã hiện gói lại','ok'); loadGoi();
    }).catch(function(){ toast('Lỗi toggle','er'); });
  };

  // ── DANG KY ──
  function loadDangKy(){
    var u='/api/admin/premium/dang-ky?page='+dkPg+'&size='+dkSz;
    if(dkFlt_) u+='&trangThai='+encodeURIComponent(dkFlt_);
    if(dkSrch_) u+='&q='+encodeURIComponent(dkSrch_);
    api(u).then(function(p){ drawDK(p.content||[]); pagDK(p); }).catch(function(e){
      g('dkBody').innerHTML='<tr><td colspan="7"><div class="pm-empty"><i class="bi bi-exclamation-circle"></i><p>Lỗi tải ('+e+')</p></div></td></tr>';
    });
  }
  function drawDK(rows){
    var tb=g('dkBody');
    if(!rows.length){ tb.innerHTML='<tr><td colspan="7"><div class="pm-empty"><i class="bi bi-inbox"></i><p>Không có dữ liệu</p></div></td></tr>'; return; }
    tb.innerHTML=rows.map(function(r,i){
      var nd=r.chuTro||{}, goi=r.goi||{};
      var av=nd.avatar||('https://ui-avatars.com/api/?name='+encodeURIComponent(nd.hoTen||'U')+'&background=f5f3ff&color=7c3aed&size=64');
      var st='';
      if(r.trangThai==='HOAT_DONG') st='<span class="pm-bdg hd"><i class="bi bi-circle-fill" style="font-size:7px"></i> Hoạt động</span>';
      else if(r.trangThai==='DA_HUY') st='<span class="pm-bdg ht"><i class="bi bi-circle-fill" style="font-size:7px"></i> Đã huỷ</span>';
      else st='<span class="pm-bdg hh"><i class="bi bi-circle-fill" style="font-size:7px"></i> '+(r.trangThai||'—')+'</span>';
      var acts='<button class="pm-btn pm-bg" onclick="dkGiaHan('+r.maDangKy+')"><i class="bi bi-plus-circle"></i> Gia hạn</button>';
      if(r.trangThai!=='DA_HUY') acts+=' <button class="pm-btn pm-bh" onclick="dkHuy('+r.maDangKy+')"><i class="bi bi-x-circle"></i> Huỷ</button>';
      return '<tr><td style="color:#94a3b8;font-size:12px">'+(dkPg*dkSz+i+1)+'</td>'
        +'<td><div style="display:flex;align-items:center;gap:9px"><img src="'+x(av)+'" style="width:34px;height:34px;border-radius:50%;object-fit:cover;border:2px solid #e8edf3;background:#f5f3ff" onerror="this.src=\'https://ui-avatars.com/api/?name=U&background=f5f3ff&color=7c3aed&size=64\'">'
        +'<div><div style="font-weight:600;color:#0f172a;font-size:13px">'+x(nd.hoTen||'—')+'</div><div style="font-size:12px;color:#64748b">'+x(nd.email||'—')+'</div></div></div></td>'
        +'<td><span style="background:#f5f3ff;color:#7c3aed;padding:3px 9px;border-radius:6px;font-size:12px;font-weight:600">'+x(goi.tenGoi||'—')+'</span></td>'
        +'<td>'+fmtDt(r.ngayDangKy)+'</td>'
        +'<td>'+fmtDt(r.ngayHetHan)+'</td>'
        +'<td>'+st+'</td>'
        +'<td><div style="display:flex;gap:5px;justify-content:flex-end">'+acts+'</div></td></tr>';
    }).join('');
  }
  function pagDK(p){
    var w=g('dkPag'), tot=p.totalElements||0, pages=p.totalPages||1;
    if(!tot){ w.style.display='none'; return; } w.style.display='flex';
    g('dkPagI').textContent='Hiển thị '+(dkPg*dkSz+1)+'–'+Math.min(dkPg*dkSz+dkSz,tot)+' / '+tot;
    var h='<button class="pm-pb" '+(dkPg===0?'disabled':'')+' onclick="dkGo('+(dkPg-1)+')"><i class="bi bi-chevron-left"></i></button>';
    var s=Math.max(0,dkPg-2), e=Math.min(pages-1,s+4);
    for(var pp=s;pp<=e;pp++) h+='<button class="pm-pb'+(pp===dkPg?' on':'')+'" onclick="dkGo('+pp+')">'+(pp+1)+'</button>';
    h+='<button class="pm-pb" '+(dkPg>=pages-1?'disabled':'')+' onclick="dkGo('+(dkPg+1)+')"><i class="bi bi-chevron-right"></i></button>';
    g('dkPagB').innerHTML=h;
  }
  window.dkGiaHan = function(id){
    if(!confirm('Gia hạn thêm 30 ngày?')) return;
    api('/api/admin/premium/dang-ky/'+id+'/gia-han','PATCH').then(function(){
      toast('Đã gia hạn 30 ngày','ok'); loadDangKy(); loadStats();
    }).catch(function(){ toast('Lỗi gia hạn','er'); });
  };
  window.dkHuy = function(id){
    if(!confirm('Huỷ đăng ký Premium này?')) return;
    api('/api/admin/premium/dang-ky/'+id+'/huy','PATCH').then(function(){
      toast('Đã huỷ đăng ký','er'); loadDangKy(); loadStats();
    }).catch(function(){ toast('Lỗi huỷ','er'); });
  };
  window.dkFlt = function(f){
    dkFlt_=f; dkPg=0;
    ['dkAll','dkHd','dkHuy'].forEach(function(id){ g(id).classList.remove('on'); });
    if(!f) g('dkAll').classList.add('on');
    else if(f==='HOAT_DONG') g('dkHd').classList.add('on');
    else g('dkHuy').classList.add('on');
    loadDangKy();
  };
  window.dkDbc = function(){ clearTimeout(dkStmr); dkStmr=setTimeout(function(){ dkSrch_=g('dkSrch').value.trim(); dkPg=0; loadDangKy(); },350); };
  window.dkGo  = function(p){ dkPg=p; loadDangKy(); };

  // ── Helpers ──
  function fmtDt(s){ if(!s) return '—'; var d=new Date(s); if(isNaN(d)) return s; return d.toLocaleDateString('vi-VN',{day:'2-digit',month:'2-digit',year:'numeric'}); }
  function fmtVnd(n){ if(!n) return '0đ'; if(n>=1e9) return (n/1e9).toFixed(1)+'T'; if(n>=1e6) return (n/1e6).toFixed(1)+'M'; return n.toLocaleString('vi-VN')+'đ'; }
  function x(s){ return String(s).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;'); }
  function g(id){ return document.getElementById(id); }
  function toast(msg,type){ var t=g('pmToast'); t.textContent=msg; t.className='pm-toast '+(type||'ok'); t.classList.add('show'); setTimeout(function(){ t.classList.remove('show'); },3000); }
  function api(path,method,body){
    var opts={method:method||'GET',headers:{'Content-Type':'application/json'}};
    if(tk) opts.headers['Authorization']='Bearer '+tk;
    if(body) opts.body=JSON.stringify(body);
    return fetch(CP+path,opts).then(function(r){ if(!r.ok) throw r.status; return r.json(); });
  }
})();
</script>
<%@ include file="includes/footer.jspf" %>
