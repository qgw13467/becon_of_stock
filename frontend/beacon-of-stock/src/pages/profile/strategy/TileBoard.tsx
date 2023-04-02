import { useEffect } from 'react';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';

type TileBoard = {
  item: any;
};

export const TileBoard = ({ item }: TileBoard) => {
  // console.log(item);
  const strategyId = item.strategyId;
  const title = item.title;
  const token = getCookie('accessToken');

  useEffect(() => {
    axios_api
      .get(`/strategies/${strategyId}`, {
        headers: {
          authentication: token,
        },
      })
      .then((res) => {
        // 전략에 대한 데이터 들어오면 그래프 그려야함.
        // map으로 반복되기 때문에 그런거니까 신경 ㄴ
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <div className='relative w-[240px] h-[180px] border-[#7D8AD8] rounded-md border-2 m-auto my-2 overflow-hidden'>
      <div className='absolute border-[#7D8AD8] border-2 bg-[#5598DE] text-[#fefefe] w-[240px] h-[80px] rounded-b-md -bottom-[9px] my-2 -right-[1.5px] m-auto'>
        {title !== null ? title : '제목없음'}
      </div>
    </div>
  );
};
