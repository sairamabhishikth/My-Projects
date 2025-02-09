import axios from "axios";

// Create an Axios instance with base URL
const axiosInstance = axios.create({
  baseURL: "http://localhost:8080", // ✅ Your Backend API URL
});

// ✅ Attach Token to Every Request
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("authToken"); // ✅ Get token from localStorage
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default axiosInstance;
