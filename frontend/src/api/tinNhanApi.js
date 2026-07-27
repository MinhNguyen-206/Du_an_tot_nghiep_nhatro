import axiosClient from './axiosClient'

const BASE_URL = '/tin-nhan'

export const getAllTinNhan = (params) => axiosClient.get(BASE_URL, { params })
export const getTinNhanById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createTinNhan = (data) => axiosClient.post(BASE_URL, data)
export const updateTinNhan = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteTinNhan = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
