# Nhà Trọ Project — Spring Boot (BE) + React (FE) + SQL Server

Đây là khung dự án (chưa code logic nghiệp vụ đầy đủ). Làm theo hướng dẫn dưới
để dựng xong môi trường trên **Windows + VSCode**.

## 1. Cài đặt phần mềm cần thiết (Windows)

| Phần mềm | Bản khuyến nghị | Link tải |
|---|---|---|
| JDK | 17 (Temurin/Eclipse Adoptium) | https://adoptium.net |
| Maven | 3.9.x (có thể bỏ qua nếu dùng `mvnw` đi kèm project) | https://maven.apache.org/download.cgi |
| Node.js | LTS 20.x | https://nodejs.org |
| SQL Server | 2022 Express (miễn phí, đủ dùng cho dự án) | https://www.microsoft.com/vi-vn/sql-server/sql-server-downloads |
| SQL Server Management Studio (SSMS) | mới nhất — GUI để quản lý DB | https://aka.ms/ssmsfullsetup |
| Git (tùy chọn) | mới nhất | https://git-scm.com |

Khi cài SQL Server, chọn kiểu **"Basic"**, và **ghi nhớ**:
- Instance name (mặc định `SQLEXPRESS` hoặc `MSSQLSERVER`)
- Bật **Mixed Mode Authentication** (nếu được hỏi) và đặt mật khẩu cho user `sa`
  — nếu không bật lúc cài, có thể bật sau trong SSMS: chuột phải server >
  Properties > Security > chọn "SQL Server and Windows Authentication mode",
  sau đó khởi động lại dịch vụ SQL Server (Windows Services, tìm "SQL Server").

Sau khi cài xong, mở **PowerShell** hoặc **CMD** và kiểm tra:
```powershell
java -version
mvn -version
node -v
npm -v
```
Riêng SQL Server không kiểm tra bằng lệnh `--version` — mở **SSMS**, thử kết nối
tới `localhost` là biết cài thành công hay chưa (xem bước 3 bên dưới).

Nếu lệnh nào báo "not recognized", nghĩa là chưa thêm vào PATH — cần mở lại
Environment Variables > Path và thêm đường dẫn thư mục `bin` của phần mềm đó.

## 2. Cài extension cho VSCode

Mở project này trong VSCode, VSCode sẽ tự đề xuất cài các extension trong
`.vscode/extensions.json`. Bấm **Install All** khi được hỏi, hoặc cài tay:

- **Extension Pack for Java** (vscjava.vscode-java-pack)
- **Spring Boot Extension Pack** (vmware.vscode-spring-boot)
- **Spring Boot Dashboard** — để chạy/dừng app bằng nút bấm thay vì gõ lệnh
- **Lombok Annotations Support**
- **SQL Server (mssql)** (ms-mssql.mssql) — để kết nối và query SQL Server ngay trong VSCode
- **Prettier** + **ESLint** — cho phần frontend React

## 3. Tạo database

Mở **SSMS**, kết nối tới server (thường gõ `localhost` hoặc `localhost\SQLEXPRESS`
tùy instance name lúc cài, Authentication chọn "SQL Server Authentication",
user `sa` + mật khẩu đã đặt lúc cài). Sau khi kết nối, mở file:

```
database/nha_tro_sqlserver.sql
```

và bấm **Execute** (hoặc F5). File này tự tạo database `nha_tro_db`, 28 bảng,
index, và dữ liệu mặc định.

> File `database/nha_tro_mysql_backup.sql` là bản MySQL cũ, giữ lại để tham
> khảo — **không dùng** file này với SQL Server vì khác cú pháp.

Cách khác: dùng ngay extension **SQL Server (mssql)** trong VSCode — mở file
`.sql`, bấm "Connect", chọn server, rồi bấm nút Run (▷) ở góc trên.

## 4. Cấu hình kết nối Backend → Database

Mở `backend/src/main/resources/application.properties`, sửa lại:
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=nha_tro_db;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=<mật khẩu SQL Server của bạn>
```
Nếu bạn cài SQL Server dạng **Named Instance** (ví dụ `SQLEXPRESS`), thay
`localhost:1433` bằng `localhost\\SQLEXPRESS` và bỏ số cổng, hoặc bật
**SQL Server Browser** service để dùng port động.

## 5. Cấu trúc thư mục hiện tại

```
nha-tro-project/
├── backend/                     # Spring Boot REST API
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/nhatro/backend/
│       │   ├── BackendApplication.java
│       │   ├── entity/          # Đã có: NguoiDung, NhaTro, PhongTro, DangTin
│       │   ├── repository/      # Khung trống — sẽ code sau
│       │   ├── controller/      # Khung trống — sẽ code sau
│       │   ├── service/         # Khung trống — sẽ code sau
│       │   └── config/CorsConfig.java
│       └── resources/application.properties
├── frontend/                    # React + Vite (khung, chưa code component)
│   ├── package.json
│   └── src/
├── .vscode/
│   ├── settings.json
│   └── extensions.json
```

> **Lưu ý:** file schema SQL Server (`nha_tro_sqlserver.sql`) được gửi riêng ở
> tin nhắn trước — không nằm trong file nén này. Đặt nó vào một thư mục
> `database/` cùng cấp nếu muốn quản lý trong cùng repo Git.

## 6. Kiểm tra backend chạy được (chưa cần code thêm)

Trong VSCode, mở thư mục `backend`, đợi Java Extension Pack tải xong
dependencies (lần đầu sẽ hơi lâu vì Maven tải các thư viện). Sau đó:

- Cách 1: Mở file `BackendApplication.java`, bấm nút **Run** phía trên hàm `main`.
- Cách 2: Dùng terminal:
  ```powershell
  cd backend
  mvn spring-boot:run
  ```

Nếu chạy thành công, terminal sẽ hiện dòng `Tomcat started on port(s): 8080`.
Vào trình duyệt gõ `http://localhost:8080` — thấy trang lỗi trắng "Whitelabel
Error Page" là **bình thường** (vì chưa có endpoint nào), nghĩa là backend đã
kết nối được DB và chạy ổn.

## 7. Kiểm tra frontend (khung, chưa có giao diện thật)

```powershell
cd frontend
npm install
npm run dev
```
Vite sẽ báo chạy ở `http://localhost:5173`.

## 8. Bước tiếp theo (chưa làm ở bước này)

- Viết Repository, Service, Controller cho từng entity (CRUD REST API).
- Viết giao diện React gọi API.
- Thêm entity cho các bảng còn lại (đăng tin, hợp đồng, thanh toán...).

Khi môi trường chạy ổn, nhắn lại để tiếp tục code phần REST API.
