package com.nhatro.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

        private static final String SCHEME_NAME = "bearerAuth";

        @Bean
        public OpenAPI nhaTroOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("Nhà Trọ Management API")
                                                .description("Tài liệu và thử nghiệm trực tiếp các API của hệ thống quản lý thuê nhà trọ")
                                                .version("1.0.0")
                                                .contact(new Contact()
                                                                .name("Nhóm dự án tốt nghiệp Nhà Trọ")
                                                                .url("https://nhatro.com")))
                                // Khai bao bearer scheme de nut "Authorize" hien ra va Swagger
                                // tu dong gan header "Authorization: Bearer <token>" vao moi request
                                .components(new Components()
                                                .addSecuritySchemes(SCHEME_NAME, new SecurityScheme()
                                                                .name(SCHEME_NAME)
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")))
                                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
                                .tags(Arrays.asList(
                                                createTag("Xác thực & Đăng nhập",
                                                                "Đăng nhập, đăng xuất, quản lý token"),
                                                createTag("Người dùng", "Quản lý thông tin người dùng"),
                                                createTag("Quyền hạn", "Quản lý vai trò và quyền hạn"),
                                                createTag("Quản trị viên", "Quản lý quản trị viên hệ thống"),
                                                createTag("Nhà trọ", "Quản lý thông tin nhà trọ"),
                                                createTag("Cấu hình danh mục", "Quản lý cấu hình và danh mục hệ thống"),
                                                createTag("Phòng trọ", "Quản lý danh sách phòng trọ"),
                                                createTag("Bài đăng cho thuê", "Quản lý bài đăng tin cho thuê"),
                                                createTag("Hợp đồng điện tử", "Quản lý hợp đồng thuê nhà"),
                                                createTag("Hoá đơn hàng tháng", "Quản lý hoá đơn hàng tháng"),
                                                createTag("Giao dịch thanh toán",
                                                                "Quản lý giao dịch và chi trả thanh toán"),
                                                createTag("Thanh toán cọc", "Quản lý tiền cọc và lịch sử thanh toán"),
                                                createTag("Gói dịch vụ", "Quản lý các gói dịch vụ trả phí"),
                                                createTag("Gói Premium", "Quản lý các gói Premium dành cho chủ trọ"),
                                                createTag("Đăng ký gói chủ trọ",
                                                                "Quản lý việc chủ trọ đăng ký/gia hạn/hủy gói Premium"),
                                                createTag("Hợp đồng Premium",
                                                                "Quản lý hợp đồng điện tử của gói chủ trọ Premium"),
                                                createTag("Hóa đơn điện tử Premium",
                                                                "Quản lý hóa đơn thanh toán gói Premium của chủ trọ"),
                                                createTag("Lịch sử gói chủ trọ",
                                                                "Quản lý lịch sử đăng ký gói dịch vụ chủ trọ"),
                                                createTag("Đánh giá & Xếp hạng", "Quản lý đánh giá và xếp hạng phòng"),
                                                createTag("Tin nhắn", "Quản lý tin nhắn giữa người dùng"),
                                                createTag("Thông báo", "Quản lý thông báo hệ thống"),
                                                createTag("Lịch hẹn", "Quản lý lịch hẹn xem phòng"),
                                                createTag("Lịch sử xem phòng", "Quản lý lịch sử xem phòng"),
                                                createTag("Báo cáo vi phạm", "Quản lý báo cáo vi phạm"),
                                                createTag("Cảnh báo phòng", "Quản lý cảnh báo phòng mới"),
                                                createTag("Xác thực OTP", "Quản lý xác thực bằng OTP"),
                                                createTag("Bộ điều khiển AI", "Các tính năng AI hỗ trợ"),
                                                createTag("Chỉ số điện nước", "Quản lý chỉ số điện nước hàng tháng"),
                                                createTag("Nhật ký hoạt động",
                                                                "Quản lý nhật ký hoạt động của người dùng"),
                                                createTag("Yêu cầu thuê", "Quản lý yêu cầu thuê nhà")));
        }

        private Tag createTag(String name, String description) {
                return new Tag().name(name).description(description);
        }
}