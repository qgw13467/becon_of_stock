import React from 'react';
import { useLocation } from 'react-router-dom';
import ResultCumulativeReturnDtos from './ResultCumulativeReturnDtos';
import ResultChangeRate from './ResultChangeRate';

type resultValues = {
  cumulativeReturnDtos: {
    year: number;
    month: number;
    strategyValue: number;
    marketValue: number;
  }[];
  cumulativeReturnDesc: string;
  strategyCumulativeReturn: number;
  marketCumulativeReturn: number;
  cagrDesc: string;
  strategyCagr: number;
  marketCagr: number;
  sharpe: string;
  strategySharpe: number;
  margetSharpe: number;
  sortino: string;
  strategySortino: number;
  marketSortino: number;
  mdd: string;
  strategyMDD: number;
  marketMDD: number;
  changeRateDesc: string;
  changeRate: {
    changeRate: number;
    year: number;
    month: number;
  }[];
  winrate: string;
  strategyRevenue: number;
  marketRevenue: number;
  totalMonth: number;
};

const BacktestResult = () => {
  const location = useLocation();
  const data: resultValues = location.state.data;

  // const data: resultValues = {
  //   cumulativeReturnDtos: [
  //     {
  //       year: 2011,
  //       month: 1,
  //       strategyValue: 132.43511756429905,
  //       marketValue: 114.55791084024654,
  //     },
  //     {
  //       year: 2011,
  //       month: 7,
  //       strategyValue: 115.2891140007492,
  //       marketValue: 103.30837577881405,
  //     }
  //   ],
  //   cumulativeReturnDesc: '전략의 누적 수익률',
  //   strategyCumulativeReturn: 115.2891140007492,
  //   marketCumulativeReturn: 103.30837577881405,
  //   cagrDesc: '리벨런싱 구간 평균 수익률',
  //   strategyCagr: 1.0990906248505161,
  //   marketCagr: 1.0252276216463772,
  //   sharpe: '변동폭을 확인하는 지표',
  //   strategySharpe: 0.4140400407196149,
  //   margetSharpe: 0.16570179288102516,
  //   sortino: '하락에 대한 변동폭을 확인하는 지표',
  //   strategySortino: 0.5855410409511876,
  //   marketSortino: 0.23433772280188342,
  //   mdd: '최대 하락폭을 확인하는 지표',
  //   strategyMDD: -12.946719781650993,
  //   marketMDD: -9.819954797465014,
  //   changeRateDesc: '구간별 변화량',
  //   changeRate: [
  //     {
  //       changeRate: 1.3263406866729999,
  //       year: 2021,
  //       month: 1,
  //     },
  //     {
  //       changeRate: 0.8718405630280321,
  //       year: 2021,
  //       month: 7,
  //     },
  //   ],
  //   winrate: '수익을 만든 구간의 횟수',
  //   strategyRevenue: 1,
  //   marketRevenue: 1,
  //   totalMonth: 2,
  // };
  console.log(data);
  // console.log(data.cumulativeReturnDtos);

  return (
    <div className='w-full h-full'>
      <ResultCumulativeReturnDtos
        cumulativeReturnDtos={data.cumulativeReturnDtos}
      />
      <ResultChangeRate changeRate={data.changeRate} />
    </div>
  );
};

export default BacktestResult;
