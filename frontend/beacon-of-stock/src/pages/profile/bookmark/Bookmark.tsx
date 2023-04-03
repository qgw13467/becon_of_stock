import { SearchbarNone } from '../../../component/search/SearchbarNone';
import { Pagenation } from '../../../component/Pagenation';
import { BookBoard } from './BookBoard';
import { useEffect, useState, useCallback } from 'react';
// import { TileBoard } from '../strategy/TileBoard';
import axios_api from '../../../assets/config/Axios';
import { usePageStore } from '../../../store/store';
import { getCookie } from '../../../assets/config/Cookie';
import StrategySelect from '../../../component/select_box/StrategySelect';
import { Link } from 'react-router-dom';

export const Bookmark = () => {
  const { page, setPage } = usePageStore();
  // console.log(page);
  const pageSize = 20;
  const 게시물수 = 100;
  const [totalElements, setTotalElements] = useState(게시물수);
  // 총 페이지 수
  const [totalPages, setTotalPages] = useState(10);
  const [items, setItems] = useState([]);
  // console.log(items);
  const token = getCookie('accessToken');
  // console.log(pageEA);

  const fetchData = useCallback(() => {
    axios_api
      .get('/strategies/dibs', {
        headers: {
          authentication: token,
        },
        params: {
          pageSize: pageSize,
          pageNumber: page,
        },
      })
      .then((res) => {
        console.log(res);
        setTotalElements(res.data.totalElements);
        setTotalPages(res.data.totalPages);
        setItems(res.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  useEffect(() => {
    fetchData();
  }, [page]);

  useEffect(() => {
    setPage(1);
  }, []);

  return (
    <section>
      {items.length > 0 ? (
        <>
          <p className='font-KJCbold text-4xl m-9'>북마크</p>
          {/* 필터링 부분 */}
          <article className='flex justify-between'>
            <StrategySelect />
            <SearchbarNone />
          </article>
          <article className='grid 2xl:grid-cols-5 xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 grid-cols-1 content-evenly mx-32'>
            {items.map((item, index) => (
              <BookBoard key={index} item={item} />
            ))}
          </article>
          <article className='my-8'>
            <Pagenation totalPage={totalPages} />
          </article>
          <article className='flex justify-center ml-32 my-8'>
            <SearchbarNone />
          </article>
        </>
      ) : (
        <div className='grid content-center justify-center h-[600px]'>
          <p className='text-center m-10 text-xl font-KJCbold'>
            북마크에 아무런 데이터가 없습니다.
          </p>
          <p className='text-center mb-5'>
            북마크 기능은 마음에 드는 전략 데이터만 저장하는 기능입니다.
          </p>
          <Link
            to='/community/dibs'
            className='grid content-center bg-[#131313] text-[#fefefe] mx-20 p-2 rounded'
          >
            <p className='text-center'>마음에 드는 전략 찜하러 가기</p>
          </Link>
        </div>
      )}
    </section>
  );
};
