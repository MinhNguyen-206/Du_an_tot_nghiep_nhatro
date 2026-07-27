import axiosClient from './axiosClient'

const BASE_URL = '/thanh-toan-coc'

export const getAllThanhToanCoc = (params) => axiosClient.get(BASE_URL, { params })
export const getThanhToanCocById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createThanhToanCoc = (data) => axiosClient.post(BASE_URL, data)
export const updateThanhToanCoc = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteThanhToanCoc = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
