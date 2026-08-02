<template>
  <div class="rd">
    <div class="rd__topbar">
      <Header />
    </div>

    <div class="rd__wrap">
      <!-- Breadcrumb -->
      <nav class="breadcrumb">
        <router-link to="/">Trang chủ</router-link>
        <span>›</span>
        <span v-for="(b, i) in room.breadcrumb" :key="i">{{ b }} <span>›</span></span>
        <strong>{{ room.title }}</strong>
      </nav>

      <!-- Thư viện ảnh -->
      <div class="gallery">
        <button class="gallery__main" @click="openLightbox(0)">
          <img :src="room.images[0]" :alt="room.title" />
        </button>
        <div class="gallery__grid">
          <button
            v-for="(img, i) in room.images.slice(1, 5)"
            :key="i"
            class="gallery__thumb"
            @click="openLightbox(i + 1)"
          >
            <img :src="img" :alt="`${room.title} - ảnh ${i + 2}`" />
            <span v-if="i === 3" class="gallery__more">
              <span class="gallery__more-icon">▦</span>
              Xem tất cả {{ room.images.length }} ảnh
            </span>
          </button>
        </div>
      </div>

      <!-- Lightbox -->
      <div v-if="lightboxOpen" class="lightbox" @click.self="closeLightbox">
        <button class="lightbox__close" @click="closeLightbox">✕</button>
        <button class="lightbox__nav lightbox__nav--prev" @click="stepLightbox(-1)">‹</button>
        <img :src="room.images[lightboxIndex]" class="lightbox__img" :alt="room.title" />
        <button class="lightbox__nav lightbox__nav--next" @click="stepLightbox(1)">›</button>
        <span class="lightbox__count">{{ lightboxIndex + 1 }} / {{ room.images.length }}</span>
      </div>

      <div class="rd__body">
        <!-- ============ CỘT NỘI DUNG ============ -->
        <div class="rd__main">
          <div class="tag-row">
            <span class="tag tag--pine">{{ room.statusTag }}</span>
            <span class="tag tag--brick">{{ room.category }}</span>
          </div>

          <h1 class="rd__title">{{ room.title }}</h1>
          <p class="rd__address">📍 {{ room.address }}</p>

          <div class="stat-card">
            <div class="stat-card__item">
              <span class="stat-card__label">Giá thuê</span>
              <strong class="stat-card__value stat-card__value--price">{{ room.price }}<small>/tháng</small></strong>
            </div>
            <div class="stat-card__item">
              <span class="stat-card__label">Diện tích</span>
              <strong class="stat-card__value">{{ room.area }}</strong>
            </div>
            <div class="stat-card__item">
              <span class="stat-card__label">Tiền cọc</span>
              <strong class="stat-card__value">{{ room.deposit }}</strong>
            </div>
            <div class="stat-card__item">
              <span class="stat-card__label">Trạng thái</span>
              <strong class="stat-card__value stat-card__value--status">{{ room.status }}</strong>
            </div>
          </div>

          <h2 class="rd__section-title">Tiện ích căn hộ</h2>
          <div class="amenity-grid">
            <div v-for="a in room.amenities" :key="a.label" class="amenity-pill">
              <span>{{ a.icon }}</span> {{ a.label }}
            </div>
          </div>

          <h2 class="rd__section-title">Thông tin chi tiết</h2>
          <p class="rd__desc">{{ room.description }}</p>
          <ul class="fact-list">
            <li v-for="f in room.facts" :key="f.label"><strong>{{ f.label }}:</strong> {{ f.value }}</li>
          </ul>

          <h2 class="rd__section-title">Vị trí trên bản đồ</h2>
          <div class="map-frame">
            <iframe
              :src="`https://www.google.com/maps?q=${encodeURIComponent(room.mapQuery)}&output=embed`"
              loading="lazy"
              referrerpolicy="no-referrer-when-downgrade"
              title="Vị trí trên bản đồ"
            ></iframe>
          </div>
        </div>

        <!-- ============ CỘT LIÊN HỆ (STICKY) ============ -->
        <aside class="rd__side">
          <div class="contact-card">
            <div class="contact-card__host">
              <img :src="room.host.avatar" :alt="room.host.name" class="contact-card__avatar" />
              <div>
                <strong>{{ room.host.name }}</strong>
                <span v-if="room.host.verified" class="contact-card__verified">✓ Đã xác thực</span>
              </div>
            </div>

            <button class="btn-phone" @click="phoneRevealed = true">
              📞 {{ phoneRevealed ? room.host.phoneFull : room.host.phoneMasked }}
            </button>

            <router-link :to="`/messages?room=${room.id}`" class="btn-outline">Nhắn tin ngay</router-link>
            <router-link :to="`/appointments?room=${room.id}`" class="btn-outline">Đặt lịch xem phòng</router-link>

            <div class="contact-card__actions">
              <router-link :to="`/deposits?room=${room.id}`" class="btn-ghost">Đặt cọc</router-link>
              <router-link :to="`/rental-requests?room=${room.id}`" class="btn-solid">Thuê phòng</router-link>
            </div>
          </div>

          <div class="fee-card">
            <div v-for="f in room.fees" :key="f.label" class="fee-card__row">
              <span>{{ f.icon }} {{ f.label }}</span>
              <strong>{{ f.value }}</strong>
            </div>
          </div>
        </aside>
      </div>

      <!-- ============ PHÒNG TƯƠNG TỰ ============ -->
      <section class="similar-section">
        <div class="section__head">
          <h2 class="rd__section-title" style="margin:0">Phòng tương tự tại {{ room.breadcrumb[room.breadcrumb.length - 1] }}</h2>
          <div class="carousel-nav">
            <button class="carousel-nav__btn" @click="scrollSimilar(-1)" aria-label="Trước">‹</button>
            <button class="carousel-nav__btn carousel-nav__btn--active" @click="scrollSimilar(1)" aria-label="Sau">›</button>
          </div>
        </div>

        <div class="similar-track" ref="similarTrack">
          <article
            v-for="(item, i) in room.similar"
            :key="item.id"
            class="post-flyer"
            :style="{ '--tilt': tiltFor(i) }"
            @click="goToRoom(item.id)"
          >
            <span class="post-flyer__tape tape--kraft"></span>
            <div class="post-flyer__image">
              <span v-if="item.badge" class="post-flyer__badge badge--new">{{ item.badge }}</span>
              <img :src="item.image" :alt="item.title" />
              <button class="post-flyer__fav" :class="{ 'is-active': item.favorite }" @click.stop="item.favorite = !item.favorite" aria-label="Yêu thích">♥</button>
            </div>
            <h3 class="post-flyer__title">{{ item.title }}</h3>
            <p class="post-flyer__address">📍 {{ item.address }}</p>
            <div class="post-flyer__footer">
              <span class="post-flyer__amenities"><span>📶</span><span>❄</span></span>
              <span class="price-tag">{{ item.price }}</span>
            </div>
          </article>
        </div>
      </section>
    </div>

    <Footer />
  </div>
</template>

<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import Header from '../../components/layout/Header.vue'
import Footer from '../../components/layout/Footer.vue'
import { getPhongTroById } from '../../api/phongTroApi'

const route = useRoute()
const router = useRouter()

const mockRoom = {
  id: route.params.id ?? 1,
  category: 'Chung cư mini',
  statusTag: 'Cho thuê',
  title: 'Căn hộ Studio cao cấp - Đầy đủ nội thất - Quận 1',
  address: 'Nguyễn Huệ, Quận 1',
  breadcrumb: ['Hồ Chí Minh', 'Quận 1'],
  price: '5,5 Tr',
  area: '35 m²',
  deposit: '1 Tháng',
  status: 'Sẵn sàng',
  images: [
    'https://loremflickr.com/900/600/apartment,bedroom,cozy?lock=501',
    'https://loremflickr.com/500/400/apartment,kitchen?lock=502',
    'https://loremflickr.com/500/400/apartment,bathroom?lock=503',
    'https://loremflickr.com/500/400/apartment,livingroom?lock=504',
    'https://loremflickr.com/500/400/apartment,bedroom,plants?lock=505',
    'https://loremflickr.com/500/400/apartment,interior?lock=506',
    'https://loremflickr.com/500/400/apartment,balcony?lock=507'
  ],
  amenities: [
    { icon: '📶', label: 'Wifi' },
    { icon: '❄️', label: 'Điều hòa' },
    { icon: '🛵', label: 'Giữ xe' },
    { icon: '🐾', label: 'Nuôi thú' },
    { icon: '📷', label: 'Camera' },
    { icon: '🌿', label: 'Ban công' },
    { icon: '🏊', label: 'Hồ bơi' },
    { icon: '🏋️', label: 'GYM' }
  ],
  description:
    'Căn hộ studio tầng 4 có ban công thoáng mát, đón ánh sáng tự nhiên cực tốt. Phòng đã được trang bị sẵn giường nệm cao cấp, tủ quần áo âm tường và bếp từ hiện đại. Khu dân cư an ninh, có camera giám sát 24/7, bảo vệ trông xe nghiêm ngặt. Thích hợp cho nhân viên văn phòng hoặc các bạn sinh viên muốn có không gian yên tĩnh để học tập và làm việc.',
  facts: [
    { label: 'Giờ giấc', value: 'Tự do (khoá vân tay)' },
    { label: 'Số người ở tối đa', value: 'Tối đa 2 người' },
    { label: 'Hợp đồng tối thiểu', value: 'Cam kết ở tối thiểu 1 năm' },
    { label: 'Nuôi thú cưng', value: 'Cho phép nuôi thú cưng nhỏ' }
  ],
  mapQuery: 'Nguyễn Huệ, Quận 1, Hồ Chí Minh',
  host: {
    name: 'Đoàn Quốc Đạt',
    verified: true,
    avatar: 'https://loremflickr.com/120/120/portrait,man?lock=88',
    phoneMasked: '0939 *** ***',
    phoneFull: '0939 123 456'
  },
  fees: [
    { icon: '⚡', label: 'Tiền điện', value: '3.500đ / kWh' },
    { icon: '💧', label: 'Tiền nước', value: '100.000đ / người' },
    { icon: '🧾', label: 'Phí quản lý / dịch vụ', value: '150.000đ / phòng' },
    { icon: '📶', label: 'Internet / Wifi', value: '50.000đ / phòng / tháng' }
  ],
  similar: [
    { id: 101, title: 'Phòng Studio full nội thất', price: '7,0 Tr', address: 'Quận 1, TP. HCM', badge: 'Mới đăng', favorite: false, image: 'https://loremflickr.com/320/220/studio,apartment,night?lock=511' },
    { id: 102, title: 'Phòng Studio full nội thất', price: '7,0 Tr', address: 'Quận 1, TP. HCM', favorite: false, image: 'https://loremflickr.com/320/220/studio,apartment,bedroom?lock=512' },
    { id: 103, title: 'Phòng Studio full nội thất', price: '7,0 Tr', address: 'Quận 1, TP. HCM', favorite: false, image: 'https://loremflickr.com/320/220/studio,apartment,workspace?lock=513' },
    { id: 104, title: 'Căn hộ ban công xanh mát', price: '6,2 Tr', address: 'Quận 1, TP. HCM', favorite: false, image: 'https://loremflickr.com/320/220/apartment,balcony,green?lock=514' }
  ]
}

const room = ref({ ...mockRoom })

async function loadRoom() {
  try {
    const { data } = await getPhongTroById(route.params.id)
    if (data) {
      room.value = {
        ...mockRoom,
        id: data.maPhongTro ?? data.id ?? mockRoom.id,
        title: data.tieuDe ?? data.tenPhong ?? mockRoom.title,
        address: data.diaChi ?? mockRoom.address,
        price: data.gia ? `${data.gia}` : mockRoom.price,
        area: data.dienTich ? `${data.dienTich} m²` : mockRoom.area,
        status: data.trangThai ?? mockRoom.status,
        description: data.moTa ?? mockRoom.description
      }
    }
  } catch (error) {
    console.warn('Không tải được dữ liệu phòng, dùng dữ liệu mẫu:', error?.message)
  }
}
onMounted(loadRoom)
watch(
  () => route.params.id,
  () => {
    loadRoom()
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }
)

function goToRoom(id) {
  router.push(`/rooms/${id}`)
}

/* ---------------- Lightbox ---------------- */
const lightboxOpen = ref(false)
const lightboxIndex = ref(0)
function openLightbox(index) {
  lightboxIndex.value = index
  lightboxOpen.value = true
}
function closeLightbox() {
  lightboxOpen.value = false
}
function stepLightbox(dir) {
  const total = room.value.images.length
  lightboxIndex.value = (lightboxIndex.value + dir + total) % total
}

/* ---------------- Liên hệ ---------------- */
const phoneRevealed = ref(false)

/* ---------------- Phòng tương tự ---------------- */
const similarTrack = ref(null)
function scrollSimilar(direction) {
  similarTrack.value?.scrollBy({ left: direction * 280, behavior: 'smooth' })
}
function tiltFor(index) {
  const angles = [-2.5, 1.5, -1, 2]
  return `${angles[index % angles.length]}deg`
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Archivo+Black&family=Be+Vietnam+Pro:wght@400;500;600;700;800&display=swap');
* { box-sizing: border-box; }

.rd {
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
  background: var(--paper-dark);
}

.rd__topbar { height: 110px; background: linear-gradient(160deg, var(--pine) 0%, var(--pine-dark) 100%); position: relative; }
.rd__wrap { max-width: 1180px; margin: 0 auto; padding: 24px 28px 60px; position: relative; }

.breadcrumb { font-size: 12.5px; color: #6f6650; margin: 18px 0 16px; display: flex; flex-wrap: wrap; gap: 4px; }
.breadcrumb a { color: var(--pine); text-decoration: none; font-weight: 600; }
.breadcrumb strong { color: var(--ink); }

/* ---------- Gallery ---------- */
.gallery { display: grid; grid-template-columns: 1.4fr 1fr; gap: 12px; height: 420px; }
.gallery__main, .gallery__thumb { border: none; padding: 0; cursor: pointer; border-radius: 12px; overflow: hidden; background: none; }
.gallery__main { height: 100%; }
.gallery__main img { width: 100%; height: 100%; object-fit: cover; display: block; }
.gallery__grid { display: grid; grid-template-columns: 1fr 1fr; grid-template-rows: 1fr 1fr; gap: 12px; }
.gallery__thumb { position: relative; height: 100%; }
.gallery__thumb img { width: 100%; height: 100%; object-fit: cover; display: block; }
.gallery__more {
  position: absolute; inset: 0;
  background: rgba(33,29,23,0.6);
  color: #fff; display: flex; flex-direction: column; align-items: center; justify-content: center;
  font-size: 12.5px; font-weight: 700; gap: 6px; text-align: center; padding: 8px;
}
.gallery__more-icon { font-size: 20px; }

/* ---------- Lightbox ---------- */
.lightbox {
  position: fixed; inset: 0; z-index: 100;
  background: rgba(17,14,10,0.92);
  display: flex; align-items: center; justify-content: center;
}
.lightbox__img { max-width: 88vw; max-height: 82vh; border-radius: 6px; }
.lightbox__close { position: absolute; top: 20px; right: 24px; background: none; border: none; color: #fff; font-size: 22px; cursor: pointer; }
.lightbox__nav {
  position: absolute; top: 50%; transform: translateY(-50%);
  background: rgba(241,232,206,0.15); border: none; color: #fff;
  width: 44px; height: 44px; border-radius: 50%; font-size: 22px; cursor: pointer;
}
.lightbox__nav--prev { left: 24px; }
.lightbox__nav--next { right: 24px; }
.lightbox__count { position: absolute; bottom: 22px; color: #fff; font-size: 13px; opacity: 0.8; }

/* ---------- Body layout ---------- */
.rd__body { display: grid; grid-template-columns: 1fr 320px; gap: 32px; margin-top: 28px; align-items: start; }
.rd__main { background: var(--paper); border-radius: 14px; padding: 30px 32px; box-shadow: 0 12px 24px rgba(33,29,23,0.1); }

.tag-row { display: flex; gap: 8px; margin-bottom: 14px; }
.tag { font-size: 11.5px; font-weight: 800; letter-spacing: 0.4px; text-transform: uppercase; padding: 6px 12px; border-radius: 4px; }
.tag--pine { background: var(--pine); color: var(--paper); }
.tag--brick { background: var(--brick); color: var(--paper); }

.rd__title { margin: 0 0 10px; font-family: 'Archivo Black', sans-serif; font-size: 26px; line-height: 1.3; }
.rd__address { margin: 0 0 22px; font-size: 14px; color: #6f6650; }

.stat-card {
  display: grid; grid-template-columns: repeat(4, 1fr); gap: 16px;
  background: var(--paper-dark); border-radius: 10px; padding: 18px 20px; margin-bottom: 28px;
}
.stat-card__item { display: flex; flex-direction: column; gap: 6px; }
.stat-card__label { font-size: 11.5px; color: #6f6650; text-transform: uppercase; letter-spacing: 0.4px; }
.stat-card__value { font-family: 'Archivo Black', sans-serif; font-size: 18px; }
.stat-card__value--price { color: var(--brick); }
.stat-card__value--price small { font-family: 'Be Vietnam Pro'; font-size: 11px; font-weight: 500; color: #6f6650; margin-left: 2px; }
.stat-card__value--status { color: var(--pine); }

.rd__section-title { font-family: 'Archivo Black', sans-serif; font-size: 16px; margin: 0 0 16px; padding-top: 4px; }

.amenity-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 12px; margin-bottom: 30px; }
.amenity-pill { display: flex; align-items: center; gap: 8px; background: #fff; border: 1px solid rgba(33,29,23,0.08); border-radius: 999px; padding: 10px 14px; font-size: 13px; }

.rd__desc { font-size: 14px; line-height: 1.7; color: #3c362b; margin: 0 0 16px; }
.fact-list { list-style: none; padding: 0; margin: 0 0 30px; display: flex; flex-direction: column; gap: 8px; font-size: 13.5px; }
.fact-list strong { color: var(--pine-dark); }

.map-frame { border-radius: 12px; overflow: hidden; height: 320px; border: 1px solid rgba(33,29,23,0.1); }
.map-frame iframe { width: 100%; height: 100%; border: 0; }

/* ---------- Sidebar ---------- */
.rd__side { position: sticky; top: 20px; display: flex; flex-direction: column; gap: 18px; }
.contact-card { background: #fff; border-radius: 14px; padding: 22px; box-shadow: 0 12px 24px rgba(33,29,23,0.12); display: flex; flex-direction: column; gap: 12px; }
.contact-card__host { display: flex; align-items: center; gap: 12px; margin-bottom: 6px; }
.contact-card__avatar { width: 52px; height: 52px; border-radius: 50%; object-fit: cover; border: 2px solid var(--paper-dark); }
.contact-card__host strong { display: block; font-size: 15px; }
.contact-card__verified { font-size: 11.5px; color: var(--pine); font-weight: 600; }

.btn-phone {
  border: none; border-radius: 8px; padding: 13px; font-weight: 700; font-size: 14px;
  background: var(--pine); color: var(--paper); cursor: pointer;
}
.btn-phone:hover { background: var(--pine-dark); }
.btn-outline {
  border: 1.5px solid var(--pine); color: var(--pine); text-align: center; text-decoration: none;
  border-radius: 8px; padding: 12px; font-weight: 700; font-size: 13.5px;
}
.btn-outline:hover { background: rgba(31,75,63,0.06); }

.contact-card__actions { display: grid; grid-template-columns: 1fr 1fr; gap: 10px; margin-top: 4px; }
.btn-ghost, .btn-solid { text-align: center; text-decoration: none; border-radius: 8px; padding: 12px; font-weight: 800; font-size: 13px; }
.btn-ghost { background: var(--paper-dark); color: var(--ink); }
.btn-solid { background: var(--brick); color: var(--paper); }
.btn-solid:hover { background: var(--brick-dark); }

.fee-card { background: #fff; border-radius: 14px; padding: 18px 20px; box-shadow: 0 12px 24px rgba(33,29,23,0.1); display: flex; flex-direction: column; gap: 12px; }
.fee-card__row { display: flex; justify-content: space-between; align-items: center; font-size: 13px; }
.fee-card__row strong { color: var(--brick); }

/* ---------- Phòng tương tự ---------- */
.similar-section { margin-top: 48px; }
.section__head { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.carousel-nav { display: flex; gap: 8px; }
.carousel-nav__btn { width: 34px; height: 34px; border-radius: 50%; border: 1px solid rgba(33,29,23,0.15); background: #fff; cursor: pointer; }
.carousel-nav__btn--active { background: var(--brick); color: #fff; border-color: var(--brick); }

.similar-track { display: flex; gap: 22px; overflow-x: auto; padding-bottom: 8px; }
.similar-track::-webkit-scrollbar { display: none; }

.post-flyer {
  position: relative; flex: 0 0 260px;
  background: #fff; border-radius: 4px 10px 6px 10px; padding: 12px 12px 16px;
  box-shadow: 0 12px 24px rgba(33,29,23,0.12);
  transform: rotate(var(--tilt));
  transition: transform 0.2s ease;
  cursor: pointer;
}
.post-flyer:hover { transform: rotate(0deg) translateY(-4px); }
.post-flyer__tape {
  position: absolute; top: -9px; left: 50%; width: 52px; height: 18px; margin-left: -26px;
  background: rgba(214, 186, 130, 0.55); mix-blend-mode: multiply;
  clip-path: polygon(6% 0%, 94% 0%, 100% 20%, 88% 34%, 100% 55%, 90% 70%, 100% 88%, 94% 100%, 6% 100%, 14% 82%, 0% 64%, 12% 48%, 2% 28%, 10% 12%);
  box-shadow: 0 2px 4px rgba(33,29,23,0.15);
}
.post-flyer__image { position: relative; border-radius: 4px; overflow: hidden; }
.post-flyer__image img { width: 100%; height: 150px; object-fit: cover; display: block; }
.post-flyer__badge { position: absolute; top: 8px; left: 8px; font-size: 10.5px; font-weight: 700; padding: 4px 9px; border-radius: 3px; }
.badge--new { background: var(--mustard); color: var(--ink); }
.post-flyer__fav { position: absolute; right: 8px; bottom: 8px; width: 26px; height: 26px; border-radius: 50%; border: none; background: rgba(255,255,255,0.92); color: #c9c0aa; font-size: 13px; cursor: pointer; }
.post-flyer__fav.is-active { color: var(--brick); }
.post-flyer__title { margin: 12px 0 4px; font-size: 14px; font-weight: 800; color: var(--pine-dark); }
.post-flyer__address { margin: 0 0 10px; font-size: 12px; color: #6f6650; }
.post-flyer__footer { display: flex; align-items: center; justify-content: space-between; }
.post-flyer__amenities { display: flex; gap: 6px; font-size: 13px; }
.price-tag { font-family: 'Archivo Black', sans-serif; font-size: 13px; color: var(--paper); background: var(--brick); padding: 5px 12px 5px 10px; border-radius: 3px 8px 8px 3px; }

/* ---------- Responsive ---------- */
@media (max-width: 900px) {
  .rd__body { grid-template-columns: 1fr; }
  .rd__side { position: static; }
  .gallery { grid-template-columns: 1fr; height: auto; }
  .gallery__grid { grid-auto-rows: 140px; }
  .stat-card { grid-template-columns: 1fr 1fr; }
  .amenity-grid { grid-template-columns: 1fr 1fr; }
}
</style>