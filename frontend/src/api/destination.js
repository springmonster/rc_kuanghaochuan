import axios from 'axios'

const API_BASE = 'http://localhost:8080/api/v1'

const api = axios.create({
  baseURL: API_BASE,
  timeout: 10000
})

export const destinationApi = {
  getAll() {
    return api.get('/destinations')
  },

  getById(id) {
    return api.get(`/destinations/${id}`)
  },

  create(data) {
    return api.post('/destinations', data)
  },

  update(id, data) {
    return api.put(`/destinations/${id}`, data)
  },

  delete(id) {
    return api.delete(`/destinations/${id}`)
  }
}

export default api
