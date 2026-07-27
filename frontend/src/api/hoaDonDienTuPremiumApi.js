import axiosClient from './axiosClient'

const BASE_URL = '/hoa-don-dien-tu-premium'

export const getAllHoaDonDienTuPremium = (params) => axiosClient.get(BASE_URL, { params })
export const getHoaDonDienTuPremiumById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createHoaDonDienTuPremium = (data) => axiosClient.post(BASE_URL, data)
export const updateHoaDonDienTuPremium = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteHoaDonDienTuPremium = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
