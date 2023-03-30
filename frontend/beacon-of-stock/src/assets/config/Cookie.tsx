import { Cookies } from 'react-cookie';

const cookies = new Cookies();

export const setCookie = (name: string, value: any, options?: object) => {
  const expirationTime = 30 * 60; // 30분
  const expirationDate = new Date(new Date().getTime() + expirationTime * 1000);
  return cookies.set(name, value, { ...options, expires: expirationDate });
};

export const getCookie = (name: string) => {
  return cookies.get(name);
};

export const removeCookie = (name: string, options?: object) => {
  return cookies.remove(name, { ...options });
};
