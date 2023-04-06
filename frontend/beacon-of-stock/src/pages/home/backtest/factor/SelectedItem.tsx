import React from 'react';
import { useBacktestFactorStore } from '../../../../store/store';
import erase from '../../../../assets/img/erase.png';

interface Props {
  id: number;
  title: string;
}

const SelectedItem = (props: Props) => {
  const backtestFactor = useBacktestFactorStore();

  const eraseIndicatorHandler = () => {
    backtestFactor.removeIndicator(props.id);
    backtestFactor.removeSelectedIndicator(props.id);
  };

  return (
    <React.Fragment>
      <div className='flex mt-[3%] ml-[5%] justify-between items-center'>
        <div className='rounded-xl bg-[#FAF6FF] px-[3%] py-[1%]'>
          {props.title}
        </div>
        <img
          src={erase}
          alt='erase'
          onClick={eraseIndicatorHandler}
          className='w-5 h-5 mr-20 cursor-pointer'
        />
      </div>
    </React.Fragment>
  );
};

export default SelectedItem;
