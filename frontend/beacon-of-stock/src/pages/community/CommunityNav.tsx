import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios_api from '../../assets/config/Axios';
import { getCookie } from '../../assets/config/Cookie';
import { useContestState, useContestStateStore } from '../../store/store';

export const CommunityNav = () => {
  const { contestData, setContestData } = useContestState();
  const { setState } = useContestStateStore();
  // console.log(contestData);
  const token = getCookie('accessToken');
  // const [content, setContent] = useState<Contest[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  useEffect(() => {
    setIsLoading(true); // 로딩 중 상태로 설정

    axios_api
      .get('/contests', {
        headers: {
          authentication: token,
        },
      })
      .then((res) => {
        // console.log(res);
        // setContent(res.data.content);
        setContestData(res.data.content);
      })
      .catch((err) => console.log(err))
      .finally(() => {
        setIsLoading(false); // 로딩이 완료되면 상태 업데이트
      });
  }, []);

  return (
    <div className='py-4 grid justify-center gap-y-4 border-2 border-[#D7609E] rounded-md w-40 h-auto m-9 sticky top-8 indent-3'>
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
      {isLoading ? ( // 로딩 중일 때 보여줄 스켈레톤 코드
        <div className='animate-pulse'>
          <div className='h-6 bg-gray-300 rounded w-4/3 mx-auto my-2'></div>
          <div className='h-6 bg-gray-300 rounded w-4/3 mx-auto my-2'></div>
          <div className='h-6 bg-gray-300 rounded w-4/3 mx-auto my-2'></div>
        </div>
      ) : (
        contestData.map((item, index) => {
          if (item.type === 0) {
            return (
              <div key={index} className='px-2'>
                <Link
                  to={`/community/contests/${item.contestId}`}
                  state={index + 1}
                  className='cursor-pointer'
                  onClick={() => setState(false)}
                >
                  {item.title}
                </Link>
              </div>
            );
          }
        })
      )}
      <h5 className='font-KJCbold'>종료된 대회</h5>
      {isLoading ? ( // 로딩 중일 때 보여줄 스켈레톤 코드
        <div className='animate-pulse'>
          <div className='h-6 bg-gray-300 rounded w-4/3 mx-auto my-2'></div>
          <div className='h-6 bg-gray-300 rounded w-4/3 mx-auto my-2'></div>
          <div className='h-6 bg-gray-300 rounded w-4/3 mx-auto my-2'></div>
        </div>
      ) : (
        contestData.map((item, index) => {
          if (item.type === 1) {
            return (
              <div key={index} className='px-2'>
                <Link
                  to={`/community/contests/${item.contestId}`}
                  state={index + 1}
                  className='cursor-pointer'
                  onClick={() => setState(true)}
                >
                  {item.title}
                </Link>
              </div>
            );
          }
        })
      )}
    </div>
  );
};
