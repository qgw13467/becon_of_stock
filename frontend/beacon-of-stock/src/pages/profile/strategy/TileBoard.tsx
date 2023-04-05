import { getCookie } from '../../../assets/config/Cookie';
import axios_api from '../../../assets/config/Axios';
import star from '../../../assets/img/star.png';
import starOn from '../../../assets/img/starOn.png';
import { useCallback, useEffect, useState } from 'react';
import StrategyGraph from './StrategyGraph';
import { useNavigate } from 'react-router';
import { useBacktestFactorStore } from '../../../store/store';

type TileBoard = {
  item: any;
};

export const TileBoard = ({ item }: TileBoard) => {
  const backtestFactor = useBacktestFactorStore();
  const navigate = useNavigate();

  // console.log('1', item);
  const title = item.title;
  const strategyId = item.strategyId;
  const token = getCookie('accessToken');
  const [rep, setRep] = useState<boolean>(false);

  // ===============전략 그리기 용 axios get===================
  const fatchData = useCallback(() => {
    axios_api
      .get(`/strategies/${strategyId}`, {
        headers: {
          authentication: token,
        },
      })
      .then((res) => {
        // console.log(res);
        setRep(res.data.representative);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [item, rep]);
  // ===========================================================

  useEffect(() => {
    fatchData();
  }, []);

  const putStrategy = () => {
    axios_api
      .put(
        `/strategies/${strategyId}`,
        { strategyId: strategyId },
        {
          headers: {
            authentication: token,
          },
        }
      )
      .then((res) => {
        console.log(res);
        fatchData();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const backtestHandler = () => {
    backtestFactor.loadSelectedIndicator(item.indicators);
    console.log(backtestFactor.selectedIndicators);
  };

  console.log(item);
  console.log(item.indicators);

  return (
    <div className='relative w-[240px] h-[180px] border-[#7D8AD8] rounded-md border-2 m-auto my-2 overflow-hidden'>
      <StrategyGraph cumulativeReturnDtos={item.cummulateReturnDtos} />
      <div className='absolute right-1 top-1' onClick={putStrategy}>
        {rep ? (
          <img src={starOn} alt='starOn' />
        ) : (
          <img src={star} alt='star' />
        )}
      </div>
      <div
        className='absolute grid content-center border-[#7D8AD8] border-2 bg-[#5598DE] w-[240px] h-[65px] rounded-b-md -bottom-[9px] my-2 -right-[1.5px] m-auto'
        onClick={backtestHandler}
      >
        <p className='text-[#fefefe] text-center'>{title}</p>
      </div>
    </div>
  );
};
