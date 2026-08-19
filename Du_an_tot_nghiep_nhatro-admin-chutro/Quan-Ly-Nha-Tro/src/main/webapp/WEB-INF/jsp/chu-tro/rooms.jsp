<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>

<div class="owner-page-head">
    <div>
        <div class="eyebrow"><i class="bi bi-door-open"></i> QUẢN LÝ PHÒNG</div>
        <h1>Phòng trọ</h1>
        <p>Theo dõi tình trạng phòng, giá thuê và người đang thuê.</p>
    </div>
    <button type="button" class="owner-btn primary" id="openRoomModal">
        <i class="bi bi-plus-lg"></i> Thêm phòng
    </button>
</div>

<div class="room-summary">
    <div><span><i class="bi bi-grid-3x3-gap"></i> Tổng phòng</span><b id="roomTotal">24</b></div>
    <div><span><i class="bi bi-person-check"></i> Đang thuê</span><b id="roomOccupied">18</b></div>
    <div><span><i class="bi bi-door-open"></i> Còn trống</span><b id="roomAvailable">6</b></div>
</div>

<section class="owner-card">
    <div class="toolbar-owner">
        <div class="owner-input-wrap">
            <i class="bi bi-search"></i>
            <input id="roomSearch" class="owner-input" placeholder="Tìm số phòng, tên người thuê...">
        </div>
        <select id="roomPropertyFilter" class="owner-input">
            <option value="all">Tất cả nhà trọ</option>
            <option value="Nhà trọ Bình Minh">Nhà trọ Bình Minh</option>
            <option value="Room Connect Garden">Room Connect Garden</option>
            <option value="Nhà trọ An Phú">Nhà trọ An Phú</option>
            <option value="Nhà trọ Phú Gia">Nhà trọ Phú Gia</option>
        </select>
        <select id="roomStatusFilter" class="owner-input">
            <option value="all">Tất cả trạng thái</option>
            <option value="occupied">Đang thuê</option>
            <option value="available">Còn trống</option>
            <option value="pending">Chờ ký HĐ</option>
        </select>
        <button type="button" class="owner-btn light" id="roomFilterBtn"><i class="bi bi-funnel"></i> Lọc</button>
    </div>

    <div id="roomGrid" class="room-grid">
        <article class="room-card occupied-card" data-name="Phòng 101 Trần Minh Anh" data-property="Nhà trọ Bình Minh" data-status="occupied">
            <div class="room-top"><b><i class="bi bi-door-closed"></i> Phòng 101</b><span class="status-pill green">Đang thuê</span></div>
            <h3>2.800.000đ <small>/ tháng</small></h3>
            <p><i class="bi bi-person"></i> Trần Minh Anh</p>
            <div class="room-foot"><span>Điện: 128 kWh</span><span>Nước: 7 m³</span></div>
        </article>

        <article class="room-card available-card" data-name="Phòng 102" data-property="Nhà trọ Bình Minh" data-status="available">
            <div class="room-top"><b><i class="bi bi-door-open"></i> Phòng 102</b><span class="status-pill orange">Còn trống</span></div>
            <h3>2.800.000đ <small>/ tháng</small></h3>
            <p><i class="bi bi-door-open"></i> Sẵn sàng cho thuê</p>
            <div class="room-foot"><span>24 m²</span><span>1 giường</span></div>
        </article>

        <article class="room-card occupied-card" data-name="Phòng 103 Nguyễn Hoàng Nam" data-property="Room Connect Garden" data-status="occupied">
            <div class="room-top"><b><i class="bi bi-door-closed"></i> Phòng 103</b><span class="status-pill green">Đang thuê</span></div>
            <h3>3.200.000đ <small>/ tháng</small></h3>
            <p><i class="bi bi-person"></i> Nguyễn Hoàng Nam</p>
            <div class="room-foot"><span>Điện: 96 kWh</span><span>Nước: 5 m³</span></div>
        </article>

        <article class="room-card pending-card" data-name="Phòng 104 Phạm Gia Huy" data-property="Nhà trọ An Phú" data-status="pending">
            <div class="room-top"><b><i class="bi bi-door-closed"></i> Phòng 104</b><span class="status-pill purple">Chờ ký HĐ</span></div>
            <h3>3.000.000đ <small>/ tháng</small></h3>
            <p><i class="bi bi-hourglass-split"></i> Phạm Gia Huy</p>
            <div class="room-foot"><span>Hẹn ký: 22/08</span><span>Đã cọc</span></div>
        </article>
    </div>

    <div id="roomEmpty" class="owner-empty" hidden>
        <i class="bi bi-door-open"></i>
        <strong>Không tìm thấy phòng</strong>
        <span>Thử đổi từ khóa, nhà trọ hoặc trạng thái.</span>
    </div>
</section>

<!-- Modal thêm phòng -->
<div class="owner-modal" id="roomModal" aria-hidden="true">
    <div class="owner-modal-backdrop" data-close-room></div>
    <div class="owner-modal-dialog" role="dialog" aria-modal="true" aria-labelledby="roomModalTitle">
        <div class="owner-modal-head">
            <div>
                <span class="modal-kicker"><i class="bi bi-door-open"></i> QUẢN LÝ PHÒNG</span>
                <h2 id="roomModalTitle">Thêm phòng</h2>
                <p>Tạo phòng mới và gán vào một nhà trọ.</p>
            </div>
            <button type="button" class="modal-close" data-close-room aria-label="Đóng"><i class="bi bi-x-lg"></i></button>
        </div>

        <form id="roomForm" class="owner-form">
            <div class="form-grid">
                <label>
                    <span>Tên / số phòng <b>*</b></span>
                    <div class="form-control-icon"><i class="bi bi-door-open"></i><input name="name" required maxlength="255" placeholder="VD: Phòng 105"></div>
                </label>
                <label>
                    <span>Nhà trọ <b>*</b></span>
                    <select name="property" class="owner-input" required>
                        <option value="">-- Chọn nhà trọ --</option>
                        <option>Nhà trọ Bình Minh</option>
                        <option>Room Connect Garden</option>
                        <option>Nhà trọ An Phú</option>
                        <option>Nhà trọ Phú Gia</option>
                    </select>
                </label>
                <label>
                    <span>Giá phòng / tháng <b>*</b></span>
                    <div class="form-control-icon"><i class="bi bi-cash-stack"></i><input name="price" type="number" min="0" step="1000" required value="2800000"></div>
                </label>
                <label>
                    <span>Diện tích (m²)</span>
                    <div class="form-control-icon"><i class="bi bi-aspect-ratio"></i><input name="area" type="number" min="0" step="0.5" value="24"></div>
                </label>
                <label>
                    <span>Loại phòng</span>
                    <select name="type" class="owner-input">
                        <option>Phòng tiêu chuẩn</option>
                        <option>Phòng có gác</option>
                        <option>Phòng studio</option>
                        <option>Phòng VIP</option>
                    </select>
                </label>
                <label>
                    <span>Trạng thái</span>
                    <select name="status" class="owner-input">
                        <option value="available">Còn trống</option>
                        <option value="occupied">Đang thuê</option>
                        <option value="pending">Chờ ký HĐ</option>
                    </select>
                </label>
                <label class="full">
                    <span>Tên người thuê</span>
                    <div class="form-control-icon"><i class="bi bi-person"></i><input name="tenant" maxlength="100" placeholder="Để trống nếu phòng còn trống"></div>
                </label>
            </div>
            <div class="owner-form-note"><i class="bi bi-info-circle"></i> Phòng mới sẽ xuất hiện ngay trong danh sách mẫu. Bạn có thể dùng chức năng tìm kiếm và lọc để kiểm tra.</div>
            <div class="owner-modal-actions">
                <button type="button" class="owner-btn light" data-close-room>Hủy</button>
                <button type="submit" class="owner-btn primary"><i class="bi bi-check-lg"></i> Tạo phòng</button>
            </div>
        </form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/chu-tro-management.js"></script>
<%@ include file="includes/footer.jspf" %>
