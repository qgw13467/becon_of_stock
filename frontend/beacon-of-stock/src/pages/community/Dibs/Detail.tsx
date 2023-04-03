import { useCallback, useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';
import { DetailButton } from './DetailButton';
import DetailComment from './comment/DetailComment';
import DetailSkeleton from './DetailSkeleton';
import followAdd from '../../../assets/img/person_add.png';

const Detail = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const boardId = location.pathname.split('/').pop();
  const token = getCookie('accessToken');
  const [data, setData] = useState<any>({});
  const [changeLike, setChangeLike] = useState<boolean>(false);
  const changeLikeChange = () => {
    setChangeLike(!changeLike);
  };
  // const [isLike, setIsLike] = useState<boolean>(false);
  // const [isBookmark, setIsBookmark] = useState<boolean>(false);
  const [isLoading, setIsLoading] = useState<boolean>(true);

  // boardId를 사용하여 해당 게시물 정보를 가져오는 로직
  useEffect(() => {
    axios_api
      .get(`/boards/${boardId}`, {
        headers: {
          authentication: token,
        },
      })
      .then((res) => {
        console.log(res.data, 'Detail Main');
        setData(res.data);
        setIsLoading(false); // 데이터 로딩이 완료됨을 알리는 상태값 변경
      })
      .catch((err) => {
        console.log(err);
      });
  }, [token, changeLike]);

  if (isLoading) {
    return <DetailSkeleton />;
  }

  return (
    <section className='m-9 px-9'>
      <p className='my-9 text-3xl font-bold bg-cyan-600 text-white text-center w-[280px] h-12 grid place-content-center rounded-md'>
        전략 공유 게시판
      </p>
      <div className='mt-6'>
        <div className='flex  justify-between items-center'>
          <div className='grid grid-cols-2 items-center'>
            <img
              src={followAdd}
              alt='followImg'
              className='w-8 h-8 mr-4 cursor-pointer'
            />
            <p className='text-[#808080]'>{data.nickname}</p>
          </div>
          <p>{data.title}</p>
          <div className='flex justify-center'>
            <p className='ml-4 text-[#808080] grid content-center'>
              {String(data.createDate).replace('T', ' ')}
            </p>
            <div className='flex justify-center ml-8'>
              <p className='ml-4 text-[#808080] grid content-center'>
                조회: {data.hit}
              </p>
              <p className='ml-4 text-[#808080] grid content-center'>
                추천: {data.likeNum}
              </p>
            </div>
            <button
              onClick={() => navigate('/community/dibs')}
              className='border-2 border-[#3E7CBC] p-2 ml-4 rounded-sm bg-[#3E7CBC] text-[#fefefe]'
            >
              <p>목록</p>
            </button>
          </div>
        </div>
      </div>
      <hr className='bg-[#808080] mt-4' />
      <div className='flex justify-between mt-8'>
        <p className='text-[#131313] text-lg indent-4'>{data.content}</p>
        <div className='w-64 h-64 bg-gray-200 rounded-md'></div>
      </div>
      <DetailButton
        boardId={data.boardId}
        // isLike={isLike}
        // isBookmark={isBookmark}
        isLike={data.likeStatus}
        isBookmark={data.dibStatus}
        setChangeLike={changeLikeChange}
        // setIsLike={setIsLike}
        // setIsBookmark={setIsBookmark}
      />
      {/* 현재 유저와 게시글의 유저가 같을 때만 보이게. */}
      {data.isAuthor ? (
        <div className='flex justify-end my-6'>
          <button className='px-1 mr-2'>수정</button>
          <p className='mx-8 text-[#808080]'>|</p>
          <button className='px-1'>삭제</button>
        </div>
      ) : (
        <div className='my-[72px]'></div>
      )}

      <hr className='bg-[#808080] my-4' />
      <DetailComment boardId={data.boardId} />
    </section>
  );
};

export default Detail;
