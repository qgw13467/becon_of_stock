import { TileBoard } from './TileBoard';
import { Pagenation } from '../../../component/Pagenation';
import { SearchbarNone } from '../../../component/search/SearchbarNone';
import StrategySelect from '../../../component/select_box/StrategySelect';
import { useEffect, useState, useCallback } from 'react';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';
import { usePageStore } from '../../../store/store';
import { Link } from 'react-router-dom';

export const Strategy = () => {
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

  const fetchData = useCallback(() => {
    axios_api
      .get('/strategies', {
        headers: {
          authentication: token,
        },
        params: {
          pageSize: pageSize,
          pageNumber: page,
        },
      })
      .then((res) => {
        // console.log(res);
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
          <p className='font-KJCbold text-4xl m-9'>내 전략조회</p>
          {/* 필터링 부분 */}
          <article className='flex justify-between'>
            <StrategySelect />
            <SearchbarNone />
          </article>
          <article className='grid 2xl:grid-cols-5 xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 grid-cols-1 content-evenly mx-32'>
            {items.map((item, index) => (
              <TileBoard key={index} item={item} />
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
            내 전략에 아무런 데이터가 없습니다.
          </p>
          <p className='text-center mb-5'>
            내 전략보기 기능은 백테스트 후 저장하는 기능입니다.
          </p>
          <Link
            to='/'
            className='grid content-center bg-[#131313] text-[#fefefe] mx-20 p-2 rounded'
          >
            <p className='text-center'>백테스트하러 가기</p>
          </Link>
        </div>
      )}
    </section>
  );
};
