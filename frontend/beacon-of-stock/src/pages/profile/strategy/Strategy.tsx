import { TileBoard } from '../../../component/TileBoard';
import { Pagenation } from '../../../component/Pagenation';
import { SearchbarNone } from '../../../component/search/SearchbarNone';
import StrategySelect from '../../../component/select_box/StrategySelect';

export const Strategy = () => {
  const PAGE_SIZE = 20; // 한 페이지에 보여질 게시글 수
  const 게시물수 = 10000; // back에서 총 게시물을 받아오게 할 것인가? x
  // 총 페이지 수
  const pageEA = Math.floor(게시물수 / PAGE_SIZE);
  // 더미
  const items: number[] = Array(PAGE_SIZE).fill(0);
  // console.log(pageEA);

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
        <Pagenation totalPage={pageEA} />
      </article>
      <article className='flex justify-center ml-32 my-8'>
        <SearchbarNone />
      </article>
    </section>
  );
};
