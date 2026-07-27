import axiosClient from './axiosClient'

const BASE_URL = '/bao-cao-vi-pham'

export const getAllBaoCaoViPham = (params) => axiosClient.get(BASE_URL, { params })
export const getBaoCaoViPhamById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createBaoCaoViPham = (data) => axiosClient.post(BASE_URL, data)
export const updateBaoCaoViPham = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteBaoCaoViPham = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
