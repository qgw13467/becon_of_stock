import React from 'react';

type ReplyProps = {
  comment: any;
  createState: number;
  setCreateState: any;
};
export const ReplyCreate: React.FC<ReplyProps> = ({
  comment,
  createState,
  setCreateState,
}) => {
  // console.log(comment, createState);
  if (comment.commentId === createState) {
    return <div className='bg-[#808080] ml-4 p-2 rounded text-[#fefefe]'></div>;
  } else {
    return <></>;
  }
};
