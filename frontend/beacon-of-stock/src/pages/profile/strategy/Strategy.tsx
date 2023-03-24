import { TileBoard } from '../../../component/TileBoard';
import StrategySelect from './StrategySelect';
import { Pagenation } from '../../../component/Pagenation';

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
      <p className='font-KJCbold text-5xl m-9'>내 전략조회</p>
      <StrategySelect />
      <article className='grid 2xl:grid-cols-5 xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 grid-cols-1 content-evenly mx-32'>
        {items.map((item, index) => (
          <TileBoard key={index} item={item} />
        ))}
      </article>
      <Pagenation pageEA={pageEA} />
      <article></article>
    </section>
  );
};
