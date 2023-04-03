import React, { Dispatch, SetStateAction } from 'react';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';
import thumbUpTrue from '../../../assets/img/thumbs-up-solid.svg';
import thumbUp from '../../../assets/img/thumbs-up-regular.svg';

interface DetailButtonProps {
  boardId: number;
  isLike: boolean;
  isBookmark: boolean;
  setChangeLike: () => void;
  // setIsLike: Dispatch<SetStateAction<boolean>>;
  // setIsBookmark: Dispatch<SetStateAction<boolean>>;
}

export const DetailButton: React.FC<DetailButtonProps> = ({
  boardId,
  isLike,
  isBookmark,
  setChangeLike,
  // setIsLike,
  // setIsBookmark,
}) => {
  // console.log(isLike);
  const fileCopy = require('../../../assets/img/file_copy.png');
  const token = getCookie('accessToken');

  const clickLikeButton = () => {
    axios_api
      .post(
        `/boards/likes/${boardId}`,
        { boardId },
        {
          headers: { authentication: token },
        }
      )
      .then((res) => {
        console.log('보낸다');
        console.log(res);
        setChangeLike();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const clickBookmarkButton = () => {
    axios_api
      .post(`/boards/boards/dibs/${boardId}`, {
        headers: { authentication: token },
      })
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <div className='flex justify-center mt-6'>
      <button
        className='flex items-center text-[#808080] mr-6'
        onClick={() => {
          // setIsLike(!isLike);
          clickLikeButton();
        }}
      >
        <img
          src={!isLike ? thumbUp : thumbUpTrue}
          alt='like'
          className='w-6 h-6 mr-2'
        />
        <p>좋아요</p>
      </button>
      <button
        className='flex items-center text-[#808080]'
        onClick={() => {
          clickBookmarkButton();
          // setIsBookmark(!isBookmark);
        }}
      >
        <img
          src={!isBookmark ? fileCopy : null}
          alt='take-bookmark'
          className='w-6 h-6 mr-2'
        />
        <p>북마크에 담기</p>
      </button>
    </div>
  );
};
