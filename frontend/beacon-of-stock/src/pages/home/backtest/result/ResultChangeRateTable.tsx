interface Props {
  revenueDataDto: {
    winrate: string;
    strategyRevenue: number;
    marketRevenue: number;
    totalMonth: number;
  };
}

const ResultChangeRateTable = (props: Props) => {
  return (
    <div className='flex flex-col justify-center pt-[1%] pl-[5%] mb-[3%]'>
      {/* <p className='text-xl font-KJCbold'>ResultChangeRateTable</p> */}
      {/* 표를 나타내는 태그 */}
      <table className='border'>
        {/* 제목 행을 그룹화하는 태그 */}
        <thead className='border'>
          <tr>
            <th className='w-40 px-2 py-1 border'>구분</th>
            <th className='w-24 px-2 py-1 border'>시장</th>
            <th className='w-24 px-2 py-1 border'>내 전략</th>
          </tr>
        </thead>
        {/* 본문 행을 그룹화하는 태그 */}
        <tbody className='border'>
          <tr>
            <td className='flex items-center w-40 px-2 py-1'>
              <div>수익이 있는 달</div>
            </td>
            <td className='px-2 py-1 text-right border'>
              {props.revenueDataDto.marketRevenue}
            </td>
            <td className='px-2 py-1 text-right border'>
              {props.revenueDataDto.strategyRevenue}
            </td>
          </tr>
        </tbody>
        <tbody className='border'>
          <tr>
            <td className='flex items-center w-40 px-2 py-1'>
              <div>전체 달</div>
            </td>
            <td className='px-2 py-1 text-right border'>
              {props.revenueDataDto.totalMonth}
            </td>
            <td className='px-2 py-1 text-right border'>
              {props.revenueDataDto.totalMonth}
            </td>
          </tr>
        </tbody>
        <tbody className='border'>
          <tr>
            <td className='flex items-center w-40 px-2 py-1'>
              <div>승률</div>
            </td>
            <td className='px-2 py-1 text-right border'>
              {(
                (props.revenueDataDto.marketRevenue * 100) /
                props.revenueDataDto.totalMonth
              ).toFixed(2)}
              %
            </td>
            <td className='px-2 py-1 text-right border'>
              {(
                (props.revenueDataDto.strategyRevenue * 100) /
                props.revenueDataDto.totalMonth
              ).toFixed(2)}
              %
            </td>
          </tr>
        </tbody>
        {/* 바닥 행을 그룹화하는 태그 */}
        {/* <tfoot>
          <tr></tr>
        </tfoot> */}
      </table>
    </div>
  );
};

export default ResultChangeRateTable;
