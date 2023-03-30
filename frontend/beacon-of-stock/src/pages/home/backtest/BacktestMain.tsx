import React from 'react';

import BasicSettings from './basic_setting/BasicSettings';
import FactorSettings from './factor/FactorSettings';
import SelectedItems from './factor/SelectedItems';

const backTestHandler = (event: React.FormEvent<HTMLFormElement>) => {
  event.preventDefault();
};

const BacktestMain = () => {
  return (
    <React.Fragment>
      <form onSubmit={backTestHandler}>
        <div className='flex justify-between items-center my-[1%] mx-[7%]'>
          <p className='text-2xl font-KJCbold'>백테스트</p>
          <input
            className='text-lg font-KJCbold text-[#A47ECF] border border-[#A47ECF] rounded-xl bg-[#FEFEFE] px-[1%] py-[0.5%] hover:bg-[#A47ECF] hover:text-[#FEFEFE] cursor-pointer'
            type='submit'
            value='백테스트'
          />
        </div>
        <main className='h-[80vh] flex place-content-around items-center mx-[5%]'>
          <section className='relative inline-block w-[29%] h-full border overflow-y-auto'>
            <BasicSettings />
          </section>
          <section className='relative inline-block w-[29%] h-full border overflow-y-auto'>
            <FactorSettings />
          </section>
          <section className='relative inline-block w-[29%] h-full border overflow-y-auto'>
            <SelectedItems />
          </section>
        </main>
      </form>
    </React.Fragment>
  );
};

export default BacktestMain;
