import { useEffect, useState } from 'react';
import axios_api from '../../assets/config/Axios';
import { getCookie } from '../../assets/config/Cookie';
import starOn from '../../assets/img/starOn.png';
import MyStrategyGraph from './MyStrategyGraph';

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
  return (
    <div>
      {data !== undefined && (
        <div className='my-4 mx-32 px-4 py-1'>
          <div className='text-2xl font-bold border-2 border-cyan-600 ml-1 rounded text-center lg:w-[360px] md:w-[300px] sm:w-[240px] w-[180px]'>
            내 대표 전략
          </div>
          <div className='flex justify-between'>
            {data.map((item: any, index: number) => {
              return (
                <div key={index}>
                  <div
                    id='hover-big'
                    className='relative lg:w-[360px] md:w-[300px] sm:w-[240px] w-[180px] h-[180px] border-[#7D8AD8] rounded-md border-2 mx-1 my-2 overflow-hidden hover:scale-125 bg-[#fefefe]'
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
                    <div className='absolute grid content-center border-[#7D8AD8] border-2 bg-[#5598DE] text-[#fefefe] lg:w-[360px] md:w-[300px] sm:w-[240px] w-[180px] h-[65px] rounded-b-md -bottom-[9px] my-2 -right-[1.5px] m-auto text-center text-lg font-bold'>
                      {item.title}
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      )}
    </div>
  );
};
