import { TileBoard } from './TileBoard';
import { Pagenation } from '../../../component/Pagenation';
import { SearchbarNone } from '../../../component/search/SearchbarNone';
import StrategySelect from '../../../component/select_box/StrategySelect';
import { useEffect, useState, useCallback } from 'react';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';
import { usePageStore } from '../../../store/store';

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
    </section>
  );
};
