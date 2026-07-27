import axiosClient from './axiosClient'

const BASE_URL = '/hop-dong-premium'

export const getAllHopDongPremium = (params) => axiosClient.get(BASE_URL, { params })
export const getHopDongPremiumById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createHopDongPremium = (data) => axiosClient.post(BASE_URL, data)
export const updateHopDongPremium = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteHopDongPremium = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
