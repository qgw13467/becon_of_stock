import React from 'react';

type ReplyReplyProp = {
  child: any;
};

export const ReplyReply: React.FC<ReplyReplyProp> = ({ child }) => {
  // console.log(child);
  return (
    <li className='my-2 flex justify-between'>
      <p>
        <span className='mr-2'>ㄴ{child.userNickname}:</span>
        <span className='ml-4'>{child.content}</span>
        {child.modified && <p>(수정됨)</p>}
      </p>
      {child.isAuthor && (
        <p>
          <span>수정</span>
          <span className='mx-4'> | </span>
          <span>삭제</span>
        </p>
      )}
    </li>
  );
};
