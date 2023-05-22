import { useState } from 'react';
import question from '../../../../assets/img/question.png';

interface Props {
  changeRate: {
    year: number;
    month: number;
    strategyValue: number;
    marketValue: number;
  }[];
}

const ResultCompareTable = (props: Props) => {
  // console.log(props);
  // 설명 표시 여부
  const [showDesc, setShowDesc] = useState(false);
  const mouseEnterHandler = () => {
    setShowDesc(true);
  };
  const mouseLeaveHandler = () => {
    setShowDesc(false);
  };
  return (
    <div className='ml-7 px-[3%] pt-[2%] pb-[1%] w-[1260px] rounded-md bg-[#FAF6FF]'>
      <table className='flex overflow-auto border-b border-r '>
        <thead className='flex'>
          <tr>
            <th className='block px-2 py-1 border-t border-l w-28'>구간</th>
            <th className='block px-2 py-1 border-t border-l w-28'>내 전략</th>
            <th className='block px-2 py-1 border-t border-l w-28'>시장</th>
            <th className='block px-2 py-1 border-t border-l w-28'>
              내 전략 / 시장
            </th>
            <th className='block px-2 py-1 border-t border-l w-28'>
              <div className='flex items-center justify-center'>
                <p>승패</p>
                <img
                  src={question}
                  alt='question'
                  className='w-4 h-4 ml-[2%]'
                  onMouseEnter={mouseEnterHandler}
                  onMouseLeave={mouseLeaveHandler}
                />
                {showDesc && (
                  <div className='w-40 text-sm absolute border rounded-lg border-[#131313] bg-[#FEFEFE] px-[1%] py-[0.5%] ml-16 mb-20'>
                    <p>내 전략의 수익률이</p>
                    <p>시장보다 큰 경우 승</p>
                  </div>
                )}
              </div>
            </th>
          </tr>
        </thead>
        {props.changeRate.map((changeRate, idx) => {
          return (
            <tbody key={idx} className='flex'>
              <tr>
                <td className='block w-20 px-2 py-1 text-center border-t border-l'>
                  {changeRate.year}-{changeRate.month}
                </td>
                <td className='block px-2 py-1 border-t border-l text-end'>
                  {((changeRate.strategyValue - 1) * 100).toFixed(2)}%
                </td>
                <td className='block px-2 py-1 border-t border-l text-end'>
                  {((changeRate.marketValue - 1) * 100).toFixed(2)}%
                </td>
                <td className='block px-2 py-1 border-t border-l text-end'>
                  {(
                    (changeRate.strategyValue - 1) /
                    (changeRate.marketValue - 1)
                  ).toFixed(2)}
                </td>
                <td
                  className={
                    changeRate.strategyValue - 1 > changeRate.marketValue - 1
                      ? 'border-t border-l block px-2 py-1 text-[#f72300] text-end'
                      : 'border-t border-l block px-2 py-1 text-[#0083B5] text-end'
                  }
                >
                  {changeRate.strategyValue - 1 > changeRate.marketValue - 1
                    ? '승'
                    : '패'}
                </td>
              </tr>
            </tbody>
          );
        })}
      </table>
    </div>
  );
};

export default ResultCompareTable;
