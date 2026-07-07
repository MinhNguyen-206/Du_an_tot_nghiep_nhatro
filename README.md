# Nhà Trọ Project — Spring Boot (BE) + React (FE) + SQL Server

Đây là khung dự án (chưa code logic nghiệp vụ đầy đủ). Làm theo hướng dẫn dưới
để dựng xong môi trường trên **Windows + VSCode**.

## 1. Cài đặt phần mềm cần thiết (Windows)

| Phần mềm                            | Bản khuyến nghị                                      | Link tải                                                        |
| ----------------------------------- | ---------------------------------------------------- | --------------------------------------------------------------- |
| JDK                                 | 17 (Temurin/Eclipse Adoptium)                        | https://adoptium.net                                            |
| Maven                               | 3.9.x (có thể bỏ qua nếu dùng `mvnw` đi kèm project) | https://maven.apache.org/download.cgi                           |
| Node.js                             | LTS 20.x                                             | https://nodejs.org                                              |
| SQL Server                          | 2022 Express (miễn phí, đủ dùng cho dự án)           | https://www.microsoft.com/vi-vn/sql-server/sql-server-downloads |
| SQL Server Management Studio (SSMS) | mới nhất — GUI để quản lý DB                         | https://aka.ms/ssmsfullsetup                                    |
| Git (tùy chọn)                      | mới nhất                                             | https://git-scm.com                                             |

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

**Xem toàn bộ API bằng giao diện Swagger** (thay vì vào từng link JSON thô):

```
http://localhost:8080/swagger-ui/index.html
```

Trang này liệt kê hết các API, có thể bấm **Try it out** để test trực tiếp
(gửi GET/POST/PUT/DELETE) ngay trên trình duyệt, không cần Postman.

## 7. Kiểm tra frontend (khung, chưa có giao diện thật)

```powershell
cd frontend
npm install
npm run dev
```

Vite sẽ báo chạy ở `http://localhost:5173`.

## 8. Quy trình làm việc nhóm với Git (Git Workflow)

Vì repo Private trên GitHub Free **không chặn được** việc push thẳng vào
`main` bằng kỹ thuật (branch protection chỉ hoạt động thật với GitHub Pro/Team),
nhóm cần **tự giác tuân theo quy trình dưới đây** — không ai push code trực
tiếp vào `main`, luôn làm việc qua nhánh riêng (branch) + Pull Request.

### 8.1. Nguyên tắc cốt lõi

- `main` luôn là code **chạy được, đã kiểm tra** — không code dở dang trên đó.
- Mỗi người/mỗi tính năng làm trên **1 nhánh riêng**, đặt tên rõ ràng:
  - `feature/ten-tinh-nang` — khi thêm chức năng mới (vd `feature/crud-dangtin`)
  - `fix/mo-ta-loi` — khi sửa lỗi (vd `fix/loi-ket-noi-db`)
- Code xong 1 việc → tạo **Pull Request (PR)** → nhờ 1 bạn khác xem qua → merge
  vào `main`. Không tự merge PR của chính mình nếu nhóm có ≥ 3 người.

### 8.2. Quy trình từng bước khi bắt đầu 1 việc mới

```powershell
git checkout main
git pull                                  # lay code moi nhat cua ca nhom
git checkout -b feature/ten-tinh-nang     # tao nhanh moi TU main moi nhat
```

### 8.3. Trong lúc code — lặp lại nhiều lần

```powershell
git add .
git commit -m "Mo ta ngan gon thay doi"
git push                                  # lan dau can go: git push -u origin feature/ten-tinh-nang
```

> Chỉ cần `-b` (tạo nhánh) và `-u` (gán nhánh mặc định) **1 lần đầu tiên**
> cho mỗi nhánh. Các lần push sau chỉ cần `git push`.

### 8.4. Tiếp tục 1 việc dở dang (nhánh đã có sẵn, hôm sau code tiếp)

```powershell
git checkout feature/ten-tinh-nang        # khong co -b, vi nhanh da ton tai
git pull                                  # phong khi may khac (hoac chinh minh) da push them
# ... code tiep ...
git add .
git commit -m "Mo ta"
git push
```

### 8.5. Xong việc — tạo Pull Request

1. Vào GitHub → tab **Pull requests** → **New pull request**
2. Base: `main` ← Compare: `feature/ten-tinh-nang`
3. Viết mô tả ngắn đã làm gì → **Create pull request**
4. Nhờ 1 thành viên khác **Review** → nếu ổn, bấm **Merge pull request**
5. Sau khi merge, có thể xoá nhánh đó trên GitHub (nút **Delete branch**
   hiện ngay sau khi merge) để danh sách nhánh gọn gàng.

### 8.6. Xử lý khi `git push` bị từ chối (rejected)

Nghĩa là trên GitHub đã có commit mới hơn máy bạn (ai đó push trước). Chạy:

```powershell
git pull
git push
```

Nếu Git báo **conflict** (đụng độ), VSCode sẽ đánh dấu đoạn code xung đột
ngay trong file (`<<<<<<<`, `=======`, `>>>>>>>`) — sửa lại cho đúng, xoá các
dấu đó đi, rồi:

```powershell
git add .
git commit -m "Fix conflict"
git push
```

### 8.7. Tóm tắt lệnh hay dùng

| Việc cần làm                       | Lệnh                                  |
| ---------------------------------- | ------------------------------------- |
| Xem đang ở nhánh nào               | `git status` hoặc `git branch`        |
| Chuyển sang `main`                 | `git checkout main`                   |
| Lấy code mới nhất                  | `git pull`                            |
| Tạo nhánh mới                      | `git checkout -b feature/ten`         |
| Chuyển sang nhánh có sẵn           | `git checkout feature/ten`            |
| Lưu thay đổi                       | `git add .` rồi `git commit -m "..."` |
| Đẩy lên GitHub (lần đầu của nhánh) | `git push -u origin feature/ten`      |
| Đẩy lên GitHub (các lần sau)       | `git push`                            |
| Xem lịch sử commit                 | `git log --oneline --graph --all`     |

## 9. Bước tiếp theo (chưa làm ở bước này)

- Viết Repository, Service, Controller cho từng entity (CRUD REST API).
- Viết giao diện React gọi API.
- Thêm entity cho các bảng còn lại (đăng tin, hợp đồng, thanh toán...).

Khi môi trường chạy ổn, nhắn lại để tiếp tục code phần REST API.
