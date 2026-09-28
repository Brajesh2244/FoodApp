/**
 * Category API service — maps to CategoryController.
 */
import api from '../api/axios';

const URL = '/api/categories';

const categoryService = {
  getAll: () => api.get(URL).then((r) => r.data),
  getById: (id) => api.get(`${URL}/${id}`).then((r) => r.data),
  create: (data) => api.post(URL, data).then((r) => r.data),
  update: (id, data) => api.put(`${URL}/${id}`, data).then((r) => r.data),
  delete: (id) => api.delete(`${URL}/${id}`),
};

export default categoryService;
