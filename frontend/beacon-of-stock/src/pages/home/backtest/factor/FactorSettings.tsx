import React, { useState, useEffect } from 'react';
import FactorBasic from './FactorBasic';
import { useBacktestFactorStore } from '../../../../store/store';
import axios_api from '../../../../assets/config/Axios';
import { getCookie } from '../../../../assets/config/Cookie';

const FactorSettings = () => {
  const backtestFactor = useBacktestFactorStore();
  // console.log(backtestFactor.indicators);
  // console.log(backtestFactor.selectedIndicators);

  const resetIndicatorHandler = () => {
    backtestFactor.resetIndicator();
    backtestFactor.resetSelectedIndicator();
  };

  type dataType = {
    factors: {
      description: string;
      id: number;
      title: string;
      indicators: {
        id: number;
        title: string;
        count: number;
        description: string;
      }[];
    }[];
  };

  const [data, setData] = useState<dataType>();
  const token = getCookie('accessToken');

  useEffect(() => {
    axios_api
      .get('/indicators', {
        headers: {
          authentication: token,
        },
      })
      .then((response) => {
        // console.log(response);
        // console.log(response.data);
        setData(response.data);
      })
      .catch((error) => console.log(error));
  }, [token]);

  // console.log(data);

  return (
    <React.Fragment>
      <div className='flex items-start justify-between'>
        <div className='items-start text-xl font-KJCbold w-[60%]'>
          팩터 설정
        </div>
        <div className='flex flex-col mr-[8%]'>
          <p
            className='mt-[3%] text-sm mx-[5%] cursor-pointer text-[#808080] hover:text-[#131313] hover:font-KJCbold'
            onClick={resetIndicatorHandler}
          >
            초기화
          </p>
          <p className='relative top-[15px] rounded-full border-[#131313] bg-[#FAF6FF]'>
            사용횟수
          </p>
        </div>
      </div>
      <ul>
        {data?.factors.map((factor) => {
          return (
            <li key={factor.id}>
              <FactorBasic
                id={factor.id}
                title={factor.title}
                description={factor.description}
                indicators={factor.indicators}
              />
            </li>
          );
        })}
      </ul>
    </React.Fragment>
  );
};

export default FactorSettings;
