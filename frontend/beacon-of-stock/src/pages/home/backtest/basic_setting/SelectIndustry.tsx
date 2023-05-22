import React, { useState, useEffect } from 'react';
import { useBacktestIndustryStore } from '../../../../store/store';
import checkboxChecked from '../../../../assets/img/checkbox-checked.png';
import checkboxBlank from '../../../../assets/img/checkbox-blank.png';

interface Props {
  id: number;
  industryName: string;
  onCheckSelected: (checkSelected: boolean) => void;
}

const SelectIndustry = (props: Props) => {
  // 산업 선택 여부
  const [industrySelected, setIndustrySelected] = useState<boolean>(true);

  const backtestIndustry = useBacktestIndustryStore();
  // console.log(backtestIndustry);
  // console.log(backtestIndustry.selectedIndustries.sort());
  // console.log(backtestIndustry.allSelectedIndustry.sort());

  // 제일 처음 로드될 때만 모든 id값을 getAllIndustry 추가
  useEffect(() => {
    if (!backtestIndustry.allSelectedIndustry.includes(props.id)) {
      backtestIndustry.getAllIndustry(props.id);
    }
  }, []);

  useEffect(() => {
    if (
      backtestIndustry.selectedIndustries.includes(props.id) &&
      industrySelected === false
    ) {
      setIndustrySelected(true);
    } else if (!backtestIndustry.selectedIndustries.includes(props.id)) {
      setIndustrySelected(false);
    }
    if (
      backtestIndustry.selectedIndustries.length ===
      backtestIndustry.allSelectedIndustry.length
    ) {
      props.onCheckSelected(true);
    } else {
      props.onCheckSelected(false);
    }
  }, [
    backtestIndustry.selectedIndustries,
    backtestIndustry.allSelectedIndustry,
  ]);

  const industrySelectedHandler = () => {
    setIndustrySelected(!industrySelected);
    if (!industrySelected) {
      backtestIndustry.addSelectedIndustry(props.id);
    } else if (industrySelected) {
      backtestIndustry.removeSelectedIndustry(props.id);
    }
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
