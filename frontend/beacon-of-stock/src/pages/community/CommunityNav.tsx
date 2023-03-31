import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios_api from '../../assets/config/Axios';
import { getCookie } from '../../assets/config/Cookie';

interface Contest {
  type: number;
  contestId: number;
}

export const CommunityNav = () => {
  const token = getCookie('accessToken');
  const [content, setContent] = useState<Contest[]>([]);
  useEffect(() => {
    axios_api
      .get('/contests', {
        headers: {
          authentication: token,
        },
      })
      .then((res) => {
        console.log(res);
        setContent(res.data.content);
        // console.log(content);
      })
      .catch((err) => console.log(err));
  }, []);

  return (
    <div className=' py-4 grid justify-center gap-y-4 border-2 border-[#D7609E] rounded-md w-40 h-[404px] m-9 sticky top-8'>
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
      <>
        {content.map((item, index) => {
          // console.log(index);
          if (item.type === 0) {
            return (
              <div key={index}>
                <Link
                  to={`/community/contests/${item.contestId}`}
                  state={index + 1}
                  className='ml-1 cursor-pointer'
                >
                  ㄴ임의의 대회 {index + 1}
                </Link>
              </div>
            );
          }
        })}
      </>
      <h5 className='font-KJCbold'>종료된 대회</h5>
      <>
        {content.map((item, index) => {
          // console.log(index);
          if (item.type === 1) {
            return (
              <div key={index}>
                <Link
                  to={`/community/contests/${item.contestId}`}
                  state={index + 1}
                  className='ml-1 cursor-pointer'
                >
                  ㄴ임의의 대회 {index + 1}
                </Link>
              </div>
            );
          }
        })}
      </>
    </div>
  );
};
