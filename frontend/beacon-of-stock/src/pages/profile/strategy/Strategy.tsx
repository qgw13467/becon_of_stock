import { OneOfStrategy } from './OneOfStrategy';

export const Strategy = () => {
  const PAGE_SIZE = 30; // 한 페이지에 보여질 게시글 수
  const 게시물수 = 100; // back에서 총 게시물을 받아오게 할 것인가? x
  // 더미
  const items: number[] = Array(PAGE_SIZE).fill(0);
  return (
    <div>
      <p className='font-KJCbold text-5xl m-9'>내 전략조회</p>
      <div className='grid 2xl:grid-cols-5 xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 grid-cols-1 content-evenly mx-32'>
        {items.map((item, index) => (
          <OneOfStrategy key={index} item={item} />
        ))}
      </div>
    </div>
  );
};
