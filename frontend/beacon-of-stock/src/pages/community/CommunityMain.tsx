import { CommunityNav } from './CommunityNav';
import { Outlet } from 'react-router-dom';

const CommunityMain = () => {
  return (
    <main>
      <section className='flex justify-start'>
        {/* 왼쪽 게시판 Navigation bar */}
        <article className='w-40 mr-9'>
          <CommunityNav />
        </article>
        <article className='w-full'>
          <Outlet />
        </article>
      </section>
    </main>
  );
};

export default CommunityMain;
