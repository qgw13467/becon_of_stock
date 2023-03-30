import { create } from 'zustand';
import { getCookie } from '../assets/config/Cookie';

interface testState {
  isLogin: boolean;
  setIsLogin: (by: boolean) => void;
  setIsLogout: (by: boolean) => void;
}

export const useTestStore = create<testState>((set) => ({
  isLogin: !!getCookie('accessToken'),
  setIsLogin: (by) => {
    set(() => ({ isLogin: by }));
  },
  setIsLogout: (by) => {
    set(() => ({ isLogin: by }));
  },
}));
