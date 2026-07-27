import axiosClient from './axiosClient'

const BASE_URL = '/canh-bao-phong-moi'

export const getAllCanhBaoPhongMoi = (params) => axiosClient.get(BASE_URL, { params })
export const getCanhBaoPhongMoiById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createCanhBaoPhongMoi = (data) => axiosClient.post(BASE_URL, data)
export const updateCanhBaoPhongMoi = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteCanhBaoPhongMoi = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
