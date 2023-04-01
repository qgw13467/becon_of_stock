import { create } from 'zustand';
import { getCookie } from '../assets/config/Cookie';

interface loginState {
  isLogin: boolean;
  setIsLogin: (by: boolean) => void;
  setIsLogout: (by: boolean) => void;
}

export const useLoginStore = create<loginState>((set) => ({
  isLogin: !!getCookie('accessToken'),
  setIsLogin: (by) => {
    set(() => ({ isLogin: by }));
  },
  setIsLogout: (by) => {
    set(() => ({ isLogin: by }));
  },
}));

interface pageState {
  page: number;
  setPage: (by: number) => void;
}
export const usePageStore = create<pageState>((set) => ({
  page: 1,
  setPage: (by) => {
    set(() => ({ page: by }));
  },
}));

interface profileState {
  profile: any;
  setProfile: (by: any) => void;
}
export const useProfileStore = create<profileState>((set) => ({
  profile: {},
  setProfile: (by) => {
    set(() => ({ profile: by }));
  },
}));

// Backtest

// industries
// standard interface and functions
const addSelectedIndustry = (
  selectedIndustries: number[],
  id: number
): number[] => [...selectedIndustries, id];

const removeSelectedIndustry = (
  selectedIndustries: number[],
  id: number
): number[] =>
  selectedIndustries.filter((selectedIndustry) => selectedIndustry !== id);

const resetSelectedIndustry = (selectedIndicators: number[]): number[] =>
  selectedIndicators.filter((selectedIndicator) => selectedIndicator === 0);

const getAllIndustry = (
  allSelectedIndustry: number[],
  id: number
): number[] => [...allSelectedIndustry, id];

const selectAllIndustry = (
  selectedIndicators: number[],
  allSelectedIndustry: number[]
): number[] => (selectedIndicators = allSelectedIndustry);

// zustand implementation
type backtestIndustryStore = {
  selectedIndustries: number[];
  addSelectedIndustry: (id: number) => void;
  removeSelectedIndustry: (id: number) => void;
  resetSelectedIndustry: () => void;
  allSelectedIndustry: number[];
  getAllIndustry: (id: number) => void;
  selectAllIndustry: () => void;
};

// set: mutate the state
export const useBacktestIndustryStore = create<backtestIndustryStore>(
  (set) => ({
    selectedIndustries: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
    addSelectedIndustry(id: number) {
      set((state) => ({
        ...state,
        selectedIndustries: addSelectedIndustry(state.selectedIndustries, id),
      }));
    },
    removeSelectedIndustry(id: number) {
      set((state) => ({
        ...state,
        selectedIndustries: removeSelectedIndustry(
          state.selectedIndustries,
          id
        ),
      }));
    },
    resetSelectedIndustry() {
      set((state) => ({
        ...state,
        selectedIndustries: resetSelectedIndustry(state.selectedIndustries),
      }));
    },
    allSelectedIndustry: [],
    getAllIndustry(id: number) {
      set((state) => ({
        ...state,
        allSelectedIndustry: getAllIndustry(state.allSelectedIndustry, id),
      }));
    },
    selectAllIndustry() {
      set((state) => ({
        selectedIndustries: selectAllIndustry(
          state.selectedIndustries,
          state.allSelectedIndustry
        ),
      }));
    },
  })
);

// indicator
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
