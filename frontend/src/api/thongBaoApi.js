import axiosClient from './axiosClient'

const BASE_URL = '/thong-bao'

export const getAllThongBao = (params) => axiosClient.get(BASE_URL, { params })
export const getThongBaoById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createThongBao = (data) => axiosClient.post(BASE_URL, data)
export const updateThongBao = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteThongBao = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
