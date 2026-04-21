import axios from 'axios';

const apiClient = axios.create({
  baseURL: "http://localhost:8080/api", // ⚠️ 改成你的後端路徑
});

export default apiClient;
