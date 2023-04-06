import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import ResultCumulativeReturnDtos from './ResultCumulativeReturnDtos';
import ResultCumulativeReturnTable from './ResultCumulativeReturnTable';
import ResultChangeRate from './ResultChangeRate';
import ResultChangeRateTable from './ResultChangeRateTable';
import axios_api from '../../../../assets/config/Axios';
import { getCookie } from '../../../../assets/config/Cookie';
import question from '../../../../assets/img/question.png';
import Swal from 'sweetalert2';
import ResultCompareTable from './ResultCompareTable';

type resultValues = {
  cumulativeReturnDtos: {
    year: number;
    month: number;
    strategyValue: number;
    marketValue: number;
  }[];
  cumulativeReturnDataDto: {
    cumulativeReturn: string;
    cumulativeReturnDesc: string;
    strategyCumulativeReturn: number;
    marketCumulativeReturn: number;
    cagr: string;
    cagrDesc: string;
    strategyCagr: number;
    marketCagr: number;
    sharpe: string;
    sharpeDesc: string;
    strategySharpe: number;
    marketSharpe: number;
    sortino: string;
    sortinoDesc: string;
    strategySortino: number;
    marketSortino: number;
    mdd: string;
    mddDesc: string;
    strategyMDD: number;
    marketMDD: number;
  };
  changeRate: {
    year: number;
    month: number;
    strategyValue: number;
    marketValue: number;
  }[];
  revenueDataDto: {
    winrate: string;
    strategyRevenue: number;
    marketRevenue: number;
    totalMonth: number;
  };
  indicators: number[];
};

const BacktestResult = () => {
  const location = useLocation();
  const data: resultValues = location.state.data;

  // const data = {
  //   cumulativeReturnDtos: [
  //     {
  //       year: 2001,
  //       month: 1,
  //       strategyValue: 98.13623542666295,
  //       marketValue: 103.692075522,
  //     },
  //     {
  //       year: 2001,
  //       month: 4,
  //       strategyValue: 144.9243816431105,
  //       marketValue: 117.95117813965193,
  //     },
  //     {
  //       year: 2001,
  //       month: 7,
  //       strategyValue: 157.47310311602834,
  //       marketValue: 95.07870845703121,
  //     },
  //     {
  //       year: 2001,
  //       month: 10,
  //       strategyValue: 224.03228407744936,
  //       marketValue: 137.50232516007426,
  //     },
  //     {
  //       year: 2002,
  //       month: 1,
  //       strategyValue: 221.98792143543616,
  //       marketValue: 177.5179489006366,
  //     },
  //     {
  //       year: 2002,
  //       month: 4,
  //       strategyValue: 252.66749930874647,
  //       marketValue: 147.2326707072318,
  //     },
  //     {
  //       year: 2002,
  //       month: 7,
  //       strategyValue: 238.47016161471817,
  //       marketValue: 128.13550866166375,
  //     },
  //     {
  //       year: 2002,
  //       month: 10,
  //       strategyValue: 215.6168807501237,
  //       marketValue: 124.38894606696182,
  //     },
  //     {
  //       year: 2003,
  //       month: 1,
  //       strategyValue: 229.00802791909888,
  //       marketValue: 106.1805157929725,
  //     },
  //     {
  //       year: 2003,
  //       month: 4,
  //       strategyValue: 229.00802791909888,
  //       marketValue: 132.77354017938637,
  //     },
  //     {
  //       year: 2003,
  //       month: 7,
  //       strategyValue: 278.8276220607004,
  //       marketValue: 138.2415710607606,
  //     },
  //     {
  //       year: 2003,
  //       month: 10,
  //       strategyValue: 348.3216305797511,
  //       marketValue: 160.6680521562911,
  //     },
  //     {
  //       year: 2004,
  //       month: 1,
  //       strategyValue: 353.5662073105452,
  //       marketValue: 174.50540245839974,
  //     },
  //     {
  //       year: 2004,
  //       month: 4,
  //       strategyValue: 304.0113964760693,
  //       marketValue: 155.71730554300947,
  //     },
  //     {
  //       year: 2004,
  //       month: 7,
  //       strategyValue: 391.40851341770644,
  //       marketValue: 165.48542343784052,
  //     },
  //     {
  //       year: 2004,
  //       month: 10,
  //       strategyValue: 438.9724179072834,
  //       marketValue: 177.53765972876772,
  //     },
  //     {
  //       year: 2005,
  //       month: 1,
  //       strategyValue: 479.6360956665325,
  //       marketValue: 191.3571990099297,
  //     },
  //     {
  //       year: 2005,
  //       month: 4,
  //       strategyValue: 775.4082581356673,
  //       marketValue: 199.76645696298883,
  //     },
  //     {
  //       year: 2005,
  //       month: 7,
  //       strategyValue: 1007.3430000281267,
  //       marketValue: 241.93591573467813,
  //     },
  //     {
  //       year: 2005,
  //       month: 10,
  //       strategyValue: 1393.7485235452048,
  //       marketValue: 273.30409199526173,
  //     },
  //   ],
  //   cumulativeReturnDataDto: {
  //     cumulativeReturnDesc: '전략의 누적 수익률',
  //     strategyCumulativeReturn: 1393.7485235452048,
  //     marketCumulativeReturn: 273.30409199526173,
  //     cagrDesc: '리벨런싱 구간 평균 수익률',
  //     strategyCagr: 1.1574612974052596,
  //     marketCagr: 1.0636106205019498,
  //     sharpe: '변동폭을 확인하는 지표',
  //     strategySharpe: 0.586547761176209,
  //     marketSharpe: 0.14836404856056482,
  //     sortino: '하락에 대한 변동폭을 확인하는 지표',
  //     strategySortino: 1.0381150314037055,
  //     marketSortino: 0.21754430539790107,
  //     mdd: '최대 하락폭을 확인하는 지표',
  //     strategyMDD: -14.663784879332207,
  //     marketMDD: -40.18603952414656,
  //   },
  //   changeRate: [
  //     {
  //       year: 2001,
  //       month: 1,
  //       strategyValue: 0.9813623542666295,
  //       marketValue: 1.03692075522,
  //     },
  //     {
  //       year: 2001,
  //       month: 4,
  //       strategyValue: 1.476767281861064,
  //       marketValue: 1.13751390881,
  //     },
  //     {
  //       year: 2001,
  //       month: 7,
  //       strategyValue: 1.0865880628962779,
  //       marketValue: 0.80608528,
  //     },
  //     {
  //       year: 2001,
  //       month: 10,
  //       strategyValue: 1.4226701553749106,
  //       marketValue: 1.4461947095359997,
  //     },
  //     {
  //       year: 2002,
  //       month: 1,
  //       strategyValue: 0.9908746962499991,
  //       marketValue: 1.2910177969280001,
  //     },
  //     {
  //       year: 2002,
  //       month: 4,
  //       strategyValue: 1.1382038161127306,
  //       marketValue: 0.8293959659800001,
  //     },
  //     {
  //       year: 2002,
  //       month: 7,
  //       strategyValue: 0.9438101942953894,
  //       marketValue: 0.870292633056,
  //     },
  //     {
  //       year: 2002,
  //       month: 10,
  //       strategyValue: 0.9041671263614224,
  //       marketValue: 0.9707609340000002,
  //     },
  //     {
  //       year: 2003,
  //       month: 1,
  //       strategyValue: 1.0621062095063607,
  //       marketValue: 0.85361697442,
  //     },
  //     {
  //       year: 2003,
  //       month: 4,
  //       strategyValue: 1.0,
  //       marketValue: 1.2504510755840001,
  //     },
  //     {
  //       year: 2003,
  //       month: 7,
  //       strategyValue: 1.2175451864910218,
  //       marketValue: 1.041183136896,
  //     },
  //     {
  //       year: 2003,
  //       month: 10,
  //       strategyValue: 1.2492364565800513,
  //       marketValue: 1.162226752224,
  //     },
  //     {
  //       year: 2004,
  //       month: 1,
  //       strategyValue: 1.0150567069925143,
  //       marketValue: 1.086123844264,
  //     },
  //     {
  //       year: 2004,
  //       month: 4,
  //       strategyValue: 0.8598429097299144,
  //       marketValue: 0.8923351561,
  //     },
  //     {
  //       year: 2004,
  //       month: 7,
  //       strategyValue: 1.287479739097599,
  //       marketValue: 1.062729815808,
  //     },
  //     {
  //       year: 2004,
  //       month: 10,
  //       strategyValue: 1.121519851661523,
  //       marketValue: 1.0728295945380002,
  //     },
  //     {
  //       year: 2005,
  //       month: 1,
  //       strategyValue: 1.0926337876833023,
  //       marketValue: 1.077840044204,
  //     },
  //     {
  //       year: 2005,
  //       month: 4,
  //       strategyValue: 1.6166595157066133,
  //       marketValue: 1.0439453440820001,
  //     },
  //     {
  //       year: 2005,
  //       month: 7,
  //       strategyValue: 1.2991130665155741,
  //       marketValue: 1.2110937912839999,
  //     },
  //     {
  //       year: 2005,
  //       month: 10,
  //       strategyValue: 1.3835888307222952,
  //       marketValue: 1.1296548971049998,
  //     },
  //   ],
  //   revenueDataDto: {
  //     winrate: '수익을 만든 구간의 횟수',
  //     strategyRevenue: 15,
  //     marketRevenue: 14,
  //     totalMonth: 20,
  //   },
  //   indicators: [1, 2],
  // };
  // console.log(data);

  // 전략 저장
  const token = getCookie('accessToken');
  // title창 show 여부
  const [saveStrategy, setSaveStrategy] = useState(false);
  const showStrategyTitleHandler = () => {
    setSaveStrategy(true);
  };
  const cancelStrategyHandler = () => {
    setSaveStrategy(false);
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
      Swal.fire({
        // title: '<div style="font-size:24px;font-weight:bold;">에러!<div>',
        html: '<div>전략명을</div><div>입력해주세요!</div>',
        width: 300,
        icon: 'warning',
        confirmButtonText: '<div>확인</div>',
        confirmButtonColor: '#f8bb86',
      });
    } else if (strategyTitle.trim().length > 100) {
      Swal.fire({
        // title: '<div style="font-size:24px;font-weight:bold;">에러!<div>',
        html: '<div>100자 이하의 전략명을</div><div>입력해주세요!</div>',
        width: 300,
        icon: 'warning',
        confirmButtonText: '<div>확인</div>',
        confirmButtonColor: '#f8bb86',
      });
    } else if (strategyTitle.trim().length !== 0) {
      axios_api
        .post(
          'strategies',
          {
            indicators: data.indicators,
            cumulativeReturnDtos: data.cumulativeReturnDtos,
            strategyCagr: data.cumulativeReturnDataDto.strategyCagr,
            strategyCumulativeReturn:
              data.cumulativeReturnDataDto.strategyCumulativeReturn,
            strategyMDD: data.cumulativeReturnDataDto.strategyMDD,
            strategyRevenue: data.revenueDataDto.strategyRevenue,
            strategySharpe: data.cumulativeReturnDataDto.strategySharpe,
            strategySortino: data.cumulativeReturnDataDto.strategySortino,
            title: strategyTitle,
            totalMonth: data.revenueDataDto.totalMonth,
          },
          { headers: { authentication: token } }
        )
        .then((response) => {
          // console.log(response);
          Swal.fire({
            // title: '<div style="font-size:24px;font-weight:bold;">에러!<div>',
            html: '<div>전략을</div><div>저장했습니다!</div>',
            width: 300,
            icon: 'success',
            confirmButtonText: '<div>확인</div>',
            confirmButtonColor: '#a5dc86',
          });
        })
        .then(() => {
          setStrategyTitle('');
          setSaveStrategy(false);
        });
    }
  };

  return (
    <div className='w-full h-full px-[3%]'>
      <div className='flex justify-end my-[1%] mr-[3%]'>
        {saveStrategy && (
          <input
            type='text'
            className='px-[1%] border border-[#A47ECF] rounded-xl bg-[#FEFEFE] focus:outline-none h-10 w-96'
            value={strategyTitle}
            onChange={strategyTitleHandler}
            placeholder='100자 이하의 전략명을 입력해주세요.'
          />
        )}
        {saveStrategy && (
          <input
            type='button'
            value='전략 저장'
            className='px-[1%] font-KJCbold text-[#A47ECF] border border-[#A47ECF] rounded-xl bg-[#FEFEFE] h-10 hover:bg-[#A47ECF] hover:text-[#FEFEFE] cursor-pointer'
            onClick={saveStrategyHandler}
          />
        )}
        {saveStrategy && (
          <input
            type='button'
            value='취소'
            className='px-[1%] font-KJCbold text-[#A47ECF] border border-[#A47ECF] rounded-xl bg-[#FEFEFE] h-10 hover:bg-[#A47ECF] hover:text-[#FEFEFE] cursor-pointer'
            onClick={cancelStrategyHandler}
          />
        )}
        {!saveStrategy && (
          <input
            type='button'
            value='현재 전략 저장'
            className='px-[1%] font-KJCbold text-[#A47ECF] border border-[#A47ECF] rounded-xl bg-[#FEFEFE] h-10 hover:bg-[#A47ECF] hover:text-[#FEFEFE] cursor-pointer'
            onClick={showStrategyTitleHandler}
          />
        )}
      </div>
      <div className='flex flex-col ml-[3%] w-[1320px]'>
        <p className='text-xl font-KJCbold mb-[1%]'>시계열 수익률</p>
        <div className='flex items-end rounded-md pt-[1%] bg-[#FAF6FF] mx-[2%]'>
          <ResultCumulativeReturnDtos
            cumulativeReturnDtos={data.cumulativeReturnDtos}
          />
          <ResultCumulativeReturnTable
            cumulativeReturnDataDto={data.cumulativeReturnDataDto}
          />
        </div>
      </div>
      <div className='flex flex-col ml-[3%] w-[1320px] mt-[3%]'>
        <p className='text-xl font-KJCbold mb-[1%]'>전월대비 증감률</p>
        <div className='flex items-end rounded-md pt-[1%] bg-[#FAF6FF] mx-[2%]'>
          <ResultChangeRate changeRate={data.changeRate} />
          <ResultChangeRateTable revenueDataDto={data.revenueDataDto} />
        </div>
      </div>
      <div className='mb-[5%] ml-[3%] mt-[3%]'>
        <p className='text-xl font-KJCbold mb-[1%]'>구간별 수익률 비교</p>
        <ResultCompareTable changeRate={data.changeRate} />
      </div>
    </div>
  );
};

export default BacktestResult;
