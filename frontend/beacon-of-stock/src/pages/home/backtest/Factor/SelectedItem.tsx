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
  };
  return (
    <React.Fragment>
      <div className='flex mt-[5%] mx-[10%] justify-between items-center'>
        <div className='text-sm'>{props.title}</div>
        <img
          src={erase}
          alt='erase'
          onClick={eraseIndicatorHandler}
          className='w-4 h-4'
        />
      </div>
    </React.Fragment>
  );
};

export default SelectedItem;
