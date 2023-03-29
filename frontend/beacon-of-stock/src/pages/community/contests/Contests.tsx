import { useLocation } from 'react-router-dom';
import { ForumComponent } from '../forum/ForumComponent';

export const Contests = () => {
  const location = useLocation();
  // console.log(location.state); << 이거 써서 Back에 데이터 요청하자!

  return (
    <article>
      <p className='m-9 text-3xl font-bold bg-cyan-600 text-[#fefefe] text-center w-[280px] h-12 grid content-center rounded-[4px]'>
        대회 게시판
      </p>
      <p>{location.state}</p>
      <ForumComponent />
    </article>
  );
};
