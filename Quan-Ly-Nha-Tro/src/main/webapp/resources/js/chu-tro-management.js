(function () {
    "use strict";

    const storageKeys = {
        properties: "roomConnect.demo.properties.v1",
        rooms: "roomConnect.demo.rooms.v1"
    };

    const $ = (selector, root = document) => root.querySelector(selector);
    const $$ = (selector, root = document) => Array.from(root.querySelectorAll(selector));

    function escapeHtml(value) {
        return String(value ?? "").replace(/[&<>"']/g, ch => ({
            "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#039;"
        }[ch]));
    }

    function formatMoney(value) {
        return new Intl.NumberFormat("vi-VN").format(Number(value) || 0) + "đ";
    }

    function readStorage(key) {
        try {
            return JSON.parse(localStorage.getItem(key) || "[]");
        } catch (e) {
            return [];
        }
    }

    function writeStorage(key, data) {
        localStorage.setItem(key, JSON.stringify(data));
    }

    function openModal(id) {
        const modal = $("#" + id);
        if (!modal) return;
        modal.classList.add("show");
        modal.setAttribute("aria-hidden", "false");
        document.body.classList.add("modal-open");
        const input = modal.querySelector("input");
        if (input) setTimeout(() => input.focus(), 80);
    }

    function closeModal(id) {
        const modal = $("#" + id);
        if (!modal) return;
        modal.classList.remove("show");
        modal.setAttribute("aria-hidden", "true");
        if (!$$(".owner-modal.show").length) document.body.classList.remove("modal-open");
    }

    function bindModal(openId, modalId, closeAttr) {
        const openButton = $("#" + openId);
        if (openButton) openButton.addEventListener("click", () => openModal(modalId));

        $$("[" + closeAttr + "]").forEach(el => {
            el.addEventListener("click", () => closeModal(modalId));
        });
    }

    document.addEventListener("keydown", e => {
        if (e.key === "Escape") $$(".owner-modal.show").forEach(m => closeModal(m.id));
    });

    // =========================
    // NHÀ TRỌ
    // =========================
    function propertyCard(item) {
        const cover = ["one", "two", "three", "four"][Math.floor(Math.random() * 4)];
        const status = item.status === "paused" ? "paused" : "active";
        const statusText = status === "paused" ? "TẠM NGƯNG" : "ĐANG HOẠT ĐỘNG";
        const rooms = Number(item.rooms) || 0;
        const occupied = Number(item.occupied) || 0;

        return `
            <article class="property-card" data-name="${escapeHtml(item.name)}" data-status="${status}" data-demo-id="${escapeHtml(item.id)}">
                <div class="property-cover ${cover}">
                    <span class="${status === "paused" ? "paused" : ""}">
                        <i class="bi ${status === "paused" ? "bi-pause-circle-fill" : "bi-check-circle-fill"}"></i> ${statusText}
                    </span>
                </div>
                <div class="property-body">
                    <h3>${escapeHtml(item.name)}</h3>
                    <p><i class="bi bi-geo-alt"></i> ${escapeHtml(item.address)}</p>
                    <div class="property-meta"><b>${rooms} phòng</b><span>${occupied} đang thuê</span></div>
                    <button type="button" class="owner-btn light full-btn property-manage" data-property="${escapeHtml(item.name)}">
                        <i class="bi bi-gear"></i> Quản lý nhà trọ
                    </button>
                </div>
            </article>`;
    }

    function updatePropertyStats() {
        const added = readStorage(storageKeys.properties);
        const propertyCount = 4 + added.length;
        const extraRooms = added.reduce((sum, p) => sum + (Number(p.rooms) || 0), 0);
        const extraOccupied = added.reduce((sum, p) => sum + (Number(p.occupied) || 0), 0);

        const setText = (id, value) => { const el = $("#" + id); if (el) el.textContent = value; };
        setText("propertyCount", propertyCount);
        setText("propertyRoomCount", 24 + extraRooms);
        setText("propertyOccupiedCount", 18 + extraOccupied);
        setText("propertyAvailableCount", 6 + Math.max(0, extraRooms - extraOccupied));
    }

    function initProperties() {
        const grid = $("#propertyGrid");
        if (!grid) return;

        readStorage(storageKeys.properties).forEach(item => {
            grid.insertAdjacentHTML("beforeend", propertyCard(item));
        });

        const search = $("#propertySearch");
        const status = $("#propertyStatusFilter");
        const empty = $("#propertyEmpty");

        function filter() {
            const keyword = (search?.value || "").trim().toLowerCase();
            const wantedStatus = status?.value || "all";
            let visible = 0;

            $$(".property-card", grid).forEach(card => {
                const matchName = (card.dataset.name || "").toLowerCase().includes(keyword);
                const matchStatus = wantedStatus === "all" || card.dataset.status === wantedStatus;
                card.style.display = matchName && matchStatus ? "" : "none";
                if (matchName && matchStatus) visible++;
            });

            if (empty) empty.hidden = visible !== 0;
        }

        search?.addEventListener("input", filter);
        $("#propertyFilterBtn")?.addEventListener("click", filter);

        grid.addEventListener("click", e => {
            const button = e.target.closest(".property-manage");
            if (!button) return;
            const property = button.dataset.property || "";
            window.location.href = "rooms?property=" + encodeURIComponent(property);
        });

        bindModal("openPropertyModal", "propertyModal", "data-close-property");

        $("#propertyForm")?.addEventListener("submit", e => {
            e.preventDefault();
            const form = e.currentTarget;
            const data = new FormData(form);
            const rooms = Math.max(0, Number(data.get("rooms")) || 0);

            const item = {
                id: Date.now().toString(),
                name: data.get("name"),
                address: data.get("address"),
                status: data.get("status"),
                rooms: rooms,
                occupied: 0,
                rating: data.get("rating"),
                description: data.get("description")
            };

            const properties = readStorage(storageKeys.properties);
            properties.push(item);
            writeStorage(storageKeys.properties, properties);

            grid.insertAdjacentHTML("beforeend", propertyCard(item));
            updatePropertyStats();
            form.reset();
            closeModal("propertyModal");
            filter();
            showToast("Đã thêm nhà trọ " + item.name);
        });

        updatePropertyStats();
        filter();
    }

    // =========================
    // PHÒNG
    // =========================
    function roomCard(item) {
        const status = item.status || "available";
        const map = {
            occupied: { text: "Đang thuê", cls: "green", card: "occupied-card", icon: "bi-door-closed" },
            available: { text: "Còn trống", cls: "orange", card: "available-card", icon: "bi-door-open" },
            pending: { text: "Chờ ký HĐ", cls: "purple", card: "pending-card", icon: "bi-door-closed" }
        };
        const meta = map[status] || map.available;
        const detail = status === "occupied"
            ? escapeHtml(item.tenant || "Đang cập nhật")
            : status === "pending"
                ? escapeHtml(item.tenant || "Đang chờ ký hợp đồng")
                : "Sẵn sàng cho thuê";

        return `
            <article class="room-card ${meta.card}" data-name="${escapeHtml((item.name || "") + " " + (item.tenant || ""))}"
                     data-property="${escapeHtml(item.property)}" data-status="${status}" data-demo-id="${escapeHtml(item.id)}">
                <div class="room-top">
                    <b><i class="bi ${meta.icon}"></i> ${escapeHtml(item.name)}</b>
                    <span class="status-pill ${meta.cls}">${meta.text}</span>
                </div>
                <h3>${formatMoney(item.price)} <small>/ tháng</small></h3>
                <p><i class="bi ${status === "available" ? "bi-door-open" : "bi-person"}"></i> ${detail}</p>
                <div class="room-foot">
                    <span>${item.area ? escapeHtml(item.area) + " m²" : "Diện tích chưa cập nhật"}</span>
                    <span>${escapeHtml(item.type || "Phòng tiêu chuẩn")}</span>
                </div>
            </article>`;
    }

    function updateRoomStats() {
        const added = readStorage(storageKeys.rooms);
        let occupied = 18, available = 6;
        added.forEach(r => {
            if (r.status === "occupied") occupied++;
            else if (r.status === "available") available++;
        });

        const setText = (id, value) => { const el = $("#" + id); if (el) el.textContent = value; };
        setText("roomTotal", 24 + added.length);
        setText("roomOccupied", occupied);
        setText("roomAvailable", available);
    }

    function initRooms() {
        const grid = $("#roomGrid");
        if (!grid) return;

        readStorage(storageKeys.rooms).forEach(item => {
            grid.insertAdjacentHTML("beforeend", roomCard(item));
        });

        const search = $("#roomSearch");
        const property = $("#roomPropertyFilter");
        const status = $("#roomStatusFilter");
        const empty = $("#roomEmpty");

        const params = new URLSearchParams(window.location.search);
        const propertyFromUrl = params.get("property");
        if (propertyFromUrl && property) {
            property.value = propertyFromUrl;
        }

        function filter() {
            const keyword = (search?.value || "").trim().toLowerCase();
            const wantedProperty = property?.value || "all";
            const wantedStatus = status?.value || "all";
            let visible = 0;

            $$(".room-card", grid).forEach(card => {
                const matchName = (card.dataset.name || "").toLowerCase().includes(keyword);
                const matchProperty = wantedProperty === "all" || card.dataset.property === wantedProperty;
                const matchStatus = wantedStatus === "all" || card.dataset.status === wantedStatus;
                const show = matchName && matchProperty && matchStatus;
                card.style.display = show ? "" : "none";
                if (show) visible++;
            });

            if (empty) empty.hidden = visible !== 0;
        }

        search?.addEventListener("input", filter);
        $("#roomFilterBtn")?.addEventListener("click", filter);
        property?.addEventListener("change", filter);
        status?.addEventListener("change", filter);

        bindModal("openRoomModal", "roomModal", "data-close-room");

        $("#roomForm")?.addEventListener("submit", e => {
            e.preventDefault();
            const form = e.currentTarget;
            const data = new FormData(form);

            const item = {
                id: Date.now().toString(),
                name: data.get("name"),
                property: data.get("property"),
                price: Number(data.get("price")) || 0,
                area: data.get("area"),
                type: data.get("type"),
                status: data.get("status"),
                tenant: data.get("tenant")
            };

            const rooms = readStorage(storageKeys.rooms);
            rooms.push(item);
            writeStorage(storageKeys.rooms, rooms);

            grid.insertAdjacentHTML("beforeend", roomCard(item));
            updateRoomStats();
            form.reset();
            closeModal("roomModal");
            filter();
            showToast("Đã thêm " + item.name);
        });

        updateRoomStats();
        filter();
    }

    function showToast(message) {
        let toast = $("#ownerToast");
        if (!toast) {
            toast = document.createElement("div");
            toast.id = "ownerToast";
            toast.className = "owner-toast";
            document.body.appendChild(toast);
        }
        toast.innerHTML = '<i class="bi bi-check-circle-fill"></i><span></span>';
        toast.querySelector("span").textContent = message;
        toast.classList.add("show");
        clearTimeout(window.__ownerToastTimer);
        window.__ownerToastTimer = setTimeout(() => toast.classList.remove("show"), 2600);
    }

    document.addEventListener("DOMContentLoaded", () => {
        initProperties();
        initRooms();
    });
})();
