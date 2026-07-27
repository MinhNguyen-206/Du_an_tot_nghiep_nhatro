import axiosClient from './axiosClient'

export const loginApi = (payload) => axiosClient.post('/auth/login', payload)

// Backend hiện chưa có endpoint /auth/register riêng,
// tạm dùng API tạo người dùng để đăng ký tài khoản mới
export const registerApi = (payload) => axiosClient.post('/nguoi-dung', payload)
