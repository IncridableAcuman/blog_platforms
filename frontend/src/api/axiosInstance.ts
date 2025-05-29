import axios from 'axios';
import { toast } from 'sonner';

const axiosInstance = axios.create({
    withCredentials: true,
    baseURL: "http://localhost:8080/api"
});

// Request Interceptor
axiosInstance.interceptors.request.use(config => {
    const token = localStorage.getItem("accessToken");
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
}, error => Promise.reject(error));

// Response Interceptor
axiosInstance.interceptors.response.use(
    response => response,
    async error => {
        const originalRequest = error.config;

        if (error.response?.status === 401 && !originalRequest._isRetry) {
            originalRequest._isRetry = true;
            try {
                // Use a fresh axios instance for refresh
                const { data } = await axios.get("http://localhost:8080/api/auth/refresh", {
                    withCredentials: true
                });
                localStorage.setItem("accessToken", data.accessToken);
                originalRequest.headers['Authorization'] = `Bearer ${data.accessToken}`;
                return axiosInstance(originalRequest);
            } catch (err) {
                console.log("Refresh failed", err);
                localStorage.removeItem("accessToken");
                toast.error("Session expired. Please log in again.");
                window.location.href = "/login";
                return Promise.reject(err);
            }
        }

        return Promise.reject(error);
    }
);

export default axiosInstance;
