import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
type BookItem = {
  nickname: string;
  title: string;
  createDate: string;
  boardId: number;
  commentNum: number;
  hit: number;
  likeNum: number;
  memberId: number;
};

type BookBoard = {
  item: BookItem;
};
export const BookBoard = ({ item }: BookBoard) => {
  const [isHovered, setIsHovered] = useState(false);
  const navigate = useNavigate();

  const goDetail = () => {
    navigate(`/community/detail/${item.boardId}`);
  };

  return (
    <div className='flex justify-evenly mr-32 mt-4 border h-14 rounded pt-4 border-[#7D8AD8]'>
      <div className=' w-1/2 text-center '>{item.nickname}</div>
      <div>:</div>
      <div className='flex justify-center w-full text-center relative'>
        <div>{item.title} </div>
        <div className='ml-2'> ({item.commentNum})</div>
        <div
          onMouseEnter={() => setIsHovered(true)}
          onMouseLeave={() => setIsHovered(false)}
        >
          {isHovered ? (
            <button
              className='absolute bg-[#7D8AD8] -right-8 w-32 h-10 -top-4 rounded-md text-[#FEFEFE]'
              onClick={() => goDetail()}
            >
              게시글보러 가기
            </button>
          ) : (
            <div className='absolute bg-[#7D8AD8] -right-8 w-8 h-10 -top-4 rounded-sm'></div>
          )}
        </div>
      </div>
    </div>
  );
};
