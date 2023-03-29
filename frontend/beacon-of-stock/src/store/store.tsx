import { create } from 'zustand';

interface loginState {
  isLogin: boolean;
  setIsLogin: (by: boolean) => void;
  setIsLogout: (by: boolean) => void;
}

export const useLoginStore = create<loginState>((set) => ({
  isLogin: false,
  setIsLogin: (by) => {
    set(() => ({ isLogin: by }));
  },
  setIsLogout: (by) => {
    set(() => ({ isLogin: by }));
  },
}));

// Backtest
// standard interface and functions
const addSelectedIndicator = (
  selectedIndicators: number[],
  id: number
): number[] => [...selectedIndicators, id];

const removeSelectedIndicator = (
  selectedIndicators: number[],
  id: number
): number[] =>
  selectedIndicators.filter((selectedIndicator) => selectedIndicator !== id);

const resetSelectedIndicator = (selectedIndicators: number[]): number[] =>
  selectedIndicators.filter((selectedIndicator) => selectedIndicator === 0);

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

const resetIndicator = (indicators: Indicator[]): Indicator[] =>
  indicators.filter((indicator) => indicator.id === 0);

// zustand implementation
type backtestFactorStore = {
  selectedIndicators: number[];
  addSelectedIndicator: (id: number) => void;
  removeSelectedIndicator: (id: number) => void;
  resetSelectedIndicator: () => void;
  loadSelectedIndicator: (selectedIndicators: number[]) => void;
  indicators: Indicator[];
  addIndicator: (id: number, title: string) => void;
  removeIndicator: (id: number) => void;
  resetIndicator: () => void;
  loadIndicator: (indicators: Indicator[]) => void;
};

// set: mutate the state
export const useBacktestFactorStore = create<backtestFactorStore>((set) => ({
  selectedIndicators: [],
  addSelectedIndicator(id: number) {
    set((state) => ({
      ...state,
      selectedIndicators: addSelectedIndicator(state.selectedIndicators, id),
    }));
  },
  removeSelectedIndicator(id: number) {
    set((state) => ({
      ...state,
      selectedIndicators: removeSelectedIndicator(state.selectedIndicators, id),
    }));
  },
  resetSelectedIndicator() {
    set((state) => ({
      ...state,
      selectedIndicators: resetSelectedIndicator(state.selectedIndicators),
    }));
  },
  loadSelectedIndicator(selectedIndicators: number[]) {
    set((state) => ({
      ...state,
      selectedIndicators,
    }));
  },
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
    set((state) => ({
      ...state,
      indicators: resetIndicator(state.indicators),
    }));
  },
  loadIndicator(indicators: Indicator[]) {
    set((state) => ({
      ...state,
      indicators,
    }));
  },
}));
