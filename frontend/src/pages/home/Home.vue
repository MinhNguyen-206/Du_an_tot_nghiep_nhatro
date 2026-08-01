<template>
  <div class="board">
    <!-- ================= HERO: BẢNG GHIM ================= -->
    <section class="hero">
      <Header />

      <div class="hero__pins">
        <img
          v-for="(p, i) in heroPins"
          :key="i"
          :src="p"
          class="hero__pin"
          :style="{ '--i': i }"
          alt=""
        />
      </div>

      <div class="hero__content">
        <span class="hero__eyebrow">Sổ tay tìm trọ &middot; cập nhật mỗi giờ</span>
        <h1 class="hero__headline">
          <span class="hero__headline-stamp">TÌM TRỌ</span>
          
          <br />DỄ DÀNG CHON LỰA
          <br />ƯU TIÊN NHU CẦU
        </h1>
        <p class="hero__tagline">
          <h3>Trọ · Ký túc xá · Ở ghép · Chung cư mini </h3>
          
        </p>

        <form class="search-card" @submit.prevent="handleSearch">
          <div class="search-card__row">
            <input
              v-model="searchForm.keyword"
              type="text"
              class="search-card__input"
              placeholder="Tìm theo khu vực, tên đường, trường học..."
            />
            <select v-model="searchForm.category" class="search-card__select">
              <option value="">Danh mục</option>
              <option v-for="c in categories" :key="c.id" :value="c.id">{{ c.label }}</option>
            </select>
            <select v-model="searchForm.province" class="search-card__select">
              <option value="">Toàn quốc</option>
              <option value="hcm">TP. Hồ Chí Minh</option>
              <option value="hn">Hà Nội</option>
              <option value="dn">Đà Nẵng</option>
            </select>
            <button type="submit" class="search-card__submit">TÌM →</button>
          </div>
        </form>

        <div class="ticket-strip">
          <span><strong>2.400+</strong> phòng đang trống</span>
          <span class="ticket-strip__dot">•</span>
          <span><strong>18</strong> quận/huyện</span>
          <span class="ticket-strip__dot">•</span>
          <span>cập nhật theo giờ</span>
        </div>

        <div class="keyword-tags">
          <button
            v-for="kw in popularKeywords"
            :key="kw"
            type="button"
            class="keyword-tags__tag"
            @click="applyKeyword(kw)"
          >
            #{{ kw }}
          </button>
        </div>
      </div>
    </section>

    <!-- ================= DANH MỤC: BẢNG GHIM MINI ================= -->
    <section class="section pinboard-section">
      <div class="section__head">
        <h2 class="section__title">Danh mục</h2>
        <p class="section__hint">ghim sẵn theo nhu cầu ở của bạn</p>
      </div>

      <div class="pinboard">
        <router-link
          v-for="(c, i) in categories"
          :key="c.id"
          to="/rooms"
          class="flyer-card"
          :style="{ '--tilt': tiltFor(i) }"
        >
          <span class="flyer-card__tape" :class="i % 2 === 0 ? 'tape--kraft' : 'tape--frost'"></span>
          <img :src="c.image" :alt="c.label" class="flyer-card__img" />
          <span class="flyer-card__label">{{ c.label }}</span>
        </router-link>
      </div>
    </section>

    <!-- ================= KHU VỰC NỔI BẬT: TUYẾN ĐƯỜNG ================= -->
    <section class="section route-section">
      <div class="section__head">
        <h2 class="section__title">Khu vực nổi bật</h2>
        <router-link to="/rooms" class="link-underline">Xem bản đồ khu trọ →</router-link>
      </div>

      <div class="route-track">
        <div class="route-line"></div>
        <div v-for="area in areas" :key="area.code" class="route-stop">
          <span class="route-stop__code">{{ area.code }}</span>
          <div class="route-stop__card">
            <img :src="area.image" :alt="area.name" />
            <div class="route-stop__info">
              <strong>{{ area.name }}</strong>
              <span>{{ area.note }}</span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- ================= TIN NỔI BẬT: VÉ GIỮ CHỖ ================= -->
    <section class="section spotlight-section">
      <div class="ticket">
        <div class="ticket__image">
          <img src="https://loremflickr.com/700/560/apartment,studio,luxury?lock=39" alt="Phòng nổi bật" />
          <span class="ticket__stamp">TIN<br />NỔI BẬT</span>
        </div>

        <div class="ticket__notch" aria-hidden="true"></div>

        <div class="ticket__body">
          <span class="ticket__tag">Premium Listing</span>
          <h3 class="ticket__title">Căn hộ studio cao cấp — View Landmark 81</h3>
          <p class="ticket__address">📍 Vinhomes Central Park, Bình Thạnh</p>

          <div class="ticket__price">
            <span>5,5 Tr</span><small>/tháng</small>
          </div>

          <ul class="ticket__amenities">
            <li>🛏 1 phòng ngủ</li>
            <li>▭ 35 m²</li>
            <li>🏡 Ban công</li>
          </ul>

          <router-link to="/rooms/1" class="btn-stamp">Xem chi tiết ngay →</router-link>
        </div>
      </div>
    </section>

    <!-- ================= BÀI ĐĂNG MỚI NHẤT: BẢNG TỜ RƠI ================= -->
    <section class="section latest-section">
      <div class="section__head">
        <h2 class="section__title">Bài đăng mới nhất</h2>
        <router-link to="/rooms" class="link-underline">Khám phá tất cả →</router-link>
      </div>

      <div class="flyer-grid">
        <article
          v-for="(post, i) in paginatedPosts"
          :key="post.id"
          class="post-flyer"
          :style="{ '--tilt': tiltFor(i) }"
          @click="goToRoom(post.id)"
        >
          <span class="post-flyer__tape" :class="i % 2 === 0 ? 'tape--kraft' : 'tape--frost'"></span>
          <div class="post-flyer__image">
            <span v-if="post.badge" class="post-flyer__badge" :class="post.badgeClass">{{ post.badge }}</span>
            <img :src="post.image" :alt="post.title" />
            <button
              class="post-flyer__fav"
              :class="{ 'is-active': post.favorite }"
              @click.stop="toggleFavorite(post)"
              aria-label="Yêu thích"
            >
              ♥
            </button>
          </div>

          <h3 class="post-flyer__title">{{ post.title }}</h3>
          <p class="post-flyer__address">📍 {{ post.address }}</p>

          <div class="post-flyer__footer">
            <span class="post-flyer__amenities">
              <span v-if="post.wifi">📶</span>
              <span v-if="post.aircon">❄</span>
            </span>
            <span class="price-tag">{{ post.price }}</span>
          </div>
        </article>

        <p v-if="!paginatedPosts.length" class="empty-note">Bảng tin đang trống — chưa có tin nào phù hợp.</p>
      </div>

      <!-- Phân trang kiểu tab sổ tay -->
      <nav v-if="totalPages > 1" class="notebook-pagination" aria-label="Phân trang bài đăng">
        <button class="notebook-tab notebook-tab--nav" :disabled="currentPage === 1" @click="goToPage(currentPage - 1)">‹</button>
        <button
          v-for="page in pageNumbers"
          :key="page"
          class="notebook-tab"
          :class="{ 'is-active': page === currentPage }"
          :disabled="page === '...'"
          @click="typeof page === 'number' && goToPage(page)"
        >
          {{ page }}
        </button>
        <button class="notebook-tab notebook-tab--nav" :disabled="currentPage === totalPages" @click="goToPage(currentPage + 1)">›</button>
      </nav>
    </section>

    <Footer />
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import Header from '../../components/layout/Header.vue'
import Footer from '../../components/layout/Footer.vue'
import { getAllDangTin } from '../../api/dangTinApi'

const router = useRouter()

/* ---------------- Tìm kiếm ---------------- */
const searchForm = reactive({ keyword: '', category: '', province: '' })

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

const popularKeywords = ['Quận 1', 'Gần đại học', 'Dưới 3 triệu', 'Có gác lửng', 'Ở ghép']
function applyKeyword(keyword) {
  searchForm.keyword = keyword
  handleSearch()
}

/* ---------------- Danh mục ---------------- */
const categories = [
  { id: 'ktx', label: 'Ký túc xá', image: 'https://loremflickr.com/360/440/dormitory,bunkbed,student?lock=48' },
  { id: 'phong-tro', label: 'Phòng trọ giá rẻ', image: 'https://loremflickr.com/360/440/bedroom,rentroom,cheap?lock=54' },
  { id: 'o-ghep', label: 'Tìm bạn ở ghép', image: 'https://loremflickr.com/360/440/roommates,sharedhouse,friends?lock=74' },
  { id: 'chung-cu-mini', label: 'Chung cư mini', image: 'https://loremflickr.com/360/440/apartment,minimalist,interior?lock=81' }
]

// độ nghiêng luân phiên cho hiệu ứng "ghim lệch tay"
function tiltFor(index) {
  const angles = [-2.5, 1.5, -1, 2, -1.8, 1]
  return `${angles[index % angles.length]}deg`
}

const heroPins = [
  'https://loremflickr.com/160/160/bedroom,cozy?lock=201',
  'https://loremflickr.com/160/160/apartment,livingroom?lock=202',
  'https://loremflickr.com/160/160/studio,interior?lock=203'
]

/* ---------------- Khu vực nổi bật ---------------- */
const areas = [
  { code: 'ĐH', name: 'Gần các trường đại học', note: '1.240+ tin đăng mới', image: 'https://loremflickr.com/240/180/university,campus,dormitory?lock=11' },
  { code: 'Q1', name: 'Trung tâm thành phố', note: 'Đi lại thuận tiện', image: 'https://loremflickr.com/240/180/cityscape,skyline,vietnam?lock=15' },
  { code: 'PN', name: 'Phòng giá rẻ', note: 'Dưới 3 triệu/tháng', image: 'https://loremflickr.com/240/180/bedroom,budget,simple?lock=60' },
  { code: 'TĐ', name: 'Căn hộ dịch vụ', note: 'Full nội thất', image: 'https://loremflickr.com/240/180/apartment,service,interior?lock=80' }
]

/* ---------------- Bài đăng mới nhất + phân trang ---------------- */
const mockPosts = [
  { id: 1, title: 'Phòng Studio Quận 1', price: '3,5 Tr', address: 'Nguyễn Huệ, Quận 1', badge: 'Mới nhất', badgeClass: 'badge--new', wifi: true, aircon: true, favorite: true, image: 'https://loremflickr.com/320/220/studio,apartment,interior?lock=27' },
  { id: 2, title: 'Phòng gần ĐH FPT', price: '2,8 Tr', address: 'Quận 10, TP. HCM', badge: 'Hot sale', badgeClass: 'badge--hot', wifi: true, aircon: true, favorite: false, image: 'https://loremflickr.com/320/220/bedroom,student,dormitory?lock=31' },
  { id: 3, title: 'Chung cư mini xịn sò', price: '4,0 Tr', address: 'Quận Phú Nhuận', wifi: true, aircon: true, favorite: false, image: 'https://loremflickr.com/320/220/apartment,minimalist,cozy?lock=40' },
  { id: 4, title: 'Phòng ban công thoáng mát', price: '3,2 Tr', address: 'Quận Bình Thạnh', wifi: true, aircon: false, favorite: false, image: 'https://loremflickr.com/320/220/balcony,apartment,bright?lock=50' },
  { id: 5, title: 'Căn hộ dịch vụ đầy đủ nội thất', price: '5,0 Tr', address: 'Quận 2, TP. Thủ Đức', wifi: true, aircon: true, favorite: false, image: 'https://loremflickr.com/320/220/apartment,furnished,service?lock=52' },
  { id: 6, title: 'Phòng trọ sinh viên giá rẻ', price: '1,8 Tr', address: 'Quận Thủ Đức', wifi: false, aircon: false, favorite: false, image: 'https://loremflickr.com/320/220/dormitory,student,cheap?lock=55' },
  { id: 7, title: 'Studio full nội thất', price: '4,5 Tr', address: 'Quận 7, TP. HCM', wifi: true, aircon: true, favorite: false, image: 'https://loremflickr.com/320/220/studio,furnished,apartment?lock=56' },
  { id: 8, title: 'Chung cư mini có thang máy', price: '4,2 Tr', address: 'Quận Gò Vấp', wifi: true, aircon: true, favorite: false, image: 'https://loremflickr.com/320/220/apartment,elevator,modern?lock=58' },
  { id: 9, title: 'Phòng gần chợ, tiện sinh hoạt', price: '2,5 Tr', address: 'Quận Tân Bình', wifi: true, aircon: false, favorite: false, image: 'https://loremflickr.com/320/220/bedroom,simple,rent?lock=61' },
  { id: 10, title: 'Ký túc xá sinh viên mới', price: '1,5 Tr', address: 'Quận Thủ Đức', wifi: true, aircon: false, favorite: false, image: 'https://loremflickr.com/320/220/dormitory,newbuilding?lock=62' },
  { id: 11, title: 'Phòng có gác lửng', price: '3,0 Tr', address: 'Quận Bình Tân', wifi: true, aircon: true, favorite: false, image: 'https://loremflickr.com/320/220/loft,mezzanine,room?lock=63' },
  { id: 12, title: 'Căn hộ view sông', price: '6,0 Tr', address: 'Quận 2, TP. Thủ Đức', wifi: true, aircon: true, favorite: false, image: 'https://loremflickr.com/320/220/apartment,riverview?lock=64' }
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
    console.warn('Không tải được bài đăng mới nhất, dùng dữ liệu mẫu:', error?.message)
  }
}
onMounted(loadLatestPosts)

function toggleFavorite(post) {
  post.favorite = !post.favorite
}

function goToRoom(id) {
  router.push(`/rooms/${id}`)
}

const postsPerPage = 6
const currentPage = ref(1)
const totalPages = computed(() => Math.max(1, Math.ceil(posts.value.length / postsPerPage)))
const paginatedPosts = computed(() => {
  const start = (currentPage.value - 1) * postsPerPage
  return posts.value.slice(start, start + postsPerPage)
})

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
@import url('https://fonts.googleapis.com/css2?family=Archivo+Black&family=Be+Vietnam+Pro:wght@400;500;600;700;800&display=swap');

* { box-sizing: border-box; }

.board {
  --paper: #f1e8ce;
  --paper-dark: #e5d8ae;
  --ink: #211d17;
  --pine: #1f4b3f;
  --pine-dark: #163a30;
  --brick: #c23b2b;
  --brick-dark: #a32e20;
  --mustard: #e4a63a;

  font-family: 'Be Vietnam Pro', 'Segoe UI', Arial, sans-serif;
  color: var(--ink);
  background: var(--paper);
}

/* ================= HERO ================= */
.hero {
  position: relative;
  min-height: 560px;
  padding: 96px 32px 56px;
  overflow: hidden;
  background:
    radial-gradient(rgba(0,0,0,0.05) 1px, transparent 1.4px) 0 0/14px 14px,
    linear-gradient(160deg, var(--pine) 0%, var(--pine-dark) 100%);
}
.hero::before {
  content: '';
  position: absolute; inset: 0;
  background: repeating-linear-gradient(0deg, rgba(0,0,0,0.06) 0 1px, transparent 1px 90px);
  pointer-events: none;
}

.hero__pins {
  position: absolute;
  top: 26px;
  right: 32px;
  display: flex;
  gap: 12px;
  z-index: 3;
}
.hero__pin {
  width: 64px; height: 64px;
  object-fit: cover;
  border: 4px solid var(--paper);
  border-radius: 4px;
  box-shadow: 0 6px 14px rgba(0,0,0,0.35);
  transform: rotate(calc(var(--i) * 6deg - 6deg));
  display: none;
}

.hero__content { position: relative; z-index: 2; max-width: 620px; margin: 0 auto; text-align: center; }
.hero__eyebrow {
  display: inline-block;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 1.5px;
  text-transform: uppercase;
  color: var(--mustard);
  margin-bottom: 18px;
}
.hero__headline {
  margin: 0 0 14px;
  font-family: 'Archivo Black', sans-serif;
  font-size: 42px;
  line-height: 1.12;
  color: var(--paper);
  letter-spacing: 0.5px;
}
.hero__headline-stamp {
  display: inline-block;
  color: var(--paper);
  background: var(--brick);
  padding: 2px 12px;
  border-radius: 3px;
  transform: rotate(-3deg);
  box-shadow: 0 3px 0 rgba(0,0,0,0.2);
}
.hero__tagline { margin: 0 0 32px; font-size: 15px; color: rgba(241,232,206,0.85); }

.search-card {
  background: var(--paper);
  border-radius: 10px;
  padding: 10px;
  box-shadow: 0 16px 34px rgba(0,0,0,0.3);
  transform: rotate(-0.6deg);
}
.search-card__row { display: flex; gap: 6px; flex-wrap: wrap; }
.search-card__input {
  flex: 1.6; min-width: 160px;
  border: none; outline: none;
  background: var(--paper-dark);
  border-radius: 6px;
  padding: 12px 14px;
  font-size: 14px;
  font-family: inherit;
  color: var(--ink);
}
.search-card__select {
  flex: 1; min-width: 110px;
  border: none; outline: none;
  background: var(--paper-dark);
  border-radius: 6px;
  padding: 12px 10px;
  font-size: 13px;
  font-family: inherit;
  color: var(--ink);
}
.search-card__submit {
  border: none;
  border-radius: 6px;
  background: var(--brick);
  color: var(--paper);
  font-family: 'Archivo Black', sans-serif;
  font-size: 13px;
  letter-spacing: 0.5px;
  padding: 12px 22px;
  cursor: pointer;
}
.search-card__submit:hover { background: var(--brick-dark); }

.ticket-strip {
  margin-top: 22px;
  font-size: 13px;
  color: rgba(241,232,206,0.9);
  display: flex;
  justify-content: center;
  gap: 10px;
  flex-wrap: wrap;
}
.ticket-strip strong { color: var(--mustard); }
.ticket-strip__dot { opacity: 0.5; }

.keyword-tags { margin-top: 18px; display: flex; flex-wrap: wrap; gap: 8px; justify-content: center; }
.keyword-tags__tag {
  border: 1px dashed rgba(241,232,206,0.5);
  background: transparent;
  color: rgba(241,232,206,0.9);
  font-size: 12px;
  padding: 6px 13px;
  border-radius: 999px;
  cursor: pointer;
}
.keyword-tags__tag:hover { background: rgba(241,232,206,0.12); }

/* ================= SECTION GENERIC ================= */
.section { max-width: 1180px; margin: 0 auto; padding: 56px 28px; }
.section__head { display: flex; align-items: baseline; justify-content: space-between; margin-bottom: 28px; flex-wrap: wrap; gap: 8px; }
.section__title { margin: 0; font-family: 'Archivo Black', sans-serif; font-size: 22px; letter-spacing: 0.3px; }
.section__hint { margin: 0; font-size: 13px; color: #6f6650; font-style: italic; }
.link-underline { font-size: 13px; font-weight: 700; color: var(--pine); text-decoration: underline; text-underline-offset: 4px; }

/* ================= DANH MỤC - PINBOARD ================= */
.pinboard { display: grid; grid-template-columns: repeat(4, 1fr); gap: 26px; }
.flyer-card {
  position: relative;
  display: block;
  background: var(--paper);
  border: 1px solid rgba(33,29,23,0.08);
  border-radius: 6px 10px 8px 12px;
  padding: 10px 10px 14px;
  text-decoration: none;
  color: var(--ink);
  box-shadow: 0 10px 20px rgba(33,29,23,0.12);
  transform: rotate(var(--tilt));
  transition: transform 0.2s ease;
}
.flyer-card:hover { transform: rotate(0deg) translateY(-4px); }
.flyer-card__tape {
  position: absolute;
  top: -10px; left: 50%;
  width: 62px; height: 22px;
  margin-left: -31px;
  transform: rotate(-2deg);
  clip-path: polygon(6% 0%, 94% 0%, 100% 18%, 90% 32%, 100% 52%, 88% 68%, 100% 86%, 94% 100%, 6% 100%, 14% 84%, 2% 66%, 12% 50%, 0% 30%, 8% 14%);
  box-shadow: 0 2px 4px rgba(33,29,23,0.15);
}
.flyer-card__tape::after {
  content: '';
  position: absolute; inset: 0;
  background: repeating-linear-gradient(78deg, rgba(255,255,255,0.35) 0 2px, transparent 2px 6px);
  mix-blend-mode: overlay;
}
.tape--kraft { background: rgba(214, 186, 130, 0.55); mix-blend-mode: multiply; }
.tape--frost { background: rgba(255, 255, 255, 0.55); mix-blend-mode: normal; }
.flyer-card__img { width: 100%; height: 210px; object-fit: cover; border-radius: 3px; display: block; }
.flyer-card__label {
  display: block;
  text-align: center;
  margin-top: 12px;
  font-weight: 800;
  font-size: 13px;
  letter-spacing: 0.3px;
}

/* ================= KHU VỰC NỔI BẬT - ROUTE ================= */
.route-track { position: relative; display: flex; gap: 40px; overflow-x: auto; padding: 20px 4px 8px; }
.route-line {
  position: absolute; left: 0; right: 0; top: 44px; height: 2px;
  background: repeating-linear-gradient(90deg, var(--ink) 0 8px, transparent 8px 16px);
  opacity: 0.25;
}
.route-stop { position: relative; flex: 0 0 220px; text-align: center; }
.route-stop__code {
  display: inline-flex; align-items: center; justify-content: center;
  width: 34px; height: 34px;
  border-radius: 50%;
  background: var(--pine);
  color: var(--paper);
  font-weight: 800;
  font-size: 12px;
  position: relative;
  z-index: 2;
  margin-bottom: 12px;
  box-shadow: 0 0 0 5px var(--paper);
}
.route-stop__card { border-radius: 10px; overflow: hidden; box-shadow: 0 10px 20px rgba(33,29,23,0.12); background: #fff; }
.route-stop__card img { width: 100%; height: 120px; object-fit: cover; display: block; }
.route-stop__info { padding: 12px; text-align: left; }
.route-stop__info strong { display: block; font-size: 13.5px; margin-bottom: 2px; }
.route-stop__info span { font-size: 12px; color: #6f6650; }

/* ================= TICKET SPOTLIGHT ================= */
.spotlight-section { max-width: none; background: var(--paper-dark); }
.ticket {
  max-width: 1000px; margin: 0 auto;
  display: grid;
  grid-template-columns: 1fr auto 1.1fr;
  align-items: stretch;
  background: #fff;
  border-radius: 14px;
  overflow: hidden;
  box-shadow: 0 20px 40px rgba(33,29,23,0.15);
}
.ticket__image { position: relative; }
.ticket__image img { width: 100%; height: 100%; object-fit: cover; display: block; min-height: 280px; }
.ticket__stamp {
  position: absolute; top: 18px; left: 18px;
  background: var(--brick);
  color: var(--paper);
  font-family: 'Archivo Black', sans-serif;
  font-size: 12px;
  line-height: 1.3;
  text-align: center;
  padding: 10px 12px;
  border-radius: 50%;
  transform: rotate(-8deg);
  box-shadow: 0 4px 10px rgba(0,0,0,0.3);
}
.ticket__notch {
  position: relative;
  width: 1px;
  background: repeating-linear-gradient(180deg, var(--paper-dark) 0 10px, transparent 10px 20px);
}
.ticket__notch::before, .ticket__notch::after {
  content: '';
  position: absolute; left: -11px;
  width: 22px; height: 22px;
  background: var(--paper-dark);
  border-radius: 50%;
}
.ticket__notch::before { top: -11px; }
.ticket__notch::after { bottom: -11px; }

.ticket__body { padding: 32px 34px; }
.ticket__tag {
  display: inline-block; font-size: 12px; font-weight: 700; color: var(--brick);
  border: 1px dashed var(--brick); padding: 5px 12px; border-radius: 999px; margin-bottom: 14px;
}
.ticket__title { margin: 0 0 8px; font-family: 'Archivo Black', sans-serif; font-size: 21px; line-height: 1.3; }
.ticket__address { margin: 0 0 16px; font-size: 13.5px; color: #6f6650; }
.ticket__price { margin-bottom: 18px; font-family: 'Archivo Black', sans-serif; font-size: 30px; color: var(--brick); }
.ticket__price small { font-size: 13px; font-family: 'Be Vietnam Pro'; font-weight: 500; color: #6f6650; margin-left: 4px; }
.ticket__amenities { list-style: none; margin: 0 0 26px; padding: 0; display: flex; gap: 10px; flex-wrap: wrap; }
.ticket__amenities li { font-size: 12.5px; background: var(--paper); padding: 8px 12px; border-radius: 999px; }
.btn-stamp {
  display: inline-block;
  background: var(--pine);
  color: var(--paper);
  text-decoration: none;
  font-weight: 700;
  font-size: 13px;
  padding: 14px 24px;
  border-radius: 6px;
}
.btn-stamp:hover { background: var(--pine-dark); }

/* ================= BÀI ĐĂNG MỚI NHẤT - FLYER GRID ================= */
.flyer-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 30px 24px; }
.post-flyer {
  position: relative;
  background: #fff;
  border-radius: 4px 10px 6px 10px;
  padding: 12px 12px 16px;
  box-shadow: 0 12px 24px rgba(33,29,23,0.12);
  transform: rotate(var(--tilt));
  transition: transform 0.2s ease;
  cursor: pointer;
}
.post-flyer:hover { transform: rotate(0deg) translateY(-4px); }
.post-flyer__tape {
  position: absolute; top: -9px; left: 50%;
  width: 52px; height: 18px; margin-left: -26px;
  transform: rotate(1.5deg);
  clip-path: polygon(6% 0%, 94% 0%, 100% 20%, 88% 34%, 100% 55%, 90% 70%, 100% 88%, 94% 100%, 6% 100%, 14% 82%, 0% 64%, 12% 48%, 2% 28%, 10% 12%);
  box-shadow: 0 2px 4px rgba(33,29,23,0.15);
}
.post-flyer__tape::after {
  content: '';
  position: absolute; inset: 0;
  background: repeating-linear-gradient(78deg, rgba(255,255,255,0.35) 0 2px, transparent 2px 6px);
  mix-blend-mode: overlay;
}
.post-flyer__image { position: relative; border-radius: 4px; overflow: hidden; }
.post-flyer__image img { width: 100%; height: 150px; object-fit: cover; display: block; }
.post-flyer__badge {
  position: absolute; top: 8px; left: 8px;
  font-size: 10.5px; font-weight: 700; color: #fff;
  padding: 4px 9px; border-radius: 3px;
}
.badge--new { background: var(--mustard); color: var(--ink); }
.badge--hot { background: var(--brick); }
.post-flyer__fav {
  position: absolute; right: 8px; bottom: 8px;
  width: 26px; height: 26px; border-radius: 50%;
  border: none; background: rgba(255,255,255,0.92);
  color: #c9c0aa; font-size: 13px; cursor: pointer;
}
.post-flyer__fav.is-active { color: var(--brick); }

.post-flyer__title { margin: 12px 0 4px; font-size: 14.5px; font-weight: 800; color: var(--pine-dark); }
.post-flyer__address { margin: 0 0 10px; font-size: 12px; color: #6f6650; }
.post-flyer__footer { display: flex; align-items: center; justify-content: space-between; }
.post-flyer__amenities { display: flex; gap: 6px; font-size: 13px; }
.price-tag {
  position: relative;
  font-family: 'Archivo Black', sans-serif;
  font-size: 14px;
  color: var(--paper);
  background: var(--brick);
  padding: 5px 12px 5px 10px;
  border-radius: 3px 8px 8px 3px;
}
.price-tag::before {
  content: '';
  position: absolute; left: -4px; top: 50%; transform: translateY(-50%);
  width: 8px; height: 8px; border-radius: 50%;
  background: var(--paper-dark);
}

.empty-note { text-align: center; color: #8a7f65; padding: 40px 0; grid-column: 1 / -1; }

/* ================= PAGINATION - NOTEBOOK TABS ================= */
.notebook-pagination { display: flex; justify-content: center; gap: 6px; margin-top: 36px; }
.notebook-tab {
  min-width: 36px; height: 36px; padding: 0 8px;
  border: 1px solid rgba(33,29,23,0.15);
  border-bottom: 3px solid rgba(33,29,23,0.15);
  border-radius: 4px 4px 0 0;
  background: #fff;
  font-weight: 700;
  font-size: 13px;
  color: var(--ink);
  cursor: pointer;
}
.notebook-tab:hover:not(:disabled) { transform: translateY(-2px); }
.notebook-tab.is-active { background: var(--pine); border-color: var(--pine); border-bottom-color: var(--mustard); color: var(--paper); }
.notebook-tab:disabled { opacity: 0.4; cursor: not-allowed; }

/* ================= RESPONSIVE ================= */
@media (min-width: 900px) {
  .hero__pin { display: block; }
  .hero__headline { font-size: 54px; }
}
@media (max-width: 900px) {
  .pinboard { grid-template-columns: repeat(2, 1fr); }
  .flyer-grid { grid-template-columns: repeat(2, 1fr); }
  .ticket { grid-template-columns: 1fr; }
  .ticket__notch { display: none; }
}
@media (max-width: 560px) {
  .hero { padding-top: 140px; }
  .pinboard { grid-template-columns: 1fr; }
  .flyer-grid { grid-template-columns: 1fr; }
  .search-card__row { flex-direction: column; }
}
</style>