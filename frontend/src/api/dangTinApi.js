import axiosClient from './axiosClient'

const BASE_URL = '/dang-tin'

export const getAllDangTin = (params) => axiosClient.get(BASE_URL, { params })
export const getDangTinById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createDangTin = (data) => axiosClient.post(BASE_URL, data)
export const updateDangTin = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteDangTin = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
