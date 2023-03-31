import { useEffect, useState, useCallback, memo } from 'react';
import { Pagenation } from '../../../component/Pagenation';
import { SearchbarNone } from '../../../component/search/SearchbarNone';
import { ForumBoard } from '../../../component/ForumBoard';
import { Link, Location } from 'react-router-dom';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';
import { usePageStore } from '../../../store/store';

interface ForumComponentProps {
  location: Location;
}

export const ForumComponent = ({ location }: ForumComponentProps) => {
  const { page } = usePageStore();
  console.log(page);
  const pageNumber = page - 1;
  const pathState = location.state;
  // console.log(location);
  const pathName = location.pathname;
  let pName = '';
  if (pathName.includes('/community/contests/')) {
    pName = pathName.replace('/community/contests/', '');
    Number(pName);
  }
  const PageSize = 30; // 한 페이지에 보여줄 최대 개수
  // 총 게시글 수
  const [totalElement, setTotalElement] = useState(100);
  // 총 페이지 수
  const [totalPage, setTotalPage] = useState(0);
  // 게시글 목록
  const [content, setContent] = useState([]);
  // 현재 페이지
  const token = getCookie('accessToken');

  const fetchData = useCallback(() => {
    if (!pathState) {
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
          console.log(data);
          setContent(data.content);
          setTotalPage(data.totalPages);
          setTotalElement(data.totalElements);
        })
        .catch(({ error }) => {
          console.log(error);
        });
    } else {
      axios_api
        .get(`/contests/${pName}`, {
          headers: {
            authentication: token,
          },
        })
        .then((data) => {
          console.log(data);
          // setContent(data.data.content);
          // setTotalPage(data.data.totalPages);
          // setTotalElement(data.data.totalElements);
          // setPageNumber(data.data.pageNumber);
        })
        .catch((res) => console.log(res));
    }
  }, [page, token, totalPage, pName]);

  useEffect(() => {
    fetchData();
  }, [page]);

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
