import { useEffect, useState } from 'react';
import axios_api from '../../assets/config/Axios';
import { getCookie } from '../../assets/config/Cookie';
import starOn from '../../assets/img/starOn.png';
import MyStrategyGraph from './MyStrategyGraph';
import { useNavigate } from 'react-router';

export const MyStrategy = () => {
  const [data, setData] = useState<any>(undefined);
  useEffect(() => {
    const token = getCookie('accessToken');
    axios_api
      .get('/strategies/representative', {
        headers: { authentication: token },
      })
      .then((res) => {
        // console.log(res);
        setData(res.data.content);
      })
      .catch((res) => {
        console.log(res);
      });
  }, []);
  // console.log(data);

  // 백테스트로 리다이렉트
  const navigate = useNavigate();

  // const backtestHandler = () => {
  //   // console.log(item.indicators);
  //   navigate('/', { state: { indicators: item.indicators } });
  // };

  return (
    <div className='col-span-2 my-6 ml-32'>
      {data !== undefined && (
        <div className='my-4 py-1 w-[800px]'>
          <div className='text-2xl font-bold bg-[#A47ECF] text-[#FEFEFE] rounded text-center lg:w-[360px] md:w-[300px] sm:w-[240px] w-[180px] ml-56 mb-8'>
            내 대표 전략
          </div>
          <div className='grid grid-cols-2'>
            {data.map((item: any, index: number) => {
              return (
                <div key={index} className='flex justify-center my-2'>
                  <div
                    id='hover-big'
                    className='relative lg:w-[300px] md:w-[240px] w-[180px] h-[180px] border-[#A47ECF] rounded-md border-2 my-2 overflow-hidden hover:scale-110 bg-[#fefefe] duration-500 ml-1'
                  >
                    <MyStrategyGraph
                      cumulativeReturnDtos={item.cummulateReturnDtos}
                    />
                    <div className='absolute right-1 top-1'>
                      <img src={starOn} alt='starOn' />
                    </div>
                    <div className='absolute left-4 top-1 text-2xl text-[#131313] font-KJCbold'>
                      {index + 1}
                    </div>
                    <div
                      className='absolute grid content-center border-[#A47ECF] border-2 bg-[#A47ECF] text-[#FEFEFE] lg:w-[300px] md:w-[240px] w-[180px] h-[56px] rounded-b-md -bottom-[9px] my-2 -right-[1.5px] m-auto text-center text-lg font-bold'
                      // onClick={backtestHandler}
                    >
                      {item.title}
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
          <div className='flex justify-end mr-12'>
            <button className='my-6 bg-[#A47ECF] w-24 text-[#FEFEFE] rounded-md'>
              전략수정
            </button>
          </div>
        </div>
      )}
    </div>
  );
};
