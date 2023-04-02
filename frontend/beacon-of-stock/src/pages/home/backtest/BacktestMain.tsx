import React, { useState } from 'react';
import { useNavigate } from 'react-router';
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
  const useBacktestIndustry = useBacktestIndustryStore();
  const backtestFactor = useBacktestFactorStore();
  const token = getCookie('accessToken');
  const navigate = useNavigate();

  type UpdateSettings = {
    industries: string;
    fee: string;
    maxStocks: string;
    rebalance: string;
    start: string;
    end: string;
  };

  // 기본 설정 Lift
  const [updatedSettings, setUpdatedSettings] = useState({
    startYear: 2001,
    startMonth: 1,
    endYear: 2021,
    endMonth: 12,
    maxStocks: '10',
    fee: '0',
    rebalance: '3',
  });

  const updateSettingsHandler = (updatedSettings: UpdateSettings) => {
    // console.log(updatedSettings);
    setUpdatedSettings((prevState) => {
      return {
        ...prevState,
        startYear: new Date(updatedSettings.start).getFullYear(),
      };
    });
    setUpdatedSettings((prevState) => {
      return {
        ...prevState,
        startMonth: new Date(updatedSettings.start).getMonth() + 1,
      };
    });
    setUpdatedSettings((prevState) => {
      return {
        ...prevState,
        endYear: new Date(updatedSettings.end).getFullYear(),
      };
    });
    setUpdatedSettings((prevState) => {
      return {
        ...prevState,
        endMonth: new Date(updatedSettings.end).getMonth() + 1,
      };
    });
    setUpdatedSettings((prevState) => {
      return {
        ...prevState,
        maxStocks: updatedSettings.maxStocks,
      };
    });
    setUpdatedSettings((prevState) => {
      return {
        ...prevState,
        fee: updatedSettings.fee,
      };
    });
    setUpdatedSettings((prevState) => {
      return {
        ...prevState,
        rebalance: updatedSettings.rebalance.slice(0, -2),
      };
    });
  };
  console.log(updatedSettings);

  // 백테스트 실행
  const industriesURL = qs.stringify(
    { industries: useBacktestIndustry.selectedIndustries },
    { arrayFormat: 'repeat' }
  );
  const indicatorsURL = qs.stringify(
    { indicators: backtestFactor.selectedIndicators },
    { arrayFormat: 'repeat' }
  );
  const doBackTestHandler = () => {
    axios_api
      .get(`/backtest?${industriesURL}&${indicatorsURL}`, {
        headers: {
          authentication: token,
        },
        params: {
          startYear: updatedSettings.startYear,
          startMonth: updatedSettings.startMonth,
          endYear: updatedSettings.endYear,
          endMonth: updatedSettings.endMonth,
          maxStocks: updatedSettings.maxStocks,
          fee: updatedSettings.fee,
          rebalance: updatedSettings.rebalance,
        },
      })
      .then((response) => {
        // console.log(response);
        console.log(response.data.asd);
        const data = response.data;
        useBacktestIndustry.selectAllIndustry();
        backtestFactor.resetSelectedIndicator();
        backtestFactor.resetIndicator();
        navigate('/result', { state: { data } });
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
          <BasicSettings onUpdateSettings={updateSettingsHandler} />
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
