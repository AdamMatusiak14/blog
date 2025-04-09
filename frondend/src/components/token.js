import axios from "axios";

const token = axios.create({
  baseURL: "http://localhost:8080/api", // Adres backendu
});

// Interceptor dodający token do każdego żądania
token.interceptors.request.use(config => {
  const token = sessionStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  console.log("Token:", token);  
  console.log("Nagłówki przed wysłaniem:", config.headers);

  return config;
}, error => Promise.reject(error));

export default token;



