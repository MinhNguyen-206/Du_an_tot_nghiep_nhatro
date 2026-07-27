import axiosClient from './axiosClient'

const BASE_URL = '/goi-dich-vu'

export const getAllGoiDichVu = (params) => axiosClient.get(BASE_URL, { params })
export const getGoiDichVuById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createGoiDichVu = (data) => axiosClient.post(BASE_URL, data)
export const updateGoiDichVu = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteGoiDichVu = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
