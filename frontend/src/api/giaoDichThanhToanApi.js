import axiosClient from './axiosClient'

const BASE_URL = '/giao-dich-thanh-toan'

export const getAllGiaoDichThanhToan = (params) => axiosClient.get(BASE_URL, { params })
export const getGiaoDichThanhToanById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createGiaoDichThanhToan = (data) => axiosClient.post(BASE_URL, data)
export const updateGiaoDichThanhToan = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteGiaoDichThanhToan = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
