import { createRouter, createWebHistory } from 'vue-router'

// Các component trang sẽ được migrate dần sang .vue
// Tạm thời dùng import lazy
const Home = () => import('../pages/home/Home.vue')
const Login = () => import('../pages/auth/Login.vue')
const Register = () => import('../pages/auth/Register.vue')
const Profile = () => import('../pages/profile/Profile.vue')
const RoomList = () => import('../pages/room/RoomList.vue')
const RoomDetail = () => import('../pages/room/RoomDetail.vue')
const AdminDashboard = () => import('../pages/admin/AdminDashboard.vue')
const PostManagement = () => import('../pages/post/PostManagement.vue')
const PaymentManagement = () => import('../pages/payment/PaymentManagement.vue')
const ContractManagement = () => import('../pages/contract/ContractManagement.vue')

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/login', name: 'Login', component: Login },
  { path: '/register', name: 'Register', component: Register },
  { path: '/profile', name: 'Profile', component: Profile },
  { path: '/rooms', name: 'RoomList', component: RoomList },
  { path: '/rooms/:id', name: 'RoomDetail', component: RoomDetail },
  { path: '/admin', name: 'AdminDashboard', component: AdminDashboard },
  { path: '/admin/posts', name: 'PostManagement', component: PostManagement },
  { path: '/admin/payments', name: 'PaymentManagement', component: PaymentManagement },
  { path: '/admin/contracts', name: 'ContractManagement', component: ContractManagement },
  
  // Catch all cho 404 có thể cấu hình sau
  { path: '/:pathMatch(.*)*', redirect: '/' }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
