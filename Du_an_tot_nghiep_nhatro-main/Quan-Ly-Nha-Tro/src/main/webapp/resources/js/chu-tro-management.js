(function () {
    "use strict";

    const API = window.NHA_TRO_API || "/api/nha-tro";

    const $ = (selector, root = document) =>
        root.querySelector(selector);

    const $$ = (selector, root = document) =>
        Array.from(root.querySelectorAll(selector));


    // =========================================================
    // TOKEN
    // =========================================================

    function getToken() {
        return localStorage.getItem("token") || "";
    }


    function headers(json = false) {

        const result = {};

        if (json) {
            result["Content-Type"] = "application/json";
        }

        const token = getToken();

        if (token) {
            result["Authorization"] = "Bearer " + token;
        }

        return result;
    }


    // =========================================================
    // HTML SAFE
    // =========================================================

    function escapeHtml(value) {

        return String(value ?? "")
            .replace(/[&<>"']/g, ch => ({
                "&": "&amp;",
                "<": "&lt;",
                ">": "&gt;",
                '"': "&quot;",
                "'": "&#039;"
            }[ch]));
    }


    // =========================================================
    // MODAL
    // =========================================================

    function openModal() {

        const modal = $("#propertyModal");

        if (!modal) return;

        modal.classList.add("show");
        modal.setAttribute("aria-hidden", "false");
        document.body.classList.add("modal-open");

        const input = $("#propertyName");

        if (input) {
            setTimeout(() => input.focus(), 80);
        }
    }


    function closeModal() {

        const modal = $("#propertyModal");

        if (!modal) return;

        modal.classList.remove("show");
        modal.setAttribute("aria-hidden", "true");

        if (!$$(".owner-modal.show").length) {
            document.body.classList.remove("modal-open");
        }
    }


    // =========================================================
    // RESET FORM
    // =========================================================

    function resetFormForCreate() {

        const form = $("#propertyForm");

        if (!form) return;

        form.reset();

        $("#propertyId").value = "";
        $("#propertyRating").value = "5.0";

        $("#propertyModalTitle").textContent =
            "Thêm nhà trọ";

        $("#propertyModalDescription").textContent =
            "Nhập thông tin để tạo nhà trọ mới.";

        $("#propertySubmitBtn span").textContent =
            "Thêm nhà trọ";

        $("#propertySubmitBtn i").className =
            "bi bi-check-lg";

        hideImagePreview();
    }


    // =========================================================
    // EDIT FORM
    // =========================================================

    function fillFormForEdit(card) {

        const id = card.dataset.id;

        if (!id) {
            showToast(
                "Không tìm thấy mã nhà trọ.",
                "error"
            );
            return;
        }


        const name =
            card.querySelector("h3")?.textContent.trim() || "";


        const address =
            card.querySelector("p")?.textContent
                .replace(/\s+/g, " ")
                .replace("📍", "")
                .trim() || "";


        const ratingText =
            card.querySelector("small")?.textContent || "";


        const ratingMatch =
            ratingText.match(/★\s*([0-9]+(?:\.[0-9]+)?)/);


        const rating =
            ratingMatch
                ? ratingMatch[1]
                : "5.0";


        const image =
            card.querySelector(".property-image")?.getAttribute("src")
            || "";


        $("#propertyId").value = id;
        $("#propertyName").value = name;
        $("#propertyAddress").value = address;
        $("#propertyRating").value = rating;
        $("#propertyDescription").value = "";

        $("#propertyImage").value = image;


        $("#propertyModalTitle").textContent =
            "Sửa nhà trọ";

        $("#propertyModalDescription").textContent =
            "Cập nhật thông tin nhà trọ và lưu trực tiếp vào SQL Server.";

        $("#propertySubmitBtn span").textContent =
            "Lưu thay đổi";

        $("#propertySubmitBtn i").className =
            "bi bi-save";


        if (image) {
            showImagePreview(image);
        } else {
            hideImagePreview();
        }


        /*
         * Lấy mô tả đầy đủ từ API vì card không hiển thị mô tả.
         */
        loadPropertyDetailForEdit(id);

        openModal();
    }


    async function loadPropertyDetailForEdit(id) {

        try {

            const response =
                await fetch(
                    API + "/" + encodeURIComponent(id)
                );


            if (!response.ok) {
                return;
            }


            const item =
                await response.json();


            if ($("#propertyId").value !== String(id)) {
                return;
            }


            $("#propertyDescription").value =
                item.moTa || "";


            $("#propertyImage").value =
                item.hinhAnh || "";


            if (item.hinhAnh) {
                showImagePreview(item.hinhAnh);
            }

        } catch (error) {

            console.warn(
                "Không lấy được chi tiết nhà trọ:",
                error
            );
        }
    }


    // =========================================================
    // IMAGE PREVIEW
    // =========================================================

    function showImagePreview(url) {

        const preview =
            $("#propertyImagePreview");

        if (!preview || !url) return;

        preview.src = url;
        preview.classList.add("show");

        preview.onerror = function () {

            preview.classList.remove("show");

            showToast(
                "Link ảnh không hợp lệ hoặc ảnh không cho phép nhúng.",
                "error"
            );
        };
    }


    function hideImagePreview() {

        const preview =
            $("#propertyImagePreview");

        if (!preview) return;

        preview.removeAttribute("src");
        preview.classList.remove("show");
    }


    // =========================================================
    // TOAST
    // =========================================================

    function showToast(
        message,
        type = "success"
    ) {

        let toast =
            $("#ownerToast");


        if (!toast) {

            toast =
                document.createElement("div");

            toast.id =
                "ownerToast";

            toast.className =
                "owner-toast";

            document.body.appendChild(toast);
        }


        toast.innerHTML =
            '<i></i><span></span>';


        const icon =
            toast.querySelector("i");


        icon.className =
            type === "error"
                ? "bi bi-exclamation-circle-fill"
                : "bi bi-check-circle-fill";


        toast.querySelector("span")
            .textContent = message;


        toast.classList.add("show");


        clearTimeout(
            window.__ownerToastTimer
        );


        window.__ownerToastTimer =
            setTimeout(
                () => toast.classList.remove("show"),
                3000
            );
    }


    // =========================================================
    // API ERROR
    // =========================================================

    async function readError(response) {

        try {

            const data =
                await response.json();

            return data.message
                || data.error
                || "Có lỗi xảy ra.";

        } catch (e) {

            return "HTTP " + response.status;
        }
    }


    // =========================================================
    // FILTER
    // =========================================================

    function initFilter() {

        const grid =
            $("#propertyGrid");

        if (!grid) return;


        const search =
            $("#propertySearch");

        const status =
            $("#propertyStatusFilter");

        const empty =
            $("#propertyEmpty");


        function filter() {

            const keyword =
                (search?.value || "")
                    .trim()
                    .toLowerCase();


            const wantedStatus =
                status?.value || "all";


            let visible = 0;


            $$(".property-card", grid)
                .forEach(card => {

                    const name =
                        (card.dataset.name || "")
                            .toLowerCase();


                    const cardStatus =
                        card.dataset.status || "active";


                    const matchName =
                        name.includes(keyword);


                    const matchStatus =
                        wantedStatus === "all"
                        || cardStatus === wantedStatus;


                    const show =
                        matchName && matchStatus;


                    card.style.display =
                        show ? "" : "none";


                    if (show) {
                        visible++;
                    }
                });


            if (empty) {
                empty.hidden =
                    visible !== 0;
            }
        }


        search?.addEventListener(
            "input",
            filter
        );


        $("#propertyFilterBtn")
            ?.addEventListener(
                "click",
                filter
            );


        status?.addEventListener(
            "change",
            filter
        );


        filter();
    }


    // =========================================================
    // ADD
    // =========================================================

    async function createProperty(form) {

        const data =
            new FormData(form);


        const body = {

            tenNhaTro:
                String(
                    data.get("name") || ""
                ).trim(),

            diaChi:
                String(
                    data.get("address") || ""
                ).trim(),

            soSao:
                data.get("rating")
                    ? Number(data.get("rating"))
                    : null,

            moTa:
                String(
                    data.get("description") || ""
                ).trim(),

            hinhAnh:
                String(
                    data.get("imageUrl") || ""
                ).trim()
        };


        const response =
            await fetch(
                API + "/mine",
                {
                    method: "POST",
                    headers: headers(true),
                    body: JSON.stringify(body)
                }
            );


        if (!response.ok) {

            throw new Error(
                await readError(response)
            );
        }


        return response.json();
    }


    // =========================================================
    // UPDATE
    // =========================================================

    async function updateProperty(
        id,
        form
    ) {

        const data =
            new FormData(form);


        const body = {

            tenNhaTro:
                String(
                    data.get("name") || ""
                ).trim(),

            diaChi:
                String(
                    data.get("address") || ""
                ).trim(),

            soSao:
                data.get("rating")
                    ? Number(data.get("rating"))
                    : null,

            moTa:
                String(
                    data.get("description") || ""
                ).trim(),

            hinhAnh:
                String(
                    data.get("imageUrl") || ""
                ).trim()
        };


        const response =
            await fetch(
                API
                + "/mine/"
                + encodeURIComponent(id),
                {
                    method: "PUT",
                    headers: headers(true),
                    body: JSON.stringify(body)
                }
            );


        if (!response.ok) {

            throw new Error(
                await readError(response)
            );
        }


        return response.json();
    }


    // =========================================================
    // DELETE
    // =========================================================

    async function deleteProperty(id) {

        const response =
            await fetch(
                API
                + "/mine/"
                + encodeURIComponent(id),
                {
                    method: "DELETE",
                    headers: headers()
                }
            );


        if (!response.ok) {

            throw new Error(
                await readError(response)
            );
        }
    }


    // =========================================================
    // SUBMIT FORM
    // =========================================================

    function initForm() {

        const form =
            $("#propertyForm");

        if (!form) return;


        form.addEventListener(
            "submit",
            async function (event) {

                event.preventDefault();


                const id =
                    $("#propertyId").value.trim();


                const button =
                    $("#propertySubmitBtn");


                const oldText =
                    button.querySelector("span")
                        .textContent;


                button.disabled = true;


                button.querySelector("span")
                    .textContent =
                    id
                        ? "Đang lưu..."
                        : "Đang thêm...";


                try {

                    if (!getToken()) {

                        throw new Error(
                            "Bạn chưa đăng nhập hoặc token đã hết hạn. Hãy đăng nhập lại."
                        );
                    }


                    if (id) {

                        await updateProperty(
                            id,
                            form
                        );

                        showToast(
                            "Đã sửa nhà trọ trong database."
                        );

                    } else {

                        await createProperty(
                            form
                        );

                        showToast(
                            "Đã thêm nhà trọ vào database."
                        );
                    }


                    closeModal();


                    /*
                     * Reload để số phòng, tổng nhà trọ
                     * và danh sách lấy lại hoàn toàn từ SQL Server.
                     */
                    setTimeout(
                        () => window.location.reload(),
                        500
                    );

                } catch (error) {

                    console.error(error);

                    showToast(
                        error.message
                        || "Không thể lưu nhà trọ.",
                        "error"
                    );

                    button.disabled = false;

                    button.querySelector("span")
                        .textContent =
                        oldText;
                }
            }
        );
    }


    // =========================================================
    // GRID ACTIONS
    // =========================================================

    function initGridActions() {

        const grid =
            $("#propertyGrid");

        if (!grid) return;


        grid.addEventListener(
            "click",
            async function (event) {

                const manageButton =
                    event.target.closest(
                        ".property-manage"
                    );


                if (manageButton) {

                    const property =
                        manageButton.dataset.property
                        || "";


                    window.location.href =
                        "rooms?property="
                        + encodeURIComponent(
                            property
                        );

                    return;
                }


                const editButton =
                    event.target.closest(
                        ".property-edit"
                    );


                if (editButton) {

                    const card =
                        editButton.closest(
                            ".property-card"
                        );


                    if (card) {
                        fillFormForEdit(card);
                    }

                    return;
                }


                const deleteButton =
                    event.target.closest(
                        ".property-delete"
                    );


                if (deleteButton) {

                    const card =
                        deleteButton.closest(
                            ".property-card"
                        );


                    if (!card) return;


                    const id =
                        card.dataset.id;


                    const name =
                        card.querySelector("h3")
                            ?.textContent
                            .trim()
                        || "nhà trọ này";


                    if (!id) {

                        showToast(
                            "Không tìm thấy mã nhà trọ.",
                            "error"
                        );

                        return;
                    }


                    const confirmed =
                        window.confirm(
                            "Bạn có chắc muốn xóa \""
                            + name
                            + "\" không?\n\n"
                            + "Nếu nhà trọ còn phòng, hệ thống sẽ không cho xóa để tránh mất dữ liệu."
                        );


                    if (!confirmed) {
                        return;
                    }


                    deleteButton.disabled = true;


                    try {

                        if (!getToken()) {

                            throw new Error(
                                "Bạn chưa đăng nhập hoặc token đã hết hạn. Hãy đăng nhập lại."
                            );
                        }


                        await deleteProperty(id);


                        showToast(
                            "Đã xóa nhà trọ khỏi database."
                        );


                        setTimeout(
                            () => window.location.reload(),
                            500
                        );

                    } catch (error) {

                        console.error(error);

                        showToast(
                            error.message
                            || "Không thể xóa nhà trọ.",
                            "error"
                        );

                        deleteButton.disabled =
                            false;
                    }
                }
            }
        );
    }


    // =========================================================
    // IMAGE INPUT
    // =========================================================

    function initImagePreview() {

        const input =
            $("#propertyImage");

        if (!input) return;


        input.addEventListener(
            "input",
            function () {

                const url =
                    input.value.trim();


                if (!url) {

                    hideImagePreview();
                    return;
                }


                showImagePreview(url);
            }
        );
    }


    // =========================================================
    // OPEN / CLOSE
    // =========================================================

    function initModal() {

        $("#openPropertyModal")
            ?.addEventListener(
                "click",
                function () {

                    resetFormForCreate();
                    openModal();
                }
            );


        $$("[data-close-property]")
            .forEach(button => {

                button.addEventListener(
                    "click",
                    closeModal
                );
            });


        document.addEventListener(
            "keydown",
            function (event) {

                if (event.key === "Escape") {
                    closeModal();
                }
            }
        );
    }


    // =========================================================
    // START
    // =========================================================

    document.addEventListener(
        "DOMContentLoaded",
        function () {

            initFilter();
            initGridActions();
            initForm();
            initImagePreview();
            initModal();
        }
    );

})();
