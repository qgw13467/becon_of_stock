import { useState } from 'react';

import question from '../../../../assets/img/question.png';

interface Props {
  id: number;
  title: string;
  count: number;
  description: string;
}

const FactorDetail = (props: Props) => {
  const [showDescription, setShowDescription] = useState<boolean>(false);

  const mouseEnterHandler = () => {
    setShowDescription(true);
  };

  const mouseLeaveHandler = () => {
    setShowDescription(false);
  };

  return (
    <div>
      <div className='flex flex-row items-center'>
        <p>{props.title}</p>
        <img
          src={question}
          alt='question'
          className='w-4 mx-[3%]'
          onMouseEnter={mouseEnterHandler}
          onMouseLeave={mouseLeaveHandler}
        />
        <p>{props.count}</p>
      </div>
      {showDescription && <p className='text-sm'>{props.description}</p>}
    </div>
  );
};

export default FactorDetail;
