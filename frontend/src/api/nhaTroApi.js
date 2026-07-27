import axiosClient from './axiosClient'

const BASE_URL = '/nha-tro'

export const getAllNhaTro = (params) => axiosClient.get(BASE_URL, { params })
export const getNhaTroById = (id) => axiosClient.get(`${BASE_URL}/${id}`)
export const createNhaTro = (data) => axiosClient.post(BASE_URL, data)
export const updateNhaTro = (id, data) => axiosClient.put(`${BASE_URL}/${id}`, data)
export const deleteNhaTro = (id) => axiosClient.delete(`${BASE_URL}/${id}`)
