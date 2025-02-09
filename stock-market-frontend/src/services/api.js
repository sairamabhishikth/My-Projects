import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080", // Replace with your backend's base URL
});

// Add a request interceptor for attaching JWT tokens (for protected routes)
API.interceptors.request.use((config) => {
  const token = localStorage.getItem("token"); // Fetch token from localStorage
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default API;
