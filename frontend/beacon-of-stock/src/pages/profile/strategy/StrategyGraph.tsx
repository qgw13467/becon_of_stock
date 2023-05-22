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

const StrategyGraph = (props: Props) => {
  // 시계열 수익률 가공
  const [actualCumulativeReturnDtos, setActualCumulativeReturnDtos] = useState([
    { name: '', 내전략: 100, 시장전략: 100 },
  ]);

  useEffect(() => {
    const dataTemp = props.cumulativeReturnDtos.map((data) => {
      return {
        name: `${data.year}-${data.month}`,
        내전략: Number(data.strategyValue.toFixed(2)),
        시장전략: Number(data.marketValue.toFixed(2)),
      };
    });
    setActualCumulativeReturnDtos([...actualCumulativeReturnDtos, ...dataTemp]);
  }, []);

  console.log();

  return (
    <div className='flex flex-col'>
      <LineChart
        width={240}
        height={130}
        data={actualCumulativeReturnDtos}
        margin={{
          top: 30,
          // right: 20,
          // left: 20,
          // bottom: 5,
        }}
      >
        <CartesianGrid strokeDasharray='3 3' />
        <XAxis dataKey='name' tick={false} axisLine={false} />
        {/* <YAxis label={{ value: '(%)', offset: 20, position: 'top' }} /> */}
        {/* <Tooltip /> */}
        {/* <Legend /> */}
        <Line
          type='monotone'
          dataKey='내전략'
          stroke='#f72300'
          dot={false}
          // activeDot={{ r: 5 }}
        />
        <Line
          type='monotone'
          dataKey='시장전략'
          stroke='#0083B5'
          dot={false}
          // activeDot={{ r: 5 }}
        />
      </LineChart>
    </div>
  );
};

export default StrategyGraph;
