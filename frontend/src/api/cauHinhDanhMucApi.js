import axiosClient from './axiosClient'

const BASE_URL = '/cau-hinh-danh-muc'

export const getAllCauHinhDanhMuc = (params) => axiosClient.get(BASE_URL, { params })
export const getCauHinhDanhMucById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createCauHinhDanhMuc = (data) => axiosClient.post(BASE_URL, data)
export const updateCauHinhDanhMuc = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteCauHinhDanhMuc = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
