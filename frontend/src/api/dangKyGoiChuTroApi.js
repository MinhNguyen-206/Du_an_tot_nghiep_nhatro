import axiosClient from './axiosClient'

const BASE_URL = '/dang-ky-goi-chu-tro'

export const getAllDangKyGoiChuTro = (params) => axiosClient.get(BASE_URL, { params })
export const getDangKyGoiChuTroById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createDangKyGoiChuTro = (data) => axiosClient.post(BASE_URL, data)
export const updateDangKyGoiChuTro = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteDangKyGoiChuTro = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
