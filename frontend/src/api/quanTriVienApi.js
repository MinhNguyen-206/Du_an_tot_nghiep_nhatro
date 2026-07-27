import axiosClient from './axiosClient'

const BASE_URL = '/quan-tri-vien'

export const getAllQuanTriVien = (params) => axiosClient.get(BASE_URL, { params })
export const getQuanTriVienById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createQuanTriVien = (data) => axiosClient.post(BASE_URL, data)
export const updateQuanTriVien = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteQuanTriVien = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
