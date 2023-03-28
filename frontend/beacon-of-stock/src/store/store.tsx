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
interface Indicator {
  id: number;
  title: string;
}

const addIndicator = (
  indicators: Indicator[],
  id: number,
  title: string
): Indicator[] => [
  ...indicators,
  {
    id,
    title,
  },
];

const removeIndicator = (indicators: Indicator[], id: number): Indicator[] =>
  indicators.filter((indicator) => indicator.id !== id);

// zustand implementation
type backtestFactorStore = {
  indicators: Indicator[];
  addIndicator: (id: number, title: string) => void;
  removeIndicator: (id: number) => void;
  resetIndicator: () => void;
  loadIndicator: (indicators: Indicator[]) => void;
};

// set: mutate the state
export const useBacktestFactorStore = create<backtestFactorStore>((set) => ({
  indicators: [],
  addIndicator(id: number, title: string) {
    set((state) => ({
      ...state,
      indicators: addIndicator(state.indicators, id, title),
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
  loadIndicator(indicators: Indicator[]) {
    set((state) => ({
      ...state,
      indicators,
    }));
  },
}));
