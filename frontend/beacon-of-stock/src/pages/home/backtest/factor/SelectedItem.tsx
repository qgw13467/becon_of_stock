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
      <div className='inline-block mt-[3%] ml-[5%] items-center'>
        <div className='flex items-center'>
          <div className='rounded-lg bg-[#FAF6FF] py-[2%]'>{props.title}</div>
          <img
            src={erase}
            alt='erase'
            onClick={eraseIndicatorHandler}
            className='w-5 h-5 cursor-pointer'
          />
        </div>
      </div>
    </React.Fragment>
  );
};

export default SelectedItem;
