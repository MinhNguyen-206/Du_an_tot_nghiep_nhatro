import axiosClient from './axiosClient'

const BASE_URL = '/lich-su-xem-phong'

export const getAllLichSuXemPhong = (params) => axiosClient.get(BASE_URL, { params })
export const getLichSuXemPhongById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createLichSuXemPhong = (data) => axiosClient.post(BASE_URL, data)
export const updateLichSuXemPhong = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteLichSuXemPhong = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
