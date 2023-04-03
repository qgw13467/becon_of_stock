// import { useEffect } from 'react';
import { getCookie } from '../../../assets/config/Cookie';
import axios_api from '../../../assets/config/Axios';
import star from '../../../assets/img/star.png';
import starOn from '../../../assets/img/starOn.png';

type TileBoard = {
  item: any;
};

export const TileBoard = ({ item }: TileBoard) => {
  // console.log(item);
  const title = item.title;
  const strategyId = item.strategyId;
  const token = getCookie('accessToken');

  // ===============전략 그리기 용 axios get===================

  // useEffect(() => {
  //   axios_api
  //     .get(`/strategies/${strategyId}`, {
  //       headers: {
  //         authentication: token,
  //       },
  //     })
  //     .then((res) => {
  //       // 전략에 대한 데이터 들어오면 그래프 그려야함.
  //       // map으로 반복되기 때문에 콘솔 많이 찍힘. 신경 ㄴ
  //       console.log(res);
  //     })
  //     .catch((err) => {
  //       console.log(err);
  //     });
  // }, []);
  // ===========================================================

  const putStrategy = () => {
    axios_api
      .put(`/strategies/${strategyId}`, {
        headers: {
          authentication: token,
        },
      })
      .then((res) => {
        // 전략에 대한 데이터 들어오면 그래프 그려야함.
        // map으로 반복되기 때문에 콘솔 많이 찍힘. 신경 ㄴ
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  return (
    <div className='relative w-[240px] h-[180px] border-[#7D8AD8] rounded-md border-2 m-auto my-2 overflow-hidden'>
      <div className='absolute right-1 top-1' onClick={putStrategy}>
        {item.indicators ? (
          <img src={starOn} alt='starOn' />
        ) : (
          <img src={star} alt='star' />
        )}
      </div>
      <div className='absolute border-[#7D8AD8] border-2 bg-[#5598DE] text-[#fefefe] w-[240px] h-[80px] rounded-b-md -bottom-[9px] my-2 -right-[1.5px] m-auto'>
        {title !== null ? title : '제목없음'}
      </div>
    </div>
  );
};
