import { create } from 'zustand';

interface loginState {
  isLogin: boolean;
  setIsLogin: (by: boolean) => void;
  setIsLogout: (by: boolean) => void;
}

export const useLoginStore = create<loginState>((set) => ({
  isLogin: true,
  setIsLogin: (by) => {
    set(() => ({ isLogin: by }));
  },
  setIsLogout: (by) => {
    set(() => ({ isLogin: by }));
  },
}));

// Backtest
// standard interface and functions

const addIndicator = (indicators: number[], id: number): number[] => [
  ...indicators,
  id,
];

const removeIndicator = (indicators: number[], id: number): number[] =>
  indicators.filter((indicator) => indicator !== id);

// zustand implementation
type backtestFactorStore = {
  indicators: number[];
  addIndicator: (id: number) => void;
  removeIndicator: (id: number) => void;
  resetIndicator: () => void;
  loadIndicator: (indicators: number[]) => void;
};

// set: mutate the state
export const useBacktestFactorStore = create<backtestFactorStore>((set) => ({
  indicators: [],
  addIndicator(id: number) {
    set((state) => ({
      ...state,
      indicators: addIndicator(state.indicators, id),
    }));
  },
  removeIndicator(id: number) {
    set((state) => ({
      ...state,
      indicators: removeIndicator(state.indicators, id),
    }));
  },
  resetIndicator() {
    set((state) => ({}));
  },
  loadIndicator(indicators: number[]) {
    set((state) => ({
      ...state,
      indicators,
    }));
  },
}));
