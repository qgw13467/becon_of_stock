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
    <div className='flex justify-center'>
      <div className='flex my-[1%] w-[70%] justify-between items-center'>
        <div className='flex items-center'>
          {factorSelected ? (
            <img
              src={checkboxChecked}
              alt='checkboxChecked'
              className='w-5'
              onClick={factorSelectedHandler}
            />
          ) : (
            <img
              src={checkboxBlank}
              alt='checkboxBlank'
              className='w-5'
              onClick={factorSelectedHandler}
            />
          )}
          <p className='ml-1 text-sm'>{props.title}</p>
          <img
            src={question}
            alt='question'
            className='w-5 ml-[3%]'
            onMouseEnter={mouseEnterHandler}
            onMouseLeave={mouseLeaveHandler}
          />
        </div>
        {showDescription && (
          <div className='flex justify-center'>
            <p className='text-sm absolute border rounded-lg border-[#131313] bg-[#FEFEFE] px-[3%] py-[2%]'>
              {props.description}
            </p>
          </div>
        )}
        <div className='flex justify-center rounded-full border-[#131313] bg-[#FAF6FF]'>
          <p>{props.count}</p>
        </div>
      </div>
    </div>
  );
};

export default FactorDetail;
