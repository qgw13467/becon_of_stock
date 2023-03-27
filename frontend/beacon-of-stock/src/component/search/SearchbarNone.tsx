import React, { useState } from 'react';
import SearchSelect from '../select_box/SearchSelect';

// Props가 안 될 경우의 Searchbar
export const SearchbarNone = () => {
  const searchImg = require('../../assets/img/search.png');
  const [searchText, setSearchText] = useState('');

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchText(event.target.value);
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    // 여기에 입력된 값을 사용하는 로직을 추가할 수 있습니다.
  };

  return (
    <article className='border border-[#131313] rounded-lg w-80 mr-[134px] mb-3 h-10 flex justify-between overflow-hidden'>
      <article className='border-r border-[#131313] w-20 h-10 grid justify-center content-center  '>
        <SearchSelect />
      </article>
      <input
        type='text'
        value={searchText}
        onChange={handleInputChange}
        className='w-50 indent-2 focus:outline-none'
      />
      <article className='border-l border-[#131313] w-10 h-10 grid justify-center content-center cursor-pointer'>
        <img src={searchImg} alt='search_img' className='w-8 h-8' />
      </article>
    </article>
  );
};
