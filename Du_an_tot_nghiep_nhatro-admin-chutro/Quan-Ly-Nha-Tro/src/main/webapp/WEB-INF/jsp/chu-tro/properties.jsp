<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>

<div class="owner-page-head">
    <div>
        <div class="eyebrow"><i class="bi bi-buildings"></i> QUẢN LÝ BẤT ĐỘNG SẢN</div>
        <h1>Nhà trọ của tôi</h1>
        <p>Quản lý các khu nhà trọ, địa chỉ và tình trạng hoạt động trên một màn hình.</p>
    </div>
    <button type="button" class="owner-btn primary" id="openPropertyModal">
        <i class="bi bi-plus-lg"></i> Thêm nhà trọ
    </button>
</div>

<div class="owner-stats compact">
    <div class="owner-stat blue">
        <div class="stat-icon"><i class="bi bi-buildings"></i></div>
        <span>Tổng nhà trọ</span>
        <strong id="propertyCount">4</strong>
        <small>Đang hoạt động</small>
    </div>
    <div class="owner-stat green">
        <div class="stat-icon"><i class="bi bi-door-open"></i></div>
        <span>Tổng phòng</span>
        <strong id="propertyRoomCount">24</strong>
        <small><span id="propertyOccupiedCount">18</span> phòng đang thuê</small>
    </div>
    <div class="owner-stat orange">
        <div class="stat-icon"><i class="bi bi-door-open-fill"></i></div>
        <span>Phòng trống</span>
        <strong id="propertyAvailableCount">6</strong>
        <small>Có thể đăng tin</small>
    </div>
</div>

<section class="owner-card">
    <div class="toolbar-owner">
        <div class="owner-input-wrap">
            <i class="bi bi-search"></i>
            <input id="propertySearch" class="owner-input" placeholder="Tìm theo tên nhà trọ...">
        </div>
        <select id="propertyStatusFilter" class="owner-input">
            <option value="all">Tất cả trạng thái</option>
            <option value="active">Đang hoạt động</option>
            <option value="paused">Tạm ngưng</option>
        </select>
        <button type="button" class="owner-btn light" id="propertyFilterBtn">
            <i class="bi bi-funnel"></i> Lọc
        </button>
    </div>

    <div id="propertyGrid" class="property-grid">
        <article class="property-card" data-name="Nhà trọ Bình Minh" data-status="active">
            <div class="property-cover one"><span><i class="bi bi-check-circle-fill"></i> ĐANG HOẠT ĐỘNG</span></div>
            <div class="property-body">
                <h3>Nhà trọ Bình Minh</h3>
                <p><i class="bi bi-geo-alt"></i> 125 Lê Lợi, Quận 1</p>
                <div class="property-meta"><b>12 phòng</b><span>9 đang thuê</span></div>
                <button type="button" class="owner-btn light full-btn property-manage">
                    <i class="bi bi-gear"></i> Quản lý nhà trọ
                </button>
            </div>
        </article>

        <article class="property-card" data-name="Room Connect Garden" data-status="active">
            <div class="property-cover two"><span><i class="bi bi-check-circle-fill"></i> ĐANG HOẠT ĐỘNG</span></div>
            <div class="property-body">
                <h3>Room Connect Garden</h3>
                <p><i class="bi bi-geo-alt"></i> 28 Nguyễn Trãi, Quận 5</p>
                <div class="property-meta"><b>6 phòng</b><span>5 đang thuê</span></div>
                <button type="button" class="owner-btn light full-btn property-manage">
                    <i class="bi bi-gear"></i> Quản lý nhà trọ
                </button>
            </div>
        </article>

        <article class="property-card" data-name="Nhà trọ An Phú" data-status="active">
            <div class="property-cover three"><span><i class="bi bi-check-circle-fill"></i> ĐANG HOẠT ĐỘNG</span></div>
            <div class="property-body">
                <h3>Nhà trọ An Phú</h3>
                <p><i class="bi bi-geo-alt"></i> 72 Võ Văn Ngân, Thủ Đức</p>
                <div class="property-meta"><b>6 phòng</b><span>4 đang thuê</span></div>
                <button type="button" class="owner-btn light full-btn property-manage">
                    <i class="bi bi-gear"></i> Quản lý nhà trọ
                </button>
            </div>
        </article>

        <article class="property-card" data-name="Nhà trọ Phú Gia" data-status="active">
            <div class="property-cover four"><span><i class="bi bi-check-circle-fill"></i> ĐANG HOẠT ĐỘNG</span></div>
            <div class="property-body">
                <h3>Nhà trọ Phú Gia</h3>
                <p><i class="bi bi-geo-alt"></i> 16 Phạm Văn Đồng, Thủ Đức</p>
                <div class="property-meta"><b>8 phòng</b><span>6 đang thuê</span></div>
                <button type="button" class="owner-btn light full-btn property-manage">
                    <i class="bi bi-gear"></i> Quản lý nhà trọ
                </button>
            </div>
        </article>
    </div>

    <div id="propertyEmpty" class="owner-empty" hidden>
        <i class="bi bi-buildings"></i>
        <strong>Không tìm thấy nhà trọ</strong>
        <span>Thử đổi từ khóa hoặc trạng thái lọc.</span>
    </div>
</section>

<!-- Modal thêm nhà trọ -->
<div class="owner-modal" id="propertyModal" aria-hidden="true">
    <div class="owner-modal-backdrop" data-close-property></div>
    <div class="owner-modal-dialog" role="dialog" aria-modal="true" aria-labelledby="propertyModalTitle">
        <div class="owner-modal-head">
            <div>
                <span class="modal-kicker"><i class="bi bi-house-add"></i> QUẢN LÝ NHÀ TRỌ</span>
                <h2 id="propertyModalTitle">Thêm nhà trọ</h2>
                <p>Nhập thông tin cơ bản để tạo nhà trọ mới.</p>
            </div>
            <button type="button" class="modal-close" data-close-property aria-label="Đóng"><i class="bi bi-x-lg"></i></button>
        </div>

        <form id="propertyForm" class="owner-form">
            <div class="form-grid">
                <label>
                    <span>Tên nhà trọ <b>*</b></span>
                    <div class="form-control-icon"><i class="bi bi-buildings"></i><input name="name" required maxlength="255" placeholder="VD: Nhà trọ Hoa Mai"></div>
                </label>
                <label>
                    <span>Trạng thái</span>
                    <select name="status" class="owner-input">
                        <option value="active">Đang hoạt động</option>
                        <option value="paused">Tạm ngưng</option>
                    </select>
                </label>
                <label class="full">
                    <span>Địa chỉ <b>*</b></span>
                    <div class="form-control-icon"><i class="bi bi-geo-alt"></i><input name="address" required maxlength="500" placeholder="VD: 25 Nguyễn Trãi, Quận 5"></div>
                </label>
                <label>
                    <span>Số phòng mẫu</span>
                    <div class="form-control-icon"><i class="bi bi-door-open"></i><input name="rooms" type="number" min="0" max="999" value="0"></div>
                </label>
                <label>
                    <span>Đánh giá</span>
                    <div class="form-control-icon"><i class="bi bi-star"></i><input name="rating" type="number" min="0" max="5" step="0.1" value="5.0"></div>
                </label>
                <label class="full">
                    <span>Mô tả</span>
                    <textarea name="description" maxlength="1000" placeholder="Mô tả ngắn về nhà trọ..."></textarea>
                </label>
            </div>
            <div class="owner-form-note"><i class="bi bi-info-circle"></i> Dữ liệu mẫu được lưu trên trình duyệt để bạn có thể kiểm tra giao diện và thao tác thêm nhanh.</div>
            <div class="owner-modal-actions">
                <button type="button" class="owner-btn light" data-close-property>Hủy</button>
                <button type="submit" class="owner-btn primary"><i class="bi bi-check-lg"></i> Tạo nhà trọ</button>
            </div>
        </form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/chu-tro-management.js"></script>
<%@ include file="includes/footer.jspf" %>
