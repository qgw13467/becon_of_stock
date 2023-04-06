import { Pagenation } from '../../../component/Pagenation';
import { BookBoard } from './BookBoard';
import { useEffect, useState, useCallback } from 'react';
import axios_api from '../../../assets/config/Axios';
import { usePageStore } from '../../../store/store';
import { getCookie } from '../../../assets/config/Cookie';
import { Link } from 'react-router-dom';

export const Bookmark = () => {
  const { page, setPage } = usePageStore();
  const [totalPages, setTotalPages] = useState(10);
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true); // 로딩 상태 추가
  const token = getCookie('accessToken');

  const fetchData = useCallback(() => {
    setLoading(true); // 데이터를 요청할 때 로딩 상태 설정
    axios_api
      .get('/boards/dibs', {
        headers: {
          authentication: token,
        },
        params: {
          // pageSize: pageSize,
          // page: 0,
          page: page - 1,
        },
      })
      .then((res) => {
        // console.log(res);
        // setTotalElements(res.data.totalElements);
        setTotalPages(res.data.totalPages);
        setItems(res.data.content);
      })
      .catch((err) => {
        console.log(err);
      })
      .finally(() => {
        setLoading(false); // 데이터 요청 완료 후 로딩 상태 설정
      });
  }, [page]);

  useEffect(() => {
    fetchData();
  }, [page]);

  useEffect(() => {
    setPage(1);
  }, []);

  return (
    <section>
      {loading ? (
        <>
          <div className='flex justify-center items-center h-[600px]'>
            <div className='animate-pulse rounded-md bg-gray-200 w-64 h-12'></div>
          </div>
        </>
      ) : (
        <>
          {items.length > 0 ? (
            <>
              <p className='font-KJCbold text-2xl my-9 mx-32'>북마크</p>
              {/* 필터링 부분 */}
              {/* <article className='flex justify-between'>
                <StrategySelect />
                <SearchbarNone />
              </article> */}
              <article className='flex justify-around w-1/2'>
                <p className='border w-1/2 text-center rounded ml-32 border-[#131313]'>
                  작성자
                </p>
                <p className='border w-full text-center rounded mr-32 border-[#131313]'>
                  제목
                </p>
              </article>
              <article className='grid grid-cols-2 content-evenly mx-32'>
                {items.map((item, index) => (
                  <BookBoard key={index} item={item} />
                ))}
              </article>
              <article className='my-8'>
                <Pagenation totalPage={totalPages} />
              </article>
              {/* <article className='flex justify-center ml-32 my-8'>
                <SearchbarNone />
              </article> */}
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
        </>
      )}
    </section>
  );
};
