import axiosClient from './axiosClient'

const BASE_URL = '/hoa-don-thang'

export const getAllHoaDonThang = (params) => axiosClient.get(BASE_URL, { params })
export const getHoaDonThangById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createHoaDonThang = (data) => axiosClient.post(BASE_URL, data)
export const updateHoaDonThang = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteHoaDonThang = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
