import React, { useState } from 'react';
import Factor from './Factor';
import FactorDetail from './FactorDetail';

import dropdownImg from '../../../../assets/img/down-arrow.png';
import question from '../../../../assets/img/question.png';

interface Props {
  id: number;
  title: string;
  description: string;
  indicators: {
    id: number;
    title: string;
    count: number;
    description: string;
  }[];
}

const BasicFactor = (props: Props) => {
  const [showIndicator, setShowIndicator] = useState<boolean>(false);
  const [showDescription, setShowDescription] = useState<boolean>(false);

  const showIndicatorHandler = () => {
    setShowIndicator(!showIndicator);
  };

  const mouseEnterHandler = () => {
    setShowDescription(true);
  };

  const mouseLeaveHandler = () => {
    setShowDescription(false);
  };

  return (
    <React.Fragment>
      <div className='flex items-center my-[5%]'>
        <img
          src={dropdownImg}
          alt='dropdownImg'
          className='w-6 mx-[5%]'
          onClick={showIndicatorHandler}
        />
        <div className='text-lg'>{props.title}</div>
        <img
          src={question}
          alt='question'
          className='w-6 mx-[5%]'
          onMouseEnter={mouseEnterHandler}
          onMouseLeave={mouseLeaveHandler}
        />
      </div>
      {showDescription && <p className='text-sm'>{props.description}</p>}
      {showIndicator && (
        <ul>
          {props.indicators.map((indicator) => {
            return (
              <li key={indicator.id}>
                <FactorDetail
                  id={indicator.id}
                  title={indicator.title}
                  count={indicator.count}
                  description={indicator.description}
                />
              </li>
            );
          })}
        </ul>
      )}
    </React.Fragment>
  );
};

export default BasicFactor;
