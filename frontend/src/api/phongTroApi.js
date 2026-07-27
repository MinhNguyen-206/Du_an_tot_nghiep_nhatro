import axiosClient from './axiosClient'

const BASE_URL = '/phong-tro'

export const getAllPhongTro = (params) => axiosClient.get(BASE_URL, { params })
export const getPhongTroById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createPhongTro = (data) => axiosClient.post(BASE_URL, data)
export const updatePhongTro = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deletePhongTro = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
