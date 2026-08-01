import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/authStore'

// Các component trang sẽ được migrate dần sang .vue
// Tạm thời dùng import lazy
const Home = () => import('../pages/home/Home.vue')
const Login = () => import('../pages/auth/Login.vue')
const Register = () => import('../pages/auth/Register.vue')
const OtpVerify = () => import('../pages/auth/OtpVerify.vue')
const ForgotPassword = () => import('../pages/auth/ForgotPassword.vue')
const Profile = () => import('../pages/profile/Profile.vue')
const RoomList = () => import('../pages/room/RoomList.vue')
const RoomDetail = () => import('../pages/room/RoomDetail.vue')
const RoomViewHistory = () => import('../pages/room/RoomViewHistory.vue')
const NewRoomAlert = () => import('../pages/room/NewRoomAlert.vue')
const AdminDashboard = () => import('../pages/admin/AdminDashboard.vue')
const PostManagement = () => import('../pages/post/PostManagement.vue')
const PaymentManagement = () => import('../pages/payment/PaymentManagement.vue')
const ContractManagement = () => import('../pages/contract/ContractManagement.vue')

// Trang mới bổ sung
const UserManagement = () => import('../pages/admin/UserManagement.vue')
const RoleManagement = () => import('../pages/admin/RoleManagement.vue')
const AdminAccountManagement = () => import('../pages/admin/AdminAccountManagement.vue')
const CategoryConfigManagement = () => import('../pages/admin/CategoryConfigManagement.vue')
const ActivityLogManagement = () => import('../pages/admin/ActivityLogManagement.vue')
const ViolationReportManagement = () => import('../pages/admin/ViolationReportManagement.vue')
const AiControlManagement = () => import('../pages/admin/AiControlManagement.vue')
const HouseManagement = () => import('../pages/admin/HouseManagement.vue')

const NotificationList = () => import('../pages/notification/NotificationList.vue')
const MessageBox = () => import('../pages/message/MessageBox.vue')
const AppointmentManagement = () => import('../pages/appointment/AppointmentManagement.vue')
const RentalRequestManagement = () => import('../pages/rental-request/RentalRequestManagement.vue')
const ReviewManagement = () => import('../pages/review/ReviewManagement.vue')
const UtilityIndexManagement = () => import('../pages/utility/UtilityIndexManagement.vue')
const MonthlyInvoiceManagement = () => import('../pages/invoice/MonthlyInvoiceManagement.vue')
const DepositPaymentManagement = () => import('../pages/deposit/DepositPaymentManagement.vue')
const ServicePackageManagement = () => import('../pages/service-package/ServicePackageManagement.vue')

const PremiumPackageList = () => import('../pages/premium/PremiumPackageList.vue')
const PremiumSubscription = () => import('../pages/premium/PremiumSubscription.vue')
const PremiumSubscriptionHistory = () => import('../pages/premium/PremiumSubscriptionHistory.vue')
const PremiumContractManagement = () => import('../pages/premium/PremiumContractManagement.vue')
const PremiumInvoiceManagement = () => import('../pages/premium/PremiumInvoiceManagement.vue')

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/login', name: 'Login', component: Login },
  { path: '/register', name: 'Register', component: Register },
  { path: '/verify-otp', name: 'OtpVerify', component: OtpVerify },
  { path: '/forgot-password', name: 'ForgotPassword', component: ForgotPassword },

  { path: '/profile', name: 'Profile', component: Profile, meta: { requiresAuth: true } },

  { path: '/rooms', name: 'RoomList', component: RoomList },
  { path: '/rooms/:id', name: 'RoomDetail', component: RoomDetail },
  { path: '/rooms/view-history', name: 'RoomViewHistory', component: RoomViewHistory, meta: { requiresAuth: true } },
  { path: '/rooms/new-alerts', name: 'NewRoomAlert', component: NewRoomAlert, meta: { requiresAuth: true } },

  { path: '/notifications', name: 'NotificationList', component: NotificationList, meta: { requiresAuth: true } },
  { path: '/messages', name: 'MessageBox', component: MessageBox, meta: { requiresAuth: true } },
  { path: '/appointments', name: 'AppointmentManagement', component: AppointmentManagement, meta: { requiresAuth: true } },
  { path: '/rental-requests', name: 'RentalRequestManagement', component: RentalRequestManagement, meta: { requiresAuth: true } },
  { path: '/reviews', name: 'ReviewManagement', component: ReviewManagement, meta: { requiresAuth: true } },
  { path: '/utility-index', name: 'UtilityIndexManagement', component: UtilityIndexManagement, meta: { requiresAuth: true } },
  { path: '/invoices/monthly', name: 'MonthlyInvoiceManagement', component: MonthlyInvoiceManagement, meta: { requiresAuth: true } },
  { path: '/deposits', name: 'DepositPaymentManagement', component: DepositPaymentManagement, meta: { requiresAuth: true } },
  { path: '/service-packages', name: 'ServicePackageManagement', component: ServicePackageManagement, meta: { requiresAuth: true } },

  { path: '/premium/packages', name: 'PremiumPackageList', component: PremiumPackageList, meta: { requiresAuth: true } },
  { path: '/premium/subscribe', name: 'PremiumSubscription', component: PremiumSubscription, meta: { requiresAuth: true } },
  { path: '/premium/subscription-history', name: 'PremiumSubscriptionHistory', component: PremiumSubscriptionHistory, meta: { requiresAuth: true } },
  { path: '/premium/contracts', name: 'PremiumContractManagement', component: PremiumContractManagement, meta: { requiresAuth: true } },
  { path: '/premium/invoices', name: 'PremiumInvoiceManagement', component: PremiumInvoiceManagement, meta: { requiresAuth: true } },

  { path: '/admin', name: 'AdminDashboard', component: AdminDashboard, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/admin/posts', name: 'PostManagement', component: PostManagement, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/admin/payments', name: 'PaymentManagement', component: PaymentManagement, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/admin/contracts', name: 'ContractManagement', component: ContractManagement, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/admin/users', name: 'UserManagement', component: UserManagement, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/admin/roles', name: 'RoleManagement', component: RoleManagement, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/admin/admins', name: 'AdminAccountManagement', component: AdminAccountManagement, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/admin/categories', name: 'CategoryConfigManagement', component: CategoryConfigManagement, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/admin/activity-logs', name: 'ActivityLogManagement', component: ActivityLogManagement, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/admin/violation-reports', name: 'ViolationReportManagement', component: ViolationReportManagement, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/admin/ai-control', name: 'AiControlManagement', component: AiControlManagement, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/admin/houses', name: 'HouseManagement', component: HouseManagement, meta: { requiresAuth: true, requiresAdmin: true } },

  // Catch all cho 404
  { path: '/:pathMatch(.*)*', redirect: '/' }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()

  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next('/login')
    return
  }

  if (to.meta.requiresAdmin && authStore.user?.vaiTro !== 3) {
    next('/')
    return
  }

  next()
})

export default router