import React, { useState } from 'react';

interface SearchbarProps {
  closeSearchBar: () => void;
}

export const Searchbar: React.FC<SearchbarProps> = ({ closeSearchBar }) => {
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
    <form onSubmit={handleSubmit}>
      <article className='border border-[#131313] rounded-lg w-80 mr-[134px] mb-3 h-10 flex justify-center overflow-hidden relative'>
        <article className='border-r border-[#131313] w-20 h-10 grid justify-center content-center absolute left-0'>
          검색
        </article>
        <article>
          <input type='text' value={searchText} onChange={handleInputChange} />
        </article>
        <article
          className='border-l border-[#131313] w-10 h-10 grid justify-center content-center absolute right-0'
          onClick={() => closeSearchBar()}
        >
          <img src={searchImg} alt='search_img' className='w-8 h-8' />
        </article>
      </article>
    </form>
  );
};
