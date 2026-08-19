<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Footer Component - Include this in any page -->
<footer class="footer">
    <div class="footer-container">
        <!-- Footer Content -->
        <div class="footer-content">
            <!-- About Section -->
            <div class="footer-section">
                <h3 class="footer-title">Về Room Connect</h3>
                <p class="footer-description">
                    Room Connect là nền tảng quản lý nhà trọ và cho thuê phòng trực tuyến hàng đầu.
                    Chúng tôi giúp bạn quản lý tài sản, kết nối với khách thuê và tối ưu hóa lợi nhuận.
                </p>
            </div>

            <!-- Quick Links Section -->
            <div class="footer-section">
                <h3 class="footer-title">Liên kết nhanh</h3>
                <ul class="footer-links">
                    <li><a href="/">Trang chủ</a></li>
                    <li><a href="#about">Giới thiệu</a></li>
                    <li><a href="#features">Tính năng</a></li>
                    <li><a href="#pricing">Bảng giá</a></li>
                    <li><a href="/contact">Liên hệ</a></li>
                </ul>
            </div>

            <!-- Support Section -->
            <div class="footer-section">
                <h3 class="footer-title">Hỗ trợ</h3>
                <ul class="footer-links">
                    <li><a href="#help">Trung tâm trợ giúp</a></li>
                    <li><a href="#faq">Câu hỏi thường gặp</a></li>
                    <li><a href="#privacy">Chính sách bảo mật</a></li>
                    <li><a href="#terms">Điều khoản sử dụng</a></li>
                </ul>
            </div>

            <!-- Contact Section -->
            <div class="footer-section">
                <h3 class="footer-title">Liên hệ</h3>
                <ul class="footer-contact">
                    <li>
                        <span class="contact-icon">📧</span>
                        <a href="mailto:support@roomconnect.vn">support@roomconnect.vn</a>
                    </li>
                    <li>
                        <span class="contact-icon">📞</span>
                        <a href="tel:+84123456789">+84 (0) 123 456 789</a>
                    </li>
                    <li>
                        <span class="contact-icon">📍</span>
                        <span>Hà Nội, Việt Nam</span>
                    </li>
                </ul>
            </div>
        </div>

        <!-- Footer Bottom -->
        <div class="footer-bottom">
            <div class="footer-copyright">
                <p>&copy; 2026 Room Connect. Tất cả các quyền được bảo lưu.</p>
            </div>
            <div class="footer-social">
                <a href="#facebook" class="social-link" title="Facebook">f</a>
                <a href="#twitter" class="social-link" title="Twitter">𝕏</a>
                <a href="#instagram" class="social-link" title="Instagram">📷</a>
                <a href="#linkedin" class="social-link" title="LinkedIn">in</a>
            </div>
        </div>
    </div>
</footer>

<style>
    .footer {
        background-color: #6b6360;
        color: #f3f3f3;
        padding: 1.5rem 0 0;
        margin: 2rem 2rem 0 2rem;
        border-top: 1px solid #5d5651;
        border-radius: 8px 8px 0 0;
    }

    .footer-container {
        max-width: 100%;
        margin: 0 auto;
        padding: 0 2rem;
    }

    .footer-content {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
        gap: 2rem;
        margin-bottom: 1.5rem;
    }

    .footer-section {
        display: flex;
        flex-direction: column;
    }

    .footer-title {
        font-size: 1.1rem;
        font-weight: 600;
        margin: 0 0 1rem 0;
        color: #ffffff;
    }

    .footer-description {
        font-size: 0.95rem;
        line-height: 1.6;
        color: #bdc3c7;
        margin: 0;
    }

    .footer-links {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .footer-links li {
        margin-bottom: 0.75rem;
    }

    .footer-links a {
        color: #bdc3c7;
        text-decoration: none;
        transition: color 0.3s;
        font-size: 0.95rem;
    }

    .footer-links a:hover {
        color: #fff;
    }

    .footer-contact {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .footer-contact li {
        margin-bottom: 0.75rem;
        display: flex;
        align-items: center;
        gap: 0.75rem;
        font-size: 0.95rem;
        color: #bdc3c7;
    }

    .footer-contact a {
        color: #bdc3c7;
        text-decoration: none;
        transition: color 0.3s;
    }

    .footer-contact a:hover {
        color: #fff;
    }

    .contact-icon {
        font-size: 1.2rem;
    }

    .footer-bottom {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding-top: 1.5rem;
        border-top: 1px solid #5d5651;
        flex-wrap: wrap;
        gap: 1rem;
    }

    .footer-copyright {
        font-size: 0.9rem;
        color: #95a5a6;
        margin: 0;
    }

    .footer-copyright p {
        margin: 0;
    }

    .footer-social {
        display: flex;
        gap: 1rem;
    }

    .social-link {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        width: 32px;
        height: 32px;
        border-radius: 50%;
        background-color: #5d5651;
        color: #ecf0f1;
        text-decoration: none;
        font-weight: 600;
        transition: all 0.3s;
    }

    .social-link:hover {
        background-color: #8b7d72;
        transform: translateY(-3px);
    }

    @media (max-width: 768px) {
        .footer {
            padding: 2rem 0 1rem;
        }

        .footer-container {
            padding: 0 1.5rem;
        }

        .footer-content {
            grid-template-columns: 1fr;
            gap: 1.5rem;
        }

        .footer-bottom {
            flex-direction: column;
            text-align: center;
        }

        .footer-social {
            justify-content: center;
        }
    }
</style>
