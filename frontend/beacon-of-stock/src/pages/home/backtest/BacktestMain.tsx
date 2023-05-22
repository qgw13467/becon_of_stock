import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';
import { useLocation } from 'react-router-dom';
import qs from 'qs';
import BasicSettings from './basic_setting/BasicSettings';
import FactorSettings from './factor/FactorSettings';
import SelectedItems from './factor/SelectedItems';
import Loading from './result/Loading';
import {
  useBacktestIndustryStore,
  useBacktestFactorStore,
} from '../../../store/store';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';
import Swal from 'sweetalert2';

const BacktestMain = () => {
  const useBacktestIndustry = useBacktestIndustryStore();
  const backtestFactor = useBacktestFactorStore();
  const token = getCookie('accessToken');
  const navigate = useNavigate();
  const location = useLocation();

  type UpdateSettings = {
    industries: string;
    fee: string;
    maxStocks: string;
    rebalance: string;
    start: string;
    end: string;
  };

  // 기본 설정 버튼 클릭
  const [showFactor, setShowFactor] = useState(false);
  const showFactorHandler = () => {
    setShowFactor(true);
  };

  // 기본 설정 Lift
  const [updatedSettings, setUpdatedSettings] = useState({
    startYear: 2015,
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
  // console.log(updatedSettings);

  // 백테스트 실행
  const [isLoading, setIsLoading] = useState(false);

  const industriesURL = qs.stringify(
    { industries: useBacktestIndustry.selectedIndustries },
    { arrayFormat: 'repeat' }
  );
  const indicatorsURL = qs.stringify(
    { indicators: backtestFactor.selectedIndicators },
    { arrayFormat: 'repeat' }
  );

  // componentUnmount의 경우 설정 초기화
  const reset = () => {
    backtestFactor.resetSelectedIndicator();
    backtestFactor.resetIndicator();
    useBacktestIndustry.selectedIndustries = [
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
    ];
    // useBacktestIndustry.selectedIndustries =
    //   useBacktestIndustry.allSelectedIndustry;
  };

  const doBackTestHandler = () => {
    if (
      useBacktestIndustry.selectedIndustries.length === 0 ||
      backtestFactor.selectedIndicators.length === 0
    ) {
      Swal.fire({
        html: '<div>한 개 이상의 산업과 팩터를</div><div>선택해주세요!</div>',
        width: 300,
        icon: 'warning',
        confirmButtonText: '<div>확인</div>',
        confirmButtonColor: '#f8bb86',
      });
    } else if (updatedSettings.maxStocks === '0') {
      Swal.fire({
        html: '<div>최대 종목 수는</div><div>1개 이상 30개 이하여야 합니다!</div>',
        width: 300,
        icon: 'warning',
        confirmButtonText: '<div>확인</div>',
        confirmButtonColor: '#f8bb86',
      });
    } else if (
      updatedSettings.startYear < 2001 ||
      updatedSettings.startYear > 2021 ||
      updatedSettings.endYear < 2001 ||
      updatedSettings.endYear > 2021 ||
      updatedSettings.startYear > updatedSettings.endYear ||
      (updatedSettings.startYear === updatedSettings.endYear &&
        updatedSettings.startMonth >= updatedSettings.endMonth)
    ) {
      Swal.fire({
        html: '<div>시작 시점과</div><div>종료 시점을 확인해 주세요!</div>',
        width: 300,
        icon: 'warning',
        confirmButtonText: '<div>확인</div>',
        confirmButtonColor: '#f8bb86',
      });
    } else {
      setIsLoading(true);
      useBacktestIndustry.selectAllIndustry();
      backtestFactor.resetSelectedIndicator();
      backtestFactor.resetIndicator();
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
          const data = response.data;
          navigate('/result', { state: { data } });
          setIsLoading(false);
          setShowFactor(false);
        })
        .catch((error) => {
          console.log(error);
          setIsLoading(false);
          Swal.fire({
            html: '<div>다시 시도해주세요!</div>',
            width: 300,
            icon: 'error',
            confirmButtonText: '<div>확인</div>',
            confirmButtonColor: '#f27474',
          });
        });
    }
  };

  // 새로고침 시 정보 초기화
  const beforeUnLoad = () => {
    // reset();
  };
  // 대표전략이나 나의 전략조회에서 바로 넘어오는 경우
  useEffect(() => {
    if (location.state !== null) {
      const redirectIndicators: number[] = location.state.indicators;
      // console.log(redirectIndicators);
      backtestFactor.loadSelectedIndicator(redirectIndicators);
      setShowFactor(true);
    }
    window.addEventListener('beforeunload', beforeUnLoad);
    return () => {
      window.removeEventListener('beforeunload', beforeUnLoad);
      reset();
    };
  }, []);

  return (
    <React.Fragment>
      {isLoading ? (
        <Loading />
      ) : (
        <div>
          <div className='flex justify-between items-center my-[1%] mx-[7%]'>
            <p className='text-2xl font-KJCbold'>백테스트</p>
          </div>
          <main
            className={
              showFactor
                ? 'h-[80vh] flex items-center mx-[5%]'
                : 'h-[80vh] flex items-center mx-[5%]'
            }
          >
            <section className='relative inline-block w-[30%] pl-[2%] h-full border-l border-r border-[#FAF6FF] overflow-y-auto'>
              <BasicSettings onUpdateSettings={updateSettingsHandler} />
              {!showFactor && (
                <div className='flex justify-end mt-[5%] mr-[5%]'>
                  <input
                    type='button'
                    onClick={showFactorHandler}
                    value='다음'
                    // className='bg-[#B8C2FD]'
                    className='text-lg font-KJCbold text-[#A47ECF] border border-[#A47ECF] rounded-xl bg-[#FEFEFE] w-[30%] h-10 hover:bg-[#A47ECF] hover:text-[#FEFEFE] cursor-pointer'
                  />
                </div>
              )}
            </section>
            {showFactor && (
              <>
                <section className='relative inline-block w-[30%] ml-[2%] h-full border-r border-[#FAF6FF] overflow-y-auto'>
                  <FactorSettings />
                </section>
                <section className='relative inline-block w-[30%] ml-[2%] h-full border-r border-[#FAF6FF] overflow-y-auto'>
                  <SelectedItems />
                </section>
              </>
            )}
            {showFactor && (
              <div
                onClick={doBackTestHandler}
                className='fixed right-0 bottom-0 mr-[13%] mb-[3%]'
              >
                <input
                  className='text-lg font-KJCbold text-[#A47ECF] border border-[#A47ECF] rounded-xl bg-[#FEFEFE] w-[150%] h-10 hover:bg-[#A47ECF] hover:text-[#FEFEFE] cursor-pointer'
                  type='submit'
                  value='백테스트'
                />
              </div>
            )}
          </main>
        </div>
      )}
    </React.Fragment>
  );
};

export default BacktestMain;
