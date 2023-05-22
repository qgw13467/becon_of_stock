import { useState } from 'react';
import { Searchbar } from './Searchbar';

export const Search = () => {
  const searchImg = require('../../assets/img/search.png');
  const [isTrue, setIsTrue] = useState(false);
  const openSearchBar = () => {
    // console.log(isTrue);
    setIsTrue(true);
  };
  const closeSearchBar = () => {
    // console.log(isTrue);
    setIsTrue(false);
  };
  return (
    <section>
      {!isTrue && (
        <article
          className='border border-[#808080] rounded-lg w-10 h-10 grid justify-center content-center mr-[134px] mb-3'
          onClick={() => openSearchBar()}
        >
          <img src={searchImg} alt='search_img' className='w-8 h-8' />
        </article>
      )}
      {isTrue && <Searchbar closeSearchBar={closeSearchBar} />}
    </section>
  );
};
