import axios, { AxiosInstance, AxiosResponse, AxiosError } from 'axios';

const axios_api: AxiosInstance = axios.create({
  baseURL: 'https://j8d207.p.ssafy.io/api',
  // baseURL: 'https://localhost:3000/api',
  headers: {
    'content-type': 'application/json;charset=UTF-8',
    accept: 'application/json,',
    authentication: '',
  },
});

axios_api.interceptors.response.use(
  (response: AxiosResponse) => {
    return response;
  },
  (error: AxiosError) => {
    if (error.response?.status === 404) {
      if (typeof window !== 'undefined') {
        window.location.assign('/*'); // 혹은 라우트에 맞는 경로로 변경
      }
    } else {
      return Promise.reject(error);
    }
  }
);

export default axios_api;
