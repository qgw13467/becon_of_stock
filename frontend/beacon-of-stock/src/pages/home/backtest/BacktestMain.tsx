import React from 'react';
import qs from 'qs';
import BasicSettings from './basic_setting/BasicSettings';
import FactorSettings from './factor/FactorSettings';
import SelectedItems from './factor/SelectedItems';
import {
  useBacktestIndustryStore,
  useBacktestFactorStore,
} from '../../../store/store';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';

const BacktestMain = () => {
  const industriesURL = qs.stringify(
    { industries: [1, 2] },
    { arrayFormat: 'repeat' }
  );
  const indicatorsURL = qs.stringify(
    { indicators: [1, 2, 6, 8] },
    { arrayFormat: 'repeat' }
  );
  const useBacktestIndustry = useBacktestIndustryStore();
  const backtestFactor = useBacktestFactorStore();
  const token = getCookie('accessToken');
  const doBackTestHandler = () => {
    axios_api
      .get(`/backtest?${industriesURL}&${indicatorsURL}`, {
        headers: {
          authentication: token,
        },
        params: {
          // industries: useBacktestIndustry.selectedIndustries,
          // indicators: backtestFactor.selectedIndicators,
          // startYear: basicSettings.start.getFullYear(),
          // startMonth: basicSettings.start.toLocaleString("en-US", { month: "long" }),
          // qs.stringify({industries: [1, 2]}, {arrayFormat: 'repeat'}),
          // indicators: [1, 2, 6, 8],
          startYear: 2007,
          startMonth: 5,
          endYear: 2013,
          endMonth: 5,
          maxStocks: 10,
          fee: 0.35,
          rebalance: 3,
        },
      })
      .then((response) => {
        console.log(response);
        // console.log(response.data);
      })
      .catch((error) => console.log(error));
  };

  return (
    <React.Fragment>
      <div className='flex justify-between items-center my-[1%] mx-[7%]'>
        <p className='text-2xl font-KJCbold'>백테스트</p>
        <div onClick={doBackTestHandler} className='mr-[2.5%]'>
          <input
            className='text-lg font-KJCbold text-[#A47ECF] border border-[#A47ECF] rounded-xl bg-[#FEFEFE] w-[130%] h-10 hover:bg-[#A47ECF] hover:text-[#FEFEFE] cursor-pointer'
            type='submit'
            value='백테스트'
          />
        </div>
      </div>
      <main className='h-[80vh] flex place-content-around items-center mx-[5%]'>
        <section className='relative inline-block w-[29%] h-full border overflow-y-auto'>
          <BasicSettings />
        </section>
        <section className='relative inline-block w-[29%] h-full border overflow-y-auto'>
          <FactorSettings />
        </section>
        <section className='relative inline-block w-[29%] h-full border overflow-y-auto'>
          <SelectedItems />
        </section>
      </main>
    </React.Fragment>
  );
};

export default BacktestMain;
