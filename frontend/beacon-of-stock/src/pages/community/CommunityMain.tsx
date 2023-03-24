import { CommunityNav } from './CommunityNav';

const CommunityMain = () => {
  const PAGE_SIZE = 30; // 한 페이지에 보여질 게시글 수
  const 게시물수 = 10000; // back에서 총 게시물을 받아오게 할 것인가? x
  // 총 페이지 수
  const pageEA = Math.floor(게시물수 / PAGE_SIZE);

  return (
    <main className='grid grid-cols-2'>
      <CommunityNav />
      <p className='m-9'>커뮤니티 페이지 입니다</p>
    </main>
  );
};

export default CommunityMain;
