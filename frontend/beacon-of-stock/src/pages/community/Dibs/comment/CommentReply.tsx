import React, { useState } from 'react';
import { ReplyReply } from './ReplyReply';
import { ReplyCreate } from './ReplyCreate';
import { ReplyMain } from './ReplyMain';

type ReplyProps = {
  comments: any[];
  changeImeeey: () => void;
};

export const CommentReply: React.FC<ReplyProps> = ({
  comments,
  changeImeeey,
}) => {
  const [createState, setCreateState] = useState<number>(-1);

  return (
    <ul className='bg-[#fefefe] p-2 rounded'>
      {comments.map((comment, index) => (
        <div key={index}>
          {index > 0 && <hr />}
          <ReplyMain
            comment={comment}
            changeImeeey={changeImeeey}
            setCreateState={setCreateState}
          />
          {/* 답글 쓰기 버튼이 눌리면 활성화 되는 컴포넌트 */}
          <ReplyCreate
            comment={comment}
            createState={createState}
            setCreateState={setCreateState}
          />
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
