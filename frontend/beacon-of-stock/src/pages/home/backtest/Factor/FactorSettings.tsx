import React from 'react';
import FactorBasic from './FactorBasic';

const FactorSettings = () => {
  const data = {
    factors: [
      {
        description:
          '주식가격과 회사의 매출을 통해 얼마나 저평가 되었는지 확인한 지표',
        id: 1,
        title: '가치 (가격/매출)',
        indicators: [
          {
            id: 1,
            title: 'PER',
            count: 3,
            description: 'PER = 현재 가격/순이익',
          },
          {
            id: 2,
            title: 'PBR',
            count: 0,
            description: 'PBR = 현재 가격/장부가치',
          },
          {
            id: 3,
            title: 'PSR',
            count: 3,
            description: 'PSR = 현재 가격/매출액',
          },
          {
            id: 4,
            title: 'POR',
            count: 0,
            description: 'POR = 현재 가격/영업이익',
          },
        ],
      },
      {
        description:
          '회사의 매출과 회사의 자산을통해 얼마나 효율적으로 수익을 내는지 확인하는 지표',
        id: 2,
        title: '퀄리티 (매출/자산)',
        indicators: [
          {
            id: 6,
            title: 'ROE',
            count: 3,
            description: 'ROE = 순이익/자본',
          },
          {
            id: 7,
            title: 'ROA',
            count: 0,
            description: 'ROA = 순이익/자산',
          },
        ],
      },
      {
        description: '회사의 매출이 얼마나 빠르게 성장하는지 확인하는 지표',
        id: 3,
        title: '성장성 (이익 성장률)',
        indicators: [
          {
            id: 8,
            title: '3MothTake',
            count: 3,
            description: '3개월간 매출 성장률',
          },
          {
            id: 9,
            title: '12MothTake',
            count: 0,
            description: '12개월간 매출 성장률',
          },
          {
            id: 10,
            title: '3MothOperatingProfit',
            count: 0,
            description: '3개월간 영업이익 성장률',
          },
          {
            id: 11,
            title: '12MothOperatingProfit',
            count: 0,
            description: '12개월간 영업이익 성장률',
          },
          {
            id: 12,
            title: '3MothNetProfit',
            count: 0,
            description: '3개월간 순이익 성장률',
          },
          {
            id: 13,
            title: '12MothNetProfit',
            count: 0,
            description: '12개월간 순이익 성장률',
          },
        ],
      },
    ],
  };

  return (
    <React.Fragment>
      <p className='text-xl font-KJCbold'>팩터 설정</p>
      <ul>
        {data.factors.map((factor) => {
          return (
            <li key={factor.id}>
              <FactorBasic
                id={factor.id}
                title={factor.title}
                description={factor.description}
                indicators={factor.indicators}
              />
            </li>
          );
        })}
      </ul>
    </React.Fragment>
  );
};

export default FactorSettings;
