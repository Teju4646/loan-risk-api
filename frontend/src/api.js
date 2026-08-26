import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
});

// Attach JWT token to every request if present
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const register = (username, password) =>
  api.post('/api/auth/register', { username, password });

export const login = (username, password) =>
  api.post('/api/auth/login', { username, password });

export const submitApplicant = (applicantData) =>
  api.post('/api/applicants', applicantData);

export const getHistory = (applicantId) =>
  api.get(`/api/applicants/${applicantId}/history`);

export default api;
