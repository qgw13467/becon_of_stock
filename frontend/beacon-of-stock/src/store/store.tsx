import { create } from 'zustand'

interface loginState {
  isLogin: boolean
  setIsLogin: (by: boolean) => void
  setIsLogout : (by: boolean) => void
}

export const useLoginStore = create<loginState>((set) => ({
    isLogin: false,
    setIsLogin: (by) => {
      set(() => ({isLogin:by}))
    },
    setIsLogout: (by) => {
      set(() => ({isLogin:by}))
    },
  })
)
