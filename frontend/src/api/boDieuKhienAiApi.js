import axiosClient from './axiosClient'

const BASE_URL = '/bo-dieu-khien-ai'

export const getAllBoDieuKhienAi = (params) => axiosClient.get(BASE_URL, { params })
export const getBoDieuKhienAiById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createBoDieuKhienAi = (data) => axiosClient.post(BASE_URL, data)
export const updateBoDieuKhienAi = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteBoDieuKhienAi = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
