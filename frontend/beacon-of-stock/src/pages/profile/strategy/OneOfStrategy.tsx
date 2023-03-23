import React from 'react';

type OneOfStrategyProps = {
  item: number;
};

export const OneOfStrategy = ({ item }: OneOfStrategyProps) => {
  return (
    <div className='relative w-[240px] h-[180px] border-black rounded-md border-2 m-auto my-2'>
      <div className='absolute border-black border-2 bg-[#808080] w-[240px] h-[80px] rounded-b-md -bottom-[9px] my-2 -right-[1.5px] m-auto'></div>
    </div>
  );
};
