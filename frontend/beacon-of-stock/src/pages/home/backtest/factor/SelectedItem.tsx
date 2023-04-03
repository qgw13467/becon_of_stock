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
      <div className='flex mt-[4%] mx-[10%] justify-between items-center'>
        <div>{props.title}</div>
        <img
          src={erase}
          alt='erase'
          onClick={eraseIndicatorHandler}
          className='w-5 h-5 cursor-pointer'
        />
      </div>
    </React.Fragment>
  );
};

export default SelectedItem;
