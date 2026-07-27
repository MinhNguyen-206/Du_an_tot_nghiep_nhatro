import axiosClient from './axiosClient'

const BASE_URL = '/chi-so-dien-nuoc'

export const getAllChiSoDienNuoc = (params) => axiosClient.get(BASE_URL, { params })
export const getChiSoDienNuocById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createChiSoDienNuoc = (data) => axiosClient.post(BASE_URL, data)
export const updateChiSoDienNuoc = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteChiSoDienNuoc = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
