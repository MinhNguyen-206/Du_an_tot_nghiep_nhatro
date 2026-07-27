import axiosClient from './axiosClient'

const BASE_URL = '/lich-hen'

export const getAllLichHen = (params) => axiosClient.get(BASE_URL, { params })
export const getLichHenById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createLichHen = (data) => axiosClient.post(BASE_URL, data)
export const updateLichHen = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteLichHen = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
