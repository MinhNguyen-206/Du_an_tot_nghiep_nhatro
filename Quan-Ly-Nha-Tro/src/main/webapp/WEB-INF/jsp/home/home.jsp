<<<<<<< HEAD
=======
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%
    List<Map<String, String>> roomList = new ArrayList<>();

    Map<String, String> room1 = new HashMap<>();
    room1.put("title", "Phòng trọ cao cấp full nội thất gần Đại học FPT, giờ giấc tự do");
    room1.put("price", "3.2 Triệu/tháng");
    room1.put("area", "25 m²");
    room1.put("location", "Quận 9, TP. Hồ Chí Minh");
    room1.put("img", "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?auto=format&fit=crop&w=500&q=80");
    room1.put("tag", "Xác minh eKYC");
    room1.put("time", "15 phút trước");
    roomList.add(room1);

    Map<String, String> room2 = new HashMap<>();
    room2.put("title", "Căn hộ Studio mới 100%, không chung chủ, có ban công thoáng mát");
    room2.put("price", "4.5 Triệu/tháng");
    room2.put("area", "35 m²");
    room2.put("location", "Quận Bình Thạnh, TP. Hồ Chí Minh");
    room2.put("img", "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?auto=format&fit=crop&w=500&q=80");
    room2.put("tag", "Chủ trọ Premium");
    room2.put("time", "1 giờ trước");
    roomList.add(room2);

    Map<String, String> room3 = new HashMap<>();
    room3.put("title", "Ký túc xá cao cấp cho sinh viên, free điện nước wifi, thang máy");
    room3.put("price", "1.5 Triệu/tháng");
    room3.put("area", "18 m²");
    room3.put("location", "Quận Cầu Giấy, Hà Nội");
    room3.put("img", "https://images.unsplash.com/photo-1555854877-bab0e564b8d5?auto=format&fit=crop&w=500&q=80");
    room3.put("tag", "Xác minh eKYC");
    room3.put("time", "3 giờ trước");
    roomList.add(room3);
%>

>>>>>>> origin/main
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Nhà Tốt - Trang Chủ Hệ Thống Tìm Kiếm Phòng Trọ</title>

    <!-- Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        brand: '#FF6400',
                        brandDark: '#E05800'
                    }
                }
            }
        }
    </script>
</head>

<body class="bg-gray-100 font-sans text-gray-800 flex flex-col min-h-screen">

    <!-- ========================================================= -->
    <!-- HEADER -->
    <!-- ========================================================= -->

    <header class="bg-white border-b border-gray-200 sticky top-0 z-50 shadow-sm">

        <div class="max-w-7xl mx-auto px-4 h-16 flex items-center justify-between text-sm">

            <!-- LOGO + MENU -->
            <div class="flex items-center space-x-6">

                <!-- LOGO -->
                <a href="${pageContext.request.contextPath}/home" class="flex items-center space-x-2">
                    <span
                        class="bg-brand text-white font-black px-2.5 py-1 rounded-lg text-xl italic tracking-wider shadow-sm">
                        NHÀTỐT
                    </span>
                </a>

                <!-- MENU -->
                <nav class="hidden md:flex items-center space-x-6 text-gray-700 font-medium text-xs">

                    <!-- Trang chủ -->
                    <a href="${pageContext.request.contextPath}/home"
                        class="text-brand font-bold border-b-2 border-brand pb-4">
                        Trang chủ
                    </a>

                    <!-- Thuê trọ -->
                    <a href="${pageContext.request.contextPath}/thue-tro" class="hover:text-brand pb-4">
                        Thuê Trọ
                    </a>

                    <!-- Thuê căn hộ -->
                    <a href="${pageContext.request.contextPath}/thue-can-ho" class="hover:text-brand pb-4">
                        Thuê Căn Hộ
                    </a>

                </nav>

            </div>


            <!-- RIGHT MENU -->
            <div class="flex items-center space-x-3 text-xs">

                <!-- Thông báo -->
                <button type="button"
                    class="relative w-9 h-9 rounded-full border border-gray-200 flex items-center justify-center text-gray-600 hover:bg-gray-50">
                    <i class="fa-regular fa-bell text-base"></i>

                    <span class="absolute top-1 right-1 w-2 h-2 bg-red-500 rounded-full"></span>
                </button>


                <!-- Yêu thích -->
                <button type="button"
                    class="w-9 h-9 rounded-full border border-gray-200 flex items-center justify-center text-gray-600 hover:bg-gray-50">
                    <i class="fa-regular fa-heart text-base"></i>
                </button>


                <!-- Đăng nhập -->
                <a href="${pageContext.request.contextPath}/login" class="font-semibold px-2 hover:text-brand">
                    Đăng nhập
                </a>


                <!-- Đăng tin -->
                <a href="${pageContext.request.contextPath}/login"
                    class="bg-brand hover:bg-brandDark text-white px-4 py-2 rounded-full font-bold shadow-md transition flex items-center space-x-1">
                    <i class="fa-solid fa-pen-to-square"></i>

                    <span>ĐĂNG TIN</span>
                </a>

            </div>

        </div>

    </header>


    <!-- ========================================================= -->
    <!-- HERO BANNER -->
    <!-- ========================================================= -->

    <section class="bg-gradient-to-r from-orange-500 to-brand py-8 px-4 text-white">

        <div class="max-w-5xl mx-auto">

            <!-- TITLE -->
            <div class="text-center mb-6">

                <h1 class="text-2xl md:text-3xl font-extrabold tracking-tight">
                    Tìm Phòng Trọ, Căn Hộ Nhanh Chóng & An Toàn
                </h1>

                <p class="text-xs md:text-sm text-orange-100 mt-1">
                    Nền tảng kết nối trực tiếp chủ trọ và người thuê
                </p>

            </div>


            <!-- SEARCH -->
            <div class="bg-white rounded-2xl p-4 shadow-2xl text-gray-800">

                <form action="${pageContext.request.contextPath}/rooms" method="GET"
                    class="flex flex-col md:flex-row gap-2 mb-3">

                    <div class="flex-1 flex items-center bg-gray-100 rounded-xl px-3 py-2">

                        <i class="fa-solid fa-magnifying-glass text-gray-400 mr-2"></i>

                        <input type="text" name="keyword" placeholder="Nhập khu vực, trường học..."
                            class="bg-transparent w-full focus:outline-none text-sm">

                    </div>


                    <button type="submit"
                        class="bg-brand hover:bg-brandDark text-white font-bold px-8 py-2.5 rounded-xl text-sm shadow transition">
                        TÌM PHÒNG
                    </button>

                </form>

            </div>

        </div>

    </section>


    <!-- ========================================================= -->
    <!-- CATEGORY CARDS -->
    <!-- ========================================================= -->

    <section class="max-w-7xl mx-auto px-4 py-6 w-full">

        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">


            <!-- THUÊ TRỌ -->
            <a href="${pageContext.request.contextPath}/thue-tro"
                class="bg-white rounded-xl p-5 shadow-sm border border-gray-100 hover:shadow-md transition flex items-center space-x-4">

                <div
                    class="w-14 h-14 rounded-full bg-orange-100 flex items-center justify-center text-brand text-2xl flex-shrink-0">
                    <i class="fa-solid fa-house-user"></i>
                </div>


                <div>

                    <h3 class="font-bold text-gray-800 text-base">
                        Xem Danh Mục: Thuê Trọ
                    </h3>

                    <p class="text-xs text-gray-500 mt-0.5">
                        Phòng trọ giá rẻ, ký túc xá, phòng có gác lửng
                    </p>

                </div>

            </a>


            <!-- THUÊ CĂN HỘ -->
            <a href="${pageContext.request.contextPath}/thue-can-ho"
                class="bg-white rounded-xl p-5 shadow-sm border border-gray-100 hover:shadow-md transition flex items-center space-x-4">

                <div
                    class="w-14 h-14 rounded-full bg-blue-100 flex items-center justify-center text-blue-600 text-2xl flex-shrink-0">
                    <i class="fa-solid fa-building"></i>
                </div>


                <div>

                    <h3 class="font-bold text-gray-800 text-base">
                        Xem Danh Mục: Thuê Căn Hộ
                    </h3>

                    <p class="text-xs text-gray-500 mt-0.5">
                        Chung cư mini, căn hộ dịch vụ Studio full đồ
                    </p>

                </div>

            </a>

        </div>

    </section>


    <!-- ========================================================= -->
    <!-- FEATURED LISTINGS -->
    <!-- ========================================================= -->

    <main class="max-w-7xl mx-auto px-4 py-4 w-full flex-grow">

        <div class="flex justify-between items-center mb-4">

            <h2 class="text-lg font-bold text-gray-800">
                Tin đăng tổng hợp nổi bật
            </h2>

        </div>


        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">


            <% for (Map<String, String> room : roomList) { %>

                <!-- ROOM CARD -->
                <div
                    class="bg-white rounded-xl overflow-hidden shadow-sm border border-gray-100 flex flex-col justify-between">

                    <div>

                        <!-- IMAGE -->
                        <div class="relative">

                            <img src="<%= room.get(" img") %>"
                            alt="<%= room.get("title") %>"
                                class="w-full h-44 object-cover"
                                >

                                <span
                                    class="absolute top-2 left-2 bg-brand text-white text-[10px] font-bold px-2 py-0.5 rounded-full">
                                    <%= room.get("tag") %>
                                </span>

                        </div>


                        <!-- CONTENT -->
                        <div class="p-3">

                            <h3 class="text-xs font-bold line-clamp-2 text-gray-800 mb-2">
                                <%= room.get("title") %>
                            </h3>


                            <div class="flex items-baseline justify-between mb-1">

                                <span class="text-brand font-black text-sm">
                                    <%= room.get("price") %>
                                </span>

                                <span class="text-xs text-gray-500">
                                    <%= room.get("area") %>
                                </span>

                            </div>


                            <p class="text-[11px] text-gray-500 truncate">

                                <i class="fa-solid fa-location-dot mr-1"></i>

                                <%= room.get("location") %>

                            </p>

                        </div>

                    </div>


                    <!-- FOOTER CARD -->
                    <div
                        class="px-3 py-2 border-t border-gray-50 text-[10px] text-gray-400 flex justify-between bg-gray-50/50">

                        <span>
                            <%= room.get("time") %>
                        </span>

                        <a href="${pageContext.request.contextPath}/rooms" class="text-brand font-semibold">
                            Chi tiết →
                        </a>

                    </div>

                </div>

                <% } %>

        </div>

    </main>


    <!-- ========================================================= -->
    <!-- FOOTER -->
    <!-- ========================================================= -->

    <footer class="bg-white border-t border-gray-200 mt-auto py-6 text-gray-600 text-xs text-center">

        <p>
            © 2026 NHÀ TỐT - Hệ thống quản lý thuê trọ thông minh.
        </p>

    </footer>

</body>

</html>
<<<<<<< HEAD
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="vi">

    <head>
        <meta charset="UTF-8">
        <title>${tieuDe} - Room Connect</title>
    </head>

    <body>

        <%-- Vi du: bien "tieuDe" duoc PageController.java truyen sang qua Model --%>
            <h1>${tieuDe}</h1>

            <%-- TODO: noi dung trang Trang chủ --%>

    </body>

    </html>
=======
>>>>>>> origin/main
