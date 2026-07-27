import axiosClient from './axiosClient'

const BASE_URL = '/yeu-cau-thue'

export const getAllYeuCauThue = (params) => axiosClient.get(BASE_URL, { params })
export const getYeuCauThueById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createYeuCauThue = (data) => axiosClient.post(BASE_URL, data)
export const updateYeuCauThue = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteYeuCauThue = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
