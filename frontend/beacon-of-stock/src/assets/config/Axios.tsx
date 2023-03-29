import axios, { AxiosInstance } from 'axios';

const axios_api: AxiosInstance = axios.create({
  baseURL: 'https://j8d207.p.ssafy.io/api',
  // baseURL: 'https://localhost:3000/api',
  headers: {
    'content-type': 'application/json;charset=UTF-8',
    accept: 'application/json,',
    authentication: '',
  },
});

export default axios_api;
