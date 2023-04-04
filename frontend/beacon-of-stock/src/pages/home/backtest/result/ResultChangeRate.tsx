import React, { useState, useEffect } from 'react';
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ReferenceLine,
} from 'recharts';

interface Props {
  changeRate: {
    strategyValue: number;
    marketValue: number;
    year: number;
    month: number;
  }[];
}

const ResultChangeRate = (props: Props) => {
  // console.log(props.changeRate);
  // 전월대비 증감률 가공
  const [actualChangeRate, setActualChangeRate] = useState([
    {
      name: '',
      내전략: 0,
      시장전략: 0,
    },
  ]);

  useEffect(() => {
    const dataTemp = props.changeRate.map((data) => {
      return {
        name: `${data.year}-${data.month}`,
        내전략: Number(((data.strategyValue - 1) * 100).toFixed(2)),
        시장전략: Number(((data.marketValue - 1) * 100).toFixed(2)),
      };
    });
    setActualChangeRate([...actualChangeRate, ...dataTemp]);
  }, []);
  // const data = [
  //   {
  //     strategyValue: 1.3263406866729999,
  //     marketValue: 1.3263406866729999,
  //     year: 2011,
  //     month: 1,
  //   },
  //   {
  //     strategyValue: 0.8718405630280321,
  //     marketValue: 0.8718405630280321,
  //     year: 2011,
  //     month: 7,
  //   },
  //   {
  //     strategyValue: -1.63406866729999,
  //     marketValue: -1.63406866729999,
  //     year: 2012,
  //     month: 1,
  //   },
  //   {
  //     strategyValue: -0.3718405630280321,
  //     marketValue: -0.3718405630280321,
  //     year: 2012,
  //     month: 7,
  //   },
  //   {
  //     strategyValue: 1.3263406866729999,
  //     marketValue: 1.3263406866729999,
  //     year: 2013,
  //     month: 1,
  //   },
  //   {
  //     strategyValue: 0.8718405630280321,
  //     marketValue: 0.8718405630280321,
  //     year: 2013,
  //     month: 7,
  //   },
  //   {
  //     strategyValue: 1.3263406866729999,
  //     marketValue: 1.3263406866729999,
  //     year: 2014,
  //     month: 1,
  //   },
  //   {
  //     strategyValue: 0.8718405630280321,
  //     marketValue: 0.8718405630280321,
  //     year: 2014,
  //     month: 7,
  //   },
  //   {
  //     strategyValue: 1.3263406866729999,
  //     marketValue: 1.3263406866729999,
  //     year: 2015,
  //     month: 1,
  //   },
  //   {
  //     strategyValue: 0.8718405630280321,
  //     marketValue: 0.8718405630280321,
  //     year: 2015,
  //     month: 7,
  //   },
  //   {
  //     strategyValue: 1.3263406866729999,
  //     marketValue: 1.3263406866729999,
  //     year: 2016,
  //     month: 1,
  //   },
  //   {
  //     strategyValue: 0.8718405630280321,
  //     marketValue: 0.8718405630280321,
  //     year: 2016,
  //     month: 7,
  //   },
  //   {
  //     strategyValue: 1.3263406866729999,
  //     marketValue: 1.3263406866729999,
  //     year: 2017,
  //     month: 1,
  //   },
  //   {
  //     strategyValue: 0.8718405630280321,
  //     marketValue: 0.8718405630280321,
  //     year: 2017,
  //     month: 7,
  //   },
  //   {
  //     strategyValue: 1.3263406866729999,
  //     marketValue: 1.3263406866729999,
  //     year: 2018,
  //     month: 1,
  //   },
  //   {
  //     strategyValue: 0.8718405630280321,
  //     marketValue: 0.8718405630280321,
  //     year: 2018,
  //     month: 7,
  //   },
  //   {
  //     strategyValue: 1.3263406866729999,
  //     marketValue: 1.3263406866729999,
  //     year: 2019,
  //     month: 1,
  //   },
  //   {
  //     strategyValue: 0.8718405630280321,
  //     marketValue: 0.8718405630280321,
  //     year: 2019,
  //     month: 7,
  //   },
  //   {
  //     strategyValue: 1.3263406866729999,
  //     marketValue: 1.3263406866729999,
  //     year: 2020,
  //     month: 1,
  //   },
  //   {
  //     strategyValue: 0.8718405630280321,
  //     marketValue: 0.8718405630280321,
  //     year: 2020,
  //     month: 7,
  //   },
  // ];
  return (
    <div className='flex flex-col'>
      <p className='text-xl font-KJCbold'>전월대비 증감률</p>
      <BarChart
        width={800}
        height={300}
        // 임의로 집어넣은 0번째는 제거
        data={actualChangeRate.slice(1)}
        margin={{
          // top: 5,
          top: 40,
          right: 30,
          left: 20,
          bottom: 5,
        }}
        barCategoryGap={0}
      >
        <CartesianGrid strokeDasharray='3 3' />
        <XAxis dataKey='name' />
        <YAxis label={{ value: '(%)', offset: 20, position: 'top' }} />
        <Tooltip cursor={{ fill: '#FFF2F8' }} />
        <Legend />
        <ReferenceLine y={0} stroke='#000' />
        <Bar dataKey='내전략' fill='#ec6c70' />
      </BarChart>
    </div>
  );
};

export default ResultChangeRate;
