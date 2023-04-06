import React, { useEffect, useState } from 'react';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';
import { useContestStateStore } from '../../../store/store';

type contestProps = {
  contestId: number;
};

export const AttendList: React.FC<contestProps> = ({ contestId }) => {
  const { falseState } = useContestStateStore();
  const [content, setContent] = useState<any[]>([]);
  const token = getCookie('accessToken');
  useEffect(() => {
    axios_api
      .get(`/contests/status/${contestId}`, {
        headers: { authentication: token },
      })
      .then((res) => {
        // console.log(res.data);
        setContent(res.data.content);
        // axios_api
        //   .put(`/`)
      })
      .catch((err) => {
        console.log(err);
      });
  }, [contestId]);
  return (
    <div>
      {content.length > 0 ? (
        <>
          {falseState && (
            <p className='m-4 border border-emerald-700'>종료된 대회 입니다.</p>
          )}
          <div className='w-40 bg-emerald-700 text-[#fefefe] rounded my-4 p-1'>
            <p className='text-center'>참여자 명단</p>
          </div>
          <div className='grid grid-cols-3 border border-emerald-700 rounded my-4'>
            {content.slice(0, 3).map((item, index) => {
              return (
                <div
                  key={index}
                  className='flex justify-center border border-emerald-700 rounded m-1 py-4'
                >
                  <p>{index + 1} : </p>
                  <p>{item.userNickname}</p>
                </div>
              );
            })}
          </div>
          {content.length > 3 && (
            <div className='grid grid-cols-10 border border-emerald-700 rounded'>
              {content.slice(3).map((item, index) => {
                return (
                  <div
                    key={index}
                    className='flex justify-center border border-emerald-700 rounded m-1'
                  >
                    <p>{index + 4} : </p>
                    <p>{item.userNickname}</p>
                  </div>
                );
              })}
            </div>
          )}
        </>
      ) : (
        <div className='text-xl m-9'>
          {!falseState ? (
            <>
              <p className='m-4'>아직 대회 참여자가 없습니다.</p>
              <p className='m-4'>
                오른쪽의 대회 참가버튼을 통해 대회에 참가해보세요.
              </p>
            </>
          ) : (
            <>
              <p className='m-4'>이미 종료된 대회입니다.</p>
              <p className='m-4'>작성된 글이 없습니다.</p>
              <p className='m-4'>더 이상 글을 작성하실 수 없습니다.</p>
            </>
          )}
        </div>
      )}
    </div>
  );
};
