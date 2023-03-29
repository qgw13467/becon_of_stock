import { useEffect, useState } from 'react';
import { Pagenation } from '../../../component/Pagenation';
import { SearchbarNone } from '../../../component/search/SearchbarNone';
import { ForumBoard } from '../../../component/ForumBoard';
import axios_api from '../../../assets/config/Axios';
import { Link } from 'react-router-dom';
import { getCookie } from '../../../assets/config/Cookie';
// import axios from 'axios';

export const ForumComponent = () => {
  const [PageSize, setPageSize] = useState<number>(30); // 한 페이지에 보여질 게시글 수
  const [totalElement, setTotalElement] = useState<number>(10000); // back에서 총 게시물을 받아오게 할 것인가? x
  // 총 페이지 수
  const [totalPage, setTotalPage] = useState<number>(0);
  // content
  // const items: number[] = Array(PageSize).fill(0);
  const [content, setContent] = useState([]);
  //==========================
  const token = getCookie('accessToken');
  // console.log(token);
  useEffect(() => {
    axios_api
      .get('/boards/', {
        headers: {
          authentication: token,
        },
        params: {
          page: 0,
        },
      })
      .then(({ data }) => {
        setContent(data.content);
      })
      .catch(({ error }) => {
        console.log(error);
      });
  }, []);
  //==========================

  return (
    <section className='grid content-between m-9'>
      <article className='flex justify-between my-8'>
        <p>게시판 리스트 입니다.</p>
        <Link to='/community/write'>글 쓰기</Link>
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
        {content.map((item, index) => (
          <ForumBoard key={index} item={item} />
        ))}
      </article>
      <article>
        <article className='my-8'>
          <Pagenation totalPage={totalPage} />
        </article>
        <article className='flex justify-center ml-32 my-8'>
          <SearchbarNone />
        </article>
      </article>
    </section>
  );
};
