import { useState } from 'react';

import question from '../../../../assets/img/question.png';
import checkboxBlank from '../../../../assets/img/checkbox-blank.png';
import checkboxChecked from '../../../../assets/img/checkbox-checked.png';

interface Props {
  id: number;
  title: string;
  count: number;
  description: string;
}

const FactorDetail = (props: Props) => {
  const [factorSelected, setFactorSelected] = useState<boolean>(false);
  const [showDescription, setShowDescription] = useState<boolean>(false);

  const factorSelectedHandler = () => {
    setFactorSelected(!factorSelected);
  };

  const mouseEnterHandler = () => {
    setShowDescription(true);
  };

  const mouseLeaveHandler = () => {
    setShowDescription(false);
  };

  return (
    <div>
      <div className='flex ml-[18%] my-[2%] items-center'>
        {factorSelected ? (
          <img
            src={checkboxChecked}
            alt='checkboxChecked'
            className='ml-[1%] mr-[1.1%]'
            onClick={factorSelectedHandler}
          />
        ) : (
          <img
            src={checkboxBlank}
            alt='checkboxBlank'
            onClick={factorSelectedHandler}
          />
        )}
        <p className='ml-[3%]'>{props.title}</p>
        <img
          src={question}
          alt='question'
          className='w-4 mx-[3%]'
          onMouseEnter={mouseEnterHandler}
          onMouseLeave={mouseLeaveHandler}
        />
        <p>{props.count}</p>
      </div>
      {showDescription && (
        <div className='flex justify-center'>
          <p className='text-sm absolute border rounded-lg border-[#131313] bg-[#FEFEFE] px-[3%] py-[2%]'>
            {props.description}
          </p>
        </div>
      )}
    </div>
  );
};

export default FactorDetail;
