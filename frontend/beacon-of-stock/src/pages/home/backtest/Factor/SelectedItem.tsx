import React from 'react';
import { useBacktestFactorStore } from '../../../../store/store';
import erase from '../../../../assets/img/erase.png';
import eraseHover from '../../../../assets/img/erase-hover.png';

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
      <div>{props.id}</div>
      <div>{props.title}</div>
      <img src={erase} alt='erase' onClick={eraseIndicatorHandler} />
    </React.Fragment>
  );
};

export default SelectedItem;
