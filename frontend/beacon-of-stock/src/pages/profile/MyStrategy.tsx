import { useEffect, useState } from 'react';
import axios_api from '../../assets/config/Axios';
import { getCookie } from '../../assets/config/Cookie';
import starOn from '../../assets/img/starOn.png';

interface DataType {
  title: string;
  age?: number;
}

export const MyStrategy = () => {
  const [data, setData] = useState<DataType[]>([]);
  useEffect(() => {
    const token = getCookie('accessToken');
    axios_api
      .get('/strategies/representative', {
        headers: { authentication: token },
      })
      .then((res) => {
        // console.log(res);
        setData(res.data);
      })
      .catch((res) => {
        console.log(res);
      });
  }, []);
  return (
    <div className='flex justify-between'>
      {data.map((item, index) => {
        return (
          <div key={index}>
            <div className='relative lg:w-[360px] md:w-[300px] sm:w-[240px] w-[180px] h-[180px] border-[#7D8AD8] rounded-md border-2 mx-1 my-2 overflow-hidden'>
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
  );
};
