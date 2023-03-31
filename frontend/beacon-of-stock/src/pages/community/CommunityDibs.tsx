import { ForumComponent } from './forum/ForumComponent';
import { useLocation } from 'react-router-dom';

export const CommunityDibs = () => {
  const location = useLocation();
  // console.log(location);
  return (
    <article>
      <p className='m-9 text-3xl font-bold bg-cyan-600 text-[#fefefe] text-center w-[280px] h-12 grid content-center rounded-[4px]'>
        전략 공유 게시판
      </p>
      <ForumComponent location={location} />
    </article>
  );
};
