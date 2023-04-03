import { useCallback, useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';
import { DetailButton } from './DetailButton';
import DetailComment from './DetailComment';

const Detail = () => {
  const followAdd = require('../../../assets/img/person_add.png');
  const navigate = useNavigate();
  const location = useLocation();
  const boardId = location.pathname.split('/').pop();
  const token = getCookie('accessToken');
  const [data, setData] = useState<any>({});
  const [isLike, setIsLike] = useState<boolean>(false);
  const [isBookmark, setIsBookmark] = useState<boolean>(false);

  // boardId를 사용하여 해당 게시물 정보를 가져오는 로직
  useEffect(() => {
    axios_api
      .get(`/boards/${boardId}`, {
        headers: {
          authentication: token,
        },
      })
      .then((res) => {
        console.log(res.data);
        setData(res.data);
        setIsLike(res.data.likeStatus);
        setIsBookmark(res.data.dibStatus);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <section className='m-9 px-9'>
      <p className='my-9 text-3xl font-bold bg-cyan-600 text-white text-center w-[280px] h-12 grid place-content-center rounded-md'>
        전략 공유 게시판
      </p>
      <div className='flex justify-between items-center'>
        <p className='text-2xl font-bold'>{data.title}</p>
        <button
          className='px-4 py-2 rounded-md bg-gray-200 hover:bg-gray-300'
          onClick={() => navigate(-1)}
        >
          목록
        </button>
      </div>
      <div className='mt-6'>
        <div className='flex  justify-between items-center'>
          <div className='flex justify-center'>
            <button className='w-8 h-8 mr-4'>
              <img src={followAdd} alt='followImg' />
            </button>
            <p className='text-[#808080]'>{data.nickname}</p>
          </div>
          <p>{data.title}</p>
          <div className='flex justify-center'>
            <p className='ml-4 text-[#808080]'>{data.createDate}</p>
            <div className='flex justify-center ml-8'>
              <p className='ml-4 text-[#808080]'>조회: {data.hit}</p>
              <p className='ml-4 text-[#808080]'>추천: {data.likeNum}</p>
            </div>
          </div>
        </div>
      </div>
      <div className='flex justify-between mt-10'>
        <p className='text-[#131313] text-lg indent-4'>{data.content}</p>
        <div className='w-64 h-64 bg-gray-200 rounded-md'></div>
      </div>
      <DetailButton
        boardId={data.boardId}
        isLike={isLike}
        isBookmark={isBookmark}
        setIsLike={setIsLike}
        setIsBookmark={setIsBookmark}
      />
      <div className='flex justify-end my-6'>
        <button className='px-4 mr-2'>수정</button>
        <p className='mx-10 text-[#808080]'>|</p>
        <button className='px-4'>삭제</button>
      </div>
      <DetailComment boardId={data.boardId} />
    </section>

    // <section className='m-9 px-9'>
    //   <p className='my-9 text-3xl font-bold bg-cyan-600 text-[#fefefe] text-center w-[280px] h-12 grid content-center rounded-[4px]'>
    //     전략 공유 게시판
    //   </p>
    //   <div className='flex justify-between'>
    //     <p>{data.title}</p>
    //     <button>목록</button>
    //   </div>
    //   <div className='flex justify-evenly'>
    //     <button>
    //       <img src='' alt='followImg' />
    //     </button>
    //     <p>{data.nickname}</p>
    //     <p>{data.createDate}</p>
    //     <p>조회 : {data.hit}</p>
    //     <p>추천 : {data.likeNum}</p>
    //   </div>
    //   <div className='flex justify-between'>
    //     <p>{data.content}</p>
    //     <div>{/* 전략 정보 들어올 곳 */}</div>
    //   </div>
    //   <div className='flex justify-center'>
    //     <button className='flex justify-center'>
    //       <img src='' alt='like' />
    //       <p>좋아요</p>
    //     </button>
    //     <button className='flex justify-center'>
    //       <img src='' alt='take-bookmark' />
    //       <p>북마크에 담기</p>
    //     </button>
    //   </div>
    //   <div className='flex justify-end'>
    //     <button>수정</button>
    //     <p className='mx-10'>|</p>
    //     <button>삭제</button>
    //   </div>
    //   <DetailComment boardId={data.boardId} />
    //   {/* 이하 자세한 내용 생략 */}
    // </section>
  );
};

export default Detail;
