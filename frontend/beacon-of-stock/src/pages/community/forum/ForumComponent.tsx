import { useEffect, useState, useCallback, memo } from 'react';
import { Pagenation } from '../../../component/Pagenation';
import { SearchbarNone } from '../../../component/search/SearchbarNone';
import { ForumBoard } from '../../../component/ForumBoard';
import { Link } from 'react-router-dom';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';

// const ForumBoard = memo(({ item }) => {
//   // ... ForumBoard 컴포넌트 내용
// });

export const ForumComponent = () => {
  const PageSize = 30;
  const [totalElement, setTotalElement] = useState(100);
  const [totalPage, setTotalPage] = useState(0);
  const [content, setContent] = useState([]);
  const [page, setPage] = useState(0);
  const [pageNumber, setPageNumber] = useState(0);
  const token = getCookie('accessToken');

  const fetchData = useCallback(() => {
    axios_api
      .get('/boards/', {
        headers: {
          authentication: token,
        },
        params: {
          page,
        },
      })
      .then(({ data }) => {
        setContent(data.content);
        setTotalPage(data.totalPage);
        setTotalElement(data.totalElements);
        if (totalPage - 1 >= data.pageNumber) {
          setPageNumber(data.pageNumber);
        }
      })
      .catch(({ error }) => {
        console.log(error);
      });
  }, [page, token, totalPage]);

  useEffect(() => {
    fetchData();
  }, []);

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

// import { useEffect, useState } from 'react';
// import { Pagenation } from '../../../component/Pagenation';
// import { SearchbarNone } from '../../../component/search/SearchbarNone';
// import { ForumBoard } from '../../../component/ForumBoard';
// import axios_api from '../../../assets/config/Axios';
// import { Link } from 'react-router-dom';
// import { getCookie } from '../../../assets/config/Cookie';
// // import axios from 'axios';

// export const ForumComponent = () => {
//   const PageSize: number = 30; // 한 페이지에 보여질 게시글 수
//   // const [PageSize, setPageSize] = useState<number>(30); // 한 페이지에 보여질 게시글 수
//   const [totalElement, setTotalElement] = useState<number>(100); // back에서 총 게시물 수 받아왔음.
//   // 총 페이지 수
//   const [totalPage, setTotalPage] = useState<number>(0);
//   // content
//   // const items: number[] = Array(PageSize).fill(0);
//   const [content, setContent] = useState([]);
//   const [page, setPage] = useState<number>(0);
//   const [pageNumber, setPageNumber] = useState<number>(0);
//   //==========================
//   const token = getCookie('accessToken');
//   // console.log(token);
//   useEffect(() => {
//     axios_api
//       .get('/boards/', {
//         headers: {
//           authentication: token,
//         },
//         params: {
//           page: page,
//         },
//       })
//       .then(({ data }) => {
//         console.log(data);
//         setContent(data.content);
//         setTotalPage(data.totalPage);
//         setTotalElement(data.totalElements);
//         if (totalPage - 1 >= data.pageNumber) {
//           setPageNumber(data.pageNumber);
//         } else {
//         }
//       })
//       .catch(({ error }) => {
//         console.log(error);
//       });
//   }, []);
//   //==========================

//   return (
//     <section className='grid content-between m-9'>
//       <article className='flex justify-between my-8'>
//         <p>게시판 리스트 입니다.</p>
//         <Link to='/community/write'>글 쓰기</Link>
//       </article>
//       <article className='grid justify-between grid-cols-11 my-4 '>
//         <p className='text-center'>번호</p>
//         <p className='text-center col-span-4'>제목</p>
//         <p className='text-center col-span-2'>글쓴이</p>
//         <p className='text-center col-span-2'>등록일</p>
//         <p className='text-center'>조회</p>
//         <p className='text-center'>추천</p>
//       </article>
//       <article>
//         {content.map((item, index) => (
//           <ForumBoard key={index} item={item} />
//         ))}
//       </article>
//       <article>
//         <article className='my-8'>
//           <Pagenation totalPage={totalPage} />
//         </article>
//         <article className='flex justify-center ml-32 my-8'>
//           <SearchbarNone />
//         </article>
//       </article>
//     </section>
//   );
// };
