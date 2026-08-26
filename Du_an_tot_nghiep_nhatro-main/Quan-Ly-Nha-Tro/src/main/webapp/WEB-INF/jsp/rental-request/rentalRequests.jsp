<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ include file="includes/header.jspf" %>

<div class="owner-page-head">
    <div>
        <div class="eyebrow">NGƯỜI THUÊ</div>
        <h1>Yêu cầu thuê</h1>
        <p>Danh sách yêu cầu thuê thật từ SQL Server, chỉ thuộc các phòng trong nhà trọ của bạn.</p>
    </div>
</div>

<div class="room-summary" style="grid-template-columns:repeat(3,1fr);">
    <div>
        <span><i class="bi bi-inbox"></i> Tổng yêu cầu</span>
        <b>${rentalRequests.size()}</b>
    </div>
    <div>
        <span><i class="bi bi-hourglass-split"></i> Chờ xử lý</span>
        <b>${pendingRentalRequests}</b>
    </div>
    <div>
        <span><i class="bi bi-person-check"></i> Đã xử lý</span>
        <b>${rentalRequests.size() - pendingRentalRequests}</b>
    </div>
</div>

<section class="owner-card">
    <c:choose>
        <c:when test="${not empty rentalRequests}">
            <div class="request-list">
                <c:forEach var="request" items="${rentalRequests}">
                    <div class="request-item">
                        <div class="request-avatar">
                            <c:choose>
                                <c:when test="${not empty request.nguoiThue.hoTen}">
                                    ${fn:toUpperCase(fn:substring(request.nguoiThue.hoTen, 0, 1))}
                                </c:when>
                                <c:otherwise>?</c:otherwise>
                            </c:choose>
                        </div>

                        <div class="request-main">
                            <b>${request.nguoiThue.hoTen}</b>
                            <span>
                                Muốn thuê <strong>${request.phong.tenPhong}</strong>
                                • ${request.phong.nhaTro.tenNhaTro}
                                <c:if test="${not empty request.ngayMuonNhanPhong}">
                                    • Dự kiến vào
                                    ${request.ngayMuonNhanPhong.dayOfMonth}/${request.ngayMuonNhanPhong.monthValue}/${request.ngayMuonNhanPhong.year}
                                </c:if>
                            </span>
                            <small>
                                <c:if test="${not empty request.ngayGui}">
                                    Đã gửi:
                                    ${request.ngayGui.dayOfMonth}/${request.ngayGui.monthValue}/${request.ngayGui.year}
                                    ${request.ngayGui.hour}:${request.ngayGui.minute < 10 ? '0' : ''}${request.ngayGui.minute}
                                </c:if>
                                <c:if test="${not empty request.soDienThoaiLienHe}">
                                    • SĐT: ${request.soDienThoaiLienHe}
                                </c:if>
                            </small>
                        </div>

                        <span class="status-pill
                            ${request.trangThai == 'Chờ duyệt' ? 'orange' :
                              request.trangThai == 'Đã duyệt' ? 'green' : 'red'}">
                            ${request.trangThai}
                        </span>

                        <button type="button"
                                class="owner-btn primary small view-request"
                                data-name="${fn:escapeXml(request.nguoiThue.hoTen)}"
                                data-email="${fn:escapeXml(request.nguoiThue.email)}"
                                data-phone="${fn:escapeXml(request.nguoiThue.soDienThoai)}"
                                data-contact="${fn:escapeXml(request.soDienThoaiLienHe)}"
                                data-address="${fn:escapeXml(request.nguoiThue.diaChi)}"
                                data-room="${fn:escapeXml(request.phong.tenPhong)}"
                                data-property="${fn:escapeXml(request.phong.nhaTro.tenNhaTro)}"
                                data-date="${request.ngayMuonNhanPhong}"
                                data-note="${fn:escapeXml(request.ghiChu)}"
                                data-status="${fn:escapeXml(request.trangThai)}">
                            <i class="bi bi-person-vcard"></i> Xem hồ sơ
                        </button>

                        <c:choose>
                            <c:when test="${request.trangThai == 'Chờ duyệt' || empty request.trangThai}">
                                <a class="owner-btn primary small" style="text-decoration:none;"
                                   href="${pageContext.request.contextPath}/tien-trinh-dat-phong?id=${request.maYeuCau}">
                                    <i class="bi bi-check2-circle"></i> Xử lý yêu cầu
                                </a>
                            </c:when>
                            <c:when test="${request.trangThai == 'Đã duyệt'}">
                                <a class="owner-btn light small" style="text-decoration:none;"
                                   href="${pageContext.request.contextPath}/tien-trinh-dat-phong?id=${request.maYeuCau}">
                                    <i class="bi bi-arrow-right-circle"></i> Xem tiến trình
                                </a>
                            </c:when>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="owner-empty">
                <i class="bi bi-inbox"></i>
                <strong>Chưa có yêu cầu thuê</strong>
                <span>Khi người thuê gửi yêu cầu cho phòng của bạn, yêu cầu sẽ xuất hiện ở đây.</span>
            </div>
        </c:otherwise>
    </c:choose>
</section>

<div class="owner-modal" id="requestModal" aria-hidden="true">
    <div class="owner-modal-backdrop" data-close-request></div>
    <div class="owner-modal-dialog" style="width:min(620px,100%);">
        <div class="owner-modal-head">
            <div>
                <span class="modal-kicker"><i class="bi bi-person-vcard"></i> HỒ SƠ NGƯỜI THUÊ</span>
                <h2 id="requestModalName">Chi tiết yêu cầu</h2>
                <p id="requestModalStatus"></p>
            </div>
            <button type="button" class="modal-close" data-close-request>
                <i class="bi bi-x-lg"></i>
            </button>
        </div>

        <div class="owner-form">
            <div class="form-grid">
                <label><span>Họ tên</span><input id="detailName" class="owner-input" readonly></label>
                <label><span>Email</span><input id="detailEmail" class="owner-input" readonly></label>
                <label><span>Số điện thoại</span><input id="detailPhone" class="owner-input" readonly></label>
                <label><span>SĐT liên hệ</span><input id="detailContact" class="owner-input" readonly></label>
                <label><span>Phòng</span><input id="detailRoom" class="owner-input" readonly></label>
                <label><span>Nhà trọ</span><input id="detailProperty" class="owner-input" readonly></label>
                <label><span>Ngày dự kiến nhận phòng</span><input id="detailDate" class="owner-input" readonly></label>
                <label><span>Địa chỉ người thuê</span><input id="detailAddress" class="owner-input" readonly></label>
                <label class="full"><span>Ghi chú</span><textarea id="detailNote" readonly></textarea></label>
            </div>
        </div>
    </div>
</div>

<script>
(function () {
    const modal = document.getElementById("requestModal");

    function setValue(id, value) {
        const el = document.getElementById(id);
        if (el) el.value = value || "—";
    }

    document.querySelectorAll(".view-request").forEach(button => {
        button.addEventListener("click", () => {
            document.getElementById("requestModalName").textContent =
                button.dataset.name || "Chi tiết yêu cầu";
            document.getElementById("requestModalStatus").textContent =
                "Trạng thái: " + (button.dataset.status || "—");

            setValue("detailName", button.dataset.name);
            setValue("detailEmail", button.dataset.email);
            setValue("detailPhone", button.dataset.phone);
            setValue("detailContact", button.dataset.contact);
            setValue("detailRoom", button.dataset.room);
            setValue("detailProperty", button.dataset.property);
            setValue("detailDate", button.dataset.date);
            setValue("detailAddress", button.dataset.address);
            setValue("detailNote", button.dataset.note);

            modal.classList.add("show");
            modal.setAttribute("aria-hidden", "false");
            document.body.classList.add("modal-open");
        });
    });

    function closeModal() {
        modal.classList.remove("show");
        modal.setAttribute("aria-hidden", "true");
        document.body.classList.remove("modal-open");
    }

    document.querySelectorAll("[data-close-request]").forEach(button => {
        button.addEventListener("click", closeModal);
    });

    document.addEventListener("keydown", event => {
        if (event.key === "Escape") closeModal();
    });
})();
</script>

<%@ include file="includes/footer.jspf" %>
