<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jspf" %>
<style>
.ek-head{display:flex;align-items:center;justify-content:space-between;margin-bottom:24px}
.ek-head h1{font-size:22px;font-weight:700;color:#0f172a;margin:0}
.ek-head p{font-size:13px;color:#64748b;margin:2px 0 0}
.ek-stats{display:grid;grid-template-columns:repeat(4,1fr);gap:16px;margin-bottom:24px}
.ek-sc{background:#fff;border-radius:14px;padding:18px 20px;border:1px solid #e8edf3;display:flex;align-items:center;gap:14px;box-shadow:0 1px 4px rgba(0,0,0,.04);transition:transform .15s}
.ek-sc:hover{transform:translateY(-2px)}
.ek-si{width:46px;height:46px;border-radius:12px;display:flex;align-items:center;justify-content:center;font-size:20px;flex-shrink:0}
.ek-si.bl{background:#eff6ff;color:#3b82f6}.ek-si.or{background:#fff7ed;color:#f97316}
.ek-si.gr{background:#f0fdf4;color:#22c55e}.ek-si.rd{background:#fef2f2;color:#ef4444}
.ek-sv{font-size:26px;font-weight:700;color:#0f172a;line-height:1}
.ek-sl{font-size:12px;color:#64748b;margin-top:3px}
.ek-bar{display:flex;align-items:center;gap:10px;margin-bottom:16px;flex-wrap:wrap}
.ek-srch{position:relative;flex:1;min-width:200px}
.ek-srch i{position:absolute;left:12px;top:50%;transform:translateY(-50%);color:#94a3b8;font-size:14px}
.ek-srch input{width:100%;height:38px;padding:0 12px 0 36px;border:1px solid #e2e8f0;border-radius:10px;font-size:13px;outline:none;background:#f8fafc;color:#0f172a;transition:border .15s}
.ek-srch input:focus{border-color:#6366f1;background:#fff}
.ek-fb{height:38px;padding:0 14px;border-radius:10px;border:1px solid #e2e8f0;background:#f8fafc;font-size:13px;font-weight:500;color:#374151;cursor:pointer;transition:all .15s;white-space:nowrap}
.ek-fb:hover,.ek-fb.on{background:#6366f1;color:#fff;border-color:#6366f1}
.ek-fb.fc.on{background:#f97316;border-color:#f97316}
.ek-fb.fd.on{background:#22c55e;border-color:#22c55e}
.ek-fb.ft.on{background:#ef4444;border-color:#ef4444}
.ek-card{background:#fff;border-radius:16px;border:1px solid #e8edf3;box-shadow:0 1px 6px rgba(0,0,0,.05);overflow:hidden}
.ek-tw{overflow-x:auto}
.ek-tbl{width:100%;border-collapse:collapse;font-size:13px}
.ek-tbl thead{background:#f8fafc}
.ek-tbl th{padding:12px 16px;text-align:left;font-weight:600;color:#475569;font-size:12px;text-transform:uppercase;letter-spacing:.04em;border-bottom:1px solid #e8edf3;white-space:nowrap}
.ek-tbl td{padding:13px 16px;border-bottom:1px solid #f1f5f9;vertical-align:middle}
.ek-tbl tbody tr:last-child td{border-bottom:none}
.ek-tbl tbody tr:hover td{background:#fafbff}
.ek-uc{display:flex;align-items:center;gap:10px}
.ek-av{width:36px;height:36px;border-radius:50%;object-fit:cover;flex-shrink:0;border:2px solid #e8edf3;background:#e0e7ff}
.ek-un{font-weight:600;color:#0f172a}.ek-ue{font-size:12px;color:#64748b;margin-top:1px}
.ek-bdg{display:inline-flex;align-items:center;gap:5px;padding:4px 10px;border-radius:20px;font-size:12px;font-weight:600}
.ek-bdg.cho{background:#fff7ed;color:#c2410c}.ek-bdg.ok{background:#f0fdf4;color:#15803d}.ek-bdg.no{background:#fef2f2;color:#b91c1c}
.ek-acts{display:flex;gap:6px;align-items:center}
.ek-btn{height:30px;padding:0 12px;border-radius:8px;border:1px solid transparent;font-size:12px;font-weight:500;cursor:pointer;display:flex;align-items:center;gap:4px;transition:all .15s;white-space:nowrap}
.ek-bv{background:#eff6ff;color:#2563eb;border-color:#bfdbfe}.ek-bv:hover{background:#3b82f6;color:#fff}
.ek-bo{background:#f0fdf4;color:#15803d;border-color:#bbf7d0}.ek-bo:hover{background:#22c55e;color:#fff}
.ek-bd{background:#fef2f2;color:#b91c1c;border-color:#fecaca}.ek-bd:hover{background:#ef4444;color:#fff}
.ek-br{background:#f8fafc;color:#475569;border-color:#e2e8f0}.ek-br:hover{background:#f1f5f9}
.ek-empty{padding:60px 20px;text-align:center;color:#94a3b8}
.ek-empty i{font-size:48px;display:block;margin-bottom:12px;opacity:.4}
.ek-pag{display:flex;align-items:center;justify-content:space-between;padding:14px 16px;border-top:1px solid #f1f5f9;font-size:13px}
.ek-pag-info{color:#64748b}.ek-pag-btns{display:flex;gap:6px}
.ek-pb{width:32px;height:32px;border-radius:8px;border:1px solid #e2e8f0;background:#fff;font-size:13px;cursor:pointer;display:flex;align-items:center;justify-content:center;color:#374151;transition:all .15s}
.ek-pb:hover:not(:disabled){background:#6366f1;color:#fff;border-color:#6366f1}
.ek-pb.on{background:#6366f1;color:#fff;border-color:#6366f1;font-weight:600}
.ek-pb:disabled{opacity:.4;cursor:not-allowed}
/* Modal */
.ek-mbg{position:fixed;inset:0;background:rgba(15,23,42,.55);z-index:2000;display:flex;align-items:center;justify-content:center;padding:20px;opacity:0;visibility:hidden;transition:opacity .2s,visibility .2s}
.ek-mbg.show{opacity:1;visibility:visible}
.ek-modal{background:#fff;border-radius:20px;width:740px;max-width:100%;max-height:90vh;overflow-y:auto;box-shadow:0 24px 60px rgba(0,0,0,.2);transform:translateY(20px);transition:transform .2s}
.ek-mbg.show .ek-modal{transform:translateY(0)}
.ek-mh{display:flex;align-items:center;justify-content:space-between;padding:20px 24px 16px;border-bottom:1px solid #f1f5f9;position:sticky;top:0;background:#fff;z-index:1}
.ek-mh h3{font-size:17px;font-weight:700;color:#0f172a;margin:0}
.ek-mcls{width:32px;height:32px;border-radius:8px;border:none;background:#f1f5f9;cursor:pointer;font-size:16px;color:#475569;display:flex;align-items:center;justify-content:center}
.ek-mcls:hover{background:#e2e8f0}
.ek-mb{padding:20px 24px}
.ek-igrid{display:grid;grid-template-columns:1fr 1fr;gap:14px;margin-bottom:20px}
.ek-iitem label{font-size:11px;font-weight:600;color:#94a3b8;text-transform:uppercase;letter-spacing:.04em;display:block;margin-bottom:3px}
.ek-iitem span{font-size:14px;color:#0f172a;font-weight:500}
.ek-imgs{display:grid;grid-template-columns:repeat(3,1fr);gap:12px;margin-bottom:20px}
.ek-iw{border-radius:12px;overflow:hidden;border:1px solid #e8edf3;aspect-ratio:4/3;background:#f8fafc;position:relative}
.ek-iw img{width:100%;height:100%;object-fit:cover;cursor:zoom-in}
.ek-ilbl{position:absolute;bottom:0;left:0;right:0;background:rgba(15,23,42,.6);color:#fff;font-size:11px;font-weight:500;padding:5px 8px;text-align:center}
.ek-iph{width:100%;height:100%;display:flex;flex-direction:column;align-items:center;justify-content:center;gap:6px;color:#94a3b8;font-size:12px}
.ek-iph i{font-size:28px}
.ek-mf{padding:16px 24px 20px;border-top:1px solid #f1f5f9;display:flex;gap:10px;justify-content:flex-end;flex-wrap:wrap;position:sticky;bottom:0;background:#fff}
.eb{height:38px;padding:0 20px;border-radius:10px;border:none;font-size:14px;font-weight:600;cursor:pointer;display:flex;align-items:center;gap:6px;transition:all .15s}
.eb.ok{background:#22c55e;color:#fff}.eb.ok:hover{background:#16a34a}
.eb.dn{background:#ef4444;color:#fff}.eb.dn:hover{background:#dc2626}
.eb.rs{background:#f1f5f9;color:#374151}.eb.rs:hover{background:#e2e8f0}
.eb.cl{background:#f8fafc;color:#64748b;border:1px solid #e2e8f0}.eb.cl:hover{background:#f1f5f9}
/* Reject dialog */
.rj-bg{position:fixed;inset:0;background:rgba(15,23,42,.6);z-index:3000;display:flex;align-items:center;justify-content:center;padding:20px;opacity:0;visibility:hidden;transition:opacity .2s,visibility .2s}
.rj-bg.show{opacity:1;visibility:visible}
.rj-box{background:#fff;border-radius:16px;width:420px;max-width:100%;padding:24px;box-shadow:0 20px 50px rgba(0,0,0,.2);transform:scale(.95);transition:transform .2s}
.rj-bg.show .rj-box{transform:scale(1)}
.rj-box h4{font-size:16px;font-weight:700;margin:0 0 6px;color:#0f172a}
.rj-box p{font-size:13px;color:#64748b;margin:0 0 14px}
.rj-box textarea{width:100%;min-height:90px;padding:10px 12px;border:1px solid #e2e8f0;border-radius:10px;font-size:13px;resize:vertical;outline:none;font-family:inherit;transition:border .15s;box-sizing:border-box}
.rj-box textarea:focus{border-color:#ef4444}
.rj-acts{display:flex;gap:8px;justify-content:flex-end;margin-top:14px}
/* Lightbox */
.lb{position:fixed;inset:0;background:rgba(0,0,0,.9);z-index:9000;display:flex;align-items:center;justify-content:center;opacity:0;visibility:hidden;transition:opacity .2s,visibility .2s;cursor:zoom-out}
.lb.show{opacity:1;visibility:visible}
.lb img{max-width:90vw;max-height:90vh;border-radius:8px;object-fit:contain}
/* Toast */
.ek-toast{position:fixed;bottom:24px;right:24px;z-index:9999;padding:12px 18px;border-radius:12px;font-size:13px;font-weight:500;box-shadow:0 8px 24px rgba(0,0,0,.15);display:flex;align-items:center;gap:8px;transform:translateY(80px);opacity:0;transition:all .3s;pointer-events:none}
.ek-toast.show{transform:translateY(0);opacity:1}
.ek-toast.ok{background:#22c55e;color:#fff}.ek-toast.er{background:#ef4444;color:#fff}
@keyframes spin{from{transform:rotate(0)}to{transform:rotate(360deg)}}
@media(max-width:768px){.ek-stats{grid-template-columns:repeat(2,1fr)}.ek-igrid{grid-template-columns:1fr}.ek-imgs{grid-template-columns:1fr 1fr}}
</style>

<div class="ek-head">
  <div>
    <h1><i class="bi bi-person-vcard" style="color:#6366f1;margin-right:8px"></i>Phê duyệt hồ sơ eKYC</h1>
    <p>Xem xét và xác minh danh tính người dùng qua CCCD và ảnh chân dung</p>
  </div>
</div>

<!-- Stats -->
<div class="ek-stats">
  <div class="ek-sc"><div class="ek-si bl"><i class="bi bi-people-fill"></i></div><div><div class="ek-sv" id="s0">—</div><div class="ek-sl">Tổng hồ sơ</div></div></div>
  <div class="ek-sc"><div class="ek-si or"><i class="bi bi-hourglass-split"></i></div><div><div class="ek-sv" id="s1">—</div><div class="ek-sl">Chờ duyệt</div></div></div>
  <div class="ek-sc"><div class="ek-si gr"><i class="bi bi-check-circle-fill"></i></div><div><div class="ek-sv" id="s2">—</div><div class="ek-sl">Đã xác minh</div></div></div>
  <div class="ek-sc"><div class="ek-si rd"><i class="bi bi-x-circle-fill"></i></div><div><div class="ek-sv" id="s3">—</div><div class="ek-sl">Từ chối</div></div></div>
</div>

<!-- Toolbar -->
<div class="ek-bar">
  <div class="ek-srch"><i class="bi bi-search"></i><input type="text" id="ekSrch" placeholder="Tìm tên hoặc số CCCD..." oninput="ekDbc()"></div>
  <button class="ek-fb on"  id="fA" onclick="ekFlt('')">Tất cả</button>
  <button class="ek-fb fc"  id="fC" onclick="ekFlt('CHO_DUYET')"><i class="bi bi-hourglass-split"></i> Chờ duyệt</button>
  <button class="ek-fb fd"  id="fD" onclick="ekFlt('DA_DUYET')"><i class="bi bi-check-circle"></i> Đã duyệt</button>
  <button class="ek-fb ft"  id="fT" onclick="ekFlt('TU_CHOI')"><i class="bi bi-x-circle"></i> Từ chối</button>
</div>

<!-- Table -->
<div class="ek-card">
  <div class="ek-tw">
    <table class="ek-tbl">
      <thead><tr><th>#</th><th>Người dùng</th><th>Số CCCD</th><th>Ngày gửi</th><th>Ngày xử lý</th><th>Trạng thái</th><th style="text-align:right">Thao tác</th></tr></thead>
      <tbody id="ekBody"><tr><td colspan="7"><div class="ek-empty"><i class="bi bi-arrow-clockwise" style="animation:spin 1s linear infinite"></i><p>Đang tải...</p></div></td></tr></tbody>
    </table>
  </div>
  <div class="ek-pag" id="ekPag" style="display:none">
    <span class="ek-pag-info" id="ekPagI"></span>
    <div class="ek-pag-btns" id="ekPagB"></div>
  </div>
</div>

<!-- Detail Modal -->
<div class="ek-mbg" id="detBg" onclick="if(event.target===this)ekCloseDet()">
  <div class="ek-modal">
    <div class="ek-mh">
      <h3><i class="bi bi-person-badge" style="color:#6366f1;margin-right:6px"></i>Chi tiết hồ sơ eKYC</h3>
      <button class="ek-mcls" onclick="ekCloseDet()"><i class="bi bi-x-lg"></i></button>
    </div>
    <div class="ek-mb">
      <div class="ek-igrid" id="mdI"></div>
      <div style="margin-bottom:16px" id="mdSt"></div>
      <div style="font-size:13px;font-weight:600;color:#475569;margin-bottom:10px"><i class="bi bi-images" style="margin-right:5px"></i>Ảnh xác thực</div>
      <div class="ek-imgs" id="mdImg"></div>
      <div id="mdRej" style="display:none;padding:12px 14px;background:#fef2f2;border-radius:10px;border:1px solid #fecaca">
        <div style="font-size:12px;font-weight:600;color:#b91c1c;margin-bottom:4px"><i class="bi bi-exclamation-triangle"></i> Lý do từ chối</div>
        <div id="mdRejTxt" style="font-size:13px;color:#374151"></div>
      </div>
    </div>
    <div class="ek-mf" id="mdFt"></div>
  </div>
</div>

<!-- Reject Modal -->
<div class="rj-bg" id="rjBg">
  <div class="rj-box">
    <h4><i class="bi bi-x-circle" style="color:#ef4444;margin-right:6px"></i>Từ chối hồ sơ eKYC</h4>
    <p>Nhập lý do từ chối để thông báo đến người dùng.</p>
    <textarea id="rjReason" placeholder="Ảnh CCCD không rõ / thông tin không khớp..."></textarea>
    <div class="rj-acts">
      <button class="eb cl" onclick="ekCloseRj()"><i class="bi bi-x"></i> Hủy</button>
      <button class="eb dn" onclick="ekDoRj()"><i class="bi bi-x-circle"></i> Xác nhận từ chối</button>
    </div>
  </div>
</div>

<!-- Lightbox -->
<div class="lb" id="lbBg" onclick="this.classList.remove('show')"><img id="lbImg" src="" alt=""></div>
<div class="ek-toast" id="ekToast"></div>

<script>
(function(){
  var CP='${pageContext.request.contextPath}',tk=localStorage.getItem('token');
  var pg=0,sz=10,flt='',srch='',rid=null,stmr=null;
  loadStats();loadList();

  function loadStats(){
    api('/api/admin/ekyc/count').then(function(d){
      g('s0').textContent=d.tatCa||0; g('s1').textContent=d.choDuyet||0;
      g('s2').textContent=d.daDuyet||0; g('s3').textContent=d.tuChoi||0;
    }).catch(function(){});
  }

  function loadList(){
    var u='/api/admin/ekyc?page='+pg+'&size='+sz;
    if(flt)  u+='&trangThai='+encodeURIComponent(flt);
    if(srch) u+='&q='+encodeURIComponent(srch);
    api(u).then(function(p){ draw(p.content||[]); pag(p); }).catch(function(e){
      g('ekBody').innerHTML='<tr><td colspan="7"><div class="ek-empty"><i class="bi bi-exclamation-circle"></i><p>Lỗi tải dữ liệu ('+e+')</p></div></td></tr>';
    });
  }

  function draw(rows){
    var tb=g('ekBody');
    if(!rows.length){ tb.innerHTML='<tr><td colspan="7"><div class="ek-empty"><i class="bi bi-inbox"></i><p>Không có hồ sơ nào</p></div></td></tr>'; return; }
    tb.innerHTML=rows.map(function(r,i){
      var nd=r.nguoiDung||{}, nm=nd.hoTen||'—', em=nd.email||'—';
      var av=nd.avatar||('https://ui-avatars.com/api/?name='+encodeURIComponent(nm)+'&background=e0e7ff&color=6366f1&size=64');
      var act='<button class="ek-btn ek-bv" onclick="ekDet('+r.maEKYC+')"><i class="bi bi-eye"></i> Xem</button>';
      if(r.trangThai==='CHO_DUYET'){
        act+='<button class="ek-btn ek-bo" onclick="ekOk('+r.maEKYC+',event)"><i class="bi bi-check-lg"></i> Duyệt</button>';
        act+='<button class="ek-btn ek-bd" onclick="ekOpenRj('+r.maEKYC+')"><i class="bi bi-x-lg"></i> Từ chối</button>';
      }else{
        act+='<button class="ek-btn ek-br" onclick="ekRst('+r.maEKYC+',event)"><i class="bi bi-arrow-counterclockwise"></i> Đặt lại</button>';
      }
      return '<tr>'
        +'<td style="color:#94a3b8;font-size:12px">'+(pg*sz+i+1)+'</td>'
        +'<td><div class="ek-uc"><img src="'+x(av)+'" class="ek-av" onerror="this.src=\'https://ui-avatars.com/api/?name=U&background=e0e7ff&color=6366f1&size=64\'">'
        +'<div><div class="ek-un">'+x(nm)+'</div><div class="ek-ue">'+x(em)+'</div></div></div></td>'
        +'<td style="font-family:monospace">'+x(r.soCCCD||'—')+'</td>'
        +'<td>'+dt(r.ngayGui)+'</td><td>'+dt(r.ngayDuyet)+'</td>'
        +'<td>'+bdg(r.trangThai)+'</td>'
        +'<td><div class="ek-acts" style="justify-content:flex-end">'+act+'</div></td></tr>';
    }).join('');
  }

  function pag(p){
    var w=g('ekPag'), tot=p.totalElements||0, pages=p.totalPages||1;
    if(!tot){ w.style.display='none'; return; } w.style.display='flex';
    g('ekPagI').textContent='Hiển thị '+(pg*sz+1)+'–'+Math.min(pg*sz+sz,tot)+' / '+tot;
    var h='<button class="ek-pb" '+(pg===0?'disabled':'')+' onclick="ekPg('+(pg-1)+')"><i class="bi bi-chevron-left"></i></button>';
    var s=Math.max(0,pg-2), e=Math.min(pages-1,s+4);
    for(var pp=s;pp<=e;pp++) h+='<button class="ek-pb'+(pp===pg?' on':'')+'" onclick="ekPg('+pp+')">'+(pp+1)+'</button>';
    h+='<button class="ek-pb" '+(pg>=pages-1?'disabled':'')+' onclick="ekPg('+(pg+1)+')"><i class="bi bi-chevron-right"></i></button>';
    g('ekPagB').innerHTML=h;
  }

  window.ekDet=function(id){
    api('/api/admin/ekyc/'+id).then(function(r){
      var nd=r.nguoiDung||{}, nm=nd.hoTen||'—';
      g('mdI').innerHTML=[
        ['Họ tên',nm],['Email',nd.email||'—'],
        ['Số điện thoại',nd.soDienThoai||'—'],['Số CCCD',r.soCCCD||'—'],
        ['Ngày gửi',dt(r.ngayGui)],['Ngày xử lý',dt(r.ngayDuyet)],
      ].map(function(f){ return '<div class="ek-iitem"><label>'+f[0]+'</label><span>'+x(String(f[1]))+'</span></div>'; }).join('');
      g('mdSt').innerHTML='<div style="display:flex;align-items:center;gap:8px"><span style="font-size:12px;font-weight:600;color:#64748b">TRẠNG THÁI:</span>'+bdg(r.trangThai)+'</div>';
      g('mdImg').innerHTML=[
        [r.anhMatTruoc,'CCCD Mặt trước'],[r.anhMatSau,'CCCD Mặt sau'],[r.anhChanDung,'Ảnh chân dung']
      ].map(function(img){
        return img[0]
          ? '<div class="ek-iw"><img src="'+x(img[0])+'" alt="'+x(img[1])+'" onclick="ekLb(\''+x(img[0])+'\')"><div class="ek-ilbl">'+img[1]+'</div></div>'
          : '<div class="ek-iw"><div class="ek-iph"><i class="bi bi-image-fill"></i>'+img[1]+'</div></div>';
      }).join('');
      var rjW=g('mdRej');
      if(r.trangThai==='TU_CHOI'&&r.lyDoTuChoi){ g('mdRejTxt').textContent=r.lyDoTuChoi; rjW.style.display='block'; } else rjW.style.display='none';
      var ft='';
      if(r.trangThai==='CHO_DUYET'){
        ft+='<button class="eb ok" onclick="ekOk('+id+',null,1)"><i class="bi bi-check-circle"></i> Xác minh</button>';
        ft+='<button class="eb dn" onclick="ekOpenRj('+id+')"><i class="bi bi-x-circle"></i> Từ chối</button>';
      } else {
        ft+='<button class="eb rs" onclick="ekRst('+id+',null,1)"><i class="bi bi-arrow-counterclockwise"></i> Đặt lại</button>';
      }
      ft+='<button class="eb cl" onclick="ekCloseDet()"><i class="bi bi-x"></i> Đóng</button>';
      g('mdFt').innerHTML=ft;
      g('detBg').classList.add('show'); document.body.style.overflow='hidden';
    }).catch(function(){ toast('Không thể tải chi tiết','er'); });
  };
  window.ekCloseDet=function(){ g('detBg').classList.remove('show'); document.body.style.overflow=''; };

  window.ekOk=function(id,e,fm){
    if(e) e.stopPropagation();
    if(!confirm('Xác nhận duyệt hồ sơ eKYC này?')) return;
    api('/api/admin/ekyc/'+id+'/duyet','PUT').then(function(){
      toast('✅ Đã xác minh thành công!','ok');
      if(fm) ekCloseDet(); loadStats(); loadList();
    }).catch(function(){ toast('Lỗi khi duyệt hồ sơ','er'); });
  };

  window.ekOpenRj=function(id){ rid=id; g('rjReason').value=''; g('rjBg').classList.add('show'); };
  window.ekCloseRj =function(){ g('rjBg').classList.remove('show'); };
  window.ekDoRj=function(){
    var ly=g('rjReason').value.trim(); if(!ly){ g('rjReason').focus(); return; }
    api('/api/admin/ekyc/'+rid+'/tu-choi','PUT',{lyDo:ly}).then(function(){
      toast('Đã từ chối hồ sơ','er'); ekCloseRj(); ekCloseDet(); loadStats(); loadList();
    }).catch(function(){ toast('Lỗi khi từ chối','er'); });
  };

  window.ekRst=function(id,e,fm){
    if(e) e.stopPropagation();
    if(!confirm('Đặt lại hồ sơ về Chờ duyệt?')) return;
    api('/api/admin/ekyc/'+id+'/dat-lai','PUT').then(function(){
      toast('Đã đặt lại trạng thái','ok');
      if(fm) ekCloseDet(); loadStats(); loadList();
    }).catch(function(){ toast('Lỗi khi đặt lại','er'); });
  };

  window.ekLb=function(src){ g('lbImg').src=src; g('lbBg').classList.add('show'); };

  window.ekFlt=function(f){
    flt=f; pg=0;
    ['fA','fC','fD','fT'].forEach(function(id){ g(id).classList.remove('on'); });
    if(!f) g('fA').classList.add('on');
    else if(f==='CHO_DUYET') g('fC').classList.add('on');
    else if(f==='DA_DUYET')  g('fD').classList.add('on');
    else if(f==='TU_CHOI')   g('fT').classList.add('on');
    loadList();
  };
  window.ekDbc=function(){ clearTimeout(stmr); stmr=setTimeout(function(){ srch=g('ekSrch').value.trim(); pg=0; loadList(); },350); };
  window.ekPg =function(p){ pg=p; loadList(); };

  function bdg(s){
    if(s==='DA_DUYET') return '<span class="ek-bdg ok"><i class="bi bi-check-circle-fill"></i> Đã xác minh</span>';
    if(s==='TU_CHOI')  return '<span class="ek-bdg no"><i class="bi bi-x-circle-fill"></i> Từ chối</span>';
    return '<span class="ek-bdg cho"><i class="bi bi-hourglass-split"></i> Chờ duyệt</span>';
  }
  function dt(s){ if(!s) return '—'; var d=new Date(s); if(isNaN(d)) return s; return d.toLocaleDateString('vi-VN',{day:'2-digit',month:'2-digit',year:'numeric',hour:'2-digit',minute:'2-digit'}); }
  function x(s){ return String(s).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;'); }
  function g(id){ return document.getElementById(id); }
  function toast(msg,type){ var t=g('ekToast'); t.textContent=msg; t.className='ek-toast '+(type||'ok'); t.classList.add('show'); setTimeout(function(){ t.classList.remove('show'); },3000); }
  function api(path,method,body){
    var opts={method:method||'GET',headers:{'Content-Type':'application/json'}};
    if(tk) opts.headers['Authorization']='Bearer '+tk;
    if(body) opts.body=JSON.stringify(body);
    return fetch(CP+path,opts).then(function(r){ if(!r.ok) throw r.status; return r.json(); });
  }
})();
</script>
<%@ include file="includes/footer.jspf" %>
