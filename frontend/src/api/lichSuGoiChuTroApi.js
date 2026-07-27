import axiosClient from './axiosClient'

const BASE_URL = '/lich-su-goi-chu-tro'

export const getAllLichSuGoiChuTro = (params) => axiosClient.get(BASE_URL, { params })
export const getLichSuGoiChuTroById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createLichSuGoiChuTro = (data) => axiosClient.post(BASE_URL, data)
export const updateLichSuGoiChuTro = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteLichSuGoiChuTro = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
