import React from 'react';
import SelectedItem from './SelectedItem';
import { useBacktestFactorStore } from '../../../../store/store';

const SelectedItems = () => {
  const backtestFactor = useBacktestFactorStore();
  // console.log(backtestFactor.indicators);

  return (
    <React.Fragment>
      <p className='text-xl font-KJCbold'>선택 항목 보기</p>
      <ul>
        {backtestFactor.indicators.map((indicator) => {
          return (
            <li key={indicator.id}>
              <SelectedItem id={indicator.id} title={indicator.title} />
            </li>
          );
        })}
      </ul>
    </React.Fragment>
  );
};

export default SelectedItems;
