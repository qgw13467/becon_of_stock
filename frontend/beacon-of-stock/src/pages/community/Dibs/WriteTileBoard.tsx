// import { useEffect } from 'react';
// import axios_api from '../../../assets/config/Axios';
// import { getCookie } from '../../../assets/config/Cookie';
// import { Dispatch, SetStateAction } from 'react';
import StrategyGraph from '../../profile/strategy/StrategyGraph';

type WriteTileBoardProps = {
  item: any;
  setThisId: (id: number, title: string) => void;
  closeModal: () => void;
};

export const WriteTileBoard = ({
  item,
  setThisId,
  closeModal,
}: WriteTileBoardProps) => {
  // console.log(item);
  const strategyId = item.strategyId;
  // console.log(strategyId);
  const strategyTitle = item.title;
  // const token = getCookie('accessToken');

  // useEffect(() => {
  //   axios_api
  //     .get(`/strategies/${strategyId}`, {
  //       headers: {
  //         authentication: token,
  //       },
  //     })
  //     .then((res) => {
  //       // 전략에 대한 데이터 들어오면 그래프 그려야함.
  //       // map으로 반복되기 때문에 그런거니까 신경 ㄴ
  //       console.log(res);
  //     })
  //     .catch((err) => {
  //       console.log(err);
  //     });
  // }, []);

  return (
    <div
      className='relative w-[240px] h-[180px] border-[#7D8AD8] rounded-md border-2 m-auto my-2 overflow-hidden'
      onClick={() => {
        setThisId(strategyId, strategyTitle);
        closeModal();
      }}
    >
      <StrategyGraph cumulativeReturnDtos={item.cummulateReturnDtos} />
      <div className='absolute grid content-center border-[#7D8AD8] border-2 bg-[#5598DE] w-[240px] h-[65px] rounded-b-md -bottom-[9px] my-2 -right-[1.5px] m-auto '>
        <p className='text-[#fefefe] text-center'>{strategyTitle}</p>
      </div>
    </div>
  );
};
