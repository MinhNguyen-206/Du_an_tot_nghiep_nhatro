<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="includes/header.jspf" %>

<style>
    .appointment-head{display:flex;justify-content:space-between;align-items:flex-end;gap:20px;margin-bottom:22px}
    .appointment-head .eyebrow{color:#647cff;font-size:12px;font-weight:800;letter-spacing:.12em}
    .appointment-head h1{margin:6px 0;font-size:30px;font-weight:800}
    .appointment-head p{margin:0;color:#7890ad}
    .appointment-stats{display:grid;grid-template-columns:repeat(3,1fr);gap:14px;margin-bottom:18px}
    .appointment-stat{background:#111a2d;border:1px solid #24324b;border-radius:14px;padding:18px 20px}
    .appointment-stat span{display:block;color:#7f94b2;font-size:12px;margin-bottom:7px}
    .appointment-stat strong{font-size:25px;color:#fff}
    .appointment-list-card{background:#111a2d;border:1px solid #24324b;border-radius:16px;overflow:hidden}
    .appointment-list-head{padding:18px 20px;border-bottom:1px solid #24324b;display:flex;justify-content:space-between;align-items:center}
    .appointment-list-head strong{color:#fff;font-size:15px}
    .appointment-list-head span{color:#7186a4;font-size:12px}
    .real-appointment{display:grid;grid-template-columns:76px 1fr auto;gap:18px;align-items:center;padding:19px 20px;border-bottom:1px solid #202d45;transition:.18s}
    .real-appointment:last-child{border-bottom:0}
    .real-appointment:hover{background:#141f34}
    .appointment-time{font-size:18px;font-weight:800;color:#fff;text-align:center}
    .appointment-time small{display:block;color:#6f85a4;font-size:10px;font-weight:600;margin-top:4px}
    .appointment-main b{display:block;color:#fff;font-size:14px;margin-bottom:6px}
    .appointment-main .muted{display:block;color:#7288a8;font-size:12px;margin-bottom:5px}
    .appointment-main .meta{display:flex;flex-wrap:wrap;gap:8px}
    .appointment-main .meta span{background:#18243b;border:1px solid #273650;color:#93a6c2;padding:4px 8px;border-radius:999px;font-size:11px}
    .appointment-status{padding:7px 11px;border-radius:999px;font-size:11px;font-weight:800;white-space:nowrap;background:#352b10;color:#ffc542}
    .appointment-status.done{background:#103b2d;color:#29df9b}
    .appointment-detail-btn{margin-top:8px;border:1px solid #30415f;background:#17243a;color:#c7d5e8;border-radius:8px;padding:7px 11px;cursor:pointer;font-weight:700}
    .appointment-empty{padding:70px 20px;text-align:center;color:#7186a4}
    .appointment-empty i{font-size:42px;color:#5f6fff;display:block;margin-bottom:12px}
    .detail-overlay{position:fixed;inset:0;background:rgba(4,9,18,.76);display:flex;align-items:center;justify-content:center;padding:20px;z-index:5000}
    .detail-overlay.hidden{display:none}
    .detail-modal{width:min(560px,100%);background:#101a2c;border:1px solid #2a3a56;border-radius:18px;box-shadow:0 25px 70px rgba(0,0,0,.4);overflow:hidden}
    .detail-modal-head{padding:20px;border-bottom:1px solid #263650;display:flex;justify-content:space-between;align-items:center}
    .detail-modal-head strong{color:#fff;font-size:18px}
    .detail-close{border:0;background:#1b2940;color:#a9b9d0;width:34px;height:34px;border-radius:9px;cursor:pointer;font-size:18px}
    .detail-body{padding:20px}
    .detail-grid{display:grid;grid-template-columns:1fr 1fr;gap:12px}
    .detail-item{background:#141f33;border:1px solid #253550;border-radius:11px;padding:12px}
    .detail-item label{display:block;color:#7186a4;font-size:11px;margin-bottom:5px}
    .detail-item strong{color:#fff;font-size:13px}
    .detail-note{margin-top:12px;background:#141f33;border:1px solid #253550;border-radius:11px;padding:13px;color:#c1cee0;font-size:13px;white-space:pre-wrap}
    @media(max-width:800px){.appointment-stats{grid-template-columns:1fr}.real-appointment{grid-template-columns:1fr}.appointment-time{text-align:left}.detail-grid{grid-template-columns:1fr}}
</style>

<div class="appointment-head">
    <div>
        <div class="eyebrow">LỊCH HẸN</div>
        <h1>Lịch xem phòng</h1>
        <p>Danh sách lịch hẹn được gửi thật từ người tìm trọ.</p>
    </div>
</div>

<div class="appointment-stats">
    <div class="appointment-stat"><span>Đang xử lý</span><strong>${pendingAppointments}</strong></div>
    <div class="appointment-stat"><span>Đã xác nhận</span><strong>${confirmedAppointments}</strong></div>
    <div class="appointment-stat"><span>Tổng lịch hẹn</span><strong>${appointments.size()}</strong></div>
</div>

<section class="appointment-list-card">
    <div class="appointment-list-head">
        <strong><i class="bi bi-calendar2-week"></i> Lịch hẹn thực tế</strong>
        <span>${appointments.size()} lịch hẹn</span>
    </div>

    <c:choose>
        <c:when test="${appointments != null && appointments.size() > 0}">
            <c:forEach var="a" items="${appointments}">
                <div class="real-appointment">
                    <div class="appointment-time">
                        <c:out value="${a.gioHen}"/>
                        <small><c:out value="${a.ngayHen}"/></small>
                    </div>
                    <div class="appointment-main">
                        <b><c:out value="${a.hoTen}"/> • Xem phòng <c:out value="${a.tenPhong}"/></b>
                        <span class="muted"><c:out value="${a.tenNhaTro}"/> • <c:out value="${a.soDienThoai}"/></span>
                        <div class="meta">
                            <span>👥 <c:out value="${a.soNguoiDiCung}"/> người</span>
                            <span>🐾 <c:out value="${a.nuoiThuCung}"/></span>
                            <span>🏠 <c:out value="${a.thoiGianDuKienDonVao}"/></span>
                        </div>
                        <button type="button" class="appointment-detail-btn"
                                data-name="<c:out value='${a.hoTen}'/>"
                                data-phone="<c:out value='${a.soDienThoai}'/>"
                                data-room="<c:out value='${a.tenPhong}'/>"
                                data-property="<c:out value='${a.tenNhaTro}'/>"
                                data-date="<c:out value='${a.ngayHen}'/>"
                                data-time="<c:out value='${a.gioHen}'/>"
                                data-pet="<c:out value='${a.nuoiThuCung}'/>"
                                data-move="<c:out value='${a.thoiGianDuKienDonVao}'/>"
                                data-note="<c:out value='${a.ghiChu}'/>"
                                onclick="showAppointmentDetail(this)">Xem chi tiết</button>
                    </div>
                    <span class="appointment-status ${a.trangThai == 'Đã xác nhận' ? 'done' : ''}"><c:out value="${a.trangThai}"/></span>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="appointment-empty">
                <i class="bi bi-calendar-x"></i>
                <strong>Chưa có lịch hẹn</strong>
                <p>Khi người tìm trọ bấm “Đặt lịch xem phòng”, lịch sẽ xuất hiện ở đây.</p>
            </div>
        </c:otherwise>
    </c:choose>
</section>

<div class="detail-overlay hidden" id="appointmentDetailOverlay" onclick="if(event.target===this)closeAppointmentDetail()">
    <div class="detail-modal">
        <div class="detail-modal-head">
            <strong>Chi tiết lịch xem phòng</strong>
            <button class="detail-close" type="button" onclick="closeAppointmentDetail()">×</button>
        </div>
        <div class="detail-body">
            <div class="detail-grid">
                <div class="detail-item"><label>Họ và tên</label><strong id="dName">—</strong></div>
                <div class="detail-item"><label>Điện thoại</label><strong id="dPhone">—</strong></div>
                <div class="detail-item"><label>Phòng</label><strong id="dRoom">—</strong></div>
                <div class="detail-item"><label>Nhà trọ</label><strong id="dProperty">—</strong></div>
                <div class="detail-item"><label>Ngày xem phòng</label><strong id="dDate">—</strong></div>
                <div class="detail-item"><label>Giờ xem phòng</label><strong id="dTime">—</strong></div>
                <div class="detail-item"><label>Nuôi thú cưng</label><strong id="dPet">—</strong></div>
                <div class="detail-item"><label>Dự kiến dọn vào</label><strong id="dMove">—</strong></div>
            </div>
            <div class="detail-note"><strong>Ghi chú:</strong><br><span id="dNote">—</span></div>
        </div>
    </div>
</div>

<script>
    function showAppointmentDetail(btn){
        const map={name:'dName',phone:'dPhone',room:'dRoom',property:'dProperty',date:'dDate',time:'dTime',pet:'dPet',move:'dMove',note:'dNote'};
        Object.keys(map).forEach(k=>document.getElementById(map[k]).textContent=btn.dataset[k]||'—');
        document.getElementById('appointmentDetailOverlay').classList.remove('hidden');
    }
    function closeAppointmentDetail(){document.getElementById('appointmentDetailOverlay').classList.add('hidden');}
    document.addEventListener('keydown',e=>{if(e.key==='Escape')closeAppointmentDetail();});
</script>

<%@ include file="includes/footer.jspf" %>
