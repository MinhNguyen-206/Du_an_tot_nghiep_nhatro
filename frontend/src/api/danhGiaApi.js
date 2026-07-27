import axiosClient from './axiosClient'

const BASE_URL = '/danh-gia'

export const getAllDanhGia = (params) => axiosClient.get(BASE_URL, { params })
export const getDanhGiaById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createDanhGia = (data) => axiosClient.post(BASE_URL, data)
export const updateDanhGia = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteDanhGia = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
