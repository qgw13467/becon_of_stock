import { useEffect } from 'react';
import { Pagenation } from '../../../component/Pagenation';
import { SearchbarNone } from '../../../component/search/SearchbarNone';
import { ForumBoard } from '../../../component/ForumBoard';
import axios_api from '../../../assets/config/Axios';
// import axios from 'axios';

export const ForumComponent = () => {
  const PAGE_SIZE = 30; // 한 페이지에 보여질 게시글 수
  const 게시물수 = 10000; // back에서 총 게시물을 받아오게 할 것인가? x
  // 총 페이지 수
  const pageEA = Math.floor(게시물수 / PAGE_SIZE);
  // 더미
  const items: number[] = Array(PAGE_SIZE).fill(0);
  // console.log(pageEA);
  //==========================
  // useEffect(() => {
  //   // console.log(axios_api);
  //   axios_api
  //     .get('/boards')
  //     .then(({ data }) => {
  //       console.log(data);
  //     })
  //     .catch(({ error }) => {
  //       console.log(error);
  //     });
  // }, []);
  //==========================
  // useEffect(() => {
  //   axios
  //     .get('https://j8d207.p.ssafy.io/api/boards')
  //     .then(({ data }) => {
  //       console.log(data);
  //     })
  //     .catch(({ error }) => {
  //       console.log(error);
  //     });
  // }, []);

  return (
    <section className='grid content-between m-9'>
      <article className='flex justify-between my-8'>
        <p>게시판 리스트 입니다.</p>
        <button>글 쓰기</button>
      </article>
      <article className='grid justify-between grid-cols-11 my-4 '>
        <p className='text-center'>번호</p>
        <p className='text-center col-span-4'>제목</p>
        <p className='text-center col-span-2'>글쓴이</p>
        <p className='text-center col-span-2'>등록일</p>
        <p className='text-center'>조회</p>
        <p className='text-center'>추천</p>
      </article>
      <article>
        {items.map((item, index) => (
          <ForumBoard key={index} item={item} />
        ))}
      </article>
      <article>
        <article className='my-8'>
          <Pagenation pageEA={pageEA} />
        </article>
        <article className='flex justify-center ml-32 my-8'>
          <SearchbarNone />
        </article>
      </article>
    </section>
  );
};
