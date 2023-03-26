import { CommunityNav } from './CommunityNav';
import { Pagenation } from '../../component/Pagenation';
import { ForumComponent } from '../../component/ForumComponent';

const CommunityMain = () => {
  const PAGE_SIZE = 30; // 한 페이지에 보여질 게시글 수
  const 게시물수 = 10000; // back에서 총 게시물을 받아오게 할 것인가? x
  // 총 페이지 수
  const pageEA = Math.floor(게시물수 / PAGE_SIZE);

  return (
    <main>
      <section className='flex justify-start'>
        {/* 왼쪽 게시판 Navigation bar */}
        <CommunityNav />
        <article>
          <p className='m-9 text-3xl font-bold bg-cyan-600 text-[#fefefe] text-center w-[280px] h-12 grid content-center rounded-[4px]'>
            전략 공유 게시판
          </p>
        </article>
      </section>
      <section>
        <Pagenation pageEA={pageEA} />
      </section>
    </main>
  );
};

export default CommunityMain;
