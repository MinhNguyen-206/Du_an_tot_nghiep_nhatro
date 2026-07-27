import axiosClient from './axiosClient'

const BASE_URL = '/nguoi-dung'

export const getAllNguoiDung = (params) => axiosClient.get(BASE_URL, { params })
export const getNguoiDungById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createNguoiDung = (data) => axiosClient.post(BASE_URL, data)
export const updateNguoiDung = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteNguoiDung = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
