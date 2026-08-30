<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="includes/header.jspf" %>

<div class="owner-page-head">
    <div>
        <div class="eyebrow"><i class="bi bi-door-open"></i> QUẢN LÝ PHÒNG</div>
        <h1>Phòng trọ</h1>
        <p>Thêm, sửa, xóa và theo dõi tình trạng phòng bằng dữ liệu SQL Server.</p>
    </div>
    <button type="button" class="owner-btn primary" id="openRoomModal">
        <i class="bi bi-plus-lg"></i> Thêm phòng
    </button>
</div>

<c:if test="${param.success == '1'}">
    <div class="owner-form-note" style="margin-bottom:16px;">
        <i class="bi bi-check-circle-fill"></i>
        <span>Thêm phòng thành công. Dữ liệu đã được lưu vào SQL Server.</span>
    </div>
</c:if>
<c:if test="${param.success == '2'}">
    <div class="owner-form-note" style="margin-bottom:16px;">
        <i class="bi bi-check-circle-fill"></i>
        <span>Cập nhật phòng thành công.</span>
    </div>
</c:if>
<c:if test="${param.success == '3'}">
    <div class="owner-form-note" style="margin-bottom:16px;">
        <i class="bi bi-check-circle-fill"></i>
        <span>Xóa phòng thành công.</span>
    </div>
</c:if>
<c:if test="${not empty param.error}">
    <div class="owner-form-note" style="margin-bottom:16px;border-color:rgba(248,113,113,.25);">
        <i class="bi bi-exclamation-circle-fill" style="color:#f87171;"></i>
        <span>${param.error}</span>
    </div>
</c:if>

<div class="room-summary">
    <div><span><i class="bi bi-grid-3x3-gap"></i> Tổng phòng</span><b>${roomTotal}</b></div>
    <div><span><i class="bi bi-person-check"></i> Đang thuê</span><b>${roomOccupied}</b></div>
    <div><span><i class="bi bi-door-open"></i> Còn trống</span><b>${roomAvailable}</b></div>
</div>

<section class="owner-card">
    <div class="toolbar-owner">
        <div class="owner-input-wrap">
            <i class="bi bi-search"></i>
            <input id="roomSearch" class="owner-input" placeholder="Tìm số phòng, nhà trọ...">
        </div>
        <select id="roomPropertyFilter" class="owner-input">
            <option value="all">Tất cả nhà trọ</option>
            <c:forEach var="property" items="${roomProperties}">
                <option value="${property.maNhaTro}">${property.tenNhaTro}</option>
            </c:forEach>
        </select>
        <select id="roomStatusFilter" class="owner-input">
            <option value="all">Tất cả trạng thái</option>
            <option value="available">Còn trống</option>
            <option value="occupied">Đang thuê</option>
        </select>
        <button type="button" class="owner-btn light" id="roomFilterBtn">
            <i class="bi bi-funnel"></i> Lọc
        </button>
    </div>

    <div id="roomGrid" class="room-grid">
        <c:forEach var="room" items="${roomCards}">
            <article class="room-card ${room.available ? 'available-card' : 'occupied-card'}"
                     data-name="${room.name} ${room.propertyName}"
                     data-property="${room.propertyId}"
                     data-status="${room.status}">
                <div class="room-top">
                    <b><i class="bi ${room.available ? 'bi-door-open' : 'bi-door-closed'}"></i> ${room.name}</b>
                    <span class="status-pill ${room.available ? 'orange' : 'green'}">
                        ${room.available ? 'Còn trống' : 'Đang thuê'}
                    </span>
                </div>
                <h3>
                    <fmt:formatNumber value="${room.price}" type="number" groupingUsed="true" maxFractionDigits="0"/> đ
                    <small>/ tháng</small>
                </h3>
                <p><i class="bi bi-buildings"></i> ${room.propertyName}</p>
                <div class="room-foot">
                    <span>${not empty room.area ? room.area : '—'} m²</span>
                    <span>Tối đa ${room.maxPeople} người</span>
                </div>

                <div style="display:flex;gap:8px;margin-top:13px;">
                    <button type="button" class="owner-btn light small edit-room"
                            data-id="${room.id}"
                            data-property="${room.propertyId}"
                            data-name="${room.name}"
                            data-price="${room.price}"
                            data-area="${room.area}"
                            data-type="${room.roomType}"
                            data-max="${room.maxPeople}"
                            data-electric="${room.electricPrice}"
                            data-water="${room.waterPrice}"
                            data-parking="${room.parkingPrice}"
                            data-internet="${room.internetPrice}"
                            data-status="${room.available}">
                        <i class="bi bi-pencil"></i> Sửa
                    </button>

                    <form method="post"
                          action="${pageContext.request.contextPath}/chu-tro/rooms/${room.id}/delete"
                          onsubmit="return confirm('Bạn có chắc muốn xóa phòng ${room.name}?');"
                          style="margin:0;">
                        <button type="submit" class="owner-btn small"
                                style="background:rgba(239,68,68,.10);border:1px solid rgba(239,68,68,.25);color:#fca5a5;"
                                ${room.available ? '' : 'disabled'}>
                            <i class="bi bi-trash3"></i> Xóa
                        </button>
                    </form>
                </div>

                <c:if test="${not room.available}">
                    <small style="display:block;margin-top:8px;color:#64748b;">
                        Phòng đang thuê nên không thể xóa.
                    </small>
                </c:if>
            </article>
        </c:forEach>
    </div>

    <div id="roomEmpty" class="owner-empty" hidden>
        <i class="bi bi-door-open"></i>
        <strong>Không tìm thấy phòng</strong>
        <span>Thử đổi từ khóa, nhà trọ hoặc trạng thái.</span>
    </div>

    <c:if test="${empty roomCards}">
        <div class="owner-empty">
            <i class="bi bi-door-open"></i>
            <strong>Chưa có phòng nào</strong>
            <span>Hãy bấm "Thêm phòng" để tạo phòng đầu tiên.</span>
        </div>
    </c:if>
</section>

<!-- MODAL THÊM / SỬA PHÒNG -->
<div class="owner-modal" id="roomModal" aria-hidden="true">
    <div class="owner-modal-backdrop" data-close-room></div>

    <div class="owner-modal-dialog" role="dialog" aria-modal="true">
        <div class="owner-modal-head">
            <div>
                <span class="modal-kicker"><i class="bi bi-door-open"></i> QUẢN LÝ PHÒNG</span>
                <h2 id="roomModalTitle">Thêm phòng</h2>
                <p id="roomModalSubtitle">Tạo phòng mới và gán vào nhà trọ của bạn.</p>
            </div>
            <button type="button" class="modal-close" data-close-room aria-label="Đóng">
                <i class="bi bi-x-lg"></i>
            </button>
        </div>

        <form id="roomForm" class="owner-form" method="post"
              action="${pageContext.request.contextPath}/chu-tro/rooms/save">
            <input type="hidden" name="maPhong" id="roomId">

            <div class="form-grid">
                <label>
                    <span>Nhà trọ <b>*</b></span>
                    <select name="maNhaTro" id="roomProperty" class="owner-input" required>
                        <option value="">-- Chọn nhà trọ --</option>
                        <c:forEach var="property" items="${roomProperties}">
                            <option value="${property.maNhaTro}">${property.tenNhaTro}</option>
                        </c:forEach>
                    </select>
                </label>

                <label>
                    <span>Tên / số phòng <b>*</b></span>
                    <div class="form-control-icon">
                        <i class="bi bi-door-open"></i>
                        <input name="tenPhong" id="roomName" required maxlength="255" placeholder="VD: Phòng 105">
                    </div>
                </label>

                <label>
                    <span>Giá phòng / tháng <b>*</b></span>
                    <div class="form-control-icon">
                        <i class="bi bi-cash-stack"></i>
                        <input name="giaPhong" id="roomPrice" type="number" min="0" step="1000" required>
                    </div>
                </label>

                <label>
                    <span>Diện tích (m²)</span>
                    <div class="form-control-icon">
                        <i class="bi bi-aspect-ratio"></i>
                        <input name="dienTich" id="roomArea" type="number" min="0" step="0.5">
                    </div>
                </label>

                <label>
                    <span>Loại phòng</span>
                    <select name="loaiPhong" id="roomType" class="owner-input">
                        <option value="Phòng tiêu chuẩn">Phòng tiêu chuẩn</option>
                        <option value="Phòng có gác">Phòng có gác</option>
                        <option value="Phòng studio">Phòng studio</option>
                        <option value="Phòng VIP">Phòng VIP</option>
                    </select>
                </label>

                <label>
                    <span>Số người tối đa</span>
                    <div class="form-control-icon">
                        <i class="bi bi-people"></i>
                        <input name="soLuongNguoi" id="roomMax" type="number" min="1" step="1" value="2">
                    </div>
                </label>

                <label>
                    <span>Tiền điện / kWh</span>
                    <div class="form-control-icon">
                        <i class="bi bi-lightning-charge"></i>
                        <input name="giaDien" id="roomElectric" type="number" min="0" step="100">
                    </div>
                </label>

                <label>
                    <span>Tiền nước / m³</span>
                    <div class="form-control-icon">
                        <i class="bi bi-droplet"></i>
                        <input name="giaNuoc" id="roomWater" type="number" min="0" step="100">
                    </div>
                </label>

                <label>
                    <span>Phí gửi xe / tháng</span>
                    <div class="form-control-icon">
                        <i class="bi bi-bicycle"></i>
                        <input name="giaGuiXe" id="roomParking" type="number" min="0" step="1000">
                    </div>
                </label>

                <label>
                    <span>Phí Internet / tháng</span>
                    <div class="form-control-icon">
                        <i class="bi bi-wifi"></i>
                        <input name="giaInternet" id="roomInternet" type="number" min="0" step="1000">
                    </div>
                </label>

                <label>
                    <span>Trạng thái</span>
                    <select name="trangThai" id="roomStatus" class="owner-input">
                        <option value="true">Còn trống</option>
                        <option value="false">Đang thuê</option>
                    </select>
                </label>
            </div>

            <div class="owner-form-note">
                <i class="bi bi-info-circle"></i>
                Dữ liệu được lưu trực tiếp vào bảng <b>PHONG_TRO</b>. Chỉ nhà trọ thuộc tài khoản Chủ trọ đang đăng nhập mới được thao tác.
            </div>

            <div class="owner-modal-actions">
                <button type="button" class="owner-btn light" data-close-room>Hủy</button>
                <button type="submit" class="owner-btn primary" id="roomSubmit">
                    <i class="bi bi-check-lg"></i> Tạo phòng
                </button>
            </div>
        </form>
    </div>
</div>

<script>
(function () {
    "use strict";

    const contextPath = "${pageContext.request.contextPath}";
    const modal = document.getElementById("roomModal");
    const form = document.getElementById("roomForm");
    const openButton = document.getElementById("openRoomModal");
    const title = document.getElementById("roomModalTitle");
    const subtitle = document.getElementById("roomModalSubtitle");
    const submit = document.getElementById("roomSubmit");

    const idInput = document.getElementById("roomId");
    const propertyInput = document.getElementById("roomProperty");
    const nameInput = document.getElementById("roomName");
    const priceInput = document.getElementById("roomPrice");
    const areaInput = document.getElementById("roomArea");
    const typeInput = document.getElementById("roomType");
    const maxInput = document.getElementById("roomMax");
    const electricInput = document.getElementById("roomElectric");
    const waterInput = document.getElementById("roomWater");
    const parkingInput = document.getElementById("roomParking");
    const internetInput = document.getElementById("roomInternet");
    const statusInput = document.getElementById("roomStatus");

    function openModal(editData) {
        if (!modal) return;

        if (editData) {
            title.textContent = "Sửa phòng";
            subtitle.textContent = "Cập nhật thông tin phòng trong nhà trọ của bạn.";
            submit.innerHTML = '<i class="bi bi-check-lg"></i> Lưu thay đổi';
            form.action = contextPath + "/chu-tro/rooms/update";

            idInput.value = editData.id || "";
            propertyInput.value = editData.property || "";
            nameInput.value = editData.name || "";
            priceInput.value = editData.price || "";
            areaInput.value = editData.area || "";
            typeInput.value = editData.type || "Phòng tiêu chuẩn";
            maxInput.value = editData.max || "1";
            electricInput.value = editData.electric || "";
            waterInput.value = editData.water || "";
            parkingInput.value = editData.parking || "";
            internetInput.value = editData.internet || "";
            statusInput.value = String(editData.status) === "true" ? "true" : "false";
        } else {
            title.textContent = "Thêm phòng";
            subtitle.textContent = "Tạo phòng mới và gán vào nhà trọ của bạn.";
            submit.innerHTML = '<i class="bi bi-check-lg"></i> Tạo phòng';
            form.action = contextPath + "/chu-tro/rooms/save";
            form.reset();
            idInput.value = "";
            statusInput.value = "true";
            maxInput.value = "2";
        }

        modal.classList.add("show");
        modal.setAttribute("aria-hidden", "false");
        document.body.classList.add("modal-open");
        setTimeout(() => nameInput?.focus(), 80);
    }

    function closeModal() {
        modal?.classList.remove("show");
        modal?.setAttribute("aria-hidden", "true");
        document.body.classList.remove("modal-open");
    }

    openButton?.addEventListener("click", () => openModal(null));

    document.querySelectorAll(".edit-room").forEach(button => {
        button.addEventListener("click", () => openModal({
            id: button.dataset.id,
            property: button.dataset.property,
            name: button.dataset.name,
            price: button.dataset.price,
            area: button.dataset.area,
            type: button.dataset.type,
            max: button.dataset.max,
            electric: button.dataset.electric,
            water: button.dataset.water,
            parking: button.dataset.parking,
            internet: button.dataset.internet,
            status: button.dataset.status
        }));
    });

    document.querySelectorAll("[data-close-room]").forEach(button => {
        button.addEventListener("click", closeModal);
    });

    document.addEventListener("keydown", event => {
        if (event.key === "Escape") closeModal();
    });

    form?.addEventListener("submit", () => {
        submit.disabled = true;
        submit.style.opacity = ".7";
    });

    const search = document.getElementById("roomSearch");
    const propertyFilter = document.getElementById("roomPropertyFilter");
    const statusFilter = document.getElementById("roomStatusFilter");
    const filterButton = document.getElementById("roomFilterBtn");
    const empty = document.getElementById("roomEmpty");

    function filterRooms() {
        const keyword = (search?.value || "").trim().toLowerCase();
        const property = propertyFilter?.value || "all";
        const status = statusFilter?.value || "all";
        let visible = 0;

        document.querySelectorAll("#roomGrid .room-card").forEach(card => {
            const name = (card.dataset.name || "").toLowerCase();
            const cardProperty = card.dataset.property || "";
            const cardStatus = card.dataset.status || "";

            const show =
                (!keyword || name.includes(keyword)) &&
                (property === "all" || cardProperty === property) &&
                (status === "all" || cardStatus === status);

            card.style.display = show ? "" : "none";
            if (show) visible++;
        });

        if (empty) empty.hidden = visible !== 0;
    }

    search?.addEventListener("input", filterRooms);
    propertyFilter?.addEventListener("change", filterRooms);
    statusFilter?.addEventListener("change", filterRooms);
    filterButton?.addEventListener("click", filterRooms);
})();
</script>

<%@ include file="includes/footer.jspf" %>
