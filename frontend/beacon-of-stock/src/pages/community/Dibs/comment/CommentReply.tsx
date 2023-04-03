import React from 'react';
import { ReplyReply } from './ReplyReply';

type ReplyProps = {
  comments: any[];
};

export const CommentReply: React.FC<ReplyProps> = ({ comments }) => {
  return (
    <ul className='bg-[#fefefe] p-2 rounded'>
      {comments.map((comment, index) => (
        <div key={index}>
          {index > 0 && <hr />}
          <li className='my-2 flex justify-between'>
            <p className='text-gray-700'>
              <span className='font-bold mr-2'>{comment.userNickname}:</span>
              <span className='ml-4'>{comment.content}</span>
            </p>
            <p>
              <span>수정</span>
              <span className='mx-4'> | </span>
              <span>삭제</span>
            </p>
          </li>
          {comment.children !== null && (
            <ul className='bg-[#808080] ml-4 p-2 rounded text-[#fefefe]'>
              {comment.children.map((child: any, childIndex: any) => (
                <div key={childIndex}>
                  {childIndex > 0 && <hr />}
                  <ReplyReply child={child} />
                </div>
              ))}
            </ul>
          )}
        </div>
      ))}
    </ul>
  );
};
