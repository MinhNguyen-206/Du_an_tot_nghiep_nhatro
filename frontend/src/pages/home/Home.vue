<template>
  <div class="home">
    <!-- ================= HERO ================= -->
    <section class="hero">
      <Header />
      <div class="hero__overlay"></div>
      <div class="hero__content">
        <h1 class="hero__title">ROOM - CONNECT</h1>
        <p class="hero__tagline">Hỗ trợ tìm trọ - Dễ dàng chọn lựa - Ưu tiên nhu cầu</p>

        <form class="search-bar" @submit.prevent="handleSearch">
          <input
            v-model="searchForm.keyword"
            type="text"
            class="search-bar__input"
            placeholder="Từ khóa cần tìm..."
          />
          <select v-model="searchForm.category" class="search-bar__select">
            <option value="">--Danh mục--</option>
            <option v-for="c in categories" :key="c.id" :value="c.id">{{ c.label }}</option>
          </select>
          <select v-model="searchForm.province" class="search-bar__select">
            <option value="">Toàn quốc</option>
            <option value="hcm">TP. Hồ Chí Minh</option>
            <option value="hn">Hà Nội</option>
            <option value="dn">Đà Nẵng</option>
          </select>
          <button type="button" class="search-bar__filter" @click="showFilters = !showFilters">
            <FilterIcon /> Bộ lọc
          </button>
          <button type="submit" class="search-bar__submit">TÌM KIẾM</button>
        </form>

        <div class="popular-keywords">
          <span class="popular-keywords__label">Tìm kiếm phổ biến:</span>
          <button
            v-for="kw in popularKeywords"
            :key="kw"
            type="button"
            class="popular-keywords__pill"
            @click="applyKeyword(kw)"
          >
            {{ kw }}
          </button>
        </div>
      </div>
    </section>

    <!-- ================= DANH MỤC ================= -->
    <section class="section categories-section">
      <div class="section__head">
        <h2 class="section__title">DANH MỤC</h2>
        <div class="carousel-nav">
          <button class="carousel-nav__btn" @click="scrollCategories(-1)" aria-label="Trước">‹</button>
          <button class="carousel-nav__btn carousel-nav__btn--active" @click="scrollCategories(1)" aria-label="Sau">›</button>
        </div>
      </div>

      <div class="categories-track" ref="categoriesTrack">
        <div v-for="c in categories" :key="c.id" class="category-card">
          <img :src="c.image" :alt="c.label" class="category-card__img" />
          <span class="category-card__label">{{ c.label }}</span>
        </div>
      </div>

      <div class="dots">
        <span v-for="n in 3" :key="n" class="dots__item" :class="{ 'is-active': n === 1 }"></span>
      </div>
    </section>

    <!-- ================= KHU VỰC NỔI BẬT ================= -->
    <section class="section highlight-section">
      <div class="section__head">
        <h2 class="section__title">KHU VỰC NỔI BẬT</h2>
        <router-link to="/rooms" class="link-map">Xem bản đồ ⚑</router-link>
      </div>

      <div class="highlight-grid">
        <div class="highlight-card highlight-card--big">
          <img src="https://loremflickr.com/700/700/university,campus,dormitory?lock=11" alt="Gần các trường đại học" />
          <div class="highlight-card__overlay">
            <strong>Gần các trường đại học</strong>
            <span>1.240+ tin đăng mới</span>
          </div>
        </div>

        <div class="highlight-grid__right">
          <div class="highlight-card highlight-card--wide">
            <img src="https://loremflickr.com/700/400/cityscape,skyline,vietnam?lock=15" alt="Trung tâm thành phố" />
            <div class="highlight-card__overlay">
              <strong>Trung tâm thành phố</strong>
            </div>
          </div>
          <div class="highlight-grid__pair">
            <div class="highlight-card">
              <img src="https://loremflickr.com/350/350/bedroom,budget,simple?lock=60" alt="Phòng giá rẻ" />
              <div class="highlight-card__overlay">
                <strong>Phòng giá rẻ</strong>
              </div>
            </div>
            <div class="highlight-card">
              <img src="https://loremflickr.com/350/350/apartment,service,interior?lock=80" alt="Căn hộ dịch vụ" />
              <div class="highlight-card__overlay">
                <strong>Căn hộ dịch vụ</strong>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- ================= PHÒNG NỔI BẬT TRONG TUẦN ================= -->
    <section class="section featured-section">
      <div class="featured-card">
        <div class="featured-card__image">
          <span class="featured-card__badge">★ PHÒNG NỔI BẬT TRONG TUẦN</span>
          <img src="https://loremflickr.com/700/500/apartment,luxury,studio?lock=39" alt="Phòng nổi bật" />
        </div>
        <div class="featured-card__body">
          <span class="tag-premium">Premium Listing</span>
          <h3 class="featured-card__title">Căn hộ studio cao cấp - View Landmark 81</h3>
          <p class="featured-card__address">📍 Vinhomes Central Park - Bình Thạnh</p>
          <p class="featured-card__price">5,5 Tr <span>/tháng</span></p>
          <div class="featured-card__amenities">
            <span>🛏 1 PN</span>
            <span>▭ 35m²</span>
            <span>🏡 Ban công</span>
          </div>
          <router-link to="/rooms" class="btn-dark">XEM CHI TIẾT NGAY →</router-link>
        </div>
      </div>
    </section>

    <!-- ================= BÀI ĐĂNG MỚI NHẤT ================= -->
    <section class="section latest-section">
      <div class="section__head">
        <h2 class="section__title">BÀI ĐĂNG MỚI NHẤT</h2>
        <router-link to="/rooms" class="link-pill">Khám phá tất cả →</router-link>
      </div>

      <div class="post-list">
        <article v-for="post in paginatedPosts" :key="post.id" class="post-card">
          <div class="post-card__image">
            <span v-if="post.badge" class="post-card__badge" :class="post.badgeClass">{{ post.badge }}</span>
            <img :src="post.image" :alt="post.title" />
            <button
              class="post-card__fav"
              :class="{ 'is-active': post.favorite }"
              @click="toggleFavorite(post)"
              aria-label="Yêu thích"
            >
              ♥
            </button>
          </div>
          <div class="post-card__body">
            <div class="post-card__top">
              <h3 class="post-card__title">{{ post.title }}</h3>
              <span class="post-card__price">{{ post.price }}</span>
            </div>
            <p class="post-card__address">📍 {{ post.address }}</p>
            <hr class="post-card__divider" />
            <div class="post-card__amenities">
              <span v-if="post.wifi">📶 Wifi</span>
              <span v-if="post.aircon">❄ Điều hòa</span>
            </div>
          </div>
        </article>

        <p v-if="!paginatedPosts.length" class="empty-state">Chưa có bài đăng nào phù hợp.</p>
      </div>

      <!-- Phân trang -->
      <nav v-if="totalPages > 1" class="pagination" aria-label="Phân trang bài đăng">
        <button
          class="pagination__btn"
          :disabled="currentPage === 1"
          @click="goToPage(currentPage - 1)"
        >
          ‹
        </button>

        <button
          v-for="page in pageNumbers"
          :key="page"
          class="pagination__btn"
          :class="{ 'is-active': page === currentPage }"
          :disabled="page === '...'"
          @click="typeof page === 'number' && goToPage(page)"
        >
          {{ page }}
        </button>

        <button
          class="pagination__btn"
          :disabled="currentPage === totalPages"
          @click="goToPage(currentPage + 1)"
        >
          ›
        </button>
      </nav>
    </section>

    <Footer />
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref, h } from 'vue'
import { useRouter } from 'vue-router'
import Header from '../../components/layout/Header.vue'
import Footer from '../../components/layout/Footer.vue'
import { getAllDangTin } from '../../api/dangTinApi'

const router = useRouter()

/* ---------------- Tìm kiếm ---------------- */
const searchForm = reactive({ keyword: '', category: '', province: '' })
const showFilters = ref(false)

function handleSearch() {
  router.push({
    path: '/rooms',
    query: {
      keyword: searchForm.keyword || undefined,
      category: searchForm.category || undefined,
      province: searchForm.province || undefined
    }
  })
}

const popularKeywords = [
  'Quận 1',
  'Gần đại học',
  'Dưới 3 triệu',
  'Có gác lửng',
  'Full nội thất',
  'Ở ghép'
]

function applyKeyword(keyword) {
  searchForm.keyword = keyword
  handleSearch()
}

const FilterIcon = () =>
  h('svg', { width: 16, height: 16, viewBox: '0 0 24 24', fill: 'none', stroke: 'currentColor', 'stroke-width': 2 }, [
    h('line', { x1: 4, y1: 6, x2: 20, y2: 6 }),
    h('line', { x1: 8, y1: 12, x2: 16, y2: 12 }),
    h('line', { x1: 11, y1: 18, x2: 13, y2: 18 })
  ])

/* ---------------- Danh mục ---------------- */
const categories = [
  { id: 'ktx', label: 'KÝ TÚC XÁ', image: 'https://loremflickr.com/400/500/dormitory,bunkbed,student?lock=48' },
  { id: 'phong-tro', label: 'PHÒNG TRỌ GIÁ RẺ', image: 'https://loremflickr.com/400/500/bedroom,rentroom,cheap?lock=54' },
  { id: 'o-ghep', label: 'TÌM BẠN Ở GHÉP', image: 'https://loremflickr.com/400/500/roommates,sharedhouse,friends?lock=74' },
  { id: 'chung-cu-mini', label: 'CHUNG CƯ MINI', image: 'https://loremflickr.com/400/500/apartment,minimalist,interior?lock=81' }
]
const categoriesTrack = ref(null)
function scrollCategories(direction) {
  const el = categoriesTrack.value
  if (!el) return
  el.scrollBy({ left: direction * 280, behavior: 'smooth' })
}

/* ---------------- Bài đăng mới nhất + phân trang ---------------- */
const mockPosts = [
  { id: 1, title: 'Phòng Studio Quận 1', price: '3,5 Tr', address: 'Nguyễn Huệ, Quận 1', badge: 'Mới nhất', badgeClass: 'badge--new', wifi: true, aircon: true, favorite: true, image: 'https://loremflickr.com/300/220/studio,apartment,interior?lock=27' },
  { id: 2, title: 'Phòng gần ĐH FPT', price: '2,8 Tr', address: 'Quận 10, TP. HCM', badge: 'Hot sale', badgeClass: 'badge--hot', wifi: true, aircon: true, favorite: false, image: 'https://loremflickr.com/300/220/bedroom,student,dormitory?lock=31' },
  { id: 3, title: 'Chung cư mini xịn sò', price: '4,0 Tr', address: 'Quận Phú Nhuận', wifi: true, aircon: true, favorite: false, image: 'https://loremflickr.com/300/220/apartment,minimalist,cozy?lock=40' },
  { id: 4, title: 'Chung cư mini xịn sò', price: '4,0 Tr', address: 'Quận Phú Nhuận', wifi: true, aircon: true, favorite: false, image: 'https://loremflickr.com/300/220/apartment,cozy,bedroom?lock=41' },
  { id: 5, title: 'Chung cư mini xịn sò', price: '4,0 Tr', address: 'Quận Phú Nhuận', wifi: true, aircon: true, favorite: false, image: 'https://loremflickr.com/300/220/apartment,interior,cozy?lock=42' },
  { id: 6, title: 'Chung cư mini xịn sò', price: '4,0 Tr', address: 'Quận Phú Nhuận', wifi: true, aircon: true, favorite: false, image: 'https://loremflickr.com/300/220/apartment,bedroom,interior?lock=43' },
  { id: 7, title: 'Chung cư mini xịn sò', price: '4,0 Tr', address: 'Quận Phú Nhuận', wifi: true, aircon: true, favorite: false, image: 'https://loremflickr.com/300/220/apartment,cozy,minimalist?lock=44' },
  { id: 8, title: 'Chung cư mini xịn sò', price: '4,0 Tr', address: 'Quận Phú Nhuận', wifi: true, aircon: true, favorite: false, image: 'https://loremflickr.com/300/220/apartment,bedroom,cozy?lock=45' },
  { id: 9, title: 'Phòng ban công thoáng mát', price: '3,2 Tr', address: 'Quận Bình Thạnh', wifi: true, aircon: false, favorite: false, image: 'https://loremflickr.com/300/220/balcony,apartment,bright?lock=50' },
  { id: 10, title: 'Căn hộ dịch vụ đầy đủ nội thất', price: '5,0 Tr', address: 'Quận 2, TP. Thủ Đức', wifi: true, aircon: true, favorite: false, image: 'https://loremflickr.com/300/220/apartment,furnished,service?lock=52' },
  { id: 11, title: 'Phòng trọ sinh viên giá rẻ', price: '1,8 Tr', address: 'Quận Thủ Đức', wifi: false, aircon: false, favorite: false, image: 'https://loremflickr.com/300/220/dormitory,student,cheap?lock=55' },
  { id: 12, title: 'Studio full nội thất', price: '4,5 Tr', address: 'Quận 7, TP. HCM', wifi: true, aircon: true, favorite: false, image: 'https://loremflickr.com/300/220/studio,furnished,apartment?lock=56' }
]

const posts = ref([...mockPosts])

async function loadLatestPosts() {
  try {
    const { data } = await getAllDangTin()
    if (Array.isArray(data) && data.length) {
      posts.value = data.map((item, index) => ({
        id: item.maDangTin ?? item.id ?? index,
        title: item.tieuDe ?? item.title ?? 'Tin đăng phòng trọ',
        price: item.gia ? `${item.gia}` : mockPosts[index % mockPosts.length].price,
        address: item.diaChi ?? item.address ?? 'Đang cập nhật',
        badge: index === 0 ? 'Mới nhất' : undefined,
        badgeClass: index === 0 ? 'badge--new' : undefined,
        wifi: true,
        aircon: true,
        favorite: false,
        image: item.hinhAnh ?? item.image ?? mockPosts[index % mockPosts.length].image
      }))
    }
  } catch (error) {
    // Chưa kết nối được backend / chưa có dữ liệu -> giữ dữ liệu mẫu để xem giao diện
    console.warn('Không tải được bài đăng mới nhất, dùng dữ liệu mẫu:', error?.message)
  }
}
onMounted(loadLatestPosts)

function toggleFavorite(post) {
  post.favorite = !post.favorite
}

const postsPerPage = 6
const currentPage = ref(1)

const totalPages = computed(() => Math.max(1, Math.ceil(posts.value.length / postsPerPage)))

const paginatedPosts = computed(() => {
  const start = (currentPage.value - 1) * postsPerPage
  return posts.value.slice(start, start + postsPerPage)
})

// Tạo dãy số trang, rút gọn bằng "..." khi có nhiều trang
const pageNumbers = computed(() => {
  const total = totalPages.value
  const current = currentPage.value
  if (total <= 7) return Array.from({ length: total }, (_, i) => i + 1)

  const pages = [1]
  if (current > 3) pages.push('...')

  const start = Math.max(2, current - 1)
  const end = Math.min(total - 1, current + 1)
  for (let p = start; p <= end; p++) pages.push(p)

  if (current < total - 2) pages.push('...')
  pages.push(total)
  return pages
})

function goToPage(page) {
  if (page < 1 || page > totalPages.value) return
  currentPage.value = page
  document.querySelector('.latest-section')?.scrollIntoView({ behavior: 'smooth', block: 'start' })
}
</script>

<style scoped>
* { box-sizing: border-box; }
.home { font-family: 'Segoe UI', Arial, sans-serif; color: #1a1a1a; background: #fff; }

/* ================= HERO ================= */
.hero {
  position: relative;
  min-height: 460px;
  display: flex;
  align-items: center;
  justify-content: center;
  background:
    linear-gradient(135deg, rgba(44,32,24,0.55) 0%, rgba(28,23,18,0.65) 100%),
    url('https://loremflickr.com/1600/700/apartment,cozyroom,interior?lock=901') center/cover no-repeat;
  overflow: hidden;
}
.hero::before {
  content: '';
  position: absolute; inset: 0;
  background:
    radial-gradient(circle at 15% 30%, rgba(255,180,80,0.18), transparent 45%),
    radial-gradient(circle at 85% 70%, rgba(0,0,0,0.4), transparent 55%);
}
.hero__overlay {
  position: absolute; inset: 0;
  background: linear-gradient(180deg, rgba(8,6,4,0.7) 0%, rgba(8,6,4,0.45) 55%, rgba(8,6,4,0.72) 100%);
}
.hero__content {
  position: relative;
  z-index: 2;
  text-align: center;
  color: #fff;
  padding: 100px 20px 48px;
  width: 100%;
  max-width: 760px;
}
.hero__title { margin: 0; font-size: 26px; font-weight: 800; letter-spacing: 3px; }
.hero__tagline { margin: 8px 0 28px; font-size: 14px; color: rgba(255,255,255,0.85); }

.search-bar {
  display: flex;
  align-items: center;
  gap: 8px;
  background: #fff;
  border-radius: 999px;
  padding: 6px;
  box-shadow: 0 12px 30px rgba(0,0,0,0.25);
}
.search-bar__input {
  flex: 1.4;
  min-width: 0;
  border: none;
  outline: none;
  padding: 10px 16px;
  font-size: 14px;
  border-radius: 999px;
  color: #1a1a1a;
}
.search-bar__select {
  flex: 1;
  min-width: 0;
  border: none;
  border-left: 1px solid #eee;
  outline: none;
  padding: 10px 12px;
  font-size: 13px;
  color: #444;
  background: transparent;
}
.search-bar__filter {
  display: flex;
  align-items: center;
  gap: 6px;
  border: none;
  border-left: 1px solid #eee;
  background: transparent;
  padding: 10px 14px;
  font-size: 13px;
  color: #444;
  cursor: pointer;
  white-space: nowrap;
}
.search-bar__submit {
  border: none;
  border-radius: 999px;
  background: #ee6383;
  color: #fff;
  font-weight: 700;
  font-size: 13px;
  padding: 12px 22px;
  cursor: pointer;
  white-space: nowrap;
}
.search-bar__submit:hover { background: #e14e70; }

.popular-keywords {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-top: 18px;
}
.popular-keywords__label {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.75);
  margin-right: 2px;
}
.popular-keywords__pill {
  border: 1px solid rgba(255, 255, 255, 0.4);
  background: rgba(255, 255, 255, 0.08);
  color: #fff;
  font-size: 12.5px;
  font-weight: 600;
  padding: 7px 16px;
  border-radius: 999px;
  cursor: pointer;
  backdrop-filter: blur(4px);
  transition: background 0.15s ease, border-color 0.15s ease;
}
.popular-keywords__pill:hover {
  background: #ee6383;
  border-color: #ee6383;
}

/* ================= SECTION GENERIC ================= */
.section { max-width: 1200px; margin: 0 auto; padding: 48px 32px; }
.section__head { display: flex; align-items: center; justify-content: space-between; margin-bottom: 24px; }
.section__title { margin: 0; font-size: 20px; font-weight: 800; letter-spacing: 0.5px; }
.link-map { font-size: 13px; font-weight: 700; color: #1a1a1a; text-decoration: none; }
.link-pill {
  font-size: 13px; font-weight: 700; color: #4d8a2f;
  background: #e7f7cf; padding: 8px 16px; border-radius: 999px; text-decoration: none;
}

/* ================= DANH MỤC ================= */
.categories-section { background: #fff; }
.carousel-nav { display: flex; gap: 8px; }
.carousel-nav__btn {
  width: 34px; height: 34px; border-radius: 50%;
  border: 1px solid #ddd; background: #fff; cursor: pointer; font-size: 16px;
}
.carousel-nav__btn--active { background: #ee6383; color: #fff; border-color: #ee6383; }

.categories-track {
  display: flex;
  gap: 20px;
  overflow-x: auto;
  scroll-behavior: smooth;
  padding-bottom: 8px;
}
.categories-track::-webkit-scrollbar { display: none; }
.category-card {
  position: relative;
  flex: 0 0 240px;
  height: 300px;
  border-radius: 20px;
  overflow: hidden;
}
.category-card__img { width: 100%; height: 100%; object-fit: cover; display: block; }
.category-card__label {
  position: absolute; left: 12px; right: 12px; bottom: 12px;
  background: rgba(20,20,20,0.65);
  color: #fff; font-size: 12px; font-weight: 700;
  text-align: center; padding: 10px; border-radius: 999px;
  letter-spacing: 0.5px;
}
.dots { display: flex; justify-content: center; gap: 6px; margin-top: 20px; }
.dots__item { width: 6px; height: 6px; border-radius: 50%; background: #ddd; }
.dots__item.is-active { background: #ee6383; width: 18px; border-radius: 999px; }

/* ================= KHU VỰC NỔI BẬT ================= */
.highlight-section { background: #f2f2f0; max-width: none; }
.highlight-section > .section__head,
.highlight-section > .highlight-grid { max-width: 1200px; margin: 0 auto; }
.highlight-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }
.highlight-card {
  position: relative; border-radius: 18px; overflow: hidden; min-height: 180px;
}
.highlight-card img { width: 100%; height: 100%; object-fit: cover; display: block; }
.highlight-card--big { min-height: 380px; }
.highlight-card__overlay {
  position: absolute; left: 16px; bottom: 16px; right: 16px;
  color: #fff; display: flex; flex-direction: column; gap: 2px;
  text-shadow: 0 2px 8px rgba(0,0,0,0.5);
}
.highlight-card__overlay strong { font-size: 15px; }
.highlight-card__overlay span { font-size: 12px; opacity: 0.9; }
.highlight-grid__right { display: flex; flex-direction: column; gap: 20px; }
.highlight-grid__right .highlight-card--wide { min-height: 180px; }
.highlight-grid__pair { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }
.highlight-grid__pair .highlight-card { min-height: 180px; }

/* ================= PHÒNG NỔI BẬT ================= */
.featured-section { max-width: none; background: #fff; }
.featured-card {
  max-width: 1200px; margin: 0 auto;
  display: grid; grid-template-columns: 1fr 1fr; gap: 32px;
  align-items: center;
}
.featured-card__image { position: relative; border-radius: 20px; overflow: hidden; }
.featured-card__image img { width: 100%; height: 320px; object-fit: cover; display: block; }
.featured-card__badge {
  position: absolute; top: 16px; left: 16px;
  background: #2c3455; color: #ffd76a;
  font-size: 12px; font-weight: 700;
  padding: 8px 14px; border-radius: 999px;
}
.tag-premium {
  display: inline-block; font-size: 12px; font-weight: 700; color: #ee6383;
  background: #fdeaef; padding: 6px 12px; border-radius: 999px; margin-bottom: 12px;
}
.featured-card__title { margin: 0 0 8px; font-size: 24px; font-weight: 800; }
.featured-card__address { margin: 0 0 14px; color: #666; font-size: 14px; }
.featured-card__price { margin: 0 0 16px; font-size: 30px; font-weight: 800; color: #ee6383; }
.featured-card__price span { font-size: 14px; font-weight: 500; color: #999; }
.featured-card__amenities { display: flex; gap: 10px; margin-bottom: 24px; flex-wrap: wrap; }
.featured-card__amenities span {
  font-size: 12px; background: #f4f4f2; padding: 8px 12px; border-radius: 999px; color: #333;
}
.btn-dark {
  display: inline-block; background: #23304a; color: #fff; text-decoration: none;
  font-weight: 700; font-size: 13px; padding: 14px 26px; border-radius: 999px;
}
.btn-dark:hover { background: #1a2439; }

/* ================= BÀI ĐĂNG MỚI NHẤT ================= */
.latest-section { max-width: 1200px; }
.post-list {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}
.post-card {
  display: grid; grid-template-columns: 160px 1fr;
  gap: 16px;
  background: #fdedf1;
  border-radius: 18px;
  padding: 14px;
}
.post-card__image { position: relative; border-radius: 14px; overflow: hidden; }
.post-card__image img { width: 100%; height: 140px; object-fit: cover; display: block; }
.post-card__badge {
  position: absolute; top: 10px; left: 10px;
  font-size: 11px; font-weight: 700; color: #fff;
  padding: 5px 10px; border-radius: 999px;
}
.badge--new { background: #f3963e; }
.badge--hot { background: #e13a4d; }
.post-card__fav {
  position: absolute; right: 10px; bottom: 10px;
  width: 30px; height: 30px; border-radius: 50%;
  border: none; background: rgba(255,255,255,0.9);
  cursor: pointer; color: #bbb; font-size: 15px;
}
.post-card__fav.is-active { color: #e13a4d; }

.post-card__body { display: flex; flex-direction: column; justify-content: center; }
.post-card__top { display: flex; justify-content: space-between; align-items: baseline; gap: 12px; }
.post-card__title { margin: 0; font-size: 16px; font-weight: 800; color: #3b3fa0; }
.post-card__price { font-size: 18px; font-weight: 800; color: #e13a4d; white-space: nowrap; }
.post-card__address { margin: 6px 0 10px; font-size: 13px; color: #666; }
.post-card__divider { border: none; border-top: 1px dashed #e6c8ce; margin: 0 0 10px; }
.post-card__amenities { display: flex; gap: 16px; font-size: 13px; color: #444; }

.empty-state { text-align: center; color: #999; padding: 40px 0; }

/* ================= PAGINATION ================= */
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  margin-top: 32px;
}
.pagination__btn {
  min-width: 38px; height: 38px;
  padding: 0 10px;
  border-radius: 999px;
  border: 1px solid #e5d7da;
  background: #fff;
  font-size: 14px;
  font-weight: 600;
  color: #333;
  cursor: pointer;
}
.pagination__btn:hover:not(:disabled) { background: #fdedf1; }
.pagination__btn.is-active { background: #23304a; border-color: #23304a; color: #fff; }
.pagination__btn:disabled { opacity: 0.4; cursor: not-allowed; }

@media (max-width: 720px) {
  .search-bar { flex-wrap: wrap; border-radius: 20px; }
  .search-bar__input, .search-bar__select { flex: 1 1 100%; border-left: none; border-top: 1px solid #eee; }
  .highlight-grid { grid-template-columns: 1fr; }
  .featured-card { grid-template-columns: 1fr; }
  .post-list { grid-template-columns: 1fr; }
  .post-card { grid-template-columns: 1fr; }
}

@media (max-width: 980px) and (min-width: 721px) {
  .post-card { grid-template-columns: 130px 1fr; }
}
</style>