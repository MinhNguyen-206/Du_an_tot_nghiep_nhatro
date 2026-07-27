import axiosClient from './axiosClient'

const BASE_URL = '/hop-dong'

export const getAllHopDongDienTu = (params) => axiosClient.get(BASE_URL, { params })
export const getHopDongDienTuById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createHopDongDienTu = (data) => axiosClient.post(BASE_URL, data)
export const updateHopDongDienTu = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteHopDongDienTu = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
