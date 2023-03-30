import React, { useState } from 'react';
import checkboxChecked from '../../../../assets/img/checkbox-checked.png';
import checkboxBlank from '../../../../assets/img/checkbox-blank.png';

interface Props {
  id: number;
  industryName: string;
}

const SelectIndustry = (props: Props) => {
  // 산업 선택 여부
  const [industrySelected, setIndustrySelected] = useState<boolean>(true);
  const industrySelectedHandler = () => {
    setIndustrySelected(!industrySelected);
  };

  return (
    <div className='flex items-center my-[2%] ml-[5%]'>
      {industrySelected ? (
        <img
          src={checkboxChecked}
          alt='checkboxChecked'
          className='w-4 h-4'
          onClick={industrySelectedHandler}
        />
      ) : (
        <img
          src={checkboxBlank}
          alt='checkboxBlank'
          className='w-4 h-4'
          onClick={industrySelectedHandler}
        />
      )}
      <div className='ml-2 text-lg'>{props.industryName}</div>
    </div>
  );
};

export default SelectIndustry;
