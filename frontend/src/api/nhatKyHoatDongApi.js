import axiosClient from './axiosClient'

const BASE_URL = '/nhat-ky-hoat-dong'

export const getAllNhatKyHoatDong = (params) => axiosClient.get(BASE_URL, { params })
export const getNhatKyHoatDongById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createNhatKyHoatDong = (data) => axiosClient.post(BASE_URL, data)
export const updateNhatKyHoatDong = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteNhatKyHoatDong = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
