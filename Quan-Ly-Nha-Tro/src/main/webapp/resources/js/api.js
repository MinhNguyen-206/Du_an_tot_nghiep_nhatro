/**
 * Helper goi API dung chung cho toan bo cac trang JSP.
 * Thay cho axiosClient.js ben Vue truoc day - gio dung fetch() thuan,
 * khong can build tool (npm/vite) vi JSP + JS nay chay thang trong trinh duyet.
 *
 * Vi JSP va REST API (/api/**) gio nam CUNG 1 server (cung port 8080),
 * KHONG CON BI CORS NUA - khong can chinh CorsConfig.java gi them.
 */
const API_BASE = "/api";

/**
 * Luu JWT vao CA localStorage (cho apiFetch dung Authorization header)
 * LAN cookie "jwt" (de trinh duyet tu dong gui kem khi dieu huong trang
 * thuong nhu /chu-tro, /admin/... - nhung request nay KHONG phai la
 * fetch() nen khong the tu gan header Authorization).
 * JwtAuthenticationFilter se doc them cookie nay khi khong co header.
 */
function saveAuthToken(token) {
  localStorage.setItem("token", token);
  document.cookie = "jwt=" + encodeURIComponent(token) + "; path=/; SameSite=Lax";
}

function clearAuthToken() {
  localStorage.removeItem("token");
  localStorage.removeItem("user");
  document.cookie = "jwt=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT";
}

/**
 * @param {string} path        VD: "/auth/login", "/phong-tro"
 * @param {object} [options]   { method, body, headers }
 */
async function apiFetch(path, options = {}) {
  const token = localStorage.getItem("token");

  const res = await fetch(API_BASE + path, {
    method: options.method || "GET",
    headers: {
      "Content-Type": "application/json",
      ...(token ? { Authorization: "Bearer " + token } : {}),
      ...(options.headers || {}),
    },
    body: options.body ? JSON.stringify(options.body) : undefined,
  });

  if (res.status === 401) {
    clearAuthToken();
    window.location.href = "/login";
    return null;
  }

  const contentType = res.headers.get("content-type") || "";
  const data = contentType.includes("application/json") ? await res.json() : null;

  if (!res.ok) {
    const err = new Error(data?.message || "Loi khong xac dinh");
    err.status = res.status;
    err.data = data;
    throw err;
  }
  return data;
}

// ---- Vi du cac ham goi API cu the (tuong duong cac file api/*.js ben Vue) ----
const AuthApi = {
  login: (email, password) => apiFetch("/auth/login", { method: "POST", body: { email, password } }),

  // Chua co AuthController.register() rieng ben BE, nen dung tam endpoint
  // POST /api/nguoi-dung (NguoiDungController.create) - endpoint nay da
  // permitAll trong SecurityConfig va da tu hash mat khau trong
  // NguoiDungService.create(). KHONG con gui maVaiTro nua: BE (xem
  // NguoiDungController.create) LUON ep vai tro tai khoan moi ve
  // "Nguoi thue" bat ke client gui gi - dang ky khong con cho chon vai
  // tro nua. Muon thanh Chu tro se phai di qua luong "dang ky chu tro"
  // rieng (Admin duyet) - luong nay CHUA duoc trien khai o BE.
  register: ({ hoTen, email, soDienThoai, matKhau }) =>
    apiFetch("/nguoi-dung", {
      method: "POST",
      body: { hoTen, email, soDienThoai, matKhau },
    }),

  // Gui yeu cau khoi phuc mat khau - BE se gui email chua link dat lai mat khau
  // (xem AuthController.forgotPassword / MailService).
  forgotPassword: (email) => apiFetch("/auth/forgot-password", { method: "POST", body: { email } }),

  // Dat lai mat khau bang token lay tu link trong email (query param ?token=...)
  resetPassword: (token, matKhauMoi) =>
    apiFetch("/auth/reset-password", { method: "POST", body: { token, matKhauMoi } }),
};

const PhongTroApi = {
  getAll: () => apiFetch("/phong-tro"),
  getById: (id) => apiFetch(`/phong-tro/${id}`),
};
