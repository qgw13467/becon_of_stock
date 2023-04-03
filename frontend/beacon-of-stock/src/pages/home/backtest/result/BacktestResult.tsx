import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import ResultCumulativeReturnDtos from './ResultCumulativeReturnDtos';
import ResultCumulativeReturnTable from './ResultCumulativeReturnTable';
import ResultChangeRate from './ResultChangeRate';
import ResultChangeRateTable from './ResultChangeRateTable';
import axios_api from '../../../../assets/config/Axios';
import { getCookie } from '../../../../assets/config/Cookie';
import question from '../../../../assets/img/question.png';

type resultValues = {
  indicators: number[];
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
    strategyValue: number;
    marketValue: number;
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
  //   indicators: [1, 2, 3]
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
  //       strategyValue: 1.3263406866729999,
  //       marketValue: 1.3263406866729999,
  //       year: 2021,
  //       month: 1,
  //     },
  //     {
  //       strategyValue: 0.8718405630280321,
  //       marketValue: 0.8718405630280321,
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

  // 전략 저장
  const token = getCookie('accessToken');
  // title창 show 여부
  const [saveStrategy, setSaveStrategy] = useState(false);
  const showStrategyTitleHandler = () => {
    setSaveStrategy(true);
  };
  // title 값
  const [strategyTitle, setStrategyTitle] = useState('');
  const strategyTitleHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    // console.log(event.target.value);
    setStrategyTitle(event.target.value);
  };
  // 전략 저장 axios 요청
  const saveStrategyHandler = () => {
    // title명이 빈 값이 아닌 경우 추가하기
    if (strategyTitle.trim().length === 0) {
      console.log('title입력해달라는 알림');
    } else if (strategyTitle.trim().length > 100) {
      console.log('title은 100자 이하여야 한다는 알람');
    } else if (strategyTitle.trim().length !== 0) {
      axios_api
        .post(
          'strategies',
          {
            indicators: data.indicators,
            cumulativeReturnDtos: data.cumulativeReturnDtos,
            strategyCagr: data.strategyCagr,
            strategyCumulativeReturn: data.strategyCumulativeReturn,
            strategyMDD: data.strategyMDD,
            strategyRevenue: data.strategyRevenue,
            strategySharpe: data.strategySharpe,
            strategySortino: data.strategySortino,
            title: strategyTitle,
            totalMonth: data.totalMonth,
          },
          { headers: { authentication: token } }
        )
        .then((response) => {
          console.log(response);
        });
    }
  };

  return (
    <div className='w-full h-full'>
      <div className='flex justify-end'>
        {saveStrategy && (
          <input
            type='text'
            className='border w-96'
            value={strategyTitle}
            onChange={strategyTitleHandler}
            placeholder='100자 이하의 전략명을 입력해주세요.'
          />
        )}
        {saveStrategy && (
          <input
            type='button'
            value='전략 저장'
            className='border'
            onClick={saveStrategyHandler}
          />
        )}
        {!saveStrategy && (
          <input
            type='button'
            value='전략 저장'
            className='border'
            onClick={showStrategyTitleHandler}
          />
        )}
      </div>
      <div className='flex justify-around'>
        <ResultCumulativeReturnDtos
          cumulativeReturnDtos={data.cumulativeReturnDtos}
        />
        {/* 시계열 수익률 표 */}
        <div className='flex flex-col justify-center'>
          <p className='text-xl font-KJCbold'>ResultCumulativeReturnTable</p>
          {/* 표를 나타내는 태그 */}
          <table className='border'>
            {/* 제목 행을 그룹화하는 태그 */}
            <thead className='border'>
              <tr className='border'>
                <th></th>
                <th>시장</th>
                <th>내 전략</th>
              </tr>
            </thead>
            {/* 본문 행을 그룹화하는 태그 */}
            <tbody className='border'>
              <tr>
                <td className='flex items-center'>
                  <p>총수익률</p>
                  <img src={question} alt='question' className='w-4 h-4 ml-1' />
                </td>
                <td>$100</td>
                <td>$100</td>
              </tr>
            </tbody>
            <tbody className='border'>
              <tr>
                <td className='flex items-center'>
                  <p>연평균수익률</p>
                  <img src={question} alt='question' className='w-4 h-4 ml-1' />
                </td>
                <td>$100</td>
                <td>$100</td>
              </tr>
            </tbody>
            <tbody className='border'>
              <tr>
                <td className='flex items-center'>
                  <p>sharpe</p>
                  <img src={question} alt='question' className='w-4 h-4 ml-1' />
                </td>
                <td>$100</td>
                <td>$100</td>
              </tr>
            </tbody>
            <tbody className='border'>
              <tr>
                <td className='flex items-center'>
                  <p>sortino</p>
                  <img src={question} alt='question' className='w-4 h-4 ml-1' />
                </td>
                <td>$100</td>
                <td>$100</td>
              </tr>
            </tbody>
            {/* 바닥 행을 그룹화하는 태그 */}
            <tfoot className='border'>
              <tr>
                <td className='flex items-center'>
                  <p>MDD</p>
                  <img src={question} alt='question' className='w-4 h-4 ml-1' />
                </td>
                <td>$180</td>
                <td>$180</td>
              </tr>
            </tfoot>
          </table>
        </div>
      </div>
      <div className='flex justify-around'>
        <ResultChangeRate changeRate={data.changeRate} />
        <ResultChangeRateTable />
      </div>
    </div>
  );
};

export default BacktestResult;
