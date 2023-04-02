import React, { useState, useEffect } from 'react';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
} from 'recharts';

interface Props {
  cumulativeReturnDtos: {
    year: number;
    month: number;
    strategyValue: number;
    marketValue: number;
  }[];
}

const ResultCumulativeReturnDtos = (props: Props) => {
  // 시계열 수익률 가공
  const [actualCumulativeReturnDtos, setActualCumulativeReturnDtos] = useState([
    { name: '', strategyValue: 100, marketValue: 100 },
  ]);

  useEffect(() => {
    const dataTemp = props.cumulativeReturnDtos.map((data) => {
      return {
        name: `${data.year}-${data.month}`,
        strategyValue: data.strategyValue,
        marketValue: data.marketValue,
      };
    });
    setActualCumulativeReturnDtos([...actualCumulativeReturnDtos, ...dataTemp]);
  }, []);

  console.log();

  return (
    <div className='flex flex-col'>
      <p className='text-xl font-KJCbold'>시계열 수익률</p>
      <LineChart
        width={800}
        height={300}
        data={actualCumulativeReturnDtos}
        margin={{
          top: 40,
          right: 30,
          left: 20,
          bottom: 5,
        }}
      >
        <CartesianGrid strokeDasharray='3 3' />
        <XAxis dataKey='name' />
        <YAxis label={{ value: '(%)', offset: 20, position: 'top' }} />
        <Tooltip />
        <Legend />
        <Line
          type='monotone'
          dataKey='strategyValue'
          stroke='#802A57'
          dot={false}
          activeDot={{ r: 5 }}
        />
        <Line
          type='monotone'
          dataKey='marketValue'
          stroke='#0083B5'
          dot={false}
          activeDot={{ r: 5 }}
        />
      </LineChart>
    </div>
  );
};

export default ResultCumulativeReturnDtos;
