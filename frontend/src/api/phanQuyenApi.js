import axiosClient from './axiosClient'

const BASE_URL = '/phan-quyen'

export const getAllPhanQuyen = (params) => axiosClient.get(BASE_URL, { params })
export const getPhanQuyenById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createPhanQuyen = (data) => axiosClient.post(BASE_URL, data)
export const updatePhanQuyen = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deletePhanQuyen = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
