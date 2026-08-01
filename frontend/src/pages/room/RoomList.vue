<template>
  <div class="rl">
    <section class="rl-hero">
      <Header />
    </section>

    <div class="rl-wrap">
      <!-- ============ THANH TÌM KIẾM ============ -->
      <div class="search-bar-slip">
        <div class="search-bar-slip__field">
          <span class="search-bar-slip__icon">🔎</span>
          <input
            v-model="keyword"
            type="text"
            placeholder="Tìm theo khu vực, tên đường, trường học..."
            @keyup.enter="applySearch"
          />
          <button v-if="keyword" class="search-bar-slip__clear" @click="clearKeyword" aria-label="Xóa">✕</button>
        </div>

        <button class="filter-toggle" :class="{ 'is-open': showFilters }" @click="showFilters = !showFilters">
          Lọc <span class="filter-toggle__chevron">⌄</span>
          <span v-if="activeFilterCount" class="filter-toggle__badge">{{ activeFilterCount }}</span>
        </button>
      </div>

      <!-- ============ BẢNG LỌC ============ -->
      <transition name="fade-slide">
        <div v-if="showFilters" class="filter-panel">
          <div class="filter-group">
            <span class="filter-group__label">Loại hình</span>
            <div class="chip-row">
              <button
                v-for="c in categoryOptions"
                :key="c"
                class="chip"
                :class="{ 'is-active': filters.category === c }"
                @click="filters.category = filters.category === c ? '' : c"
              >
                {{ c }}
              </button>
            </div>
          </div>

          <div class="filter-group">
            <span class="filter-group__label">Khoảng giá (triệu/tháng)</span>
            <div class="range-row">
              <input v-model.number="filters.priceMin" type="number" min="0" step="0.5" class="range-input" placeholder="Từ" />
              <span class="range-sep">—</span>
              <input v-model.number="filters.priceMax" type="number" min="0" step="0.5" class="range-input" placeholder="Đến" />
            </div>
            <div class="quick-row">
              <button v-for="q in quickPriceOptions" :key="q.label" class="quick-chip" @click="filters.priceMin = q.min; filters.priceMax = q.max">
                {{ q.label }}
              </button>
            </div>
          </div>

          <div class="filter-group">
            <span class="filter-group__label">Diện tích (m²)</span>
            <div class="range-row">
              <input v-model.number="filters.areaMin" type="number" min="0" step="1" class="range-input" placeholder="Từ" />
              <span class="range-sep">—</span>
              <input v-model.number="filters.areaMax" type="number" min="0" step="1" class="range-input" placeholder="Đến" />
            </div>
          </div>

          <div class="filter-group">
            <span class="filter-group__label">Số người ở tối đa</span>
            <div class="chip-row">
              <button
                v-for="c in capacityOptions"
                :key="c.value"
                class="chip"
                :class="{ 'is-active': filters.capacity === c.value }"
                @click="filters.capacity = filters.capacity === c.value ? '' : c.value"
              >
                {{ c.label }}
              </button>
            </div>
          </div>

          <div class="filter-group">
            <span class="filter-group__label">Thời hạn thuê tối thiểu</span>
            <select v-model="filters.minDuration" class="filter-select">
              <option value="">Không yêu cầu</option>
              <option value="0">Theo ngày</option>
              <option value="1">Từ 1 tháng</option>
              <option value="3">Từ 3 tháng</option>
              <option value="6">Từ 6 tháng</option>
              <option value="12">Từ 12 tháng</option>
            </select>
          </div>

          <div class="filter-group">
            <span class="filter-group__label">Đối tượng thuê</span>
            <div class="chip-row">
              <button
                v-for="g in genderOptions"
                :key="g.value"
                class="chip"
                :class="{ 'is-active': filters.gender === g.value }"
                @click="filters.gender = filters.gender === g.value ? '' : g.value"
              >
                {{ g.label }}
              </button>
            </div>
          </div>

          <div class="filter-group">
            <span class="filter-group__label">Sắp xếp theo</span>
            <select v-model="filters.sortBy" class="filter-select">
              <option value="newest">Mới đăng nhất</option>
              <option value="price-asc">Giá thấp → cao</option>
              <option value="price-desc">Giá cao → thấp</option>
              <option value="area-desc">Diện tích lớn nhất</option>
            </select>
          </div>

          <div class="filter-group">
            <span class="filter-group__label">Tình trạng</span>
            <label class="switch-row">
              <input type="checkbox" v-model="filters.availableOnly" />
              <span class="switch-track"><span class="switch-thumb"></span></span>
              Chỉ hiện phòng còn trống
            </label>
          </div>

          <div class="filter-group filter-group--wide">
            <span class="filter-group__label">Tiện ích</span>
            <div class="chip-row">
              <button
                v-for="a in amenityOptions"
                :key="a"
                class="chip"
                :class="{ 'is-active': filters.amenities.includes(a) }"
                @click="toggleAmenity(a)"
              >
                {{ a }}
              </button>
            </div>
          </div>

          <div class="filter-panel__actions">
            <button class="btn-reset" @click="resetFilters">Đặt lại tất cả</button>
            <button class="btn-apply" @click="showFilters = false">Áp dụng ({{ filteredRooms.length }} phòng)</button>
          </div>
        </div>
      </transition>

      <!-- ============ TAG LỌC ĐANG ÁP DỤNG ============ -->
      <div v-if="activeFilterTags.length" class="active-tags">
        <span
          v-for="tag in activeFilterTags"
          :key="tag.key"
          class="active-tag"
          @click="tag.clear()"
        >
          {{ tag.label }} ✕
        </span>
        <button class="active-tags__clear-all" @click="resetFilters">Xóa hết</button>
      </div>

      <!-- ============ KẾT QUẢ ============ -->
      <div class="result-head">
        <h1 class="result-title">
          <template v-if="keyword">Kết quả cho “{{ keyword }}”</template>
          <template v-else>Tất cả phòng đang cho thuê</template>
        </h1>
        <span class="result-count">{{ filteredRooms.length }} phòng phù hợp</span>
      </div>

      <div v-if="paginatedRooms.length" class="flyer-grid">
        <article
          v-for="(room, i) in paginatedRooms"
          :key="room.id"
          class="post-flyer"
          :style="{ '--tilt': tiltFor(i) }"
          @click="goToRoom(room.id)"
        >
          <span class="post-flyer__tape tape--kraft"></span>
          <div class="post-flyer__image">
            <span v-if="room.badge" class="post-flyer__badge badge--new">{{ room.badge }}</span>
            <img :src="room.image" :alt="room.title" />
            <button
              class="post-flyer__fav"
              :class="{ 'is-active': room.favorite }"
              @click.stop="room.favorite = !room.favorite"
              aria-label="Yêu thích"
            >
              ♥
            </button>
          </div>

          <h3 class="post-flyer__title">{{ room.title }}</h3>
          <p class="post-flyer__address">📍 {{ room.address }}</p>

          <div class="tag-chips">
            <span>{{ room.area }}</span>
            <span>{{ room.capacity }} người</span>
            <span v-if="!room.available" class="tag-chips__unavailable">Hết phòng</span>
            <span v-for="a in room.amenities.slice(0, 2)" :key="a">{{ a }}</span>
          </div>

          <div class="post-flyer__footer">
            <span class="price-tag">{{ room.price }}/tháng</span>
            <span class="detail-link">Xem chi tiết →</span>
          </div>
        </article>
      </div>

      <div v-else class="empty-state">
        <p>😕 Không tìm thấy phòng nào phù hợp với "{{ keyword || 'bộ lọc hiện tại' }}".</p>
        <button class="btn-reset" @click="clearAll">Xóa bộ lọc và tìm lại</button>
      </div>

      <!-- ============ PHÂN TRANG ============ -->
      <nav v-if="totalPages > 1" class="notebook-pagination" aria-label="Phân trang kết quả">
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
    </div>

    <Footer />
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import Header from '../../components/layout/Header.vue'
import Footer from '../../components/layout/Footer.vue'
import { getAllPhongTro } from '../../api/phongTroApi'

const route = useRoute()
const router = useRouter()

/* ---------------- Dữ liệu ---------------- */
const mockRooms = [
  { id: 1, title: 'Phòng trọ 2 người, máy lạnh mới, gần Đại học', price: '4 triệu', priceValue: 4, area: '30 m²', areaValue: 30, address: 'Trung Mỹ Tây, Quận 12', category: 'Phòng trọ', amenities: ['Wifi', 'Điều hòa', 'Gửi xe'], capacity: 2, minDuration: 3, gender: 'khong-yeu-cau', available: true, badge: 'Mới đăng', favorite: false, image: 'https://loremflickr.com/400/300/bedroom,rentroom?lock=901' },
  { id: 2, title: 'Phòng trọ 2 người, máy lạnh, nội thất cơ bản', price: '3 triệu', priceValue: 3, area: '22 m²', areaValue: 22, address: 'Quận Tân Bình', category: 'Phòng trọ', amenities: ['Wifi'], capacity: 2, minDuration: 1, gender: 'khong-yeu-cau', available: true, favorite: false, image: 'https://loremflickr.com/400/300/bedroom,simple?lock=902' },
  { id: 3, title: 'Căn hộ gác lửng đầy đủ nội thất, gần Đại học', price: '4 triệu', priceValue: 4, area: '30 m²', areaValue: 30, address: 'Trung Mỹ Tây, Quận 12', category: 'Chung cư mini', amenities: ['Wifi', 'Điều hòa', 'Gửi xe', 'Nuôi thú'], capacity: 3, minDuration: 6, gender: 'khong-yeu-cau', available: true, favorite: false, image: 'https://loremflickr.com/400/300/loft,mezzanine,apartment?lock=903' },
  { id: 4, title: 'Phòng trọ 2 người, máy lạnh mới, gần Đại học', price: '4 triệu', priceValue: 4, area: '30 m²', areaValue: 30, address: 'Trung Mỹ Tây, Quận 12', category: 'Phòng trọ', amenities: ['Wifi', 'Điều hòa'], capacity: 2, minDuration: 3, gender: 'nu', available: false, favorite: false, image: 'https://loremflickr.com/400/300/bedroom,student?lock=904' },
  { id: 5, title: 'Studio gác lửng màu sắc, gần Đại học', price: '4 triệu', priceValue: 4, area: '30 m²', areaValue: 30, address: 'Trung Mỹ Tây, Quận 12', category: 'Chung cư mini', amenities: ['Wifi', 'Điều hòa', 'Gửi xe'], capacity: 1, minDuration: 1, gender: 'khong-yeu-cau', available: true, favorite: false, image: 'https://loremflickr.com/400/300/loft,colorful,apartment?lock=905' },
  { id: 6, title: 'Phòng trọ 2 người, máy lạnh mới, gần Đại học', price: '4 triệu', priceValue: 4, area: '30 m²', areaValue: 30, address: 'Trung Mỹ Tây, Quận 12', category: 'Phòng trọ', amenities: ['Wifi', 'Điều hòa'], capacity: 2, minDuration: 3, gender: 'nam', available: true, favorite: false, image: 'https://loremflickr.com/400/300/bedroom,cozy,student?lock=906' },
  { id: 7, title: 'Nhà nguyên căn thoáng mát, gần Đại học', price: '4 triệu', priceValue: 4, area: '30 m²', areaValue: 30, address: 'Trung Mỹ Tây, Quận 12', category: 'Ở ghép', amenities: ['Wifi', 'Gửi xe'], capacity: 3, minDuration: 6, gender: 'cap-doi', available: true, favorite: false, image: 'https://loremflickr.com/400/300/house,room,bright?lock=907' },
  { id: 8, title: 'Studio gác lửng màu sắc, gần Đại học', price: '4 triệu', priceValue: 4, area: '30 m²', areaValue: 30, address: 'Trung Mỹ Tây, Quận 12', category: 'Chung cư mini', amenities: ['Wifi', 'Điều hòa', 'Gửi xe', 'Nuôi thú'], capacity: 2, minDuration: 12, gender: 'khong-yeu-cau', available: true, favorite: false, image: 'https://loremflickr.com/400/300/loft,apartment,design?lock=908' },
  { id: 9, title: 'Ký túc xá sinh viên mới xây', price: '1,5 triệu', priceValue: 1.5, area: '18 m²', areaValue: 18, address: 'Quận Thủ Đức', category: 'Ký túc xá', amenities: ['Wifi'], capacity: 4, minDuration: 6, gender: 'nam', available: true, favorite: false, image: 'https://loremflickr.com/400/300/dormitory,student?lock=909' },
  { id: 10, title: 'Chung cư mini full nội thất, view đẹp', price: '6,5 triệu', priceValue: 6.5, area: '40 m²', areaValue: 40, address: 'Quận 7, TP. HCM', category: 'Chung cư mini', amenities: ['Wifi', 'Điều hòa', 'Gửi xe', 'Nuôi thú'], capacity: 2, minDuration: 12, gender: 'khong-yeu-cau', available: true, favorite: false, image: 'https://loremflickr.com/400/300/apartment,luxury,view?lock=910' },
  { id: 11, title: 'Phòng ở ghép nữ, an ninh tốt', price: '2 triệu', priceValue: 2, area: '25 m²', areaValue: 25, address: 'Quận Bình Thạnh', category: 'Ở ghép', amenities: ['Wifi', 'Điều hòa'], capacity: 3, minDuration: 1, gender: 'nu', available: true, favorite: false, image: 'https://loremflickr.com/400/300/roommates,sharedroom?lock=911' },
  { id: 12, title: 'Phòng trọ giá rẻ gần chợ', price: '2,2 triệu', priceValue: 2.2, area: '20 m²', areaValue: 20, address: 'Quận Gò Vấp', category: 'Phòng trọ', amenities: ['Gửi xe'], capacity: 1, minDuration: 0, gender: 'khong-yeu-cau', available: false, favorite: false, image: 'https://loremflickr.com/400/300/bedroom,budget?lock=912' }
]

const rooms = ref([...mockRooms])

async function loadRooms() {
  try {
    const { data } = await getAllPhongTro()
    if (Array.isArray(data) && data.length) {
      rooms.value = data.map((item, index) => {
        const fallback = mockRooms[index % mockRooms.length]
        return {
          id: item.maPhongTro ?? item.id ?? index,
          title: item.tieuDe ?? item.tenPhong ?? fallback.title,
          price: item.gia ? `${item.gia}` : fallback.price,
          priceValue: Number(item.gia) || fallback.priceValue,
          area: item.dienTich ? `${item.dienTich} m²` : fallback.area,
          areaValue: Number(item.dienTich) || fallback.areaValue,
          address: item.diaChi ?? fallback.address,
          category: item.danhMuc ?? fallback.category,
          amenities: fallback.amenities,
          capacity: item.soNguoiO ?? fallback.capacity,
          minDuration: item.thoiHanToiThieu ?? fallback.minDuration,
          gender: item.doiTuong ?? fallback.gender,
          available: item.conTrong ?? fallback.available,
          favorite: false,
          image: item.hinhAnh ?? fallback.image
        }
      })
    }
  } catch (error) {
    console.warn('Không tải được danh sách phòng, dùng dữ liệu mẫu:', error?.message)
  }
}
onMounted(loadRooms)

/* ---------------- Tìm kiếm + lọc ---------------- */
const keyword = ref(route.query.keyword || '')
const showFilters = ref(false)
const categoryOptions = ['Phòng trọ', 'Chung cư mini', 'Ký túc xá', 'Ở ghép']
const amenityOptions = ['Wifi', 'Điều hòa', 'Gửi xe', 'Nuôi thú', 'Ban công', 'Thang máy', 'Bảo vệ 24/7', 'Máy giặt', 'Khép kín (WC riêng)', 'Gác lửng']
const capacityOptions = [
  { value: 1, label: '1 người' },
  { value: 2, label: '2 người' },
  { value: 3, label: '3+ người' }
]
const genderOptions = [
  { value: 'nam', label: 'Nam' },
  { value: 'nu', label: 'Nữ' },
  { value: 'cap-doi', label: 'Cặp đôi' },
  { value: 'khong-yeu-cau', label: 'Không yêu cầu' }
]
const durationLabels = { 0: 'Theo ngày', 1: 'Từ 1 tháng', 3: 'Từ 3 tháng', 6: 'Từ 6 tháng', 12: 'Từ 12 tháng' }
const quickPriceOptions = [
  { label: 'Dưới 2tr', min: null, max: 2 },
  { label: '2 - 4tr', min: 2, max: 4 },
  { label: '4 - 6tr', min: 4, max: 6 },
  { label: 'Trên 6tr', min: 6, max: null }
]

const filters = reactive({
  category: route.query.category || '',
  priceMin: null,
  priceMax: null,
  areaMin: null,
  areaMax: null,
  capacity: '',
  minDuration: '',
  gender: '',
  amenities: [],
  availableOnly: false,
  sortBy: 'newest'
})

function toggleAmenity(a) {
  const idx = filters.amenities.indexOf(a)
  if (idx === -1) filters.amenities.push(a)
  else filters.amenities.splice(idx, 1)
}

const activeFilterCount = computed(() => {
  let n = 0
  if (filters.category) n++
  if (filters.priceMin != null || filters.priceMax != null) n++
  if (filters.areaMin != null || filters.areaMax != null) n++
  if (filters.capacity !== '') n++
  if (filters.minDuration !== '') n++
  if (filters.gender) n++
  if (filters.availableOnly) n++
  n += filters.amenities.length
  return n
})

const activeFilterTags = computed(() => {
  const tags = []
  if (filters.category) tags.push({ key: 'category', label: filters.category, clear: () => (filters.category = '') })
  if (filters.priceMin != null || filters.priceMax != null) {
    const label = `Giá: ${filters.priceMin ?? 0} - ${filters.priceMax ?? '∞'} triệu`
    tags.push({ key: 'price', label, clear: () => { filters.priceMin = null; filters.priceMax = null } })
  }
  if (filters.areaMin != null || filters.areaMax != null) {
    const label = `Diện tích: ${filters.areaMin ?? 0} - ${filters.areaMax ?? '∞'} m²`
    tags.push({ key: 'area', label, clear: () => { filters.areaMin = null; filters.areaMax = null } })
  }
  if (filters.capacity !== '') {
    tags.push({ key: 'capacity', label: capacityOptions.find((c) => c.value === filters.capacity)?.label, clear: () => (filters.capacity = '') })
  }
  if (filters.minDuration !== '') {
    tags.push({ key: 'duration', label: durationLabels[filters.minDuration], clear: () => (filters.minDuration = '') })
  }
  if (filters.gender) {
    tags.push({ key: 'gender', label: genderOptions.find((g) => g.value === filters.gender)?.label, clear: () => (filters.gender = '') })
  }
  if (filters.availableOnly) {
    tags.push({ key: 'available', label: 'Còn trống', clear: () => (filters.availableOnly = false) })
  }
  filters.amenities.forEach((a) => {
    tags.push({ key: `amenity-${a}`, label: a, clear: () => toggleAmenity(a) })
  })
  return tags
})

function applySearch() {
  currentPage.value = 1
  router.replace({ query: { ...route.query, keyword: keyword.value || undefined } })
}
function clearKeyword() {
  keyword.value = ''
  applySearch()
}
function resetFilters() {
  filters.category = ''
  filters.priceMin = null
  filters.priceMax = null
  filters.areaMin = null
  filters.areaMax = null
  filters.capacity = ''
  filters.minDuration = ''
  filters.gender = ''
  filters.amenities = []
  filters.availableOnly = false
  filters.amenities = []
}
function clearAll() {
  keyword.value = ''
  resetFilters()
  applySearch()
}

watch(
  () => route.query.keyword,
  (val) => { keyword.value = val || '' }
)

const filteredRooms = computed(() => {
  const result = rooms.value.filter((room) => {
    if (keyword.value) {
      const kw = keyword.value.toLowerCase()
      const match = room.title.toLowerCase().includes(kw) || room.address.toLowerCase().includes(kw)
      if (!match) return false
    }
    if (filters.category && room.category !== filters.category) return false
    if (filters.priceMin != null && room.priceValue < filters.priceMin) return false
    if (filters.priceMax != null && room.priceValue > filters.priceMax) return false
    if (filters.areaMin != null && room.areaValue < filters.areaMin) return false
    if (filters.areaMax != null && room.areaValue > filters.areaMax) return false
    if (filters.capacity !== '') {
      if (filters.capacity === 3 ? room.capacity < 3 : room.capacity !== filters.capacity) return false
    }
    if (filters.minDuration !== '' && room.minDuration < Number(filters.minDuration)) return false
    if (filters.gender && room.gender !== filters.gender && room.gender !== 'khong-yeu-cau') return false
    if (filters.availableOnly && !room.available) return false
    if (filters.amenities.length) {
      const hasAll = filters.amenities.every((a) => room.amenities.includes(a))
      if (!hasAll) return false
    }
    return true
  })

  const sorted = [...result]
  switch (filters.sortBy) {
    case 'price-asc': sorted.sort((a, b) => a.priceValue - b.priceValue); break
    case 'price-desc': sorted.sort((a, b) => b.priceValue - a.priceValue); break
    case 'area-desc': sorted.sort((a, b) => b.areaValue - a.areaValue); break
    default: break // 'newest' giữ nguyên thứ tự gốc
  }
  return sorted
})

function tiltFor(index) {
  const angles = [-2.5, 1.5, -1, 2, -1.8, 1]
  return `${angles[index % angles.length]}deg`
}

function goToRoom(id) {
  router.push(`/rooms/${id}`)
}

/* ---------------- Phân trang ---------------- */
const roomsPerPage = 8
const currentPage = ref(1)
const totalPages = computed(() => Math.max(1, Math.ceil(filteredRooms.value.length / roomsPerPage)))
const paginatedRooms = computed(() => {
  const start = (currentPage.value - 1) * roomsPerPage
  return filteredRooms.value.slice(start, start + roomsPerPage)
})
watch(filteredRooms, () => { if (currentPage.value > totalPages.value) currentPage.value = 1 })

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
  document.querySelector('.result-head')?.scrollIntoView({ behavior: 'smooth', block: 'start' })
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Archivo+Black&family=Be+Vietnam+Pro:wght@400;500;600;700;800&display=swap');
* { box-sizing: border-box; }

.rl {
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

.rl-hero { position: relative; padding: 22px 28px 100px; background: linear-gradient(160deg, var(--pine) 0%, var(--pine-dark) 100%); }
.rl-wrap { max-width: 1180px; margin: -46px auto 0; padding: 0 28px 60px; position: relative; }

/* ---------- Search bar ---------- */
.search-bar-slip { display: flex; gap: 12px; margin-bottom: 16px; }
.search-bar-slip__field {
  flex: 1; display: flex; align-items: center; gap: 10px;
  background: #fff; border-radius: 10px; padding: 4px 18px;
  box-shadow: 0 12px 24px rgba(33,29,23,0.15);
}
.search-bar-slip__icon { font-size: 15px; opacity: 0.5; }
.search-bar-slip__field input {
  flex: 1; border: none; outline: none; padding: 14px 0;
  font-size: 14.5px; font-family: inherit; color: var(--ink); background: transparent;
}
.search-bar-slip__clear { border: none; background: var(--paper-dark); color: var(--ink); width: 26px; height: 26px; border-radius: 50%; cursor: pointer; font-size: 12px; }

.filter-toggle {
  position: relative;
  display: flex; align-items: center; gap: 6px;
  border: none; border-radius: 10px; background: #fff; color: var(--ink);
  font-weight: 700; font-size: 14px; padding: 0 22px;
  box-shadow: 0 12px 24px rgba(33,29,23,0.15);
  cursor: pointer;
}
.filter-toggle.is-open { background: var(--pine); color: var(--paper); }
.filter-toggle__chevron { font-size: 12px; }
.filter-toggle__badge {
  position: absolute; top: -6px; right: -6px;
  background: var(--brick); color: #fff; font-size: 10.5px; font-weight: 800;
  width: 18px; height: 18px; border-radius: 50%; display: flex; align-items: center; justify-content: center;
}

/* ---------- Filter panel ---------- */
.fade-slide-enter-active, .fade-slide-leave-active { transition: all 0.18s ease; }
.fade-slide-enter-from, .fade-slide-leave-to { opacity: 0; transform: translateY(-6px); }

.filter-panel {
  background: #fff; border-radius: 12px; padding: 22px 24px;
  box-shadow: 0 12px 24px rgba(33,29,23,0.12);
  margin-bottom: 24px;
  display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px;
}
.filter-group { display: flex; flex-direction: column; gap: 10px; }
.filter-group--wide { grid-column: 1 / -1; }
.filter-group__label { font-size: 12px; font-weight: 700; text-transform: uppercase; letter-spacing: 0.4px; color: #6f6650; }
.chip-row { display: flex; flex-wrap: wrap; gap: 6px; }
.chip {
  border: 1px solid rgba(33,29,23,0.15); background: var(--paper); color: var(--ink);
  font-size: 12.5px; padding: 7px 12px; border-radius: 999px; cursor: pointer;
}
.chip.is-active { background: var(--pine); color: var(--paper); border-color: var(--pine); }
.filter-select {
  border: 1px solid rgba(33,29,23,0.15); border-radius: 8px; padding: 10px 12px;
  font-size: 13px; font-family: inherit; color: var(--ink); background: var(--paper);
}

.range-row { display: flex; align-items: center; gap: 8px; }
.range-input {
  width: 0; flex: 1; min-width: 0;
  border: 1px solid rgba(33,29,23,0.15); border-radius: 8px; padding: 9px 10px;
  font-size: 13px; font-family: inherit; color: var(--ink); background: var(--paper);
}
.range-input::-webkit-outer-spin-button, .range-input::-webkit-inner-spin-button { -webkit-appearance: none; margin: 0; }
.range-sep { color: #a89b7c; font-size: 12px; flex: none; }
.quick-row { display: flex; flex-wrap: wrap; gap: 6px; }
.quick-chip {
  border: 1px dashed rgba(33,29,23,0.25); background: transparent; color: #6f6650;
  font-size: 11.5px; padding: 5px 10px; border-radius: 999px; cursor: pointer;
}
.quick-chip:hover { border-color: var(--pine); color: var(--pine); }

.switch-row { display: flex; align-items: center; gap: 10px; font-size: 13px; cursor: pointer; user-select: none; }
.switch-row input { display: none; }
.switch-track {
  width: 38px; height: 22px; border-radius: 999px; background: rgba(33,29,23,0.2);
  position: relative; transition: background 0.15s ease; flex: none;
}
.switch-thumb {
  position: absolute; top: 2px; left: 2px; width: 18px; height: 18px; border-radius: 50%;
  background: #fff; transition: transform 0.15s ease; box-shadow: 0 1px 3px rgba(0,0,0,0.3);
}
.switch-row input:checked + .switch-track { background: var(--pine); }
.switch-row input:checked + .switch-track .switch-thumb { transform: translateX(16px); }

.filter-panel__actions { grid-column: 1 / -1; display: flex; justify-content: flex-end; gap: 10px; border-top: 1px dashed rgba(33,29,23,0.15); padding-top: 16px; }
.btn-reset { border: 1.5px solid var(--ink); background: #fff; color: var(--ink); font-weight: 700; font-size: 13px; padding: 10px 18px; border-radius: 8px; cursor: pointer; }
.btn-apply { border: none; background: var(--brick); color: var(--paper); font-weight: 700; font-size: 13px; padding: 10px 20px; border-radius: 8px; cursor: pointer; }
.btn-apply:hover { background: var(--brick-dark); }

/* ---------- Active filter tags ---------- */
.active-tags { display: flex; flex-wrap: wrap; align-items: center; gap: 8px; margin-bottom: 20px; }
.active-tag {
  display: inline-flex; align-items: center; gap: 6px;
  background: #fff; border: 1px solid rgba(31,75,63,0.3); color: var(--pine-dark);
  font-size: 12px; font-weight: 600; padding: 6px 12px; border-radius: 999px; cursor: pointer;
}
.active-tag:hover { background: rgba(31,75,63,0.06); }
.active-tags__clear-all { border: none; background: none; color: var(--brick); font-size: 12px; font-weight: 700; cursor: pointer; text-decoration: underline; }

.tag-chips__unavailable { background: var(--brick) !important; color: #fff !important; font-weight: 700; }

/* ---------- Result head ---------- */
.result-head { display: flex; align-items: baseline; justify-content: space-between; margin-bottom: 22px; flex-wrap: wrap; gap: 6px; }
.result-title { margin: 0; font-family: 'Archivo Black', sans-serif; font-size: 22px; }
.result-count { font-size: 13px; color: #6f6650; }

/* ---------- Grid (tái sử dụng style tờ rơi) ---------- */
.flyer-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 26px 22px; }
.post-flyer {
  position: relative; background: #fff; border-radius: 4px 10px 6px 10px;
  padding: 12px 12px 16px; box-shadow: 0 12px 24px rgba(33,29,23,0.12);
  transform: rotate(var(--tilt)); transition: transform 0.2s ease; cursor: pointer;
}
.post-flyer:hover { transform: rotate(0deg) translateY(-4px); }
.post-flyer__tape {
  position: absolute; top: -9px; left: 50%; width: 52px; height: 18px; margin-left: -26px;
  background: rgba(214, 186, 130, 0.55); mix-blend-mode: multiply;
  clip-path: polygon(6% 0%, 94% 0%, 100% 20%, 88% 34%, 100% 55%, 90% 70%, 100% 88%, 94% 100%, 6% 100%, 14% 82%, 0% 64%, 12% 48%, 2% 28%, 10% 12%);
  box-shadow: 0 2px 4px rgba(33,29,23,0.15);
}
.post-flyer__image { position: relative; border-radius: 4px; overflow: hidden; }
.post-flyer__image img { width: 100%; height: 140px; object-fit: cover; display: block; }
.post-flyer__badge { position: absolute; top: 8px; left: 8px; font-size: 10.5px; font-weight: 700; padding: 4px 9px; border-radius: 3px; }
.badge--new { background: var(--mustard); color: var(--ink); }
.post-flyer__fav { position: absolute; right: 8px; bottom: 8px; width: 26px; height: 26px; border-radius: 50%; border: none; background: rgba(255,255,255,0.92); color: #c9c0aa; font-size: 13px; cursor: pointer; }
.post-flyer__fav.is-active { color: var(--brick); }
.post-flyer__title { margin: 12px 0 4px; font-size: 13.5px; font-weight: 800; color: var(--pine-dark); line-height: 1.3; min-height: 35px; }
.post-flyer__address { margin: 0 0 10px; font-size: 12px; color: #6f6650; }

.tag-chips { display: flex; flex-wrap: wrap; gap: 5px; margin-bottom: 12px; }
.tag-chips span { font-size: 10.5px; background: var(--paper); color: #4a4335; padding: 4px 9px; border-radius: 999px; }

.post-flyer__footer { display: flex; align-items: center; justify-content: space-between; }
.price-tag { font-family: 'Archivo Black', sans-serif; font-size: 12.5px; color: var(--paper); background: var(--brick); padding: 5px 10px; border-radius: 3px 8px 8px 3px; }
.detail-link { font-size: 11.5px; font-weight: 700; color: var(--pine); }

.empty-state { text-align: center; padding: 60px 0; color: #6f6650; display: flex; flex-direction: column; align-items: center; gap: 16px; }

/* ---------- Pagination ---------- */
.notebook-pagination { display: flex; justify-content: center; gap: 6px; margin-top: 40px; }
.notebook-tab {
  min-width: 36px; height: 36px; padding: 0 8px;
  border: 1px solid rgba(33,29,23,0.15); border-bottom: 3px solid rgba(33,29,23,0.15);
  border-radius: 4px 4px 0 0; background: #fff; font-weight: 700; font-size: 13px; color: var(--ink); cursor: pointer;
}
.notebook-tab:hover:not(:disabled) { transform: translateY(-2px); }
.notebook-tab.is-active { background: var(--pine); border-color: var(--pine); border-bottom-color: var(--mustard); color: var(--paper); }
.notebook-tab:disabled { opacity: 0.4; cursor: not-allowed; }

/* ---------- Responsive ---------- */
@media (max-width: 1024px) {
  .flyer-grid { grid-template-columns: repeat(3, 1fr); }
  .filter-panel { grid-template-columns: repeat(2, 1fr); }
}
@media (max-width: 720px) {
  .flyer-grid { grid-template-columns: repeat(2, 1fr); }
  .search-bar-slip { flex-direction: column; }
}
@media (max-width: 480px) {
  .flyer-grid { grid-template-columns: 1fr; }
}
</style>