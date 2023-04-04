import { useState } from 'react';
import question from '../../../../assets/img/question.png';

interface Props {
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
}

const ResultCumulativeReturnTable = (props: Props) => {
  // console.log(props);
  const [showDesc, setShowDesc] = useState({
    showCumul: false,
    showCagr: false,
    showSharpe: false,
    showSortino: false,
    showMdd: false,
  });

  const mouseEnterHandler1 = () => {
    setShowDesc((prevState) => {
      return {
        ...prevState,
        showCumul: true,
      };
    });
  };

  const mouseLeaveHandler1 = () => {
    setShowDesc((prevState) => {
      return {
        ...prevState,
        showCumul: false,
      };
    });
  };
  const mouseEnterHandler2 = () => {
    setShowDesc((prevState) => {
      return {
        ...prevState,
        showCagr: true,
      };
    });
  };

  const mouseLeaveHandler2 = () => {
    setShowDesc((prevState) => {
      return {
        ...prevState,
        showCagr: false,
      };
    });
  };
  const mouseEnterHandler3 = () => {
    setShowDesc((prevState) => {
      return {
        ...prevState,
        showSharpe: true,
      };
    });
  };

  const mouseLeaveHandler3 = () => {
    setShowDesc((prevState) => {
      return {
        ...prevState,
        showSharpe: false,
      };
    });
  };
  const mouseEnterHandler4 = () => {
    setShowDesc((prevState) => {
      return {
        ...prevState,
        showSortino: true,
      };
    });
  };

  const mouseLeaveHandler4 = () => {
    setShowDesc((prevState) => {
      return {
        ...prevState,
        showSortino: false,
      };
    });
  };
  const mouseEnterHandler5 = () => {
    setShowDesc((prevState) => {
      return {
        ...prevState,
        showMdd: true,
      };
    });
  };

  const mouseLeaveHandler5 = () => {
    setShowDesc((prevState) => {
      return {
        ...prevState,
        showMdd: false,
      };
    });
  };

  return (
    <div className='flex flex-col justify-center'>
      {/* <p className='text-xl font-KJCbold'>ResultCumulativeReturnTable</p> */}
      {/* 표를 나타내는 태그 */}
      <table className='border'>
        {/* 제목 행을 그룹화하는 태그 */}
        <thead className='border'>
          <tr>
            <th className='border'>구분</th>
            <th className='border'>시장</th>
            <th className='border'>내 전략</th>
          </tr>
        </thead>
        {/* 본문 행을 그룹화하는 태그 */}
        <tbody className='border'>
          <tr>
            <td className='flex items-center px-2 py-1'>
              <div>{props.cumulativeReturnDataDto.cumulativeReturn}</div>
              <img
                src={question}
                alt='question'
                className='w-4 h-4 ml-[2%]'
                onMouseEnter={mouseEnterHandler1}
                onMouseLeave={mouseLeaveHandler1}
              />
              {showDesc.showCumul && (
                <div className='text-sm absolute border rounded-lg border-[#131313] bg-[#FEFEFE] px-[1%] py-[0.5%] ml-[2%] mt-[4%]'>
                  {props.cumulativeReturnDataDto.cumulativeReturnDesc}
                </div>
              )}
            </td>
            <td className='px-2 py-1 text-right border'>
              {props.cumulativeReturnDataDto.marketCumulativeReturn.toFixed(2)}%
            </td>
            <td className='px-2 py-1 text-right border'>
              {props.cumulativeReturnDataDto.strategyCumulativeReturn.toFixed(
                2
              )}
              %
            </td>
          </tr>
        </tbody>
        <tbody className='border'>
          <tr>
            <td className='flex items-center px-2 py-1'>
              <div>{props.cumulativeReturnDataDto.cagr}</div>
              <img
                src={question}
                alt='question'
                className='w-4 h-4'
                onMouseEnter={mouseEnterHandler2}
                onMouseLeave={mouseLeaveHandler2}
              />
              {showDesc.showCagr && (
                <div className='text-sm absolute border rounded-lg border-[#131313] bg-[#FEFEFE] px-[1%] py-[0.5%] ml-[2%] mt-[4%]'>
                  {props.cumulativeReturnDataDto.cagrDesc}
                </div>
              )}
            </td>
            <td className='px-2 py-1 text-right border'>
              {props.cumulativeReturnDataDto.marketCagr.toFixed(2)}%
            </td>
            <td className='px-2 py-1 text-right border'>
              {props.cumulativeReturnDataDto.strategyCagr.toFixed(2)}%
            </td>
          </tr>
        </tbody>
        <tbody className='border'>
          <tr>
            <td className='flex items-center px-2 py-1'>
              <div>{props.cumulativeReturnDataDto.sharpe}</div>
              <img
                src={question}
                alt='question'
                className='w-4 h-4 ml-[2%]'
                onMouseEnter={mouseEnterHandler3}
                onMouseLeave={mouseLeaveHandler3}
              />
              {showDesc.showSharpe && (
                <div className='text-sm absolute border rounded-lg border-[#131313] bg-[#FEFEFE] px-[1%] py-[0.5%] ml-[2%] mt-[4%]'>
                  {props.cumulativeReturnDataDto.sharpeDesc}
                </div>
              )}
            </td>
            <td className='px-2 py-1 text-right border'>
              {props.cumulativeReturnDataDto.marketSharpe.toFixed(2)}
            </td>
            <td className='px-2 py-1 text-right border'>
              {props.cumulativeReturnDataDto.strategySharpe.toFixed(2)}
            </td>
          </tr>
        </tbody>
        <tbody className='border'>
          <tr>
            <td className='flex items-center px-2 py-1'>
              <div>{props.cumulativeReturnDataDto.sortino}</div>
              <img
                src={question}
                alt='question'
                className='w-4 h-4 ml-[2%]'
                onMouseEnter={mouseEnterHandler4}
                onMouseLeave={mouseLeaveHandler4}
              />
              {showDesc.showSortino && (
                <div className='text-sm absolute border rounded-lg border-[#131313] bg-[#FEFEFE] px-[1%] py-[0.5%] ml-[2%] mt-[4%]'>
                  {props.cumulativeReturnDataDto.sortinoDesc}
                </div>
              )}
            </td>
            <td className='px-2 py-1 text-right border'>
              {props.cumulativeReturnDataDto.marketSortino.toFixed(2)}
            </td>
            <td className='px-2 py-1 text-right border'>
              {props.cumulativeReturnDataDto.strategySortino.toFixed(2)}
            </td>
          </tr>
        </tbody>
        <tbody className='border'>
          <tr>
            <td className='flex items-center px-2 py-1'>
              <div>{props.cumulativeReturnDataDto.mdd}</div>
              <img
                src={question}
                alt='question'
                className='w-4 h-4 ml-[2%]'
                onMouseEnter={mouseEnterHandler5}
                onMouseLeave={mouseLeaveHandler5}
              />
              {showDesc.showMdd && (
                <div className='text-sm absolute border rounded-lg border-[#131313] bg-[#FEFEFE] px-[1%] py-[0.5%] ml-[2%] mt-[4%]'>
                  {props.cumulativeReturnDataDto.mddDesc}
                </div>
              )}
            </td>
            <td className='px-2 py-1 text-right border'>
              {props.cumulativeReturnDataDto.marketMDD.toFixed(2)}%
            </td>
            <td className='px-2 py-1 text-right border'>
              {props.cumulativeReturnDataDto.strategyMDD.toFixed(2)}%
            </td>
          </tr>
        </tbody>
        {/* 바닥 행을 그룹화하는 태그 */}
        <tfoot>
          <tr></tr>
        </tfoot>
      </table>
    </div>
  );
};

export default ResultCumulativeReturnTable;
