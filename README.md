# Đồ án tốt nghiệp – Hệ thống Quản lý Nhà trọ

Ứng dụng web quản lý cho thuê nhà trọ / phòng trọ, xây dựng bằng **Spring Boot 3 (Java 21)**, giao diện render phía server bằng **JSP + JSTL**, cơ sở dữ liệu **SQL Server**.

## Cấu trúc thư mục

```
Quan-Ly-Nha-Tro/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/nhatro/backend/
│   │   │   ├── controller/     # REST + Web controllers
│   │   │   ├── service/        # Business logic
│   │   │   ├── repository/     # Spring Data JPA repositories
│   │   │   ├── entity/         # JPA entities
│   │   │   ├── dto/            # Request/response DTOs
│   │   │   ├── security/       # JWT filter, Spring Security config
│   │   │   ├── config/         # CORS, OpenAPI/Swagger config
│   │   │   └── exception/      # Global exception handling
│   │   ├── resources/
│   │   │   └── application.properties
│   │   └── webapp/
│   │       ├── WEB-INF/jsp/    # Giao diện JSP theo từng module
│   │       └── resources/js/   # JS tĩnh (gọi API)
│   └── test/                   # Unit test
└── target/                     # Build output (không commit)
```

## Các module chức năng chính

Xác thực (Auth) · Người dùng & phân quyền · Nhà trọ / Phòng trọ · Đăng tin · Hợp đồng điện tử & Premium · Hoá đơn tháng / Premium · Thanh toán cọc & giao dịch thanh toán · Chỉ số điện nước · Yêu cầu thuê · Lịch hẹn · Đánh giá · Tin nhắn · Thông báo · Xác thực eKYC · Nhật ký hoạt động · Báo cáo · Cấu hình danh mục · Gói dịch vụ & đăng ký gói chủ trọ.

## Yêu cầu môi trường

- JDK 21
- Maven 3.9+
- SQL Server (đã tạo sẵn database `QuanLyNhaTro` theo schema, project dùng `ddl-auto=none`)

## Cài đặt & chạy

1. Clone repo:
   ```bash
   git clone https://github.com/MinhNguyen-206/Du_an_tot_nghiep_nhatro.git
   cd Du_an_tot_nghiep_nhatro/Quan-Ly-Nha-Tro
   ```

2. Cấu hình kết nối database — **không sửa trực tiếp `application.properties`**, thay vào đó:
   - Copy `src/main/resources/application-local.properties.example` thành `application-local.properties` rồi điền thông tin thật, **hoặc**
   - Set biến môi trường trước khi chạy:
     ```bash
     export DB_URL="jdbc:sqlserver://localhost:1433;databaseName=QuanLyNhaTro;encrypt=true;trustServerCertificate=true"
     export DB_USERNAME=sa
     export DB_PASSWORD=your_password
     ```

3. Build & chạy:
   ```bash
   mvn spring-boot:run
   ```
   Ứng dụng chạy tại `http://localhost:8080`.

4. Swagger UI (tài liệu API): `http://localhost:8080/swagger-ui.html`

## Ghi chú

- File cấu hình chứa mật khẩu thật (`application-local.properties`) đã được `.gitignore`, tuyệt đối không commit lên Git.
- Thư mục `target/` là build output, không nên commit — đã được thêm vào `.gitignore`.
