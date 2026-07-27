import axiosClient from './axiosClient'

const BASE_URL = '/goi-premium'

export const getAllGoiPremium = (params) => axiosClient.get(BASE_URL, { params })
export const getGoiPremiumById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createGoiPremium = (data) => axiosClient.post(BASE_URL, data)
export const updateGoiPremium = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteGoiPremium = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
