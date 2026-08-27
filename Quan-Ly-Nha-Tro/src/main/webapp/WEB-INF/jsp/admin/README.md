                                                                                                                                                                                                                # Admin JSP Demo - Room Connect

## Cấu trúc
WEB-INF/jsp/admin/
├── adminDashboard.jsp
├── postApproval.jsp
├── ekycApproval.jsp
├── userManagement.jsp
├── complaintManagement.jsp
├── categoryManagement.jsp
├── blogManagement.jsp
├── statistics.jsp
├── revenue.jsp
├── transactions.jsp
├── premiumManagement.jsp
├── notifications.jsp
├── activityLogManagement.jsp
├── reviewModeration.jsp
├── aiAdmin.jsp
└── includes/
    ├── header.jspf
    └── footer.jspf

assets/admin.css

## Cách đặt vào project
Copy toàn bộ thư mục `admin` vào:
src/main/webapp/WEB-INF/jsp/

Copy `assets/admin.css` vào:
src/main/webapp/assets/

Nếu project dùng Spring MVC:
return "admin/adminDashboard";
return "admin/postApproval";
...

Các số liệu và bảng hiện là Demo. Khi nối SQL, thay mock bằng EL:
${totalUsers}, ${totalPosts}, ${logs}, ${users}, ${transactions}, ...

Các nút Duyệt/Từ chối/Khóa/Sửa hiện chỉ là UI, chưa có POST/PUT/DELETE backend.
