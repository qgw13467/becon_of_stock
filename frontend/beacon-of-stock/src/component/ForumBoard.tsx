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
  console.log(item);
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
        <p className='text-center'>{item.hit}</p>
        <p className='text-center'>{item.likeNum}</p>
      </article>
    </section>
  );
});
