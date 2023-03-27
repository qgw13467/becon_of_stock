import { Link } from 'react-router-dom';

export const CommunityNav = () => {
  return (
    <aside className=' py-4 grid justify-center gap-y-4 border-2 border-[#D7609E] rounded-md w-40 h-[404px] m-9 sticky top-24'>
      <Link
        to='/community/dibs'
        className='text-lg font-KJCbold text-[#D7609E]'
      >
        전략 공유 게시판
      </Link>
      <hr className='bg-[#D7609E] border border-[#D7609E]' />
      <h3 className='text-lg font-KJCbold text-[#D7609E]'>대회 게시판</h3>
      <h5 className='font-KJCbold'>진행 중인 대회</h5>
      {/* 대회 리스트 받아오면 map함수 써서 link 반복 시켜버릴 것 */}
      <Link
        to='/community/contests/1'
        state={1}
        className='ml-1 cursor-pointer'
      >
        ㄴ임의의 대회 1
      </Link>
      <Link
        to='/community/contests/2'
        state={2}
        className='ml-1 cursor-pointer'
      >
        ㄴ임의의 대회 2
      </Link>
      <Link
        to='/community/contests/3'
        state={3}
        className='ml-1 cursor-pointer'
      >
        ㄴ임의의 대회 3
      </Link>
      <h5 className='font-KJCbold'>종료된 대회</h5>
      <Link
        to='/community/contests/4'
        state={4}
        className='ml-1 cursor-pointer'
      >
        ㄴ임의의 대회 4
      </Link>
      <Link
        to='/community/contests/5'
        state={5}
        className='ml-1 cursor-pointer'
      >
        ㄴ임의의 대회 5
      </Link>
    </aside>
  );
};
