<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%
    List<Map<String, String>> roomList = new ArrayList<>();

    Map<String, String> room1 = new HashMap<>();
    room1.put("title", "Phòng trọ cao cấp full nội thất gần Đại học FPT, giờ giấc tự do");
    room1.put("price", "3.2 Triệu/tháng");
    room1.put("area", "25 m²");
    room1.put("location", "Quận 9, TP. Hồ Chí Minh");
    room1.put("img", "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?auto=format&fit=crop&w=900&q=85");
    room1.put("tag", "Đã xác minh");
    room1.put("time", "15 phút trước");
    roomList.add(room1);

    Map<String, String> room2 = new HashMap<>();
    room2.put("title", "Căn hộ Studio mới 100%, không chung chủ, có ban công thoáng mát");
    room2.put("price", "4.5 Triệu/tháng");
    room2.put("area", "35 m²");
    room2.put("location", "Quận Bình Thạnh, TP. Hồ Chí Minh");
    room2.put("img", "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?auto=format&fit=crop&w=900&q=85");
    room2.put("tag", "Chủ trọ uy tín");
    room2.put("time", "1 giờ trước");
    roomList.add(room2);

    Map<String, String> room3 = new HashMap<>();
    room3.put("title", "Ký túc xá cao cấp cho sinh viên, free điện nước wifi, thang máy");
    room3.put("price", "1.5 Triệu/tháng");
    room3.put("area", "18 m²");
    room3.put("location", "Quận Cầu Giấy, Hà Nội");
    room3.put("img", "https://images.unsplash.com/photo-1555854877-bab0e564b8d5?auto=format&fit=crop&w=900&q=85");
    room3.put("tag", "Đã xác minh");
    room3.put("time", "3 giờ trước");
    roomList.add(room3);

    Map<String, String> room4 = new HashMap<>();
    room4.put("title", "Phòng mới đầy đủ nội thất, có ban công, giờ giấc tự do");
    room4.put("price", "3.8 Triệu/tháng");
    room4.put("area", "28 m²");
    room4.put("location", "Quận Gò Vấp, TP. Hồ Chí Minh");
    room4.put("img", "https://images.unsplash.com/photo-1493809842364-78817add7ffb?auto=format&fit=crop&w=900&q=85");
    room4.put("tag", "Chính chủ");
    room4.put("time", "5 giờ trước");
    roomList.add(room4);

    Map<String, String> room5 = new HashMap<>();
    room5.put("title", "Căn hộ mini cao cấp, full nội thất, bảo vệ 24/7");
    room5.put("price", "5.2 Triệu/tháng");
    room5.put("area", "40 m²");
    room5.put("location", "Quận 7, TP. Hồ Chí Minh");
    room5.put("img", "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?auto=format&fit=crop&w=900&q=85");
    room5.put("tag", "Premium");
    room5.put("time", "8 giờ trước");
    roomList.add(room5);

    Map<String, String> room6 = new HashMap<>();
    room6.put("title", "Phòng trọ sinh viên gần trường, có máy lạnh và máy giặt");
    room6.put("price", "2.7 Triệu/tháng");
    room6.put("area", "22 m²");
    room6.put("location", "Thủ Đức, TP. Hồ Chí Minh");
    room6.put("img", "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=900&q=85");
    room6.put("tag", "Đã xác minh");
    room6.put("time", "12 giờ trước");
    roomList.add(room6);
%>

<!DOCTYPE html>
<html lang="vi">

<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Nhà Tốt - Tìm phòng trọ nhanh chóng</title>

    <!-- Tailwind -->
    <script src="https://cdn.tailwindcss.com"></script>

    <!-- Font Awesome -->
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
    >

    <!-- Font đẹp -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

    <link
            href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@400;500;600;700;800;900&display=swap"
            rel="stylesheet"
    >

    <script>

        tailwind.config = {

            theme: {

                extend: {

                    colors: {

                        brand: '#ff641e',
                        brandDark: '#e9510d',

                        cream: '#fff7f1',

                        navy: '#172033'

                    },

                    fontFamily: {

                        sans: ['Be Vietnam Pro', 'sans-serif']

                    },

                    boxShadow: {

                        soft: '0 8px 30px rgba(38, 32, 25, .08)',

                        card: '0 5px 20px rgba(38, 32, 25, .07)'

                    }

                }

            }

        }

    </script>

    <style>

        * {
            box-sizing: border-box;
        }

        body {
            font-family: 'Be Vietnam Pro', sans-serif;
            background: #f7f7f6;
            color: #172033;
        }

        .hide-scroll::-webkit-scrollbar {
            display: none;
        }

        .hide-scroll {
            -ms-overflow-style: none;
            scrollbar-width: none;
        }

        .hero-pattern {
            background:
                    radial-gradient(circle at 10% 20%, rgba(255,255,255,.13) 0 70px, transparent 71px),
                    radial-gradient(circle at 90% 30%, rgba(255,255,255,.10) 0 100px, transparent 101px),
                    linear-gradient(135deg, #ff7b22 0%, #ff641e 48%, #f4520b 100%);
        }

        .hero-shape {
            position: absolute;
            border-radius: 999px;
            background: rgba(255,255,255,.08);
            filter: blur(1px);
        }

        .category-card {
            transition: all .25s ease;
        }

        .category-card:hover {
            transform: translateY(-5px);
        }

        .room-card {
            transition: all .25s ease;
        }

        .room-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 16px 35px rgba(38,32,25,.12);
        }

        .room-image {
            transition: transform .45s ease;
        }

        .room-card:hover .room-image {
            transform: scale(1.05);
        }

        .gradient-border {
            position: relative;
        }

        .gradient-border::before {
            content: "";
            position: absolute;
            inset: 0;
            border-radius: inherit;
            padding: 1px;
            background: linear-gradient(
                    135deg,
                    rgba(255,100,30,.35),
                    rgba(255,255,255,.7)
            );

            -webkit-mask:
                    linear-gradient(#fff 0 0) content-box,
                    linear-gradient(#fff 0 0);

            -webkit-mask-composite: xor;
            mask-composite: exclude;

            pointer-events: none;
        }

    </style>

</head>


<body class="min-h-screen">


<!-- ========================================================= -->
<!-- HEADER -->
<!-- ========================================================= -->

<header class="sticky top-0 z-50 bg-white/95 backdrop-blur-md border-b border-gray-200">

    <div class="max-w-[1180px] mx-auto px-4">

        <div class="h-[64px] flex items-center gap-3">

            <!-- MENU -->

            <button
                    class="w-10 h-10 rounded-full flex items-center justify-center hover:bg-orange-50 transition"
            >
                <i class="fa-solid fa-bars text-lg"></i>
            </button>


            <!-- LOGO -->

            <a
                    href="${pageContext.request.contextPath}/home"
                    class="shrink-0"
            >

                <div class="text-[24px] font-black italic tracking-[-1.5px] text-brand">
                    NHÀTỐT
                </div>

            </a>


            <!-- LOCATION -->

            <button
                    class="hidden md:flex items-center gap-2 bg-gray-100 hover:bg-orange-50 px-4 py-2.5 rounded-full text-sm font-semibold transition"
            >

                <i class="fa-solid fa-location-dot text-brand"></i>

                <span>TP Hồ Chí Minh</span>

                <i class="fa-solid fa-chevron-down text-[10px]"></i>

            </button>


            <!-- SEARCH -->

            <form
                    action="${pageContext.request.contextPath}/rooms"
                    method="GET"
                    class="flex-1 hidden sm:flex"
            >

                <div class="relative w-full">

                    <i
                            class="fa-solid fa-magnifying-glass absolute left-4 top-1/2 -translate-y-1/2 text-gray-400"
                    ></i>

                    <input
                            type="text"
                            name="keyword"
                            placeholder="Tìm phòng trọ, căn hộ..."
                            class="w-full h-11 rounded-full bg-gray-100 pl-11 pr-14 text-sm outline-none border border-transparent focus:border-orange-200 focus:bg-white transition"
                    >

                    <button
                            type="submit"
                            class="absolute right-1 top-1/2 -translate-y-1/2 w-9 h-9 rounded-full bg-brand text-white hover:bg-brandDark transition"
                    >

                        <i class="fa-solid fa-arrow-right"></i>

                    </button>

                </div>

            </form>


            <!-- RIGHT -->

            <div class="flex items-center gap-1 ml-auto">

                <button
                        class="hidden md:flex w-10 h-10 items-center justify-center rounded-full hover:bg-orange-50"
                >
                    <i class="fa-regular fa-heart text-lg"></i>
                </button>

                <button
                        class="hidden md:flex w-10 h-10 items-center justify-center rounded-full hover:bg-orange-50"
                >
                    <i class="fa-regular fa-bell text-lg"></i>
                </button>


                <a
                        href="${pageContext.request.contextPath}/login"
                        class="hidden sm:flex px-4 py-2.5 rounded-full border border-gray-200 text-sm font-bold hover:border-orange-300 hover:text-brand transition"
                >
                    Đăng nhập
                </a>


                <a
                        href="${pageContext.request.contextPath}/login"
                        class="flex items-center gap-2 bg-brand text-white px-4 py-2.5 rounded-full text-sm font-bold hover:bg-brandDark transition shadow-sm"
                >

                    <i class="fa-solid fa-pen-to-square"></i>

                    <span>Đăng tin</span>

                </a>

            </div>

        </div>

    </div>

</header>


<!-- ========================================================= -->
<!-- HERO -->
<!-- ========================================================= -->

<section class="hero-pattern relative overflow-hidden">

    <div class="hero-shape w-72 h-72 -left-20 top-20"></div>

    <div class="hero-shape w-96 h-96 right-[-100px] top-[-100px]"></div>

    <div class="hero-shape w-40 h-40 right-[25%] bottom-[-70px]"></div>


    <div class="relative max-w-[1000px] mx-auto px-4 pt-12 pb-20">

        <!-- SMALL BADGE -->

        <div class="flex justify-center">

            <div class="inline-flex items-center gap-2 bg-white/15 border border-white/20 text-white px-4 py-2 rounded-full text-xs font-semibold backdrop-blur">

                <i class="fa-solid fa-house"></i>

                Tìm phòng trọ dễ dàng hơn

            </div>

        </div>


        <!-- TITLE -->

        <h1
                class="text-center text-white text-4xl md:text-5xl font-black tracking-[-1.5px] mt-5"
        >
            Tìm phòng vừa ý,
            <span class="text-yellow-200">giá hợp lý!</span>
        </h1>


        <p class="text-center text-orange-50 mt-3 text-sm md:text-base">

            Hàng nghìn phòng trọ và căn hộ đang chờ bạn khám phá

        </p>


        <!-- SEARCH BOX -->

        <div
                class="mt-8 bg-white rounded-[22px] p-3 shadow-2xl max-w-[900px] mx-auto"
        >

            <form
                    action="${pageContext.request.contextPath}/rooms"
                    method="GET"
                    class="flex flex-col md:flex-row gap-2"
            >

                <div class="flex-1 relative">

                    <i
                            class="fa-solid fa-magnifying-glass absolute left-4 top-1/2 -translate-y-1/2 text-gray-400"
                    ></i>

                    <input
                            type="text"
                            name="keyword"
                            placeholder="Nhập khu vực, tên đường, trường học..."
                            class="w-full h-12 bg-gray-50 rounded-xl pl-11 pr-4 outline-none text-sm focus:bg-orange-50 transition"
                    >

                </div>


                <select
                        name="type"
                        class="h-12 md:w-40 bg-gray-50 rounded-xl px-4 outline-none text-sm font-semibold"
                >

                    <option value="">Loại phòng</option>
                    <option value="room">Phòng trọ</option>
                    <option value="ktx">Ký túc xá</option>
                    <option value="apartment">Căn hộ</option>

                </select>


                <button
                        type="submit"
                        class="h-12 px-8 rounded-xl bg-brand hover:bg-brandDark text-white font-bold transition"
                >

                    Tìm phòng

                </button>

            </form>


            <!-- QUICK SEARCH -->

            <div class="flex flex-wrap items-center gap-2 mt-3 px-2">

                <span class="text-[11px] text-gray-400">
                    Gợi ý:
                </span>

                <a
                        href="${pageContext.request.contextPath}/rooms?keyword=FPT"
                        class="px-3 py-1.5 rounded-full bg-orange-50 text-orange-600 text-[11px] font-semibold hover:bg-orange-100"
                >
                    Gần Đại học FPT
                </a>

                <a
                        href="${pageContext.request.contextPath}/rooms?keyword=Thu Duc"
                        class="px-3 py-1.5 rounded-full bg-blue-50 text-blue-600 text-[11px] font-semibold hover:bg-blue-100"
                >
                    Thủ Đức
                </a>

                <a
                        href="${pageContext.request.contextPath}/rooms?keyword=Binh Thanh"
                        class="px-3 py-1.5 rounded-full bg-purple-50 text-purple-600 text-[11px] font-semibold hover:bg-purple-100"
                >
                    Bình Thạnh
                </a>

                <a
                        href="${pageContext.request.contextPath}/rooms?keyword=Go Vap"
                        class="px-3 py-1.5 rounded-full bg-green-50 text-green-600 text-[11px] font-semibold hover:bg-green-100"
                >
                    Gò Vấp
                </a>

            </div>

        </div>

    </div>

</section>


<!-- ========================================================= -->
<!-- CATEGORY -->
<!-- ========================================================= -->

<section class="max-w-[1060px] mx-auto px-4 -mt-9 relative z-10">

    <div
            class="bg-white rounded-[22px] shadow-soft border border-gray-100 grid grid-cols-2 md:grid-cols-4 overflow-hidden"
    >


        <!-- THUÊ TRỌ -->

        <a
                href="${pageContext.request.contextPath}/thue-tro"
                class="category-card p-5 md:p-6 flex items-center gap-4 border-b md:border-b-0 md:border-r border-gray-100 hover:bg-orange-50"
        >

            <div
                    class="w-12 h-12 shrink-0 rounded-2xl bg-orange-100 text-brand flex items-center justify-center text-xl"
            >

                <i class="fa-solid fa-house"></i>

            </div>

            <div>

                <h3 class="font-extrabold text-sm">
                    Thuê trọ
                </h3>

                <p class="text-[11px] text-gray-500 mt-1">
                    Phòng trọ, KTX
                </p>

            </div>

        </a>


        <!-- CĂN HỘ -->

        <a
                href="${pageContext.request.contextPath}/thue-can-ho"
                class="category-card p-5 md:p-6 flex items-center gap-4 border-b md:border-b-0 md:border-r border-gray-100 hover:bg-blue-50"
        >

            <div
                    class="w-12 h-12 shrink-0 rounded-2xl bg-blue-100 text-blue-600 flex items-center justify-center text-xl"
            >

                <i class="fa-solid fa-building"></i>

            </div>

            <div>

                <h3 class="font-extrabold text-sm">
                    Thuê căn hộ
                </h3>

                <p class="text-[11px] text-gray-500 mt-1">
                    Studio, chung cư
                </p>

            </div>

        </a>


        <!-- TÌM QUANH ĐÂY -->

        <a
                href="${pageContext.request.contextPath}/rooms"
                class="category-card p-5 md:p-6 flex items-center gap-4 border-r border-gray-100 hover:bg-green-50"
        >

            <div
                    class="w-12 h-12 shrink-0 rounded-2xl bg-green-100 text-green-600 flex items-center justify-center text-xl"
            >

                <i class="fa-solid fa-map-location-dot"></i>

            </div>

            <div>

                <h3 class="font-extrabold text-sm">
                    Tìm quanh đây
                </h3>

                <p class="text-[11px] text-gray-500 mt-1">
                    Tìm theo bản đồ
                </p>

            </div>

        </a>


        <!-- LỌC NÂNG CAO -->

        <a
                href="${pageContext.request.contextPath}/rooms"
                class="category-card p-5 md:p-6 flex items-center gap-4 hover:bg-purple-50"
        >

            <div
                    class="w-12 h-12 shrink-0 rounded-2xl bg-purple-100 text-purple-600 flex items-center justify-center text-xl"
            >

                <i class="fa-solid fa-filter"></i>

            </div>

            <div>

                <h3 class="font-extrabold text-sm">
                    Lọc nâng cao
                </h3>

                <p class="text-[11px] text-gray-500 mt-1">
                    Theo tiện ích
                </p>

            </div>

        </a>

    </div>

</section>


<!-- ========================================================= -->
<!-- MAIN -->
<!-- ========================================================= -->

<main class="max-w-[1060px] mx-auto px-4 pt-7 pb-14">


    <!-- ===================================================== -->
    <!-- AI SEARCH -->
    <!-- ===================================================== -->

    <section
            class="gradient-border relative overflow-hidden bg-gradient-to-r from-orange-50 via-white to-purple-50 rounded-[22px] p-5 md:p-6 mb-8 shadow-card"
    >

        <div class="absolute right-[-30px] top-[-40px] w-40 h-40 bg-orange-100 rounded-full blur-3xl opacity-70"></div>


        <div class="relative flex flex-col md:flex-row items-center justify-between gap-5">

            <div class="flex items-center gap-4">

                <div
                        class="w-14 h-14 shrink-0 rounded-2xl bg-gradient-to-br from-orange-500 to-red-500 text-white flex items-center justify-center text-xl shadow-lg"
                >

                    <i class="fa-solid fa-robot"></i>

                </div>


                <div>

                    <div class="flex items-center gap-2">

                        <h2 class="font-black text-lg">
                            AI tìm phòng thông minh
                        </h2>

                        <span
                                class="px-2 py-0.5 rounded-full bg-orange-100 text-orange-600 text-[9px] font-black"
                        >
                            AI
                        </span>

                    </div>

                    <p class="text-xs text-gray-500 mt-1">
                        Mô tả nhu cầu bằng tiếng Việt, AI sẽ gợi ý phòng phù hợp.
                    </p>

                </div>

            </div>


            <a
                    href="${pageContext.request.contextPath}/rooms?ai=true"
                    class="shrink-0 bg-brand hover:bg-brandDark text-white px-5 py-3 rounded-xl text-xs font-bold transition shadow-md"
            >

                <i class="fa-solid fa-wand-magic-sparkles mr-2"></i>

                Tìm phòng bằng AI

            </a>

        </div>

    </section>


    <!-- ===================================================== -->
    <!-- TITLE -->
    <!-- ===================================================== -->

    <div class="flex items-end justify-between mb-4">

        <div>

            <h2 class="text-xl font-black tracking-tight">
                Tin cho thuê mới đăng
            </h2>

            <p class="text-xs text-gray-500 mt-1">
                Những phòng mới nhất trên hệ thống
            </p>

        </div>


        <a
                href="${pageContext.request.contextPath}/rooms"
                class="text-brand text-xs font-bold hover:underline"
        >

            Xem thêm

            <i class="fa-solid fa-arrow-right ml-1"></i>

        </a>

    </div>


    <!-- ===================================================== -->
    <!-- ROOM GRID -->
    <!-- ===================================================== -->

    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-5">


        <% for (Map<String, String> room : roomList) { %>


        <article
                class="room-card bg-white rounded-[18px] overflow-hidden border border-gray-100 shadow-card"
        >

            <!-- IMAGE -->

            <div class="relative h-52 overflow-hidden bg-gray-200">

                <img
                        src="<%= room.get("img") %>"
                        alt="<%= room.get("title") %>"
                        class="room-image w-full h-full object-cover"
                >


                <!-- DARK GRADIENT -->

                <div
                        class="absolute inset-x-0 bottom-0 h-20 bg-gradient-to-t from-black/50 to-transparent"
                ></div>


                <!-- TAG -->

                <span
                        class="absolute top-3 left-3 bg-brand text-white px-2.5 py-1 rounded-lg text-[9px] font-black shadow"
                >

                    <%= room.get("tag") %>

                </span>


                <!-- HEART -->

                <button
                        type="button"
                        class="absolute top-3 right-3 w-9 h-9 rounded-full bg-white/90 backdrop-blur flex items-center justify-center text-gray-700 hover:text-red-500 hover:bg-white transition shadow-sm"
                        onclick="toggleFavorite(this)"
                >

                    <i class="fa-regular fa-heart"></i>

                </button>


                <!-- TIME -->

                <span
                        class="absolute bottom-3 left-3 text-white text-[10px] font-semibold"
                >

                    <i class="fa-regular fa-clock mr-1"></i>

                    <%= room.get("time") %>

                </span>

            </div>


            <!-- CONTENT -->

            <div class="p-4">

                <h3
                        class="font-bold text-sm leading-5 line-clamp-2 min-h-[40px]"
                >

                    <%= room.get("title") %>

                </h3>


                <!-- PRICE -->

                <div class="flex items-center justify-between mt-3">

                    <span class="text-brand font-black text-base">

                        <%= room.get("price") %>

                    </span>

                    <span
                            class="text-xs font-semibold text-gray-500 bg-gray-100 px-2 py-1 rounded-md"
                    >

                        <%= room.get("area") %>

                    </span>

                </div>


                <!-- LOCATION -->

                <div
                        class="flex items-center gap-1.5 mt-3 text-gray-500 text-[11px]"
                >

                    <i class="fa-solid fa-location-dot text-brand"></i>

                    <span class="truncate">
                        <%= room.get("location") %>
                    </span>

                </div>


                <!-- FEATURES -->

                <div class="flex gap-2 mt-3 flex-wrap">

                    <span
                            class="bg-green-50 text-green-600 px-2 py-1 rounded-md text-[9px] font-semibold"
                    >
                        <i class="fa-solid fa-wifi mr-1"></i>
                        Wifi
                    </span>

                    <span
                            class="bg-blue-50 text-blue-600 px-2 py-1 rounded-md text-[9px] font-semibold"
                    >
                        <i class="fa-solid fa-snowflake mr-1"></i>
                        Máy lạnh
                    </span>

                    <span
                            class="bg-purple-50 text-purple-600 px-2 py-1 rounded-md text-[9px] font-semibold"
                    >
                        <i class="fa-solid fa-clock mr-1"></i>
                        Tự do
                    </span>

                </div>

            </div>


            <!-- FOOTER -->

            <div class="px-4 pb-4">

                <a
                        href="${pageContext.request.contextPath}/rooms"
                        class="block w-full text-center py-2.5 rounded-xl bg-orange-50 text-brand hover:bg-brand hover:text-white text-xs font-bold transition"
                >

                    Xem chi tiết

                    <i class="fa-solid fa-arrow-right ml-1"></i>

                </a>

            </div>

        </article>


        <% } %>


    </div>


    <!-- ===================================================== -->
    <!-- DISCOVER -->
    <!-- ===================================================== -->

    <section class="mt-10">

        <div class="flex items-end justify-between mb-4">

            <div>

                <h2 class="text-xl font-black">
                    Khám phá theo nhu cầu
                </h2>

                <p class="text-xs text-gray-500 mt-1">
                    Chọn loại phòng phù hợp với bạn
                </p>

            </div>

        </div>


        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">


            <a
                    href="${pageContext.request.contextPath}/rooms?type=student"
                    class="group relative overflow-hidden rounded-2xl p-5 bg-gradient-to-br from-blue-500 to-indigo-600 text-white min-h-[150px]"
            >

                <div class="relative z-10">

                    <span class="text-2xl">
                        🎓
                    </span>

                    <h3 class="font-black text-lg mt-3">
                        Phòng cho sinh viên
                    </h3>

                    <p class="text-blue-100 text-xs mt-1">
                        Giá hợp lý, gần trường học
                    </p>

                </div>

                <i
                        class="fa-solid fa-graduation-cap absolute right-5 bottom-4 text-7xl opacity-10 group-hover:scale-110 transition"
                ></i>

            </a>


            <a
                    href="${pageContext.request.contextPath}/rooms?type=apartment"
                    class="group relative overflow-hidden rounded-2xl p-5 bg-gradient-to-br from-orange-400 to-red-500 text-white min-h-[150px]"
            >

                <div class="relative z-10">

                    <span class="text-2xl">
                        🏢
                    </span>

                    <h3 class="font-black text-lg mt-3">
                        Căn hộ tiện nghi
                    </h3>

                    <p class="text-orange-100 text-xs mt-1">
                        Studio, căn hộ mini, full nội thất
                    </p>

                </div>

                <i
                        class="fa-solid fa-building absolute right-5 bottom-4 text-7xl opacity-10 group-hover:scale-110 transition"
                ></i>

            </a>


            <a
                    href="${pageContext.request.contextPath}/rooms?verified=true"
                    class="group relative overflow-hidden rounded-2xl p-5 bg-gradient-to-br from-emerald-400 to-teal-600 text-white min-h-[150px]"
            >

                <div class="relative z-10">

                    <span class="text-2xl">
                        🛡️
                    </span>

                    <h3 class="font-black text-lg mt-3">
                        Phòng đã xác minh
                    </h3>

                    <p class="text-emerald-100 text-xs mt-1">
                        Ưu tiên tin có eKYC chính chủ
                    </p>

                </div>

                <i
                        class="fa-solid fa-shield-halved absolute right-5 bottom-4 text-7xl opacity-10 group-hover:scale-110 transition"
                ></i>

            </a>

        </div>

    </section>


    <!-- ===================================================== -->
    <!-- BLOG -->
    <!-- ===================================================== -->

    <section class="mt-10">

        <div class="flex justify-between items-end mb-4">

            <div>

                <h2 class="text-xl font-black">
                    Kinh nghiệm thuê trọ
                </h2>

                <p class="text-xs text-gray-500 mt-1">
                    Một vài điều bạn nên biết trước khi thuê phòng
                </p>

            </div>

            <a
                    href="#"
                    class="text-brand text-xs font-bold"
            >
                Xem thêm →
            </a>

        </div>


        <div class="grid grid-cols-1 md:grid-cols-3 gap-5">


            <!-- BLOG 1 -->

            <article
                    class="bg-white rounded-2xl overflow-hidden shadow-card border border-gray-100"
            >

                <div class="h-44 overflow-hidden">

                    <img
                            src="https://images.unsplash.com/photo-1560448204-603b3fc33ddc?auto=format&fit=crop&w=700&q=85"
                            class="w-full h-full object-cover hover:scale-105 transition duration-500"
                    >

                </div>

                <div class="p-4">

                    <span
                            class="text-[9px] font-black text-brand uppercase"
                    >
                        Kinh nghiệm
                    </span>

                    <h3 class="font-black text-sm mt-2 leading-5">
                        7 điều cần kiểm tra trước khi thuê phòng trọ
                    </h3>

                    <p class="text-xs text-gray-500 mt-2 leading-5">
                        Những điều người thuê nên kiểm tra để tránh gặp rắc rối khi phòng.
                    </p>

                </div>

            </article>


            <!-- BLOG 2 -->

            <article
                    class="bg-white rounded-2xl overflow-hidden shadow-card border border-gray-100"
            >

                <div class="h-44 overflow-hidden">

                    <img
                            src="https://images.unsplash.com/photo-1493663284031-b7e3aefcae8e?auto=format&fit=crop&w=700&q=85"
                            class="w-full h-full object-cover hover:scale-105 transition duration-500"
                    >

                </div>

                <div class="p-4">

                    <span
                            class="text-[9px] font-black text-blue-600 uppercase"
                    >
                        Tài chính
                    </span>

                    <h3 class="font-black text-sm mt-2 leading-5">
                        Cách tính tổng chi phí khi thuê phòng
                    </h3>

                    <p class="text-xs text-gray-500 mt-2 leading-5">
                        Tiền phòng chỉ là một phần. Hãy tính cả điện, nước, wifi và phí dịch vụ.
                    </p>

                </div>

            </article>


            <!-- BLOG 3 -->

            <article
                    class="bg-white rounded-2xl overflow-hidden shadow-card border border-gray-100"
            >

                <div class="h-44 overflow-hidden">

                    <img
                            src="https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=700&q=85"
                            class="w-full h-full object-cover hover:scale-105 transition duration-500"
                    >

                </div>

                <div class="p-4">

                    <span
                            class="text-[9px] font-black text-green-600 uppercase"
                    >
                        An toàn
                    </span>

                    <h3 class="font-black text-sm mt-2 leading-5">
                        Nhận biết tin đăng phòng trọ đáng tin cậy
                    </h3>

                    <p class="text-xs text-gray-500 mt-2 leading-5">
                        Ưu tiên bài đăng có eKYC, thông tin rõ ràng và chủ trọ xác minh.
                    </p>

                </div>

            </article>

        </div>

    </section>


</main>


<!-- ========================================================= -->
<!-- LANDLORD CTA -->
<!-- ========================================================= -->

<section class="bg-gradient-to-r from-orange-50 via-[#fff4eb] to-orange-100 border-y border-orange-100">

    <div class="max-w-[1060px] mx-auto px-4 py-9 flex flex-col md:flex-row items-center justify-between gap-5">

        <div>

            <h2 class="text-2xl font-black">
                Bạn là chủ trọ?
            </h2>

            <p class="text-sm text-gray-600 mt-2">
                Đăng tin để tiếp cận hàng nghìn người đang tìm phòng mỗi ngày.
            </p>

        </div>


        <a
                href="${pageContext.request.contextPath}/login"
                class="bg-brand hover:bg-brandDark text-white px-7 py-3.5 rounded-xl font-bold text-sm shadow-lg transition"
        >

            <i class="fa-solid fa-plus mr-2"></i>

            Đăng tin cho thuê

        </a>

    </div>

</section>


<!-- ========================================================= -->
<!-- FOOTER -->
<!-- ========================================================= -->

<footer class="bg-[#172033] text-white">

    <div class="max-w-[1060px] mx-auto px-4 py-12">

        <div class="grid grid-cols-1 md:grid-cols-4 gap-9">


            <!-- BRAND -->

            <div>

                <div
                        class="text-2xl font-black italic text-orange-400"
                >
                    NHÀTỐT
                </div>

                <p class="text-gray-400 text-xs leading-6 mt-4 max-w-[250px]">
                    Hệ thống tìm kiếm và quản lý phòng trọ thông minh.
                    Kết nối người thuê với chủ trọ nhanh chóng và an toàn.
                </p>

            </div>


            <!-- USER -->

            <div>

                <h3 class="font-bold text-sm">
                    Người tìm trọ
                </h3>

                <div class="mt-4 space-y-3 text-xs text-gray-400">

                    <a href="${pageContext.request.contextPath}/rooms" class="block hover:text-orange-400">
                        Tìm phòng trọ
                    </a>

                    <a href="${pageContext.request.contextPath}/thue-can-ho" class="block hover:text-orange-400">
                        Tìm căn hộ
                    </a>

                    <a href="#" class="block hover:text-orange-400">
                        Phòng yêu thích
                    </a>

                    <a href="#" class="block hover:text-orange-400">
                        Kinh nghiệm thuê trọ
                    </a>

                </div>

            </div>


            <!-- LANDLORD -->

            <div>

                <h3 class="font-bold text-sm">
                    Chủ trọ
                </h3>

                <div class="mt-4 space-y-3 text-xs text-gray-400">

                    <a href="${pageContext.request.contextPath}/login" class="block hover:text-orange-400">
                        Đăng tin cho thuê
                    </a>

                    <a href="#" class="block hover:text-orange-400">
                        Quản lý phòng
                    </a>

                    <a href="#" class="block hover:text-orange-400">
                        Quản lý người thuê
                    </a>

                    <a href="#" class="block hover:text-orange-400">
                        Gói Premium
                    </a>

                </div>

            </div>


            <!-- CONTACT -->

            <div>

                <h3 class="font-bold text-sm">
                    Liên hệ
                </h3>

                <div class="mt-4 space-y-3 text-xs text-gray-400">

                    <p>
                        <i class="fa-solid fa-envelope text-orange-400 w-5"></i>
                        support@nhatot.vn
                    </p>

                    <p>
                        <i class="fa-solid fa-phone text-orange-400 w-5"></i>
                        1900 0000
                    </p>

                    <p>
                        <i class="fa-solid fa-location-dot text-orange-400 w-5"></i>
                        TP. Hồ Chí Minh
                    </p>

                </div>


                <div class="flex gap-2 mt-5">

                    <a
                            href="#"
                            class="w-9 h-9 rounded-full bg-white/10 flex items-center justify-center hover:bg-orange-500 transition"
                    >
                        <i class="fa-brands fa-facebook-f"></i>
                    </a>

                    <a
                            href="#"
                            class="w-9 h-9 rounded-full bg-white/10 flex items-center justify-center hover:bg-orange-500 transition"
                    >
                        <i class="fa-brands fa-youtube"></i>
                    </a>

                    <a
                            href="#"
                            class="w-9 h-9 rounded-full bg-white/10 flex items-center justify-center hover:bg-orange-500 transition"
                    >
                        <i class="fa-brands fa-tiktok"></i>
                    </a>

                </div>

            </div>

        </div>


        <div class="border-t border-white/10 mt-10 pt-6 text-center">

            <p class="text-[11px] text-gray-500">
                © 2026 NHÀ TỐT - Hệ thống quản lý thuê trọ thông minh.
            </p>

        </div>

    </div>

</footer>


<!-- ========================================================= -->
<!-- AI FLOAT BUTTON -->
<!-- ========================================================= -->

<a
        href="${pageContext.request.contextPath}/rooms?ai=true"
        class="fixed right-5 bottom-5 w-14 h-14 rounded-full bg-brand hover:bg-brandDark text-white flex items-center justify-center shadow-2xl z-40 hover:scale-110 transition"
        title="AI tìm phòng"
>

    <i class="fa-solid fa-robot text-xl"></i>

</a>


<!-- ========================================================= -->
<!-- JAVASCRIPT -->
<!-- ========================================================= -->

<script>

    function toggleFavorite(button) {

        const icon = button.querySelector("i");

        if (icon.classList.contains("fa-regular")) {

            icon.classList.remove("fa-regular");
            icon.classList.add("fa-solid");

            button.classList.add("text-red-500");

        } else {

            icon.classList.remove("fa-solid");
            icon.classList.add("fa-regular");

            button.classList.remove("text-red-500");

        }

    }

</script>


</body>
</html>