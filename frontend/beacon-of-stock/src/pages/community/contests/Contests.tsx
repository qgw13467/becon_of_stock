import { CommunityContest } from './CommunityContest';

export const Contests = () => {
  return (
    <article>
      <p className='m-9 text-3xl font-bold bg-cyan-600 text-[#fefefe] text-center w-[280px] h-12 grid content-center rounded-[4px]'>
        대회 게시판
      </p>
      <CommunityContest />
    </article>
  );
};
