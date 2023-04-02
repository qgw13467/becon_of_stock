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
  // const data = [
  //   {
  //     year: 2011,
  //     month: 1,
  //     strategyValue: 132.43511756429905,
  //     marketValue: 114.55791084024654,
  //   },
  //   {
  //     year: 2011,
  //     month: 7,
  //     strategyValue: 115.2891140007492,
  //     marketValue: 103.30837577881405,
  //   },
  //   {
  //     year: 2012,
  //     month: 1,
  //     strategyValue: 132.43511756429905,
  //     marketValue: 114.55791084024654,
  //   },
  //   {
  //     year: 2012,
  //     month: 7,
  //     strategyValue: 115.2891140007492,
  //     marketValue: 103.30837577881405,
  //   },
  //   {
  //     year: 2013,
  //     month: 1,
  //     strategyValue: 132.43511756429905,
  //     marketValue: 114.55791084024654,
  //   },
  //   {
  //     year: 2013,
  //     month: 7,
  //     strategyValue: 800.2891140007492,
  //     marketValue: 400.30837577881405,
  //   },
  //   {
  //     year: 2014,
  //     month: 1,
  //     strategyValue: 132.43511756429905,
  //     marketValue: 114.55791084024654,
  //   },
  //   {
  //     year: 2014,
  //     month: 7,
  //     strategyValue: 115.2891140007492,
  //     marketValue: 103.30837577881405,
  //   },
  //   {
  //     year: 2015,
  //     month: 1,
  //     strategyValue: 132.43511756429905,
  //     marketValue: 114.55791084024654,
  //   },
  //   {
  //     year: 2015,
  //     month: 7,
  //     strategyValue: 115.2891140007492,
  //     marketValue: 103.30837577881405,
  //   },
  //   {
  //     year: 2016,
  //     month: 1,
  //     strategyValue: 132.43511756429905,
  //     marketValue: 114.55791084024654,
  //   },
  //   {
  //     year: 2016,
  //     month: 7,
  //     strategyValue: 115.2891140007492,
  //     marketValue: 103.30837577881405,
  //   },
  //   {
  //     year: 2017,
  //     month: 1,
  //     strategyValue: 132.43511756429905,
  //     marketValue: 114.55791084024654,
  //   },
  //   {
  //     year: 2017,
  //     month: 7,
  //     strategyValue: 27.2891140007492,
  //     marketValue: 88.30837577881405,
  //   },
  // ];
  // console.log(props.cumulativeReturnDtos);
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
    <React.Fragment>
      <p className='text-xl font-KJCbold'>시계열 수익률</p>
      <LineChart
        width={800}
        height={300}
        data={actualCumulativeReturnDtos}
        margin={{
          top: 5,
          right: 30,
          left: 20,
          bottom: 5,
        }}
      >
        <CartesianGrid strokeDasharray='3 3' />
        <XAxis dataKey='name' />
        <YAxis />
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
    </React.Fragment>
  );
};

export default ResultCumulativeReturnDtos;
