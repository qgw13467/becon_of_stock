import { memo } from 'react';
import { Link } from 'react-router-dom';

type ForumBoard = {
  item: {
    boardId: number;
    commentNum: number;
    createDate: string;
    hit: number;
    likeNum: number;
    memberId: number;
    nickname: string;
    title: string;
  };
};

export const ForumBoard = memo(({ item }: ForumBoard) => {
  // =======================================================================
  // 일정 수치를 뛰어넘는 조회수, 추천수가 있다면 스타일 태그를 다르게 적용하기 위한 부분.
  const defaultStyle = 'text-center';
  const hitRedStyle = 'text-center text-red-500 font-KJCbold';
  const likeRedStyle = 'text-center text-red-500 font-KJCbold';
  const thisHitStyle = item.hit >= 100 ? hitRedStyle : defaultStyle;
  const thisLikeStyle = item.likeNum >= 10 ? likeRedStyle : defaultStyle;
  // =======================================================================
  return (
    <section className='my-2'>
      <article className='grid justify-between grid-cols-11 my-4 '>
        <p className='text-center'>{item.boardId}</p>
        <Link
          to={`/community/detail/${item.boardId}`}
          className='text-center col-span-4'
        >
          {item.title} {item.commentNum > 0 ? <>({item.commentNum})</> : null}
        </Link>
        <p className='text-center col-span-2'>{item.nickname}</p>
        <p className='text-center col-span-2'>
          {String(item.createDate).replace('T', ' ')}
        </p>
        <p className={thisHitStyle}>{item.hit}</p>
        <p className={thisLikeStyle}>{item.likeNum}</p>
      </article>
    </section>
  );
});
