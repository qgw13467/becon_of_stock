import { CommunityNav } from './CommunityNav';
import { Outlet } from 'react-router-dom';

const CommunityMain = () => {
  return (
    <main>
      <section className='flex justify-start'>
        {/* 왼쪽 게시판 Navigation bar */}
        <CommunityNav />
        <article>
          <Outlet />
        </article>
      </section>
    </main>
  );
};

export default CommunityMain;
