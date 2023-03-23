import { OneOfStrategy } from './OneOfStrategy';
import StrategySelect from './StrategySelect';

export const Strategy = () => {
  const PAGE_SIZE = 30; // 한 페이지에 보여질 게시글 수
  const 게시물수 = 10000; // back에서 총 게시물을 받아오게 할 것인가? x
  const pageEA = Math.floor(게시물수 / PAGE_SIZE);
  // 더미
  const items: number[] = Array(PAGE_SIZE).fill(0);
  // 총 페이지 수
  const pageItems: number[] = Array(pageEA).fill(0);
  // console.log(pageEA);
  let first = 0;
  let end = 10;
  let this_page = 1;
  const prevClick = () => {
    console.log('실행 됨');
    if (first === 0) {
      return; // first가 0일 때는 작동하지 않음
    }
    this_page--;
    first = Math.max(0, first - 10); // first에 -10을 주되, 0보다 작아지지 않도록 조정
    end -= 10; // end에 -10을 주기
  };
  const nextClick = () => {
    console.log('실행 됨');
    if (this_page >= pageEA) {
      return; // 마지막 페이지인 경우, 넘어가지 않음
    }
    this_page++;
    first += 10; // first에 +10을 주기
    end = Math.min(pageEA, end + 10); // end에 +10을 주되, 게시물 수를 넘어가지 않도록 조정
  };
  return (
    <section>
      <p className='font-KJCbold text-5xl m-9'>내 전략조회</p>
      <StrategySelect />
      <article className='grid 2xl:grid-cols-5 xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 grid-cols-1 content-evenly mx-32'>
        {items.map((item, index) => (
          <OneOfStrategy key={index} item={item} />
        ))}
      </article>
      <article className='flex justify-center m-auto p-auto'>
        <button
          id='prev-button'
          onClick={() => prevClick()}
          className='box border-2 border-[#6773BB] rounded-md bg-[#6773BB] text-[#fefefe] text-lg w-16 h-12 my-4 ml-4 mr-3'
        >
          prev
        </button>
        <div className='grid grid-cols-10'>
          {pageItems.splice(first, end).map((item, index) => (
            <div className='text-center m-auto px-1 text-lg'>{index + 1}</div>
          ))}
        </div>
        <button
          id='next-button'
          onClick={() => nextClick()}
          className='box border-2 border-[#6773BB] rounded-md bg-[#6773BB] text-[#fefefe] text-lg w-16 h-12 my-4 mr-4 ml-4'
        >
          next
        </button>
      </article>
      <article></article>
    </section>
  );
};
