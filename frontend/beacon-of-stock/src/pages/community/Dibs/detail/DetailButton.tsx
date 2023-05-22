import React from 'react';
import axios_api from '../../../../assets/config/Axios';
import { getCookie } from '../../../../assets/config/Cookie';
import thumbUpTrue from '../../../../assets/img/thumbs-up-solid.svg';
import thumbUp from '../../../../assets/img/thumbs-up-regular.svg';
import setBookmark from '../../../../assets/img/file_copy.png';
import yaBookmark from '../../../../assets/img/file_copy_1.png';

interface DetailButtonProps {
  boardId: number;
  isLike: boolean;
  isBookmark: boolean;
  setChangeLike: () => void;
  setChangeBookmark: () => void;
}

export const DetailButton: React.FC<DetailButtonProps> = ({
  boardId,
  isLike,
  isBookmark,
  setChangeLike,
  setChangeBookmark,
}) => {
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
        // console.log('보낸다');
        // console.log(res);
        setChangeLike();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const clickBookmarkButton = () => {
    axios_api
      .post(
        `/boards/dibs/${boardId}`,
        { boardId },
        {
          headers: { authentication: token },
        }
      )
      .then((res) => {
        console.log(res);
        // console.log('bookmark에 담겼어요.');
        setChangeBookmark();
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
        }}
      >
        <img
          src={!isBookmark ? setBookmark : yaBookmark}
          alt='take-bookmark'
          className='w-6 h-6 mr-2'
        />
        <p>북마크에 담기</p>
      </button>
    </div>
  );
};
