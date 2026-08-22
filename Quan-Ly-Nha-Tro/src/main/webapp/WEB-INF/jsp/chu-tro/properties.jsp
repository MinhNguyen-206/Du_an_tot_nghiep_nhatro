<%@ page contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib
        prefix="c"
        uri="jakarta.tags.core" %>

<%@ taglib
        prefix="fmt"
        uri="jakarta.tags.fmt" %>

<%@ include file="includes/header.jspf" %>

<style>

    .property-page {
        padding: 28px;
    }

    .property-head {
        display: flex;
        justify-content: space-between;
        align-items: center;
        gap: 20px;
        margin-bottom: 24px;
    }

    .property-head h1 {
        margin: 0;
        font-size: 28px;
        color: #ffffff;
    }

    .property-head p {
        margin-top: 8px;
        color: #8290ae;
    }

    .btn-add-property {
        border: 0;
        border-radius: 10px;
        padding: 12px 18px;
        color: white;
        background: #7657ff;
        cursor: pointer;
        font-weight: 700;
    }

    .property-stats {
        display: grid;
        grid-template-columns:
                repeat(3, minmax(0, 1fr));

        gap: 14px;

        margin-bottom: 18px;
    }

    .property-stat {
        border: 1px solid #24304a;
        border-radius: 15px;
        padding: 18px;
        background: #111a2b;
    }

    .property-stat span {
        display: block;
        color: #8190ad;
        font-size: 13px;
    }

    .property-stat strong {
        display: block;
        color: #ffffff;
        font-size: 28px;
        margin-top: 5px;
    }

    .property-list {
        display: grid;

        grid-template-columns:
                repeat(
                        auto-fill,
                        minmax(330px, 1fr)
                );

        gap: 16px;
    }

    .property-card {
        overflow: hidden;
        border: 1px solid #26334c;
        border-radius: 15px;
        background: #111a2b;
    }

    .property-image {
        height: 190px;
        position: relative;
        overflow: hidden;
        background: #5551e6;
    }

    .property-image img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        display: block;
    }

    .property-image-placeholder {
        height: 100%;

        display: flex;
        align-items: center;
        justify-content: center;

        color: #a9a4ff;
        font-size: 60px;

        background:
                linear-gradient(
                        135deg,
                        #5d58eb,
                        #4e4ad2
                );
    }

    .property-status {
        position: absolute;

        top: 12px;
        left: 12px;

        padding: 6px 10px;

        border-radius: 7px;

        background: #09251a;
        color: #31e58c;

        font-size: 11px;
        font-weight: 800;
    }

    .property-body {
        padding: 14px;
    }

    .property-name {
        color: #ffffff;
        font-size: 16px;
        font-weight: 800;
    }

    .property-address {
        margin-top: 6px;

        color: #70809e;
        font-size: 12px;

        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    .property-info {
        display: grid;

        grid-template-columns:
                repeat(2, 1fr);

        gap: 8px;

        margin-top: 14px;
    }

    .property-info-item {
        padding: 10px;

        border-radius: 8px;

        background: #182238;
    }

    .property-info-item small {
        display: block;
        color: #73829f;
        font-size: 11px;
    }

    .property-info-item strong {
        display: block;
        color: #ffffff;
        margin-top: 4px;
        font-size: 13px;
    }

    .property-rating {
        margin-top: 10px;

        color: #dce3f4;
        font-size: 13px;
    }

    .property-actions {
        display: grid;

        grid-template-columns:
                1fr 46px 46px;

        gap: 7px;

        margin-top: 14px;
    }

    .property-actions a,
    .property-actions button {

        height: 38px;

        border: 1px solid #33415c;

        border-radius: 8px;

        background: #1d293d;

        color: #dce5f7;

        cursor: pointer;

        display: flex;

        align-items: center;

        justify-content: center;

        text-decoration: none;
    }

    .property-actions a:hover,
    .property-actions button:hover {
        background: #283750;
    }

    .property-actions .delete-btn {
        color: #ff7c86;
    }

    /* MODAL */

    .property-modal {
        position: fixed;

        inset: 0;

        z-index: 9999;

        display: none;

        align-items: center;

        justify-content: center;

        padding: 20px;

        background:
                rgba(3, 7, 18, .78);
    }

    .property-modal.show {
        display: flex;
    }

    .property-modal-box {
        width: min(620px, 100%);

        max-height: 90vh;

        overflow-y: auto;

        padding: 24px;

        border: 1px solid #2b3852;

        border-radius: 18px;

        background: #111a2b;
    }

    .modal-title {
        display: flex;

        align-items: center;

        justify-content: space-between;

        margin-bottom: 20px;
    }

    .modal-title h2 {
        margin: 0;

        color: white;
    }

    .modal-close {
        width: 36px;
        height: 36px;

        border: 0;

        border-radius: 8px;

        background: #202c43;

        color: white;

        cursor: pointer;
    }

    .form-group {
        margin-bottom: 15px;
    }

    .form-group label {
        display: block;

        margin-bottom: 7px;

        color: #dbe4f4;

        font-size: 13px;

        font-weight: 700;
    }

    .form-group input,
    .form-group select,
    .form-group textarea {

        width: 100%;

        box-sizing: border-box;

        border: 1px solid #2c3a55;

        border-radius: 9px;

        outline: none;

        padding: 11px 12px;

        background: #0d1525;

        color: #ffffff;
    }

    .form-group textarea {
        min-height: 90px;
        resize: vertical;
    }

    .form-group input:focus,
    .form-group select:focus,
    .form-group textarea:focus {
        border-color: #7657ff;
    }

    .image-helper {
        margin-top: 7px;

        color: #74839f;

        font-size: 12px;

        line-height: 1.6;
    }

    .image-helper a {
        color: #8b73ff;

        font-weight: 700;

        text-decoration: none;
    }

    .image-preview {
        display: none;

        margin-top: 10px;

        border-radius: 10px;

        overflow: hidden;
    }

    .image-preview.show {
        display: block;
    }

    .image-preview img {
        width: 100%;

        max-height: 180px;

        object-fit: cover;
    }

    .modal-actions {
        display: flex;

        justify-content: flex-end;

        gap: 10px;

        margin-top: 20px;
    }

    .modal-actions button {
        padding: 11px 18px;

        border: 0;

        border-radius: 9px;

        cursor: pointer;

        font-weight: 700;
    }

    .btn-cancel {
        background: #27344b;
        color: white;
    }

    .btn-save {
        background: #7657ff;
        color: white;
    }

    .empty-property {
        padding: 70px 20px;

        text-align: center;

        color: #7887a3;
    }

    @media (max-width: 800px) {

        .property-stats {
            grid-template-columns: 1fr;
        }

        .property-head {
            align-items: flex-start;

            flex-direction: column;
        }

    }

</style>


<div class="property-page">

    <!-- HEADER -->

    <div class="property-head">

        <div>

            <div style="
                    color:#8371ff;
                    font-size:12px;
                    font-weight:800;
                ">
                QUẢN LÝ BẤT ĐỘNG SẢN
            </div>

            <h1>
                Nhà trọ của tôi
            </h1>

            <p>
                Thêm, sửa, xóa nhà trọ trực tiếp trong SQL Server.
                Ảnh được lưu bằng đường link.
            </p>

        </div>

        <button
                type="button"
                class="btn-add-property"
                onclick="openAddModal()">

            <i class="bi bi-plus-lg"></i>

            Thêm nhà trọ

        </button>

    </div>


    <!-- STATS -->

    <div class="property-stats">

        <div class="property-stat">

            <span>
                Tổng nhà trọ
            </span>

            <strong>
                ${propertyCount}
            </strong>

        </div>


        <div class="property-stat">

            <span>
                Tổng phòng
            </span>

            <strong>
                ${propertyRoomCount}
            </strong>

        </div>


        <div class="property-stat">

            <span>
                Phòng trống
            </span>

            <strong>
                ${propertyAvailableCount}
            </strong>

        </div>

    </div>


    <!-- LIST -->

    <div class="property-list">

        <c:choose>

            <c:when
                    test="${not empty propertyCards}">

                <c:forEach
                        var="property"
                        items="${propertyCards}">

                    <div
                            class="property-card"
                            data-name="${property.name}">

                        <!-- IMAGE -->

                        <div class="property-image">

                            <span
                                    class="property-status">

                                <i class="bi bi-check-circle-fill"></i>

                                ĐANG HOẠT ĐỘNG

                            </span>


                            <c:choose>

                                <c:when
                                        test="${not empty property.image}">

                                    <img
                                            src="${property.image}"
                                            alt="${property.name}"
                                            onerror="
                                                this.style.display='none';
                                                this.nextElementSibling.style.display='flex';
                                            ">

                                    <div
                                            class="property-image-placeholder"
                                            style="display:none;">

                                        <i
                                                class="bi bi-house-heart">
                                        </i>

                                    </div>

                                </c:when>

                                <c:otherwise>

                                    <div
                                            class="property-image-placeholder">

                                        <i
                                                class="bi bi-house-heart">
                                        </i>

                                    </div>

                                </c:otherwise>

                            </c:choose>

                        </div>


                        <!-- BODY -->

                        <div class="property-body">

                            <div class="property-name">

                                ${property.name}

                            </div>


                            <div class="property-address">

                                <i class="bi bi-geo-alt"></i>

                                ${property.address}

                            </div>


                            <!-- GIÁ + LOẠI -->

                            <div class="property-info">

                                <div
                                        class="property-info-item">

                                    <small>
                                        Giá phòng
                                    </small>

                                    <strong>

                                        <c:choose>

                                            <c:when
                                                    test="${not empty property.price}">

                                                <fmt:formatNumber
                                                        value="${property.price}"
                                                        type="number"
                                                        groupingUsed="true"
                                                />

                                                đ/tháng

                                            </c:when>

                                            <c:otherwise>

                                                Chưa cập nhật

                                            </c:otherwise>

                                        </c:choose>

                                    </strong>

                                </div>


                                <div
                                        class="property-info-item">

                                    <small>
                                        Loại phòng
                                    </small>

                                    <strong>

                                        <c:choose>

                                            <c:when
                                                    test="${not empty property.roomType}">

                                                ${property.roomType}

                                            </c:when>

                                            <c:otherwise>

                                                Chưa cập nhật

                                            </c:otherwise>

                                        </c:choose>

                                    </strong>

                                </div>

                            </div>


                            <!-- ROOM -->

                            <div
                                    style="
                                        margin-top:10px;
                                        color:#b6c2d9;
                                        font-size:13px;
                                    ">

                                <strong
                                        style="color:white;">

                                    ${property.roomCount}
                                    phòng

                                </strong>

                                <span
                                        style="
                                            float:right;
                                            color:#70809e;
                                        ">

                                    ${property.occupiedCount}
                                    đang thuê

                                </span>

                            </div>


                            <!-- RATING -->

                            <div
                                    class="property-rating">

                                ${property.availableCount}
                                phòng trống

                                • ★

                                ${property.rating}

                            </div>


                            <!-- ACTION -->

                            <div class="property-actions">

                                <a
                                        href="${pageContext.request.contextPath}/chu-tro/rooms">

                                    <i class="bi bi-gear"></i>

                                    &nbsp;

                                    Quản lý phòng

                                </a>


                                <button
                                        type="button"
                                        title="Sửa"
                                        onclick="
                                            openEditModal(
                                                ${property.id},
                                                '${property.name}',
                                                '${property.address}',
                                                '${property.rating}',
                                                '${property.price}',
                                                '${property.roomType}',
                                                '${property.description}',
                                                '${property.image}'
                                            )
                                        ">

                                    <i
                                            class="bi bi-pencil-square">
                                    </i>

                                </button>


                                <button
                                        type="button"
                                        class="delete-btn"
                                        title="Xóa"
                                        onclick="
                                            deleteProperty(
                                                ${property.id},
                                                '${property.name}'
                                            )
                                        ">

                                    <i
                                            class="bi bi-trash3">
                                    </i>

                                </button>

                            </div>

                        </div>

                    </div>

                </c:forEach>

            </c:when>


            <c:otherwise>

                <div class="empty-property">

                    <i
                            class="bi bi-buildings"
                            style="
                                font-size:50px;
                                color:#6658ff;
                            ">
                    </i>

                    <h3
                            style="color:white;">

                        Chưa có nhà trọ

                    </h3>

                    <p>

                        Bấm "Thêm nhà trọ" để bắt đầu.

                    </p>

                </div>

            </c:otherwise>

        </c:choose>

    </div>

</div>


<!-- ========================================================= -->
<!-- MODAL -->
<!-- ========================================================= -->

<div
        id="propertyModal"
        class="property-modal">

    <div class="property-modal-box">

        <div class="modal-title">

            <h2 id="modalTitle">
                Thêm nhà trọ
            </h2>

            <button
                    type="button"
                    class="modal-close"
                    onclick="closePropertyModal()">

                <i class="bi bi-x-lg"></i>

            </button>

        </div>


        <form
                id="propertyForm"
                onsubmit="saveProperty(event)">

            <input
                    type="hidden"
                    id="propertyId">


            <!-- TÊN -->

            <div class="form-group">

                <label>
                    Tên nhà trọ *
                </label>

                <input
                        type="text"
                        id="propertyName"
                        required
                        maxlength="200"
                        placeholder="Ví dụ: Nhà trọ Minh Phát">

            </div>


            <!-- ĐỊA CHỈ -->

            <div class="form-group">

                <label>
                    Địa chỉ *
                </label>

                <input
                        type="text"
                        id="propertyAddress"
                        required
                        maxlength="500"
                        placeholder="Nhập địa chỉ nhà trọ">

            </div>


            <!-- GIÁ -->

            <div class="form-group">

                <label>
                    Giá phòng *
                </label>

                <input
                        type="text"
                        id="propertyPrice"
                        required
                        inputmode="numeric"
                        autocomplete="off"
                        placeholder="Ví dụ: 35,00,000">

            </div>


            <!-- LOẠI -->

            <div class="form-group">

                <label>
                    Loại phòng *
                </label>

                <select
                        id="propertyRoomType"
                        required>

                    <option value="">
                        -- Chọn loại phòng --
                    </option>

                    <option value="Phòng đơn">
                        Phòng đơn
                    </option>

                    <option value="Phòng studio">
                        Phòng studio
                    </option>

                    <option value="Phòng đôi">
                        Phòng đôi
                    </option>

                    <option value="Căn hộ mini">
                        Căn hộ mini
                    </option>

                    <option value="Phòng có gác">
                        Phòng có gác
                    </option>

                </select>

            </div>


            <!-- SAO -->

            <div class="form-group">

                <label>
                    Đánh giá
                </label>

                <input
                        type="number"
                        id="propertyRating"
                        min="0"
                        max="5"
                        step="0.1"
                        value="0">

            </div>


            <!-- ẢNH -->

            <div class="form-group">

                <label>
                    Link ảnh
                </label>

                <input
                        type="url"
                        id="propertyImage"
                        maxlength="2000"
                        placeholder="Dán link ảnh trực tiếp vào đây">

                <div class="image-helper">

                    <i class="bi bi-image"></i>

                    Không có ảnh?

                    <a
                            href="https://phototourl.com/vi"
                            target="_blank"
                            rel="noopener noreferrer">

                        Lấy link ảnh tại Photo to URL

                    </a>

                    <br>

                    Link:

                    <a
                            href="https://phototourl.com/vi"
                            target="_blank"
                            rel="noopener noreferrer">

                        https://phototourl.com/vi

                    </a>

                </div>


                <div
                        id="imagePreview"
                        class="image-preview">

                    <img
                            id="previewImage"
                            src=""
                            alt="Preview">

                </div>

            </div>


            <!-- MÔ TẢ -->

            <div class="form-group">

                <label>
                    Mô tả
                </label>

                <textarea
                        id="propertyDescription"
                        placeholder="Nhập mô tả nhà trọ..."></textarea>

            </div>


            <!-- BUTTON -->

            <div class="modal-actions">

                <button
                        type="button"
                        class="btn-cancel"
                        onclick="closePropertyModal()">

                    Hủy

                </button>

                <button
                        type="submit"
                        class="btn-save"
                        id="saveButton">

                    Lưu nhà trọ

                </button>

            </div>

        </form>

    </div>

</div>


<script>

    const contextPath =
        '${pageContext.request.contextPath}';

    let editingId = null;


    // =====================================================
    // MỞ THÊM
    // =====================================================

    function openAddModal() {

        editingId = null;

        document.getElementById(
            'modalTitle'
        ).textContent = 'Thêm nhà trọ';

        document.getElementById(
            'saveButton'
        ).textContent = 'Thêm nhà trọ';

        document.getElementById(
            'propertyForm'
        ).reset();

        document.getElementById(
            'propertyRating'
        ).value = '0';

        document.getElementById(
            'propertyId'
        ).value = '';

        hidePreview();

        document.getElementById(
            'propertyModal'
        ).classList.add('show');
    }


    // =====================================================
    // ĐỊNH DẠNG GIÁ PHÒNG
    // Hiển thị theo kiểu: 100000 -> 1,00,000
    // Khi gửi API vẫn chuyển về số nguyên: 100000
    // =====================================================

    function formatPrice(value) {

        const digits = String(value ?? '')
            .replace(/\D/g, '');

        if (!digits) {
            return '';
        }

        return Number(digits).toLocaleString('en-IN');
    }


    function parsePrice(value) {

        const digits = String(value ?? '')
            .replace(/\D/g, '');

        return digits ? Number(digits) : 0;
    }


    document.getElementById('propertyPrice').addEventListener(
        'input',
        function () {

            const cursorEnd = this.selectionStart === this.value.length;
            this.value = formatPrice(this.value);

            if (cursorEnd) {
                this.setSelectionRange(
                    this.value.length,
                    this.value.length
                );
            }
        }
    );


    // =====================================================
    // MỞ SỬA
    // =====================================================

    function openEditModal(
        id,
        name,
        address,
        rating,
        price,
        roomType,
        description,
        image
    ) {

        editingId = id;

        document.getElementById(
            'modalTitle'
        ).textContent = 'Sửa nhà trọ';

        document.getElementById(
            'saveButton'
        ).textContent = 'Lưu thay đổi';

        document.getElementById(
            'propertyId'
        ).value = id;

        document.getElementById(
            'propertyName'
        ).value = name || '';

        document.getElementById(
            'propertyAddress'
        ).value = address || '';

        document.getElementById(
            'propertyRating'
        ).value = rating || 0;

        document.getElementById(
            'propertyPrice'
        ).value = formatPrice(price);

        document.getElementById(
            'propertyRoomType'
        ).value = roomType || '';

        document.getElementById(
            'propertyDescription'
        ).value = description || '';

        document.getElementById(
            'propertyImage'
        ).value = image || '';

        previewImage(image);

        document.getElementById(
            'propertyModal'
        ).classList.add('show');
    }


    // =====================================================
    // ĐÓNG
    // =====================================================

    function closePropertyModal() {

        document.getElementById(
            'propertyModal'
        ).classList.remove('show');
    }


    // =====================================================
    // PREVIEW ẢNH
    // =====================================================

    function previewImage(url) {

        const preview =
            document.getElementById(
                'imagePreview'
            );

        const image =
            document.getElementById(
                'previewImage'
            );

        if (!url || !url.trim()) {

            hidePreview();

            return;
        }

        image.src = url;

        preview.classList.add('show');
    }


    function hidePreview() {

        document.getElementById(
            'imagePreview'
        ).classList.remove('show');

        document.getElementById(
            'previewImage'
        ).src = '';
    }


    document.getElementById(
        'propertyImage'
    ).addEventListener(
        'input',
        function () {

            previewImage(
                this.value
            );

        }
    );


    // =====================================================
    // LƯU
    // =====================================================

    async function saveProperty(event) {

        event.preventDefault();

        const data = {

            tenNhaTro:
                document.getElementById(
                    'propertyName'
                ).value.trim(),

            diaChi:
                document.getElementById(
                    'propertyAddress'
                ).value.trim(),

            giaPhong:
                parsePrice(
                    document.getElementById(
                        'propertyPrice'
                    ).value
                ),

            loaiPhong:
                document.getElementById(
                    'propertyRoomType'
                ).value,

            soSao:
                Number(
                    document.getElementById(
                        'propertyRating'
                    ).value
                ) || 0,

            moTa:
                document.getElementById(
                    'propertyDescription'
                ).value.trim(),

            hinhAnh:
                document.getElementById(
                    'propertyImage'
                ).value.trim()
        };


        if (!data.tenNhaTro) {

            alert(
                'Vui lòng nhập tên nhà trọ.'
            );

            return;
        }


        if (!data.diaChi) {

            alert(
                'Vui lòng nhập địa chỉ.'
            );

            return;
        }


        if (!data.giaPhong
                || data.giaPhong < 0) {

            alert(
                'Vui lòng nhập giá phòng.'
            );

            return;
        }


        if (!data.loaiPhong) {

            alert(
                'Vui lòng chọn loại phòng.'
            );

            return;
        }


        const url =
            editingId

                ? contextPath
                  + '/api/nha-tro/mine/'
                  + editingId

                : contextPath
                  + '/api/nha-tro/mine';


        const method =
            editingId
                ? 'PUT'
                : 'POST';


        const button =
            document.getElementById(
                'saveButton'
            );

        button.disabled = true;

        button.textContent =
            'Đang lưu...';


        try {

            const response =
                await fetch(
                    url,
                    {
                        method: method,

                        headers: {
                            'Content-Type':
                                'application/json'
                        },

                        credentials: 'same-origin',

                        body:
                            JSON.stringify(data)
                    }
                );


            const rawResponse = await response.text();

            let result = {};

            try {
                result = rawResponse
                    ? JSON.parse(rawResponse)
                    : {};
            } catch (parseError) {
                result = { message: rawResponse };
            }

            if (!response.ok) {

                throw new Error(
                    result.message
                    || result.error
                    || ('Không thể lưu nhà trọ. HTTP ' + response.status)
                );
            }


            alert(
                editingId
                    ? 'Sửa nhà trọ thành công!'
                    : 'Thêm nhà trọ thành công!'
            );


            closePropertyModal();


            // Load lại dữ liệu từ SQL Server
            window.location.reload();


        } catch (error) {

            console.error(error);

            alert(
                error.message
                || 'Có lỗi xảy ra.'
            );

        } finally {

            button.disabled = false;

            button.textContent =
                editingId
                    ? 'Lưu thay đổi'
                    : 'Thêm nhà trọ';
        }
    }


    // =====================================================
    // XÓA
    // =====================================================

    async function deleteProperty(
        id,
        name
    ) {

        const confirmDelete =
            confirm(
                'Bạn có chắc muốn xóa nhà trọ "' +
                name +
                '" không?'
            );


        if (!confirmDelete) {
            return;
        }


        try {

            const response =
                await fetch(
                    contextPath
                    + '/api/nha-tro/mine/'
                    + id,
                    {
                        method: 'DELETE',

                        credentials:
                            'same-origin'
                    }
                );


            const result =
                await response.json()
                    .catch(() => ({}));


            if (!response.ok) {

                throw new Error(
                    result.message
                    || 'Không thể xóa nhà trọ.'
                );
            }


            alert(
                result.message
                || 'Xóa nhà trọ thành công!'
            );


            window.location.reload();


        } catch (error) {

            console.error(error);

            alert(
                error.message
                || 'Có lỗi khi xóa nhà trọ.'
            );
        }
    }


    // =====================================================
    // CLICK RA NGOÀI MODAL
    // =====================================================

    document.getElementById(
        'propertyModal'
    ).addEventListener(
        'click',
        function (event) {

            if (
                event.target === this
            ) {

                closePropertyModal();

            }

        }
    );


</script>


<%@ include file="includes/footer.jspf" %>