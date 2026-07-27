import axiosClient from './axiosClient'

const BASE_URL = '/xac-thuc-otp'

export const getAllXacThucOtp = (params) => axiosClient.get(BASE_URL, { params })
export const getXacThucOtpById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createXacThucOtp = (data) => axiosClient.post(BASE_URL, data)
export const updateXacThucOtp = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteXacThucOtp = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
